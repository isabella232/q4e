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

    /**
     * This method finds an adapter for an object to the given class using all the means available in eclipse.
     * <ol>
     * <li>If the object already implements the given class, return the same object.</li>
     * <li>Else, check if the object is an IAdaptable and try its getAdapter() method</li>
     * <li>Else, do a look up using the Platform Adapter Manager.</li>
     * </ol>
     * 
     * @param clazz
     *            class to adapt to
     * @param object
     *            the object to be adapted.
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public static <T> T adaptAs( Class<T> clazz, Object object )
    {
        if ( object == null )
        {
            // can't adapt null
            return null;
        }
        if ( clazz.isAssignableFrom( object.getClass() ) )
        {
            // the object is or extends the requested class
            return (T) object;
        }
        if ( object instanceof IAdaptable )
        {
            // try to adapt through the interface
            T adapted = (T) ( (IAdaptable) object ).getAdapter( clazz );
            if ( adapted != null )
            {
                // adapting succeeded
                return adapted;
            }
        }
        // nothing worked, try the platform adapted manager
        return (T) Platform.getAdapterManager().getAdapter( object, clazz );
    }
}
