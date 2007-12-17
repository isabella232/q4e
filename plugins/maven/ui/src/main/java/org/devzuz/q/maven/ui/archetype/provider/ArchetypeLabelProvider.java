/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * TODO Document
 * 
 * @author amuino
 */
public class ArchetypeLabelProvider extends LabelProvider
{

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage( Object element )
    {
        // TODO: We can present a custom image for each archetype if configured by the ArchetypeProvider
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText( Object element )
    {
        Archetype a = (Archetype) element;
        return a.getArtifactId()
                        + ( a.getVersion() != null && a.getVersion().length() > 0 ? " [" + a.getVersion() + "]"
                                        : "[latest]" );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    @Override
    public boolean isLabelProperty( Object element, String property )
    {
        // TODO: This method is probably not needed, since archetypes do not change
        return property.equals( "artifactId" ) || property.equals( "version" );
    }
}
