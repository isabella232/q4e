/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages;

import org.eclipse.jface.wizard.WizardPage;

public abstract class Maven2ValidatingWizardPage
    extends WizardPage
{
    private String errorString;

    public Maven2ValidatingWizardPage( String name )
    {
        super( name );
    }

    public String getError()
    {
        return errorString;
    }

    protected void setError( String errorString )
    {
        this.errorString = errorString;
        setErrorMessage( getError() );
    }

    protected boolean validate()
    {
        boolean didValidate = isPageValid();
        if ( !didValidate )
        {
            setErrorMessage( getError() );
        }
        else
        {
            onPageValidated();
            setErrorMessage( null );
        }

        setPageComplete( didValidate );

        return didValidate;
    }

    protected boolean isPageValid()
    {
        return false;
    }

    protected void onPageValidated()
    {
    }
}
