/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor.contentassist;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.devzuz.q.maven.search.ArtifactSearchUtils;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.search.SearchCriteria;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Base class for content assist processors that provide assistance for
 * artifact fields.
 * 
 * @author staticsnow@gmail.com
 *
 */
public abstract class AbstractArtifactFieldContentProposer
    implements IElementContentProposer
{

    @Override
    public void propose( ContentAssistRequest contentAssistRequest )
    {
        Node node = contentAssistRequest.getNode();
        if( node instanceof Element )
        {
            if( getNodeName().equals( node.getLocalName() ) )
            {
                String context = ContentAssistUtils.computeContextString( contentAssistRequest );
                
                List<IArtifactInfo> artifacts = ArtifactSearchUtils.findArtifacts( new SearchCriteria( getArtifactIdValue( node ), getGroupIdValue( node ), context, getSearchType() ) );
                Collections.sort( artifacts, getComparator() );
                
                Set<String> proposed = new HashSet<String>(); 
                for ( IArtifactInfo artifactInfo : artifacts )
                {
                    String proposal = extractValue( artifactInfo );
                    if( !proposed.contains( proposal ) )
                    {
                        contentAssistRequest.addProposal( new CompletionProposal( proposal, contentAssistRequest.getReplacementBeginPosition() - context.length(), context.length(), proposal.length() ) );
                        proposed.add( proposal );
                    }   
                }
            }
        }
        
    }
    
    protected abstract String getNodeName();
    protected abstract int getSearchType();
    protected abstract String extractValue( IArtifactInfo ai );
    protected abstract Comparator<IArtifactInfo> getComparator();
    
    protected String getArtifactIdValue( Node node ) 
    {
        String artifactValue = null;
        Node artifactIdNode = findSiblingWithName( node, "artifactId" );
        if( artifactIdNode != null )
        {
            artifactValue = getNodeText( artifactIdNode );
            if( artifactValue != null && artifactValue.trim().length() == 0 )
            {
                artifactValue = null;
            }
        }
        return artifactValue;
    }
    
    protected String getGroupIdValue( Node node )
    {
        String groupValue = null;
        Node groupIdNode = findSiblingWithName( node, "groupId" );
        if( groupIdNode != null )
        {
            groupValue = getNodeText( groupIdNode );
            if( groupValue != null && groupValue.trim().length() == 0 )
            {
                groupValue = null;
            }
        }
        return groupValue;
    }

    private Node findSiblingWithName( Node node, String name )
    {
        Node parent = node.getParentNode();
        Node sibling = parent.getFirstChild();
        while( sibling != null && !name.equals( sibling.getLocalName() ) )
        {
            sibling = sibling.getNextSibling();
        }
        return sibling;
    }

    private String getNodeText( Node node )
    {
        String text = "";
        NodeList children = node.getChildNodes();
        for( int i = 0; i < children.getLength(); i++ )
        {
            Node child = children.item( i );
            String childText = child.getNodeValue();
            if( childText != null )
            {
                text += childText;
            }
        }
        return text;
    }

}