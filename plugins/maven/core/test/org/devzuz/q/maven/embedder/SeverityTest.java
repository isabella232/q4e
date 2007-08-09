/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.Severity;

import junit.framework.TestCase;

public class SeverityTest extends TestCase
{

    public void testCompare()
    {
        assertTrue( Severity.debug.compareTo( Severity.info ) < 0 );
        assertTrue( Severity.info.compareTo( Severity.warn ) < 0 );
        assertTrue( Severity.warn.compareTo( Severity.error ) < 0 );
        assertTrue( Severity.error.compareTo( Severity.fatal ) < 0 );

        assertTrue( Severity.info.compareTo( Severity.debug ) > 0 );
        assertTrue( Severity.warn.compareTo( Severity.info ) > 0 );
        assertTrue( Severity.error.compareTo( Severity.warn ) > 0 );
        assertTrue( Severity.fatal.compareTo( Severity.error ) > 0 );
    }

    public void testOrdinal()
    {
        int i = 0;
        assertEquals( i++, Severity.debug.ordinal() );
        assertEquals( i++, Severity.info.ordinal() );
        assertEquals( i++, Severity.warn.ordinal() );
        assertEquals( i++, Severity.error.ordinal() );
        assertEquals( i++, Severity.fatal.ordinal() );
    }
}
