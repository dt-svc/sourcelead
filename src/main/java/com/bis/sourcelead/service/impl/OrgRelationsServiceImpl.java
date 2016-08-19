package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.Employees;
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
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		List<OrgRelations> emplist= orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		return emplist;
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
	
	
}