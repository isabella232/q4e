/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.actions;


import org.devzuz.q.maven.ui.actions.helper.MavenConvertProjectHelper;
import org.apache.maven.aardvark.converter.ConversionFailedException;
import org.apache.maven.aardvark.converter.ProjectConverter;
import org.apache.maven.aardvark.converter.maven1.MavenOneProjectConverter;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.PlexusContainerException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import java.io.File;


public class MavenConvertMaven1Action 
	implements IObjectActionDelegate
{

    private IStructuredSelection selection;

    private static String PROJECT_XML = "project.xml";
    

    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {
    }


    public void run( IAction action )
    {  	
    	
        File maven1ProjectFile = getEclipseProject().getFile( PROJECT_XML ).getLocation().toFile();
        
        PlexusContainer container;
        try
        {
            container = new DefaultPlexusContainer();
            MavenOneProjectConverter converter;
            try 
            {
                converter = (MavenOneProjectConverter) container.lookup( ProjectConverter.ROLE, "maven1" );
                try
                {
                    converter.execute(maven1ProjectFile.getParentFile(), maven1ProjectFile.getParentFile());
                    //converter.execute(new File("src_test"), new File("src_test"));
                }
                catch ( ConversionFailedException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } 
            catch (ComponentLookupException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            container.dispose();
          
        }
        catch ( PlexusContainerException e1 )
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }       

    }

    public void selectionChanged( IAction action, ISelection selection )
    {
        if(!selection.isEmpty())
        {
            this.selection = (IStructuredSelection) selection;
        }
    }
    
    public IProject getEclipseProject()
    {
        return MavenConvertProjectHelper.getEclipseProject( this.selection );
    }
    
}

