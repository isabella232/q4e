/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeLabelProvider;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeRetrievalResult;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.MavenArchetypeProviderManager;
import org.devzuz.q.maven.wizard.Messages;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * Implementation of a page for the New Maven Project wizard which allows the user to select an archetype from the list
 * or to manually specify one.
 * 
 * @author Abel Muiño <amuino@gmail.com>
 */
public class Maven2ProjectChooseArchetypePage extends Maven2ValidatingWizardPage
{
    private Label archetypeDescriptionLabel;

    private Button publishedArchetypesButton;

    private Group publishedArchetypesGroup;

    private Button customArchetypesButton;

    private Group customArchetypeGroup;

    private Text groupIDText;

    private Text artifactIDText;

    private Text versionText;

    private Text remoteRepositoryText;

    private FilteredTree archetypeTree;

    /**
     * Creates the wizard's page.
     */
    public Maven2ProjectChooseArchetypePage()
    {
        super( Messages.wizard_project_chooseArchetype_name );
        setTitle( Messages.wizard_project_chooseArchetype_title );
        setDescription( Messages.wizard_project_chooseArchetype_desc );
        setPageComplete( false );
    }

    public void createControl( Composite parent )
    {
        SelectionAdapter buttonListener = new SelectionAdapter()
        {
            @Override
            public void widgetDefaultSelected( SelectionEvent e )
            {
                widgetSelected( e );
            }

            @Override
            public void widgetSelected( SelectionEvent e )
            {
                boolean publishedArchetypesEnabled = publishedArchetypesButton.getSelection();

                publishedArchetypesGroup.setEnabled( publishedArchetypesEnabled );
                setEnableChildren( publishedArchetypesGroup, publishedArchetypesEnabled );

                customArchetypeGroup.setEnabled( !publishedArchetypesEnabled );
                setEnableChildren( customArchetypeGroup, !publishedArchetypesEnabled );

                validate();
            }
        };

        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        Composite container = new Composite( parent, SWT.NULL );
        container.setLayout( new GridLayout( 1, false ) );

        publishedArchetypesButton = new Button( container, SWT.RADIO );
        publishedArchetypesButton.setText( Messages.wizard_project_chooseArchetype_group_label );
        publishedArchetypesButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false, 3, 1 ) );
        publishedArchetypesButton.addSelectionListener( buttonListener );

        publishedArchetypesGroup = new Group( container, SWT.NULL );
        publishedArchetypesGroup.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

        FillLayout fillLayout = new FillLayout( SWT.HORIZONTAL );
        fillLayout.spacing = 10;
        publishedArchetypesGroup.setLayout( fillLayout );
        publishedArchetypesGroup.setText( "" );

        Composite archetypeFilterGroup = new Composite( publishedArchetypesGroup, SWT.NULL );
        archetypeFilterGroup.setLayout( new GridLayout( 1, true ) );

        PatternFilter patternFilter = new PatternFilter();
        archetypeTree =
            new FilteredTree( archetypeFilterGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL, patternFilter );

        GridData archetypeTreeGridData = new GridData( GridData.FILL, GridData.FILL, true, true );
        archetypeTreeGridData.minimumHeight = 150;
        archetypeTree.getViewer().getControl().setLayoutData( archetypeTreeGridData );

        archetypeTree.getViewer().setSorter( new ViewerSorter() );

        archetypeTree.getViewer().setContentProvider( new ArchetypeTreeContentProvider() );

        archetypeTree.getViewer().setLabelProvider( new ArchetypeLabelProvider() );
        
        archetypeTree.getViewer().addSelectionChangedListener( new ISelectionChangedListener()
        {
            public void selectionChanged( SelectionChangedEvent event )
            {
                validate();
            }
        } );
        
        archetypeDescriptionLabel = new Label( publishedArchetypesGroup, SWT.WRAP );

        customArchetypesButton = new Button( container, SWT.RADIO );
        customArchetypesButton.setText( Messages.wizard_project_chooseArchetype_custom_label );
        customArchetypesButton.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false, 3, 1 ) );
        customArchetypesButton.addSelectionListener( buttonListener );

        customArchetypeGroup = new Group( container, SWT.NULL );
        customArchetypeGroup.setLayout( new GridLayout( 2, false ) );
        customArchetypeGroup.setLayoutData( new GridData( GridData.FILL, SWT.BOTTOM, true, false ) );

        Label groupIDLabel = new Label( customArchetypeGroup, SWT.NULL );
        groupIDLabel.setText( Messages.wizard_project_archetypeInfo_arch_groupid_label );
        groupIDText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        groupIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        groupIDText.addModifyListener( modifyingListener );

        Label artifactIDLabel = new Label( customArchetypeGroup, SWT.NULL );
        artifactIDLabel.setText( Messages.wizard_project_archetypeInfo_arch_artifactid_label );
        artifactIDText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        artifactIDText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        artifactIDText.addModifyListener( modifyingListener );

        Label versionLabel = new Label( customArchetypeGroup, SWT.NULL );
        versionLabel.setText( Messages.wizard_project_archetypeInfo_arch_version_label );
        versionText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        versionText.setText( "" );
        versionText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );

        Label remoteRepositoryLabel = new Label( customArchetypeGroup, SWT.NULL );
        remoteRepositoryLabel.setText( Messages.wizard_project_archetypeInfo_remoteRepo_label );
        remoteRepositoryText = new Text( customArchetypeGroup, SWT.BORDER | SWT.SINGLE );
        remoteRepositoryText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, false ) );
        remoteRepositoryText.addModifyListener( modifyingListener );

        setControl( container );
        
        initialize();
    }

    @Override
    protected boolean isPageValid()
    {
        if ( !publishedArchetypesButton.getSelection() )
        {
            if ( artifactIDText.getText().trim().length() > 0 )
            {
                if ( groupIDText.getText().trim().length() > 0 )
                {
                    if ( validateUrl( remoteRepositoryText.getText().trim() ) )
                    {
                        return true;
                    }
                    else
                    {
                        setError( Messages.wizard_project_archetypeInfo_error_remote_url );
                    }
                }
                else
                {
                    setError( Messages.wizard_project_archetypeInfo_error_groupId );
                }
            }
            else
            {
                setError( Messages.wizard_project_archetypeInfo_error_artifactId );
            }
        }
        else
        {
            if ( archetypeTree.getViewer().getSelection() instanceof IStructuredSelection )
            {
                Object selectedObject = ((IStructuredSelection) archetypeTree.getViewer().getSelection()).getFirstElement();
                if ( selectedObject instanceof Archetype )
                {
                    archetypeDescriptionLabel.setText( ( (Archetype) selectedObject ).getDescription() );
                    return true;
                }
            }
            
            archetypeDescriptionLabel.setText( Messages.wizard_project_archetypeInfo_error_noSelection );
            setError( Messages.wizard_project_archetypeInfo_error_noSelection );
        }

        return false;
    }

    /**
     * Returns the user-selected archetype.
     * 
     * @return the selected archetype.
     */
    public Archetype getArchetype()
    {
        if ( publishedArchetypesButton.getSelection() )
        {
            IStructuredSelection selection = (IStructuredSelection) archetypeTree.getViewer().getSelection();
            if ( selection.getFirstElement() instanceof Archetype )
            {
                return (Archetype) selection.getFirstElement();
            }

            return null;
        }
        else
        {
            return new Archetype( artifactIDText.getText().trim(), groupIDText.getText().trim(),
                                  versionText.getText().trim(), remoteRepositoryText.getText().trim(), "" );
        }
    }

    private void initialize()
    {
        ArchetypeRetrievalResult archetypes = MavenArchetypeProviderManager.getArchetypes();

        if ( archetypes.getExceptions().size() > 0 )
        {
            MessageBox messageBox = new MessageBox( this.getShell(), SWT.ERROR_IO | SWT.OK );
            StringBuffer errorMessage = new StringBuffer();
            for ( QCoreException e : archetypes.getExceptions() )
            {
                errorMessage.append( e.getStatus().getMessage() );
            }
            messageBox.setMessage( errorMessage.toString() );
            messageBox.open();
        }

        archetypeTree.getViewer().setInput( MavenArchetypeProviderManager.getArchetypeProviders() );
        publishedArchetypesButton.setSelection( true );
        setEnableChildren( publishedArchetypesGroup, true );
        setEnableChildren( customArchetypeGroup, false );
        
        validate();
    }
    
    private class ArchetypeTreeContentProvider implements ITreeContentProvider
    {
        public Object[] getChildren( Object element )
        {
            if ( element instanceof IArchetypeProvider )
            {
                try
                {
                    return ( ( (IArchetypeProvider) element ).getArchetypes().toArray( new Archetype[0] ) );
                }
                catch ( QCoreException e )
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public Object getParent( Object element )
        {
            return null;
        }

        public boolean hasChildren( Object element )
        {
            if ( element instanceof IArchetypeProvider )
            {
                return getChildren( element ).length > 0;
            }
            return false;
        }

        public Object[] getElements( Object element )
        {
            if ( element instanceof IArchetypeProvider[] )
            {
                return (IArchetypeProvider[]) element;
            }
            return null;
        }

        public void dispose()
        {
        }

        public void inputChanged( Viewer viewer, Object arg1, Object arg2 )
        {
        }
    }

    static private void setEnableChildren( Composite parent, boolean flag )
    {
        for ( Control control : parent.getChildren() )
        {
            control.setEnabled( flag );
        }
    }

    static private boolean validateUrl( String urlStr )
    {
        try
        {
            URL url = new URL( urlStr );
            String protocol = url.getProtocol();
            return "http".equals( protocol ) || "https".equals( protocol ) || "ftp".equals( protocol );
        }
        catch ( MalformedURLException e )
        {
            return false;
        }
    }
}
