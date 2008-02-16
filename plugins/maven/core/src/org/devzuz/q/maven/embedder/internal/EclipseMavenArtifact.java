/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.versioning.VersionRange;
import org.devzuz.q.maven.embedder.IMavenArtifact;

public class EclipseMavenArtifact
    implements IMavenArtifact
{

    private Artifact artifact;

    private IMavenArtifact parent = null;

    private Set<IMavenArtifact> children = new HashSet<IMavenArtifact>();

    public EclipseMavenArtifact( Artifact artifact )
    {
        this.artifact = artifact;
    }

    private Artifact getArtifact()
    {
        return artifact;
    }

    public Artifact toMaven()
    {
        Artifact artifact =
            new DefaultArtifact( getGroupId(), getArtifactId(), VersionRange.createFromVersion( getVersion() ),
                                 getScope(), getType(), getClassifier(), null, false );
        return artifact;
    }

    public String getArtifactId()
    {
        return getArtifact().getArtifactId();
    }

    public String getGroupId()
    {
        return getArtifact().getGroupId();
    }

    public String getId()
    {
        return getArtifact().getId();
    }

    public String getVersion()
    {
        return getArtifact().getVersion();
    }

    public File getFile()
    {
        return getArtifact().getFile();
    }

    public IMavenArtifact getParent()
    {
        return parent;
    }

    public void setParent( IMavenArtifact parent )
    {
        this.parent = parent;
    }

    public String getScope()
    {
        return getArtifact().getScope();
    }

    public String getType()
    {
        return getArtifact().getType();
    }

    public String getClassifier()
    {
        return getArtifact().getArtifactHandler().getClassifier();
    }

    public boolean isAddedToClasspath()
    {
        return getArtifact().getArtifactHandler().isAddedToClasspath();
    }

    public Set<IMavenArtifact> getChildren()
    {
        return children;
    }

    public void addChild( IMavenArtifact child )
    {
        children.add( child );
        child.setParent( this );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof EclipseMavenArtifact ) )
        {
            return false;
        }
        if ( this == obj )
        {
            return true;
        }
        EclipseMavenArtifact rhs = (EclipseMavenArtifact) obj;
        return this.getArtifact().equals( rhs.getArtifact() );
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder( 17, 37 ).append( getArtifact() ).toHashCode();
    }

    @Override
    public String toString()
    {
        return getArtifact().toString();
    }
}
