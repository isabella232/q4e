package org.devzuz.q.maven.jdt.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class Messages
{
    private static final String BUNDLE_NAME = Activator.PLUGIN_ID + ".messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle( BUNDLE_NAME );

    public static String MavenPomEditor_MavenPomEditor_BasicInformation;

    public static String MavenPomEditor_MavenPomEditor_Links;

    public static String MavenPomEditor_MavenPomEditor_ParentPOM;

    public static String MavenPomEditor_MavenPomEditor_MoreProjInfo;

    public static String MavenPomEditor_MavenPomEditor_Properties;

    public static String MavenPomEditor_MavenPomEditor_GroupId;

    public static String MavenPomEditor_MavenPomEditor_ArtifactId;

    public static String MavenPomEditor_MavenPomEditor_Version;

    public static String MavenPomEditor_MavenPomEditor_Type;

    public static String MavenPomEditor_MavenPomEditor_Scope;

    public static String MavenPomEditor_MavenPomEditor_SystemPath;

    public static String MavenPomEditor_MavenPomEditor_Optional;

    public static String MavenPomEditor_MavenPomEditor_Packaging;

    public static String MavenPomEditor_MavenPomEditor_Classifier;

    public static String MavenPomEditor_MavenPomEditor_RelativePath;

    public static String MavenPomEditor_MavenPomEditor_AddButton;

    public static String MavenPomEditor_MavenPomEditor_EditButton;

    public static String MavenPomEditor_MavenPomEditor_RemoveButton;

    public static String MavenPomEditor_MavenPomEditor_Key;

    public static String MavenPomEditor_MavenPomEditor_Value;

    public static String MavenPomEditor_MavenPomEditor_Modules;

    public static String MavenPomEditor_MavenPomEditor_Module;

    public static String MavenPomEditor_MavenPomEditor_Name;

    public static String MavenPomEditor_MavenPomEditor_Description;

    public static String MavenPomEditor_MavenPomEditor_URL;

    public static String MavenPomEditor_MavenPomEditor_InceptionYear;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }

    /**
     * @deprecated use static variables
     */
    public static String getString( String key )
    {
        try
        {
            return RESOURCE_BUNDLE.getString( key );
        }
        catch ( MissingResourceException e )
        {
            return '!' + key + '!';
        }
    }
}
