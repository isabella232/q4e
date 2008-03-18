/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.analysis.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * Comparator for the tables. Allows column sort order to be changed at runtime
 * 
 * @author jake pezaro
 */
public abstract class ColumnComparator
    extends ViewerComparator
{

    protected Column column;

    private boolean direction;

    public ColumnComparator( Column defaultSortColumn, boolean defaultDirection )
    {
        super();
        column = defaultSortColumn;
        direction = defaultDirection;
    }

    public void selectColumn( Column column )
    {
        if ( this.column.equals( column ) )
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

    /**
     * combine the provided data object with the selected column and return a Comparable object
     */
    protected abstract Comparable getComparable( Object o );

}
