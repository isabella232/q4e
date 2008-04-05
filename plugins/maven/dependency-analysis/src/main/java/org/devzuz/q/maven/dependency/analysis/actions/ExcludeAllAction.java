/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.dependency.analysis.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.Messages;
import org.devzuz.q.maven.dependency.analysis.extension.IArtifact;
import org.devzuz.q.maven.dependency.analysis.extension.IInstance;
import org.devzuz.q.maven.dependency.analysis.extension.ISelectionAction;
import org.devzuz.q.maven.dependency.analysis.extension.ISelectionSet;
import org.devzuz.q.maven.dependency.analysis.extension.IVersion;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.pom.Dependency;
import org.devzuz.q.maven.embedder.pom.Dom4JModel;
import org.devzuz.q.maven.embedder.pom.Exclusion;
import org.devzuz.q.maven.embedder.pom.ModelRoot;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExcludeAllAction
    implements ISelectionAction
{

    public static final String PLUGIN_ID = DependencyAnalysisActivator.PLUGIN_ID + ".excludeAll";

    public void execute( IMavenProject project, ISelectionSet primary, ISelectionSet secondary )
        throws CoreException
    {
        try
        {
            List<ExclusionSet> exclusionSets = new ArrayList<ExclusionSet>();

            for ( IArtifact artifact : primary.getArtifacts() )
            {
                if ( key( artifact ).equals( key( project ) ) )
                {
                    handleError( NLS.bind( Messages.ExcludeAll_Error_Cant_Exclude_Root, key( artifact ) ), null );
                    return;
                }

                ExclusionSet exclusionSet = new ExclusionSet();
                exclusionSets.add( exclusionSet );
                exclusionSet.excluded = artifact;

                for ( IVersion version : artifact.getVersions() )
                {
                    for ( IInstance instance : version.getInstances() )
                    {
                        IInstance topLevelDependency = getTopLevelDependency( instance );
                        if ( topLevelDependency != null && !exclusionSet.fromInstances.contains( topLevelDependency ) )
                        {
                            exclusionSet.fromInstances.add( topLevelDependency );
                        }
                    }
                }
            }

            StringBuffer message = new StringBuffer();
            message.append( NLS.bind( Messages.ExcludeAll_Confirm_Message, project.getProject().getName() ) );
            for ( int i = 0; i < exclusionSets.size(); i++ )
            {
                ExclusionSet exclusionSet = exclusionSets.get( i );
                message.append( "\n\n" );
                message.append( NLS.bind( Messages.ExcludeAll_Confirm_Excluded_Artifact, i, key( exclusionSet.excluded ) ) );

                for ( IInstance instance : exclusionSet.fromInstances )
                {
                    message.append( "\n\t" );
                    message.append( NLS.bind( Messages.ExcludeAll_Confirm_Excluded_Dependency, key( instance ) ) );
                }
            }

            if ( MessageDialog.openConfirm( new Shell( Display.getCurrent() ), Messages.ExcludeAll_Confirm_Title,
                                            message.toString() ) )
            {
                createExclusions( project, exclusionSets );
            }
        }
        catch ( RuntimeException e )
        {
            handleError( e );
        }

    }

    private void createExclusions( IMavenProject project, List<ExclusionSet> exclusionSets )
    {
        // create an indexed list of the dependency management pom element
        Dom4JModel root = new Dom4JModel( project.getPomFile() );
        ModelRoot model = root.getModel();

        Map<String, Dependency> index = new HashMap<String, Dependency>();
        for ( Dependency dependency : model.getDependencyManagement().getDependencies() )
        {
            String key = key( dependency );
            if ( !index.containsKey( key ) )
            {
                index.put( key, dependency );
            }
        }

        // perform the exclusions
        for ( ExclusionSet exclusionSet : exclusionSets )
        {
            for ( IInstance instance : exclusionSet.fromInstances )
            {
                String exclusionKey = key( exclusionSet.excluded );
                String instanceKey = key( instance );
                if ( exclusionKey.equals( instanceKey ) )
                {
                    /*
                     * exclude a top level artifact - remove it from the dependencies instead
                     */
                    for ( Dependency dependency : model.getDependencies() )
                    {
                        if ( instanceKey.equals( key( dependency ) ) )
                        {
                            dependency.detach();
                        }
                    }
                }
                else
                {
                    Dependency dependency = index.get( instanceKey );
                    if ( dependency == null )
                    {
                        dependency = model.getDependencyManagement().getDependencies().addNew();
                        dependency.setGroupId( instance.getGroupId() );
                        dependency.setArtifactId( instance.getArtifactId() );
                        dependency.setVersion( instance.getVersion() );
                        index.put( instanceKey, dependency );
                    }
                    if ( !containsExclusion( exclusionKey, dependency.getExclusions() ) )
                    {
                        Exclusion exclusion = dependency.getExclusions().addNew();
                        exclusion.setGroupId( exclusionSet.excluded.getGroupId() );
                        exclusion.setArtifactId( exclusionSet.excluded.getArtifactId() );
                    }
                }
            }
        }

        try
        {
            root.write();
            project.getProject().refreshLocal( IResource.DEPTH_ONE, null );
        }
        catch ( IOException e )
        {
            handleError( e );
        }
        catch ( CoreException e )
        {
            handleError( e );
        }

    }

    private void handleError( Throwable e )
    {
        handleError( e.getMessage(), e );
    }

    private void handleError( String message, Throwable e )
    {
        Status status = new Status( Status.ERROR, PLUGIN_ID, message, e );
        DependencyAnalysisActivator.getLogger().log( status );
        ErrorDialog.openError( new Shell( Display.getCurrent() ), Messages.ExcludeAll_Error_Title,
                               Messages.ExcludeAll_Error_Message, status );
    }

    private boolean containsExclusion( String exclusionKey, Iterable<Exclusion> exclusions )
    {
        for ( Exclusion exclusion : exclusions )
        {
            if ( key( exclusion ).equals( exclusionKey ) )
            {
                return true;
            }
        }
        return false;
    }

    private String key( Exclusion exclusion )
    {
        return exclusion.getGroupId() + ":" + exclusion.getArtifactId();
    }

    private String key( Dependency dependency )
    {
        return dependency.getGroupId() + ":" + dependency.getArtifactId();
    }

    private String key( IInstance instance )
    {
        return instance.getGroupId() + ":" + instance.getArtifactId();
    }

    private String key( IArtifact artifact )
    {
        return artifact.getGroupId() + ":" + artifact.getArtifactId();
    }

    private String key( IMavenProject project )
    {
        return project.getGroupId() + ":" + project.getArtifactId();
    }

    private IInstance getTopLevelDependency( IInstance leaf )
    {
        if ( leaf.getDependencyParent() == null )
        {
            // this is the top level project
            return null;
        }
        if ( leaf.getDependencyParent().getDependencyParent() == null )
        {
            // this is a direct dependency of the top level project
            return leaf;
        }
        // go up a level and try again
        return getTopLevelDependency( leaf.getDependencyParent() );
    }

    private class ExclusionSet
    {
        private IArtifact excluded;

        private List<IInstance> fromInstances;

        public ExclusionSet()
        {
            fromInstances = new ArrayList<IInstance>();
        }
    }

}
