package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.EmployerPayrollCostDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Service
public interface EmployerPayrollCostService {

	Integer saveEmployerPayrollCost(MultipartFile multipartFile,OrgLocations orgLocation,User user, JSONObject jsonObj)throws Exception;

	ResponseList<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation, EmployerPayrollCostDTO employerPayrollCostDTO)throws Exception;

	List<UserPayroll> runPayrollCycle(OrgLocations orgLocation, User user,String isGlobalAccess,JSONObject jsonObj)throws Exception;
	
	int getEmployerPayrollCostVersionByLocationId(Long locationId);

	ResponseList<PayrollBalance> getAllPayrollBalanceSheetByOrgLocationId(OrgLocations orgLocation,PayrollBalance payrollBalance, String loginUserRole)	throws Exception;

	List<EmployerPayrollCost> getEmployerPayrollCostByLocationId(Long locationId);
	
	EmployerPayrollCost saveEmpPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception;

}
