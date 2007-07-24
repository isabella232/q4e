/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.IMavenListener;

public class FileMavenListener implements IMavenListener {

    private static final String LS = System.getProperty("line.separator");

    private File log = new File("maven.log");

    private BufferedWriter os;

    private void openFile() {
        try {
            os = new BufferedWriter(new FileWriter(log));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleEvent(IMavenEvent event) {
        if (os == null) {
            openFile();
        }
        try {
            os.write(event.getCreatedDate() + " : " + event.getTypeText() + " : " + event.getDescriptionText() + LS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispose() {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                // nothing that can be done
            }
        }
    }
}
