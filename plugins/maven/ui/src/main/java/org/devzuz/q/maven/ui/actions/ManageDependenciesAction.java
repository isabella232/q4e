/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.dialogs.ManageDependenciesDialog;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;

public class ManageDependenciesAction
    extends AbstractMavenAction
{

    public void runInternal( IAction action )
        throws CoreException
    {
        Object selectedProject = getSelection().iterator().next();
        if ( selectedProject instanceof IProject )
        {
            IFile pomFile = ( (IProject) selectedProject ).getFile( POM_XML );
            // Check if it is a maven project
            if ( pomFile.exists() )
            {
                try
                {
                    ManageDependenciesDialog dialog = ManageDependenciesDialog.getManageDependenciesDialog();
                    dialog.setDependencies( MavenUtils.getDependenciesFromPom( pomFile.getLocation().toFile() ) );

                    if ( dialog.open() == Window.OK )
                    {
                        // write the entire project back to the pom.xml file
                        MavenUtils.rewritePomWithNewDependencies( pomFile.getLocation().toFile(),
                                                                  dialog.getDependencies() );
                        pomFile.refreshLocal( IResource.DEPTH_ZERO, null );
                    }
                }
                catch ( XmlPullParserException e )
                {
                    MavenUiActivator.getLogger().log( e );
                }
                catch ( IOException e )
                {
                    MavenUiActivator.getLogger().log( e );
                }
            }
        }
    }
}
