package org.devzuz.q.maven.ui.preferences.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.devzuz.q.maven.ui.customcomponents.MavenProfileUi;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MavenProfileFieldEditor
    extends FieldEditor
{
    private MavenProfileUi profileUi;

    public MavenProfileFieldEditor( String name, String labelText, Composite parent )
    {
        init( name, labelText );
        createControl( parent );
    }

    @Override
    protected void adjustForNumColumns( int numColumns )
    {
        Control control = getLabelControl();
        ( (GridData) control.getLayoutData() ).horizontalSpan = numColumns;
    }

    @Override
    protected void doFillIntoGrid( Composite parent, int numColumns )
    {
        Control control = getLabelControl( parent );
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData( gd );

        List<String> profiles =
            new ArrayList<String>( MavenUIPreferenceManagerAdapter.getInstance().getConfiguredProfiles() );
        
        profileUi = new MavenProfileUi( parent, profiles, numColumns );
        profileUi.draw();
    }

    @Override
    protected void doLoad()
    {
    }

    @Override
    protected void doLoadDefault()
    {

    }

    @Override
    protected void doStore()
    {
        MavenUIPreferenceManagerAdapter.getInstance().setConfiguredProfiles(
                                                                             new HashSet<String>(
                                                                                                  profileUi.getProfiles() ) );
    }

    @Override
    public int getNumberOfControls()
    {
        return 2;
    }
}
