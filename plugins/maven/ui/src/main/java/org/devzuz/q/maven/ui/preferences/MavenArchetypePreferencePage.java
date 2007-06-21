package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenArchetypePreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage 
{	
	public MavenArchetypePreferencePage() 
	{
		setDescription( Messages.MavenArchetypePreferencePage_description );
	}
	
	public void createControl(Composite parent) 
	{
		super.createControl(parent);
	}

	protected Control createContents(Composite parent)
	{
		noDefaultAndApplyButton();
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout( new GridLayout( 2 , false ) );
		
        Table artifactsTable = new Table( composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        artifactsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        artifactsTable.setHeaderVisible( true );
        artifactsTable.setLinesVisible( true );
        //artifactsTable.addSelectionListener( tableListener );

        TableColumn column = new TableColumn( artifactsTable, SWT.CENTER, 0 );
        column.setText( Messages.MavenArchetypePreferencePage_sourceurl );
        column.setWidth( 300 );

        column = new TableColumn( artifactsTable, SWT.CENTER, 1 );
        column.setText( Messages.MavenArchetypePreferencePage_type );
        column.setWidth( 50 );

        Composite container2 = new Composite( composite, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        Button addPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addPropertyButton.setText( Messages.MavenCustomComponent_AddButtonLabel );
        //addPropertyButton.addSelectionListener( buttonListener );

        Button editPropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editPropertyButton.setText( Messages.MavenCustomComponent_EditButtonLabel );
        //editPropertyButton.addSelectionListener( buttonListener );
        editPropertyButton.setEnabled( false );

        Button removePropertyButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removePropertyButton.setText( Messages.MavenCustomComponent_RemoveButtonLabel );
        //removePropertyButton.addSelectionListener( buttonListener );
        removePropertyButton.setEnabled( false );
		
        return composite;
	}
	
	public void init(IWorkbench workbench) 
	{
	
	}
}
