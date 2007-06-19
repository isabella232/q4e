package org.devzuz.q.project;

/**
 * Information specific to an external tool, which configures that tool for 
 * use with this project.
 * 
 * @author jdcasey
 * @model
 */
public interface ToolConfiguration {
	
	/**
	 * @model
	 */
	ToolId getToolId();

}
