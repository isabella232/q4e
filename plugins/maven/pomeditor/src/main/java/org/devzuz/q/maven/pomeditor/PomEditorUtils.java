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
