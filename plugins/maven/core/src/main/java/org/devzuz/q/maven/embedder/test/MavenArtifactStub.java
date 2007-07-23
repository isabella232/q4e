/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder.test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenArtifact;

/**
 * Stub of {@link IMavenArtifact} for testing
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class MavenArtifactStub implements IMavenArtifact {

    private String artifactId;

    private String groupId;

    private String id;

    private String version;

    private String scope;

    private String type;

    private boolean addedToClasspath;

    private File file;

    private IMavenArtifact parent = null;

    private Set<IMavenArtifact> children = new HashSet<IMavenArtifact>();

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public IMavenArtifact getParent() {
        return parent;
    }

    public void setParent(IMavenArtifact parent) {
        this.parent = parent;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAddedToClasspath() {
        return addedToClasspath;
    }

    public void setAddedToClasspath(boolean addedToClasspath) {
        this.addedToClasspath = addedToClasspath;
    }

    public Set<IMavenArtifact> getChildren() {
        return children;
    }

    public void setChildren(Set<IMavenArtifact> children) {
        this.children = children;
    }

    public void addChild(IMavenArtifact child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof IMavenArtifact) {
            IMavenArtifact secondArtifact = (IMavenArtifact) arg0;
            if (!secondArtifact.getArtifactId().equals(getArtifactId()))
                return false;
            if (!secondArtifact.getGroupId().equals(getGroupId()))
                return false;
            if (!secondArtifact.getVersion().equals(getVersion()))
                return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getArtifactId().hashCode() * getGroupId().hashCode() * getVersion().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("artifactId[");
        sb.append(getArtifactId());
        sb.append("] ");
        sb.append("groupId[");
        sb.append(getGroupId());
        sb.append("] ");
        sb.append("version[");
        sb.append(getVersion());
        sb.append("] ");
        return sb.toString();
    }
}
