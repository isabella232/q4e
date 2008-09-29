/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.jdt.ui.launchconfiguration;

import org.devzuz.q.maven.ui.Messages;

public enum LaunchConfigValidationResult
{
    NO_GIVEN_PROJECT( false, Messages.MavenLaunchConfigurationCustomGoalTab_NoProjectGiven ),
    GOAL_MISSING( false , Messages.MavenLaunchConfigurationCustomGoalTab_GoalMissing ),
    VALID( true , "" );
    
    private boolean isValid;
    private String errorMessage;
    
    LaunchConfigValidationResult( boolean isValid , String errorMessage )
    {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }
    
    public boolean isValid()
    {
        return isValid;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
}
