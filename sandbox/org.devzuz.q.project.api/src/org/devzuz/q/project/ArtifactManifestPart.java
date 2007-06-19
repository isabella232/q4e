package org.devzuz.q.project;

import java.net.URI;

/**
 * This is meant to describe something about the files contained within the resultant 
 * project artifact. Such information might include:
 * 
 * - SCM revision numbers 
 * - regular jar-manifest information
 * - "provides" annotations (about a set of specifications satisfied by this artifact)
 * - aggregation information (information about which of the projects dependencies are 
 *   embedded within this artifact)
 * - etc.
 * 
 * @author jdcasey
 * 
 * @model
 */
public interface ArtifactManifestPart {
	
	/**
	 * @model
	 */
	URI getType();

}
