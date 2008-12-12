/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.customcomponents;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class DependencyViewer extends Composite
{
    private List<Dependency> artifacts;

    private final Button addPropertyButton;

    private final Button removePropertyButton;

    private final Button editPropertyButton;

    private final Table artifactsTable;

    public DependencyViewer( final Composite parent, int styles )
    {
        super( parent, styles );
        setLayout( new GridLayout( 1, false ) );

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }

            @Override
            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };

        SelectionAdapter tableListener = new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            @Override
            public void widgetSelected( SelectionEvent e )
            {
                editPropertyButton.setEnabled( true );
                removePropertyButton.setEnabled( true );
            }
        };

        Group container = new Group( this, SWT.NONE );
        container.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        container.setLayout( new GridLayout( 2, false ) );
        container.setText( Messages.MavenCustomComponent_DependenciesLabel );

        artifactsTable = new Table( container, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        artifactsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        artifactsTable.setHeaderVisible( true );
        artifactsTable.setLinesVisible( true );
        artifactsTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( artifactsTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenCustomComponent_GroupIdLabel );
        column.setWidth( 160 );

        column = new TableColumn( artifactsTable, SWT.CENTER, 1 );
        column.setText( Messages.MavenCustomComponent_ArtifactIdLabel );
        column.setWidth( 140 );

        column = new TableColumn( artifactsTable, SWT.CENTER, 2 );
        column.setText( Messages.MavenCustomComponent_VersionLabel );
        column.setWidth( 70 );

        column = new TableColumn( artifactsTable, SWT.CENTER, 3 );
        column.setText( Messages.MavenCustomComponent_ScopeLabel );
        column.setWidth( 60 );

        column = new TableColumn( artifactsTable, SWT.CENTER, 4 );
        column.setText( Messages.MavenCustomComponent_TypeLabel );
        column.setWidth( 25 );
        
        column = new TableColumn( artifactsTable, SWT.CENTER, 5 );
        column.setText( Messages.MavenCustomComponent_ClassifierLabel );
        column.setWidth( 25 );
        
        column = new TableColumn( artifactsTable, SWT.CENTER, 6 );
        column.setText( Messages.MavenCustomComponent_SystemPath );
        column.setWidth( 25 );
        
        column = new TableColumn( artifactsTable, SWT.CENTER, 7 );
        column.setText( Messages.MavenCustomComponent_Optional );
        column.setWidth( 25 );

        Composite container2 = new Composite( container, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addPropertyButton.setText( Messages.MavenCustomComponent_AddButtonLabel );
        addPropertyButton.addSelectionListener( buttonListener );

        editPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editPropertyButton.setText( Messages.MavenCustomComponent_EditButtonLabel );
        editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        removePropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removePropertyButton.setText( Messages.MavenCustomComponent_RemoveButtonLabel );
        removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
    }

    public void refreshArtifactsTable()
    {
        artifactsTable.removeAll();

        for ( Dependency artifact : artifacts )
        {
            TableItem item = new TableItem( artifactsTable, SWT.BEGINNING );
            item.setText( new String[] { artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion(),
                artifact.getScope(), artifact.getType() , artifact.getClassifier() , artifact.getSystemPath() ,
                String.valueOf( artifact.isOptional() ) } );
        }

        artifactsTable.deselectAll();
        editPropertyButton.setEnabled( false );
        removePropertyButton.setEnabled( false );

        this.getParent().getShell().setActive();
    }

    public void buttonSelected( SelectionEvent e )
    {
        if ( e.getSource() == addPropertyButton )
        {
            AddEditDependencyDialog dialog = AddEditDependencyDialog.getAddEditDependencyDialog();

            if ( dialog.openWithDependency( null ) == Window.OK )
            {
                Dependency dependency = dialog.getDependency();
                if ( !dependencyAlreadyExist( dependency ) )
                {
                    artifacts.add( dependency );
                    refreshArtifactsTable();
                }
            }
        }
        else if ( e.getSource() == removePropertyButton )
        {
            int selection = artifactsTable.getSelectionIndex();
            if( selection != -1 )
            {
                artifacts.remove( selection );
                refreshArtifactsTable();
            }
        }
        else if ( e.getSource() == editPropertyButton )
        {
            AddEditDependencyDialog dialog = AddEditDependencyDialog.getAddEditDependencyDialog();

            int selectedIndex = artifactsTable.getSelectionIndex();
            if ( selectedIndex != -1 )
            {
                if ( dialog.openWithDependency( artifacts.get( selectedIndex ) ) == Window.OK )
                {
                    artifacts.set( selectedIndex , dialog.getDependency() );
                }
            }
        }
        else
        {
            throw new RuntimeException( "Unknown event source " + e.getSource() );
        }
    }

    public List<Dependency> getDependencies()
    {
        return artifacts;
    }

    public void setDependencies( List<Dependency> artifacts )
    {
        this.artifacts = artifacts;
    }

    private boolean dependencyAlreadyExist( Dependency dependency )
    {
        for( Dependency artifact : artifacts )
        {
            if( dependency.equals( artifact ) )
            {
                return true;
            }
        }

        return false;
    }
}
