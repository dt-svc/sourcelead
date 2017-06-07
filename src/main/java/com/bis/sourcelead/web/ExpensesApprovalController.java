package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ExpensesApprovalController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@RequestMapping(value = "/expensesApprovalList")
	public ModelAndView expensesApprovalList(HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession().getAttribute("sessionData");
		if (null == sessionData) {
			sessionData = new SessionMaintainanceData();
		}
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		sessionData.setNav(Constants.EXPENSES_APPROVAL);
		request.getSession().setAttribute("sessionData", sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc);
		model.addObject("ExpApperoverHierarchy", expApperoverHierarchy);
		model.setViewName("expenses/expensesApprovalList");
		return model;
	}
}
