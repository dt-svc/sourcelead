package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.rest.TimesheetApprovalRestController;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
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
	ProjectMembersService projectMembersService;
	
	@Autowired
	public ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;

	
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
	 * @author Naresh Bolisetty
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
		   // SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
		   // DateFormat format = simpleDateFormat;
		    
		    List<SubmissionApproverHierarchy> list  =	submissionApproverHierarchyService.getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(user.getId(), loginOrgLocations.getId(), status, userRole);
		    StringBuilder sb = new StringBuilder();	
	     	sb.append(" <table id='example'  style='width:100%' >");
			sb.append(" <thead>");
			sb.append("     <tr>");
			sb.append("         <th>Employee</th>");
			sb.append("         <th>Project</th>");
			sb.append("         <th>Client</th>");
			sb.append("         <th>Frequency</th>");
			sb.append("         <th>Date</th>");
			sb.append("         <th>Total</th>");
			sb.append("         <th>Stage</th>");
			sb.append("         <th>Action</th>");
			sb.append("     </tr>");
			sb.append(" </thead>");
			sb.append(" <tbody>");	
			int tdSpanLength = 8;
		    if(list != null && !list.isEmpty())
		    {
		    	
			     for(SubmissionApproverHierarchy submissionApproverHierarchy:list){
				    	 Date actualStartDate =submissionApproverHierarchy.getFrequencyStartDate();
				    	 Date actualEndDate =submissionApproverHierarchy.getFrequencyEndDate();
				    	 if(actualStartDate != null && actualEndDate != null){
				    	 LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
				    		Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate), commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate),loginOrgLocations.getDateFormat());
							if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
								for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
						    		 String  k = entry.getKey();
						    		 String  v = entry.getValue();
									tdSpanLength =tdSpanLength+1;
							        Date weekStartDate;
							        Date weekEndDate;
									try {
										 weekStartDate = commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),k.replaceAll("/", "-"));
										 weekEndDate = commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),v.replaceAll("/", "-"));
										 displayWeeksList.put(weekStartDate, weekEndDate);
									} catch (Exception e) {}
								}
							}
							
				    	  ProjectMembers projectMember = projectMembersService.getProjectMemberById(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getId());
						 sb.append("<tr id='row").append(projectMember.getId()).append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)).append("To").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate)).append("'>");
						 sb.append("  <td>");
						 sb.append("<div class='time_appr_user'>");
						 sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='").append(request.getContextPath()).append("/displayUserImage/").append(projectMember.getEmployee().getUser().getId()).append("' alt='Profile Image' class='img-circle img-responsive'>");
						 sb.append("    <span><b title='").append(projectMember.getEmployee().getUser().getFullName()).append("'>").append(projectMember.getEmployee().getUser().getFullName()).append("</b>")
						                           .append("<b title='").append(projectMember.getEmployee().getUser().getEmail()).append("'>").append(projectMember.getEmployee().getUser().getEmail()).append("</b>")
                                                   .append("<b title='").append((projectMember.getEmployee().getUser().getMobilePhone() != null) ?(projectMember.getEmployee().getUser().getMobilePhone()) : "").append("'>").append((projectMember.getEmployee().getUser().getMobilePhone() != null) ?(projectMember.getEmployee().getUser().getMobilePhone()) : "").append("</b>")
						                                             .append("</span>");
						 sb.append("   <div>");
						 sb.append(" </td>");
						 sb.append("	<td><div class='tbldata-dotdot-sm'>").append("<span title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</span></div></td>");
					     
					     sb.append("	<td><div>").append(((projectMember.getProjects().getOrgRelationId() != null) ?projectMember.getProjects().getOrgRelationId().getResponderLocationId().getOrgLocationName() : "--")).append("</div></td>");
					     sb.append("	<td><div>").append(projectMember.getProjects().getInvoiceFrequency()).append("</div></td>");
					     sb.append("<td><div id='datefrequency"+projectMember.getId()+"'>");
				         sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)+" To "+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate)+"</span>");
				         sb.append("    </div>");
				     	 sb.append("    </td>");
					     Float totalHours = 0f;
					     Float estimatedTotalHours = 0f;
					     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projectMember.getId(),actualStartDate,actualEndDate);
					     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
					    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
					    	    	  long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
					    	    	  Float weekTotal = 0f;
					    	    	  if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
					    	    		  weekTotal = (float) ((diff)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	  else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
					    	    		  weekTotal = (float) ((diff-1)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	  else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
					    	    		  weekTotal = (float) ((diff+1)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	 if(sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty() && sah.getWeekTotals().get(entry.getKey()) != null){
					    	    		 totalHours = totalHours+sah.getWeekTotals().get(entry.getKey());
					    	    	 }
					    	    	
					    	    	
					    	     }
					     }
					
					    /*sb.append("<td><div class='sm'><a href='javascript:void(0)'>").append(totalHours).append("/40</a></div></td>");*/
					     sb.append("<td><div class='sm'><a href='#' onClick='timesheetApprovalPopUpByProjectMemberId(\""+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)+"To"+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate)+"\" ,\""+projectMember.getId()+"\",\"dashboard\");'>").append(CommonUtil.getHoursDisplayFormat(totalHours)).append("/").append(CommonUtil.getHoursDisplayFormat(estimatedTotalHours)).append("</a></div></td>");
					    sb.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(sah.getSubmissionApprovalStatus()).append("</div></td>");
					    sb.append("<td>");
					     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
					    	 sb.append("<div class='sm text-center'>--</div>");
					     }
					     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
					    	 sb.append("<div class='sm text-center'id='action").append(projectMember.getId()).append("'> <i class='fa fa-check' alt='Approve'  onclick='approveSubmissionByProjectMemberId(\"")
			            	                     .append(projectMember.getId()).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate)).
			            	                      append("\",").append(tdSpanLength).append(",\"").append("dashboard").append("\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmissionByProjectMemberId(\"")
			            	                     .append(projectMember.getId()).append("\",\"")
			            	                     .append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate))
			            	                     .append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate))
			            	                     .append("\",").append(tdSpanLength).append(",\"").append("dashboard").append("\");'></i></div>");
			             }
					      if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
					    	  sb.append(" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>");
			            	 sb.append(" <i class='glyphicon glyphicon-comment' id='").append(projectMember.getId()).append("' onclick='rejectedMessage(\"rejectedMessage").append(projectMember.getId()).append("\");'>");
			            	 sb.append(" <div class='app_error_mes' id='rejectedMessage").append(projectMember.getId()).append("'>").append(sah.getRejectedReason()).append("</div>");
			            	 sb.append(" </i>");
			            	 sb.append(" <b class='Rname' title='"+sah.getActionPerformedBy()+"'>By : ").append(sah.getActionPerformedBy());
			            	 sb.append(" </b>");
			            	 sb.append(" </div>");
			            }
					     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED){
					    	 sb.append(" <div class='sm text-center'> <h3>Approved</h3><b class='Rname' title='"+sah.getActionPerformedBy()+"'>By : ").append(sah.getActionPerformedBy()).append("</b></div>");
			            }  
					     sb.append("    </td>");
			            sb.append("</tr>");
			    	
				    }
			     }
		         
		     }
			sb.append(" </tbody>");	
			sb.append(" </table>");	
	        resultJson = sb.toString();
			
		    return resultJson;
	 }
	 

}
