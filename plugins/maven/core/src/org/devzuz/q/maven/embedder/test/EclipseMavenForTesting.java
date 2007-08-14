package org.devzuz.q.maven.embedder.test;

import org.devzuz.q.maven.embedder.internal.EclipseMaven;
import org.eclipse.core.runtime.CoreException;

public class EclipseMavenForTesting extends EclipseMaven
{

    @Override
    public void start() throws CoreException
    {
        super.start();
    }

    @Override
    public void stop() throws CoreException
    {
        super.stop();
    }

}
