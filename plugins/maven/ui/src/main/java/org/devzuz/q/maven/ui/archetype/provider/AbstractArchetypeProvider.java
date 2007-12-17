/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import org.eclipse.ui.IMemento;

/**
 * Base class implementing getters and mutators for the common properties.
 * 
 * @author amuino
 */
public abstract class AbstractArchetypeProvider implements IArchetypeProvider
{

    private String name = null;

    private String type = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider#getName()
     */
    public String getName()
    {
        return name;
    }

    /**
     * Allows setting the
     * 
     * @param name
     */
    public void setName( String name )
    {
        if ( name == null )
        {
            throw new IllegalArgumentException( "Name can't be null." );
        }
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.devzuz.q.maven.ui.archetype.provider.IArchetypeProvider#getType()
     */
    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        if ( type == null )
        {
            throw new IllegalArgumentException( "Type can't be null." );
        }
        this.type = type;
    }

    /**
     * Default implementation which exports an empty state.
     */
    public IMemento exportState( String rootType )
    {
        return null;
    }

    /**
     * Default implementation which does not modify the state.
     */
    public void importState( IMemento customProperties )
    {
        // Nothing to do.
    }
}
