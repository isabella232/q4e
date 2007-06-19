/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.apache.maven.pom400.ExclusionsType;
import org.apache.maven.pom400.mavenFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Exclusions Type</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExclusionsTypeTest extends TestCase {
	/**
	 * The fixture for this Exclusions Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExclusionsType fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ExclusionsTypeTest.class);
	}

	/**
	 * Constructs a new Exclusions Type test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExclusionsTypeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Exclusions Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ExclusionsType fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Exclusions Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ExclusionsType getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(mavenFactory.eINSTANCE.createExclusionsType());
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

} //ExclusionsTypeTest
