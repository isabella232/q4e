/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor;

import org.devzuz.q.maven.pom.PomFactory;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

/**
 * Utility methods for manipulating the EMF model.
 * 
 * @author Mike Poindexter
 *
 */
public class ModelUtil
{
    public static EObject createOrGetContainer( EObject root, EReference[] path, EditingDomain domain )
    {
        EObject ret = root;
        for ( EReference reference : path )
        {
            EObject next = (EObject) ret.eGet( reference );
            if ( null == next )
            {
                next = PomFactory.eINSTANCE.create( reference.getEReferenceType() );
                Command command = SetCommand.create( domain, ret, reference, next );
                domain.getCommandStack().execute( command );
            }
            ret = next;
        }
        return ret;
    }

    public static Object getValue( EObject root, EStructuralFeature[] path, EditingDomain domain, boolean create )
    {
        Object ret = root;
        for ( EStructuralFeature feature : path )
        {
            Object next = ( (EObject) ret ).eGet( feature );
            if ( null == next )
            {
                if ( create && ret instanceof EObject && feature instanceof EReference )
                {
                    EReference reference = (EReference) feature;
                    next = PomFactory.eINSTANCE.create( reference.getEReferenceType() );
                    Command command = SetCommand.create( domain, ret, reference, next );
                    domain.getCommandStack().execute( command );
                }
                else
                {
                    ret = null;
                    break;
                }
            }
            ret = next;
        }
        return ret;
    }

    public static void bindTable( final EObject root, final EStructuralFeature[] path, EStructuralFeature[] columns,
                                  Table table, final EditingDomain domain )
    {
        TableViewer viewer = new TableViewer( table );
        ObservableListContentProvider contentProvider = new ObservableListContentProvider();
        viewer.setContentProvider( contentProvider );

        IObservableValue ctx = new WritableValue();
        ctx.setValue( root );
        for ( int i = 0; i < path.length - 1; i++ )
        {
            ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, path[i] );
        }

        IObservableList list =
            EMFEditObservables.observeDetailList( Realm.getDefault(), domain, ctx, path[path.length - 1] );
        viewer.setInput( list );

        if ( columns != null )
        {
            IObservableMap[] labels =
                EMFEditObservables.observeMaps( domain, contentProvider.getKnownElements(), columns );

            viewer.setLabelProvider( new ObservableMapLabelProvider( labels ) );
        }
        else
        {
            viewer.setLabelProvider( new ILabelProvider()
            {
                public void addListener( ILabelProviderListener listener )
                {
                }

                public void dispose()
                {
                }

                public Image getImage( Object element )
                {
                    return null;
                }

                public String getText( Object element )
                {
                    return element.toString();
                }

                public boolean isLabelProperty( Object element, String property )
                {
                    return false;
                }

                public void removeListener( ILabelProviderListener listener )
                {
                }

            } );
        }

    }

    public static void bind( final EObject root, final EStructuralFeature[] path, IObservableValue uiValue,
                             final EditingDomain domain, DataBindingContext dbc )
    {
        IObservableValue ctx = new WritableValue();
        ctx.setValue( root );
        for ( EStructuralFeature feature : path )
        {
            ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, feature );
        }
        ctx.getValue();
        dbc.bindValue( uiValue, ctx, new UpdateValueStrategy()
        {
            @Override
            protected IStatus doSet( IObservableValue observableValue, Object value )
            {
                getValue( root, path, domain, true );
                if ( "".equals( value ) )
                {
                    value = null;
                }
                return super.doSet( observableValue, value );
            }
        }, null );
    }

    public static void setValue( final EObject obj, EStructuralFeature feature, Object value, EditingDomain domain )
    {
        Command cmd = SetCommand.create( domain, obj, feature, value );
        domain.getCommandStack().execute( cmd );
    }
}
