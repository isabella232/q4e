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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Plugin Execution</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> 4.0.0 <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.PluginExecution#getId <em>Id</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.PluginExecution#getPhase <em>Phase</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.PluginExecution#getInherited <em>Inherited</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.PluginExecution#getGoals <em>Goals</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.devzuz.q.maven.pom.PomPackage#getPluginExecution()
 * @model extendedMetaData="name='PluginExecution' kind='elementOnly'"
 * @generated
 */
public interface PluginExecution
    extends EObject
{
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> 4.0.0 The identifier of this execution for labelling the goals during the build, and for
     * matching exections to merge during inheritance. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Id</em>' attribute.
     * @see #isSetId()
     * @see #unsetId()
     * @see #setId(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginExecution_Id()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element'
     *        name='id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.PluginExecution#getId <em>Id</em>}' attribute. <!--
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
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.PluginExecution#getId <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetId()
     * @see #getId()
     * @see #setId(String)
     * @generated
     */
    void unsetId();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.PluginExecution#getId <em>Id</em>}' attribute
     * is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Id</em>' attribute is set.
     * @see #unsetId()
     * @see #getId()
     * @see #setId(String)
     * @generated
     */
    boolean isSetId();

    /**
     * Returns the value of the '<em><b>Phase</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> 4.0.0 The build lifecycle phase to bind the goals in this execution to. If omitted, the
     * goals will be bound to the default specified in their metadata. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Phase</em>' attribute.
     * @see #setPhase(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginExecution_Phase()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='phase'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getPhase();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.PluginExecution#getPhase <em>Phase</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Phase</em>' attribute.
     * @see #getPhase()
     * @generated
     */
    void setPhase( String value );

    /**
     * Returns the value of the '<em><b>Inherited</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * --> <!-- begin-model-doc --> 4.0.0 Whether any configuration should be propagated to child POMs. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Inherited</em>' attribute.
     * @see #setInherited(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginExecution_Inherited()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='inherited'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getInherited();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.PluginExecution#getInherited <em>Inherited</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Inherited</em>' attribute.
     * @see #getInherited()
     * @generated
     */
    void setInherited( String value );

    /**
     * Returns the value of the '<em><b>Goals</b></em>' attribute list. The list contents are of type
     * {@link java.lang.String}. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Goals</em>' attribute list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Goals</em>' attribute list.
     * @see org.devzuz.q.maven.pom.PomPackage#getPluginExecution_Goals()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<String> getGoals();

} // PluginExecution
