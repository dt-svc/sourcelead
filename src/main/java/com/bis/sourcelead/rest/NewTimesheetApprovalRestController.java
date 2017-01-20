package com.bis.sourcelead.rest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
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
import com.bis.sourcelead.util.TimeSheetPdfUtil;
 

@RestController 
public class NewTimesheetApprovalRestController {
	
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
	
	 
	@RequestMapping(value = "/timesheetApprovalDiv", method = RequestMethod.POST )   
	public @ResponseBody String timesheetApprovalDiv(HttpServletRequest request) throws Exception {
			 
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		timesheetApprovalDiv.append("");
		
		timesheetApprovalDiv.append("		<div class='timesheet_out'> ");
		timesheetApprovalDiv.append("		<div class='timesheet_appr_wrap'>");
		    timesheetApprovalDiv.append("			<div class='timesheetNewHead'>");
		
			timesheetApprovalDiv.append("				<div class='timeNewSe'>");
			timesheetApprovalDiv.append("	                <select class='form-control chosen-select' onChange='timesheetApprovalDivByProject();'>");
			timesheetApprovalDiv.append("	                  <option>Select Project</option>");
			timesheetApprovalDiv.append("	                  <option>Project 1</option>");
			timesheetApprovalDiv.append("	                  <option>Project 2</option>");
			timesheetApprovalDiv.append("	                  <option>Project 3</option>");
			timesheetApprovalDiv.append("	                  <option>Project 4</option>");
			timesheetApprovalDiv.append("	                </select>");
			timesheetApprovalDiv.append("	            </div>");
			
			timesheetApprovalDiv.append("				<div id='timesheetApprovalDivByProject'>");
				StringBuilder frequencyDiv = timesheetApprovalDivByProject("1");
				timesheetApprovalDiv.append(frequencyDiv);
			timesheetApprovalDiv.append("	</div>");
		
		timesheetApprovalDiv.append("	</div>");
		timesheetApprovalDiv.append("	</div>");
		
		return timesheetApprovalDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetApprovalDivByProject", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetApprovalDivByProject(HttpServletRequest request,
			@RequestParam(value="projectId", required=false) String projectId) throws Exception {
			
			StringBuilder projectDiv = timesheetApprovalDivByProject("1");
		    return projectDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetApprovalDivByFrequency", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetApprovalDivByFrequency(HttpServletRequest request,
			@RequestParam(value="frequency", required=false) String frequency ) throws Exception {
			
		StringBuilder frequencyBasedDiv =  timesheetApprovalDivByFrequnecy("28/11/2016", "28/11/2016");
		return frequencyBasedDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetApprovalDivByStatus", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetApprovalDivByStatus(HttpServletRequest request,
		   @RequestParam(value="status", required=false) String status) throws Exception {
			 
			
			StringBuilder tabDiv = timesheetApprovalDivByStatus("new");
		    return tabDiv.toString();
	}
	
	public StringBuilder timesheetApprovalDivByProject(String projectId) throws Exception{
		
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		
		
				timesheetApprovalDiv.append("				<div class='timeNewSe timeNewSe_a'>");
				timesheetApprovalDiv.append("	                <select class='form-control chosen-select' onChange='timesheetApprovalDivByFrequency();'>");
				timesheetApprovalDiv.append("	                  <option>Select Week Frequency</option>");
				timesheetApprovalDiv.append("	                  <option>28/11/2016 to 04/12/2016</option>");
				timesheetApprovalDiv.append("	                  <option>05/12/2016 to 11/12/2016</option>");
				timesheetApprovalDiv.append("	                  <option>12/12/2016 to 18/12/2016</option>");
				timesheetApprovalDiv.append("	                  <option>19/12/2016 to 25/12/2016</option>");
				timesheetApprovalDiv.append("	                  <option>26/12/2016 to 01/01/2017</option>");
				timesheetApprovalDiv.append("	                  <option>02/01/2017 to 08/01/2017</option>");
				timesheetApprovalDiv.append("	                  <option>09/01/2017 to 15/01/2017</option>");
				timesheetApprovalDiv.append("	                  <option>16/12/2017 to 22/12/2017</option>");
				timesheetApprovalDiv.append("	                  <option>23/12/2016 to 29/12/2017</option>");
				timesheetApprovalDiv.append("	                  </select>");
				timesheetApprovalDiv.append("	            </div>");
				
				
		 
		timesheetApprovalDiv.append("				<div id='timesheetApprovalDivByFrequency'>");
		StringBuilder statusBasedDiv = timesheetApprovalDivByFrequnecy("28/11/2016", "28/11/2016");
		timesheetApprovalDiv.append(statusBasedDiv);
		timesheetApprovalDiv.append("			</div>	");
		
		return timesheetApprovalDiv;
		
	}
	
	public StringBuilder timesheetApprovalDivByFrequnecy(String weekStartDate,String weekEndDate) throws Exception{
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		
				timesheetApprovalDiv.append("				<div class='timeNewSe'>");
				timesheetApprovalDiv.append("	                <select class='form-control chosen-select' onChange='timesheetApprovalDivByStatus();'>");
				timesheetApprovalDiv.append("	                  <option>Stages</option>");
				timesheetApprovalDiv.append("	                  <option>New</option>");
				timesheetApprovalDiv.append("	                  <option>Submit for approval</option>");
				timesheetApprovalDiv.append("	                  <option>Rejected</option>");
				timesheetApprovalDiv.append("	                </select>");
				timesheetApprovalDiv.append("	            </div>   ");  
				
				timesheetApprovalDiv.append("				<div id='timesheetApprovalDivByStatus'>");
					StringBuilder statusDiv = timesheetApprovalDivByStatus("new");
					timesheetApprovalDiv.append(statusDiv);
				timesheetApprovalDiv.append("	</div>");	
				
		return timesheetApprovalDiv;
		
	}
	
	public StringBuilder timesheetApprovalDivByStatus(String status) throws Exception{
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		
		
				timesheetApprovalDiv.append("			<div class='timesheet_appr_table'>");
				timesheetApprovalDiv.append("	    	<table>");
				timesheetApprovalDiv.append("	        	<tbody>");
				timesheetApprovalDiv.append("				<thead>");
				timesheetApprovalDiv.append("	            	<tr>");
				timesheetApprovalDiv.append("	                	<th>Employee</th>");
				timesheetApprovalDiv.append("	                    <th>Email id</th>");
				timesheetApprovalDiv.append("	                    <th>Project</th>");
				timesheetApprovalDiv.append("	                    <th>Frequency</th>");
				timesheetApprovalDiv.append("	                    <th>Total hours</th>");
				timesheetApprovalDiv.append("	                    <th>Stage</th>");
				timesheetApprovalDiv.append("	                    <th>Action</th>");
				timesheetApprovalDiv.append("	                    <th>Approved By</th>");
				timesheetApprovalDiv.append("	                </tr>");
				timesheetApprovalDiv.append("				</thead>");	
				timesheetApprovalDiv.append("	               <tr>");
				timesheetApprovalDiv.append("	                	<td><div class='time_appr_user'><img src='images/pratap-bis.jpg'>");
				timesheetApprovalDiv.append("							<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>");
				timesheetApprovalDiv.append("						</td>");
				timesheetApprovalDiv.append("	                   	<td><div>emailid@domain.com</div></td>");
				timesheetApprovalDiv.append("	                   	<td><div>Timesheets</div></td>");
				timesheetApprovalDiv.append("	                    <td>");
				timesheetApprovalDiv.append("							<div id='datefrequency609012017'><span class='res_date'><i class='fa fa-calendar'></i>09-01-2017 To 15-01-2017</span></div> </td>");
				timesheetApprovalDiv.append("	                    <td><div><a href='javascript:void(0)' onclick='timesheetApprovalPopup();'>50/50</a></div></td>");
				timesheetApprovalDiv.append("	                    <td><div>Waiting for approval</div></td>");
				timesheetApprovalDiv.append("	                    <td><div><i class='fa fa-check' alt='Approve'></i>|<i class='fa fa-times' alt='Rejected'></i></div>");
				timesheetApprovalDiv.append("						</td>");
				timesheetApprovalDiv.append("	               		<td><div>Ranjit</div></td>");
				timesheetApprovalDiv.append("	                </tr>");
				timesheetApprovalDiv.append("	            </tbody>");
				timesheetApprovalDiv.append("	        </table> ");
			    timesheetApprovalDiv.append("	</div>");
	    
	    return timesheetApprovalDiv;
	    
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
		

	}