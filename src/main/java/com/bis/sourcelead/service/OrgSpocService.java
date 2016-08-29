package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import java.util.List;

import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

public interface OrgSpocService {
	
	OrgSpoc addOrgSpoc(OrgSpoc orgSpoc)throws Exception;
	

	void addVendorContacts(JSONObject jsonObj,User user,OrgLocations orgLocations)throws Exception;

	/**
	 * @author Prasad Salina 
	 * To get all the spocs
	 */
	List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId) throws Exception;

}
