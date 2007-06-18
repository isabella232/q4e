/***************************************************************************************************
 * Copyright (c) 2006 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM - Initial API and implementation
 **************************************************************************************************/
package org.devzuz.q.bundle.classpath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.devzuz.q.bundle.classpath.ClasspathComputer3_0.ClasspathElement;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.osgi.framework.Constants;

public class MavenGenerator
{
    private static final String ADAPTER_ACCESS = "#ADAPTER#ACCESS#"; //$NON-NLS-1$

    private MavenState state = null;

    // private MavenProject project = null;
    private BundleDescription bundle = null;

    public static class JarFilter
        implements FileFilter
    {
        private static final String JAR = ".jar";

        public boolean accept( File pathname )
        {
            String name = pathname.getName();
            return JAR.equalsIgnoreCase( name.substring( name.length() - 4, name.length() ) );
        }
    }

    public static final JarFilter jarFilter = new JarFilter();

    public MavenState getState()
    {
        return state;
    }

    public BundleDescription getModel()
    {
        // TODO Auto-generated method stub
        return bundle;
    }

    // public MavenProject getProject() {
    // // TODO Auto-generated method stub
    // return project;
    // }

    /*
     * return an array of extra entries to put on the classpath for the given project
     */
    // public String[] getExtraClasspath(MavenProject jar) {
    // // TODO Auto-generated method stub
    // return null;
    // }
    private List findJars( File dir )
    {
        List jars = new ArrayList();
        process( dir, jars );
        return jars;
    }

    public void process( File input, List results )
    {
        if ( input == null || !input.exists() )
            return;

        File[] files = null;
        if ( input.isDirectory() )
        {
            files = input.listFiles();
        }
        else if ( jarFilter.accept( input ) )
        {
            files = new File[] { input };
        }
        for ( int i = 0; i < files.length; i++ )
        {
            if ( files[i].isDirectory() )
            {
                process( files[i], results );
            }
            else if ( jarFilter.accept( files[i] ) )
            {
                results.add( files[i] );
            }
        }
    }

    private void writeJDTCompilerAdapterArgFile( File file, List classpath )
    {
        if ( classpath.size() == 0 || ( !( classpath.get( 0 ) instanceof ClasspathElement ) ) )
            return;

        Writer writer;
        try
        {
            writer = new BufferedWriter( new FileWriter( file ) );
        }
        catch ( IOException e )
        {
            return;
        }

        try
        {
            for ( Iterator iterator = classpath.iterator(); iterator.hasNext(); )
            {
                ClasspathElement element = (ClasspathElement) iterator.next();
                if ( element.getPath() != null && element.getPath().length() > 0
                    && element.getAccessRules().length() > 0 )
                {
                    String path = element.getPath();
                    // if (path.startsWith(Utils.getPropertyFormat(PROPERTY_BUILD_RESULT_FOLDER))) {
                    // //remove leading ${build.result.folder}/
                    // path = path.substring(Utils.getPropertyFormat(PROPERTY_BUILD_RESULT_FOLDER).length() + 1);
                    // }
                    // remove leading ../../..
                    path = path.replaceFirst( "^(\\.\\.[\\\\/])*", "" ); //$NON-NLS-1$//$NON-NLS-2$
                    if ( writer == null )
                        writer = new BufferedWriter( new FileWriter( file ) );
                    writer.write( ADAPTER_ACCESS + path + element.getAccessRules() + "\n" ); //$NON-NLS-1$
                }
            }
        }
        catch ( IOException e )
        {

        }
        finally
        {
            if ( writer != null )
                try
                {
                    writer.close();
                }
                catch ( IOException e )
                {
                }
        }
    }

    public void generate( File manifest, File bundleLoc, File repo )
    {
        List jars = findJars( repo );

        state = new MavenState();
        for ( Iterator iter = jars.iterator(); iter.hasNext(); )
        {
            File jar = (File) iter.next();
            state.addBundle( jar );
        }
        state.addBundle( manifest, bundleLoc );
        state.resolveState();

        Dictionary props = state.loadManifest( manifest );
        String bundleId = (String) props.get( Constants.BUNDLE_SYMBOLICNAME );
        bundle = state.getResolvedBundle( bundleId );

        if ( bundle != null )
        {
            ClasspathComputer3_0 computer = new ClasspathComputer3_0( this );
            List classpath = null;
            try
            {
                classpath = computer.getClasspath( bundle );
            }
            catch ( CoreException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for ( Iterator iter = classpath.iterator(); iter.hasNext(); )
            {
                ClasspathElement element = (ClasspathElement) iter.next();
            }
        }
    }

    /**
     * 
     * @param args args[0] : the manifest file for the bundle we are building args[1] : the location of the bundle we
     * are building (target/classes?) args[2] : the location in the repo to start looking for bundles to add to the
     * state
     */
    public static void main( String[] args )
    {
        if ( args.length < 3 )
            return;

        MavenGenerator generator = new MavenGenerator();
        generator.generate( new File( args[0] ), new File( args[1] ), new File( args[2] ) );
    }
}
