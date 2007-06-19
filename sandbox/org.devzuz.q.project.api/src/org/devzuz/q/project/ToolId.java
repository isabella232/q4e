package org.devzuz.q.project;

import java.net.URI;

/**
 * Unique identifier for a tool configuration within the current project.
 * 
 * @author jdcasey
 * @model
 */
public interface ToolId {
	
	/**
	 * @model
	 * @return Standard tool-type URI
	 */
	URI getTypeURI();
	
	/**
	 * @model
	 * @return Qualifier identifying the specific instance of this tool type within the project
	 */
	String getTypeQualifier();

}
