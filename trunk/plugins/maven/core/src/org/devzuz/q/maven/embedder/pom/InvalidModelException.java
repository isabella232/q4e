/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

public class InvalidModelException
    extends RuntimeException
{

    public InvalidModelException()
    {
    }

    public InvalidModelException( String message )
    {
        super( message );
    }

    public InvalidModelException( Throwable cause )
    {
        super( cause );
    }

    public InvalidModelException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
