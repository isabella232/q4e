/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import java.util.Collection;

import org.apache.maven.pom400.MailingList;
import org.apache.maven.pom400.MailingListsType;
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
 * An implementation of the model object '<em><b>Mailing Lists Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.impl.MailingListsTypeImpl#getMailingList <em>Mailing List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MailingListsTypeImpl extends EObjectImpl implements MailingListsType {
	/**
	 * The cached value of the '{@link #getMailingList() <em>Mailing List</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMailingList()
	 * @generated
	 * @ordered
	 */
	protected EList mailingList = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MailingListsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return mavenPackage.Literals.MAILING_LISTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMailingList() {
		if (mailingList == null) {
			mailingList = new EObjectContainmentEList(MailingList.class, this, mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST);
		}
		return mailingList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST:
				return ((InternalEList)getMailingList()).basicRemove(otherEnd, msgs);
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
			case mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST:
				return getMailingList();
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
			case mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST:
				getMailingList().clear();
				getMailingList().addAll((Collection)newValue);
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
			case mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST:
				getMailingList().clear();
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
			case mavenPackage.MAILING_LISTS_TYPE__MAILING_LIST:
				return mailingList != null && !mailingList.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MailingListsTypeImpl
