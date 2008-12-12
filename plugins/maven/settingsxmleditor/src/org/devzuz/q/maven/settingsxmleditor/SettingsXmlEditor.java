/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.settingsxmleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.maven.settings.Settings;
import org.apache.maven.settings.io.xpp3.SettingsXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

/**
 * @author Allan Ramirez
 */
public class SettingsXmlEditor
    extends StructuredTextEditor
{
    private static String SETTINGSXML_FILENAME = "settings.xml";

    private boolean hasEssentialChanges;

    public SettingsXmlEditor()
    {
        super();
    }

    @Override
    public void doSave( IProgressMonitor progressMonitor )
    {
        checkForEssentialChanges();
        
        super.doSave( progressMonitor );

        if ( hasEssentialChanges && MessageDialog.openQuestion( new Shell(), Messages.MessageDialog_Confirm_Title,
                                         Messages.MessageDialog_Confirm_Message ) )
        {
            try
            {
                MavenManager.getMaven().refresh();
                buildAllMavenProjectsinWorkspace();
            }
            catch ( CoreException e )
            {
                SettingsXmlEditorActivator.getLogger().log( e );
            }
        }
    }

    @Override
    public void doSaveAs()
    {
        checkForEssentialChanges();
        
        super.doSaveAs();
        if ( hasEssentialChanges && getEditorInput().getName().equals( SETTINGSXML_FILENAME ) )
        {
            if ( MessageDialog.openQuestion( new Shell(), Messages.MessageDialog_Confirm_Title,
                                             Messages.MessageDialog_Confirm_Message ) )
            {
                try
                {
                    MavenManager.getMaven().refresh();
                    buildAllMavenProjectsinWorkspace();
                }
                catch ( CoreException e )
                {
                    SettingsXmlEditorActivator.getLogger().log( e );
                }
            }
        }
    }

    /*
     * Build all maven projects in workspace
     */
    public static void buildAllMavenProjectsinWorkspace()
    {
        final IProject[] mavenProjects = MavenManager.getMavenProjectManager().getWorkspaceProjects();

        WorkspaceJob job = new WorkspaceJob( "Building Maven Projects in Workspace" )
        {
            public IStatus runInWorkspace( IProgressMonitor monitor )
                throws CoreException
            {
                for ( int i = 0; i < mavenProjects.length; i++ )
                {
                    mavenProjects[i].build( IncrementalProjectBuilder.FULL_BUILD, monitor );
                }
                return Status.OK_STATUS;
            }
        };
        job.setUser( true );
        job.setRule( modifyRule( mavenProjects ) );
        job.schedule();
    }

    public static ISchedulingRule modifyRule( IProject[] projects )
    {
        ISchedulingRule combinedRule = null;
        IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
        for ( int i = 0; i < projects.length; i++ )
        {
            ISchedulingRule rule = ruleFactory.modifyRule( projects[i] );
            combinedRule = MultiRule.combine( rule, combinedRule );
        }
        return combinedRule;
    }

    private void checkForEssentialChanges()
    {
        try
        {
            IDocumentProvider dp = getDocumentProvider();
            IDocument doc = dp.getDocument( getEditorInput() );
            String bufferedCopy = doc.get();
            File file = new File( ( (FileStoreEditorInput) getEditorInput() ).getURI() );

            SettingsXpp3Reader settingsReader = new SettingsXpp3Reader();

            Settings copy = settingsReader.read( new StringReader( bufferedCopy ) );
            Settings orig = settingsReader.read( new FileReader( file ) );

            SettingsXmlDiff diff = new SettingsXmlDiff( orig, copy );
            hasEssentialChanges = diff.hasEssentialDiffs();
        }
        catch ( FileNotFoundException e )
        {
            SettingsXmlEditorActivator.getLogger().log( e );
        }
        catch ( IOException e )
        {
            SettingsXmlEditorActivator.getLogger().log( e );
        }
        catch ( XmlPullParserException e )
        {
            MessageDialog.openWarning( getEditorSite().getShell(), "Warning", "settings.xml is invalid. Please correct this in order to avoid further problems. \n\n" + e.getMessage());
        }
    }
}