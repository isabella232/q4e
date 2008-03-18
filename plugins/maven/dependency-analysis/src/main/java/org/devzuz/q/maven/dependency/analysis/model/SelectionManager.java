/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.model;

import java.util.ArrayList;
import java.util.List;

import org.devzuz.q.maven.dependency.analysis.DependencyAnalysisActivator;
import org.eclipse.swt.graphics.Color;

/**
 * Manages selection state of the model. The model is selection agnositic and delegates all selection functionality to
 * this class
 * 
 * @author jake pezaro
 */
public class SelectionManager
{

    private SelectionSet primary;

    private SelectionSet secondary;

    private SelectionSet tertiary;

    public SelectionManager()
    {
        clearSelections();
    }

    public void clearSelections()
    {
        primary = new SelectionSet();
        secondary = new SelectionSet();
        tertiary = new SelectionSet();
    }

    public void select( Instance selected )
    {
        primary.instances.add( selected );
        createTertiarySelections( selected );
        secondary.versions.add( selected.getClassificationParent() );
        secondary.artifacts.add( selected.getClassificationParent().getClassificationParent() );
    }

    private void createTertiarySelections( Instance instance )
    {
        Instance dependencyParent = instance.getDependencyParent();
        if ( dependencyParent != null )
        {
            tertiary.instances.add( dependencyParent );
            createTertiarySelections( dependencyParent );
        }
    }

    public void select( Version selected )
    {
        primary.versions.add( selected );
        for ( Instance instance : selected.getInstances() )
        {
            secondary.instances.add( instance );
            createTertiarySelections( instance );
        }
        secondary.artifacts.add( selected.getClassificationParent() );
    }

    public void select( Artifact selected )
    {
        primary.artifacts.add( selected );
        for ( Version version : selected.getVersions() )
        {
            secondary.versions.add( version );
            for ( Instance instance : version.getInstances() )
            {
                secondary.instances.add( instance );
                createTertiarySelections( instance );
            }
        }
    }

    public SelectionType isSelectionType( Instance selected )
    {
        if ( primary.instances.contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.instances.contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        if ( tertiary.instances.contains( selected ) )
        {
            return SelectionType.TERTIARY;
        }
        return SelectionType.NONE;
    }

    public SelectionType isSelectionType( Version selected )
    {
        if ( primary.versions.contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.versions.contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        return SelectionType.NONE;
    }

    public SelectionType isSelectionType( Artifact selected )
    {
        if ( primary.artifacts.contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.artifacts.contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        return SelectionType.NONE;
    }

    private class SelectionSet
    {
        private List<Instance> instances;

        private List<Version> versions;

        private List<Artifact> artifacts;

        public SelectionSet()
        {
            instances = new ArrayList<Instance>();
            versions = new ArrayList<Version>();
            artifacts = new ArrayList<Artifact>();
        }

    }

    public static Color getColour( Selectable selectable )
    {
        if ( selectable.isSelected().equals( SelectionType.PRIMARY ) )
        {
            return DependencyAnalysisActivator.SELECTIONCOLOUR_PRIMARY;
        }
        if ( selectable.isSelected().equals( SelectionType.SECONDARY ) )
        {
            return DependencyAnalysisActivator.SELECTIONCOLOUR_SECONDARY;
        }
        if ( selectable.isSelected().equals( SelectionType.TERTIARY ) )
        {
            return DependencyAnalysisActivator.SELECTIONCOLOUR_TERTIARY;
        }
        return null;
    }
}
