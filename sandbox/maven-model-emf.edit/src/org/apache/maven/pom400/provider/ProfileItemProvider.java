/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.provider;


import java.util.Collection;
import java.util.List;

import org.apache.maven.pom400.Profile;
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
 * This is the item provider adapter for a {@link org.apache.maven.pom400.Profile} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProfileItemProvider
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
	public ProfileItemProvider(AdapterFactory adapterFactory) {
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

			addIdPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Id feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIdPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Profile_id_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Profile_id_feature", "_UI_Profile_type"),
				 mavenPackage.Literals.PROFILE__ID,
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
			childrenFeatures.add(mavenPackage.Literals.PROFILE__ACTIVATION);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__BUILD);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__MODULES);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__REPOSITORIES);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__PLUGIN_REPOSITORIES);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__DEPENDENCIES);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__REPORTS);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__REPORTING);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__DEPENDENCY_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__DISTRIBUTION_MANAGEMENT);
			childrenFeatures.add(mavenPackage.Literals.PROFILE__PROPERTIES);
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
	 * This returns Profile.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Profile"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((Profile)object).getId();
		return label == null || label.length() == 0 ?
			getString("_UI_Profile_type") :
			getString("_UI_Profile_type") + " " + label;
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

		switch (notification.getFeatureID(Profile.class)) {
			case mavenPackage.PROFILE__ID:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case mavenPackage.PROFILE__ACTIVATION:
			case mavenPackage.PROFILE__BUILD:
			case mavenPackage.PROFILE__MODULES:
			case mavenPackage.PROFILE__REPOSITORIES:
			case mavenPackage.PROFILE__PLUGIN_REPOSITORIES:
			case mavenPackage.PROFILE__DEPENDENCIES:
			case mavenPackage.PROFILE__REPORTS:
			case mavenPackage.PROFILE__REPORTING:
			case mavenPackage.PROFILE__DEPENDENCY_MANAGEMENT:
			case mavenPackage.PROFILE__DISTRIBUTION_MANAGEMENT:
			case mavenPackage.PROFILE__PROPERTIES:
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
				(mavenPackage.Literals.PROFILE__ACTIVATION,
				 mavenFactory.eINSTANCE.createActivation()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__BUILD,
				 mavenFactory.eINSTANCE.createBuildBase()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__MODULES,
				 mavenFactory.eINSTANCE.createModulesType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__REPOSITORIES,
				 mavenFactory.eINSTANCE.createRepositoriesType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__PLUGIN_REPOSITORIES,
				 mavenFactory.eINSTANCE.createPluginRepositoriesType1()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__DEPENDENCIES,
				 mavenFactory.eINSTANCE.createDependenciesType2()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__REPORTS,
				 mavenFactory.eINSTANCE.createReportsType2()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__REPORTING,
				 mavenFactory.eINSTANCE.createReporting()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__DEPENDENCY_MANAGEMENT,
				 mavenFactory.eINSTANCE.createDependencyManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__DISTRIBUTION_MANAGEMENT,
				 mavenFactory.eINSTANCE.createDistributionManagement()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.PROFILE__PROPERTIES,
				 mavenFactory.eINSTANCE.createPropertiesType1()));
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
