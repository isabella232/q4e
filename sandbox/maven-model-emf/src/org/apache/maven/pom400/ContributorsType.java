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
 * A representation of the model object '<em><b>Contributors Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.ContributorsType#getContributor <em>Contributor</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.apache.maven.pom400.mavenPackage#getContributorsType()
 * @model extendedMetaData="name='contributors_._type' kind='elementOnly'"
 * @generated
 */
public interface ContributorsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Contributor</b></em>' containment reference list.
	 * The list contents are of type {@link org.apache.maven.pom400.Contributor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contributor</em>' containment reference list.
	 * @see org.apache.maven.pom400.mavenPackage#getContributorsType_Contributor()
	 * @model type="org.apache.maven.pom400.Contributor" containment="true"
	 *        extendedMetaData="kind='element' name='contributor' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getContributor();

} // ContributorsType
