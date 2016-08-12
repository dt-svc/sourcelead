package com.bis.sourcelead.web;


import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller 
public class TimesheetSubmissionsController {
	
	 
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
		
	
	@RequestMapping(value = "/timesheetApproval")
	public ModelAndView timesheetApproval(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(com.bis.sourcelead.util.Constants.TimeSheetApproval);
			
			request.getSession().setAttribute("sessionData",sessionData);
			
			Collection<TimesheetSubmissions> timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissions(user.getId());
			model.addObject("sourcelead_timesheetSubmissionsList",timesheetSubmissionsList);
			
			
			List<TimesheetSubmissions> thslist = timesheetSubmissionsService.getSubmissionApproverHierarchyForEmployeeList(user.getId());
			for(TimesheetSubmissions ths : thslist ){
				ths.setFullName(ths.getProjectMembers().getEmployee().getUser().getFirstName() +""+ths.getProjectMembers().getEmployee().getUser().getLastName());
				ths.setEmployeeId(ths.getProjectMembers().getEmployee().getId().toString());
			}
			model.addObject("thslist",thslist);

			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheetApproval");
	        return model;
	}
	
	 public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
 
}
