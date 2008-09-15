package org.devzuz.q.maven.jdt.core.handlers;

import org.eclipse.jdt.core.IClasspathEntry;

public interface IBuildPluginHandler
{

    public abstract void setBuildOptions();

    public abstract IClasspathEntry getClasspath();

}