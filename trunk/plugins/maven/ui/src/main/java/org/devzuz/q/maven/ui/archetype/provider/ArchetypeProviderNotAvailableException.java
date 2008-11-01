/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

/**
 * This exception signals an attempt to restore an archetype provider of an unavailable type from its saved description.
 * 
 * This might be caused after a restart when removing/installing some plug-in which provided support for the (now
 * missing) archetype provider type.
 * 
 * @author amuino
 */
public class ArchetypeProviderNotAvailableException extends Exception
{

    private String name;

    private String type;

    /**
     * @param name
     *            the name of the archetype provider.
     * @param type
     *            the type of the archetype provider.
     */
    public ArchetypeProviderNotAvailableException( String name, String type )
    {
        super( "The archetype provider '" + name + "' could not be restored because support for the type '" + type
                        + "' is missing on the current workbench configuration." );
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the archetype provider.
     * 
     * @return the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the type of the archetype provider that could not be restored.
     * 
     * @return the type.
     */
    public String getType()
    {
        return type;
    }
}
