/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.embedder;

import java.io.File;

import org.apache.maven.model.Model;

/**
 * This class is a javabean-like object for a parsed pom.xml anywhere in the filesystem (even outside the workspace).
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class PomFileDescriptor
{
    private File file;

    private Model model;

    /**
     * Default contructor.
     */
    public PomFileDescriptor()
    {
        super();
    }

    /**
     * Full constructor.
     * 
     * @param file
     *            the pom.xml file.
     * @param model
     *            the parsed contents of the pom.xml file.
     */
    public PomFileDescriptor( File file, Model model )
    {
        super();
        this.file = file;
        this.model = model;
    }

    /**
     * Gets the pom.xml file.
     * 
     * @return the file.
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Sets the pom.xml file.
     * 
     * @param file
     *            the file.
     */
    public void setFile( File file )
    {
        this.file = file;
    }

    /**
     * Gets the parsed pom.xml model.
     * 
     * @return the parsed pom.xml model.
     */
    public Model getModel()
    {
        return model;
    }

    /**
     * Sets the parsed pom.xml model.
     * 
     * @param model
     *            the parsed pom.xml model.
     */
    public void setModel( Model model )
    {
        this.model = model;
    }

    /**
     * Utility method to retrieve the folder where the pom.xml file is.
     * 
     * @return the parent folder.
     */
    public File getBaseDirectory()
    {
        return file.getParentFile();
    }
}
