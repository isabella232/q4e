/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * 
 */
package org.devzuz.q.maven.wizard.postprocessor.core;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.wizard.core.IMavenWizardContext;
import org.devzuz.q.maven.wizard.pages.ui.AbstractMavenWizardPage;
import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.eclipse.core.runtime.IStatus;

/**
 * The <code>IMavenProjectPostprocessor</code> interface will be implemented by extenders who whish to perform
 * additional processing on an {@link IMavenProjectPostprocessor} after its creation. If also one or more
 * {@link IMavenWizardPage}s are provided, the postprocessor will be given the custom configuration object.
 * 
 * <b>Note:</b> Implementors can extend {@link AbstractMavenWizardPage} instead.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public interface IMavenProjectPostprocessor
{

    /**
     * Passes the current context where the postprocessor is executing.
     * 
     * @param context
     *            the current wizard context.
     */
    public abstract void setWizardContext( IMavenWizardContext context );

    /**
     * Gets the current context where the postprocessor is executing.
     * 
     * @return context the current wizard context.
     */
    public abstract IMavenWizardContext setWizardContext();

    /**
     * Sets the specific configuration captured through a wizard page.
     * 
     * @param config
     *            the page-specific config.
     */
    public abstract void setConfig( Object config );

    /**
     * Gets the specific configuration captured through a wizard page.
     * 
     * @return context the page-specific config.
     */
    public abstract Object getConfig();

    /**
     * Executes the postprocessor on the given maven project.
     * 
     * @param mavenProject
     *            the project to postprocess.
     * 
     * @return the result status.
     */
    public abstract IStatus run( IMavenProject mavenProject );
}
