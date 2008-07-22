/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SSESyncResource
    extends ResourceImpl
{
    private volatile boolean processEvents = true;

    private Model pomModel;

    private Document doc;

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
                pomModel = PomFactory.eINSTANCE.createModel();
                doc = domModel.getDocument();
                DocumentAdapter da = new DocumentAdapter();
                if ( doc.getDocumentElement() != null )
                {
                    createAdapterForRootNode( domModel.getDocument().getDocumentElement() ).load();
                }
                else
                {
                    pomModel.eAdapters().add( da );
                }
                ( (IDOMNode) doc ).addAdapter( da );
                this.getContents().add( pomModel );
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

    private SSESyncAdapter createAdapterForRootNode( Element root )
    {
        SSESyncAdapter adapter = new SSESyncAdapter( this, pomModel, root );
        ( (IDOMElement) root ).addAdapter( adapter );
        pomModel.eAdapters().add( adapter );
        return adapter;
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
            // IOException can't wrap another exception before Java 6
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            } else {
                throw new IOException( e.getMessage() );
            }
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

    private class DocumentAdapter
        implements INodeAdapter, Adapter
    {
        private Notifier target;

        public boolean isAdapterForType( Object type )
        {
            return getClass().equals( type );
        }

        public void notifyChanged( Notification notification )
        {
            if ( isProcessEvents() )
            {
                setProcessEvents( false );
                try
                {
                    int type = notification.getEventType();
                    if ( Notification.ADD == type || Notification.ADD_MANY == type || Notification.SET == type )
                    {
                        if ( null == doc.getDocumentElement() )
                        {
                            Element newRoot = doc.createElement( "model" );
                            doc.appendChild( newRoot );
                            createAdapterForRootNode( newRoot ).save();
                        }
                        else
                        {
                            Element root = doc.getDocumentElement();
                            createAdapterForRootNode( root ).load();
                        }
                        DocumentAdapter existingDocAdapter =
                            (DocumentAdapter) EcoreUtil.getExistingAdapter( pomModel, DocumentAdapter.class );
                        if ( null != existingDocAdapter )
                        {
                            pomModel.eAdapters().remove( existingDocAdapter );
                        }
                    }
                }
                finally
                {
                    setProcessEvents( true );
                }
            }

        }

        public void notifyChanged( INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue,
                                   Object newValue, int pos )
        {
            if ( isProcessEvents() )
            {
                setProcessEvents( false );
                try
                {
                    if ( INodeNotifier.ADD == eventType )
                    {
                        if ( newValue instanceof Element )
                        {
                            Element e = (Element) newValue;
                            if ( doc.getDocumentElement().equals( e ) )
                            {
                                DocumentAdapter existingDocAdapter =
                                    (DocumentAdapter) EcoreUtil.getExistingAdapter( pomModel, DocumentAdapter.class );
                                if ( null != existingDocAdapter )
                                {
                                    pomModel.eAdapters().remove( existingDocAdapter );
                                }
                                createAdapterForRootNode( e ).load();
                            }
                        }
                    }
                    else if ( INodeNotifier.REMOVE == eventType )
                    {
                        if ( changedFeature instanceof Element )
                        {
                            SSESyncAdapter existing =
                                (SSESyncAdapter) EcoreUtil.getExistingAdapter( pomModel, SSESyncAdapter.class );
                            if ( existing != null )
                            {
                                pomModel.eAdapters().remove( existing );
                            }

                            if ( null == doc.getDocumentElement() )
                            {
                                for ( EStructuralFeature feature : pomModel.eClass().getEStructuralFeatures() )
                                {
                                    pomModel.eUnset( feature );
                                }
                            }

                            DocumentAdapter existingDocAdapter =
                                (DocumentAdapter) EcoreUtil.getExistingAdapter( pomModel, DocumentAdapter.class );
                            if ( null == existingDocAdapter )
                            {
                                pomModel.eAdapters().add( this );
                            }

                        }
                    }
                }
                finally
                {
                    setProcessEvents( true );
                }
            }
        }

        public Notifier getTarget()
        {
            return target;
        }

        public void setTarget( Notifier newTarget )
        {
            this.target = newTarget;
        }
    }
}
