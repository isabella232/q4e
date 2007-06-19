/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import java.util.Collection;

import org.apache.maven.pom400.Contributor;
import org.apache.maven.pom400.ContributorsType;
import org.apache.maven.pom400.mavenPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contributors Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.impl.ContributorsTypeImpl#getContributor <em>Contributor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContributorsTypeImpl extends EObjectImpl implements ContributorsType {
	/**
	 * The cached value of the '{@link #getContributor() <em>Contributor</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContributor()
	 * @generated
	 * @ordered
	 */
	protected EList contributor = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContributorsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return mavenPackage.Literals.CONTRIBUTORS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getContributor() {
		if (contributor == null) {
			contributor = new EObjectContainmentEList(Contributor.class, this, mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR);
		}
		return contributor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR:
				return ((InternalEList)getContributor()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR:
				return getContributor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR:
				getContributor().clear();
				getContributor().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR:
				getContributor().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case mavenPackage.CONTRIBUTORS_TYPE__CONTRIBUTOR:
				return contributor != null && !contributor.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ContributorsTypeImpl
