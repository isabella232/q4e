package org.apache.maven.embedder.test;

import junit.framework.TestCase;

public class ClassloaderTestCase extends TestCase {
	public void testAccessMavenArtifact() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.artifact.Artifact");
		assertClassExists("org.apache.maven.artifact.repository.ArtifactRepository");
	}

	public void testAccessMavenCore() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.DefaultMaven");
		assertClassExists("org.apache.maven.execution.BuildFailure");
	}

	public void testAccessMavenLifecycle() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.lifecycle.LifecycleUtils");
		assertClassExists("org.apache.maven.lifecycle.model.Phase");
	}

	public void testAccessMavenModel() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.model.Model");
		assertClassExists("org.apache.maven.model.Dependency");
	}
	
	public void testAccessMavenPluginApi() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.plugin.Mojo");
		assertClassExists("org.apache.maven.plugin.descriptor.PluginDescriptor");
	}

	public void testAccessMavenProfile() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.profiles.Activation");
		assertClassExists("org.apache.maven.profiles.Profile");
	}

	public void testAccessMavenProject() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.project.MavenProject");
		assertClassExists("org.apache.maven.project.artifact.AttachedArtifact");
	}

	public void testAccessMavenReportingApi() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.reporting.MavenReport");
	}

	public void testAccessMavenToolchain() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.toolchain.DefaultToolchain");
		assertClassExists("org.apache.maven.toolchain.Toolchain");
	}

	public void testAccessMavenWorkspace() throws ClassNotFoundException {
		assertClassExists("org.apache.maven.workspace.DefaultMavenWorkspaceStore");
	}
	
	private void assertClassExists(String className)
			throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		assertNotNull("Class should exist: " + className, clazz);
	}
}
