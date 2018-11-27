package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrglocationDepositTaxesDao;
import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationDepositTaxes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationDepositTaxesService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class OrglocationDepositTaxesServiceImpl implements OrglocationDepositTaxesService {
	
	@Autowired
	OrglocationDepositTaxesDao orglocationDepositTaxesDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public OrglocationDepositTaxes saveOrglocationDepositTaxes(FederalAndStateTaxDTO federalAndStateTaxDTO,
			OrgLocations loginOrgLocation, User user) throws Exception {
		
		OrglocationDepositTaxes orglocationDepositTaxes = new OrglocationDepositTaxes();
		
		orglocationDepositTaxes.setOrgLocationId(loginOrgLocation);
		orglocationDepositTaxes.setFillerType(federalAndStateTaxDTO.getFillerType());
		orglocationDepositTaxes.setTaxDepositSchedule(federalAndStateTaxDTO.getTaxDepositSchedule());
		orglocationDepositTaxes.setFederalDeposit(federalAndStateTaxDTO.getFederalDeposit());
		orglocationDepositTaxes.setStatus(Constants.RECORD_ACTIVE);
		orglocationDepositTaxes.setCreatedBy(user);
		orglocationDepositTaxes.setUpdatedBy(user);
		orglocationDepositTaxes.setCreatedDate(commonUtil.currentDate());
		orglocationDepositTaxes.setUpdatedDate(commonUtil.currentDate());
		OrglocationDepositTaxes orglocDepTaxes = orglocationDepositTaxesDao.saveOrglocationDepositTaxes(orglocationDepositTaxes);
		
		return orglocDepTaxes;
	}

	@Override
	public OrglocationDepositTaxes getOrglocationDepositTaxesByOrgLocationId(Long locationId) {
		return orglocationDepositTaxesDao.getOrglocationDepositTaxesByOrgLocationId(locationId);
	}
	

}
