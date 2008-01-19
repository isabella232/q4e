/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages.ui;

import org.devzuz.q.maven.wizard.core.IMavenWizardContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;

/**
 * This abstract class implements the get/set methods in {@link IMavenWizardPage}.
 * 
 * @author amuino
 */
public abstract class AbstractMavenWizardPage extends WizardPage implements IMavenWizardPage
{

    private IMavenWizardContext wizardContext;

    private Object pageContext;

    /**
     * Creates the wizard page with the given name, title and image descriptor.
     * 
     * @see WizardPage#WizardPage(String,String,ImageDescriptor)
     * @param pageName
     *            name for the wizard page.
     * @param title
     *            the title displayed in the page
     * @param titleImage
     *            the image used in the title.
     */
    protected AbstractMavenWizardPage( String pageName, String title, ImageDescriptor titleImage )
    {
        super( pageName, title, titleImage );
    }

    /**
     * Creates the wizard page with the given name.
     * 
     * @see WizardPage#WizardPage(String)
     * @param pageName
     *            name for the wizard page.
     */
    protected AbstractMavenWizardPage( String pageName )
    {
        super( pageName );
    }

    public Object getConfig()
    {
        return pageContext;
    }

    public void setConfig( Object context )
    {
        this.pageContext = context;
    }

    public IMavenWizardContext getWizardContext()
    {
        return wizardContext;
    }

    public void setWizardContext( IMavenWizardContext context )
    {
        this.wizardContext = context;
    }

    /**
     * Removes use of cached previous page and always asks the wizard.
     */
    @Override
    public IWizardPage getPreviousPage()
    {
        IWizard wizard = getWizard();
        if ( wizard == null )
        {
            return null;
        }

        return wizard.getPreviousPage( this );
    }
}
