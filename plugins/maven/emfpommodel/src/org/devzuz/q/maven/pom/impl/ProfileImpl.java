/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.Activation;
import org.devzuz.q.maven.pom.BuildBase;
import org.devzuz.q.maven.pom.DependenciesType2;
import org.devzuz.q.maven.pom.DependencyManagement;
import org.devzuz.q.maven.pom.DistributionManagement;
import org.devzuz.q.maven.pom.ModulesType1;
import org.devzuz.q.maven.pom.PluginRepositoriesType1;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.Profile;
import org.devzuz.q.maven.pom.PropertiesType1;
import org.devzuz.q.maven.pom.Reporting;
import org.devzuz.q.maven.pom.ReportsType2;
import org.devzuz.q.maven.pom.RepositoriesType1;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getActivation <em>Activation</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getBuild <em>Build</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getModules <em>Modules</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getPluginRepositories <em>Plugin Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getReports <em>Reports</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getReporting <em>Reporting</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getDependencyManagement <em>Dependency Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getDistributionManagement <em>Distribution Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ProfileImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfileImpl extends EObjectImpl implements Profile
{
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The cached value of the '{@link #getActivation() <em>Activation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivation()
     * @generated
     * @ordered
     */
    protected Activation activation;

    /**
     * This is true if the Activation containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean activationESet;

    /**
     * The cached value of the '{@link #getBuild() <em>Build</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBuild()
     * @generated
     * @ordered
     */
    protected BuildBase build;

    /**
     * This is true if the Build containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean buildESet;

    /**
     * The cached value of the '{@link #getModules() <em>Modules</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModules()
     * @generated
     * @ordered
     */
    protected ModulesType1 modules;

    /**
     * This is true if the Modules containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean modulesESet;

    /**
     * The cached value of the '{@link #getRepositories() <em>Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRepositories()
     * @generated
     * @ordered
     */
    protected RepositoriesType1 repositories;

    /**
     * This is true if the Repositories containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean repositoriesESet;

    /**
     * The cached value of the '{@link #getPluginRepositories() <em>Plugin Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPluginRepositories()
     * @generated
     * @ordered
     */
    protected PluginRepositoriesType1 pluginRepositories;

    /**
     * This is true if the Plugin Repositories containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean pluginRepositoriesESet;

    /**
     * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDependencies()
     * @generated
     * @ordered
     */
    protected DependenciesType2 dependencies;

    /**
     * This is true if the Dependencies containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean dependenciesESet;

    /**
     * The cached value of the '{@link #getReports() <em>Reports</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReports()
     * @generated
     * @ordered
     */
    protected ReportsType2 reports;

    /**
     * This is true if the Reports containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reportsESet;

    /**
     * The cached value of the '{@link #getReporting() <em>Reporting</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getReporting()
     * @generated
     * @ordered
     */
    protected Reporting reporting;

    /**
     * This is true if the Reporting containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reportingESet;

    /**
     * The cached value of the '{@link #getDependencyManagement() <em>Dependency Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDependencyManagement()
     * @generated
     * @ordered
     */
    protected DependencyManagement dependencyManagement;

    /**
     * This is true if the Dependency Management containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean dependencyManagementESet;

    /**
     * The cached value of the '{@link #getDistributionManagement() <em>Distribution Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistributionManagement()
     * @generated
     * @ordered
     */
    protected DistributionManagement distributionManagement;

    /**
     * This is true if the Distribution Management containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean distributionManagementESet;

    /**
     * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProperties()
     * @generated
     * @ordered
     */
    protected PropertiesType1 properties;

    /**
     * This is true if the Properties containment reference has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean propertiesESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProfileImpl()
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
        return PomPackage.Literals.PROFILE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId()
    {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId)
    {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Activation getActivation()
    {
        return activation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActivation(Activation newActivation, NotificationChain msgs)
    {
        Activation oldActivation = activation;
        activation = newActivation;
        boolean oldActivationESet = activationESet;
        activationESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__ACTIVATION, oldActivation, newActivation, !oldActivationESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivation(Activation newActivation)
    {
        if (newActivation != activation)
        {
            NotificationChain msgs = null;
            if (activation != null)
                msgs = ((InternalEObject)activation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__ACTIVATION, null, msgs);
            if (newActivation != null)
                msgs = ((InternalEObject)newActivation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__ACTIVATION, null, msgs);
            msgs = basicSetActivation(newActivation, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldActivationESet = activationESet;
            activationESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__ACTIVATION, newActivation, newActivation, !oldActivationESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetActivation(NotificationChain msgs)
    {
        Activation oldActivation = activation;
        activation = null;
        boolean oldActivationESet = activationESet;
        activationESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__ACTIVATION, oldActivation, null, oldActivationESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetActivation()
    {
        if (activation != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)activation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__ACTIVATION, null, msgs);
            msgs = basicUnsetActivation(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldActivationESet = activationESet;
            activationESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__ACTIVATION, null, null, oldActivationESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetActivation()
    {
        return activationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BuildBase getBuild()
    {
        return build;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBuild(BuildBase newBuild, NotificationChain msgs)
    {
        BuildBase oldBuild = build;
        build = newBuild;
        boolean oldBuildESet = buildESet;
        buildESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__BUILD, oldBuild, newBuild, !oldBuildESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBuild(BuildBase newBuild)
    {
        if (newBuild != build)
        {
            NotificationChain msgs = null;
            if (build != null)
                msgs = ((InternalEObject)build).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__BUILD, null, msgs);
            if (newBuild != null)
                msgs = ((InternalEObject)newBuild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__BUILD, null, msgs);
            msgs = basicSetBuild(newBuild, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldBuildESet = buildESet;
            buildESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__BUILD, newBuild, newBuild, !oldBuildESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetBuild(NotificationChain msgs)
    {
        BuildBase oldBuild = build;
        build = null;
        boolean oldBuildESet = buildESet;
        buildESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__BUILD, oldBuild, null, oldBuildESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetBuild()
    {
        if (build != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)build).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__BUILD, null, msgs);
            msgs = basicUnsetBuild(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldBuildESet = buildESet;
            buildESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__BUILD, null, null, oldBuildESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetBuild()
    {
        return buildESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModulesType1 getModules()
    {
        return modules;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetModules(ModulesType1 newModules, NotificationChain msgs)
    {
        ModulesType1 oldModules = modules;
        modules = newModules;
        boolean oldModulesESet = modulesESet;
        modulesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__MODULES, oldModules, newModules, !oldModulesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModules(ModulesType1 newModules)
    {
        if (newModules != modules)
        {
            NotificationChain msgs = null;
            if (modules != null)
                msgs = ((InternalEObject)modules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__MODULES, null, msgs);
            if (newModules != null)
                msgs = ((InternalEObject)newModules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__MODULES, null, msgs);
            msgs = basicSetModules(newModules, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldModulesESet = modulesESet;
            modulesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__MODULES, newModules, newModules, !oldModulesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetModules(NotificationChain msgs)
    {
        ModulesType1 oldModules = modules;
        modules = null;
        boolean oldModulesESet = modulesESet;
        modulesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__MODULES, oldModules, null, oldModulesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetModules()
    {
        if (modules != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)modules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__MODULES, null, msgs);
            msgs = basicUnsetModules(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldModulesESet = modulesESet;
            modulesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__MODULES, null, null, oldModulesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetModules()
    {
        return modulesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RepositoriesType1 getRepositories()
    {
        return repositories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRepositories(RepositoriesType1 newRepositories, NotificationChain msgs)
    {
        RepositoriesType1 oldRepositories = repositories;
        repositories = newRepositories;
        boolean oldRepositoriesESet = repositoriesESet;
        repositoriesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPOSITORIES, oldRepositories, newRepositories, !oldRepositoriesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRepositories(RepositoriesType1 newRepositories)
    {
        if (newRepositories != repositories)
        {
            NotificationChain msgs = null;
            if (repositories != null)
                msgs = ((InternalEObject)repositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPOSITORIES, null, msgs);
            if (newRepositories != null)
                msgs = ((InternalEObject)newRepositories).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPOSITORIES, null, msgs);
            msgs = basicSetRepositories(newRepositories, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldRepositoriesESet = repositoriesESet;
            repositoriesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPOSITORIES, newRepositories, newRepositories, !oldRepositoriesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetRepositories(NotificationChain msgs)
    {
        RepositoriesType1 oldRepositories = repositories;
        repositories = null;
        boolean oldRepositoriesESet = repositoriesESet;
        repositoriesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPOSITORIES, oldRepositories, null, oldRepositoriesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetRepositories()
    {
        if (repositories != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)repositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPOSITORIES, null, msgs);
            msgs = basicUnsetRepositories(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldRepositoriesESet = repositoriesESet;
            repositoriesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPOSITORIES, null, null, oldRepositoriesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetRepositories()
    {
        return repositoriesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginRepositoriesType1 getPluginRepositories()
    {
        return pluginRepositories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPluginRepositories(PluginRepositoriesType1 newPluginRepositories, NotificationChain msgs)
    {
        PluginRepositoriesType1 oldPluginRepositories = pluginRepositories;
        pluginRepositories = newPluginRepositories;
        boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
        pluginRepositoriesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__PLUGIN_REPOSITORIES, oldPluginRepositories, newPluginRepositories, !oldPluginRepositoriesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPluginRepositories(PluginRepositoriesType1 newPluginRepositories)
    {
        if (newPluginRepositories != pluginRepositories)
        {
            NotificationChain msgs = null;
            if (pluginRepositories != null)
                msgs = ((InternalEObject)pluginRepositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PLUGIN_REPOSITORIES, null, msgs);
            if (newPluginRepositories != null)
                msgs = ((InternalEObject)newPluginRepositories).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PLUGIN_REPOSITORIES, null, msgs);
            msgs = basicSetPluginRepositories(newPluginRepositories, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
            pluginRepositoriesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__PLUGIN_REPOSITORIES, newPluginRepositories, newPluginRepositories, !oldPluginRepositoriesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetPluginRepositories(NotificationChain msgs)
    {
        PluginRepositoriesType1 oldPluginRepositories = pluginRepositories;
        pluginRepositories = null;
        boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
        pluginRepositoriesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__PLUGIN_REPOSITORIES, oldPluginRepositories, null, oldPluginRepositoriesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPluginRepositories()
    {
        if (pluginRepositories != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)pluginRepositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PLUGIN_REPOSITORIES, null, msgs);
            msgs = basicUnsetPluginRepositories(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
            pluginRepositoriesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__PLUGIN_REPOSITORIES, null, null, oldPluginRepositoriesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPluginRepositories()
    {
        return pluginRepositoriesESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependenciesType2 getDependencies()
    {
        return dependencies;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDependencies(DependenciesType2 newDependencies, NotificationChain msgs)
    {
        DependenciesType2 oldDependencies = dependencies;
        dependencies = newDependencies;
        boolean oldDependenciesESet = dependenciesESet;
        dependenciesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DEPENDENCIES, oldDependencies, newDependencies, !oldDependenciesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDependencies(DependenciesType2 newDependencies)
    {
        if (newDependencies != dependencies)
        {
            NotificationChain msgs = null;
            if (dependencies != null)
                msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCIES, null, msgs);
            if (newDependencies != null)
                msgs = ((InternalEObject)newDependencies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCIES, null, msgs);
            msgs = basicSetDependencies(newDependencies, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDependenciesESet = dependenciesESet;
            dependenciesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DEPENDENCIES, newDependencies, newDependencies, !oldDependenciesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetDependencies(NotificationChain msgs)
    {
        DependenciesType2 oldDependencies = dependencies;
        dependencies = null;
        boolean oldDependenciesESet = dependenciesESet;
        dependenciesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DEPENDENCIES, oldDependencies, null, oldDependenciesESet);
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
        if (dependencies != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCIES, null, msgs);
            msgs = basicUnsetDependencies(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDependenciesESet = dependenciesESet;
            dependenciesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DEPENDENCIES, null, null, oldDependenciesESet));
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
    public ReportsType2 getReports()
    {
        return reports;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReports(ReportsType2 newReports, NotificationChain msgs)
    {
        ReportsType2 oldReports = reports;
        reports = newReports;
        boolean oldReportsESet = reportsESet;
        reportsESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPORTS, oldReports, newReports, !oldReportsESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReports(ReportsType2 newReports)
    {
        if (newReports != reports)
        {
            NotificationChain msgs = null;
            if (reports != null)
                msgs = ((InternalEObject)reports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTS, null, msgs);
            if (newReports != null)
                msgs = ((InternalEObject)newReports).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTS, null, msgs);
            msgs = basicSetReports(newReports, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldReportsESet = reportsESet;
            reportsESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPORTS, newReports, newReports, !oldReportsESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetReports(NotificationChain msgs)
    {
        ReportsType2 oldReports = reports;
        reports = null;
        boolean oldReportsESet = reportsESet;
        reportsESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPORTS, oldReports, null, oldReportsESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReports()
    {
        if (reports != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)reports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTS, null, msgs);
            msgs = basicUnsetReports(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldReportsESet = reportsESet;
            reportsESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPORTS, null, null, oldReportsESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReports()
    {
        return reportsESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Reporting getReporting()
    {
        return reporting;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetReporting(Reporting newReporting, NotificationChain msgs)
    {
        Reporting oldReporting = reporting;
        reporting = newReporting;
        boolean oldReportingESet = reportingESet;
        reportingESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPORTING, oldReporting, newReporting, !oldReportingESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReporting(Reporting newReporting)
    {
        if (newReporting != reporting)
        {
            NotificationChain msgs = null;
            if (reporting != null)
                msgs = ((InternalEObject)reporting).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTING, null, msgs);
            if (newReporting != null)
                msgs = ((InternalEObject)newReporting).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTING, null, msgs);
            msgs = basicSetReporting(newReporting, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldReportingESet = reportingESet;
            reportingESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__REPORTING, newReporting, newReporting, !oldReportingESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetReporting(NotificationChain msgs)
    {
        Reporting oldReporting = reporting;
        reporting = null;
        boolean oldReportingESet = reportingESet;
        reportingESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPORTING, oldReporting, null, oldReportingESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReporting()
    {
        if (reporting != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)reporting).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__REPORTING, null, msgs);
            msgs = basicUnsetReporting(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldReportingESet = reportingESet;
            reportingESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__REPORTING, null, null, oldReportingESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReporting()
    {
        return reportingESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependencyManagement getDependencyManagement()
    {
        return dependencyManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDependencyManagement(DependencyManagement newDependencyManagement, NotificationChain msgs)
    {
        DependencyManagement oldDependencyManagement = dependencyManagement;
        dependencyManagement = newDependencyManagement;
        boolean oldDependencyManagementESet = dependencyManagementESet;
        dependencyManagementESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, oldDependencyManagement, newDependencyManagement, !oldDependencyManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDependencyManagement(DependencyManagement newDependencyManagement)
    {
        if (newDependencyManagement != dependencyManagement)
        {
            NotificationChain msgs = null;
            if (dependencyManagement != null)
                msgs = ((InternalEObject)dependencyManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, null, msgs);
            if (newDependencyManagement != null)
                msgs = ((InternalEObject)newDependencyManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, null, msgs);
            msgs = basicSetDependencyManagement(newDependencyManagement, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDependencyManagementESet = dependencyManagementESet;
            dependencyManagementESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, newDependencyManagement, newDependencyManagement, !oldDependencyManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetDependencyManagement(NotificationChain msgs)
    {
        DependencyManagement oldDependencyManagement = dependencyManagement;
        dependencyManagement = null;
        boolean oldDependencyManagementESet = dependencyManagementESet;
        dependencyManagementESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, oldDependencyManagement, null, oldDependencyManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDependencyManagement()
    {
        if (dependencyManagement != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)dependencyManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, null, msgs);
            msgs = basicUnsetDependencyManagement(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDependencyManagementESet = dependencyManagementESet;
            dependencyManagementESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DEPENDENCY_MANAGEMENT, null, null, oldDependencyManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDependencyManagement()
    {
        return dependencyManagementESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionManagement getDistributionManagement()
    {
        return distributionManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDistributionManagement(DistributionManagement newDistributionManagement, NotificationChain msgs)
    {
        DistributionManagement oldDistributionManagement = distributionManagement;
        distributionManagement = newDistributionManagement;
        boolean oldDistributionManagementESet = distributionManagementESet;
        distributionManagementESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, oldDistributionManagement, newDistributionManagement, !oldDistributionManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistributionManagement(DistributionManagement newDistributionManagement)
    {
        if (newDistributionManagement != distributionManagement)
        {
            NotificationChain msgs = null;
            if (distributionManagement != null)
                msgs = ((InternalEObject)distributionManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, null, msgs);
            if (newDistributionManagement != null)
                msgs = ((InternalEObject)newDistributionManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, null, msgs);
            msgs = basicSetDistributionManagement(newDistributionManagement, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDistributionManagementESet = distributionManagementESet;
            distributionManagementESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, newDistributionManagement, newDistributionManagement, !oldDistributionManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetDistributionManagement(NotificationChain msgs)
    {
        DistributionManagement oldDistributionManagement = distributionManagement;
        distributionManagement = null;
        boolean oldDistributionManagementESet = distributionManagementESet;
        distributionManagementESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, oldDistributionManagement, null, oldDistributionManagementESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDistributionManagement()
    {
        if (distributionManagement != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)distributionManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, null, msgs);
            msgs = basicUnsetDistributionManagement(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldDistributionManagementESet = distributionManagementESet;
            distributionManagementESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT, null, null, oldDistributionManagementESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDistributionManagement()
    {
        return distributionManagementESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesType1 getProperties()
    {
        return properties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetProperties(PropertiesType1 newProperties, NotificationChain msgs)
    {
        PropertiesType1 oldProperties = properties;
        properties = newProperties;
        boolean oldPropertiesESet = propertiesESet;
        propertiesESet = true;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__PROPERTIES, oldProperties, newProperties, !oldPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProperties(PropertiesType1 newProperties)
    {
        if (newProperties != properties)
        {
            NotificationChain msgs = null;
            if (properties != null)
                msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PROPERTIES, null, msgs);
            if (newProperties != null)
                msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PROPERTIES, null, msgs);
            msgs = basicSetProperties(newProperties, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPropertiesESet = propertiesESet;
            propertiesESet = true;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.PROFILE__PROPERTIES, newProperties, newProperties, !oldPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicUnsetProperties(NotificationChain msgs)
    {
        PropertiesType1 oldProperties = properties;
        properties = null;
        boolean oldPropertiesESet = propertiesESet;
        propertiesESet = false;
        if (eNotificationRequired())
        {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__PROPERTIES, oldProperties, null, oldPropertiesESet);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetProperties()
    {
        if (properties != null)
        {
            NotificationChain msgs = null;
            msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.PROFILE__PROPERTIES, null, msgs);
            msgs = basicUnsetProperties(msgs);
            if (msgs != null) msgs.dispatch();
        }
        else
        {
            boolean oldPropertiesESet = propertiesESet;
            propertiesESet = false;
            if (eNotificationRequired())
                eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.PROFILE__PROPERTIES, null, null, oldPropertiesESet));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetProperties()
    {
        return propertiesESet;
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
            case PomPackage.PROFILE__ACTIVATION:
                return basicUnsetActivation(msgs);
            case PomPackage.PROFILE__BUILD:
                return basicUnsetBuild(msgs);
            case PomPackage.PROFILE__MODULES:
                return basicUnsetModules(msgs);
            case PomPackage.PROFILE__REPOSITORIES:
                return basicUnsetRepositories(msgs);
            case PomPackage.PROFILE__PLUGIN_REPOSITORIES:
                return basicUnsetPluginRepositories(msgs);
            case PomPackage.PROFILE__DEPENDENCIES:
                return basicUnsetDependencies(msgs);
            case PomPackage.PROFILE__REPORTS:
                return basicUnsetReports(msgs);
            case PomPackage.PROFILE__REPORTING:
                return basicUnsetReporting(msgs);
            case PomPackage.PROFILE__DEPENDENCY_MANAGEMENT:
                return basicUnsetDependencyManagement(msgs);
            case PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
                return basicUnsetDistributionManagement(msgs);
            case PomPackage.PROFILE__PROPERTIES:
                return basicUnsetProperties(msgs);
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
            case PomPackage.PROFILE__ID:
                return getId();
            case PomPackage.PROFILE__ACTIVATION:
                return getActivation();
            case PomPackage.PROFILE__BUILD:
                return getBuild();
            case PomPackage.PROFILE__MODULES:
                return getModules();
            case PomPackage.PROFILE__REPOSITORIES:
                return getRepositories();
            case PomPackage.PROFILE__PLUGIN_REPOSITORIES:
                return getPluginRepositories();
            case PomPackage.PROFILE__DEPENDENCIES:
                return getDependencies();
            case PomPackage.PROFILE__REPORTS:
                return getReports();
            case PomPackage.PROFILE__REPORTING:
                return getReporting();
            case PomPackage.PROFILE__DEPENDENCY_MANAGEMENT:
                return getDependencyManagement();
            case PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
                return getDistributionManagement();
            case PomPackage.PROFILE__PROPERTIES:
                return getProperties();
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
            case PomPackage.PROFILE__ID:
                setId((String)newValue);
                return;
            case PomPackage.PROFILE__ACTIVATION:
                setActivation((Activation)newValue);
                return;
            case PomPackage.PROFILE__BUILD:
                setBuild((BuildBase)newValue);
                return;
            case PomPackage.PROFILE__MODULES:
                setModules((ModulesType1)newValue);
                return;
            case PomPackage.PROFILE__REPOSITORIES:
                setRepositories((RepositoriesType1)newValue);
                return;
            case PomPackage.PROFILE__PLUGIN_REPOSITORIES:
                setPluginRepositories((PluginRepositoriesType1)newValue);
                return;
            case PomPackage.PROFILE__DEPENDENCIES:
                setDependencies((DependenciesType2)newValue);
                return;
            case PomPackage.PROFILE__REPORTS:
                setReports((ReportsType2)newValue);
                return;
            case PomPackage.PROFILE__REPORTING:
                setReporting((Reporting)newValue);
                return;
            case PomPackage.PROFILE__DEPENDENCY_MANAGEMENT:
                setDependencyManagement((DependencyManagement)newValue);
                return;
            case PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
                setDistributionManagement((DistributionManagement)newValue);
                return;
            case PomPackage.PROFILE__PROPERTIES:
                setProperties((PropertiesType1)newValue);
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
            case PomPackage.PROFILE__ID:
                setId(ID_EDEFAULT);
                return;
            case PomPackage.PROFILE__ACTIVATION:
                unsetActivation();
                return;
            case PomPackage.PROFILE__BUILD:
                unsetBuild();
                return;
            case PomPackage.PROFILE__MODULES:
                unsetModules();
                return;
            case PomPackage.PROFILE__REPOSITORIES:
                unsetRepositories();
                return;
            case PomPackage.PROFILE__PLUGIN_REPOSITORIES:
                unsetPluginRepositories();
                return;
            case PomPackage.PROFILE__DEPENDENCIES:
                unsetDependencies();
                return;
            case PomPackage.PROFILE__REPORTS:
                unsetReports();
                return;
            case PomPackage.PROFILE__REPORTING:
                unsetReporting();
                return;
            case PomPackage.PROFILE__DEPENDENCY_MANAGEMENT:
                unsetDependencyManagement();
                return;
            case PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
                unsetDistributionManagement();
                return;
            case PomPackage.PROFILE__PROPERTIES:
                unsetProperties();
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
            case PomPackage.PROFILE__ID:
                return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
            case PomPackage.PROFILE__ACTIVATION:
                return isSetActivation();
            case PomPackage.PROFILE__BUILD:
                return isSetBuild();
            case PomPackage.PROFILE__MODULES:
                return isSetModules();
            case PomPackage.PROFILE__REPOSITORIES:
                return isSetRepositories();
            case PomPackage.PROFILE__PLUGIN_REPOSITORIES:
                return isSetPluginRepositories();
            case PomPackage.PROFILE__DEPENDENCIES:
                return isSetDependencies();
            case PomPackage.PROFILE__REPORTS:
                return isSetReports();
            case PomPackage.PROFILE__REPORTING:
                return isSetReporting();
            case PomPackage.PROFILE__DEPENDENCY_MANAGEMENT:
                return isSetDependencyManagement();
            case PomPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
                return isSetDistributionManagement();
            case PomPackage.PROFILE__PROPERTIES:
                return isSetProperties();
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
        result.append(" (id: ");
        result.append(id);
        result.append(')');
        return result.toString();
    }

} //ProfileImpl
