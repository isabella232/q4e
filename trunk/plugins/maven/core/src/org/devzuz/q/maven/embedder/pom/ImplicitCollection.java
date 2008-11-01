/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
 * a generic representation of a pom collection (eg model.dependencies). allows type-safe operations on the collection
 * without having to create a explicitly typed wrapper.
 * 
 * @author jake pezaro
 * @param <E> the type of the underlying collection
 */
public class ImplicitCollection<E extends AbstractDom4JModelElement>
    extends AbstractDom4JModelElement
    implements Iterable<E>
{

    private String name;

    private Class<E> type;

    /**
     * @param element - the dom4j element representing the collection wrapper (eg model.dependencies)
     * @param collectionElementName - the name of the elements in the collection (eg dependency)
     * @param collectionElementType - the type of the underlying collection (eg
     *            org.devzuz.q.maven.embedder.pom.Dependency)
     */
    ImplicitCollection( Element element, String collectionElementName, Class<E> collectionElementType )
    {
        super( element );
        name = collectionElementName;
        type = collectionElementType;
    }

    /**
     * add a new instance of the underlying collection type to the dom4j model
     * 
     * @return the newly added instance
     */
    public E addNew()
    {
        Element newElement = getElement().addElement( name );
        return createInstance( type, newElement );
    }

    @Override
    protected String getName()
    {
        return name;
    }

    public Iterator<E> iterator()
    {
        List<E> elements = new ArrayList<E>();
        Iterator elementIterator = getElement().elementIterator( name );
        while ( elementIterator.hasNext() )
        {
            Element element = (Element) elementIterator.next();
            E instance = createInstance( type, element );
            elements.add( instance );
        }
        return elements.iterator();
    }

}
