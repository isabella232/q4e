package org.devzuz.q.project;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @model
 * @author jdcasey
 */
public interface Person {
	
	/**
	 * @model
	 */
	String getName();
	
	/**
	 * @model
	 */
	String getEmail();
	
	/**
	 * @model type="String"
	 */
	List<String> getRoles();
	
	/**
	 * @model
	 */
	TimeZone getTimezone();
	
	/**
	 * @model keyType="MessagingSystemId" valueType="String"
	 * @return mapping of the person's nicknames, keyed by the service on 
	 *   which the nickname is used
	 */
	Map<MessagingSystemId, String> getNicknames();

}
