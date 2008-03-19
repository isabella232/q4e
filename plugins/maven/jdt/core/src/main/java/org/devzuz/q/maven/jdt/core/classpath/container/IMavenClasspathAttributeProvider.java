package org.devzuz.q.maven.jdt.core.classpath.container;

import java.util.Set;

import org.devzuz.q.maven.embedder.IMavenArtifact;
import org.devzuz.q.maven.embedder.IMavenProject;
import org.eclipse.jdt.core.IClasspathAttribute;

/**
 * This interface allows third parties to provide additional attributes for the maven classpath container and the
 * resolved classpath entries.
 * 
 * Classpath attribute names should be scoped to avoid name clashes. If two attributes are contributed with the same
 * name and different values, the assigned value is unspecified.
 * 
 * @author amuino
 */
public interface IMavenClasspathAttributeProvider
{
    /**
     * Obtains the set of attributes that are contributed to the maven classpath containerr by this attribute provider
     * for the given project.
     * 
     * @param mavenProject
     *            the project whose classpath container is being updated.
     * @return the <b>not null</b> set of artifacts. Might be empty.
     */
    Set<IClasspathAttribute> getExtraAttributesForContainer( IMavenProject mavenProject );

    /**
     * Obtains the set of attributes that are contributed to the classpath entry representing the artifact resolved by
     * this attribute provider for the given project.
     * 
     * @param mavenProject
     *            the project whose classpath container is being updated.
     * @param artifact
     *            the resolved artifact.
     * @param isInWorkspace
     *            <code>true</code> if the artifact has been resolved to a project in the user's workspace,
     *            <code>false</code> if it has been resolved from the repository.
     * 
     * @return the <b>not null</b> set of artifacts. Might be empty.
     */
    Set<IClasspathAttribute> getExtraAttributesForArtifact( IMavenProject mavenProject, IMavenArtifact artifact,
                                                            boolean isInWorkspace );
}
