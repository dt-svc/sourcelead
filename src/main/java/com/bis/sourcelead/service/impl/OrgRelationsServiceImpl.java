package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgRelationsService;

@Service
public class OrgRelationsServiceImpl implements OrgRelationsService {

	@Autowired
	OrgRelationsDao orgRelationsDao;

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
	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		Collection<OrgRelations> clientssList = orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		return clientssList;
	}

	@Override
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		OrgRelations orgRelation =  orgRelationsDao.getClientByOrgLocationId(orgLocationId);
		orgRelation.setOrgName(orgRelation.getClientLocationId().getOrg().getOrgName());
		orgRelation.setWebSite(orgRelation.getClientLocationId().getOrg().getWebSite());
		orgRelation.setCountry(orgRelation.getClientLocationId().getCountry());
		orgRelation.setCity(orgRelation.getClientLocationId().getCity());
		orgRelation.setState(orgRelation.getClientLocationId().getState());
		orgRelation.setPhone(orgRelation.getClientLocationId().getPhone());
		orgRelation.setEmail(orgRelation.getClientLocationId().getEmail());
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
	 * 
	 * This method is for org Relatons saving..
	 * 
	 * @param jsonObj, clientOrgLocation, orgLocations, loginUserCode
	 *  
	 * @return Set<OrgRelations> 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Set<OrgRelations> orgRelationsListByJson(JSONObject jsonObj, OrgLocations clientOrgLocation, OrgLocations orgLocations, String loginUserCode) throws Exception {
		
		HashSet<OrgRelations> orgRelationsList = new HashSet<OrgRelations>();
		
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setClientLocationId(clientOrgLocation);
		orgRelations.setLocationId(orgLocations);
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedBy(loginUserCode);
		orgRelations.setCreatedDate(new Date());
		
		orgRelationsList.add(orgRelations);
		return orgRelationsList;
	}
}