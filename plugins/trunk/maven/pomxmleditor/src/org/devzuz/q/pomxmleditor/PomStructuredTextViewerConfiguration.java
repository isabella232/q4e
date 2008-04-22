/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor;

import java.util.Arrays;

import org.devzuz.q.pomxmleditor.contentassist.ArtifactIdContentProposer;
import org.devzuz.q.pomxmleditor.contentassist.GroupIdContentProposer;
import org.devzuz.q.pomxmleditor.contentassist.POMContentAssistProcessor;
import org.devzuz.q.pomxmleditor.contentassist.PropertyContentProposer;
import org.devzuz.q.pomxmleditor.contentassist.VersionContentProposer;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.wst.sse.core.text.IStructuredPartitions;
import org.eclipse.wst.xml.core.text.IXMLPartitions;
import org.eclipse.wst.xml.ui.StructuredTextViewerConfigurationXML;

public class PomStructuredTextViewerConfiguration
    extends StructuredTextViewerConfigurationXML
{
    @Override
    public IContentAssistProcessor[] getContentAssistProcessors( ISourceViewer sourceViewer, String partitionType )
    {

        IContentAssistProcessor[] processors;

        if ( partitionType == IStructuredPartitions.DEFAULT_PARTITION || partitionType == IXMLPartitions.XML_DEFAULT )
        {
            processors = new IContentAssistProcessor[] { new POMContentAssistProcessor( Arrays.asList( new PropertyContentProposer(), new GroupIdContentProposer(), new ArtifactIdContentProposer(), new VersionContentProposer() ) ) };
        }
        else
        {
            processors = super.getContentAssistProcessors( sourceViewer, partitionType );
        }
        return processors;
    }
}
