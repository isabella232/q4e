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

import org.devzuz.q.maven.embedder.internal.EclipseMavenEventPropagator;

public class EclipseMavenEventPropagatorPrintStream extends PrintStream {
    private static final String LS = System.getProperty("line.separator");

    private StringBuffer sb = new StringBuffer();

    private EclipseMavenEventPropagator eventPropagator;

    public EclipseMavenEventPropagatorPrintStream(EclipseMavenEventPropagator eventPropagator, PrintStream out) {
        super(out);
        this.eventPropagator = eventPropagator;
    }

    protected EclipseMavenEventPropagator getEventPropagator() {
        return eventPropagator;
    }

    protected void log(String s) {
        getEventPropagator().info(s);
    }

    @Override
    public void print(String arg0) {
        sb.append(arg0);
    }

    @Override
    public void println(String arg0) {
        sb.append(arg0);
        String line = sb.toString();
        if (line.endsWith(LS)) {
            line = line.substring(0, line.length() - LS.length());
        }
        log(line);
        sb = new StringBuffer();
    }

    @Override
    public PrintStream append(char arg0) {
        return append(new String(new char[] { arg0 }));
    }

    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        print(csq.subSequence(start, end).toString());
        return this;
    }

    @Override
    public PrintStream append(CharSequence arg0) {
        return append(arg0, 0, arg0.length() - 1);
    }

    @Override
    public boolean checkError() {
        return false;
    }

    protected void clearError() {
    }

    @Override
    public void close() {
        println();
    }

    @Override
    public void flush() {
        println();
    }

    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        return this;
    }

    @Override
    public PrintStream format(String arg0, Object... arg1) {
        return this;
    }

    @Override
    public void print(boolean arg0) {
        print(new Boolean(arg0).toString());
    }

    @Override
    public void print(char arg0) {
        print(new String(new char[] { arg0 }));
    }

    @Override
    public void print(char[] arg0) {
        print(new String(arg0));
    }

    @Override
    public void print(double arg0) {
        print(new Double(arg0).toString());
    }

    @Override
    public void print(float arg0) {
        print(new Float(arg0).toString());
    }

    @Override
    public void print(int arg0) {
        print(new Integer(arg0).toString());
    }

    @Override
    public void print(long arg0) {
        print(new Long(arg0).toString());
    }

    @Override
    public void print(Object arg0) {
        print(arg0.toString());
    }

    @Override
    public PrintStream printf(Locale arg0, String arg1, Object... arg2) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintStream printf(String arg0, Object... arg1) {
        return printf(Locale.getDefault(), arg0, arg1);
    }

    @Override
    public void println() {
        println("");
    }

    @Override
    public void println(boolean arg0) {
        println(new Boolean(arg0).toString());
    }

    @Override
    public void println(char arg0) {
        println(new String(new char[] { arg0 }));
    }

    @Override
    public void println(char[] arg0) {
        println(new String(arg0));
    }

    @Override
    public void println(double arg0) {
        println(new Double(arg0).toString());
    }

    @Override
    public void println(float arg0) {
        println(new Float(arg0).toString());
    }

    @Override
    public void println(int arg0) {
        println(new Integer(arg0).toString());
    }

    @Override
    public void println(long arg0) {
        println(new Long(arg0).toString());
    }

    @Override
    public void println(Object arg0) {
        println(arg0.toString());
    }

    @Override
    protected void setError() {
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        print(new String(buf, off, len));
    }

    @Override
    public void write(int arg0) {
        print((char) arg0);
    }

    @Override
    public void write(byte[] buf) throws IOException {
        write(buf, 0, buf.length);
    }

}
