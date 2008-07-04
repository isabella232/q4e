package org.devzuz.q.maven.pomeditor.model;

import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ConfigurationTreeLabelProvider
    implements ILabelProvider
{

    public Image getImage( Object element )
    {        
        return null;
    }

    @SuppressWarnings("unchecked")
    public String getText( Object element )
    {
        if ( element instanceof Xpp3Dom )
        {
            Xpp3Dom dom = ( Xpp3Dom ) element;
            System.out.println("getText element is Xpp3Dom");
            System.out.println("getText " + dom.getName() + " " + dom.getValue() );
            if( dom.getName().equalsIgnoreCase( "configuration" ) )
            {
                System.out.println("moogle testing dom");
                return "Configuration";
            }
            else
            {
                String value = dom.getValue();
                if ( value != null )
                {
                    StringBuffer configString = new StringBuffer();
                    configString.append( "Configuration { " );
                    configString.append( dom.getName() );
                    configString.append( "," );
                    configString.append( dom.getValue() );
                    configString.append( " }" );
                    
                    return configString.toString();
                }
                else
                {
                    return dom.getName();
                }
            }
        }
        
        return null;        
    }

    public void addListener( ILabelProviderListener listener )
    {
        // TODO Auto-generated method stub

    }

    public void dispose()
    {
        // TODO Auto-generated method stub

    }

    public boolean isLabelProperty( Object element, String property )
    {
        // TODO Auto-generated method stub
        return false;
    }

    public void removeListener( ILabelProviderListener listener )
    {
        // TODO Auto-generated method stub

    }

}
