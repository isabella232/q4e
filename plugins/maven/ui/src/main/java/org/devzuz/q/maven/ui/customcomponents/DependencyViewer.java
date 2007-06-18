/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.customcomponents;

import java.util.Collection;
import java.util.Iterator;

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

public class DependencyViewer
    extends Composite
{
    private Collection<Dependency> artifacts;

    private Button addPropertyButton;

    private Button removePropertyButton;

    private Button editPropertyButton;

    private Table artifactsTable;

    public DependencyViewer( final Composite parent, int styles )
    {
        super( parent, styles );
        setLayout( new GridLayout( 1, false ) );

        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }

            public void widgetSelected( SelectionEvent e )
            {
                buttonSelected( e );
            }
        };

        SelectionAdapter tableListener = new SelectionAdapter()
        {
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

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
            item.setText( new String[] {
                artifact.getGroupId(),
                artifact.getArtifactId(),
                artifact.getVersion(),
                artifact.getScope(),
                artifact.getType() } );
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

            if ( dialog.openWithItem( null ) == Window.OK )
            {
                if ( !artifactAlreadyExist( dialog.getGroupId(), dialog.getArtifactId() ) )
                {
                    Dependency dependency = new Dependency();
                    dependency.setGroupId( dialog.getGroupId() );
                    dependency.setArtifactId( dialog.getArtifactId() );
                    dependency.setVersion( nullIfBlank( dialog.getVersion() ) );
                    dependency.setScope( nullIfBlank( dialog.getScope() ) );
                    dependency.setType( "jar" );

                    artifacts.add( dependency );
                }

                refreshArtifactsTable();
            }
        }
        else if ( e.getSource() == removePropertyButton )
        {
            TableItem items[] = artifactsTable.getSelection();
            if ( items.length > 0 )
            {
                removeArtifactFromList( items[0] );
                refreshArtifactsTable();
            }
        }
        else if ( e.getSource() == editPropertyButton )
        {
            AddEditDependencyDialog dialog = AddEditDependencyDialog.getAddEditDependencyDialog();

            TableItem items[] = artifactsTable.getSelection();
            if ( items.length > 0 )
            {
                if ( dialog.openWithItem( items[0] ) == Window.OK )
                {
                    if ( shouldModify( items[0], dialog.getGroupId(), dialog.getArtifactId(), dialog.getVersion(),
                                       dialog.getScope() ) )
                    {
                        replaceArtifactInList( items[0], dialog.getGroupId(), dialog.getArtifactId(),
                                               nullIfBlank( dialog.getVersion() ), nullIfBlank( dialog.getScope() ) );
                        refreshArtifactsTable();
                    }
                }
            }
        }
        else
        {
            throw new RuntimeException( "Unknown event source " + e.getSource() );
        }
    }

    public Collection<Dependency> getDependencies()
    {
        return artifacts;
    }

    public void setDependencies( Collection<Dependency> artifacts )
    {
        this.artifacts = artifacts;
    }

    private void replaceArtifactInList( TableItem item, String groupId, String artifactId, String version, String scope )
    {
        for ( Iterator<Dependency> it = artifacts.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( itemEqualsArtifact( item, artifact ) )
            {
                artifact.setGroupId( groupId );
                artifact.setArtifactId( artifactId );
                artifact.setVersion( version );
                artifact.setScope( scope );
                break;
            }
        }
    }

    private void removeArtifactFromList( TableItem item )
    {
        for ( Iterator<Dependency> it = artifacts.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( itemEqualsArtifact( item, artifact ) )
            {
                it.remove();
                break;
            }
        }
    }

    private boolean shouldModify( TableItem item, String groupId, String artifactId, String version, String scope )
    {
        if ( item.getText( 0 ).equals( groupId ) && item.getText( 1 ).equals( artifactId ) )
        {
            if ( !( item.getText( 2 ).equals( version ) && item.getText( 3 ).equals( scope ) ) )
                return true;
        }
        else
        {
            if ( !artifactAlreadyExist( groupId, artifactId ) )
                return true;
        }

        return false;
    }

    private boolean artifactAlreadyExist( String groupId, String artifactId )
    {
        for ( Iterator<Dependency> it = artifacts.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
    }

    private static boolean itemEqualsArtifact( TableItem item, Dependency artifact )
    {
        if ( item.getText( 0 ).equals( artifact.getGroupId() ) && item.getText( 1 ).equals( artifact.getArtifactId() )
            && item.getText( 2 ).equals( artifact.getVersion() ) )
        {
            return true;
        }

        return false;
    }

    private static String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
}
