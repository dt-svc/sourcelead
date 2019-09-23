package com.bis.sourcelead.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesListUtilDTO;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.rest.ExpensesRestController;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
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
	ProjectClientService projectClientService;
	
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
		try{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/myExpensesList");
		}catch(Exception e){
			  model.addObject("AWKWARD", true);
			  model.setViewName("expenses/myExpensesList");
			  e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value = "/addExpense")
	public ModelAndView addExpense(HttpServletRequest request, @RequestParam(value="expenseId", required=false)String expenseId, @RequestParam(value="expenseLocationCode", required=false)String expenseLocationCode) throws Exception
	{
		ModelAndView model = new ModelAndView();
		 try {
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		if(StringUtils.isNotEmpty(expenseId)&& StringUtils.isNotEmpty(expenseLocationCode)){
			ExpensesListUtilDTO elDTO = new ExpensesListUtilDTO();
			elDTO.setUserId(loginUser.getId());
			elDTO.setLocationCode(orgLoc.getLocationCode());
			elDTO.setExpenseLocationCode(expenseLocationCode);
			elDTO.setExpenseId(expenseId);
			ExpensesDTO expensesDTO = expensesRestController.getExpenseDetailsByExpenseId(elDTO);
			model.addObject("expenses", expensesDTO);
			if(expensesDTO != null){
				 Submission_Status submissionStatus = Submission_Status.getNameByValue(expensesDTO.getCombinedSubmissionStatus());
				 model.addObject("submissionStatus",submissionStatus);
				 
			     String type = request.getParameter("type");
			 	 Boolean isEditable = false;
			 	 if(submissionStatus.getValue().equalsIgnoreCase(Submission_Status.DRAFT.getValue()) || submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REJECTED.getValue()) || submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REOPENED.getValue())){
			 		isEditable = true;
			 	 }
			 	 if(StringUtils.isEmpty(type) || (StringUtils.isNotEmpty(type) && type.equalsIgnoreCase("edit") && !isEditable)){
			 		type = "view";
			 	 }
			 	 model.addObject("type", type);
			 	 model.addObject("isSahApproved", expensesDTO.isSahApproved());
			}
				
			String rejectedReason = null;
			List<ExpensesApproverHierarchy> eahList =expensesApprovalService.getExpensesApproverHierarchyListByExpenseId(expensesDTO.getId());
			for(ExpensesApproverHierarchy eah : eahList){
				if(eah.getExpenseApprovalStatus().toString().equalsIgnoreCase("REJECTED")){
					rejectedReason  = eah.getRejectedReason(); 
				}
			}
			model.addObject("expenseApproverHierarchy", rejectedReason);
		}
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		eedDTO.setExpenseId(expenseId);
		eedDTO.setExpenseLocationCode(expenseLocationCode);
		eedDTO.setIsGlobalUser(isGlobalAccess);
		ExpenseEntryDetailsDTO expEntryDTO =  expensesRestController.getExpenseEntryDetails(eedDTO);
		expEntryDTO.setLoginCurrencyCode(currencyCode);
		model.addObject("expEntry", expEntryDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addExpense");
	  }catch(Exception e){
			 model.addObject("AWKWARD", true);
			  model.setViewName("expenses/addExpense");
			  e.printStackTrace();
		 }
		return model;
		
	}
	@RequestMapping(value={"/expenses","/expenses/{expensesStatus}"})
	public ModelAndView expenses(@PathVariable Optional<String> expensesStatus, HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		try{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.MY_EXPENSES);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(expensesStatus.isPresent()) {
			model.addObject("expensesStatusFormDashBoard",expensesStatus.get());
		}
		model.setViewName("expenses/expenses");
		}catch(Exception e){
			 model.addObject("AWKWARD", true);
			  model.setViewName("expenses/expenses");
			  e.printStackTrace();
		}
		return model;
	}
		
	
	@RequestMapping(value = "/addApprovalExpense")
	public ModelAndView addApprovalExpense(HttpServletRequest request, @RequestParam(value="expenseId", required=false)String expenseId, @RequestParam(value="expenseLocationCode", required=false)String expenseLocationCode) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		try{
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		sessionData.setNav(Constants.MY_EXPENSES);
		
		if(StringUtils.isNotEmpty(expenseId)&& StringUtils.isNotEmpty(expenseLocationCode)){
			ExpensesListUtilDTO elDTO = new ExpensesListUtilDTO();
			elDTO.setUserId(loginUser.getId());
			elDTO.setLocationCode(orgLoc.getLocationCode());
			elDTO.setExpenseLocationCode(expenseLocationCode);
			elDTO.setExpenseId(expenseId);
			elDTO.setIsGlobalAccess(isGlobalAccess);
			ExpensesDTO expensesDTO = expensesRestController.getExpenseDetailsByExpenseId(elDTO);
			model.addObject("expenses", expensesDTO);
			
			ExpensesApproverHierarchy  eah = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expensesDTO.getId(),orgLoc.getId(),isGlobalAccess);
		    Submission_Status submissionStatus = eah.getExpenseStatus();
		 	model.addObject("submissionStatus", submissionStatus);
		 	model.addObject("eah", eah);
		 	
		 	
		 	 Boolean isExpenseTotallyApproved = expensesApprovalService.isTheExpenseTotallyApproved(expensesDTO.getId());
			 model.addObject("isExpenseTotallyApproved", isExpenseTotallyApproved);	
			 
			 
			 Boolean isReopenButtonVisible = expensesApprovalService.isReopenButtonVisibleForLoginOrg(expensesDTO.getId(),orgLoc.getId(),isGlobalAccess);
			 model.addObject("isReopenButtonVisible", isReopenButtonVisible);
			 
			 String type = request.getParameter("type");
		 	
		 	 Boolean isEditable = false;
		 	 if(submissionStatus.getValue().equalsIgnoreCase(Submission_Status.SUBMITTED.getValue()) ||submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REJECTED.getValue()) ||submissionStatus.getValue().equalsIgnoreCase(Submission_Status.REOPENED.getValue())){
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
		     Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenExpenseses(eah.getExpenseId().getProjectMembers().getId(), loginUser,orgLoc);
			 model.addObject("isSuperUser", isSuperUser);	
			 
			
		}
		
		request.getSession().setAttribute("sessionData", sessionData);
		
		TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		eedDTO.setUserId(loginUser.getId());
		eedDTO.setLocationCode(orgLoc.getLocationCode());
		eedDTO.setExpenseId(expenseId);
		eedDTO.setExpenseFrom("approver");
		eedDTO.setExpenseLocationCode(expenseLocationCode);
		eedDTO.setIsGlobalUser(isGlobalAccess);
		ExpenseEntryDetailsDTO expEntryDTO =  expensesRestController.getExpenseEntryDetails(eedDTO);
		expEntryDTO.setLoginCurrencyCode(currencyCode);
		model.addObject("expEntry", expEntryDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addApprovalExpense");
		}catch(Exception e){
			  e.printStackTrace();
			  model.addObject("AWKWARD", true);
			  model.setViewName("expenses/addApprovalExpense");
		}
		return model;
	}

}
