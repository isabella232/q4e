package org.devzuz.q.maven.dependency.analysis.extension;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;

public class SelectionExtension
{

    public static final String EXTENSION_POINT = DependencyAnalysisActivator.PLUGIN_ID + ".selection";

    public static final String ATTR_MENU_LABEL = "menu-label";

    public static final String ATTR_CLASS = "implementation";

}
