package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.OrgRelations;
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

	
	
	
}
