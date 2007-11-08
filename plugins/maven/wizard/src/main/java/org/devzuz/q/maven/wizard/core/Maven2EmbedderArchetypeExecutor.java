/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenEventEnd;
import org.devzuz.q.maven.embedder.IMavenListener;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.embedder.PomFileDescriptor;
import org.devzuz.q.maven.jdt.ui.projectimport.ScanImportProjectJob;
import org.devzuz.q.maven.ui.core.archetypeprovider.Archetype;
import org.devzuz.q.maven.wizard.MavenWizardActivator;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

public class Maven2EmbedderArchetypeExecutor implements IArchetypeExecutor
{

    public void executeArchetype( Archetype archetype, IPath baseDir, String groupId, String artifactId,
                                  String version, String packageName, IMavenWizardContext wizardContext )
        throws CoreException
    {
        Properties archetypeProperties = new Properties( wizardContext.getArchetypeCreationProperties() );

        archetypeProperties.setProperty( "groupId", groupId );
        archetypeProperties.setProperty( "artifactId", artifactId );
        archetypeProperties.setProperty( "version", version );
        archetypeProperties.setProperty( "packageName", packageName );
        archetypeProperties.setProperty( "archetypeArtifactId", archetype.getArtifactId() );
        archetypeProperties.setProperty( "archetypeGroupId", archetype.getGroupId() );
        archetypeProperties.setProperty( "basedir", baseDir.makeAbsolute().toOSString() );
        // TODO user.dir is not needed with archetype plugin 1.0-alpha-6
        archetypeProperties.setProperty( "user.dir", baseDir.makeAbsolute().toOSString() );

        if ( !archetype.getVersion().equals( "" ) )
            archetypeProperties.setProperty( "archetypeVersion", archetype.getVersion() );

        if ( !archetype.getRemoteRepositories().equals( "" ) )
            archetypeProperties.setProperty( "remoteRepositories", archetype.getRemoteRepositories() );

        IMavenListener listener = new ArchetypeGenerationListener( baseDir.append( artifactId ), wizardContext );

        MavenManager.getMaven().addEventListener( listener );
        MavenManager.getMaven().scheduleGoal( baseDir, ARCHETYPE_PLUGIN_ID + ":create", archetypeProperties );
    }

    /**
     * Import generated archetype in the workspace when generation ends
     * 
     * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
     * @version $Id$
     */
    private class ArchetypeGenerationListener implements IMavenListener
    {

        private final IPath generatedDir;

        private final IMavenWizardContext wizardContext;

        /**
         * Creates a new maven listener for handling archetype creation.
         * 
         * @param generatedDir
         *            the target folder for the generated archetype.
         * @param wizardContext
         *            additional information provided through new project wizard extensions.
         */
        public ArchetypeGenerationListener( IPath generatedDir, IMavenWizardContext wizardContext )
        {
            this.generatedDir = generatedDir;
            this.wizardContext = wizardContext;
        }

        public void dispose()
        {
        }

        public void handleEvent( IMavenEvent event )
        {
            if ( ( event instanceof IMavenEventEnd ) && ( event.getType().equals( EventType.projectExecution ) ) )
            {
                ScanImportProjectJob job = new ScanImportProjectJob( generatedDir.toFile() );
                job.addJobChangeListener( new JobChangeAdapter()
                {
                    @Override
                    public void done( IJobChangeEvent event )
                    {
                        if ( event.getResult().getSeverity() == IStatus.OK )
                        {
                            Collection<PomFileDescriptor> pomDescriptors =
                                ( (ScanImportProjectJob) event.getJob() ).getPomDescriptors();
                            applyPostProcessors( pomDescriptors );
                        }
                    }
                } );
                job.schedule();

                MavenManager.getMaven().removeEventListener( this );
            }
        }

        /**
         * Applies the registered postprocessors to every project generated by the archetype.
         * 
         * @param pomDescriptors
         */
        protected void applyPostProcessors( Collection<PomFileDescriptor> pomDescriptors )
        {
            List<IMavenProjectPostprocessor> allPostProcessors = wizardContext.getPostProcessors();
            for ( PomFileDescriptor pomDescriptor : pomDescriptors )
            {
                IMavenProject mavenProject;
                try
                {
                    // Resolve the pom to a full IMavenProject.
                    // TODO: Should delegate to the Processor? Maybe most of them won't need so much info.
                    mavenProject = MavenManager.getMaven().getMavenProject( pomDescriptor.getFile(), false );
                    for ( IMavenProjectPostprocessor postProcessor : allPostProcessors )
                    {
                        postProcessor.run( mavenProject );
                    }
                }
                catch ( CoreException e )
                {
                    // TODO: Throw an exception and inform the user
                    MavenWizardActivator.log( "Could not resolve the Maven Project.", e );
                }
            }
        }

        /**
         * Obtains the additional information provided through new project wizard extensions.
         * 
         * @return the wizard context.
         */
        public IMavenWizardContext getWizardContext()
        {
            return wizardContext;
        }

    }
}
