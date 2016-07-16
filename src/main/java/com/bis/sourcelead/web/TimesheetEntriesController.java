package com.bis.sourcelead.web;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
 

@RestController 
public class TimesheetEntriesController {
	
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	public ProjectTasksService projectTasksService;
	
	@Autowired
	public ProjectMembersService projectMembersService;
	
	@Autowired
	public TimesheetEntriesDao timesheetEntriesDao;
	
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public ProjectTasksService tasksService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	
	
	@RequestMapping(value = "/newTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String newTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		/*Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(projectMemberId, user.getId());*/
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions(new Date(), new Date(), user.getId());
		
		 StringBuilder sb1 = new StringBuilder();
		 
		  StringBuilder startDateBuilder = new StringBuilder();
		  StringBuilder endDateBuilder = new StringBuilder();
		 
		 String pattern = "dd-MM-yyyy";
          SimpleDateFormat format = new SimpleDateFormat(pattern);
		 if(startDate == null || startDate.equalsIgnoreCase("undefined") || startDate.isEmpty() ){  
		        startDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayForTimesheets("MON")));
		        endDateBuilder.append(format.format(CalendarUtil.getCurrentWeekDayForTimesheets("SUN")));
                 sb1.append("<th>Mon<i>"+startDateBuilder+"</i></th>"); 
                 sb1.append("<th>Tue<i>"+format.format(CalendarUtil.getCurrentWeekDayForTimesheets("TUE"))+"</i></th>"); 
                 sb1.append("<th>Wed<i>"+format.format(CalendarUtil.getCurrentWeekDayForTimesheets("WED"))+"</i></th>"); 
                 sb1.append("<th>Thu<i>"+format.format(CalendarUtil.getCurrentWeekDayForTimesheets("THU"))+"</i></th>"); 
                 sb1.append("<th>Fri<i>"+format.format(CalendarUtil.getCurrentWeekDayForTimesheets("FRI"))+"</i></th>"); 
                 sb1.append("<th>Sat<i>"+format.format(CalendarUtil.getCurrentWeekDayForTimesheets("SAT"))+"</i></th>"); 
                 sb1.append("<th>Sun<i>"+endDateBuilder+"</i></th>"); 
                 
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
		sb.append("           </li>");
		sb.append("       	<li><i class='glyphicon glyphicon-chevron-left' onclick='dynamicTimesheet(\"left\",\""+startDateBuilder+"\");'></i><span class='res_date'><i class='fa fa-calendar'></i>"+startDateBuilder+"-"+endDateBuilder+"</span>");
		sb.append("           <i class='glyphicon glyphicon-chevron-right' onclick='dynamicTimesheet(\"right\",\""+startDateBuilder+"\");'></i><abbr>|</abbr></li> ");               
		sb.append("           <div class='clearfix'></div>");
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
        sb.append("        <th>Total Hours</th>");
        sb.append("         </table>");
        sb.append("        </th> ");
        sb.append("</tr>");
		
		
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
		   	
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='pname'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
			sb.append("<td>");
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			 
			Collection<TimesheetEntries>  timesheetEntriesList =  timesheetEntriesService.getAllTimesheetEntriesBySubmissionId(ts.getId());
			Integer maxCount = timesheetEntriesList.size();
			Integer rowCount = 1;
			maxCount = maxCount+1;
			sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
			sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
			for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
			              sb.append("       <tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
			              sb.append("                 <td>");
			              sb.append("                      <div> ");
			              sb.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' >");
					      
					      for(ProjectTasks pt   : ProjectTasksList){
					              sb.append("<option value='"+pt.getId()+"'>"+pt.getTaskName()+"</option>");   
					      }
			              sb.append(" </select>"); 
			              if(rowCount == 1){
			             	  sb.append("<input type='button' value='' onClick='addTimeSheetRow(\""+ts.getId()+"\",\""+ts.getProjectMembers().getProjects().getId()+"\")' class='addtaskfield'/>");
			              }else{
			                  sb.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
			              }
			               
			              sb.append("                      </div>");
			              sb.append("                 </td> ");
						  sb.append("				<td> <div> ");   
			              sb.append(" <input type='text'   onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' >"+timesheetEntries.getMoncomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
						  sb.append(" <td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='"+timesheetEntries.getTueHours()+"' placeholder='HH:MM' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
						  sb.append(" <td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='"+timesheetEntries.getWedHours()+"' placeholder='HH:MM' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>"); 
			              sb.append(" </div> "); 
			              sb.append(" </td>"); 
		
			    		  sb.append(" <td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='"+timesheetEntries.getThuHours()+"' placeholder='HH:MM' />"); 	
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
			              sb.append(" <td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='"+timesheetEntries.getFriHours()+"' placeholder='HH:MM' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'>"+timesheetEntries.getFricomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
						  sb.append("<td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value='"+timesheetEntries.getSatHours()+"'  placeholder='HH:MM' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
						  sb.append("						<td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
			              sb.append(" <div class='timesheet_comm'>"); 
			              sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
			              sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>"); 
			              sb.append(" </div> </td>"); 
			              
						  sb.append("<td> <div>"); 
			              sb.append(" <input type='text' onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"total1Id' name='"+rowCount+"submission"+ts.getId()+"total1Id' class='inline_field' placeholder='HH:MM' />"); 	
			              sb.append(" </div> </td> ");                                         
			              sb.append(" </tr> ");      
			              rowCount++;      
			}
			sb.append("                </table>"); 
			}
			sb.append(" </td>   "); 
			sb.append("   </tr>"); 
			
			
			sb.append("	  <tr class='last_child'>"); 
			sb.append("    <td></td>"); 
			sb.append("    <td> "); 
			sb.append("    <table id='1' cellpadding='0' cellspacing='0'>"); 
			sb.append("     <td class='total_field'>Total</td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal1Id' name='rowTotal1Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal2Id' name='rowTotal2Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal3Id' name='rowTotal3Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal4Id' name='rowTotal4Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal5Id' name='rowTotal5Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal6Id' name='rowTotal6Id' class='inline_field' value='HH:MM' />"); 	
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal7Id' name='rowTotal7Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>"); 
			sb.append("    <td> <div>"); 
			sb.append("    <input type='text' id='rowTotal8Id' name='rowTotal8Id' class='inline_field' value='HH:MM' />	"); 
			sb.append("    </div> </td>    ");              
			sb.append("    </table>"); 
			sb.append("           </td> "); 
			sb.append("      </tr> "); 
				       
			
			sb.append("            </tbody>");
			sb.append("     </table>");
			
			
			
			sb.append("<div class='timesheet_bot'>");
			sb.append(" 	<div class='timesheet_week_comm'>");
			sb.append("     	<textarea name='weekComment' placeholder='comments here...'></textarea>");
			sb.append("         <span>");
			sb.append("         	<input type='file' name='docFile'><label><i class='glyphicon glyphicon-save'></i>Choose file</label>");
			sb.append("         </span>");
			sb.append("         <div class='clearfix'></div>");
			sb.append("     </div>");
		   sb.append("	<div class='timesheet_button'>");
		   sb.append("	       <input type='submit' onClick='saveMyTimesheet();'  name='Draft' vaue='Draft' class='time_btn time_dra'>");
		   sb.append("        <input type='button' onClick='saveMyTimesheet();' value='Submit' class='time_btn'>");
		   sb.append("        <input type='button' value='Clear' class='time_btn time_cancel'>");
		   sb.append("    </div>");
		   sb.append("	<div class='clearfix'></div>");
		   sb.append("</div>");
			
			sb.append(" </div>");
			sb.append(" </div>");
			return sb.toString();
	}
	
	
	@RequestMapping(value = "/dynamicTimesheetpopUp", method = RequestMethod.POST )
	public @ResponseBody String dynamicTimesheetpopUp(HttpServletRequest request,@RequestParam(value="employeeId", required=false) Long employeeId,
																				 @RequestParam(value="startDate", required=false) String startDate,
																				 @RequestParam(value="endDate", required=false) String endDate) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date sDate  =  Constants.DMY.parse(startDate);
		Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(employeeId, user.getId(),sDate);
		
		StringBuilder sb = new StringBuilder();	  
		sb.append("<form id='timesheet_form' name='timesheet_form' enctype='multipart/form-data' >");
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
        
		
		for(TimesheetSubmissions ts : timesheetSubmissionsList){
		    	
			sb.append("	<input type='hidden' name='submissionId'  value='"+ts.getId()+"'/>");
			sb.append("<tr id='timesheetRow1'>");
			sb.append("<td class='pname'> <div>"+ts.getProjectMembers().getProjects().getProjectName()+"</div> </td>");
			sb.append("<td>");
			
			Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
			 
			Collection<TimesheetEntries>  timesheetEntriesList =  timesheetEntriesService.getAllTimesheetEntriesBySubmissionId(ts.getId());
			Integer maxCount = timesheetEntriesList.size();
			Integer rowCount = 1;
			maxCount = maxCount+1;
			sb.append("<input type='hidden' name='maxRowNumber"+ts.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+ts.getId()+"'  value='"+maxCount+"' />");
			sb.append("<table id='table"+ts.getId()+"' cellpadding='0' cellspacing='0'>");
			for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
			               sb.append("<tr id='timesheetRow"+rowCount+"submission"+ts.getId()+"'>");
			               
			               sb.append("<td> <div> ");   
			               sb.append(" <input type='text'  disabled  id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='inline_field_withoutMask' value='"+timesheetEntries.getProjectTask().getTaskName()+"'  />	"); 
			               sb.append(" </div> </td>");
			               
						   sb.append("				<td> <div> ");   
			               sb.append(" <input type='text'  disabled  onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment1Id' >"+timesheetEntries.getMoncomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
						   sb.append(" <td> <div>"); 
			               sb.append(" <input type='text' disabled  onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getTueHours()+"' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment2Id'>"+timesheetEntries.getTuecomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
			 			   sb.append(" <td> <div>"); 
			               sb.append(" <input type='text' disabled onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getWedHours()+"' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment3Id'>"+timesheetEntries.getWedcomments()+"</textarea></span></div>"); 
			               sb.append(" </div> "); 
			               sb.append(" </td>"); 
		
			     		   sb.append(" <td> <div>"); 
			               sb.append(" <input type='text' disabled onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getThuHours()+"' />"); 	
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment4Id'>"+timesheetEntries.getThucomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
			               sb.append(" <td> <div>"); 
			               sb.append(" <input type='text' disabled onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getFriHours()+"' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea name='"+rowCount+"submission"+ts.getId()+"comment5Id'>"+timesheetEntries.getFricomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
						   sb.append("<td> <div>"); 
			               sb.append(" <input type='text' disabled onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSatHours()+"' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment6Id'>"+timesheetEntries.getSatcomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
						   sb.append("						<td> <div>"); 
			               sb.append(" <input type='text' disabled onkeypress='return isNumber(event)' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
			               sb.append(" <div class='timesheet_comm'>"); 
			               sb.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>"); 
			               sb.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea name='"+rowCount+"submission"+ts.getId()+"comment7Id' >"+timesheetEntries.getSuncomments()+"</textarea></span></div>"); 
			               sb.append(" </div> </td>"); 
			               
			               sb.append(" </tr> ");      
			               rowCount++;      
			}
			sb.append("</table>"); 
			sb.append(" </td>   ");
			sb.append("<td> <div>"); 
            sb.append(" <input type='text' disabled  value='"+ts.getSubmissionStatus()+"'  class='inline_field_withoutMask' />"); 	
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
			
			/*sb.append("<div class='timesheet_bot'>");
		    sb.append("	<div class='timesheet_button'>");
		    sb.append("	        <input type='submit' onClick='saveMyTimesheet();'  name='Draft' vaue='Draft' class='time_btn time_dra'>");
		    sb.append("        <input type='button' onClick='saveMyTimesheet();' value='Submit' class='time_btn'>");
		    sb.append("        <input type='button' value='Clear' class='time_btn time_cancel'>");
		    sb.append("    </div>");
		    sb.append("	<div class='clearfix'></div>");
		    sb.append("</div>");*/
			
			sb.append("<div class='timesheet_bot'>");
			sb.append(" 	<div class='timesheet_week_comm'>");
			TimesheetSubmissions tsFirst = timesheetSubmissionsList.iterator().next();
			sb.append("     	<textarea style='border: none;background: transparent;color: #333;disabled  name='weekComment' placeholder='comments here...'>"+tsFirst.getWeekcomments()+"</textarea>");
			sb.append("         <span>");
			sb.append("         	<a href='http://localhost:8080/sourcelead/downloadDocument?submissionId="+tsFirst.getId()+"'><i class='glyphicon glyphicon-save'></i> Download</a>");
			sb.append("         </span>");
			sb.append("         <div class='clearfix'></div>");
			sb.append("     </div>");
		    sb.append("	<div class='clearfix'></div>");
		    sb.append("</div>");
			
			sb.append(" </div>");
			sb.append(" </div>");
			sb.append(" </div>");	
			sb.append(" </form>");	
			return sb.toString();
	 }

	@RequestMapping(value = "/timesheet")
	public ModelAndView registration(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			
			Long userId = user.getId();
			java.util.List<Long> projectMemberIds =  projectMembersService.getAllProjectMembersIdsByemployeeId(userId);
			Collection<TimesheetSubmissions>  timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions(new Date(), new Date(), user.getId());
			for(TimesheetSubmissions ts : timesheetSubmissionsList){
					Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
					ts.setProjectTasksList(ProjectTasksList);
			}
			
			model.addObject("sourcelead_timesheetSubmissionsList", timesheetSubmissionsList);
			
			Collection<Projects> projectsList =   projectsService.getAllProjects();
			model.addObject("sourcelead_projectsList", projectsList);
			
			
			Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
			model.addObject("sourcelead_tasksList", tasksList);
			
			model.setViewName("timesheet/timesheet");
			return model;

	}
	

	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
			MultipartHttpServletRequest request) throws Exception {
											
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	TimesheetEntriesForm tForm = gson.fromJson(json, TimesheetEntriesForm.class);
    	
		String result = "";
		String resultJson = null;
    	
    	String[] submissionIds = tForm.getSubmissionId();
    	for(String submissionId : submissionIds){
    		
		    
	    	TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
	    	timesheetSubmissions.setId(Long.parseLong(submissionId)); 
	    	timesheetSubmissions.setSubmissionStatus(Submission_Status.DRAFT);
	    	timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
			    
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber"+submissionId);
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			int rowNum = 0;
			
			for (int c = 1; c < maxCount; c++) {
				if (isNullOrEmpty(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay1Id")))
					continue;
				
				TimesheetEntries timesheetEntries = new TimesheetEntries();
				timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
				timesheetEntries.setStartDate(new Date());
				timesheetEntries.setEndDate(new Date());
				timesheetEntries.setCreatedDate(new Date());
				timesheetEntries.setUpdatedDate(new Date());
				
				ProjectTasks projectTasks = new ProjectTasks();
				projectTasks.setId(Long.parseLong(jsonObj.getString(c+"submission"+submissionId+"task1Id")));
				timesheetEntries.setProjectTask(projectTasks);
				
				
			    timesheetEntries.setMonHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay1Id"));
				timesheetEntries.setMoncomments(jsonObj.getString(c+"submission"+submissionId+"comment1Id"));
				
				timesheetEntries.setTueHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay2Id"));
				timesheetEntries.setTuecomments(jsonObj.getString(c+"submission"+submissionId+"comment2Id"));

				timesheetEntries.setWedHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay3Id"));
				timesheetEntries.setWedcomments(jsonObj.getString(c+"submission"+submissionId+"comment3Id"));
				 
				timesheetEntries.setThuHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setThucomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				 
				timesheetEntries.setFriHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setFricomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				timesheetEntries.setSatHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setSatcomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				 
				timesheetEntries.setSunHours(jsonObj.getString(c+"submission"+submissionId+"timeSheetDay4Id"));
				timesheetEntries.setSuncomments(jsonObj.getString(c+"submission"+submissionId+"comment4Id"));
				
				
				timesheetEntries.setWeekcomments(jsonObj.getString("weekComment"));
				Iterator<String> itrator = request.getFileNames();
		        MultipartFile multiFile = request.getFile(itrator.next());
		        if(null != multiFile.getContentType()){
		    	  try {
		    		    timesheetEntries.setFileName(multiFile.getOriginalFilename());
		    	   		timesheetEntries.setFileContentType(multiFile.getContentType());
		    	   		timesheetEntries.setDocFile(multiFile.getBytes());
			       }catch (Exception e) {
		    	      	 e.printStackTrace();
					    }
		        }
				
				
				rowNum++;
				
				timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
				
			}
    	}
    	
		result =  "success";
		resultJson = toJSON(result);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/dynamicTimesheet", method = RequestMethod.GET )
	public @ResponseBody String dynamicTimesheet() throws Exception {
											 
	
	String result = "";
	result = result +"<table id='timesheet' cellpadding='0' cellspacing=0'>";
	result = result +"<tbody>";
    result = result +"<tr>";
    result = result +"<th>Projects</th>";
    result = result +"   <th> ";
    result = result +"    <table id='1' cellpadding='0' cellspacing='0'>";
    result = result +"        <th>Tasks </th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Mon<i>15-06-2016</i></th>";
    result = result +"   <th>Total Hours</th>";
    result = result +"    </table>";
    result = result +"   </th> ";
    result = result +"</tr>";
    
    Collection<Projects> projectsList =   projectsService.getAllProjects();
    for(Projects pro : projectsList){
    
      	result = result +"<input type='hidden' name='projectId'  value='"+pro.getId()+"'/>";	  
        result = result +"<tr id='timesheetRow1'>";
        result = result +"<td class='pname'> <div>"+pro.getProjectName()+"</div> </td>";
        result = result +"<td>";
        result = result +"<input type='hidden' name='maxRowNumber"+pro.getId()+"'  class='initiateTimesheetMax' id='maxRowNumber"+pro.getId()+"'  value='2' />";
        result = result +"<table id='table"+pro.getId()+"' cellpadding='0' cellspacing='0'>";
        result = result +"<tr id='timesheetRow1project"+pro.getId()+"'>";
        result = result +"<td>";
        result = result +"<div> ";
        result = result +"<select id='1project"+pro.getId()+"task1Id' name='1project"+pro.getId()+"task1Id' >";
        Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
        for(ProjectTasks task : tasksList){
        	   result = result +"<option value='"+task.getId()+"'>"+task.getTaskName()+"</option>";
        }
        result = result +"</select>";
        result = result +"<input type='button' value='' onClick='addTimeSheetRow("+pro.getId()+")' class='addtaskfield'/>";
        result = result +"</div>";
        result = result +"</td> ";
		result = result +"<td> <div> ";
		
		result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay1Id' name='1project"+pro.getId()+"timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1project"+pro.getId()+"comment1Id');'></i>";
        result = result +"  <span class='teskcomment' id='1project"+pro.getId()+"comment1Id'><textarea name='1project"+pro.getId()+"comment1Id' ></textarea></span></div>";
        result = result +"  </div> </td>";
        
        
       	result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay2Id' name='1project"+pro.getId()+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"  <div class='timesheet_comm'>";
        result = result +"  <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment('1project"+pro.getId()+"comment2Id');'></i>";
        result = result +"  <span class='teskcomment' id='1project"+pro.getId()+"comment2Id'><textarea name='1project"+pro.getId()+"comment2Id'></textarea></span></div>";
        result = result +"  </div> </td>";
                  
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay3Id' name='1project"+pro.getId()+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment3Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment3Id'><textarea name='1project"+pro.getId()+"comment3Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay4Id' name='1project"+pro.getId()+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment4Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment4Id'><textarea name='1project"+pro.getId()+"comment4Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay5Id' name='1project"+pro.getId()+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment5Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment5Id'><textarea name='1project"+pro.getId()+"comment5Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
        
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay6Id' name='1project"+pro.getId()+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment6Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment6Id'><textarea name='1project"+pro.getId()+"comment6Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";  
       
       
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"timeSheetDay7Id' name='1project"+pro.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment7Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment7Id'><textarea name='1project"+pro.getId()+"comment7Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>   ";   
                  
        
        result = result +"<td> <div>";
        result = result +"<input type='text' onkeypress='return isNumber(event)' id='1project"+pro.getId()+"total1Id' name='1project"+pro.getId()+"total1Id' class='inline_field' placeholder='HH:MM' />";	
        result = result +"<div class='timesheet_comm'>";
        result = result +"<i class='glyphicon glyphicon-comment' onclick='timesheetComment('1project"+pro.getId()+"comment8Id');'></i>";
        result = result +"<span class='teskcomment' id='1project"+pro.getId()+"comment8Id'><textarea name='1project"+pro.getId()+"comment8Id'></textarea></span></div>";
        result = result +"</div> ";
        result = result +"</td>";                                         
        result = result +" </tr>";                             
               
               
        result = result +"</table>";
        result = result +"</td>";   
        result = result +"</tr> ";
        
    }
    /*add total row*/   
    result = result +"</tbody>";
     result = result +"</table>";  
        
   return result;
	}

	
	@RequestMapping(value = "/dateFormat", method = RequestMethod.GET )
	public @ResponseBody void dateFormat() throws Exception {
		  
		
	}
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		
		ProjectTasks task = new ProjectTasks();
		/*Tasks task = new Tasks();
		task.setId(1l);
		timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("X");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad k");
		timesheetEntries.setUpdatedBy("naidu l");
		timesheetEntries.setUpdatedDate(new Date());*/
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries updateTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntries.setId(1l);
		
		ProjectTasks task = new ProjectTasks();
		task.setId(1l);
		
		/*timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("A");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad");
		timesheetEntries.setUpdatedBy("naidu");
		timesheetEntries.setUpdatedDate(new Date());*/
		
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = timesheetEntriesService.deleteTimesheetEntries(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
     
	
	 
	@RequestMapping(value = "/getSum", method = RequestMethod.GET)
	public @ResponseBody String getDetails(@RequestParam String valueOne)
			 
	{
		 System.out.println("hlo1");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	
	
	@RequestMapping(value = "/projectTasksById" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	
	@RequestMapping(value = "/getSum", method = RequestMethod.POST)
	public @ResponseBody String getDetailss(@RequestBody String valueOne)
			 
	{
		 System.out.println("hlo2");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

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
	
	/*Pavan Jampana : This method is for TimeSheet Pdf Download*/
	
	@RequestMapping(value = "/downloadTimesheetPdf", method = RequestMethod.GET)
	public ModelAndView generateAllTimesheetPdf(HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("redirect:/timesheetPdf/downloadPdf");
		return view;
	}
	
	
	/*shivayogi s kadagad*
	 * controller to download Exceltimesheet*/
	
	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public ModelAndView getExcel() throws Exception {
		
		Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService .getAllTimesheetEntries(); 		
		return new ModelAndView(timeSheetExcelView,"timesheetEntriesList",timesheetEntriesList);
	}

 
}