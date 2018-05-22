package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;

public interface GenerateUSPayrollService {

	ResponseList<USPayrollDTO> generateUSPayroll(OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO) throws Exception;
}
