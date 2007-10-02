package org.devzuz.q.maven.jdt.core.nature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.devzuz.q.maven.jdt.core.classpath.container.MavenClasspathContainer;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

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
    
    public void testContains()
    {
        List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>();
        
        IClasspathEntry mavenContainer = JavaCore.newContainerEntry( new Path( MavenClasspathContainer.MAVEN_CLASSPATH_CONTAINER ) );
        
        classpathEntries.add( JavaCore.newContainerEntry( new Path( "test1" ) ) );
        classpathEntries.add( JavaCore.newContainerEntry( new Path( "test2" ) ) );
        classpathEntries.add( JavaCore.newContainerEntry( new Path( "test3" ) ) );
        
        assertFalse( classpathEntries.contains( mavenContainer ) );
        
        classpathEntries.add(  mavenContainer );
        
        assertTrue( classpathEntries.contains( mavenContainer ) );
    }
}
