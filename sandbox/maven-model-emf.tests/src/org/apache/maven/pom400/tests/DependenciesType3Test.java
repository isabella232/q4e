/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.apache.maven.pom400.DependenciesType3;
import org.apache.maven.pom400.mavenFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Dependencies Type3</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DependenciesType3Test extends TestCase {
	/**
	 * The fixture for this Dependencies Type3 test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependenciesType3 fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DependenciesType3Test.class);
	}

	/**
	 * Constructs a new Dependencies Type3 test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependenciesType3Test(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Dependencies Type3 test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DependenciesType3 fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Dependencies Type3 test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DependenciesType3 getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(mavenFactory.eINSTANCE.createDependenciesType3());
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

} //DependenciesType3Test
