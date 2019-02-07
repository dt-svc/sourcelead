package com.bis.sourcelead.indPayroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;

public interface IndPayrollService {

	ResponseList<IndPaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;
	
}
