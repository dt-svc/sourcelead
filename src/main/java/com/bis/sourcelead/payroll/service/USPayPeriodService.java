package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;

public interface USPayPeriodService {

	USPayPeriod getPayPeriodNumber(Long payFrequencyId);

	USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod);

	ResponseList<PaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation,PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception;

	int updatedPayperiod(String payPeriodNum);
	
	USPayPeriod getPayPeriodByOrglocationId(OrgLocations orgLocation, Long payfrequencyId);

	List<EmployeePayroll> getEmployeePayrollListByPayperiodNum(Integer payPeriodNum);

}
