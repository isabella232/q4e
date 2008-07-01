package org.devzuz.q.maven.pomeditor.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.model.Model;
import org.apache.maven.model.ReportPlugin;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class ConfigurationTreeContentProviderTest
    extends TestCase
{

    private Model pomModel;
    
    private ConfigurationTreeContentProvider provider;

    private List<ReportPlugin> pluginList;

    private Xpp3Dom dom;

    public ConfigurationTreeContentProviderTest( String name )
    {
        super( name );
    }

    protected void setUp()
        throws Exception
    {
        try
        {
            //this.pomModel = new MavenXpp3Reader().read( new FileReader( "../resources/pom.xml" ) );
            URL url = getClass().getResource("pom.xml");
            this.pomModel = new MavenXpp3Reader().read( new FileReader( url.getFile() ) );
            
            pluginList = pomModel.getReporting().getPlugins();
            
            dom = ( Xpp3Dom )( ( ReportPlugin )pluginList.get( 0 ) ).getConfiguration();
            
            assertTrue( dom != null );
            
            provider = new ConfigurationTreeContentProvider( dom );
        }
        catch ( FileNotFoundException e )
        {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        catch ( XmlPullParserException e )
        {
            e.printStackTrace();
        }
    }
    
    public void testLabelProvider()
    {
        showName( dom , new PluginTreeLabelProvider() );
    }
    
    public void showName( Object parent , PluginTreeLabelProvider labelProvider )
    {
        System.out.println("Label = " + labelProvider.getText( parent ) );
        Object[] childrenPlugins = provider.getChildren( parent );
        if ( childrenPlugins != null )
        {
            for ( Object child : childrenPlugins )
            {
                showName( child , labelProvider );
            }
        }
    }
    
    public void testParentChild()
    {
        checkNode( dom );
    }
    
    public void checkNode( Object parent )
    {
        Object[] childrenPlugins = provider.getChildren( parent );
        if ( childrenPlugins != null )
        {
            for ( Object child : childrenPlugins )
            {
                assertTrue( "Checking " + child.toString(), provider.getParent( child ) == parent );
                checkNode( child );
            }
        }
    }
    
    public void testXpp3Dom()
    {
        Xpp3Dom configDom = ( Xpp3Dom )( ( ReportPlugin )pluginList.get( 0 ) ).getConfiguration();
        assertTrue ( "configDom != null", configDom != null );
        assertTrue ( "configDom.getParent() == null" , configDom.getParent() == null );
        printXpp3DomRecursive( configDom , 0 );
    }
    
    private void printXpp3DomRecursive( Xpp3Dom dom , int gen )
    {
        String value = dom.getValue() == null ? "null" : dom.getValue();
        System.out.printf( repeat( '\t', gen ) + "{ " + dom.getName() + " , " + value + " }\n" );
        if ( dom.getChildCount() > 0 )
        {
            for ( Xpp3Dom child : dom.getChildren() )
            {
                printXpp3DomRecursive( child, gen + 1 );
            }
        }
    }
    
    private String repeat( char ch , int times )
    {
        StringBuffer buffer = new StringBuffer("");
        for( int i = 0 ; i < times; i++ )
        {
            buffer.append( ch );
        }
        
        return buffer.toString();
    }

    protected void tearDown()
        throws Exception
    {
        super.tearDown();
    }

}
