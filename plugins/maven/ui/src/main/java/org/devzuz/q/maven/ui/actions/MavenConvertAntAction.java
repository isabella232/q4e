/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.actions;


import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.ui.actions.helper.MavenConvertProjectHelper;

import org.apache.maven.aardvark.converter.ConversionFailedException;
import org.apache.maven.aardvark.converter.ProjectConverter;
import org.apache.maven.aardvark.converter.ant.AntProjectConverter;

import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import java.io.File;
import java.io.IOException;


public class MavenConvertAntAction 
    implements IObjectActionDelegate
{

    private IStructuredSelection selection;

    private static String BUILD_XML = "build.xml";
    

    public void setActivePart( IAction action, IWorkbenchPart targetPart )
    {
    }

    public void run( IAction action )
    {   

        File antBuildFile = getEclipseProject().getFile( BUILD_XML ).getLocation().toFile();
        PlexusContainer pc = MavenUtils.getPlexusContainer();
        if(pc != null )
        {        
           
            AntProjectConverter converter;
            
            try
            {                 
                
                converter = (AntProjectConverter) pc.lookup( ProjectConverter.ROLE, "ant" );
                
                converter.setSrcDirectory( "src/java" ); 

                converter.convertFromPath(antBuildFile.getParentFile(), 
                                          antBuildFile.getParentFile());
                
            }
            catch ( ComponentLookupException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch ( ConversionFailedException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }           

        }

    }

    public void selectionChanged( IAction action, ISelection selection )
    {
        if(!selection.isEmpty())
        {
            this.selection = (IStructuredSelection) selection;
        }
    }
    
    private IProject getEclipseProject()
    {
        return MavenConvertProjectHelper.getEclipseProject( this.selection );
    }
    
}

