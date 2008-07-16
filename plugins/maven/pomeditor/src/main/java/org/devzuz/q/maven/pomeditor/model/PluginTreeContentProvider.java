/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.model;

import java.util.List;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.provider.PomItemProviderAdapterFactory;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PluginTreeContentProvider
    implements ITreeContentProvider
{
    private Model model;

    private EReference[] path;

    private AdapterFactoryContentProvider delegateProvider;

    private EditingDomain domain;

    private TreeRoot root;

    private String rootName;

    public PluginTreeContentProvider( EReference[] path, EditingDomain domain, String rootName )
    {
        this.path = path;
        this.rootName = rootName;
        this.domain = domain;
        this.delegateProvider = new AdapterFactoryContentProvider( new PomItemProviderAdapterFactory() );
    }

    public void dispose()
    {
        delegateProvider.dispose();
    }

    public Object[] getChildren( Object parentElement )
    {
        if ( parentElement instanceof TreeRoot )
        {
            Object o = ModelUtil.getValue( model, path, domain, false );
            if ( null == o )
            {
                return new Object[] {};
            }
            else if ( o instanceof List )
            {
                return ( (List) o ).toArray();
            }
            else
            {
                return delegateProvider.getChildren( o );
            }
        }
        else
        {
            return delegateProvider.getChildren( parentElement );
        }
    }

    public Object[] getElements( Object inputElement )
    {
        return new Object[] { root };
    }

    public Object getParent( Object element )
    {
        if ( element instanceof EObject )
        {
            return ( (EObject) element ).eContainer();
        }
        else
        {
            return null;
        }
    }

    public boolean hasChildren( Object element )
    {
        return true;
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
        this.model = (Model) newInput;
        this.root = new TreeRoot( rootName, model, path, domain );
    }

}