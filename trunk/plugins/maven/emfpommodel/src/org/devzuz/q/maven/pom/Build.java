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
package org.devzuz.q.maven.pom;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Build</b></em>'. <!-- end-user-doc --> <!--
 * begin-model-doc --> 3.0.0+ <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.devzuz.q.maven.pom.Build#getSourceDirectory <em>Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getScriptSourceDirectory <em>Script Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getTestSourceDirectory <em>Test Source Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getOutputDirectory <em>Output Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getTestOutputDirectory <em>Test Output Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getExtensions <em>Extensions</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getDefaultGoal <em>Default Goal</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getResources <em>Resources</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getTestResources <em>Test Resources</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getDirectory <em>Directory</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getFinalName <em>Final Name</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getPluginManagement <em>Plugin Management</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getPlugins <em>Plugins</em>}</li>
 * <li>{@link org.devzuz.q.maven.pom.Build#getFilters <em>Filters</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.devzuz.q.maven.pom.PomPackage#getBuild()
 * @model extendedMetaData="name='Build' kind='elementOnly'"
 * @generated
 */
public interface Build
    extends EObject
{
    /**
     * Returns the value of the '<em><b>Source Directory</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> 3.0.0+ This element specifies a directory containing the source of the
     * project. The generated build system will compile the source in this directory when the project is built. The path
     * given is relative to the project descriptor. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Source Directory</em>' attribute.
     * @see #setSourceDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_SourceDirectory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='sourceDirectory'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getSourceDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getSourceDirectory <em>Source Directory</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Source Directory</em>' attribute.
     * @see #getSourceDirectory()
     * @generated
     */
    void setSourceDirectory( String value );

    /**
     * Returns the value of the '<em><b>Script Source Directory</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> 4.0.0 This element specifies a directory containing the script sources
     * of the project. This directory is meant to be different from the sourceDirectory, in that its contents will be
     * copied to the output directory in most cases (since scripts are interpreted rather than compiled). <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Script Source Directory</em>' attribute.
     * @see #setScriptSourceDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_ScriptSourceDirectory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element'
     *        name='scriptSourceDirectory' namespace='##targetNamespace'"
     * @generated
     */
    String getScriptSourceDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getScriptSourceDirectory <em>Script Source Directory</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Script Source Directory</em>' attribute.
     * @see #getScriptSourceDirectory()
     * @generated
     */
    void setScriptSourceDirectory( String value );

    /**
     * Returns the value of the '<em><b>Test Source Directory</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> 4.0.0 This element specifies a directory containing the unit test
     * source of the project. The generated build system will compile these directories when the project is being
     * tested. The path given is relative to the project descriptor. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Test Source Directory</em>' attribute.
     * @see #setTestSourceDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_TestSourceDirectory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element'
     *        name='testSourceDirectory' namespace='##targetNamespace'"
     * @generated
     */
    String getTestSourceDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getTestSourceDirectory <em>Test Source Directory</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Test Source Directory</em>' attribute.
     * @see #getTestSourceDirectory()
     * @generated
     */
    void setTestSourceDirectory( String value );

    /**
     * Returns the value of the '<em><b>Output Directory</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> 4.0.0 The directory where compiled application classes are placed. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Output Directory</em>' attribute.
     * @see #setOutputDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_OutputDirectory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='outputDirectory'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getOutputDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getOutputDirectory <em>Output Directory</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Output Directory</em>' attribute.
     * @see #getOutputDirectory()
     * @generated
     */
    void setOutputDirectory( String value );

    /**
     * Returns the value of the '<em><b>Test Output Directory</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc --> <!-- begin-model-doc --> 4.0.0 The directory where compiled test classes are placed. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Test Output Directory</em>' attribute.
     * @see #setTestOutputDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_TestOutputDirectory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element'
     *        name='testOutputDirectory' namespace='##targetNamespace'"
     * @generated
     */
    String getTestOutputDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getTestOutputDirectory <em>Test Output Directory</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Test Output Directory</em>' attribute.
     * @see #getTestOutputDirectory()
     * @generated
     */
    void setTestOutputDirectory( String value );

    /**
     * Returns the value of the '<em><b>Extensions</b></em>' containment reference list. The list contents are of
     * type {@link org.devzuz.q.maven.pom.Extension}. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> 4.0.0 A set of build extensions to use from this project. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Extensions</em>' containment reference list.
     * @see #isSetExtensions()
     * @see #unsetExtensions()
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_Extensions()
     * @model containment="true" unsettable="true" extendedMetaData="kind='element' name='extensions'
     *        namespace='##targetNamespace'"
     * @generated
     */
    EList<Extension> getExtensions();

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Build#getExtensions <em>Extensions</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetExtensions()
     * @see #getExtensions()
     * @generated
     */
    void unsetExtensions();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Build#getExtensions <em>Extensions</em>}'
     * containment reference list is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Extensions</em>' containment reference list is set.
     * @see #unsetExtensions()
     * @see #getExtensions()
     * @generated
     */
    boolean isSetExtensions();

    /**
     * Returns the value of the '<em><b>Default Goal</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * --> <!-- begin-model-doc --> 3.0.0+ The default goal (or phase in Maven 2) to execute when none is specified for
     * the project. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Default Goal</em>' attribute.
     * @see #setDefaultGoal(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_DefaultGoal()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='defaultGoal'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getDefaultGoal();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getDefaultGoal <em>Default Goal</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Default Goal</em>' attribute.
     * @see #getDefaultGoal()
     * @generated
     */
    void setDefaultGoal( String value );

    /**
     * Returns the value of the '<em><b>Resources</b></em>' containment reference list. The list contents are of
     * type {@link org.devzuz.q.maven.pom.Resource}. <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc
     * --> 3.0.0+ This element describes all of the classpath resources such as properties files associated with a
     * project. These resources are often included in the final package. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Resources</em>' containment reference list.
     * @see #isSetResources()
     * @see #unsetResources()
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_Resources()
     * @model containment="true" unsettable="true" extendedMetaData="kind='element' name='resources'
     *        namespace='##targetNamespace'"
     * @generated
     */
    EList<Resource> getResources();

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Build#getResources <em>Resources</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetResources()
     * @see #getResources()
     * @generated
     */
    void unsetResources();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Build#getResources <em>Resources</em>}'
     * containment reference list is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Resources</em>' containment reference list is set.
     * @see #unsetResources()
     * @see #getResources()
     * @generated
     */
    boolean isSetResources();

    /**
     * Returns the value of the '<em><b>Test Resources</b></em>' containment reference list. The list contents are
     * of type {@link org.devzuz.q.maven.pom.Resource}. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> 4.0.0 This element describes all of the classpath resources such as properties files
     * associated with a project's unit tests. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Test Resources</em>' containment reference list.
     * @see #isSetTestResources()
     * @see #unsetTestResources()
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_TestResources()
     * @model containment="true" unsettable="true" extendedMetaData="kind='element' name='testResources'
     *        namespace='##targetNamespace'"
     * @generated
     */
    EList<Resource> getTestResources();

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Build#getTestResources <em>Test Resources</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetTestResources()
     * @see #getTestResources()
     * @generated
     */
    void unsetTestResources();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Build#getTestResources <em>Test Resources</em>}'
     * containment reference list is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Test Resources</em>' containment reference list is set.
     * @see #unsetTestResources()
     * @see #getTestResources()
     * @generated
     */
    boolean isSetTestResources();

    /**
     * Returns the value of the '<em><b>Directory</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * --> <!-- begin-model-doc --> 4.0.0 The directory where all files generated by the build are placed. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Directory</em>' attribute.
     * @see #setDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_Directory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='directory'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getDirectory <em>Directory</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Directory</em>' attribute.
     * @see #getDirectory()
     * @generated
     */
    void setDirectory( String value );

    /**
     * Returns the value of the '<em><b>Final Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * --> <!-- begin-model-doc --> 4.0.0 The filename (excluding the extension, and with no path information) that the
     * produced artifact will be called. The default value is &lt;code&gt;${artifactId}-${version}&lt;/code&gt;. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Final Name</em>' attribute.
     * @see #setFinalName(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_FinalName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='element' name='finalName'
     *        namespace='##targetNamespace'"
     * @generated
     */
    String getFinalName();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getFinalName <em>Final Name</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Final Name</em>' attribute.
     * @see #getFinalName()
     * @generated
     */
    void setFinalName( String value );

    /**
     * Returns the value of the '<em><b>Plugin Management</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc --> <!-- begin-model-doc --> 4.0.0 Default plugin information to be made available for
     * reference by projects derived from this one. This plugin configuration will not be resolved or bound to the
     * lifecycle unless referenced. Any local configuration for a given plugin will override the plugin's entire
     * definition here. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Plugin Management</em>' containment reference.
     * @see #isSetPluginManagement()
     * @see #unsetPluginManagement()
     * @see #setPluginManagement(PluginManagement)
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_PluginManagement()
     * @model containment="true" unsettable="true" extendedMetaData="kind='element' name='pluginManagement'
     *        namespace='##targetNamespace'"
     * @generated
     */
    PluginManagement getPluginManagement();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Build#getPluginManagement <em>Plugin Management</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Plugin Management</em>' containment reference.
     * @see #isSetPluginManagement()
     * @see #unsetPluginManagement()
     * @see #getPluginManagement()
     * @generated
     */
    void setPluginManagement( PluginManagement value );

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Build#getPluginManagement <em>Plugin Management</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetPluginManagement()
     * @see #getPluginManagement()
     * @see #setPluginManagement(PluginManagement)
     * @generated
     */
    void unsetPluginManagement();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Build#getPluginManagement <em>Plugin Management</em>}'
     * containment reference is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Plugin Management</em>' containment reference is set.
     * @see #unsetPluginManagement()
     * @see #getPluginManagement()
     * @see #setPluginManagement(PluginManagement)
     * @generated
     */
    boolean isSetPluginManagement();

    /**
     * Returns the value of the '<em><b>Plugins</b></em>' containment reference list. The list contents are of type
     * {@link org.devzuz.q.maven.pom.Plugin}. <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * 4.0.0 The list of plugins to use. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Plugins</em>' containment reference list.
     * @see #isSetPlugins()
     * @see #unsetPlugins()
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_Plugins()
     * @model containment="true" unsettable="true" extendedMetaData="kind='element' name='plugins'
     *        namespace='##targetNamespace'"
     * @generated
     */
    EList<Plugin> getPlugins();

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Build#getPlugins <em>Plugins</em>}' containment
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isSetPlugins()
     * @see #getPlugins()
     * @generated
     */
    void unsetPlugins();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Build#getPlugins <em>Plugins</em>}' containment
     * reference list is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return whether the value of the '<em>Plugins</em>' containment reference list is set.
     * @see #unsetPlugins()
     * @see #getPlugins()
     * @generated
     */
    boolean isSetPlugins();

    /**
     * Returns the value of the '<em><b>Filters</b></em>' attribute list. The list contents are of type
     * {@link java.lang.String}. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filters</em>' attribute list isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Filters</em>' attribute list.
     * @see org.devzuz.q.maven.pom.PomPackage#getBuild_Filters()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<String> getFilters();

} // Build
