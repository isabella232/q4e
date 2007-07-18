/*****************************************************************************
 * Copyright (c) 2006-2007, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 *****************************************************************************/
package org.eclipse.buckminster.maven.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.model.Repository;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.buckminster.core.CorePlugin;
import org.eclipse.buckminster.core.RMContext;
import org.eclipse.buckminster.core.cspec.model.ComponentRequest;
import org.eclipse.buckminster.core.materializer.MaterializationContext;
import org.eclipse.buckminster.core.metadata.model.Resolution;
import org.eclipse.buckminster.core.query.model.ComponentQuery;
import org.eclipse.buckminster.core.reader.IComponentReader;
import org.eclipse.buckminster.core.reader.IVersionFinder;
import org.eclipse.buckminster.core.resolver.NodeQuery;
import org.eclipse.buckminster.core.rmap.model.Provider;
import org.eclipse.buckminster.core.rmap.model.ResourceMap;
import org.eclipse.buckminster.core.rmap.model.SearchPath;
import org.eclipse.buckminster.core.version.IVersionSelector;
import org.eclipse.buckminster.core.version.ProviderMatch;
import org.eclipse.buckminster.core.version.VersionSelectorType;
import org.eclipse.buckminster.runtime.MonitorUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * @author Thomas Hallgren
 *
 */
public class Maven2ReaderType extends AbstractMavenReaderType
{
    public Maven2ReaderType()
    {
        super( new LocalCache(MavenManager.getMaven().getLocalRepository().getBaseDirectoryPath()) );
    }

	String getMaterializationFolder()
	{
		return "maven2";
	}

    @Override
    /**
     * returns the path where the jar is going to be copied to (eg. <code>$HOME/workspace/.bukminster/maven2/junit/junit/4.2/junit-4.2.jar</code>)
     * TODO do we really need to copy it or just point to the maven local repo location?
     */
    public IPath getMaterializationLocation(Resolution cr, MaterializationContext context, boolean[] optional)
    throws CoreException
    {
        MapEntry ga = getGroupAndArtifact(cr.getProvider(), cr.getRequest());
        IVersionSelector vs = cr.getVersionMatch().getFixedVersionSelector();
        
        Artifact artifact = MavenManager.getMaven().createArtifact( ga.getGroupId(), ga.getArtifactId(),
                                                                    getMavenVersion( vs ), null, "jar" );
        try
        {
            MavenManager.getMaven().resolve( artifact, getRemoteRepositories( context ) );
            String localRepoPath = MavenManager.getMaven().getLocalRepository().getBaseDirectory().getAbsolutePath();
            String filePath = artifact.getFile().getAbsolutePath();
            optional[0] = true;
            return CorePlugin.getDefault().getBuckminsterProjectLocation().append(getMaterializationFolder()).append(filePath.substring( localRepoPath.length() ));
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new CoreException( new Status( IStatus.ERROR, "aorg.eclipse.buckminster.maven", IStatus.OK,
                                                 "Artifact " + e.getGroupId() + "/" + e.getArtifactId() + "/"
                                                     + e.getVersion() + "/" + e.getType() + " was not found.", e ) );
        }
    }

    static List<ArtifactRepository> getRemoteRepositories(RMContext context) throws CoreException
    {
        /* Component name */
        ComponentQuery cquery = context.getComponentQuery();
        ComponentRequest request = cquery.getRootRequest();
        String name = request.getName();

        /* find the project the cquery references in the workspace */
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IResource resource = root.findMember( new Path( name ) );

        IMaven maven = MavenManager.getMaven();
        List<Repository> repositories = null;

        if ( ( resource == null ) || !resource.exists() || !( resource instanceof IContainer ) )
        {
            /* the project is not in the workspace, return the repositories from the cquery */
            // TODO this need to return the repositories from the pom, we need to figure out where is the pom to read it
            ResourceMap resourceMap = ResourceMap.fromURL(cquery.getResourceMapURL());
            repositories = new ArrayList<Repository>();
            for ( SearchPath searchPath : resourceMap.getSearchPaths() )
            {
                for ( Provider provider : searchPath.getProviders() )
                {
                    if ( provider.getReaderType() instanceof Maven2ReaderType )
                    {
                        String uri = provider.getURI( context.getProperties( request ) );
                        Repository repository = new Repository();
                        repository.setId( "rmap.repository" );
                        repository.setUrl( uri );
                        repositories.add( repository );
                    }
                }
            }
        }
        else
        {
            /* find the pom.xml inside the project */
            IContainer container = (IContainer) resource;
            IFile file = container.getFile( new Path( IMavenProject.POM_FILENAME ) );

            File pom = new File( file.getLocationURI() );

            /* get repositories from pom */
            if ( pom.exists() )
            {
                /* Maven 2 project */
                IMavenProject mavenProject = maven.getMavenProject( pom, false );
                repositories = (List<Repository>) mavenProject.getMavenProject().getRepositories();
            }
            else
            {
                throw new CoreException( new Status( IStatus.ERROR, "org.eclipse.buckminster.maven", IStatus.OK,
                                                     "Project \"" + name + "\" is not a Maven 2 project. Missing pom.",
                                                     null ) );
            }
        }

        return maven.createRepositories( repositories );
    }

    @Override
    public IComponentReader getReader(ProviderMatch providerMatch, IProgressMonitor monitor) throws CoreException
    {
        MonitorUtils.complete(monitor);
        return new Maven2Reader(this, providerMatch);
    }

    @Override
    public IVersionFinder getVersionFinder(Provider provider, NodeQuery nodeQuery, IProgressMonitor monitor) throws CoreException
    {
        MonitorUtils.complete(monitor);
        return new Maven2VersionFinder(this, provider, nodeQuery);
    }
    
    static String getMavenVersion(IVersionSelector versionSelector)
    {
        String version = versionSelector.getQualifier();

        /* differenciate between releases and snapshots */
        if ( versionSelector.getType().equals( VersionSelectorType.TAG ) )
        {
            if ( versionSelector.getQualifier() != null )
            {
                /* release */
                version = versionSelector.getQualifier();
            }
            else
            {
                /* snapshot */
                version = versionSelector.getBranchName();
            }
        }
        else if ( versionSelector.getType().equals( VersionSelectorType.LATEST ) )
        {
            version = versionSelector.getBranchName() + "-SNAPSHOT";
        }
        else
        {
            throw new RuntimeException( "Unknown version selector type " + versionSelector.getType() );
        }
        return version;
    }
}
