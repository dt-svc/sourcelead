package com.bis.sourcelead.payroll.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.CompareDataDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.PayrollReportService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class USPayrollReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollReportRestController.class);
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	USPayPeriodService usPayPeriodService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	PayrollReportService payrollReportService;
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;


	/**
	 * Returning Us Payroll Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usPayrollReport")
	public ModelAndView usPayrollReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/usPayrollReport");
		return model;
	}
	
	/**
	 * Returning US Contribution Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/contributionReport")
	public ModelAndView contributionReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/contributionReport");
		return model;
	}
	
	/**
	 * Returning US Deduction Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deductionReport")
	public ModelAndView deductionReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/deductionReport");
		return model;
	}
	
	/**
	 * Returning US Payroll Register Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollRegisterReport")
	public ModelAndView payrollRegisterReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollRegisterReport");
		return model;
	}
	
	/**
	 * Returning US Payroll Comparison Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollComparison")
	public ModelAndView payrollComparisonJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollComparison");
		return model;
	}
	
	/**
	 * Returning US Payroll Direct Deposit Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/directDepositReport")
	public ModelAndView directDepositReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/directDepositReport");
		return model;
	}
	/**
	 * Returning US Payroll List Summary Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollListSummaryReport")
	public ModelAndView payrollListSummaryReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollListSummaryReport");
		return model;
	}
	/**
	 * Returning US Employee Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeePayrollReport")
	public ModelAndView employeePayrollReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/employeePayrollReport");
		return model;
	}
	
	/**
	 * Returning US Check Register Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkRegisterReport")
	public ModelAndView checkRegisterReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/checkRegisterReport");
		return model;
	}
	
	/**
	 * Pay Groups by org location Id
	 * @param request
	 * @param response
	 * @return List<UserGroup> 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayGroupList","/rest/getPayGroupList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<UserGroup> findAllDeduction(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return userGroupService.getAllUserGroupsByLocationId(orgLocation);
	}
	
	@RequestMapping(value = { "/getPayPeriodByGroupId","/rest/getPayPeriodByGroupId" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayPeriodDTO> getPayPeriodByGroupId(@RequestParam(value = "groupId",required = false) Long groupId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayPeriodDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) { 
				List<PayPeriodDTO> PayPeriodDTOList = userGroupService.getPayPeriodsByGroupId(groupId);
				responseList.setData(PayPeriodDTOList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}
	
	
	@RequestMapping(value = { "/runPayrollComparison","/rest/runPayrollComparison" },method = RequestMethod.POST)
	public PayrollComparisonDTO runPayrollComparison(PayrollComparisonDTO payrollComparisonDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return payrollReportService.payrollComparisonData(payrollComparisonDTO,orgLocation);

	}

	@RequestMapping(value = { "/runPayrollregister","/rest/runPayrollregister" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<USPayrollDTO> runPayrollregister(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<USPayrollDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) { 
				List<USPayrollDTO> payrollRegisterReports = paysheetFrequencyService.getPayrollRegisterReports(payrollReportDTO);
				responseList.setData(payrollRegisterReports);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	
	/**
	 * Get Payroll Summary by pay period and paycheck Date
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayCheckDateReports","/rest/getPayCheckDateReports" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> getPayCheckDateReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"empName", "empPayRate", "regularHours", "overTimeHours", "preTaxDeduction", "postTaxDeduction", "taxes", "netPay", "lastPay", "YTD",""};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
		}
		payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollReportDTO.setSortOrder(sortColumnOrder);
		ResponseList<PayrollReportDTO> res = payrollReportService.getPayrollSummaryByPayPeriodAndPayCheckDate(orgLocation,payrollReportDTO);
		return res;
	}
	
	/**
	 * Get Check Register Payroll Reports 
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCheckRegisterReports","/rest/getCheckRegisterReports" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"empId",  "empName", "department", "netPay"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
		}
		payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollReportDTO.setSortOrder(sortColumnOrder);
		ResponseList<CheckRegisterReportDTO> res = payrollReportService.getCheckRegisterReports(orgLocation,payrollReportDTO);
		return res;
	}
	
	
	/**
	 * Get Check Register Payroll Reports 
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getContributionReports","/rest/getContributionReports" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> getContributionReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"type",  "empId","empName", "","schedule","dedAmount","ytdDeductions"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
		}
		payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollReportDTO.setSortOrder(sortColumnOrder);
		ResponseList<PayrollReportDTO> res = payrollReportService.getDeductionAndContributionReports(orgLocation,payrollReportDTO);
		return res;
	}
}
