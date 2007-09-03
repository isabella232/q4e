package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.internal.EclipseMavenRequest;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

public class MavenExecutionJobAdapter extends JobChangeAdapter
{
    private EclipseMavenRequest mavenExecutionJob;
    
    public void setMavenExecutionJob( EclipseMavenRequest mavenExecutionJob )
    {
        this.mavenExecutionJob = mavenExecutionJob;
    }
    
    /**
     * Override 
     * public void done( IJobChangeEvent event , MavenExecutionResult result )
     * instead.
     */
    public final void done( IJobChangeEvent event ) 
    {
        done( event , mavenExecutionJob.getExecutionResult() );
    }
    
    public void done( IJobChangeEvent event , IMavenExecutionResult result )
    {
        
    }
}
