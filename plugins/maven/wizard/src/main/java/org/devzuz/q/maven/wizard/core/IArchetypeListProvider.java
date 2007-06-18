/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.io.IOException;
import java.util.Map;

public interface IArchetypeListProvider
{
    /**
     * 
     * @return List of archetypes
     * @throws IOException if an error happened while retrieving the archetype list
     */
    public Map<String, Archetype> getArchetypes()
        throws IOException;
}
