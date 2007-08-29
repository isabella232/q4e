/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor.pomreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MavenPOMParser 
{

    private File pomFile;
    
    private static NodeList xPathNodeList;
    
    public MavenPOMParser(File pomFileLocation)
    {
        this.pomFile = pomFileLocation;
    }

    public File getPOMFile()
    {
        return pomFile;
    }
    
    public void parsePOMFile(String strExpression) 
    {
        try 
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document docPOMFile = db.parse(getPOMFile()); 
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            xPath.setNamespaceContext(new MavenNameSpaceContext());
            XPathExpression expr = xPath.compile(strExpression);
            Object result = expr.evaluate(docPOMFile, XPathConstants.NODESET);
            xPathNodeList = (NodeList) result;
/*            
 *          for (int i = 0; i < xPathNodeList.getLength(); i++) 
            {
                System.out.println("item :" + i + xPathNodeList.item(i).getNodeValue()); 
            }
            */
        }
        catch(ParserConfigurationException pce)
        {
            // TODO Auto-generated catch block
            pce.printStackTrace();
        }
        catch(SAXException se)
        {
            // TODO Auto-generated catch block
            se.printStackTrace();
        }
        catch(IOException ioe)
        {
            // TODO Auto-generated catch block
            ioe.printStackTrace();
        }
        catch ( XPathExpressionException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public NodeList getXPathNodeList()
    {
        return xPathNodeList;
    }
    
}
