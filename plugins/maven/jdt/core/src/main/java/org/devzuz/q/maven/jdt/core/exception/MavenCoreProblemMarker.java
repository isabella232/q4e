/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.jdt.core.exception;

import org.eclipse.core.resources.IMarker;


public class MavenCoreProblemMarker
{
	//public static final int POM_MISSING_ARTIFACTS = 1;
	//public static final int POM_MISSING_DEPENDENCY= 2;
	private static MavenCoreProblemMarker mavenMarker = null;
	
	
	

	 public static synchronized MavenCoreProblemMarker getMavenCoreProblemMarker()
	 {
	        if ( mavenMarker == null )
	        {
	            mavenMarker = new MavenCoreProblemMarker();
	        }

	        return mavenMarker;
	 }
	 
	public void outPomMarkerProblem (String problem)
	{
		System.out.println("Problem : " + problem);
	}
	
	
	
}