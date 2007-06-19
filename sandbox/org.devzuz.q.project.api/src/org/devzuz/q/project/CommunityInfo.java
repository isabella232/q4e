package org.devzuz.q.project;

import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @model
 * @author jdcasey
 *
 */
public interface CommunityInfo {
	
	/**
	 * @return Website URL specific to this project.
	 * @model
	 */
	URL getProjectWebSite();

	/**
	 * @return mapping of download URLs keyed by type where type might be 
	 *   update-site, maven-repo, website, etc.
	 * 
	 * @model keyType="java.net.URI" valueType="java.net.URL"
	 */
	Map<URI, URL> getDownloadLocations();
	
	/**
	 * Service-access information for things like:
	 * 
	 * Forum
	 * Mailing List
	 * Wiki
	 * Issue Tracker
	 * 
	 * @return The available community services, keyed by a type URI 
	 *   denoting what sort of service it is
	 */
	Map<InfrastructuralServiceId, InfrastructuralService> getCommunityServicesByType();
	
	/**
	 * @model keyType="String" valueType="InfrastructuralService"
	 * @return mapping of service information for project's the mailing lists, keyed by mailing list id.
	 */
	Map<String, InfrastructuralService> getMailingListServices();
	
	/**
	 * @model
	 * @return service information for retrieving project sources from its SCM instance
	 */
	InfrastructuralService getScmService();
	
	/**
	 * @model
	 * @return service information for accessing the issue tracker for the project
	 */
	InfrastructuralService getIssueTrackerService();
	
}
