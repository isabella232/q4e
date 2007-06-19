/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.apache.maven.pom400.IncludesType;
import org.apache.maven.pom400.mavenFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Includes Type</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IncludesTypeTest extends TestCase {
	/**
	 * The fixture for this Includes Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncludesType fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IncludesTypeTest.class);
	}

	/**
	 * Constructs a new Includes Type test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncludesTypeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Includes Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(IncludesType fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Includes Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private IncludesType getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(mavenFactory.eINSTANCE.createIncludesType());
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

} //IncludesTypeTest
