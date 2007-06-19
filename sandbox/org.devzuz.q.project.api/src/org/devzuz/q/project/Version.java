package org.devzuz.q.project;

import java.net.URI;

/**
 * @model
 * @author jdcasey
 */
public interface Version {
	
	/**
	 * @model
	 */
	URI getVersionScheme();
	
	/**
	 * @model
	 */
	String getValue();

}
