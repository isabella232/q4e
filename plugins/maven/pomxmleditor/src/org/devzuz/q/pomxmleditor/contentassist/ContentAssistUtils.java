/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor.contentassist;

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;

public class ContentAssistUtils
{
    public static String computeContextString( ContentAssistRequest request )
    {
        String context = "";
        IStructuredDocumentRegion region = request.getDocumentRegion();
        if( DOMRegionContext.XML_TAG_NAME.equals( region.getType() ) )
        {
            region = region.getPrevious();
        }
        if( DOMRegionContext.XML_TAG_NAME.equals( region.getType() ) )
        {
            return "";
        }
        int cursorOffset = request.getReplacementBeginPosition() - region.getStart();
        cursorOffset += request.getReplacementLength();
        if( cursorOffset > -1 )
        {
            String value = region.getFullText();
            context = value.substring( 0, cursorOffset );
        }
        return context;
    }
    
}
