/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.formeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.pomeditor.PomEditorActivator;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBasicFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomBuildFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDependenciesFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomDevelopersFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomLicensesFormPage;
import org.devzuz.q.maven.pomeditor.pages.MavenPomPropertiesModuleFormPage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

public class MavenPomFormEditor extends FormEditor
{
    public static final String BASIC_INFO_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomBasicFormPage";
    
    public static final String DEPENDENCIES_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDependenciesFormPage";
    
    public static final String LICENSES_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomLicensesFormPage";
    
    public static final String MODULES_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomPropertiesModuleFormPage";
    
    public static final String DEVELOPERS_FORM_PAGE = "org.devzuz.q.maven.jdt.ui.pomeditor.MavenPomDevelopersFormPage";
    
    public static final String BUILD_FORM_PAGE = "org.devzuz.q.maven.pomeditor.MavenPomBuildFormPage";
    
    private Model pomModel;

    private MavenPomBasicFormPage basicFormPage;

    private MavenPomDependenciesFormPage dependenciesFormPage;

    private MavenPomPropertiesModuleFormPage modulePropertiesFormPage;

    private MavenPomLicensesFormPage mavenPomLicensesFormPage;
    
    private MavenPomDevelopersFormPage mavenPomDevelopersFormPage;
    
    private MavenPomBuildFormPage buildFormPage;
    
    public MavenPomFormEditor()
    {
    }

    @Override
    protected void addPages()
    {
        try
        {
            if ( initializeAddPagesOK() )
            {
                basicFormPage = 
                    new MavenPomBasicFormPage( this, BASIC_INFO_FORM_PAGE , "Project Information", this.pomModel );
                addPage( basicFormPage );

                dependenciesFormPage = 
                    new MavenPomDependenciesFormPage( this, DEPENDENCIES_FORM_PAGE, "Dependencies", this.pomModel );
                addPage( dependenciesFormPage );

                mavenPomLicensesFormPage = 
                    new MavenPomLicensesFormPage( this , LICENSES_FORM_PAGE , "License", this.pomModel );
                addPage( mavenPomLicensesFormPage );

                mavenPomDevelopersFormPage = 
                    new MavenPomDevelopersFormPage( this , DEVELOPERS_FORM_PAGE , "Developer", this.pomModel );
                addPage( mavenPomDevelopersFormPage );
                
                modulePropertiesFormPage = 
                    new MavenPomPropertiesModuleFormPage( this, MODULES_FORM_PAGE, "Properties/Module", this.pomModel );
                addPage( modulePropertiesFormPage );
                
                buildFormPage = 
                    new MavenPomBuildFormPage( this, BUILD_FORM_PAGE, "Build Management", this.pomModel );             
                addPage( buildFormPage );
            }
        }
        catch ( PartInitException pie )
        {
            pie.printStackTrace();
        }
    }

    private boolean initializeAddPagesOK()
    {
        if ( getPomFile() != null )
        {
            try
            {
                this.pomModel = new MavenXpp3Reader().read( new FileReader( getPomFile() ) );
            }
            catch ( FileNotFoundException e )
            {
                e.printStackTrace();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            catch ( XmlPullParserException e )
            {
                e.printStackTrace();
            }
        }
        else
        {
            return false;
        }

        return true;

    }

    @Override
    public void doSave( IProgressMonitor monitor )
    {
        monitor.beginTask( "Writing to POM file.", 3 );
        savePomFile();
        monitor.worked( 1 );
        setPagesClean();
        monitor.worked( 1 );
        editorDirtyStateChanged();
        monitor.worked( 1 );
        monitor.done();
    }

    @Override
    public void doSaveAs()
    {

    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    protected void savePomFile()
    {
        try
        {
            File pomFile = getPomFile();

            MavenUtils.rewritePom( pomFile , pomModel );
            
            IPath location = Path.fromOSString( pomFile.getAbsolutePath() );
            IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation( location );
            
            file.refreshLocal( IResource.DEPTH_ZERO, null );

        }
        catch ( CoreException e )
        {
            PomEditorActivator.getLogger().log( e );
        }
        catch ( IOException e )
        {
            PomEditorActivator.getLogger().log( e );
        }
        catch ( Exception e )
        {
            PomEditorActivator.getLogger().log( e );
        }

    }

    private void setPagesClean()
    {
        basicFormPage.setPageModified( false );
        dependenciesFormPage.setPageModified( false );
        modulePropertiesFormPage.setPageModified( false );
        mavenPomLicensesFormPage.setPageModified(false);
        mavenPomDevelopersFormPage.setPageModified(false);
        buildFormPage.setPageModified( false );
        // clean other pages
    }

    private File getPomFile()
    {
        IEditorInput input = getEditorInput();
        if ( input instanceof IFileEditorInput )
        {
            return ( (IFileEditorInput) input ).getFile().getLocation().toFile();
        }

        return null;
    }
}
