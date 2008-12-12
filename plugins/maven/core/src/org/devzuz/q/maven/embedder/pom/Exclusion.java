/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import org.dom4j.Element;

public class Exclusion
    extends AbstractDom4JModelElement
{

    Exclusion( Element element )
    {
        super( element );
    }

    static final String NAME = "exclusion";

    static final String ATTR_GROUP_ID = "groupId";

    static final String ATTR_ARTIFACT_ID = "artifactId";

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

    @Override
    protected String getName()
    {
        return NAME;
    }

}
