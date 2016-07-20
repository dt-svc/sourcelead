package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.service.OrgRelationsService;

@Service
public class OrgRelationsServiceImpl implements OrgRelationsService {

	@Autowired
	OrgRelationsDao orgRelationsDao;

	@Override
	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception {
		// TODO Auto-generated method stub
		return orgRelationsDao.addOrgRelations(orgRelations);
	}

	@Override
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId, Long clientOrgLocationId)
			throws Exception {
		// TODO Auto-generated method stub
		return orgRelationsDao.getOrgRelationIdByOrgLocationIdAndClientLocationId(orgLocationId, clientOrgLocationId);
	}

	
	@Override
	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		Collection<OrgRelations> clientssList = orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		return clientssList;
	}

	@Override
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		return orgRelationsDao.getClientByOrgLocationId(orgLocationId);
	}

	@Override
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception {
		return orgRelationsDao.getNumberOfProjectsPerClient(orgLocationId);
	}
	
	
}