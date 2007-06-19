/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.util;

import org.apache.maven.pom400.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.apache.maven.pom400.mavenPackage
 * @generated
 */
public class mavenAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static mavenPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public mavenAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = mavenPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected mavenSwitch modelSwitch =
		new mavenSwitch() {
			public Object caseActivation(Activation object) {
				return createActivationAdapter();
			}
			public Object caseActivationFile(ActivationFile object) {
				return createActivationFileAdapter();
			}
			public Object caseActivationOS(ActivationOS object) {
				return createActivationOSAdapter();
			}
			public Object caseActivationProperty(ActivationProperty object) {
				return createActivationPropertyAdapter();
			}
			public Object caseBuild(Build object) {
				return createBuildAdapter();
			}
			public Object caseBuildBase(BuildBase object) {
				return createBuildBaseAdapter();
			}
			public Object caseCiManagement(CiManagement object) {
				return createCiManagementAdapter();
			}
			public Object caseConfigurationType(ConfigurationType object) {
				return createConfigurationTypeAdapter();
			}
			public Object caseConfigurationType1(ConfigurationType1 object) {
				return createConfigurationType1Adapter();
			}
			public Object caseConfigurationType2(ConfigurationType2 object) {
				return createConfigurationType2Adapter();
			}
			public Object caseConfigurationType3(ConfigurationType3 object) {
				return createConfigurationType3Adapter();
			}
			public Object caseConfigurationType4(ConfigurationType4 object) {
				return createConfigurationType4Adapter();
			}
			public Object caseContributor(Contributor object) {
				return createContributorAdapter();
			}
			public Object caseContributorsType(ContributorsType object) {
				return createContributorsTypeAdapter();
			}
			public Object caseDependenciesType(DependenciesType object) {
				return createDependenciesTypeAdapter();
			}
			public Object caseDependenciesType1(DependenciesType1 object) {
				return createDependenciesType1Adapter();
			}
			public Object caseDependenciesType2(DependenciesType2 object) {
				return createDependenciesType2Adapter();
			}
			public Object caseDependenciesType3(DependenciesType3 object) {
				return createDependenciesType3Adapter();
			}
			public Object caseDependency(Dependency object) {
				return createDependencyAdapter();
			}
			public Object caseDependencyManagement(DependencyManagement object) {
				return createDependencyManagementAdapter();
			}
			public Object caseDeploymentRepository(DeploymentRepository object) {
				return createDeploymentRepositoryAdapter();
			}
			public Object caseDeveloper(Developer object) {
				return createDeveloperAdapter();
			}
			public Object caseDevelopersType(DevelopersType object) {
				return createDevelopersTypeAdapter();
			}
			public Object caseDistributionManagement(DistributionManagement object) {
				return createDistributionManagementAdapter();
			}
			public Object caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			public Object caseExcludesType(ExcludesType object) {
				return createExcludesTypeAdapter();
			}
			public Object caseExclusion(Exclusion object) {
				return createExclusionAdapter();
			}
			public Object caseExclusionsType(ExclusionsType object) {
				return createExclusionsTypeAdapter();
			}
			public Object caseExecutionsType(ExecutionsType object) {
				return createExecutionsTypeAdapter();
			}
			public Object caseExtension(Extension object) {
				return createExtensionAdapter();
			}
			public Object caseExtensionsType(ExtensionsType object) {
				return createExtensionsTypeAdapter();
			}
			public Object caseFiltersType(FiltersType object) {
				return createFiltersTypeAdapter();
			}
			public Object caseFiltersType1(FiltersType1 object) {
				return createFiltersType1Adapter();
			}
			public Object caseGoalsType(GoalsType object) {
				return createGoalsTypeAdapter();
			}
			public Object caseGoalsType1(GoalsType1 object) {
				return createGoalsType1Adapter();
			}
			public Object caseIncludesType(IncludesType object) {
				return createIncludesTypeAdapter();
			}
			public Object caseIssueManagement(IssueManagement object) {
				return createIssueManagementAdapter();
			}
			public Object caseLicense(License object) {
				return createLicenseAdapter();
			}
			public Object caseLicensesType(LicensesType object) {
				return createLicensesTypeAdapter();
			}
			public Object caseMailingList(MailingList object) {
				return createMailingListAdapter();
			}
			public Object caseMailingListsType(MailingListsType object) {
				return createMailingListsTypeAdapter();
			}
			public Object caseModel(Model object) {
				return createModelAdapter();
			}
			public Object caseModulesType(ModulesType object) {
				return createModulesTypeAdapter();
			}
			public Object caseModulesType1(ModulesType1 object) {
				return createModulesType1Adapter();
			}
			public Object caseNotifier(org.apache.maven.pom400.Notifier object) {
				return createNotifierAdapter();
			}
			public Object caseNotifiersType(NotifiersType object) {
				return createNotifiersTypeAdapter();
			}
			public Object caseOrganization(Organization object) {
				return createOrganizationAdapter();
			}
			public Object caseOtherArchivesType(OtherArchivesType object) {
				return createOtherArchivesTypeAdapter();
			}
			public Object caseParent(Parent object) {
				return createParentAdapter();
			}
			public Object casePlugin(Plugin object) {
				return createPluginAdapter();
			}
			public Object casePluginExecution(PluginExecution object) {
				return createPluginExecutionAdapter();
			}
			public Object casePluginManagement(PluginManagement object) {
				return createPluginManagementAdapter();
			}
			public Object casePluginRepositoriesType(PluginRepositoriesType object) {
				return createPluginRepositoriesTypeAdapter();
			}
			public Object casePluginRepositoriesType1(PluginRepositoriesType1 object) {
				return createPluginRepositoriesType1Adapter();
			}
			public Object casePluginsType(PluginsType object) {
				return createPluginsTypeAdapter();
			}
			public Object casePluginsType1(PluginsType1 object) {
				return createPluginsType1Adapter();
			}
			public Object casePluginsType2(PluginsType2 object) {
				return createPluginsType2Adapter();
			}
			public Object casePluginsType3(PluginsType3 object) {
				return createPluginsType3Adapter();
			}
			public Object casePrerequisites(Prerequisites object) {
				return createPrerequisitesAdapter();
			}
			public Object caseProfile(Profile object) {
				return createProfileAdapter();
			}
			public Object caseProfilesType(ProfilesType object) {
				return createProfilesTypeAdapter();
			}
			public Object casePropertiesType(PropertiesType object) {
				return createPropertiesTypeAdapter();
			}
			public Object casePropertiesType1(PropertiesType1 object) {
				return createPropertiesType1Adapter();
			}
			public Object casePropertiesType2(PropertiesType2 object) {
				return createPropertiesType2Adapter();
			}
			public Object casePropertiesType3(PropertiesType3 object) {
				return createPropertiesType3Adapter();
			}
			public Object caseRelocation(Relocation object) {
				return createRelocationAdapter();
			}
			public Object caseReporting(Reporting object) {
				return createReportingAdapter();
			}
			public Object caseReportPlugin(ReportPlugin object) {
				return createReportPluginAdapter();
			}
			public Object caseReportSet(ReportSet object) {
				return createReportSetAdapter();
			}
			public Object caseReportSetsType(ReportSetsType object) {
				return createReportSetsTypeAdapter();
			}
			public Object caseReportsType(ReportsType object) {
				return createReportsTypeAdapter();
			}
			public Object caseReportsType1(ReportsType1 object) {
				return createReportsType1Adapter();
			}
			public Object caseReportsType2(ReportsType2 object) {
				return createReportsType2Adapter();
			}
			public Object caseRepositoriesType(RepositoriesType object) {
				return createRepositoriesTypeAdapter();
			}
			public Object caseRepositoriesType1(RepositoriesType1 object) {
				return createRepositoriesType1Adapter();
			}
			public Object caseRepository(Repository object) {
				return createRepositoryAdapter();
			}
			public Object caseRepositoryPolicy(RepositoryPolicy object) {
				return createRepositoryPolicyAdapter();
			}
			public Object caseResource(Resource object) {
				return createResourceAdapter();
			}
			public Object caseResourcesType(ResourcesType object) {
				return createResourcesTypeAdapter();
			}
			public Object caseResourcesType1(ResourcesType1 object) {
				return createResourcesType1Adapter();
			}
			public Object caseRolesType(RolesType object) {
				return createRolesTypeAdapter();
			}
			public Object caseRolesType1(RolesType1 object) {
				return createRolesType1Adapter();
			}
			public Object caseScm(Scm object) {
				return createScmAdapter();
			}
			public Object caseSite(Site object) {
				return createSiteAdapter();
			}
			public Object caseTestResourcesType(TestResourcesType object) {
				return createTestResourcesTypeAdapter();
			}
			public Object caseTestResourcesType1(TestResourcesType1 object) {
				return createTestResourcesType1Adapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Activation <em>Activation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Activation
	 * @generated
	 */
	public Adapter createActivationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ActivationFile <em>Activation File</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ActivationFile
	 * @generated
	 */
	public Adapter createActivationFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ActivationOS <em>Activation OS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ActivationOS
	 * @generated
	 */
	public Adapter createActivationOSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ActivationProperty <em>Activation Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ActivationProperty
	 * @generated
	 */
	public Adapter createActivationPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Build <em>Build</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Build
	 * @generated
	 */
	public Adapter createBuildAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.BuildBase <em>Build Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.BuildBase
	 * @generated
	 */
	public Adapter createBuildBaseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.CiManagement <em>Ci Management</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.CiManagement
	 * @generated
	 */
	public Adapter createCiManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ConfigurationType <em>Configuration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ConfigurationType
	 * @generated
	 */
	public Adapter createConfigurationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ConfigurationType1 <em>Configuration Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ConfigurationType1
	 * @generated
	 */
	public Adapter createConfigurationType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ConfigurationType2 <em>Configuration Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ConfigurationType2
	 * @generated
	 */
	public Adapter createConfigurationType2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ConfigurationType3 <em>Configuration Type3</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ConfigurationType3
	 * @generated
	 */
	public Adapter createConfigurationType3Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ConfigurationType4 <em>Configuration Type4</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ConfigurationType4
	 * @generated
	 */
	public Adapter createConfigurationType4Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Contributor <em>Contributor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Contributor
	 * @generated
	 */
	public Adapter createContributorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ContributorsType <em>Contributors Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ContributorsType
	 * @generated
	 */
	public Adapter createContributorsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DependenciesType <em>Dependencies Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DependenciesType
	 * @generated
	 */
	public Adapter createDependenciesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DependenciesType1 <em>Dependencies Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DependenciesType1
	 * @generated
	 */
	public Adapter createDependenciesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DependenciesType2 <em>Dependencies Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DependenciesType2
	 * @generated
	 */
	public Adapter createDependenciesType2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DependenciesType3 <em>Dependencies Type3</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DependenciesType3
	 * @generated
	 */
	public Adapter createDependenciesType3Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Dependency
	 * @generated
	 */
	public Adapter createDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DependencyManagement <em>Dependency Management</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DependencyManagement
	 * @generated
	 */
	public Adapter createDependencyManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DeploymentRepository <em>Deployment Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DeploymentRepository
	 * @generated
	 */
	public Adapter createDeploymentRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Developer <em>Developer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Developer
	 * @generated
	 */
	public Adapter createDeveloperAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DevelopersType <em>Developers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DevelopersType
	 * @generated
	 */
	public Adapter createDevelopersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DistributionManagement <em>Distribution Management</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DistributionManagement
	 * @generated
	 */
	public Adapter createDistributionManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ExcludesType <em>Excludes Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ExcludesType
	 * @generated
	 */
	public Adapter createExcludesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Exclusion <em>Exclusion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Exclusion
	 * @generated
	 */
	public Adapter createExclusionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ExclusionsType <em>Exclusions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ExclusionsType
	 * @generated
	 */
	public Adapter createExclusionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ExecutionsType <em>Executions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ExecutionsType
	 * @generated
	 */
	public Adapter createExecutionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Extension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Extension
	 * @generated
	 */
	public Adapter createExtensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ExtensionsType <em>Extensions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ExtensionsType
	 * @generated
	 */
	public Adapter createExtensionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.FiltersType <em>Filters Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.FiltersType
	 * @generated
	 */
	public Adapter createFiltersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.FiltersType1 <em>Filters Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.FiltersType1
	 * @generated
	 */
	public Adapter createFiltersType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.GoalsType <em>Goals Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.GoalsType
	 * @generated
	 */
	public Adapter createGoalsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.GoalsType1 <em>Goals Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.GoalsType1
	 * @generated
	 */
	public Adapter createGoalsType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.IncludesType <em>Includes Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.IncludesType
	 * @generated
	 */
	public Adapter createIncludesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.IssueManagement <em>Issue Management</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.IssueManagement
	 * @generated
	 */
	public Adapter createIssueManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.License <em>License</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.License
	 * @generated
	 */
	public Adapter createLicenseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.LicensesType <em>Licenses Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.LicensesType
	 * @generated
	 */
	public Adapter createLicensesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.MailingList <em>Mailing List</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.MailingList
	 * @generated
	 */
	public Adapter createMailingListAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.MailingListsType <em>Mailing Lists Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.MailingListsType
	 * @generated
	 */
	public Adapter createMailingListsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Model
	 * @generated
	 */
	public Adapter createModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ModulesType <em>Modules Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ModulesType
	 * @generated
	 */
	public Adapter createModulesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ModulesType1 <em>Modules Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ModulesType1
	 * @generated
	 */
	public Adapter createModulesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Notifier <em>Notifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Notifier
	 * @generated
	 */
	public Adapter createNotifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.NotifiersType <em>Notifiers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.NotifiersType
	 * @generated
	 */
	public Adapter createNotifiersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Organization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Organization
	 * @generated
	 */
	public Adapter createOrganizationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.OtherArchivesType <em>Other Archives Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.OtherArchivesType
	 * @generated
	 */
	public Adapter createOtherArchivesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Parent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Parent
	 * @generated
	 */
	public Adapter createParentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Plugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Plugin
	 * @generated
	 */
	public Adapter createPluginAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginExecution <em>Plugin Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginExecution
	 * @generated
	 */
	public Adapter createPluginExecutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginManagement <em>Plugin Management</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginManagement
	 * @generated
	 */
	public Adapter createPluginManagementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginRepositoriesType <em>Plugin Repositories Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginRepositoriesType
	 * @generated
	 */
	public Adapter createPluginRepositoriesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginRepositoriesType1 <em>Plugin Repositories Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginRepositoriesType1
	 * @generated
	 */
	public Adapter createPluginRepositoriesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginsType <em>Plugins Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginsType
	 * @generated
	 */
	public Adapter createPluginsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginsType1 <em>Plugins Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginsType1
	 * @generated
	 */
	public Adapter createPluginsType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginsType2 <em>Plugins Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginsType2
	 * @generated
	 */
	public Adapter createPluginsType2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PluginsType3 <em>Plugins Type3</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PluginsType3
	 * @generated
	 */
	public Adapter createPluginsType3Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Prerequisites <em>Prerequisites</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Prerequisites
	 * @generated
	 */
	public Adapter createPrerequisitesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Profile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Profile
	 * @generated
	 */
	public Adapter createProfileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ProfilesType <em>Profiles Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ProfilesType
	 * @generated
	 */
	public Adapter createProfilesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PropertiesType <em>Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PropertiesType
	 * @generated
	 */
	public Adapter createPropertiesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PropertiesType1 <em>Properties Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PropertiesType1
	 * @generated
	 */
	public Adapter createPropertiesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PropertiesType2 <em>Properties Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PropertiesType2
	 * @generated
	 */
	public Adapter createPropertiesType2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.PropertiesType3 <em>Properties Type3</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.PropertiesType3
	 * @generated
	 */
	public Adapter createPropertiesType3Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Relocation <em>Relocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Relocation
	 * @generated
	 */
	public Adapter createRelocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Reporting <em>Reporting</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Reporting
	 * @generated
	 */
	public Adapter createReportingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportPlugin <em>Report Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportPlugin
	 * @generated
	 */
	public Adapter createReportPluginAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportSet <em>Report Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportSet
	 * @generated
	 */
	public Adapter createReportSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportSetsType <em>Report Sets Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportSetsType
	 * @generated
	 */
	public Adapter createReportSetsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportsType <em>Reports Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportsType
	 * @generated
	 */
	public Adapter createReportsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportsType1 <em>Reports Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportsType1
	 * @generated
	 */
	public Adapter createReportsType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ReportsType2 <em>Reports Type2</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ReportsType2
	 * @generated
	 */
	public Adapter createReportsType2Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.RepositoriesType <em>Repositories Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.RepositoriesType
	 * @generated
	 */
	public Adapter createRepositoriesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.RepositoriesType1 <em>Repositories Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.RepositoriesType1
	 * @generated
	 */
	public Adapter createRepositoriesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Repository
	 * @generated
	 */
	public Adapter createRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.RepositoryPolicy <em>Repository Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.RepositoryPolicy
	 * @generated
	 */
	public Adapter createRepositoryPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Resource
	 * @generated
	 */
	public Adapter createResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ResourcesType <em>Resources Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ResourcesType
	 * @generated
	 */
	public Adapter createResourcesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.ResourcesType1 <em>Resources Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.ResourcesType1
	 * @generated
	 */
	public Adapter createResourcesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.RolesType <em>Roles Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.RolesType
	 * @generated
	 */
	public Adapter createRolesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.RolesType1 <em>Roles Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.RolesType1
	 * @generated
	 */
	public Adapter createRolesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Scm <em>Scm</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Scm
	 * @generated
	 */
	public Adapter createScmAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.Site <em>Site</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.Site
	 * @generated
	 */
	public Adapter createSiteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.TestResourcesType <em>Test Resources Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.TestResourcesType
	 * @generated
	 */
	public Adapter createTestResourcesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.apache.maven.pom400.TestResourcesType1 <em>Test Resources Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.apache.maven.pom400.TestResourcesType1
	 * @generated
	 */
	public Adapter createTestResourcesType1Adapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //mavenAdapterFactory
