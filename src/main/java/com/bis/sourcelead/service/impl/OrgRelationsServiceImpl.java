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
import com.bis.sourcelead.service.OrgRelationsService;
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
			orgRelations.setResponderLevel("Level-5");
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
	public OrgRelations getOrRelationsById(Long orgRelationId) throws Exception {
		return orgRelationsDao.getOrRelationsById(orgRelationId);
	}

	@Override
	public List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId,Long orgLocationId, String relationType) throws Exception {
		List<Long> userIds = userService.getAllUserIdsByReportingManagerId(loginUserId);
		return orgRelationsDao.getAllVendorsContactListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType);
	}

	@Override
	public OrgRelations getRespondingPartyDetailsById(Long id) throws Exception {
		return orgRelationsDao.getRespondingPartyDetailsById(id);
	}
}