package org.devzuz.q.maven.embedder.internal;

import java.io.File;

import junit.framework.TestCase;

import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.embedder.test.EclipseMavenForTesting;

public class EclipseMavenTest extends TestCase
{
    private static final String TEST_DIR = "test";

    private EclipseMavenForTesting mavenInstance;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        mavenInstance = new EclipseMavenForTesting();
        mavenInstance.start();
        Activator activator = new Activator();
        activator.setMavenInstance( mavenInstance );
    }

    @Override
    protected void tearDown() throws Exception
    {
        mavenInstance.stop();
        super.tearDown();
    }

    public void testReadPomWithDistributionStatus() throws Exception
    {
        File testPom = new File( "test/pom-with-distribution-status.xml" );
        mavenInstance.getMavenProject( testPom, true );
        mavenInstance.getMavenProject( testPom, false );
    }

}
