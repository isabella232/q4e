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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.Messages;
import org.devzuz.q.maven.dependency.analysis.extension.IInstance;
import org.devzuz.q.maven.dependency.analysis.model.Version;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.pom.Dependency;
import org.devzuz.q.maven.embedder.pom.Dom4JModel;
import org.devzuz.q.maven.embedder.pom.Exclusion;
import org.devzuz.q.maven.embedder.pom.ModelRoot;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.DOMImplementation;

public class ExcludeDependencyAction
    extends StructuredSelectionAction
{
    public ExcludeDependencyAction()
    {
        super();
    }

    @SuppressWarnings( "unchecked" )
    public void run( IAction action )
    {
        IMavenProject project = null;

        for ( Iterator iterator = getSelection().iterator(); iterator.hasNext(); )
        {
            Object selected = iterator.next();
            if ( selected instanceof Version )
            {
                Version version = (Version) selected;
                project = version.getClassificationParent().getProject();

                if ( isCurrentProject( version, project ) )
                {
                    String artifact = version.getGroupId() + ":" + version.getArtifactId();
                    handleError( NLS.bind( Messages.ExcludeAll_Error_Cant_Exclude_Root, artifact ), null );
                    return;
                }

                List instances = version.getInstances();
                List<IInstance> dependencyParents = new ArrayList<IInstance>();
                for ( int i = 0; i < instances.size(); i++ )
                {
                    IInstance instance = (IInstance) instances.get( i );
                    IInstance rootDependency = getRootDependency( instance );
                    if ( !dependencyParents.contains( rootDependency ) )
                    {
                        dependencyParents.add( rootDependency );
                    }
                }
                createExclusions( project, dependencyParents, version );
            }
        }
    }

    private void handleError( String message, Throwable e )
    {
        Status status =
            new Status( Status.ERROR, DependencyAnalysisActivator.PLUGIN_ID + ".excludeDependency", message, e );
        DependencyAnalysisActivator.getLogger().log( status );
        ErrorDialog.openError( new Shell( Display.getCurrent() ), Messages.ExcludeAll_Error_Title,
                               Messages.ExcludeAll_Error_Message, status );
    }

    private void createExclusions( IMavenProject project, List<IInstance> dependencyParents, Version version )
    {
        Dom4JModel root = new Dom4JModel( project.getPomFile() );
        ModelRoot model = root.getModel();

        Map<String, Dependency> dependencyMap = new HashMap<String, Dependency>();
        for ( Dependency dependency : model.getDependencies() )
        {
            String key = key( dependency );
            if ( !dependencyMap.containsKey( key ) )
            {
                dependencyMap.put( key, dependency );
            }
        }

        for ( IInstance dependencyParent : dependencyParents )
        {
            Dependency dependency = dependencyMap.get( key( dependencyParent ) );
            Exclusion exclusion = dependency.getExclusions().addNew();
            exclusion.setGroupId( version.getGroupId() );
            exclusion.setArtifactId( version.getArtifactId() );
        }
        try
        {
            root.write();
            project.getProject().refreshLocal( IResource.DEPTH_ONE, null );
        }
        catch ( IOException e )
        {
            handleError( e.getMessage(), e );
        }
        catch ( CoreException e )
        {
            handleError( e.getMessage(), e );
        }
    }

    
    private void createExclusions2()
    {
        // Find the implementation
        

    }
    private String key( Dependency dependency )
    {
        return dependency.getGroupId() + ":" + dependency.getArtifactId();
    }

    private String key( IInstance instance )
    {
        return instance.getGroupId() + ":" + instance.getArtifactId();
    }

    private boolean isCurrentProject( Version version, IMavenProject project )
    {
        return version.getArtifactId().equals( project.getArtifactId() )
            && version.getGroupId().equals( project.getGroupId() )
            && version.getVersion().equals( project.getVersion() );
    }

    private IInstance getRootDependency( IInstance instance )
    {
        if ( instance.getDependencyParent().getDependencyParent() == null )
        {
            return instance;
        }
        else
        {
            return getRootDependency( instance.getDependencyParent() );
        }
    }
}