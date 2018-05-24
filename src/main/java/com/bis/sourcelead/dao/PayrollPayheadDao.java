package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPayhead;


public interface PayrollPayheadDao {
	
	PayrollPayhead savePayrollPayhead(PayrollPayhead payrollPayhead) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadList(OrgLocations orgLocations) throws Exception;
	
	ResponseList<PayrollPayheadDTO> getPayrollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,OrgLocations orgLocations) throws Exception;
	
	PayrollPayhead getPayrollPayheadByUUID(String uuid) throws Exception;
	
	Boolean findPayrollPayheadByNameAndLocationId(PayrollPayheadDTO payrollPayheadDTO) throws Exception;
}
