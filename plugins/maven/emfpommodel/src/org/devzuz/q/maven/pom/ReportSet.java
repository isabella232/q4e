/**
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */
package org.devzuz.q.maven.pom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Report Set</b></em>'. <!-- end-user-doc -->
 * <!-- begin-model-doc --> 4.0.0 Represents a set of reports and configuration to be used to generate them. <!--
 * end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.ReportSet#getInherited <em>Inherited</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.ReportSet#getReports <em>Reports</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet()
 * @model extendedMetaData="name='ReportSet' kind='elementOnly'"
 * @generated
 */
public interface ReportSet
    extends EObject
{
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> 0.0.0+ The unique id for this report set, to be used during POM inheritance. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Id</em>' attribute.
     * @see #isSetId()
     * @see #unsetId()
     * @see #setId(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Id()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element'
     *        name='id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #isSetId()
     * @see #unsetId()
     * @see #getId()
     * @generated
     */
    void setId( String value );

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetId()
     * @see #getId()
     * @see #setId(String)
     * @generated
     */
    void unsetId();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute is set.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Id</em>' attribute is set.
     * @see #unsetId()
     * @see #getId()
     * @see #setId(String)
     * @generated
     */
    boolean isSetId();

    /**
     * Returns the value of the '<em><b>Inherited</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * --> <!-- begin-model-doc --> 4.0.0 Whether any configuration should be propagated to child POMs. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Inherited</em>' attribute.
     * @see #setInherited(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Inherited()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='inherited'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getInherited();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getInherited <em>Inherited</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Inherited</em>' attribute.
     * @see #getInherited()
     * @generated
     */
    void setInherited( String value );

    /**
     * Returns the value of the '<em><b>Reports</b></em>' attribute list. The list contents are of type
     * {@link java.lang.String}. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Reports</em>' attribute list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Reports</em>' attribute list.
     * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Reports()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<String> getReports();

} // ReportSet
