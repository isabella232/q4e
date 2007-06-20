package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MavenArchetypePreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage 
{	
	public MavenArchetypePreferencePage(String title, ImageDescriptor image) 
	{
		super(title, image);
		
	}

	public MavenArchetypePreferencePage(String title) 
	{
		super(title);
		
	}

	protected Control createContents(Composite parent)
	{
		
		/*
		// Group ID Label, Text and Lookup Button
        Label label = new Label( parent, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenAddEditDependencyDialog_groupIdLabel );
		*/
		return parent;
	}
	
	public void init(IWorkbench workbench) 
	{
	
	}
	
}
