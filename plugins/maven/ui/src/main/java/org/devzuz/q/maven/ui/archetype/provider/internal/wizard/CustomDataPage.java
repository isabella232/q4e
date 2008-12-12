/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.internal.wizard;

import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This wizard page allows collecting archetype provider specific information.
 * 
 * @author amuino
 */
public class CustomDataPage extends WizardPage
{

    /** Control providing the content for the page. */
    private IArchetypeProviderUIBuilder uiBuilder;

    private final IArchetypeProvider archetypeProvider;

    /**
     * Creates a the wizard page.
     * 
     * @param archetypeProvider
     *            the name of the provider, for use in the title.
     * @param uiBuilder
     *            the control builder for creating the UI.
     */
    public CustomDataPage( IArchetypeProvider archetypeProvider, IArchetypeProviderUIBuilder uiBuilder )
    {
        super( "Properties for " + archetypeProvider.getName() );
        this.archetypeProvider = archetypeProvider;
        this.uiBuilder = uiBuilder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl( Composite parent )
    {
        Control c = uiBuilder.createControl( parent, this );
        setControl( c );
        uiBuilder.setInput( archetypeProvider );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
    public boolean isPageComplete()
    {
        return super.isPageComplete() && uiBuilder.isConfigured();
    }
}
