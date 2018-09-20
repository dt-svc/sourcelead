package com.bis.sourcelead.payroll.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.LCAWageDTO;
import com.bis.sourcelead.payroll.dto.ProjectRateDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface GenerateUSPayrollDao {

	PaysheetFrequency getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser);

	EmployeePayroll getEmployeePayrollByUUID(String payrollUUID);

	LCAWageDTO getLcaWageByCandidateIdLcationId(Candidate candidate, OrgLocations orgLocation);

	BigDecimal payRateByCandidateLocation(Candidate candidate, OrgLocations orgLocation, Date freqStartDate, Date freqEndDate, String payFrequency);


}
