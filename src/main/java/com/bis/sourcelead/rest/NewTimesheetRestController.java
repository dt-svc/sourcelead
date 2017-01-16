package com.bis.sourcelead.rest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	 
	@RequestMapping(value = "/newTimesheetDummy", method = RequestMethod.POST )   
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public @ResponseBody String newTimesheetDummy(HttpServletRequest request) throws Exception {
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
							       
		timesheetEntriesDiv.append("			<div class='timesheetNewTabs'> ");
		timesheetEntriesDiv.append("			<div class='project_side_wrap_inn_tabs'> ");
		timesheetEntriesDiv.append("			    <ul class='project-tabs-menu'> ");
		timesheetEntriesDiv.append("			        <li class='current'><a href='#tab-1'>Week 1</a></li> ");
		timesheetEntriesDiv.append("			        <li><a href='#tab-2'>Week 2</a></li> ");
		timesheetEntriesDiv.append("			        <li><a href='#tab-3'>Week 3</a></li> ");
		timesheetEntriesDiv.append("			        <li><a href='#tab-4'>Week 4</a></li> ");
		timesheetEntriesDiv.append("			        <li><a href='#tab-5'>Week 5</a></li> ");
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
		
		timesheetEntriesDiv.append("			    <div class='project_tabs'>");
		timesheetEntriesDiv.append("			        <div id='tab-1' class='project_tabs_inn'>");
		timesheetEntriesDiv.append("			        <div class='timesheet_tableNew'>");
		timesheetEntriesDiv.append("			        	<table id='timesheet' cellpadding='0' cellspacing='0'>");
		timesheetEntriesDiv.append("			            	<tbody>");
		timesheetEntriesDiv.append("			                	<tr>");
		timesheetEntriesDiv.append("			                        <th>Tasks </th>");
		timesheetEntriesDiv.append("			                        <th>Mon<i>09-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Tue<i>11-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Wed<i>12-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Thu<i>13-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Fri<i>14-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Sat<i>15-01-2017</i></th>");
		timesheetEntriesDiv.append("			                        <th>Sun<i>16-01-2017</i></th>");
		timesheetEntriesDiv.append("			                    </tr>");
		
		Integer maxCount = 0;
		Integer rowCount = 1;
		maxCount = maxCount+1;
		timesheetEntriesDiv.append("<input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='"+maxCount+"' />");
		/*timesheetEntriesDiv.append("  <input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='1' />");*/
		
		timesheetEntriesDiv.append("			                    <tr id='timesheetRow0'>");
		timesheetEntriesDiv.append("			                                         <td>");
		timesheetEntriesDiv.append("			                                              <div> ");
		timesheetEntriesDiv.append("			                                                <select>");
		timesheetEntriesDiv.append("			                                                  <option value='volvo'>Task1</option>");
		timesheetEntriesDiv.append("			                                                  <option value='saab'>Task2</option>");
		timesheetEntriesDiv.append("			                                                  <option value='mercedes'>Task3</option>");
		timesheetEntriesDiv.append("			                                                  <option value='audi'>Task4</option>");
		timesheetEntriesDiv.append("			                                                </select>");
		timesheetEntriesDiv.append("			                                                <input type='button' value='' onClick='addTimeSheetRow(1)' class='addtaskfield'/>");
		timesheetEntriesDiv.append("			                                              </div>");
		timesheetEntriesDiv.append("			                                         </td> ");
		timesheetEntriesDiv.append("													<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");	
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment1Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea name='"+rowCount+"comment1Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("													<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment2Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea name='"+rowCount+"comment2Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("			 										<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment3Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea name='"+rowCount+"comment3Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("			     										<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment4Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id'><textarea name='"+rowCount+"comment4Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("													<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea name='"+rowCount+"comment5Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("													<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment6Id\");'  ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id'><textarea name='"+rowCount+"comment6Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>");
		
		timesheetEntriesDiv.append("													<td> <div>");
		timesheetEntriesDiv.append("			                  <input type='text' id='REPLACEROWIDtimeSheetDay1Id' name='timeSheetDay1Id' class='inline_field' placeholder='HH:MM' />	");
		timesheetEntriesDiv.append("			                  <div class='timesheet_comm'><i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment7Id\");' ></i>");
		/*timesheetEntriesDiv.append("			                  <span id='teskcomment'><textarea></textarea></span></div>");*/
		timesheetEntriesDiv.append(" <span class='teskcomment' id='"+rowCount+"comment7Id'><textarea name='"+rowCount+"comment7Id' id='"+rowCount+"textArea1Id'></textarea></span></div>");
		timesheetEntriesDiv.append("			                  </div> </td>    ");     
		
		timesheetEntriesDiv.append("			                  </tr>");                                                        
		timesheetEntriesDiv.append("			                </tbody>");
		timesheetEntriesDiv.append("			            </table>");
		timesheetEntriesDiv.append("			        </div>");        	
		timesheetEntriesDiv.append("			        </div>");
		
		timesheetEntriesDiv.append("			  </div>");
							    
							  
					
							    
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
		timesheetEntriesDiv.append("		    <h2>45:30</h2>");	
		timesheetEntriesDiv.append("		    <span>This Week</span>");	
		timesheetEntriesDiv.append("		</div>   ");	       
		timesheetEntriesDiv.append("		    </div>");	
		timesheetEntriesDiv.append("		</div>  ");	
		timesheetEntriesDiv.append("		 </form> 	");	
		
		return timesheetEntriesDiv.toString();
	}
 
}