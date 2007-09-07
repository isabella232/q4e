/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.pages.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.devzuz.q.maven.ui.core.archetypeprovider.Archetype;
import org.devzuz.q.maven.wizard.core.internal.MavenWizardContext;
import org.devzuz.q.maven.wizard.pages.core.internal.WizardPageExtensionUtil;
import org.devzuz.q.maven.wizard.pages.ui.IMavenWizardPage;
import org.devzuz.q.maven.wizard.postprocessor.core.IMavenProjectPostprocessor;
import org.devzuz.q.maven.wizard.test.MavenProjectPostprocessor1;
import org.devzuz.q.maven.wizard.test.MavenWizardPage1;
import org.junit.Before;

/**
 * TODO Document
 * 
 * @author Abel Mui–o <amuino@gmail.com>
 */
public class WizardPageExtensionUtilTest extends TestCase
{

    private MavenWizardContext wizardContext;

    private Archetype quickStartArchetype;

    private Archetype appFuseArchetype;

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() throws Exception
    {
        wizardContext = new MavenWizardContext();
        quickStartArchetype =
            new Archetype( "maven-archetype-quickstart", "org.apache.maven.archetypes", null, null, null );
        appFuseArchetype = new Archetype( "appfuse-basic-jsf", "org.appfuse", "2.0-m5", null, null );
    }

    public void testQuickStartGetExtraPages() throws Exception
    {
        List<IMavenWizardPage> extraPages = WizardPageExtensionUtil.getExtraPages( quickStartArchetype, wizardContext );
        assertEquals( "Wrong number of extra pages found", 1, extraPages.size() );
        IMavenWizardPage page = extraPages.get( 0 );
        assertTrue( "Not the right class for page", page instanceof org.devzuz.q.maven.wizard.test.MavenWizardPage1 );
        assertTrue( "Not the right class for context", page.getConfig() instanceof HashMap );
        assertSame( "Wizard context is not set", wizardContext, page.getWizardContext() );
    }

    public void testAppFuseGetExtraPages() throws Exception
    {
        List<IMavenWizardPage> extraPages = WizardPageExtensionUtil.getExtraPages( appFuseArchetype, wizardContext );
        assertEquals( "Wrong number of extra pages found", 2, extraPages.size() );
        IMavenWizardPage page = extraPages.get( 0 );
        assertEquals( "Wrong page class", MavenWizardPage1.class, page.getClass() );
        assertEquals( "Wrong config class", ArrayList.class, page.getConfig().getClass() );
        assertSame( "Wizard context is not set", wizardContext, page.getWizardContext() );
        page = extraPages.get( 1 );
        assertEquals( "Wrong page class", MavenWizardPage1.class, page.getClass() );
        assertEquals( "Wrong config class", HashMap.class, page.getConfig().getClass() );
        assertSame( "Wizard context is not set", wizardContext, page.getWizardContext() );
    }

    /**
     * Postprocessor configuration is not set if pages are not displayed first.
     * 
     * @throws Exception
     */
    public void testGetPostProcessorsNotInitialized() throws Exception
    {
        List<IMavenProjectPostprocessor> postProcessors =
            WizardPageExtensionUtil.getPostProcessors( quickStartArchetype, wizardContext );
        IMavenProjectPostprocessor postProcessor = postProcessors.get( 0 );
        assertNull( "Postprocessor initialized before the page is completed", postProcessor.getConfig() );
        postProcessors = WizardPageExtensionUtil.getPostProcessors( appFuseArchetype, wizardContext );
        postProcessor = postProcessors.get( 0 );
        assertNull( "Postprocessor initialized before the page is completed", postProcessor.getConfig() );
    }

    public void testQuickStartGetPostProcessors() throws Exception
    {
        WizardPageExtensionUtil.getExtraPages( quickStartArchetype, wizardContext );
        List<IMavenProjectPostprocessor> postProcessors =
            WizardPageExtensionUtil.getPostProcessors( quickStartArchetype, wizardContext );
        assertEquals( "Wrong number of post processors found", 2, postProcessors.size() );
        IMavenProjectPostprocessor postProcessor = postProcessors.get( 0 );
        assertEquals( "Wrong postprocessor class", MavenProjectPostprocessor1.class, postProcessor.getClass() );
        assertEquals( "Wrong config class", HashMap.class, postProcessor.getConfig().getClass() );
        postProcessor = postProcessors.get( 1 );
        assertEquals( "Wrong postprocessor class", MavenProjectPostprocessor1.class, postProcessor.getClass() );
        assertNull( "A configuration object leaked to the post processor", postProcessor.getConfig() );
    }

    public void testAppFuseGetPostProcessors() throws Exception
    {
        WizardPageExtensionUtil.getExtraPages( appFuseArchetype, wizardContext );
        List<IMavenProjectPostprocessor> postProcessors =
            WizardPageExtensionUtil.getPostProcessors( appFuseArchetype, wizardContext );
        assertEquals( "Wrong number of post processors found", 1, postProcessors.size() );
        IMavenProjectPostprocessor postProcessor = postProcessors.get( 0 );
        assertEquals( "Wrong postprocessor class", MavenProjectPostprocessor1.class, postProcessor.getClass() );
        assertEquals( "Wrong config class", ArrayList.class, postProcessor.getConfig().getClass() );
    }
}
