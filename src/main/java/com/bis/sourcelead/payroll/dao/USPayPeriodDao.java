package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;

public interface USPayPeriodDao {

	USPayPeriod getPayPeriodNumber(Long payFrequencyId);

	USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod);

	ResponseList<USPayPeriod> getPayPeriodListByOrgLocationId(OrgLocations orgLocation,PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception;

	int updatedPayperiod(String parseInt);

	List<USPayPeriod> getPaySheetFrequencyIdAndOrglocationId(OrgLocations orgLocation,
			Long payfrequencyId);
}
