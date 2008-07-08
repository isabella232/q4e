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

import org.devzuz.q.maven.pom.Build;
import org.devzuz.q.maven.pom.Extension;
import org.devzuz.q.maven.pom.Plugin;
import org.devzuz.q.maven.pom.PluginManagement;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.Resource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Build</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getSourceDirectory <em>Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getScriptSourceDirectory <em>Script Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestSourceDirectory <em>Test Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getOutputDirectory <em>Output Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestOutputDirectory <em>Test Output Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getExtensions <em>Extensions</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getDefaultGoal <em>Default Goal</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getResources <em>Resources</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestResources <em>Test Resources</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getDirectory <em>Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getFinalName <em>Final Name</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getPluginManagement <em>Plugin Management</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getPlugins <em>Plugins</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getFilters <em>Filters</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class BuildImpl
    extends EObjectImpl
    implements Build
{
    /**
     * The default value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSourceDirectory()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getSourceDirectory()
     * @generated
     * @ordered
     */
    protected String sourceDirectory = SOURCE_DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getScriptSourceDirectory() <em>Script Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getScriptSourceDirectory()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_SOURCE_DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptSourceDirectory() <em>Script Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getScriptSourceDirectory()
     * @generated
     * @ordered
     */
    protected String scriptSourceDirectory = SCRIPT_SOURCE_DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getTestSourceDirectory() <em>Test Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTestSourceDirectory()
     * @generated
     * @ordered
     */
    protected static final String TEST_SOURCE_DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTestSourceDirectory() <em>Test Source Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTestSourceDirectory()
     * @generated
     * @ordered
     */
    protected String testSourceDirectory = TEST_SOURCE_DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOutputDirectory()
     * @generated
     * @ordered
     */
    protected static final String OUTPUT_DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getOutputDirectory()
     * @generated
     * @ordered
     */
    protected String outputDirectory = OUTPUT_DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getTestOutputDirectory() <em>Test Output Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTestOutputDirectory()
     * @generated
     * @ordered
     */
    protected static final String TEST_OUTPUT_DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTestOutputDirectory() <em>Test Output Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTestOutputDirectory()
     * @generated
     * @ordered
     */
    protected String testOutputDirectory = TEST_OUTPUT_DIRECTORY_EDEFAULT;

    /**
     * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getExtensions()
     * @generated
     * @ordered
     */
    protected EList<Extension> extensions;

    /**
     * The default value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getDefaultGoal()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_GOAL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getDefaultGoal()
     * @generated
     * @ordered
     */
    protected String defaultGoal = DEFAULT_GOAL_EDEFAULT;

    /**
     * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getResources()
     * @generated
     * @ordered
     */
    protected EList<Resource> resources;

    /**
     * The cached value of the '{@link #getTestResources() <em>Test Resources</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTestResources()
     * @generated
     * @ordered
     */
    protected EList<Resource> testResources;

    /**
     * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDirectory()
     * @generated
     * @ordered
     */
    protected static final String DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDirectory()
     * @generated
     * @ordered
     */
    protected String directory = DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getFinalName() <em>Final Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getFinalName()
     * @generated
     * @ordered
     */
    protected static final String FINAL_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFinalName() <em>Final Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getFinalName()
     * @generated
     * @ordered
     */
    protected String finalName = FINAL_NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getPluginManagement() <em>Plugin Management</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getPluginManagement()
     * @generated
     * @ordered
     */
    protected PluginManagement pluginManagement;

    /**
     * This is true if the Plugin Management containment reference has been set. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean pluginManagementESet;

    /**
     * The cached value of the '{@link #getPlugins() <em>Plugins</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getPlugins()
     * @generated
     * @ordered
     */
    protected EList<Plugin> plugins;

    /**
     * The cached value of the '{@link #getFilters() <em>Filters</em>}' attribute list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getFilters()
     * @generated
     * @ordered
     */
    protected EList<String> filters;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected BuildImpl()
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
        return PomPackage.Literals.BUILD;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getSourceDirectory()
    {
        return sourceDirectory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setSourceDirectory( String newSourceDirectory )
    {
        String oldSourceDirectory = sourceDirectory;
        sourceDirectory = newSourceDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__SOURCE_DIRECTORY,
                                            oldSourceDirectory, sourceDirectory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getScriptSourceDirectory()
    {
        return scriptSourceDirectory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setScriptSourceDirectory( String newScriptSourceDirectory )
    {
        String oldScriptSourceDirectory = scriptSourceDirectory;
        scriptSourceDirectory = newScriptSourceDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY,
                                            oldScriptSourceDirectory, scriptSourceDirectory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getTestSourceDirectory()
    {
        return testSourceDirectory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setTestSourceDirectory( String newTestSourceDirectory )
    {
        String oldTestSourceDirectory = testSourceDirectory;
        testSourceDirectory = newTestSourceDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__TEST_SOURCE_DIRECTORY,
                                            oldTestSourceDirectory, testSourceDirectory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getOutputDirectory()
    {
        return outputDirectory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setOutputDirectory( String newOutputDirectory )
    {
        String oldOutputDirectory = outputDirectory;
        outputDirectory = newOutputDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__OUTPUT_DIRECTORY,
                                            oldOutputDirectory, outputDirectory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getTestOutputDirectory()
    {
        return testOutputDirectory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setTestOutputDirectory( String newTestOutputDirectory )
    {
        String oldTestOutputDirectory = testOutputDirectory;
        testOutputDirectory = newTestOutputDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__TEST_OUTPUT_DIRECTORY,
                                            oldTestOutputDirectory, testOutputDirectory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Extension> getExtensions()
    {
        if ( extensions == null )
        {
            extensions =
                new EObjectContainmentEList.Unsettable<Extension>( Extension.class, this, PomPackage.BUILD__EXTENSIONS );
        }
        return extensions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetExtensions()
    {
        if ( extensions != null )
            ( (InternalEList.Unsettable<?>) extensions ).unset();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetExtensions()
    {
        return extensions != null && ( (InternalEList.Unsettable<?>) extensions ).isSet();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getDefaultGoal()
    {
        return defaultGoal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDefaultGoal( String newDefaultGoal )
    {
        String oldDefaultGoal = defaultGoal;
        defaultGoal = newDefaultGoal;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__DEFAULT_GOAL, oldDefaultGoal,
                                            defaultGoal ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Resource> getResources()
    {
        if ( resources == null )
        {
            resources =
                new EObjectContainmentEList.Unsettable<Resource>( Resource.class, this, PomPackage.BUILD__RESOURCES );
        }
        return resources;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetResources()
    {
        if ( resources != null )
            ( (InternalEList.Unsettable<?>) resources ).unset();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetResources()
    {
        return resources != null && ( (InternalEList.Unsettable<?>) resources ).isSet();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Resource> getTestResources()
    {
        if ( testResources == null )
        {
            testResources =
                new EObjectContainmentEList.Unsettable<Resource>( Resource.class, this,
                                                                  PomPackage.BUILD__TEST_RESOURCES );
        }
        return testResources;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetTestResources()
    {
        if ( testResources != null )
            ( (InternalEList.Unsettable<?>) testResources ).unset();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetTestResources()
    {
        return testResources != null && ( (InternalEList.Unsettable<?>) testResources ).isSet();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getDirectory()
    {
        return directory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDirectory( String newDirectory )
    {
        String oldDirectory = directory;
        directory = newDirectory;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__DIRECTORY, oldDirectory,
                                            directory ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getFinalName()
    {
        return finalName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setFinalName( String newFinalName )
    {
        String oldFinalName = finalName;
        finalName = newFinalName;
        if ( eNotificationRequired() )
            eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__FINAL_NAME, oldFinalName,
                                            finalName ) );
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public PluginManagement getPluginManagement()
    {
        return pluginManagement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetPluginManagement( PluginManagement newPluginManagement, NotificationChain msgs )
    {
        PluginManagement oldPluginManagement = pluginManagement;
        pluginManagement = newPluginManagement;
        boolean oldPluginManagementESet = pluginManagementESet;
        pluginManagementESet = true;
        if ( eNotificationRequired() )
        {
            ENotificationImpl notification =
                new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__PLUGIN_MANAGEMENT,
                                       oldPluginManagement, newPluginManagement, !oldPluginManagementESet );
            if ( msgs == null )
                msgs = notification;
            else
                msgs.add( notification );
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setPluginManagement( PluginManagement newPluginManagement )
    {
        if ( newPluginManagement != pluginManagement )
        {
            NotificationChain msgs = null;
            if ( pluginManagement != null )
                msgs =
                    ( (InternalEObject) pluginManagement ).eInverseRemove( this, EOPPOSITE_FEATURE_BASE
                        - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs );
            if ( newPluginManagement != null )
                msgs =
                    ( (InternalEObject) newPluginManagement ).eInverseAdd( this, EOPPOSITE_FEATURE_BASE
                        - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs );
            msgs = basicSetPluginManagement( newPluginManagement, msgs );
            if ( msgs != null )
                msgs.dispatch();
        }
        else
        {
            boolean oldPluginManagementESet = pluginManagementESet;
            pluginManagementESet = true;
            if ( eNotificationRequired() )
                eNotify( new ENotificationImpl( this, Notification.SET, PomPackage.BUILD__PLUGIN_MANAGEMENT,
                                                newPluginManagement, newPluginManagement, !oldPluginManagementESet ) );
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicUnsetPluginManagement( NotificationChain msgs )
    {
        PluginManagement oldPluginManagement = pluginManagement;
        pluginManagement = null;
        boolean oldPluginManagementESet = pluginManagementESet;
        pluginManagementESet = false;
        if ( eNotificationRequired() )
        {
            ENotificationImpl notification =
                new ENotificationImpl( this, Notification.UNSET, PomPackage.BUILD__PLUGIN_MANAGEMENT,
                                       oldPluginManagement, null, oldPluginManagementESet );
            if ( msgs == null )
                msgs = notification;
            else
                msgs.add( notification );
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetPluginManagement()
    {
        if ( pluginManagement != null )
        {
            NotificationChain msgs = null;
            msgs =
                ( (InternalEObject) pluginManagement ).eInverseRemove( this, EOPPOSITE_FEATURE_BASE
                    - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs );
            msgs = basicUnsetPluginManagement( msgs );
            if ( msgs != null )
                msgs.dispatch();
        }
        else
        {
            boolean oldPluginManagementESet = pluginManagementESet;
            pluginManagementESet = false;
            if ( eNotificationRequired() )
                eNotify( new ENotificationImpl( this, Notification.UNSET, PomPackage.BUILD__PLUGIN_MANAGEMENT, null,
                                                null, oldPluginManagementESet ) );
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetPluginManagement()
    {
        return pluginManagementESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Plugin> getPlugins()
    {
        if ( plugins == null )
        {
            plugins = new EObjectContainmentEList.Unsettable<Plugin>( Plugin.class, this, PomPackage.BUILD__PLUGINS );
        }
        return plugins;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetPlugins()
    {
        if ( plugins != null )
            ( (InternalEList.Unsettable<?>) plugins ).unset();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetPlugins()
    {
        return plugins != null && ( (InternalEList.Unsettable<?>) plugins ).isSet();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<String> getFilters()
    {
        if ( filters == null )
        {
            filters = new EDataTypeUniqueEList<String>( String.class, this, PomPackage.BUILD__FILTERS );
        }
        return filters;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove( InternalEObject otherEnd, int featureID, NotificationChain msgs )
    {
        switch ( featureID )
        {
            case PomPackage.BUILD__EXTENSIONS:
                return ( (InternalEList<?>) getExtensions() ).basicRemove( otherEnd, msgs );
            case PomPackage.BUILD__RESOURCES:
                return ( (InternalEList<?>) getResources() ).basicRemove( otherEnd, msgs );
            case PomPackage.BUILD__TEST_RESOURCES:
                return ( (InternalEList<?>) getTestResources() ).basicRemove( otherEnd, msgs );
            case PomPackage.BUILD__PLUGIN_MANAGEMENT:
                return basicUnsetPluginManagement( msgs );
            case PomPackage.BUILD__PLUGINS:
                return ( (InternalEList<?>) getPlugins() ).basicRemove( otherEnd, msgs );
        }
        return super.eInverseRemove( otherEnd, featureID, msgs );
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
            case PomPackage.BUILD__SOURCE_DIRECTORY:
                return getSourceDirectory();
            case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
                return getScriptSourceDirectory();
            case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
                return getTestSourceDirectory();
            case PomPackage.BUILD__OUTPUT_DIRECTORY:
                return getOutputDirectory();
            case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
                return getTestOutputDirectory();
            case PomPackage.BUILD__EXTENSIONS:
                return getExtensions();
            case PomPackage.BUILD__DEFAULT_GOAL:
                return getDefaultGoal();
            case PomPackage.BUILD__RESOURCES:
                return getResources();
            case PomPackage.BUILD__TEST_RESOURCES:
                return getTestResources();
            case PomPackage.BUILD__DIRECTORY:
                return getDirectory();
            case PomPackage.BUILD__FINAL_NAME:
                return getFinalName();
            case PomPackage.BUILD__PLUGIN_MANAGEMENT:
                return getPluginManagement();
            case PomPackage.BUILD__PLUGINS:
                return getPlugins();
            case PomPackage.BUILD__FILTERS:
                return getFilters();
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
            case PomPackage.BUILD__SOURCE_DIRECTORY:
                setSourceDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
                setScriptSourceDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
                setTestSourceDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__OUTPUT_DIRECTORY:
                setOutputDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
                setTestOutputDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__EXTENSIONS:
                getExtensions().clear();
                getExtensions().addAll( (Collection<? extends Extension>) newValue );
                return;
            case PomPackage.BUILD__DEFAULT_GOAL:
                setDefaultGoal( (String) newValue );
                return;
            case PomPackage.BUILD__RESOURCES:
                getResources().clear();
                getResources().addAll( (Collection<? extends Resource>) newValue );
                return;
            case PomPackage.BUILD__TEST_RESOURCES:
                getTestResources().clear();
                getTestResources().addAll( (Collection<? extends Resource>) newValue );
                return;
            case PomPackage.BUILD__DIRECTORY:
                setDirectory( (String) newValue );
                return;
            case PomPackage.BUILD__FINAL_NAME:
                setFinalName( (String) newValue );
                return;
            case PomPackage.BUILD__PLUGIN_MANAGEMENT:
                setPluginManagement( (PluginManagement) newValue );
                return;
            case PomPackage.BUILD__PLUGINS:
                getPlugins().clear();
                getPlugins().addAll( (Collection<? extends Plugin>) newValue );
                return;
            case PomPackage.BUILD__FILTERS:
                getFilters().clear();
                getFilters().addAll( (Collection<? extends String>) newValue );
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
            case PomPackage.BUILD__SOURCE_DIRECTORY:
                setSourceDirectory( SOURCE_DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
                setScriptSourceDirectory( SCRIPT_SOURCE_DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
                setTestSourceDirectory( TEST_SOURCE_DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__OUTPUT_DIRECTORY:
                setOutputDirectory( OUTPUT_DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
                setTestOutputDirectory( TEST_OUTPUT_DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__EXTENSIONS:
                unsetExtensions();
                return;
            case PomPackage.BUILD__DEFAULT_GOAL:
                setDefaultGoal( DEFAULT_GOAL_EDEFAULT );
                return;
            case PomPackage.BUILD__RESOURCES:
                unsetResources();
                return;
            case PomPackage.BUILD__TEST_RESOURCES:
                unsetTestResources();
                return;
            case PomPackage.BUILD__DIRECTORY:
                setDirectory( DIRECTORY_EDEFAULT );
                return;
            case PomPackage.BUILD__FINAL_NAME:
                setFinalName( FINAL_NAME_EDEFAULT );
                return;
            case PomPackage.BUILD__PLUGIN_MANAGEMENT:
                unsetPluginManagement();
                return;
            case PomPackage.BUILD__PLUGINS:
                unsetPlugins();
                return;
            case PomPackage.BUILD__FILTERS:
                getFilters().clear();
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
            case PomPackage.BUILD__SOURCE_DIRECTORY:
                return SOURCE_DIRECTORY_EDEFAULT == null ? sourceDirectory != null
                                : !SOURCE_DIRECTORY_EDEFAULT.equals( sourceDirectory );
            case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
                return SCRIPT_SOURCE_DIRECTORY_EDEFAULT == null ? scriptSourceDirectory != null
                                : !SCRIPT_SOURCE_DIRECTORY_EDEFAULT.equals( scriptSourceDirectory );
            case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
                return TEST_SOURCE_DIRECTORY_EDEFAULT == null ? testSourceDirectory != null
                                : !TEST_SOURCE_DIRECTORY_EDEFAULT.equals( testSourceDirectory );
            case PomPackage.BUILD__OUTPUT_DIRECTORY:
                return OUTPUT_DIRECTORY_EDEFAULT == null ? outputDirectory != null
                                : !OUTPUT_DIRECTORY_EDEFAULT.equals( outputDirectory );
            case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
                return TEST_OUTPUT_DIRECTORY_EDEFAULT == null ? testOutputDirectory != null
                                : !TEST_OUTPUT_DIRECTORY_EDEFAULT.equals( testOutputDirectory );
            case PomPackage.BUILD__EXTENSIONS:
                return isSetExtensions();
            case PomPackage.BUILD__DEFAULT_GOAL:
                return DEFAULT_GOAL_EDEFAULT == null ? defaultGoal != null
                                : !DEFAULT_GOAL_EDEFAULT.equals( defaultGoal );
            case PomPackage.BUILD__RESOURCES:
                return isSetResources();
            case PomPackage.BUILD__TEST_RESOURCES:
                return isSetTestResources();
            case PomPackage.BUILD__DIRECTORY:
                return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals( directory );
            case PomPackage.BUILD__FINAL_NAME:
                return FINAL_NAME_EDEFAULT == null ? finalName != null : !FINAL_NAME_EDEFAULT.equals( finalName );
            case PomPackage.BUILD__PLUGIN_MANAGEMENT:
                return isSetPluginManagement();
            case PomPackage.BUILD__PLUGINS:
                return isSetPlugins();
            case PomPackage.BUILD__FILTERS:
                return filters != null && !filters.isEmpty();
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
        result.append( " (sourceDirectory: " );
        result.append( sourceDirectory );
        result.append( ", scriptSourceDirectory: " );
        result.append( scriptSourceDirectory );
        result.append( ", testSourceDirectory: " );
        result.append( testSourceDirectory );
        result.append( ", outputDirectory: " );
        result.append( outputDirectory );
        result.append( ", testOutputDirectory: " );
        result.append( testOutputDirectory );
        result.append( ", defaultGoal: " );
        result.append( defaultGoal );
        result.append( ", directory: " );
        result.append( directory );
        result.append( ", finalName: " );
        result.append( finalName );
        result.append( ", filters: " );
        result.append( filters );
        result.append( ')' );
        return result.toString();
    }

} // BuildImpl
