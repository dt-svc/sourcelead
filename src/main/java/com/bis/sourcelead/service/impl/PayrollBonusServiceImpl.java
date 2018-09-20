package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollBonusDao;
import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.service.PayrollBonusService;

@Service
public class PayrollBonusServiceImpl implements PayrollBonusService{
	
	@Autowired
	PayrollBonusDao payrollBonusDao;

	@Override
	public PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception {
		return payrollBonusDao.savePayrollBonus(payrollBonus);
	}

	@Override
	public ResponseList<PayrollBonusDTO> getPayrollBonusList(PayrollBonusDTO payrollBonusDTO,OrgLocations orgLocations) throws Exception {
		return payrollBonusDao.getPayrollBonusList(payrollBonusDTO,orgLocations);
	}

	@Override
	public PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception {
		return payrollBonusDao.getPayrollBonusByUUID(uuid);
	}

}
