/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import java.util.Collection;

import org.devzuz.q.maven.embedder.QCoreException;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.eclipse.ui.IMemento;

/**
 * Archetype providers represent different components that can retrieve the available maven artifacts from any source (a
 * wiki page, a webservice, a folder...).
 * 
 * This is the interface that clients of the <code>org.devzuz.q.maven.core.archetypeProvider</code> extension point
 * must implement.
 * 
 * If the implementor of this interface performs network connections, it should honor the timeout preferences set by the
 * user. This timeout is available from {@link MavenUIPreferenceManagerAdapter#getArchetypeConnectionTimeout()}. For
 * example: <code>MavenPreferenceManager.getMavenPreferenceManager().getArchetypeConnectionTimeout();</code>
 * 
 * @author amuino
 */
public interface IArchetypeProvider
{
    /**
     * Returns the not-null name of this provider. When possible, the archetype provider must ensure that the name-type
     * pair must be unique to allow the end-user to identify each archetype provider.
     * 
     * An implementation can choose how to generate the value of this field. For example, it can choose to generate the
     * name from the implementation parameters or allow the user to enter a name.
     * 
     * @return the non-null, user-friendly name.
     */
    String getName();

    /**
     * Returns the not-null, user-friendly type of the archetype provider. The returned string will be visible on the
     * UI.
     * 
     * The value is initialized from the type value provided by the extension point and every implementation must
     * provide a different value.
     * 
     * @return the label describing the type of the provider.
     */
    String getType();

    /**
     * This method is not meant to be used by client code.
     * 
     * On initialization, the platform calls this method to set the not-null, user-friendly type of the archetype
     * provider. The provided string will be visible on the UI.
     * 
     * The value is initialized from the type value provided by the extension point and every implementation must
     * provide a different value.
     * 
     * @param type
     *            the type of the archetype. Must not be <code>null</code>.
     */
    void setType( String type );

    /**
     * This method is not meant to be used by client code.
     * 
     * This method allows setting a name to the configured archetype provider. It is used by the q4e runtime while
     * restoring the archetype provider and also when editing or configuring it for the first time.
     * 
     * The provided string will be visible on the UI.
     * 
     * @param name
     *            the name to set. Must not be <code>null</code>.
     */
    void setName( String name );

    /**
     * Retrieves the archetypes known by this provider.
     * 
     * <b>Note:</b> This operation can be expensive (for example, the archetype might need to access the database or a
     * remote resource).
     * 
     * @return all the archetypes known by this provider.
     * @throws QCoreException
     *             if an error is detected while recovering the collection of archetypes.
     */
    Collection<Archetype> getArchetypes() throws QCoreException;

    /**
     * Initializes the provider extended properties from the given stored copy. Note that the stored memento could
     * belong to a different version (for example, after updating the plug-in providing the implementation), so this
     * method must degrade gracefully when the information is incomplete.
     * 
     * This method is called by the platform and is not intended for use by implementors or clients.
     * 
     * @param customProperties
     *            the memento representing the storer properties.
     */
    void importState( IMemento customProperties );

    /**
     * Returns the current state of the archetype provided so it can be persisted between workbench sessions.
     * 
     * @param rootType
     *            the type that the returned memento must have.
     * @return an {@link IMemento} representing the current state. Can be <code>null</code> if there is no custom
     *         state to persist.
     */
    IMemento exportState( String rootType );
}
