/**
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.provider;

import java.util.Collection;
import java.util.List;

import org.devzuz.q.maven.pom.Notifier;
import org.devzuz.q.maven.pom.PomPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.devzuz.q.maven.pom.Notifier} object. <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class NotifierItemProvider
    extends ItemProviderAdapter
    implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
    IItemLabelProvider, IItemPropertySource
{
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotifierItemProvider( AdapterFactory adapterFactory )
    {
        super( adapterFactory );
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors( Object object )
    {
        if ( itemPropertyDescriptors == null )
        {
            super.getPropertyDescriptors( object );

            addTypePropertyDescriptor( object );
            addSendOnErrorPropertyDescriptor( object );
            addSendOnFailurePropertyDescriptor( object );
            addSendOnSuccessPropertyDescriptor( object );
            addSendOnWarningPropertyDescriptor( object );
            addAddressPropertyDescriptor( object );
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Type feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addTypePropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_type_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_type_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__TYPE, true, false,
                                                                   false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                                                                   null, null ) );
    }

    /**
     * This adds a property descriptor for the Send On Error feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSendOnErrorPropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_sendOnError_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_sendOnError_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__SEND_ON_ERROR, true,
                                                                   false, false,
                                                                   ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
                                                                   null ) );
    }

    /**
     * This adds a property descriptor for the Send On Failure feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSendOnFailurePropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_sendOnFailure_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_sendOnFailure_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__SEND_ON_FAILURE, true,
                                                                   false, false,
                                                                   ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
                                                                   null ) );
    }

    /**
     * This adds a property descriptor for the Send On Success feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSendOnSuccessPropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_sendOnSuccess_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_sendOnSuccess_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__SEND_ON_SUCCESS, true,
                                                                   false, false,
                                                                   ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
                                                                   null ) );
    }

    /**
     * This adds a property descriptor for the Send On Warning feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addSendOnWarningPropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_sendOnWarning_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_sendOnWarning_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__SEND_ON_WARNING, true,
                                                                   false, false,
                                                                   ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
                                                                   null ) );
    }

    /**
     * This adds a property descriptor for the Address feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAddressPropertyDescriptor( Object object )
    {
        itemPropertyDescriptors.add( createItemPropertyDescriptor(
                                                                   ( (ComposeableAdapterFactory) adapterFactory ).getRootAdapterFactory(),
                                                                   getResourceLocator(),
                                                                   getString( "_UI_Notifier_address_feature" ),
                                                                   getString( "_UI_PropertyDescriptor_description",
                                                                              "_UI_Notifier_address_feature",
                                                                              "_UI_Notifier_type" ),
                                                                   PomPackage.Literals.NOTIFIER__ADDRESS, true, false,
                                                                   false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                                                                   null, null ) );
    }

    /**
     * This returns Notifier.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage( Object object )
    {
        return overlayImage( object, getResourceLocator().getImage( "full/obj16/Notifier" ) );
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String getText( Object object )
    {
        String label = ( (Notifier) object ).getType();
        return label == null || label.length() == 0 ? getString( "_UI_Notifier_type" )
                        : getString( "_UI_Notifier_type" ) + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public void notifyChanged( Notification notification )
    {
        updateChildren( notification );

        switch ( notification.getFeatureID( Notifier.class ) )
        {
            case PomPackage.NOTIFIER__TYPE:
            case PomPackage.NOTIFIER__SEND_ON_ERROR:
            case PomPackage.NOTIFIER__SEND_ON_FAILURE:
            case PomPackage.NOTIFIER__SEND_ON_SUCCESS:
            case PomPackage.NOTIFIER__SEND_ON_WARNING:
            case PomPackage.NOTIFIER__ADDRESS:
                fireNotifyChanged( new ViewerNotification( notification, notification.getNotifier(), false, true ) );
                return;
        }
        super.notifyChanged( notification );
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors( Collection<Object> newChildDescriptors, Object object )
    {
        super.collectNewChildDescriptors( newChildDescriptors, object );
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator()
    {
        return PomEditPlugin.INSTANCE;
    }

}
