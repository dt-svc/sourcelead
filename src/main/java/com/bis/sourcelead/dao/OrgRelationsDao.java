package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.ResponseList;
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
	
	public OrgRelations getOrgRelationById(Long id) throws Exception;
	
//	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception;
	
	public OrgRelations getClientsByOrgLocationIdAndName(Long orgLocationId, String name) throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
	
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)throws Exception;

	List<OrgRelations> getAllVendorsByOrgLocationId(Long orgLocationId);

	OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId);
	
	OrgRelations getOrRelationsById(Long orgRelationId) throws Exception;

	List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType);
	
	OrgRelations getRespondingPartyDetailsById(Long id) throws Exception;
	
	List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId,String status) throws Exception;
	
	List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long id,String status, String vendorsRelationId)throws Exception;
	
	List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType,String vendorStatus,String vendorIds,String vendorInvitationStatus);
	
	List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType,String vendorStatus,String vendorIds,String vendorInvitationStatus, Long tabId, List<Long> vendorOrgLocationsIds);

	public List<OrgRelations> getAllVendorsListByOrgName(String orgName,Long orgLocationId);

	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId);
	
	List<OrgRelations> getClientOrVendorListByOrgLocationId(Long orgLocationId,String type);
	
	List<OrgRelations> getClientOrVendorListByOrgLocationIdForImmigration(Long orgLocationId,String type);

	public Integer getAllClientsCountByOrgLocationId(Long orgLocationId) throws Exception;

	public Integer getAllVendorsCountByOrgLocationId(Long orgLocationId,String type) throws Exception;

	public List<OrgRelations> getAllClientsByOrgLocationIdAndDomain(Long orgLocationId, String domain, String responderType);
	
	OrgRelations getVendorOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId,Long locationId) throws Exception;
	
	OrgRelations getOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId,Long locationId) throws Exception;
	
	OrgRelations getOrgRelationsByUUID(String UUID) throws Exception;
	
	ResponseList<OrgRelations> getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(OrgRelationDTO orgRelationDTO ,Long id,String status, String vendorsRelationId) throws Exception;
	
	ResponseList<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccessDTO(OrgSpocDTO orgSpocDTO,Long loginUserId, List<Long> userIds, Long orgLocationId, String relationType,String vendorStatus, Long tabId, List<Long> vendorOrgLocationsIds);
	
	List<OrgRelationDTO> getAllClientsListOrgLocationId(Long orgLocationId) throws Exception;
	
	List<OrgRelationDTO> getAllVendorsListOrgLocationId(Long orgLocationId) throws Exception;
	
	int updatePaymentTermsInOrgrelations();

	public List<OrgRelations> getMSPVendorListByOrgLocationId(Long orgLocationId,Long orgRelationId) throws Exception;

	public List<OrgRelations> selectedMSPVendorListByOrgRelationIds(String orgRelationIds) throws Exception;

	public List<OrgSpoc> getMSPVendorContactListByOrgRelationId(Long orgRelationId) throws Exception;

}