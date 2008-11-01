/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.codehaus.plexus.logging.Logger;

public class MavenExecutionParameter
{
    private Properties executionProperties;

    private boolean useReactor = false;

    private boolean recursive = false;

    private boolean showErrors = false;

    private boolean offline = false;

    private boolean updateSnapshots = false;

    private boolean noSnapshotUpdates = false;

    private Set<String> filteredGoals = Collections.emptySet();

    private int loggingLevel = Logger.LEVEL_INFO;

    private List<String> activeProfiles = new LinkedList<String>();

    private List<String> inActiveProfiles = new LinkedList<String>();;

    public static MavenExecutionParameter newDefaultMavenExecutionParameter()
    {
        return newDefaultMavenExecutionParameter( null );
    }

    public static MavenExecutionParameter newDefaultMavenExecutionParameter( Properties properties )
    {
        MavenExecutionParameter parameter = new MavenExecutionParameter();
        parameter.setOffline( MavenManager.getMavenPreferenceManager().isOffline() );
        parameter.setRecursive( MavenManager.getMavenPreferenceManager().isRecursive() );
        if ( properties != null )
        {
            parameter.setExecutionProperties( properties );
        }
        return parameter;
    }

    private MavenExecutionParameter()
    {
        executionProperties = null;
        clearProfiles();
    }

    public void clearProfiles()
    {
        activeProfiles = new ArrayList<String>();
        inActiveProfiles = new ArrayList<String>();
    }

    public List<String> getActiveProfiles()
    {
        return Collections.unmodifiableList( activeProfiles );
    }

    public List<String> getInActiveProfiles()
    {
        return Collections.unmodifiableList( inActiveProfiles );
    }

    public boolean addActiveProfiles( Collection<? extends String> profiles )
    {
        return activeProfiles.addAll( profiles );
    }

    public boolean addInActiveProfiles( Collection<? extends String> profiles )
    {
        return inActiveProfiles.addAll( profiles );
    }

    public boolean addActiveProfile( String profile )
    {
        return activeProfiles.add( profile );
    }

    public boolean addInActiveProfile( String profile )
    {
        return inActiveProfiles.add( profile );
    }

    private MavenExecutionParameter( Properties properties )
    {
        setExecutionProperties( properties );
    }

    public Properties getExecutionProperties()
    {
        return executionProperties;
    }

    public void setExecutionProperties( Properties executionProperties )
    {
        this.executionProperties = executionProperties;
    }

    public boolean isUseReactor()
    {
        return useReactor;
    }

    public void setUseReactor( boolean useReactor )
    {
        this.useReactor = useReactor;
    }

    public boolean isRecursive()
    {
        return recursive;
    }

    public void setRecursive( boolean recursive )
    {
        this.recursive = recursive;
    }

    public boolean isShowErrors()
    {
        return showErrors;
    }

    public void setShowErrors( boolean showErrors )
    {
        this.showErrors = showErrors;
    }

    public boolean isOffline()
    {
        return offline;
    }

    public void setOffline( boolean offline )
    {
        this.offline = offline;
    }

    public boolean isUpdateSnapshots()
    {
        return updateSnapshots;
    }

    public void setUpdateSnapshots( boolean updateSnapshots )
    {
        this.updateSnapshots = updateSnapshots;
    }

    public boolean isNoSnapshotUpdates()
    {
        return noSnapshotUpdates;
    }

    public void setNoSnapshotUpdates( boolean noSnapshotUpdates )
    {
        this.noSnapshotUpdates = noSnapshotUpdates;
    }

    public int getLoggingLevel()
    {
        return loggingLevel;
    }

    public void setLoggingLevel( int loggingLevel )
    {
        this.loggingLevel = loggingLevel;
    }

    public Set<String> getFilteredGoals()
    {
        return filteredGoals;
    }

    public void setFilteredGoals( Set<String> filteredGoals )
    {
        this.filteredGoals = filteredGoals;
    }

}
