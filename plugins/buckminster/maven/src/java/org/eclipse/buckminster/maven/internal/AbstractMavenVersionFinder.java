/*****************************************************************************
 * Copyright (c) 2006-2007, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 *****************************************************************************/
/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.buckminster.maven.internal;

import java.util.List;

import org.eclipse.buckminster.core.reader.IVersionFinder;
import org.eclipse.buckminster.core.version.IVersion;
import org.eclipse.buckminster.core.version.IVersionDesignator;
import org.eclipse.buckminster.core.version.IVersionType;
import org.eclipse.buckminster.core.version.VersionFactory;
import org.eclipse.buckminster.core.version.VersionMatch;
import org.eclipse.buckminster.runtime.MonitorUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public abstract class AbstractMavenVersionFinder implements IVersionFinder
{

    /**
     * Returns the highest version of the two arguments
     */
    static VersionMatch getBestVersion( VersionMatch a, VersionMatch b )
    {
    	if(a == null)
    		return b;
    	
    	if(b == null)
    		return a;
    
    	IVersion av = a.getVersion();
    	IVersion bv = b.getVersion();
    	IVersionType at = av.getType();
    	IVersionType bt = bv.getType();
    	if(at.isComparableTo(bt))
    		return (av.compareTo(bv) < 0) ? b : a;
    
    	// We only deal with triplets, timestamps, and snapshots here. The
    	// order of precedence is triplet, timestamp, snapshot
    	// 
    	//
    	if(at.equals(VersionFactory.TripletType))
    		return a;
    
    	if(bt.equals(VersionFactory.TripletType))
    		return b;
    
    	if(at.equals(VersionFactory.TimestampType))
    		return a;
    
    	// at is a snapshot and since they are not comparable, bt has to be a timestamp
    	// type at this point
    	//
    	return b;
    }

    /**
     * Maven always uses plugin style versioning.
     * 
     * @return the version selector that is highest in magnitude.
     */
    public VersionMatch getDefaultVersion( IProgressMonitor monitor )
        throws CoreException
    {
    	monitor.beginTask(null, 2000);
    	try
    	{
    		IVersionDesignator designator = VersionFactory.createExplicitDesignator(VersionFactory.defaultVersion());
    		IVersionQuery query = VersionSelectorFactory.createQuery(null, designator);
    		VersionMatch best = getBestVersion(query, MonitorUtils.subMonitor(monitor, 1000));
    		if(best != null)
    		{
    			MonitorUtils.worked(monitor, 1000);
    			return best;
    		}
    
    		designator = VersionFactory.createDesignator(VersionFactory.TripletType, "0.0.0");
    		query = VersionSelectorFactory.createQuery(null, designator);
    		return getBestVersion(query, MonitorUtils.subMonitor(monitor, 1000));
    	}
    	finally
    	{
    		monitor.done();
    	}
    }

    /**
     * Get the best version, which in this implementation is the highest one
     * TODO best shouldn't be latest, for instance if the query asks for 1.0 you shouldn't get 2.0
     */
    public VersionMatch getBestVersion( IVersionQuery query, IProgressMonitor monitor )
        throws CoreException
    {
    	VersionMatch best = null;
    	for(VersionMatch candidate : getComponentVersions(query, monitor))
    		best = getBestVersion(candidate, best);
    	return best;
    }

    abstract List<VersionMatch> getComponentVersions(IVersionQuery query, IProgressMonitor monitor) throws CoreException;

    public void close()
    {
        // do nothing
    }
}
