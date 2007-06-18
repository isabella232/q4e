/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.customcomponents.DependencyViewer;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ManageDependenciesDialog
    extends AbstractResizableDialog
{
    private static ManageDependenciesDialog dependencyManagementDialog = null;

    public static synchronized ManageDependenciesDialog getManageDependenciesDialog()
    {
        if ( dependencyManagementDialog == null )
        {
            dependencyManagementDialog = new ManageDependenciesDialog( PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell() );
        }

        return dependencyManagementDialog;
    }

    private DependencyViewer dependencyViewer;

    private List<Dependency> artifacts;

    public ManageDependenciesDialog( Shell shell )
    {
        super( shell );
    }

    protected Control internalCreateDialogArea( Composite container )
    {
        container.setLayout( new GridLayout( 1, false ) );

        dependencyViewer = new DependencyViewer( container, SWT.NONE );
        dependencyViewer.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        return container;
    }

    public void onWindowActivate()
    {
        synchronizeDataSourceWithGUI();
    }

    private void synchronizeDataSourceWithGUI()
    {
        if ( artifacts != null )
        {
            dependencyViewer.setDependencies( artifacts );
            dependencyViewer.refreshArtifactsTable();
        }
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return Activator.getDefault().getPluginPreferences();
    }

    public List<Dependency> getDependencies()
    {
        return artifacts;
    }

    public void setDependencies( List<Dependency> artifacts )
    {
        this.artifacts = Collections.synchronizedList( new LinkedList<Dependency>( artifacts ) );
    }
}
