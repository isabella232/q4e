/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.preferences.editor;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.dialogs.ArchetypeListSourceDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;


public abstract class MavenPreferenceTableEditor extends FieldEditor
{
	private Table artifactsTable;
    
	private Button addPropertyButton;

    private Button removePropertyButton;

    private Button editPropertyButton;
    
    private SelectionListener selectionListener;
    
    private Composite buttonBox;
    
    protected MavenPreferenceTableEditor() 
    {
    }


    protected MavenPreferenceTableEditor(String name, String labelText, Composite parent) 
    {
        init(name, labelText);
        createControl(parent);
    }
    
    private void createButtons(Composite box) 
    {
    	addPropertyButton = createPushButton(box, Messages.MavenCustomComponent_AddButtonLabel);
    	editPropertyButton = createPushButton(box, Messages.MavenCustomComponent_EditButtonLabel);
    	removePropertyButton = createPushButton(box, Messages.MavenCustomComponent_RemoveButtonLabel);
    }
    
    private Button createPushButton(Composite parent, String key) 
    {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(key);
        button.setFont(parent.getFont());
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        int widthHint = convertHorizontalDLUsToPixels(button,
                IDialogConstants.BUTTON_WIDTH);
        data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
                SWT.DEFAULT, true).x);
        button.setLayoutData(data);
        button.addSelectionListener(getSelectionListener());
        return button;
    }
    
    
    private SelectionListener getSelectionListener() 
    {
        if (selectionListener == null) 
        {
			createSelectionListener();
		}
        return selectionListener;
    }
    
    
    public void createSelectionListener() 
    {
    	selectionListener = new SelectionAdapter()
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
    }
    
    protected void doFillIntoGrid(Composite parent, int numColumns) 
    {
        Control control = getLabelControl(parent);
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData(gd);

        artifactsTable = getTableControl(parent);
        gd = new GridData(GridData.FILL_BOTH);
        gd.verticalAlignment = GridData.FILL;
        gd.horizontalSpan = numColumns - 1;
        gd.grabExcessHorizontalSpace = true;
        artifactsTable.setLayoutData(gd);

        buttonBox = getButtonBoxControl(parent);
        gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        buttonBox.setLayoutData(gd);
    }
    
    public Composite getButtonBoxControl(Composite parent) 
    {
        if (buttonBox == null) 
        {
            buttonBox = new Composite(parent, SWT.NULL);
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            buttonBox.setLayout(layout);
            createButtons(buttonBox);
            buttonBox.addDisposeListener(new DisposeListener() 
            {
                public void widgetDisposed(DisposeEvent event) 
                {
                	addPropertyButton = null;
                	removePropertyButton = null;
                	editPropertyButton = null;
                    buttonBox = null;
                }
            });

        } 
        else 
        {
            checkParent(buttonBox, parent);
        }
        return buttonBox;
    }
    
    public Table getTableControl(Composite parent) 
    {
        if (artifactsTable == null) 
        {
        	artifactsTable = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );        	
        	artifactsTable.setFont(parent.getFont());
        	
        	PreferencesTableListener tableListener = new PreferencesTableListener();
        	artifactsTable.addSelectionListener(tableListener);        	
        	artifactsTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
            artifactsTable.setHeaderVisible( true );
            artifactsTable.setLinesVisible( true );
          
            TableColumn column = new TableColumn( artifactsTable, SWT.CENTER, 0 );
            column.setText( Messages.MavenArchetypePreferencePage_sourceurl );
            column.setWidth( 300 );
            column = new TableColumn( artifactsTable, SWT.CENTER, 1 );
            column.setText( Messages.MavenArchetypePreferencePage_type );
            column.setWidth( 50 );
            
        	artifactsTable.addDisposeListener(new DisposeListener() 
        	{
                public void widgetDisposed(DisposeEvent event) {
                	artifactsTable = null;
                }
            });
        } 
        else 
        {
            checkParent(artifactsTable, parent);
        }
        return artifactsTable;
    }
	
    protected void doLoad() 
    {
    	try
    	{
    		artifactsTable.clearAll();   
            String s = getPreferenceStore().getString(getPreferenceName());
            String[] array = parsePrefDataString(s);
            if(!s.trim().equals(""))
            {
            	for (int i = 0; i < array.length; i++) 
                {
                	String tabledata [] = array[i].split(";");
                	TableItem item = new TableItem( artifactsTable, SWT.BEGINNING );
    				   item.setText( new String[] {
    						   tabledata[0],
    						   tabledata[1]});
                }	
            }
            else
            {
            	disableEditRemoveButtons();
            }
    	}
    	catch(Exception e)
    	{
    		
    	}
    	
    }
	
    protected void doLoadDefault() 
    {
    	try 
    	{
    		artifactsTable.clearAll();          
            String s = getPreferenceStore().getDefaultString(getPreferenceName());
            String[] array = parsePrefDataString(s);            
            for (int i = 0; i < array.length; i++) 
            {
            	String tabledata [] = array[i].split(";");
            	TableItem item = new TableItem( artifactsTable, SWT.BEGINNING );
				   item.setText( new String[] {
						   tabledata[0],
						   tabledata[1]});
            }
    	}
    	catch(Exception e)
    	{
    		
    	}
    	
    }
    	
    private void disableEditRemoveButtons()
    {
    	editPropertyButton.setEnabled( false );
        removePropertyButton.setEnabled( false );
    }
    
    protected abstract String[] parsePrefDataString(String prefData);
    
    protected void adjustForNumColumns(int numColumns) 
    {
        Control control = getLabelControl();
        ((GridData) control.getLayoutData()).horizontalSpan = numColumns;
        ((GridData) artifactsTable.getLayoutData()).horizontalSpan = numColumns - 1;
    }
    
    public int getNumberOfControls() 
    {
        return 2;
    }
    
    
    protected void doStore() 
    {
        String strDataPreference = createTableDataList(artifactsTable.getItems()).trim();
        if (strDataPreference != null)
        {
			getPreferenceStore().setValue(getPreferenceName(), strDataPreference);
		}
    }
    
    protected abstract String createTableDataList(TableItem [] items);
    
	private void buttonSelected( SelectionEvent e )
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
		   if(artifactsTable.getItemCount()<1) 
		   {
			   disableEditRemoveButtons();
		   }
	   }
	   else 
	   {
		   throw new RuntimeException( "Unknown event source " + e.getSource() );
	   }
	   
    }
	
	private boolean isItemPresent(String archeTypeListSource, String type) 
	{
		for(int iCount = 0; iCount < artifactsTable.getItemCount(); iCount++ ) 
		{
			TableItem items = artifactsTable.getItem(iCount);
			
			if(items.getText(0).equals(archeTypeListSource) && items.getText(1).equals(type) )
			{
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
