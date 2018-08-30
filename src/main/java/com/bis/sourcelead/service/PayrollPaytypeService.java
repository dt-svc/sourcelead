package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPaytype;

public interface PayrollPaytypeService {

	PayrollPaytype savePayrollPaytype(PayrollPaytype payrollPaytype) throws Exception;
	
	ResponseList<PayrollPaytypeDTO> getPayrollPaytypeList(OrgLocations orgLocations) throws Exception;
	
	Boolean findPayrollPaytypeByNameAndLocationId(PayrollPaytypeDTO payrollPaytypeDTO) throws Exception;
	
}
