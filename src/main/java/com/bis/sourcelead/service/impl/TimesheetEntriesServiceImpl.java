package com.bis.sourcelead.service.impl;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Service
public class TimesheetEntriesServiceImpl implements TimesheetEntriesService {

	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	
	@Override
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		return timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
	}

	@Override
	public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.updateTimesheetEntries(timesheetEntries);
	}

	@Override
	public TimesheetEntries getTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.getTimesheetEntries(id);
	}

	@Override
	public Boolean deleteTimesheetEntries(String id) throws Exception {
		Boolean status = timesheetEntriesDao.deleteTimesheetEntries(id);
		return status;
		// TODO Auto-generated method stub
	}

	@Override
	public List<TimesheetEntries> getAllTimesheetEntries() throws Exception {
		// TODO Auto-generated method stub
		List<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntries();
		return list;
	}

	@Override
	public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		// TODO Auto-generated method stub
		 
		Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
		return list;
		
	}

	@Override
	public String newTimesheet(HttpServletRequest request,String navigation, String startDate) throws Exception {
		 
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		HashMap<String, Date> submissionDateRange =	timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(user.getId());
		Date employeeStartDate = submissionDateRange.get("STARTDATE");
		Date employeeEndDate = submissionDateRange.get("ENDDATE");
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = null;
		
		
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
        }
        else{
                
                 if(navigation.equalsIgnoreCase("left")){
                     
                     
                     Date sDate  =  Constants.DMY.parse(startDate);
	                 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					 startCal.setLenient(false);
					 startCal.setTime(sDate);
					 
						                
	                startDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime()));
			        endDateBuilder.append(format.format(CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime()));
	                sb1.append("<th>Mon<i>"+startDateBuilder+"</i></th>"); 
	                sb1.append("<th>Tue<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,-6).getTime())+"</i></th>"); 
	                sb1.append("<th>Wed<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,-5).getTime())+"</i></th>"); 
	                sb1.append("<th>Thu<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,-4).getTime())+"</i></th>"); 
	                sb1.append("<th>Fri<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,-3).getTime())+"</i></th>"); 
	                sb1.append("<th>Sat<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,-2).getTime())+"</i></th>"); 
	                sb1.append("<th>Sun<i>"+endDateBuilder+"</i></th>");
	                
	                timesheetSubmissionsList = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissions(CalendarUtil.getDateIncrementByincrement(startCal,-7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,-1).getTime(), user.getId());
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
	                sb1.append("<th>Mon<i>"+startDateBuilder+"</i></th>"); 
	                sb1.append("<th>Tue<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,8).getTime())+"</i></th>"); 
	                sb1.append("<th>Wed<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,9).getTime())+"</i></th>"); 
	                sb1.append("<th>Thu<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,10).getTime())+"</i></th>"); 
	                sb1.append("<th>Fri<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,11).getTime())+"</i></th>"); 
	                sb1.append("<th>Sat<i>"+format.format(CalendarUtil.getDateIncrementByincrement(startCal,12).getTime())+"</i></th>"); 
	                sb1.append("<th>Sun<i>"+endDateBuilder+"</i></th>"); 
	                
	                timesheetSubmissionsList = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissions(CalendarUtil.getDateIncrementByincrement(startCal,7).getTime(),CalendarUtil.getDateIncrementByincrement(startCal,13).getTime(), user.getId());
	                currentStartDate = CalendarUtil.getDateIncrementByincrement(startCal,7).getTime();
	                currentEndDate = CalendarUtil.getDateIncrementByincrement(startCal,13).getTime();
                 }
        }
        
		StringBuilder sb = new StringBuilder();	
		sb.append("	<div class='timesheet_head'>");
		sb.append("   	<ul>");
		sb.append("       	<li><i class='glyphicon glyphicon-print'></i></li>");
		sb.append("           <li><i class='glyphicon glyphicon-export'></i><span>Export</span><i class='glyphicon glyphicon-play' id='export-pop'></i>|");
		sb.append("           	<div class='export_pop'>");
		sb.append("               	<ul>");
		sb.append("                   	<li>PDF<i class='fa fa-file-pdf-o'></i></li>");
		sb.append("                       <li>Excel<i class='fa fa-file-excel-o'></i></li>");
		sb.append("                       <li>Word<i class='fa fa-file-word-o'></i></li>");
		sb.append("                   </ul>");
		sb.append("               </div>");
		sb.append("           </li><li>");
		if(employeeStartDate.before(currentStartDate)){
			sb.append("       	<i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDateBuilder+"\");'></i>");
		}
		sb.append("            <span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+" To "+endDateBuilder+"</span>");
		
		if(currentEndDate.before(employeeEndDate)){
			sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDateBuilder+"\");'></i><abbr>|</abbr> ");	
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
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
			
			
			Submission_Status submissionStatus = ts.getSubmissionStatus();
			Date assignDate = ts.getProjectMembers().getProjectAssignedDate();
			//CommonUtil
			//currentStartDate
			
			String submissionStatusStr = submissionStatus.getValue();
			String disbleCondition1 = null;
			 Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 currentCal.setLenient(false);
			 currentCal.setTime(currentStartDate);
			if(assignDate.after(currentStartDate) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition1 = "disabled";
			}
			String disbleCondition2 = null;
            if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE").getTime()) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition2 = "disabled";
			}
			String disbleCondition3 = null;
            if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED").getTime()) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition3 = "disabled";
			}
			String disbleCondition4 = null;
            if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU").getTime()) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition4 = "disabled";
			} 
			String disbleCondition5 = null;
            if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI").getTime()) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition5 = "disabled";
			}
			String disbleCondition6 = null;
            if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT").getTime()) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition6 = "disabled";
			}
			String disbleCondition7 = null;
            if(assignDate.after(currentEndDate) || !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
            	disbleCondition7 = "disabled";
			} 
			
			/*if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition = "disabled";
			}*/
			if(ts.getSubmissionApprovalStatus() != null){
				approvalStatus = ts.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}
			
		   	
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='pname'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
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
							              sb.append("<p>"+timesheetEntries.getProjectTask().getTaskName()+"</p>");
						              }
						              else{
						            	      sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' >");
										      for(ProjectTasks pt   : ProjectTasksList){
										    	       if(pt.getId() == timesheetEntries.getProjectTask().getId()){
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
						              if(isNullOrEmpty(disbleCondition1)){
						            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
							              }
						              }
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' >"+timesheetEntries.getMoncomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='"+timesheetEntries.getTueHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>");
						              if(isNullOrEmpty(disbleCondition2)){
							              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
							              }
						              }
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='"+timesheetEntries.getWedHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition3)){
							              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
							                  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");  
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
							              }
						              }
						               
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>"); 
						              sb.append(" </div> "); 
						              sb.append(" </td>"); 
					
						    		  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition4+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='"+timesheetEntries.getThuHours()+"' placeholder='HH:MM' />"); 	
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition4)){
						            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
							              }
							              else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
							              }
						              }
						             
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" <td> <div>"); 
						              sb.append(" <input type='text'  "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='"+timesheetEntries.getFriHours()+"' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition5)){
						            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
							              }
						              }
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'>"+timesheetEntries.getFricomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value='"+timesheetEntries.getSatHours()+"'  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition6)){
						            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
							              }
						              }
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("						<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
						              sb.append(" <div class='timesheet_comm'>");
						              if(isNullOrEmpty(disbleCondition7)){
						            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
							            	  sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
							              }else{
							            	  sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
							              }
						              }
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
									                                         
						              sb.append(" </tr> ");      
						              rowCount++;      
						     }
						        sb.append(" </table>"); 
						        sb.append("</td> "); 
						        sb.append("<td class='pname'> <div>");
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

                                          sb.append("     <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' >");
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
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' ></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition2))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append(" <td> <div>");  
						              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition3))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'></textarea></span></div>"); 
						              sb.append(" </div> "); 
						              sb.append(" </td>"); 
					
						    		  sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='' placeholder='HH:MM' />"); 	
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition4))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" <td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition5))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition6))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
									  sb.append("						<td> <div>"); 
						              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='' />	"); 
						              sb.append(" <div class='timesheet_comm'>"); 
						              if(isNullOrEmpty(disbleCondition7))
						              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
						              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' ></textarea></span></div>"); 
						              sb.append(" </div> </td>"); 
						              
						              sb.append(" </tr> ");      
						              sb.append(" </table>"); 
						              sb.append(" </td>   "); 
						              sb.append("</td> "); 
								      sb.append("<td class='pname'> <div> <b>"+submissionStatus+"</b></div> </td>");
								      
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
			
			TimesheetSubmissions tsFirst = timesheetSubmissionsList.iterator().next();
			if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
				sb.append("<textarea   style='border: none;background: transparent;color: #333;'   name='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
			}else{
				sb.append("<textarea   style='border: none;background: transparent;color: #333;'   name='weekComment' placeholder='comments here...'></textarea>");
			}
			
			/*sb.append("     	<textarea name='weekComment' placeholder='comments here...'></textarea>");*/
		
			
			if(!isNullOrEmpty(tsFirst.getFileName())){
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
			}else{
				sb.append("<span>");
				sb.append("<input type='file' name='docFile'><label><i class='glyphicon glyphicon-save'></i>Choose file</label>");
				sb.append("</span>");
			}
			
			sb.append("         <div class='clearfix'></div>");
			sb.append("     </div>");
			
			
			if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("        <input type='button' onClick='WITHDRAWMyTimesheet(\"Submit\");' value='withDraw' class='time_btn'>");
				   sb.append("  </div>");
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("</div>");
			}
			else{
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("	       <input type='button' name='submit'  onClick='saveMyTimesheet(\"Draft\");' value='Save' class='time_btn time_dra'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\");' value='Submit' class='time_btn'>");
				   sb.append("        <input type='button' onClick='clearTimesheet();'  value='Clear' class='time_btn time_cancel'>");
				   
				   sb.append("");
				   
				   sb.append("    </div>");
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("</div>");
			}
		  
			
			sb.append(" </div>");
			sb.append(" </div>");
			return sb.toString();
		}else{
			return "";
		}
	
		
	}
	

	@Override
	public String dynamicTimesheetpopUp(HttpServletRequest request, Long employeeId, String startDate, String endDate)
			throws Exception {
		
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(),sDate);
		
		StringBuilder sb = new StringBuilder();	  
		sb.append("<form id='timesheet_form' name='timesheet_form' enctype='multipart/form-data' >");
		sb.append("<div id='hideError' style='display:none;'></div>");
		sb.append("<div class='time_appr_side_wrap_inn'>");
	    sb.append("<div class='proj_close'><i class='glyphicon glyphicon-share-alt' onClick='removeTimesheetPopup();' ></i></div>");
	    
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
		    	
			Submission_Status submissionStatus = ts.getSubmissionStatus();
			String submissionStatusStr = submissionStatus.getValue();
			
			Date assignDate = ts.getProjectMembers().getProjectAssignedDate();
			String disbleCondition1 = null;
			 Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 currentCal.setLenient(false);
			 currentCal.setTime(currentStartDate);
			if(assignDate.after(currentStartDate) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition1 = "disabled";
			}
			String disbleCondition2 = null;
           if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition2 = "disabled";
			}
			String disbleCondition3 = null;
           if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition3 = "disabled";
			}
			String disbleCondition4 = null;
           if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU").getTime()) || (!isNullOrEmpty(submissionStatusStr) &&  submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition4 = "disabled";
			} 
			String disbleCondition5 = null;
           if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI").getTime()) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition5 = "disabled";
			}
			String disbleCondition6 = null;
           if(assignDate.after(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT").getTime()) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition6 = "disabled";
			}
			String disbleCondition7 = null;
           if(assignDate.after(currentEndDate) || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
           	disbleCondition7 = "disabled";
			} 
			/*String disbleCondition = null;
			if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())|| submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition = "disabled";
			}*/
			if(ts.getSubmissionApprovalStatus() != null){
				approvalStatus = ts.getSubmissionApprovalStatus().getValue();
			}
			else{
				approvalStatus = Submission_ApprovalStatus.getNameByValue("new").getValue();
			}
			
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='pname'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
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
							              sb.append("<p>"+timesheetEntries.getProjectTask().getTaskName()+"</p>");
						              }
						              else{
						              
							              sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' >");
									      
									      for(ProjectTasks pt   : ProjectTasksList){
									    	  if(pt.getId() == timesheetEntries.getProjectTask().getId()){
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
					               if(isNullOrEmpty(disbleCondition1)){
					            	   if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
						               }
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' >"+timesheetEntries.getMoncomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition2+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getTueHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition2)){
					            	   if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
						               }
					               }
					             
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
					 			   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text'"+disbleCondition3+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getWedHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition3)){
					            	   if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
						               }
					               }
					              
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>"); 
					               sb.append(" </div> "); 
					               sb.append(" </td>"); 
				
					     		   sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getThuHours()+"' />"); 	
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition4)){
					            	   if(!isNullOrEmpty(timesheetEntries.getThucomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
						               }
					               }
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
					               sb.append(" <td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition5+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getFriHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition5)){
					            	   if(!isNullOrEmpty(timesheetEntries.getFricomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
						               }
					               }
					             
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'>"+timesheetEntries.getFricomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append("<td> <div>"); 
					               sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSatHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition6)){
					            	   if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
						               }
					               }
					              
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>"); 
					               sb.append(" </div> </td>"); 
					               
								   sb.append("						<td> <div>"); 
					               sb.append(" <input type='text'  "+disbleCondition7+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
					               sb.append(" <div class='timesheet_comm'>"); 
					               if(isNullOrEmpty(disbleCondition7)){
					            	   if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
						            	   sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
						               }else{
						            	   sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
						               } 
					               }
					             
					               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>"); 
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
				              sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' >");
						      
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
				              
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>"); 
				              if(isNullOrEmpty(disbleCondition1)){
				            	  sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' ></textarea></span></div>");
				              }
				              sb.append(" </div> </td>"); 
				              
							  sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition2))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append(" <td> <div>");  
				              sb.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition3))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'></textarea></span></div>"); 
				              sb.append(" </div> "); 
				              sb.append(" </td>"); 
			
				    		  sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition4+" onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='' placeholder='HH:MM' />"); 	
				              sb.append(" <div class='timesheet_comm'>");
				              if(isNullOrEmpty(disbleCondition4))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
				              sb.append(" <td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>");
				              if(isNullOrEmpty(disbleCondition5))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append("<td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition6))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'></textarea></span></div>"); 
				              sb.append(" </div> </td>"); 
				              
							  sb.append("						<td> <div>"); 
				              sb.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='' />	"); 
				              sb.append(" <div class='timesheet_comm'>"); 
				              if(isNullOrEmpty(disbleCondition7))
				              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
				              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' ></textarea></span></div>"); 
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
			
		   /*	sb.append("<div class='timesheet_bot'>");
		    sb.append("	<div class='timesheet_button'>");
		    sb.append("        <input type='button' value='Edit' class='time_btn time_cancel'>");
		    sb.append("    </div>");
		    sb.append("	<div class='clearfix'></div>");
		    sb.append("</div>");*/
			
			sb.append("<div class='timesheet_bot'>");
			sb.append(" 	<div class='timesheet_week_comm'>");
			TimesheetSubmissions tsFirst = timesheetSubmissionsList.iterator().next();
			if(tsFirst.getWeekcomments() != null && !tsFirst.getWeekcomments().isEmpty()){
				sb.append("<textarea   style='border: none;background: transparent;color: #333;'   name='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
			}else{
				sb.append("<textarea   style='border: none;background: transparent;color: #333;'   name='weekComment' placeholder='comments here...'></textarea>");
			}
			
			
			if(!isNullOrEmpty(tsFirst.getFileName())){
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
				sb.append("<input type='file' name='docFile'><label><i class='glyphicon glyphicon-save'></i>Choose file</label>");
				sb.append("</span>");
			}
			
			sb.append("<div class='clearfix'></div>");
			sb.append("</div>");
			
			if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\");' value='Submit' class='time_btn'>");
				   /*sb.append("        <input type='button' value='Clear' class='time_btn time_cancel'>");*/
				   sb.append("    </div>");
				   sb.append("	<div class='clearfix'></div>");
				   sb.append("</div>");
			}
			else{
				   sb.append("	<div class='timesheet_button'>");
				   sb.append("	       <input type='button' name='submit'  onClick='saveMyTimesheet(\"Draft\");' value='Save' class='time_btn time_dra'>");
				   sb.append("        <input type='button' onClick='saveMyTimesheet(\"Submit\");' value='Submit' class='time_btn'>");
				   sb.append("        <input type='button' onClick='clearTimesheet();' value='Clear' class='time_btn time_cancel'>");
				   
				   sb.append("");
				   
				   sb.append("    </div>");
				   sb.append("	<div class='clearfix'></div>");
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
	
	@Override
	public String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm, String json,
			MultipartHttpServletRequest request, String status) throws Exception {
		// TODO Auto-generated method stub

		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	/*TimesheetEntriesForm tForm = gson.fromJson(json, TimesheetEntriesForm.class);*/
    	
		String result = "";
		String resultJson = null;
		
		String localsubmissionId = jsonObj.getString("submissionId");
	    localsubmissionId = localsubmissionId.replaceAll("\\[", "").replaceAll("\\]","");
	    String[] submissionIds = localsubmissionId.split(",");
    	
		if(submissionIds != null && submissionIds.length>0){ 
			for(String submissionId : submissionIds){
				  				submissionId = submissionId.replace("\"", "");
				                Long total = 0l;
					    		TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
					    		if(status.equalsIgnoreCase("Draft")){
					    			timesheetSubmissions.setSubmissionStatus(Submission_Status.DRAFT);
					    		}
					    		if(status.equalsIgnoreCase("Submit")){
					    			timesheetSubmissions.setSubmissionStatus(Submission_Status.SUBMITTED);
					    			timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
					    		}
					    		
					    		timesheetSubmissions.setWeekcomments(jsonObj.getString("weekComment"));
								Iterator<String> itrator = request.getFileNames();
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
								}
						    	timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
								    
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
										timesheetEntries.setStartDate(new Date());
										timesheetEntries.setEndDate(new Date());
										timesheetEntries.setCreatedDate(new Date());
										timesheetEntries.setUpdatedDate(new Date());
										
										ProjectTasks projectTasks = new ProjectTasks();
										projectTasks.setId(Long.parseLong(jsonObj.getString(c+"submission"+submissionId+"task1Id")));
										timesheetEntries.setProjectTask(projectTasks);
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
										if (jsonObj.has(c+"submission"+submissionId+"timeSheetDay6Id")){
											String sunHours = jsonObj.getString(c+"submission"+submissionId+"timeSheetDay7Id");
											if(!isNullOrEmpty(sunHours)){
												timesheetEntries.setSunHours(sunHours);
												total = getTotalHoursFormDayHours(sunHours, total);
												
											}else{
												timesheetEntries.setSunHours("00:00");
											}
										}
										
										//For sunday Comments
										if (jsonObj.has(c+"submission"+submissionId+"comment6Id")){
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
						            String actualTimeString =  actualHours+"."+actualMinutes;
						            
									timesheetSubmissions.setTotalHours(actualTimeString);
									timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
								}
								
								
					    	}
		} 
    	
		result =  "success";
		resultJson = toJSON(result);
		return resultJson;
		
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
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private String toJSON(Object obj){
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer writer) {
	            return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
	    return xstream.toXML(obj);
	}

}
