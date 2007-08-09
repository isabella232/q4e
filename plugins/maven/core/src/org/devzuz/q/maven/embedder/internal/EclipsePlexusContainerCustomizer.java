/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.apache.maven.embedder.ContainerCustomizer;
import org.codehaus.plexus.PlexusContainer;

/**
 * An implementation of a Plexus Container Customizer to allow the Embedder to switch into an Eclipse Environment
 * 
 * @author pdodds
 * 
 */
public class EclipsePlexusContainerCustomizer implements ContainerCustomizer
{

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.maven.embedder.ContainerCustomizer#customize(org.codehaus.plexus.PlexusContainer)
     */
    public void customize( PlexusContainer arg0 )
    {

    }

}
