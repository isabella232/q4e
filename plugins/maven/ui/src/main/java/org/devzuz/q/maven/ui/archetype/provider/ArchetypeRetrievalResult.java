/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.archetype.provider;

import java.util.Collection;

import org.devzuz.q.maven.embedder.QCoreException;

public class ArchetypeRetrievalResult
{
    private Collection<Archetype> archetypes;
    private Collection<QCoreException>  exceptions;
    
    public ArchetypeRetrievalResult( Collection<Archetype> archetypes , Collection<QCoreException> exceptions )
    {
        this.archetypes = archetypes;
        this.exceptions = exceptions;
    }
    
    public Collection<Archetype> getArchetypes()
    {
        return archetypes;
    }
    
    public Collection<QCoreException> getExceptions()
    {
        return exceptions;
    }
}
