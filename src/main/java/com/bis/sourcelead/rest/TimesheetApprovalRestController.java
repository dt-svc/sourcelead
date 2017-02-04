package com.bis.sourcelead.rest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
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
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
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
public class TimesheetApprovalRestController {
	
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
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	@RequestMapping(value = "/timesheetApprovalDiv", method = RequestMethod.POST )   
	public @ResponseBody String timesheetApprovalDiv(
			@RequestParam(value="frequencyMonth", required=false) String  frequencyMonth,
			@RequestParam(value="timesheetFrequency",required=false) String  timesheetFrequency,
			@RequestParam(value="weekFrequency", required=false) String weekFrequency,
			@RequestParam(value="client", required=false) String client,
			@RequestParam(value="stage", required=false) String stage,
			HttpServletRequest request) throws Exception {
		DateFormat format = Constants.DF_DMY;
		StringBuilder timesheetApprovalDiv = new StringBuilder("");
        StringBuilder selectedDate = new StringBuilder("");
        StringBuilder selectedFrequency = new StringBuilder("");
        StringBuilder selectedClient = new StringBuilder("");
        StringBuilder selectedStage = new StringBuilder("");
        
        
	    LinkedHashSet<String> datesList = timesheetApprovalFrequencyGeneratorUtil.getMonthsListFromCurrentDate(2, 2);
		
		timesheetApprovalDiv.append("		<div class='timesheet_out'> ");
		timesheetApprovalDiv.append("		<div class='timesheet_appr_wrap'>");
		timesheetApprovalDiv.append("			<div class='timesheetNewHead'>");
		
                  timesheetApprovalDiv.append("				<div class='timeNewSe addicon'>");
								       timesheetApprovalDiv.append("		                <select id='frequencyMonth' name='frequencyMonth' class='form-control chosen-select' onchange='timesheetApprovalDiv(\"month\");'  >");
										for (String dateStr : datesList) {
											    StringBuilder isSelected = new StringBuilder("");
											    String currentDate = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),0);
											    if(StringUtils.isEmpty(frequencyMonth) && currentDate.equalsIgnoreCase(dateStr)){
											    	isSelected.append("selected='selected'");
											    	selectedDate.append(dateStr);
											    }
											    else if(StringUtils.isNotEmpty(frequencyMonth) && frequencyMonth.equalsIgnoreCase(dateStr)){
											    	isSelected.append("selected='selected'");
											    	selectedDate.append(frequencyMonth);
											    }
											    timesheetApprovalDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+dateStr+"'>");
											    timesheetApprovalDiv.append(dateStr);
											    timesheetApprovalDiv.append("		                  </option>");
										}
										timesheetApprovalDiv.append("		                </select>");
		             timesheetApprovalDiv.append("	            </div>");
		             if(StringUtils.isEmpty(timesheetFrequency)){
		            	 timesheetFrequency = "Weekly";
		             }
		             timesheetApprovalDiv.append(" <div id='timesheetFrequencyDiv' class='timeNewSe'>");
					        timesheetApprovalDiv.append(" <select id='timesheetFrequency' class='form-control chosen-select' onchange='timesheetApprovalDiv(\"frequency\");'>");
					             timesheetApprovalDiv.append(" <option ").append(StringUtils.isNotEmpty(timesheetFrequency) ? (timesheetFrequency.equalsIgnoreCase("Monthly") ? "selected='selected'" : "") : "").append("  value='Monthly'>Monthly</option>");
							     timesheetApprovalDiv.append(" <option ").append(StringUtils.isNotEmpty(timesheetFrequency) ? (timesheetFrequency.equalsIgnoreCase("Semi-monthly") ? "selected='selected'" : "") : "").append("  value='Semi-monthly'>Semi-monthly</option>");
			   				     timesheetApprovalDiv.append(" <option ").append(StringUtils.isNotEmpty(timesheetFrequency) ? (timesheetFrequency.equalsIgnoreCase("Bi-weekly") ? "selected='selected'" : "") : "").append("  value='Bi-weekly'>Bi-weekly</option>");
			   				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(timesheetFrequency) ? (timesheetFrequency.equalsIgnoreCase("Weekly")? "selected='selected'" : "") : "").append(" value='Weekly'>Weekly</option>");
	   				        timesheetApprovalDiv.append(" </select>");
                    timesheetApprovalDiv.append("	</div>");
                    selectedFrequency.append(timesheetFrequency);
                  
                    Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthString(selectedDate.toString());
                    if(!selectedFrequency.toString().equalsIgnoreCase("Monthly")){
                    	 String weekStartDate = null;
                    	 String weekEndDate =  null;
                    	 if(StringUtils.isNotEmpty(weekFrequency)){
                    		 String[] frequency = weekFrequency.split("To");
                    		 weekStartDate = frequency[0].trim();
                		  	 weekEndDate = frequency[1].trim();
                    	 }
                    	 Map<StringBuilder,Map<String,Date>> weekFrequencyDropDownMap =  getWeekFrequncyDropDown(selectedDate.toString(),selectedFrequency.toString(),dateList,weekStartDate,weekEndDate);
                    	 weekFrequencyDropDownMap.forEach((weekFrequencyDiv,dates)->
                    	 { 
                    	    	   timesheetApprovalDiv.append(weekFrequencyDiv) ;
                    	    	   
                    	    	   dateList.put("STARTDATE", dates.get("STARTDATE")) ;
                    	    	   dateList.put("ENDDATE", dates.get("ENDDATE")) ;
                    	  }
                    	  );
                    
                    }
                    Map<StringBuilder,StringBuilder> clientDivMap = getClient(request,client);
                    clientDivMap.forEach((clientitr,clientDiv) ->
		                    {
		                    	 timesheetApprovalDiv.append(clientDiv) ;
		                    	 selectedClient.append(clientitr);
		                     }
                     );
                    
                    Map<StringBuilder,StringBuilder> stagesDiv = getStageList(stage);
                         stagesDiv.forEach((stageitr,stageDiv) ->
	                     {
	                    	 timesheetApprovalDiv.append(stageDiv) ;
	                    	 selectedStage.append(stageitr);
	                     }
                    );
                    
                 timesheetApprovalDiv.append("	           <div class='clearfix'></div>   ");  
                 timesheetApprovalDiv.append("	</div>");
         		 StringBuilder statusDiv = timesheetApprovalTableDiv(request,format.format(dateList.get("STARTDATE")),format.format(dateList.get("ENDDATE")),selectedFrequency.toString(),selectedClient.toString(),selectedStage.toString());
         		 timesheetApprovalDiv.append(statusDiv);
                 timesheetApprovalDiv.append("	</div>");
                 timesheetApprovalDiv.append("	</div>");
		  return timesheetApprovalDiv.toString();
	}
	
	private Map<StringBuilder,Map<String,Date>>  getWeekFrequncyDropDown(String selectedMonth,String selectedFrequency, Map<String,Date> dateList,String selectedStartDate,String selectedEndDate) throws Exception{
		 DateFormat format = Constants.DF_DMY;
		 Date startDate = dateList.get("STARTDATE");
	     Date endDate = dateList.get("ENDDATE");
	     Date sDate = null;
	     Date eDate = null;
         if(StringUtils.isNotEmpty(selectedStartDate) && StringUtils.isNotEmpty(selectedEndDate))
		 {
			 sDate = format.parse(selectedStartDate.replaceAll("/", "-"));
			 eDate = format.parse(selectedEndDate.replaceAll("/", "-"));
		 }
		 
	     Map<String,String> frequencyDropDown = new LinkedHashMap<>();
	     if(selectedFrequency.equalsIgnoreCase("Semi-monthly"))      
        {
	    	 HashMap<String,Date> tab1to15Frequency =  CalendarUtil.getMonthStartDateAnd15thDateByGivenDateAndMothPreference(startDate,0);
	    	 frequencyDropDown.put(format.format(tab1to15Frequency.get("STARTDATE")), format.format(tab1to15Frequency.get("ENDDATE")));
			 
	    	 HashMap<String,Date> tab16toEndFrequency = CalendarUtil.getMonth16thDateAndEndDateByGivenDateAndMothPreference(startDate,0);
	    	 frequencyDropDown.put(format.format(tab16toEndFrequency.get("STARTDATE")), format.format(tab16toEndFrequency.get("ENDDATE")));
        }
	     else if(selectedFrequency.equalsIgnoreCase("Weekly") || selectedFrequency.equalsIgnoreCase("Bi-Weekly")){
	    	 int dayOfTheDate = CalendarUtil.getDayOfTheDate(startDate);
			 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 startCal.setLenient(false);
			 startCal.setTime(startDate);
			 Calendar startMonday =null;
			 if(dayOfTheDate == 1){
				 startMonday = CalendarUtil.getPreviousWeekDayByCalendar(startCal, "MON");
			  }
			  else{
				  startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
			  }
			 Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 endCal.setLenient(false);
			 endCal.setTime(endDate);
			 Calendar endDay = null;
			 if(selectedFrequency.equalsIgnoreCase("Weekly")){
				 endDay = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN");
			 }
			 else {
				 endDay = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON");
			 }
			 if(selectedFrequency.equalsIgnoreCase("Weekly")){
				 frequencyDropDown= timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(format.format(startMonday.getTime()), format.format(endDay.getTime()));
			 }
			 else {
				  Date tsStartDate = null;
				  Date tsEndDate = null;
				  Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
				  tsCal.setLenient(false);
				  
				  int totalWeeks = (int)((endDay.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
				  for (int week = 0; week <(totalWeeks); week++) {
					  if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
						}
					    if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
					    frequencyDropDown.put(format.format(tsStartDate), format.format(tsEndDate));
					    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON").getTime();
				  }
				}
	       }
		
		   Map<StringBuilder,Map<String,Date>> weekFrequencyBuilder = new LinkedHashMap<>();
		    StringBuilder timesheetApprovalDiv = new StringBuilder(""); 
	    	 timesheetApprovalDiv.append("					<div class='timeNewSe timeNewSe_a addicon'>");    
			    timesheetApprovalDiv.append("<select id='weekFrequency' class='form-control chosen-select' onchange='timesheetApprovalDiv( \"weekfrequency\");' >");
			    if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			    	int  count = 1;
			    	for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
			    		 String  k = entry.getKey();
			    		 String  v = entry.getValue();
			    		 Date frequencyStartDate = format.parse(k.replaceAll("/", "-"));
			    		 Date frequencyEndDate = format.parse(v.replaceAll("/", "-"));
			    		 StringBuilder isSelected = new StringBuilder("");
			    		try {
			    			if(sDate == null && eDate == null&& count == 1
			    				&& !isCurrentDateBetweenDateRange(format.format(startDate), format.format(endDate))){
			    				 dateList.clear();
                          	     dateList.put("STARTDATE", frequencyStartDate);
							     dateList.put("ENDDATE",frequencyEndDate);
							     isSelected.append("selected='selected'");
			    			}
			    			else if(sDate == null && eDate == null
			    					 && isCurrentDateBetweenDateRange(k, v)){
                           	     dateList.clear();
                           	     dateList.put("STARTDATE",frequencyStartDate);
							     dateList.put("ENDDATE", frequencyEndDate);
							     isSelected.append("selected='selected'");
                            }
			    			else if(sDate != null && eDate != null 
			    					&& sDate.equals(frequencyStartDate) && eDate.equals(frequencyEndDate))
			    			{
			    				 dateList.clear();
                          	     dateList.put("STARTDATE",frequencyStartDate);
							     dateList.put("ENDDATE", frequencyEndDate);
							     isSelected.append("selected='selected'");
			    			}
					      } catch (Exception e) {}
			    	     if(k.equalsIgnoreCase(v)){
			    	    	 timesheetApprovalDiv.append("<option "+isSelected.toString()+" value='"+k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-")+"'>"+k.replaceAll("/", "-")+"</option>");
			    	     }else{
			    	    	 timesheetApprovalDiv.append("<option "+isSelected.toString()+" value='"+k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-")+"'>"+k.replaceAll("/", "-")+" To "+v.replaceAll("/", "-")+"</option>");
			    	     }
			    	     count++;
			    	}
			    } 
               timesheetApprovalDiv.append("</select>");
            timesheetApprovalDiv.append("		            </div> ");
            weekFrequencyBuilder.put(timesheetApprovalDiv, dateList);
		    return weekFrequencyBuilder;
	}
	/*
	 * client Filter
	 */
	private Map<StringBuilder,StringBuilder> getClient(HttpServletRequest request,String client) throws Exception
	{
		
		Map<StringBuilder,StringBuilder> clientBuilder = new LinkedHashMap<>();
		StringBuilder selectedClient = new StringBuilder("");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 List<Projects> projectsList = projectsService.getProjectBasedClientsByOrgLocationsIdAndApproverUserId(orgLocations.getId(), user.getId());
		 StringBuilder timesheetApprovalDiv = new StringBuilder(""); 
		 timesheetApprovalDiv.append("					<div class='timeNewSe timeNewSe_a'>");  
		  timesheetApprovalDiv.append("<select id='client' onChange='timesheetApprovalDiv(\"client\");' class='form-control chosen-select'>");
		         if(StringUtils.isEmpty(client)){
		        	 timesheetApprovalDiv.append(" <option selected='selected' value=''>Select Client</option>");
		         }
		         else{
		        	 timesheetApprovalDiv.append(" <option  value=''>Select Client</option>");
		         }
		       
                if(projectsList != null && !projectsList.isEmpty()){
                	    if(projectsList.size()>1){
                	    	if(StringUtils.isNotEmpty(client) && client.equalsIgnoreCase("All")){
                  	    	  timesheetApprovalDiv.append(" <option selected='selected' value='All'>All</option>");
	                  	    }
	                  	    else{
	                  	    	  timesheetApprovalDiv.append(" <option value='All'>All</option>");
	                  	    }
                	    }
                	    
               	        projectsList.forEach(projects ->{
               	        	StringBuilder isSelected = new StringBuilder("");
               	        	if(StringUtils.isNotEmpty(client) && !client.equalsIgnoreCase("All")
               	        	   && Integer.parseInt(client) == projects.getOrgRelationId().getId()){
               	        		isSelected.append("selected='selected'");
               	        	}
               	        	timesheetApprovalDiv.append("<option "+isSelected.toString()+"  value='"+projects.getOrgRelationId().getId()+"'>"+projects.getOrgRelationId().getResponderLocationId().getOrg().getOrgName()+"</option>");
               	        });
							
               }
               timesheetApprovalDiv.append("</select>");
               timesheetApprovalDiv.append("		            </div> ");
               selectedClient.append(client);
               
               clientBuilder.put(selectedClient,timesheetApprovalDiv);
               return clientBuilder;
	}
	
	/*
	 * Stage Filter
	 */
	private Map<StringBuilder,StringBuilder> getStageList(String stage){
		   Map<StringBuilder,StringBuilder> stageBuilder = new LinkedHashMap<>();
		   StringBuilder timesheetApprovalDiv = new StringBuilder(""); 
		   StringBuilder selectedStage = new StringBuilder("");
		   
		   if(StringUtils.isEmpty(stage)){
			   stage = "All";
		   }
		   timesheetApprovalDiv.append("				<div class='timeNewSe'>");
			timesheetApprovalDiv.append("	                <select id='stage' class='form-control chosen-select' onChange='timesheetApprovalDiv(\"stage\");'>");
			     timesheetApprovalDiv.append(" <option      ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("All") ? "selected='selected'" : "") : "").append("  value='All'>All</option>");
			     timesheetApprovalDiv.append(" <option      ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("NEW") ? "selected='selected'" : "") : "").append("  value='NEW'>NEW</option>");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("SUBMITTEDFORAPPROVAL") ? "selected='selected'" : "") : "").append("  value='SUBMITTEDFORAPPROVAL'>SUBMITTED FOR APPROVAL");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("APPROVED") ? "selected='selected'" : "") : "").append("  value='APPROVED'>APPROVED</option>");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("REJECTED") ? "selected='selected'" : "") : "").append("  value='REJECTED'>REJECTED</option>");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("REOPENED") ? "selected='selected'" : "") : "").append("  value='REOPENED'>REOPENED</option>");
			timesheetApprovalDiv.append("	                </select>");
			timesheetApprovalDiv.append("	            </div>   ");  
			selectedStage.append(stage);
			stageBuilder.put(selectedStage,timesheetApprovalDiv);
			return stageBuilder;
	}
	
	public StringBuilder timesheetApprovalTableDiv(HttpServletRequest request,String startDate,String endDate,String timesheetFrequency,String client,String stage) throws Exception{
		       
		        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		        DateFormat format = Constants.DF_DMY;
		        DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		        Date actualStartDate=null;
			    Date actualEndDate=null;
			    try {
			    	 actualStartDate = format.parse(startDate.replaceAll("/", "-"));
			    	 actualEndDate = format.parse(endDate.replaceAll("/", "-"));
			    }catch (Exception e) {}
		        StringBuilder timesheetApprovalDiv = new StringBuilder();
		        Long clientId = null;
		        if(StringUtils.isNotEmpty(client) && !client.equalsIgnoreCase("All")){
		        	clientId =Long.parseLong(client);
		        }
		        
		        List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, actualStartDate, actualEndDate, clientId, stage, orgLocations.getId(), user.getId());
		        if(projectMembers != null && !projectMembers.isEmpty()){  
			    	timesheetApprovalDiv.append("			<div class='timesheet_appr_table'>");
					timesheetApprovalDiv.append("	    	<table id='example'>");
					timesheetApprovalDiv.append("	        	<tbody>");
					timesheetApprovalDiv.append("				<thead>");   
			        
					timesheetApprovalDiv.append("	            	<tr>");
					timesheetApprovalDiv.append("	                	<th>Employee</th>           ");
					timesheetApprovalDiv.append("	                    <th>Email id</th>           ");
					timesheetApprovalDiv.append("	                    <th>Phone No</th>           ");
					timesheetApprovalDiv.append("	                    <th>Project</th>            ");
					timesheetApprovalDiv.append("	                    <th>Client</th>             ");
					
					LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
					Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(startDate, endDate);
					if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
						frequencyDropDown.forEach((k,v)->{
	 				        Date weekStartDate;
	 				        Date weekEndDate;
							try {
								 weekStartDate = format.parse(k.replaceAll("/", "-"));
								 weekEndDate = format.parse(v.replaceAll("/", "-"));
								 displayWeeksList.put(weekStartDate, weekEndDate);
								 if(weekStartDate.equals(weekEndDate)){
									 timesheetApprovalDiv.append("	                    <th>"+shortormat.format(weekStartDate)+"</th>  ");
								 }else{
									 timesheetApprovalDiv.append("	                    <th>"+shortormat.format(weekStartDate)+"-"+shortormat.format(weekEndDate)+"</th>  ");
								 }
			   					
							} catch (Exception e) {}
						});
					}
					
					timesheetApprovalDiv.append("	                    <th>Total</th>              ");	
					timesheetApprovalDiv.append("	                    <th>Stage</th>              ");
					timesheetApprovalDiv.append("	                    <th>Action</th>             ");
					timesheetApprovalDiv.append("	                    <th>Approved By</th>        ");
					
					timesheetApprovalDiv.append("	                </tr>");
					timesheetApprovalDiv.append("				</thead>");	
					for (ProjectMembers projectMember : projectMembers)
					{
						 timesheetApprovalDiv.append("<tr id='row"+projectMember.getId()+"'>");
						 timesheetApprovalDiv.append("  <td>");
						 timesheetApprovalDiv.append("<div class='time_appr_user'>");
						 timesheetApprovalDiv.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='").append(request.getContextPath()).append("/displayUserImage/").append(projectMember.getEmployee().getUser().getId()).append("' alt='Profile Image' class='img-circle img-responsive'>");
						 timesheetApprovalDiv.append("    <span><b>").append(projectMember.getEmployee().getUser().getFullName()).append("</b></span>");
						 timesheetApprovalDiv.append("   <div>");
						 timesheetApprovalDiv.append(" </td>");
						 timesheetApprovalDiv.append("<td><div>"+projectMember.getEmployee().getUser().getEmail()+"</div></td>");
					     timesheetApprovalDiv.append("	<td><div>"+((projectMember.getEmployee().getUser().getMobilePhone() != null) ?(projectMember.getEmployee().getUser().getMobilePhone()) : "--")+"</div></td>");
					     timesheetApprovalDiv.append("	<td><div>"+projectMember.getProjects().getProjectName()+"</div></td>");
					     timesheetApprovalDiv.append("	<td><div>"+((projectMember.getProjects().getOrgRelationId() != null) ?projectMember.getProjects().getOrgRelationId().getResponderLocationId().getOrg().getOrgName() : "--")+"</div></td>");
					     
					     Float totalHours = 0f;
					     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(user.getId(),projectMember.getId(),actualStartDate,actualEndDate);
					     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
					    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
					    	    	
					    	    	 if(sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty()){
					    	    		 totalHours = sah.getWeekTotals().get(entry.getKey());
					    	    		 timesheetApprovalDiv.append("<td><div class='sm'>"+sah.getWeekTotals().get(entry.getKey())+"/40</div></td>");
					    	    	 }
					    	    	 else{
					    	    		 timesheetApprovalDiv.append("<td><div class='sm'>0/40</div></td>");
					    	    	 }
					    	    	
					    	     }
					     }
					    //timesheetApprovalDiv.append("<td><div class='sm'><a href='javascript:void(0)'>"+totalHours+"/40</a></div></td>");
					    timesheetApprovalDiv.append("<td><div class='sm'><a href='#' onClick='timesheetApprovalPopUpByProjectMemberId(\""+startDate+"To"+endDate+"\" ,\""+projectMember.getId()+"\");'>"+sah.getTotalHours()+"/40</a></div></td>");
					    timesheetApprovalDiv.append("<td><div>"+sah.getSubmissionApprovalStatus()+"</div></td>");
					    timesheetApprovalDiv.append("    <td><div id='action"+projectMember.getId()+"'> " );
					     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
					    	 timesheetApprovalDiv.append("<div>--</div>");
					    	 timesheetApprovalDiv.append("<div id='rejectedPopup'></div>");
					     }
					      if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
			            	 timesheetApprovalDiv.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmissionByProjectMemberId(\""+projectMember.getId()+"\",\""+format.format(sah.getSubmissionId().getStartDate())+"\",\""+format.format(sah.getSubmissionId().getEndDate())+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmissionByProjectMemberId(\""+projectMember.getId()+"\",\""+format.format(sah.getSubmissionId().getStartDate())+"\",\""+format.format(sah.getSubmissionId().getEndDate())+"\");'></i></div>");
			                 timesheetApprovalDiv.append("<div id='rejectedPopup'></div>");
			             }
					      if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
			            	 timesheetApprovalDiv.append(" <div><h3 class='reje_color'>Rejected</h3>");
			            	 timesheetApprovalDiv.append(" <i class='glyphicon glyphicon-comment' id='"+projectMember.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+projectMember.getId()+"\");'>");
			            	 timesheetApprovalDiv.append(" <div class='app_error_mes' id='rejectedMessage"+projectMember.getId()+"'>").append(sah.getSubmissionId().getRejectedReason()).append("</div>");
			            	 timesheetApprovalDiv.append(" </i>");
			            	 timesheetApprovalDiv.append(" </div>");
			            }
					     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED){
			            	timesheetApprovalDiv.append(" <div><h3>Approved</h3></div>");
			            }  
			            timesheetApprovalDiv.append(" </div>");
			            timesheetApprovalDiv.append("    </td>");
				        timesheetApprovalDiv.append("<td><div class='sm'>Ranjit</div></td>");
				        timesheetApprovalDiv.append("</tr>");
				  }
				
				  timesheetApprovalDiv.append("	        </tbody>");
				  timesheetApprovalDiv.append("	     </table> ");
			      timesheetApprovalDiv.append("	</div>");
			    
				}
		        else{
		        	timesheetApprovalDiv.append("   <div class='row'>"); 
					timesheetApprovalDiv.append("	<div class='col-lg-12'>"); 
					timesheetApprovalDiv.append("		<div class='content-area clearfix'>"); 
					timesheetApprovalDiv.append("			<div class='blank-pages'>");
					timesheetApprovalDiv.append("			        <p><i class='fa fa-5x fa-bar-chart'></i></p>");
					timesheetApprovalDiv.append("			        <p>No Timesheets Found</p>");
					timesheetApprovalDiv.append("			 </div>");
					timesheetApprovalDiv.append("		</div>"); 
					timesheetApprovalDiv.append("	</div>"); 
					timesheetApprovalDiv.append("  </div> "); 
		        }
	    
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