/*
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.List;

/**
 * @author emantos
 * 
 */

public interface IMavenExecutionResult
{
    /**
     * Gives access to the maven project where the execution as performed.
     * 
     * @return the maven project.
     */
    public IMavenProject getMavenProject();

    /**
     * Returns the <b>not-null</b> list of exceptions generated during the maven execution.
     * 
     * @return the list of exceptions.
     */
    public List<Exception> getExceptions();

    /**
     * Utility method for checking if any exception was raised during the execution.
     * 
     * It is equivalent to <code>!getExceptions().isEmpty()</code>
     * 
     * @return <code>true</code> if {@link #getExceptions()} will return a non-empty list.
     */
    public boolean hasErrors();
}
