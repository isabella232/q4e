/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.projectwizard;

import java.lang.reflect.InvocationTargetException;

import org.devzuz.q.maven.wizard.Activator;
import org.devzuz.q.maven.wizard.core.Archetype;
import org.devzuz.q.maven.wizard.core.Maven2ArchetypeManager;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectArchetypeInfoPage;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectChooseArchetypePage;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectLocationPage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class Maven2ProjectWizard
    extends Wizard
    implements INewWizard
{
    private Maven2ProjectLocationPage locationPage;

    private Maven2ProjectChooseArchetypePage archetypePage;

    private Maven2ProjectArchetypeInfoPage archetypeInfoPage;

    private IPath projectPath = null;

    private String projectName = "";

    private Archetype archetype = null;

    private String groupID = "";

    private String artifactID = "";

    private String packageName = "";

    private String version = "";

    private String description = "";

    @Override
    public void addPages()
    {
        locationPage = new Maven2ProjectLocationPage();
        archetypePage = new Maven2ProjectChooseArchetypePage();
        archetypeInfoPage = new Maven2ProjectArchetypeInfoPage();

        addPage( locationPage );
        addPage( archetypePage );
        addPage( archetypeInfoPage );
    }

    @Override
    public boolean performFinish()
    {
        projectName = locationPage.getProjectName().trim();
        projectPath = locationPage.getProjectLocation();
        archetype = archetypePage.getArchetype();
        groupID = archetypeInfoPage.getGroupID();
        artifactID = archetypeInfoPage.getArtifactID();
        packageName = archetypeInfoPage.getPackageName();
        version = archetypeInfoPage.getVersion();
        description = archetypeInfoPage.getDescription();

        try
        {
            getContainer().run( true, true, new IRunnableWithProgress()
            {
                public void run( IProgressMonitor monitor )
                    throws InvocationTargetException, InterruptedException
                {
                    try
                    {
                        Maven2ArchetypeManager.executeArchetype( archetype, projectPath, groupID, artifactID, version,
                                                                 packageName );
                    }
                    catch ( CoreException e )
                    {
                        Activator.getLogger().log( "Error executing the archetype", e );
                    }
                }
            } );
        }
        catch ( InvocationTargetException e )
        {
            Activator.getLogger().log( "Error finalizing the wizard", e );
            return false;
        }
        catch ( InterruptedException e )
        {
            // User canceled, so stop but don't close wizard. 
            return false;
        }

        return true;
    }

    public void init( IWorkbench workbench, IStructuredSelection selection )
    {

    }

    /* Commented due to Eclipse having unstable API. This should be changed when 3.3 is finalized. */
    /*
    public void pageTransition(PageTransitionEvent event)
    {
        if( event.getType() == PageTransitionEvent.EVENT_NEXT && 
            event.getSelectedPage() == locationPage)
        {
            setArchetypeInfoUIElements();
        }
    }*/

    public void setProjectInfo()
    {
        projectName = locationPage.getProjectName().trim();

        /* Workaround to problem above */
        archetypeInfoPage.setGroupID( projectName );
        archetypeInfoPage.setArtifactID( projectName );
        archetypeInfoPage.setPackageName( projectName );
    }

}
