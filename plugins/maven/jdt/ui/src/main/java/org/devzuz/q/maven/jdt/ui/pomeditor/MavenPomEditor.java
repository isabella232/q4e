package org.devzuz.q.maven.jdt.ui.pomeditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

public class MavenPomEditor extends EditorPart
{
    private FormToolkit toolkit;
    private ScrolledForm form;
    
    @Override
    public void doSave( IProgressMonitor monitor )
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void doSaveAs()
    {
        
    }

    @Override
    public void init( IEditorSite site, IEditorInput input ) throws PartInitException
    {
        System.out.println("Trace - init( IEditorSite site, IEditorInput input ).");
        if( input instanceof IFileEditorInput )
        {
            setSite( site );
            setInput( input );
        }
    }

    @Override
    public boolean isDirty()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSaveAsAllowed()
    {
        return false;
    }

    @Override
    public void createPartControl( Composite parent )
    {
        toolkit = new FormToolkit( parent.getDisplay() );
        form = toolkit.createScrolledForm( parent );
        form.setText( "Maven 2 POM Editor Should be here." );
    }

    @Override
    public void setFocus()
    {
        // TODO Auto-generated method stub

    }

}
