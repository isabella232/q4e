/*****************************************************************************
 * Copyright (c) 2006-2007, Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 *****************************************************************************/
package org.eclipse.buckminster.maven.internal;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.OverConstrainedVersionException;
import org.devzuz.q.maven.embedder.IMaven;
import org.devzuz.q.maven.embedder.MavenManager;
import org.eclipse.buckminster.core.resolver.NodeQuery;
import org.eclipse.buckminster.core.rmap.model.Provider;
import org.eclipse.buckminster.core.version.IVersion;
import org.eclipse.buckminster.core.version.IVersionDesignator;
import org.eclipse.buckminster.core.version.VersionFactory;
import org.eclipse.buckminster.core.version.VersionMatch;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * @author Thomas Hallgren
 */
public class Maven2VersionFinder extends AbstractMavenVersionFinder
{
    private final MapEntry m_mapEntry;

    private List<ArtifactRepository> remoteRepositories;

    private IMaven maven;

	public Maven2VersionFinder(Maven2ReaderType readerType, Provider provider, NodeQuery query)
	throws CoreException
	{
        m_mapEntry = Maven2ReaderType.getGroupAndArtifact(provider, query.getComponentRequest());
        maven = MavenManager.getMaven();
        remoteRepositories = Maven2ReaderType.getRemoteRepositories( query.getContext() );
	}

    private IMaven getMaven()
    {
        return maven;
    }

    /**
     * Returns an array of versions known to this repository.
     * 
     * @return known versions or <code>null</code> if not applicable.
     * @throws CoreException
     */
    List<VersionMatch> getComponentVersions(IVersionQuery query, IProgressMonitor monitor) throws CoreException
    {
        IVersionDesignator designator = query.getDesignator();
        if(designator.getVersion().getType().equals(VersionFactory.OSGiType))
        {
            // Convert the OSGi version to a Triplet version instead.
            //
            designator = VersionFactory.createDesignator(VersionFactory.TripletType, designator.toString());
            query = VersionSelectorFactory.createQuery(query.getConverter(), designator);
        }

        Artifact artifact = getMaven().createArtifact( m_mapEntry.getGroupId(), m_mapEntry.getArtifactId(),
                                                       getMavenVersion( designator.getVersion() ), null, "jar" );

        /* this returns the versions listed in the maven-metadata.xml that are not always up to date */
        List<ArtifactVersion> artifactVersions = getMaven().getArtifactVersions( artifact, remoteRepositories );
        List<ArtifactVersion> availableVersions = new ArrayList<ArtifactVersion>( artifactVersions );;

        /* check explicitly for the version as Maven would do, ignoring the maven-metadata.xml */
        try
        {
            // TODO should we just check if it exist instead of downloading it in case we don't need it later?
            getMaven().resolve( artifact, remoteRepositories );
            availableVersions.add( artifact.getSelectedVersion() );
        }
        catch ( ArtifactNotFoundException e )
        {
            // do nothing
        }
        catch ( OverConstrainedVersionException e )
        {
            /* this should never happen */
            throw new CoreException( new Status( IStatus.ERROR, "org.eclipse.buckminster.maven", IStatus.OK,
                                                 "Unknown error, version " + artifact.getVersion()
                                                     + " was explicit but Maven says it is overconstrained", e ) );
        }

        List<VersionMatch> versions = new ArrayList<VersionMatch>( availableVersions.size() );

        for ( ArtifactVersion artifactVersion : availableVersions )
        {
            VersionMatch versionMatch = MavenComponentType.createVersionMatch( artifactVersion.toString(), null );
            IVersion version = versionMatch.getVersion();

            /* if the selector is LATEST then it's a snapshot */
            /* this will fail in ResourceMap:146 if(!versionDesignator.designates(version)) */
//            if (versionMatch.getFixedVersionSelector().getType().equals(VersionSelectorType.LATEST))
//            {
//                version = VersionFactory.StringType.fromString(version.toString() + "-SNAPSHOT");
//            }
            if ( versionMatch != null && query.matches( version ) )
                versions.add( versionMatch );
        }

        return versions;
    }

    /**
     * Get the maven version for a {@link IVersion} object
     */
    static String getMavenVersion( IVersion version )
    {
        return version.toString();
    }
}
