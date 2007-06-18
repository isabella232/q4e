/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.ui.core.IDependencyVisualRenderer;
import org.devzuz.q.maven.ui.core.IDependencyVisualSource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;
import org.holongate.j2d.IPaintable;
import org.holongate.j2d.J2DCanvas;
import org.holongate.j2d.J2DUtilities;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;

public class MavenDependencyVisualView extends ViewPart
{

    public static final String VIEW_ID = MavenDependencyVisualView.class.getName();

    private J2DCanvas j2dCanvas;

    private final RefreshGenerator refresher;

    private IDependencyVisualRenderer renderer;
    
    private IDependencyVisualSource source;
    
    private PrefusePaintable prefusePaintable;

    public MavenDependencyVisualView()
    {
        prefusePaintable = new PrefusePaintable();
        refresher = new RefreshGenerator();
        new Thread( refresher ).start();
    }

    @Override
    public void createPartControl( Composite parent )
    {
        j2dCanvas = new J2DCanvas( parent, SWT.BORDER, prefusePaintable );
        
        refresher.addRefreshListener( new CanvasRefreshListener( j2dCanvas ) );
    }

    @Override
    public void setFocus()
    {
    }

    public J2DCanvas getControl()
    {
        return j2dCanvas;
    }

    public class PrefusePaintable implements IPaintable
    {

        private Display iPaintableDisplay = null;

        public PrefusePaintable()
        {
            this( null );
        }

        public PrefusePaintable( InputStream model )
        {
            if ( model != null )
                setModelAndInit( model );
        }

        public void setModelAndInit( InputStream model )
        {
            init( model );
        }

        private void init( InputStream model )
        {

            Visualization vis = new Visualization();
            
            renderer.render( model , vis );
            
            // the display and interactive controls -------------------------

            Display d = new Display( vis );
            d.setSize( 720, 500 ); // set display size
            d.pan( 360, 250 );
            
            // drag individual items around
            d.addControlListener( new DragControl() );
            // pan with left-click drag on background
            d.addControlListener( new PanControl() );
            // zoom with right-click drag
            d.addControlListener( new ZoomControl() );

            iPaintableDisplay = d;
        }

        public Rectangle2D getBounds( Control control )
        {
            return J2DUtilities.toRectangle2D( control.getBounds() );
        }

        public void paint( Control control, Graphics2D g2d )
        {
            if ( iPaintableDisplay != null )
            {
                org.eclipse.swt.graphics.Point pt = j2dCanvas.getSize();
                iPaintableDisplay.setSize( pt.x , pt.y );
                iPaintableDisplay.paintComponent( g2d );
            }
        }

        public void redraw( Control control, GC gc )
        {
            J2DCanvas canvas = (J2DCanvas) control;
            Graphics2D g2d = canvas.getGraphics2DFactory().createGraphics2D();
            // iPaintableDisplay.paintComponent( g2d );
            paint( canvas, g2d );
        }
    }

    private class CanvasRefreshListener
        implements RefreshListener
    {
        private J2DCanvas canvas;

        public CanvasRefreshListener( J2DCanvas canvas )
        {
            this.canvas = canvas;
        }

        public void action()
        {
            if ( !canvas.isDisposed() )
            {
                canvas.getDisplay().asyncExec( new Runnable()
                {
                    public void run()
                    {
                        if ( !canvas.isDisposed() )
                            canvas.redraw();
                    }
                } );
            }
        }

    }

    // temporary refresh generator for java2d painting
    // temporary until a better refresh event mechanism is in place
    public class RefreshGenerator
        implements Runnable
    {
        private List<RefreshListener> listeners;

        public RefreshGenerator()
        {
            listeners = new ArrayList<RefreshListener>();
        }

        public void run()
        {
            while ( true )
            {
                if ( !( listeners.size() <= 0 ) )
                {
                    for ( RefreshListener listener : listeners )
                    {
                        listener.action();
                    }
                }
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public void addRefreshListener( RefreshListener listener )
        {
            listeners.add( listener );
        }
    }

    public static interface RefreshListener
    {
        public void action();
    }

    public void setRendererAndSource( IDependencyVisualRenderer renderer, IDependencyVisualSource source )
    {
        this.renderer = renderer;
        this.source = source;
    }
    
    public void setMavenProject( IMavenProject project ) throws CoreException
    {
        prefusePaintable.setModelAndInit( source.getGraphData( project ) );
    }
}
