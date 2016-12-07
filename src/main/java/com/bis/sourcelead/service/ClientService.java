package com.bis.sourcelead.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

public interface ClientService {
	

	public void storeClient(JSONObject jsonObj,User user,OrgLocations orgLocations,String responderType,Org org) throws Exception;

	public void updateClientDetails(JSONObject jsonObj,Long orgRelationId, Long clientLocationId, Long loginLocationId) throws Exception;


	public void updateVendorDetails(JSONObject jsonObj, Long orgRelationId, Long vendorLocationId, Long loginOrgId) throws JSONException, Exception;


	public OrgSpoc updateVendorContactDetails(JSONObject jsonObj, Long spocId, Long id, User loginUser, Long ownerId, Long spocUserId,OrgLocations orgLocation) throws Exception;


}
