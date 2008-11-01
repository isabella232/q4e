/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.lucene;

import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.devzuz.q.maven.search.lucene.preferences.LucenePreferencesManager;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class LuceneSearchPlugin
    extends AbstractUIPlugin
{

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.search.lucene";

    // The shared instance
    private static LuceneSearchPlugin plugin;

    private Logger logger = new EclipseLogger( PLUGIN_ID, this.getLog() );

    private LucenePreferencesManager preferencesManager;

    private String bundleName;

    private LuceneService luceneService;

    /**
     * The constructor
     */
    public LuceneSearchPlugin()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start( BundleContext context )
        throws Exception
    {
        super.start( context );
        plugin = this;
        this.bundleName = context.getBundle().getSymbolicName();
        this.preferencesManager =
            new LucenePreferencesManager( new ScopedPreferenceStore( new InstanceScope(), this.bundleName ) );
        this.luceneService = new LuceneService( this.preferencesManager.getIndexers() );
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop( BundleContext context )
        throws Exception
    {
        plugin = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static LuceneSearchPlugin getDefault()
    {
        return plugin;
    }

    public static Logger getLogger()
    {
        return getDefault().logger;
    }

    public static LucenePreferencesManager getPreferencesManager()
    {
        return getDefault().preferencesManager;
    }

    public static String getBundleName()
    {
        return getDefault().bundleName;
    }

    public static LuceneService getLuceneService()
    {
        return getDefault().luceneService;
    }
}
