package org.devzuz.q.maven.ui.archetype.provider.impl;

import java.io.File;

import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Archetype2CatalogProviderUiBuilder implements IArchetypeProviderUIBuilder
{
    private Archetype2CatalogProvider provider;
    
    private boolean validFile;
    
    private Text filenameText;
    
    private Button browseButton;
    
    private WizardPage page;
    
    public Control createControl( final Composite parent, WizardPage ownerPage )
    {
        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validateData();
            }
        };
        
        this.page = ownerPage;
        
        Composite container = new Composite( parent, SWT.NONE );

        container.setLayout( new GridLayout( 3, false ) );

        // filename with browse button
        Label filenameLabel = new Label( container, SWT.NULL );
        filenameLabel.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
        filenameLabel.setText( "Archetype Catalog Filename" );

        filenameText = new Text( container, SWT.BORDER | SWT.SINGLE );
        filenameText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
        filenameText.addModifyListener( listener );
        
        browseButton = new Button( container, SWT.PUSH );
        browseButton.setText( "Browse" );
        browseButton.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                String chosenFilename = new FileDialog( parent.getShell(), SWT.OPEN ).open();
                if ( chosenFilename != null )
                {
                    filenameText.setText( chosenFilename.trim() );
                }
            }
        } );
        
        return container;
    }

    public boolean isConfigured()
    {
        return validFile;
    }

    public void setInput( IArchetypeProvider provider )
    {
        this.provider = (Archetype2CatalogProvider) provider;
        filenameText.setText( blankIfNull( this.provider.getCatalogFilename() ) );
        page.setTitle( "Configuration for Archetype 2.0 Catalog providers" );
    }
    
    private void validateData()
    {
        validFile = new File( filenameText.getText().trim() ).exists();
        page.setPageComplete( validFile );
    }
    
    public void applyConfiguration()
    {
        provider.setCatalogFilename( filenameText.getText().trim() );
    }
    
    private String blankIfNull( String str )
    {
        if( str == null )
            return "";
        return str;
    }
}
