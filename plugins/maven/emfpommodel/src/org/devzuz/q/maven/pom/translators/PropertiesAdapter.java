package org.devzuz.q.maven.pom.translators;

import java.util.List;

import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PropertyElement;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Translates a property list using the name and value instead of reflection.
 * @author Mike Poindexter
 *
 */
public class PropertiesAdapter
    extends ListAdapter
{
    protected List<PropertyElement> properties;

    public PropertiesAdapter( SSESyncResource resc, Element containerNode, List<PropertyElement> properties )
    {
        super( resc, containerNode, properties, null );
        this.node = containerNode;
        this.properties = properties;
    }

    @Override
    public void notifyChanged( INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue,
                               Object newValue, int pos )
    {
        if ( resource.isProcessEvents() )
        {
            try
            {
                resource.setProcessEvents( false );
                if ( INodeNotifier.ADD == eventType && newValue instanceof Element )
                {
                    if ( notifier == node )
                    {
                        IDOMElement addedElement = (IDOMElement) newValue;
                        int idx = absoluteIndexOf( node, addedElement );
                        if ( idx == -1 )
                            idx = 0;
                        properties.add( idx, createObject( addedElement ) );
                    }
                }
                else if ( INodeNotifier.REMOVE == eventType && oldValue instanceof Element )
                {
                    if ( notifier == node )
                    {
                        int idx = absoluteIndexOf( node, (Element) oldValue );
                        if ( idx == -1 )
                            idx = 0;
                        properties.remove( idx );
                    }
                }
                else if ( changedFeature instanceof Text )
                {
                    if ( notifier != node && notifier instanceof Element )
                    {
                        Element e = (Element) notifier;
                        String name = e.getLocalName();
                        for ( PropertyElement prop : properties )
                        {
                            if ( name.equals( prop.getName() ) )
                            {
                                prop.setValue( getElementText( e ) );
                            }
                        }
                    }
                }
            }
            finally
            {
                resource.setProcessEvents( true );
            }

        }

    }

    public void add( Object newValue, int position )
    {
        final PropertyElement prop = (PropertyElement) newValue;
        prop.eAdapters().add( new Adapter()
        {
            private Notifier notifier;

            public boolean isAdapterForType( Object type )
            {
                return this.getClass().equals( type );
            }

            public void notifyChanged( Notification notification )
            {
                if ( resource.isProcessEvents() )
                {
                    try
                    {
                        resource.setProcessEvents( false );
                        int idx = properties.indexOf( prop );
                        Node n = getNthChildWithName( node, "*", idx );
                        Element newElement = node.getOwnerDocument().createElement( prop.getName() );
                        Text value = node.getOwnerDocument().createTextNode( prop.getValue() );
                        newElement.appendChild( value );
                        node.replaceChild( n, newElement );
                        formatNode( newElement );
                        ( (IDOMNode) newElement ).addAdapter( PropertiesAdapter.this );

                    }
                    finally
                    {
                        resource.setProcessEvents( true );
                    }
                }
            }

            public Notifier getTarget()
            {
                return notifier;
            }

            public void setTarget( Notifier newTarget )
            {
                this.notifier = newTarget;
            }
        } );
        Element newElement = node.getOwnerDocument().createElement( prop.getName() );
        Text value = node.getOwnerDocument().createTextNode( prop.getValue() );
        newElement.appendChild( value );

        if ( position < 0 )
            position = 0;
        Node n = getNthChildWithName( node, "*", position );
        if ( n != null )
        {
            node.insertBefore( newElement, n );
        }
        else
        {
            node.appendChild( newElement );
        }
        formatNode( newElement );
        ( (IDOMNode) newElement ).addAdapter( this );
    }

    public void remove( Object oldValue, int position )
    {
        if ( position == -1 )
            position = 0;

        Element n = getNthChildWithName( node, "*", position );
        if ( n != null )
            removeChildElement( n );
    }

    @Override
    public void update( Object oldValue )
    {
        // TODO Auto-generated method stub

    }

    public PropertyElement createObject( Element child )
    {
        PropertyElement propertyElement = PomFactory.eINSTANCE.createPropertyElement();
        propertyElement.setName( child.getLocalName() );
        propertyElement.setValue( getElementText( child ) );
        if ( null == ( (IDOMNode) child ).getExistingAdapter( PropertiesAdapter.class ) )
        {
            ( (IDOMNode) child ).addAdapter( this );
        }
        return propertyElement;
    }

    @Override
    public boolean isAdapterForType( Object type )
    {
        return PropertiesAdapter.class.equals( type );
    }

    @Override
    public void load()
    {
        NodeList children = node.getChildNodes();
        int nChildren = children.getLength();
        for ( int i = 0; i < nChildren; i++ )
        {
            Node child = children.item( i );
            if ( child instanceof Element )
            {
                properties.add( createObject( (Element) child ) );
            }

        }

    }

    @Override
    public void save()
    {
        for ( PropertyElement o : properties )
        {
            add( o, -1 );
        }
    }

}
