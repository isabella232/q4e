/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.apache.maven.pom400.ReportPlugin;
import org.apache.maven.pom400.mavenFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Report Plugin</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReportPluginTest extends TestCase {
	/**
	 * The fixture for this Report Plugin test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReportPlugin fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ReportPluginTest.class);
	}

	/**
	 * Constructs a new Report Plugin test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReportPluginTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Report Plugin test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ReportPlugin fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Report Plugin test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ReportPlugin getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(mavenFactory.eINSTANCE.createReportPlugin());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ReportPluginTest
