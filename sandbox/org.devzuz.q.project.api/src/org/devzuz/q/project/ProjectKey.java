package org.devzuz.q.project;

/**
 * @model
 */
public interface ProjectKey {
	
	/**
	 * @model
	 */
	String getGroupId();
	
	/**
	 * @model
	 */
	String getArtifactId();
	
	/**
	 * @model
	 */
	Version getVersionSpec();

}
