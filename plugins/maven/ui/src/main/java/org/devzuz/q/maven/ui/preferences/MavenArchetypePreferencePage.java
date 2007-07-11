package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.dialogs.ArchetypeListSourceDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenArchetypePreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage 
{	
	
	private Table artifactsTable;
    
	private Button addPropertyButton;

    private Button removePropertyButton;

    private Button editPropertyButton;
    
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
        
		noDefaultAndApplyButton();
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout( new GridLayout( 2 , false ) );
		
		PreferencesTableListener tableListener = new PreferencesTableListener();
		
        artifactsTable = new Table( composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        artifactsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        artifactsTable.setHeaderVisible( true );
        artifactsTable.setLinesVisible( true );
        artifactsTable.addSelectionListener( tableListener );     
       

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
		
        return composite;
	}
	
	public void buttonSelected( SelectionEvent e )
    {	
		
	   if( e.getSource() == addPropertyButton )
	   {
		   ArchetypeListSourceDialog dialog = ArchetypeListSourceDialog.getArchetypeListSourceDialog();
		   if ( dialog.open() == Window.OK )
		   {
			   if(!isItemPresent(dialog.getArchetypeListSource(), dialog.getType()))
				{
				   TableItem item = new TableItem( artifactsTable, SWT.BEGINNING );
				   item.setText( new String[] {
		                dialog.getArchetypeListSource(),
		                dialog.getType()});
				}
		   }
	   }
	   else if( e.getSource() == editPropertyButton )
	   {
		   ArchetypeListSourceDialog dialog = ArchetypeListSourceDialog.getArchetypeListSourceDialog();
		   TableItem[] items = artifactsTable.getSelection();
		   dialog.openWithEntry(items[0].getText(0), items[0].getText(1));
		   if(!isItemPresent(dialog.getArchetypeListSource(), dialog.getType()))
			{
			   items[0].setText( new String[] {
		                dialog.getArchetypeListSource(),
		                dialog.getType()});
			}
	   }
	   else if( e.getSource() == removePropertyButton )
	   {
		   artifactsTable.remove(artifactsTable.getSelectionIndex());
		   if(artifactsTable.getItemCount()<1) {
			   editPropertyButton.setEnabled( false );
	           removePropertyButton.setEnabled( false );
		   }
	   }
	   else 
	   {
		   throw new RuntimeException( "Unknown event source " + e.getSource() );
	   }
	   
    }
	
	private boolean isItemPresent(String archeTypeListSource, String type) {
		
		for(int iCount = 0; iCount < artifactsTable.getItemCount(); iCount++ ) 
		{
			TableItem items = artifactsTable.getItem(iCount);
			
			if(items.getText(0).equals(archeTypeListSource) && items.getText(1).equals(type) ){
				return true;
			}
		}
		return false;
	}
	public void init(IWorkbench workbench) 
	{
	
	}
	
    private class PreferencesTableListener
    		extends SelectionAdapter
    {
    	
    public void widgetDefaultSelected( SelectionEvent e )
    {
        System.out.println("item : " + artifactsTable.getItemCount());
        widgetSelected( e );
       
    }

    public void widgetSelected( SelectionEvent e )
    {
    	TableItem[] items = artifactsTable.getSelection();
    	if ( ( items != null ) && ( items.length > 0 ) )
    	{
    		editPropertyButton.setEnabled( true );
            removePropertyButton.setEnabled( true );
        } 
    	
    }
    
    }
}
