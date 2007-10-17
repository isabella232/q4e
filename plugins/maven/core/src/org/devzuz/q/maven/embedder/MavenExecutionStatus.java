package org.devzuz.q.maven.embedder;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class MavenExecutionStatus extends Status
{
    IMavenExecutionResult executionResult;

    public MavenExecutionStatus( int severity, String pluginId, String message, IMavenExecutionResult executionResult )
    {
        super( severity, pluginId, message );
        List<CoreException> exceptions = executionResult.getExceptions();
        if ( exceptions.size() == 1 )
        {
            IStatus innerStatus = exceptions.get( 0 ).getStatus();
            setSeverity( innerStatus.getSeverity() );
            setPlugin( innerStatus.getPlugin() );
            setMessage( innerStatus.getMessage() );
            setException( innerStatus.getException() );
        }
        else if ( exceptions.size() > 1 )
        {
            setMessage( message + ": several errors found." );
            // Use the first exception wrapped
            setException( exceptions.get( 0 ).getStatus().getException() );
        }

        this.executionResult = executionResult;
    }

    public IMavenExecutionResult getMavenExecutionResult()
    {
        return executionResult;
    }
}
