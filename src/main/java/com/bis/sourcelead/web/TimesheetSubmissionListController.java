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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
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
	public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;

	@RequestMapping(value = "/timesheetlistByStatus")
	public ModelAndView timesheetListByStatus(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		ModelAndView model = new ModelAndView();
		request.setAttribute("status", status);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
	    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		
		Collection<SubmissionApproverHierarchy> list = null  ;
		
		if(status != null){
			
			if(userRole.equalsIgnoreCase("ROLE_ADMIN")){
				
				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
					 list = submissionApproverHierarchyService.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(user.getId(), loginOrgLocations.getId(), status);
				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED")  ||  status.equalsIgnoreCase("NEW") ){
					 list = submissionApproverHierarchyService.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(user.getId(), loginOrgLocations.getId(),  status);
				}
				
			}else if( userRole.equalsIgnoreCase("ROLE_USER") ){
				
				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
					list = submissionApproverHierarchyService.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(user.getId(), loginOrgLocations.getId(), status);
				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED") ||  status.equalsIgnoreCase("NEW")){
					list = submissionApproverHierarchyService.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(user.getId(), loginOrgLocations.getId(),  status);
				}
				
			}else if( userRole.equalsIgnoreCase("ROLE_APPROVER") ){
				
				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
					list = submissionApproverHierarchyService.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(user.getId(), loginOrgLocations.getId(), status);
				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED") ||  status.equalsIgnoreCase("NEW")){
					list = submissionApproverHierarchyService.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(user.getId(), loginOrgLocations.getId(),  status);
				}

			}
			
		}
		if(list!=null && !list.isEmpty()){
			
		for(SubmissionApproverHierarchy submissionApproverHierarchy:list){
	
			Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getProjects().getId());
			for(ProjectApproverHierarchy pah : projectApproverHierarchies){
				if(pah.getLevel() == 1){
					submissionApproverHierarchy.setManagerLevel1(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
				}else if(pah.getLevel() == 2){
					submissionApproverHierarchy.setManagerLevel2(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
				}
			}
		}
	 }
        model.addObject("Timesheet_Submissions", list);
		model.setViewName("timesheet/timesheetStatusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

}
