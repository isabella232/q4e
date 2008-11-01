/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This interface can be implemented to provide controls that can edit the properties of a corresponding implementation
 * of {@link IArchetypeProvider}. In addition to the methods in this interface, implementors <b>must</b> also provide
 * a zero-argument constructor.
 * 
 * The platform uses the contract defined in this interface as follows:
 * <ol>
 * <li>{@link #createControl(Composite)} is called to initialize the user interface controls.</li>
 * <li>{@link #setInput(IArchetypeProvider)} is then invoked to provide the instance of archetype provider that must be
 * modified.</li>
 * <li>{@link #isConfigured()} is used to validate user-entered data.</li>
 * <li>If the data was valid, {@link #applyConfiguration()} is invoked to allow the implementation to set the UI values
 * in the archetype provider.</li>
 * <li>The archetype provider is then used as needed.</li>
 * </ol>
 * 
 * Since implementations must keep a reference to the {@link IArchetypeProvider}, they should be assumed to be not
 * thread-safe.
 * 
 * @author amuino
 */
public interface IArchetypeProviderUIBuilder
{
    /**
     * Creates and returns the UI control for editing the archetype provider. The created control must be created as a
     * child of the provided parent.
     * 
     * @param parent
     *            the composite where the UI control must be created.
     * @param ownerPage
     *            the wizard page where this control is being created. Useful for displaying validation messages.
     * @return the created control.
     */
    Control createControl( Composite parent, WizardPage ownerPage );

    /**
     * This method should not be called by client code.
     * 
     * This method is invoked to set the provider which will be edited. It is expected that implementations will
     * (re)initialize its internal state at this point.
     * 
     * @param provider
     */
    void setInput( IArchetypeProvider provider );

    /**
     * Indicates when the {@link IArchetypeProvider} is correctly configured (there are no errors in user-provided data
     * nor any missing input).
     * 
     * @return <code>true</code> when all the properties have been correctly configured by the user.
     */
    boolean isConfigured();

    /**
     * This method should not be called by client code.
     * 
     * This method is invoked by the platform before using the {@link IArchetypeProvider} to ensure that any changes
     * made on the UI have been applied to the archetype provider given in {@link #setInput(IArchetypeProvider)}.
     */
    void applyConfiguration();
}
