/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages.core.internal;

import java.util.LinkedList;
import java.util.List;

import org.devzuz.q.maven.ui.archetype.provider.Archetype;
import org.devzuz.q.maven.wizard.config.core.IWizardConfigFactory;
import org.devzuz.q.maven.wizard.core.internal.MavenWizardContext;
import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Utility class for working with the wizard extensions provided by the archetype extenders.
 * 
 * It is expected that an instance of this class will be created with the New Maven Project Wizard and will be disposed
 * after all the postprocessors are executed.
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class WizardPageExtensionUtil
{
    private static final String POSTPROCESSOR_ELEMENT = "postprocessor";

    private static final String CONFIG_ATTRIBUTE = "config";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final String ID_ATTRIBUTE = "id";

    private static final String PAGE_ELEMENT = "page";

    private static final String GROUP_ELEMENT = "group";

    private static final String ARTIFACT_ELEMENT = "artifact";

    private static final String ARCHETYPE_ELEMENT = "archetype";

    private static final String CONFIG_FACTORY_ELEMENT = "config-factory";

    public static final String ARCHETYPE_EXTENDER_EXTENSION_POINT_ID = "org.devzuz.q.maven.wizard.archetypeExtender";

    /**
     * Finds the extra wizard pages registered for the given archetype.
     * 
     * @param archetype
     *            the archetype about to be created.
     * @param wizardContext
     *            the mutable context shared by every page of the wizard.
     * @return the list of wizard pages configured with the global wizard context and the page specific configuration.
     * @throws CoreException
     *             if an error is detected accessing the extension registry.
     */
    public static List<IMavenWizardPage> getExtraPages( Archetype archetype, MavenWizardContext wizardContext )
        throws CoreException
    {
        IConfigurationElement[] archetypeSpecs = getConfigurationElements();
        List<IMavenWizardPage> extraPages = new LinkedList<IMavenWizardPage>();
        for ( IConfigurationElement archetypeSpec : archetypeSpecs )
        {
            if ( !ARCHETYPE_ELEMENT.equals( archetypeSpec.getName() ) )
            {
                // Not an archetype element, skip
                continue;
            }
            String artifactSpec = archetypeSpec.getAttribute( ARTIFACT_ELEMENT );
            String groupSpec = archetypeSpec.getAttribute( GROUP_ELEMENT );
            if ( doesArchetypeSpecMatch( artifactSpec, groupSpec, archetype ) )
            {
                IConfigurationElement[] pageSpecs = archetypeSpec.getChildren( PAGE_ELEMENT );
                for ( IConfigurationElement pageSpec : pageSpecs )
                {
                    IMavenWizardPage page = (IMavenWizardPage) pageSpec.createExecutableExtension( CLASS_ATTRIBUTE );
                    String configId = pageSpec.getAttribute( CONFIG_ATTRIBUTE );
                    Object pageConfig = wizardContext.getPageConfig( configId );
                    if ( null == pageConfig )
                    {
                        IWizardConfigFactory factory = getConfigFactory( configId );
                        pageConfig = factory.create();
                    }
                    page.setWizardContext( wizardContext );
                    page.setConfig( pageConfig );
                    wizardContext.setPageConfig( configId, pageConfig );
                    extraPages.add( page );
                }
            }
            // }
        }
        return extraPages;
    }

    /**
     * Finds the postprocessors to be applied after the creation of the maven project is complete.
     * 
     * @param archetype
     *            the archetype that has been created.
     * @param wizardContext
     *            the context with all the information added by the wizard pages.
     * @return the list of postprocessors configured with the global wizard context and the page specific configuration.
     */
    public static List<IMavenProjectPostprocessor> getPostProcessors( Archetype archetype,
                                                                      MavenWizardContext wizardContext )
        throws CoreException
    {
        IConfigurationElement[] archetypeSpecs = getConfigurationElements();
        List<IMavenProjectPostprocessor> postprocessors = new LinkedList<IMavenProjectPostprocessor>();
        for ( IConfigurationElement archetypeSpec : archetypeSpecs )
        {
            if ( !ARCHETYPE_ELEMENT.equals( archetypeSpec.getName() ) )
            {
                // Not an archetype element, skip
                continue;
            }
            String artifactSpec = archetypeSpec.getAttribute( ARTIFACT_ELEMENT );
            String groupSpec = archetypeSpec.getAttribute( GROUP_ELEMENT );
            if ( doesArchetypeSpecMatch( artifactSpec, groupSpec, archetype ) )
            {
                IConfigurationElement[] postprocessorSpecs = archetypeSpec.getChildren( POSTPROCESSOR_ELEMENT );
                for ( IConfigurationElement postprocessorSpec : postprocessorSpecs )
                {
                    IMavenProjectPostprocessor postprocessor =
                        (IMavenProjectPostprocessor) postprocessorSpec.createExecutableExtension( CLASS_ATTRIBUTE );
                    String configId = postprocessorSpec.getAttribute( CONFIG_ATTRIBUTE );
                    postprocessor.setConfig( wizardContext.getPageConfig( configId ) );
                    postprocessor.setWizardContext( wizardContext );
                    postprocessors.add( postprocessor );
                }
            }
        }
        return postprocessors;
    }

    /**
     * Check if the specification given for the artifact and group selector matches the actual archetype.
     * 
     * @param artifactSpec
     *            the archetype artifact selector.
     * @param groupSpec
     *            the archetype group selector.
     * @param archetype
     *            the actual archetype.
     * @return <code>true</code> if the archetype matches the selectors.
     */
    protected static boolean doesArchetypeSpecMatch( String artifactSpec, String groupSpec, Archetype archetype )
    {
        String artifactId = archetype.getArtifactId();
        String groupId = archetype.getGroupId();
        return artifactId.equals( artifactSpec ) && ( null == groupSpec || groupSpec.equals( groupId ) );
    }

    /**
     * Returns a wizard configuration factory registered with the given id.
     * 
     * @param id
     *            the id of the factory.
     * @return a wizard configuration factory.
     */
    public static IWizardConfigFactory getConfigFactory( String id ) throws CoreException
    {
        IConfigurationElement[] configFactorySpecs = getConfigurationElements();
        for ( IConfigurationElement configFactorySpec : configFactorySpecs )
        {
            if ( !CONFIG_FACTORY_ELEMENT.equals( configFactorySpec.getName() ) )
            {
                // Not a config-factory element, skip
                continue;
            }
            String element_id = configFactorySpec.getAttribute( ID_ATTRIBUTE );
            if ( element_id.equals( id ) )
            {
                IWizardConfigFactory configFactory =
                    (IWizardConfigFactory) configFactorySpec.createExecutableExtension( CLASS_ATTRIBUTE );
                return configFactory;
            }
        }
        // TODO: Factory not found. Should at least log a message.
        return null;
    }

    /**
     * Finds all the extensions for the archetype extender extension point.
     * 
     * @return the array of registered {@link IConfigurationElement}s.
     */
    private static IConfigurationElement[] getConfigurationElements()
    {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] extensions =
            registry.getConfigurationElementsFor( ARCHETYPE_EXTENDER_EXTENSION_POINT_ID );
        return extensions;
    }
}