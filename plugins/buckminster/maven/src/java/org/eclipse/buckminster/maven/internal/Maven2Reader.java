/*******************************************************************************
 * Copyright (c) 2004, 2006
 * Thomas Hallgren, Kenneth Olwing, Mitch Sonies
 * Pontus Rydin, Nils Unden, Peer Torngren
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the individual
 * copyright holders listed above, as Initial Contributors under such license.
 * The text of such license is available at www.eclipse.org.
 *******************************************************************************/
package org.eclipse.buckminster.maven.internal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.eclipse.buckminster.core.helpers.BuckminsterException;
import org.eclipse.buckminster.core.version.ProviderMatch;
import org.eclipse.buckminster.maven.embedder.IMavenProject;
import org.eclipse.buckminster.maven.embedder.MavenManager;
import org.eclipse.buckminster.runtime.MonitorUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * The URL used by the Maven2Reader denotes the group directory within one
 * specific repository. The format must be <br/>
 * <code>[&lt;schema&gt;][//&lt;authority&gt;]&lt;path to group&gt;#&lt;artifact&gt;</code><br/>
 * The ability to search trhough multiple repositories is obtained by using the
 * <code>SearchPath</code> or the <code>ResourceMap</code>. The
 * 
 * @author Thomas Hallgren
 */
public class Maven2Reader extends AbstractMavenReader
{
    private final MapEntry m_mapEntry;

    private final Artifact m_artifact;

    public Maven2Reader(Maven2ReaderType readerType, ProviderMatch rInfo) throws CoreException
    {
        super( readerType, rInfo );
        m_mapEntry = AbstractMavenReaderType.getGroupAndArtifact( rInfo.getProvider(), rInfo.getNodeQuery()
            .getComponentRequest() );

        String version = Maven2ReaderType.getMavenVersion( getVersionSelector() );

        m_artifact = MavenManager.getMaven().createArtifact( m_mapEntry.getGroupId(), m_mapEntry.getArtifactId(),
                                                             version, null, "jar" );
    }

    private Artifact getArtifact()
    {
        return m_artifact;
    }

    private Artifact getResolvedArtifact()
        throws CoreException
    {
        if ( !getArtifact().isResolved() )
        {
            try
            {
                MavenManager.getMaven().resolve( getArtifact(),
                                                 Maven2ReaderType.getRemoteRepositories( getNodeQuery().getContext() ) );
            }
            catch ( ArtifactNotFoundException e )
            {
                throw new CoreException( new Status( IStatus.ERROR, "org.eclipse.buckminster.maven", IStatus.OK,
                                                     "Artifact " + e.getGroupId() + "/" + e.getArtifactId() + "/"
                                                         + e.getVersion() + "/" + e.getType() + " was not found.", e ) );
            }
        }
        return m_artifact;
    }

    @Override
    /**
     * Return the URL of the jar in the local repository, eg. <code>file://$HOME/.m2/repository/junit/junit/4.2/junit-4.2.jar</code>
     */
    public URL getURL() throws CoreException
    {
        URL url = null;
        try
        {
            url = getResolvedArtifact().getFile().toURI().toURL();
        }
        catch ( MalformedURLException e )
        {
            throw BuckminsterException.wrap( e );
        }

        return url;
    }

    @Override
    public InputStream open(IProgressMonitor monitor) throws CoreException, IOException
    {
        return new FileInputStream( getResolvedArtifact().getFile() );
    }

    IMavenProject getProject(IProgressMonitor monitor) throws CoreException
    {
        IMavenProject project = null;
        try
        {
            project = MavenManager.getMaven().getMavenProject( getResolvedArtifact(), Maven2ReaderType.getRemoteRepositories(getNodeQuery().getContext()));
            MonitorUtils.worked( monitor, 500 );
        }
        catch ( CoreException e )
        {
            throw BuckminsterException.wrap( e );
        }

        return project;
    }

    public String toString()
    {
        return getArtifact().toString();
    }
}
