package com.bis.sourcelead.payroll.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.impl.USPayrollDaoImpl;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.PayrollContributionService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.USPayrollService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class USPayrollRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollRestController.class);
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	USPayrollService uSPayrollService;
	
	@Autowired
	PayrollDeductionService payrollDeductionService;
	
	@Autowired
	PayrollContributionService payrollContributionService;
	
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
		return  payrollDeductionService.savePayrollDeduction(payrollDeductionDTO,orgLocation,user);	
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
		return  payrollDeductionService.findAllPayrollDeduction(payrollDeductionDTO,orgLocation);	
	}
	
	
	@RequestMapping(value = { "/getAllDeductionType","/rest/getAllDeductionType" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollDeductionDTO> getAllDeductionTypeByLocationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return  payrollDeductionService.getAllDeductionTypeByLocationId(orgLocation);	
	}
	
	/**
	 * Add Payroll Contribution
	 * 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/savePayrollContribution","/rest/savePayrollContribution" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollContributionDTO> savePayrollContribution(PayrollContributionDTO payrollContributionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return payrollContributionService.savePayrollContribution(payrollContributionDTO, orgLocation, user);
	}
	
	
	/**
	 * Add Payroll Deductions 
	 * @param payrollDeductionDTO
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = { "/findAllPayrollContribution","/rest/findAllPayrollContribution" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollContributionDTO> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"", "description","payrollContributionMethod", "contributionAmount", };
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollContributionDTO.getOrder() != null && !payrollContributionDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollContributionDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollContributionDTO.getOrder().get(0).get("dir");
		}
		payrollContributionDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollContributionDTO.setSortOrder(sortColumnOrder);
		return  payrollContributionService.findAllPayrollContribution(payrollContributionDTO,orgLocation);	
	}
	
	/**
	 * Edit Payroll Deduction Type
	 * @param deductionUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDeductionType")
	public ModelAndView editDeductionType(@RequestParam(value="deductionUUID", required=false) String deductionUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   PayrollDeduction payrollDeduction = payrollDeductionService.findPayrollDeductionByUUID(deductionUUID);
		   model.addObject("payrollDeduction",payrollDeduction);
		   model.setViewName("payroll/addDeduction");
	       return model;
	}
	
	/**
	 * Edit Payroll Contribution Type
	 * @param contributionUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editContributionType")
	public ModelAndView editContributionType(@RequestParam(value="contributionUUID", required=false) String contributionUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   PayrollContribution payrollContribution = payrollContributionService.findPayrollContributionByUUID(contributionUUID);
		   model.addObject("deductionType",payrollContribution.getDeductionTypeId().getPayrollDeductionType());
		   model.addObject("payrollContribution",payrollContribution);
		   model.setViewName("payroll/addContribution");
	       return model;
	}
	
	/**
	 * Delete Contribution Type by Id
	 * @param payrollContributionDTO
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/deleteContributionType","/rest/deleteContributionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteSelectedContributionType(PayrollContributionDTO payrollContributionDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (payrollContributionDTO.getContributionId() !=0 ) {
			return payrollContributionService.deleteContributionById(payrollContributionDTO.getContributionId());
		}
		return false;
	}
	
	/**
	 * Delete Deduction Type by Id
	 * 
	 * @param payrollDeduction
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/deleteDeductionType","/rest/deleteDeductionType" }, method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteSelectedContributionType(PayrollDeduction payrollDeduction, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (payrollDeduction.getId() !=0 ) {
			return payrollDeductionService.deleteDeductionById(payrollDeduction.getId());
		}
		return false;
	}
}
