/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 4.0.0
 * 
 *         Modifications to the build process which is activated based on environmental parameters or command line arguments.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getId <em>Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getActivation <em>Activation</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getBuild <em>Build</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getModules <em>Modules</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getRepositories <em>Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getPluginRepositories <em>Plugin Repositories</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getReports <em>Reports</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getReporting <em>Reporting</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getDependencyManagement <em>Dependency Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getDistributionManagement <em>Distribution Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Profile#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getProfile()
 * @model extendedMetaData="name='Profile' kind='elementOnly'"
 * @generated
 */
public interface Profile extends EObject
{
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * The identifier of this build profile. This used both for command line activation, and identifies
     *             identical profiles to merge with during inheritance.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Id()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='id' namespace='##targetNamespace'"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Activation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * The conditional logic which will automatically
     *             trigger the inclusion of this profile.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Activation</em>' containment reference.
     * @see #isSetActivation()
     * @see #unsetActivation()
     * @see #setActivation(Activation)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Activation()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='activation' namespace='##targetNamespace'"
     * @generated
     */
    Activation getActivation();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getActivation <em>Activation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activation</em>' containment reference.
     * @see #isSetActivation()
     * @see #unsetActivation()
     * @see #getActivation()
     * @generated
     */
    void setActivation(Activation value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getActivation <em>Activation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetActivation()
     * @see #getActivation()
     * @see #setActivation(Activation)
     * @generated
     */
    void unsetActivation();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getActivation <em>Activation</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Activation</em>' containment reference is set.
     * @see #unsetActivation()
     * @see #getActivation()
     * @see #setActivation(Activation)
     * @generated
     */
    boolean isSetActivation();

    /**
     * Returns the value of the '<em><b>Build</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * Information required to build the project.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Build</em>' containment reference.
     * @see #isSetBuild()
     * @see #unsetBuild()
     * @see #setBuild(BuildBase)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Build()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='build' namespace='##targetNamespace'"
     * @generated
     */
    BuildBase getBuild();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getBuild <em>Build</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Build</em>' containment reference.
     * @see #isSetBuild()
     * @see #unsetBuild()
     * @see #getBuild()
     * @generated
     */
    void setBuild(BuildBase value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getBuild <em>Build</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBuild()
     * @see #getBuild()
     * @see #setBuild(BuildBase)
     * @generated
     */
    void unsetBuild();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getBuild <em>Build</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Build</em>' containment reference is set.
     * @see #unsetBuild()
     * @see #getBuild()
     * @see #setBuild(BuildBase)
     * @generated
     */
    boolean isSetBuild();

    /**
     * Returns the value of the '<em><b>Modules</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             The modules (sometimes called subprojects) to build as a part of this project.
     *             Each module listed is a relative path to the directory containing the module.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Modules</em>' containment reference.
     * @see #isSetModules()
     * @see #unsetModules()
     * @see #setModules(ModulesType1)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Modules()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='modules' namespace='##targetNamespace'"
     * @generated
     */
    ModulesType1 getModules();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getModules <em>Modules</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Modules</em>' containment reference.
     * @see #isSetModules()
     * @see #unsetModules()
     * @see #getModules()
     * @generated
     */
    void setModules(ModulesType1 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getModules <em>Modules</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetModules()
     * @see #getModules()
     * @see #setModules(ModulesType1)
     * @generated
     */
    void unsetModules();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getModules <em>Modules</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Modules</em>' containment reference is set.
     * @see #unsetModules()
     * @see #getModules()
     * @see #setModules(ModulesType1)
     * @generated
     */
    boolean isSetModules();

    /**
     * Returns the value of the '<em><b>Repositories</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * The lists of the remote repositories for discovering dependencies and
     *           extensions.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Repositories</em>' containment reference.
     * @see #isSetRepositories()
     * @see #unsetRepositories()
     * @see #setRepositories(RepositoriesType1)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Repositories()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='repositories' namespace='##targetNamespace'"
     * @generated
     */
    RepositoriesType1 getRepositories();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getRepositories <em>Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repositories</em>' containment reference.
     * @see #isSetRepositories()
     * @see #unsetRepositories()
     * @see #getRepositories()
     * @generated
     */
    void setRepositories(RepositoriesType1 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getRepositories <em>Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetRepositories()
     * @see #getRepositories()
     * @see #setRepositories(RepositoriesType1)
     * @generated
     */
    void unsetRepositories();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getRepositories <em>Repositories</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Repositories</em>' containment reference is set.
     * @see #unsetRepositories()
     * @see #getRepositories()
     * @see #setRepositories(RepositoriesType1)
     * @generated
     */
    boolean isSetRepositories();

    /**
     * Returns the value of the '<em><b>Plugin Repositories</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             The lists of the remote repositories for discovering plugins for builds and reports.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Plugin Repositories</em>' containment reference.
     * @see #isSetPluginRepositories()
     * @see #unsetPluginRepositories()
     * @see #setPluginRepositories(PluginRepositoriesType1)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_PluginRepositories()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='pluginRepositories' namespace='##targetNamespace'"
     * @generated
     */
    PluginRepositoriesType1 getPluginRepositories();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getPluginRepositories <em>Plugin Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugin Repositories</em>' containment reference.
     * @see #isSetPluginRepositories()
     * @see #unsetPluginRepositories()
     * @see #getPluginRepositories()
     * @generated
     */
    void setPluginRepositories(PluginRepositoriesType1 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getPluginRepositories <em>Plugin Repositories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPluginRepositories()
     * @see #getPluginRepositories()
     * @see #setPluginRepositories(PluginRepositoriesType1)
     * @generated
     */
    void unsetPluginRepositories();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getPluginRepositories <em>Plugin Repositories</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Plugin Repositories</em>' containment reference is set.
     * @see #unsetPluginRepositories()
     * @see #getPluginRepositories()
     * @see #setPluginRepositories(PluginRepositoriesType1)
     * @generated
     */
    boolean isSetPluginRepositories();

    /**
     * Returns the value of the '<em><b>Dependencies</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *               This element describes all of the dependencies associated with a
     *               project.
     *               These dependencies are used to construct a classpath for your 
     *               project during the build process. They are automatically downloaded from the
     *               repositories defined in this project.
     *               See &lt;a href="http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html"&gt;the
     *               dependency mechanism&lt;/a&gt; for more information.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Dependencies</em>' containment reference.
     * @see #isSetDependencies()
     * @see #unsetDependencies()
     * @see #setDependencies(DependenciesType2)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Dependencies()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='dependencies' namespace='##targetNamespace'"
     * @generated
     */
    DependenciesType2 getDependencies();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencies <em>Dependencies</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dependencies</em>' containment reference.
     * @see #isSetDependencies()
     * @see #unsetDependencies()
     * @see #getDependencies()
     * @generated
     */
    void setDependencies(DependenciesType2 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencies <em>Dependencies</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDependencies()
     * @see #getDependencies()
     * @see #setDependencies(DependenciesType2)
     * @generated
     */
    void unsetDependencies();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencies <em>Dependencies</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Dependencies</em>' containment reference is set.
     * @see #unsetDependencies()
     * @see #getDependencies()
     * @see #setDependencies(DependenciesType2)
     * @generated
     */
    boolean isSetDependencies();

    /**
     * Returns the value of the '<em><b>Reports</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             &lt;b&gt;Deprecated&lt;/b&gt;. Now ignored by Maven.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reports</em>' containment reference.
     * @see #isSetReports()
     * @see #unsetReports()
     * @see #setReports(ReportsType2)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Reports()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='reports' namespace='##targetNamespace'"
     * @generated
     */
    ReportsType2 getReports();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getReports <em>Reports</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reports</em>' containment reference.
     * @see #isSetReports()
     * @see #unsetReports()
     * @see #getReports()
     * @generated
     */
    void setReports(ReportsType2 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getReports <em>Reports</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReports()
     * @see #getReports()
     * @see #setReports(ReportsType2)
     * @generated
     */
    void unsetReports();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getReports <em>Reports</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Reports</em>' containment reference is set.
     * @see #unsetReports()
     * @see #getReports()
     * @see #setReports(ReportsType2)
     * @generated
     */
    boolean isSetReports();

    /**
     * Returns the value of the '<em><b>Reporting</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             This element includes the specification of report plugins to use to generate the reports on the
     *             Maven-generated site.  These reports will be run when a user executes &lt;code&gt;mvn site&lt;/code&gt;.  All of the
     *             reports will be included in the navigation bar for browsing.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Reporting</em>' containment reference.
     * @see #isSetReporting()
     * @see #unsetReporting()
     * @see #setReporting(Reporting)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Reporting()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='reporting' namespace='##targetNamespace'"
     * @generated
     */
    Reporting getReporting();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getReporting <em>Reporting</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reporting</em>' containment reference.
     * @see #isSetReporting()
     * @see #unsetReporting()
     * @see #getReporting()
     * @generated
     */
    void setReporting(Reporting value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getReporting <em>Reporting</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReporting()
     * @see #getReporting()
     * @see #setReporting(Reporting)
     * @generated
     */
    void unsetReporting();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getReporting <em>Reporting</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Reporting</em>' containment reference is set.
     * @see #unsetReporting()
     * @see #getReporting()
     * @see #setReporting(Reporting)
     * @generated
     */
    boolean isSetReporting();

    /**
     * Returns the value of the '<em><b>Dependency Management</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *             Default dependency information for projects that inherit from
     *             this one. The dependencies in this section are not immediately resolved.
     *             Instead, when a POM derived from this one declares a dependency 
     *             described by a matching groupId and artifactId, the version and other values from this
     *             section are used for that dependency if they were not already specified.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Dependency Management</em>' containment reference.
     * @see #isSetDependencyManagement()
     * @see #unsetDependencyManagement()
     * @see #setDependencyManagement(DependencyManagement)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_DependencyManagement()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='dependencyManagement' namespace='##targetNamespace'"
     * @generated
     */
    DependencyManagement getDependencyManagement();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencyManagement <em>Dependency Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Dependency Management</em>' containment reference.
     * @see #isSetDependencyManagement()
     * @see #unsetDependencyManagement()
     * @see #getDependencyManagement()
     * @generated
     */
    void setDependencyManagement(DependencyManagement value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencyManagement <em>Dependency Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDependencyManagement()
     * @see #getDependencyManagement()
     * @see #setDependencyManagement(DependencyManagement)
     * @generated
     */
    void unsetDependencyManagement();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getDependencyManagement <em>Dependency Management</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Dependency Management</em>' containment reference is set.
     * @see #unsetDependencyManagement()
     * @see #getDependencyManagement()
     * @see #setDependencyManagement(DependencyManagement)
     * @generated
     */
    boolean isSetDependencyManagement();

    /**
     * Returns the value of the '<em><b>Distribution Management</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * Distribution information for a project that enables deployment of the site
     *           and artifacts to remote web servers and repositories respectively.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Distribution Management</em>' containment reference.
     * @see #isSetDistributionManagement()
     * @see #unsetDistributionManagement()
     * @see #setDistributionManagement(DistributionManagement)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_DistributionManagement()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='distributionManagement' namespace='##targetNamespace'"
     * @generated
     */
    DistributionManagement getDistributionManagement();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDistributionManagement <em>Distribution Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution Management</em>' containment reference.
     * @see #isSetDistributionManagement()
     * @see #unsetDistributionManagement()
     * @see #getDistributionManagement()
     * @generated
     */
    void setDistributionManagement(DistributionManagement value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getDistributionManagement <em>Distribution Management</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDistributionManagement()
     * @see #getDistributionManagement()
     * @see #setDistributionManagement(DistributionManagement)
     * @generated
     */
    void unsetDistributionManagement();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getDistributionManagement <em>Distribution Management</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Distribution Management</em>' containment reference is set.
     * @see #unsetDistributionManagement()
     * @see #getDistributionManagement()
     * @see #setDistributionManagement(DistributionManagement)
     * @generated
     */
    boolean isSetDistributionManagement();

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 4.0.0
     * 
     *              Properties that can be used throughout the POM as a substitution, and are used as filters in resources
     *              if enabled. The format is &lt;code&gt;&amp;lt;name&amp;gt;value&amp;lt;/name&amp;gt;&lt;/code&gt;.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Properties</em>' containment reference.
     * @see #isSetProperties()
     * @see #unsetProperties()
     * @see #setProperties(PropertiesType1)
     * @see org.devzuz.q.maven.pom.PomPackage#getProfile_Properties()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
     * @generated
     */
    PropertiesType1 getProperties();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Profile#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Properties</em>' containment reference.
     * @see #isSetProperties()
     * @see #unsetProperties()
     * @see #getProperties()
     * @generated
     */
    void setProperties(PropertiesType1 value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Profile#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetProperties()
     * @see #getProperties()
     * @see #setProperties(PropertiesType1)
     * @generated
     */
    void unsetProperties();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Profile#getProperties <em>Properties</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Properties</em>' containment reference is set.
     * @see #unsetProperties()
     * @see #getProperties()
     * @see #setProperties(PropertiesType1)
     * @generated
     */
    boolean isSetProperties();

} // Profile
