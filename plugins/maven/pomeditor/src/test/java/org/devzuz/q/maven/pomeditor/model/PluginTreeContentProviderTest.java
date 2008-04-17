package org.devzuz.q.maven.pomeditor.model;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PluginTreeContentProviderTest
{
    Model pomModel;
    PluginTreeContentProvider provider;
    List<Plugin> plugins = null;
    
    @SuppressWarnings ("unchecked")
    @Before
    public void setUp() throws Exception
    {
        try
        {
            //this.pomModel = new MavenXpp3Reader().read( new FileReader( "../resources/pom.xml" ) );
            URL url = getClass().getResource("pom.xml");
            this.pomModel = new MavenXpp3Reader().read( new FileReader( url.getFile() ) );
            plugins = pomModel.getBuild().getPlugins();
            
            assertTrue( plugins != null );
            
            provider = new PluginTreeContentProvider( plugins );
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
    
    @SuppressWarnings ("unchecked")
    @Test
    public void testSamplePom()
    {
        Object[] childrenPlugins = provider.getChildren( plugins );
        assertTrue( childrenPlugins.length == 2 );
        
        assertTrue( childrenPlugins[0] instanceof Plugin );
        
        Plugin plugin1 = ( Plugin ) childrenPlugins[0];
        
        Object[] plugin1Children = provider.getChildren( plugin1 );
        
        assertTrue( plugin1Children.length == 1 );
        
        assertTrue( ((Plugin) childrenPlugins[0]).getConfiguration() instanceof Xpp3Dom );
        
        Xpp3Dom dom = (Xpp3Dom) ((Plugin) childrenPlugins[0]).getConfiguration();
        
        System.out.println("children count = " + dom.getChildCount() );
        
        
        assertTrue( childrenPlugins[1] instanceof Plugin );
        
        Plugin plugin2 = (Plugin) childrenPlugins[1];
        
        assertTrue( plugin2.getExecutions().size() == 1 );
        
        assertTrue( plugin2.getConfiguration() == null );
        
        assertTrue( plugin2.getDependencies().size() == 0 );
        
        Object[] plugin2Children = provider.getChildren( plugin2 );
        
        System.out.println("Count = " + plugin2Children.length );
        
        assertTrue( plugin2Children[0] instanceof List );
        
        PluginExecution pluginExecution = ( (List<PluginExecution>) plugin2Children[0] ).get( 0 );
        
        Object[] executionChildren = provider.getChildren( pluginExecution );
        
        assertTrue( executionChildren.length == 2 );
    }
    
    @Test
    public void testParentChild()
    {
        checkNode( plugins );
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
    
    @Test
    public void testLabelProvider()
    {
        showName( plugins , new PluginTreeLabelProvider() );
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

    @After
    public void tearDown() throws Exception
    {
    }
}
