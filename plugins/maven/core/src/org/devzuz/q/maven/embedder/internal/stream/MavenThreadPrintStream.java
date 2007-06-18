/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal.stream;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

public class MavenThreadPrintStream extends PrintStream {
    private PrintStream out;

    private EclipseMavenEventPropagatorPrintStream eventPropagatorPrintStream;

    private ThreadGroup mavenThreadGroup;

    public MavenThreadPrintStream(Thread mavenThread, EclipseMavenEventPropagatorPrintStream mavenStream,
            PrintStream out) {
        super(out);
        this.out = out;
        this.mavenThreadGroup = mavenThread.getThreadGroup();
        this.eventPropagatorPrintStream = mavenStream;
    }

    public PrintStream getOut() {
        ThreadGroup currentThreadGroup = Thread.currentThread().getThreadGroup();
        if (mavenThreadGroup.equals(currentThreadGroup)) {
            return eventPropagatorPrintStream;
        } else {
            return out;
        }
    }

    /**
     * @param arg0
     * @return
     * @see java.io.PrintStream#append(char)
     */
    public PrintStream append(char arg0) {
        return getOut().append(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @see java.io.PrintStream#append(java.lang.CharSequence, int, int)
     */
    public PrintStream append(CharSequence arg0, int arg1, int arg2) {
        return getOut().append(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @return
     * @see java.io.PrintStream#append(java.lang.CharSequence)
     */
    public PrintStream append(CharSequence arg0) {
        return getOut().append(arg0);
    }

    /**
     * @return
     * @see java.io.PrintStream#checkError()
     */
    public boolean checkError() {
        return getOut().checkError();
    }

    /**
     * 
     * @see java.io.PrintStream#close()
     */
    public void close() {
        getOut().close();
    }

    /**
     * @param arg0
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        return getOut().equals(arg0);
    }

    /**
     * 
     * @see java.io.PrintStream#flush()
     */
    public void flush() {
        getOut().flush();
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @see java.io.PrintStream#format(java.util.Locale, java.lang.String, java.lang.Object[])
     */
    public PrintStream format(Locale arg0, String arg1, Object... arg2) {
        return getOut().format(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     * @see java.io.PrintStream#format(java.lang.String, java.lang.Object[])
     */
    public PrintStream format(String arg0, Object... arg1) {
        return getOut().format(arg0, arg1);
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getOut().hashCode();
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(boolean)
     */
    public void print(boolean arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(char)
     */
    public void print(char arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(char[])
     */
    public void print(char[] arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(double)
     */
    public void print(double arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(float)
     */
    public void print(float arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(int)
     */
    public void print(int arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(long)
     */
    public void print(long arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(java.lang.Object)
     */
    public void print(Object arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#print(java.lang.String)
     */
    public void print(String arg0) {
        getOut().print(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @see java.io.PrintStream#printf(java.util.Locale, java.lang.String, java.lang.Object[])
     */
    public PrintStream printf(Locale arg0, String arg1, Object... arg2) {
        return getOut().printf(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     * @see java.io.PrintStream#printf(java.lang.String, java.lang.Object[])
     */
    public PrintStream printf(String arg0, Object... arg1) {
        return getOut().printf(arg0, arg1);
    }

    /**
     * 
     * @see java.io.PrintStream#println()
     */
    public void println() {
        getOut().println();
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(boolean)
     */
    public void println(boolean arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(char)
     */
    public void println(char arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(char[])
     */
    public void println(char[] arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(double)
     */
    public void println(double arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(float)
     */
    public void println(float arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(int)
     */
    public void println(int arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(long)
     */
    public void println(long arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(java.lang.Object)
     */
    public void println(Object arg0) {
        getOut().println(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#println(java.lang.String)
     */
    public void println(String arg0) {
        getOut().println(arg0);
    }

    /**
     * @return
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getOut().toString();
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @see java.io.PrintStream#write(byte[], int, int)
     */
    public void write(byte[] arg0, int arg1, int arg2) {
        getOut().write(arg0, arg1, arg2);
    }

    /**
     * @param arg0
     * @throws IOException
     * @see java.io.FilterOutputStream#write(byte[])
     */
    public void write(byte[] arg0) throws IOException {
        getOut().write(arg0);
    }

    /**
     * @param arg0
     * @see java.io.PrintStream#write(int)
     */
    public void write(int arg0) {
        getOut().write(arg0);
    }
}
