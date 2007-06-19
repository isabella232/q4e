/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.apache.maven.pom400.Build;
import org.apache.maven.pom400.mavenFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Build</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class BuildTest extends TestCase {
	/**
	 * The fixture for this Build test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Build fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(BuildTest.class);
	}

	/**
	 * Constructs a new Build test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Build test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Build fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Build test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private Build getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(mavenFactory.eINSTANCE.createBuild());
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

} //BuildTest
