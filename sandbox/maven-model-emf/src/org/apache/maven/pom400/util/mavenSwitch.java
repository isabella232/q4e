/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.util;

import java.util.List;

import org.apache.maven.pom400.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.apache.maven.pom400.mavenPackage
 * @generated
 */
public class mavenSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static mavenPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public mavenSwitch() {
		if (modelPackage == null) {
			modelPackage = mavenPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case mavenPackage.ACTIVATION: {
				Activation activation = (Activation)theEObject;
				Object result = caseActivation(activation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ACTIVATION_FILE: {
				ActivationFile activationFile = (ActivationFile)theEObject;
				Object result = caseActivationFile(activationFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ACTIVATION_OS: {
				ActivationOS activationOS = (ActivationOS)theEObject;
				Object result = caseActivationOS(activationOS);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ACTIVATION_PROPERTY: {
				ActivationProperty activationProperty = (ActivationProperty)theEObject;
				Object result = caseActivationProperty(activationProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.BUILD: {
				Build build = (Build)theEObject;
				Object result = caseBuild(build);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.BUILD_BASE: {
				BuildBase buildBase = (BuildBase)theEObject;
				Object result = caseBuildBase(buildBase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CI_MANAGEMENT: {
				CiManagement ciManagement = (CiManagement)theEObject;
				Object result = caseCiManagement(ciManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONFIGURATION_TYPE: {
				ConfigurationType configurationType = (ConfigurationType)theEObject;
				Object result = caseConfigurationType(configurationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONFIGURATION_TYPE1: {
				ConfigurationType1 configurationType1 = (ConfigurationType1)theEObject;
				Object result = caseConfigurationType1(configurationType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONFIGURATION_TYPE2: {
				ConfigurationType2 configurationType2 = (ConfigurationType2)theEObject;
				Object result = caseConfigurationType2(configurationType2);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONFIGURATION_TYPE3: {
				ConfigurationType3 configurationType3 = (ConfigurationType3)theEObject;
				Object result = caseConfigurationType3(configurationType3);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONFIGURATION_TYPE4: {
				ConfigurationType4 configurationType4 = (ConfigurationType4)theEObject;
				Object result = caseConfigurationType4(configurationType4);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONTRIBUTOR: {
				Contributor contributor = (Contributor)theEObject;
				Object result = caseContributor(contributor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.CONTRIBUTORS_TYPE: {
				ContributorsType contributorsType = (ContributorsType)theEObject;
				Object result = caseContributorsType(contributorsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCIES_TYPE: {
				DependenciesType dependenciesType = (DependenciesType)theEObject;
				Object result = caseDependenciesType(dependenciesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCIES_TYPE1: {
				DependenciesType1 dependenciesType1 = (DependenciesType1)theEObject;
				Object result = caseDependenciesType1(dependenciesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCIES_TYPE2: {
				DependenciesType2 dependenciesType2 = (DependenciesType2)theEObject;
				Object result = caseDependenciesType2(dependenciesType2);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCIES_TYPE3: {
				DependenciesType3 dependenciesType3 = (DependenciesType3)theEObject;
				Object result = caseDependenciesType3(dependenciesType3);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCY: {
				Dependency dependency = (Dependency)theEObject;
				Object result = caseDependency(dependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPENDENCY_MANAGEMENT: {
				DependencyManagement dependencyManagement = (DependencyManagement)theEObject;
				Object result = caseDependencyManagement(dependencyManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEPLOYMENT_REPOSITORY: {
				DeploymentRepository deploymentRepository = (DeploymentRepository)theEObject;
				Object result = caseDeploymentRepository(deploymentRepository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEVELOPER: {
				Developer developer = (Developer)theEObject;
				Object result = caseDeveloper(developer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DEVELOPERS_TYPE: {
				DevelopersType developersType = (DevelopersType)theEObject;
				Object result = caseDevelopersType(developersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DISTRIBUTION_MANAGEMENT: {
				DistributionManagement distributionManagement = (DistributionManagement)theEObject;
				Object result = caseDistributionManagement(distributionManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				Object result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXCLUDES_TYPE: {
				ExcludesType excludesType = (ExcludesType)theEObject;
				Object result = caseExcludesType(excludesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXCLUSION: {
				Exclusion exclusion = (Exclusion)theEObject;
				Object result = caseExclusion(exclusion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXCLUSIONS_TYPE: {
				ExclusionsType exclusionsType = (ExclusionsType)theEObject;
				Object result = caseExclusionsType(exclusionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXECUTIONS_TYPE: {
				ExecutionsType executionsType = (ExecutionsType)theEObject;
				Object result = caseExecutionsType(executionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXTENSION: {
				Extension extension = (Extension)theEObject;
				Object result = caseExtension(extension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.EXTENSIONS_TYPE: {
				ExtensionsType extensionsType = (ExtensionsType)theEObject;
				Object result = caseExtensionsType(extensionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.FILTERS_TYPE: {
				FiltersType filtersType = (FiltersType)theEObject;
				Object result = caseFiltersType(filtersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.FILTERS_TYPE1: {
				FiltersType1 filtersType1 = (FiltersType1)theEObject;
				Object result = caseFiltersType1(filtersType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.GOALS_TYPE: {
				GoalsType goalsType = (GoalsType)theEObject;
				Object result = caseGoalsType(goalsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.GOALS_TYPE1: {
				GoalsType1 goalsType1 = (GoalsType1)theEObject;
				Object result = caseGoalsType1(goalsType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.INCLUDES_TYPE: {
				IncludesType includesType = (IncludesType)theEObject;
				Object result = caseIncludesType(includesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ISSUE_MANAGEMENT: {
				IssueManagement issueManagement = (IssueManagement)theEObject;
				Object result = caseIssueManagement(issueManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.LICENSE: {
				License license = (License)theEObject;
				Object result = caseLicense(license);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.LICENSES_TYPE: {
				LicensesType licensesType = (LicensesType)theEObject;
				Object result = caseLicensesType(licensesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.MAILING_LIST: {
				MailingList mailingList = (MailingList)theEObject;
				Object result = caseMailingList(mailingList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.MAILING_LISTS_TYPE: {
				MailingListsType mailingListsType = (MailingListsType)theEObject;
				Object result = caseMailingListsType(mailingListsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.MODEL: {
				Model model = (Model)theEObject;
				Object result = caseModel(model);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.MODULES_TYPE: {
				ModulesType modulesType = (ModulesType)theEObject;
				Object result = caseModulesType(modulesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.MODULES_TYPE1: {
				ModulesType1 modulesType1 = (ModulesType1)theEObject;
				Object result = caseModulesType1(modulesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.NOTIFIER: {
				Notifier notifier = (Notifier)theEObject;
				Object result = caseNotifier(notifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.NOTIFIERS_TYPE: {
				NotifiersType notifiersType = (NotifiersType)theEObject;
				Object result = caseNotifiersType(notifiersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ORGANIZATION: {
				Organization organization = (Organization)theEObject;
				Object result = caseOrganization(organization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.OTHER_ARCHIVES_TYPE: {
				OtherArchivesType otherArchivesType = (OtherArchivesType)theEObject;
				Object result = caseOtherArchivesType(otherArchivesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PARENT: {
				Parent parent = (Parent)theEObject;
				Object result = caseParent(parent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGIN: {
				Plugin plugin = (Plugin)theEObject;
				Object result = casePlugin(plugin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGIN_EXECUTION: {
				PluginExecution pluginExecution = (PluginExecution)theEObject;
				Object result = casePluginExecution(pluginExecution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGIN_MANAGEMENT: {
				PluginManagement pluginManagement = (PluginManagement)theEObject;
				Object result = casePluginManagement(pluginManagement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE: {
				PluginRepositoriesType pluginRepositoriesType = (PluginRepositoriesType)theEObject;
				Object result = casePluginRepositoriesType(pluginRepositoriesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGIN_REPOSITORIES_TYPE1: {
				PluginRepositoriesType1 pluginRepositoriesType1 = (PluginRepositoriesType1)theEObject;
				Object result = casePluginRepositoriesType1(pluginRepositoriesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGINS_TYPE: {
				PluginsType pluginsType = (PluginsType)theEObject;
				Object result = casePluginsType(pluginsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGINS_TYPE1: {
				PluginsType1 pluginsType1 = (PluginsType1)theEObject;
				Object result = casePluginsType1(pluginsType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGINS_TYPE2: {
				PluginsType2 pluginsType2 = (PluginsType2)theEObject;
				Object result = casePluginsType2(pluginsType2);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PLUGINS_TYPE3: {
				PluginsType3 pluginsType3 = (PluginsType3)theEObject;
				Object result = casePluginsType3(pluginsType3);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PREREQUISITES: {
				Prerequisites prerequisites = (Prerequisites)theEObject;
				Object result = casePrerequisites(prerequisites);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROFILE: {
				Profile profile = (Profile)theEObject;
				Object result = caseProfile(profile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROFILES_TYPE: {
				ProfilesType profilesType = (ProfilesType)theEObject;
				Object result = caseProfilesType(profilesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROPERTIES_TYPE: {
				PropertiesType propertiesType = (PropertiesType)theEObject;
				Object result = casePropertiesType(propertiesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROPERTIES_TYPE1: {
				PropertiesType1 propertiesType1 = (PropertiesType1)theEObject;
				Object result = casePropertiesType1(propertiesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROPERTIES_TYPE2: {
				PropertiesType2 propertiesType2 = (PropertiesType2)theEObject;
				Object result = casePropertiesType2(propertiesType2);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.PROPERTIES_TYPE3: {
				PropertiesType3 propertiesType3 = (PropertiesType3)theEObject;
				Object result = casePropertiesType3(propertiesType3);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.RELOCATION: {
				Relocation relocation = (Relocation)theEObject;
				Object result = caseRelocation(relocation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORTING: {
				Reporting reporting = (Reporting)theEObject;
				Object result = caseReporting(reporting);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORT_PLUGIN: {
				ReportPlugin reportPlugin = (ReportPlugin)theEObject;
				Object result = caseReportPlugin(reportPlugin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORT_SET: {
				ReportSet reportSet = (ReportSet)theEObject;
				Object result = caseReportSet(reportSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORT_SETS_TYPE: {
				ReportSetsType reportSetsType = (ReportSetsType)theEObject;
				Object result = caseReportSetsType(reportSetsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORTS_TYPE: {
				ReportsType reportsType = (ReportsType)theEObject;
				Object result = caseReportsType(reportsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORTS_TYPE1: {
				ReportsType1 reportsType1 = (ReportsType1)theEObject;
				Object result = caseReportsType1(reportsType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPORTS_TYPE2: {
				ReportsType2 reportsType2 = (ReportsType2)theEObject;
				Object result = caseReportsType2(reportsType2);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPOSITORIES_TYPE: {
				RepositoriesType repositoriesType = (RepositoriesType)theEObject;
				Object result = caseRepositoriesType(repositoriesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPOSITORIES_TYPE1: {
				RepositoriesType1 repositoriesType1 = (RepositoriesType1)theEObject;
				Object result = caseRepositoriesType1(repositoriesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPOSITORY: {
				Repository repository = (Repository)theEObject;
				Object result = caseRepository(repository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.REPOSITORY_POLICY: {
				RepositoryPolicy repositoryPolicy = (RepositoryPolicy)theEObject;
				Object result = caseRepositoryPolicy(repositoryPolicy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.RESOURCE: {
				Resource resource = (Resource)theEObject;
				Object result = caseResource(resource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.RESOURCES_TYPE: {
				ResourcesType resourcesType = (ResourcesType)theEObject;
				Object result = caseResourcesType(resourcesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.RESOURCES_TYPE1: {
				ResourcesType1 resourcesType1 = (ResourcesType1)theEObject;
				Object result = caseResourcesType1(resourcesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ROLES_TYPE: {
				RolesType rolesType = (RolesType)theEObject;
				Object result = caseRolesType(rolesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.ROLES_TYPE1: {
				RolesType1 rolesType1 = (RolesType1)theEObject;
				Object result = caseRolesType1(rolesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.SCM: {
				Scm scm = (Scm)theEObject;
				Object result = caseScm(scm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.SITE: {
				Site site = (Site)theEObject;
				Object result = caseSite(site);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.TEST_RESOURCES_TYPE: {
				TestResourcesType testResourcesType = (TestResourcesType)theEObject;
				Object result = caseTestResourcesType(testResourcesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case mavenPackage.TEST_RESOURCES_TYPE1: {
				TestResourcesType1 testResourcesType1 = (TestResourcesType1)theEObject;
				Object result = caseTestResourcesType1(testResourcesType1);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivation(Activation object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activation File</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activation File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivationFile(ActivationFile object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activation OS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activation OS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivationOS(ActivationOS object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activation Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activation Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivationProperty(ActivationProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Build</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Build</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBuild(Build object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Build Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Build Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBuildBase(BuildBase object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Ci Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Ci Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCiManagement(CiManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Configuration Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Configuration Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConfigurationType(ConfigurationType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Configuration Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Configuration Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConfigurationType1(ConfigurationType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Configuration Type2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Configuration Type2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConfigurationType2(ConfigurationType2 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Configuration Type3</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Configuration Type3</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConfigurationType3(ConfigurationType3 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Configuration Type4</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Configuration Type4</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConfigurationType4(ConfigurationType4 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Contributor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Contributor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseContributor(Contributor object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Contributors Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Contributors Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseContributorsType(ContributorsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependencies Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependencies Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependenciesType(DependenciesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependencies Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependencies Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependenciesType1(DependenciesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependencies Type2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependencies Type2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependenciesType2(DependenciesType2 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependencies Type3</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependencies Type3</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependenciesType3(DependenciesType3 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependency(Dependency object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependency Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependency Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependencyManagement(DependencyManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployment Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployment Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeploymentRepository(DeploymentRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Developer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Developer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeveloper(Developer object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Developers Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Developers Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDevelopersType(DevelopersType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Distribution Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Distribution Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDistributionManagement(DistributionManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Excludes Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Excludes Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExcludesType(ExcludesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Exclusion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Exclusion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExclusion(Exclusion object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Exclusions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Exclusions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExclusionsType(ExclusionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Executions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Executions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExecutionsType(ExecutionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtension(Extension object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extensions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extensions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensionsType(ExtensionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Filters Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Filters Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFiltersType(FiltersType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Filters Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Filters Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFiltersType1(FiltersType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Goals Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Goals Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGoalsType(GoalsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Goals Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Goals Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGoalsType1(GoalsType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Includes Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Includes Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIncludesType(IncludesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Issue Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Issue Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIssueManagement(IssueManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>License</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>License</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLicense(License object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Licenses Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Licenses Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLicensesType(LicensesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mailing List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mailing List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMailingList(MailingList object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Mailing Lists Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Mailing Lists Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMailingListsType(MailingListsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModel(Model object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Modules Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Modules Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModulesType(ModulesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Modules Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Modules Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModulesType1(ModulesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Notifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Notifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNotifier(Notifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Notifiers Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Notifiers Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNotifiersType(NotifiersType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Organization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Organization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOrganization(Organization object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Other Archives Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Other Archives Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOtherArchivesType(OtherArchivesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParent(Parent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePlugin(Plugin object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugin Execution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugin Execution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginExecution(PluginExecution object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugin Management</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugin Management</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginManagement(PluginManagement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugin Repositories Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugin Repositories Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginRepositoriesType(PluginRepositoriesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugin Repositories Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugin Repositories Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginRepositoriesType1(PluginRepositoriesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugins Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugins Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginsType(PluginsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugins Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugins Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginsType1(PluginsType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugins Type2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugins Type2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginsType2(PluginsType2 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Plugins Type3</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Plugins Type3</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePluginsType3(PluginsType3 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Prerequisites</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Prerequisites</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePrerequisites(Prerequisites object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProfile(Profile object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Profiles Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Profiles Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProfilesType(ProfilesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertiesType(PropertiesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertiesType1(PropertiesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties Type2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties Type2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertiesType2(PropertiesType2 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Properties Type3</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Properties Type3</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertiesType3(PropertiesType3 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Relocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Relocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRelocation(Relocation object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reporting</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reporting</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReporting(Reporting object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Report Plugin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Report Plugin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportPlugin(ReportPlugin object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Report Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Report Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportSet(ReportSet object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Report Sets Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Report Sets Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportSetsType(ReportSetsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reports Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reports Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportsType(ReportsType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reports Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reports Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportsType1(ReportsType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reports Type2</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reports Type2</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReportsType2(ReportsType2 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Repositories Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Repositories Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRepositoriesType(RepositoriesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Repositories Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Repositories Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRepositoriesType1(RepositoriesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRepository(Repository object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Repository Policy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Repository Policy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRepositoryPolicy(RepositoryPolicy object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseResource(Resource object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Resources Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Resources Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseResourcesType(ResourcesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Resources Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Resources Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseResourcesType1(ResourcesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Roles Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Roles Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRolesType(RolesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Roles Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Roles Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRolesType1(RolesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Scm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Scm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseScm(Scm object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Site</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Site</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSite(Site object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Test Resources Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Test Resources Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTestResourcesType(TestResourcesType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Test Resources Type1</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Test Resources Type1</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTestResourcesType1(TestResourcesType1 object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //mavenSwitch
