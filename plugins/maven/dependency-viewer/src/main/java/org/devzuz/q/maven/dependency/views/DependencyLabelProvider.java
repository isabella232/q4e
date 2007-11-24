/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.dependency.views;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * TODO Document
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class DependencyLabelProvider extends LabelProvider
    implements IEntityStyleProvider, IEntityConnectionStyleProvider
{
    private static final Color TEST_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 200, 200, 100 );

    private static final Color PROJECT_COLOR = new Color( Display.getCurrent(), 128, 255, 128 );

    private static final Color DIRECT_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 0xbb, 0xbb, 0xdd );

    private static final Color TRANSITIVE_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 0xcc, 0xcc, 0xcc );

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage( Object element )
    {
        // if ( element instanceof IMavenProject )
        // {
        // return projectImage;
        // }
        // else if ( element instanceof IMavenArtifact )
        // {
        // return dependencyImage;
        // }
        // else
        // {
        // // an edge
        // return null;
        // }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText( Object element )
    {
        if ( element instanceof IMavenProject )
        {
            return ( (IMavenProject) element ).getArtifactId();
        }
        else if ( element instanceof IMavenArtifact )
        {
            return ( (IMavenArtifact) element ).getArtifactId();
        }
        else
        {
            // an edge
            EntityConnectionData connection = (EntityConnectionData) element;
            IMavenArtifact destination = (IMavenArtifact) connection.dest;
            return destination.getVersion();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#fisheyeNode(java.lang.Object)
     */
    public boolean fisheyeNode( Object entity )
    {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBackgroundColour(java.lang.Object)
     */
    public Color getBackgroundColour( Object entity )
    {
        if ( entity instanceof IMavenProject )
        {
            return PROJECT_COLOR;
        }
        else
        {
            IMavenArtifact a = (IMavenArtifact) entity;
            // XXX just for demonstration
            if ( a.getScope().equals( "test" ) )
            {
                return TEST_DEPENDENCY_COLOR;
            }
            else
            {
                // default
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderColor(java.lang.Object)
     */
    public Color getBorderColor( Object entity )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderHighlightColor(java.lang.Object)
     */
    public Color getBorderHighlightColor( Object entity )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderWidth(java.lang.Object)
     */
    public int getBorderWidth( Object entity )
    {
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getForegroundColour(java.lang.Object)
     */
    public Color getForegroundColour( Object entity )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getNodeHighlightColor(java.lang.Object)
     */
    public Color getNodeHighlightColor( Object entity )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getTooltip(java.lang.Object)
     */
    public IFigure getTooltip( Object entity )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider#getColor(java.lang.Object, java.lang.Object)
     */
    public Color getColor( Object src, Object dest )
    {
        if ( src instanceof IMavenProject )
        {
            // highlight non-transitive dependencies
            return DIRECT_DEPENDENCY_COLOR;
        }
        else
        {
            return TRANSITIVE_DEPENDENCY_COLOR;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider#getConnectionStyle(java.lang.Object,
     *      java.lang.Object)
     */
    public int getConnectionStyle( Object src, Object dest )
    {
        return ZestStyles.CONNECTIONS_SOLID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider#getHighlightColor(java.lang.Object,
     *      java.lang.Object)
     */
    public Color getHighlightColor( Object src, Object dest )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider#getLineWidth(java.lang.Object,
     *      java.lang.Object)
     */
    public int getLineWidth( Object src, Object dest )
    {
        if ( src instanceof IMavenProject )
        {
            // highlight non-transitive dependencies
            return 2;
        }
        else
        {
            return 1;
        }
    }
}