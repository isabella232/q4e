/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import org.dom4j.Element;

public class DependencyManagement
    extends AbstractDom4JModelElement
{

    DependencyManagement( Element element )
    {
        super( element );
    }

    static final String NAME = "dependencyManagement";

    static final String DEPENDENCIES_NAME = "dependencies";

    public ImplicitCollection<Dependency> getDependencies()
    {
        return getImplicitCollection( DEPENDENCIES_NAME, Dependency.NAME, Dependency.class );
    }

    @Override
    protected String getName()
    {
        return NAME;
    }
}
