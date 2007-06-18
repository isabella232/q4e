/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.core.model;

import java.util.Date;

public class ActivityEntry
{

    private Date activityDate;

    private String description;

    private String link;

    private String source;

    public Date getActivityDate()
    {
        return activityDate;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLink()
    {
        return link;
    }

    public String getSource()
    {
        return source;
    }

    public void setActivityDate( Date activityDate )
    {
        this.activityDate = activityDate;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public void setLink( String link )
    {
        this.link = link;
    }

    public void setSource( String source )
    {
        this.source = source;
    }
}
