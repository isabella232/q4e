/***************************************************************************************************
 * Copyright (c) 2006 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM - Initial API and implementation
 **************************************************************************************************/
package org.devzuz.q.bundle.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.osgi.service.resolver.State;
import org.eclipse.osgi.service.resolver.StateHelper;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

public class MavenState
{
    // Filter properties
    public final static String OSGI_WS = "osgi.ws"; //$NON-NLS-1$

    public final static String OSGI_OS = "osgi.os"; //$NON-NLS-1$

    public final static String OSGI_ARCH = "osgi.arch"; //$NON-NLS-1$

    public final static String OSGI_NL = "osgi.nl"; //$NON-NLS-1$

    public final static String ANY = "*"; //$NON-NLS-1$

    private static final String PROFILE_EXTENSION = ".profile"; //$NON-NLS-1$

    public final static String SYSTEM_PACKAGES = "org.osgi.framework.system.packages"; //$NON-NLS-1$

    private StateObjectFactory factory = StateObjectFactory.defaultFactory;

    private State state;

    private long id = 0;

    private Map bundleClasspaths;

    private Map patchBundles;

    public static BundleDescription[] getDependentBundles( BundleDescription root )
    {
        if ( root == null )
            return new BundleDescription[0];
        BundleDescription[] imported = getImportedBundles( root );
        BundleDescription[] required = getRequiredBundles( root );
        BundleDescription[] dependents = new BundleDescription[imported.length + required.length];
        System.arraycopy( imported, 0, dependents, 0, imported.length );
        System.arraycopy( required, 0, dependents, imported.length, required.length );
        return dependents;
    }

    public static BundleDescription[] getImportedBundles( BundleDescription root )
    {
        if ( root == null )
            return new BundleDescription[0];
        ExportPackageDescription[] packages = root.getResolvedImports();
        ArrayList resolvedImports = new ArrayList( packages.length );
        for ( int i = 0; i < packages.length; i++ )
            if ( !root.getLocation().equals( packages[i].getExporter().getLocation() )
                && !resolvedImports.contains( packages[i].getExporter() ) )
                resolvedImports.add( packages[i].getExporter() );
        return (BundleDescription[]) resolvedImports.toArray( new BundleDescription[resolvedImports.size()] );
    }

    public static BundleDescription[] getRequiredBundles( BundleDescription root )
    {
        if ( root == null )
            return new BundleDescription[0];
        return root.getResolvedRequires();
    }

    public MavenState()
    {
        bundleClasspaths = new HashMap();
        patchBundles = new HashMap();
        state = factory.createState( true );
    }

    private long getNextId()
    {
        return ++id;
    }

    public boolean addBundle( File bundleLocation )
    {
        if ( bundleLocation == null || !bundleLocation.exists() )
            return false;
        Dictionary manifest = loadManifest( bundleLocation );
        if ( manifest == null )
            return false;
        return addBundle( manifest, bundleLocation );
    }

    public boolean addBundle( File manifestLocation, File bundleLocation )
    {
        if ( bundleLocation == null || !bundleLocation.exists() )
            return false;
        Dictionary manifest = loadManifest( manifestLocation );
        if ( manifest == null )
            return false;
        return addBundle( manifest, bundleLocation );
    }

    public Dictionary loadManifest( File bundleLocation )
    {
        Dictionary manifest = basicLoadManifest( bundleLocation );
        if ( manifest == null )
            return null;

        // enforce symbolic name
        if ( manifest.get( Constants.BUNDLE_SYMBOLICNAME ) == null )
        {
            // TODO maybe derive symbolic name from artifactId/groupId if we have them?
            return null;
        }

        // enforce bundle classpath
        if ( manifest.get( Constants.BUNDLE_CLASSPATH ) == null )
        {
            manifest.put( Constants.BUNDLE_CLASSPATH, "." ); //$NON-NLS-1$
        }

        return manifest;
    }

    // Return a dictionary representing a manifest. The data may result from plugin.xml conversion
    private Dictionary basicLoadManifest( File bundleLocation )
    {
        InputStream manifestStream = null;
        ZipFile jarFile = null;
        try
        {
            if ( bundleLocation.isFile() )
            {
                String name = bundleLocation.getName();
                if ( ".jar".equalsIgnoreCase( name.substring( name.length() - 4, name.length() ) ) )
                {
                    jarFile = new ZipFile( bundleLocation, ZipFile.OPEN_READ );
                    ZipEntry manifestEntry = jarFile.getEntry( JarFile.MANIFEST_NAME );
                    if ( manifestEntry != null )
                    {
                        manifestStream = jarFile.getInputStream( manifestEntry );
                    }
                }
                else
                {
                    manifestStream = new FileInputStream( bundleLocation );
                }
            }
            else
            {
                manifestStream = new FileInputStream( new File( bundleLocation, JarFile.MANIFEST_NAME ) );
            }
        }
        catch ( IOException e )
        {
            // ignore
        }

        Dictionary manifest = null;

        // It is not a manifest, but a plugin or a fragment
        // if (manifestStream == null) {
        // manifest = convertPluginManifest(bundleLocation, true);
        // if (manifest == null)
        // return null;
        // }

        if ( manifestStream != null )
        {
            try
            {
                Manifest m = new Manifest( manifestStream );
                manifest = manifestToProperties( m.getMainAttributes() );
            }
            catch ( IOException ioe )
            {
                return null;
            }
            finally
            {
                try
                {
                    manifestStream.close();
                }
                catch ( IOException e1 )
                {
                    // Ignore
                }
                try
                {
                    if ( jarFile != null )
                        jarFile.close();
                }
                catch ( IOException e2 )
                {
                    // Ignore
                }
            }
        }
        return manifest;
    }

    private Properties manifestToProperties( Attributes d )
    {
        Iterator iter = d.keySet().iterator();
        Properties result = new Properties();
        while ( iter.hasNext() )
        {
            Attributes.Name key = (Attributes.Name) iter.next();
            result.put( key.toString(), d.get( key ) );
        }
        return result;
    }

    private String[] getClasspath( Dictionary manifest )
    {
        String fullClasspath = (String) manifest.get( Constants.BUNDLE_CLASSPATH );
        String[] result = new String[0];
        try
        {
            if ( fullClasspath != null )
            {
                ManifestElement[] classpathEntries;
                classpathEntries = ManifestElement.parseHeader( Constants.BUNDLE_CLASSPATH, fullClasspath );
                result = new String[classpathEntries.length];
                for ( int i = 0; i < classpathEntries.length; i++ )
                {
                    result[i] = classpathEntries[i].getValue();
                }
            }
        }
        catch ( BundleException e )
        {
            // Ignore
        }
        return result;
    }

    private String fillPatchData( Dictionary manifest )
    {
        if ( manifest.get( "Eclipse-ExtensibleAPI" ) != null )
        {
            return "Eclipse-ExtensibleAPI: true";
        }

        if ( manifest.get( "Eclipse-PatchFragment" ) != null )
        {
            return "Eclipse-PatchFragment: true";
        }
        return null;
    }

    private boolean addBundle( Dictionary enhancedManifest, File bundleLocation )
    {
        // TODO Qualifier Replacement. do we do this for maven?
        // updateVersionNumber(enhancedManifest);
        try
        {
            BundleDescription descriptor;
            descriptor = factory.createBundleDescription( state, enhancedManifest, bundleLocation.getAbsolutePath(),
                                                          getNextId() );
            bundleClasspaths.put( new Long( descriptor.getBundleId() ), getClasspath( enhancedManifest ) );
            String patchValue = fillPatchData( enhancedManifest );
            if ( patchValue != null )
                patchBundles.put( new Long( descriptor.getBundleId() ), patchValue );
            // rememberQualifierTagPresence(descriptor);

            state.addBundle( descriptor );
        }
        catch ( BundleException e )
        {
            // IStatus status = new Status(IStatus.WARNING, IPDEBuildConstants.PI_PDEBUILD, EXCEPTION_STATE_PROBLEM,
            // NLS.bind(Messages.exception_stateAddition, enhancedManifest.get(Constants.BUNDLE_NAME)), e);
            // BundleHelper.getDefault().getLog().log(status);
            return false;
        }
        return true;
    }

    public StateHelper getStateHelper()
    {
        return state.getStateHelper();
    }

    public Map getExtraData()
    {
        return bundleClasspaths;
    }

    public Map getPatchData()
    {
        return patchBundles;
    }

    public BundleDescription getResolvedBundle( String bundleId )
    {
        BundleDescription[] description = state.getBundles( bundleId );
        if ( description == null )
            return null;
        for ( int i = 0; i < description.length; i++ )
        {
            if ( description[i].isResolved() )
                return description[i];
        }
        return null;
    }

    public void resolveState()
    {
        Hashtable properties = new Hashtable( 3 );
        properties.put( OSGI_WS, CatchAllValue.singleton );
        properties.put( OSGI_OS, CatchAllValue.singleton );
        properties.put( OSGI_ARCH, CatchAllValue.singleton );

        // Set the JRE profile
        Properties profileProps = getJavaProfileProperties();
        if ( profileProps != null )
        {
            String systemPackages = profileProps.getProperty( SYSTEM_PACKAGES );
            if ( systemPackages != null )
                properties.put( SYSTEM_PACKAGES, systemPackages );
            String ee = profileProps.getProperty( Constants.FRAMEWORK_EXECUTIONENVIRONMENT );
            if ( ee != null )
                properties.put( Constants.FRAMEWORK_EXECUTIONENVIRONMENT, ee );
        }

        state.setPlatformProperties( properties );
        state.resolve( false );
    }

    private File getOSGiLocation()
    {
        BundleDescription osgiBundle = state.getBundle( "org.eclipse.osgi", null ); //$NON-NLS-1$
        if ( osgiBundle == null )
            return null;
        return new File( osgiBundle.getLocation() );
    }

    private String[] getJavaProfiles()
    {
        String[] javaProfiles = null;
        File osgiLocation = getOSGiLocation();
        if ( osgiLocation == null )
            return null;
        if ( osgiLocation.isDirectory() )
            javaProfiles = getDirJavaProfiles( osgiLocation );
        else
            javaProfiles = getJarJavaProfiles( osgiLocation );
        return javaProfiles;
    }

    private Properties getJavaProfileProperties()
    {
        String[] javaProfiles = getJavaProfiles();
        String profile = null;
        if ( javaProfiles != null && javaProfiles.length > 0 )
            profile = javaProfiles[0];
        else
            return null;

        File location = getOSGiLocation();
        if ( location == null )
            return null;
        InputStream is = null;
        ZipFile zipFile = null;
        try
        {
            if ( location.isDirectory() )
            {
                is = new FileInputStream( new File( location, profile ) );
            }
            else
            {
                zipFile = null;
                try
                {
                    zipFile = new ZipFile( location, ZipFile.OPEN_READ );
                    ZipEntry entry = zipFile.getEntry( profile );
                    if ( entry != null )
                        is = zipFile.getInputStream( entry );
                }
                catch ( IOException e )
                {
                    // nothing to do
                }
            }
            Properties props = new Properties();
            props.load( is );
            return props;
        }
        catch ( IOException e )
        {
            // nothing to do
        }
        finally
        {
            if ( is != null )
                try
                {
                    is.close();
                }
                catch ( IOException e )
                {
                    // nothing to do
                }
            if ( zipFile != null )
                try
                {
                    zipFile.close();
                }
                catch ( IOException e )
                {
                    // nothing to do
                }
        }
        return null;
    }

    private String[] getDirJavaProfiles( File bundleLocation )
    {
        // try the profile list first
        File profileList = new File( bundleLocation, "profile.list" );
        if ( profileList.exists() )
            try
            {
                return getJavaProfiles( new FileInputStream( profileList ) );
            }
            catch ( IOException e )
            {
                // this should not happen because we just checked if the file exists
            }
        String[] profiles = bundleLocation.list( new FilenameFilter()
        {
            public boolean accept( File dir, String name )
            {
                return name.endsWith( PROFILE_EXTENSION );
            }
        } );
        return sortProfiles( profiles );
    }

    private String[] sortProfiles( String[] profiles )
    {
        Arrays.sort( profiles, new Comparator()
        {
            public int compare( Object profile1, Object profile2 )
            {
                // need to make sure J2SE profiles are sorted ahead of all other profiles
                String p1 = (String) profile1;
                String p2 = (String) profile2;
                if ( p1.startsWith( "J2SE" ) && !p2.startsWith( "J2SE" ) )
                    return -1;
                if ( !p1.startsWith( "J2SE" ) && p2.startsWith( "J2SE" ) )
                    return 1;
                return -p1.compareTo( p2 );
            }
        } );
        return profiles;
    }

    private String[] getJarJavaProfiles( File bundleLocation )
    {
        ZipFile zipFile = null;
        ArrayList results = new ArrayList( 6 );
        try
        {
            zipFile = new ZipFile( bundleLocation, ZipFile.OPEN_READ );
            ZipEntry profileList = zipFile.getEntry( "profile.list" );
            if ( profileList != null )
                try
                {
                    return getJavaProfiles( zipFile.getInputStream( profileList ) );
                }
                catch ( IOException e )
                {
                    // this should not happen, just incase do the default
                }

            Enumeration entries = zipFile.entries();
            while ( entries.hasMoreElements() )
            {
                String entryName = ( (ZipEntry) entries.nextElement() ).getName();
                if ( entryName.indexOf( '/' ) < 0 && entryName.endsWith( PROFILE_EXTENSION ) )
                    results.add( entryName );
            }
        }
        catch ( IOException e )
        {
            // nothing to do
        }
        finally
        {
            if ( zipFile != null )
                try
                {
                    zipFile.close();
                }
                catch ( IOException e )
                {
                    // nothing to do
                }
        }
        return sortProfiles( (String[]) results.toArray( new String[results.size()] ) );
    }

    private String[] getJavaProfiles( InputStream is )
        throws IOException
    {
        Properties props = new Properties();
        props.load( is );
        return ManifestElement.getArrayFromList( props.getProperty( "java.profiles" ), "," ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
