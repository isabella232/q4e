package org.devzuz.q.maven.pom.translators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.util.PropertyUtil;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.wst.common.internal.emf.utilities.ExtendedEcoreUtil;
import org.eclipse.wst.sse.core.internal.provisional.INodeAdapter;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Listens for notifications from the dom model and the E model, and syncs the models.
 * 
 * @author Mike Poindexter
 *
 */
public class SSESyncAdapter
    implements Adapter, INodeAdapter, HasLinkedWhitespaceNodes
{
    private SSESyncResource resource;
    private EObject eobject;
    private Node node;
    private Notifier target;
    private List<Node> linkedWhitespaceNodes = new ArrayList<Node>( 4 );
    
    public SSESyncAdapter( SSESyncResource resource, EObject eobject, Node node )
    {
        super();
        this.eobject = eobject;
        this.node = node;
        this.resource = resource;
    }

    public boolean isAdapterForType( Object type )
    {
        return true;
    }
    
    public void notifyChanged( INodeNotifier notifier, int eventType, Object changedFeature, Object oldValue,
                               Object newValue, int pos )
    {
        if( resource.isProcessEvents() )
        {
            resource.setProcessEvents( false );
            try
            {
                if( INodeNotifier.ADD == eventType && newValue instanceof Element )
                {
                    if( notifier == node )
                    {
                        IDOMElement addedElement = (IDOMElement)newValue;
                        createModelChild( addedElement, indexOf( addedElement ) );
                    }
                }
                else if( INodeNotifier.REMOVE == eventType && oldValue instanceof Element )
                {
                    if( notifier == node )
                    {
                        IDOMElement removedElement = (IDOMElement)oldValue;
                        EStructuralFeature feature = eobject.eClass().getEStructuralFeature( removedElement.getTagName() );
                        if( feature != null)
                        {
                            Object value = null;
                            SSESyncAdapter adapter = (SSESyncAdapter)removedElement.getExistingAdapter( SSESyncAdapter.class );
                            if( adapter != null )
                            {
                                value = adapter.eobject;
                            }
                            else
                            {
                                value = getElementText( removedElement );
                            }
                            ExtendedEcoreUtil.eUnsetOrRemove( eobject, feature, value );
                        }
                        else
                        {
                            //handle feature maps
                            feature = PropertyUtil.getPropertyFeature( removedElement.getTagName() );
                            for ( EAttribute classFeature : eobject.eClass().getEAttributes() )
                            {
                                if( classFeature.getEAttributeType() == EcorePackage.Literals.EFEATURE_MAP_ENTRY )
                                {
                                    FeatureMap featureMap = (FeatureMap) eobject.eGet( classFeature );
                                    featureMap.unset( feature );
                                }
                            }
                        }
                    }
                }
                else if( notifier instanceof IDOMElement && changedFeature instanceof Text )
                {
                    if( notifier != node )
                    {
                        String tagName = ((IDOMElement)notifier).getTagName();
                        EStructuralFeature feature = eobject.eClass().getEStructuralFeature( tagName );
                        if( null == newValue )
                        {
                            ExtendedEcoreUtil.eUnsetOrRemove( eobject, feature, oldValue );
                        }
                        else
                        {
                            ExtendedEcoreUtil.eSetOrAdd( eobject, feature, newValue.toString().trim() );
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
    
    public void notifyChanged( Notification notification )
    {
        if( resource.isProcessEvents() )
        {
            resource.setProcessEvents( false );
            try
            {
                EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
                switch( notification.getEventType() )
                {
                    
                    case Notification.SET:
                        setChildElement( feature, notification.getNewValue(), notification.getOldValue(), notification.getPosition() );
                        break;
                        
                    case Notification.ADD:
                        //On add or set of a feature map entry we get a SET and then an ADD
                        //
                        //I'm not sure why or how to handle this, so I'll
                        //just ignore the add.
                        if( !( notification.getNewValue() instanceof FeatureMap.Entry ) )
                        {
                            addChildElement( feature, notification.getNewValue(), notification.getPosition() );
                        }
                        break;
                        
                    case Notification.ADD_MANY:
                        Collection<?> addMany = (Collection<?>)notification.getNewValue();
                        int addPosition = notification.getPosition();
                        int addIdx = addPosition;
                        for ( Object object : addMany )
                        {
                            addChildElement( feature, object, addPosition == Notification.NO_INDEX ? Notification.NO_INDEX : addIdx );
                            addIdx++;
                        }
                        break;

                    case Notification.REMOVE:
                    case Notification.UNSET:
                        if( ( notification.getOldValue() instanceof FeatureMap.Entry ) )
                        {
                            FeatureMap.Entry oldValue = (FeatureMap.Entry) notification.getOldValue();
                            removeDomChild( oldValue.getEStructuralFeature(), oldValue.getValue(), Notification.NO_INDEX );
                        }
                        else
                        {
                            removeDomChild( feature, notification.getOldValue(), Notification.NO_INDEX );
                        }
                        break;
                        
                    
                    case Notification.REMOVE_MANY:
                        Collection<?> removeMany = (Collection<?>)notification.getOldValue();
                        int removePosition = notification.getPosition();
                        int removeIdx = removePosition;
                        for ( Object object : removeMany )
                        {
                            removeDomChild( feature, object, removePosition == Notification.NO_INDEX ? Notification.NO_INDEX : removeIdx );
                            removeIdx++;
                        }
                        
                }
            }
            finally
            {
                resource.setProcessEvents( true );
            }
        }
        
    }

    /**
     * Removes the positionth dom child with the name mathcing the feature.
     * 
     * @param feature
     * @param value
     * @param position
     */
    private void removeDomChild( EStructuralFeature feature, Object value, int position )
    {
        if( value instanceof EObject )
        {
            EObject oldValue = (EObject)value;
            SSESyncAdapter existingAdapter = (SSESyncAdapter)EcoreUtil.getExistingAdapter( oldValue, SSESyncAdapter.class );
            if( existingAdapter != null )
            {
                for ( Node n : existingAdapter.getLinkedWhitespaceNodes() )
                {
                    n.getParentNode().removeChild( n );
                }
                node.removeChild( existingAdapter.getNode() );
            }
        }
        else
        {
            Node valueNode = null;
            if( position == Notification.NO_INDEX )
            {
                valueNode = getFirstChildWithName( feature.getName() );
            }
            else
            {
                valueNode = getNthChildWithName( feature.getName(), position );
            }
            if( valueNode != null )
            {
                ValueUpdateAdapter existingAdapter = (ValueUpdateAdapter) ( (IDOMNode) valueNode ).getExistingAdapter( ValueUpdateAdapter.class );
                if( existingAdapter != null )
                {
                    for ( Node n : existingAdapter.getLinkedWhitespaceNodes() )
                    {
                        n.getParentNode().removeChild( n );
                    }
                }
                node.removeChild( valueNode );
            }
        }
    }
    
    /**
     * Adds a new child to the node at position.
     * @param feature
     * @param value
     * @param position
     */
    private void addChildElement( EStructuralFeature feature, Object value, int position )
    {
        Element element = createAndInsertChildElement( value, feature.getName(), position );
    }
    
    /**
     * Sets the element child at position to newValue, creating if necessary
     * @param feature
     * @param newValue
     * @param oldValue
     * @param position
     */
    private void setChildElement( EStructuralFeature feature, Object newValue, Object oldValue, int position )
    {
        String tagName = feature.getName();
        Element element = null;
        if( !feature.isMany() )
        {
            element = getFirstChildWithName( tagName );
        }
        else
        {
            element = getNthChildWithName( tagName, position );
        }
        if( element == null )
        {
            element = createAndInsertChildElement( newValue, tagName, Notification.NO_INDEX );
        }
        else
        {
            //If we're setting an object we will
            //remove the child and replace it.
            if( newValue instanceof EObject )
            {
                Element oldElement = element;
                element = createAndInsertChildElement( newValue, tagName, indexOf( oldElement ) );
                node.removeChild( oldElement );
            }
            //For simple text values just replace the element's text
            else
            {
                setElementTextValue( element, oldValue, newValue );
            }
        }
    }

    /**
     * Creates a child element for insertion into the tree.
     * 
     * @param value
     * @param tagName
     * @return
     */
    private Element createAndInsertChildElement( Object value, String tagName, int position )
    {
        Element element = null;
        if( value instanceof EObject )
        {
            EObject eValue = (EObject)value;
            SSESyncAdapter adapter = (SSESyncAdapter)EcoreUtil.getExistingAdapter( eValue, SSESyncAdapter.class );
            if( adapter != null )
            {
                eValue.eAdapters().remove( adapter );
                adapter = null;
            }
            
            element = node.getOwnerDocument().createElement( tagName );
            insertElement( element, position );
            adapter = new SSESyncAdapter( this.resource, eValue, element );
            formatNode( element, true, adapter );
            eValue.eAdapters().add( adapter );
            ((IDOMElement)element).addAdapter( adapter );
            adapter.save();
        }
        else
        {
            element = node.getOwnerDocument().createElement( tagName );
            insertElement( element, position );
            ValueUpdateAdapter adapter = new ValueUpdateAdapter(this );
            formatNode( element, false, adapter );
            ((IDOMElement)element).addAdapter( adapter );
            setElementTextValue( element, null, value );
        }
        return element;
    }
    
    /**
     * Sets the text value of an existing node, attempting to preserve whitespace
     * @param element
     * @param oldValue
     * @param newValue
     */
    private void setElementTextValue( Element element, Object oldValue, Object newValue )
    {
        boolean replacedChild = false;
        
        //First try to find a text node with the old value and set it (to preserve whitespace)
        NodeList children = element.getChildNodes();
        int nChildren = children.getLength();
        for ( int i = 0; i < nChildren; i++ )
        {
            Node child = children.item( i );
            if( child instanceof Text )
            {
                String value = ( (Text) child ).getData();
                int oldIdx = value.indexOf( oldValue.toString() );
                if( oldIdx > -1 )
                {
                    String replacement = value.substring( 0, oldIdx ) + newValue.toString() + value.substring( oldIdx + oldValue.toString().length() );
                    ((Text)child).setData( replacement );
                    replacedChild = true;
                }
            }
        }
        
        //If for some reason we couldn't find a text to update, just clear the 
        //element contents and put in our text.
        if( !replacedChild )
        {
            while( element.getFirstChild() != null )
            {
                element.removeChild( element.getFirstChild() );
            }
            Text text = node.getOwnerDocument().createTextNode( newValue == null ? "" : newValue.toString() );
            element.appendChild( text );
        }
    }

    /**
     * Inserts an element under the current node, attempting to preserve
     * proper node ordering.
     * 
     * @param element
     * @param position
     */
    private void insertElement( Element element, int position )
    {
        Node beforeNode = null;
        if( position != Notification.NO_INDEX )
        {
            beforeNode = getNthChildWithName( element.getTagName(), position );
        }
        
        //Find the proper place for the node to be inserted 
        if( beforeNode == null )
        {
            boolean searching = false;
            for ( EStructuralFeature feature : eobject.eClass().getEStructuralFeatures() )
            {
                if( element.getTagName().equals( feature.getName() ) )
                {
                    searching = true;
                }
                if( searching )
                {
                    beforeNode = getFirstChildWithName( feature.getName() );
                    if( beforeNode != null )
                    {
                        break;
                    }
                }
            }
        }

        if( beforeNode == null )
        {
            beforeNode = node.getLastChild();
            while( !( beforeNode.getPreviousSibling() instanceof Element || beforeNode.getPreviousSibling() == null) )
            {
                beforeNode = beforeNode.getPreviousSibling();
            }
        }
        
        if( beforeNode == null )
        {
            node.appendChild( element );
        }
        else
        {
            node.insertBefore( element, beforeNode );
        }
    }
    
    private void formatNode( Element element, boolean canHaveChildren, HasLinkedWhitespaceNodes adapter )
    {
        ArrayList<Node> linked = new ArrayList<Node>( 4 );
        String indent = getIndentForNode( element );
        String newline = getNewlineString();
        Node beforeNL = node.getOwnerDocument().createTextNode( newline );
        Node beforeWS = node.getOwnerDocument().createTextNode( indent );
        node.insertBefore( beforeNL, element );
        node.insertBefore( beforeWS, element );
        linked.add( beforeNL );
        linked.add( beforeWS );
        if( canHaveChildren )
        {
            Node afterNL = node.getOwnerDocument().createTextNode( newline );
            Node afterWS = node.getOwnerDocument().createTextNode( indent );
            element.appendChild( afterNL );
            element.appendChild( afterWS );
            linked.add( afterWS );
            linked.add( afterNL );
        }
        
        if(element.getNextSibling() instanceof Element)
        {
            Node refNode = element.getNextSibling();
            Node afterNL = node.getOwnerDocument().createTextNode( newline );
            Node afterWS = node.getOwnerDocument().createTextNode( indent );
            node.insertBefore( afterNL, refNode );
            node.insertBefore( afterWS, refNode );
            linked.add( afterWS );
            linked.add( afterNL );
        }
        adapter.setLinkedWhitespaceNodes( linked );
    }
    
    private String getIndentForNode( Element node )
    {
        String ret = null;
        Node prev = node.getPreviousSibling();
        while( prev != null )
        {
            if( prev instanceof Element )
            {
                ret = getIndentBeforeStartTag( prev );
                break;
            }
            prev = prev.getPreviousSibling();
        }
        
        if( null == ret )
        {
            ret = getIndentBeforeStartTag( node.getParentNode() ) + "\t";
        }
        return ret;
    }

    private String getIndentBeforeStartTag( Node node )
    {
        StringBuilder builder = new StringBuilder( 100 );
        IStructuredDocument doc = ( (IDOMNode) node ).getStructuredDocument();
        int nodeStartOff = ( (IDOMNode) node ).getStartOffset();
        int i = nodeStartOff - 1;
        while( i > 0 )
        {
            char c = ' ';
            try
            {
                c = doc.getChar( i );
            }
            catch ( BadLocationException e )
            {
                //We check for bad locations so this should not happen
            }
            if( Character.isWhitespace( c ) && !( c == '\r' || c == '\n' ) )
            {
                builder.insert( 0, c );
                i--;
            }
            else
            {
                break;
            }
        }
        return builder.toString();
    }
    
    private String getNewlineString()
    {
        return ( (IDOMNode) node ).getStructuredDocument().getLineDelimiter();
    }
    
    /**
     * Populates all child model objects from the child
     * nodes in the dom tree.
     *  
     */
    public void load( )
    {
        NodeList children = node.getChildNodes();
        int nChildren = children.getLength();
        for ( int i = 0; i < nChildren; i++ )
        {
            Node child = children.item( i );
            if( child instanceof Element )
            {
                createModelChild( (Element)child, -1 );
            }
            
        }
    }
    
    /**
     * Populates all child dom objects from the model children.
     * 
     */
    public void save( )
    {
        for ( EStructuralFeature feature : eobject.eClass().getEStructuralFeatures() )
        {
            if( eobject.eIsSet( feature ) )
            {
                if( feature.isMany() )
                {
                    Collection<?> values = (Collection<?>)eobject.eGet( feature );
                    int idx = 0;
                    for ( Object object : values )
                    {
                        addChildElement( feature, object, idx++ );
                    }
                }
                else
                {
                    setChildElement( feature, eobject.eGet( feature ), null, Notification.NO_INDEX );
                }
            }
        }
    }

    /**
     * Creates a model child from the given dom element.
     * 
     * @param element
     */
    private void createModelChild( Element element, int pos )
    {
        EStructuralFeature feature = eobject.eClass().getEStructuralFeature( element.getTagName() );
        if( feature != null )
        {
            if( feature instanceof EReference )
            {
                EReference ref = (EReference)feature;
                EObject childObject = null;
                SSESyncAdapter existingAdapter = (SSESyncAdapter) ( (IDOMElement)element ).getExistingAdapter( SSESyncAdapter.class );
                if( existingAdapter == null)
                {
                    childObject = PomFactory.eINSTANCE.create( ref.getEReferenceType() );
                    SSESyncAdapter adapter = new SSESyncAdapter( resource, childObject, element );
                    childObject.eAdapters().add( adapter );
                    ((IDOMElement)element).addAdapter( adapter );
                    ExtendedEcoreUtil.eSetOrAdd( eobject, feature, childObject, pos );
                    adapter.load();
                }
                else
                {
                    childObject = existingAdapter.eobject;
                }
            }
            else
            {
                String newValue = getElementText( element );
                ExtendedEcoreUtil.eSetOrAdd( eobject, feature, newValue );
                ((IDOMElement)element).addAdapter( new ValueUpdateAdapter(this ) );
            }
        }
        else
        {
            //handle feature maps
            
            for ( EAttribute classFeature : eobject.eClass().getEAttributes() )
            {
                if( classFeature.getEAttributeType() == EcorePackage.Literals.EFEATURE_MAP_ENTRY )
                {
                    feature = PropertyUtil.getPropertyFeature( element.getTagName() );
                    FeatureMap featureMap = (FeatureMap) eobject.eGet( classFeature );
                    featureMap.set( feature, getElementText( element ) );
                }
            }
        }
    }
    
    /**
     * Returns the index of the given element in the list of elements of the same name.
     * 
     * @param e
     * @return
     */
    private int indexOf( Element element )
    {
       int ret = 0;
       NodeList children = node.getChildNodes();
       int nChildren = children.getLength();
       for ( int i = 0; i < nChildren; i++ )
       {
           Node child = children.item( i );
           if( child instanceof Element )
           {
               Element e = (Element) child;
               if( e.getLocalName().equals( element.getLocalName() ) )
               {
                   if( e == element )
                   {
                       return ret;
                   }
                   else
                   {
                       ret++;
                   }
               }
           }
       }
       return -1;
    }
    
    /**
     * Returns the first child with the given name, or null if none exists.
     * 
     * @param name
     * @return
     */
    private Element getFirstChildWithName( String name )
    {
        return getNthChildWithName( name, 0 );
    }
    
    /**
     * Returns the nth child element with a given name, or null
     * if no such element exists.
     * 
     * @param name
     * @param n
     * @return
     */
    private Element getNthChildWithName( String name, int n )
    {
        int matchCount = 0;
        NodeList children = node.getChildNodes();
        int nChildren = children.getLength();
        for ( int i = 0; i < nChildren; i++ )
        {
            Node child = children.item( i );
            if( child instanceof Element )
            {
                Element e = (Element) child;
                if( e.getTagName().equals( name ) )
                {
                    if( matchCount == n )
                    {
                        return e;
                    }
                    else
                    {
                        n++;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the textual value of an element.
     * 
     * @param e
     * @return
     */
    private static String getElementText( Element e )
    {
        StringBuilder ret = new StringBuilder();
        NodeList children = e.getChildNodes();
        int nChildren = children.getLength();
        for ( int i = 0; i < nChildren; i++ )
        {
            Node child = children.item( i );
            if( child instanceof Text )
            {
                ret.append( ((Text)child).getData() );
            }
        }
        return ret.toString().trim();
    }

    public Notifier getTarget()
    {
        return target;
    }

    public void setTarget( Notifier target )
    {
        this.target = target;
    }

    public Node getNode()
    {
        return node;
    }

    public void setNode( Node node )
    {
        this.node = node;
    }
    
    public List<Node> getLinkedWhitespaceNodes()
    {
        return linkedWhitespaceNodes;
    }

    public void setLinkedWhitespaceNodes( List<Node> linkedWhitespaceNodes )
    {
        this.linkedWhitespaceNodes = linkedWhitespaceNodes;
    }

    

}
