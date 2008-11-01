/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import junit.framework.TestCase;

import org.devzuz.q.maven.embedder.Severity;

/**
 * Test for {@link AbstractMavenEvent}
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class AbstractMavenEventTest extends TestCase
{

    private AbstractMavenEvent event;

    public void testMergeMessages()
    {
        String description = "\ttest";
        event = new MavenLog( Severity.info, description, null );
        String result = event.mergeMessages( description, new Object[0] );
        assertEquals( "  test", result );
    }
}
