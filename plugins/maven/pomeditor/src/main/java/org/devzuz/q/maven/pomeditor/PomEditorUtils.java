/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor;

import org.apache.maven.model.Build;

public class PomEditorUtils
{
    public static boolean isBuildNull( Build build )
    {
        return ( build.getFinalName() == null && build.getDirectory() == null && build.getOutputDirectory() == null &&
                 build.getTestOutputDirectory() == null && build.getSourceDirectory() == null &&
                 build.getScriptSourceDirectory() == null && build.getTestSourceDirectory() == null &&
                 build.getExtensions().size() <= 0 && build.getResources().size() <= 0 &&
                 build.getTestResources().size() <= 0 && build.getPlugins().size() <= 0 &&
                 build.getPluginManagement() == null && build.getDefaultGoal() == null && 
                 build.getFilters().size() <= 0 );
    }
    
    public static boolean isNullOrWhiteSpace( String str )
    {
        return ( str == null || str.trim().length() == 0 );
    }
}
