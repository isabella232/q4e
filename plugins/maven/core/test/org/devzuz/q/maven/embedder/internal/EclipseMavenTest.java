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

import org.devzuz.q.maven.embedder.TestableMavenCoreActivator;
import org.devzuz.q.maven.embedder.test.EclipseMavenForTesting;

public class EclipseMavenTest extends TestCase
{
    private static final String TEST_DIR = "test";

    private EclipseMavenForTesting mavenInstance;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        mavenInstance = new EclipseMavenForTesting();
        mavenInstance.start();
        TestableMavenCoreActivator activator = new TestableMavenCoreActivator();
        activator.setMavenInstance( mavenInstance );
    }

    @Override
    protected void tearDown() throws Exception
    {
        mavenInstance.stop();
        super.tearDown();
    }

    public void testReadPomWithDistributionStatus() throws Exception
    {
        File testPom = new File( "test/pom-with-distribution-status.xml" );
        mavenInstance.getMavenProject( testPom, true );
        mavenInstance.getMavenProject( testPom, false );
    }

}
