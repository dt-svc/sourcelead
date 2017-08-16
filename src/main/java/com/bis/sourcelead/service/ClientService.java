package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;

public interface ClientService {
	

	public void storeClient(JSONObject jsonObj,User user,OrgLocations orgLocations,String responderType,Org org) throws Exception;

	public void updateClientDetails(JSONObject jsonObj,Long orgRelationId, Long clientLocationId, Long loginLocationId, User user) throws Exception;


	public void updateVendorDetails(JSONObject jsonObj, Long orgRelationId, Long vendorLocationId, Long loginOrgId, User user) throws JSONException, Exception;


	public OrgSpoc updateVendorContactDetails(JSONObject jsonObj, Long spocId, Long id, User loginUser, Long ownerId, Long spocUserId,OrgLocations orgLocation, String type) throws Exception;

	void saveOrUpdateClientVendorElkDocument(Org org,OrgLocations clientOrgLocations, String responderType, OrgSpoc orgSpoc,
			OrgRelations orgRelationsList, SubmissionTemplate submissionTemplate, User user,OrgLocations loginOrgId) throws Exception;
	
	public void sendAcceptEmailToClientOrVendor(OrgSpoc orgSpoc,User loginUser,OrgLocations orgLocations)throws Exception;

	public void sendRejectEmailToClientOrVendor(OrgSpoc orgSpoc, User loginUser, OrgLocations orgLocations)throws Exception;

	List<Object> getAllUniversalSearchVendorList(OrgLocations orgLocation, String searchValue, String searchType) throws Exception;
	
	List<Object> getAllUniversalSearchClientList(OrgLocations orgLocations, String serachValue, String searchType) throws Exception;


}
