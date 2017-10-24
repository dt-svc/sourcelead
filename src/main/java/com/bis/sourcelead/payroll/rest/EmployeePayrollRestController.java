package com.bis.sourcelead.payroll.rest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.EmployeeListDTO;
import com.bis.sourcelead.dto.HrDeductionsDTO;
import com.bis.sourcelead.dto.EmployerPayrollCostDTO;
import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.WagesTaxSummaryDTO;
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
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.AccountDeductionsService;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.payroll.service.HrDeductionsService;
import com.bis.sourcelead.payroll.service.InvoiceDataService;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DecimalFormat;

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
	
	@Autowired
	OrgLocationService orgLocationService;
	
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
	@RequestMapping(value = {"/loadEmployerPayrollCostByOrgLocationId", "/rest/loadEmployerPayrollCostByOrgLocationId"}, method = RequestMethod.POST)
	public @ResponseBody  ResponseList<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(EmployerPayrollCostDTO employerPayrollCostDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
//		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(employerPayrollCostDTO.getOrgLocationId()));
		
		String[] tableColumns = {"fromDate", "toDate", "employeeId", "employeeName", "ssn", "totalHrs","grossWages","fica","futa","sui","erLocals","wcCost","adminFee","totalErCost"};
	     int sortColumnIndex = 3; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(employerPayrollCostDTO.getOrder() != null && !employerPayrollCostDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(employerPayrollCostDTO.getOrder().get(0).get("column"));
			sortColumnOrder = employerPayrollCostDTO.getOrder().get(0).get("dir");
		}
		employerPayrollCostDTO.setSortColumn(tableColumns[sortColumnIndex]);
		employerPayrollCostDTO.setSortOrder(sortColumnOrder);
		
		ResponseList<EmployerPayrollCost> list = employerPayrollCostService.loadEmployerPayrollCostByOrgLocationId(orgLocation,employerPayrollCostDTO);
		List<EmployerPayrollCost> data = getEmployerCostSortedList(list.getData());
		list.setData(data);
		return list;
	}
	
	

	private List<EmployerPayrollCost> getEmployerCostSortedList(List<EmployerPayrollCost> employerPayrollCostList) {
		if(employerPayrollCostList != null && !employerPayrollCostList.isEmpty()){
			DecimalFormat df = new DecimalFormat("#.00");
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
		}
		return employerPayrollCostList;
	}

	/**
	 * @author  Pavan Jampana
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getWagesAndTaxList", "/rest/getWagesAndTaxList"}, method = RequestMethod.POST)
	@ResponseBody
	public  ResponseList<WageTaxSummary> getWagesAndTaxList(WagesTaxSummaryDTO wagesTaxSummaryDTO ,HttpServletRequest request) throws Exception
	{
//		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(wagesTaxSummaryDTO.getOrgLocationId()));
		
		String[] tableColumns = {"fromDate", "toDate", "employeeId", "employeeName", "ssnNumber", "checkDate","checkNo","gross","fwt","ssec","med","swt","sdiSui","local","deds","net","batchNumber"};
	     int sortColumnIndex = 3; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(wagesTaxSummaryDTO.getOrder() != null && !wagesTaxSummaryDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(wagesTaxSummaryDTO.getOrder().get(0).get("column"));
			sortColumnOrder = wagesTaxSummaryDTO.getOrder().get(0).get("dir");
		}
		wagesTaxSummaryDTO.setSortColumn(tableColumns[sortColumnIndex]);
		wagesTaxSummaryDTO.setSortOrder(sortColumnOrder);
		
		ResponseList<WageTaxSummary> list = wageTaxSummaryService.getWagesAndTaxListByOrgLocationId(orgLocation,wagesTaxSummaryDTO);
		List<WageTaxSummary> data = getSortWageTaxList(list.getData());
		list.setData(data);
		return list;
//		return wageTaxSummaryService.getWagesAndTaxListByOrgLocationId(orgLocation,wagesTaxSummaryDTO);
	}
	
	
	/**
	 * Hiding ssn number into unreadable format
	 * @param wagesAndTaxList
	 * @return List<WageTaxSummary>
	 */
	private List<WageTaxSummary> getSortWageTaxList(List<WageTaxSummary> wagesAndTaxList) {
		if(wagesAndTaxList != null){
			for(WageTaxSummary wts : wagesAndTaxList){
				StringBuilder sb = new StringBuilder();
				if(null !=  wts.getFromDate()){
					wts.setFromDateStr(conversionUtil.getDateAsString(wts.getFromDate()));
				}
				if(null != wts.getToDate()){
					wts.setToDateStr(conversionUtil.getDateAsString(wts.getToDate()));
				}
				
				if(wts.getCheckDate() !=null   ){
					wts.setCheckDateStr(conversionUtil.getDateAsString(wts.getCheckDate()));
				}
				if(wts.getCheckNo() != null){
					wts.setCheckNo(wts.getCheckNo());
				}
				String ssn = wts.getSsnNumber();
				String[] arr = ssn.split("-");
				sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
				wts.setSsnNumber(sb.toString());
			}
			
			Collections.sort(wagesAndTaxList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	WageTaxSummary wageTaxSummary1 = (WageTaxSummary)obj1;  
	            	WageTaxSummary wageTaxSummary2 = (WageTaxSummary)obj2; 
	            	return wageTaxSummary1.getEmployeeName().compareToIgnoreCase(wageTaxSummary2.getEmployeeName());  
	            }  
	        });
		}
		return wagesAndTaxList;
		
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getAccountDeductionsList", "/rest/getAccountDeductionsList"}, method = RequestMethod.POST)
	public  ResponseList<AccountDeductions> getAccountDeductionsList(AccountDeductionsDTO accountDeductionsDTO ,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(accountDeductionsDTO.getOrgLocationId()));
		
		String[] tableColumns = {"employeeId", "employeeName", "fromDate", "toDate", "dedAmount", "batchNumber","requestSubDate","requestApprovedDate","repaidStatus"};
	     int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(accountDeductionsDTO.getOrder() != null && !accountDeductionsDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(accountDeductionsDTO.getOrder().get(0).get("column"));
			sortColumnOrder = accountDeductionsDTO.getOrder().get(0).get("dir");
		}
		accountDeductionsDTO.setSortColumn(tableColumns[sortColumnIndex]);
		accountDeductionsDTO.setSortOrder(sortColumnOrder);
		
		return accountDeductionsService.getAccountDeductionsListByOrgLocationId(orgLocation,accountDeductionsDTO);
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getDeductionsDetailsbyId", "/rest/getDeductionsDetailsbyId"}, method = RequestMethod.GET)
	@ResponseBody
	public  List<AccountDeductionsDetails> getDeductionsDetailsbyId(@RequestParam(value = "deductionId", required = false) String deductionId, HttpServletRequest request) throws Exception
	{
		List<AccountDeductionsDetails> accountDeductionsDetailsList = accountDeductionsService.getDeductionsDetailsbyId(Long.parseLong(deductionId));
		return accountDeductionsDetailsList;
	}
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getHrDeductionsList", "/rest/getHrDeductionsList"}, method = RequestMethod.POST)
	@ResponseBody
	public  ResponseList<HrDeductions> getHrDeductionsList(HrDeductionsDTO hrDeductionsDTO ,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(hrDeductionsDTO.getOrgLocationId()));
		
		String[] tableColumns = {"employeeId", "employeeName", "fromDate", "toDate", "dedAmount", "batchNumber","requestSubDate","requestApprovedDate","repaidStatus"};
	     int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(hrDeductionsDTO.getOrder() != null && !hrDeductionsDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(hrDeductionsDTO.getOrder().get(0).get("column"));
			sortColumnOrder = hrDeductionsDTO.getOrder().get(0).get("dir");
		}
		hrDeductionsDTO.setSortColumn(tableColumns[sortColumnIndex]);
		hrDeductionsDTO.setSortOrder(sortColumnOrder);
		
		return hrDeductionsService.getHrDeductionsListByOrgLocationid(orgLocation,hrDeductionsDTO);
	}
	
	
	/**
	 * @author : Pavan Jampana
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getEmpHrDeductionsDetailsbyId","/rest/getEmpHrDeductionsDetailsbyId"}, method = RequestMethod.GET)
	@ResponseBody
	public  List<HrDeductionsDetails> getEmpHrDeductionsDetailsbyId(@RequestParam(value = "hrDeductionId", required = false) String hrDeductionId, HttpServletRequest request) throws Exception
	{
		List<HrDeductionsDetails> hrDeductionsList = hrDeductionsService.getHrDeductionsDetailsbyId(Long.parseLong(hrDeductionId));
		return hrDeductionsList;
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
	@RequestMapping(value = {"/runPayrollCycle","/rest/runPayrollCycle"}, method = RequestMethod.GET)
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
		
		int  count = wageTaxSummaryService.mergeUser(jsonObj,orgLocation);
//		BisUserMapping bisUser = bisUserMappingService.mergeUser(jsonObj,orgLocation.getId());
		if (count >0) {
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
	@RequestMapping(value = {"/getMappedUserList", "/rest/getMappedUserList"}, method = RequestMethod.GET)
	@ResponseBody
	public List<WageTaxSummary> getMappedUserList(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		List<WageTaxSummary>  wageTaxSummaryList = wageTaxSummaryService.getMappedUsersListByOrgLocationId(orgLocationId);
//		List<BisUserMapping> usersList = bisUserMappingService.getMappedUsersListByOrgLocationId(orgLocation.getId());
		if (wageTaxSummaryList != null) {
			return wageTaxSummaryList;
		}
		return null;
	}
	
	@RequestMapping(value = "/updateAccountDeduction", method = RequestMethod.POST)
	@ResponseBody 
	public String updateAccountDeduction(@RequestParam(value = "accDeductionId", required = false) String accDeductionId,@RequestParam(value = "dedAmount", required = false) String dedAmount,HttpServletRequest request) throws Exception {
		
		AccountDeductions accountDeductions = accountDeductionsService.getDeductionsbyId(Long.parseLong(accDeductionId));
		accountDeductions.setDedAmount(Float.parseFloat(dedAmount));
		AccountDeductions updatedAccountDeductions = accountDeductionsService.saveAccountDeductions(accountDeductions);
		return commonUtil.toJSON(updatedAccountDeductions.getId());
	}
	
	
	@RequestMapping(value = "/updateHrDeduction", method = RequestMethod.POST)
	@ResponseBody 
	public String updateHrDeduction(@RequestParam(value = "hrDeductionId", required = false) String hrDeductionId,@RequestParam(value = "dedAmount", required = false) String dedAmount,HttpServletRequest request, String hrrDeductionId) throws Exception {
		
		HrDeductions hrDeductions = hrDeductionsService.getDeductionsbyId(Long.parseLong(hrDeductionId));
		hrDeductions.setDedAmount(Float.parseFloat(dedAmount));
		HrDeductions updatedHrDeductions = hrDeductionsService.saveHrDeductions(hrDeductions);
		return commonUtil.toJSON(updatedHrDeductions.getId());
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
	
	
	
	@RequestMapping(value = { "/getChecksList", "/rest/getChecksList" }, method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public ResponseList<Checks> getChecksList(ChecksDTO checksDTO,HttpServletRequest request)
					throws Exception {
		
		
//	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] tableColumns = {"empId", "empName", "date", "typeOfExpense", "bankName", "month","legalFees","advance","generalExpenses","vendorPayments","electronicWithdrawals","payrollChecks","amount","checkNo"};
	     int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(checksDTO.getOrder() != null && !checksDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(checksDTO.getOrder().get(0).get("column"));
			sortColumnOrder = checksDTO.getOrder().get(0).get("dir");
		}
		checksDTO.setSortColumn(tableColumns[sortColumnIndex]);
		checksDTO.setSortOrder(sortColumnOrder);
		
		return userPayrollService.getChecksList(checksDTO, Long.parseLong(checksDTO.getOrgLocationId()));
		
		// TODO do not delete @Rajashekar
		/*List<Checks> checksList = userPayrollService.getChecksList(userId, orgLocationId);
		return checksList;*/
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
	 
	 @RequestMapping(value = { "/getEmployeeList", "/rest/getEmployeeList" }, method = RequestMethod.POST,produces = "application/json")
	 @ResponseBody
	 public ResponseList<BisUserMapping> getEmployeeList(EmployeeListDTO employeeListDTO,HttpServletRequest request)
						throws Exception {
//		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			String[] tableColumns = {"company", "branch", "empId", "candidateName", "rate", "fixedRate","payRate","fixedPay","insurance","ded","effectiveRate","payrollDifference","immigration","comments","advanceHcp","balance"};
		     int sortColumnIndex = 3; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default descending order
			if(employeeListDTO.getOrder() != null && !employeeListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(employeeListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = employeeListDTO.getOrder().get(0).get("dir");
			}
			employeeListDTO.setSortColumn(tableColumns[sortColumnIndex]);
			employeeListDTO.setSortOrder(sortColumnOrder);
			
			return bisUserMappingService.getEmployeeList(employeeListDTO, Long.parseLong(employeeListDTO.getOrgLocationId()));
			// TODO do not delete @Rajashekar
//			List<BisUserMapping> bisUsersList = bisUserMappingService.getEmployeeList(orgLocationId);
//			return bisUsersList;
		}
	 
	 /**
	  * Get Invoice Data Details
	  * @param userId
	  * @param orgLocationId
	  * @return 
	  * @throws Exception
	  */
	 
																				 
	 
	 @RequestMapping(value = { "/getInvoiceDetails", "/rest/getInvoiceDetails"}, method = RequestMethod.POST,produces = "application/json")
	 @ResponseBody
		public  ResponseList<InvoiceData> getInvoiceDetails (InvoiceDetailsDTO invoiceDetailsDTO ,HttpServletRequest request) throws Exception
		{
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 
		 String[] tableColumns = {"sku", "productService", "empName", "number", "memoDescription", "date","lastmodified","dueDate","rate","qty","amount","arPaid","oldNew"};
	     int sortColumnIndex = 2; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(invoiceDetailsDTO.getOrder() != null && !invoiceDetailsDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(invoiceDetailsDTO.getOrder().get(0).get("column"));
			sortColumnOrder = invoiceDetailsDTO.getOrder().get(0).get("dir");
		}
		invoiceDetailsDTO.setSortColumn(tableColumns[sortColumnIndex]);
		invoiceDetailsDTO.setSortOrder(sortColumnOrder);
		
		return invoiceDataService.getInvoiceDetails(invoiceDetailsDTO, orgLocation);
		}
		
	 
	 
	/**
	 * Get Invoice Data Details
	 * 
	 * @param userId
	 * @param orgLocationId
	 * @return List<InvoiceData>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getInvoiceDetailsByEmpId","/rest/getInvoiceDetailsByEmpId"}, method = RequestMethod.GET)
	public List<InvoiceData> getInvoiceDetailsByEmpId(@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<InvoiceData> invoiceDataList = null;
		if(empId !=null){
			 invoiceDataList = invoiceDataService.getInvoiceDetailsByEmpId(empId, orgLocations);
		}else{
			return invoiceDataList;
		}
		if(invoiceDataList !=null && invoiceDataList.size() >0){
			return invoiceDataList;
		}
		return null;
	}
	
	
	/**
	 * checks list by employeeid
	 * 
	 * @param empId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getChecksListByEmployeeId", "/rest/getChecksListByEmployeeId"}, method = RequestMethod.GET)
	public List<Checks> getChecksListByEmployeeId(@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<Checks> checksList = null;
		if(empId !=null){
			checksList =userPayrollService.getChecksListByEmployeeId(empId, orgLocations);
		}else{
			return checksList;
		}
		if(checksList !=null && checksList.size() >0){
			return checksList;
		}
		
		return null;
	}
	
	/**
	 * wages and tax summary by employeeid
	 * 
	 * @param empId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getWagesTaxSummaryByEmpId", "/rest/getWagesTaxSummaryByEmpId"}, method = RequestMethod.GET)
	public List<WageTaxSummary> getWagesTaxSummaryByEmpId(@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<WageTaxSummary>  wagesAndTaxList = null;
		if(empId !=null){
			 wagesAndTaxList = wageTaxSummaryService.getWagesAndTaxListByEmployeeId(orgLocations,empId);
		}else{
			return wagesAndTaxList;
		}
		if(wagesAndTaxList !=null && wagesAndTaxList.size() >0){
			return wagesAndTaxList;
		}
		return null;
	}
	
	/**
	 * Get Account Deductions By Employee id
	 * 
	 * @param empId
	 * @param orgLocationId
	 * @return List<AccountDeductions>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getAccontDeductionsByEmpId", "/rest/getAccontDeductionsByEmpId"}, method = RequestMethod.GET)
	public List<AccountDeductions> getAccontDeductionsByEmpId(
			@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<AccountDeductions> accountDeductionsList = null;
		if (empId != null) {
			accountDeductionsList = accountDeductionsService.getAccountDeductionsByEmployeeId(orgLocations, empId);
		} else {
			return accountDeductionsList;
		}

		if (accountDeductionsList != null && accountDeductionsList.size() > 0) {
			return accountDeductionsList;
		}
		return null;

	}
	
	/**
	 * get Hr deductions By Employee id
	 * 
	 * @param empId
	 * @param orgLocationId
	 * @return List<HrDeductions>
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getHRDeductionsByEmployeeId", "/rest/getHRDeductionsByEmployeeId" }, method = RequestMethod.GET)
	public List<HrDeductions> getHRDeductionsByEmployeeId(
			@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<HrDeductions> hrDeductionsList = null;
		if (empId != null) {
			hrDeductionsList = hrDeductionsService.getHrDeductionsByEmployeeId(orgLocations, empId);
		} else {
			return hrDeductionsList;
		}

		if (hrDeductionsList != null && hrDeductionsList.size() > 0) {
			return hrDeductionsList;
		}
		return null;
	}
	
	
	@RequestMapping(value = { "/getfinalBalanceByEmpId", "/rest/getfinalBalanceByEmpId" }, method = RequestMethod.GET)
	public PayrollBalance getfinalBalanceByEmpId(
			@RequestParam(value = "empId", required = false) Long empId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		
		PayrollBalance payrollBalance = accountDeductionsService.getBalance(empId, orgLocations);
		return payrollBalance;
	}
	
	/**
	 * Add Check manually 
	 * @param orgLocationId
	 * @param userId
	 * @param checksDTO
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = { "/addCheques" }, method = RequestMethod.POST)
	public boolean addCheques(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			                 @RequestParam(value = "userId", required = false) Long userId,ChecksDTO checksDTO) throws Exception {
		boolean isInserted = false;
		Checks checks = userPayrollService.getUniqueChecksListByEmployeeId(checksDTO.getEmpId(), orgLocationId,checksDTO);
		if(checks !=null) {
			isInserted = true;
		}
		return isInserted;
	}
	
	@RequestMapping(value = {"/getAllPayrollBalanceReport", "/rest/getAllPayrollBalanceReport"}, method = RequestMethod.POST)
	public @ResponseBody  ResponseList<PayrollBalance> getAllPayrollBalanceReport(PayrollBalance payrollBalance,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(payrollBalance.getOrgLocationId()));
		ResponseList<PayrollBalance> list = employerPayrollCostService.getAllPayrollBalanceSheetByOrgLocationId(orgLocation,payrollBalance);
		return list;
	}
	
}
