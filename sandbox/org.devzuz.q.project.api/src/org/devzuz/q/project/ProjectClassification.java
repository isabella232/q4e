package org.devzuz.q.project;

import java.net.URI;

/**
 * This is meant to categorize the project in some way, so it can be organized 
 * within a larger set of projects, to help potential users find what they're 
 * looking for. Some examples might be the projects place in a fixed taxonomy,
 * or a set of tags that apply to the project.
 *  
 * @author jdcasey
 * @model
 */
public interface ProjectClassification {
	
	/**
	 * @model
	 */
	URI getClassificationSystemId();

}
