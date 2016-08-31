package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

public interface OrgSpocService {
	
	/**
	 * Add spoc for organization
	 * @param orgSpoc
	 * @return
	 * @throws Exception
	 */
	OrgSpoc addOrgSpoc(OrgSpoc orgSpoc)throws Exception;
	
/**
 * add Vendor contacts
 * @param jsonObj
 * @param user
 * @param orgLocations
 * @throws Exception
 */
	void addVendorContacts(JSONObject jsonObj,User user,OrgLocations orgLocations, String orgRelationId)throws Exception;

	/**
	 * @author Prasad Salina 
	 * To get all the spocs
	 */
	List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId) throws Exception;
	
	/**To get OrgSpoc by ID
	 * @author Prasad Salina
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	OrgSpoc getOrgSpocById(Long Id) throws Exception;

}
