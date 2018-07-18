package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;

public interface GenerateUSPayrollService {

	ResponseList<USPayrollDTO> generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO, User loginUser) throws Exception;

	List<USPayrollDTO> getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser);

	Response<USPayrollDTO> getEmployeePayrollByUUID(String payrollUUID, OrgLocations orgLocation);
}
