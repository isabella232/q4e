/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.DependenciesType1;
import org.devzuz.q.maven.pom.DependencyManagement;
import org.devzuz.q.maven.pom.PomPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency Management</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.impl.DependencyManagementImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DependencyManagementImpl extends EObjectImpl implements DependencyManagement
{
    /**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
    protected DependenciesType1 dependencies;

    /**
	 * This is true if the Dependencies containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean dependenciesESet;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected DependencyManagementImpl()
    {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass()
    {
		return PomPackage.Literals.DEPENDENCY_MANAGEMENT;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DependenciesType1 getDependencies()
    {
		return dependencies;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDependencies(DependenciesType1 newDependencies, NotificationChain msgs)
    {
		DependenciesType1 oldDependencies = dependencies;
		dependencies = newDependencies;
		boolean oldDependenciesESet = dependenciesESet;
		dependenciesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, oldDependencies, newDependencies, !oldDependenciesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDependencies(DependenciesType1 newDependencies)
    {
		if (newDependencies != dependencies) {
			NotificationChain msgs = null;
			if (dependencies != null)
				msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, null, msgs);
			if (newDependencies != null)
				msgs = ((InternalEObject)newDependencies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, null, msgs);
			msgs = basicSetDependencies(newDependencies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependenciesESet = dependenciesESet;
			dependenciesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, newDependencies, newDependencies, !oldDependenciesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetDependencies(NotificationChain msgs)
    {
		DependenciesType1 oldDependencies = dependencies;
		dependencies = null;
		boolean oldDependenciesESet = dependenciesESet;
		dependenciesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, oldDependencies, null, oldDependenciesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetDependencies()
    {
		if (dependencies != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, null, msgs);
			msgs = basicUnsetDependencies(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependenciesESet = dependenciesESet;
			dependenciesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES, null, null, oldDependenciesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetDependencies()
    {
		return dependenciesESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
		switch (featureID) {
			case PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES:
				return basicUnsetDependencies(msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType)
    {
		switch (featureID) {
			case PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES:
				return getDependencies();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue)
    {
		switch (featureID) {
			case PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES:
				setDependencies((DependenciesType1)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID)
    {
		switch (featureID) {
			case PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES:
				unsetDependencies();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID)
    {
		switch (featureID) {
			case PomPackage.DEPENDENCY_MANAGEMENT__DEPENDENCIES:
				return isSetDependencies();
		}
		return super.eIsSet(featureID);
	}

} //DependencyManagementImpl
