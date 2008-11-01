/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenCoreActivator;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;

public class EclipseMavenProjectAdapterFactory
    implements IAdapterFactory
{

    private static final Class[] types = { IMavenProject.class, };

    public Object getAdapter( Object adaptableObject, Class adapterType )
    {
        if ( adapterType == IMavenProject.class )
        {
            IProject project;

            if ( adaptableObject instanceof IProject )
            {
                project = (IProject) adaptableObject;
            }
            else if ( adaptableObject instanceof IFile )
            {
                project = ( (IFile) adaptableObject ).getProject();
            }
            else
            {
                return null;
            }

            try
            {
                return MavenManager.getMavenProjectManager().getMavenProject( project, true );
            }
            catch ( CoreException e )
            {
                MavenCoreActivator.getDefault().getMavenExceptionHandler().handle( project, e );
            }
        }
        return null;
    }

    public Class[] getAdapterList()
    {
        return types;
    }

}
