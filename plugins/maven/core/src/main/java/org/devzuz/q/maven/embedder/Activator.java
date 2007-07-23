/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.devzuz.q.maven.embedder.internal.ExtensionPointHelper;
import org.devzuz.q.maven.embedder.log.EclipseLogger;
import org.devzuz.q.maven.embedder.log.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.core";

    // The shared instance
    private static Activator plugin;

    private EclipseMaven mavenInstance;

    private Logger logger;

    /**
     * The constructor
     */
    public Activator() {
        plugin = this;
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
        logger = new EclipseLogger(PLUGIN_ID, this.getLog());

        // Initialize the maven instance
        mavenInstance = new EclipseMaven();
        mavenInstance.start();
        ExtensionPointHelper.resolveExtensionPoints(this);
    }

    public void stop(BundleContext context) throws Exception {
        plugin = null;
        mavenInstance.stop();
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    public EclipseMaven getMavenInstance() {
        return mavenInstance;
    }

    public void setMavenInstance(EclipseMaven mavenInstance) {
        this.mavenInstance = mavenInstance;
    }

    public static Logger getLogger() {
        return getDefault().logger;
    }

}
