/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor;

import org.eclipse.wst.sse.core.internal.document.IDocumentCharsetDetector;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.AbstractModelHandler;
import org.eclipse.wst.sse.core.internal.ltk.modelhandler.IModelHandler;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;
import org.eclipse.wst.xml.core.internal.encoding.XMLDocumentCharsetDetector;
import org.eclipse.wst.xml.core.internal.encoding.XMLDocumentLoader;
import org.eclipse.wst.xml.core.internal.modelhandler.XMLModelLoader;

/**
 * Provides model handling for pom files.
 */
@SuppressWarnings( "restriction" )
public class PomModelHandler
    extends AbstractModelHandler
    implements IModelHandler
{
    private static String modelHandlerID = "org.eclipse.wst.sse.core.handler.pomFile";

    private static String associatedContentTypeID = Activator.PLUGIN_ID + ".pomFile";

    public PomModelHandler()
    {
        setId( modelHandlerID );
        setAssociatedContentTypeId( associatedContentTypeID );
    }

    @Override
    public IDocumentCharsetDetector getEncodingDetector()
    {
        return new XMLDocumentCharsetDetector();
    }

    public IDocumentLoader getDocumentLoader()
    {
        return new XMLDocumentLoader();
    }

    public IModelLoader getModelLoader()
    {
        return new XMLModelLoader();
    }
}
