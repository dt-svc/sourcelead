package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesListUtilDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.rest.ExpensesRestController;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.math.BigDecimal;

@Controller
public class ExpensesController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ExpensesCategoryService expensesCategoryService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	ExpensesService expensesService;
	
	@Autowired
	ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ExpensesRestController expensesRestController;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/myExpensesList")
	public ModelAndView myExpensesList(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/myExpensesList");
		return model;
	}
	
	
	@RequestMapping(value = "/addExpense")
	public ModelAndView addExpense(HttpServletRequest request, @RequestParam(value="expenseId", required=false)String expenseId) throws Exception
	{
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		if(null != expenseId && !expenseId.isEmpty()){
			ExpensesListUtilDTO elDTO = new ExpensesListUtilDTO();
			elDTO.setUserId(loginUser.getId());
			elDTO.setLocationCode(orgLoc.getLocationCode());
			elDTO.setExpenseId(expenseId);
			ExpensesDTO expensesDTO = expensesRestController.getExpenseDetailsByExpenseId(elDTO);
			model.addObject("expenses", expensesDTO);
			if(expensesDTO != null){
				 Submission_Status submissionStatus = expensesDTO.getCombinedSubmissionStatus();
				 model.addObject("submissionStatus",submissionStatus);
				 
			     String type = request.getParameter("type");
			 	 Boolean isEditable = false;
			 	 if(submissionStatus.getValue().equalsIgnoreCase(Submission_Status.DRAFT.getValue()) || submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REJECTED.getValue())){
			 		isEditable = true;
			 	 }
			 	 if(StringUtils.isEmpty(type) || (StringUtils.isNotEmpty(type) && type.equalsIgnoreCase("edit") && !isEditable)){
			 		type = "view";
			 	 }
			 	 model.addObject("type", type);
			 	 model.addObject("isSahApproved", expensesDTO.getSahApproved());
			}
				
		/*	Expenses expenses = expensesService.getExpenseByExpenseUniqueId(expenseId,orgLoc.getId());
			expenses.setStartDateStr(Constants.DF_DMY.format(expenses.getStartDate()));
			if(null != expenses.getEndDate()){
				expenses.setEndDateStr(Constants.DF_DMY.format(expenses.getEndDate()));
			}
			ExpensesApproverHierarchy eah =expensesApprovalService.getexpensesApproverHierarchyForEmployeeByExpenseId(expenses.getId());
			 Submission_Status submissionStatus = eah.getExpenseStatus();
		 	model.addObject("submissionStatus", submissionStatus);
		 	model.addObject("expenseApproverHierarchy", eah);
		 	
		 	
		 	
		    ExpensesApproverHierarchy isSah = expensesApprovalService.isThereAnyApproverApprovedTheExpense(expenses.getId());
		    boolean isSahApproved = false;
		    if(isSah != null){
		        	isSahApproved = true;
		    }
		    model.addObject("isSahApproved", isSahApproved);	
			model.addObject("expenses", expenses);*/
			StringBuffer rejectedReason = new StringBuffer();
			List<ExpensesApproverHierarchy> eahList =expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(Long.parseLong(expenseId));
			for(ExpensesApproverHierarchy eah : eahList){
				if(eah.getExpenseApprovalStatus().toString().equalsIgnoreCase("REJECTED")){
					rejectedReason.append(eah.getRejectedReason()).append(""); 
				}
			}
			model.addObject("expenseApproverHierarchy", rejectedReason.toString());
		}
		ExpenseEntryDetailsUtilDTO eedDTO = new ExpenseEntryDetailsUtilDTO();
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		ExpenseEntryDetailsDTO expEntryDTO =  expensesRestController.getExpenseEntryDetails(eedDTO);
		model.addObject("expEntry", expEntryDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addExpense");
		return model;
		
		
		/*List<ProjectMembers> projectMembersList = addExpensesService.getAllProjectsForExpenses(orgLoc,loginUser);
		model.addObject("projectMembersList", projectMembersList);*/
		
		
		/*Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}*/
		
	/*	List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(orgLoc.getId(), "1");
	    model.addObject("expensesCategoryList", expensesCategoryList);*/
		
		
	}
	
	@RequestMapping(value = "/expenses")
	public ModelAndView expenses(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/expenses");
		return model;
	}
		
	
	@RequestMapping(value = "/addApprovalExpense")
	public ModelAndView addApprovalExpense(HttpServletRequest request, @RequestParam(value="expenseId", required=false)String expenseId) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		sessionData.setNav(Constants.MY_EXPENSES);
		/*String expensesId = addExpensesService.getExpenseId();
		model.addObject("ExpensesId", expensesId);*/
		//SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
		Expenses expenses = null;
		if(null != expenseId && !expenseId.isEmpty()){
			expenses = expensesService.getExpenseByExpenseUniqueId(expenseId,orgLoc.getId());
			expenses.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getStartDate()));
			if(null != expenses.getEndDate()){
				expenses.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getEndDate()));
			}
			
			BigDecimal totalAmo = new BigDecimal(expenses.getTotalAmount());
			BigDecimal totalAmountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			expenses.setTotalAmountRoundoffValue(totalAmountRoundOff);
			if(expenses.getCreatedBy()!= null){
				User createduser = userService.getUserByUserCode(expenses.getCreatedBy());
				StringBuilder createdName = new StringBuilder();
				if(createduser.getFirstName()!= null && createduser.getLastName()!= null){
					createdName.append(createduser.getFirstName()).append(" ").append(createduser.getLastName());
				}else{
					createdName.append(createduser.getFirstName());
				}
				model.addObject("expensesCreatedBy", createdName);
			}
			
			model.addObject("expenses", expenses);
			
			ExpensesApproverHierarchy  eah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenses.getId());
		    Submission_Status submissionStatus = eah.getExpenseStatus();
		 	model.addObject("submissionStatus", submissionStatus);
		 	model.addObject("eah", eah);
		 	
		 	
		 	 Boolean isExpenseTotallyApproved = expensesApprovalService.isTheExpenseTotallyApproved(expenses.getId());
			 model.addObject("isExpenseTotallyApproved", isExpenseTotallyApproved);	
			 
			 String type = request.getParameter("type");
		 	
		 	 Boolean isEditable = false;
		 	 if(submissionStatus.getValue().equalsIgnoreCase(Submission_Status.SUBMITTED.getValue()) ||submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REJECTED.getValue())){
		 		isEditable = true;
		 	 }
		 	
		  	 if(StringUtils.isEmpty(type) || (StringUtils.isNotEmpty(type) && type.equalsIgnoreCase("edit") && !isEditable)){
		 		type = "view";
		 	 }
		 	 model.addObject("type", type);
			 	
			 
			 boolean isSahReopened = false;
		     if(null != isExpenseTotallyApproved && StringUtils.isNotEmpty(eah.getReopenReason())){
			   isSahReopened = true;
		     }
		     model.addObject("isSahReopened", isSahReopened);	
		     
			 Boolean isSuperUser = (Boolean) SourceLeadSessionManager.getSessionAttribute(request, "isSuperUser");
			 model.addObject("isSuperUser", isSuperUser);	
			 
			
		   Collection<ProjectMembers> projectMembers = projectMembersService.getAllProjectMembersByProjectId(expenses.getProjectMembers().getProjects().getId());
		   model.addObject("projectMembers", projectMembers);
		}else if(expenses == null){
			expenseId = addExpensesService.getExpenseId(orgLoc);
			model.addObject("ExpensesId", expenseId);
		}
		
		List<Projects> projectsList = projectsService.getProjectsByOrgLocationsIdAndApproverUserId(orgLoc.getId(),loginUser.getId());
		model.addObject("projectsList", projectsList);
		
		request.getSession().setAttribute("sessionData", sessionData);
		
		/*Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(orgLoc.getId(), "1");
	    model.addObject("expensesCategoryList", expensesCategoryList);*/
		
		ExpenseEntryDetailsUtilDTO eedDTO = new ExpenseEntryDetailsUtilDTO();
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		ExpenseEntryDetailsDTO expEntryDTO =  expensesRestController.getExpenseEntryDetails(eedDTO);
		model.addObject("expEntry", expEntryDTO);
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addApprovalExpense");
		return model;
	}
	
	
	
	
	

}
