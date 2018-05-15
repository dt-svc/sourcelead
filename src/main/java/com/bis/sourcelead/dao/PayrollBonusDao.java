package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.entity.PayrollBonus;

public interface PayrollBonusDao {
	
	PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception;
	
	List<PayrollBonusDTO> getPayrollBonusList(Long orgLocationId) throws Exception;
	
	PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception;

}
