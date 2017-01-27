package com.bis.sourcelead.rest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
 

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
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	 
	@RequestMapping(value = "/timesheetApprovalDiv", method = RequestMethod.POST )   
	public @ResponseBody String timesheetApprovalDiv(HttpServletRequest request) throws Exception {
			 
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		timesheetApprovalDiv.append("");
		
		timesheetApprovalDiv.append("		<div class='timesheet_out'> ");
		timesheetApprovalDiv.append("		<div class='timesheet_appr_wrap'>");
		    timesheetApprovalDiv.append("			<div class='timesheetNewHead'>");
						      
		                      timesheetApprovalDiv.append("				<div class='timeNewSe addicon'>");
						       LinkedHashSet<String> datesList = timesheetApprovalFrequencyGeneratorUtil.getMonthsListFromCurrentDate(3, 3);
											       timesheetApprovalDiv.append("		                <select id='frequencyMonth' name='frequencyMonth' class='form-control chosen-select'   >");
													String selectedDate = null;
													for (String dateStr : datesList) {
														    StringBuilder isSelected = new StringBuilder("");
														    String currentDate = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),0);
														    if(currentDate.equalsIgnoreCase(dateStr)){
														    	isSelected.append("selected='selected'");
														    	selectedDate = dateStr;
														    }
														    timesheetApprovalDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+dateStr+"'>");
														    timesheetApprovalDiv.append(dateStr);
														    timesheetApprovalDiv.append("		                  </option>");
													}
													timesheetApprovalDiv.append("		                </select>");
					             timesheetApprovalDiv.append("	            </div>");
						        
					             timesheetApprovalDiv.append(" <div id='timesheetFrequencyDiv' class='timeNewSe'>");
								        timesheetApprovalDiv.append(" <select class='form-control chosen-select'>");
								             timesheetApprovalDiv.append(" <option value=''>Select Frequency</option>");
								             timesheetApprovalDiv.append(" <option value='all'>All</option>");
										     timesheetApprovalDiv.append(" <option value='Monthly'>Monthly</option>");
										     timesheetApprovalDiv.append(" <option value='Semi-monthly'>Semi-monthly</option>");
						   				     timesheetApprovalDiv.append(" <option value='Bi-weekly'>Bi-weekly</option>");
						   				     timesheetApprovalDiv.append(" <option value='Weekly'>Weekly</option>");
				   				        timesheetApprovalDiv.append(" </select>");
		                        timesheetApprovalDiv.append("	</div>");
							     
		                        
		                          StringBuilder frequencyDiv = timesheetApprovalDivByProject("1");
							      timesheetApprovalDiv.append(frequencyDiv);
		
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
			
		StringBuilder frequencyBasedDiv =  timesheetApprovalDivForStages();
		return frequencyBasedDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetApprovalDivByStatus", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetApprovalDivByStatus(HttpServletRequest request,
		   @RequestParam(value="status", required=false) String status) throws Exception {
			StringBuilder tabDiv = timesheetApprovalDivByStatus("new","28/11/2016", "28/11/2016","1");
		    return tabDiv.toString();
	}
	
	public StringBuilder timesheetApprovalDivByProject(String projectId) throws Exception{
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		if(StringUtils.isNotEmpty(projectId)){
			Projects projects = projectsService.getProjectById(Long.parseLong(projectId));
			if(projects != null){
				Map<String,String>  timesheetFrequencyMap = timesheetApprovalFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projects);
				if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
					
				timesheetApprovalDiv.append("					<div class='timeNewSe timeNewSe_a'>");    
							timesheetApprovalDiv.append("<select class='form-control chosen-select'>");
									timesheetApprovalDiv.append("  <option>Select Client</option>");
									timesheetApprovalDiv.append("<option>Client 1</option>");
									timesheetApprovalDiv.append("<option>Client 2</option>");
									timesheetApprovalDiv.append("<option>Client 3</option>");
									timesheetApprovalDiv.append("<option>Client 4</option>");
							timesheetApprovalDiv.append("</select>");
				timesheetApprovalDiv.append("		            </div> ");
				StringBuilder frequencyBasedDiv = timesheetApprovalDivForStages();
				timesheetApprovalDiv.append(frequencyBasedDiv);
				
			 }
		   }
		 }
		 return timesheetApprovalDiv;
	}
	
	public StringBuilder timesheetApprovalDivForStages() throws Exception{
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
		
				timesheetApprovalDiv.append("				<div class='timeNewSe'>");
				timesheetApprovalDiv.append("	                <select class='form-control chosen-select' onChange='timesheetApprovalDivByStatus();'>");
				timesheetApprovalDiv.append("	                  <option selected>All</option>");
				timesheetApprovalDiv.append("	                  <option>New</option>");
				timesheetApprovalDiv.append("	                  <option>Submit for approval</option>");
				timesheetApprovalDiv.append("	                  <option>Rejected</option>");
				timesheetApprovalDiv.append("	                </select>");
				timesheetApprovalDiv.append("	            </div>   ");  
				timesheetApprovalDiv.append("	           <div class='clearfix'></div>   ");  
				timesheetApprovalDiv.append("	            </div>   ");  
				
					StringBuilder statusDiv = timesheetApprovalDivByStatus("ALL","","","");
					timesheetApprovalDiv.append(statusDiv);
				
		return timesheetApprovalDiv;
		
	}
	
	public StringBuilder timesheetApprovalDivByStatus(String status,String startDate,String endDate,String projectId) throws Exception{
		
		StringBuilder timesheetApprovalDiv = new StringBuilder();
			
				Collection<ProjectMembers>  projectMembersList =  projectMembersService.getAllProjectMembers();
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
				for(ProjectMembers pm : projectMembersList){
						timesheetApprovalDiv.append("	               <tr>");
						timesheetApprovalDiv.append("	                	<td><div class='time_appr_user'><img src='images/pratap-bis.jpg'>");
						timesheetApprovalDiv.append("							<span><b>"+pm.getEmployee().getUser().getFirstName()+"</b><b>"+pm.getEmployee().getUser().getLastName()+"</b></span></div></div>");
						timesheetApprovalDiv.append("						</td>");
						timesheetApprovalDiv.append("	                   	<td><div>"+pm.getEmpEmail()+"</div></td>");
						timesheetApprovalDiv.append("	                   	<td><div>"+pm.getProjectName()+"</div></td>");
						timesheetApprovalDiv.append("	                    <td>");
						timesheetApprovalDiv.append("							<div id='datefrequency609012017'><span class='res_date'><i class='fa fa-calendar'></i>09-01-2017 To 15-01-2017</span></div> </td>");
						timesheetApprovalDiv.append("	                    <td><div><a href='javascript:void(0)' onclick='timesheetApprovalPopup();'>50/50</a></div></td>");
						timesheetApprovalDiv.append("	                    <td><div>Waiting for approval</div></td>");
						timesheetApprovalDiv.append("	                    <td><div><i class='fa fa-check' alt='Approve'></i>|<i class='fa fa-times' alt='Rejected'></i></div>");
						timesheetApprovalDiv.append("						</td>");
						timesheetApprovalDiv.append("	               		<td><div>Ranjit</div></td>");
						timesheetApprovalDiv.append("	                </tr>");
				}
				timesheetApprovalDiv.append("	            </tbody>");
				timesheetApprovalDiv.append("	        </table> ");
			    timesheetApprovalDiv.append("	</div>");
	    
	    return timesheetApprovalDiv;
	    
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	boolean isCurrentDateBetweenDateRange(String weekStartDate,String weekEndDate) {
		Boolean flag = false;
		if(StringUtils.isNotEmpty(weekStartDate) && StringUtils.isNotEmpty(weekEndDate)){
			Date currentDate = CommonUtil.currentDate();
			 DateFormat format = Constants.DF_STD_DMY;
			 Date startDate = null;
			 Date endDate = null;
			try {
				startDate = format.parse(weekStartDate.replaceAll("-", "/"));
				 endDate = format.parse(weekEndDate.replaceAll("-", "/"));
				 flag =   currentDate.after(startDate) && currentDate.before(endDate);
			} catch (ParseException e) {}
		}
		return flag;
	}
		

	}