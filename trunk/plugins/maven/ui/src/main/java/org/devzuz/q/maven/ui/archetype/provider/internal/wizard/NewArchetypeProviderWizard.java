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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

/**
 * This wizard implements the creation and configuration flow for a new {@link IArchetypeProvider}.
 * 
 * The first page allows to enter common data (name and type), while the second page allows specific configuration for
 * each {@link IArchetypeProvider} type.
 * 
 * @author amuino
 */
public class NewArchetypeProviderWizard extends Wizard
{

    private IArchetypeProvider archetypeProvider;

    private CommonDataPage commonDataPage = new CommonDataPage();

    private CustomDataPage customDataPage = null;

    ArchetypeProviderFactory archetypeProviderFactory = MavenUiActivator.getDefault().getArchetypeProviderFactory();

    private IArchetypeProviderUIBuilder uiBuilder;

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
        addPage( commonDataPage );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    @Override
    public boolean canFinish()
    {
        return customDataPage != null && customDataPage.isPageComplete();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getPreviousPage( IWizardPage page )
    {
        if ( page != null && page == customDataPage )
        {
            customDataPage.dispose();
            customDataPage = null;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public IWizardPage getNextPage( IWizardPage page )
    {
        if ( page == commonDataPage )
        {
            try
            {
                archetypeProvider = archetypeProviderFactory.createProvider( commonDataPage.getArchetypeProviderType() );
                archetypeProvider.setName( commonDataPage.getArchetypeProviderName() );
                archetypeProvider.setType( commonDataPage.getArchetypeProviderType() );
                return createCustomPage( archetypeProvider );
            }
            catch ( CoreException e )
            {
                // This is impossible unless the extension configuration is invalid.
                MavenUiActivator.getLogger().log( e );
                // TODO: Need a more elegant solution
                throw new RuntimeException( e );
            }
        }
        else
        {
            return null;
        }
    }

    protected IWizardPage createCustomPage( IArchetypeProvider archetypeProvider )
    {
        try
        {
            uiBuilder = archetypeProviderFactory.createUiBuilder( commonDataPage.getArchetypeProviderType() );
            customDataPage = new CustomDataPage( archetypeProvider, uiBuilder );
            customDataPage.setWizard( this );
            // uiBuilder.setInput( archetypeProvider );
            return customDataPage;
        }
        catch ( CoreException e )
        {
            // This is impossible unless the extension configuration is invalid.
            MavenUiActivator.getLogger().log( e );
            // TODO: Need a more elegant solution
            throw new RuntimeException( e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#needsPreviousAndNextButtons()
     */
    @Override
    public boolean needsPreviousAndNextButtons()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPageControls( Composite pageContainer )
    {
        super.createPageControls( pageContainer );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish()
    {
        uiBuilder.applyConfiguration();
        return true;
    }
}
