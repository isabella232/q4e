package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Collection;
import java.util.HashSet;

import org.devzuz.q.maven.jdt.ui.MavenJdtUiActivator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Singleton manager for holding references to the project postprocessors that are applied after a maven project is
 * imported into eclipse.
 * 
 * <b>Prototype code:</b> This is not the best place nor the best way of implementing this, <b>must</b> be refactored.
 * 
 * @author amuino
 * 
 */
public class ImportProjectPostprocessorManager
{
    private final Collection<IImportProjectPostprocessor> postprocessors;

    private final static ImportProjectPostprocessorManager instance = new ImportProjectPostprocessorManager();

    private static final String PROJECT_POSTPROCESSOR_EXTENSION_POINT_ID =
        "org.devzuz.q.maven.jdt.ui.projectPostprocessors";

    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * Hidden default constructor for the Singleton pattern.
     */
    private ImportProjectPostprocessorManager()
    {
        postprocessors = initializePostprocessors();
    }

    /**
     * Reads the postprocessor definitions contributed to the
     * <code>org.devzuz.q.maven.jdt.ui.projectPostprocessors</code> extension point.
     */
    private Collection<IImportProjectPostprocessor> initializePostprocessors()
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] extensions =
            registry.getConfigurationElementsFor( PROJECT_POSTPROCESSOR_EXTENSION_POINT_ID );
        Collection<IImportProjectPostprocessor> result = new HashSet<IImportProjectPostprocessor>( extensions.length );
        for ( IConfigurationElement extension : extensions )
        {
            try
            {
                // TODO: abstract this to avoid activation of all the plug-ins contributing postprocessors
                IImportProjectPostprocessor postprocessor =
                    (IImportProjectPostprocessor) extension.createExecutableExtension( CLASS_ATTRIBUTE );
                result.add( postprocessor );
            }
            catch ( CoreException e )
            {
                MavenJdtUiActivator.getLogger().log(
                                                     "Could not create postprocessor: "
                                                                     + extension.getAttribute( CLASS_ATTRIBUTE ), e );
            }

        }
        return result;
    }

    /**
     * Obtains the single instance of this class.
     * 
     * @return the single instance of this class.
     */
    public final static ImportProjectPostprocessorManager getInstance()
    {
        return instance;
    }

    /**
     * Returns an unmodifiable collection with the registered postprocessors.
     * 
     * @return
     */
    public synchronized IImportProjectPostprocessor[] getPostprocessors()
    {
        return postprocessors.toArray( new IImportProjectPostprocessor[postprocessors.size()] );
    }
}
