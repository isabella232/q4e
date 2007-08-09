/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;
import java.util.Set;

public interface IMavenArtifact
{

    public abstract Set<IMavenArtifact> getChildren();

    public abstract void setChildren( Set<IMavenArtifact> children );

    public abstract void addChild( IMavenArtifact child );

    public abstract String getArtifactId();

    public abstract void setArtifactId( String artifactId );

    public abstract String getGroupId();

    public abstract void setGroupId( String groupId );

    public abstract String getId();

    public abstract void setId( String id );

    public abstract String getVersion();

    public abstract void setVersion( String version );

    public abstract File getFile();

    public abstract void setFile( File file );

    public abstract IMavenArtifact getParent();

    public abstract void setParent( IMavenArtifact parent );

    public abstract String getScope();

    public abstract void setScope( String scope );

    public abstract String getType();

    public abstract void setType( String type );

    public abstract boolean isAddedToClasspath();

    public abstract void setAddedToClasspath( boolean addedToClasspath );
}