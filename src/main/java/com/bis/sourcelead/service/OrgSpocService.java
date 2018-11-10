package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

public interface OrgSpocService {
	
	/**
	 * Add spoc for organization
	 * @param orgSpoc
	 * @return
	 * @throws Exception
	 */
	OrgSpoc addOrgSpoc(OrgSpoc orgSpoc)throws Exception;
	
/**
 * @author Rajashekar
 * add Vendor contacts
 * @param jsonObj
 * @param user
 * @param orgLocations
 * @throws Exception
 */
	void addVendorContacts(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO,User user,OrgLocations orgLocations, String orgRelationId, String clientLocationId,String responderType,Org org)throws Exception;

	/**
	 * @author Prasad Salina 
	 * To get all the spocs
	 */
	List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId ,Long orgLocationId,String type) throws Exception;
	
	
	List<OrgSpoc> getAllActiveSpocsByOrgRelationId(Long orgRelationId, Long userId ,Long orgLocationId) throws Exception;
	
	
	/**To get OrgSpoc by ID
	 * @author Prasad Salina
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	OrgSpoc getOrgSpocById(Long spocId) throws Exception;
	
	/**To validate spoc email id while create new spoc
	 * @author Prasad Salina
	 * @param email
	 * @param orgRelationId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId, Integer status) throws Exception;

	OrgSpoc getSpocAndOwnerDetailsById(Long spocId, Long ownerId);
	
	OrgSpoc getSpocAndOwnerDetailsByIdAndOwnerId(Long spocId, Long ownerId, Long spocUserId, Long loginUserId, Long orgLocationId,String type) throws Exception;

	List<Long> getOwnerIdbySpocId(Long spocUserId, Long ownerId);
	
	List<Long> getOwnerUserIdsbySpocEmailAndOrgRelationId(String email, Long orgRelationId,String type) throws Exception;
	
	
	/**To get Spoc user
	 * @author Prasad Salina
	 * @param spocUserId
	 * @return
	 */
	OrgSpoc getSpocByClientOrVendorUserId(Long spocUserId) throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param spocUUID
	 * @return
	 * @throws Exception
	 */
	OrgSpoc getSpocBySpocUUID(String spocUUID) throws Exception;
	
	/**To get Spoc user Contact List for requirement
	 * @author David Raju
	 * @param orgLocationId,UserId,relationType
	 * @return List<OrgSpoc>
	 */
	List<OrgSpoc> getAllContactsbyOrgLocationIdAndUserIdForRequirement(Long orgLocationId,Long userId,String relationType) throws Exception;
	
	List<OrgSpoc> getAllContactsbyOrgId(Long orgId) throws Exception;
	
	List<OrgSpoc> getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(Long orgId, String invitationStatus, String spocStatus) throws Exception;
	
	List<OrgSpoc> getAllContactsbyOrgRelationIdAndLocationId(Long relationId, Long locationId, String initiator) throws Exception;
	
	List<Long> getAssignedAsClientOrVendorSpocCountByUserId(Long userId, Long orgLocationId,String type) throws Exception;
	
	List<OrgSpoc> getAssignedAsClientOrVendorSpocByUserId(Long userId, Long orgLocationId,String type) throws Exception;
	
	List<OrgSpoc> getAllClientContactsListByOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	Integer getAllClientContactsCountForCampaignByOrgLocationId(Long orgRelationId,Long userId,Long orgLocationId) throws Exception;
	
	OrgSpoc getSpocListByResponderLocationId(Long initiatingPartyOrgLocationId,Long responderLocationId,String respondingOrgType) throws Exception;
	
	List<OrgSpoc> getOrgSpocByOrgRelationId(Long orgRelationId) throws Exception;
	
	Integer getAllVendorContactsCountbyOrgLocationId(Long orgId,String type,Long userId) throws Exception;

	void saveOrUpdateClientVendorContactElkDocument(OrgSpoc orgSpoc) throws Exception;

	List<OrgSpoc> getOrgSpocByUserIdAndOrgLocationCode(Long userId, Long orgLocationId) throws Exception;

	List<Long> getOwnerListByUserIdAndOrglocationId(User user, OrgLocations orgLocations, String type);
	
	OrgSpoc getValidateOwnersBySpocUserIdAndSpocOwnerUserId(Long spocUserId,Long spocOwnderUserId, Long responderLocationId, String responderType) throws Exception;

	List<OrgSpoc> getOrgSpocContactEmailsByOrgRelationIdAndUserId(Long userId,Long orgRelationId,Long orgLocationId) throws Exception;

	OrgSpoc getValidateOwnersByVendorUserIdAndSpocOwnerUserId(Long spocUserId,Long spocOwnderUserId, Long responderLocationId)throws Exception;

	List<OrgSpoc> getAllClientsByOrgLocationId(Long orgLocationId)throws Exception;

	List<OrgSpoc> getSpoclistByDomain(Long id, String domain)throws Exception;
	
	boolean getOwnerUserExistedByOrgRelationIdAndOwnerUserId(Long orgRelationId, Long ownerUserId) throws Exception;
	
	OrgSpoc getSpocByOrgRelationIdAndAssignedUserId(Long orgRelationId,Long assignUserId) throws Exception;
	
	OrgSpoc getSpocByOrgRelationIdForNewVendor(Long orgRelationId) throws Exception;
	
	List<OrgSpoc> getSpocListByVendorUserIdAndOrgRelationId(Long orgRelationId,Long vendorUserId)throws Exception;
	
	OrgSpoc getOrgSpocByUUID(String UUID) throws Exception;
	
	Integer getNumberOfContactCount(Long orgRelationId,Long userId,Long orgLocationId,String responderType) throws Exception;
	
	public void addClientVendorContactsELK(OrgSpoc orgSpoc) throws Exception;
	
	OrgSpoc getSpocByVendorUserIdAndOrgRelationId(Long orgRelationId,Long vendorUserId)throws Exception;


}
