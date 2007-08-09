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
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

public class MavenLifeCycleView 
    extends ViewPart
    implements Observer
{

    public static final String POM_XML = "pom.xml";
    
    private TableViewer mavenLifeCycleTableViewer;
    
    private Action refreshMavenLifeCycleAction;
    
    private Table mavenLifeCycleTable;
    
    public MavenLifeCycleView()
    {
    }

    @Override
    public void createPartControl( Composite parent )
    {
        mavenLifeCycleTableViewer = new TableViewer( parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.CTRL);
        mavenLifeCycleTable = mavenLifeCycleTableViewer.getTable();
        
        TableColumn column = new TableColumn( mavenLifeCycleTable, SWT.NONE);
        column.setText( Messages.MavenLifeCycleView_Project );
        column.setWidth( 300 );
        TableColumn column1 = new TableColumn( mavenLifeCycleTable, SWT.NONE);
        column1.setText( Messages.MavenLifeCycleView_PluginID );
        column1.setWidth( 300 );
        TableColumn column2 = new TableColumn( mavenLifeCycleTable,  SWT.NONE);
        column2.setText( Messages.MavenLifeCycleView_PhaseName );
        column2.setWidth( 200 );
        TableColumn column3 = new TableColumn( mavenLifeCycleTable,  SWT.NONE);
        column3.setText( Messages.MavenLifeCycleView_AssociatedPluginGoal );
        column3.setWidth( 200 );
        mavenLifeCycleTable.setHeaderVisible( true );
        mavenLifeCycleTable.setLinesVisible( true );        
        createMavenLifeCycleAction();
        addMenusAndToolbars();
        setProjectColumnData();
        
        
    }
    
    private void createMavenLifeCycleAction()
    {
    	refreshMavenLifeCycleAction = new Action( Messages.MavenLifeCycleView_TableRefresh )
        {
            public void run()
            {
            	mavenLifeCycleTable.removeAll();
            	setProjectColumnData();
            }
        };
        refreshMavenLifeCycleAction.setEnabled( true );
        refreshMavenLifeCycleAction.setToolTipText( Messages.MavenLifeCycleView_TableRefreshToolTip );
        refreshMavenLifeCycleAction.setImageDescriptor(MavenImages.DESC_REFRESHMLIFECYCLEVIEW);
    }
    
    private void addMenusAndToolbars()
    {
        IActionBars bars = getViewSite().getActionBars();        
        IToolBarManager toolBarManager = bars.getToolBarManager();
        toolBarManager.add( refreshMavenLifeCycleAction );
    }

     private synchronized IProject[] getWorkBenchProjects()
     {
        return ResourcesPlugin.getWorkspace().getRoot().getProjects();           
     }
     
     private void setProjectColumnData()
     {       
         IProject[] projects  = getWorkBenchProjects();         
         if(projects.length > 0 || projects != null) 
         {             
             for (int i = 0; i  < projects.length ; i++)
             {
                 if(projects[i].isOpen() && isValidMavenProject(projects[i])) 
                 {
                     try 
                     {
                         POMMavenLifeCycleParser pmlcp = new POMMavenLifeCycleParser(mavenLifeCycleTableViewer,
                                                                                     projects[i].getName(), 
                                                                                     getPOMFileLocation(projects[i])); 
                         pmlcp.parsePOMFile();
                         if(pmlcp.getICounterPrjPhaseAndGoal() == 0) 
                         {
                             TableItem item = new TableItem( mavenLifeCycleTableViewer.getTable(), 
                                                             SWT.BEGINNING );
                             
                             item.setText(  new String[] { projects[i].getName(), "" , "" , "" } );
                         }
                     }
                     catch(Exception e)
                     {
                         
                     }
                         
                 }
                 
             }
         }
     }
     
     private IPath getPOMFileLocation(IProject project)
     {
         return project.getFile( POM_XML ).getLocation();   
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
