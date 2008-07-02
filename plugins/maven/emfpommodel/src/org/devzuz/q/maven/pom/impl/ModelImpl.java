/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.Build;
import org.devzuz.q.maven.pom.CiManagement;
import org.devzuz.q.maven.pom.ContributorsType;
import org.devzuz.q.maven.pom.DependenciesType;
import org.devzuz.q.maven.pom.DependencyManagement;
import org.devzuz.q.maven.pom.DevelopersType;
import org.devzuz.q.maven.pom.DistributionManagement;
import org.devzuz.q.maven.pom.IssueManagement;
import org.devzuz.q.maven.pom.LicensesType;
import org.devzuz.q.maven.pom.MailingListsType;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.ModulesType;
import org.devzuz.q.maven.pom.Organization;
import org.devzuz.q.maven.pom.Parent;
import org.devzuz.q.maven.pom.PluginRepositoriesType;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.Prerequisites;
import org.devzuz.q.maven.pom.ProfilesType;
import org.devzuz.q.maven.pom.Properties;
import org.devzuz.q.maven.pom.Reporting;
import org.devzuz.q.maven.pom.ReportsType1;
import org.devzuz.q.maven.pom.RepositoriesType;
import org.devzuz.q.maven.pom.Scm;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getModelVersion <em>Model Version</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getGroupId <em>Group Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getArtifactId <em>Artifact Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getPackaging <em>Packaging</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getPrerequisites <em>Prerequisites</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getIssueManagement <em>Issue Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getCiManagement <em>Ci Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getInceptionYear <em>Inception Year</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getMailingLists <em>Mailing Lists</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getDevelopers <em>Developers</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getContributors <em>Contributors</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getLicenses <em>Licenses</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getScm <em>Scm</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getBuild <em>Build</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getProfiles <em>Profiles</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getModules <em>Modules</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getPluginRepositories <em>Plugin Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getReports <em>Reports</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getReporting <em>Reporting</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getDependencyManagement <em>Dependency Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getDistributionManagement <em>Distribution Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.ModelImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends EObjectImpl implements Model
{
    /**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
    protected Parent parent;

    /**
	 * This is true if the Parent containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean parentESet;

    /**
	 * The default value of the '{@link #getModelVersion() <em>Model Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getModelVersion()
	 * @generated
	 * @ordered
	 */
    protected static final String MODEL_VERSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getModelVersion() <em>Model Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getModelVersion()
	 * @generated
	 * @ordered
	 */
    protected String modelVersion = MODEL_VERSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getGroupId() <em>Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroupId()
	 * @generated
	 * @ordered
	 */
    protected static final String GROUP_ID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getGroupId() <em>Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroupId()
	 * @generated
	 * @ordered
	 */
    protected String groupId = GROUP_ID_EDEFAULT;

    /**
	 * The default value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getArtifactId()
	 * @generated
	 * @ordered
	 */
    protected static final String ARTIFACT_ID_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getArtifactId()
	 * @generated
	 * @ordered
	 */
    protected String artifactId = ARTIFACT_ID_EDEFAULT;

    /**
	 * The default value of the '{@link #getPackaging() <em>Packaging</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPackaging()
	 * @generated
	 * @ordered
	 */
    protected static final String PACKAGING_EDEFAULT = "jar";

    /**
	 * The cached value of the '{@link #getPackaging() <em>Packaging</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPackaging()
	 * @generated
	 * @ordered
	 */
    protected String packaging = PACKAGING_EDEFAULT;

    /**
	 * This is true if the Packaging attribute has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean packagingESet;

    /**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected static final String VERSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected String version = VERSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
    protected String description = DESCRIPTION_EDEFAULT;

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
	 * The cached value of the '{@link #getPrerequisites() <em>Prerequisites</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPrerequisites()
	 * @generated
	 * @ordered
	 */
    protected Prerequisites prerequisites;

    /**
	 * This is true if the Prerequisites containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean prerequisitesESet;

    /**
	 * The cached value of the '{@link #getIssueManagement() <em>Issue Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIssueManagement()
	 * @generated
	 * @ordered
	 */
    protected IssueManagement issueManagement;

    /**
	 * This is true if the Issue Management containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean issueManagementESet;

    /**
	 * The cached value of the '{@link #getCiManagement() <em>Ci Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCiManagement()
	 * @generated
	 * @ordered
	 */
    protected CiManagement ciManagement;

    /**
	 * This is true if the Ci Management containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean ciManagementESet;

    /**
	 * The default value of the '{@link #getInceptionYear() <em>Inception Year</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getInceptionYear()
	 * @generated
	 * @ordered
	 */
    protected static final String INCEPTION_YEAR_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getInceptionYear() <em>Inception Year</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getInceptionYear()
	 * @generated
	 * @ordered
	 */
    protected String inceptionYear = INCEPTION_YEAR_EDEFAULT;

    /**
	 * The cached value of the '{@link #getMailingLists() <em>Mailing Lists</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMailingLists()
	 * @generated
	 * @ordered
	 */
    protected MailingListsType mailingLists;

    /**
	 * This is true if the Mailing Lists containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean mailingListsESet;

    /**
	 * The cached value of the '{@link #getDevelopers() <em>Developers</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDevelopers()
	 * @generated
	 * @ordered
	 */
    protected DevelopersType developers;

    /**
	 * This is true if the Developers containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean developersESet;

    /**
	 * The cached value of the '{@link #getContributors() <em>Contributors</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getContributors()
	 * @generated
	 * @ordered
	 */
    protected ContributorsType contributors;

    /**
	 * This is true if the Contributors containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean contributorsESet;

    /**
	 * The cached value of the '{@link #getLicenses() <em>Licenses</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLicenses()
	 * @generated
	 * @ordered
	 */
    protected LicensesType licenses;

    /**
	 * The cached value of the '{@link #getScm() <em>Scm</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getScm()
	 * @generated
	 * @ordered
	 */
    protected Scm scm;

    /**
	 * This is true if the Scm containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean scmESet;

    /**
	 * The cached value of the '{@link #getOrganization() <em>Organization</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOrganization()
	 * @generated
	 * @ordered
	 */
    protected Organization organization;

    /**
	 * This is true if the Organization containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean organizationESet;

    /**
	 * The cached value of the '{@link #getBuild() <em>Build</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
    protected Build build;

    /**
	 * This is true if the Build containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean buildESet;

    /**
	 * The cached value of the '{@link #getProfiles() <em>Profiles</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProfiles()
	 * @generated
	 * @ordered
	 */
    protected ProfilesType profiles;

    /**
	 * This is true if the Profiles containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean profilesESet;

    /**
	 * The cached value of the '{@link #getModules() <em>Modules</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getModules()
	 * @generated
	 * @ordered
	 */
    protected ModulesType modules;

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
    protected RepositoriesType repositories;

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
    protected PluginRepositoriesType pluginRepositories;

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
    protected DependenciesType dependencies;

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
    protected ReportsType1 reports;

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
    protected Properties properties;

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
    protected ModelImpl()
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
		return PomPackage.Literals.MODEL;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Parent getParent()
    {
		return parent;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetParent(Parent newParent, NotificationChain msgs)
    {
		Parent oldParent = parent;
		parent = newParent;
		boolean oldParentESet = parentESet;
		parentESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PARENT, oldParent, newParent, !oldParentESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setParent(Parent newParent)
    {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PARENT, null, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PARENT, null, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldParentESet = parentESet;
			parentESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PARENT, newParent, newParent, !oldParentESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetParent(NotificationChain msgs)
    {
		Parent oldParent = parent;
		parent = null;
		boolean oldParentESet = parentESet;
		parentESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PARENT, oldParent, null, oldParentESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetParent()
    {
		if (parent != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)parent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PARENT, null, msgs);
			msgs = basicUnsetParent(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldParentESet = parentESet;
			parentESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PARENT, null, null, oldParentESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetParent()
    {
		return parentESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getModelVersion()
    {
		return modelVersion;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setModelVersion(String newModelVersion)
    {
		String oldModelVersion = modelVersion;
		modelVersion = newModelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__MODEL_VERSION, oldModelVersion, modelVersion));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getGroupId()
    {
		return groupId;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setGroupId(String newGroupId)
    {
		String oldGroupId = groupId;
		groupId = newGroupId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__GROUP_ID, oldGroupId, groupId));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getArtifactId()
    {
		return artifactId;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setArtifactId(String newArtifactId)
    {
		String oldArtifactId = artifactId;
		artifactId = newArtifactId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__ARTIFACT_ID, oldArtifactId, artifactId));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getPackaging()
    {
		return packaging;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPackaging(String newPackaging)
    {
		String oldPackaging = packaging;
		packaging = newPackaging;
		boolean oldPackagingESet = packagingESet;
		packagingESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PACKAGING, oldPackaging, packaging, !oldPackagingESet));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetPackaging()
    {
		String oldPackaging = packaging;
		boolean oldPackagingESet = packagingESet;
		packaging = PACKAGING_EDEFAULT;
		packagingESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PACKAGING, oldPackaging, PACKAGING_EDEFAULT, oldPackagingESet));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetPackaging()
    {
		return packagingESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getName()
    {
		return name;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setName(String newName)
    {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getVersion()
    {
		return version;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setVersion(String newVersion)
    {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__VERSION, oldVersion, version));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDescription()
    {
		return description;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDescription(String newDescription)
    {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DESCRIPTION, oldDescription, description));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getUrl()
    {
		return url;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setUrl(String newUrl)
    {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__URL, oldUrl, url));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Prerequisites getPrerequisites()
    {
		return prerequisites;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPrerequisites(Prerequisites newPrerequisites, NotificationChain msgs)
    {
		Prerequisites oldPrerequisites = prerequisites;
		prerequisites = newPrerequisites;
		boolean oldPrerequisitesESet = prerequisitesESet;
		prerequisitesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PREREQUISITES, oldPrerequisites, newPrerequisites, !oldPrerequisitesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPrerequisites(Prerequisites newPrerequisites)
    {
		if (newPrerequisites != prerequisites) {
			NotificationChain msgs = null;
			if (prerequisites != null)
				msgs = ((InternalEObject)prerequisites).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PREREQUISITES, null, msgs);
			if (newPrerequisites != null)
				msgs = ((InternalEObject)newPrerequisites).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PREREQUISITES, null, msgs);
			msgs = basicSetPrerequisites(newPrerequisites, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPrerequisitesESet = prerequisitesESet;
			prerequisitesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PREREQUISITES, newPrerequisites, newPrerequisites, !oldPrerequisitesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetPrerequisites(NotificationChain msgs)
    {
		Prerequisites oldPrerequisites = prerequisites;
		prerequisites = null;
		boolean oldPrerequisitesESet = prerequisitesESet;
		prerequisitesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PREREQUISITES, oldPrerequisites, null, oldPrerequisitesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetPrerequisites()
    {
		if (prerequisites != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)prerequisites).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PREREQUISITES, null, msgs);
			msgs = basicUnsetPrerequisites(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPrerequisitesESet = prerequisitesESet;
			prerequisitesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PREREQUISITES, null, null, oldPrerequisitesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetPrerequisites()
    {
		return prerequisitesESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public IssueManagement getIssueManagement()
    {
		return issueManagement;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetIssueManagement(IssueManagement newIssueManagement, NotificationChain msgs)
    {
		IssueManagement oldIssueManagement = issueManagement;
		issueManagement = newIssueManagement;
		boolean oldIssueManagementESet = issueManagementESet;
		issueManagementESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__ISSUE_MANAGEMENT, oldIssueManagement, newIssueManagement, !oldIssueManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIssueManagement(IssueManagement newIssueManagement)
    {
		if (newIssueManagement != issueManagement) {
			NotificationChain msgs = null;
			if (issueManagement != null)
				msgs = ((InternalEObject)issueManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ISSUE_MANAGEMENT, null, msgs);
			if (newIssueManagement != null)
				msgs = ((InternalEObject)newIssueManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ISSUE_MANAGEMENT, null, msgs);
			msgs = basicSetIssueManagement(newIssueManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldIssueManagementESet = issueManagementESet;
			issueManagementESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__ISSUE_MANAGEMENT, newIssueManagement, newIssueManagement, !oldIssueManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetIssueManagement(NotificationChain msgs)
    {
		IssueManagement oldIssueManagement = issueManagement;
		issueManagement = null;
		boolean oldIssueManagementESet = issueManagementESet;
		issueManagementESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__ISSUE_MANAGEMENT, oldIssueManagement, null, oldIssueManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetIssueManagement()
    {
		if (issueManagement != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)issueManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ISSUE_MANAGEMENT, null, msgs);
			msgs = basicUnsetIssueManagement(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldIssueManagementESet = issueManagementESet;
			issueManagementESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__ISSUE_MANAGEMENT, null, null, oldIssueManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetIssueManagement()
    {
		return issueManagementESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public CiManagement getCiManagement()
    {
		return ciManagement;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetCiManagement(CiManagement newCiManagement, NotificationChain msgs)
    {
		CiManagement oldCiManagement = ciManagement;
		ciManagement = newCiManagement;
		boolean oldCiManagementESet = ciManagementESet;
		ciManagementESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__CI_MANAGEMENT, oldCiManagement, newCiManagement, !oldCiManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setCiManagement(CiManagement newCiManagement)
    {
		if (newCiManagement != ciManagement) {
			NotificationChain msgs = null;
			if (ciManagement != null)
				msgs = ((InternalEObject)ciManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CI_MANAGEMENT, null, msgs);
			if (newCiManagement != null)
				msgs = ((InternalEObject)newCiManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CI_MANAGEMENT, null, msgs);
			msgs = basicSetCiManagement(newCiManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldCiManagementESet = ciManagementESet;
			ciManagementESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__CI_MANAGEMENT, newCiManagement, newCiManagement, !oldCiManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetCiManagement(NotificationChain msgs)
    {
		CiManagement oldCiManagement = ciManagement;
		ciManagement = null;
		boolean oldCiManagementESet = ciManagementESet;
		ciManagementESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__CI_MANAGEMENT, oldCiManagement, null, oldCiManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetCiManagement()
    {
		if (ciManagement != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)ciManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CI_MANAGEMENT, null, msgs);
			msgs = basicUnsetCiManagement(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldCiManagementESet = ciManagementESet;
			ciManagementESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__CI_MANAGEMENT, null, null, oldCiManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetCiManagement()
    {
		return ciManagementESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getInceptionYear()
    {
		return inceptionYear;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setInceptionYear(String newInceptionYear)
    {
		String oldInceptionYear = inceptionYear;
		inceptionYear = newInceptionYear;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__INCEPTION_YEAR, oldInceptionYear, inceptionYear));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public MailingListsType getMailingLists()
    {
		return mailingLists;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetMailingLists(MailingListsType newMailingLists, NotificationChain msgs)
    {
		MailingListsType oldMailingLists = mailingLists;
		mailingLists = newMailingLists;
		boolean oldMailingListsESet = mailingListsESet;
		mailingListsESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__MAILING_LISTS, oldMailingLists, newMailingLists, !oldMailingListsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setMailingLists(MailingListsType newMailingLists)
    {
		if (newMailingLists != mailingLists) {
			NotificationChain msgs = null;
			if (mailingLists != null)
				msgs = ((InternalEObject)mailingLists).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MAILING_LISTS, null, msgs);
			if (newMailingLists != null)
				msgs = ((InternalEObject)newMailingLists).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MAILING_LISTS, null, msgs);
			msgs = basicSetMailingLists(newMailingLists, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldMailingListsESet = mailingListsESet;
			mailingListsESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__MAILING_LISTS, newMailingLists, newMailingLists, !oldMailingListsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetMailingLists(NotificationChain msgs)
    {
		MailingListsType oldMailingLists = mailingLists;
		mailingLists = null;
		boolean oldMailingListsESet = mailingListsESet;
		mailingListsESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__MAILING_LISTS, oldMailingLists, null, oldMailingListsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetMailingLists()
    {
		if (mailingLists != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)mailingLists).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MAILING_LISTS, null, msgs);
			msgs = basicUnsetMailingLists(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldMailingListsESet = mailingListsESet;
			mailingListsESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__MAILING_LISTS, null, null, oldMailingListsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetMailingLists()
    {
		return mailingListsESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DevelopersType getDevelopers()
    {
		return developers;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDevelopers(DevelopersType newDevelopers, NotificationChain msgs)
    {
		DevelopersType oldDevelopers = developers;
		developers = newDevelopers;
		boolean oldDevelopersESet = developersESet;
		developersESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEVELOPERS, oldDevelopers, newDevelopers, !oldDevelopersESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDevelopers(DevelopersType newDevelopers)
    {
		if (newDevelopers != developers) {
			NotificationChain msgs = null;
			if (developers != null)
				msgs = ((InternalEObject)developers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEVELOPERS, null, msgs);
			if (newDevelopers != null)
				msgs = ((InternalEObject)newDevelopers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEVELOPERS, null, msgs);
			msgs = basicSetDevelopers(newDevelopers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDevelopersESet = developersESet;
			developersESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEVELOPERS, newDevelopers, newDevelopers, !oldDevelopersESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetDevelopers(NotificationChain msgs)
    {
		DevelopersType oldDevelopers = developers;
		developers = null;
		boolean oldDevelopersESet = developersESet;
		developersESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEVELOPERS, oldDevelopers, null, oldDevelopersESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetDevelopers()
    {
		if (developers != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)developers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEVELOPERS, null, msgs);
			msgs = basicUnsetDevelopers(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDevelopersESet = developersESet;
			developersESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEVELOPERS, null, null, oldDevelopersESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetDevelopers()
    {
		return developersESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ContributorsType getContributors()
    {
		return contributors;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetContributors(ContributorsType newContributors, NotificationChain msgs)
    {
		ContributorsType oldContributors = contributors;
		contributors = newContributors;
		boolean oldContributorsESet = contributorsESet;
		contributorsESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__CONTRIBUTORS, oldContributors, newContributors, !oldContributorsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setContributors(ContributorsType newContributors)
    {
		if (newContributors != contributors) {
			NotificationChain msgs = null;
			if (contributors != null)
				msgs = ((InternalEObject)contributors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CONTRIBUTORS, null, msgs);
			if (newContributors != null)
				msgs = ((InternalEObject)newContributors).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CONTRIBUTORS, null, msgs);
			msgs = basicSetContributors(newContributors, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldContributorsESet = contributorsESet;
			contributorsESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__CONTRIBUTORS, newContributors, newContributors, !oldContributorsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetContributors(NotificationChain msgs)
    {
		ContributorsType oldContributors = contributors;
		contributors = null;
		boolean oldContributorsESet = contributorsESet;
		contributorsESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__CONTRIBUTORS, oldContributors, null, oldContributorsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetContributors()
    {
		if (contributors != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)contributors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__CONTRIBUTORS, null, msgs);
			msgs = basicUnsetContributors(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldContributorsESet = contributorsESet;
			contributorsESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__CONTRIBUTORS, null, null, oldContributorsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetContributors()
    {
		return contributorsESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public LicensesType getLicenses()
    {
		return licenses;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetLicenses(LicensesType newLicenses, NotificationChain msgs)
    {
		LicensesType oldLicenses = licenses;
		licenses = newLicenses;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__LICENSES, oldLicenses, newLicenses);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLicenses(LicensesType newLicenses)
    {
		if (newLicenses != licenses) {
			NotificationChain msgs = null;
			if (licenses != null)
				msgs = ((InternalEObject)licenses).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__LICENSES, null, msgs);
			if (newLicenses != null)
				msgs = ((InternalEObject)newLicenses).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__LICENSES, null, msgs);
			msgs = basicSetLicenses(newLicenses, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__LICENSES, newLicenses, newLicenses));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Scm getScm()
    {
		return scm;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetScm(Scm newScm, NotificationChain msgs)
    {
		Scm oldScm = scm;
		scm = newScm;
		boolean oldScmESet = scmESet;
		scmESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__SCM, oldScm, newScm, !oldScmESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setScm(Scm newScm)
    {
		if (newScm != scm) {
			NotificationChain msgs = null;
			if (scm != null)
				msgs = ((InternalEObject)scm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__SCM, null, msgs);
			if (newScm != null)
				msgs = ((InternalEObject)newScm).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__SCM, null, msgs);
			msgs = basicSetScm(newScm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldScmESet = scmESet;
			scmESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__SCM, newScm, newScm, !oldScmESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetScm(NotificationChain msgs)
    {
		Scm oldScm = scm;
		scm = null;
		boolean oldScmESet = scmESet;
		scmESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__SCM, oldScm, null, oldScmESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetScm()
    {
		if (scm != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)scm).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__SCM, null, msgs);
			msgs = basicUnsetScm(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldScmESet = scmESet;
			scmESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__SCM, null, null, oldScmESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetScm()
    {
		return scmESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Organization getOrganization()
    {
		return organization;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetOrganization(Organization newOrganization, NotificationChain msgs)
    {
		Organization oldOrganization = organization;
		organization = newOrganization;
		boolean oldOrganizationESet = organizationESet;
		organizationESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__ORGANIZATION, oldOrganization, newOrganization, !oldOrganizationESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOrganization(Organization newOrganization)
    {
		if (newOrganization != organization) {
			NotificationChain msgs = null;
			if (organization != null)
				msgs = ((InternalEObject)organization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ORGANIZATION, null, msgs);
			if (newOrganization != null)
				msgs = ((InternalEObject)newOrganization).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ORGANIZATION, null, msgs);
			msgs = basicSetOrganization(newOrganization, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldOrganizationESet = organizationESet;
			organizationESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__ORGANIZATION, newOrganization, newOrganization, !oldOrganizationESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetOrganization(NotificationChain msgs)
    {
		Organization oldOrganization = organization;
		organization = null;
		boolean oldOrganizationESet = organizationESet;
		organizationESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__ORGANIZATION, oldOrganization, null, oldOrganizationESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetOrganization()
    {
		if (organization != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)organization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__ORGANIZATION, null, msgs);
			msgs = basicUnsetOrganization(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldOrganizationESet = organizationESet;
			organizationESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__ORGANIZATION, null, null, oldOrganizationESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetOrganization()
    {
		return organizationESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Build getBuild()
    {
		return build;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetBuild(Build newBuild, NotificationChain msgs)
    {
		Build oldBuild = build;
		build = newBuild;
		boolean oldBuildESet = buildESet;
		buildESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__BUILD, oldBuild, newBuild, !oldBuildESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setBuild(Build newBuild)
    {
		if (newBuild != build) {
			NotificationChain msgs = null;
			if (build != null)
				msgs = ((InternalEObject)build).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__BUILD, null, msgs);
			if (newBuild != null)
				msgs = ((InternalEObject)newBuild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__BUILD, null, msgs);
			msgs = basicSetBuild(newBuild, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldBuildESet = buildESet;
			buildESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__BUILD, newBuild, newBuild, !oldBuildESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetBuild(NotificationChain msgs)
    {
		Build oldBuild = build;
		build = null;
		boolean oldBuildESet = buildESet;
		buildESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__BUILD, oldBuild, null, oldBuildESet);
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
		if (build != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)build).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__BUILD, null, msgs);
			msgs = basicUnsetBuild(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldBuildESet = buildESet;
			buildESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__BUILD, null, null, oldBuildESet));
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
    public ProfilesType getProfiles()
    {
		return profiles;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetProfiles(ProfilesType newProfiles, NotificationChain msgs)
    {
		ProfilesType oldProfiles = profiles;
		profiles = newProfiles;
		boolean oldProfilesESet = profilesESet;
		profilesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PROFILES, oldProfiles, newProfiles, !oldProfilesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setProfiles(ProfilesType newProfiles)
    {
		if (newProfiles != profiles) {
			NotificationChain msgs = null;
			if (profiles != null)
				msgs = ((InternalEObject)profiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROFILES, null, msgs);
			if (newProfiles != null)
				msgs = ((InternalEObject)newProfiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROFILES, null, msgs);
			msgs = basicSetProfiles(newProfiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldProfilesESet = profilesESet;
			profilesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PROFILES, newProfiles, newProfiles, !oldProfilesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetProfiles(NotificationChain msgs)
    {
		ProfilesType oldProfiles = profiles;
		profiles = null;
		boolean oldProfilesESet = profilesESet;
		profilesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PROFILES, oldProfiles, null, oldProfilesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetProfiles()
    {
		if (profiles != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)profiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROFILES, null, msgs);
			msgs = basicUnsetProfiles(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldProfilesESet = profilesESet;
			profilesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PROFILES, null, null, oldProfilesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetProfiles()
    {
		return profilesESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ModulesType getModules()
    {
		return modules;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetModules(ModulesType newModules, NotificationChain msgs)
    {
		ModulesType oldModules = modules;
		modules = newModules;
		boolean oldModulesESet = modulesESet;
		modulesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__MODULES, oldModules, newModules, !oldModulesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setModules(ModulesType newModules)
    {
		if (newModules != modules) {
			NotificationChain msgs = null;
			if (modules != null)
				msgs = ((InternalEObject)modules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MODULES, null, msgs);
			if (newModules != null)
				msgs = ((InternalEObject)newModules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MODULES, null, msgs);
			msgs = basicSetModules(newModules, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldModulesESet = modulesESet;
			modulesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__MODULES, newModules, newModules, !oldModulesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetModules(NotificationChain msgs)
    {
		ModulesType oldModules = modules;
		modules = null;
		boolean oldModulesESet = modulesESet;
		modulesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__MODULES, oldModules, null, oldModulesESet);
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
		if (modules != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)modules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__MODULES, null, msgs);
			msgs = basicUnsetModules(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldModulesESet = modulesESet;
			modulesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__MODULES, null, null, oldModulesESet));
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
    public RepositoriesType getRepositories()
    {
		return repositories;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetRepositories(RepositoriesType newRepositories, NotificationChain msgs)
    {
		RepositoriesType oldRepositories = repositories;
		repositories = newRepositories;
		boolean oldRepositoriesESet = repositoriesESet;
		repositoriesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPOSITORIES, oldRepositories, newRepositories, !oldRepositoriesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setRepositories(RepositoriesType newRepositories)
    {
		if (newRepositories != repositories) {
			NotificationChain msgs = null;
			if (repositories != null)
				msgs = ((InternalEObject)repositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPOSITORIES, null, msgs);
			if (newRepositories != null)
				msgs = ((InternalEObject)newRepositories).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPOSITORIES, null, msgs);
			msgs = basicSetRepositories(newRepositories, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldRepositoriesESet = repositoriesESet;
			repositoriesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPOSITORIES, newRepositories, newRepositories, !oldRepositoriesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetRepositories(NotificationChain msgs)
    {
		RepositoriesType oldRepositories = repositories;
		repositories = null;
		boolean oldRepositoriesESet = repositoriesESet;
		repositoriesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPOSITORIES, oldRepositories, null, oldRepositoriesESet);
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
		if (repositories != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)repositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPOSITORIES, null, msgs);
			msgs = basicUnsetRepositories(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldRepositoriesESet = repositoriesESet;
			repositoriesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPOSITORIES, null, null, oldRepositoriesESet));
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
    public PluginRepositoriesType getPluginRepositories()
    {
		return pluginRepositories;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPluginRepositories(PluginRepositoriesType newPluginRepositories, NotificationChain msgs)
    {
		PluginRepositoriesType oldPluginRepositories = pluginRepositories;
		pluginRepositories = newPluginRepositories;
		boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
		pluginRepositoriesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PLUGIN_REPOSITORIES, oldPluginRepositories, newPluginRepositories, !oldPluginRepositoriesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPluginRepositories(PluginRepositoriesType newPluginRepositories)
    {
		if (newPluginRepositories != pluginRepositories) {
			NotificationChain msgs = null;
			if (pluginRepositories != null)
				msgs = ((InternalEObject)pluginRepositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PLUGIN_REPOSITORIES, null, msgs);
			if (newPluginRepositories != null)
				msgs = ((InternalEObject)newPluginRepositories).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PLUGIN_REPOSITORIES, null, msgs);
			msgs = basicSetPluginRepositories(newPluginRepositories, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
			pluginRepositoriesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PLUGIN_REPOSITORIES, newPluginRepositories, newPluginRepositories, !oldPluginRepositoriesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetPluginRepositories(NotificationChain msgs)
    {
		PluginRepositoriesType oldPluginRepositories = pluginRepositories;
		pluginRepositories = null;
		boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
		pluginRepositoriesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PLUGIN_REPOSITORIES, oldPluginRepositories, null, oldPluginRepositoriesESet);
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
		if (pluginRepositories != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)pluginRepositories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PLUGIN_REPOSITORIES, null, msgs);
			msgs = basicUnsetPluginRepositories(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginRepositoriesESet = pluginRepositoriesESet;
			pluginRepositoriesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PLUGIN_REPOSITORIES, null, null, oldPluginRepositoriesESet));
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
    public DependenciesType getDependencies()
    {
		return dependencies;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetDependencies(DependenciesType newDependencies, NotificationChain msgs)
    {
		DependenciesType oldDependencies = dependencies;
		dependencies = newDependencies;
		boolean oldDependenciesESet = dependenciesESet;
		dependenciesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEPENDENCIES, oldDependencies, newDependencies, !oldDependenciesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDependencies(DependenciesType newDependencies)
    {
		if (newDependencies != dependencies) {
			NotificationChain msgs = null;
			if (dependencies != null)
				msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCIES, null, msgs);
			if (newDependencies != null)
				msgs = ((InternalEObject)newDependencies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCIES, null, msgs);
			msgs = basicSetDependencies(newDependencies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependenciesESet = dependenciesESet;
			dependenciesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEPENDENCIES, newDependencies, newDependencies, !oldDependenciesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetDependencies(NotificationChain msgs)
    {
		DependenciesType oldDependencies = dependencies;
		dependencies = null;
		boolean oldDependenciesESet = dependenciesESet;
		dependenciesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEPENDENCIES, oldDependencies, null, oldDependenciesESet);
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
			msgs = ((InternalEObject)dependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCIES, null, msgs);
			msgs = basicUnsetDependencies(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependenciesESet = dependenciesESet;
			dependenciesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEPENDENCIES, null, null, oldDependenciesESet));
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
    public ReportsType1 getReports()
    {
		return reports;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetReports(ReportsType1 newReports, NotificationChain msgs)
    {
		ReportsType1 oldReports = reports;
		reports = newReports;
		boolean oldReportsESet = reportsESet;
		reportsESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPORTS, oldReports, newReports, !oldReportsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setReports(ReportsType1 newReports)
    {
		if (newReports != reports) {
			NotificationChain msgs = null;
			if (reports != null)
				msgs = ((InternalEObject)reports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTS, null, msgs);
			if (newReports != null)
				msgs = ((InternalEObject)newReports).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTS, null, msgs);
			msgs = basicSetReports(newReports, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldReportsESet = reportsESet;
			reportsESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPORTS, newReports, newReports, !oldReportsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetReports(NotificationChain msgs)
    {
		ReportsType1 oldReports = reports;
		reports = null;
		boolean oldReportsESet = reportsESet;
		reportsESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPORTS, oldReports, null, oldReportsESet);
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
		if (reports != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)reports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTS, null, msgs);
			msgs = basicUnsetReports(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldReportsESet = reportsESet;
			reportsESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPORTS, null, null, oldReportsESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPORTING, oldReporting, newReporting, !oldReportingESet);
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
		if (newReporting != reporting) {
			NotificationChain msgs = null;
			if (reporting != null)
				msgs = ((InternalEObject)reporting).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTING, null, msgs);
			if (newReporting != null)
				msgs = ((InternalEObject)newReporting).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTING, null, msgs);
			msgs = basicSetReporting(newReporting, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldReportingESet = reportingESet;
			reportingESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__REPORTING, newReporting, newReporting, !oldReportingESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPORTING, oldReporting, null, oldReportingESet);
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
		if (reporting != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)reporting).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__REPORTING, null, msgs);
			msgs = basicUnsetReporting(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldReportingESet = reportingESet;
			reportingESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__REPORTING, null, null, oldReportingESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEPENDENCY_MANAGEMENT, oldDependencyManagement, newDependencyManagement, !oldDependencyManagementESet);
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
		if (newDependencyManagement != dependencyManagement) {
			NotificationChain msgs = null;
			if (dependencyManagement != null)
				msgs = ((InternalEObject)dependencyManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCY_MANAGEMENT, null, msgs);
			if (newDependencyManagement != null)
				msgs = ((InternalEObject)newDependencyManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCY_MANAGEMENT, null, msgs);
			msgs = basicSetDependencyManagement(newDependencyManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependencyManagementESet = dependencyManagementESet;
			dependencyManagementESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DEPENDENCY_MANAGEMENT, newDependencyManagement, newDependencyManagement, !oldDependencyManagementESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEPENDENCY_MANAGEMENT, oldDependencyManagement, null, oldDependencyManagementESet);
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
		if (dependencyManagement != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)dependencyManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DEPENDENCY_MANAGEMENT, null, msgs);
			msgs = basicUnsetDependencyManagement(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDependencyManagementESet = dependencyManagementESet;
			dependencyManagementESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DEPENDENCY_MANAGEMENT, null, null, oldDependencyManagementESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, oldDistributionManagement, newDistributionManagement, !oldDistributionManagementESet);
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
		if (newDistributionManagement != distributionManagement) {
			NotificationChain msgs = null;
			if (distributionManagement != null)
				msgs = ((InternalEObject)distributionManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, null, msgs);
			if (newDistributionManagement != null)
				msgs = ((InternalEObject)newDistributionManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, null, msgs);
			msgs = basicSetDistributionManagement(newDistributionManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDistributionManagementESet = distributionManagementESet;
			distributionManagementESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, newDistributionManagement, newDistributionManagement, !oldDistributionManagementESet));
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
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, oldDistributionManagement, null, oldDistributionManagementESet);
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
		if (distributionManagement != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)distributionManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, null, msgs);
			msgs = basicUnsetDistributionManagement(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDistributionManagementESet = distributionManagementESet;
			distributionManagementESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__DISTRIBUTION_MANAGEMENT, null, null, oldDistributionManagementESet));
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
    public Properties getProperties()
    {
		return properties;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(Properties newProperties, NotificationChain msgs) {
		Properties oldProperties = properties;
		properties = newProperties;
		boolean oldPropertiesESet = propertiesESet;
		propertiesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PROPERTIES, oldProperties, newProperties, !oldPropertiesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(Properties newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPropertiesESet = propertiesESet;
			propertiesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.MODEL__PROPERTIES, newProperties, newProperties, !oldPropertiesESet));
		}
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetProperties(NotificationChain msgs)
    {
		Properties oldProperties = properties;
		properties = null;
		boolean oldPropertiesESet = propertiesESet;
		propertiesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PROPERTIES, oldProperties, null, oldPropertiesESet);
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
		if (properties != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.MODEL__PROPERTIES, null, msgs);
			msgs = basicUnsetProperties(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPropertiesESet = propertiesESet;
			propertiesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.MODEL__PROPERTIES, null, null, oldPropertiesESet));
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
		switch (featureID) {
			case PomPackage.MODEL__PARENT:
				return basicUnsetParent(msgs);
			case PomPackage.MODEL__PREREQUISITES:
				return basicUnsetPrerequisites(msgs);
			case PomPackage.MODEL__ISSUE_MANAGEMENT:
				return basicUnsetIssueManagement(msgs);
			case PomPackage.MODEL__CI_MANAGEMENT:
				return basicUnsetCiManagement(msgs);
			case PomPackage.MODEL__MAILING_LISTS:
				return basicUnsetMailingLists(msgs);
			case PomPackage.MODEL__DEVELOPERS:
				return basicUnsetDevelopers(msgs);
			case PomPackage.MODEL__CONTRIBUTORS:
				return basicUnsetContributors(msgs);
			case PomPackage.MODEL__LICENSES:
				return basicSetLicenses(null, msgs);
			case PomPackage.MODEL__SCM:
				return basicUnsetScm(msgs);
			case PomPackage.MODEL__ORGANIZATION:
				return basicUnsetOrganization(msgs);
			case PomPackage.MODEL__BUILD:
				return basicUnsetBuild(msgs);
			case PomPackage.MODEL__PROFILES:
				return basicUnsetProfiles(msgs);
			case PomPackage.MODEL__MODULES:
				return basicUnsetModules(msgs);
			case PomPackage.MODEL__REPOSITORIES:
				return basicUnsetRepositories(msgs);
			case PomPackage.MODEL__PLUGIN_REPOSITORIES:
				return basicUnsetPluginRepositories(msgs);
			case PomPackage.MODEL__DEPENDENCIES:
				return basicUnsetDependencies(msgs);
			case PomPackage.MODEL__REPORTS:
				return basicUnsetReports(msgs);
			case PomPackage.MODEL__REPORTING:
				return basicUnsetReporting(msgs);
			case PomPackage.MODEL__DEPENDENCY_MANAGEMENT:
				return basicUnsetDependencyManagement(msgs);
			case PomPackage.MODEL__DISTRIBUTION_MANAGEMENT:
				return basicUnsetDistributionManagement(msgs);
			case PomPackage.MODEL__PROPERTIES:
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
		switch (featureID) {
			case PomPackage.MODEL__PARENT:
				return getParent();
			case PomPackage.MODEL__MODEL_VERSION:
				return getModelVersion();
			case PomPackage.MODEL__GROUP_ID:
				return getGroupId();
			case PomPackage.MODEL__ARTIFACT_ID:
				return getArtifactId();
			case PomPackage.MODEL__PACKAGING:
				return getPackaging();
			case PomPackage.MODEL__NAME:
				return getName();
			case PomPackage.MODEL__VERSION:
				return getVersion();
			case PomPackage.MODEL__DESCRIPTION:
				return getDescription();
			case PomPackage.MODEL__URL:
				return getUrl();
			case PomPackage.MODEL__PREREQUISITES:
				return getPrerequisites();
			case PomPackage.MODEL__ISSUE_MANAGEMENT:
				return getIssueManagement();
			case PomPackage.MODEL__CI_MANAGEMENT:
				return getCiManagement();
			case PomPackage.MODEL__INCEPTION_YEAR:
				return getInceptionYear();
			case PomPackage.MODEL__MAILING_LISTS:
				return getMailingLists();
			case PomPackage.MODEL__DEVELOPERS:
				return getDevelopers();
			case PomPackage.MODEL__CONTRIBUTORS:
				return getContributors();
			case PomPackage.MODEL__LICENSES:
				return getLicenses();
			case PomPackage.MODEL__SCM:
				return getScm();
			case PomPackage.MODEL__ORGANIZATION:
				return getOrganization();
			case PomPackage.MODEL__BUILD:
				return getBuild();
			case PomPackage.MODEL__PROFILES:
				return getProfiles();
			case PomPackage.MODEL__MODULES:
				return getModules();
			case PomPackage.MODEL__REPOSITORIES:
				return getRepositories();
			case PomPackage.MODEL__PLUGIN_REPOSITORIES:
				return getPluginRepositories();
			case PomPackage.MODEL__DEPENDENCIES:
				return getDependencies();
			case PomPackage.MODEL__REPORTS:
				return getReports();
			case PomPackage.MODEL__REPORTING:
				return getReporting();
			case PomPackage.MODEL__DEPENDENCY_MANAGEMENT:
				return getDependencyManagement();
			case PomPackage.MODEL__DISTRIBUTION_MANAGEMENT:
				return getDistributionManagement();
			case PomPackage.MODEL__PROPERTIES:
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
		switch (featureID) {
			case PomPackage.MODEL__PARENT:
				setParent((Parent)newValue);
				return;
			case PomPackage.MODEL__MODEL_VERSION:
				setModelVersion((String)newValue);
				return;
			case PomPackage.MODEL__GROUP_ID:
				setGroupId((String)newValue);
				return;
			case PomPackage.MODEL__ARTIFACT_ID:
				setArtifactId((String)newValue);
				return;
			case PomPackage.MODEL__PACKAGING:
				setPackaging((String)newValue);
				return;
			case PomPackage.MODEL__NAME:
				setName((String)newValue);
				return;
			case PomPackage.MODEL__VERSION:
				setVersion((String)newValue);
				return;
			case PomPackage.MODEL__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case PomPackage.MODEL__URL:
				setUrl((String)newValue);
				return;
			case PomPackage.MODEL__PREREQUISITES:
				setPrerequisites((Prerequisites)newValue);
				return;
			case PomPackage.MODEL__ISSUE_MANAGEMENT:
				setIssueManagement((IssueManagement)newValue);
				return;
			case PomPackage.MODEL__CI_MANAGEMENT:
				setCiManagement((CiManagement)newValue);
				return;
			case PomPackage.MODEL__INCEPTION_YEAR:
				setInceptionYear((String)newValue);
				return;
			case PomPackage.MODEL__MAILING_LISTS:
				setMailingLists((MailingListsType)newValue);
				return;
			case PomPackage.MODEL__DEVELOPERS:
				setDevelopers((DevelopersType)newValue);
				return;
			case PomPackage.MODEL__CONTRIBUTORS:
				setContributors((ContributorsType)newValue);
				return;
			case PomPackage.MODEL__LICENSES:
				setLicenses((LicensesType)newValue);
				return;
			case PomPackage.MODEL__SCM:
				setScm((Scm)newValue);
				return;
			case PomPackage.MODEL__ORGANIZATION:
				setOrganization((Organization)newValue);
				return;
			case PomPackage.MODEL__BUILD:
				setBuild((Build)newValue);
				return;
			case PomPackage.MODEL__PROFILES:
				setProfiles((ProfilesType)newValue);
				return;
			case PomPackage.MODEL__MODULES:
				setModules((ModulesType)newValue);
				return;
			case PomPackage.MODEL__REPOSITORIES:
				setRepositories((RepositoriesType)newValue);
				return;
			case PomPackage.MODEL__PLUGIN_REPOSITORIES:
				setPluginRepositories((PluginRepositoriesType)newValue);
				return;
			case PomPackage.MODEL__DEPENDENCIES:
				setDependencies((DependenciesType)newValue);
				return;
			case PomPackage.MODEL__REPORTS:
				setReports((ReportsType1)newValue);
				return;
			case PomPackage.MODEL__REPORTING:
				setReporting((Reporting)newValue);
				return;
			case PomPackage.MODEL__DEPENDENCY_MANAGEMENT:
				setDependencyManagement((DependencyManagement)newValue);
				return;
			case PomPackage.MODEL__DISTRIBUTION_MANAGEMENT:
				setDistributionManagement((DistributionManagement)newValue);
				return;
			case PomPackage.MODEL__PROPERTIES:
				setProperties((Properties)newValue);
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
			case PomPackage.MODEL__PARENT:
				unsetParent();
				return;
			case PomPackage.MODEL__MODEL_VERSION:
				setModelVersion(MODEL_VERSION_EDEFAULT);
				return;
			case PomPackage.MODEL__GROUP_ID:
				setGroupId(GROUP_ID_EDEFAULT);
				return;
			case PomPackage.MODEL__ARTIFACT_ID:
				setArtifactId(ARTIFACT_ID_EDEFAULT);
				return;
			case PomPackage.MODEL__PACKAGING:
				unsetPackaging();
				return;
			case PomPackage.MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PomPackage.MODEL__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case PomPackage.MODEL__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case PomPackage.MODEL__URL:
				setUrl(URL_EDEFAULT);
				return;
			case PomPackage.MODEL__PREREQUISITES:
				unsetPrerequisites();
				return;
			case PomPackage.MODEL__ISSUE_MANAGEMENT:
				unsetIssueManagement();
				return;
			case PomPackage.MODEL__CI_MANAGEMENT:
				unsetCiManagement();
				return;
			case PomPackage.MODEL__INCEPTION_YEAR:
				setInceptionYear(INCEPTION_YEAR_EDEFAULT);
				return;
			case PomPackage.MODEL__MAILING_LISTS:
				unsetMailingLists();
				return;
			case PomPackage.MODEL__DEVELOPERS:
				unsetDevelopers();
				return;
			case PomPackage.MODEL__CONTRIBUTORS:
				unsetContributors();
				return;
			case PomPackage.MODEL__LICENSES:
				setLicenses((LicensesType)null);
				return;
			case PomPackage.MODEL__SCM:
				unsetScm();
				return;
			case PomPackage.MODEL__ORGANIZATION:
				unsetOrganization();
				return;
			case PomPackage.MODEL__BUILD:
				unsetBuild();
				return;
			case PomPackage.MODEL__PROFILES:
				unsetProfiles();
				return;
			case PomPackage.MODEL__MODULES:
				unsetModules();
				return;
			case PomPackage.MODEL__REPOSITORIES:
				unsetRepositories();
				return;
			case PomPackage.MODEL__PLUGIN_REPOSITORIES:
				unsetPluginRepositories();
				return;
			case PomPackage.MODEL__DEPENDENCIES:
				unsetDependencies();
				return;
			case PomPackage.MODEL__REPORTS:
				unsetReports();
				return;
			case PomPackage.MODEL__REPORTING:
				unsetReporting();
				return;
			case PomPackage.MODEL__DEPENDENCY_MANAGEMENT:
				unsetDependencyManagement();
				return;
			case PomPackage.MODEL__DISTRIBUTION_MANAGEMENT:
				unsetDistributionManagement();
				return;
			case PomPackage.MODEL__PROPERTIES:
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
		switch (featureID) {
			case PomPackage.MODEL__PARENT:
				return isSetParent();
			case PomPackage.MODEL__MODEL_VERSION:
				return MODEL_VERSION_EDEFAULT == null ? modelVersion != null : !MODEL_VERSION_EDEFAULT.equals(modelVersion);
			case PomPackage.MODEL__GROUP_ID:
				return GROUP_ID_EDEFAULT == null ? groupId != null : !GROUP_ID_EDEFAULT.equals(groupId);
			case PomPackage.MODEL__ARTIFACT_ID:
				return ARTIFACT_ID_EDEFAULT == null ? artifactId != null : !ARTIFACT_ID_EDEFAULT.equals(artifactId);
			case PomPackage.MODEL__PACKAGING:
				return isSetPackaging();
			case PomPackage.MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PomPackage.MODEL__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case PomPackage.MODEL__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case PomPackage.MODEL__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case PomPackage.MODEL__PREREQUISITES:
				return isSetPrerequisites();
			case PomPackage.MODEL__ISSUE_MANAGEMENT:
				return isSetIssueManagement();
			case PomPackage.MODEL__CI_MANAGEMENT:
				return isSetCiManagement();
			case PomPackage.MODEL__INCEPTION_YEAR:
				return INCEPTION_YEAR_EDEFAULT == null ? inceptionYear != null : !INCEPTION_YEAR_EDEFAULT.equals(inceptionYear);
			case PomPackage.MODEL__MAILING_LISTS:
				return isSetMailingLists();
			case PomPackage.MODEL__DEVELOPERS:
				return isSetDevelopers();
			case PomPackage.MODEL__CONTRIBUTORS:
				return isSetContributors();
			case PomPackage.MODEL__LICENSES:
				return licenses != null;
			case PomPackage.MODEL__SCM:
				return isSetScm();
			case PomPackage.MODEL__ORGANIZATION:
				return isSetOrganization();
			case PomPackage.MODEL__BUILD:
				return isSetBuild();
			case PomPackage.MODEL__PROFILES:
				return isSetProfiles();
			case PomPackage.MODEL__MODULES:
				return isSetModules();
			case PomPackage.MODEL__REPOSITORIES:
				return isSetRepositories();
			case PomPackage.MODEL__PLUGIN_REPOSITORIES:
				return isSetPluginRepositories();
			case PomPackage.MODEL__DEPENDENCIES:
				return isSetDependencies();
			case PomPackage.MODEL__REPORTS:
				return isSetReports();
			case PomPackage.MODEL__REPORTING:
				return isSetReporting();
			case PomPackage.MODEL__DEPENDENCY_MANAGEMENT:
				return isSetDependencyManagement();
			case PomPackage.MODEL__DISTRIBUTION_MANAGEMENT:
				return isSetDistributionManagement();
			case PomPackage.MODEL__PROPERTIES:
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
		result.append(" (modelVersion: ");
		result.append(modelVersion);
		result.append(", groupId: ");
		result.append(groupId);
		result.append(", artifactId: ");
		result.append(artifactId);
		result.append(", packaging: ");
		if (packagingESet) result.append(packaging); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(", version: ");
		result.append(version);
		result.append(", description: ");
		result.append(description);
		result.append(", url: ");
		result.append(url);
		result.append(", inceptionYear: ");
		result.append(inceptionYear);
		result.append(')');
		return result.toString();
	}

} //ModelImpl
