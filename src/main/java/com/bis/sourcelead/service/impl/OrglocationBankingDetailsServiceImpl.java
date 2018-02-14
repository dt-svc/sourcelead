package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrglocationBankingDetailsDao;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;

@Service
public class OrglocationBankingDetailsServiceImpl implements OrglocationBankingDetailsService{

	@Autowired
	OrglocationBankingDetailsDao orglocationBankingDetailsDao;
	
	@Override
	public OrglocationBankingDetails saveOrglocationBankingDetails(OrglocationBankingDetails orglocationBankingDetails)
			throws Exception {
		return orglocationBankingDetailsDao.saveOrglocationBankingDetails(orglocationBankingDetails);
	}

	@Override
	public OrglocationBankingDetails getOrglocationBankingDetailsByUUID(String uuid) throws Exception {
		return orglocationBankingDetailsDao.getOrglocationBankingDetailsByUUID(uuid);
	}

	@Override
	public OrglocationBankingDetails getOrglocationBankingDetailsByOrgLocationId(Long orgLocationId) throws Exception {
		return orglocationBankingDetailsDao.getOrglocationBankingDetailsByOrgLocationId(orgLocationId);
	}

}
