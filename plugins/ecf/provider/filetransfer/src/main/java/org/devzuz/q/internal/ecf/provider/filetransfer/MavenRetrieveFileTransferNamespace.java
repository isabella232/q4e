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

import java.net.URL;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDCreateException;
import org.eclipse.ecf.provider.filetransfer.identity.FileTransferNamespace;

public class MavenRetrieveFileTransferNamespace
    extends FileTransferNamespace
{

    public static final String NAMESPACE_NAME = "maven.filetransfer.namespace";

    public static final String SCHEME = "maven";

    private static final long serialVersionUID = -5216527030990468831L;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ecf.core.identity.Namespace#createInstance(java.lang.Object[])
     */
    public ID createInstance( Object[] args )
        throws IDCreateException
    {
        if ( args == null || args.length == 0 )
            throw new IDCreateException( "arguments is null or empty" );
        try
        {
            if ( args[0] instanceof URL )
            {
                URL u = (URL) args[0];
                String protocol = u.getProtocol();
                if ( protocol != null && protocol.equals( SCHEME ) )
                {
                    return new MavenRetrieveFileTransferID( this, ( (URL) args[0] ).getPath() );
                }
                else
                    return new MavenRetrieveFileTransferID( this, u.toString() );
            }
            if ( args[0] instanceof String )
                return new MavenRetrieveFileTransferID( this, (String) args[0] );
        }
        catch ( Exception e )
        {
            throw new IDCreateException( "Exception in createInstance", e );
        }
        throw new IDCreateException( "arguments not correct to create instance of MavenRetrieveFileTransferNamespace" );
    }

    public String getScheme()
    {
        return SCHEME;
    }

}
