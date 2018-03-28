package com.bis.sourcelead.payroll.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class USPayrollRestController {
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	USPayrollService uSPayrollService;
	
	/**
	 * find all deduction types from lookup_data table 
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllLookUpDeductionTypes","/rest/findAllLookUpDeductionTypes" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> findAllDeduction(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			return  uSPayrollService.findAllDeduction();	
	}
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/savePayrollDeduction","/rest/savePayrollDeduction" }, method = RequestMethod.POST)
	@ResponseBody
	public PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO , HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return  uSPayrollService.savePayrollDeduction(payrollDeductionDTO,orgLocation,user);	
	}
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllPayrollDeduction","/rest/findAllPayrollDeduction" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"payrollDeductionType", "description", "payrollDeductionMethod", "deductionAmount"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollDeductionDTO.getOrder() != null && !payrollDeductionDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollDeductionDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollDeductionDTO.getOrder().get(0).get("dir");
		}
		payrollDeductionDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollDeductionDTO.setSortOrder(sortColumnOrder);
		return  uSPayrollService.findAllPayrollDeduction(payrollDeductionDTO,orgLocation);	
	}
}
