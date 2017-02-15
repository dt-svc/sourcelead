package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class OrgRelationsServiceImpl implements OrgRelationsService {

	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private OrgSpocService orgSpocService;

	@Override
	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception {
		orgRelations.setUpdatedDate(commonUtil.currentDate());
		return orgRelationsDao.addOrgRelations(orgRelations);
	}

	@Override
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId, Long clientOrgLocationId)
			throws Exception {
		return orgRelationsDao.getOrgRelationIdByOrgLocationIdAndClientLocationId(orgLocationId, clientOrgLocationId);
	}

	
	
	@Override
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		List<OrgRelations> emplist= orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		return emplist;
	}

	@Override
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		OrgRelations orgRelation =  orgRelationsDao.getClientByOrgLocationId(orgLocationId);
		orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
		orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
		orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
		orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
		orgRelation.setState(orgRelation.getResponderLocationId().getState());
		orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
		orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
		return orgRelation;
	}

	@Override
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception {
		return orgRelationsDao.getNumberOfProjectsPerClient(orgLocationId);
	}

	@Override
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)
			throws Exception {
		
		return orgRelationsDao.getProjectsCountByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId);
	}
	
	/**
	 * @author VenkatKesav
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * This method is for org Relatons saving..
	 * @param jsonObj, clientOrgLocation, orgLocations, loginUserCode
	 * @return Set<OrgRelations> 
	 * @throws NullPointer
	 */
	@Override
	public Set<OrgRelations> orgRelationsListByJson(JSONObject jsonObj, OrgLocations clientOrgLocation, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception {
		
		HashSet<OrgRelations> orgRelationsList = new HashSet<OrgRelations>();
		
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setResponderLocationId(clientOrgLocation);
		orgRelations.setLocationId(orgLocations);
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedBy(loginUserCode);
		orgRelations.setCreatedDate(commonUtil.currentDate());
		orgRelations.setUpdatedDate(commonUtil.currentDate());
		
		if(null != responderType  && !responderType.isEmpty()){
			if("Client".equalsIgnoreCase(responderType)){
				orgRelations.setInitiatorType("Vendor"); 
				orgRelations.setResponderType("Client");
			}else{
				orgRelations.setInitiatorType("Client"); 
				orgRelations.setResponderType("Vendor");
			}
		  }
		if(jsonObj.has("level")){
			//orgRelations.setLevel(jsonObj.getString("level"));
			orgRelations.setInitiatorLevel(jsonObj.getString("level"));
			orgRelations.setResponderLevel("Tier-5");
		}else{
			orgRelations.setInitiatorLevel("Tier-5");
			orgRelations.setResponderLevel("Tier-5");
		}
		orgRelationsList.add(orgRelations);
		return orgRelationsList;
	}

	@Override
	public List<OrgRelations> getAllVendorsListByOrgLocationId(Long orgLocationId) {
		return orgRelationsDao.getAllVendorsByOrgLocationId(orgLocationId);
	}

	@Override
	public OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId) {
		
		return orgRelationsDao.getClientAndVendorOrgDeatilsById(orgLocationId,orgRelationId);
	}
	
	@Override
	public OrgRelations getClientAndVendorOrgDeatilsByResponderLocationdIdAndOrgRelationId(Long orgLocationId, Long orgRelationId, Long loginOrgLocationId) {
		
		OrgRelations orgRelation =  orgRelationsDao.getClientAndVendorOrgDeatilsById(orgLocationId,orgRelationId);
		if(orgRelation != null){
			//if login user org id is equals to vendor location id then we get vendor list of ResponderLocationId Org details 
			 if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== loginOrgLocationId.longValue()){ 
					if(orgRelation.getResponderLocationId() !=  null){
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
						orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
						orgRelation.setOrgType(orgRelation.getResponderLocationId().getOrg().getOrgType());
					}
					orgRelation.setLevel(orgRelation.getInitiatorLevel());
			 }
			 //to fetch user details by usercode 
			User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
			 StringBuilder vendorName = new StringBuilder();
			orgRelation.setVendorCreateduserName(vendorName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			
			//if login user org id is equals to vendor ResponderLocationId id then we get vendor list of location Org details 
			 if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue()== loginOrgLocationId.longValue()){ 
					if(orgRelation.getLocationId() != null){
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setPhone(orgRelation.getLocationId().getPhone());
						orgRelation.setEmail(orgRelation.getLocationId().getEmail());
						orgRelation.setOrgType(orgRelation.getLocationId().getOrg().getOrgType());
					}
					orgRelation.setLevel(orgRelation.getResponderLevel());
			 } 
		}
		return orgRelation;
	}

	@Override
	public OrgRelations getOrRelationsById(Long orgRelationId) throws Exception {
		return orgRelationsDao.getOrRelationsById(orgRelationId);
	}

	@Override
	public List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId,Long orgLocationId, String relationType) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		return orgRelationsDao.getAllVendorsContactListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType);
	}

	@Override
	public OrgRelations getRespondingPartyDetailsById(Long id) throws Exception {
		return orgRelationsDao.getRespondingPartyDetailsById(id);
	}

	@Override
	public List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId,String status, Long loginUserId) throws Exception {
		
		List<OrgRelations> filterClientList= orgRelationsDao.getAllClientsFilterByOrgLocationId(orgLocationId, status);	
		if(filterClientList != null && !filterClientList.isEmpty()){
			for (OrgRelations orgRelation : filterClientList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				 StringBuilder vendorCreatedName = new StringBuilder();
				if(user != null){
					orgRelation.setVendorCreateduserName(vendorCreatedName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(),loginUserId,orgLocationId);
				orgRelation.setNoOfContatcs(orgSpocs.size());
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocationId.longValue()){
					if(orgRelation.getResponderLocationId() != null){
						if(orgRelation.getResponderLocationId().getOrg().getWebSite() !=null){
							orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						}
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue()== orgLocationId.longValue()){
					if(orgRelation.getLocationId() != null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
			}
		}
		
		return filterClientList;
	}

	@Override
	public List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long orgLocationId, String status) throws Exception{
		return orgRelationsDao.getAllVendorsFilterByOrgLocationId(orgLocationId,status);
	}

	@Override
	public List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long loginUserId,Long orgLocationId, String relationType,String status,String vendorIds, String invitationStatus) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		List<OrgSpoc> vandorsContactsList =  orgRelationsDao.getAllVendorsContactFilterListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType,status,vendorIds,invitationStatus);
		if(vandorsContactsList != null && !vandorsContactsList.isEmpty()){
		   for (OrgSpoc orgSpoc : vandorsContactsList) {
				if((orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == loginUserId.longValue()) || (orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == loginUserId.longValue())){
					//if login user is spoc
					if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == loginUserId.longValue()){
						 StringBuilder spocNameSB = new StringBuilder();
						 StringBuilder ownerNameSB = new StringBuilder();
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
	    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
	    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == loginUserId.longValue()){
						 StringBuilder spocNameSB1 = new StringBuilder();
						    if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
								orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
							}else{
								orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).toString());
							}
						    StringBuilder ownerNameSB1 = new StringBuilder();
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
						 StringBuilder spocNameSB2 = new StringBuilder();
						if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
							orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						}else{
							orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
						}
						StringBuilder ownerNameSB2 = new StringBuilder();
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						 
					 }
					if(orgSpoc.getResponderLocationId() !=  null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
						StringBuilder spocNameSB3 = new StringBuilder();
						StringBuilder ownerNameSB3 = new StringBuilder();
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setOwnerName(ownerNameSB3.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					}
				}
				if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgSpoc.getInitiatorLocationId() != null){
						 orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
						 orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
					}
				}
				if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgSpoc.getResponderLocationId() != null){
						 orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
						 orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
					}
				}
				if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null){
					orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
				}
				if(orgSpoc.getSpocUser() !=  null){
					orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
				}
				if(orgSpoc.getOrgRelations() != null){
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
				}
			}
	   }
		
		return vandorsContactsList;
	}

	@Override
	public List<OrgRelations> getAllVendorsListByOrgName(String orgName,Long orgLocationId) {
		return orgRelationsDao.getAllVendorsListByOrgName(orgName,orgLocationId);
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllClientsByOrgNameOrgLocationId(orgName,orgLocationId);
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId, Long loginUserId) throws Exception {
		List<OrgRelations> orgRelationsList =  orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		List<OrgRelations> clientsList = new ArrayList<OrgRelations>();
		if(orgRelationsList != null && !orgRelationsList.isEmpty()){
			for (OrgRelations orgRelation : orgRelationsList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				if(user != null){
					StringBuilder userName=new StringBuilder();
					orgRelation.setVendorCreateduserName(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(),loginUserId,orgLocationId);
				orgRelation.setNoOfContatcs(orgSpocs.size());
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgRelation.getResponderLocationId() != null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgRelation.getLocationId() != null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					}
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				clientsList.add(orgRelation);
				
			}
		}
		
		return clientsList;
	}

	@Override
	public List<OrgRelations> getClientOrVendorListByOrgLocationId(Long orgLocationId, String type) {
		return orgRelationsDao.getClientOrVendorListByOrgLocationId(orgLocationId, type);
	}

	@Override
	public Integer getAllClientsCountByOrgLocationId(Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllClientsCountByOrgLocationId(orgLocationId);
	}

	@Override
	public Integer getAllVendorsCountByOrgLocationId(Long orgLocationId,String type) throws Exception {
		return orgRelationsDao.getAllVendorsCountByOrgLocationId(orgLocationId,type);

	}
	
}