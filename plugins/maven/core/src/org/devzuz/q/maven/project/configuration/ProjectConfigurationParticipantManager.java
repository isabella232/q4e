package org.devzuz.q.maven.project.configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.jdt.project.configuration.internal.PostProcessorDeclaration;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Singleton manager for holding references to the project postprocessors that are applied after a maven project is
 * imported into eclipse.
 * 
 * <b>Prototype code:</b> This is not the best place nor the best way of implementing this, <b>must</b> be refactored.
 * 
 * @author amuino
 * 
 */
public class ProjectConfigurationParticipantManager
{
    private final Collection<PostProcessorDeclaration> postprocessors;

    private final static ProjectConfigurationParticipantManager instance = new ProjectConfigurationParticipantManager();

    private static final String PROJECT_POSTPROCESSOR_EXTENSION_POINT_ID =
        "org.devzuz.q.maven.core.project.configurationParticipants";

    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * Hidden default constructor for the Singleton pattern.
     */
    private ProjectConfigurationParticipantManager()
    {
        postprocessors = initializePostprocessors();
    }

    /**
     * Reads the postprocessor definitions contributed to the
     * <code>org.devzuz.q.maven.jdt.ui.projectPostprocessors</code> extension point.
     */
    private Collection<PostProcessorDeclaration> initializePostprocessors()
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint postprocessorExtensionPoint =
            registry.getExtensionPoint( PROJECT_POSTPROCESSOR_EXTENSION_POINT_ID );
        IExtension[] extensions = postprocessorExtensionPoint.getExtensions();

        Collection<PostProcessorDeclaration> result = new HashSet<PostProcessorDeclaration>( extensions.length );
        for ( IExtension extension : extensions )
        {
            Expression expression = null;
            IConfigurationElement[] configElements = extension.getConfigurationElements();
            String className = configElements[0].getAttribute( CLASS_ATTRIBUTE );
            IConfigurationElement[] enablements = configElements[0].getChildren( "enablement" );
            if ( enablements.length > 0 )
            {
                try
                {
                    expression = ExpressionConverter.getDefault().perform( enablements[0] );
                }
                catch ( CoreException e )
                {
                    MavenCoreActivator.getLogger().log(
                                                        "Could not read enablement for project postprocessor extension",
                                                        e );
                }
            }
            IContributor contributor = extension.getContributor();
            result.add( new PostProcessorDeclaration( className, contributor, expression ) );
        }
        return result;
    }

    /**
     * Obtains the single instance of this class.
     * 
     * @return the single instance of this class.
     */
    public final static ProjectConfigurationParticipantManager getInstance()
    {
        return instance;
    }

    /**
     * Returns the postprocessors that apply for the given project.
     * 
     * If any postprocessor can not be instantiated, this method will be successful, but an exception will be logged.
     * 
     * @param mavenProject
     * @return
     */
    public Set<IProjectConfigurationParticipant> getPostProcessors( IMavenProject mavenProject ) 
    {
        if ( postprocessors.size() == 0 )
        {
            return Collections.emptySet();
        }
        Set<IProjectConfigurationParticipant> result = new HashSet<IProjectConfigurationParticipant>();
        for ( PostProcessorDeclaration decl : postprocessors )
        {
            if ( decl.enablesFor( mavenProject ) )
            {
                IProjectConfigurationParticipant projectPostprocessor;
                try
                {
                    projectPostprocessor = decl.createInstance();
                    result.add( projectPostprocessor );
                }
                catch ( QCoreException e )
                {
                    MavenCoreActivator.getLogger().log( "Unable to instantiate " + decl, e );
                }
            }
        }
        return result;
    }
}
