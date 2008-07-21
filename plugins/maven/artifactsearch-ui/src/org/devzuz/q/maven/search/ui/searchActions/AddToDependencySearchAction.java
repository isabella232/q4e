package org.devzuz.q.maven.search.ui.searchActions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.devzuz.q.maven.embedder.MavenUtils;
import org.devzuz.q.maven.search.IArtifactInfo;
import org.devzuz.q.maven.ui.dialogs.MavenProjectSelectionDialog;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

public class AddToDependencySearchAction implements ISearchAction
{
    public String getName()
    {
        return "Add as project dependency";
    }
    
    public void doAction( Set<IArtifactInfo> selectedArtifacts )
    {
        MavenProjectSelectionDialog dialog = new MavenProjectSelectionDialog( PlatformUI.getWorkbench().
                                                                              getActiveWorkbenchWindow().getShell() );
        
        IProject[] projects = dialog.getSelectedProjects();
        for( IProject project : projects )
        {
            IFile pomFile = project.getFile( IMavenProject.POM_FILENAME );
            if( pomFile.exists() )
            {
                try
                {
                    File pom = pomFile.getLocation().toFile();
                    List<Dependency> dependencies = MavenUtils.getDependenciesFromPom( pom );
                    
                    for( IArtifactInfo artifactInfo : selectedArtifacts )
                    {
                        Dependency dependency = new Dependency();
                        
                        dependency.setGroupId( artifactInfo.getGroupId() );
                        dependency.setArtifactId( artifactInfo.getArtifactId() );
                        dependency.setVersion( nullIfBlank( artifactInfo.getVersion() ) );
                        
                        dependencies.add( dependency );
                    }
                    
                    MavenUtils.rewritePomWithNewDependencies( pom , dependencies );
                    pomFile.refreshLocal( IResource.DEPTH_ZERO, null );
                }
                catch( IOException ioEx )
                {
                    // show error dialog 
                    ioEx.printStackTrace();
                }
                catch( XmlPullParserException ppEx )
                {
                    // show error dialog
                    ppEx.printStackTrace();
                }
                catch( CoreException coreEx )
                {
                    // show error dialog
                    coreEx.printStackTrace();
                }
            }
        }
    }

    private static String nullIfBlank( String str )
    {
        return ( str == null || str.equals( "" ) ) ? null : str;
    }
}
