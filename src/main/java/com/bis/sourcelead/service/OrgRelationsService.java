package com.bis.sourcelead.service;


import java.util.Collection;
import java.util.Set;

import java.util.List;


import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;

public interface OrgRelationsService {
	
	public OrgRelations addOrgRelations(OrgRelations OrgRelations)throws Exception;
	
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId,Long clientOrgLocationId)throws Exception;
   
//	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
	
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)throws Exception;
	
	Set<OrgRelations> orgRelationsListByJson(JSONObject jsonObj, OrgLocations clientOrgLocation, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception;
	
	List<OrgRelations> getAllVendorsListByOrgLocationId(Long id);

	OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId);
	
	OrgRelations getOrRelationsById(Long orgRelationId)  throws Exception;

}