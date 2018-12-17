package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgLocationIndiaBankingDetailsDao;
import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.entity.OrglocationIndiaBankingDetails;
import com.bis.sourcelead.service.OrgLocationIndiaBankingDetailsService;

@Service
public class OrgLocationIndiaBankingDetailsServiceImpl implements OrgLocationIndiaBankingDetailsService{
	
	@Autowired
	OrgLocationIndiaBankingDetailsDao orgLocationIndiaBankingDetailsDao;

	@Override
	public OrglocationIndiaBankingDetails saveOrgLocationIndiaBankingDetails(
			OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception {
		return orgLocationIndiaBankingDetailsDao.saveOrgLocationIndiaBankingDetails(orgLocationIndiaBankingDetails);
	}

	@Override
	public OrglocationIndiaBankingDetails getOrglocationIndiaBankingDetailsByUUID(String uuid) throws Exception {
		return orgLocationIndiaBankingDetailsDao.getOrglocationIndiaBankingDetailsByUUID(uuid);
	}

	@Override
	public List<OrgLocationIndiaBankingDetailsDTO> getOrglocationIndiaBankingDetailsList(Long orgLocationId)
			throws Exception {
		return orgLocationIndiaBankingDetailsDao.getOrglocationIndiaBankingDetailsList(orgLocationId);
	}

	@Override
	public OrglocationIndiaBankingDetails deleteOrglocationIndiaBankingDetails(OrglocationIndiaBankingDetails orgLocationIndiaBankingDetails) throws Exception {
		return orgLocationIndiaBankingDetailsDao.deleteOrglocationIndiaBankingDetails(orgLocationIndiaBankingDetails);
	}

}
