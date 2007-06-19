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
 * A representation of the model object '<em><b>Notifiers Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.NotifiersType#getNotifier <em>Notifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.apache.maven.pom400.mavenPackage#getNotifiersType()
 * @model extendedMetaData="name='notifiers_._type' kind='elementOnly'"
 * @generated
 */
public interface NotifiersType extends EObject {
	/**
	 * Returns the value of the '<em><b>Notifier</b></em>' containment reference list.
	 * The list contents are of type {@link org.apache.maven.pom400.Notifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notifier</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notifier</em>' containment reference list.
	 * @see org.apache.maven.pom400.mavenPackage#getNotifiersType_Notifier()
	 * @model type="org.apache.maven.pom400.Notifier" containment="true"
	 *        extendedMetaData="kind='element' name='notifier' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getNotifier();

} // NotifiersType
