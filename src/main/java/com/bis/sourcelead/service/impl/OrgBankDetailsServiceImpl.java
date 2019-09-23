package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgBankDetailsDao;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.service.OrgBankDetailsService;

@Service
public class OrgBankDetailsServiceImpl implements OrgBankDetailsService {

	@Autowired
	OrgBankDetailsDao orgBankDetailsDao;
	
	@Override
	public OrgBankDetails saveOrgBankDetails(OrgBankDetails orgBankDetails) throws Exception {
		return orgBankDetailsDao.saveOrgBankDetails(orgBankDetails);
	}

	@Override
	public OrgBankDetails getOrgBankDetailsByOrgLocationId(Long orgLocationId) throws Exception {
		return orgBankDetailsDao.getOrgBankDetailsByOrgLocationId(orgLocationId);
	}

	@Override
	public OrgBankDetails getOrgBankDetailsById(Long id) throws Exception {
		return orgBankDetailsDao.getOrgBankDetailsById(id);
	}

}
