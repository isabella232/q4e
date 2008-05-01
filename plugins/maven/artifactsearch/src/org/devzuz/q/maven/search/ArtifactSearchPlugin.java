/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search;

import java.util.HashSet;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.devzuz.q.maven.search.preferences.ArtifactSearchPreferencesManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ArtifactSearchPlugin
    extends Plugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.search";

    public static final String SEARCH_PROVIDER_EXTENSION_ID = PLUGIN_ID + ".searchProvider";

    // The shared instance
    private static ArtifactSearchPlugin plugin;

    private Logger logger = new EclipseLogger( PLUGIN_ID, this.getLog() );

    private ArtifactSearchService searchService;

    private ArtifactSearchPreferencesManager searchPreferencesManager;

    private ScopedPreferenceStore prefs;

    private String bundleName;

    /**
     * The constructor
     */
    public ArtifactSearchPlugin()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        plugin = this;
        bundleName = context.getBundle().getSymbolicName();
        prefs = new ScopedPreferenceStore( new InstanceScope(), bundleName );
        searchPreferencesManager = new ArtifactSearchPreferencesManager( prefs );
        searchService =
            new ArtifactSearchService( new HashSet<String>( searchPreferencesManager.getEnabledSearchProviderIds() ) );
        
        new Job( "Initialize artifact search" )
        {
            @Override
            protected IStatus run( IProgressMonitor monitor )
            {
                getSearchService().initializeProviders();
                return new Status( IStatus.OK, PLUGIN_ID, "Completed" );
            }
        }.schedule();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop( BundleContext context )
        throws Exception
    {
        plugin = null;
        prefs.save();
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ArtifactSearchPlugin getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

    public static ArtifactSearchPreferencesManager getSearchPreferencesManager()
    {
        return getDefault().searchPreferencesManager;
    }

    public static String getBundleName()
    {
        return getDefault().bundleName;
    }

    public static ArtifactSearchService getSearchService()
    {
        return getDefault().searchService;
    }
}
