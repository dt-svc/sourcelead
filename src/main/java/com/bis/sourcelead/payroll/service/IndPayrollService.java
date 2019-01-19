package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;

public interface IndPayrollService {

	ResponseList<IndPaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;
	
}
