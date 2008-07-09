package org.devzuz.q.maven.pomeditor.model;

import org.codehaus.plexus.util.xml.Xpp3Dom;

public class DomContainer
{
    private Xpp3Dom dom;
    
    public DomContainer( )
    {
    }
    
    public DomContainer( Xpp3Dom dom )
    {
        this.dom = dom;
    }
    
    public void setDom( Xpp3Dom dom )
    {
        this.dom = dom;
    }
    
    public Xpp3Dom getDom()
    {
        return dom;
    }
}
