package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * This class is used for Timesheet Submission List.
 * 
 * @author: Pavan Jampana
 * @date: 20-July-2016
 */

@RestController
public class TimesheetSubmissionListController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionListController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;

	@RequestMapping(value = "/timesheetlistByStatus")
	public ModelAndView timesheetListByStatus(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		ModelAndView model = new ModelAndView();
		request.setAttribute("status", status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		Collection<TimesheetSubmissions> list = null  ;
		
		if(status != null){
			list  = timesheetSubmissionsService.getTimeSheetSubmissionsListByStatus(user.getId(), status);
		}
		for(TimesheetSubmissions timesheetSubmissions:list){
	
			Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(timesheetSubmissions.getProjectMembers().getProjects().getId());
			for(ProjectApproverHierarchy pah : projectApproverHierarchies){
				if(pah.getLevel() == 1){
					timesheetSubmissions.setManagerLevel1(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
				}else if(pah.getLevel() == 2){
					timesheetSubmissions.setManagerLevel2(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
				}
			}
		}
        model.addObject("Timesheet_Submissions", list);
		model.setViewName("timesheet/timesheetStatusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

}
