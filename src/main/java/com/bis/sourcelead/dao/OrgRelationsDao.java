package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.OrgRelations;

/**
 * 
 * @author Prasadk
 * @date 08th Jul 2016
 *
 */
public interface OrgRelationsDao {

	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception;

	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId,Long clientOrgLocationId)throws Exception;
	
	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
	
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)throws Exception;
	
}