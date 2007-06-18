/***************************************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM - Initial API and implementation
 **************************************************************************************************/
package org.devzuz.q.bundle.classpath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.osgi.service.resolver.HostSpecification;
import org.eclipse.osgi.service.resolver.StateHelper;
import org.eclipse.osgi.util.NLS;

public class ClasspathComputer3_0
{
    public static class ClasspathElement
    {
        private String path;

        private String accessRules;

        /**
         * Create a ClasspathElement object
         * 
         * @param path
         * @param accessRules
         * @throws NullPointerException if path is null
         */
        public ClasspathElement( String path, String accessRules )
        {
            this.path = path;
            this.accessRules = accessRules;
        }

        public String toString()
        {
            return path;
        }

        public String getPath()
        {
            return path;
        }

        public String getAccessRules()
        {
            return accessRules;
        }

        public void addRules( String newRule )
        {
            if ( accessRules.equals( "" ) || accessRules.equals( newRule ) ) //$NON-NLS-1$
                return;
            if ( !newRule.equals( "" ) ) { //$NON-NLS-1$
                String join = accessRules.substring( 0, accessRules.length() - EXCLUDE_ALL_RULE.length() - 1 );
                newRule = join + newRule.substring( 1 );
            }
            accessRules = newRule;
            return;
        }

        /**
         * ClasspathElement objects are equal if they have the same path. Access rules are not considered.
         */
        public boolean equals( Object obj )
        {
            if ( obj instanceof ClasspathElement )
            {
                ClasspathElement element = (ClasspathElement) obj;
                return ( path != null && path.equals( element.getPath() ) );
            }
            return false;
        }

        public int hashCode()
        {
            return path.hashCode();
        }

        public static String normalize( String path )
        {
            // always use '/' as a path separator to help with comparing paths in equals
            return path.replaceAll( "\\\\", "/" ); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    private static final String EXCLUDE_ALL_RULE = "?**/*"; //$NON-NLS-1$

    private static final String error_pluginCycle = "A cycle was detected when generating the classpath {0}.";//$NON-NLS-1$ 

    private MavenGenerator generator;

    private Map visiblePackages = null;

    private Map pathElements = null;

    private MavenState state = null;

    public ClasspathComputer3_0( MavenGenerator generator )
    {
        this.generator = generator;
        this.state = generator.getState();
    }

    /**
     * Compute the classpath for the given jar. The path returned conforms to Parent / Prerequisite / Self
     * 
     * @param model the plugin containing the jar compiled
     * @param jar the jar for which the classpath is being compiled
     * @return String the classpath
     * @throws CoreException
     */
    public List getClasspath( BundleDescription model )
        throws CoreException
    {
        List classpath = new ArrayList( 20 );
        List pluginChain = new ArrayList( 10 ); // The list of plugins added to detect cycle

        Set addedPlugins = new HashSet( 10 ); // The set of all the plugins already added to the classpath (this allows
        // for optimization)
        pathElements = new HashMap();
        visiblePackages = getVisiblePackages( model );

        // PREREQUISITE
        addPrerequisites( model, classpath, pluginChain, addedPlugins );

        // SELF
        addSelf( model, classpath, pluginChain, addedPlugins );

        return classpath;

    }

    private Map getVisiblePackages( BundleDescription model )
    {
        Map packages = new HashMap( 20 );
        StateHelper helper = state.getStateHelper();
        addVisiblePackagesFromState( helper, model, packages );
        if ( model.getHost() != null )
            addVisiblePackagesFromState( helper, (BundleDescription) model.getHost().getSupplier(), packages );
        return packages;
    }

    private void addVisiblePackagesFromState( StateHelper helper, BundleDescription model, Map packages )
    {
        ExportPackageDescription[] exports = helper.getVisiblePackages( model );
        for ( int i = 0; i < exports.length; i++ )
        {
            BundleDescription exporter = exports[i].getExporter();
            if ( exporter == null )
                continue;

            boolean discouraged = helper.getAccessCode( model, exports[i] ) == StateHelper.ACCESS_DISCOURAGED;
            String pattern = exports[i].getName().replaceAll( "\\.", "/" ) + "/*"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            String rule = ( discouraged ? '~' : '+' ) + pattern;

            String rules = (String) packages.get( exporter.getSymbolicName() );
            if ( rules != null )
            {
                if ( rules.indexOf( rule ) == -1 )
                    rules = ( rules != null ) ? rules + File.pathSeparator + rule : rule;
            }
            else
            {
                rules = rule;
            }

            packages.put( exporter.getSymbolicName(), rules );
        }
    }

    /**
     * Add the specified plugin (including its jars) and its fragments
     * 
     * @param plugin
     * @param classpath
     * @param location
     * @throws CoreException
     */
    private void addPlugin( BundleDescription plugin, List classpath )
        throws CoreException
    {
        boolean allFragments = true;
        String patchInfo = (String) state.getPatchData().get( new Long( plugin.getBundleId() ) );
        if ( patchInfo != null && plugin != generator.getModel() )
        {
            addFragmentsLibraries( plugin, classpath, false, false );
            allFragments = false;
        }
        addRuntimeLibraries( plugin, classpath );
        addFragmentsLibraries( plugin, classpath, true, allFragments );
    }

    /**
     * Add the runtime libraries for the specified plugin.
     * 
     * @param model
     * @param classpath
     * @param baseLocation
     * @throws CoreException
     */
    private void addRuntimeLibraries( BundleDescription model, List classpath )
        throws CoreException
    {
        String[] libraries = getClasspathEntries( model );
        String base = model.getLocation();

        for ( int i = 0; i < libraries.length; i++ )
        {
            // addDevEntries(model, baseLocation, classpath,
            // Utils.getArrayFromString(modelProps.getProperty(PROPERTY_OUTPUT_PREFIX + libraries[i])));
            addPathAndCheck( model, base, libraries[i], classpath );
        }
    }

    /**
     * Add all fragments of the given plugin
     * 
     * @param plugin
     * @param classpath
     * @param baseLocation
     * @throws CoreException
     */
    private void addFragmentsLibraries( BundleDescription plugin, List classpath, boolean afterPlugin, boolean all )
        throws CoreException
    {
        // if plugin is not a plugin, it's a fragment and there is no fragment for a fragment. So we return.
        BundleDescription[] fragments = plugin.getFragments();
        if ( fragments == null )
            return;

        for ( int i = 0; i < fragments.length; i++ )
        {
            if ( fragments[i] == generator.getModel() )
                continue;
            // if (matchFilter(fragments[i]) == false)
            // continue;
            // check resolved status instead of filter
            if ( !fragments[i].isResolved() )
                continue;
            if ( !afterPlugin && isPatchFragment( fragments[i] ) )
            {
                addPluginLibrariesToFragmentLocations( plugin, fragments[i], classpath );
                addRuntimeLibraries( fragments[i], classpath );
                continue;
            }
            if ( ( afterPlugin && !isPatchFragment( fragments[i] ) ) || all )
            {
                addRuntimeLibraries( fragments[i], classpath );
                addPluginLibrariesToFragmentLocations( plugin, fragments[i], classpath );
                continue;
            }
        }
    }

    private boolean isPatchFragment( BundleDescription fragment )
        throws CoreException
    {
        return state.getPatchData().get( new Long( fragment.getBundleId() ) ) != null;
    }

    /**
     * There are cases where the plug-in only declares a library but the real JAR is under a fragment location. This
     * method gets all the plugin libraries and place them in the possible fragment location.
     * 
     * @param plugin
     * @param fragment
     * @param classpath
     * @param baseLocation
     * @throws CoreException
     */
    private void addPluginLibrariesToFragmentLocations( BundleDescription plugin, BundleDescription fragment,
                                                        List classpath )
        throws CoreException
    {
        // TODO This methods causes the addition of a lot of useless entries. See bug #35544
        // If we reintroduce the test below, we reintroduce the problem 35544
        // if (fragment.getRuntime() != null)
        // return;
        String[] libraries = getClasspathEntries( plugin );

        String root = fragment.getLocation();
        // IPath base = Utils.makeRelative(new Path(root), new Path(baseLocation));
        // Properties modelProps = getBuildPropertiesFor(fragment);
        for ( int i = 0; i < libraries.length; i++ )
        {
            addPathAndCheck( fragment, root, libraries[i], classpath );
        }
    }

    // Add a path into the classpath for a given model
    // pluginId the plugin we are adding to the classpath
    // basePath : the relative path between the plugin from which we are adding the classpath and the plugin that is
    // requiring this entry
    // classpath : The classpath in which we want to add this path
    private void addPathAndCheck( BundleDescription model, String basePath, String libraryName, List classpath )
    {
        String pluginId = model != null ? model.getSymbolicName() : null;
        String rules = ""; //$NON-NLS-1$
        // only add access rules to libraries that are not part of the current bundle
        // and are not this bundle's host if we are a fragment
        BundleDescription currentBundle = generator.getModel();
        if ( model != null && model != currentBundle
            && ( currentBundle.getHost() == null || currentBundle.getHost().getSupplier() != model ) )
        {
            String packageKey = pluginId;
            if ( model.isResolved() && model.getHost() != null )
            {
                packageKey = ( (BundleDescription) model.getHost().getSupplier() ).getSymbolicName();
            }
            if ( visiblePackages.containsKey( packageKey ) )
            {
                rules = "[" + (String) visiblePackages.get( packageKey ) + File.pathSeparator + EXCLUDE_ALL_RULE + "]"; //$NON-NLS-1$ //$NON-NLS-2$
            }
            else
            {
                rules = "[" + EXCLUDE_ALL_RULE + "]"; //$NON-NLS-1$//$NON-NLS-2$
            }
        }

        String path = null;
        File base = new File( basePath );
        try
        {
            if ( base.isFile() )
            { // &&"jar".equalsIgnoreCase(basePath.getFileExtension())) { //$NON-NLS-1$
                path = base.getCanonicalPath();
            }
            else
            {
                path = new File( base, libraryName ).getCanonicalPath();// basePath.append(libraryName).toString();
            }
        }
        catch ( IOException e )
        {

        }

        // path = generator.replaceVariables(path, pluginId == null ? false :
        // generator.getCompiledElements().contains(pluginId));
        // String secondaryPath = null;
        // if (generator.getCompiledElements().contains(pluginId)) {
        // if (modelProperties == null || modelProperties.getProperty(IBuildPropertiesConstants.PROPERTY_SOURCE_PREFIX +
        // libraryName) != null)
        // path = Utils.getPropertyFormat(PROPERTY_BUILD_RESULT_FOLDER) + '/' + path;
        // secondaryPath = Utils.getPropertyFormat(PROPERTY_BUILD_RESULT_FOLDER) + "/../" + pluginId + '/' +
        // libraryName; //$NON-NLS-1$
        //		
        // }
        if ( path != null )
            addClasspathElementWithRule( classpath, path, rules );
        // if (secondaryPath != null) {
        // addClasspathElementWithRule(classpath, secondaryPath, rules);
        // }
    }

    private void addClasspathElementWithRule( List classpath, String path, String rules )
    {
        String normalizedPath = ClasspathElement.normalize( path );
        ClasspathElement existing = (ClasspathElement) pathElements.get( normalizedPath );
        if ( existing != null )
        {
            existing.addRules( rules );
        }
        else
        {
            ClasspathElement element = new ClasspathElement( normalizedPath, rules );
            classpath.add( element );
            pathElements.put( normalizedPath, element );
        }
    }

    private void addSelf( BundleDescription model, List classpath, List pluginChain, Set addedPlugins )
        throws CoreException
    {
        // If model is a fragment, we need to add in the classpath the plugin to which it is related
        HostSpecification host = model.getHost();
        if ( host != null )
        {
            BundleDescription[] hosts = host.getHosts();
            for ( int i = 0; i < hosts.length; i++ )
                addPluginAndPrerequisites( hosts[i], classpath, pluginChain, addedPlugins );
        }

        // Add the libraries
        String[] libraries = getClasspathEntries( model );
        if ( libraries != null )
        {
            for ( int i = 0; i < libraries.length; i++ )
            {
                String libraryName = libraries[i];
                // if (jar.getArtifactId().equals(libraryName))
                // continue;

                addPathAndCheck( model, model.getLocation(), libraryName, classpath );
            }
        }

        // add extra classpath if it exists. this code is kept for backward compatibility
        // String[] extra = generator.getExtraClasspath(jar);
        // if (extra != null) {
        // for (int i = 0; i < extra.length; i++) {
        // //Potential pb: if the path refers to something that is being compiled (which is supposetly not the case, but
        // who knows...)
        // //the user will get $basexx instead of $ws
        // addPathAndCheck(null, extra[i], "", classpath);
        // }
        // }

        // add extra classpath if it is specified for the given jar
        // String[] jarSpecificExtraClasspath = jar.getExtraClasspath();
        // for (int i = 0; i < jarSpecificExtraClasspath.length; i++) {
        // //Potential pb: if the path refers to something that is being compiled (which is supposetly not the case, but
        // who knows...)
        // //the user will get $basexx instead of $ws
        // String toAdd = computeExtraPath(jarSpecificExtraClasspath[i], classpath, location);
        // if (toAdd != null)
        // addPathAndCheck(null, new Path(toAdd), "", modelProperties, classpath); //$NON-NLS-1$
        // }
    }

    // Add the prerequisite of a given plugin (target)
    private void addPrerequisites( BundleDescription target, List classpath, List pluginChain, Set addedPlugins )
        throws CoreException
    {
        if ( pluginChain.contains( target ) )
        {
            String cycleString = ""; //$NON-NLS-1$
            for ( Iterator iter = pluginChain.iterator(); iter.hasNext(); )
                cycleString += iter.next().toString() + ", "; //$NON-NLS-1$
            cycleString += target.toString();
            throw new CoreException( NLS.bind( error_pluginCycle, cycleString ) );
        }
        if ( addedPlugins.contains( target ) ) // the plugin we are considering has already been added
            return;

        // add libraries from pre-requisite plug-ins. Don't worry about the export flag
        // as all required plugins may be required for compilation.
        BundleDescription[] requires = MavenState.getDependentBundles( target );
        pluginChain.add( target );
        for ( int i = 0; i < requires.length; i++ )
        {
            addPluginAndPrerequisites( requires[i], classpath, pluginChain, addedPlugins );
        }
        pluginChain.remove( target );
        addedPlugins.add( target );
    }

    /**
     * The pluginChain parameter is used to keep track of possible cycles. If prerequisite is already present in the
     * chain it is not included in the classpath.
     * 
     * @param target : the plugin for which we are going to introduce
     * @param classpath
     * @param baseLocation
     * @param pluginChain
     * @param addedPlugins
     * @throws CoreException
     */
    private void addPluginAndPrerequisites( BundleDescription target, List classpath, List pluginChain, Set addedPlugins )
        throws CoreException
    {
        // if (matchFilter(target) == false)
        // return;
        if ( !target.isResolved() )
            return;

        addPlugin( target, classpath );
        addPrerequisites( target, classpath, pluginChain, addedPlugins );
    }

    private boolean matchFilter( BundleDescription target )
    {
        return true;
        // String filter = target.getPlatformFilter();
        // if (filter == null) //Target is platform independent, add it
        // return true;
        //
        // IPluginEntry associatedEntry = generator.getAssociatedEntry();
        // if (associatedEntry == null)
        // return true;
        //
        // String os = associatedEntry.getOS();
        // String ws = associatedEntry.getWS();
        // String arch = associatedEntry.getOSArch();
        // String nl = associatedEntry.getNL();
        // if (os == null && ws == null && arch == null && nl == null) //I'm a platform independent plugin
        // return true;
        //
        // //The plugin for which we are generating the classpath and target are not platform independent
        // Filter f = BundleHelper.getDefault().createFilter(filter);
        // if (f == null)
        // return true;
        //
        // Dictionary properties = new Hashtable(3);
        // if (os != null) {
        // properties.put(OSGI_OS, os);
        // } else {
        // properties.put(OSGI_OS, CatchAllValue.singleton);
        // }
        // if (ws != null)
        // properties.put(OSGI_WS, ws);
        // else
        // properties.put(OSGI_WS, CatchAllValue.singleton);
        //
        // if (arch != null)
        // properties.put(OSGI_ARCH, arch);
        // else
        // properties.put(OSGI_ARCH, CatchAllValue.singleton);
        //
        // if (arch != null)
        // properties.put(OSGI_NL, arch);
        // else
        // properties.put(OSGI_NL, CatchAllValue.singleton);
        //
        // return f.match(properties);
    }

    // Return the jar name from the classpath
    private String[] getClasspathEntries( BundleDescription bundle )
        throws CoreException
    {
        return (String[]) state.getExtraData().get( new Long( bundle.getBundleId() ) );
        // return generator.getClasspathEntries(bundle);
    }
}
