/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.internal.maven.wagon;

import org.apache.maven.artifact.manager.WagonManager;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MavenWagonActivator
    extends Plugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.wagon";

    private static final String PLEXUS_CONTAINER_CONFIG = "/components.xml";

    // The shared instance
    private static MavenWagonActivator plugin;

    private DefaultPlexusContainer plexusContainer;

    WagonManager wagonManager;

    /**
     * The constructor
     */
    public MavenWagonActivator()
    {
    }

    public EclipseWagonManager getWagonManager()
    {
        try
        {
            return (EclipseWagonManager) plexusContainer.lookup( WagonManager.ROLE );
        }
        catch ( ComponentLookupException e )
        {
            return null;
        }
    }

    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        plugin = this;
        try
        {
            plexusContainer = new DefaultPlexusContainer();
        }
        catch ( Exception e )
        {
            this.getLog().log( new Status( IStatus.ERROR, PLUGIN_ID, 100, "Exception creating plexus container", e ) );
        }
    }

    public void stop( BundleContext context )
        throws Exception
    {
        if ( plexusContainer != null )
        {
            plexusContainer.dispose();
            plexusContainer = null;
        }
        plugin = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static MavenWagonActivator getDefault()
    {
        return plugin;
    }

}
