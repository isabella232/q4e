/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.internal.ecf.provider.filetransfer;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;

public class MavenRetrieveFileTransferAdapterFactory
    implements IAdapterFactory
{

    public Object getAdapter( Object adaptableObject, Class adapterType )
    {
        if ( adapterType.equals( IRetrieveFileTransferContainerAdapter.class ) )
            return new MavenRetrieveFileTransferContainerAdapter();
        return null;
    }

    public Class[] getAdapterList()
    {
        return new Class[] { IRetrieveFileTransferContainerAdapter.class };
    }

}
