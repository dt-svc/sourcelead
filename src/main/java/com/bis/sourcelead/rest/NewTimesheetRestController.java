package com.bis.sourcelead.rest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
 

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
	
	 
	@RequestMapping(value = "/newTimesheetDummy", method = RequestMethod.POST )   
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public @ResponseBody String newTimesheetDummy(HttpServletRequest request,
			@RequestParam(value="weekStartDate", required=false) String weekStartDate,
			@RequestParam(value="weekEndDate", required=false) String weekEndDate) throws Exception {
		StringBuilder timesheetEntriesDiv = new StringBuilder();
		timesheetEntriesDiv.append("");
		
		
		timesheetEntriesDiv.append(" <form id='timesheet_form' name='timesheet_form'> ");
		
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
		timesheetEntriesDiv.append("		        	<input type='button' value='Draft' class='time_btn time_dra' />");	
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
			
		StringBuilder tabDiv = new StringBuilder();
				
		tabDiv.append("<div id='tab-1' class='project_tabs_inn'>");
		tabDiv.append("<div class='timesheet_tableNew'>");
		tabDiv.append("			        	<table id='timesheet' cellpadding='0' cellspacing='0'>");
		tabDiv.append("			            	<tbody>");
		tabDiv.append("			                	<tr>");
		tabDiv.append("			                        <th>Tasks </th>");
		tabDiv.append("			                        <th>Mon<i>09-01-2017</i></th>");
		tabDiv.append("			                        <th>Tue<i>11-01-2017</i></th>");
		tabDiv.append("			                        <th>Wed<i>12-01-2017</i></th>");
		tabDiv.append("			                        <th>Thu<i>13-01-2017</i></th>");
		tabDiv.append("			                        <th>Fri<i>14-01-2017</i></th>");
		tabDiv.append("			                        <th>Sat<i>15-01-2017</i></th>");
		tabDiv.append("			                        <th>Sun<i>16-01-2017</i></th>");
		tabDiv.append("			                    </tr>");
		
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
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");	
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment1Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea name='"+rowCount+"comment1Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay2Id' name='timeSheetDay2Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment2Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea name='"+rowCount+"comment2Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("			 										<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay3Id' name='timeSheetDay3Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment3Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea name='"+rowCount+"comment3Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("			     										<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay4Id' name='timeSheetDay4Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment4Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id'><textarea name='"+rowCount+"comment4Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay5Id' name='timeSheetDay5Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea name='"+rowCount+"comment5Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay6Id' name='timeSheetDay6Id' class='inline_field' placeholder='HH:MM' />	");
		tabDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment6Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id'><textarea name='"+rowCount+"comment6Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		tabDiv.append("			                  </div> </td>");
		
		tabDiv.append("													<td> <div>");
		tabDiv.append("			                  <input type='text' onblur='checkHours()' id='"+rowCount+"timeSheetDay7Id' name='timeSheetDay7Id' class='inline_field' placeholder='HH:MM' />	");
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
			
		
		
		
		tabDiv.append("			                </tbody>");
		tabDiv.append("			            </table>");
		tabDiv.append("</div>");        	
		tabDiv.append("</div>");
		
		return tabDiv;
	}
 
}