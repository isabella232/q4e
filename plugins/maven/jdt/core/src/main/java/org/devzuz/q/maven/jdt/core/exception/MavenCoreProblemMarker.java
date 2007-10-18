/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.jdt.core.exception;

import org.devzuz.q.maven.jdt.core.Activator;

/**
 * @deprecated use {@link Activator#MARKER_ID}
 */
public class MavenCoreProblemMarker
{
    /**
     * @deprecated use {@link Activator#MARKER_ID}
     */
    public static String getMavenPOMMarker()
    {
        return Activator.MARKER_ID;
    }
}