/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.projectimport;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.PomFileDescriptor;

/**
 * Default class for naming maven projects
 * 
 * @author emantos
 */
public class DefaultMavenProjectNamingScheme implements IMavenProjectNamingScheme
{
    public static final String GROUP_ID_VAR = "${groupId}";
    
    public static final String ARTIFACT_ID_VAR = "${artifactId}";
    
    public static final String VERSION_VAR = "${version}";
    
    public static final String DIRECTORY_VAR = "${directory}";
    
    private String namingScheme;
    
    public DefaultMavenProjectNamingScheme( String namingScheme )
    {
        this.namingScheme = namingScheme;
    }

    public String getNamingScheme()
    {
        return namingScheme;
    }

    public void setNamingScheme( String namingScheme )
    {
        this.namingScheme = namingScheme;
    }
    
    public String getMavenProjectName( IMavenProject mavenProject )
    {   
        return getMavenProjectName( namingScheme , mavenProject.getGroupId() , mavenProject.getArtifactId(), 
                                    mavenProject.getVersion(), mavenProject.getBaseDirectory().getName() );
    }
    
    public String getMavenProjectName( PomFileDescriptor fileDescriptor )
    {
        return getMavenProjectName( namingScheme , fileDescriptor.getModel().getGroupId() , fileDescriptor.getModel().getArtifactId(), 
                                    fileDescriptor.getModel().getVersion(), fileDescriptor.getFile().getName() );
    }
    
    public static String getMavenProjectName( String namingScheme , String groupId, String artifactId , 
                                              String version , String baseDirectory )
    {
        if( namingScheme != null )
        {
            namingScheme = namingScheme.replace( GROUP_ID_VAR, groupId != null ? groupId : "" ); 
            namingScheme = namingScheme.replace( ARTIFACT_ID_VAR, artifactId != null ? artifactId : "" );
            namingScheme = namingScheme.replace( DIRECTORY_VAR, baseDirectory != null ? baseDirectory : "" );
            namingScheme = namingScheme.replace( VERSION_VAR, version != null ? version : "" );
        }
        
        return namingScheme;
    }
}
