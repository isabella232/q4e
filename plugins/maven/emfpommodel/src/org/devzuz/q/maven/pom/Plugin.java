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
 * A representation of the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 4.0.0
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getGroupId <em>Group Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getArtifactId <em>Artifact Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getVersion <em>Version</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#isExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getGoals <em>Goals</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getInherited <em>Inherited</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Plugin#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin()
 * @model extendedMetaData="name='Plugin' kind='elementOnly'"
 * @generated
 */
public interface Plugin extends EObject
{
    /**
	 * Returns the value of the '<em><b>Group Id</b></em>' attribute.
	 * The default value is <code>"org.apache.maven.plugins"</code>.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * The group ID of the plugin in the repository.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Group Id</em>' attribute.
	 * @see #isSetGroupId()
	 * @see #unsetGroupId()
	 * @see #setGroupId(String)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_GroupId()
	 * @model default="org.apache.maven.plugins" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='groupId' namespace='##targetNamespace'"
	 * @generated
	 */
    String getGroupId();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGroupId <em>Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Id</em>' attribute.
	 * @see #isSetGroupId()
	 * @see #unsetGroupId()
	 * @see #getGroupId()
	 * @generated
	 */
    void setGroupId(String value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGroupId <em>Group Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetGroupId()
	 * @see #getGroupId()
	 * @see #setGroupId(String)
	 * @generated
	 */
    void unsetGroupId();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGroupId <em>Group Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Group Id</em>' attribute is set.
	 * @see #unsetGroupId()
	 * @see #getGroupId()
	 * @see #setGroupId(String)
	 * @generated
	 */
    boolean isSetGroupId();

    /**
	 * Returns the value of the '<em><b>Artifact Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * The artifact ID of the plugin in the repository.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Artifact Id</em>' attribute.
	 * @see #setArtifactId(String)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_ArtifactId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='artifactId' namespace='##targetNamespace'"
	 * @generated
	 */
    String getArtifactId();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getArtifactId <em>Artifact Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact Id</em>' attribute.
	 * @see #getArtifactId()
	 * @generated
	 */
    void setArtifactId(String value);

    /**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * The version (or valid range of verisons) of the plugin to be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
    String getVersion();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
    void setVersion(String value);

    /**
	 * Returns the value of the '<em><b>Extensions</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * Whether to load Maven extensions (such as packaging and type handlers) from this
	 *             plugin. For performance reasons, this should only be enabled when necessary.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extensions</em>' attribute.
	 * @see #isSetExtensions()
	 * @see #unsetExtensions()
	 * @see #setExtensions(boolean)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Extensions()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='extensions' namespace='##targetNamespace'"
	 * @generated
	 */
    boolean isExtensions();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#isExtensions <em>Extensions</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' attribute.
	 * @see #isSetExtensions()
	 * @see #unsetExtensions()
	 * @see #isExtensions()
	 * @generated
	 */
    void setExtensions(boolean value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#isExtensions <em>Extensions</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetExtensions()
	 * @see #isExtensions()
	 * @see #setExtensions(boolean)
	 * @generated
	 */
    void unsetExtensions();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#isExtensions <em>Extensions</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Extensions</em>' attribute is set.
	 * @see #unsetExtensions()
	 * @see #isExtensions()
	 * @see #setExtensions(boolean)
	 * @generated
	 */
    boolean isSetExtensions();

    /**
	 * Returns the value of the '<em><b>Executions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * Multiple specifications of a set of goals to execute during the build lifecycle, each having
	 *             (possibly) different
	 *             configuration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Executions</em>' containment reference.
	 * @see #isSetExecutions()
	 * @see #unsetExecutions()
	 * @see #setExecutions(ExecutionsType)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Executions()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='executions' namespace='##targetNamespace'"
	 * @generated
	 */
    ExecutionsType getExecutions();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getExecutions <em>Executions</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Executions</em>' containment reference.
	 * @see #isSetExecutions()
	 * @see #unsetExecutions()
	 * @see #getExecutions()
	 * @generated
	 */
    void setExecutions(ExecutionsType value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getExecutions <em>Executions</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetExecutions()
	 * @see #getExecutions()
	 * @see #setExecutions(ExecutionsType)
	 * @generated
	 */
    void unsetExecutions();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#getExecutions <em>Executions</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Executions</em>' containment reference is set.
	 * @see #unsetExecutions()
	 * @see #getExecutions()
	 * @see #setExecutions(ExecutionsType)
	 * @generated
	 */
    boolean isSetExecutions();

    /**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * Additional dependencies that this project needs to introduce to the plugin's
	 *             classloader.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference.
	 * @see #isSetDependencies()
	 * @see #unsetDependencies()
	 * @see #setDependencies(DependenciesType3)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Dependencies()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='dependencies' namespace='##targetNamespace'"
	 * @generated
	 */
    DependenciesType3 getDependencies();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getDependencies <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependencies</em>' containment reference.
	 * @see #isSetDependencies()
	 * @see #unsetDependencies()
	 * @see #getDependencies()
	 * @generated
	 */
    void setDependencies(DependenciesType3 value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getDependencies <em>Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetDependencies()
	 * @see #getDependencies()
	 * @see #setDependencies(DependenciesType3)
	 * @generated
	 */
    void unsetDependencies();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#getDependencies <em>Dependencies</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Dependencies</em>' containment reference is set.
	 * @see #unsetDependencies()
	 * @see #getDependencies()
	 * @see #setDependencies(DependenciesType3)
	 * @generated
	 */
    boolean isSetDependencies();

    /**
	 * Returns the value of the '<em><b>Goals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * 
	 *             &lt;b&gt;Deprecated&lt;/b&gt;. Unused by Maven.
	 *           
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Goals</em>' containment reference.
	 * @see #isSetGoals()
	 * @see #unsetGoals()
	 * @see #setGoals(GoalsType)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Goals()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='goals' namespace='##targetNamespace'"
	 * @generated
	 */
    GoalsType getGoals();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGoals <em>Goals</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goals</em>' containment reference.
	 * @see #isSetGoals()
	 * @see #unsetGoals()
	 * @see #getGoals()
	 * @generated
	 */
    void setGoals(GoalsType value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGoals <em>Goals</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetGoals()
	 * @see #getGoals()
	 * @see #setGoals(GoalsType)
	 * @generated
	 */
    void unsetGoals();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#getGoals <em>Goals</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Goals</em>' containment reference is set.
	 * @see #unsetGoals()
	 * @see #getGoals()
	 * @see #setGoals(GoalsType)
	 * @generated
	 */
    boolean isSetGoals();

    /**
	 * Returns the value of the '<em><b>Inherited</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * 
	 *             Whether any configuration should be propagated to child POMs.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Inherited</em>' attribute.
	 * @see #setInherited(String)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Inherited()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='inherited' namespace='##targetNamespace'"
	 * @generated
	 */
    String getInherited();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getInherited <em>Inherited</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inherited</em>' attribute.
	 * @see #getInherited()
	 * @generated
	 */
    void setInherited(String value);

    /**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 0.0.0+
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Configuration</em>' containment reference.
	 * @see #isSetConfiguration()
	 * @see #unsetConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @see org.devzuz.q.maven.pom.PomPackage#getPlugin_Configuration()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='configuration' namespace='##targetNamespace'"
	 * @generated
	 */
    Configuration getConfiguration();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getConfiguration <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' containment reference.
	 * @see #isSetConfiguration()
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(Configuration value);

				/**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.Plugin#getConfiguration <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @generated
	 */
    void unsetConfiguration();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Plugin#getConfiguration <em>Configuration</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Configuration</em>' containment reference is set.
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @generated
	 */
    boolean isSetConfiguration();

} // Plugin
