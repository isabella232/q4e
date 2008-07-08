package org.devzuz.q.maven.pom.translators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

public class SSESyncResource
    extends ResourceImpl
{
    private volatile boolean processEvents = true;

    public SSESyncResource()
    {
        super();
    }

    public SSESyncResource( URI uri )
    {
        super( uri );
    }

    @Override
    public void load( Map<?, ?> options )
        throws IOException
    {
        String localPath = getURI().toPlatformString( true );
        IPath path = Path.fromPortableString( localPath );
        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
        if ( file.exists() )
        {
            setProcessEvents( false );
            try
            {
                IDOMModel domModel = buildModel( file );
                Model model = PomFactory.eINSTANCE.createModel();
                SSESyncAdapter adapter = new SSESyncAdapter( this, model, domModel.getDocument().getDocumentElement() );
                ( (IDOMElement) domModel.getDocument().getDocumentElement() ).addAdapter( adapter );
                model.eAdapters().add( adapter );
                adapter.load();
                this.getContents().add( model );
                this.setLoaded( true );
                domModel.releaseFromEdit();
            }
            finally
            {
                setProcessEvents( true );
            }

        }
        else
        {
            throw new FileNotFoundException( file.toString() );
        }
    }

    private synchronized IDOMModel buildModel( IFile file )
        throws IOException
    {
        try
        {
            IModelManager modelManager = StructuredModelManager.getModelManager();
            IDOMModel model = (IDOMModel) modelManager.getExistingModelForEdit( file );
            if ( null == model )
            {
                model = (IDOMModel) modelManager.getModelForEdit( file );
            }
            return model;
        }
        catch ( CoreException e )
        {
            throw new IOException( e );
        }
    }

    boolean isProcessEvents()
    {
        return processEvents;
    }

    void setProcessEvents( boolean processEvents )
    {
        this.processEvents = processEvents;
    }
}
