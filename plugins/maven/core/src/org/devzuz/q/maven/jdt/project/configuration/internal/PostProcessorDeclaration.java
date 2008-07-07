package org.devzuz.q.maven.jdt.project.configuration.internal;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.project.configuration.IProjectConfigurationParticipant;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Class representing a project postprocessor declaration parsed from the extension points.
 * 
 * This class is intended to decouple the data needed to determine applicability of a postprocessor from the
 * implementation, avoiding the need on activate the plug-in contributing the postprocessor.
 * 
 * @author amuino
 */
public class PostProcessorDeclaration
{
    private String postProcessorClassName;

    private IContributor declaringPlugIn;

    private Expression enablementExpression;

    /**
     * Store the data extracted from the extension point in a new instance.
     * 
     * @param postProcessorClassName
     *            the name of the postprocessor class to instantiate.
     * @param declaringPlugIn
     *            the plug-in declaring the postprocessor.
     * @param enablementExpression
     *            the core expression used to check if the postprocessor can be enabled.
     */
    public PostProcessorDeclaration( String postProcessorClassName, IContributor declaringPlugIn,
                                     Expression enablementExpression )
    {
        super();
        this.postProcessorClassName = postProcessorClassName;
        this.declaringPlugIn = declaringPlugIn;
        this.enablementExpression = enablementExpression;
    }

    public boolean enablesFor( IMavenProject project )
    {
        // Backwards compatibility, no <enablement> means always enabled.
        if ( enablementExpression == null )
        {
            return true;
        }
        // An expression has been configured for the extension, evaluate
        try
        {
            EvaluationResult result = enablementExpression.evaluate( new EvaluationContext( null, project.getProject() ) );
            return result == EvaluationResult.TRUE;
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getLogger().log(
                                                "Could not evaluate enablemente expression for import postprocessor: "
                                                                + postProcessorClassName, e );
            return false;
        }
    }

    public IProjectConfigurationParticipant createInstance() throws QCoreException
    {
        Bundle bundle = Platform.getBundle( declaringPlugIn.getName() );

        try
        {
            Class<? extends IProjectConfigurationParticipant> postProcessorClass = bundle.loadClass( postProcessorClassName );
            IProjectConfigurationParticipant newInstance = postProcessorClass.newInstance();
            return newInstance;
        }
        catch ( ClassNotFoundException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                  "Unable to load implementation class for import project postprocessor: "
                                                                  + postProcessorClassName, e ) );
        }
        catch ( ClassCastException e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                  "Implementation class for import project postprocessor is not a subclass of "
                                                                  + IProjectConfigurationParticipant.class.getName() + ": "
                                                                  + postProcessorClassName, e ) );
        }
        catch ( Exception e )
        {
            throw new QCoreException( new Status( IStatus.ERROR, MavenCoreActivator.PLUGIN_ID,
                                                  "Unable to instantiate implementation class for import project postprocessor: "
                                                                  + postProcessorClassName, e ) );
        }
    }

    @Override
    public String toString()
    {
        return "[class: " + postProcessorClassName + ", declared on " + declaringPlugIn + ", enablement: "
                        + enablementExpression + "]";
    }

}
