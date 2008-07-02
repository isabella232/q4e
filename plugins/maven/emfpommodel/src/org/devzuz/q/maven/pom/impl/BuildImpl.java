/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.Build;
import org.devzuz.q.maven.pom.ExtensionsType;
import org.devzuz.q.maven.pom.FiltersType1;
import org.devzuz.q.maven.pom.PluginManagement;
import org.devzuz.q.maven.pom.PluginsType2;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pom.ResourcesType1;
import org.devzuz.q.maven.pom.TestResourcesType1;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Build</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getSourceDirectory <em>Source Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getScriptSourceDirectory <em>Script Source Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestSourceDirectory <em>Test Source Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getOutputDirectory <em>Output Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestOutputDirectory <em>Test Output Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getDefaultGoal <em>Default Goal</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getTestResources <em>Test Resources</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getDirectory <em>Directory</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getFinalName <em>Final Name</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getFilters <em>Filters</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getPluginManagement <em>Plugin Management</em>}</li>
 *   <li>{@link org.devzuz.q.maven.pom.impl.BuildImpl#getPlugins <em>Plugins</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BuildImpl extends EObjectImpl implements Build
{
    /**
	 * The default value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String SOURCE_DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getSourceDirectory() <em>Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected String sourceDirectory = SOURCE_DIRECTORY_EDEFAULT;

    /**
	 * The default value of the '{@link #getScriptSourceDirectory() <em>Script Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getScriptSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String SCRIPT_SOURCE_DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getScriptSourceDirectory() <em>Script Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getScriptSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected String scriptSourceDirectory = SCRIPT_SOURCE_DIRECTORY_EDEFAULT;

    /**
	 * The default value of the '{@link #getTestSourceDirectory() <em>Test Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTestSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String TEST_SOURCE_DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTestSourceDirectory() <em>Test Source Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTestSourceDirectory()
	 * @generated
	 * @ordered
	 */
    protected String testSourceDirectory = TEST_SOURCE_DIRECTORY_EDEFAULT;

    /**
	 * The default value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOutputDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String OUTPUT_DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOutputDirectory()
	 * @generated
	 * @ordered
	 */
    protected String outputDirectory = OUTPUT_DIRECTORY_EDEFAULT;

    /**
	 * The default value of the '{@link #getTestOutputDirectory() <em>Test Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTestOutputDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String TEST_OUTPUT_DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTestOutputDirectory() <em>Test Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTestOutputDirectory()
	 * @generated
	 * @ordered
	 */
    protected String testOutputDirectory = TEST_OUTPUT_DIRECTORY_EDEFAULT;

    /**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
    protected ExtensionsType extensions;

    /**
	 * This is true if the Extensions containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean extensionsESet;

    /**
	 * The default value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDefaultGoal()
	 * @generated
	 * @ordered
	 */
    protected static final String DEFAULT_GOAL_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDefaultGoal() <em>Default Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDefaultGoal()
	 * @generated
	 * @ordered
	 */
    protected String defaultGoal = DEFAULT_GOAL_EDEFAULT;

    /**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
    protected ResourcesType1 resources;

    /**
	 * This is true if the Resources containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean resourcesESet;

    /**
	 * The cached value of the '{@link #getTestResources() <em>Test Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTestResources()
	 * @generated
	 * @ordered
	 */
    protected TestResourcesType1 testResources;

    /**
	 * This is true if the Test Resources containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean testResourcesESet;

    /**
	 * The default value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDirectory()
	 * @generated
	 * @ordered
	 */
    protected static final String DIRECTORY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDirectory() <em>Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDirectory()
	 * @generated
	 * @ordered
	 */
    protected String directory = DIRECTORY_EDEFAULT;

    /**
	 * The default value of the '{@link #getFinalName() <em>Final Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFinalName()
	 * @generated
	 * @ordered
	 */
    protected static final String FINAL_NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getFinalName() <em>Final Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFinalName()
	 * @generated
	 * @ordered
	 */
    protected String finalName = FINAL_NAME_EDEFAULT;

    /**
	 * The cached value of the '{@link #getFilters() <em>Filters</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFilters()
	 * @generated
	 * @ordered
	 */
    protected FiltersType1 filters;

    /**
	 * This is true if the Filters containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean filtersESet;

    /**
	 * The cached value of the '{@link #getPluginManagement() <em>Plugin Management</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPluginManagement()
	 * @generated
	 * @ordered
	 */
    protected PluginManagement pluginManagement;

    /**
	 * This is true if the Plugin Management containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean pluginManagementESet;

    /**
	 * The cached value of the '{@link #getPlugins() <em>Plugins</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPlugins()
	 * @generated
	 * @ordered
	 */
    protected PluginsType2 plugins;

    /**
	 * This is true if the Plugins containment reference has been set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    protected boolean pluginsESet;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected BuildImpl()
    {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass()
    {
		return PomPackage.Literals.BUILD;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getSourceDirectory()
    {
		return sourceDirectory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSourceDirectory(String newSourceDirectory)
    {
		String oldSourceDirectory = sourceDirectory;
		sourceDirectory = newSourceDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__SOURCE_DIRECTORY, oldSourceDirectory, sourceDirectory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getScriptSourceDirectory()
    {
		return scriptSourceDirectory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setScriptSourceDirectory(String newScriptSourceDirectory)
    {
		String oldScriptSourceDirectory = scriptSourceDirectory;
		scriptSourceDirectory = newScriptSourceDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY, oldScriptSourceDirectory, scriptSourceDirectory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getTestSourceDirectory()
    {
		return testSourceDirectory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTestSourceDirectory(String newTestSourceDirectory)
    {
		String oldTestSourceDirectory = testSourceDirectory;
		testSourceDirectory = newTestSourceDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__TEST_SOURCE_DIRECTORY, oldTestSourceDirectory, testSourceDirectory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getOutputDirectory()
    {
		return outputDirectory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOutputDirectory(String newOutputDirectory)
    {
		String oldOutputDirectory = outputDirectory;
		outputDirectory = newOutputDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__OUTPUT_DIRECTORY, oldOutputDirectory, outputDirectory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getTestOutputDirectory()
    {
		return testOutputDirectory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTestOutputDirectory(String newTestOutputDirectory)
    {
		String oldTestOutputDirectory = testOutputDirectory;
		testOutputDirectory = newTestOutputDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__TEST_OUTPUT_DIRECTORY, oldTestOutputDirectory, testOutputDirectory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ExtensionsType getExtensions()
    {
		return extensions;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs)
    {
		ExtensionsType oldExtensions = extensions;
		extensions = newExtensions;
		boolean oldExtensionsESet = extensionsESet;
		extensionsESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__EXTENSIONS, oldExtensions, newExtensions, !oldExtensionsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setExtensions(ExtensionsType newExtensions)
    {
		if (newExtensions != extensions) {
			NotificationChain msgs = null;
			if (extensions != null)
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldExtensionsESet = extensionsESet;
			extensionsESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__EXTENSIONS, newExtensions, newExtensions, !oldExtensionsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetExtensions(NotificationChain msgs)
    {
		ExtensionsType oldExtensions = extensions;
		extensions = null;
		boolean oldExtensionsESet = extensionsESet;
		extensionsESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__EXTENSIONS, oldExtensions, null, oldExtensionsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetExtensions()
    {
		if (extensions != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__EXTENSIONS, null, msgs);
			msgs = basicUnsetExtensions(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldExtensionsESet = extensionsESet;
			extensionsESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__EXTENSIONS, null, null, oldExtensionsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetExtensions()
    {
		return extensionsESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDefaultGoal()
    {
		return defaultGoal;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDefaultGoal(String newDefaultGoal)
    {
		String oldDefaultGoal = defaultGoal;
		defaultGoal = newDefaultGoal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__DEFAULT_GOAL, oldDefaultGoal, defaultGoal));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResourcesType1 getResources()
    {
		return resources;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetResources(ResourcesType1 newResources, NotificationChain msgs)
    {
		ResourcesType1 oldResources = resources;
		resources = newResources;
		boolean oldResourcesESet = resourcesESet;
		resourcesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__RESOURCES, oldResources, newResources, !oldResourcesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setResources(ResourcesType1 newResources)
    {
		if (newResources != resources) {
			NotificationChain msgs = null;
			if (resources != null)
				msgs = ((InternalEObject)resources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__RESOURCES, null, msgs);
			if (newResources != null)
				msgs = ((InternalEObject)newResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__RESOURCES, null, msgs);
			msgs = basicSetResources(newResources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldResourcesESet = resourcesESet;
			resourcesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__RESOURCES, newResources, newResources, !oldResourcesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetResources(NotificationChain msgs)
    {
		ResourcesType1 oldResources = resources;
		resources = null;
		boolean oldResourcesESet = resourcesESet;
		resourcesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__RESOURCES, oldResources, null, oldResourcesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetResources()
    {
		if (resources != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)resources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__RESOURCES, null, msgs);
			msgs = basicUnsetResources(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldResourcesESet = resourcesESet;
			resourcesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__RESOURCES, null, null, oldResourcesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetResources()
    {
		return resourcesESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TestResourcesType1 getTestResources()
    {
		return testResources;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetTestResources(TestResourcesType1 newTestResources, NotificationChain msgs)
    {
		TestResourcesType1 oldTestResources = testResources;
		testResources = newTestResources;
		boolean oldTestResourcesESet = testResourcesESet;
		testResourcesESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__TEST_RESOURCES, oldTestResources, newTestResources, !oldTestResourcesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTestResources(TestResourcesType1 newTestResources)
    {
		if (newTestResources != testResources) {
			NotificationChain msgs = null;
			if (testResources != null)
				msgs = ((InternalEObject)testResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__TEST_RESOURCES, null, msgs);
			if (newTestResources != null)
				msgs = ((InternalEObject)newTestResources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__TEST_RESOURCES, null, msgs);
			msgs = basicSetTestResources(newTestResources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldTestResourcesESet = testResourcesESet;
			testResourcesESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__TEST_RESOURCES, newTestResources, newTestResources, !oldTestResourcesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetTestResources(NotificationChain msgs)
    {
		TestResourcesType1 oldTestResources = testResources;
		testResources = null;
		boolean oldTestResourcesESet = testResourcesESet;
		testResourcesESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__TEST_RESOURCES, oldTestResources, null, oldTestResourcesESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetTestResources()
    {
		if (testResources != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)testResources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__TEST_RESOURCES, null, msgs);
			msgs = basicUnsetTestResources(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldTestResourcesESet = testResourcesESet;
			testResourcesESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__TEST_RESOURCES, null, null, oldTestResourcesESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetTestResources()
    {
		return testResourcesESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDirectory()
    {
		return directory;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDirectory(String newDirectory)
    {
		String oldDirectory = directory;
		directory = newDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__DIRECTORY, oldDirectory, directory));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFinalName()
    {
		return finalName;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFinalName(String newFinalName)
    {
		String oldFinalName = finalName;
		finalName = newFinalName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__FINAL_NAME, oldFinalName, finalName));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FiltersType1 getFilters()
    {
		return filters;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetFilters(FiltersType1 newFilters, NotificationChain msgs)
    {
		FiltersType1 oldFilters = filters;
		filters = newFilters;
		boolean oldFiltersESet = filtersESet;
		filtersESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__FILTERS, oldFilters, newFilters, !oldFiltersESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFilters(FiltersType1 newFilters)
    {
		if (newFilters != filters) {
			NotificationChain msgs = null;
			if (filters != null)
				msgs = ((InternalEObject)filters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__FILTERS, null, msgs);
			if (newFilters != null)
				msgs = ((InternalEObject)newFilters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__FILTERS, null, msgs);
			msgs = basicSetFilters(newFilters, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldFiltersESet = filtersESet;
			filtersESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__FILTERS, newFilters, newFilters, !oldFiltersESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetFilters(NotificationChain msgs)
    {
		FiltersType1 oldFilters = filters;
		filters = null;
		boolean oldFiltersESet = filtersESet;
		filtersESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__FILTERS, oldFilters, null, oldFiltersESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetFilters()
    {
		if (filters != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)filters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__FILTERS, null, msgs);
			msgs = basicUnsetFilters(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldFiltersESet = filtersESet;
			filtersESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__FILTERS, null, null, oldFiltersESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetFilters()
    {
		return filtersESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PluginManagement getPluginManagement()
    {
		return pluginManagement;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPluginManagement(PluginManagement newPluginManagement, NotificationChain msgs)
    {
		PluginManagement oldPluginManagement = pluginManagement;
		pluginManagement = newPluginManagement;
		boolean oldPluginManagementESet = pluginManagementESet;
		pluginManagementESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__PLUGIN_MANAGEMENT, oldPluginManagement, newPluginManagement, !oldPluginManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPluginManagement(PluginManagement newPluginManagement)
    {
		if (newPluginManagement != pluginManagement) {
			NotificationChain msgs = null;
			if (pluginManagement != null)
				msgs = ((InternalEObject)pluginManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs);
			if (newPluginManagement != null)
				msgs = ((InternalEObject)newPluginManagement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs);
			msgs = basicSetPluginManagement(newPluginManagement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginManagementESet = pluginManagementESet;
			pluginManagementESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__PLUGIN_MANAGEMENT, newPluginManagement, newPluginManagement, !oldPluginManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetPluginManagement(NotificationChain msgs)
    {
		PluginManagement oldPluginManagement = pluginManagement;
		pluginManagement = null;
		boolean oldPluginManagementESet = pluginManagementESet;
		pluginManagementESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__PLUGIN_MANAGEMENT, oldPluginManagement, null, oldPluginManagementESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetPluginManagement()
    {
		if (pluginManagement != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)pluginManagement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGIN_MANAGEMENT, null, msgs);
			msgs = basicUnsetPluginManagement(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginManagementESet = pluginManagementESet;
			pluginManagementESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__PLUGIN_MANAGEMENT, null, null, oldPluginManagementESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetPluginManagement()
    {
		return pluginManagementESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PluginsType2 getPlugins()
    {
		return plugins;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetPlugins(PluginsType2 newPlugins, NotificationChain msgs)
    {
		PluginsType2 oldPlugins = plugins;
		plugins = newPlugins;
		boolean oldPluginsESet = pluginsESet;
		pluginsESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__PLUGINS, oldPlugins, newPlugins, !oldPluginsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPlugins(PluginsType2 newPlugins)
    {
		if (newPlugins != plugins) {
			NotificationChain msgs = null;
			if (plugins != null)
				msgs = ((InternalEObject)plugins).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGINS, null, msgs);
			if (newPlugins != null)
				msgs = ((InternalEObject)newPlugins).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGINS, null, msgs);
			msgs = basicSetPlugins(newPlugins, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginsESet = pluginsESet;
			pluginsESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, PomPackage.BUILD__PLUGINS, newPlugins, newPlugins, !oldPluginsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicUnsetPlugins(NotificationChain msgs)
    {
		PluginsType2 oldPlugins = plugins;
		plugins = null;
		boolean oldPluginsESet = pluginsESet;
		pluginsESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__PLUGINS, oldPlugins, null, oldPluginsESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void unsetPlugins()
    {
		if (plugins != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)plugins).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PomPackage.BUILD__PLUGINS, null, msgs);
			msgs = basicUnsetPlugins(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldPluginsESet = pluginsESet;
			pluginsESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, PomPackage.BUILD__PLUGINS, null, null, oldPluginsESet));
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isSetPlugins()
    {
		return pluginsESet;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
    {
		switch (featureID) {
			case PomPackage.BUILD__EXTENSIONS:
				return basicUnsetExtensions(msgs);
			case PomPackage.BUILD__RESOURCES:
				return basicUnsetResources(msgs);
			case PomPackage.BUILD__TEST_RESOURCES:
				return basicUnsetTestResources(msgs);
			case PomPackage.BUILD__FILTERS:
				return basicUnsetFilters(msgs);
			case PomPackage.BUILD__PLUGIN_MANAGEMENT:
				return basicUnsetPluginManagement(msgs);
			case PomPackage.BUILD__PLUGINS:
				return basicUnsetPlugins(msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType)
    {
		switch (featureID) {
			case PomPackage.BUILD__SOURCE_DIRECTORY:
				return getSourceDirectory();
			case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
				return getScriptSourceDirectory();
			case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
				return getTestSourceDirectory();
			case PomPackage.BUILD__OUTPUT_DIRECTORY:
				return getOutputDirectory();
			case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
				return getTestOutputDirectory();
			case PomPackage.BUILD__EXTENSIONS:
				return getExtensions();
			case PomPackage.BUILD__DEFAULT_GOAL:
				return getDefaultGoal();
			case PomPackage.BUILD__RESOURCES:
				return getResources();
			case PomPackage.BUILD__TEST_RESOURCES:
				return getTestResources();
			case PomPackage.BUILD__DIRECTORY:
				return getDirectory();
			case PomPackage.BUILD__FINAL_NAME:
				return getFinalName();
			case PomPackage.BUILD__FILTERS:
				return getFilters();
			case PomPackage.BUILD__PLUGIN_MANAGEMENT:
				return getPluginManagement();
			case PomPackage.BUILD__PLUGINS:
				return getPlugins();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue)
    {
		switch (featureID) {
			case PomPackage.BUILD__SOURCE_DIRECTORY:
				setSourceDirectory((String)newValue);
				return;
			case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
				setScriptSourceDirectory((String)newValue);
				return;
			case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
				setTestSourceDirectory((String)newValue);
				return;
			case PomPackage.BUILD__OUTPUT_DIRECTORY:
				setOutputDirectory((String)newValue);
				return;
			case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
				setTestOutputDirectory((String)newValue);
				return;
			case PomPackage.BUILD__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case PomPackage.BUILD__DEFAULT_GOAL:
				setDefaultGoal((String)newValue);
				return;
			case PomPackage.BUILD__RESOURCES:
				setResources((ResourcesType1)newValue);
				return;
			case PomPackage.BUILD__TEST_RESOURCES:
				setTestResources((TestResourcesType1)newValue);
				return;
			case PomPackage.BUILD__DIRECTORY:
				setDirectory((String)newValue);
				return;
			case PomPackage.BUILD__FINAL_NAME:
				setFinalName((String)newValue);
				return;
			case PomPackage.BUILD__FILTERS:
				setFilters((FiltersType1)newValue);
				return;
			case PomPackage.BUILD__PLUGIN_MANAGEMENT:
				setPluginManagement((PluginManagement)newValue);
				return;
			case PomPackage.BUILD__PLUGINS:
				setPlugins((PluginsType2)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID)
    {
		switch (featureID) {
			case PomPackage.BUILD__SOURCE_DIRECTORY:
				setSourceDirectory(SOURCE_DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
				setScriptSourceDirectory(SCRIPT_SOURCE_DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
				setTestSourceDirectory(TEST_SOURCE_DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__OUTPUT_DIRECTORY:
				setOutputDirectory(OUTPUT_DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
				setTestOutputDirectory(TEST_OUTPUT_DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__EXTENSIONS:
				unsetExtensions();
				return;
			case PomPackage.BUILD__DEFAULT_GOAL:
				setDefaultGoal(DEFAULT_GOAL_EDEFAULT);
				return;
			case PomPackage.BUILD__RESOURCES:
				unsetResources();
				return;
			case PomPackage.BUILD__TEST_RESOURCES:
				unsetTestResources();
				return;
			case PomPackage.BUILD__DIRECTORY:
				setDirectory(DIRECTORY_EDEFAULT);
				return;
			case PomPackage.BUILD__FINAL_NAME:
				setFinalName(FINAL_NAME_EDEFAULT);
				return;
			case PomPackage.BUILD__FILTERS:
				unsetFilters();
				return;
			case PomPackage.BUILD__PLUGIN_MANAGEMENT:
				unsetPluginManagement();
				return;
			case PomPackage.BUILD__PLUGINS:
				unsetPlugins();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID)
    {
		switch (featureID) {
			case PomPackage.BUILD__SOURCE_DIRECTORY:
				return SOURCE_DIRECTORY_EDEFAULT == null ? sourceDirectory != null : !SOURCE_DIRECTORY_EDEFAULT.equals(sourceDirectory);
			case PomPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
				return SCRIPT_SOURCE_DIRECTORY_EDEFAULT == null ? scriptSourceDirectory != null : !SCRIPT_SOURCE_DIRECTORY_EDEFAULT.equals(scriptSourceDirectory);
			case PomPackage.BUILD__TEST_SOURCE_DIRECTORY:
				return TEST_SOURCE_DIRECTORY_EDEFAULT == null ? testSourceDirectory != null : !TEST_SOURCE_DIRECTORY_EDEFAULT.equals(testSourceDirectory);
			case PomPackage.BUILD__OUTPUT_DIRECTORY:
				return OUTPUT_DIRECTORY_EDEFAULT == null ? outputDirectory != null : !OUTPUT_DIRECTORY_EDEFAULT.equals(outputDirectory);
			case PomPackage.BUILD__TEST_OUTPUT_DIRECTORY:
				return TEST_OUTPUT_DIRECTORY_EDEFAULT == null ? testOutputDirectory != null : !TEST_OUTPUT_DIRECTORY_EDEFAULT.equals(testOutputDirectory);
			case PomPackage.BUILD__EXTENSIONS:
				return isSetExtensions();
			case PomPackage.BUILD__DEFAULT_GOAL:
				return DEFAULT_GOAL_EDEFAULT == null ? defaultGoal != null : !DEFAULT_GOAL_EDEFAULT.equals(defaultGoal);
			case PomPackage.BUILD__RESOURCES:
				return isSetResources();
			case PomPackage.BUILD__TEST_RESOURCES:
				return isSetTestResources();
			case PomPackage.BUILD__DIRECTORY:
				return DIRECTORY_EDEFAULT == null ? directory != null : !DIRECTORY_EDEFAULT.equals(directory);
			case PomPackage.BUILD__FINAL_NAME:
				return FINAL_NAME_EDEFAULT == null ? finalName != null : !FINAL_NAME_EDEFAULT.equals(finalName);
			case PomPackage.BUILD__FILTERS:
				return isSetFilters();
			case PomPackage.BUILD__PLUGIN_MANAGEMENT:
				return isSetPluginManagement();
			case PomPackage.BUILD__PLUGINS:
				return isSetPlugins();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString()
    {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (sourceDirectory: ");
		result.append(sourceDirectory);
		result.append(", scriptSourceDirectory: ");
		result.append(scriptSourceDirectory);
		result.append(", testSourceDirectory: ");
		result.append(testSourceDirectory);
		result.append(", outputDirectory: ");
		result.append(outputDirectory);
		result.append(", testOutputDirectory: ");
		result.append(testOutputDirectory);
		result.append(", defaultGoal: ");
		result.append(defaultGoal);
		result.append(", directory: ");
		result.append(directory);
		result.append(", finalName: ");
		result.append(finalName);
		result.append(')');
		return result.toString();
	}

} //BuildImpl
