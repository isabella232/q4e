/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import org.dom4j.Element;

public class Dependency
    extends AbstractDom4JModelElement
{

    Dependency( Element element )
    {
        super( element );
    }

    static final String NAME = "dependency";

    static final String ATTR_GROUP_ID = "groupId";

    static final String ATTR_ARTIFACT_ID = "artifactId";

    static final String ATTR_VERSION = "version";

    static final String ATTR_SCOPE = "scope";

    static final String EXCLUSIONS_NAME = "exclusions";

    public String getGroupId()
    {
        return getTextElement( ATTR_GROUP_ID );
    }

    public void setGroupId( String value )
    {
        setTextElement( ATTR_GROUP_ID, value );
    }

    public String getArtifactId()
    {
        return getTextElement( ATTR_ARTIFACT_ID );
    }

    public void setArtifactId( String value )
    {
        setTextElement( ATTR_ARTIFACT_ID, value );
    }

    public String getVersion()
    {
        return getTextElement( ATTR_VERSION );
    }

    public void setVersion( String value )
    {
        setTextElement( ATTR_VERSION, value );
    }

    public String getScope()
    {
        return getTextElement( ATTR_SCOPE );
    }

    public void setScope( String value )
    {
        setTextElement( ATTR_SCOPE, value );
    }

    public ImplicitCollection<Exclusion> getExclusions()
    {
        return getImplicitCollection( EXCLUSIONS_NAME, Exclusion.NAME, Exclusion.class );
    }

    @Override
    protected String getName()
    {
        return NAME;
    }

}
