/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import java.util.Collection;

import org.apache.maven.pom400.PluginRepositoriesType;
import org.apache.maven.pom400.Repository;
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
 * An implementation of the model object '<em><b>Plugin Repositories Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.impl.PluginRepositoriesTypeImpl#getPluginRepository <em>Plugin Repository</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PluginRepositoriesTypeImpl extends EObjectImpl implements PluginRepositoriesType {
	/**
	 * The cached value of the '{@link #getPluginRepository() <em>Plugin Repository</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginRepository()
	 * @generated
	 * @ordered
	 */
	protected EList pluginRepository = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PluginRepositoriesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return mavenPackage.Literals.PLUGIN_REPOSITORIES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPluginRepository() {
		if (pluginRepository == null) {
			pluginRepository = new EObjectContainmentEList(Repository.class, this, mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY);
		}
		return pluginRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY:
				return ((InternalEList)getPluginRepository()).basicRemove(otherEnd, msgs);
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
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY:
				return getPluginRepository();
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
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY:
				getPluginRepository().clear();
				getPluginRepository().addAll((Collection)newValue);
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
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY:
				getPluginRepository().clear();
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
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE__PLUGIN_REPOSITORY:
				return pluginRepository != null && !pluginRepository.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PluginRepositoriesTypeImpl
