package com.bis.sourcelead.web;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController 
public class TimesheetSubmissionsController {
	
	 
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	
	@Autowired
	private DataSource dataSource;
	
	// sarath kumar punnana : this  controller method is for withdraw  timwsheet entries.
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody void withDrawTimesheet(@RequestParam(value="json", required=false) String json) throws Exception {
				
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	
		String result = "";
		String resultJson = null;
		
		String localsubmissionId = jsonObj.getString("submissionId");
	    localsubmissionId = localsubmissionId.replaceAll("\\[", "").replaceAll("\\]","");
	    String[] submissionIds = localsubmissionId.split(",");
    	
		if(submissionIds != null && submissionIds.length>0){ 
			for(String submissionId : submissionIds){
				  	submissionId = submissionId.replace("\"", "");
				  	submissionApproverHierarchyService.updateSubmissionStatus(Long.parseLong(submissionId), Submission_Status.DRAFT.getValue());
				  	/*TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
				  	timesheetSubmissions.setSubmissionStatus(Submission_Status.DRAFT);
				    timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
				  	timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);*/
			}
		}
			
	}
		

	
	@RequestMapping(value = "/getArrovalSubmissions",  method = RequestMethod.POST)
	public @ResponseBody  String getArrovalSubmissions(HttpServletRequest request, @RequestParam(value="employeeId", required=false) String employeeId) throws Exception {
		
		String resultJson = null;
		Collection<TimesheetSubmissions> timesheetSubmissionsList  = null;
        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        if(!employeeId.isEmpty()){
        	
        	 timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(user.getId(),employeeId);
        	 
        }else{
        	
        	  timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissions(user.getId());
        }
      
        
        ArrayList<TimesheetSubmissions> timesheetSubmissionsArrList = new ArrayList<TimesheetSubmissions>();
        
        StringBuilder sb = new StringBuilder();
		/*sb.append(" <table>");
		sb.append(" <tbody>");
		sb.append(" <thead>");
		sb.append("     <tr>");
		sb.append("         <th>Employee</th>");
		sb.append("         <th>Frequency</th>");
		sb.append("         <th>Total hours</th>");
		sb.append("         <th>Stage</th>");
		sb.append("         <th>Action</th>");
		sb.append("     </tr>");
		sb.append(" </thead>");*/
		
		if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
			
        sb.append("	<div>");
		sb.append("	<ul class='table_head'>");
		sb.append("    <li>Employee</li> ");
		sb.append("    <li>Frequency</li>            ");
		sb.append("    <li>Total hours</li>          ");
		sb.append("    <li>Stage</li>                ");
		sb.append("    <li>Action</li>               ");
		sb.append("    <div class='clearfix'></div> ");
		sb.append("</ul>");
       	sb.append(" <ul class='table_details'> ");
		 
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date currentStartDate = null;
        Date currentEndDate = null;
        
        
        for(TimesheetSubmissions ts : timesheetSubmissionsList){
        	 
        	
        	   HashMap<String, Date> submissionDateRange = ts.getDateList();
        	   Date employeeStartDate = submissionDateRange.get("STARTDATE");
        	   Date employeeEndDate = submissionDateRange.get("ENDDATE");
        	   
        	   Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
			   Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			   startCal.setLenient(false);
        	   
        	   if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
			    	  startCal.setTime(currentWeekStartDate);
		       }
		       else if(employeeEndDate.before(currentWeekStartDate)){
		    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		    	  endCal.setLenient(false);
		    	  endCal.setTime(employeeEndDate);
		    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON").getTime());
		       }
		       else if(employeeStartDate.after(currentWeekStartDate)){
		    	  startCal.setTime(employeeStartDate);
		       }
        	   
        	   String startDate =  format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
		       String endDate   =  format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime());
			 
        		 		
		       ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
		       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
        	   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
        		   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
        	   }
        	   
        	   currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
               currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
               
               if(currentStartDate !=null){
            	   	ts.setStartDateStr(Constants.DF_DMY.format(currentStartDate));
               }
        	   if(currentEndDate !=null){
        		   ts.setEndDateStr(Constants.DF_DMY.format(currentEndDate));
        	   }
        	   
               				sb.append("<div id='row"+ts.getProjectMembers().getEmployee().getUser().getId()+"'>");
		                    sb.append("  <li><div class='time_appr_user'>");
		                    sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
		                    sb.append("    <span><b>"+ts.getFullName()+"</b></span></div>");
		                    sb.append("    </li>");
		                    
		                    sb.append("<li><div>");
		     		       if(employeeStartDate.before(currentStartDate)){
		     		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
		     	       	   }
		     	       	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDate+" To "+endDate+"</span>");
		     	       		
		     	       	   if(currentEndDate.before(employeeEndDate)){
		     	       			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
		     	       	   }
		     	       	   sb.append("    </div>");
		     	       	   sb.append("    </li>");
		     	       	   
		                    sb.append("    <li><div> " );
		                    sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
		                    sb.append("    </div>");
		                    sb.append("    </li>");
		                    
		                    sb.append("  <li><div>"+ts.getCombinedStatus()+"</div></li>");
		                    
		                    sb.append("    <li><div> " );
		     	               if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
		     	                   sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
		     	                   sb.append("<div id='rejectedPopup'></div>");
		     	               }
		     	               if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
		     	            	  sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
		     	                  sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
		     	                  sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
		     	                  sb.append(" </i>");
		     	                  sb.append(" </div>");
		     	              }
		     	              if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
		     	            	  sb.append(" <div><h3>Approved</h3></div>");
		     	              }  
		                    sb.append("    </div>");
		                    sb.append("    </li>");
		                    sb.append(" <div class='clearfix'></div>");
		                    sb.append("</div>");
       	
        	   /*sb.append("  <tr id='row"+ts.getId()+"'>");
               sb.append("  <td><div class='time_appr_user'>");
               sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
               sb.append("    <span><b>"+ts.getFullName()+"</b></span></div></div>");
               sb.append("    </td>");
               
               sb.append("<td><div>");
		       if(employeeStartDate.before(currentStartDate)){
		       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getId()+"\");'></i>");
	       	   }
	       	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDate+" To "+endDate+"</span>");
	       		
	       	   if(currentEndDate.before(employeeEndDate)){
	       			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDate+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getId()+"\");'></i><abbr></abbr> ");	
	       	   }
	       	   sb.append("    </div>");
	       	   sb.append("    </td>");
	       	   
               sb.append("    <td><div> " );
               sb.append(" <a onclick='myTimesheet(\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a></div></td>");
               sb.append("    </div>");
               sb.append("    </td>");
               
               sb.append("  <td><div>"+ts.getCombinedStatus()+"</div></td>");
               
               sb.append("    <td><div> " );
	               if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
	                   sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\'"+ts.getId()+"\',\'"+ts.getEmployeeId()+"\',\'"+ts.getStartDateStr()+"\',\'"+ts.getEndDateStr()+"\');'></i></div>");
	                   sb.append("<div id='rejectedPopup'></div>");
	               }
	               if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
	                 sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
	                 sb.append(" <i class='glyphicon glyphicon-comment' onclick='rejectedMessage(\'rejectedMessage"+ts.getId()+"\');'>");
	                 sb.append(" <strong id='rejectedMessage"+ts.getId()+"'><p>"+ts.getRejectedReason()+"</p></strong>");
	                 sb.append(" </i>");
	                 sb.append(" </div>");
	              }
	              if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
	            	  sb.append(" <div><h3>Approved</h3></div>");
	              }  
               sb.append("    </div>");
               sb.append("    </td>");
               sb.append("</tr>");*/
             
         }
         sb.append("	<div>");
		}
        /* sb.append(" </tbody>");
         sb.append(" </table>");
          */
      
         return sb.toString();
         
	}
	
	
	// sarath kumar punnana : this controller  method is for get new timwsheet screen at page load.
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String dynamicTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate,@RequestParam(value="memberId", required=false) Long memberId,@RequestParam(value="projectMemberUserId", required=false) Long projectMemberUserId,@RequestParam(value="employeeId", required=false) Long employeeId) throws Exception {
		 
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(projectMemberUserId);
		Date employeeStartDate = submissionDateRange.get("STARTDATE");
		Date employeeEndDate = submissionDateRange.get("ENDDATE");
		TimesheetSubmissions  ts = null;
		
		StringBuilder sb = new StringBuilder();	
		if(employeeStartDate != null && employeeStartDate != null){
		
		  StringBuilder sb1 = new StringBuilder();
		 
		  StringBuilder startDateBuilder = new StringBuilder();
		  StringBuilder endDateBuilder = new StringBuilder();
		 
		  String pattern = "dd-MM-yyyy";
          SimpleDateFormat format = new SimpleDateFormat(pattern);
          Date currentStartDate = null;
          Date currentEndDate = null;
		 if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty() ){  
			      Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
			      Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				   startCal.setLenient(false);
					
			      if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
			    	  startCal.setTime(currentWeekStartDate);
			      }
			      else if(employeeEndDate.before(currentWeekStartDate)){
			    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    	  endCal.setLenient(false);
			    	  endCal.setTime(employeeEndDate);
			    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON").getTime());
			      }
			      else if(employeeStartDate.after(currentWeekStartDate)){
			    	  startCal.setTime(employeeStartDate);
			      }
			      
	        	  
		         startDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime()));
		         endDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime()));
		         ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId(),memberId,employeeId);
                 
                 currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
                 currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
        }
        else{
                
                 if(navigation.equalsIgnoreCase("left")){
                     
                     
                     Date sDate  =  Constants.DMY.parse(startDate);
	                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					 startCal.setLenient(false);
					 startCal.setTime(sDate);
					 
						                
	                startDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime()));
			        endDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime()));
	                
			        ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime(), user.getId(),memberId,employeeId);
	                currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime();
	                currentEndDate = CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime();
                 }
                 else{ 
                 
	                 Date sDate  =  Constants.DMY.parse(startDate);
	                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					 startCal.setLenient(false);
					 startCal.setTime(sDate);
					 
						                
	                startDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime()));
			        endDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,13).getTime()));
	                
			        ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,13).getTime(), user.getId(),memberId,employeeId);
	                currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,7).getTime();
	                currentEndDate = CalendarUtil.getDateIncrementByincrement(startCal,13).getTime();
                 }
        }
        
		if(currentStartDate !=null){
      	   	ts.setStartDateStr(Constants.DF_DMY.format(currentStartDate));
        }
  	    if(currentEndDate !=null){
  		   ts.setEndDateStr(Constants.DF_DMY.format(currentEndDate));
  	    }
  	    
  	   ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
	   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
		   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
	   }
	   		
         sb.append("  <li><div class='time_appr_user'>");
         sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='/displayUserImage/"+ts.getUserId()+"' alt='Profile Image' class='img-circle img-responsive'>");
         sb.append("    <span><b>"+ts.getFullName()+"</b></span>");
         sb.append(" <div></li>");
         
         sb.append("<li><div>");
	       if(employeeStartDate.before(currentStartDate)){
	       			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDateBuilder+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i>");
     	   }
     	   sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+" To "+endDateBuilder+"</span>");
     		
     	   if(currentEndDate.before(employeeEndDate)){
     			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDateBuilder+"\",\""+ts.getProjectMembers().getEmployee().getUser().getId()+"\",\""+ts.getProjectMembers().getId()+"\",\""+ts.getProjectMembers().getEmployee().getId()+"\");'></i><abbr></abbr> ");	
     	   }
     	   sb.append("    </div>");
     	   sb.append("    </li>");
     	   
     	 
         sb.append("    <li><div> " );
         sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
         sb.append("    </div>");
         sb.append("    </li>");
         
         sb.append("  <li><div>"+ts.getCombinedStatus()+"</div></li>");
         
         sb.append("    <li><div> " );
             if(ts.getCombinedStatus().equalsIgnoreCase("NEW") || ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL") ){
                 sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
                 sb.append("<div id='rejectedPopup'></div>");
             }
             if(ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
               sb.append(" <i class='glyphicon glyphicon-comment' onclick='rejectedMessage(\'rejectedMessage"+ts.getId()+"\');'>");
               sb.append(" <strong id='rejectedMessage"+ts.getId()+"'><p>"+ts.getRejectedReason()+"</p></strong>");
               sb.append(" </i>");
               sb.append(" </div>");
            }
            if(ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
          	  sb.append(" <div><h3>Approved</h3></div>");
            }  
            sb.append(" </div>");
            sb.append("    </li>");
            sb.append("<div class='clearfix'></div>");
		}
        
        return sb.toString();
	}
	
	@RequestMapping(value = "/saveSubmissionApprovalStatus", method = RequestMethod.POST)
    public  String saveSubmissionApprovalStatus(HttpServletRequest request,
									    		@RequestParam(value="employeeId", required=false) Long employeeId,
												@RequestParam(value="startDate", required=false) String startDate,
												@RequestParam(value="endDate", required=false) String endDate,
												@RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
									             
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
        
        
        submissionApproverHierarchyService.updateApprovalStatusForApprover(employeeId, user.getId(), sDate, approvalStatus, rejectedReason);
        
      /*  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(), sDate);
        if(timesheetSubmissionsList!=null){
        	for(TimesheetSubmissions timesheetSubmissions :timesheetSubmissionsList){
	             if(approvalStatus.equalsIgnoreCase("REJECTED") && approvalStatus!=null){
	            	 	timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
	            	 	timesheetSubmissions.setSubmissionStatus(Submission_Status.REJECTED);
	            	 	timesheetSubmissions.setRejectedReason(rejectedReason);
	             }else if(approvalStatus.equalsIgnoreCase("APPROVED") && approvalStatus!=null){
	            	 timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 timesheetSubmissions.setSubmissionStatus(Submission_Status.APPROVED);
	             }
	             
	             timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
        	}  
        }*/
        return "success";
    }
	
	
	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(@RequestParam(value="submissionId", required=false) Long submissionId ,HttpServletResponse response) throws Exception {
		
		    TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition",
	                "attachment;filename="+timesheetSubmissions.getFileName());
		    
			InputStream inputStream = new ByteArrayInputStream(timesheetSubmissions.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}  
		 

	
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
	
	
	@RequestMapping(value = "timesheetSubmissions/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("ok");*/
		timesheetSubmissions.setStatus("Active");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("prasad");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("venket");
		
		TimesheetSubmissions savedTimesheetSubmissionss = timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		return savedTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("False");*/
		timesheetSubmissions.setStatus("Reject");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("Sri");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("Sai");
		
		TimesheetSubmissions updtaeTimesheetSubmissionss = timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		return updtaeTimesheetSubmissionss; 
	}
	
	
/*	@RequestMapping(value = "/getTimesheetEntries", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@RequestParam String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}*/
	
	@RequestMapping(value = "timesheetSubmissions/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		Boolean status = timesheetSubmissionsService.deleteTimesheetSubmissions(id);
		return status; 
	}
	
	@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
    public String deleteSubmissionDocument(@RequestParam(value="submissionId", required=false) Long submissionId ,HttpServletResponse response) throws Exception {
        
            TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
            timesheetSubmissions.setDocFile(null);
            timesheetSubmissions.setFileContentType(null);
            timesheetSubmissions.setFileName(null);
            timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
            return "success";
    }
	
	/*@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetSubmissions> getAllTimesheetSubmissions() throws Exception
			 
	{
		  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions();
		  return timesheetSubmissionsList;
	}*/
     
	 private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	 }
	
	 public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
 
}
