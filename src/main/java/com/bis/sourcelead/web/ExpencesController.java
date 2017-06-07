package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ExpencesController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	CountriesService countriesService;
	
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
	public ModelAndView addExpense(HttpServletRequest request) throws Exception
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
		List<ProjectMembers> projectMembersList = addExpensesService.getAllProjectsForExpenses(orgLoc,loginUser);
		model.addObject("projectMembersList", projectMembersList);
		request.getSession().setAttribute("sessionData", sessionData);
		
		Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/addExpense");
		return model;
	}
	
	
	/*@RequestMapping(value = "/expensesApprovalList")
	public ModelAndView expensesApprovalList(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.EXPENSES_APPROVAL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("expenses/expensesApprovalList");
		return model;
	}*/
}
