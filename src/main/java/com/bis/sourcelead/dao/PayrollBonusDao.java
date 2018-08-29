package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;

public interface PayrollBonusDao {
	
	PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception;
	
	ResponseList<PayrollBonusDTO> getPayrollBonusList(PayrollBonusDTO payrollBonusDTO,OrgLocations orgLocations) throws Exception;
	
	PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception;

}
