/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.conversion.actions;


import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.ui.actions.helper.MavenConvertProjectHelper;

import org.apache.maven.aardvark.converter.ConversionFailedException;
import org.apache.maven.aardvark.converter.ProjectConverter;
import org.apache.maven.aardvark.converter.maven1.MavenOneProjectConverter;

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
        PlexusContainer pc = MavenUtils.getPlexusContainer();
        if(pc != null )
        {        
           
            MavenOneProjectConverter converter;
            try
            {
                converter = (MavenOneProjectConverter) pc.lookup( ProjectConverter.ROLE, "maven1" );

                converter.convertFromPath(maven1ProjectFile.getParentFile(), 
                                          maven1ProjectFile.getParentFile());
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

