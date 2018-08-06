package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;

public interface ConfigurePayrollApproverService {

	List<UserDTO> getPayrollApprover(OrgLocations orgLocation, User loginUser);

	Response<PayrollApproverDTO> savePayrollApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation, User loginUser);

	ConfigurePayrollApprover getConfigurePayrollApproverByLoc(OrgLocations orgLocation);

	ResponseList<PayrollApproverDTO> getConfigurePayrollApprover(OrgLocations orgLocation);
}
