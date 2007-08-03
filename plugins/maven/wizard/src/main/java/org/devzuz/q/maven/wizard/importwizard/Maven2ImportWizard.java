/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.wizard.importwizard;

import org.devzuz.q.maven.jdt.ui.projectimport.ImportProjectWorkspaceJob;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectImportPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class Maven2ImportWizard extends Wizard implements IImportWizard
{
    private Maven2ProjectImportPage projectImportPage;
    
    @Override
    public void addPages()
    {
        projectImportPage = new Maven2ProjectImportPage();
        
        addPage( projectImportPage );
    }

    @Override
    public boolean performFinish()
    {
        /*
        ImportProjectJob projectImporter = ImportProjectJob.getProjectImporterJob( projectImportPage.getSelectedMavenProjects() );
        projectImporter.schedule();
        */
        ImportProjectWorkspaceJob projectImporter = ImportProjectWorkspaceJob.getProjectImporterRunnable( projectImportPage.getSelectedMavenProjects() );
        projectImporter.setUser( true );
        projectImporter.schedule();
        
        return true;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection)
    {
       
    }
}
