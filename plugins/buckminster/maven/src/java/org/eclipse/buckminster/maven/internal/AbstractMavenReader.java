/*******************************************************************************
 * Copyright (c) 2004, 2006
 * Thomas Hallgren, Kenneth Olwing, Mitch Sonies
 * Pontus Rydin, Nils Unden, Peer Torngren
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the individual
 * copyright holders listed above, as Initial Contributors under such license.
 * The text of such license is available at www.eclipse.org.
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.buckminster.maven.internal;

import org.eclipse.buckminster.core.reader.URLFileReader;
import org.eclipse.buckminster.core.version.ProviderMatch;
import org.eclipse.core.runtime.CoreException;

public class AbstractMavenReader extends URLFileReader
{
    public AbstractMavenReader(AbstractMavenReaderType readerType, ProviderMatch rInfo) throws CoreException
    {
        super(readerType, rInfo);
    }

    IVersionSelector getVersionSelector() throws CoreException
    {
        return getProviderMatch().getVersionMatch().getFixedVersionSelector();
    }

}
