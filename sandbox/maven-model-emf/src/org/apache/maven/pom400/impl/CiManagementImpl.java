/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import org.apache.maven.pom400.CiManagement;
import org.apache.maven.pom400.NotifiersType;
import org.apache.maven.pom400.mavenPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ci Management</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.apache.maven.pom400.impl.CiManagementImpl#getSystem <em>System</em>}</li>
 *   <li>{@link org.apache.maven.pom400.impl.CiManagementImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.apache.maven.pom400.impl.CiManagementImpl#getNotifiers <em>Notifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CiManagementImpl extends EObjectImpl implements CiManagement {
	/**
	 * The default value of the '{@link #getSystem() <em>System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystem() <em>System</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected String system = SYSTEM_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNotifiers() <em>Notifiers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotifiers()
	 * @generated
	 * @ordered
	 */
	protected NotifiersType notifiers = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CiManagementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return mavenPackage.Literals.CI_MANAGEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem(String newSystem) {
		String oldSystem = system;
		system = newSystem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, mavenPackage.CI_MANAGEMENT__SYSTEM, oldSystem, system));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, mavenPackage.CI_MANAGEMENT__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotifiersType getNotifiers() {
		return notifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNotifiers(NotifiersType newNotifiers, NotificationChain msgs) {
		NotifiersType oldNotifiers = notifiers;
		notifiers = newNotifiers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, mavenPackage.CI_MANAGEMENT__NOTIFIERS, oldNotifiers, newNotifiers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotifiers(NotifiersType newNotifiers) {
		if (newNotifiers != notifiers) {
			NotificationChain msgs = null;
			if (notifiers != null)
				msgs = ((InternalEObject)notifiers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - mavenPackage.CI_MANAGEMENT__NOTIFIERS, null, msgs);
			if (newNotifiers != null)
				msgs = ((InternalEObject)newNotifiers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - mavenPackage.CI_MANAGEMENT__NOTIFIERS, null, msgs);
			msgs = basicSetNotifiers(newNotifiers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, mavenPackage.CI_MANAGEMENT__NOTIFIERS, newNotifiers, newNotifiers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case mavenPackage.CI_MANAGEMENT__NOTIFIERS:
				return basicSetNotifiers(null, msgs);
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
			case mavenPackage.CI_MANAGEMENT__SYSTEM:
				return getSystem();
			case mavenPackage.CI_MANAGEMENT__URL:
				return getUrl();
			case mavenPackage.CI_MANAGEMENT__NOTIFIERS:
				return getNotifiers();
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
			case mavenPackage.CI_MANAGEMENT__SYSTEM:
				setSystem((String)newValue);
				return;
			case mavenPackage.CI_MANAGEMENT__URL:
				setUrl((String)newValue);
				return;
			case mavenPackage.CI_MANAGEMENT__NOTIFIERS:
				setNotifiers((NotifiersType)newValue);
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
			case mavenPackage.CI_MANAGEMENT__SYSTEM:
				setSystem(SYSTEM_EDEFAULT);
				return;
			case mavenPackage.CI_MANAGEMENT__URL:
				setUrl(URL_EDEFAULT);
				return;
			case mavenPackage.CI_MANAGEMENT__NOTIFIERS:
				setNotifiers((NotifiersType)null);
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
			case mavenPackage.CI_MANAGEMENT__SYSTEM:
				return SYSTEM_EDEFAULT == null ? system != null : !SYSTEM_EDEFAULT.equals(system);
			case mavenPackage.CI_MANAGEMENT__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case mavenPackage.CI_MANAGEMENT__NOTIFIERS:
				return notifiers != null;
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
		result.append(" (system: ");
		result.append(system);
		result.append(", url: ");
		result.append(url);
		result.append(')');
		return result.toString();
	}

} //CiManagementImpl
