package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollBonusDao;
import com.bis.sourcelead.dto.PayrollBonusDTO;
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
	public List<PayrollBonusDTO> getPayrollBonusList(Long orgLocationId) throws Exception {
		return payrollBonusDao.getPayrollBonusList(orgLocationId);
	}

	@Override
	public PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception {
		return payrollBonusDao.getPayrollBonusByUUID(uuid);
	}

}
