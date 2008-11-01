/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import java.util.List;

public interface IObjectActionMap
{
    /**
     * @param object the object to get actions for
     * @return The list of ITreeObjectAction for this object's class 
     */
    public List< ITreeObjectAction > getObjectActions( Object object, String name );
}
