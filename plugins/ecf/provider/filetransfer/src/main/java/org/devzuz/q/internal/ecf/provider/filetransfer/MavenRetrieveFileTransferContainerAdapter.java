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

import org.eclipse.ecf.core.identity.IDFactory;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.provider.filetransfer.retrieve.MultiProtocolRetrieveAdapter;

public class MavenRetrieveFileTransferContainerAdapter
    extends MultiProtocolRetrieveAdapter
{

    public Namespace getRetrieveNamespace()
    {
        return IDFactory.getDefault().getNamespaceByName( MavenRetrieveFileTransferNamespace.NAMESPACE_NAME );
    }
}
