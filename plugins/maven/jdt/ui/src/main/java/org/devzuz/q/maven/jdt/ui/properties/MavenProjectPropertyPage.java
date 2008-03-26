/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.jdt.ui.properties;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.jdt.core.builder.MavenIncrementalBuilder;
import org.devzuz.q.maven.jdt.core.properties.MavenPropertyManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * This class provides a property page for displaying the Maven JDT preferences managed by the MavenPropertyManager.
 * 
 * @author staticsnow@gmail.com
 * 
 */
public class MavenProjectPropertyPage extends PropertyPage
{

    final Set<String> newExcludedResourceGoals = new HashSet<String>();

    final Set<String> newExcludedTestResourceGoals = new HashSet<String>();

    @Override
    protected Control createContents( Composite parent )
    {
        try
        {
            final Composite composite = new Composite( parent, SWT.NONE );
            GridLayout rowLayout = new GridLayout( 1, true );
            rowLayout.verticalSpacing = 20;
            composite.setLayout( rowLayout );
            Set<String> excludedResourceGoals =
                MavenPropertyManager.getInstance().getResourceExcludedGoals( getProject() );
            buildGoalTableRow( composite, "Execute on resource build", newExcludedResourceGoals, excludedResourceGoals,
                               MavenIncrementalBuilder.RESOURCES_GOAL );
            Set<String> excludedTestResourceGoals =
                MavenPropertyManager.getInstance().getTestResourceExcludedGoals( getProject() );
            buildGoalTableRow( composite, "Execute on test resource build", newExcludedTestResourceGoals,
                               excludedTestResourceGoals, MavenIncrementalBuilder.TEST_RESOURCES_GOAL );

            return composite;
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
            return null;
        }
    }

    @Override
    protected void performApply()
    {
        try
        {
            MavenPropertyManager.getInstance().setResourceExcludedGoals( getProject(), newExcludedResourceGoals );
            MavenPropertyManager.getInstance().setTestResourceExcludedGoals( getProject(), newExcludedTestResourceGoals );
        }
        catch ( CoreException e )
        {
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
        }
    }

    private void buildGoalTableRow( Composite parent, String label, final Set<String> managedSet,
                                    Set<String> existingExcludes, String phase ) throws CoreException
    {
        Composite row = new Composite( parent, SWT.NONE );
        GridLayout rowLayout = new GridLayout( 1, true );
        row.setLayout( rowLayout );
        row.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );

        Label resourcesLabel = new Label( row, SWT.READ_ONLY );
        resourcesLabel.setText( label );

        int style =
            SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION | SWT.CHECK;

        CheckboxTableViewer resourceGoalsCheckTableViewer = CheckboxTableViewer.newCheckList( row, style );
        resourceGoalsCheckTableViewer.getTable().setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
        managedSet.clear();
        managedSet.addAll( existingExcludes );
        Set<String> allResourceGoals =
            new LinkedHashSet<String>( MavenManager.getMaven().getGoalsForPhase( getMavenProject(), phase ) );
        for ( String goal : allResourceGoals )
        {
            resourceGoalsCheckTableViewer.add( goal );
            resourceGoalsCheckTableViewer.setChecked( goal, !existingExcludes.contains( goal ) );
        }
        resourceGoalsCheckTableViewer.addCheckStateListener( new ICheckStateListener()
        {
            public void checkStateChanged( CheckStateChangedEvent event )
            {
                if ( event.getChecked() )
                {
                    managedSet.remove( event.getElement() );
                }
                else
                {
                    managedSet.add( (String) event.getElement() );
                }

            }
        } );
    }

    private IProject getProject()
    {
        return (IProject) getElement().getAdapter( IProject.class );
    }

    private IMavenProject getMavenProject()
    {
        try
        {
            return MavenManager.getMavenProjectManager().getMavenProject( getProject(), true );
        }
        catch ( CoreException e )
        {
            /* project doesn't build */
            MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( getProject(), e );
            return null;
        }
    }

}
