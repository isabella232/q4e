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

    private List<Instance> tertiary;

    public SelectionManager()
    {
        clearSelections();
    }

    public void clearSelections()
    {
        primary = new SelectionSet();
        secondary = new SelectionSet();
        tertiary = new ArrayList<Instance>();
    }

    public SelectionSet getPrimary()
    {
        return primary;
    }

    public SelectionSet getSecondary()
    {
        return secondary;
    }

    public void select( Instance selected )
    {
        primary.getInstances().add( selected );
        createTertiarySelections( selected );
        secondary.getVersions().add( selected.getClassificationParent() );
        secondary.getArtifacts().add( selected.getClassificationParent().getClassificationParent() );
    }

    private void createTertiarySelections( Instance instance )
    {
        Instance dependencyParent = instance.getDependencyParent();
        if ( dependencyParent != null )
        {
            tertiary.add( dependencyParent );
            createTertiarySelections( dependencyParent );
        }
    }

    public void select( Version selected )
    {
        primary.getVersions().add( selected );
        for ( Instance instance : selected.getInstances() )
        {
            secondary.getInstances().add( instance );
            createTertiarySelections( instance );
        }
        secondary.getArtifacts().add( selected.getClassificationParent() );
    }

    public void select( Artifact selected )
    {
        primary.getArtifacts().add( selected );
        for ( Version version : selected.getVersions() )
        {
            secondary.getVersions().add( version );
            for ( Instance instance : version.getInstances() )
            {
                secondary.getInstances().add( instance );
                createTertiarySelections( instance );
            }
        }
    }

    public SelectionType isSelectionType( Instance selected )
    {
        if ( primary.getInstances().contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.getInstances().contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        if ( tertiary.contains( selected ) )
        {
            return SelectionType.TERTIARY;
        }
        return SelectionType.NONE;
    }

    public SelectionType isSelectionType( Version selected )
    {
        if ( primary.getVersions().contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.getVersions().contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        return SelectionType.NONE;
    }

    public SelectionType isSelectionType( Artifact selected )
    {
        if ( primary.getArtifacts().contains( selected ) )
        {
            return SelectionType.PRIMARY;
        }
        if ( secondary.getArtifacts().contains( selected ) )
        {
            return SelectionType.SECONDARY;
        }
        return SelectionType.NONE;
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
