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
 * A representation of the model object '<em><b>Report Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 4.0.0
 * Represents a set of reports and configuration to be used to generate them.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.ReportSet#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.ReportSet#getInherited <em>Inherited</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.ReportSet#getReports <em>Reports</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet()
 * @model extendedMetaData="name='ReportSet' kind='elementOnly'"
 * @generated
 */
public interface ReportSet extends EObject
{
    /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * The default value is <code>"default"</code>.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 0.0.0+
	 * The unique id for this report set, to be used during POM inheritance.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #setId(String)
	 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Id()
	 * @model default="default" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
    String getId();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #isSetId()
	 * @see #unsetId()
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetId()
	 * @see #getId()
	 * @see #setId(String)
	 * @generated
	 */
    void unsetId();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getId <em>Id</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Id</em>' attribute is set.
	 * @see #unsetId()
	 * @see #getId()
	 * @see #setId(String)
	 * @generated
	 */
    boolean isSetId();

    /**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * Configuration of the report to be used when generating this set.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Configuration</em>' containment reference.
	 * @see #isSetConfiguration()
	 * @see #unsetConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Configuration()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='configuration' namespace='##targetNamespace'"
	 * @generated
	 */
    Configuration getConfiguration();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getConfiguration <em>Configuration</em>}' containment reference.
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
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getConfiguration <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @generated
	 */
    void unsetConfiguration();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getConfiguration <em>Configuration</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Configuration</em>' containment reference is set.
	 * @see #unsetConfiguration()
	 * @see #getConfiguration()
	 * @see #setConfiguration(Configuration)
	 * @generated
	 */
    boolean isSetConfiguration();

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
	 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Inherited()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='inherited' namespace='##targetNamespace'"
	 * @generated
	 */
    String getInherited();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getInherited <em>Inherited</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inherited</em>' attribute.
	 * @see #getInherited()
	 * @generated
	 */
    void setInherited(String value);

    /**
	 * Returns the value of the '<em><b>Reports</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 4.0.0
	 * 
	 *             The list of reports from this plugin which should be generated from this set.
	 *           
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reports</em>' containment reference.
	 * @see #isSetReports()
	 * @see #unsetReports()
	 * @see #setReports(ReportsType)
	 * @see org.devzuz.q.maven.pom.PomPackage#getReportSet_Reports()
	 * @model containment="true" unsettable="true"
	 *        extendedMetaData="kind='element' name='reports' namespace='##targetNamespace'"
	 * @generated
	 */
    ReportsType getReports();

    /**
	 * Sets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getReports <em>Reports</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reports</em>' containment reference.
	 * @see #isSetReports()
	 * @see #unsetReports()
	 * @see #getReports()
	 * @generated
	 */
    void setReports(ReportsType value);

    /**
	 * Unsets the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getReports <em>Reports</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetReports()
	 * @see #getReports()
	 * @see #setReports(ReportsType)
	 * @generated
	 */
    void unsetReports();

    /**
	 * Returns whether the value of the '{@link org.devzuz.q.maven.pom.ReportSet#getReports <em>Reports</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Reports</em>' containment reference is set.
	 * @see #unsetReports()
	 * @see #getReports()
	 * @see #setReports(ReportsType)
	 * @generated
	 */
    boolean isSetReports();

} // ReportSet
