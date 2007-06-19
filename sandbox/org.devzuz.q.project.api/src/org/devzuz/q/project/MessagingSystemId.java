package org.devzuz.q.project;

import java.net.URI;

/**
 * @model
 * @author jdcasey
 */
public interface MessagingSystemId {

	/**
	 * @model
	 * @return Standard messaging system type URI
	 */
	URI getTypeURI();
	
	/**
	 * @model
	 * @return Qualifier identifying the specific instance of this messaging service
	 *   type within the project
	 */
	String getSystemQualifier();
}
