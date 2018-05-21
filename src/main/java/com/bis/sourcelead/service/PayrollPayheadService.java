package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;

public interface PayrollPayheadService {

	PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadList(PayrollPayheadDTO payrollPayheadDTO, OrgLocations orgLocations) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO, OrgLocations orgLocations) throws Exception;
	
}
