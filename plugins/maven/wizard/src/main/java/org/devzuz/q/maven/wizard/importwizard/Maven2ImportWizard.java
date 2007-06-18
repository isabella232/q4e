package org.devzuz.q.maven.wizard.importwizard;

import org.devzuz.q.maven.jdt.ui.projectimport.ImportProjectJob;
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
        ImportProjectJob projectImporter = new ImportProjectJob( projectImportPage.getSelectedMavenProjects() ); 
        projectImporter.schedule();
        
        return true; 
    }

    public void init(IWorkbench workbench, IStructuredSelection selection)
    {
       
    }
}
