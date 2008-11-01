/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.actions.helper;

public class GraphHelper
{

    private static IdGenerator idGenerator;

    public static final String NODE_ELEM = "node";

    public static final String EDGE_ELEM = "edge";

    public static final String GRAPH_ELEM = "graph";

    public static final String GRAPHML_ELEM = "graphml";

    public static final String ID_ATTR = "id";

    public static final String KEY_ATTR = "key";

    public static final String SOURCE_ATTR = "source";

    public static final String TARGET_ATTR = "target";

    public static final String EDGE_DEFAULT_ATTR = "edgedefault";

    public static final String UNDIRECTED_VALUE = "undirected";

    public static final String GRAPHML_NS = "http://graphml.graphdrawing.org/xmlns";

    public static IdGenerator getIdGenerator()
    {
        if ( idGenerator == null )
            idGenerator = new SimpleIdGenerator();
        return idGenerator;
    }
}
