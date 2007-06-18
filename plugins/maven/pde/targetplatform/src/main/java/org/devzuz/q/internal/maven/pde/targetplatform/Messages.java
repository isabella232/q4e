/*
 * Copyright (c) 2005-2006 Simula Labs and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at:
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Simula Labs - initial API and implementation
 * 
 */
package org.devzuz.q.internal.maven.pde.targetplatform;

import org.eclipse.osgi.util.NLS;

public class Messages
    extends NLS
{
    private static final String BUNDLE_NAME = "org.devzuz.q.internal.maven.pde.targetplatform.messages"; //$NON-NLS-1$

    public static String MavenProvisionerWizard_WizardWindowTitle;

    public static String MavenProvisionerWizardPage_AddButton;

    public static String MavenProvisionerWizardPage_DirectoryDialogMessage;

    public static String MavenProvisionerWizardPage_DirectoryListLabel;

    public static String MavenProvisionerWizardPage_RemoveButton;

    public static String MavenProvisionerWizardPage_WizardPageDescription;

    public static String MavenProvisionerWizardPage_WizardPageTitle;
    static
    {
        // initialize resource bundle
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    private Messages()
    {
    }
}
