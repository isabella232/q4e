/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exclusions Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.ExclusionsType#getExclusion <em>Exclusion</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.apache.maven.pom400.mavenPackage#getExclusionsType()
 * @model extendedMetaData="name='exclusions_._type' kind='elementOnly'"
 * @generated
 */
public interface ExclusionsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Exclusion</b></em>' containment reference list.
	 * The list contents are of type {@link org.apache.maven.pom400.Exclusion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusion</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclusion</em>' containment reference list.
	 * @see org.apache.maven.pom400.mavenPackage#getExclusionsType_Exclusion()
	 * @model type="org.apache.maven.pom400.Exclusion" containment="true"
	 *        extendedMetaData="kind='element' name='exclusion' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getExclusion();

} // ExclusionsType
