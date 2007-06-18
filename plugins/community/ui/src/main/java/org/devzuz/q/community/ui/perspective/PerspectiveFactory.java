/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.community.ui.perspective;

import org.devzuz.q.community.ui.views.CommunityView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveFactory
    implements IPerspectiveFactory
{

    public void createInitialLayout( IPageLayout layout )
    {
        // Editors are placed for free.
        String editorArea = layout.getEditorArea();

        IFolderLayout left = layout.createFolder( "left", IPageLayout.LEFT, (float) 0.26, editorArea );
        left.addView( CommunityView.class.getName() );

    }

}
