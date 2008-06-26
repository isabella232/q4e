/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.BuildBase;
import org.devzuz.q.maven.pom.FiltersType;
import org.devzuz.q.maven.pom.PluginManagement;
import org.devzuz.q.maven.pom.PluginsType;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.ResourcesType;
import org.devzuz.q.maven.pom.TestResourcesType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Build Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getDefaultGoal <em>Default Goal</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getTestResources <em>Test Resources</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getDirectory <em>Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getFinalName <em>Final Name</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getFilters <em>Filters</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getPluginManagement <em>Plugin Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildBaseImpl#getPlugins <em>Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BuildBaseImpl extends EObjectImpl implements BuildBase
{
    /**
     * The default value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultGoal()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_GOAL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDefaultGoal()
     * @generated
     * @ordered
     */
    protected String defaultGoal = DEFAULT_GOAL_EDEFAULT;

    /**
     * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResources()
     * @generated
     * @ordered
     */
    protected ResourcesType resources;

    /**
     * This is true if the Resources containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean resourcesESet;

    /**
     * The cached value of the '{@link #getTestResources() <em>Test Resources</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTestResources()
     * @generated
     * @ordered
     */
    protected TestResourcesType testResources;

    /**
     * This is true if the Test Resources containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean testResourcesESet;

    /**
     * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDirectory()
     * @generated
     * @ordered
     */
    protected static final String DIRECTORY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDirectory()
     * @generated
     * @ordered
     */
    protected String directory = DIRECTORY_EDEFAULT;

    /**
     * The default value of the '{@link #getFinalName() <em>Final Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFinalName()
     * @generated
     * @ordered
     */
    protected static final String FINAL_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFinalName() <em>Final Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFinalName()
     * @generated
     * @ordered
     */
    protected String finalName = FINAL_NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getFilters() <em>Filters</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFilters()
     * @generated
     * @ordered
     */
    protected FiltersType filters;

    /**
     * This is true if the Filters containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean filtersESet;

    /**
     * The cached value of the '{@link #getPluginManagement() <em>Plugin Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPluginManagement()
     * @generated
     * @ordered
     */
    protected PluginManagement pluginManagement;

    /**
     * This is true if the Plugin Management containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean pluginManagementESet;

    /**
     * The cached value of the '{@link #getPlugins() <em>Plugins</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPlugins()
     * @generated
     * @ordered
     */
    protected PluginsType plugins;

    /**
     * This is true if the Plugins containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean pluginsESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected BuildBaseImpl()
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
        return PomPackage.Literals.BUILD_BASE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDefaultGoal()
    {
        return defaultGoal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDefaultGoal(String newDefaultGoal)
    {
        String oldDefaultGoal = defaultGoal;
        defaultGoal = newDefaultGoal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__DEFAULT_GOAL, oldDefaultGoal, defaultGoal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesType getResources()
    {
        return resources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResources(ResourcesType newResources, NotificationChain msgs)
    {
        ResourcesType oldResources = resources;
        resources = newResources;
        boolean oldResourcesESet = resourcesESet;
        resourcesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__RESOURCES, oldResources, newResources, !oldResourcesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResources(ResourcesType newResources)
    {
        if (newResources != resources)
        {
            NotificationChain msgs = null;
            if (resources != null)
                msgs = ((InternalEObject)resources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__RESOURCES, null, msgs);
            if (newResources != null)
                msgs = ((InternalEObject)newResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__RESOURCES, null, msgs);
            msgs = basicSetResources(newResources, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldResourcesESet = resourcesESet;
            resourcesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__RESOURCES, newResources, newResources, !oldResourcesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetResources(NotificationChain msgs)
    {
        ResourcesType oldResources = resources;
        resources = null;
        boolean oldResourcesESet = resourcesESet;
        resourcesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__RESOURCES, oldResources, null, oldResourcesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetResources()
    {
        if (resources != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)resources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__RESOURCES, null, msgs);
            msgs = basicUnsetResources(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldResourcesESet = resourcesESet;
            resourcesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__RESOURCES, null, null, oldResourcesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetResources()
    {
        return resourcesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TestResourcesType getTestResources()
    {
        return testResources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTestResources(TestResourcesType newTestResources, NotificationChain msgs)
    {
        TestResourcesType oldTestResources = testResources;
        testResources = newTestResources;
        boolean oldTestResourcesESet = testResourcesESet;
        testResourcesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__TEST_RESOURCES, oldTestResources, newTestResources, !oldTestResourcesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTestResources(TestResourcesType newTestResources)
    {
        if (newTestResources != testResources)
        {
            NotificationChain msgs = null;
            if (testResources != null)
                msgs = ((InternalEObject)testResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__TEST_RESOURCES, null, msgs);
            if (newTestResources != null)
                msgs = ((InternalEObject)newTestResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__TEST_RESOURCES, null, msgs);
            msgs = basicSetTestResources(newTestResources, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldTestResourcesESet = testResourcesESet;
            testResourcesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__TEST_RESOURCES, newTestResources, newTestResources, !oldTestResourcesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetTestResources(NotificationChain msgs)
    {
        TestResourcesType oldTestResources = testResources;
        testResources = null;
        boolean oldTestResourcesESet = testResourcesESet;
        testResourcesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__TEST_RESOURCES, oldTestResources, null, oldTestResourcesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTestResources()
    {
        if (testResources != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)testResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__TEST_RESOURCES, null, msgs);
            msgs = basicUnsetTestResources(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldTestResourcesESet = testResourcesESet;
            testResourcesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__TEST_RESOURCES, null, null, oldTestResourcesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTestResources()
    {
        return testResourcesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDirectory()
    {
        return directory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDirectory(String newDirectory)
    {
        String oldDirectory = directory;
        directory = newDirectory;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__DIRECTORY, oldDirectory, directory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFinalName()
    {
        return finalName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFinalName(String newFinalName)
    {
        String oldFinalName = finalName;
        finalName = newFinalName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__FINAL_NAME, oldFinalName, finalName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FiltersType getFilters()
    {
        return filters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFilters(FiltersType newFilters, NotificationChain msgs)
    {
        FiltersType oldFilters = filters;
        filters = newFilters;
        boolean oldFiltersESet = filtersESet;
        filtersESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__FILTERS, oldFilters, newFilters, !oldFiltersESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFilters(FiltersType newFilters)
    {
        if (newFilters != filters)
        {
            NotificationChain msgs = null;
            if (filters != null)
                msgs = ((InternalEObject)filters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__FILTERS, null, msgs);
            if (newFilters != null)
                msgs = ((InternalEObject)newFilters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__FILTERS, null, msgs);
            msgs = basicSetFilters(newFilters, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldFiltersESet = filtersESet;
            filtersESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__FILTERS, newFilters, newFilters, !oldFiltersESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetFilters(NotificationChain msgs)
    {
        FiltersType oldFilters = filters;
        filters = null;
        boolean oldFiltersESet = filtersESet;
        filtersESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__FILTERS, oldFilters, null, oldFiltersESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetFilters()
    {
        if (filters != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)filters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__FILTERS, null, msgs);
            msgs = basicUnsetFilters(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldFiltersESet = filtersESet;
            filtersESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__FILTERS, null, null, oldFiltersESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetFilters()
    {
        return filtersESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginManagement getPluginManagement()
    {
        return pluginManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPluginManagement(PluginManagement newPluginManagement, NotificationChain msgs)
    {
        PluginManagement oldPluginManagement = pluginManagement;
        pluginManagement = newPluginManagement;
        boolean oldPluginManagementESet = pluginManagementESet;
        pluginManagementESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, oldPluginManagement, newPluginManagement, !oldPluginManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPluginManagement(PluginManagement newPluginManagement)
    {
        if (newPluginManagement != pluginManagement)
        {
            NotificationChain msgs = null;
            if (pluginManagement != null)
                msgs = ((InternalEObject)pluginManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, null, msgs);
            if (newPluginManagement != null)
                msgs = ((InternalEObject)newPluginManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, null, msgs);
            msgs = basicSetPluginManagement(newPluginManagement, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginManagementESet = pluginManagementESet;
            pluginManagementESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, newPluginManagement, newPluginManagement, !oldPluginManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetPluginManagement(NotificationChain msgs)
    {
        PluginManagement oldPluginManagement = pluginManagement;
        pluginManagement = null;
        boolean oldPluginManagementESet = pluginManagementESet;
        pluginManagementESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, oldPluginManagement, null, oldPluginManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPluginManagement()
    {
        if (pluginManagement != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)pluginManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, null, msgs);
            msgs = basicUnsetPluginManagement(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginManagementESet = pluginManagementESet;
            pluginManagementESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT, null, null, oldPluginManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPluginManagement()
    {
        return pluginManagementESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginsType getPlugins()
    {
        return plugins;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPlugins(PluginsType newPlugins, NotificationChain msgs)
    {
        PluginsType oldPlugins = plugins;
        plugins = newPlugins;
        boolean oldPluginsESet = pluginsESet;
        pluginsESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__PLUGINS, oldPlugins, newPlugins, !oldPluginsESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPlugins(PluginsType newPlugins)
    {
        if (newPlugins != plugins)
        {
            NotificationChain msgs = null;
            if (plugins != null)
                msgs = ((InternalEObject)plugins).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGINS, null, msgs);
            if (newPlugins != null)
                msgs = ((InternalEObject)newPlugins).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGINS, null, msgs);
            msgs = basicSetPlugins(newPlugins, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginsESet = pluginsESet;
            pluginsESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD_BASE__PLUGINS, newPlugins, newPlugins, !oldPluginsESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetPlugins(NotificationChain msgs)
    {
        PluginsType oldPlugins = plugins;
        plugins = null;
        boolean oldPluginsESet = pluginsESet;
        pluginsESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__PLUGINS, oldPlugins, null, oldPluginsESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPlugins()
    {
        if (plugins != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)plugins).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD_BASE__PLUGINS, null, msgs);
            msgs = basicUnsetPlugins(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginsESet = pluginsESet;
            pluginsESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD_BASE__PLUGINS, null, null, oldPluginsESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPlugins()
    {
        return pluginsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
        switch (featureID)
        {
            case PomPackage.BUILD_BASE__RESOURCES:
                return basicUnsetResources(msgs);
            case PomPackage.BUILD_BASE__TEST_RESOURCES:
                return basicUnsetTestResources(msgs);
            case PomPackage.BUILD_BASE__FILTERS:
                return basicUnsetFilters(msgs);
            case PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT:
                return basicUnsetPluginManagement(msgs);
            case PomPackage.BUILD_BASE__PLUGINS:
                return basicUnsetPlugins(msgs);
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
        switch (featureID)
        {
            case PomPackage.BUILD_BASE__DEFAULT_GOAL:
                return getDefaultGoal();
            case PomPackage.BUILD_BASE__RESOURCES:
                return getResources();
            case PomPackage.BUILD_BASE__TEST_RESOURCES:
                return getTestResources();
            case PomPackage.BUILD_BASE__DIRECTORY:
                return getDirectory();
            case PomPackage.BUILD_BASE__FINAL_NAME:
                return getFinalName();
            case PomPackage.BUILD_BASE__FILTERS:
                return getFilters();
            case PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT:
                return getPluginManagement();
            case PomPackage.BUILD_BASE__PLUGINS:
                return getPlugins();
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
        switch (featureID)
        {
            case PomPackage.BUILD_BASE__DEFAULT_GOAL:
                setDefaultGoal((String)newValue);
                return;
            case PomPackage.BUILD_BASE__RESOURCES:
                setResources((ResourcesType)newValue);
                return;
            case PomPackage.BUILD_BASE__TEST_RESOURCES:
                setTestResources((TestResourcesType)newValue);
                return;
            case PomPackage.BUILD_BASE__DIRECTORY:
                setDirectory((String)newValue);
                return;
            case PomPackage.BUILD_BASE__FINAL_NAME:
                setFinalName((String)newValue);
                return;
            case PomPackage.BUILD_BASE__FILTERS:
                setFilters((FiltersType)newValue);
                return;
            case PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT:
                setPluginManagement((PluginManagement)newValue);
                return;
            case PomPackage.BUILD_BASE__PLUGINS:
                setPlugins((PluginsType)newValue);
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
        switch (featureID)
        {
            case PomPackage.BUILD_BASE__DEFAULT_GOAL:
                setDefaultGoal(DEFAULT_GOAL_EDEFAULT);
                return;
            case PomPackage.BUILD_BASE__RESOURCES:
                unsetResources();
                return;
            case PomPackage.BUILD_BASE__TEST_RESOURCES:
                unsetTestResources();
                return;
            case PomPackage.BUILD_BASE__DIRECTORY:
                setDirectory(DIRECTORY_EDEFAULT);
                return;
            case PomPackage.BUILD_BASE__FINAL_NAME:
                setFinalName(FINAL_NAME_EDEFAULT);
                return;
            case PomPackage.BUILD_BASE__FILTERS:
                unsetFilters();
                return;
            case PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT:
                unsetPluginManagement();
                return;
            case PomPackage.BUILD_BASE__PLUGINS:
                unsetPlugins();
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
        switch (featureID)
        {
            case PomPackage.BUILD_BASE__DEFAULT_GOAL:
                return DEFAULT_GOAL_EDEFAULT == null ? defaultGoal != null : !DEFAULT_GOAL_EDEFAULT.equals(defaultGoal);
            case PomPackage.BUILD_BASE__RESOURCES:
                return isSetResources();
            case PomPackage.BUILD_BASE__TEST_RESOURCES:
                return isSetTestResources();
            case PomPackage.BUILD_BASE__DIRECTORY:
                return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals(directory);
            case PomPackage.BUILD_BASE__FINAL_NAME:
                return FINAL_NAME_EDEFAULT == null ? finalName != null : !FINAL_NAME_EDEFAULT.equals(finalName);
            case PomPackage.BUILD_BASE__FILTERS:
                return isSetFilters();
            case PomPackage.BUILD_BASE__PLUGIN_MANAGEMENT:
                return isSetPluginManagement();
            case PomPackage.BUILD_BASE__PLUGINS:
                return isSetPlugins();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString()
    {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (defaultGoal: ");
        result.append(defaultGoal);
        result.append(", directory: ");
        result.append(directory);
        result.append(", finalName: ");
        result.append(finalName);
        result.append(')');
        return result.toString();
    }

} //BuildBaseImpl
