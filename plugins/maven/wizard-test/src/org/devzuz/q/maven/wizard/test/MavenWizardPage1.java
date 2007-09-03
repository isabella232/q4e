/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.test;

import org.devzuz.q.maven.wizard.pages.ui.AbstractMavenWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * TODO Document
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class MavenWizardPage1 extends AbstractMavenWizardPage
{

    /**
     * Test constructor
     */
    public MavenWizardPage1()
    {
        super( "A Test Wizard Page" );
    }

    public void createControl( Composite parent )
    {
        Label l = new Label( parent, SWT.NONE );
        l.setText( "Hello world" );
        setControl( l );
    }

}
