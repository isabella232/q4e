/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider.internal.wizard;

import org.devzuz.q.maven.ui.MavenUiActivator;
import org.devzuz.q.maven.ui.archetype.provider.ArchetypeProviderFactory;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider;
import org.devzuz.q.maven.ui.archetype.provider.IArchetypeProviderUIBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;

/**
 * This wizard implements the modification flow for an existing {@link IArchetypeProvider}.
 * 
 * The first page allows to enter common data (name and type), while the second page allows specific configuration for
 * each {@link IArchetypeProvider} type.
 * 
 * @author amuino
 */
public class EditArchetypeProviderWizard extends Wizard
{

    private IArchetypeProvider archetypeProvider;

    private EditDataPage editDataPage;

    private IArchetypeProviderUIBuilder uiBuilder;

    /**
     * @param archetypeProvider
     */
    public EditArchetypeProviderWizard( IArchetypeProvider archetypeProvider )
    {
        this.archetypeProvider = archetypeProvider;
        ArchetypeProviderFactory archetypeProviderFactory = MavenUiActivator.getDefault().getArchetypeProviderFactory();
        try
        {
            uiBuilder = archetypeProviderFactory.createUiBuilder( archetypeProvider.getType() );
            editDataPage = new EditDataPage( archetypeProvider, uiBuilder );
        }
        catch ( CoreException e )
        {
            // This is impossible unless the extension configuration is invalid.
            MavenUiActivator.getLogger().log( e );
            // TODO: Need a more elegant solution
            throw new RuntimeException( e );
        }
    }

    public IArchetypeProvider getArchetypeProvider()
    {
        return archetypeProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages()
    {
        addPage( editDataPage );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish()
    {
        this.archetypeProvider.setName( editDataPage.getArchetypeProvierName() );
        uiBuilder.applyConfiguration();
        return true;
    }
}
