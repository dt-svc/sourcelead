package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;

/**
 * 
 * @author Prasadk
 * @date 08th Jul 2016
 *
 */
public interface OrgRelationsDao {

	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception;

	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId,Long clientOrgLocationId)throws Exception;
	
//	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
	
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)throws Exception;

	List<OrgRelations> getAllVendorsByOrgLocationId(Long orgLocationId);

	OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId);
	
	OrgRelations getOrRelationsById(Long orgRelationId) throws Exception;

	List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType);
	
	OrgRelations getRespondingPartyDetailsById(Long id) throws Exception;
	
	List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId,String status) throws Exception;
	
	List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long id,String status)throws Exception;
	
	List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType,String vendorStatus,String vendorIds,String vendorInvitationStatus);

	public List<OrgRelations> getAllVendorsListByOrgName(String orgName,Long orgLocationId);

	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId);

}