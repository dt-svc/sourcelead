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
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
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
	
	@RequestMapping(value = "/uploadPayrollDocument", method = RequestMethod.POST)
	@ResponseBody 
	public String fileUploaded(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
			multipartFile = request.getFile(itrator.next());
		}
		String payrollType = jsonObj.getString("payrollType");
		if(Constants.EMPLOYEE_PAYROLL.equalsIgnoreCase(payrollType)){
			String isInserted = employerPayrollCostService.saveEmployerPayrollCost(multipartFile,orgLocation,user,jsonObj);
		}else if(Constants.WAGE_TAX_SUMMARY.equalsIgnoreCase(payrollType)){
			String isInserted = wageTaxSummaryService.saveWagesTaxSummary(multipartFile,orgLocation,user,jsonObj);
		}
		return commonUtil.toJSON("success");
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
	
}
