/**
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import java.util.Collection;

import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.ReportSet;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Report Set</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.impl.ReportSetImpl#getId <em>Id</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.ReportSetImpl#getInherited <em>Inherited</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.ReportSetImpl#getReports <em>Reports</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ReportSetImpl
    extends EObjectImpl
    implements ReportSet
{
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * This is true if the Id attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean idESet;

    /**
     * The default value of the '{@link #getInherited() <em>Inherited</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getInherited()
     * @generated
     * @ordered
     */
    protected static final String INHERITED_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInherited() <em>Inherited</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getInherited()
     * @generated
     * @ordered
     */
    protected String inherited = INHERITED_EDEFAULT;

    /**
     * The cached value of the '{@link #getReports() <em>Reports</em>}' attribute list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getReports()
     * @generated
     * @ordered
     */
    protected EList<String> reports;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ReportSetImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return PomPackage.Literals.REPORT_SET;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getId()
    {
        return id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setId( String newId )
    {
        String oldId = id;
        id = newId;
        boolean oldIdESet = idESet;
        idESet = true;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.REPORT_SET__ID, oldId, id, !oldIdESet ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetId()
    {
        String oldId = id;
        boolean oldIdESet = idESet;
        id = ID_EDEFAULT;
        idESet = false;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.UNSET, PomPackage.REPORT_SET__ID, oldId, ID_EDEFAULT,
                                            oldIdESet ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetId()
    {
        return idESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getInherited()
    {
        return inherited;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setInherited( String newInherited )
    {
        String oldInherited = inherited;
        inherited = newInherited;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.REPORT_SET__INHERITED, oldInherited,
                                            inherited ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<String> getReports()
    {
        if ( reports == null )
        {
            reports = new EDataTypeUniqueEList<String>( String.class, this, PomPackage.REPORT_SET__REPORTS );
        }
        return reports;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet( int featureID, boolean resolve, boolean coreType )
    {
        switch ( featureID )
        {
            case PomPackage.REPORT_SET__ID:
                return getId();
            case PomPackage.REPORT_SET__INHERITED:
                return getInherited();
            case PomPackage.REPORT_SET__REPORTS:
                return getReports();
        }
        return super.eGet( featureID, resolve, coreType );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public void eSet( int featureID, Object newValue )
    {
        switch ( featureID )
        {
            case PomPackage.REPORT_SET__ID:
                setId( (String) newValue );
                return;
            case PomPackage.REPORT_SET__INHERITED:
                setInherited( (String) newValue );
                return;
            case PomPackage.REPORT_SET__REPORTS:
                getReports().clear();
                getReports().addAll( (Collection<? extends String>) newValue );
                return;
        }
        super.eSet( featureID, newValue );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset( int featureID )
    {
        switch ( featureID )
        {
            case PomPackage.REPORT_SET__ID:
                unsetId();
                return;
            case PomPackage.REPORT_SET__INHERITED:
                setInherited( INHERITED_EDEFAULT );
                return;
            case PomPackage.REPORT_SET__REPORTS:
                getReports().clear();
                return;
        }
        super.eUnset( featureID );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet( int featureID )
    {
        switch ( featureID )
        {
            case PomPackage.REPORT_SET__ID:
                return isSetId();
            case PomPackage.REPORT_SET__INHERITED:
                return INHERITED_EDEFAULT == null ? inherited != null : !INHERITED_EDEFAULT.equals( inherited );
            case PomPackage.REPORT_SET__REPORTS:
                return reports != null && !reports.isEmpty();
        }
        return super.eIsSet( featureID );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString()
    {
        if ( eIsProxy() )
            return super.toString();

        StringBuffer result = new StringBuffer( super.toString() );
        result.append( " (id: " );
        if ( idESet )
            result.append( id );
        else
            result.append( "<unset>" );
        result.append( ", inherited: " );
        result.append( inherited );
        result.append( ", reports: " );
        result.append( reports );
        result.append( ')' );
        return result.toString();
    }

} // ReportSetImpl
