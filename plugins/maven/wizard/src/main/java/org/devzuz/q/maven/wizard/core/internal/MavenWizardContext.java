/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.devzuz.q.maven.wizard.core.IMavenWizardContext;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;

/**
 * Default implementation for {@link IMavenWizardContext}.
 * 
 * Additionally, this class extends {@link IMavenWizardContext} with non API methods that should not be used by clients.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class MavenWizardContext implements IMavenWizardContext
{
    private Properties archetypeCreationProperties = new Properties();

    private Map<String, Object> pageConfigMap = new HashMap<String, Object>();

    public List<IMavenProjectPostprocessor> postProcessors;

    public Properties getArchetypeCreationProperties()
    {
        return archetypeCreationProperties;
    }

    public Object getPageConfig( String configId )
    {
        return pageConfigMap.get( configId );
    }

    /**
     * Sets the configuration object for a page with the specified <code>configId</code>. This method is not intended
     * to be used by clients.
     * 
     * @param configId
     *            the identifier of the configuration object to set.
     * @param pageConfig
     *            the configuration object. If <code>null</code>, the existing configuration is deleted.
     */
    public void setPageConfig( String configId, Object pageConfig )
    {
        pageConfigMap.put( configId, pageConfig );
    }

    /**
     * Obtains the list of registered postprocessors for the selected archetype. This list is only populated when the
     * new maven project wizard is finished. This method is not intended to be used by clients.
     * 
     * @return the list of project postprocessors to be applied after running the archetype.
     */
    public List<IMavenProjectPostprocessor> getPostProcessors()
    {
        return postProcessors;
    }

    /**
     * Sets the list of registered postprocessors for the selected archetype. This list can only populated when the new
     * maven project wizard is finished. This method is not intended to be used by clients.
     * 
     * @param postProcessors
     *            the list of project postprocessors to be applied after running the archetype.
     */
    public void setPostProcessors( List<IMavenProjectPostprocessor> postProcessors )
    {
        this.postProcessors = postProcessors;
    }

}
