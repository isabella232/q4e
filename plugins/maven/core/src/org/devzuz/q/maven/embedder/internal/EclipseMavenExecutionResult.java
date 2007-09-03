package org.devzuz.q.maven.embedder.internal;

import java.util.List;

import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.ReactorManager;
import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenExecutionResult;

public class EclipseMavenExecutionResult implements IMavenExecutionResult
{
    private List<Exception> exceptions;
    private MavenProject mavenProject;
    private ReactorManager reactorManager;
    
    public EclipseMavenExecutionResult( MavenExecutionResult result )
    {
        setExceptions( result.getExceptions() );
        setReactorManager( result.getReactorManager() );
        setMavenProject( result.getMavenProject() );
    }
    
    public List<Exception> getExceptions()
    {
        return exceptions;
    }

    public MavenProject getMavenProject()
    {
        return mavenProject;
    }

    public ReactorManager getReactorManager()
    {
        return reactorManager;
    }
    
    public void setExceptions( List<Exception> exceptions )
    {
        this.exceptions = exceptions;
    }

    public void setMavenProject( MavenProject project )
    {
        this.mavenProject = project;
    }
    
    public void setReactorManager( ReactorManager reactorManager )
    {
        this.reactorManager = reactorManager;
    }
}
