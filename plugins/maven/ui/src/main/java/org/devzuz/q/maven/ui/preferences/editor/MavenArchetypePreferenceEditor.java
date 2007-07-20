/**
 * 
 */
package org.devzuz.q.maven.ui.preferences.editor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

public class MavenArchetypePreferenceEditor extends MavenPrefenceTableEditor
{
	public MavenArchetypePreferenceEditor()
	{
		
	}
	
    public MavenArchetypePreferenceEditor(String name, String labelText, Composite parent) 
    {
        init(name, labelText);
        createControl(parent);
    }
	
	protected String[] parsePrefDataString(String prefData) 
	{
		final String tokenizer = "MakenTokenPreference";
		String tabledata [] = prefData.split(tokenizer);
	    return tabledata;    
	}

	
    protected String createTableDataList(TableItem [] items) 
    {
    	final String tokenizer = "MakenTokenPreference";
        StringBuffer strBuffer = new StringBuffer("");
		for(int x =0; x < items.length; x++) 
		{
			strBuffer.append((items[x].getText(0) + ";" +items[x].getText(1) +  tokenizer));
		}
        return strBuffer.toString();
    }


}
