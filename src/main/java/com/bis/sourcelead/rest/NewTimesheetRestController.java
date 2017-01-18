package com.bis.sourcelead.rest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 

@RestController 
public class NewTimesheetRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public ProjectTasksService tasksService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	@Autowired
	private ExportTimesheetEntries exportTimesheetEntries;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeSheetPdfUtil timeSheetPdfUtil;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	@Autowired
	TimesheetSubmissionsDocumentsService TimesheetSubmissionsDocumentsService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	@RequestMapping(value = "/newTimesheetDummy", method = RequestMethod.POST )   
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public @ResponseBody String newTimesheetDummy(HttpServletRequest request,
			@RequestParam(value="weekStartDate", required=false) String weekStartDate,
			@RequestParam(value="weekEndDate", required=false) String weekEndDate) throws Exception {
		StringBuilder timesheetEntriesDiv = new StringBuilder();
		timesheetEntriesDiv.append("");
		
		
		timesheetEntriesDiv.append("<form id='timesheet_form' name='timesheet_form' enctype='multipart/form-data' >");
		
		timesheetEntriesDiv.append("		<div class='timesheet_out'> ");
		timesheetEntriesDiv.append("		<div class='timesheet_wrap'>");
		timesheetEntriesDiv.append("		<div class='data_loading'></div>");
		
		timesheetEntriesDiv.append("				<div class='timesheetNewHead'>");
		timesheetEntriesDiv.append("					<div class='timeNewSe'>");
		timesheetEntriesDiv.append("		                <select class='form-control chosen-select'>");
		timesheetEntriesDiv.append("		                  <option>Project 1</option>");
		timesheetEntriesDiv.append("		                  <option>Project 2</option>");
		timesheetEntriesDiv.append("		                  <option>Project 3</option>");
		timesheetEntriesDiv.append("		                  <option>Project 4</option>");
		timesheetEntriesDiv.append("		                  <option>Project 5</option>");
		timesheetEntriesDiv.append("		                </select>");
		timesheetEntriesDiv.append("		            </div>");
		
		timesheetEntriesDiv.append("					<div class='timeNewSe'>");
		timesheetEntriesDiv.append("		                <select class='form-control chosen-select'>");
		timesheetEntriesDiv.append("		                  <option>January - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>February - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>March - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>April - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>May - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>June - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>July - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>August - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>September - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>October - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>November - 2017</option>");
		timesheetEntriesDiv.append("		                  <option>December - 2017</option>");
		timesheetEntriesDiv.append("		                </select>");
		timesheetEntriesDiv.append("		            </div> ");
		timesheetEntriesDiv.append("					<div class='timeNewSe'>");
		timesheetEntriesDiv.append("						<b class='proj_active'>Active</b><i class='glyphicon glyphicon-comment com_active' >");
		timesheetEntriesDiv.append("							<div class='timeNewSeMess'><p>Rejected message here Rejected message here Rejected message here</p></div>");
		timesheetEntriesDiv.append("						</i>");
		timesheetEntriesDiv.append("					</div>");
		timesheetEntriesDiv.append("					<div class='clearfix'></div>   ");       
		timesheetEntriesDiv.append("				</div>  ");
							       
		
		HashMap<String,String> tabFrequency = new HashMap<>();
		tabFrequency.put("2-1", "8-1");
		tabFrequency.put("9-1", "15-1");
		tabFrequency.put("16-1", "22-1");
		tabFrequency.put("23-1", "29-1");
		tabFrequency.put("30-1", "31-1");
		StringBuilder tabData = new StringBuilder();
		
		timesheetEntriesDiv.append("			<div class='timesheetNewTabs'> ");
		timesheetEntriesDiv.append("			<div class='project_side_wrap_inn_tabs'> ");
		timesheetEntriesDiv.append("			    <ul class='project-tabs-menu'> ");
		Boolean firstTab = true;
		for (Map.Entry<String, String> entry : tabFrequency.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    if(weekStartDate != null && weekStartDate.equalsIgnoreCase(key)){  
		    	   timesheetEntriesDiv.append("			        <li class='current'><a href='#tab-1'>"+key+" To "+value+"</a></li> ");
		    	   firstTab = false;
		    }else{
		    	  if( ( null == weekStartDate || weekStartDate.isEmpty() )  && firstTab ){
		    		  timesheetEntriesDiv.append("			        <li class='current'><a href='#tab-1'>"+key+" To "+value+"</a></li> ");
		    		  firstTab = false;
		    	  }else{
		    		  timesheetEntriesDiv.append("			        <li><a href='#tab-1'>"+key+" To "+value+"</a></li> ");
		    	  }
		    	   
		    }
		}
		weekStartDate = "2017-09-01";
		tabData =	projectTabDataByDates(weekStartDate, weekEndDate);
		timesheetEntriesDiv.append("					<div class='export'> ");
		timesheetEntriesDiv.append("						<p><i class='glyphicon glyphicon-export'></i>Export<i class='fa fa-caret-down'></i></p> ");
		timesheetEntriesDiv.append("							<ul> ");
		timesheetEntriesDiv.append("			                	<li>PDF<i class='fa fa-file-pdf-o'></i></li> ");
		timesheetEntriesDiv.append("			                    <li>Excel<i class='fa fa-file-excel-o'></i></li> ");
		timesheetEntriesDiv.append("			                    <li>Word<i class='fa fa-file-word-o'></i></li> ");
		timesheetEntriesDiv.append("							</ul>			 ");
		timesheetEntriesDiv.append("					</div> ");
		timesheetEntriesDiv.append("			        <div class='clearfix'></div> ");
		timesheetEntriesDiv.append("			    </ul> "); 
		
		
		
		
		timesheetEntriesDiv.append("<div class='project_tabs'>");
		timesheetEntriesDiv.append(tabData);
		timesheetEntriesDiv.append("</div>");
							    
		timesheetEntriesDiv.append("					</div>	");
		timesheetEntriesDiv.append("					</div>");       
							       
							        
						
		timesheetEntriesDiv.append("<input type='hidden' name='attcahmentMaxRowNumber'  id='attcahmentMaxRowNumber' value='1' />");
		timesheetEntriesDiv.append("		    <div class='timesheet_bot'>");	
		timesheetEntriesDiv.append("		    	<div class='timesheet_week_comm'>");	
		
		/*timesheetEntriesDiv.append("		            <div class='commentFile'>");	
		timesheetEntriesDiv.append("		            	<span><input type='file' ><i class='fa fa-times'></i></span>");	
		timesheetEntriesDiv.append("		                <span><input type='file' ><i class='fa fa-plus'></i></span>");	
		timesheetEntriesDiv.append("		            </div>   ");*/	
		

		timesheetEntriesDiv.append("<div class='commentFile'>"); 
		timesheetEntriesDiv.append("<span>"); 
		timesheetEntriesDiv.append("<table class='timesheetAttachment' id='attachmentTable' cellpadding='0' cellspacing='0'>");
			timesheetEntriesDiv.append("<tr id='divAttachmentTsRow0'>");
			timesheetEntriesDiv.append("<td><div id='0docFileuploadFileGroup'><input type='file' name='0docFile' id='0docFile' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment()'></i></div></td> ");           
			timesheetEntriesDiv.append("</tr>");
		timesheetEntriesDiv.append("</table>");
		timesheetEntriesDiv.append("</span>");
		timesheetEntriesDiv.append("</div>");

		
		timesheetEntriesDiv.append("		            <div class='commentTime'><textarea placeholder='comments here...'></textarea></div>");	
		timesheetEntriesDiv.append("		            <div class='clearfix'></div>");	
		timesheetEntriesDiv.append("		        </div>");	
		timesheetEntriesDiv.append("		    	<div class='clearfix'></div>");	
		timesheetEntriesDiv.append("		    	<div class='timesheet_button'>");	
		timesheetEntriesDiv.append("		        	<input type='button' onClick='newSaveTimesheet(\"Draft\",\"user\");'  value='Draft' class='time_btn time_dra' />");	
		timesheetEntriesDiv.append("		            <input type='button' value='Submit' class='time_btn' />");	
		timesheetEntriesDiv.append("		            <input type='button' value='Clear' class='time_btn time_cancel' />");	
		timesheetEntriesDiv.append("		        </div>   ");	     
		timesheetEntriesDiv.append("		    </div>  ");	
		timesheetEntriesDiv.append("		<div class='res_total_hour'>");	
		timesheetEntriesDiv.append("			<i></i>");	
		timesheetEntriesDiv.append("			<p>Total hours</p>");	
		timesheetEntriesDiv.append("		    <h2> <input disabled type='text' id='grandTotal' name='grandTotal' class='inline_field' value='HH:MM' value='00:00' /> </h2>");	
		timesheetEntriesDiv.append("		    <span>This Week</span>");	
		timesheetEntriesDiv.append("		</div>   ");	       
		timesheetEntriesDiv.append("		    </div>");	
		timesheetEntriesDiv.append("		</div>  ");	
		timesheetEntriesDiv.append("		 </form> 	");	
		
		return timesheetEntriesDiv.toString();
	}
	
	
	public StringBuilder projectTabDataByDates(String startDate ,String endDate) throws Exception{
		
		Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		Boolean isExportMyTimeSheet = commonUtil.hasRole("ROLE_EXPORT_MY_TIMESHEETS");
		Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
			
		StringBuilder tabDiv = new StringBuilder();
				
		tabDiv.append("<div id='tab-1' class='project_tabs_inn'>");
		tabDiv.append("<div class='timesheet_tableNew'>");
		tabDiv.append("			        	<table id='timesheet' cellpadding='0' cellspacing='0'>");
		tabDiv.append("			            	<tbody>");
		tabDiv.append("			                	<tr>");
		tabDiv.append("			                        <th>Tasks </th>");
		
		tabDiv.append("			                        <th>Mon<i>09-01-2017</i></th>");
		String tuesDayDate =  getNextDate(startDate);
		tabDiv.append("			                        <th>Tue<i>").append(tuesDayDate).append("</i></th>");
		
		String WednesDayDate =  getNextDate(tuesDayDate);
		tabDiv.append("			                        <th>Wed<i>").append(WednesDayDate).append("</i></th>");
		
		String ThursDayDate =  getNextDate(WednesDayDate);
		tabDiv.append("			                        <th>Thu<i>").append(ThursDayDate).append("</i></th>");
		
		String FriDayDate =  getNextDate(ThursDayDate);
		tabDiv.append("			                        <th>Fri<i>").append(FriDayDate).append("</i></th>");
		
		String SaturdayDate =  getNextDate(FriDayDate);
		tabDiv.append("			                        <th>Sat<i>").append(SaturdayDate).append("</i></th>");
		
		String SundayDate =  getNextDate(FriDayDate);
		tabDiv.append("			                        <th>Sun<i>").append(SundayDate).append("</i></th>");
		
		tabDiv.append("			                    </tr>");
		
		
		 
		String disbleCondition1 = "";
		if(false){
			disbleCondition1 = "disabled";
		}
		String disbleCondition2 = "";
		if(false){
        	disbleCondition2 = "disabled";
		}
		String disbleCondition3 = "";
		if(false){
        	disbleCondition3 = "disabled";
		}
		String disbleCondition4 = "";
		if(false){
        	disbleCondition4 = "disabled";
		} 
		String disbleCondition5 = "";
		if(false){
        	disbleCondition5 = "disabled";
		}
		String disbleCondition6 = "";
		if(false){
        	disbleCondition6 = "disabled";
		}
		String disbleCondition7 = "";
		if(false){
        	disbleCondition7 = "disabled";
		} 
		
		TimesheetSubmissions ts = timesheetSubmissionsService.getTimesheetSubmissions("1");
		Collection<TimesheetEntries>  timesheetEntriesList =   timesheetEntriesDao.getAllTimesheetEntries();
		if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
					Integer maxCount = timesheetEntriesList.size();
					Integer rowCount = 1;
					tabDiv.append("<input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='"+maxCount+"' />");
					for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
						           
						          /*timesheet entries id*/
								  tabDiv.append("<input type='hidden' name='"+rowCount+"timesheetEntriesId'   id='"+rowCount+"timesheetEntriesId'   value='"+timesheetEntries.getId()+"' />");
								  tabDiv.append("       <tr id='timesheetRow"+maxCount+"'>");
								  tabDiv.append("                 <td>");
								  tabDiv.append("                      <div> ");
					              /*if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
					            	  if(timesheetEntries.getProjectTask() != null){
					            		  tabDiv.append("<p>").append(timesheetEntries.getProjectTask().getTaskName()).append("</p>");
					            	  }
					              }
					              else{*/
								  /*Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());*/
								  Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasks();
								  tabDiv.append("                       <select id='"+rowCount+"submission"+ts.getId()+"task1Id' name='"+rowCount+"submission"+ts.getId()+"task1Id' class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasksOnChange(\""+rowCount+"submission"+ts.getId()+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
								  tabDiv.append("<option value=''>--select task--</option>");
					              for(ProjectTasks pt   : ProjectTasksList){
									    	       if(timesheetEntries.getProjectTask() != null && pt.getId().equals(timesheetEntries.getProjectTask().getId())){
									    	    	   tabDiv.append("<option value='"+pt.getId()+"' selected>").append(pt.getTaskName()).append("</option>");
									    	       }
									    	       else{
									    	    	   if(isFillMyTimeSheet){
									    	    		   tabDiv.append("<option value='"+pt.getId()+"'>").append(pt.getTaskName()).append("</option>");
									    	    	   }
									    	       }
									                
									      }
					              tabDiv.append(" </select>"); 
							              if(isFillMyTimeSheet){
								              if(rowCount == 1){
								            	  tabDiv.append("<input type='button' value='' onClick='addTimeSheetRow(1)' class='addtaskfield'/>");
								              }else{
								            	  tabDiv.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
								              }
							              }   
					              /*}*/
					               
								  tabDiv.append("                      </div>");
					              tabDiv.append("                 </td> ");
								  tabDiv.append("				<td> <div> ");   
					              tabDiv.append(" <input type='text' "+disbleCondition1+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
					              tabDiv.append(" <div class='timesheet_comm'>"); 
				            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"submission"+ts.getId()+"comment1Id' >").append(timesheetEntries.getMoncomments()).append("</textarea></span></div>");
				            	  }else{
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment1Id\");'></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"submission"+ts.getId()+"comment1Id'  id='"+rowCount+"submission"+ts.getId()+"textArea1Id' ></textarea></span></div>");
				            	  }
				            	  tabDiv.append(" </div> </td>"); 
					              
				            	  tabDiv.append(" <td> <div>"); 
				            	  tabDiv.append(" <input type='text' "+disbleCondition2+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay2Id' class='inline_field' value='"+timesheetEntries.getTueHours()+"' placeholder='HH:MM' />	"); 
				            	  tabDiv.append(" <div class='timesheet_comm'>");
					              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
					            	  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"submission"+ts.getId()+"comment2Id'>").append(timesheetEntries.getTuecomments()).append("</textarea></span></div>");
					              }else{
					            	  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment2Id\");'></i>");
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"submission"+ts.getId()+"comment2Id' id='"+rowCount+"submission"+ts.getId()+"textArea2Id'></textarea></span></div>");
					              }
					              tabDiv.append(" </div> </td>"); 
					              
					              tabDiv.append(" <td> <div>"); 
					              tabDiv.append(" <input type='text' "+disbleCondition3+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay3Id' class='inline_field' value='"+timesheetEntries.getWedHours()+"' placeholder='HH:MM' />	"); 
					              tabDiv.append(" <div class='timesheet_comm'>"); 
					              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
					            	  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");  
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"submission"+ts.getId()+"comment3Id'>").append(timesheetEntries.getWedcomments()).append("</textarea></span></div>");
					              }else{
					            	  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment3Id\");'></i>");
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"submission"+ts.getId()+"comment3Id' id='"+rowCount+"submission"+ts.getId()+"textArea3Id'></textarea></span></div>");
					              }
					              tabDiv.append(" </div> "); 
					              tabDiv.append(" </td>"); 
				
					              tabDiv.append(" <td> <div>"); 
					    		  tabDiv.append(" <input type='text' "+disbleCondition4+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay4Id' class='inline_field' value='"+timesheetEntries.getThuHours()+"' placeholder='HH:MM' />"); 	
					    		  tabDiv.append(" <div class='timesheet_comm'>"); 
				            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>"); 
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"submission"+ts.getId()+"comment4Id'>").append(timesheetEntries.getThucomments()).append("</textarea></span></div>");
				            	  }
					              else{
					            	  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment4Id\");'></i>");
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"submission"+ts.getId()+"comment4Id' id='"+rowCount+"submission"+ts.getId()+"textArea4Id'></textarea></span></div>");
					              }
				            	  tabDiv.append(" </div> </td>"); 
					              
				            	  tabDiv.append(" <td> <div>"); 
				            	  tabDiv.append(" <input type='text'  "+disbleCondition5+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay5Id' class='inline_field' value='"+timesheetEntries.getFriHours()+"' placeholder='HH:MM' />	"); 
				            	  tabDiv.append(" <div class='timesheet_comm'>"); 
				            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"submission"+ts.getId()+"comment5Id'>").append(timesheetEntries.getFricomments()).append("</textarea></span></div>");
				            	  }else{
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment5Id\");' ></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"submission"+ts.getId()+"comment5Id' id='"+rowCount+"submission"+ts.getId()+"textArea5Id'></textarea></span></div>");
				            	  }
				            	  tabDiv.append(" </div> </td>"); 
					              
				            	  tabDiv.append("<td> <div>"); 
				            	  tabDiv.append(" <input type='text' "+disbleCondition6+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay6Id' class='inline_field' value='"+timesheetEntries.getSatHours()+"'  placeholder='HH:MM' />	"); 
				            	  tabDiv.append(" <div class='timesheet_comm'>"); 
				            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"submission"+ts.getId()+"comment6Id'>").append(timesheetEntries.getSatcomments()).append("</textarea></span></div>");
				            	  }else{
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment6Id\");'></i>");
					            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"submission"+ts.getId()+"comment6Id' id='"+rowCount+"submission"+ts.getId()+"textArea6Id'></textarea></span></div>");
				            	  }
				            	  tabDiv.append(" </div> </td>"); 
					              
				            	  tabDiv.append("						<td> <div>"); 
				            	  tabDiv.append(" <input type='text' "+disbleCondition7+"  onblur='checkHours()' id='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' name='"+rowCount+"submission"+ts.getId()+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
				            	  tabDiv.append(" <div class='timesheet_comm'>");
				            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"submission"+ts.getId()+"comment7Id' >").append(timesheetEntries.getSuncomments()).append("</textarea></span></div>");
				            	  }else{
				            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"submission"+ts.getId()+"comment7Id\");'></i>");
				            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"submission"+ts.getId()+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"submission"+ts.getId()+"comment7Id' id='"+rowCount+"submission"+ts.getId()+"textArea7Id'></textarea></span></div>");
				            	  }
				            	  tabDiv.append(" </div> </td>"); 
								                                         
				            	  tabDiv.append(" </tr> ");      
					              rowCount++;      
					     }
							/*tabDiv.append("</td> "); 
							tabDiv.append("<td class='pname last_td'> <div>");*/
						        /*if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
						        	tabDiv.append("<b>").append(submissionStatus).append("</b>");
						        }
						        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
						        	tabDiv.append("<b class='proj_draft'>").append(submissionStatus).append("</b>");
						        }
						        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
						        	tabDiv.append("<b class='proj_process'>").append(submissionStatus).append("</b>");
						        	if(isSah == null && isModifyMyTimeSheet){
						                String startDateStr = format.format(ts.getStartDate());
						                tabDiv.append("<b class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+ts.getId()+"\",\""+startDateStr+"\");' >Modify</b>");
						        	}
						        }
						        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
						        	tabDiv.append("<b class='proj_active'>").append(submissionStatus).append("</b>");
						        }
						        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
						        	tabDiv.append("<b class='proj_closed'>").append(submissionStatus).append("</b>");
						        	
						        	SubmissionApproverHierarchy submissionApproverHierarchy =  submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(ts.getId());
						        	tabDiv.append(" <i class='glyphicon glyphicon-comment rejectedHover' id='"+submissionApproverHierarchy.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+submissionApproverHierarchy.getId()+"\");'>");
						        	tabDiv.append(" <div class='app_error_mes' id='rejectedMessage"+submissionApproverHierarchy.getId()+"'>").append(submissionApproverHierarchy.getRejectedReason()).append("</div>");
						        	tabDiv.append(" </i>");
						        }*/
						      /*  tabDiv.append("</div> </td>");*/
			  }
		      else{
		
		Integer maxCount = 0;
		Integer rowCount = 1;
		maxCount = maxCount+1;
		tabDiv.append("<input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='"+maxCount+"' />");
		
		tabDiv.append("			                    <tr id='timesheetRow0'>");
		tabDiv.append("			                                         <td>");
		tabDiv.append("			                                              <div> ");
		
		Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId((long) 1);
		tabDiv.append("                       <select id='"+rowCount+"task1Id' name='"+rowCount+"task1Id' class='existedProjectTaskId' onChange='getProjectTasksOnChange()' >");
		tabDiv.append("<option value=''>--select task--</option>");
	    for(ProjectTasks pt   : ProjectTasksList){
	    	tabDiv.append("<option value='"+pt.getId()+"'>").append(pt.getTaskName()).append("</option>");
	    }
	    tabDiv.append(" </select>"); 
		/*tabDiv.append("			                                                <select>");
		tabDiv.append("			                                                  <option value='volvo'>Task1</option>");
		tabDiv.append("			                                                  <option value='saab'>Task2</option>");
		tabDiv.append("			                                                  <option value='mercedes'>Task3</option>");
		tabDiv.append("			                                                  <option value='audi'>Task4</option>");
		tabDiv.append("			                                                </select>");*/
		tabDiv.append("			                                                <input type='button' value='' onClick='addTimeSheetRow(1)' class='addtaskfield'/>");
		tabDiv.append("			                                              </div>");
		tabDiv.append("			                                         </td> ");
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay1Id' name='"+rowCount+"timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");	
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment1Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea name='"+rowCount+"comment1Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay2Id' name='"+rowCount+"timeSheetDay2Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment2Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea name='"+rowCount+"comment2Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("			 										<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay3Id' name='"+rowCount+"timeSheetDay3Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment3Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea name='"+rowCount+"comment3Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("			     										<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay4Id' name='"+rowCount+"timeSheetDay4Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment4Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id'><textarea name='"+rowCount+"comment4Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay5Id' name='"+rowCount+"timeSheetDay5Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea name='"+rowCount+"comment5Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay6Id' name='"+rowCount+"timeSheetDay6Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment6Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id'><textarea name='"+rowCount+"comment6Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay7Id' name='"+rowCount+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment7Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment7Id'><textarea name='"+rowCount+"comment7Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>    ");     
		
		tabDiv.append("			                  </tr>");   
		
		
		
		
		
		tabDiv.append("	  <tr class='last_child'>"); 
		tabDiv.append("    <td class='total_field'>Total</td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal1Id' name='rowTotal1Id' class='inline_field' value='HH:MM' />"); 	
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal2Id' name='rowTotal2Id' class='inline_field' value='HH:MM' />	"); 
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal3Id' name='rowTotal3Id' class='inline_field' value='HH:MM' />	"); 
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text'disabled  id='rowTotal4Id' name='rowTotal4Id' class='inline_field' value='HH:MM' />	"); 
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal5Id' name='rowTotal5Id' class='inline_field' value='HH:MM' />"); 	
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal6Id' name='rowTotal6Id' class='inline_field' value='HH:MM' />"); 	
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("    <td> <div>"); 
		tabDiv.append("    <input type='text' disabled id='rowTotal7Id' name='rowTotal7Id' class='inline_field' value='HH:MM' />	"); 
		tabDiv.append("    </div> </td>"); 
		tabDiv.append("      </tr> ");  
	}
		tabDiv.append("			                </tbody>");
		tabDiv.append("			            </table>");
		tabDiv.append("</div>");        	
		tabDiv.append("</div>");
		 
		return tabDiv;
	}
	
	
		
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/newSaveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
			
			MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status,String savingType) throws Exception {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			
			json = "{"+json+"}";
			JSONObject jsonObj = new JSONObject(json);
	    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    	
			String result = "";
			String resultJson = null;
			
			String	 submissionId = null;
			if (jsonObj.has("submissionId")){
				 submissionId = jsonObj.getString("submissionId");
			}
			
			 
			 Date startDate = new Date();
			/* String localsubmissionId = null;
		   	 try{
		   		   JSONArray jsonArray = jsonObj.getJSONArray("submissionId");
		   		localsubmissionId = jsonArray.toString();
		   	  }
		   	  catch(JSONException e){
		   		localsubmissionId= jsonObj.getString("submissionId");
		   	  }
			
		    localsubmissionId = localsubmissionId.replaceAll("\\[", "").replaceAll("\\]","");
		    String[] submissionIds = localsubmissionId.split(",");
		    Date startDate = new Date() ;*/
	    	
			/*if(submissionIds != null && submissionIds.length>0){ 
				for(String submissionId : submissionIds){*/
					
					  				/*submissionId = submissionId.replace("\"", "");*/
					                Long total = 0l;
					                TimesheetSubmissions timesheetSubmissions ;
					                if(!isNullOrEmpty(submissionId)){
					                	 timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
					                }else{
					                	 timesheetSubmissions = new TimesheetSubmissions();
					                	 timesheetSubmissions.setStartDate(startDate);
					                	 timesheetSubmissions.setEndDate(startDate);
					                	 ProjectMembers pm = new ProjectMembers();
					                	 pm.setId( (long) 1);
					                	 timesheetSubmissions.setProjectMembers(pm);
					                	 
					                	 timesheetSubmissions.setCreatedDate(new Date());
					                	 timesheetSubmissions.setCreatedBy("");
					                	 timesheetSubmissions.setUpdatedDate(new Date());
					                	 timesheetSubmissions.setUpdatedBy("");
					                	 
					                	 ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
					                	 List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(1);
					                	 HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
					                        if(hierarchyList != null && !hierarchyList.isEmpty()){
												for(ProjectApproverHierarchy hierarchy : hierarchyList){
													SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
													saHierarchy.setHeirarchyId(hierarchy);
													saHierarchy.setSubmissionId(timesheetSubmissions);
													saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
													saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
													saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
													saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
													saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
													saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
													saHierarchyList.add(saHierarchy);
												}
												timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
											}
											timesheetSubmissionsList.add(timesheetSubmissions);
											timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
											
					                	 
										}
					              
					               /* startDate = timesheetSubmissions.getStartDate();*/
						    		
					                /*SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionLowLevelApproverHierarchyForEmployee(timesheetSubmissions.getId());*/
					                /*if(sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("draft"))  || sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("new")) || sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("rejected")) || (sah.getSubmissionStatus().equals(Submission_Status.getNameByValue("submitted")) && "approver".equalsIgnoreCase(savingType)  ) ){*/
					               /* submissionApproverHierarchyService.updateSubmissionStatus(Long.parseLong(submissionId), status);	
						    		timesheetSubmissions.setWeekcomments(jsonObj.getString("weekComment"));*/
									
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
									
									String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber");
									int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
									int rowNum = 0;
									
									
									for (int c = 1; c <= maxCount; c++) {
									   if (jsonObj.has(c+"timeSheetDay1Id") ||
										   jsonObj.has(c+"timeSheetDay2Id") ||
										   jsonObj.has(c+"timeSheetDay3Id") ||
										   jsonObj.has(c+"timeSheetDay4Id") ||
									       jsonObj.has(c+"timeSheetDay5Id") ||
										   jsonObj.has(c+"timeSheetDay6Id") ||
										   jsonObj.has(c+"timeSheetDay7Id")){
											
											TimesheetEntries timesheetEntries = new TimesheetEntries();
											
											if ((jsonObj.has(c+"timesheetEntriesId"))){
												String timesheetEntriesId = jsonObj.getString(c+"timesheetEntriesId");
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
											if (jsonObj.has(c+"timeSheetDay1Id")){
												String monHours = jsonObj.getString(c+"timeSheetDay1Id");
												if(!isNullOrEmpty(monHours)){
													timesheetEntries.setMonHours(monHours);
													total = getTotalHoursFormDayHours(monHours, total);
												}else{
													timesheetEntries.setMonHours("00:00");
												}
											}
											//For Monday Comments
											if (jsonObj.has(c+"comment1Id")){
												timesheetEntries.setMoncomments(jsonObj.getString(c+"comment1Id"));
											}
											
											//For Tuesday Hours
											if (jsonObj.has(c+"timeSheetDay2Id")){
												String tueHours = jsonObj.getString(c+"timeSheetDay2Id");
												if(!isNullOrEmpty(tueHours)){
													timesheetEntries.setTueHours(tueHours);
													total = getTotalHoursFormDayHours(tueHours, total);
												}else{
													timesheetEntries.setTueHours("00:00");
												}
												
											}
											//For Tuesday Comments
											if (jsonObj.has(c+"comment2Id")){
												timesheetEntries.setTuecomments(jsonObj.getString(c+"comment2Id"));
											}
						                    
											//For wednesday Hours
											if (jsonObj.has(c+"timeSheetDay3Id")){
												String wedHours = jsonObj.getString(c+"timeSheetDay3Id");
												if(!isNullOrEmpty(wedHours)){
													timesheetEntries.setWedHours(wedHours);
													total = getTotalHoursFormDayHours(wedHours, total);
													
												}else{
													timesheetEntries.setWedHours("00:00");
												}
											}
											//For wednesday Comments
											if (jsonObj.has(c+"comment3Id")){
												timesheetEntries.setWedcomments(jsonObj.getString(c+"comment3Id"));
											}
											
											//For thursday Hours
											if (jsonObj.has(c+"timeSheetDay4Id")){

												String thuHours = jsonObj.getString(c+"timeSheetDay4Id");
												if(!isNullOrEmpty(thuHours)){
													timesheetEntries.setThuHours(thuHours);
													total = getTotalHoursFormDayHours(thuHours, total);
													
												}else{
													timesheetEntries.setThuHours("00:00");
												}
											}
											 
											//For thursday Comments
											if (jsonObj.has(c+"comment4Id")){
												timesheetEntries.setThucomments(jsonObj.getString(c+"comment4Id"));
											}
											
											
											//For friday Hours
											if (jsonObj.has(c+"timeSheetDay5Id")){
												String friHours = jsonObj.getString(c+"timeSheetDay5Id");
												if(!isNullOrEmpty(friHours)){
													timesheetEntries.setFriHours(friHours);
													total = getTotalHoursFormDayHours(friHours, total);
												}else{
													timesheetEntries.setFriHours("00:00");
												}
											}
											 
											//For friday Comments
											if (jsonObj.has(c+"comment5Id")){
												timesheetEntries.setFricomments(jsonObj.getString(c+"comment5Id"));
											}
											
											
											//For saturday Hours
											if (jsonObj.has(c+"timeSheetDay6Id")){
												String satHours = jsonObj.getString(c+"timeSheetDay6Id");
												if(!isNullOrEmpty(satHours)){
													timesheetEntries.setSatHours(satHours);
													total = getTotalHoursFormDayHours(satHours, total);
												}else{
													timesheetEntries.setSatHours("00:00");
												}
											}
											//For saturday Comments
											if (jsonObj.has(c+"comment6Id")){
												timesheetEntries.setSatcomments(jsonObj.getString(c+"comment6Id"));
											}
											
											
											//For sunday Hours
											if (jsonObj.has(c+"timeSheetDay7Id")){
												String sunHours = jsonObj.getString(c+"timeSheetDay7Id");
												if(!isNullOrEmpty(sunHours)){
													timesheetEntries.setSunHours(sunHours);
													total = getTotalHoursFormDayHours(sunHours, total);
													
												}else{
													timesheetEntries.setSunHours("00:00");
												}
											}
											
											//For sunday Comments
											if (jsonObj.has(c+"comment7Id")){
												timesheetEntries.setSuncomments(jsonObj.getString(c+"comment7Id"));
											}
											rowNum++;
											timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
											
										}else{
											if ((jsonObj.has(c+"timesheetEntriesId"))){
												String timesheetEntriesId = jsonObj.getString(c+"timesheetEntriesId");
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
										/*if(!actualTimeString.equalsIgnoreCase("0.0")){
											if(!"Draft".equalsIgnoreCase(status)){
												sendEmailToManagerAtTimesheetSubmissions(timesheetSubmissions,loginUser,loginOrgLocation,loginOrgCode);
											}
										}*/
									}
									
				         /* }	*/
				/*					
				 }
			} */
	    	
			/*result =  "success";*/
			/*resultJson = toJSON(startDateResult);*/
			String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
	        String startDateStr = format.format(startDate);
			return startDateStr;
			
		}
	
		public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
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

		
		public static String getNextDate(String  curDate) throws ParseException {
			  final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			  final Date date = format.parse(curDate);
			  final Calendar calendar = Calendar.getInstance();
			  calendar.setTime(date);
			  calendar.add(Calendar.DAY_OF_YEAR, 1);
			  return format.format(calendar.getTime()); 
		}
 
}