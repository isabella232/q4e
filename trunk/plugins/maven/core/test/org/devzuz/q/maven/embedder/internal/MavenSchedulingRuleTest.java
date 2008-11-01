/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder.internal;

import java.io.File;

import junit.framework.TestCase;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class MavenSchedulingRuleTest extends TestCase
{
    EclipseMaven.MavenSchedulingRule baseRule = new EclipseMaven.MavenSchedulingRule();

    EclipseMaven.MavenSchedulingRule relativeRule =
        new EclipseMaven.MavenSchedulingRule( new EclipseMavenProject( new File( "." ) ) );

    final static ISchedulingRule NULL_SCHEDULING_RULE = new ISchedulingRule()
    {

        public boolean isConflicting( ISchedulingRule rule )
        {
            return false;
        }

        public boolean contains( ISchedulingRule rule )
        {
            return false;
        }

    };

    public void testContains()
    {
        assertTrue( baseRule.contains( baseRule ) );
        assertFalse( baseRule.contains( relativeRule ) );
        assertFalse( relativeRule.contains( baseRule ) );
        assertFalse( baseRule.contains( NULL_SCHEDULING_RULE ) );
    }

    // TODO Implement

    public void testIsConflicting()
    {
        assertTrue( baseRule.isConflicting( baseRule ) );
        assertTrue( baseRule.isConflicting( relativeRule ) );
        assertTrue( relativeRule.isConflicting( baseRule ) );

        assertFalse( baseRule.isConflicting( NULL_SCHEDULING_RULE ) );
    }
}
