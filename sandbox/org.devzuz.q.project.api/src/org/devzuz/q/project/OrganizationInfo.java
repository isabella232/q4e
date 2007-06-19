package org.devzuz.q.project;

import java.net.URL;

/**
 * @model
 */
public interface OrganizationInfo {
	
	/**
	 * @model
	 * @return Main Website URL for the organization running this project.
	 */
	URL getWebSite();
	
	/**
	 * @model
	 * @return User-friendly name of the organization.
	 */
	String getOrganizationName();

}
