package org.devzuz.q.maven.jdt.ui.projectimport;

import java.util.Collection;
import java.util.HashSet;

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
    private final Collection<IImportProjectPostprocessor> postprocessors =
        new HashSet<IImportProjectPostprocessor>( 20 );

    private final static ImportProjectPostprocessorManager instance = new ImportProjectPostprocessorManager();

    /**
     * Hidden default constructor for the Singleton pattern.
     */
    private ImportProjectPostprocessorManager()
    {
        // No-op
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

    public synchronized void registerPostprocessor( IImportProjectPostprocessor postprocessor )
    {
        postprocessors.add( postprocessor );
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
