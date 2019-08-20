package com.bis.sourcelead.service;


import java.util.List;
import java.util.Set;

import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;

public interface OrgRelationsService {
	
	public OrgRelations addOrgRelations(OrgRelations OrgRelations)throws Exception;
	
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId,Long clientOrgLocationId)throws Exception;
	
	public OrgRelations getOrgRelationById(Long id) throws Exception;
   
//	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;
	
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception;
	
	public OrgRelations getClientsByOrgLocationIdAndName(Long orgLocationId, String name) throws Exception;
	
	List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String  orgName,Long orgLocationId) throws Exception;
	
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception;
	
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)throws Exception;
	
	Set<OrgRelations> orgRelationsListByJson(ClientDTO clientDTO, OrgLocations clientOrgLocation, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception;
	
	List<OrgRelations> getAllVendorsListByOrgLocationId(Long id);
	
	List<OrgRelationDTO> getAllVendorsDTOListByOrgLocationId(Long id);
	
	List<OrgRelations> getAllVendorsListByOrgName(String  orgName,Long id);

	OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId);
	
	OrgRelations getClientAndVendorOrgDeatilsByResponderLocationdIdAndOrgRelationId(Long orgLocationId, Long orgRelationId, Long loginOrgLocationId) throws Exception;
	
	OrgRelations getOrRelationsById(Long orgRelationId)  throws Exception;

	List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long id, Long orgLocationId, String relationType) throws Exception;
	
	OrgRelations getRespondingPartyDetailsById(Long id) throws Exception;
	
	List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId,String status, Long loginUserId) throws Exception;
	
	List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long id,String status, String vendorsRelationId) throws Exception;

	List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long id, Long orgLocationId, String relationType,String vendorStatus,String vendorIds,String vendorInvitationStatus) throws Exception;
	
	List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(Long id, Long orgLocationId, String relationType,String vendorStatus,String vendorIds,String vendorInvitationStatus, List<Long> vendorOrgLocationsIds) throws Exception;
	
	List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId,Long loginUserId) throws Exception;
	
	List<OrgRelations> getClientOrVendorListByOrgLocationId(Long orgLocationId,String type);
	
	List<OrgRelations> getClientOrVendorListByOrgLocationIdForImmigration(Long orgLocationId,String type);
	
	public Integer getAllClientsCountByOrgLocationId(Long orgLocationId) throws Exception;
	
	public Integer getAllVendorsCountByOrgLocationId(Long orgLocationId,String type) throws Exception;

	public List<OrgRelations> getAllClientsByOrgLocationIdAndDomain(Long orgLocationId, String domain, String responderType);

	OrgRelations getVendorOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId,Long locationId) throws Exception;
	
	OrgRelations getOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId,Long locationId) throws Exception;
	
	OrgRelations getOrgRelationsByUUID(String UUID) throws Exception;
	
	List<OrgRelations> getAllVendorsByOrgLocationId(Long orgLocationId);
	
	ResponseList<OrgSpoc>  getAllVendorsContactFilterListByOrgLocationIdAndTabAccessAndDTO(OrgSpocDTO orgSpocDTO,Long id, Long orgLocationId, String relationType,String vendorStatus, List<Long> vendorOrgLocationsIds) throws Exception;

	ResponseList<OrgRelations> getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(OrgRelationDTO orgRelationDTO ,Long id,String status, String vendorsRelationId) throws Exception;

	List<OrgRelationDTO> getAllClientsListOrgLocationId(Long orgLocationId) throws Exception;
	
	List<OrgRelationDTO> getAllVendorsListOrgLocationId(Long orgLocationId) throws Exception;
	
	int updatePaymentTermsInOrgrelations();
	
	public List<OrgRelations> getMSPVendorListByOrgLocationId(Long orgLocationId,Long orgRelationId) throws Exception;

	public List<OrgRelations> selectedMSPVendorListByOrgRelationIds(String orgRelationIds) throws Exception;
	
	public List<OrgSpoc> getMSPVendorContactListByOrgRelationId(Long orgRelationId) throws Exception;

}
