package com.bis.sourcelead.web;

import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class addExpenseController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	CountriesService countriesService;
	
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
	UserService userService;
	

	@RequestMapping(value = "/acceptOrRejectReopenExpense")
	@ResponseBody  ModelAndView acceptOrRejectReopenExpense(
			@RequestParam(value="type", required=false) String type,
			@RequestParam(value="expenseId", required=false) String expenseId,
			@RequestParam(value="reOpenedUserId", required=false) String reOpenedUserId,
			@RequestParam(value="reason", required=false) String reason,HttpServletRequest request) throws Exception {
		
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User reopenedUser = userService.getUserById(Long.parseLong(reOpenedUserId));
		if(expenseId != null && null != type && "accept".equalsIgnoreCase(type)){
			expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(Long.parseLong(expenseId),reason,loginUser,type,Constants.YES);
		  	
		}else if(expenseId != null &&  null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
			expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(Long.parseLong(expenseId),reason,loginUser,type,Constants.YES);
		}
		
		ModelAndView model = new ModelAndView();
		/*User superUser = userService.getUserById(Long.parseLong(reOpenedUserId));
		ProjectMembers projectMembers = projectMembersService.getProjectMemberById(Long.parseLong(expenseId));

		
		if(StringUtils.isNotEmpty(expenseId) && null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
			User reopenedUser = expensesApprovalService.updateExpenseStatusByExpenseIdAndReOpenReason(Long.parseLong(expenseId),reason,superUser,type,Constants.YES);
			expensesApprovalService.sendReopenExpenseAcceptOrRejectMail(Long.parseLong(expenseId),reason,superUser,projectMembers.getProjects().getOrgLocationsId(),type,reopenedUser);
		}*/

		model.setViewName("expenses/reopenExpenseAcceptOrReject");
		model.addObject("type",type);
		model.addObject("expenseId",expenseId);
		model.addObject("reOpenedUserId",reOpenedUserId);
		model.addObject("reason",reason);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
}
