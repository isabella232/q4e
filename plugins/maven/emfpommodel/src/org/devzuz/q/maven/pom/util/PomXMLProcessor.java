/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.util;

import java.util.Map;

import org.devzuz.q.maven.pom.PomPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class PomXMLProcessor
    extends XMLProcessor
{

    /**
     * Public constructor to instantiate the helper. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public PomXMLProcessor()
    {
        super( ( EPackage.Registry.INSTANCE ) );
        PomPackage.eINSTANCE.eClass();
    }

    /**
     * Register for "*" and "xml" file extensions the PomResourceFactoryImpl factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations()
    {
        if ( registrations == null )
        {
            super.getRegistrations();
            registrations.put( XML_EXTENSION, new PomResourceFactoryImpl() );
            registrations.put( STAR_EXTENSION, new PomResourceFactoryImpl() );
        }
        return registrations;
    }

} // PomXMLProcessor
