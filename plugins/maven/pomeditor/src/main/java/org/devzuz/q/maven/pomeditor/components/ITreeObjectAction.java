/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

public interface ITreeObjectAction
{
    /**
     * @return The string that would appear as the title of this action
     */
    public String getName();
    
    /**
     * This is the action that will be performed when this action is executed. 
     */
    public void doAction( Object obj );
}
