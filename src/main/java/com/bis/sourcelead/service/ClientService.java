package com.bis.sourcelead.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ClientService {
	
	public void saveClient(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;
	

	public void storeClient(JSONObject jsonObj,User user,OrgLocations orgLocations,String responderType) throws Exception;

	public void updateClientDetails(JSONObject jsonObj,Long orgRelationId) throws Exception;


	public void updateVendorDetails(JSONObject jsonObj, Long orgRelationId, Long vendorLocationId, Long loginOrgId) throws JSONException, Exception;


}
