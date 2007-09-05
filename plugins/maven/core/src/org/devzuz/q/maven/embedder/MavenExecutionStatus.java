package org.devzuz.q.maven.embedder;

import org.eclipse.core.runtime.Status;

public class MavenExecutionStatus extends Status
{
    IMavenExecutionResult executionResult;
    
    public MavenExecutionStatus( int severity, String pluginId, String message, IMavenExecutionResult executionResult )
    {
        super( severity, pluginId, message, executionResult.getExceptions().get( 0 ) );
        this.executionResult = executionResult;
    }
    
    public IMavenExecutionResult getMavenExecutionResult()
    {
        return executionResult;
    }
}
