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

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.provider.filetransfer.identity.FileTransferID;

public class MavenRetrieveFileTransferID
    extends FileTransferID
{

    private static final long serialVersionUID = -5870368015878068868L;

    public MavenRetrieveFileTransferID( Namespace namespace, String fullURL )
        throws MalformedURLException
    {
        super( namespace, new URL( fullURL ) );
    }

}
