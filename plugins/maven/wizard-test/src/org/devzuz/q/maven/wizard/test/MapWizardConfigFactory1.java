package org.devzuz.q.maven.wizard.test;

import java.util.HashMap;

import org.devzuz.q.maven.wizard.config.core.IWizardConfigFactory;
import org.eclipse.core.runtime.CoreException;

public class MapWizardConfigFactory1 implements IWizardConfigFactory
{

    public Object create() throws CoreException
    {
        return new HashMap<String, String>();
    }
}
