package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
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

@Controller
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
	 CommonUtil commonUtil;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;

	
	/**
	 * @author David
	 * This Method is for redirecting  to Timsheet Submissions List 
	 * @param request
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/timesheetlistByStatus")
	public ModelAndView timesheetListByStatus(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status) throws Exception {
		ModelAndView model = new ModelAndView();
		request.setAttribute("status", status);
		
		model.setViewName("timesheet/timesheetStatusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	/***
	 * @author Sammeta David
	 * This method is get TimesheetList By Status
	 * @param status
	 * @param request
	 * @return
	 * @throws Null Exception
	 */
	 @RequestMapping(value = "/getTimesheetListByStatus", method = RequestMethod.POST)
	 public @ResponseBody String getTimesheetListByStatus(@RequestParam(value="status", required=false) String status,HttpServletRequest request) throws Exception{ 
		    String resultJson = "";
			request.setAttribute("status", status);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			
		    List<SubmissionApproverHierarchy> list  =	submissionApproverHierarchyService.getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(user.getId(), loginOrgLocations.getId(), status, userRole);
		    StringBuilder sb = new StringBuilder();	
	     	sb.append(" <table id='example' class='display' cellspacing='0' width='100%' >");
			sb.append(" <thead>");
			sb.append("     <tr>");
			sb.append("         <th>Employee</th>");
			sb.append("         <th>Frequency</th>");
			sb.append("         <th>Total hours</th>");
			sb.append("         <th>Stage</th>");
			sb.append("         <th>Action</th>");
			sb.append("     </tr>");
			sb.append(" </thead>");
			sb.append(" <tbody>");	
		    if(list != null && !list.isEmpty())
		    {
		    	
				
						for(SubmissionApproverHierarchy submissionApproverHierarchy:list){
							  DateFormat DF_DMY_WITH_OUT_SEPERATORS = new SimpleDateFormat("ddMMyyyy");
						         String userUniqueAppendor = DF_DMY_WITH_OUT_SEPERATORS.format(submissionApproverHierarchy.getSubmissionId().getStartDate());
							sb.append("<tr id='row"+submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getId()+userUniqueAppendor+"'>");
							TimesheetSubmissions  ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(submissionApproverHierarchy.getSubmissionId().getStartDate(), 
		                            submissionApproverHierarchy.getSubmissionId().getEndDate(), 
		                            user.getId(), 
		                            submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getId(),
		                            submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getId());
							
							  ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
						       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
							   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
								   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
							   }
							   ts.setStartDateStr(Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate()));
							   ts.setEndDateStr(Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate()));
							 sb.append("  <td><div class='time_appr_user tbldata-dotdot-me'>");
							 sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='"+request.getContextPath()+"/displayUserImage/"+ts.getProjectMembers().getEmployee().getUser().getId()+"' alt='Profile Image' class='img-circle img-responsive'>");
					         sb.append("    <span><b>"+ts.getFullName()+"</b></span>");
					         sb.append(" <div></td>");
					       
					         sb.append("<td><div id='datefrequency"+ts.getUserId()+userUniqueAppendor+"'>");
					         sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate())+" To "+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate())+"</span>");
					         sb.append("    </div>");
					     	 sb.append("    </td>");
					     	
					     	sb.append("    <td><div> " );
					         sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate())+"\",\""+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate())+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
					         sb.append("    </div>");
					         sb.append("    </td>");
					         
					         sb.append("  <td><div id='status"+ts.getUserId()+userUniqueAppendor+"'>"+ts.getCombinedStatus()+"</div></td>");
					         
					         sb.append("    <td><div id='action"+ts.getUserId()+userUniqueAppendor+"'> " );
					             if( (ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("NEW")) || ( ts.getCombinedStatus() != null && ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL")) ){
					                 sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getUserId()+userUniqueAppendor+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getUserId()+userUniqueAppendor+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
					                 sb.append("<div id='rejectedPopup'></div>");
					             }
					             if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
					               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
					               sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
					               sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
					               sb.append(" </i>");
					               sb.append(" </div>");
					            }
					            if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
					          	  sb.append(" <div><h3>Approved</h3></div>");
					            }  
					            sb.append(" </div>");
					            sb.append("    </td>");
					            sb.append("<div class='clearfix'></div>");
					            sb.append("</tr>");
					            sb.append("	<div>");
						}
		         
		     }
			sb.append(" </tbody>");	
			sb.append(" </table>");	
	        resultJson = sb.toString();
			
		    return resultJson;
	 }
	 

}
