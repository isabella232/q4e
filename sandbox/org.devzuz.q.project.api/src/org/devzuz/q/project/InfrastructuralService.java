package org.devzuz.q.project;

import java.net.URI;
import java.util.Map;

/**
 * @model
 */
public interface InfrastructuralService {
	
	/**
	 * @model
	 */
	InfrastructuralServiceId getServiceId();
	
	/**
	 * @model keyType="java.net.URI" valueType="InfrastructuralServiceInterface"
	 */
	Map<URI, InfrastructuralServiceInterface> getSubmitInterfaces();
	
	/**
	 * @model keyType="java.net.URI" valueType="InfrastructuralServiceInterface"
	 */
	Map<URI, InfrastructuralServiceInterface> getQueryInterfaces();
	
	/**
	 * @model keyType="java.net.URI" valueType="InfrastructuralServiceInterface"
	 */
	Map<URI, InfrastructuralServiceInterface> getBrowseInterfaces();
	
	/**
	 * @model keyType="java.net.URI" valueType="InfrastructuralServiceInterface"
	 */
	Map<URI, InfrastructuralServiceInterface> getMaintenanceInterfaces();
	
	/**
	 * @model
	 */
	String getServiceName();
	
	/**
	 * @model
	 */
	String getServiceDescription();
	
}
