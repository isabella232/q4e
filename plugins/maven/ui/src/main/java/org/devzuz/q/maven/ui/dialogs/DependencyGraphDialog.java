/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.io.InputStream;

import javax.swing.JPanel;

import org.devzuz.q.maven.ui.Activator;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.NeighborHighlightControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.force.Force;
import prefuse.util.force.ForceSimulator;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

public class DependencyGraphDialog extends AbstractResizableDialog
{
    private GraphView graphView;
    private Panel panel;
    private InputStream inputStream;
    private String      renderLabel;
    /*
    private static DependencyGraphDialog dependencyGraphDialog = null;
    public static synchronized DependencyGraphDialog getDependencyGraphDialog(  ) throws DataIOException
    {
        if ( dependencyGraphDialog == null )
        {
            dependencyGraphDialog = new DependencyGraphDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }
        
        dependencyGraphDialog.setGraphInputStream( null );

        return dependencyGraphDialog;
    }*/
    
    public DependencyGraphDialog( Shell parentShell , InputStream is , String renderLabel )
    {
        super( parentShell );       
        inputStream = is;
        this.renderLabel = renderLabel;
    }

    public DependencyGraphDialog( IShellProvider parentShell , InputStream is , String renderLabel )
    {
        super( parentShell );
        inputStream = is;
        this.renderLabel = renderLabel;
    }
    
    @Override
    protected Preferences getDialogPreferences()
    {
        return Activator.getDefault().getPluginPreferences();
    }

    @Override
    protected Control internalCreateDialogArea( Composite parent )
    {
        parent.setLayout( new GridLayout( 2, false ) );
        
        getShell().addControlListener(  new ControlAdapter()
        {
            public void controlResized(ControlEvent e)
            {
                Dimension pt = panel.getSize();
                if( (pt.width > 0) && (pt.height > 0) )
                    graphView.setViewSize( pt.width, pt.height );
            }
        });
        
        Composite composite = new Composite( parent , SWT.EMBEDDED );
        composite.setLayoutData( new GridData( GridData.FILL_BOTH) );
        composite.setSize( 700, 700 );
        
        Frame displayFrame = SWT_AWT.new_Frame( composite );
        displayFrame.setLayout( new BorderLayout() );
        
        panel = new Panel(new BorderLayout());
        displayFrame.add( panel , BorderLayout.CENTER );
        
        // graphView = new GraphView( getGraph() , "name" );
        try
        {
            graphView = new GraphView( getGraph( inputStream ) , renderLabel );
        }
        catch( DataIOException e )
        {
            
        }
        
        panel.add( graphView , BorderLayout.CENTER );
        
        Composite controlGroup = new Composite( parent , SWT.NONE );
        controlGroup.setLayoutData( new GridData( SWT.LEFT , SWT.TOP , false , false ) );
        controlGroup.setLayout( new RowLayout( SWT.VERTICAL ) );
        
        generatePhysicsControls( controlGroup , graphView.getForceSimulator() );
        
        return parent;
    }
    
    public class GraphView extends JPanel
    {

        private static final String graph = "graph";

        private static final String nodes = "graph.nodes";

        private static final String edges = "graph.edges";

        private Visualization m_vis;
        
        private Display display;
        
        private ForceSimulator fsim; 

        public GraphView( Graph g, String label )
        {
            // create a new, empty visualization for our data
            m_vis = new Visualization();

            // set up the renderers
            LabelRenderer tr = new LabelRenderer();
            tr.setRoundedCorner( 8, 8 );
            m_vis.setRendererFactory( new DefaultRendererFactory( tr ) );

            // register the data with a visualization
            // adds graph to visualization and sets renderer label field
            setGraph( g, label );

            // fix selected focus nodes
            TupleSet focusGroup = m_vis.getGroup( Visualization.FOCUS_ITEMS );
            focusGroup.addTupleSetListener( new TupleSetListener()
            {
                public void tupleSetChanged( TupleSet ts, Tuple[] add, Tuple[] rem )
                {
                    for ( int i = 0; i < rem.length; ++i )
                        ( (VisualItem) rem[i] ).setFixed( false );
                    for ( int i = 0; i < add.length; ++i )
                    {
                        ( (VisualItem) add[i] ).setFixed( false );
                        ( (VisualItem) add[i] ).setFixed( true );
                    }
                    if ( ts.getTupleCount() == 0 )
                    {
                        ts.addTuple( rem[0] );
                        ( (VisualItem) rem[0] ).setFixed( false );
                    }
                    m_vis.run( "draw" );
                }
            } );

            // create actions to process the visual data
            int hops = 30;
            final GraphDistanceFilter filter = new GraphDistanceFilter( graph, hops );

            ColorAction fill = new ColorAction( nodes, VisualItem.FILLCOLOR, ColorLib.rgb( 200, 200, 255 ) );
            fill.add( VisualItem.FIXED, ColorLib.rgb( 255, 100, 100 ) );
            fill.add( VisualItem.HIGHLIGHT, ColorLib.rgb( 255, 200, 125 ) );

            ActionList draw = new ActionList();
            draw.add( filter );
            draw.add( fill );
            draw.add( new ColorAction( nodes, VisualItem.STROKECOLOR, 0 ) );
            draw.add( new ColorAction( nodes, VisualItem.TEXTCOLOR, ColorLib.rgb( 0, 0, 0 ) ) );
            draw.add( new ColorAction( edges, VisualItem.FILLCOLOR, ColorLib.gray( 200 ) ) );
            draw.add( new ColorAction( edges, VisualItem.STROKECOLOR, ColorLib.gray( 200 ) ) );

            ActionList animate = new ActionList( Activity.INFINITY );
            animate.add( new ForceDirectedLayout( graph ) );
            animate.add( fill );
            animate.add( new RepaintAction() );

            // finally, we register our ActionList with the Visualization.
            // we can later execute our Actions by invoking a method on our
            // Visualization, using the name we've chosen below.
            m_vis.putAction( "draw", draw );
            m_vis.putAction( "layout", animate );

            m_vis.runAfter( "draw", "layout" );

            // set up a display to show the visualization
            display = new Display( m_vis );
            display.setSize( 700, 700 );
            display.pan( 350, 350 );
            display.setForeground( Color.GRAY );
            display.setBackground( Color.WHITE );

            // main display controls
            display.addControlListener( new FocusControl( 1 ) );
            display.addControlListener( new DragControl() );
            display.addControlListener( new PanControl() );
            display.addControlListener( new ZoomControl() );
            display.addControlListener( new WheelZoomControl() );
            display.addControlListener( new ZoomToFitControl() );
            display.addControlListener( new NeighborHighlightControl() );

            display.setForeground( Color.GRAY );
            display.setBackground( Color.WHITE );

            // launch the visualization

            // create a panel for editing force values
            fsim = ( (ForceDirectedLayout) animate.get( 0 ) ).getForceSimulator();

            // now we run our action list
            m_vis.run( "draw" );

            add( display );
        }

        public void setViewSize( int x, int y )
        {
            display.setSize( x, y );
            m_vis.run( "draw" );
            display.invalidate();
        }
        
        public ForceSimulator getForceSimulator()
        {
            return fsim;
        }

        public void setGraph( Graph g, String label )
        {
            // update labeling
            DefaultRendererFactory drf = (DefaultRendererFactory) m_vis.getRendererFactory();
            ( (LabelRenderer) drf.getDefaultRenderer() ).setTextField( label );

            // update graph
            m_vis.removeGroup( graph );
            VisualGraph vg = m_vis.addGraph( graph, g );
            m_vis.setValue( edges, null, VisualItem.INTERACTIVE, Boolean.FALSE );
            VisualItem f = (VisualItem) vg.getNode( 0 );
            m_vis.getGroup( Visualization.FOCUS_ITEMS ).setTuple( f );
            f.setFixed( false );
        }
    }
    
    public Graph getGraph( )
    {
        Graph ret = null;
        
        try
        {
            ret = new GraphMLReader().readGraph("/home/emantos/src-java/prefuse/prefuse-beta/data/socialnet.xml");
        }
        catch( DataIOException e )
        {
            e.printStackTrace();
        }
        
        return ret;
    }
     
    public Graph getGraph( InputStream is ) throws DataIOException
    {
        return new GraphMLReader().readGraph( is );
    }
    
    private static int MIN = 0 , MAX = 100 , INC = 1;
    
    public void generatePhysicsControls( Composite parent , ForceSimulator fsim )
    {
        ForceSimulatorPanelListener forceSimulatorPanelListener = new ForceSimulatorPanelListener();
        Force[] forces = fsim.getForces();
        for ( int i=0; i < forces.length; i++ ) 
        {
            Force force = forces[i];
            String forceName = force.getClass().getName();
            
            Group group = new Group( parent , SWT.NONE );
            group.setLayout( new RowLayout( SWT.VERTICAL ) );
            group.setText( forceName.substring( forceName.lastIndexOf( "." ) + 1 ) );
            
            for ( int j=0; j < force.getParameterCount(); j++ ) 
            {
                double value = force.getParameter( j );
                double min   = force.getMinValue( j );
                double max   = force.getMaxValue( j );
                String name = force.getParameterName( j );
                
                System.out.println("-erle- min(" + min + ") max(" + max + ") value(" + value + ")" );
                
                Label label = new Label( group , SWT.NULL );
                label.setText( name );
                
                Slider slider = new Slider( group , SWT.HORIZONTAL );
                slider.setMinimum( MIN );
                slider.setMaximum( MAX );
                slider.setIncrement( INC );
                slider.setSelection( mapForceValue( value , min , max , MIN , MAX ) );
                slider.addSelectionListener( forceSimulatorPanelListener );
                slider.setData( new ForceCategory( force , j ) );
            }
        }
    }
    
    private class ForceCategory
    {
        private Force force;
        private int category;
        public ForceCategory( Force force , int category )
        {
            this.force = force;
            this.category = category; 
        }
        
        public Force getForce()
        {
            return force;
        }
        
        public int getCategory()
        {
            return category;
        }
    }
    
    private class ForceSimulatorPanelListener 
        implements SelectionListener
    {
        public void widgetDefaultSelected( SelectionEvent e ) 
        {
            widgetSelected( e );
        }
        
        public void widgetSelected( SelectionEvent e )
        {
            Slider slider = (Slider) e.getSource();
            ForceCategory forceCategory = (ForceCategory) slider.getData();
            Force force = forceCategory.getForce(); 
            int category = forceCategory.getCategory();
            int value = slider.getSelection();
            float newValue = mapSliderValue( value , MIN , MAX , force.getMinValue( category ) , force.getMaxValue( category ) );
            System.out.println("-erle- : ForceSimulatorPanelListener.widgetSelected( "+ value +" ==> " + newValue + " )");
            force.setParameter( category , newValue );
        }
    }
    
    private static int mapForceValue( double val , double min , double max , int mapMin , int mapMax )
    {
        return mapMin + (int)(((val - min)/(max - min)) * (mapMax - mapMin));
    }
    
    private static float mapSliderValue( int val , int min , int max , double mapMin , double mapMax )
    {
        return (float) (mapMin + (((double)((val - min) / (max - min))) * (mapMax - mapMin))); 
    }
}
