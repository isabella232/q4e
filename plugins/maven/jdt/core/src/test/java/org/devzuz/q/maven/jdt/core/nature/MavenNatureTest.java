package org.devzuz.q.maven.jdt.core.nature;

import java.io.File;

import junit.framework.TestCase;

public class MavenNatureTest
    extends TestCase
{

    private MavenNature nature = new MavenNature();

    protected void setUp()
        throws Exception
    {
        super.setUp();
    }

    public void testGetRelativePath()
    {
        File basedir = new File( "c:\\test" );
        String fullPath = basedir.getAbsolutePath();
        String relativePath = nature.getRelativePath( basedir, fullPath );

        assertEquals( ".", relativePath );

        basedir = new File( "c:\\test" );
        fullPath = basedir.getAbsolutePath() + "\\a\\b";
        relativePath = nature.getRelativePath( basedir, fullPath );

        assertEquals( "a/b", relativePath );
    }

}
