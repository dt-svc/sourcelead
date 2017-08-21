package com.bis.sourcelead.payroll.rest;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.payroll.service.HrDeductionsService;
import com.bis.sourcelead.payroll.service.InvoiceDataService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
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
	
	@Autowired
	private BisUserMappingService  bisUserMappingService;
	
	@Autowired
	UserPayrollService userPayrollService;
	
	@Autowired
	InvoiceDataService invoiceDataService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	/**
	 * insert upload rcords 
	 * @param json
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadPayrollDocument", method = RequestMethod.POST)
	@ResponseBody 
	public String fileUploaded(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		
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
			}else if(Constants.BIS_USERS.equalsIgnoreCase(payrollType)){
				isInserted = bisUserMappingService.saveBisUserData(multipartFile,orgLocation,user,jsonObj);
			}else if(Constants.BIS_CHECKS.equalsIgnoreCase(payrollType)){
				isInserted = bisUserMappingService.saveChecksData(multipartFile,orgLocation,user,jsonObj);
			}else if(Constants.INVOICE_DATA.equalsIgnoreCase(payrollType)) {
				isInserted = invoiceDataService.saveInvoiceData(multipartFile,orgLocation,user,jsonObj);
			}
		}else{
			isInserted = wageTaxSummaryService.saveWagesTaxSummaryBulk(multipartFile,orgLocation,user);
		}
		return commonUtil.toJSON(isInserted);
	}
	/**
	 * load EmployerPayrollCost using OrgLocationId
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/loadEmployerPayrollCostByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String loadEmployerPayrollCostByOrgLocationId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resultJson = "";
		DecimalFormat df = new DecimalFormat("#.00");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<EmployerPayrollCost>  employerPayrollCostList = employerPayrollCostService.loadEmployerPayrollCostByOrgLocationId(orgLocation);
		if(employerPayrollCostList != null){
			for(EmployerPayrollCost employerPayrollCost:employerPayrollCostList){
				StringBuilder sb = new StringBuilder();
				employerPayrollCost.setFromDateStr(conversionUtil.getDateAsString(employerPayrollCost.getFromDate()));
				employerPayrollCost.setToDateStr(conversionUtil.getDateAsString(employerPayrollCost.getToDate()));
				String ssn = employerPayrollCost.getSsn();
				String[] arr = ssn.split("-");
				sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
				employerPayrollCost.setSsn(sb.toString());
				double totHrs = Double.parseDouble(employerPayrollCost.getTotalHrs());
				employerPayrollCost.setTotalHrs(String.valueOf(df.format(totHrs)));
			}
			resultJson = commonUtil.toJSON(employerPayrollCostList);
		}
		return resultJson;
	}
	
	/**
	 * @author  Pavan Jampana
	 * @return json
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
	
	/**
	 * Generate payrolls for approved candidates
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/runPayrollCycle", method = RequestMethod.GET)
	public @ResponseBody String generatePayroll(@RequestParam(value = "json", required = false) String json,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		List<UserPayroll> payrollReportList = employerPayrollCostService.runPayrollCycle(orgLocation,user,jsonObj);
//		Collections.sort(payrollReportList,new FinalPayrollComparator());  
		Collections.sort(payrollReportList, new Comparator() {  
            @Override  
            public int compare(Object obj1, Object obj2) {  
            	UserPayroll report1 = (UserPayroll)obj1;  
            	UserPayroll report2 = (UserPayroll)obj2; 
            	return report1.getEmployeeName().compareToIgnoreCase(report2.getEmployeeName());  
            }  
        });
	
		return commonUtil.toJSON(payrollReportList);
	}


	/**
	 * Merge Bis user with sl user
	 * 
	 * @param json
	 * @param request
	 * @param response
	 * @return boolean
	 */
	@RequestMapping(value = "/mergeUser", method = RequestMethod.POST)
	public @ResponseBody boolean mergeUser(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isMerged = false;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		BisUserMapping bisUser = bisUserMappingService.mergeUser(jsonObj,orgLocation.getId());
		if (bisUser != null) {
			isMerged = true;
		}
		return isMerged;

	}

	/**
	 * Gte the list of mapped users
	 * @param request
	 * @param response
	 * @return json string
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMappedUserList", method = RequestMethod.GET)
	@ResponseBody
	public String getMappedUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String resultJson = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<BisUserMapping> usersList = bisUserMappingService.getMappedUsersListByOrgLocationId(orgLocation.getId());
		if (usersList != null) {
			resultJson = commonUtil.toJSON(usersList);
		}
		return resultJson;
	}
	
	@RequestMapping(value = "/updateAccountDeduction", method = RequestMethod.POST)
	@ResponseBody 
	public String updateAccountDeduction(@RequestParam(value = "accDeductionId", required = false) String accDeductionId,@RequestParam(value = "dedAmount", required = false) String dedAmount,HttpServletRequest request) throws Exception {
		
		AccountDeductions accountDeductions = accountDeductionsService.getDeductionsbyId(Long.parseLong(accDeductionId));
		accountDeductions.setDedAmount(Float.parseFloat(dedAmount));
		AccountDeductions updatedAccountDeductions = accountDeductionsService.saveAccountDeductions(accountDeductions);
		return commonUtil.toJSON(updatedAccountDeductions.getId());
	}
	
	/**
	 * @author BISraja used for user payroll list based on user id
	 * 
	 * @param userId
	 * @param orgLocationId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getUserPayrollList", "/rest/getUserPayrollList" }, method = RequestMethod.GET)
	public List<UserPayroll> getUserPayrollList(@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "selectedYear", required = false) String selectedYear)
					throws Exception {
		List<UserPayroll> userpayroll = userPayrollService.getPayByUserId(userId, orgLocationId,selectedYear);
		return userpayroll;
	}
	
	/**
	 * Get Checks list of payroll
	 * @param userId
	 * @param orgLocationId
	 * @return json object
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getChecksList", "/rest/getChecksList" }, method = RequestMethod.GET)
	public List<Checks> getChecksList(@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId)
					throws Exception {
		List<Checks> checksList = userPayrollService.getChecksList(userId, orgLocationId);
		return checksList;
	}
	
	
	/**
	 * Returns payroll documents upload jsp
	 * @param request
	 * @return ModelAndView
	 */
	 @RequestMapping(value = "/uploadDocuments")
		public ModelAndView fileUploadForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PAYROLL);
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("payroll/payrollUploadForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}
	 
	 @RequestMapping(value = { "/getEmployeeList", "/rest/getEmployeeList" }, method = RequestMethod.GET)
		public List<BisUserMapping> getEmployeeList(@RequestParam(value = "userId", required = false) Long userId,
				@RequestParam(value = "orgLocationId", required = false) Long orgLocationId)
						throws Exception {
			List<BisUserMapping> bisUsersList = bisUserMappingService.getEmployeeList(orgLocationId);
			return bisUsersList;
		}
}
