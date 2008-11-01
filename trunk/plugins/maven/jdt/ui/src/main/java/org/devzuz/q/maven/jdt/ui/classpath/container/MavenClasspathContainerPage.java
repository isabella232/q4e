/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.jdt.ui.classpath.container;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class MavenClasspathContainerPage
    extends WizardPage
    implements IClasspathContainerPage, IClasspathContainerPageExtension
{

    public MavenClasspathContainerPage()
    {
        super( "Apache Maven Classpath Container" );
    }

    public void createControl( Composite parent )
    {
        // TODO Auto-generated method stub

    }

    public boolean finish()
    {
        return true;
    }

    public IClasspathEntry getSelection()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void setSelection( IClasspathEntry containerEntry )
    {
        // TODO Auto-generated method stub

    }

    public void initialize( IJavaProject project, IClasspathEntry[] currentEntries )
    {
        // TODO Auto-generated method stub

    };

}
