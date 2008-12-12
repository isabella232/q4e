/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Exception that will expose the nested exception from the {@link CoreException} Status in the standard JDK 1.4 API
 * 
 * @author <a href="mailto:carlos@apache.org">Carlos Sanchez</a>
 * @version $Id$
 */
public class QCoreException extends CoreException
{

    public QCoreException( IStatus status )
    {
        super( status );
    }

    @Override
    public Throwable getCause()
    {
        return getStatus().getException();
    }

}
