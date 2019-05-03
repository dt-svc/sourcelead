package com.bis.sourcelead.payroll.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.AssociateReportDTO;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.DeductionSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;
import com.bis.sourcelead.payroll.service.DeductionHistoryService;
import com.bis.sourcelead.payroll.service.PayrollDeductionService;
import com.bis.sourcelead.payroll.service.PayrollReportService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.util.PayrollReportPdfUtil;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;

@RestController
@RequestMapping("/app")
public class USPayrollReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollReportRestController.class);
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	PayrollReportService payrollReportService;

	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;

	@Autowired
	USPayPeriodService usPayPeriodService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	PayrollReportPdfUtil payrollReportPdfUtil;
	
	@Autowired
	private EmployeesService employeesService;
	
    @Autowired
	DepartmentService departmentService;
    
    @Autowired
	private PayrollDeductionService payrollDeductionService;

    @Autowired
	DynamicReport dynamicReport;
    
    @Autowired
	private PaysheetFrequencyDao paysheetFrequencyDao;
    
    @Autowired
	private DeductionHistoryService deductionHistoryService;
    
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
	 * Returning US Payroll Taxes And Wages Report Jsp
	 * @author pavan jampana
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollTaxesAndWagesReport")
	public ModelAndView payrollTaxesAndWagesReport(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollTaxesAndWagesReport");
		return model;
	}
	
	/**
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
	@RequestMapping(value = "/usEmployeePayrollReport")
	public ModelAndView employeePayrollReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/usEmployeePayrollReport");
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
	 * Returning Us Associate Withholding JSP
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/associateWithholding")
	public ModelAndView associateWithholding(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/associateWithHolding");
		return model;
	}
	
	
	
	
	/**
	 * Returning US Deduction Summary Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usDeductionSummaryReport")
	public ModelAndView usDeductionSummaryReport(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/usDeductionSummaryReport");
		return model;
	}
	
	

	/**
	 * Returning US Contribution Summary Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usContributionSummaryReport")
	public ModelAndView usContributionSummaryReport(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/usContributionSummaryReport");
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
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) { 
				List<PayPeriodDTO> PayPeriodDTOList = userGroupService.getPayPeriodsByGroupId(groupId,orgLocation);
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

	/**
	 * @author pavan jampana
	 * 
	 * this method is used to run the payroll register report
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/runPayrollregister","/rest/runPayrollregister" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> runPayrollregister(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayrollReportDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", "", ""};
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
					sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
				}
				payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
				payrollReportDTO.setSortOrder(sortColumnOrder);
				
				responseList = paysheetFrequencyService.getPayrollRegisterReports(payrollReportDTO,orgLocations);
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
		String[] tableColumns = {"payrollDeductionType",  "employeeId","firstName", "departmentName","schedule","dedAmount","ytdDeductions"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
		}
		payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollReportDTO.setSortOrder(sortColumnOrder);
		ResponseList<PayrollReportDTO> res = payrollReportService.getContributionReports(orgLocation,payrollReportDTO);
		return res;
	}
	
	
	@RequestMapping(value = { "/getDeductionReports","/rest/getDeductionReports" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> getDeductionReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"payrollDeductionType",  "employeeId","firstName", "departmentName","schedule","dedAmount","ytdDeductions"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
			sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
		}
		payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
		payrollReportDTO.setSortOrder(sortColumnOrder);
		ResponseList<PayrollReportDTO> res = payrollReportService.getDeductionReports(orgLocation,payrollReportDTO);
		return res;
	}
	
	
	/**
	 * Get Check Register & Deposit Reports Payroll Reports 
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportToPdfCheckRegisterReport")
	public void exportToPdfCheckRegisterReport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CheckRegisterReportDTO> checkRegData = payrollReportService.getexportToPdfCheckRegisterReport(orgLocation,payrollReportDTO);
		
		File file = null;
		if("Cheque".equalsIgnoreCase(payrollReportDTO.getDepositType())) {
			file = payrollReportPdfUtil.getCheckRegisterReportPdf(checkRegData,orgLocation);
		}else {
			file = payrollReportPdfUtil.getDirectDepositExportFile(checkRegData,orgLocation,payrollReportDTO);
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}
	
	/**
	 * Export Pdf for Contribution
	 * 
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportToPdfContributionReport")
	public void exportToPdfContributionReport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PayrollReportDTO>  deductionHisList = payrollReportService.exportToPdfContributionReport(orgLocation,payrollReportDTO);
		File file = payrollReportPdfUtil.getontributionReportPdf(orgLocation,deductionHisList);
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
		
	}
	
	
	@RequestMapping(value = { "/exportPayrollRegisterReportPdf"})
	public void exportPayrollRegisterReportPdf(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayrollReportDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				responseList = paysheetFrequencyService.getPayrollRegisterReports(payrollReportDTO,orgLocations);
				if(responseList.getData().size() > 0) {
					File file =  payrollReportPdfUtil.getPayrollRegisterReportsPdf(responseList,orgLocations);
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					StringBuilder fileName = new StringBuilder();
					response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
					response.setContentLength((int) file.length());
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
					inputStream.close();
					response.getOutputStream().close();
					response.getOutputStream().flush();
					if (file != null) {
						file.delete();
					}
				}
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}

	@RequestMapping(value = "/exportToPdfDeductionReport")
	public void exportToPdfDeductionReport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<PayrollReportDTO>  deductionHisList = payrollReportService.exportToPdfDeductionReport(orgLocation,payrollReportDTO);
		
		File file = payrollReportPdfUtil.getDeductionReportPdf(orgLocation,deductionHisList);
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}
	

	/**
	 * @author pavan jampana
	 * 
	 * this method is to get payroll Tax and Wages Report
	 * 
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = { "/getpayrollTaxAndWagesReport","/rest/getpayrollTaxAndWagesReport" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> getpayrollTaxAndWagesReport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayrollReportDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
				
				/*String[] tableColumns = {"empId", "empName", "department", "", "", "", "", "", "", ""};
			    int sortColumnIndex = 1;		 //by default sorting using updated date
				String sortColumnOrder = "asc";  //by default descending order
				if(payrollReportDTO.getOrder() != null && !payrollReportDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(payrollReportDTO.getOrder().get(0).get("column"));
					sortColumnOrder = payrollReportDTO.getOrder().get(0).get("dir");
				}
				payrollReportDTO.setSortColumn(tableColumns[sortColumnIndex]);
				payrollReportDTO.setSortOrder(sortColumnOrder);*/
				
				responseList = paysheetFrequencyService.getpayrollTaxAndWagesReport(payrollReportDTO,orgLocations);
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
	 * @author pavan jampana
	 * 
	 * this method is to download payroll Tax And Wages Report Pdf
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getpayrollTaxAndWagesReportPdf","/rest/getpayrollTaxAndWagesReportPdf" }, method = RequestMethod.GET)
	@ResponseBody
	public void getpayrollTaxAndWagesReportPdf(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<PayrollReportDTO>  responseList = paysheetFrequencyService.getpayrollTaxAndWagesReport(payrollReportDTO,orgLocations);
		
		File file = null;
	    if(payrollReportDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file=  dynamicReport.payrollTaxAndWagesReportPdfExport(responseList,orgLocations);
	    }else {
	    	 file=  dynamicReport.payrollTaxAndWagesReportExcelExport(responseList,orgLocations);
	    }
	    
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
		 
	}

	/**get Employee payroll reports
	 * 
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/runEmployeePayroll","/rest/runEmployeePayroll" }, method = RequestMethod.GET)
	public ResponseList<AssociateReportDTO> runEmployeePayroll(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return paysheetFrequencyService.getEmployeePayrollListByPayCheckDate(payrollReportDTO,orgLocation);
	}
	
	/**
	 * get All Employees based on department
	 * @param departmentId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getEmployeesBasedOnDepartment","/rest/getEmployeesBasedOnDepartment" }, method = RequestMethod.GET)
	public List<UserDTO> getEmployeesBasedOnDepartment(@RequestParam(value="departmentId", required=true) String departmentId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return employeesService.getEmployeesBasedOnDepartment(departmentId,orgLocation);
	}

	@RequestMapping(value = { "/getAssociateWithHoldingReports","/rest/getAssociateWithHoldingReports" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<AssociateWithHoldingDTO> getAssociateWithHoldingReports(AssociateWithHoldingDTO associateWithHoldingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = {"employeeId","empName","ssnNumber","workLocation","federalFillingStatus","federalTaxAllowances",
				"residenceStateCode","stateFillingStatus","stateTaxAllowances"};
	    int sortColumnIndex = 1;		 //by default sorting using updated date
		String sortColumnOrder = "asc";  //by default descending order
		if(associateWithHoldingDTO.getOrder() != null && !associateWithHoldingDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(associateWithHoldingDTO.getOrder().get(0).get("column"));
			sortColumnOrder = associateWithHoldingDTO.getOrder().get(0).get("dir");
		}
		associateWithHoldingDTO.setSortColumn(tableColumns[sortColumnIndex]);
		associateWithHoldingDTO.setSortOrder(sortColumnOrder);
		ResponseList<AssociateWithHoldingDTO> res = payrollReportService.getAssociateWithHoldingReports(orgLocation,associateWithHoldingDTO);
		return res;
	}
	
	
	
	@RequestMapping(value = { "/findAllDepartments","/rest/findAllDepartments" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Department> getDepartmentList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		return departmentService.getDepartmentByOrgLocationId(orgLocation.getId());
		
	}
	
	/**
	 * employee payroll export
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = { "/exportEmployeePayroll","/rest/exportEmployeePayroll" }, method = RequestMethod.GET)
	public void exportEmployeePayroll(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<AssociateReportDTO>  responseList =  paysheetFrequencyService.getEmployeePayrollListByPayCheckDate(payrollReportDTO,orgLocation);
		File file = null;
	    if(payrollReportDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file=  payrollReportPdfUtil.employeePayrollPdfExport(responseList,orgLocation);
	    }else {
	    	 file=  payrollReportPdfUtil.employeePayrollExcelExport(responseList,orgLocation);
	    }
	    
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
	}
	
	
	/**
	 * associate withholding report export
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = { "/exportAssociateWithholding","/rest/exportAssociateWithholding" }, method = RequestMethod.GET)
	public void exportAssociateWithholding(AssociateWithHoldingDTO associateWithHoldingDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<AssociateWithHoldingDTO> res = payrollReportService.getAssociateWithHoldingReports(orgLocation,associateWithHoldingDTO);
		File file = null;
	    if(associateWithHoldingDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file=  payrollReportPdfUtil.associateWithholdingPdfExport(res,orgLocation);
	    }else {
	    	 file=  payrollReportPdfUtil.associateWithholdingExcelExport(res,orgLocation);
	    }
	    
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
	}
	
	/**
	 * deduction summary reports
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getDeductionSummaryReports","/rest/getDeductionSummaryReports" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<DeductionSummaryDTO> getDeductionSummaryReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return deductionHistoryService.getDedudctionDataBasedOnPayrollCheckDate(orgLocation,payrollReportDTO);
		
	}
	
	/**
	 * contribution summary reports
	 * @param payrollReportDTO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getContributionSummaryReports","/rest/getContributionSummaryReports" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<DeductionSummaryDTO> getContributionSummaryReports(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return deductionHistoryService.getContributionDataBasedOnPayrollCheckDate(orgLocation,payrollReportDTO);
		
	}
	
	@RequestMapping(value = "/exportdeductionContributionSummaryReport")
	public void exportdeductionContributionSummaryReport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		File file = null;
		ResponseList<DeductionSummaryDTO> checkRegData = null;
		if(Constants.DEDUCTION.equalsIgnoreCase(payrollReportDTO.getType())) {
			checkRegData = deductionHistoryService.getDedudctionDataBasedOnPayrollCheckDate(orgLocation,payrollReportDTO);
		}else {
			checkRegData = deductionHistoryService.getContributionDataBasedOnPayrollCheckDate(orgLocation, payrollReportDTO);
		}
		
		 if(payrollReportDTO.getExportType().equalsIgnoreCase("pdf")) {
			 file = payrollReportPdfUtil.getDeductionSummaryReportPdf(checkRegData.getData(),orgLocation,payrollReportDTO.getType());
		 }else {
			 file = payrollReportPdfUtil.getDeductionSummaryReportXls(checkRegData.getData(),orgLocation,payrollReportDTO.getType());
		 }
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}
	
	/**
	 * @author pavan jampana
	 * this method is is used to return Contract Employee Payreport Jsp page 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/contractEmployeePayreport")
	public ModelAndView contractEmployeePayreport(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/contractEmployeePayreport");
		return model;
	}
	
	
	/**
	 * @author pavan jampana
	 * this method is is used to return Contract Employee Payreport List 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getContractEmployeeReportList","/rest/getContractEmployeeReportList" }, method = RequestMethod.GET)
	public ResponseList<PayrollReportDTO> getContractEmployeeReportList(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return paysheetFrequencyService.getContractEmployeeReportList(payrollReportDTO,orgLocation);
	}
	
	/**
	 * @author pavan jampana
	 * this method is is used to export Contract Employee Payreport 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getContractEmployeeReportExport","/rest/getContractEmployeeReportExport" }, method = RequestMethod.GET)
	public void getContractEmployeeReportExport(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<PayrollReportDTO>  responseList = paysheetFrequencyService.getContractEmployeeReportList(payrollReportDTO,orgLocations);
		
		File file = null;
	    if(payrollReportDTO.getExportType().equalsIgnoreCase("pdf")) {
	    	 file=  dynamicReport.contractEmployeeReportPDFExport(responseList,orgLocations);
	    }else {
	    	 file=  dynamicReport.contractEmployeeReportExcelExport(responseList,orgLocations);
	    }
	    
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
		 
	}
	
}
