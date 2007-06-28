package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class MavenPomDependenciesFormPage extends FormPage
{

    public MavenPomDependenciesFormPage( String id, String title )
    {
        super( id, title );
    }

    public MavenPomDependenciesFormPage( FormEditor editor, String id, String title )
    {
        super( editor, id, title );
    }

    @Override
    protected void createFormContent( IManagedForm managedForm )
    {
        
    }
}
