package org.devzuz.q.maven.ui.core;

import java.io.InputStream;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.core.runtime.CoreException;

public interface IDependencyVisualSource
{
    public InputStream getGraphData( IMavenProject project ) throws CoreException;
}
