/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.apache.maven.pom400.provider;


import java.util.Collection;
import java.util.List;

import org.apache.maven.pom400.DistributionManagement;
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
 * This is the item provider adapter for a {@link org.apache.maven.pom400.DistributionManagement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DistributionManagementItemProvider
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
	public DistributionManagementItemProvider(AdapterFactory adapterFactory) {
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

			addDownloadUrlPropertyDescriptor(object);
			addStatusPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Download Url feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDownloadUrlPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DistributionManagement_downloadUrl_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DistributionManagement_downloadUrl_feature", "_UI_DistributionManagement_type"),
				 mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__DOWNLOAD_URL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Status feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStatusPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DistributionManagement_status_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_DistributionManagement_status_feature", "_UI_DistributionManagement_type"),
				 mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__STATUS,
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
			childrenFeatures.add(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY);
			childrenFeatures.add(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY);
			childrenFeatures.add(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE);
			childrenFeatures.add(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION);
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
	 * This returns DistributionManagement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/DistributionManagement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((DistributionManagement)object).getDownloadUrl();
		return label == null || label.length() == 0 ?
			getString("_UI_DistributionManagement_type") :
			getString("_UI_DistributionManagement_type") + " " + label;
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

		switch (notification.getFeatureID(DistributionManagement.class)) {
			case mavenPackage.DISTRIBUTION_MANAGEMENT__DOWNLOAD_URL:
			case mavenPackage.DISTRIBUTION_MANAGEMENT__STATUS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case mavenPackage.DISTRIBUTION_MANAGEMENT__REPOSITORY:
			case mavenPackage.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY:
			case mavenPackage.DISTRIBUTION_MANAGEMENT__SITE:
			case mavenPackage.DISTRIBUTION_MANAGEMENT__RELOCATION:
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
				(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY,
				 mavenFactory.eINSTANCE.createDeploymentRepository()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY,
				 mavenFactory.eINSTANCE.createDeploymentRepository()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__SITE,
				 mavenFactory.eINSTANCE.createSite()));

		newChildDescriptors.add
			(createChildParameter
				(mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__RELOCATION,
				 mavenFactory.eINSTANCE.createRelocation()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__REPOSITORY ||
			childFeature == mavenPackage.Literals.DISTRIBUTION_MANAGEMENT__SNAPSHOT_REPOSITORY;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
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
