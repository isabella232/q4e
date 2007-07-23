/*******************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.devzuz.q.maven.ui.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.actions.helper.GraphHelper;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DependencyGraphDataSource implements IDependencyVisualSource
{
    private static DependencyGraphDataSource dependencyGraphDataSource;
    public static synchronized DependencyGraphDataSource getDependencyGraphDataSource()
    {
        if( dependencyGraphDataSource == null )
            dependencyGraphDataSource = new DependencyGraphDataSource();
        
        return dependencyGraphDataSource;
    }
    
    private DependencyGraphDataSource()
    {
    }

    public InputStream getGraphData( IMavenProject project ) throws CoreException
    {
        ByteArrayInputStream instream = null;
        
        Set<IMavenArtifact> artifacts = project.getArtifacts();
        
        DocumentBuilder builder = null;
        try
        {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            generateTopLevel( doc );

            GraphHelper.getIdGenerator().setKey( doc );

            Map<String, Integer> uniquenessMap = new HashMap<String, Integer>();

            int projId = processProject( doc, uniquenessMap, project.getArtifactId(), project.getGroupId() );

            if ( artifacts != null && artifacts.size() > 0 )
            {
                // process all children
                for ( IMavenArtifact child : artifacts )
                {
                    process( doc, uniquenessMap, child, projId );
                }
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

            DOMSource source = new DOMSource( doc );
            StreamResult result = new StreamResult( byteOut );
            tr.transform( source, result );

            instream = new ByteArrayInputStream( byteOut.toByteArray() );
        }
        catch ( ParserConfigurationException e )
        {
            throw new CoreException(new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Unable to read data source", e ));
        }
        catch ( TransformerFactoryConfigurationError e )
        {
            throw new CoreException(new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Unable to read data source", e ));
        }
        catch ( TransformerException e )
        {
            throw new CoreException(new Status( IStatus.ERROR, Activator.PLUGIN_ID, "Unable to read data source", e ));
        }
        
        return instream;
    }
    
    // duplicate code here - move to helper
    private int processProject( Document document, Map<String, Integer> uMap, String projArtId, String projGrpId )
    {

        NodeList nodes = document.getElementsByTagName( "graph" );
        // nodes should be length 1
        Element graph = (Element) nodes.item( 0 );

        int id = GraphHelper.getIdGenerator().generate( document );

        Element artElem = document.createElement( "node" );
        artElem.setAttribute( "id", "" + id );

        Element dataGroupElem = document.createElement( "data" );
        dataGroupElem.setAttribute( "key", "groupId" );
        dataGroupElem.appendChild( document.createTextNode( projGrpId ) );

        Element dataArtElem = document.createElement( "data" );
        dataArtElem.setAttribute( "key", "artifactId" );
        dataArtElem.appendChild( document.createTextNode( projArtId ) );

        artElem.appendChild( dataGroupElem );
        artElem.appendChild( dataArtElem );
        graph.appendChild( artElem );

        uMap.put( projGrpId + ":" + projArtId, id );

        return id;
    }
    
    // move to helper
    private void process( Document document, Map<String, Integer> uMap, IMavenArtifact artifact, int parentId )
    {

        String uKey = artifact.getGroupId() + ":" + artifact.getArtifactId();

        Integer artifactNodeId = uMap.get( uKey );

        NodeList nodes = document.getElementsByTagName( "graph" );
        // nodes should be length 1
        Element graph = (Element) nodes.item( 0 );

        int id = GraphHelper.getIdGenerator().generate( document );

        if ( artifactNodeId == null )
        {

            Element artElem = document.createElement( "node" );
            artElem.setAttribute( "id", "" + id );

            Element dataGroupElem = document.createElement( "data" );
            dataGroupElem.setAttribute( "key", "groupId" );
            dataGroupElem.appendChild( document.createTextNode( artifact.getGroupId() ) );

            Element dataArtElem = document.createElement( "data" );
            dataArtElem.setAttribute( "key", "artifactId" );
            dataArtElem.appendChild( document.createTextNode( artifact.getArtifactId() ) );

            artElem.appendChild( dataGroupElem );
            artElem.appendChild( dataArtElem );
            graph.appendChild( artElem );

            Element edge = document.createElement( "edge" );
            edge.setAttribute( "source", "" + parentId );
            edge.setAttribute( "target", "" + id );

            graph.appendChild( edge );

            uMap.put( uKey, id );
        }
        else
        {
            Element edge = document.createElement( "edge" );
            edge.setAttribute( "source", "" + parentId );
            edge.setAttribute( "target", "" + artifactNodeId.intValue() );

            graph.appendChild( edge );
        }

        Set<IMavenArtifact> artifacts = artifact.getChildren();

        id = artifactNodeId != null ? artifactNodeId.intValue() : id;

        // now process the children
        if ( artifacts != null && artifacts.size() > 0 )
        {
            for ( IMavenArtifact child : artifacts )
            {
                process( document, uMap, child, id );
            }
        }
    }
    
    // move to appropriate helper
    private void generateTopLevel( Document doc )
        throws ParserConfigurationException
    {

        Element root = doc.createElementNS( "http://graphml.graphdrawing.org/xmlns", "graphml" );
        doc.appendChild( root );

        Element graph = doc.createElement( "graph" );
        graph.setAttribute( "edgedefault", "undirected" );

        root.appendChild( graph );

        generateDataSchema( doc, graph );
    }
    
    // move to appropriate helper
    private void generateDataSchema( Document document, Element graphNode )
    {

        Element key = document.createElement( "key" );
        key.setAttribute( "id", "groupId" );
        key.setAttribute( "for", "node" );
        key.setAttribute( "attr.name", "groupId" );
        key.setAttribute( "attr.type", "string" );
        graphNode.appendChild( key );

        key = document.createElement( "key" );
        key.setAttribute( "id", "artifactId" );
        key.setAttribute( "for", "node" );
        key.setAttribute( "attr.name", "artifactId" );
        key.setAttribute( "attr.type", "string" );
        graphNode.appendChild( key );
    }
}
