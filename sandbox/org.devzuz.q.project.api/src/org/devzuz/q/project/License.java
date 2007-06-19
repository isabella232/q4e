package org.devzuz.q.project;

import java.net.URL;
import java.util.List;

/**
 * @model
 */
public interface License {
	
	/**
	 * @model
	 * @return The URL where the full text of this license can be found online.
	 *   This is important for people browsing by on the web who want to learn more
	 *   about the project without downloading it.
	 */
	URL getURL();
	
	/**
	 * @model
	 * @return The common name for this license (eg. EPL, GPL, APL)
	 */
	String getLicenseName();
	
	/**
	 * @model type="String"
	 * @return a list of packages, classes, and files (jars?) embedded in the 
	 *   project's artifact to which this license applies.
	 */
	List<String> getApplicability();

}
