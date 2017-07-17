package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.ExpensesCategoryDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.helper.ExpenseCategoryDTOConverter;
import com.bis.sourcelead.dto.helper.ExpensesDTOConverter;
import com.bis.sourcelead.dto.helper.ProjectMemberDTOConverter;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.CurrencyCodes;
import com.bis.sourcelead.enums.PaymentModes;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ExpensesRestController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ExpensesCategoryService expensesCategoryService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService  orgLocationService;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getExpensesList")
	 public String getExpensesList(HttpServletRequest request, String expenseStatus, String startDate, String endDate) throws Exception {
		String resultJson="";
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Expenses> expensesList = expensesService.getAllExpenses(orgLoc,loginUser, expenseStatus, startDate, endDate);
		for(Expenses ex:expensesList){
			if(null != ex.getProjectMembers())
			{
				ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
			}
			if(null != ex.getStartDate())
			{
			ex.setStartDateStr(Constants.DF_DMY.format(ex.getStartDate()));
			}
			if(null != ex.getEndDate())
			{
				ex.setEndDateStr(Constants.DF_DMY.format(ex.getEndDate()));
			}
		}
		resultJson = commonUtil.toJSON(expensesList);
		return resultJson;
	 }
	
	 @RequestMapping(value = "/rest/getExpenseEntryDetails")
	 public ExpenseEntryDetailsDTO getExpenseEntryDetails(@RequestBody ExpenseEntryDetailsUtilDTO eedDTO) throws Exception {
		 ExpenseEntryDetailsDTO expenseEntryDetailsDTO = new ExpenseEntryDetailsDTO();
		 OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		 List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndEmpUserId(orgLocations.getId(),eedDTO.getUserId());
		  if(projectMembersList != null && !projectMembersList.isEmpty()){
			  ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
			  for(ProjectMembers pm : projectMembersList){
				  pmDTOList.add(ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm)) ;
			  }
			  expenseEntryDetailsDTO.setProjectMembersList(pmDTOList);
		  }
		  List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(orgLocations.getId(), "1");
		  if(expensesCategoryList != null && !expensesCategoryList.isEmpty()){
			  ArrayList<ExpensesCategoryDTO> ecDTOList = new ArrayList<>();
			  for(ExpensesCategory ec : expensesCategoryList){
				  ecDTOList.add(ExpenseCategoryDTOConverter.getExpensesCategoryDTOFromExpensesCategory(ec)) ;
			  }
			  expenseEntryDetailsDTO.setExpenseCategoryList(ecDTOList);;
		  }
		  
		  String expenseId = addExpensesService.getExpenseId(orgLocations);
		  expenseEntryDetailsDTO.setExpenseId(expenseId);
		  
		  Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		  if(null != countries){
			 expenseEntryDetailsDTO.setCurrencyCode(countries.getCurrency());
		  }
		  
		  List<String>  paymentModes = new LinkedList<>();
		  paymentModes.add(PaymentModes.CREDITCARD.getValue());
		  paymentModes.add(PaymentModes.DEBITCARD.getValue());
		  paymentModes.add(PaymentModes.CASH.getValue());
		  paymentModes.add(PaymentModes.CHEQUE.getValue());
		  paymentModes.add(PaymentModes.EWALLET.getValue());
		  expenseEntryDetailsDTO.setPaymentModes(paymentModes);
		  
		  List<String>  currencyCodeList = new LinkedList<>();
		  currencyCodeList.add(CurrencyCodes.INR.getValue());
		  currencyCodeList.add(CurrencyCodes.AUD.getValue());
		  currencyCodeList.add(CurrencyCodes.USD.getValue());
		  currencyCodeList.add(CurrencyCodes.GBP.getValue());
		  currencyCodeList.add(CurrencyCodes.SGD.getValue());
		  currencyCodeList.add(CurrencyCodes.EUR.getValue());
		  expenseEntryDetailsDTO.setCurrencyCodeList(currencyCodeList);
		 return expenseEntryDetailsDTO;
	 }
	 
	 
	 @RequestMapping(value = "/rest/getExpenseDetailsByExpenseId")
	 public ExpensesDTO getExpenseDetailsByExpenseId(@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "locationCode", required = true) String locationCode,
			      @RequestParam(value = "expenseId", required = true) String expenseId) throws Exception {
		
		   ExpensesDTO expensesDTO = null;
		   OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		   Expenses expenses = expensesService.getExpenseByExpenseUniqueId(expenseId,orgLocations.getId());
		   if(expenses != null){
			   expensesDTO =  ExpensesDTOConverter.getExpensesDTOFromExpenses(expenses, expensesDTO);
			   ExpensesApproverHierarchy eah =expensesApprovalService.getexpensesApproverHierarchyForEmployeeByExpenseId(expenses.getId());
			   Submission_Status submissionStatus = eah.getExpenseStatus();
			   expensesDTO.setCombinedSubmissionStatus(submissionStatus);
			   
			   ExpensesApproverHierarchy isSah = expensesApprovalService.isThereAnyApproverApprovedTheExpense(expenses.getId());
			   boolean isSahApproved = false;
			   if(isSah != null){
			        	isSahApproved = true;
			   }
			   if(expenses.getCreatedBy()!= null){
					User createduser = userService.getUserByUserCode(expenses.getCreatedBy());
					StringBuilder createdName = new StringBuilder();
					if(createduser.getFirstName()!= null && createduser.getLastName()!= null){
						createdName.append(createduser.getFirstName()).append(" ").append(createduser.getLastName());
					}else{
						createdName.append(createduser.getFirstName());
					}
					 expensesDTO.setExpCreatedBy(createdName.toString());
				}
			   expensesDTO.setSahApproved(isSahApproved);
		   }
		 return expensesDTO;
	 }
	
	@RequestMapping(value = "/getIndividualExpense")
	 public String getIndividualExpense(HttpServletRequest request,String expenseId) throws Exception {

		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesEntries> expensesEntriesList = expensesService.getExpensesEntries(expenseId);
		
		
		for(ExpensesEntries exEnt:expensesEntriesList){

			if(null != exEnt.getExpenseDate())
			{
				exEnt.setExpenseDateStr(Constants.DF_DMY.format(exEnt.getExpenseDate()));
			}

			if(null == exEnt.getExpenseIndList())
			{
				List<Expenses> expensesList = expensesService.getIndividualExpense(orgLoc,loginUser,expenseId);
				for(Expenses ex:expensesList){
					if(null != ex.getProjectMembers())
					{
						ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
					}
					if(null != ex.getStartDate())
					{
						ex.setStartDateStr(Constants.DF_DMY.format(ex.getStartDate()));
					}
					if(null != ex.getEndDate())
					{
						ex.setEndDateStr(Constants.DF_DMY.format(ex.getEndDate()));
					}
				}
				exEnt.setExpenseIndList(expensesList);
			}
		}
		System.out.println("expensesEntriesList"+expensesEntriesList);
		resultJson = commonUtil.toJSON(expensesEntriesList);
		return resultJson;
	 }

	@RequestMapping(value = "/exportMyExpensesList")
	 public void exportMyExpensesList(HttpServletRequest request,HttpServletResponse response,
			 @RequestParam(value = "type", required = false) String type,
			 @RequestParam(value = "Expid", required = false) String Expid,
			 @RequestParam(value = "ApproverType", required = false) String ApproverType) throws Exception {
		
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		Expenses ExpenseDetails = expensesService.getExpenseByExpenseId(Long.parseLong(Expid));

		if(ExpenseDetails != null){
			if(type.equalsIgnoreCase("PDF")){
				file = expensesService.exportMyExpensesToPdf(ExpenseDetails,orgLoc,loginUser,ApproverType);
			}else{
				file = expensesService.exportMyExpensesToExcel(ExpenseDetails,orgLoc,loginUser,ApproverType);	
			}
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
		if(file != null){
			file.delete();
		}
	 }
	
	@RequestMapping(value = "/exportAllMyExpensesList")
	 public void exportAllMyExpensesList(HttpServletRequest request,HttpServletResponse response,
			 @RequestParam(value = "type", required = false) String type,
			 @RequestParam(value = "expenseStatus", required = false) String expenseStatus,
			 @RequestParam(value="startDate", required=false) String startDate,
			 @RequestParam(value="endDate", required=false) String endDate) throws Exception {
		
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<Expenses> expensesList = expensesService.getAllExpenses(orgLoc,loginUser, expenseStatus, startDate, endDate);
		if(expensesList != null && expensesList.size() > 0){
			for(Expenses ex:expensesList){
				if(null != ex.getProjectMembers())
				{
					ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
				}
				if(null != ex.getStartDate())
				{
					ex.setStartDateStr(Constants.DF_DMY.format(ex.getStartDate()));
				}
				if(null != ex.getEndDate())
				{
					ex.setEndDateStr(Constants.DF_DMY.format(ex.getEndDate()));
				}
			}

			if(type.equalsIgnoreCase("PDF")){
				file = expensesService.exportAllMyExpensesToPdf(expensesList,orgLoc,loginUser);
			}else{
				file = expensesService.exportAllMyExpensesToExcel(expensesList,orgLoc);	
			}
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
		if(file != null){
			file.delete();
		}
	 }
	
	@RequestMapping(value = "/myExpensesListByStage")
	 public String myExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception {
		String resultJson="";
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Expenses> expensesList = expensesService.getTotalMyExpensesListByuserIdAndOrgLocationIdForEmployeeLogin(loginUser.getId(), orgLoc.getId(), expenseStatus);
		for(Expenses ex:expensesList){
			if(null != ex.getProjectMembers())
			{
				ex.setProjectMemberId(ex.getProjectMembers().getProjects().getProjectName());	
			}
			if(null != ex.getStartDate())
			{
			ex.setStartDateStr(Constants.DF_DMY.format(ex.getStartDate()));
			}
			if(null != ex.getEndDate())
			{
				ex.setEndDateStr(Constants.DF_DMY.format(ex.getEndDate()));
			}
		}
		resultJson = commonUtil.toJSON(expensesList);
		return resultJson;
	 }
	
	@RequestMapping(value = "/employeeExpensesListByStage")
	 public String employeeExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception {
		String resultJson="";
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesService.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(loginUser.getId(), orgLoc.getId(), expenseStatus);
		for(ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy){
			
			if(null != expensesApproverHierarchy.getExpenseId().getEndDate()){
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getEndDate()));
			}else{
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			
			if (null != expensesApproverHierarchy.getExpenseId()) {
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
					
					if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee()) {
						
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId())){
								expensesApproverHierarchy.setExpensesEmployeeUserId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId());
							}else{
								expensesApproverHierarchy.setExpensesEmployeeUserId("--");
							}
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType())){
								String empType = expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType();
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgLocations().getId(), expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgRelationId().getId());
									expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrg().getOrgName());
									expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()){
								StringBuilder firstLastName = new StringBuilder();
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(firstLastName.append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName()).append("").append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()).toString());
							}else{
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());	
							}
								expensesApproverHierarchy.setExpenseEmpUserEmail(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getEmail());
								expensesApproverHierarchy.setExpensesUserid(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getId());
						}
					}
				}
			}

		 }
		resultJson = commonUtil.toJSON(expApperoverHierarchy);
		return resultJson;
	 }
	
	

}