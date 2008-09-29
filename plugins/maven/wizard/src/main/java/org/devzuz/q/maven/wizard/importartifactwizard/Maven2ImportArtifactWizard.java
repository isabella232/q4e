/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.wizard.importartifactwizard;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.embedder.MavenExecutionParameter;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.wizard.MavenWizardActivator;
import org.devzuz.q.maven.wizard.pages.ImportArtifactAdvanceWizardPage;
import org.devzuz.q.maven.wizard.pages.ImportArtifactWizardPage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class Maven2ImportArtifactWizard extends Wizard implements IImportWizard
{
    private ImportArtifactWizardPage importArtifactWizardPage;
    private ImportArtifactAdvanceWizardPage importArtifactAdvanceWizardPage;
    @Override
    public void addPages()
    {
        importArtifactWizardPage = new ImportArtifactWizardPage();
        importArtifactAdvanceWizardPage = new ImportArtifactAdvanceWizardPage();
        addPage( importArtifactWizardPage );
        addPage( importArtifactAdvanceWizardPage );
    }

    @Override
    public boolean performFinish()
    {
        try
        {
            String goal = getGoal();
            Map<String, String> propertyMap = getGoalParameters();

            Properties properties = new Properties();
            properties.putAll( propertyMap );

            MavenExecutionParameter parameter = MavenExecutionParameter.newDefaultMavenExecutionParameter( properties );
            /* We don't really need a baseDirectory because the goal doesnt require it, so we'll just pass any directory */
            MavenManager.getMaven().scheduleGoal( ResourcesPlugin.getWorkspace().getRoot().getFullPath() , goal, parameter );

            return true;
        }
        catch ( CoreException e )
        {
            MavenWizardActivator.getLogger().error( "In performFinish() of Maven2ImportArtifactWizard - " + e.getMessage() );
            
            MessageBox msgBox = new MessageBox( this.getShell() , SWT.ICON_ERROR | SWT.OK );
            msgBox.setMessage( "Error in maven execution : " + e );
            msgBox.open();
            
            return false;
        }
    }

    public void init( IWorkbench workbench, IStructuredSelection selection )
    {

    }
    
    private String getGoal()
    {
        if( importArtifactAdvanceWizardPage.isInstallLocally() )
        {
            return "install:install-file"; //$NON-NLS-1$
        }
        else
        {
            return "deploy:deploy-file"; //$NON-NLS-1$
        }
    }
    
    private Map<String , String> getGoalParameters()
    {
        Map<String , String> parameters = new HashMap<String , String>();
        
        parameters.put( "groupId", importArtifactWizardPage.getGroupId() ); //$NON-NLS-1$
        parameters.put( "artifactId", importArtifactWizardPage.getArtifactId() ); //$NON-NLS-1$
        parameters.put( "version", importArtifactWizardPage.getVersion() ); //$NON-NLS-1$
        parameters.put( "packaging", importArtifactWizardPage.getPackaging() ); //$NON-NLS-1$
        parameters.put( "generatePom" , importArtifactWizardPage.isGeneratePom() ? "true" : "false" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        parameters.put( "file" , importArtifactWizardPage.getFileToInstall() ); //$NON-NLS-1$
        
        if( !importArtifactWizardPage.getClassifier().equals( "" ) ) //$NON-NLS-1$
        {
            parameters.put( "classifier", importArtifactWizardPage.getClassifier() ); //$NON-NLS-1$
        }
        
        if( importArtifactAdvanceWizardPage.isInstallRemotely() )
        {
            parameters.put( "repositoryId", importArtifactAdvanceWizardPage.getRepositoryId() ); //$NON-NLS-1$
            parameters.put( "repositoryLayout" , importArtifactAdvanceWizardPage.getRepositoryLayout() ); //$NON-NLS-1$
            parameters.put( "url" , importArtifactAdvanceWizardPage.getRepositoryUrl() ); //$NON-NLS-1$
        }
        
        return parameters;
    }
}
