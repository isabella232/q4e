/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.postprocessor.core;

import org.devzuz.q.maven.wizard.core.IMavenWizardContext;

/**
 * This abstract class implements the get/set methods of the {@link IMavenProjectPostprocessor}.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public abstract class AbstractMavenProjectPostprocessor implements IMavenProjectPostprocessor
{

    private Object pageContext;

    private IMavenWizardContext wizardContext;

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor#getPageContext()
     */
    public Object getConfig()
    {
        return pageContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor#setPageContext(java.lang.Object)
     */
    public void setConfig( Object context )
    {
        this.pageContext = context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor#setWizardContext(org.devzuz.q.maven.wizard.core.IMavenWizardContext)
     */
    public void setWizardContext( IMavenWizardContext context )
    {
        wizardContext = context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor#setWizardContext()
     */
    public IMavenWizardContext setWizardContext()
    {
        return wizardContext;
    }
}
