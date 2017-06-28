package com.bis.sourcelead.payroll.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.payroll.service.HrDeductionsService;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class EmployeePayrollRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeePayrollRestController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	EmployerPayrollCostService employerPayrollCostService;
	
	@Autowired
	private WageTaxSummaryService wageTaxSummaryService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private AccountDeductionsService accountDeductionsService;
	
	@Autowired
	private HrDeductionsService hrDeductionsService;
	
	@RequestMapping(value = "/uploadPayrollDocument", method = RequestMethod.POST)
	@ResponseBody 
	public String fileUploaded(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		
		long startTime = System.currentTimeMillis();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		MultipartFile multipartFile = null;
		Integer isInserted = 0;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
			multipartFile = request.getFile(itrator.next());
		}
		if(jsonObj.has("payrollType")){
			String payrollType = jsonObj.getString("payrollType");
			if(Constants.EMPLOYEE_PAYROLL.equalsIgnoreCase(payrollType)){
				 isInserted = employerPayrollCostService.saveEmployerPayrollCost(multipartFile,orgLocation,user,jsonObj);
			}else if(Constants.WAGE_TAX_SUMMARY.equalsIgnoreCase(payrollType)){
				 isInserted = wageTaxSummaryService.saveWagesTaxSummary(multipartFile,orgLocation,user,jsonObj);
			}else if(Constants.ACCOUNT_DEDUCTIONS.equalsIgnoreCase(payrollType)){
				 isInserted = accountDeductionsService.saveAccountDeductions(multipartFile,orgLocation,user,jsonObj);
			}else if(Constants.HR_DEDUCTIONS.equalsIgnoreCase(payrollType)){
				 isInserted = hrDeductionsService.saveHRDeductions(multipartFile,orgLocation,user,jsonObj);
			}
		}else{
			isInserted = wageTaxSummaryService.saveWagesTaxSummaryBulk(multipartFile,orgLocation,user);
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		logger.info("final Time responser time is: "+totalTime);
		return commonUtil.toJSON(isInserted);
	}
	
	@RequestMapping(value = "/loadEmployerPayrollCostByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String loadEmployerPayrollCostByOrgLocationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson = "";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<EmployerPayrollCost>  employerPayrollCostList = employerPayrollCostService.loadEmployerPayrollCostByOrgLocationId(orgLocation);
		if(employerPayrollCostList != null){
			for(EmployerPayrollCost employerPayrollCost:employerPayrollCostList){
				employerPayrollCost.setFromDateStr(conversionUtil.getDateAsString(employerPayrollCost.getFromDate()));
				employerPayrollCost.setToDateStr(conversionUtil.getDateAsString(employerPayrollCost.getToDate()));
			}
			resultJson = commonUtil.toJSON(employerPayrollCostList);
		}
		return resultJson;
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWagesAndTaxList", method = RequestMethod.GET)
	public @ResponseBody String getWagesAndTaxList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<WageTaxSummary>  wagesAndTaxList = wageTaxSummaryService.getWagesAndTaxListByOrgLocationId(orgLocation);
		return commonUtil.toJSON(wagesAndTaxList);
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAccountDeductionsList", method = RequestMethod.GET)
	public @ResponseBody String getAccountDeductionsList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<AccountDeductions> accountDeductionsList = accountDeductionsService.getAccountDeductionsListByOrgLocationId(orgLocation);
		return commonUtil.toJSON(accountDeductionsList);
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDeductionsDetailsbyId", method = RequestMethod.GET)
	public @ResponseBody String getDeductionsDetailsbyId(@RequestParam(value = "deductionId", required = false) String deductionId, HttpServletRequest request) throws Exception
	{
		List<AccountDeductionsDetails> accountDeductionsDetailsList = accountDeductionsService.getDeductionsDetailsbyId(Long.parseLong(deductionId));
		return commonUtil.toJSON(accountDeductionsDetailsList);
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHrDeductionsList", method = RequestMethod.GET)
	public @ResponseBody String getHrDeductionsList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<HrDeductions> hrDeductionsList = hrDeductionsService.getHrDeductionsListByOrgLocationid(orgLocation);
		return commonUtil.toJSON(hrDeductionsList);
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEmpHrDeductionsDetailsbyId", method = RequestMethod.GET)
	public @ResponseBody String getEmpHrDeductionsDetailsbyId(@RequestParam(value = "hrDeductionId", required = false) String hrDeductionId, HttpServletRequest request) throws Exception
	{
		List<HrDeductionsDetails> hrDeductionsList = hrDeductionsService.getHrDeductionsDetailsbyId(Long.parseLong(hrDeductionId));
		return commonUtil.toJSON(hrDeductionsList);
	}
	
}
