package com.bis.sourcelead.service.impl;

import java.util.Collection;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.util.List;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;

import com.bis.sourcelead.entity.OrgLocations;

import com.bis.sourcelead.entity.Employees;

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
			 if(orgRelation.getLocationId().getId()== loginOrgLocationId){ 
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
					orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
					orgRelation.setLevel(orgRelation.getInitiatorLevel());
					orgRelation.setOrgType(orgRelation.getResponderLocationId().getOrg().getOrgType());
					
			 }
			 //to fetch user details by usercode 
			User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
			orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
			
			//if login user org id is equals to vendor ResponderLocationId id then we get vendor list of location Org details 
			 if(orgRelation.getResponderLocationId().getId()== loginOrgLocationId){ 
					orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
					orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelation.setCountry(orgRelation.getLocationId().getCountry());
					orgRelation.setCity(orgRelation.getLocationId().getCity());
					orgRelation.setState(orgRelation.getLocationId().getState());
					orgRelation.setPhone(orgRelation.getLocationId().getPhone());
					orgRelation.setEmail(orgRelation.getLocationId().getEmail());
					orgRelation.setLevel(orgRelation.getResponderLevel());
					orgRelation.setOrgType(orgRelation.getLocationId().getOrg().getOrgType());
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
				if(user != null){
					orgRelation.setVendorCreateduserName(user.getFirstName()+" "+user.getLastName());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(),loginUserId,orgLocationId);
				orgRelation.setNoOfContatcs(orgSpocs.size());
				if(orgRelation.getLocationId().getId() == orgLocationId){
					
					orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelation.setState(orgRelation.getResponderLocationId().getState());
					orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId().getId() == orgLocationId){
					orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelation.setOrgName(orgRelation.getLocationId().getOrg().getOrgName());
					orgRelation.setCountry(orgRelation.getLocationId().getCountry());
					orgRelation.setState(orgRelation.getLocationId().getState());
					orgRelation.setCity(orgRelation.getLocationId().getCity());
					orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
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
				
				if(orgSpoc.getSpocUser().getId() == loginUserId || orgSpoc.getOwnerUser().getId() == loginUserId){
					//if login user is spoc
					if(orgSpoc.getSpocUser().getId() == loginUserId){
	    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
	    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
	    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
	    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
	    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
	    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					}
					//if login user is Owner
					else if(orgSpoc.getOwnerUser().getId() == loginUserId){
							 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						}
						
					 
				}else{
					if(orgSpoc.getInitiatorLocationId().getId() == orgLocationId ){
						 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
						 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
						 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
						 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						 
					 }
					if(orgSpoc.getResponderLocationId().getId() == orgLocationId){
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
					}
				}
				if(orgSpoc.getResponderLocationId().getId() == orgLocationId){
					orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
				}
				if(orgSpoc.getInitiatorLocationId().getId() == orgLocationId ){
					 orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
					orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
				}
				orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
				orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
				orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
			}
	   }
		
		return vandorsContactsList;
	}
	
}