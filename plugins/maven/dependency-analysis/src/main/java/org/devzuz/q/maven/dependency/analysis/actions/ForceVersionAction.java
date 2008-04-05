/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/

package org.devzuz.q.maven.dependency.analysis.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.devzuz.q.maven.dependency.analysis.Messages;
import org.devzuz.q.maven.dependency.analysis.extension.IArtifact;
import org.devzuz.q.maven.dependency.analysis.extension.ISelectionAction;
import org.devzuz.q.maven.dependency.analysis.extension.ISelectionSet;
import org.devzuz.q.maven.dependency.analysis.views.SelectVersionDialog;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.pom.Dependency;
import org.devzuz.q.maven.embedder.pom.Dom4JModel;
import org.devzuz.q.maven.embedder.pom.ImplicitCollection;
import org.devzuz.q.maven.embedder.pom.ModelRoot;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ForceVersionAction
    implements ISelectionAction
{

    public static final String PLUGIN_ID = DependencyAnalysisActivator.PLUGIN_ID + ".forceVersion";

    public void execute( IMavenProject project, ISelectionSet primary, ISelectionSet secondary )
        throws CoreException
    {
        Dom4JModel root = new Dom4JModel( project.getPomFile() );
        ModelRoot model = root.getModel();
        Map<String, Dependency> dependencyManagementMap =
            createDependencyMap( model.getDependencyManagement().getDependencies() );
        Map<String, Dependency> dependencyMap = createDependencyMap( model.getDependencies() );
        boolean changesMade = false;

        for ( IArtifact artifact : primary.getArtifacts() )
        {
            SelectVersionDialog dialog = new SelectVersionDialog( new Shell( Display.getCurrent() ), artifact );
            if ( SelectVersionDialog.OK == dialog.open() )
            {
                if ( dialog.versionHasChanged() )
                {
                    changesMade = true;
                    String selectedVersion = dialog.getSelectedVersion();
                    createDependencyManagementEntry( dependencyManagementMap, dependencyMap, model, artifact,
                                                     selectedVersion );
                }
            }
        }
        if ( changesMade )
        {
            try
            {
                root.write();
                project.getProject().refreshLocal( IResource.DEPTH_ONE, null );
            }
            catch ( IOException e )
            {
                handleError( e );
            }

        }
    }

    private Map<String, Dependency> createDependencyMap( ImplicitCollection<Dependency> list )
    {
        Map<String, Dependency> map = new HashMap<String, Dependency>();
        for ( Dependency dependency : list )
        {
            map.put( key( dependency ), dependency );
        }
        return map;
    }

    private void createDependencyManagementEntry( Map<String, Dependency> dependencyManagementMap,
                                                  Map<String, Dependency> dependencyMap, ModelRoot model,
                                                  IArtifact artifact, String version )
    {
        // add the dependency management entry
        Dependency dependencyManagement = dependencyManagementMap.get( key( artifact ) );
        if ( dependencyManagement == null )
        {
            dependencyManagement = model.getDependencyManagement().getDependencies().addNew();
            dependencyManagementMap.put( key( dependencyManagement ), dependencyManagement );
            dependencyManagement.setArtifactId( artifact.getArtifactId() );
            dependencyManagement.setGroupId( artifact.getGroupId() );
        }
        dependencyManagement.setVersion( version );

        // if there are any top level dependencies on this artifact then remove the version information
        Dependency dependency = dependencyMap.get( key( artifact ) );
        if ( dependency != null )
        {
            dependency.setVersion( null );
        }

    }

    private String key( Dependency dependency )
    {
        return dependency.getGroupId() + ":" + dependency.getArtifactId();
    }

    private String key( IArtifact artifact )
    {
        return artifact.getGroupId() + ":" + artifact.getArtifactId();
    }

    private void handleError( Throwable e )
    {
        handleError( e.getMessage(), e );
    }

    private void handleError( String message, Throwable e )
    {
        Status status = new Status( Status.ERROR, PLUGIN_ID, message, e );
        DependencyAnalysisActivator.getLogger().log( status );
        ErrorDialog.openError( new Shell( Display.getCurrent() ), Messages.ForceVersion_Error_Title,
                               Messages.ForceVersion_Error_Message, status );
    }
}
