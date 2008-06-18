package org.devzuz.q.util;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

/**
 * Utility code not related to maven that can be used by any other plug-in.
 * 
 * @author amuino
 */
public class QUtil
{
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
