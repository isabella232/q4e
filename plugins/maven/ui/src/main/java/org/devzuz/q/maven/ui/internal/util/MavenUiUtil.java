package org.devzuz.q.maven.ui.internal.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Utility Class for Maven UI
 * 
 * @author aramirez
 */
public class MavenUiUtil
{
    /**
     * Returns the first project in the selection.
     * 
     * @param selection
     * @return project first project in selection. null if selection is not an instanceof IStructuredSelection or the
     *         first selected element is not an IResource
     */
    public static IProject getProjectInSelection( ISelection selection )
    {
        IProject project = null;

        if ( selection instanceof IStructuredSelection )
        {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            Object object = structuredSelection.getFirstElement();

            IResource asResource = adaptAs( IResource.class, object );
            if ( null != asResource )
            {
                project = asResource.getProject();
            }
        }
        return project;
    }

    @SuppressWarnings( "unchecked" )
    private static <T> T adaptAs( Class<T> clazz, Object object )
    {
        if ( object == null )
        {
            return null;
        }
        if ( clazz.isAssignableFrom( object.getClass() ) )
        {
            return (T) object;
        }
        if ( object instanceof IAdaptable )
        {
            return (T) ( (IAdaptable) object ).getAdapter( clazz );
        }
        else
        {
            return (T) Platform.getAdapterManager().getAdapter( object, clazz );
        }
    }
}
