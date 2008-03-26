/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.embedder.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.LifecycleLoaderException;
import org.apache.maven.lifecycle.LifecycleSpecificationException;
import org.apache.maven.lifecycle.LifecycleUtils;
import org.apache.maven.lifecycle.MojoBindingUtils;
import org.apache.maven.lifecycle.model.MojoBinding;
import org.apache.maven.lifecycle.plan.BuildPlan;
import org.apache.maven.lifecycle.plan.DefaultBuildPlanner;
import org.apache.maven.lifecycle.plan.LifecyclePlannerException;
import org.apache.maven.project.MavenProject;

/**
 * This class extends the DefaultBuildPlanner to provide the ability to filter out certain goals from the execution of a
 * lifecycle phase.
 * 
 * @author staticsnow@gmail.com
 */
public class EclipseMavenBuildPlanner
    extends DefaultBuildPlanner
{
    @Override
    public BuildPlan constructBuildPlan( List goals, MavenProject project, MavenSession session )
        throws LifecycleLoaderException, LifecycleSpecificationException, LifecyclePlannerException
    {
        BuildPlan plan = super.constructBuildPlan( goals, project, session );
        MavenExecutionRequest request = session.getRequest();
        if ( request instanceof EclipseMavenExecutionRequest )
        {
            EclipseMavenExecutionRequest eclipseRequest = (EclipseMavenExecutionRequest) request;
            Set<String> skipped = eclipseRequest.getSkippedGoals();
            List<MojoBinding> toRemove = new ArrayList<MojoBinding>( plan.renderExecutionPlan( new Stack() ) );
            for ( ListIterator<MojoBinding> bindingItr = toRemove.listIterator(); bindingItr.hasNext(); )
            {
                MojoBinding binding = bindingItr.next();
                if ( !skipped.contains( MojoBindingUtils.createMojoBindingKey( binding, true ) ) )
                {
                    bindingItr.remove();
                }
            }
            LifecycleUtils.removeMojoBindings( toRemove, plan.getLifecycleBindings(), true );
        }
        return plan;
    }
}
