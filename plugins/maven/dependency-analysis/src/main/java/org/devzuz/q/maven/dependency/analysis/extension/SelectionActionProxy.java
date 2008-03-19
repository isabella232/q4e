package org.devzuz.q.maven.dependency.analysis.extension;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.Messages;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

public class SelectionActionProxy
    implements ISelectionAction
{

    private ISelectionAction delegate;

    private IConfigurationElement member;

    public SelectionActionProxy( IConfigurationElement member )
    {
        this.member = member;
    }

    public void execute( IMavenProject project, ISelectionSet primary, ISelectionSet secondary )
        throws CoreException
    {
        if ( delegate == null )
        {
            Object callback = member.createExecutableExtension( SelectionExtension.ATTR_CLASS );
            if ( callback instanceof ISelectionAction )
            {
                delegate = (ISelectionAction) callback;

            }
            else
            {
                throw new CoreException( new Status( IStatus.ERROR, DependencyAnalysisActivator.PLUGIN_ID,
                                                     NLS.bind( Messages.SelectionExtension_Error_Implementation,
                                                               callback.getClass().getName() ) ) );
            }
        }
        delegate.execute( project, primary, secondary );

    }
}
