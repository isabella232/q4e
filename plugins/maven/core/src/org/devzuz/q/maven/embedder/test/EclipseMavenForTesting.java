package org.devzuz.q.maven.embedder.test;

import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.eclipse.core.runtime.CoreException;

public class EclipseMavenForTesting extends EclipseMaven
{

    @Override
    public boolean start() throws CoreException
    {
        return super.start();
    }

    @Override
    public boolean stop() throws CoreException
    {
        return super.stop();
    }

}
