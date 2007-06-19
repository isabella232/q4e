/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.impl;

import org.apache.maven.pom400.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class mavenFactoryImpl extends EFactoryImpl implements mavenFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static mavenFactory init() {
		try {
			mavenFactory themavenFactory = (mavenFactory)EPackage.Registry.INSTANCE.getEFactory("http://maven.apache.org/POM/4.0.0"); 
			if (themavenFactory != null) {
				return themavenFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new mavenFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public mavenFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case mavenPackage.ACTIVATION: return createActivation();
			case mavenPackage.ACTIVATION_FILE: return createActivationFile();
			case mavenPackage.ACTIVATION_OS: return createActivationOS();
			case mavenPackage.ACTIVATION_PROPERTY: return createActivationProperty();
			case mavenPackage.BUILD: return createBuild();
			case mavenPackage.BUILD_BASE: return createBuildBase();
			case mavenPackage.CI_MANAGEMENT: return createCiManagement();
			case mavenPackage.CONFIGURATION_TYPE: return createConfigurationType();
			case mavenPackage.CONFIGURATION_TYPE1: return createConfigurationType1();
			case mavenPackage.CONFIGURATION_TYPE2: return createConfigurationType2();
			case mavenPackage.CONFIGURATION_TYPE3: return createConfigurationType3();
			case mavenPackage.CONFIGURATION_TYPE4: return createConfigurationType4();
			case mavenPackage.CONTRIBUTOR: return createContributor();
			case mavenPackage.CONTRIBUTORS_TYPE: return createContributorsType();
			case mavenPackage.DEPENDENCIES_TYPE: return createDependenciesType();
			case mavenPackage.DEPENDENCIES_TYPE1: return createDependenciesType1();
			case mavenPackage.DEPENDENCIES_TYPE2: return createDependenciesType2();
			case mavenPackage.DEPENDENCIES_TYPE3: return createDependenciesType3();
			case mavenPackage.DEPENDENCY: return createDependency();
			case mavenPackage.DEPENDENCY_MANAGEMENT: return createDependencyManagement();
			case mavenPackage.DEPLOYMENT_REPOSITORY: return createDeploymentRepository();
			case mavenPackage.DEVELOPER: return createDeveloper();
			case mavenPackage.DEVELOPERS_TYPE: return createDevelopersType();
			case mavenPackage.DISTRIBUTION_MANAGEMENT: return createDistributionManagement();
			case mavenPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case mavenPackage.EXCLUDES_TYPE: return createExcludesType();
			case mavenPackage.EXCLUSION: return createExclusion();
			case mavenPackage.EXCLUSIONS_TYPE: return createExclusionsType();
			case mavenPackage.EXECUTIONS_TYPE: return createExecutionsType();
			case mavenPackage.EXTENSION: return createExtension();
			case mavenPackage.EXTENSIONS_TYPE: return createExtensionsType();
			case mavenPackage.FILTERS_TYPE: return createFiltersType();
			case mavenPackage.FILTERS_TYPE1: return createFiltersType1();
			case mavenPackage.GOALS_TYPE: return createGoalsType();
			case mavenPackage.GOALS_TYPE1: return createGoalsType1();
			case mavenPackage.INCLUDES_TYPE: return createIncludesType();
			case mavenPackage.ISSUE_MANAGEMENT: return createIssueManagement();
			case mavenPackage.LICENSE: return createLicense();
			case mavenPackage.LICENSES_TYPE: return createLicensesType();
			case mavenPackage.MAILING_LIST: return createMailingList();
			case mavenPackage.MAILING_LISTS_TYPE: return createMailingListsType();
			case mavenPackage.MODEL: return createModel();
			case mavenPackage.MODULES_TYPE: return createModulesType();
			case mavenPackage.MODULES_TYPE1: return createModulesType1();
			case mavenPackage.NOTIFIER: return createNotifier();
			case mavenPackage.NOTIFIERS_TYPE: return createNotifiersType();
			case mavenPackage.ORGANIZATION: return createOrganization();
			case mavenPackage.OTHER_ARCHIVES_TYPE: return createOtherArchivesType();
			case mavenPackage.PARENT: return createParent();
			case mavenPackage.PLUGIN: return createPlugin();
			case mavenPackage.PLUGIN_EXECUTION: return createPluginExecution();
			case mavenPackage.PLUGIN_MANAGEMENT: return createPluginManagement();
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE: return createPluginRepositoriesType();
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE1: return createPluginRepositoriesType1();
			case mavenPackage.PLUGINS_TYPE: return createPluginsType();
			case mavenPackage.PLUGINS_TYPE1: return createPluginsType1();
			case mavenPackage.PLUGINS_TYPE2: return createPluginsType2();
			case mavenPackage.PLUGINS_TYPE3: return createPluginsType3();
			case mavenPackage.PREREQUISITES: return createPrerequisites();
			case mavenPackage.PROFILE: return createProfile();
			case mavenPackage.PROFILES_TYPE: return createProfilesType();
			case mavenPackage.PROPERTIES_TYPE: return createPropertiesType();
			case mavenPackage.PROPERTIES_TYPE1: return createPropertiesType1();
			case mavenPackage.PROPERTIES_TYPE2: return createPropertiesType2();
			case mavenPackage.PROPERTIES_TYPE3: return createPropertiesType3();
			case mavenPackage.RELOCATION: return createRelocation();
			case mavenPackage.REPORTING: return createReporting();
			case mavenPackage.REPORT_PLUGIN: return createReportPlugin();
			case mavenPackage.REPORT_SET: return createReportSet();
			case mavenPackage.REPORT_SETS_TYPE: return createReportSetsType();
			case mavenPackage.REPORTS_TYPE: return createReportsType();
			case mavenPackage.REPORTS_TYPE1: return createReportsType1();
			case mavenPackage.REPORTS_TYPE2: return createReportsType2();
			case mavenPackage.REPOSITORIES_TYPE: return createRepositoriesType();
			case mavenPackage.REPOSITORIES_TYPE1: return createRepositoriesType1();
			case mavenPackage.REPOSITORY: return createRepository();
			case mavenPackage.REPOSITORY_POLICY: return createRepositoryPolicy();
			case mavenPackage.RESOURCE: return createResource();
			case mavenPackage.RESOURCES_TYPE: return createResourcesType();
			case mavenPackage.RESOURCES_TYPE1: return createResourcesType1();
			case mavenPackage.ROLES_TYPE: return createRolesType();
			case mavenPackage.ROLES_TYPE1: return createRolesType1();
			case mavenPackage.SCM: return createScm();
			case mavenPackage.SITE: return createSite();
			case mavenPackage.TEST_RESOURCES_TYPE: return createTestResourcesType();
			case mavenPackage.TEST_RESOURCES_TYPE1: return createTestResourcesType1();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activation createActivation() {
		ActivationImpl activation = new ActivationImpl();
		return activation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivationFile createActivationFile() {
		ActivationFileImpl activationFile = new ActivationFileImpl();
		return activationFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivationOS createActivationOS() {
		ActivationOSImpl activationOS = new ActivationOSImpl();
		return activationOS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivationProperty createActivationProperty() {
		ActivationPropertyImpl activationProperty = new ActivationPropertyImpl();
		return activationProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Build createBuild() {
		BuildImpl build = new BuildImpl();
		return build;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildBase createBuildBase() {
		BuildBaseImpl buildBase = new BuildBaseImpl();
		return buildBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CiManagement createCiManagement() {
		CiManagementImpl ciManagement = new CiManagementImpl();
		return ciManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType createConfigurationType() {
		ConfigurationTypeImpl configurationType = new ConfigurationTypeImpl();
		return configurationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType1 createConfigurationType1() {
		ConfigurationType1Impl configurationType1 = new ConfigurationType1Impl();
		return configurationType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType2 createConfigurationType2() {
		ConfigurationType2Impl configurationType2 = new ConfigurationType2Impl();
		return configurationType2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType3 createConfigurationType3() {
		ConfigurationType3Impl configurationType3 = new ConfigurationType3Impl();
		return configurationType3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType4 createConfigurationType4() {
		ConfigurationType4Impl configurationType4 = new ConfigurationType4Impl();
		return configurationType4;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Contributor createContributor() {
		ContributorImpl contributor = new ContributorImpl();
		return contributor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContributorsType createContributorsType() {
		ContributorsTypeImpl contributorsType = new ContributorsTypeImpl();
		return contributorsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType createDependenciesType() {
		DependenciesTypeImpl dependenciesType = new DependenciesTypeImpl();
		return dependenciesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType1 createDependenciesType1() {
		DependenciesType1Impl dependenciesType1 = new DependenciesType1Impl();
		return dependenciesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType2 createDependenciesType2() {
		DependenciesType2Impl dependenciesType2 = new DependenciesType2Impl();
		return dependenciesType2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType3 createDependenciesType3() {
		DependenciesType3Impl dependenciesType3 = new DependenciesType3Impl();
		return dependenciesType3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dependency createDependency() {
		DependencyImpl dependency = new DependencyImpl();
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyManagement createDependencyManagement() {
		DependencyManagementImpl dependencyManagement = new DependencyManagementImpl();
		return dependencyManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentRepository createDeploymentRepository() {
		DeploymentRepositoryImpl deploymentRepository = new DeploymentRepositoryImpl();
		return deploymentRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Developer createDeveloper() {
		DeveloperImpl developer = new DeveloperImpl();
		return developer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DevelopersType createDevelopersType() {
		DevelopersTypeImpl developersType = new DevelopersTypeImpl();
		return developersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistributionManagement createDistributionManagement() {
		DistributionManagementImpl distributionManagement = new DistributionManagementImpl();
		return distributionManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExcludesType createExcludesType() {
		ExcludesTypeImpl excludesType = new ExcludesTypeImpl();
		return excludesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Exclusion createExclusion() {
		ExclusionImpl exclusion = new ExclusionImpl();
		return exclusion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExclusionsType createExclusionsType() {
		ExclusionsTypeImpl exclusionsType = new ExclusionsTypeImpl();
		return exclusionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionsType createExecutionsType() {
		ExecutionsTypeImpl executionsType = new ExecutionsTypeImpl();
		return executionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Extension createExtension() {
		ExtensionImpl extension = new ExtensionImpl();
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType createExtensionsType() {
		ExtensionsTypeImpl extensionsType = new ExtensionsTypeImpl();
		return extensionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FiltersType createFiltersType() {
		FiltersTypeImpl filtersType = new FiltersTypeImpl();
		return filtersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FiltersType1 createFiltersType1() {
		FiltersType1Impl filtersType1 = new FiltersType1Impl();
		return filtersType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GoalsType createGoalsType() {
		GoalsTypeImpl goalsType = new GoalsTypeImpl();
		return goalsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GoalsType1 createGoalsType1() {
		GoalsType1Impl goalsType1 = new GoalsType1Impl();
		return goalsType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncludesType createIncludesType() {
		IncludesTypeImpl includesType = new IncludesTypeImpl();
		return includesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IssueManagement createIssueManagement() {
		IssueManagementImpl issueManagement = new IssueManagementImpl();
		return issueManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public License createLicense() {
		LicenseImpl license = new LicenseImpl();
		return license;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LicensesType createLicensesType() {
		LicensesTypeImpl licensesType = new LicensesTypeImpl();
		return licensesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MailingList createMailingList() {
		MailingListImpl mailingList = new MailingListImpl();
		return mailingList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MailingListsType createMailingListsType() {
		MailingListsTypeImpl mailingListsType = new MailingListsTypeImpl();
		return mailingListsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Model createModel() {
		ModelImpl model = new ModelImpl();
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModulesType createModulesType() {
		ModulesTypeImpl modulesType = new ModulesTypeImpl();
		return modulesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModulesType1 createModulesType1() {
		ModulesType1Impl modulesType1 = new ModulesType1Impl();
		return modulesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Notifier createNotifier() {
		NotifierImpl notifier = new NotifierImpl();
		return notifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotifiersType createNotifiersType() {
		NotifiersTypeImpl notifiersType = new NotifiersTypeImpl();
		return notifiersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization createOrganization() {
		OrganizationImpl organization = new OrganizationImpl();
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OtherArchivesType createOtherArchivesType() {
		OtherArchivesTypeImpl otherArchivesType = new OtherArchivesTypeImpl();
		return otherArchivesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parent createParent() {
		ParentImpl parent = new ParentImpl();
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plugin createPlugin() {
		PluginImpl plugin = new PluginImpl();
		return plugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginExecution createPluginExecution() {
		PluginExecutionImpl pluginExecution = new PluginExecutionImpl();
		return pluginExecution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginManagement createPluginManagement() {
		PluginManagementImpl pluginManagement = new PluginManagementImpl();
		return pluginManagement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginRepositoriesType createPluginRepositoriesType() {
		PluginRepositoriesTypeImpl pluginRepositoriesType = new PluginRepositoriesTypeImpl();
		return pluginRepositoriesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginRepositoriesType1 createPluginRepositoriesType1() {
		PluginRepositoriesType1Impl pluginRepositoriesType1 = new PluginRepositoriesType1Impl();
		return pluginRepositoriesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginsType createPluginsType() {
		PluginsTypeImpl pluginsType = new PluginsTypeImpl();
		return pluginsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginsType1 createPluginsType1() {
		PluginsType1Impl pluginsType1 = new PluginsType1Impl();
		return pluginsType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginsType2 createPluginsType2() {
		PluginsType2Impl pluginsType2 = new PluginsType2Impl();
		return pluginsType2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginsType3 createPluginsType3() {
		PluginsType3Impl pluginsType3 = new PluginsType3Impl();
		return pluginsType3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Prerequisites createPrerequisites() {
		PrerequisitesImpl prerequisites = new PrerequisitesImpl();
		return prerequisites;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Profile createProfile() {
		ProfileImpl profile = new ProfileImpl();
		return profile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProfilesType createProfilesType() {
		ProfilesTypeImpl profilesType = new ProfilesTypeImpl();
		return profilesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType createPropertiesType() {
		PropertiesTypeImpl propertiesType = new PropertiesTypeImpl();
		return propertiesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType1 createPropertiesType1() {
		PropertiesType1Impl propertiesType1 = new PropertiesType1Impl();
		return propertiesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType2 createPropertiesType2() {
		PropertiesType2Impl propertiesType2 = new PropertiesType2Impl();
		return propertiesType2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesType3 createPropertiesType3() {
		PropertiesType3Impl propertiesType3 = new PropertiesType3Impl();
		return propertiesType3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Relocation createRelocation() {
		RelocationImpl relocation = new RelocationImpl();
		return relocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reporting createReporting() {
		ReportingImpl reporting = new ReportingImpl();
		return reporting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportPlugin createReportPlugin() {
		ReportPluginImpl reportPlugin = new ReportPluginImpl();
		return reportPlugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportSet createReportSet() {
		ReportSetImpl reportSet = new ReportSetImpl();
		return reportSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportSetsType createReportSetsType() {
		ReportSetsTypeImpl reportSetsType = new ReportSetsTypeImpl();
		return reportSetsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportsType createReportsType() {
		ReportsTypeImpl reportsType = new ReportsTypeImpl();
		return reportsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportsType1 createReportsType1() {
		ReportsType1Impl reportsType1 = new ReportsType1Impl();
		return reportsType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportsType2 createReportsType2() {
		ReportsType2Impl reportsType2 = new ReportsType2Impl();
		return reportsType2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoriesType createRepositoriesType() {
		RepositoriesTypeImpl repositoriesType = new RepositoriesTypeImpl();
		return repositoriesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoriesType1 createRepositoriesType1() {
		RepositoriesType1Impl repositoriesType1 = new RepositoriesType1Impl();
		return repositoriesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Repository createRepository() {
		RepositoryImpl repository = new RepositoryImpl();
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryPolicy createRepositoryPolicy() {
		RepositoryPolicyImpl repositoryPolicy = new RepositoryPolicyImpl();
		return repositoryPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesType createResourcesType() {
		ResourcesTypeImpl resourcesType = new ResourcesTypeImpl();
		return resourcesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesType1 createResourcesType1() {
		ResourcesType1Impl resourcesType1 = new ResourcesType1Impl();
		return resourcesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RolesType createRolesType() {
		RolesTypeImpl rolesType = new RolesTypeImpl();
		return rolesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RolesType1 createRolesType1() {
		RolesType1Impl rolesType1 = new RolesType1Impl();
		return rolesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scm createScm() {
		ScmImpl scm = new ScmImpl();
		return scm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Site createSite() {
		SiteImpl site = new SiteImpl();
		return site;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestResourcesType createTestResourcesType() {
		TestResourcesTypeImpl testResourcesType = new TestResourcesTypeImpl();
		return testResourcesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestResourcesType1 createTestResourcesType1() {
		TestResourcesType1Impl testResourcesType1 = new TestResourcesType1Impl();
		return testResourcesType1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public mavenPackage getmavenPackage() {
		return (mavenPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static mavenPackage getPackage() {
		return mavenPackage.eINSTANCE;
	}

} //mavenFactoryImpl
