package org.devzuz.q.maven.pomeditor.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ConfigurationTreeContentProvider
    implements ITreeContentProvider
{    
    private Map<Object, Object> childParentMap;
    
    private Xpp3Dom dom;
    
    public ConfigurationTreeContentProvider( Xpp3Dom dom )
    {
        setDom( dom );
    }
    
    public Xpp3Dom getDom()
    {
        return dom;
    }

    @SuppressWarnings("unchecked")
    public void setDom( Xpp3Dom dom )
    {
        this.dom = dom;
        
        System.out.println("setDom name = " + dom.getName() );
        
        if( childParentMap == null )
        {
            childParentMap = new LinkedHashMap<Object, Object>();
        }
        else
        {
            childParentMap.clear();
        }
        
        setDomObjects( dom );
    }
    
    private void setDomObjects( Xpp3Dom dom )
    {        
        if( dom != null )
        {
            addChildrenToMap( childParentMap , dom );
        }
    }
    
    /*
     * private void addChildrenToMap( Map<Object, Object> map , Xpp3Dom dom ) { if( dom.getChildCount() > 0 ) { for(
     * Xpp3Dom child : dom.getChildren() ) { if( child.getChildCount() > 0 ) { map.put( child , dom ); addChildrenToMap(
     * map , child ); } } } }
     */
    private void addChildrenToMap( Map<Object, Object> map, Xpp3Dom dom )
    {
        for ( Xpp3Dom child : dom.getChildren() )
        {
            System.out.println("addChildrenToMap " + dom.getValue() + " " + dom.getName() );
            map.put( child, dom );
            addChildrenToMap( map, child );
        }
    }

    @SuppressWarnings("unchecked")
    public Object[] getChildren( Object parentElement )
    {
        List< Object > children = new ArrayList< Object >();
        
        // This is a hack for the "Configuration" string to appear
        // in the tree 
        // -start-
        if( parentElement == this.dom )
        {
            return new String[] { "Configuration" };
        }
        else if( parentElement instanceof String )
        {
            parentElement = this.dom;
        }
        // -end-
        
        if( childParentMap.containsValue( parentElement ) )
        {
            for( Object object :  childParentMap.entrySet() )
            {
                Map.Entry< Object , Object > entry = ( Map.Entry< Object , Object > ) object;
                if( entry.getValue() == parentElement )
                {
                    children.add( entry.getKey() );
                }
            }
        }
        
        return children.toArray();
    }

    public Object getParent( Object element )
    {   
        return childParentMap.get( element );
    }

    public boolean hasChildren( Object element )
    {
        return childParentMap.containsValue( element );
    }

    public Object[] getElements( Object inputElement )
    {
        return getChildren( inputElement );
    }

    public void dispose()
    {
        // TODO Auto-generated method stub
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        // TODO Auto-generated method stub
    }
}
