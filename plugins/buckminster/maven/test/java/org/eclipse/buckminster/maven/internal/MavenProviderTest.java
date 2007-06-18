/***************************************************************************************************
 * Copyright (c) 2007 Simula Labs All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.eclipse.buckminster.maven.internal;

import junit.framework.TestCase;

public class MavenProviderTest
    extends TestCase
{

    public void testGetGroupAndArtifact()
    {
        MapEntry mapEntry = MavenProvider.getDefaultGroupAndArtifact( "org.mortbay.jetty:servlet-api-2.5" );
        assertEquals( "org.mortbay.jetty", mapEntry.getGroupId() );
        assertEquals( "servlet-api-2.5", mapEntry.getArtifactId() );
    }

}
