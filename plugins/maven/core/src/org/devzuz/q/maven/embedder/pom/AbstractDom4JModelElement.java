/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.dom4j.Element;

/**
 * Parent class for all model elements. Provides method for accessing and modifying the underlying jdom document.
 * 
 * @author jake pezaro
 */
public abstract class AbstractDom4JModelElement
{

    private Element element;

    AbstractDom4JModelElement( Element element )
    {
        this.element = element;
    }

    /**
     * return an implicit collection. if the implicit collection does not already exist then a new one is created.
     * 
     * @param collectionName - the name of the collection (eg dependencies)
     * @param collectionElementName - the name of the elements in the collection (eg dependency)
     * @param collectionElementType - the type of the collection (eg org.devzuz.q.maven.embedder.pom.Dependency)
     */
    protected <E extends AbstractDom4JModelElement> ImplicitCollection<E> getImplicitCollection(
                                                                                                 String collectionName,
                                                                                                 String collectionElementName,
                                                                                                 Class<E> collectionElementType )
    {
        Element element = getUniqueElement( collectionName );
        if ( element == null )
        {
            element = this.element.addElement( collectionName );
        }
        return new ImplicitCollection<E>( element, collectionElementName, collectionElementType );
    }

    /**
     * return a single element. if the element does not already exist then a new one is created.
     * 
     * @param name - the name of the element (eg dependencyManagement)
     * @param type - the type of the element (eg org.devzuz.q.maven.embedder.pom.DependencyManagement)
     */
    protected <E extends AbstractDom4JModelElement> E getUniqueElement( String name, Class<E> type )
    {
        Element element = getUniqueElement( name );
        if ( element == null )
        {
            element = this.element.addElement( name );
        }
        return createInstance( type, element );
    }

    private Element getUniqueElement( String name )
    {
        Iterator elementIterator = this.element.elementIterator( name );
        while ( elementIterator.hasNext() )
        {
            Element element = (Element) elementIterator.next();
            if ( elementIterator.hasNext() )
            {
                throw new InvalidModelException( "Found more than one element with name " + name );
            }
            return element;
        }
        return null;
    }

    /**
     * creates a new instance of the supplied type, and initialises it with the provided dom4j element
     */
    protected <E extends AbstractDom4JModelElement> E createInstance( Class<E> type, Element element )
    {
        try
        {
            E newInstance = type.getDeclaredConstructor( Element.class ).newInstance( element );
            return newInstance;
        }
        catch ( InstantiationException e )
        {
            throw new InvalidModelException( e );
        }
        catch ( IllegalAccessException e )
        {
            throw new InvalidModelException( e );
        }
        catch ( IllegalArgumentException e )
        {
            throw new InvalidModelException( e );
        }
        catch ( SecurityException e )
        {
            throw new InvalidModelException( e );
        }
        catch ( InvocationTargetException e )
        {
            throw new InvalidModelException( e );
        }
        catch ( NoSuchMethodException e )
        {
            throw new InvalidModelException( e );
        }
    }

    /**
     * Get the value of the indicated text element. <b>If the element does not exist it is not created.</b>
     * 
     * @param name - eg groupId
     * @return - eg the value of the groupId element or null if it does not exist
     */
    protected String getTextElement( String name )
    {
        Element element = getUniqueElement( name );
        if ( element == null )
        {
            return null;
        }
        return element.getText();
    }

    /**
     * Set the value of the indicated text element. If the element does not exist it is created
     * 
     * @param name - eg groupId
     * @param value - eg the value of the groupId element
     */
    protected void setTextElement( String name, String value )
    {
        Element element = getUniqueElement( name );
        if ( element != null )
        {
            element.detach();
        }
        this.element.addElement( name ).addText( value );
    }

    protected abstract String getName();

    Element getElement()
    {
        return this.element;
    }

    /**
     * removes the object from it's parent in the dom4j heirarchy.
     * 
     * @see org.dom4j.Node.detach()
     * @return the detached instance
     */
    public AbstractDom4JModelElement detach()
    {
        getElement().detach();
        return this;
    }

}
