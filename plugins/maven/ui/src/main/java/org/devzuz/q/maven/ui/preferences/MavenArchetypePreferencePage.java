package org.devzuz.q.maven.ui.preferences;

import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.preferences.editor.MavenArchetypePreferenceEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class MavenArchetypePreferencePage 
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage
{	
	public MavenArchetypePreferencePage()
	{
		super( GRID );
        setPreferenceStore( Activator.getDefault().getPreferenceStore() );
	}
	
	protected void createFieldEditors() 
	{
		noDefaultAndApplyButton();
		addField( new MavenArchetypePreferenceEditor( Activator.PLUGIN_ID + "MavenArchetypePreferencePage",
				  Messages.MavenArchetypePreferencePage_description, getFieldEditorParent() ) ); 	
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}


}
