/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.pom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Entry point for pom modification. <br>
 * Usage:. <br>
 * <br>
 * <code>
 * Dom4JModel root = new Dom4JModel(file);<br>
 * ModelRoot model = root.getRoot();<br>
 * ... perform model modifications<br>
 * root.write();<br>
 * </code><br>
 * Repeated calls to the same object in the heirarcy will return new instances of the wrapper classes, however each
 * instance of the wrapper class will contain a reference to the same dom4j element.<br>
 * </code>
 * 
 * @author jake pezaro
 */
public class Dom4JModel
{

    private File file;

    private Document document;

    private ModelRoot model;

    public Dom4JModel( File file )
    {
        this.file = file;
        SAXReader xmlReader = new SAXReader();
        try
        {
            document = xmlReader.read( this.file );
            model = new ModelRoot( document.getRootElement() );
        }
        catch ( DocumentException e )
        {
            throw new InvalidModelException( e );
        }
    }

    public ModelRoot getModel()
    {
        return model;
    }

    public void write()
        throws IOException
    {
        XMLWriter writer = null;
        try
        {
            writer = new XMLWriter( new BufferedWriter( new FileWriter( file ) ) );
            writer.write( document );
            writer.flush();
        }
        finally
        {
            if ( writer != null )
            {
                writer.close();
            }
        }
    }
}
