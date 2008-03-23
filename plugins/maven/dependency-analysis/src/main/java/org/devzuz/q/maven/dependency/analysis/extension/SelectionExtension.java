package org.devzuz.q.maven.dependency.analysis.extension;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;

public class SelectionExtension
{
    /*
     * The below constants must match the relevant element name in the extension point schema
     */

    public static final String EXTENSION_POINT = DependencyAnalysisActivator.PLUGIN_ID + ".selection";

    public static final String ATTR_MENU_LABEL = "menu-label";

    public static final String ATTR_CLASS = "implementation";

    public static final String ATTR_SHOW_IN_VIEW = "show-in-view";

    public static final String ATTR_VIEW_NAME = "view-name";

    public enum Menu
    {
        INSTANCES, VERSIONS, ARTIFACTS;
    };

}
