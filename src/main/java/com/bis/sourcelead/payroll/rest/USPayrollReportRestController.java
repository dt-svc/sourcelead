package com.bis.sourcelead.payroll.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.Response;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;

import com.bis.sourcelead.payroll.dto.CompareDataDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.util.PayrollUtil;

import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;

import com.bis.sourcelead.payroll.service.PayrollReportService;

import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;

import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
		PayrollComparisonDTO compare = new PayrollComparisonDTO();
		try {
			
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String [] compareDates = new String[2];
			compareDates[0] = payrollComparisonDTO.getCompareDate1();
			compareDates[1] = payrollComparisonDTO.getCompareDate2();
			int i=0;
			for(String freqDates : compareDates) {
				CompareDataDTO compareData1 = new CompareDataDTO();
				PaysheetFrequency paySheet = paysheetFrequencyService.getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(freqDates,orgLocation,payrollComparisonDTO.getGroupId());
				List<EmployeePayroll> list1 = paySheet.getEmployeepayroll().stream().filter(emp ->emp.getSubmitForAproverStatus().equalsIgnoreCase(PayrollConstant.APPROVE)).collect(Collectors.toList());
				
				List<USPayrollDTO> employeePayList = new ArrayList<>();
				
				List<ContributionDTO> contributionHisList = new ArrayList<>();
				List<DeductionDTO> dedHisList = new ArrayList<>();
				List<AssociatePayItemDTO> payItemList = new ArrayList<>();
				
				Map<String, BigDecimal> taxMap = new HashMap<>();
				BigDecimal totalFederalWithholdAmt = BigDecimal.ZERO;
				BigDecimal totalFica = BigDecimal.ZERO;
				BigDecimal totalMedicare = BigDecimal.ZERO;
				BigDecimal totalEarnedIncCredit = BigDecimal.ZERO;
				BigDecimal totalStateWithloldAmt = BigDecimal.ZERO;
				BigDecimal totalLocalTax = BigDecimal.ZERO;
				BigDecimal totalSdi = BigDecimal.ZERO;
				BigDecimal totalSui = BigDecimal.ZERO;
				BigDecimal totalfli = BigDecimal.ZERO;
				BigDecimal totalhcsf = BigDecimal.ZERO;
				BigDecimal totalwc = BigDecimal.ZERO;
				
				for (EmployeePayroll payroll : list1) {
					USPayrollDTO usPayroll = new USPayrollDTO();
					usPayroll.setRegularHours(Float.parseFloat(payroll.getRegularHours()));
					if(payroll.getOverTimeHours() != null)
					usPayroll.setOvertimeHours(Double.parseDouble(payroll.getOverTimeHours()));
					usPayroll.setGrossPay(payroll.getGrossPay());
					usPayroll.setNetPay(payroll.getNetPay());
					
					
					List<ContributionDTO> clist = payrollUtil.getContributionData(payroll);
					contributionHisList.addAll(clist);	
					
					List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
					dedHisList.addAll(dlist);
					
					List<AssociatePayItemDTO> pList = payrollUtil.getPayItemData(payroll);
					payItemList.addAll(pList);
					
					
					for(EmployeePayrollTax tax : payroll.getUsPayrollTax()) {
						
						if(tax.getFederalWithholding() != null)
						totalFederalWithholdAmt = totalFederalWithholdAmt.add(tax.getFederalWithholding());
						if(tax.getFicaWithholding() != null)
						totalFica = totalFica.add(tax.getFicaWithholding());
						if(tax.getMedicareWithholding() != null)
						totalMedicare = totalMedicare.add(tax.getMedicareWithholding());
						if(tax.getEarnedIncCredit() != null)
						totalEarnedIncCredit = totalEarnedIncCredit.add(tax.getEarnedIncCredit());
						if(tax.getStateWithholding() != null)
						totalStateWithloldAmt = totalStateWithloldAmt.add(tax.getStateWithholding());
						if(tax.getLocalTax() != null)
						totalLocalTax = totalLocalTax.add(tax.getLocalTax());
						if(tax.getSDI() != null)
						totalSdi = totalSdi.add(tax.getSDI());
						if(tax.getSUI() != null)
						totalSui = totalSui.add(tax.getSUI());
						if(tax.getFLI() != null)
						totalfli = totalfli.add(tax.getFLI());
						if(tax.getHCSF() != null)
						totalhcsf = totalhcsf.add(tax.getHCSF());
						if(tax.getWC() != null)
						totalwc = totalwc.add(tax.getWC());
						
					}
					
					employeePayList.add(usPayroll);
				}
				
				taxMap.put("Federal Withholding", totalFederalWithholdAmt);
				taxMap.put("FICA",totalFica);
				taxMap.put("Medicare",totalMedicare);
				taxMap.put("Advanced Earned Income Credit",totalEarnedIncCredit);
				taxMap.put("State Withholding",totalStateWithloldAmt);
				taxMap.put("LocalTax",totalLocalTax);
				taxMap.put("SUI",totalSdi);
				taxMap.put("SUI", totalSui);
				taxMap.put("FLI", totalfli);
				taxMap.put("HCSF", totalhcsf);
				taxMap.put("WC", totalwc);
				
				//contributionData
				Map<String, Double> contribution = null;
				if(contributionHisList.size() >0) {
					 contribution = contributionHisList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getContributionAmt)));
				}
				
				//deductionData
				Map<String, Double> deductionMap = null;
				if(payItemList.size() > 0) {
					deductionMap = dedHisList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
				}
				
				
				
				//payItemDta
				Map<String, Double> payItem = null;
				if(payItemList.size() > 0) {
					payItem = payItemList.stream().collect(Collectors.groupingBy(AssociatePayItemDTO::getPayItemName,Collectors.summingDouble(AssociatePayItemDTO::getPayItemAmt)));
				}
		
				
				double totalRegularHours = employeePayList.stream().mapToDouble(reg ->reg.getRegularHours()).sum();
				double totalOvertimeHours = employeePayList.stream().mapToDouble(reg ->reg.getOvertimeHours()).sum();
				BigDecimal totalGrossPay = employeePayList.stream().map(USPayrollDTO::getGrossPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				BigDecimal totalNetPay = employeePayList.stream().map(USPayrollDTO::getNetPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				
				compareData1.setContriMap(contribution);
				compareData1.setPayItemMap(payItem);
				compareData1.setDedMap(deductionMap);
				compareData1.setTotalRegHours(totalRegularHours);
				compareData1.setTotalOTHours(totalOvertimeHours);
				compareData1.setTotalGross(totalGrossPay);
				compareData1.setNetPay(totalNetPay);
				compareData1.setTaxMap(taxMap);
				if(i == 0)
				compare.setCompareData1(compareData1);
				
				if(i == 1) {
					compare.setCompareData2(compareData1);
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return compare;
	}

	@RequestMapping(value = { "/runPayrollregister","/rest/runPayrollregister" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<PayrollReportDTO> runPayrollregister(PayrollReportDTO payrollReportDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayrollReportDTO> responseList = new ResponseList<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (orgLocations != null) { 
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
		ResponseList<PayrollReportDTO> res = payrollReportService.getDeductionAndContributionReports(orgLocation,payrollReportDTO);
		return res;
	}
}
