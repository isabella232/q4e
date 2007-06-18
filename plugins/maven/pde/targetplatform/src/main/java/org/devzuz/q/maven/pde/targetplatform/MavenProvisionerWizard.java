/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.maven.pde.targetplatform;

import java.io.File;

import org.devzuz.q.internal.maven.pde.targetplatform.Messages;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.ui.IProvisionerWizard;

public class MavenProvisionerWizard
    extends Wizard
    implements IProvisionerWizard
{

    IMaven embedder;

    MavenProvisionerWizardPage page;

    File[] dirs;

    public MavenProvisionerWizard()
    {
        super();
        setWindowTitle( Messages.MavenProvisionerWizard_WizardWindowTitle );
        embedder = MavenManager.getMaven();
    }

    public void addPages()
    {
        super.addPages();
        page = new MavenProvisionerWizardPage( "maven", embedder.getLocalRepository() ); //$NON-NLS-1$
        addPage( page );
    }

    public boolean canFinish()
    {
        return true;
    }

    public boolean performFinish()
    {
        dirs = page.getLocations();
        return true;
    }

    public File[] getLocations()
    {
        return dirs;
    }

}
