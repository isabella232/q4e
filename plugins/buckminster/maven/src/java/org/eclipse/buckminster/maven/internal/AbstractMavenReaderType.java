/*****************************************************************************
 * Copyright (c) 2006-2007, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 *****************************************************************************/
/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.buckminster.maven.internal;

import org.eclipse.buckminster.core.cspec.model.ComponentRequest;
import org.eclipse.buckminster.core.helpers.BuckminsterException;
import org.eclipse.buckminster.core.reader.URLCatalogReaderType;
import org.eclipse.buckminster.core.rmap.model.Provider;

public class AbstractMavenReaderType
    extends URLCatalogReaderType
{
    private final LocalCache m_localCache;

    public AbstractMavenReaderType(LocalCache localCache)
    {
        m_localCache = localCache;
    }

    LocalCache getLocalCache()
    {
        return m_localCache;
    }

    protected static MapEntry getGroupAndArtifact( Provider provider, ComponentRequest request )
        throws BuckminsterException
    {
    	String name = request.getName();
    	return (provider instanceof MavenProvider)
    		? ((MavenProvider)provider).getGroupAndArtifact(name)
    		: MavenProvider.getDefaultGroupAndArtifact(name);
    }

}
