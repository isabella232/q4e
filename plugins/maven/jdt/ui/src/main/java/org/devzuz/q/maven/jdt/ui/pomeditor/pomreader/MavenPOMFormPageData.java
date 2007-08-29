/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.pomeditor.pomreader;

import java.io.File;

import org.w3c.dom.NodeList;

public class MavenPOMFormPageData
{
    private MavenPOMParser mavenPOMParser;
    
    public MavenPOMFormPageData(File strPOMLocation)
    {
        this.mavenPOMParser = new MavenPOMParser(strPOMLocation);
    }
    
    public void doXPathExpression(String strSearchExpr)
    {
        mavenPOMParser.parsePOMFile(strSearchExpr);         
    }
    
    private NodeList getNodeList()
    {
        return mavenPOMParser.getXPathNodeList();
    }
    
    public String[] processNodeList()
    {
        String [] strNodeItems={""};
        for(int i =0; i < getNodeList().getLength() ; i ++)
        {
            strNodeItems[i] = getNodeList().item(i).getNodeValue();
        }
        return strNodeItems;
    }
    
    
}
