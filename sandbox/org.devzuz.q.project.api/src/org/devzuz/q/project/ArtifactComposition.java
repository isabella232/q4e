package org.devzuz.q.project;

import java.net.URI;
import java.util.Map;

/**
 * Contains information about the contents of the project artifact, and its format.
 * 
 * @author jdcasey
 * @model
 */
public interface ArtifactComposition {
	
	/**
	 * @return URI referencing a particular artifact type (eg. OSGi bundle, 
	 *   EJB jar, "vanilla" jar, .so file, etc.)
	 *   
	 * @model
	 */
	URI getArtifactType();
	
	/**
	 * @return mapping of manifest information about the project artifact, 
	 *   keyed by manifest type URI.
	 * 
	 * @model keyType="java.net.URI" valueType="ArtifactManifestPart"
	 */
	Map<URI, ArtifactManifestPart> getArtifactManifestPartsByType();

}
