/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.projectwizard;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.devzuz.q.maven.wizard.MavenWizardActivator;
import org.devzuz.q.maven.wizard.core.ArchetypeGenerationJobAdapter;
import org.devzuz.q.maven.wizard.core.IArchetypeExecutor;
import org.devzuz.q.maven.wizard.core.Maven2ArchetypeManager;
import org.devzuz.q.maven.wizard.core.internal.MavenWizardContext;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectArchetypeInfoPage;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectChooseArchetypePage;
import org.devzuz.q.maven.wizard.pages.Maven2ProjectLocationPage;
import org.devzuz.q.maven.wizard.pages.core.internal.WizardPageExtensionUtil;
import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class Maven2ProjectWizard extends Wizard implements INewWizard
{
    /** Number of pages created by the wizard for the default case (no extra pages) . */
    private static final int DEFAULT_PAGE_COUNT = 3;

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

    private boolean importParentEnabled = false;

    /** Holds the current wizard context, shared with every extension page */
    private MavenWizardContext wizardContext = new MavenWizardContext();

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
        importParentEnabled = archetypeInfoPage.isImportParentEnabled();

        try
        {
            getContainer().run( true, true, new IRunnableWithProgress()
            {
                public void run( IProgressMonitor monitor ) throws InvocationTargetException, InterruptedException
                {
                    try
                    {
                        monitor.setTaskName( "Invoking maven to create the archetype" );
                        List<IMavenProjectPostprocessor> postProcessors =
                            WizardPageExtensionUtil.getPostProcessors( archetype, wizardContext );
                        wizardContext.setPostProcessors( postProcessors );

                        ArchetypeGenerationJobAdapter jobAdapter =
                            new ArchetypeGenerationJobAdapter( projectPath.append( artifactID ), importParentEnabled,
                                                               wizardContext );
                        IArchetypeExecutor archetypeExecutor = Maven2ArchetypeManager.getArchetypeExecutor();
                        archetypeExecutor.executeArchetype( archetype, projectPath, groupID, artifactID, version,
                                                            packageName, wizardContext, jobAdapter );
                    }
                    catch ( CoreException e )
                    {
                        MavenWizardActivator.getLogger().log( "Error executing the archetype", e );
                    }
                }
            } );
        }
        catch ( InvocationTargetException e )
        {
            MavenWizardActivator.getLogger().log( "Error finalizing the wizard", e );
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
     * public void pageTransition(PageTransitionEvent event) { if( event.getType() == PageTransitionEvent.EVENT_NEXT &&
     * event.getSelectedPage() == locationPage) { setArchetypeInfoUIElements(); } }
     */

    public void setProjectInfo()
    {
        projectName = locationPage.getProjectName().trim();

        /* Workaround to problem above */
        archetypeInfoPage.setGroupID( projectName );
        archetypeInfoPage.setArtifactID( projectName );
        archetypeInfoPage.setPackageName( projectName );
    }

    @Override
    public IWizardPage getNextPage( IWizardPage page )
    {
        if ( archetypeInfoPage == page )
        {
            addExtraPages();
        }
        return super.getNextPage( page );
    }

    public boolean hasExtraPages()
    {
        Archetype selectedArchetype = archetypePage.getArchetype();
        try
        {
            return WizardPageExtensionUtil.getExtraPages( selectedArchetype, wizardContext ).size() > 0;
        }
        catch ( CoreException e )
        {
            return false;
        }
    }

    @Override
    public IWizardPage getPreviousPage( IWizardPage page )
    {
        /*
         * The eclipse wizard API does not allow removing pages from a wizard, so we must disallow going back to the
         * archetype selection page, which might require a different set of pages.
         */
        if ( page instanceof IMavenWizardPage )
        {
            /* check if it is the first of the added pages. */
            List<IWizardPage> pages = Arrays.asList( getPages() );
            int index = pages.indexOf( page );
            if ( DEFAULT_PAGE_COUNT == index )
            {
                return null;
            }
        }
        return super.getPreviousPage( page );
    }

    /**
     * Appends the extra pages provided by extensions to this wizard list of pages.
     */
    private void addExtraPages()
    {
        if ( getPageCount() > DEFAULT_PAGE_COUNT )
        {
            /* Extra pages already added. */
            return;
        }
        try
        {
            Archetype selectedArchetype = archetypePage.getArchetype();
            List<IMavenWizardPage> extraPages =
                WizardPageExtensionUtil.getExtraPages( selectedArchetype, wizardContext );
            for ( IMavenWizardPage extraPage : extraPages )
            {
                addPage( extraPage );
            }
        }
        catch ( CoreException e )
        {
            MavenWizardActivator.getLogger().log( e );
        }
    }
}
