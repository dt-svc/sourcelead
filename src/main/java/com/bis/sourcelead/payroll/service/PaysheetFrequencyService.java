package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.MyPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;

public interface PaysheetFrequencyService {

	Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation, User loginUser);

	ResponseList<MyPayrollDTO> getMyPayrollListByCandidateId(Long candidateId, OrgLocations orgLocation);
	
}
