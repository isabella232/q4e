package org.devzuz.q.project;

import java.net.URI;

/**
 * @model
 */
public interface Dependency {
	
	/**
	 * @model
	 */
	URI getDependencyScheme();
	
	/**
	 * @model
	 */
	ProjectKey getProjectKey();
	
	/**
	 * @model
	 */
	ArtifactClassifier getArtifactClassifier();

}
