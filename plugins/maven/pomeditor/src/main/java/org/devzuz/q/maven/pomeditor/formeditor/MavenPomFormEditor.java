/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.formeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBasicFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDependenciesFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomPropertiesModuleFormPage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;


public class MavenPomFormEditor extends FormEditor
{   
    private Model pomModel;
    
    public MavenPomFormEditor()
    {
    }
    
    @Override
    protected void addPages()
    {
        try
        {
        	if(initializeAddPagesOK())
        	{
                addPage( new MavenPomBasicFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomBasicFormPage;",
                        "Project Information" , this.pomModel ));
                addPage( new MavenPomDependenciesFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDependenciesFormPage;",
                        "Dependencies", this.pomModel ) );
                addPage( new MavenPomPropertiesModuleFormPage( this , "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomPropertiesModuleFormPage;",
                        "Properties/Module" ) ); 
        	}
        }
        catch ( PartInitException pie )
        {
            pie.printStackTrace();
        }
    }
    
    private boolean initializeAddPagesOK()
    {
        if( getPomFile() != null)
        {
        	try 
        	{
				this.pomModel = new MavenXpp3Reader().read( new FileReader( getPomFile() ) );
			} 
        	catch (FileNotFoundException e)
        	{
				e.printStackTrace();
			}
        	catch (IOException e) 
        	{
				e.printStackTrace();
			} 
        	catch (XmlPullParserException e) 
        	{
				e.printStackTrace();
			}            
        }
        else 
        {
            return false;
        }
        
        return true;
    	
    }
    @Override
    public void doSave( IProgressMonitor monitor )
    {
        monitor.done();
    }

    @Override
    public void doSaveAs()
    {
      
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }
    
    private File getPomFile()
    {
        IEditorInput input= getEditorInput();
        if( input instanceof IFileEditorInput )
        {
             return (( IFileEditorInput ) input).getFile().getLocation().toFile();
        }
        
        return null;
    }
}
