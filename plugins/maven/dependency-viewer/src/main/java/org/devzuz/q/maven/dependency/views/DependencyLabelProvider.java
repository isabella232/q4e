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
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * TODO Document
 * 
 * @author Abel Mui�o <amuino@gmail.com>
 */
public class DependencyLabelProvider
    extends LabelProvider
    implements IEntityStyleProvider, IEntityConnectionStyleProvider, IFontProvider
{
    private static final Color TEST_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 200, 200, 100 );

    private static final Color PROJECT_COLOR = new Color( Display.getCurrent(), 128, 255, 128 );

    private static final Color DIRECT_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 0xbb, 0xbb, 0xdd );

    private static final Color TRANSITIVE_DEPENDENCY_COLOR = new Color( Display.getCurrent(), 0xcc, 0xcc, 0xcc );

    private static final Font NODE_FONT;

    static
    {
        FontData systemFontData = Display.getCurrent().getSystemFont().getFontData()[0];
        NODE_FONT =
            new Font( Display.getCurrent(), new FontData( systemFontData.getName(), ( systemFontData.getHeight() - 1 ),
                                                          systemFontData.getStyle() ) );
    }

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

    @Override
    public String getText( Object element )
    {
        if ( element instanceof IMavenProject )
        {
            return ( (IMavenProject) element ).getArtifactId();
        }
        else if ( element instanceof IMavenArtifact )
        {
            IMavenArtifact artifact = ( (IMavenArtifact) element );
            return artifact.getArtifactId() + "\n" + artifact.getVersion();
        }
        else
        {
            // an edge
            // EntityConnectionData connection = (EntityConnectionData) element;
            // IMavenArtifact destination = (IMavenArtifact) connection.dest;
            // return destination.getVersion();
            return null;
        }
    }

    public boolean fisheyeNode( Object entity )
    {
        return true;
    }

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
            if ( "test".equals( a.getScope() ) )
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

    public Color getBorderColor( Object entity )
    {
        return null;
    }

    public Color getBorderHighlightColor( Object entity )
    {
        return null;
    }

    public int getBorderWidth( Object entity )
    {
        return -1;
    }

    public Color getForegroundColour( Object entity )
    {
        return null;
    }

    public Color getNodeHighlightColor( Object entity )
    {
        return null;
    }

    public IFigure getTooltip( Object entity )
    {
        return null;
    }

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

    public int getConnectionStyle( Object src, Object dest )
    {
        return ZestStyles.CONNECTIONS_SOLID;
    }

    public Color getHighlightColor( Object src, Object dest )
    {
        return null;
    }

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

    // ---------------------
    // IFontProvider
    //
    public Font getFont( Object element )
    {
        return NODE_FONT;
    }
}