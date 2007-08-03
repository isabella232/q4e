/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.ui.views;


import java.util.Observable;
import java.util.Observer;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

public class MavenLifeCycleView 
    extends ViewPart
    implements Observer
{

    public static final String POM_XML = "pom.xml";
    
    private TableViewer mavenLifeCycleTableViewer;
    
    private  IProject[] projects;
    
    public MavenLifeCycleView()
    {
    }

    @Override
    public void createPartControl( Composite parent )
    {
        mavenLifeCycleTableViewer = new TableViewer( parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.CTRL);
        final Table mavenLifeCycleTable = mavenLifeCycleTableViewer.getTable();
        
        TableColumn column = new TableColumn( mavenLifeCycleTable, SWT.NONE);
        column.setText( "Project" );
        column.setWidth( 250 );
        TableColumn column1 = new TableColumn( mavenLifeCycleTable, SWT.NONE);
        column1.setText( Messages.MavenLifeCycleView_PluginID );
        column1.setWidth( 200 );
        TableColumn column2 = new TableColumn( mavenLifeCycleTable,  SWT.NONE);
        column2.setText( Messages.MavenLifeCycleView_PhaseName );
        column2.setWidth( 200 );
        TableColumn column3 = new TableColumn( mavenLifeCycleTable,  SWT.NONE);
        column3.setText( Messages.MavenLifeCycleView_AssociatedPluginGoal );
        column3.setWidth( 350 );
        mavenLifeCycleTable.setHeaderVisible( true );
        mavenLifeCycleTable.setLinesVisible( true );
        setProjectColumnData();

        
    }

     private synchronized IProject[] getWorkBenchProjects()
     {
        return ResourcesPlugin.getWorkspace().getRoot().getProjects();           
     }
     
     private void setProjectColumnData()
     {
         projects  = getWorkBenchProjects();
         if(projects.length>0) 
         {
             for (int i = 0; i  < projects.length ; i++)
             { 
                if(projects[i].isOpen() && isValidMavenProject(projects[i])) 
                {
                    TableItem item = new TableItem( mavenLifeCycleTableViewer.getTable(), 
                                                    SWT.BEGINNING );
                    item.setText( 0, projects[i].getName() );
                }
                
             }
         }
       
     }
     
     private boolean isValidMavenProject(IProject project)     
     {
         IFile pomFile = project.getFile( POM_XML);
         if(pomFile.exists())
             return true;                         
         return false;
     }

    @Override
    public void setFocus()
    {
        mavenLifeCycleTableViewer.getControl().setFocus();
    }

    public void update( Observable arg0, Object arg1 )
    {
        // TODO Auto-generated method stub
        
    }

}
