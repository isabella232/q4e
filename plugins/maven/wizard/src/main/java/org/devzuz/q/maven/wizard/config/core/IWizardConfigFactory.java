package org.devzuz.q.maven.wizard.config.core;

import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.eclipse.core.runtime.CoreException;

/**
 * This interface defines a factory of config objects that are specific to
 * implementations of the {@link IMavenWizardPage}.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public interface IWizardConfigFactory {

	/**
	 * Creates a new archetype processor configuration object. The new
	 * configuration object should ideally be populated with reasonable
	 * defaults.
	 * 
	 * @return a new archetype processor configuration object
	 * @throws CoreException
	 *             if failed while creating the configuration object
	 */
	Object create() throws CoreException;
}
