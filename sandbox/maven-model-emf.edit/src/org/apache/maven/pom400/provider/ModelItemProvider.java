/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.provider;


import java.util.Collection;
import java.util.List;

import org.apache.maven.pom400.Model;
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
 * This is the item provider adapter for a {@link org.apache.maven.pom400.Model} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelItemProvider
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
	public ModelItemProvider(AdapterFactory adapterFactory) {
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

			addModelVersionPropertyDescriptor(object);
			addGroupIdPropertyDescriptor(object);
			addArtifactIdPropertyDescriptor(object);
			addPackagingPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addVersionPropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addUrlPropertyDescriptor(object);
			addInceptionYearPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Model Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addModelVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_modelVersion_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_modelVersion_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__MODEL_VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Group Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addGroupIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_groupId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_groupId_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__GROUP_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Artifact Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addArtifactIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_artifactId_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_artifactId_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__ARTIFACT_ID,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Packaging feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPackagingPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_packaging_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_packaging_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__PACKAGING,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_name_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Version feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVersionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_version_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_version_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__VERSION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_description_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Url feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUrlPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_url_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_url_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__URL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Inception Year feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInceptionYearPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Model_inceptionYear_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Model_inceptionYear_feature", "_UI_Model_type"),
				 mavenPackage.Literals.MODEL__INCEPTION_YEAR,
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
			childrenFeatures.add(mavenPackage.Literals.MODEL__PARENT);
			childrenFeatures.add(mavenPackage.Literals.MODEL__PREREQUISITES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__ISSUE_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.MODEL__CI_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.MODEL__MAILING_LISTS);
			childrenFeatures.add(mavenPackage.Literals.MODEL__DEVELOPERS);
			childrenFeatures.add(mavenPackage.Literals.MODEL__CONTRIBUTORS);
			childrenFeatures.add(mavenPackage.Literals.MODEL__LICENSES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__SCM);
			childrenFeatures.add(mavenPackage.Literals.MODEL__ORGANIZATION);
			childrenFeatures.add(mavenPackage.Literals.MODEL__BUILD);
			childrenFeatures.add(mavenPackage.Literals.MODEL__PROFILES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__MODULES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__REPOSITORIES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__PLUGIN_REPOSITORIES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__DEPENDENCIES);
			childrenFeatures.add(mavenPackage.Literals.MODEL__REPORTS);
			childrenFeatures.add(mavenPackage.Literals.MODEL__REPORTING);
			childrenFeatures.add(mavenPackage.Literals.MODEL__DEPENDENCY_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.MODEL__PROPERTIES);
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
	 * This returns Model.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Model"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((Model)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Model_type") :
			getString("_UI_Model_type") + " " + label;
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

		switch (notification.getFeatureID(Model.class)) {
			case mavenPackage.MODEL__MODEL_VERSION:
			case mavenPackage.MODEL__GROUP_ID:
			case mavenPackage.MODEL__ARTIFACT_ID:
			case mavenPackage.MODEL__PACKAGING:
			case mavenPackage.MODEL__NAME:
			case mavenPackage.MODEL__VERSION:
			case mavenPackage.MODEL__DESCRIPTION:
			case mavenPackage.MODEL__URL:
			case mavenPackage.MODEL__INCEPTION_YEAR:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case mavenPackage.MODEL__PARENT:
			case mavenPackage.MODEL__PREREQUISITES:
			case mavenPackage.MODEL__ISSUE_MANAGEMENT:
			case mavenPackage.MODEL__CI_MANAGEMENT:
			case mavenPackage.MODEL__MAILING_LISTS:
			case mavenPackage.MODEL__DEVELOPERS:
			case mavenPackage.MODEL__CONTRIBUTORS:
			case mavenPackage.MODEL__LICENSES:
			case mavenPackage.MODEL__SCM:
			case mavenPackage.MODEL__ORGANIZATION:
			case mavenPackage.MODEL__BUILD:
			case mavenPackage.MODEL__PROFILES:
			case mavenPackage.MODEL__MODULES:
			case mavenPackage.MODEL__REPOSITORIES:
			case mavenPackage.MODEL__PLUGIN_REPOSITORIES:
			case mavenPackage.MODEL__DEPENDENCIES:
			case mavenPackage.MODEL__REPORTS:
			case mavenPackage.MODEL__REPORTING:
			case mavenPackage.MODEL__DEPENDENCY_MANAGEMENT:
			case mavenPackage.MODEL__DISTRIBUTION_MANAGEMENT:
			case mavenPackage.MODEL__PROPERTIES:
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
				(mavenPackage.Literals.MODEL__PARENT,
				 mavenFactory.eINSTANCE.createParent()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__PREREQUISITES,
				 mavenFactory.eINSTANCE.createPrerequisites()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__ISSUE_MANAGEMENT,
				 mavenFactory.eINSTANCE.createIssueManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__CI_MANAGEMENT,
				 mavenFactory.eINSTANCE.createCiManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__MAILING_LISTS,
				 mavenFactory.eINSTANCE.createMailingListsType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__DEVELOPERS,
				 mavenFactory.eINSTANCE.createDevelopersType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__CONTRIBUTORS,
				 mavenFactory.eINSTANCE.createContributorsType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__LICENSES,
				 mavenFactory.eINSTANCE.createLicensesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__SCM,
				 mavenFactory.eINSTANCE.createScm()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__ORGANIZATION,
				 mavenFactory.eINSTANCE.createOrganization()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__BUILD,
				 mavenFactory.eINSTANCE.createBuild()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__PROFILES,
				 mavenFactory.eINSTANCE.createProfilesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__MODULES,
				 mavenFactory.eINSTANCE.createModulesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__REPOSITORIES,
				 mavenFactory.eINSTANCE.createRepositoriesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__PLUGIN_REPOSITORIES,
				 mavenFactory.eINSTANCE.createPluginRepositoriesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__DEPENDENCIES,
				 mavenFactory.eINSTANCE.createDependenciesType()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__REPORTS,
				 mavenFactory.eINSTANCE.createReportsType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__REPORTING,
				 mavenFactory.eINSTANCE.createReporting()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__DEPENDENCY_MANAGEMENT,
				 mavenFactory.eINSTANCE.createDependencyManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__DISTRIBUTION_MANAGEMENT,
				 mavenFactory.eINSTANCE.createDistributionManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.MODEL__PROPERTIES,
				 mavenFactory.eINSTANCE.createPropertiesType2()));
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
