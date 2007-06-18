package org.devzuz.q.maven.ui.core;

import java.io.InputStream;

import prefuse.Visualization;

public interface IDependencyVisualRenderer
{
    public void render( InputStream in , Visualization vis );
}
