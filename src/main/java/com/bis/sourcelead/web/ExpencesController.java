package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ExpencesController {
	
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
		
		Expenses expenses = null;
		if(null != expenseId && !expenseId.isEmpty()){
			expenses = expensesService.getExpenseByExpenseUniqueId(expenseId);
			expenses.setStartDateStr(Constants.DF_DMY.format(expenses.getStartDate()));
			expenses.setEndDateStr(Constants.DF_DMY.format(expenses.getEndDate()));
			model.addObject("expenses", expenses);
		}else if(expenses == null){
			expenseId = addExpensesService.getExpenseId();
			model.addObject("ExpensesId", expenseId);
		}
		
		
		
		
		List<ProjectMembers> projectMembersList = addExpensesService.getAllProjectsForExpenses(orgLoc,loginUser);
		model.addObject("projectMembersList", projectMembersList);
		request.getSession().setAttribute("sessionData", sessionData);
		
		Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLoc.getId());
	    model.addObject("expensesCategoryList", expensesCategoryList);
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addExpense");
		return model;
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
	public ModelAndView addApprovalExpense(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		sessionData.setNav(Constants.MY_EXPENSES);
		String expensesId = addExpensesService.getExpenseId();
		model.addObject("ExpensesId", expensesId);
		
		List<Projects> projectsList = projectsService.getProjectsByOrgLocationsIdAndApproverUserId(orgLoc.getId(),loginUser.getId());
		model.addObject("projectsList", projectsList);
		
		request.getSession().setAttribute("sessionData", sessionData);
		
		Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndCategoryId(orgLoc.getId());
	    model.addObject("expensesCategoryList", expensesCategoryList);
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addApprovalExpense");
		return model;
	}

}
