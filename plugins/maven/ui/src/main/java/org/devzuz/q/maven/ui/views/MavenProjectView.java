/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import org.devzuz.q.maven.ui.Messages;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class MavenProjectView 
	extends ViewPart
{
    private TableViewer mavenProjectsTableViewer;
    private Table mavenProjectsTable;

	@Override
	public void createPartControl(Composite parent) 
	{
	    mavenProjectsTableViewer = new TableViewer( parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.CTRL);
	    mavenProjectsTable = mavenProjectsTableViewer.getTable();
        TableColumn column = new TableColumn( mavenProjectsTable, SWT.NONE);
        column.setText( Messages.MavenProjectView_MavenParentProject );
        column.setWidth( 300 );
        TableColumn column1 = new TableColumn( mavenProjectsTable, SWT.NONE);
        column1.setText(Messages.MavenProjectView_MavenChildProject );
        column1.setWidth( 300 );        
        mavenProjectsTable.setHeaderVisible( true );
        mavenProjectsTable.setLinesVisible( true );  		
	}

	@Override
	public void setFocus() 
	{
		// TODO Auto-generated method stub
		
	}

}
