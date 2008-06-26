/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.pom.translators;

import java.util.Collections;
import java.util.List;

import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.w3c.dom.Node;

class ValueUpdateAdapter 
    implements INodeAdapter, HasLinkedWhitespaceNodes
{
    /**
     * 
     */
    private final SSESyncAdapter syncAdapter;
    private List<Node> linkedWhitespaceNodes = Collections.emptyList();
    public ValueUpdateAdapter(SSESyncAdapter syncAdapter )
    {
        super();
        this.syncAdapter = syncAdapter;
    }

    public boolean isAdapterForType( Object type )
    {
        return true;
    }
    
    public void notifyChanged( INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue,
                               Object newValue, int pos )
    {
        this.syncAdapter.notifyChanged( notifier, eventType, changedFeature, oldValue, newValue, pos );
    }

    public List<Node> getLinkedWhitespaceNodes()
    {
        return linkedWhitespaceNodes;
    }

    public void setLinkedWhitespaceNodes( List<Node> linkedWhitespaceNodes )
    {
        this.linkedWhitespaceNodes = linkedWhitespaceNodes;
    }
}