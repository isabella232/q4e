/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.wizard.core;

import java.util.List;
import java.util.Properties;

import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;

/**
 * This interface provides access to the context available to the {@link IMavenWizardPage}s.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public interface IMavenWizardContext
{

    /**
     * Obtains the properties that will be used when creating the archetype. The wizard page can modify these to reflect
     * user choices.
     * 
     * @return the current (modifiable) properties.
     */
    public abstract Properties getArchetypeCreationProperties();

    /**
     * Obtains the configuration object for a page with the specified <code>configId</code>.
     * 
     * @param configId
     *            the identifier of the configuration object to retrieve.
     * @return the configuration object, might be <code>null</code>.
     */
    public abstract Object getPageConfig( String configId );

    /**
     * Obtains the list of registered postprocessors for the selected archetype. This list is only populated when the
     * new maven project wizard is finished.
     * 
     * @return the <b>non modifiable</b> list of project postprocessors to be applied after running the archetype.
     */
    public List<IMavenProjectPostprocessor> getPostProcessors();

}
