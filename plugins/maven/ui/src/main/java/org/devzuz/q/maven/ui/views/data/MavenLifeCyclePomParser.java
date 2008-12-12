/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views.data;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class MavenLifeCyclePomParser 
	extends MavenPomParser
{
    private String phaseName;
    
    private String goal;
    
    private String groupId;
    
    private String artifactId;
    
    private boolean prjPhaseAndGoalFlag=false;
    
    private String projectName;
    
    private TableViewer tblViewer;

    public MavenLifeCyclePomParser(TableViewer tblViewer, String projectName, IPath pomFileIPath)            
    {
        this.projectName = projectName;
        this.tblViewer = tblViewer;
        super.pomFileLoc = pomFileIPath.toOSString();        
    }
    
	@Override
	public void recursiveNodeCheck(Node n) 
	{
        int type = n.getNodeType();
        if(type == Node.ELEMENT_NODE)
        {
            checkPhaseAndGoal(n);
            NamedNodeMap atts = n.getAttributes();
            for (int i = 0; i < atts.getLength(); i++) 
            {
                Node att = atts.item(i);
                recursiveNodeCheck(att);
            }
        }

        for (Node child = n.getFirstChild(); child != null;
             child = child.getNextSibling()) 
        {
            recursiveNodeCheck(child);
        }		
	}
  	
    private void checkPhaseAndGoal(Node n)
    {
        String val = n.getNodeName();
        
        String phase        = "";
        String goal         = "";
        String groupid      = "";
        String artifactid   = "";
        
        if(val.equals( "groupId" ))
        {
            Node t = n.getParentNode();
            if(t.getNodeName().equals( "plugin" ))
            {
                groupid = n.getFirstChild().getNodeValue();
                if(groupid != null) 
                {
                    this.setGroupId( groupid );
                }
            }
        }
        
        if(val.equals( "artifactId" ))
        {
            Node t = n.getParentNode();
            if(t.getNodeName().equals( "plugin" ))
            {
                artifactid = n.getFirstChild().getNodeValue();
                if(artifactid != null) 
                {
                    this.setArtifactId(artifactid);
                }
            }
        }
        
        if(val.equals( "phase" ))
        {
            phase = n.getFirstChild().getNodeValue();
            if(phase != null)
            {             
                this.setPhasename( phase );
            }
        }
        
        if(val.equals( "goal" ))
        {
            goal = n.getFirstChild().getNodeValue();            
            if(goal != null)
            {
            	prjPhaseAndGoalFlag = true;
            	this.setGoal( goal );
            	TableItem item = new TableItem( tblViewer.getTable(), 
                                               SWT.BEGINNING );
                if(getPhaseName() != "")
                {
                	item.setText(  new String[] { getProjectName(),
                			                      getGroupId()+":"+this.getArtifactId(), 
                                                  getPhaseName() , 
                                                  getGoal() } ); 
                }
                else
                {
                	this.setPhasename("");
                }
                this.setGoal("" );   
             
            }
        }
        
    }

    public boolean getPrjPhaseAndGoalFlag()
    {
        return this.prjPhaseAndGoalFlag;
    }
    
    private void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }
    
    private void setArtifactId(String artifactId)
    {
        this.artifactId = artifactId;
    }
    
    private void setPhasename(String phase)
    {
        this.phaseName = phase;
    }
    
    private void setGoal(String goal)
    {
        this.goal = goal;
    }
    
    public String getGroupId()
    {
        if(groupId!= null)
            return groupId;
        return "";
    }
    
    public String getArtifactId()
    {
        if(artifactId!= null)
            return artifactId;
        return "";   
    }
    
    public String getPhaseName()
    {
        if(phaseName!= null)
            return phaseName;
        return "";
    }
    
    public String getGoal()
    {
        if(goal!=null)
            return goal;
        return "";
    }
    
    public String getProjectName()
    {
        return projectName;
    }

}
