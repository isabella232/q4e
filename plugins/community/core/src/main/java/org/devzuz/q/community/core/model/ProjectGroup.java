/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectGroup
    extends SimpleNode
{

    public ProjectGroup( String name )
    {
        super( name );
    }

    private Community parent;

    private List<Project> projects = new ArrayList<Project>();

    public void addProject( Project project )
    {
        project.setParent( this );
        projects.add( project );
    }

    public Object[] getChildren()
    {
        return projects.toArray( new Project[projects.size()] );
    }

    public void setChildren( List<Project> projects )
    {
        this.projects = projects;
    }

    public Community getParent()
    {
        return parent;
    }

    public boolean hasChildren()
    {
        return projects.size() > 0;
    }
}