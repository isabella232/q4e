package org.devzuz.q.maven.wtp.core;

import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathAttributeProviderManager;
import org.devzuz.q.maven.jdt.ui.projectimport.ImportProjectPostprocessorManager;
import org.devzuz.q.maven.wtp.core.postprocessor.WtpEnablerPostprocessor;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.IStartup;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MavenWtpActivator extends Plugin implements IStartup
{

    private static final WtpEnablerPostprocessor WTP_ENABLER_POSTPROCESSOR = new WtpEnablerPostprocessor();

    // The plug-in ID
    public static final String PLUGIN_ID = "org.devzuz.q.maven.wtp.core";

    // The shared instance
    private static MavenWtpActivator plugin;

    /**
     * The constructor
     */
    public MavenWtpActivator()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start( BundleContext context ) throws Exception
    {
        super.start( context );
        plugin = this;
        ImportProjectPostprocessorManager.getInstance().registerPostprocessor( WTP_ENABLER_POSTPROCESSOR );
        MavenClasspathAttributeProviderManager.getInstance().registerAttributeProvider( WTP_ENABLER_POSTPROCESSOR );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop( BundleContext context ) throws Exception
    {
        plugin = null;
        super.stop( context );
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static MavenWtpActivator getDefault()
    {
        return plugin;
    }

    public void earlyStartup()
    {
        // TODO Auto-generated method stub

    }

}
