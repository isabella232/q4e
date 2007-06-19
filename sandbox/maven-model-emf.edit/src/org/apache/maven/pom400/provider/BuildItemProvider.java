/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.provider;


import java.util.Collection;
import java.util.List;

import org.apache.maven.pom400.Build;
import org.apache.maven.pom400.mavenFactory;
import org.apache.maven.pom400.mavenPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.apache.maven.pom400.Build} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BuildItemProvider
	extends ItemProviderAdapter
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addSourceDirectoryPropertyDescriptor(object);
			addScriptSourceDirectoryPropertyDescriptor(object);
			addTestSourceDirectoryPropertyDescriptor(object);
			addOutputDirectoryPropertyDescriptor(object);
			addTestOutputDirectoryPropertyDescriptor(object);
			addDefaultGoalPropertyDescriptor(object);
			addDirectoryPropertyDescriptor(object);
			addFinalNamePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Source Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSourceDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_sourceDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_sourceDirectory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__SOURCE_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Script Source Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addScriptSourceDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_scriptSourceDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_scriptSourceDirectory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__SCRIPT_SOURCE_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Test Source Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTestSourceDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_testSourceDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_testSourceDirectory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__TEST_SOURCE_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Output Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOutputDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_outputDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_outputDirectory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__OUTPUT_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Test Output Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTestOutputDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_testOutputDirectory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_testOutputDirectory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__TEST_OUTPUT_DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Default Goal feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDefaultGoalPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_defaultGoal_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_defaultGoal_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__DEFAULT_GOAL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Directory feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDirectoryPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_directory_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_directory_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__DIRECTORY,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Final Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFinalNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Build_finalName_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Build_finalName_feature", "_UI_Build_type"),
				 mavenPackage.Literals.BUILD__FINAL_NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(mavenPackage.Literals.BUILD__EXTENSIONS);
			childrenFeatures.add(mavenPackage.Literals.BUILD__RESOURCES);
			childrenFeatures.add(mavenPackage.Literals.BUILD__TEST_RESOURCES);
			childrenFeatures.add(mavenPackage.Literals.BUILD__FILTERS);
			childrenFeatures.add(mavenPackage.Literals.BUILD__PLUGIN_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.BUILD__PLUGINS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Build.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Build"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((Build)object).getFinalName();
		return label == null || label.length() == 0 ?
			getString("_UI_Build_type") :
			getString("_UI_Build_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Build.class)) {
			case mavenPackage.BUILD__SOURCE_DIRECTORY:
			case mavenPackage.BUILD__SCRIPT_SOURCE_DIRECTORY:
			case mavenPackage.BUILD__TEST_SOURCE_DIRECTORY:
			case mavenPackage.BUILD__OUTPUT_DIRECTORY:
			case mavenPackage.BUILD__TEST_OUTPUT_DIRECTORY:
			case mavenPackage.BUILD__DEFAULT_GOAL:
			case mavenPackage.BUILD__DIRECTORY:
			case mavenPackage.BUILD__FINAL_NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case mavenPackage.BUILD__EXTENSIONS:
			case mavenPackage.BUILD__RESOURCES:
			case mavenPackage.BUILD__TEST_RESOURCES:
			case mavenPackage.BUILD__FILTERS:
			case mavenPackage.BUILD__PLUGIN_MANAGEMENT:
			case mavenPackage.BUILD__PLUGINS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__EXTENSIONS,
				 mavenFactory.eINSTANCE.createExtensionsType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__RESOURCES,
				 mavenFactory.eINSTANCE.createResourcesType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__TEST_RESOURCES,
				 mavenFactory.eINSTANCE.createTestResourcesType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__FILTERS,
				 mavenFactory.eINSTANCE.createFiltersType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__PLUGIN_MANAGEMENT,
				 mavenFactory.eINSTANCE.createPluginManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.BUILD__PLUGINS,
				 mavenFactory.eINSTANCE.createPluginsType2()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return MavenEditPlugin.INSTANCE;
	}

}
