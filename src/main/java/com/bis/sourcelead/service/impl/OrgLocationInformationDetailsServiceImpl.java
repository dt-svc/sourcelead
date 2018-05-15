package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLocationInformationDetailsDao;
import com.bis.sourcelead.entity.OrgLocationInformationDetails;
import com.bis.sourcelead.service.OrgLocationInformationDetailsService;

@Service
public class OrgLocationInformationDetailsServiceImpl implements OrgLocationInformationDetailsService{

	@Autowired
	OrgLocationInformationDetailsDao orgLocationInformationDetailsDao;
	
	@Override
	public OrgLocationInformationDetails saveOrgLocationInformationDetails(
			OrgLocationInformationDetails orgLocationInformationDetails) throws Exception {
		return orgLocationInformationDetailsDao.saveOrgLocationInformationDetails(orgLocationInformationDetails);
	}

	@Override
	public OrgLocationInformationDetails getOrgLocationInformationDetailsByOrgLocationId(Long orgLocationId)
			throws Exception {
		return orgLocationInformationDetailsDao.getOrgLocationInformationDetailsByOrgLocationId(orgLocationId);
	}

}
