/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views.data;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public abstract class MavenPomParser 
{

    public String pomFileLoc;
    
    static final String JAXP_SCHEMA_SOURCE =
        "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    public String getPomFileLoc()
    {
        return pomFileLoc;
    }
    
    public void parsePOMFile() 
    {
        DocumentBuilderFactory dbf =
            DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);
        dbf.setAttribute(JAXP_SCHEMA_SOURCE, new File(getPomFileLoc()));

        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(false);
        dbf.setCoalescing(false);

        try 
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(getPomFileLoc()));
            recursiveNodeCheck(doc);
        }
        catch(ParserConfigurationException pce)
        {
            //System.out.println("ParserConfigurationException " + pce.getMessage());
        }
        catch(SAXException se)
        {
            //System.out.println("SAXException " + se.getMessage());
        }
        catch(IOException ioe)
        {
           // System.out.println("IOException " + ioe.getMessage());  
        }
    }

    public abstract void recursiveNodeCheck(Node n); 
    
}
