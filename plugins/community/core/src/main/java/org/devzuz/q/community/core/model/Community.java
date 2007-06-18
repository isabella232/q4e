/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Community
    extends SimpleNode
{

    private String description = "";

    private List<ActivityEntry> activities = new ArrayList<ActivityEntry>();

    private List<Participant> participants = new ArrayList<Participant>();

    public Community( String name, String description )
    {
        super( name );
        this.description = description;
        loadActivities();
    }

    private void loadActivities()
    {
        ActivityEntry e1 = new ActivityEntry();
        e1.setActivityDate( new Date() );
        e1.setDescription( "Community Created" );
        e1.setSource( "Q Infrastructure" );
        activities.add( e1 );

        ActivityEntry e2 = new ActivityEntry();
        e2.setActivityDate( new Date() );
        e2.setDescription( "Mailing List added" );
        e2.setSource( "Q Infrastructure" );
        activities.add( e2 );

        ActivityEntry e3 = new ActivityEntry();
        e2.setActivityDate( new Date() );
        e2.setDescription( "Project 'Example' has released version 1.1" );
        e2.setSource( "Project 'Example'" );
        activities.add( e2 );
    }

    private List<ProjectGroup> projectGroups = new ArrayList<ProjectGroup>();

    public void addProjectGroup( ProjectGroup projectGroup )
    {
        projectGroup.setParent( this );
        projectGroups.add( projectGroup );
    }

    public Object[] getChildren()
    {
        return projectGroups.toArray( new ProjectGroup[projectGroups.size()] );
    }

    public boolean hasChildren()
    {
        return projectGroups.size() > 0;
    }

    public String getDescription()
    {
        return description;
    }

    public List<ActivityEntry> getActivity()
    {
        return activities;
    }

    public List<Participant> getParticipants()
    {
        return participants;
    }
}