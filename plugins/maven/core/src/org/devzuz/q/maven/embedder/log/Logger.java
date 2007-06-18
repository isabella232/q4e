package org.devzuz.q.maven.embedder.log;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * Makes easier to log to the Eclipse logging system
 */
public interface Logger {

    void log(IStatus status);

    void log(CoreException e);

    void log(Throwable t);

    /**
     * @deprecated use {@link #error(String)}
     * @param msg
     */
    void log(String msg);

    void log(String msg, Throwable t);

    void error(String msg);

    void info(String msg);
}