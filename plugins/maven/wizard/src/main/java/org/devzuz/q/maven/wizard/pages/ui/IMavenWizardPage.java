/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages.ui;

import org.devzuz.q.maven.wizard.core.IMavenWizardContext;
import org.eclipse.jface.wizard.IWizardPage;

/**
 * The <code>IMavenWizardPage</code> interface allows creating wizard pages that can be integrated into Q wizards to
 * gather additional information.
 * 
 * <b>Note:</b> Implementors can extend {@link AbstractMavenWizardPage} instead.
 * 
 * The implementors will receive the context and is its responsibility to populate it with user provided information. If
 * the page is not displayed (i.e. the user presses Finish before reaching this page), suitable defaults must be
 * provided.
 * 
 * This interface is intended to be implemented only by the clients of the
 * <code>org.devzuz.q.maven.wizard.archetypeExtended</code> extension point.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public interface IMavenWizardPage extends IWizardPage
{
    /**
     * Retrieves the current context where the page is executing.
     * 
     * @return the current wizard context.
     */
    public abstract IMavenWizardContext getWizardContext();

    /**
     * Passes the current context where the page is executing. This can be used to pre-populate values on the wizard
     * fields (for example, if the user returns to the same page by using the "back" button). This method should only be
     * used by the framework to provide the initial object when the object is instantiated.
     * 
     * @param context
     *            the current wizard context.
     */
    public abstract void setWizardContext( IMavenWizardContext context );

    /**
     * Returns the page-specific configuration.
     * 
     * @return the page-specific config.
     */
    public abstract Object getConfig();

    /**
     * Sets the page-specific configuration.
     * 
     * @param context
     *            the page-specific config.
     */
    public abstract void setConfig( Object context );

}
