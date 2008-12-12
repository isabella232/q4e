package org.apache.maven.embedder.test;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import junit.framework.TestCase;

public class ClassloaderTestCase
    extends TestCase
{
    public void testAccessMavenArtifact()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.artifact.Artifact" );
        assertClassExists( "org.apache.maven.artifact.repository.ArtifactRepository" );
    }

    public void testAccessMavenCore()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.DefaultMaven" );
        assertClassExists( "org.apache.maven.execution.BuildFailure" );
    }

    public void testAccessMavenLifecycle()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.lifecycle.LifecycleUtils" );
        assertClassExists( "org.apache.maven.lifecycle.model.Phase" );
    }

    public void testAccessMavenModel()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.model.Model" );
        assertClassExists( "org.apache.maven.model.Dependency" );
    }

    public void testAccessMavenPluginApi()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.plugin.Mojo" );
        assertClassExists( "org.apache.maven.plugin.descriptor.PluginDescriptor" );
    }

    public void testAccessMavenProfile()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.profiles.Activation" );
        assertClassExists( "org.apache.maven.profiles.Profile" );
    }

    public void testAccessMavenProject()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.project.MavenProject" );
        assertClassExists( "org.apache.maven.project.artifact.AttachedArtifact" );
    }

    public void testAccessMavenReportingApi()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.reporting.MavenReport" );
    }

    public void testAccessMavenToolchain()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.toolchain.DefaultToolchain" );
        assertClassExists( "org.apache.maven.toolchain.Toolchain" );
    }

    public void testAccessMavenWorkspace()
        throws ClassNotFoundException
    {
        assertClassExists( "org.apache.maven.workspace.DefaultMavenWorkspaceStore" );
    }

    /**
     * Test for resource access to the plexus.xml file. Should be only one, at
     * <code>"/org.apache.maven.core/META-INF/plexus/plexus.xml"</code>
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void testResourcePlexusXml()
        throws IOException, ClassNotFoundException
    {
        String RESNAME = "META-INF/plexus/plexus.xml";

        // Load a core class
        Class<?> clazz = Class.forName( "org.apache.maven.DefaultMaven" );

        Enumeration<URL> xmlEnum = clazz.getClassLoader().getResources( RESNAME );
        assertNotNull( "ClassLoader.getResources(" + RESNAME + ") should not be null", xmlEnum );
        List<URL> hits = Collections.list( xmlEnum );
        assertEquals( "Hits on: " + RESNAME, 1, hits.size() );
    }

    /**
     * Test for resource access to the various component.xml files. Should find 13 of them, at
     * <code>"META-INF/plexus/components.xml"</code>
     * 
     * <pre>
     * $ find . -name &quot;components.xml&quot;
     * ./org.apache.maven.artifact/META-INF/plexus/components.xml
     * ./org.apache.maven.core/META-INF/plexus/components.xml
     * ./org.apache.maven.embedder/META-INF/plexus/components.xml
     * ./org.apache.maven.profile/META-INF/plexus/components.xml
     * ./org.apache.maven.project/META-INF/plexus/components.xml
     * ./org.apache.maven.toolchain/META-INF/plexus/components.xml
     * ./org.apache.maven.wagon.providers.file/META-INF/plexus/components.xml
     * ./org.apache.maven.wagon.providers.http.lightweight/META-INF/plexus/components.xml
     * ./org.apache.maven.wagon.providers.ssh.common/META-INF/plexus/components.xml
     * ./org.apache.maven.wagon.providers.ssh.external/META-INF/plexus/components.xml
     * ./org.apache.maven.wagon.providers.ssh.jsch/META-INF/plexus/components.xml
     * ./org.apache.maven.workspace/META-INF/plexus/components.xml
     * ./org.codehaus.plexus.interactivity.api/META-INF/plexus/components.xml
     * </pre>
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void testResourceComponentXml()
        throws IOException, ClassNotFoundException
    {
        String RESNAME = "META-INF/plexus/components.xml";

        /* DOESN'T WORK - only finds the 1 META-INF/plexus/components.xml found within the
         * org.apache.maven.core plugin, not the other ones.
         * Class<?> clazz = Class.forName( "org.apache.maven.DefaultMaven" );
         */
        Class<?> clazz = this.getClass();

        Enumeration<URL> xmlEnum = clazz.getClassLoader().getResources( RESNAME );
        assertNotNull( "ClassLoader.getResources(" + RESNAME + ") should not be null", xmlEnum );
        List<URL> hits = Collections.list( xmlEnum );
        assertEquals( "Hits on: " + RESNAME, 13, hits.size() );
    }

    private void assertClassExists( String className )
        throws ClassNotFoundException
    {
        Class<?> clazz = Class.forName( className );
        assertNotNull( "Class should exist: " + className, clazz );
    }
}
