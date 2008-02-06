/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import org.devzuz.q.maven.dependency.analysis.model.Version;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * Comparator for the Versions table. Allows column sort order to be changed at runtime
 * 
 * @author jake pezaro
 */
public class VersionListComparator
    extends ViewerComparator
{

    private Column column;

    private boolean direction;

    public VersionListComparator()
    {
        super();
        column = Column.GROUPID;
        direction = true;
    }

    public void selectColumn( Column column )
    {
        if ( this.column.equals(column) )
        {
            direction = !direction;
        }
        else
        {
            this.column = column;
        }
    }

    @Override
    public int compare( Viewer viewer, Object e1, Object e2 )
    {
        Comparable comparable1 = getComparable( e1 );
        int compare = 0;
        if ( comparable1 instanceof String )
        {
            String string1 = (String) comparable1;
            compare = string1.compareToIgnoreCase( ( getComparable( e2 ).toString() ) );
        }
        else
        {
            compare = comparable1.compareTo( getComparable( e2 ) );
        }
        if ( direction )
        {
            compare = compare * -1;
        }
        return compare;
    }

    private Comparable getComparable( Object o )
    {
        Version version = (Version) o;
        switch ( column )
        {
            case GROUPID:
                return version.getGroupId();
            case ARTIFACTID:
                return version.getArtifactId();
            case VERSION:
                return version.getVersion();
            case INSTANCES:
                return new Integer( version.getInstances().size() );
            default:
                // never can happen
                throw new RuntimeException( "Unrecognised column " + column );
        }
    }

}
