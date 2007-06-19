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
 * A representation of the model object '<em><b>Repositories Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.RepositoriesType#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.apache.maven.pom400.mavenPackage#getRepositoriesType()
 * @model extendedMetaData="name='repositories_._type' kind='elementOnly'"
 * @generated
 */
public interface RepositoriesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Repository</b></em>' containment reference list.
	 * The list contents are of type {@link org.apache.maven.pom400.Repository}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository</em>' containment reference list.
	 * @see org.apache.maven.pom400.mavenPackage#getRepositoriesType_Repository()
	 * @model type="org.apache.maven.pom400.Repository" containment="true"
	 *        extendedMetaData="kind='element' name='repository' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getRepository();

} // RepositoriesType
