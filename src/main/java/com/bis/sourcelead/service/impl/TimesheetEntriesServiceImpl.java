package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class TimesheetEntriesServiceImpl implements TimesheetEntriesService {
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesServiceImpl.class);
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TimesheetSubmissionsDocumentsService  timesheetSubmissionsDocumentsService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	

	 /**
	  * 
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to display employee Timesheet.
	 * this method is called at onload when click on timesheet left menu
	 * 
	 * @param user This is the first paramter to newTimesheet method
	 * @param navigation This is the second paramter to newTimesheet method
	 * @param startDate This is the third paramter to newTimesheet method
	 *  
	 * @return string 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public String newTimesheet(User user,String navigation, String startDate,Long locationsId) throws Exception {
		 
		
		/**
		  * Get Logged-in Employee startDate and endDate range across Various Projects(which are assigned to respective employee)
		  * @ Hint: Submission Table Records are already availble
		  * (We are storing these records with employee assigned date at the time of assignment and when employee/particular approver logged in to system(if record not exist then only))
	      *
	    */
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(user.getId(),locationsId);
		Date employeeStartDate = submissionDateRange.get("STARTDATE");
		Date employeeEndDate = submissionDateRange.get("ENDDATE");
		
		/**
		  *calculating prior 6th week mondaydate and post 6th week sunday day for employee
		  *
	      *
	    */
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(commonUtil.currentDate());
		Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON");
		Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN");
		
		
		Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(startMonday,-42);
		Date previousMondayDate = previousMonday.getTime();
		
		Calendar nextSunday = CalendarUtil.getDateIncrementByincrement(endSunday,42);
		Date nextSundayDate = nextSunday.getTime();
		
		
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = null;
		if(employeeStartDate != null && employeeStartDate != null){
		  /**
		   * checking whether prior 6th week monday date is greaterthan employee startdate or not
	       */
		   if(employeeStartDate.before(previousMondayDate)){
			   employeeStartDate = previousMondayDate;
		   }
		   
		  /**
		   * checking whether post 6th week sunday date is lesserthan employee enddate or not
	       */
		   if(employeeEndDate.after(nextSundayDate)){
			   employeeEndDate = nextSundayDate;
		   }
			
		  StringBuilder sb1 = new StringBuilder();
		 
		  StringBuilder startDateBuilder = new StringBuilder();
		  StringBuilder endDateBuilder = new StringBuilder();
		 
		 String pattern = "dd-MM-yyyy";
          SimpleDateFormat format = new SimpleDateFormat(pattern);
          Date currentStartDate = null;
          Date currentEndDate = null;
          Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		   startCal.setLenient(false);
		   /**
			 * startDate == null means current week timesheets should be shown
		   */
		   if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty() ){  
			      Date currentWeekStartDate = CalendarUtil.getCurrentWeekDayForTimesheets("MON");
			      if(currentWeekStartDate.equals(employeeStartDate)|| (currentWeekStartDate.before(employeeEndDate) && currentWeekStartDate.after(employeeStartDate))){
			    	  startCal.setTime(currentWeekStartDate);
			      }
			      else if(employeeEndDate.before(currentWeekStartDate)){
			    	  Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    	  endCal.setLenient(false);
			    	  endCal.setTime(employeeEndDate);
			    	  startCal.setTime(CalendarUtil.getPreviousWeekDayByCalendar(endCal,"MON").getTime());
			      }
			      else if(employeeStartDate.after(currentWeekStartDate)){
			    	  startCal.setTime(employeeStartDate);
			      }
          }
		   /**
			 * startDate comes from ui screen when << or >> links clicked
		   */
		  else{
			  Date sDate  =  Constants.DMY.parse(startDate);
              if(sDate != null && employeeEndDate.before(sDate)){
           	      Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			    	  endCal.setLenient(false);
			    	  endCal.setTime(employeeEndDate);
			    	  startCal.setTime(CalendarUtil.getPreviousWeekDayByCalendar(endCal,"MON").getTime());
              }
              else{
           	   startCal.setTime(sDate);
              }
		  }
		 String weekStartDate = format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
		 startDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime()));
         endDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime()));
         sb1.append("<th>Mon<i>"+startDateBuilder+"</i></th>"); 
         sb1.append("<th>Tue<i>"+format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"TUE").getTime())+"</i></th>"); 
         sb1.append("<th>Wed<i>"+format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"WED").getTime())+"</i></th>"); 
         sb1.append("<th>Thu<i>"+format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"THU").getTime())+"</i></th>"); 
         sb1.append("<th>Fri<i>"+format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"FRI").getTime())+"</i></th>"); 
         sb1.append("<th>Sat<i>"+format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SAT").getTime())+"</i></th>"); 
         sb1.append("<th>Sun<i>"+endDateBuilder+"</i></th>"); 
         timesheetSubmissionsList = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissions(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime(),CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime(), user.getId());
         currentStartDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime();
         currentEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime();
        
		StringBuilder sb = new StringBuilder();	
		sb.append("	<div class='timesheet_head'>");
		sb.append("   	<ul>");
		sb.append("       	<li><i class='glyphicon glyphicon-print'></i></li>");
		sb.append("           <li><i class='glyphicon glyphicon-export'></i><span>Export</span><i class='glyphicon glyphicon-play' id='export-pop' onclick='exportType();'></i>|");
		sb.append("           	<div class='export_pop'>");
		sb.append("               	<ul>");
		sb.append("                   	<li onclick='exportPdf();'>PDF &nbsp;&nbsp;<i class='fa fa-file-pdf-o'></i></li>");
		sb.append("                       <li onclick='exportExcel(\""+weekStartDate+"\");'>Excel &nbsp;&nbsp;<i class='fa fa-file-excel-o'></i></li>");
		sb.append("                   </ul>");
		sb.append("               </div>");
		sb.append("           </li><li>");
		
		
	    /**
		 * check whether previous submission records are available or not
	    */
		Date leftNavStartDate = timesheetSubmissionsService.getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(user.getId(), currentStartDate);
		if(leftNavStartDate != null && (leftNavStartDate.after(employeeStartDate) || leftNavStartDate.equals(employeeStartDate))){
			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+format.format(leftNavStartDate)+"\");'></i>");
		}
		sb.append("            <span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+" To "+endDateBuilder+"</span>");
		
		/**
		 * check whether next submission records are available or not
	    */
		Date rightNavStartDate = timesheetSubmissionsService.getRightNavStartDateForEmployeeBasedOnCurrentStartDate(user.getId(), currentStartDate);
		if(rightNavStartDate != null){
	    Calendar rightCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);	
		rightCal.setLenient(false);
		rightCal.setTime(rightNavStartDate);
		Calendar rightNavEndDate = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(rightCal, "SUN");
		if((rightNavEndDate.getTime().before(employeeEndDate) || employeeEndDate.equals(rightNavEndDate.getTime()))){
			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+format.format(rightNavStartDate)+"\");'></i><abbr>|</abbr> ");	
		}
		}               
		sb.append("           </li><div class='clearfix'></div>");
		sb.append("       </ul>");
		sb.append("       <div class='clearfix'></div>");
		sb.append("   </div>");
					
		sb.append("<div class='timesheet_table' id='dynamic'>");
        sb.append("<table id='timesheet' cellpadding='0' cellspacing='0'>");
        sb.append("<tbody>");
        sb.append("	<tr>");
        sb.append("    	  <th>Projects</th>");
        sb.append("        <th> ");
        sb.append("         <table id='1' cellpadding='0' cellspacing='0'>");
        sb.append("             <th>Tasks </th>");
        sb.append(sb1); // add builder1
        sb.append("         </table>");
        sb.append("        </th> ");
        sb.append("        <th>Status</th>");
        sb.append("</tr>");
        
        String approvalStatus = null;
        HashSet<String> combinedApprovalStatus = new HashSet<String>();
        ArrayList<Boolean> isThereAnyApproverApproved = null;
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			isThereAnyApproverApproved = new ArrayList<Boolean>();
			
			/**
			 * For to show submission status of each submission record(Hint:As per employee one submission related to one project)
		    */
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(ts.getId());
			/**
			 * Don't show withdraw button if any approver(either level1 or level2) is approved
		    */
			SubmissionApproverHierarchy isSah = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForTheSubmission(ts.getId(), "APPROVED");
			if(isSah != null){
				isThereAnyApproverApproved.add(true);
			}
			else{
				isThereAnyApproverApproved.add(false);
			}
			
			Submission_Status submissionStatus = sah.getSubmissionStatus();
			
			
			/**
			 * Here getting  dates which are enabled in the current submission
			 * (Hint:In particular project,the member may assigned in the mid of the week) 
		    */
			HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(ts.getProjectMembers().getId(), currentStartDate, currentEndDate);
			//Date assignDate = ts.getProjectMembers().getProjectAssignedDate();
			//Date assignEndDate = ts.getProjectMembers().getProjectEndDate();
			//CommonUtil
			//currentStartDate
			
			String submissionStatusStr = submissionStatus.getValue();
			String disbleCondition1 = "";
			 Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 currentCal.setLenient(false);
			 currentCal.setTime(currentStartDate);
			if((!pmhDateList.contains(currentStartDate)) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition1 = "disabled";
			}
			String disbleCondition2 = "";
            if((!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE").getTime())) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition2 = "disabled";
			}
			String disbleCondition3 = "";
            if((!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED").getTime())) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition3 = "disabled";
			}
			String disbleCondition4 = "";
            if((!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU").getTime())) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition4 = "disabled";
			} 
			String disbleCondition5 = "";
            if((!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI").getTime())) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition5 = "disabled";
			}
			String disbleCondition6 = "";
            if((!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT").getTime())) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition6 = "disabled";
			}
			String disbleCondition7 = "";
            if((!pmhDateList.contains(currentEndDate)) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition7 = "disabled";
			} 
						
			/*if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition = "disabled";
			}*/
			/*if(ts.getSubmissionApprovalStatus() != null){
				approvalStatus = ts.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}*/
            
            
            if(sah.getSubmissionApprovalStatus() != null){
				approvalStatus = sah.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}
            
            if(isSah != null && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())){
            	combinedApprovalStatus.add(Submission_ApprovalStatus.getNameByValue("approved").getValue());
            }
            else{
            	combinedApprovalStatus.add(approvalStatus);
            }
		   	
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='timesheetrProject pname "+submissionStatus+" ' id='"+ts.getId()+"' > <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
			sb.append("<td>");
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			 
			Collection<TimesheetEntries>  timesheetEntriesList =   getAllTimesheetEntriesBySubmissionId(ts.getId());
			
			if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
						Integer maxCount = timesheetEntriesList.size();
						Integer rowCount = 1;
						maxCount = maxCount+1;
						sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
						sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
						for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
							           
							          /*timesheet entries id*/
									  sb.append("<input type='hidden' name='"+rowCount+"submission"+ts.getId()+"timesheetEntriesId'   id='"+rowCount+"submission"+ts.getId()+"timesheetEntriesId'   value='"+timesheetEntries.getId()+"' />");
						              sb.append("       <tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
						              sb.append("                 <td>");
						              sb.append("                      <div> ");
						              
						              
						              if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
						            	  if(timesheetEntries.getProjectTask() != null){
						            	  sb.append("<p>"+timesheetEntries.getProjectTask().getTaskName()+"</p>");
						            	  }
						              }
						              else{
						            	      sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
						            	      sb.append("<option value=''>--select option--</option>");
						            	      for(ProjectTasks pt   : ProjectTasksList){
										    	       if(timesheetEntries.getProjectTask() != null && pt.getId().equals(timesheetEntries.getProjectTask().getId())){
										    	    	   sb.append("<option value='"+pt.getId()+"' selected>"+pt.getTaskName()+"</option>");
										    	       }
										    	       else{
										    	    	   sb.append("<option value='"+pt.getId()+"'>"+pt.getTaskName()+"</option>");
										    	       }
										                
										      }
								              sb.append(" </select>"); 
								              if(rowCount == 1){
								             	  sb.append("<input type='button' value='' onClick='addTimeSheetRow(\""+ts.getId()+"\",\""+ts.getProjectMembers().getProjects().getId()+"\")' class='addtaskfield'/>");
								              }else{
								                  sb.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
								              }
						              }
						               
						              sb.append("                      </div>");
						              sb.append("                 </td> ");
									  sb.append("				<td> <div> ");   
						              sb.append(" <input type='text' "+disbleCondition1+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"submission"+ts.getId()+"comment1Id' >"+timesheetEntries.getMoncomments()+"</textarea></span></div>");
					            	  }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"submission"+ts.getId()+"comment1Id'  id='"+rowCount+"submission"+ts.getId()+"textArea1Id' ></textarea></span></div>");
					            	  }
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='"+timesheetEntries.getTueHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>");
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"submission"+ts.getId()+"comment2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>");
						              }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"submission"+ts.getId()+"comment2Id' id='"+rowCount+"submission"+ts.getId()+"textArea2Id'></textarea></span></div>");
						              }
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='"+timesheetEntries.getWedHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						                  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");  
						                  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"submission"+ts.getId()+"comment3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>");
						              }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"submission"+ts.getId()+"comment3Id' id='"+rowCount+"submission"+ts.getId()+"textArea3Id'></textarea></span></div>");
						              }
						              sb.append(" </div> "); 
						              sb.append(" </td>"); 
					
						    		  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition4+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='"+timesheetEntries.getThuHours()+"' placeholder='HH:MM' />"); 	
						              sb.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"submission"+ts.getId()+"comment4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>");
					            	  }
						              else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"submission"+ts.getId()+"comment4Id' id='"+rowCount+"submission"+ts.getId()+"textArea4Id'></textarea></span></div>");
						              }
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" <td> <div>"); 
						              sb.append(" <input type='text'  "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='"+timesheetEntries.getFriHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"submission"+ts.getId()+"comment5Id'>"+timesheetEntries.getFricomments()+"</textarea></span></div>");
					            	  }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"submission"+ts.getId()+"comment5Id' id='"+rowCount+"submission"+ts.getId()+"textArea5Id'></textarea></span></div>");
					            	  }
						              sb.append(" </div> </td>"); 
						              
									  sb.append("<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value='"+timesheetEntries.getSatHours()+"'  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"submission"+ts.getId()+"comment6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>");
					            	  }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"submission"+ts.getId()+"comment6Id' id='"+rowCount+"submission"+ts.getId()+"textArea6Id'></textarea></span></div>");
					            	  }
						              sb.append(" </div> </td>"); 
						              
									  sb.append("						<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
						              sb.append(" <div class='timesheet_comm'>");
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
						            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"submission"+ts.getId()+"comment7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>");
					            	  }else{
						            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
						            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"submission"+ts.getId()+"comment7Id' id='"+rowCount+"submission"+ts.getId()+"textArea7Id'></textarea></span></div>");
					            	  }
						              sb.append(" </div> </td>"); 
									                                         
						              sb.append(" </tr> ");      
						              rowCount++;      
						     }
						        sb.append(" </table>"); 
						        sb.append("</td> "); 
						        sb.append("<td class='pname last_td'> <div>");
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
							        	sb.append("<b>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
							        	sb.append("<b class='proj_draft'>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
							        	sb.append("<b class='proj_process'>"+submissionStatus+"</b>");
							        	if(isSah == null ){
							                String startDateStr = format.format(ts.getStartDate());
							        		sb.append("<b class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+ts.getId()+"\",\""+startDateStr+"\");' >Modify</b>");
							        	}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
							        	sb.append("<b class='proj_active'>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
							        	sb.append("<b class='proj_closed'>"+submissionStatus+"</b>");
							        	
							        	SubmissionApproverHierarchy submissionApproverHierarchy =  submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(ts.getId());
						                sb.append(" <i class='glyphicon glyphicon-comment rejectedHover' id='"+submissionApproverHierarchy.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+submissionApproverHierarchy.getId()+"\");'>");
						                sb.append(" <div class='app_error_mes' id='rejectedMessage"+submissionApproverHierarchy.getId()+"'>"+submissionApproverHierarchy.getRejectedReason()+"</div>");
						                sb.append(" </i>");
							        }
						        sb.append("</div> </td>");
				  }
			      else{
			    	    Integer maxCount = 1;
						Integer rowCount = 1;
						maxCount = maxCount+1;
						sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
						sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
						              sb.append("       <tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
						              sb.append("                 <td>");
						              sb.append("                      <div> ");
						              
						              if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
						            	  sb.append("<p>NO DATA</p>");
						              }
						              else{
						            	  
                                          sb.append("     <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
                                          sb.append("<option value=''>--select option--</option>");
                                          for(ProjectTasks pt   : ProjectTasksList){
									              sb.append("<option value='"+pt.getId()+"'>"+pt.getTaskName()+"</option>");   
									      }
							              sb.append(" </select>"); 
							              if(rowCount == 1){
							             	  sb.append("<input  type='button' value='' onClick='addTimeSheetRow(\""+ts.getId()+"\",\""+ts.getProjectMembers().getProjects().getId()+"\")' class='addtaskfield'/>");
							              }else{
							                  sb.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
							              }
						              }
							              
						             
						              sb.append("                      </div>");
						              sb.append("                 </td> ");
									  sb.append("				<td> <div> ");   
						              sb.append(" <input type='text'  "+disbleCondition1+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>");
						              if(isNullOrEmpty(disbleCondition1))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' id='"+rowCount+"submission"+ts.getId()+"textArea1Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition2))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id' id='"+rowCount+"submission"+ts.getId()+"textArea2Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>");  
						              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition3))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id' id='"+rowCount+"submission"+ts.getId()+"textArea3Id'></textarea></span></div>"); 
						              sb.append(" </div> "); 
						              sb.append(" </td>"); 
					
						    		  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='' placeholder='HH:MM' />"); 	
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition4))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id' id='"+rowCount+"submission"+ts.getId()+"textArea4Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition5))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id' id='"+rowCount+"submission"+ts.getId()+"textArea5Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition6))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id' id='"+rowCount+"submission"+ts.getId()+"textArea6Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("						<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition7))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' id='"+rowCount+"submission"+ts.getId()+"textArea7Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" </tr> ");      
						              sb.append(" </table>"); 
						              sb.append(" </td>   "); 
						              sb.append("</td> "); 
								      sb.append("<td class='pname last_td'> <div>");
								      
								        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
								        	sb.append("<b>"+submissionStatus+"</b>");
								        }
								        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
								        	sb.append("<b class='proj_draft'>"+submissionStatus+"</b>");
								        }
								        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
								        	sb.append("<b class='proj_process'>"+submissionStatus+"</b>");
								        	if(isSah == null ){
								        		String startDateStr = format.format(ts.getStartDate());
								        	    sb.append("<b class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+ts.getId()+"\",\""+startDateStr+"\");' >Modify</b>");
								        	}
								        }
								        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
								        	sb.append("<b class='proj_active'>"+submissionStatus+"</b>");
								        }
								        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
								        	sb.append("<b class='proj_closed'>"+submissionStatus+"</b>");
								        	
								        	SubmissionApproverHierarchy submissionApproverHierarchy =  submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(ts.getId());
							                sb.append(" <i class='glyphicon glyphicon-comment rejectedHover' id='"+submissionApproverHierarchy.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+submissionApproverHierarchy.getId()+"\");'>");
							                sb.append(" <div class='app_error_mes' id='rejectedMessage"+submissionApproverHierarchy.getId()+"'>"+submissionApproverHierarchy.getRejectedReason()+"</div>");
							                sb.append(" </i>");
								        }
								        sb.append("</div> </td>");
								      
			       }
			}
			
			sb.append("   </tr>"); 
			sb.append("	  <tr class='last_child'>"); 
			sb.append("    <td></td>"); 
			sb.append("    <td> "); 
			sb.append("    <table id='1' cellpadding='0' cellspacing='0'>"); 
			sb.append("     <td class='total_field'>Total</td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal1Id' name='rowTotal1Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal2Id' name='rowTotal2Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal3Id' name='rowTotal3Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text'disabled  id='rowTotal4Id' name='rowTotal4Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal5Id' name='rowTotal5Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal6Id' name='rowTotal6Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal7Id' name='rowTotal7Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    </table>"); 
			sb.append("           </td> ");
			sb.append("    <td> <div>"); 
			sb.append("    <input disabled type='text' id='grandTotal' name='grandTotal' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>    "); 
			sb.append("      </tr> "); 
			sb.append("            </tbody>");
			sb.append("     </table>");
			
			sb.append("<div class='timesheet_bot'>");
			sb.append(" 	<div class='timesheet_week_comm'>");
			
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty() ){
			TimesheetSubmissions tsFirst = timesheetSubmissionsList.iterator().next();
			
			if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
					if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
						sb.append("<div class='commentTime'>");
						sb.append("<textarea  name='weekComment'  id='weekComment' readonly='readonly' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
						sb.append("</div>");
					}else{
						sb.append("<div class='commentTime'>");
						sb.append("<textarea   name='weekComment' id='weekComment' readonly='readonly' placeholder='comments here...'></textarea>");
						sb.append("</div>");
					}
			}else{
					if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
						sb.append("<div class='commentTime'>");
						sb.append("<textarea  name='weekComment'  id='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
						sb.append("</div>");
					}else{
						sb.append("<div class='commentTime'>");
						sb.append("<textarea   name='weekComment' id='weekComment' placeholder='comments here...'></textarea>");
						sb.append("</div>");
					}
			}
			
			
			
				//display multiple attachments
				StringBuilder documentBuffer = new StringBuilder();	
				List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(tsFirst.getId());
				documentBuffer.append("<input type='hidden' name='attcahmentMaxRowNumber'  id='attcahmentMaxRowNumber' value='1' />");
				documentBuffer.append("<div class='timesheetDocs'>"); 
					if(timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()){
						for(TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList){
							if(!isNullOrEmpty(tsd.getFileName())){
									documentBuffer.append("<strong>");
									documentBuffer.append(" <a class='downicon' href=\'"+sourceleadUrl+"/downloadDocument?submissionDocumentId="+tsd.getId()+"\'>");
									documentBuffer.append("<i class='glyphicon glyphicon-save'></i>");
									documentBuffer.append(" <input type='button' value='"+tsd.getFileName()+"'>");
									documentBuffer.append("</a>");
									
									if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
									}else{
										documentBuffer.append("<input class='dele' type='button' onClick='deleteSubmissionDocument(\""+tsd.getId()+"\",\""+startDate+"\");' >");
									}
									documentBuffer.append("</strong>");
							} 
						}
					} 
			
					if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
					}else{
		            	documentBuffer.append("<div class='commentFile'>"); 
						documentBuffer.append("<span>"); 
						documentBuffer.append("<table class='timesheetAttachment' id='attachmentTable' cellpadding='0' cellspacing='0'>");
								documentBuffer.append("<tr id='divAttachmentTsRow0'>");
								documentBuffer.append("<td><div id='0docFileuploadFileGroup'><input type='file' name='0docFile' id='0docFile' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment()'></i></div></td> ");           
								documentBuffer.append("</tr>");
						documentBuffer.append("</table>");
						documentBuffer.append("</span>");
						documentBuffer.append("</div>");
					}
			documentBuffer.append("</div>"); 
			sb.append(documentBuffer);
		/*	if(!isNullOrEmpty(tsFirst.getFileName())){
				sb.append("<strong>");
				sb.append(" <a class='downicon' href=\'"+sourceleadUrl+"/downloadDocument?submissionId="+tsFirst.getId()+"\'>");
				sb.append("<i class='glyphicon glyphicon-save'></i>");
				sb.append(" <input type='button' value='"+tsFirst.getFileName()+"'>");
				sb.append("</a>");
				
				if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
				}else{
					 sb.append("<input class='dele' type='button' onClick='deleteSubmissionDocument(\""+tsFirst.getId()+"\");' >");
				}
				sb.append("</strong>");
			}*/
			}
			sb.append("         <div class='clearfix'></div>");
			sb.append("     </div>");
			
			
			if(combinedApprovalStatus == null || (combinedApprovalStatus != null && (combinedApprovalStatus.contains(Submission_ApprovalStatus.getNameByValue("rejected").getValue()) || combinedApprovalStatus.contains(Submission_ApprovalStatus.getNameByValue("new").getValue())))){
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("	       <input type='button' name='submit'  onClick='saveMyTimesheet(\"Draft\",\"user\");' value='Save' class='time_btn time_dra'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\",\"user\");' value='Submit' class='time_btn'>");
				   sb.append("        <input type='button' onClick='clearTimesheet();'  value='Clear' class='time_btn time_cancel'>");
				   sb.append("");
				   sb.append("    </div>");
				   sb.append("</div>");   
			}
			else{
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("  </div>");
				   sb.append("</div>");
			}
		  
			
			sb.append(" </div>");
			sb.append(" </div>");
			return sb.toString();
		}else{
			return "";
		}
	
		
	}
	
	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to display employee Timesheets at time sheet approver.
	 * this method is called  when click on timesheet approval list count.
	 * 
	 * @param user This is the first paramter to dynamicTimesheetpopUp method
	 * @param employeeId This is the second paramter to dynamicTimesheetpopUp method
	 * @param endDate This is the third paramter to dynamicTimesheetpopUp method
	 *  
	 * @return string 
	 * 
	 * @throws NullPointer
	 */

	@Override
	public String dynamicTimesheetpopUp(User user, Long employeeId, String startDate, String endDate)
			throws Exception {
		
		
		
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
        Long employeeUserId = null;
        Long projectMemberId = null;
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(),sDate);
		
		StringBuilder sb = new StringBuilder();	  
		sb.append("<form id='timesheet_form' name='timesheet_form' enctype='multipart/form-data' >");
		sb.append("<div id='hideError' style='display:none;'></div>");
		sb.append("<div class='time_appr_side_wrap_inn'>");
	    sb.append("<div class='proj_close'  onClick='removeTimesheetPopup();' ><i></i></div>");
	    
		sb.append("<div class='timesheet_table' id='dynamic'>");
        sb.append("<table id='timesheet' cellpadding='0' cellspacing='0'>");
        sb.append("<tbody>");
      
        
        Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		startCal.setLenient(false);
		startCal.setTime(sDate);
        
		sb.append("	<tr>");
		sb.append("<th>Projects</th>");
        sb.append("<th> ");
        sb.append(" <table id='1' cellpadding='0' cellspacing='0'>");
        sb.append("<th>Tasks </th>");
        sb.append("<th>Mon<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,0).getTime())+"</i></th>"); 
        sb.append("<th>Tue<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,1).getTime())+"</i></th>"); 
        sb.append("<th>Wed<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,2).getTime())+"</i></th>"); 
        sb.append("<th>Thu<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,3).getTime())+"</i></th>"); 
        sb.append("<th>Fri<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,4).getTime())+"</i></th>"); 
        sb.append("<th>Sat<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,5).getTime())+"</i></th>"); 
        sb.append("<th>Sun<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,6).getTime())+"</i></th>");
        sb.append("         </table>");
        sb.append("        </th> ");
        sb.append("<th>Status</th>");
        sb.append("</tr>");	
        Date currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,0).getTime();
        Date currentEndDate   =   CalendarUtil.getDateIncrementByincrement(startCal,6).getTime();
		
        
        String approvalStatus = null;
			
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			
			employeeUserId = ts.getProjectMembers().getEmployee().getUser().getId();
			projectMemberId =ts.getProjectMembers().getId();
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForApprover(ts.getId(),user.getId());
			Submission_Status submissionStatus = sah.getSubmissionStatus();
			String submissionStatusStr = submissionStatus.getValue();
			
			//Date assignDate = ts.getProjectMembers().getProjectAssignedDate();
			HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(ts.getProjectMembers().getId(), currentStartDate, currentEndDate);
			String disbleCondition1 = "";
			 Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 currentCal.setLenient(false);
			 currentCal.setTime(currentStartDate);
			if(!pmhDateList.contains(currentStartDate) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition1 = "disabled";
			}
			String disbleCondition2 = "";
           if(!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition2 = "disabled";
			}
			String disbleCondition3 = "";
           if(!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition3 = "disabled";
			}
			String disbleCondition4 = "";
           if(!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition4 = "disabled";
			} 
			String disbleCondition5 = "";
           if(!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI").getTime()) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition5 = "disabled";
			}
			String disbleCondition6 = "";
           if(!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT").getTime()) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition6 = "disabled";
			}
			String disbleCondition7 = "";
           if(!pmhDateList.contains(currentEndDate) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition7 = "disabled";
			} 
			/*String disbleCondition = null;
			if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition = "disabled";
			}*/
			if(sah.getSubmissionApprovalStatus() != null){
				approvalStatus = sah.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}
			
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='timesheetrProject pname "+submissionStatus+"' id='"+ts.getId()+"'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
			sb.append("<td>");
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			 
			Collection<TimesheetEntries>  timesheetEntriesList =  getAllTimesheetEntriesBySubmissionId(ts.getId());
			
			if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
			
					Integer maxCount = timesheetEntriesList.size();
					Integer rowCount = 1;
					maxCount = maxCount+1;
					sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
					sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
					for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
					               
								   /*sb.append("<tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
					               sb.append("<td> <div> ");   
					               sb.append(" <input type='text' id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='inline_field_withoutMask' value='"+timesheetEntries.getProjectTask().getTaskName()+"'  />	"); 
					               sb.append(" </div> </td>");*/
						 
					               /*timesheet entries id*/
									  sb.append("<input type='hidden' name='"+rowCount+"submission"+ts.getId()+"timesheetEntriesId'   id='"+rowCount+"submission"+ts.getId()+"timesheetEntriesId'   value='"+timesheetEntries.getId()+"' />");
						              sb.append("       <tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
						              sb.append("                 <td>");
						              sb.append("                      <div> ");
						              
						              if(!isNullOrEmpty(approvalStatus) && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())){
							              if(timesheetEntries.getProjectTask() != null){
							            	  sb.append("<p>"+timesheetEntries.getProjectTask().getTaskName()+"</p>");
							              }
							              else{
							            	  sb.append("<p>No Data</p>");
							              }
						              }
						              else{
						              
							              sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id'  class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
									      
									      for(ProjectTasks pt   : ProjectTasksList){
									    	   if(timesheetEntries.getProjectTask() != null && pt.getId().equals(timesheetEntries.getProjectTask().getId())){
								    	    	   sb.append("<option value='"+pt.getId()+"' selected>"+pt.getTaskName()+"</option>");
								    	       }
								    	       else{
								    	    	   sb.append("<option value='"+pt.getId()+"'>"+pt.getTaskName()+"</option>");
								    	       }  
									      }
							              sb.append(" </select>"); 
							              if(rowCount == 1){
							             	  sb.append("<input type='button' value='' onClick='addTimeSheetRow(\""+ts.getId()+"\",\""+ts.getProjectMembers().getProjects().getId()+"\")' class='addtaskfield'/>");
							              }else{
							                  sb.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
							              }
						              }
							               
						              sb.append("                      </div>");
						              sb.append("                 </td> ");
						              
					               
								   sb.append("				<td> <div> ");   
					               sb.append(" <input type='text'  "+disbleCondition1+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"submission"+ts.getId()+"comment1Id' id='"+rowCount+"submission"+ts.getId()+"textArea1Id'>"+timesheetEntries.getMoncomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition2+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getTueHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"submission"+ts.getId()+"comment2Id' id='"+rowCount+"submission"+ts.getId()+"textArea2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
					 			   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text'"+disbleCondition3+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getWedHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"submission"+ts.getId()+"comment3Id' id='"+rowCount+"submission"+ts.getId()+"textArea3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>"); 
					               sb.append(" </div> "); 
					               sb.append(" </td>"); 
				
					     		   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getThuHours()+"' />"); 	
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getThucomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"submission"+ts.getId()+"comment4Id' id='"+rowCount+"submission"+ts.getId()+"textArea4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
					               sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition5+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getFriHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getFricomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"submission"+ts.getId()+"comment5Id' id='"+rowCount+"submission"+ts.getId()+"textArea5Id' >"+timesheetEntries.getFricomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append("<td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSatHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"submission"+ts.getId()+"comment6Id' id='"+rowCount+"submission"+ts.getId()+"textArea6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append("						<td> <div>"); 
					               sb.append(" <input type='text'  "+disbleCondition7+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
				            	   if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
					            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
					               }else{
					            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
					               } 
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"submission"+ts.getId()+"comment7Id' id='"+rowCount+"submission"+ts.getId()+"textArea7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
					               sb.append(" </tr> ");      
					               rowCount++;      
					}
			}
			else{
				
	    	    Integer maxCount = 1;
				Integer rowCount = 1;
				maxCount = maxCount+1;
				sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
				sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
				              sb.append("       <tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
				              sb.append("                 <td>");
				              sb.append("                      <div> ");
				              sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
						      
						      for(ProjectTasks pt   : ProjectTasksList){
						              sb.append("<option value='"+pt.getId()+"'>"+pt.getTaskName()+"</option>");   
						      }
				              sb.append(" </select>"); 
				              if(rowCount == 1){
				             	  sb.append("<input  type='button' value='' onClick='addTimeSheetRow(\""+ts.getId()+"\",\""+ts.getProjectMembers().getProjects().getId()+"\")' class='addtaskfield'/>");
				              }else{
				                  sb.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
				              }
				               
				              sb.append("                      </div>");
				              sb.append("                 </td> ");
							  sb.append("				<td> <div> ");   
				              sb.append(" <input type='text'  "+disbleCondition1+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              
				              if(isNullOrEmpty(disbleCondition1))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' id='"+rowCount+"submission"+ts.getId()+"textArea1Id' ></textarea></span></div>");
				              sb.append(" </div> </td>"); 
				              
							  sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition2))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id' id='"+rowCount+"submission"+ts.getId()+"textArea2Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append(" <td> <div>");  
				              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition3))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id' id='"+rowCount+"submission"+ts.getId()+"textArea3Id'></textarea></span></div>"); 
				              sb.append(" </div> "); 
				              sb.append(" </td>"); 
			
				    		  sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='' placeholder='HH:MM' />"); 	
				              sb.append(" <div class='timesheet_comm'>");
				              if(isNullOrEmpty(disbleCondition4))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id' id='"+rowCount+"submission"+ts.getId()+"textArea4Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
				              sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>");
				              if(isNullOrEmpty(disbleCondition5))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id' id='"+rowCount+"submission"+ts.getId()+"textArea5Id' ></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append("<td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition6))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'  id='"+rowCount+"submission"+ts.getId()+"textArea6Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append("						<td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition7))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' id='"+rowCount+"submission"+ts.getId()+"textArea7Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
				              sb.append(" </tr> ");      
				              /*sb.append(" </table>"); 
				              sb.append(" </td>   "); 
				              sb.append("</td> "); 
						      sb.append("<td class='pname'> <div>"+submissionStatus+"</div> </td>");*/
			}
			sb.append("</table>"); 
			sb.append(" </td>   ");
			sb.append("<td class='pname'> <div>"); 
            /*sb.append(" <input type='text' disabled  value='"+ts.getSubmissionStatus()+"'  class='inline_field_withoutMask' />");*/ 
            /*sb.append("<b class='proj_active'>'"+ts.getSubmissionStatus()+"'</b>"); */
            if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
	        	sb.append("<b>"+submissionStatus+"</b>");
	        }
            if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
	        	sb.append("<b class='proj_draft'>"+submissionStatus+"</b>");
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
	        	sb.append("<b class='proj_process'>"+submissionStatus+"</b></div> </td>");
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
	        	sb.append("<b class='proj_active'>"+submissionStatus+"</b></div> </td>");
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
	        	sb.append("<b class='proj_closed'>"+submissionStatus+"</b></div> </td>");
	        }
	        
            sb.append(" </div> </td> "); 
            
			}
			
			sb.append("   </tr>"); 	
			
			
			sb.append("	  <tr class='last_child'>"); 
			sb.append("    <td></td>"); 
			sb.append("    <td> "); 
			sb.append("    <table id='1' cellpadding='0' cellspacing='0'>"); 
			sb.append("     <td class='total_field'>Total</td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal1Id' name='rowTotal1Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal2Id' name='rowTotal2Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal3Id' name='rowTotal3Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal4Id' name='rowTotal4Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal5Id' name='rowTotal5Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal6Id' name='rowTotal6Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='rowTotal7Id' name='rowTotal7Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    </table>"); 
			sb.append("           </td> "); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' disabled id='grandTotal' name='grandTotal' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>    "); 
			sb.append("      </tr> "); 
			
			
			sb.append("            </tbody>");
			sb.append("     </table>");
			
			
			sb.append("<div class='timesheet_bot'>");
			sb.append(" 	<div class='timesheet_week_comm'>");
			TimesheetSubmissions tsFirst = timesheetSubmissionsList.iterator().next();
			if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
				sb.append("<div class='commentTime'>");
				sb.append("<textarea   name='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
				sb.append("</div>");
			}else{
				sb.append("<div class='commentTime'>");
				sb.append("<textarea   name='weekComment' placeholder='comments here...'></textarea>");
				sb.append("</div>");
			}
			
			
		/*	if(!isNullOrEmpty(tsFirst.getFileName())){
				sb.append("<strong>");
				
				sb.append("<i class='glyphicon glyphicon-save'></i>");
				sb.append(" <a class='downicon' href=\'"+sourceleadUrl+"/downloadDocument?submissionId="+tsFirst.getId()+"\'>");
				sb.append(" <input type='button' value='"+tsFirst.getFileName()+"'>");
				sb.append("</a>");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			    String startDate1  =  sdf.format(tsFirst.getStartDate());
			    String endDate1  =  sdf.format(tsFirst.getEndDate());
				
			    sb.append("<input class='dele' type='button'  onClick='deleteSubmissionDocument(\""+tsFirst.getId()+"\",\""+tsFirst.getProjectMembers().getEmployee().getId()+"\",\""+startDate1+"\",\""+endDate1+"\");'>");
				
				sb.append("</strong>");
			}else{
				sb.append("<span>");
				sb.append("<input type='file' name='docFile'>");
				sb.append("</span>");
			}
			*/
			
			//display multiple attachments
			StringBuilder documentBuffer = new StringBuilder();	
			List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(tsFirst.getId());
			documentBuffer.append("<input type='hidden' name='attcahmentMaxRowNumber'  id='attcahmentMaxRowNumber' value='1' />");
			documentBuffer.append("<div class='timesheetDocs'>"); 
				if(timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()){
					for(TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList){
						if(!isNullOrEmpty(tsd.getFileName())){
								documentBuffer.append("<strong>");
								documentBuffer.append(" <a class='downicon' href=\'"+sourceleadUrl+"/downloadDocument?submissionDocumentId="+tsd.getId()+"\'>");
								documentBuffer.append("<i class='glyphicon glyphicon-save'></i>");
								documentBuffer.append(" <input type='button' value='"+tsd.getFileName()+"'>");
								documentBuffer.append("</a>");
								

								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							    String startDate1  =  sdf.format(tsFirst.getStartDate());
							    String endDate1  =  sdf.format(tsFirst.getEndDate());
							    if(!isNullOrEmpty(approvalStatus) && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()) ){ 
								}else{
									documentBuffer.append("<input class='dele' type='button'  onClick='deleteSubmissionDocument(\""+tsd.getId()+"\",\""+tsFirst.getProjectMembers().getEmployee().getId()+"\",\""+startDate1+"\",\""+endDate1+"\");'>");
								}
							    
							    documentBuffer.append("</strong>");
						} 
					}
				} 
		
				if(!isNullOrEmpty(approvalStatus) && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()) ){ 
				}else{
	            	documentBuffer.append("<div class='commentFile'>"); 
					documentBuffer.append("<span>"); 
					documentBuffer.append("<table class='timesheetAttachment' id='attachmentTable' cellpadding='0' cellspacing='0'>");
							documentBuffer.append("<tr id='divAttachmentTsRow0'>");
							documentBuffer.append("<td><div id='0docFileuploadFileGroup'><input type='file' name='0docFile' id='0docFile' class='timesheetAttachmentValidation' ><i class='fa fa-plus' onClick='addAttachment()'></i></div></td> ");           
							documentBuffer.append("</tr>");
					documentBuffer.append("</table>");
					documentBuffer.append("</span>");
					documentBuffer.append("</div>");
				}
				documentBuffer.append("</div>"); 
				sb.append(documentBuffer);
		
			sb.append("<div class='clearfix'></div>");
			sb.append("</div>");
			 
			if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue()))){
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\",\"approver\",\""+format.format(CalendarUtil.getDateIncrementByincrement(startCal,0).getTime())+"\","+employeeUserId+","+projectMemberId+","+employeeId+");' value='Update' class='time_btn'>");
				   /*sb.append("        <input type='button' value='Clear' class='time_btn time_cancel'>");*/
				   sb.append("    </div>");
				   sb.append("</div>");
			}
			else if(!isNullOrEmpty(approvalStatus) && approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()) ){
				   sb.append("	<div class='clearfix'></div>");   
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("  </div>");
				   sb.append("</div>");
			}
			else{
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("	       <input type='button' name='submit'  onClick='saveMyTimesheet(\"Draft\",\"approver\",\""+format.format(CalendarUtil.getDateIncrementByincrement(startCal,0).getTime())+"\","+employeeUserId+","+projectMemberId+","+employeeId+");' value='Save' class='time_btn time_dra'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\",\"approver\",\""+format.format(CalendarUtil.getDateIncrementByincrement(startCal,0).getTime())+"\","+employeeUserId+","+projectMemberId+","+employeeId+");' value='Submit' class='time_btn'>");
				   sb.append("        <input type='button' onClick='clearTimesheet();' value='Clear' class='time_btn time_cancel'>");
				   
				   sb.append("");
				   
				   sb.append("    </div>");
				   sb.append("</div>");
			}
		  
			
		    sb.append("	<div class='clearfix'></div>");
		    sb.append("</div>");
			
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" </div>");	
			sb.append(" </form>");	
			return sb.toString();
	 
		
		
	}
	
	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save employee Timesheets at time sheet screen.
	 * this method is called  when click on save button at timesheet screen.
	 * 
	 * @param timesheetEntriesForm This is the first paramter to dynamicTimesheetpopUp method
	 * @param json This is the second paramter to dynamicTimesheetpopUp method
	 * @param request This is the third paramter to dynamicTimesheetpopUp method
	 * @param status This is the third paramter to dynamicTimesheetpopUp method
	 *  
	 *  
	 * @return string 
	 * 
	 * @throws JSONException
	 */
	
	@Override
	public String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm, String json,
			MultipartHttpServletRequest request, String status,String savingType) throws Exception {
		// TODO Auto-generated method stub
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	/*TimesheetEntriesForm tForm = gson.fromJson(json, TimesheetEntriesForm.class);*/
    	
		String result = "";
		String resultJson = null;
		
		String localsubmissionId = jsonObj.getString("submissionId");
	    localsubmissionId = localsubmissionId.replaceAll("\\[", "").replaceAll("\\]","");
	    String[] submissionIds = localsubmissionId.split(",");
	    Date startDate = new Date() ;
    	
		if(submissionIds != null && submissionIds.length>0){ 
			for(String submissionId : submissionIds){
				  				submissionId = submissionId.replace("\"", "");
				                Long total = 0l;
				                TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
				                startDate = timesheetSubmissions.getStartDate();
				                
				               /* if(status.equalsIgnoreCase("Draft")){
				                	sah.setSubmissionStatus(Submission_Status.DRAFT);
					    		}
					    		if(status.equalsIgnoreCase("Submit")){
					    			sah.setSubmissionStatus(Submission_Status.SUBMITTED);
					    			sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
					    		}*/
					    		
				                SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionLowLevelApproverHierarchyForEmployee(timesheetSubmissions.getId());
				                if(sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("draft"))  || sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("new")) || sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("rejected")) || (sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("submitted")) && "approver".equalsIgnoreCase(savingType)  ) ){
				                submissionApproverHierarchyService.updateSubmissionStatus(Long.parseLong(submissionId), status);	
					    		timesheetSubmissions.setWeekcomments(jsonObj.getString("weekComment"));
								
						    	timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
						    	
						    	//save  mutliple attachment in timesheet submissison attachment.
						    	
						    	String attcahmentMaxRowNumber = jsonObj.getString("attcahmentMaxRowNumber");
								int attcahmentMaxRowNumberCount = Integer.parseInt(attcahmentMaxRowNumber);
								int attachmentRowNum = 0;
								
						    	for (int a = 0; a < attcahmentMaxRowNumberCount; a++) {
									   
									   TimesheetSubmissionsDocuments tsd = new TimesheetSubmissionsDocuments();
									   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		                   		       final Map files = multiRequest.getFileMap();
		                   		       
		                   		       tsd.setTimesheetSubmissions(timesheetSubmissions);
		                               MultipartFile  multiFile = (MultipartFile)files.get(a+"docFile");
		                               if(multiFile != null && !multiFile.isEmpty()){
			                               tsd.setDocFile(multiFile.getBytes());
			                               tsd.setFileName(multiFile.getOriginalFilename());
			                               
			                               if(multiFile.getContentType()!=null){
			                            	   tsd.setFileContentType(multiFile.getContentType());
			                               }
		                               }
		                               
		                               tsd.setStatus(Constants.CANDIDATE_ACTIVE);
		                               tsd.setCreatedDate(commonUtil.currentDate());
		                               tsd.setCreatedBy(loginUser.getUserCode());
		                               tsd.setUpdatedDate(commonUtil.currentDate());
		                               tsd.setUpdatedBy(loginUser.getUserCode());
		                               
									   timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
						    	}
								
							   /*Iterator<String> itrator = request.getFileNames();
								while (itrator.hasNext()) {
						        MultipartFile multiFile = request.getFile(itrator.next());
						        if(null != multiFile.getContentType()){
						    	  try {
						    		    timesheetSubmissions.setFileName(multiFile.getOriginalFilename());
						    	   		timesheetSubmissions.setFileContentType(multiFile.getContentType());
						    	   		timesheetSubmissions.setDocFile(multiFile.getBytes());
							       }catch (Exception e) {
						    	      	 e.printStackTrace();
									    }
						        }
								} */
					    	
								String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber"+submissionId);
								int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
								int rowNum = 0;
								
								
								for (int c = 1; c < maxCount; c++) {
								   if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay1Id") ||
									   jsonObj.has(c+"submission"+submissionId+"timeSheetDay2Id") ||
									   jsonObj.has(c+"submission"+submissionId+"timeSheetDay3Id") ||
									   jsonObj.has(c+"submission"+submissionId+"timeSheetDay4Id") ||
								       jsonObj.has(c+"submission"+submissionId+"timeSheetDay5Id") ||
									   jsonObj.has(c+"submission"+submissionId+"timeSheetDay6Id") ||
									   jsonObj.has(c+"submission"+submissionId+"timeSheetDay7Id")){
										
										TimesheetEntries timesheetEntries = new TimesheetEntries();
										
										//if ((jsonObj.has(c+"submission"+submissionId+"timesheetEntriesId"))){
										if ((jsonObj.has(c+"submission"+submissionId+"timesheetEntriesId"))){
											String timesheetEntriesId = jsonObj.getString(c+"submission"+submissionId+"timesheetEntriesId");
											if(!isNullOrEmpty(timesheetEntriesId)){
												timesheetEntries.setId(Long.parseLong(timesheetEntriesId));
											}	
									    } 
										
										  
										timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
										timesheetEntries.setStartDate(commonUtil.currentDate());
										timesheetEntries.setEndDate(commonUtil.currentDate());
										timesheetEntries.setCreatedDate(commonUtil.currentDate());
										timesheetEntries.setUpdatedDate(commonUtil.currentDate());
										
										ProjectTasks projectTasks = new ProjectTasks();

									   if (jsonObj.has(c+"submission"+submissionId+"task1Id")){
										   if(!isNullOrEmpty(jsonObj.getString(c+"submission"+submissionId+"task1Id"))){
												  projectTasks.setId(Long.parseLong(jsonObj.getString(c+"submission"+submissionId+"task1Id")));
												  timesheetEntries.setProjectTask(projectTasks);
										   }
										 }
										//For Monday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay1Id")){
											String monHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay1Id");
											if(!isNullOrEmpty(monHours)){
												timesheetEntries.setMonHours(monHours);
												total = getTotalHoursFormDayHours(monHours, total);
											}else{
												timesheetEntries.setMonHours("00:00");
											}
										}
										//For Monday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment1Id")){
											timesheetEntries.setMoncomments(jsonObj.getString(c+"submission"+submissionId+"comment1Id"));
										}
										
										//For Tuesday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay2Id")){
											String tueHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay2Id");
											if(!isNullOrEmpty(tueHours)){
												timesheetEntries.setTueHours(tueHours);
												total = getTotalHoursFormDayHours(tueHours, total);
											}else{
												timesheetEntries.setTueHours("00:00");
											}
											
										}
										//For Tuesday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment2Id")){
											timesheetEntries.setTuecomments(jsonObj.getString(c+"submission"+submissionId+"comment2Id"));
										}
					                    
										//For wednesday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay3Id")){
											String wedHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay3Id");
											if(!isNullOrEmpty(wedHours)){
												timesheetEntries.setWedHours(wedHours);
												total = getTotalHoursFormDayHours(wedHours, total);
												
											}else{
												timesheetEntries.setWedHours("00:00");
											}
										}
										//For wednesday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment3Id")){
											timesheetEntries.setWedcomments(jsonObj.getString(c+"submission"+submissionId+"comment3Id"));
										}
										
										//For thursday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay4Id")){

											String thuHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id");
											if(!isNullOrEmpty(thuHours)){
												timesheetEntries.setThuHours(thuHours);
												total = getTotalHoursFormDayHours(thuHours, total);
												
											}else{
												timesheetEntries.setThuHours("00:00");
											}
										}
										 
										//For thursday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment4Id")){
											timesheetEntries.setThucomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
										}
										
										
										//For friday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay5Id")){
											String friHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay5Id");
											if(!isNullOrEmpty(friHours)){
												timesheetEntries.setFriHours(friHours);
												total = getTotalHoursFormDayHours(friHours, total);
											}else{
												timesheetEntries.setFriHours("00:00");
											}
										}
										 
										//For friday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment5Id")){
											timesheetEntries.setFricomments(jsonObj.getString(c+"submission"+submissionId+"comment5Id"));
										}
										
										
										//For saturday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay6Id")){
											String satHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay6Id");
											if(!isNullOrEmpty(satHours)){
												timesheetEntries.setSatHours(satHours);
												total = getTotalHoursFormDayHours(satHours, total);
											}else{
												timesheetEntries.setSatHours("00:00");
											}
										}
										//For saturday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment6Id")){
											timesheetEntries.setSatcomments(jsonObj.getString(c+"submission"+submissionId+"comment6Id"));
										}
										
										
										//For sunday Hours
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay7Id")){
											String sunHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay7Id");
											if(!isNullOrEmpty(sunHours)){
												timesheetEntries.setSunHours(sunHours);
												total = getTotalHoursFormDayHours(sunHours, total);
												
											}else{
												timesheetEntries.setSunHours("00:00");
											}
										}
										
										//For sunday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment7Id")){
											timesheetEntries.setSuncomments(jsonObj.getString(c+"submission"+submissionId+"comment7Id"));
										}
										rowNum++;
										timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
										
									}else{
										if ((jsonObj.has(c+"submission"+submissionId+"timesheetEntriesId"))){
											String timesheetEntriesId = jsonObj.getString(c+"submission"+submissionId+"timesheetEntriesId");
											timesheetEntriesDao.deleteTimesheetEntries(timesheetEntriesId);
										}
									}
								}
								if(total != null){
						            int actualHours = (int) (total / 60);
						            int actualMinutes =   (int)(total % 60);
						            String actualMinutesStr = ""+actualMinutes;
						            String startWithZero = "";
						            if(StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length()==1)
						            	startWithZero ="0";	
						            	
						            String actualTimeString =  actualHours+"."+startWithZero+actualMinutes;
						            
									timesheetSubmissions.setTotalHours(actualTimeString);
									timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
									
									//send mail to assigned manager at time sheet submission
									if(!actualTimeString.equalsIgnoreCase("0.0")){
										if(!"Draft".equalsIgnoreCase(status)){
											sendEmailToManagerAtTimesheetSubmissions(timesheetSubmissions,loginUser,loginOrgLocation,loginOrgCode);
										}
									}
								}
								
			          }	
								
					    	}
		} 
    	
		/*result =  "success";*/
		/*resultJson = toJSON(startDateResult);*/
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String startDateStr = format.format(startDate);
		return startDateStr;
		
	}
	
	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save timesheetEntries.
	 * 
	 * @param timesheetEntries This is the first paramter to addTimesheetEntries method
	 *  
	 * @return TimesheetEntries 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		return timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
	}
	
	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to update timesheetEntries.
	 * 
	 * @param timesheetEntries This is the first paramter to updateTimesheetEntries method
	 *  
	 * @return TimesheetEntries 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.updateTimesheetEntries(timesheetEntries);
	}
	
	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to getTimesheetEntries by id.
	 * 
	 * @param id This is the first paramter to getTimesheetEntries method
	 *  
	 * @return TimesheetEntries 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public TimesheetEntries getTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.getTimesheetEntries(id);
	}
	

	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to delete by id.
	 * 
	 * @param id This is the first paramter to deleteTimesheetEntries method
	 *  
	 * @return Boolean 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Boolean deleteTimesheetEntries(String id) throws Exception {
		Boolean status = timesheetEntriesDao.deleteTimesheetEntries(id);
		return status;
		// TODO Auto-generated method stub
	}

	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to getAllTimesheetEntries.
	 * 
	 *  
	 * @return List<TimesheetEntries> 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public List<TimesheetEntries> getAllTimesheetEntries() throws Exception {
		List<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntries();
		return list;
	}

	 /**
	 * @author naresh bolisetty and sarath kumar
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get all timesheets by id.
	 * 
	 * @param submissionId This is the first paramter to getAllTimesheetEntriesBySubmissionId method
	 *  
	 * @return Collection<TimesheetEntries> 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		// TODO Auto-generated method stub
		 
		Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
		return list;
		
	}
	
	private Long getTotalHoursFormDayHours(String dayHours,Long total){
				String[] parts = dayHours.split(":");
				String hours = parts[0]; 
				String min = parts[1];
				
				Long  localHours = Long.parseLong(hours) * 60;
				Long  localHoursMinute = Long.parseLong(min);
				Long  finalmin = localHours + localHoursMinute;
				total = total + finalmin;
				return total;
			
	}
	
	

	 /**
	 * @author  venkata kesav
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is used to send email to project managers .
	 * this method is called at saveTimesheet() method.
	 * 
	 * @param timesheetSubmissions This is the first paramter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginUser This is the second paramter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginOrgLocation This is the third paramter to sendEmailToManagerAtTimesheetSubmissions method
	 * @param loginOrgCode This is the third paramter to sendEmailToManagerAtTimesheetSubmissions method
	 *  
	 * @return void 
	 * 
	 * @throws NullPointer
	 */
	
	private void sendEmailToManagerAtTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions, User loginUser, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(timesheetSubmissions.getProjectMembers().getProjects().getId());
		
		
		
		for(ProjectApproverHierarchy approver : approverHierarchyList){
				
			if(!loginUser.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode()) && approver.getLevel() == Constants.LEVEL1){
				
			 User projectManager = userService.getUserById(approver.getApproverUserId().getId());
			 //Employees emp = employeesService.getEmployeeByUserId(loginUser.getId());
				
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(projectManager.getFirstName());
			 emailMessage.setLastName(projectManager.getLastName());
			 emailMessage.setToEmail(projectManager.getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
			 
			 String pattern = "dd-MM-yyyy";
	         SimpleDateFormat format = new SimpleDateFormat(pattern);
	         String startDateStr = format.format(timesheetSubmissions.getStartDate());
	         String endDateStr = format.format(timesheetSubmissions.getEndDate());

			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 //globalVarsMap.put("projectName", project.getProjectName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
			 globalVarsMap.put("timeFrequency",startDateStr+" to "+endDateStr);
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			 EmailRecipient emailRecipient = new EmailRecipient();
			 emailRecipient.setEmail(emailMessage.getToEmail());
			 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			 emailRecipientList.add(emailRecipient);
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
			 
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario);
			
		}
	}
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	

}
