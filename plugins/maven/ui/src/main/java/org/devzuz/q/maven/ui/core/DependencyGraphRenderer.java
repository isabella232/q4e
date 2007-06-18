package org.devzuz.q.maven.ui.core;

import java.io.InputStream;

import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class DependencyGraphRenderer implements IDependencyVisualRenderer
{
    private static DependencyGraphRenderer dependencyGraphRenderer;
    public static synchronized DependencyGraphRenderer getDependencyGraphRenderer()
    {
        if( dependencyGraphRenderer == null )
            dependencyGraphRenderer = new DependencyGraphRenderer();
        
        return dependencyGraphRenderer;
    }
    
    private DependencyGraphRenderer()
    {
        
    }
    
    public void render( InputStream in, Visualization vis )
    {
        Graph graph = null;
        try
        {

            graph = new GraphMLReader().readGraph( in );
        }
        catch ( DataIOException e )
        {
            e.printStackTrace();
            System.err.println( "Error loading graph. Will puke!" );
            throw new RuntimeException( e );
        }
        
        // -- 2. the visualization --------------------------------------------

        // add the graph to the visualization as the data group "graph"
        // nodes and edges are accessible as "graph.nodes" and "graph.edges"
        vis.add( "graph", graph );
        vis.setInteractive( "graph.edges", null, false );

        // -- 3. the renderers and renderer factory ---------------------------

        // draw the "name" label for NodeItems
        LabelRenderer r = new LabelRenderer( "artifactId" );
        r.setRoundedCorner( 12, 12 ); // round the corners
        vis.setRendererFactory( new DefaultRendererFactory( r ) );
        
        // -- 4. the processing actions ---------------------------------------

        // create our nominal color palette
        // pink for females, baby blue for males
        int[] palette = new int[] { ColorLib.rgb( 255, 180, 180 ), ColorLib.rgb( 190, 190, 255 ) };
        // map nominal data values to colors using our provided palette
        DataColorAction fill = new DataColorAction( "graph.nodes", "groupId", Constants.NOMINAL,
                                                    VisualItem.FILLCOLOR, palette );
        // use black for node text
        ColorAction text = new ColorAction( "graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray( 0 ) );
        // use light grey for edges
        ColorAction edges = new ColorAction( "graph.edges", VisualItem.STROKECOLOR, ColorLib.gray( 200 ) );
        
        // create an action list containing all color assignments
        ActionList color = new ActionList();
        color.add( fill );
        color.add( text );
        color.add( edges );

        // create an action list with an animated layout
        ActionList layout = new ActionList( Activity.INFINITY );
        layout.add( new ForceDirectedLayout( "graph" ) );
        layout.add( fill );
        layout.add( new RepaintAction() );

        // add the actions to the visualization
        vis.putAction( "color", color );
        vis.putAction( "layout", layout );
        
        // assign the colors
        vis.run( "color" );
        // start up the animated layout
        vis.run( "layout" );
    }
}
