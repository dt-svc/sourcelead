package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.entity.PayrollBonus;

public interface PayrollBonusService {

	PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception;
	
	List<PayrollBonusDTO> getPayrollBonusList(Long orgLocationId) throws Exception;
	
	PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception;
}
