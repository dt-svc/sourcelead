package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ExportTimesheetEntriesImpl implements ExportTimesheetEntries{
	private static final Logger logger = LoggerFactory.getLogger(ExportTimesheetEntriesImpl.class);
	
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

	@Override
	public void exportTimesheets(User user, String navigation, String startDate,OrgLocations orgLocations) throws Exception {

		
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(user.getFirstName() + "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(user.getId(),orgLocations.getId());
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
			    	  startCal.setTime(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(endCal,"MON").getTime());
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
			   startCal.setTime(sDate);
		  }
		   String weekStartDate = format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"MON").getTime());
		   String weekEndDate = format.format(CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(startCal,"SUN").getTime());
		   createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,weekStartDate,weekEndDate,user);
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
		sb.append("                   	<li onclick='exportPdf();'>PDF<i class='fa fa-file-pdf-o'></i></li>");
		sb.append("                       <li onclick='exportExcel("+startDate+");'>Excel<i class='fa fa-file-excel-o'></i></li>");
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
        int column =0;
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			writeRow = createExcelsheet.createRow(column+9);
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
			/*ram0*/
			if(ts.getProjectMembers() != null && ts.getProjectMembers().getProjects() != null){
				 writeRow.createCell(0).setCellValue(ts.getProjectMembers().getProjects().getProjectName()); 
			}
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
						            	  /*ram1*/
						            	logger.info("--------------------"+timesheetEntries.getProjectTask().getTaskName());
						            	  if(timesheetEntries.getProjectTask() != null ){
						     				 writeRow.createCell(1).setCellValue(timesheetEntries.getProjectTask().getTaskName()); 
						     			}
						            	  }
						              }
						              else{
						            	      sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
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
						              /*ram2*/
						              if(timesheetEntries.getMonHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getMonHours())){
						            	  writeRow.createCell(2).setCellValue(timesheetEntries.getMonHours());   
						              }
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
						              /*ram3*/
						              if(timesheetEntries.getTueHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getTueHours())){
						            	  writeRow.createCell(3).setCellValue(timesheetEntries.getTueHours());   
						              }
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
						             /*ram4*/
						              if(timesheetEntries.getWedHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getWedHours())){
						            	  writeRow.createCell(4).setCellValue(timesheetEntries.getWedHours());   
						              }
						              
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
						              /*ram5*/
						              if(timesheetEntries.getThuHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getThuHours())){
						            	  writeRow.createCell(5).setCellValue(timesheetEntries.getThuHours());   
						              }
						              
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
						              /*ram6*/
						              if(timesheetEntries.getFriHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getFriHours())){
						            	  writeRow.createCell(6).setCellValue(timesheetEntries.getFriHours());   
						              }
						              
						              
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
						             
						              /*ram7*/
						              if(timesheetEntries.getSatHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSatHours())){
						            	  writeRow.createCell(6).setCellValue(timesheetEntries.getSatHours());   
						              } 
						              
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
						              /*ram8*/
						              if(timesheetEntries.getSunHours() !=null && !"".equalsIgnoreCase(timesheetEntries.getSunHours())){
						            	  writeRow.createCell(8).setCellValue(timesheetEntries.getSunHours());   
						              } 
						              
						              
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
							        	 /*ram9*/
							            	  writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        	sb.append("<b>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
							        	 /*ram9*/
						            	  writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        	sb.append("<b class='proj_draft'>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
							        	 /*ram9*/
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        	sb.append("<b class='proj_process'>"+submissionStatus+"</b>");
							        	if(isSah == null ){
							        		writeRow.createCell(9).setCellValue(submissionStatusStr);   
							                String startDateStr = format.format(ts.getStartDate());
							        		sb.append("<b class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+ts.getId()+"\",\""+startDateStr+"\");' >withdraw</b>");
							        	}
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);   
							        	sb.append("<b class='proj_active'>"+submissionStatus+"</b>");
							        }
							        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
							        	writeRow.createCell(9).setCellValue(submissionStatusStr);   
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
						            	  
                                          sb.append("     <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
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
								        	    sb.append("<b class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+ts.getId()+"\",\""+startDateStr+"\");' >withdraw</b>");
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
			column++;
			}
		
		
		File file = new File("/root/Desktop/PDF_FILE/timesheet.xls");
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
				sb.append("<div class='commentTime'>");
				sb.append("<textarea  name='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
				sb.append("</div>");
			}else{
				sb.append("<div class='commentTime'>");
				sb.append("<textarea   name='weekComment' placeholder='comments here...'></textarea>");
				sb.append("</div>");
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
										documentBuffer.append("<input class='dele' type='button' onClick='deleteSubmissionDocument(\""+tsd.getId()+"\");' >");
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
								documentBuffer.append("<td><input type='file' name='0docFile' id='0docFile'><i class='fa fa-plus' onClick='addAttachment()'></i></td> ");           
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
//			return sb.toString();
		}else{
//			return "";
			logger.info("******************************************empty");
		}
	}
	
	 /**
		 * @author Rajashekar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * This method is used to get all timesheets by id.
		 * @param submissionId This is the first paramter to getAllTimesheetEntriesBySubmissionId method
		 * @return Collection<TimesheetEntries> 
		 * @throws NullPointer
		 */
    public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
		return list;
		
	}
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, String weekStartDate, String weekEndDate, User user) {

		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
         font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(1);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell1 = writeRow.createCell(0);
		 cell1.setCellValue("Name :");
		 cell1.setCellStyle(style);
		 
		 Cell cell2 = writeRow.createCell(1);
		 cell2.setCellValue(user.getFirstName());
		 cell2.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(2);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Email Id :");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
		 cell4.setCellValue(user.getEmail());
		 cell4.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Week : ");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
		 cell6.setCellValue(weekStartDate+"-"+weekEndDate); 
		 cell6.setCellStyle(style);
		 
		return createExcelsheet = setExcelHeader(createExcelsheet,style,createExcelworkbook);
	}

	private Sheet setExcelHeader(Sheet createExcelsheet, CellStyle style, Workbook wb) {
		String headers [] = Constants.TIMESHEET_EXCEL_HEADERS;
		Row excelHeader = createExcelsheet.createRow(8);
		 for (int i=0; i<headers.length;i++){
		         Cell cell = excelHeader.createCell(i);
		         cell.setCellValue(headers[i]);
		            Font font = wb.createFont();
		            font.setColor(HSSFColor.BLUE.index);
		            style.setFont(font);
		            cell.setCellStyle(style);
//		         cell.setCellStyle(style);
		     }
		return createExcelsheet;
	}
}
