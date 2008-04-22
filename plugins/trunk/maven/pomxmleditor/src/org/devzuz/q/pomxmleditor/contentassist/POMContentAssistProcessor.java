/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor.contentassist;

import java.util.List;

import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.internal.contentassist.XMLContentAssistProcessor;

/**
 * Uses a series of ContentProposers to add proposals for the current element content.
 * 
 * @author staticsnow@gmail.com
 *
 */
public class POMContentAssistProcessor
    extends XMLContentAssistProcessor
{
    private List<IElementContentProposer> proposers;
    
    
    public POMContentAssistProcessor( List<IElementContentProposer> proposers )
    {
        super();
        this.proposers = proposers;
    }


    @Override
    protected void addTagInsertionProposals( ContentAssistRequest contentAssistRequest, int childPosition )
    {
        super.addTagInsertionProposals( contentAssistRequest, childPosition );
        for ( IElementContentProposer proposer : proposers )
        {
            proposer.propose( contentAssistRequest );
        }
    }
}
