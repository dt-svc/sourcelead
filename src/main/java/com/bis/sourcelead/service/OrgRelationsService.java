package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.OrgRelations;

public interface OrgRelationsService {
	
	public OrgRelations addOrgRelations(OrgRelations OrgRelations)throws Exception;
	
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId,Long clientOrgLocationId)throws Exception;
   
	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
}