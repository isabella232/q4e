/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.pomxmleditor.contentassist;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.project.MavenProject;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.devzuz.q.maven.search.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.internal.contentassist.XMLContentAssistProcessor;

/**
 * Provides autocompletion for properties in POM files.  Triggered by use of ${.
 * @author Mike Poindexter
 *
 */
public class POMPropertyContentAssistProcessor
    extends XMLContentAssistProcessor
{
    @Override
    protected void addTagInsertionProposals( ContentAssistRequest contentAssistRequest, int childPosition )
    {
        String context = ContentAssistUtils.computeContextString( contentAssistRequest );
        int propStart = context.indexOf( "${" );
        if( propStart > -1 )
        {
            String propCtx = context.substring( propStart + 2 ); 
            LinkedList<String> propKeys = new LinkedList<String>();
            Properties projectProperties = getProjectProperties( contentAssistRequest );
            if( projectProperties != null ) 
            {
                for ( Object key : projectProperties.keySet() )
                {
                    String keyTxt = "" + key;
                    if( keyTxt.startsWith( propCtx ) )
                    {
                        propKeys.add( keyTxt );
                    }
                }
            }
            
            
            for( String propName : getProjectReflectiveProperties() )
            {
                if( propName.startsWith( propCtx ) )
                {
                    propKeys.add( propName );
                }
            }
            
            Collections.sort( propKeys );
            
            for( String proposal : new LinkedHashSet<String>( propKeys ) )
            {
                
                contentAssistRequest.getProposals().add( new CompletionProposal( proposal, contentAssistRequest.getReplacementBeginPosition() - propCtx.length(), propCtx.length(), proposal.length() ) );
            }

        }
        super.addTagInsertionProposals( contentAssistRequest, childPosition );

    }
    
    /**
     * Gets the properties defined in a project.
     * @param request
     * @return
     */
    private Properties getProjectProperties( ContentAssistRequest request )
    {
        IMavenProject mavenProject = getMavenProject( request );
        Properties props = null;
        if( mavenProject != null ) 
        {
            props = mavenProject.getRawMavenProject().getProperties();
        }
        return props;
    }
    
    /**
     * Get the maven project a request belongs to
     * @param request
     * @return
     */
    private IMavenProject getMavenProject( ContentAssistRequest request ) 
    {
        IMavenProject mavenProject = null;
        try
        {
            IProject project = getProject( request );
            if( project != null)
            {
                mavenProject = MavenManager.getMavenProjectManager().getMavenProject( project, true );
            }
        }
        catch ( CoreException e )
        {
            Activator.getLogger().error( "Get maven project: " +e.getMessage() );
        }
        return mavenProject;
    }

    /**
     * Gets the project a request belongs to.
     * @param request
     * @return
     */
    private IProject getProject( ContentAssistRequest request )
    {
        IProject project = null;
        String baselocation = null;

        if ( request != null )
        {
            IStructuredDocumentRegion region = request.getDocumentRegion();
            if ( region != null )
            {
                IDocument document = region.getParentDocument();
                IStructuredModel model = null;
                try
                {
                    model =
                        org.eclipse.wst.sse.core.StructuredModelManager.getModelManager().getExistingModelForRead(
                                                                                                                   document );
                    if ( model != null )
                    {
                        baselocation = model.getBaseLocation();
                    }
                }
                finally
                {
                    if ( model != null )
                    {
                        model.releaseFromRead();
                    }
                }
            }
        }

        if ( baselocation != null )
        {
            // copied from JSPTranslationAdapter#getJavaProject
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            IPath filePath = new Path( baselocation );
            if ( filePath.segmentCount() > 0 )
            {
                IResource resource = root.getFile( filePath );
                if ( resource != null && resource.exists() )
                {
                    project = resource.getProject();
                }
            }
        }
        return project;
    }
    
    private static volatile Set<String> cachedProjectReflectiveProperties;
    /**
     * Gets the properties available by reflecting on the maven Project object.
     * @return
     */
    private Set<String> getProjectReflectiveProperties() 
    {
        try
        {
            if( cachedProjectReflectiveProperties != null )
            {
                return cachedProjectReflectiveProperties;
            }
            
            cachedProjectReflectiveProperties = new LinkedHashSet<String>( getClassProperties( "project.", MavenProject.class, null ) );
            return cachedProjectReflectiveProperties;
        }
        catch ( IntrospectionException e )
        {
            Activator.getLogger().error( "Cannot compute project object properties: " +e.getMessage() );
            return Collections.emptySet();
        }
    }
    
    private Collection<String> getClassProperties( String prefix, Class propClass, Class ignoredClass ) throws IntrospectionException
    {
        List<String> props = new ArrayList<String>();
        BeanInfo beanInfo = java.beans.Introspector.getBeanInfo( propClass );
        for( PropertyDescriptor pd : beanInfo.getPropertyDescriptors() )
        {
            if( pd.getWriteMethod() != null && pd.getReadMethod() != null && !Object.class.equals( pd.getReadMethod().getDeclaringClass() ) )
            {
                String thisPropName = prefix + pd.getName();
                props.add( thisPropName );
                if( ignoredClass == null || !ignoredClass.equals( pd.getPropertyType() ) ) 
                {
                    props.addAll( getClassProperties( thisPropName + ".", pd.getPropertyType(), propClass ) );
                }
            }
        }
        return props;
    }
}
