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
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 3.0.0+
 * 
 *         This element describes all of the classpath resources associated with a project or
 *         unit tests.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.Resource#getTargetPath <em>Target Path</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Resource#isFiltering <em>Filtering</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Resource#getDirectory <em>Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Resource#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.Resource#getExcludes <em>Excludes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getResource()
 * @model extendedMetaData="name='Resource' kind='elementOnly'"
 * @generated
 */
public interface Resource extends EObject
{
    /**
     * Returns the value of the '<em><b>Target Path</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             Describe the resource target path. For example, if you want that 
     *             resource to appear in a specific package
     *             (&lt;code&gt;org.apache.maven.messages&lt;/code&gt;), you must specify this 
     *             element with this value: &lt;code&gt;org/apache/maven/messages&lt;/code&gt;.
     *             This is not required if you simply put the resources in that directory structure at the source, however.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Path</em>' attribute.
     * @see #setTargetPath(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getResource_TargetPath()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='targetPath' namespace='##targetNamespace'"
     * @generated
     */
    String getTargetPath();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Resource#getTargetPath <em>Target Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Path</em>' attribute.
     * @see #getTargetPath()
     * @generated
     */
    void setTargetPath(String value);

    /**
     * Returns the value of the '<em><b>Filtering</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             Whether resources are filtered to replace tokens with parameterised values or not.
     *             The values are taken from the &lt;code&gt;properties&lt;/code&gt; element and from the properties in the files listed
     *             in the &lt;code&gt;filters&lt;/code&gt; element.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Filtering</em>' attribute.
     * @see #isSetFiltering()
     * @see #unsetFiltering()
     * @see #setFiltering(boolean)
     * @see org.devzuz.q.maven.pom.PomPackage#getResource_Filtering()
     * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='element' name='filtering' namespace='##targetNamespace'"
     * @generated
     */
    boolean isFiltering();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Resource#isFiltering <em>Filtering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Filtering</em>' attribute.
     * @see #isSetFiltering()
     * @see #unsetFiltering()
     * @see #isFiltering()
     * @generated
     */
    void setFiltering(boolean value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Resource#isFiltering <em>Filtering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetFiltering()
     * @see #isFiltering()
     * @see #setFiltering(boolean)
     * @generated
     */
    void unsetFiltering();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Resource#isFiltering <em>Filtering</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Filtering</em>' attribute is set.
     * @see #unsetFiltering()
     * @see #isFiltering()
     * @see #setFiltering(boolean)
     * @generated
     */
    boolean isSetFiltering();

    /**
     * Returns the value of the '<em><b>Directory</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * 
     *             Describe the directory where the resources are stored.
     *             The path is relative to the POM.
     *           
     * <!-- end-model-doc -->
     * @return the value of the '<em>Directory</em>' attribute.
     * @see #setDirectory(String)
     * @see org.devzuz.q.maven.pom.PomPackage#getResource_Directory()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='directory' namespace='##targetNamespace'"
     * @generated
     */
    String getDirectory();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Resource#getDirectory <em>Directory</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Directory</em>' attribute.
     * @see #getDirectory()
     * @generated
     */
    void setDirectory(String value);

    /**
     * Returns the value of the '<em><b>Includes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * A list of patterns to include, e.g. &lt;code&gt;**&amp;#47;*.xml&lt;/code&gt;.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Includes</em>' containment reference.
     * @see #isSetIncludes()
     * @see #unsetIncludes()
     * @see #setIncludes(IncludesType)
     * @see org.devzuz.q.maven.pom.PomPackage#getResource_Includes()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='includes' namespace='##targetNamespace'"
     * @generated
     */
    IncludesType getIncludes();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Resource#getIncludes <em>Includes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Includes</em>' containment reference.
     * @see #isSetIncludes()
     * @see #unsetIncludes()
     * @see #getIncludes()
     * @generated
     */
    void setIncludes(IncludesType value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Resource#getIncludes <em>Includes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIncludes()
     * @see #getIncludes()
     * @see #setIncludes(IncludesType)
     * @generated
     */
    void unsetIncludes();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Resource#getIncludes <em>Includes</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Includes</em>' containment reference is set.
     * @see #unsetIncludes()
     * @see #getIncludes()
     * @see #setIncludes(IncludesType)
     * @generated
     */
    boolean isSetIncludes();

    /**
     * Returns the value of the '<em><b>Excludes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 3.0.0+
     * A list of patterns to exclude, e.g. &lt;code&gt;**&amp;#47;*.xml&lt;/code&gt;
     * <!-- end-model-doc -->
     * @return the value of the '<em>Excludes</em>' containment reference.
     * @see #isSetExcludes()
     * @see #unsetExcludes()
     * @see #setExcludes(ExcludesType)
     * @see org.devzuz.q.maven.pom.PomPackage#getResource_Excludes()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='excludes' namespace='##targetNamespace'"
     * @generated
     */
    ExcludesType getExcludes();

    /**
     * Sets the value of the '{@link org.devzuz.q.maven.pom.Resource#getExcludes <em>Excludes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Excludes</em>' containment reference.
     * @see #isSetExcludes()
     * @see #unsetExcludes()
     * @see #getExcludes()
     * @generated
     */
    void setExcludes(ExcludesType value);

    /**
     * Unsets the value of the '{@link org.devzuz.q.maven.pom.Resource#getExcludes <em>Excludes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExcludes()
     * @see #getExcludes()
     * @see #setExcludes(ExcludesType)
     * @generated
     */
    void unsetExcludes();

    /**
     * Returns whether the value of the '{@link org.devzuz.q.maven.pom.Resource#getExcludes <em>Excludes</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Excludes</em>' containment reference is set.
     * @see #unsetExcludes()
     * @see #getExcludes()
     * @see #setExcludes(ExcludesType)
     * @generated
     */
    boolean isSetExcludes();

} // Resource
