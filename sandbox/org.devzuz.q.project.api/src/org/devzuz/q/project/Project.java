package org.devzuz.q.project;

import java.util.List;
import java.util.Map;

/**
 * Represents a project. Contains a set of extensible common sections 
 * for core information about the project itself, the project's owner-organization,
 * and community infrastructure, along with an extensible section for tools which
 * should be used by automation during the project's development lifecycle.
 * 
 * @author jdcasey
 * @model
 */
public interface Project {
	
	/**
	 * @model
	 * @return Unique identification for this instance of this project. Contains version 
	 * information in addition to unique identification for the project among a 
	 * set of projects.
	 */
	ProjectKey getIdentification();
	
	/**
	 * @model
	 * @return An abstract parent project, which can be used to house common configuration
	 *   or other information common to a set of projects.
	 */
	ProjectKey getParentProject();
	
	/**
	 * @model
	 * @return Information about the organization that owns and runs this project, including
	 *   organization name, and main website.
	 */
	OrganizationInfo getOrganizationInfo();
	
	/**
	 * @model
	 * @return Information about the community infrastructure dedicated to supporting this
	 *   project.
	 */
	CommunityInfo getCommunityInfo();
	
	/**
	 * @model type="License"
	 * @return List of licensing information that applies to this project, along 
	 *   with specific information about where each license applies within the project
	 *   (eg. to a bundled dependency vs. the project classes).
	 */
	List<License> getLicenses();
	
	/**
	 * @model type="ProjectClassification"
	 * @return Information about how this project fits within a set of classification systems
	 *   (ie. applicable tags, or place in a rigid taxonomy)
	 */
	List<ProjectClassification> getProjectClassifications();
	
	/**
	 * @model
	 * @return Information about what the project artifact is composed of, possibly
	 *   including jar manifest, SCM revision numbers, specifications satisfied, etc.
	 */
	ArtifactComposition getArtifactComposition();
	
	/**
	 * @model type="Dependency"
	 * @return List of artifacts upon which this project's source code dependends, 
	 *   in one way or another.
	 */
	List<Dependency> getDependencies();
	
	/**
	 * @model keyType="ToolId" valueType="ToolConfiguration"
	 * @return mapping of external tool configurations used during the project's 
	 *   development cycle, keyed by the tool id (which may contain a tool-instance 
	 *   qualifier)
	 */
	Map<ToolId, ToolConfiguration> getToolConfigurationByType();
	
	/**
	 * @model type="Person"
	 */
	List<Person> getContributors();
	
	/**
	 * @model type="Person"
	 */
	List<Person> getDevelopers();
	
}
