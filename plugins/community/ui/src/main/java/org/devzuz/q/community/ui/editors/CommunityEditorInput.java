/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.editors;

import org.devzuz.q.community.core.model.Community;
import org.eclipse.ui.internal.part.NullEditorInput;

public class CommunityEditorInput
    extends NullEditorInput
{

    Community community;

    public CommunityEditorInput( Community community )
    {
        this.community = community;
    }

    public Community getCommunity()
    {
        return community;
    }

    public void setCommunity( Community community )
    {
        this.community = community;
    }

}
