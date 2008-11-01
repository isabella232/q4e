/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.Severity;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class MavenViewSeverityFilter extends ViewerFilter
{

    private Severity severity;

    public MavenViewSeverityFilter()
    {
    }

    public void setSeverity( Severity severity )
    {
        this.severity = severity;
    }

    public Severity getSeverity()
    {
        return severity;
    }

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element )
    {
        return select( (IMavenEvent) element );
    }

    public boolean select( IMavenEvent event )
    {
        if ( severity == null )
        {
            return true;
        }

        return event.getSeverity().compareTo( severity ) >= 0;
    }
}
