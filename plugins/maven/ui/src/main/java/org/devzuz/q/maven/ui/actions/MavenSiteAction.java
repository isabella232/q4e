/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenEventEnd;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class MavenSiteAction
    extends AbstractMavenAction
{

    protected void runInternal( IAction action )
        throws CoreException
    {
        IMavenProject project = getMavenProject();
        if ( project != null )
        {
            IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IWorkbenchBrowserSupport browserSupport = ww.getWorkbench().getBrowserSupport();
            IMavenListener listener = new SiteBuildListener( project, browserSupport );

            MavenManager.getMaven().addEventListener( listener );
            MavenManager.getMaven().scheduleGoal( project, "site" );
        }
    }

    /**
     * Open generated site in browser if successful
     * 
     * @param project
     */
    private void openSiteInBrowser( IMavenProject project, IWorkbenchBrowserSupport browserSupport )
    {

        // TODO get the target dir from maven as the user may have changed it
        File siteIndex = new File( project.getBaseDirectory() + "/target/site/index.html" );
        if ( !siteIndex.exists() )
        {
            return;
        }

        URL webUrl;
        try
        {
            webUrl = siteIndex.toURI().toURL();
        }
        catch ( MalformedURLException e )
        {
            MavenUiActivator.getLogger().log( "Unable to get URL from " + siteIndex, e );
            return;
        }

        IWebBrowser browser;
        try
        {
            browser = browserSupport.createBrowser( IWorkbenchBrowserSupport.AS_EDITOR, null, "Maven", "Maven reports" );
            browser.openURL( webUrl );
        }
        catch ( PartInitException e )
        {
            MavenUiActivator.getLogger().log( "Unable to open browser for URL " + webUrl, e );
            return;
        }
    }

    /**
     * Open generated site in browser when maven build ends
     * 
     * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
     * @version $Id$
     */
    private class SiteBuildListener
        implements IMavenListener
    {

        private final IMavenProject project;

        private final IWorkbenchBrowserSupport browserSupport;

        public SiteBuildListener( IMavenProject project, IWorkbenchBrowserSupport browserSupport )
        {
            this.project = project;
            this.browserSupport = browserSupport;
        }

        public void dispose()
        {
        }

        public void handleEvent( IMavenEvent event )
        {
            if ( ( event instanceof IMavenEventEnd ) && ( event.getType().equals( EventType.reactorExecution ) ) )
            {
                // TODO this still nees some work
                openSiteInBrowser( project, browserSupport );

                MavenManager.getMaven().removeEventListener( this );
            }
        }

    }

}
