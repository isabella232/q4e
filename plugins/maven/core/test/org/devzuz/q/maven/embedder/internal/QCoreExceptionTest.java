package org.devzuz.q.maven.embedder.internal;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.devzuz.q.maven.embedder.Activator;
import org.devzuz.q.maven.embedder.QCoreException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import junit.framework.TestCase;

public class QCoreExceptionTest extends TestCase
{
    private static final String MSG = "nested exception";

    private Exception coreException;

    protected void setUp() throws Exception
    {
        super.setUp();
        Exception e = new RuntimeException( MSG );
        coreException =
            new RuntimeException( new QCoreException( new Status( IStatus.ERROR, Activator.PLUGIN_ID,
                                                                 "Unable to read project", e ) ) );
    }

    public void testPrintStackTrace()
    {
        StringWriter sw = new StringWriter();
        coreException.printStackTrace( new PrintWriter( sw ) );
        assertTrue( sw.toString().contains( MSG ) );
    }
}
