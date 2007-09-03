package org.devzuz.q.maven.wizard.test;

import java.util.ArrayList;

import org.devzuz.q.maven.wizard.config.core.IWizardConfigFactory;
import org.eclipse.core.runtime.CoreException;

public class ListWizardConfigFactory implements IWizardConfigFactory
{

    public Object create() throws CoreException
    {
        return new ArrayList<String>();
    }
    // TODO Implement
}
