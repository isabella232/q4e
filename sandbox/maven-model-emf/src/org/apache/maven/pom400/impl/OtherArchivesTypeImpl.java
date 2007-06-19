/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import java.util.Collection;

import org.apache.maven.pom400.OtherArchivesType;
import org.apache.maven.pom400.mavenPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Other Archives Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.impl.OtherArchivesTypeImpl#getOtherArchive <em>Other Archive</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OtherArchivesTypeImpl extends EObjectImpl implements OtherArchivesType {
	/**
	 * The cached value of the '{@link #getOtherArchive() <em>Other Archive</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherArchive()
	 * @generated
	 * @ordered
	 */
	protected EList otherArchive = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OtherArchivesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return mavenPackage.Literals.OTHER_ARCHIVES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOtherArchive() {
		if (otherArchive == null) {
			otherArchive = new EDataTypeEList(String.class, this, mavenPackage.OTHER_ARCHIVES_TYPE__OTHER_ARCHIVE);
		}
		return otherArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case mavenPackage.OTHER_ARCHIVES_TYPE__OTHER_ARCHIVE:
				return getOtherArchive();
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
			case mavenPackage.OTHER_ARCHIVES_TYPE__OTHER_ARCHIVE:
				getOtherArchive().clear();
				getOtherArchive().addAll((Collection)newValue);
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
			case mavenPackage.OTHER_ARCHIVES_TYPE__OTHER_ARCHIVE:
				getOtherArchive().clear();
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
			case mavenPackage.OTHER_ARCHIVES_TYPE__OTHER_ARCHIVE:
				return otherArchive != null && !otherArchive.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (otherArchive: ");
		result.append(otherArchive);
		result.append(')');
		return result.toString();
	}

} //OtherArchivesTypeImpl
