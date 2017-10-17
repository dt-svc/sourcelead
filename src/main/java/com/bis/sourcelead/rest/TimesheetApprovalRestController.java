package com.bis.sourcelead.rest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
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
	public ProjectClientService projectClientService;
	
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
	 
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	@RequestMapping(value = "/timesheetApprovalDiv", method = RequestMethod.POST )   
	public @ResponseBody String timesheetApprovalDiv(
			@RequestParam(value="frequencyMonth", required=false) String  frequencyMonth,
			@RequestParam(value="timesheetFrequency",required=false) String  timesheetFrequency,
			@RequestParam(value="weekFrequency", required=false) String weekFrequency,
			@RequestParam(value="client", required=false) String client,
			@RequestParam(value="stage", required=false) String stage,
			@RequestParam(value="locationCode") String locationCode,
			HttpServletRequest request) throws Exception {
		//DateFormat format = Constants.DF_DMY;
		//DateFormat  format = LoginSessionController.DATEFORMAT;
		StringBuilder timesheetApprovalDiv = new StringBuilder("");
        StringBuilder selectedDate = new StringBuilder("");
        StringBuilder selectedFrequency = new StringBuilder("");
        StringBuilder selectedClient = new StringBuilder("");
        StringBuilder selectedStage = new StringBuilder("");
        OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
        
	    LinkedHashSet<String> datesList = timesheetApprovalFrequencyGeneratorUtil.getMonthsListFromCurrentDate(4, 2);
		
		timesheetApprovalDiv.append("		<div class='timesheet_out'> ");
		timesheetApprovalDiv.append("		<div class='timesheet_appr_wrap'>");
		timesheetApprovalDiv.append("			<div class='timesheetNewHead'>");
		
                  timesheetApprovalDiv.append("				<div class='timeNewSe timeNewSe_month addicon'>");
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
		             timesheetApprovalDiv.append(" <div id='timesheetFrequencyDiv' class='timeNewSe timeNewSe_week'>");
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
                    	 Map<StringBuilder,Map<String,Date>> weekFrequencyDropDownMap =  getWeekFrequncyDropDown(selectedDate.toString(),selectedFrequency.toString(),dateList,weekStartDate,weekEndDate,orgLocations);
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
         		 StringBuilder statusDiv = timesheetApprovalTableDiv(request,commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("STARTDATE")),commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("ENDDATE")),selectedFrequency.toString(),selectedClient.toString(),selectedStage.toString());
         		 timesheetApprovalDiv.append(statusDiv);
                 timesheetApprovalDiv.append("	</div>");
                 timesheetApprovalDiv.append("	</div>");
		  return timesheetApprovalDiv.toString();
	}
	
	private Map<StringBuilder,Map<String,Date>>  getWeekFrequncyDropDown(String selectedMonth,String selectedFrequency, Map<String,Date> dateList,String selectedStartDate,String selectedEndDate,OrgLocations orgLocations) throws Exception{
		 //DateFormat format = Constants.DF_DMY;
		//DateFormat  format = LoginSessionController.DATEFORMAT;
		 Date startDate = dateList.get("STARTDATE");
	     Date endDate = dateList.get("ENDDATE");
	     Date sDate = null;
	     Date eDate = null;
         if(StringUtils.isNotEmpty(selectedStartDate) && StringUtils.isNotEmpty(selectedEndDate))
		 {
			 sDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),selectedStartDate.replaceAll("/", "-"));
			 eDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),selectedEndDate.replaceAll("/", "-"));
		 }
		 
	     Map<String,String> frequencyDropDown = new LinkedHashMap<>();
	     if(selectedFrequency.equalsIgnoreCase("Semi-monthly"))      
        {
	    	 HashMap<String,Date> tab1to15Frequency =  CalendarUtil.getMonthStartDateAnd15thDateByGivenDateAndMothPreference(startDate,0);
	    	 frequencyDropDown.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab1to15Frequency.get("STARTDATE")), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab1to15Frequency.get("ENDDATE")));
			 
	    	 HashMap<String,Date> tab16toEndFrequency = CalendarUtil.getMonth16thDateAndEndDateByGivenDateAndMothPreference(startDate,0);
	    	 frequencyDropDown.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab16toEndFrequency.get("STARTDATE")), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab16toEndFrequency.get("ENDDATE")));
        }
	     else if(selectedFrequency.equalsIgnoreCase("Weekly") || selectedFrequency.equalsIgnoreCase("Bi-Weekly")){
	    	 int dayOfTheDate = CalendarUtil.getDayOfTheDate(startDate);
			 Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 startCal.setLenient(false);
			 startCal.setTime(startDate);
			 Calendar startMonday =null;
			 if(dayOfTheDate == 1){
				 startMonday = CalendarUtil.getPreviousWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
			  }
			  else{
				  startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON",orgLocations.getDateFormat());
			  }
			 Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 endCal.setLenient(false);
			 endCal.setTime(endDate);
			 Calendar endDay = null;
			 if(selectedFrequency.equalsIgnoreCase("Weekly")){
				 endDay = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN",orgLocations.getDateFormat());
			 }
			 else {
				 endDay = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
			 }
			 if(selectedFrequency.equalsIgnoreCase("Weekly")){
				 frequencyDropDown= timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startMonday.getTime()), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDay.getTime()),orgLocations.getDateFormat());
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
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
						}
					    if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
							tsEndDate = CalendarUtil.getDateIncrementByincrement(tsCal, 13).getTime();
						}
					    frequencyDropDown.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsStartDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tsEndDate));
					    tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON",orgLocations.getDateFormat()).getTime();
				  }
				}
	       }
		
		   Map<StringBuilder,Map<String,Date>> weekFrequencyBuilder = new LinkedHashMap<>();
		    StringBuilder timesheetApprovalDiv = new StringBuilder(""); 
	    	 timesheetApprovalDiv.append("					<div class='timeNewSe timeNewSe_week_date addicon'>");    
			    timesheetApprovalDiv.append("<select id='weekFrequency' class='form-control chosen-select' onchange='timesheetApprovalDiv( \"weekfrequency\");' >");
			    if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
			    	int  count = 1;
			    	for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
			    		 String  k = entry.getKey();
			    		 String  v = entry.getValue();
			    		 Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
			    		 Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
			    		 StringBuilder isSelected = new StringBuilder("");
			    		try {
			    			if(sDate == null && eDate == null&& count == 1
			    				&& !CalendarUtil.isCurrentDateBetweenDateRange(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat())){
			    				 dateList.clear();
                          	     dateList.put("STARTDATE", frequencyStartDate);
							     dateList.put("ENDDATE",frequencyEndDate);
							     isSelected.append("selected='selected'");
			    			}
			    			else if(sDate == null && eDate == null
			    					 && CalendarUtil.isCurrentDateBetweenDateRange(k, v,orgLocations.getDateFormat())){
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
		 List<ProjectClients> projectClientList = projectClientService.getProjectClientsListByLoginUserAndOrgLocationId(orgLocations.getId(), user.getId());
		 StringBuilder timesheetApprovalDiv = new StringBuilder(""); 
		 timesheetApprovalDiv.append("					<div class='timeNewSe timeNewSe_client'>");  
		  timesheetApprovalDiv.append("<select id='client' onChange='timesheetApprovalDiv(\"client\");' class='form-control chosen-select'>");
		         if(StringUtils.isEmpty(client)){
		        	 timesheetApprovalDiv.append(" <option selected='selected' value=''>Select Client</option>");
		         }
		         else{
		        	 timesheetApprovalDiv.append(" <option  value=''>Select Client</option>");
		         }
		       
                if(projectClientList != null && !projectClientList.isEmpty()){
                	    if(projectClientList.size()>1){
                	    	if(StringUtils.isNotEmpty(client) && client.equalsIgnoreCase("All")){
                  	    	  timesheetApprovalDiv.append(" <option selected='selected' value='All'>All</option>");
	                  	    }
	                  	    else{
	                  	    	  timesheetApprovalDiv.append(" <option value='All'>All</option>");
	                  	    }
                	    }
                	    
                	    projectClientList.forEach(projectClient ->{
               	        	StringBuilder isSelected = new StringBuilder("");
               	        	if( StringUtils.isNotEmpty(client) && !client.equalsIgnoreCase("All")
               	        	   && Integer.parseInt(client) == projectClient.getOrgRelationId().getId()){
               	        		isSelected.append("selected='selected'");
               	        	}
               	        	
               	        	if( null != projectClient.getOrgRelationId().getLocationId() && projectClient.getOrgRelationId().getLocationId().getId() != null && projectClient.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
        						if(projectClient.getOrgRelationId().getResponderLocationId() != null){
        							timesheetApprovalDiv.append("<option ").append(isSelected.toString()).append("  value='").append(projectClient.getOrgRelationId().getId()).append("'>").append(projectClient.getOrgRelationId().getResponderLocationId().getOrgLocationName()).append("</option>");
        						}
        					}
        					if( null != projectClient.getOrgRelationId().getResponderLocationId() && null != projectClient.getOrgRelationId().getResponderLocationId().getId() && projectClient.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
        						if(projectClient.getOrgRelationId().getLocationId() != null){
        							timesheetApprovalDiv.append("<option ").append(isSelected.toString()).append("  value='").append(projectClient.getOrgRelationId().getId()).append("'>").append(projectClient.getOrgRelationId().getLocationId().getOrgLocationName()).append("</option>");
        						}
        					}
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
		   timesheetApprovalDiv.append("				<div class='timeNewSe timeNewSe_stage'>");
			timesheetApprovalDiv.append("	                <select id='stage' class='form-control chosen-select' onChange='timesheetApprovalDiv(\"stage\");'>");
			     timesheetApprovalDiv.append(" <option      ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("All") ? "selected='selected'" : "") : "").append("  value='All'>All</option>");
			     timesheetApprovalDiv.append(" <option      ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("new") ? "selected='selected'" : "") : "").append("  value='new'>New</option>");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("submitted for approval") ? "selected='selected'" : "") : "").append("  value='submitted for approval'>Submitted For Approval");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("approved") ? "selected='selected'" : "") : "").append("  value='approved'>Approved</option>");
				     timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("rejected") ? "selected='selected'" : "") : "").append("  value='rejected'>Rejected</option>");
				    // timesheetApprovalDiv.append(" <option  ").append(StringUtils.isNotEmpty(stage) ? (stage.equalsIgnoreCase("reopened") ? "selected='selected'" : "") : "").append("  value='reopened'>REOPENED</option>");
			timesheetApprovalDiv.append("	                </select>");
			timesheetApprovalDiv.append("	            </div>   ");  
			
			timesheetApprovalDiv.append("					<div class='export dropexport' onClick='dropDown();' id='exportHideId'> ");
			timesheetApprovalDiv.append("						<p data-toggle='tooltip' title='Export'><i class='glyphicon glyphicon-export'></i></p> ");
			timesheetApprovalDiv.append("							<ul id='exporrt'> ");
			String exportPdf="pdf";
			String exportexcel="excel";
			timesheetApprovalDiv.append("			                	<li onClick='exportTimeSheetToPdfOrExcel(\""+exportPdf+"\");'><i class='fa fa-file-pdf-o text-danger'></i>&nbsp;PDF</li> ");
			timesheetApprovalDiv.append("			                    <li onClick='exportTimeSheetToPdfOrExcel(\""+exportexcel+"\");'><i class='fa fa-file-excel-o text-primary'></i>&nbsp;Excel</li> ");
			timesheetApprovalDiv.append("			                    <li class='hidden'>Word<i class='fa fa-file-word-o'></i></li> ");
			timesheetApprovalDiv.append("							</ul>			 ");
			timesheetApprovalDiv.append("					</div> ");
			timesheetApprovalDiv.append("			        <div class='clearfix'></div> ");			
			
			
			selectedStage.append(stage);
			stageBuilder.put(selectedStage,timesheetApprovalDiv);
			return stageBuilder;
	}
	
  @RequestMapping(value = "/timesheetApprovalTableRowByProjectMemberId", method = RequestMethod.POST ) 
  public @ResponseBody String timesheetApprovalTableRowByProjectMemberId(
			@RequestParam(value="projectMemberId", required=false) Long  projectMemberId,
			@RequestParam(value="timesheetFrequency",required=false) String  timesheetFrequency,
			HttpServletRequest request) throws Exception {
	  		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
		    String[] frequency = timesheetFrequency.split("To");
		    String startDate = frequency[0].trim();
		    String endDate = frequency[1].trim();
		   // DateFormat format = Constants.DF_DMY;
		    //DateFormat  format = LoginSessionController.DATEFORMAT;
	        DateFormat shortormat = Constants.SHORT_DF_STD_DM;
	        Date actualStartDate=null;
		    Date actualEndDate=null;
		    try {
		   	 actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate.replaceAll("/", "-"));
		   	 actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate.replaceAll("/", "-"));
		    }catch (Exception e) {}
		    int tdSpanLength = 6;
		    StringBuilder timesheetApprovalDiv = new StringBuilder();
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(startDate, endDate,orgLocations.getDateFormat());
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
					tdSpanLength =tdSpanLength+1;
			        Date weekStartDate;
			        Date weekEndDate;
					try {
						 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
						 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
						 displayWeeksList.put(weekStartDate, weekEndDate);
					} catch (Exception e) {}
				}
			}
			
			
	    
		// timesheetApprovalDiv.append("<tr id='row").append(projectMemberId).append("'>");
			
		 timesheetApprovalDiv.append("  <td>");
		 timesheetApprovalDiv.append("<div class='time_appr_user'>");
		 timesheetApprovalDiv.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='").append(request.getContextPath()).append("/displayUserImage/").append("").append("' alt='Profile Image' class='img-circle img-responsive'>");
		 timesheetApprovalDiv.append("    <span><b  title='").append(projectMember.getProjectMemberCandidteId().getFullName()).append("'>").append(projectMember.getProjectMemberCandidteId().getFullName()).append("</b>")
		                                             .append("<b title='").append(projectMember.getProjectMemberCandidteId().getEmail()).append("'>").append(projectMember.getProjectMemberCandidteId().getEmail()).append("</b>")
		                                             .append("<b title='").append((projectMember.getProjectMemberCandidteId().getMobilePhone() != null) ?(projectMember.getProjectMemberCandidteId().getMobilePhone()) : "").append("'>").append((projectMember.getProjectMemberCandidteId().getMobilePhone() != null) ?(projectMember.getProjectMemberCandidteId().getMobilePhone()) : "").append("</b>")
		                                             .append("</span>");
		 timesheetApprovalDiv.append("   <div>");
		 timesheetApprovalDiv.append(" </td>");
		 
		 
		 if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
	    	 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
	    	 timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjectMemberCandidteId().getDesignationType()+","+orgRelation.getResponderLocationId().getOrgLocationName()).append("'>").append(projectMember.getProjectMemberCandidteId().getDesignationType()+"<br/>"+orgRelation.getResponderLocationId().getOrgLocationName()).append("</b></span></div></td>"); 
	     }else if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
	    	 timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjectMemberCandidteId().getDesignationType()).append("'>").append(projectMember.getProjectMemberCandidteId().getDesignationType()).append("</b></span></div></td>"); 
	     }else{
	    	 timesheetApprovalDiv.append("	<td><span><b title='").append("--").append("'>").append("--").append("</b></span></td>"); 
	     }
		 
	     timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</b></span></div></td>");
	     
	     if(null != projectMember.getProjectClientsId().getOrgRelationId()){
	    	 if(null != projectMember.getProjectClientsId().getOrgRelationId().getLocationId() && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() != null){
							timesheetApprovalDiv.append("<td><div>").append(projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getOrgLocationName()).append("</div></td>");
						}
					}
					if(null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() && null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId() && projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(projectMember.getProjectClientsId().getOrgRelationId().getLocationId() != null){
							timesheetApprovalDiv.append("<td><div>").append(projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getOrgLocationName()).append("</div></td>");
						}
					}
	     }else{
	    	 timesheetApprovalDiv.append("<td><div>").append("--").append("</div></td>");
	     }
	     
	     Float totalHours = 0f;
	     Float estimatedTotalHours = 0f;
	     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(user.getId(),projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId(),orgLocations.getId(),actualStartDate,actualEndDate);
	     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
	    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) {long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
			  	    	  Float weekTotal = 0f;
			  	    	  if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
			  	    		  weekTotal = (float) ((diff)*8);
			  	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			  	    	  }
			  	    	  else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
			  	    		  weekTotal = (float) ((diff-1)*8);
			  	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			  	    	  }
			  	    	  else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
			  	    		  weekTotal = (float) ((diff+1)*8);
			  	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
			  	    	  }
			  	    	 if(sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty() && sah.getWeekTotals().get(entry.getKey()) != null){
			  	    		 totalHours = CommonUtil.getCombinedHours(totalHours, sah.getWeekTotals().get(entry.getKey()));
			  	    		 timesheetApprovalDiv.append("<td><div class='sm'>").append(CommonUtil.getHoursDisplayFormat(sah.getWeekTotals().get(entry.getKey()))).append("/").append(CommonUtil.getHoursDisplayFormat(weekTotal)).append("</div></td>");
			  	    	 }
			  	    	 else{
			  	    		 timesheetApprovalDiv.append("<td><div class='sm'>0").append("/").append(CommonUtil.getHoursDisplayFormat(weekTotal)).append("</div></td>");
			  	    	 }
	    	   }
	     }

	
	    /*timesheetApprovalDiv.append("<td><div class='sm'><a href='javascript:void(0)'>").append(totalHours).append("/40</a></div></td>");*/
	     timesheetApprovalDiv.append("<td><div class='sm'><a href='#' onClick='timesheetApprovalPopUpByProjectMemberId(\""+startDate+"To"+endDate+"\" ,\""+projectMember.getId()+"\",\"approver\" );'>").append(CommonUtil.getHoursDisplayFormat(totalHours)).append("/").append(CommonUtil.getHoursDisplayFormat(estimatedTotalHours)).append("</a></div></td>");
	    
	     if(sah.getSubmissionApprovalStatus().getValue().contains("submitted for approval")){
	    	 timesheetApprovalDiv.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(Constants.submitForApproval).append("</div></td>"); 
	     }else if(sah.getSubmissionApprovalStatus().getValue().contains("approved")){
	    	 timesheetApprovalDiv.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(Constants.TSAPPROVED).append("</div></td>");
	     }else if(sah.getSubmissionApprovalStatus().getValue().contains("rejected")){
	    	 timesheetApprovalDiv.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(Constants.TSREJECTED).append("</div></td>");
	     }else if(sah.getSubmissionApprovalStatus().getValue().contains("new")){
	    	 timesheetApprovalDiv.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(Constants.TSNEW).append("</div></td>");
	     }else{
	     timesheetApprovalDiv.append("<td><div class='sm' id='status").append(projectMember.getId()).append("'>").append(sah.getSubmissionApprovalStatus()).append("</div></td>");
	     }
	     timesheetApprovalDiv.append("<td>");
	     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
	    	 timesheetApprovalDiv.append("<div class='sm text-center'>--</div>");
	     }
	     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
     	 timesheetApprovalDiv.append("<div class='sm text-center'id='action").append(projectMember.getId()).append("'> <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveSubmissionByProjectMemberId(\"")
     	                     .append(projectMember.getId()).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate)).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate)).
     	                     append("\",").append(tdSpanLength).append(",\"").append("approval").append("\");'></i>|<i class='fa fa-times' title='Reject' alt='Rejected' onclick='rejecteSubmissionByProjectMemberId(\"")
     	                     .append(projectMember.getId()).append("\",\"")
     	                      .append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate))
     	                     .append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate))
     	                     .append("\",").append(tdSpanLength).append(",\"").append("approval").append("\");'></i></div>");
        }
	   if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
     	 timesheetApprovalDiv.append(" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>");
     	 timesheetApprovalDiv.append(" <i class='glyphicon glyphicon-comment' id='").append(projectMember.getId()).append("' onclick='rejectedMessage(\"rejectedMessage").append(projectMember.getId()).append("\");'>");
     	 timesheetApprovalDiv.append(" <div class='app_error_mes' id='rejectedMessage").append(projectMember.getId()).append("'>").append(sah.getRejectedReason()).append("</div>");
     	 timesheetApprovalDiv.append(" </i><br>By : ").append(sah.getActionPerformedBy());
     	 timesheetApprovalDiv.append(" </div>");
        }
	     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED){
     	timesheetApprovalDiv.append(" <div class='sm text-center'> <h3>Approved</h3><br>By : ").append(sah.getActionPerformedBy()).append("</div>");
     }  
     timesheetApprovalDiv.append("    </td>");
    // timesheetApprovalDiv.append("</tr>");

	  
	  return timesheetApprovalDiv.toString();
	  
  }
	
	public StringBuilder timesheetApprovalTableDiv(HttpServletRequest request,String startDate,String endDate,String timesheetFrequency,String client,String stage) throws Exception{
		       
		        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		        //DateFormat format = Constants.DF_DMY;
		        //DateFormat  format = LoginSessionController.DATEFORMAT;
		        DateFormat shortormat = Constants.SHORT_DF_STD_DM;
		        Date actualStartDate=null;
			    Date actualEndDate=null;
			    try {
			    	 actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate.replaceAll("/", "-"));
			    	 actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate.replaceAll("/", "-"));
			    }catch (Exception e) {}
		        StringBuilder timesheetApprovalDiv = new StringBuilder();
		        Long clientId = null;
		        if(StringUtils.isNotEmpty(client) && !client.equalsIgnoreCase("All")){
		        	clientId =Long.parseLong(client);
		        }
		        
		        List<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, actualStartDate, actualEndDate, clientId, stage, orgLocations.getId(), user.getId());
		        HashSet<ProjectMembers> freshProjectMembers = new HashSet<>();
		        if(projectMembers != null && !projectMembers.isEmpty()){
		        	
		        	if(StringUtils.isNotEmpty(timesheetFrequency) && timesheetFrequency.equalsIgnoreCase("Bi-Weekly")){
			        	for(ProjectMembers pm :projectMembers){
			        		 Date biweeklyStrDate = null;
			        		/* if(actualStartDate.before(CommonUtil.currentDate()) || actualStartDate == CommonUtil.currentDate()){*/
			        			 biweeklyStrDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndEmployeeStartDate(pm.getProjects(),pm.getProjects().getStartDate());
			        		/* }
			        		 else{
			        			 biweeklyStrDate = timesheetApprovalFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(pm.getProjects());
			        		 }*/
			        		 if(CalendarUtil.getNumberOfDays(biweeklyStrDate, actualStartDate)%14==0){
			        	    	 freshProjectMembers.add(pm);
			        	     }
			        	
			        	}
		        	}
		        	 else{
				        	freshProjectMembers = new HashSet<>(projectMembers);
				        }
		         }
		        
		        if(freshProjectMembers != null && !freshProjectMembers.isEmpty()){
		        	int tdSpanLength = 6;
		        	timesheetApprovalDiv.append("			<div class='timesheet_appr_table overflow_inherint'>");
					timesheetApprovalDiv.append("	    	<table id='example' style='width:100%'>");
					timesheetApprovalDiv.append("				<thead>");   
					timesheetApprovalDiv.append("	            	<tr>");
					timesheetApprovalDiv.append("	                	<th>Employee</th>           ");
					timesheetApprovalDiv.append("	                	<th>Employment Type</th>           ");
					timesheetApprovalDiv.append("	                    <th>Project</th>            ");
					timesheetApprovalDiv.append("	                    <th>Client</th>             ");
					//timesheetApprovalDiv.append("	                    <th>Vendor</th>             ");
					
					LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
					Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(startDate, endDate,orgLocations.getDateFormat());
					if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
						for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
				    		 String  k = entry.getKey();
				    		 String  v = entry.getValue();
							tdSpanLength =tdSpanLength+1;
	 				        Date weekStartDate;
	 				        Date weekEndDate;
							try {
								 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
								 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
								 displayWeeksList.put(weekStartDate, weekEndDate);
								 if(weekStartDate.equals(weekEndDate)){
									 timesheetApprovalDiv.append("	                    <th>").append(shortormat.format(weekStartDate)).append("</th>  ");
								 }else{
									 timesheetApprovalDiv.append("	                    <th>").append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate)).append("</th>  ");
								 }
			   					
							} catch (Exception e) {}
						}
					}
					
					timesheetApprovalDiv.append("	                    <th>Total</th>              ");	
					timesheetApprovalDiv.append("	                    <th>Stage</th>              ");
					timesheetApprovalDiv.append("	                    <th>Action</th>             ");
					
					timesheetApprovalDiv.append("	                </tr>");
					timesheetApprovalDiv.append("				</thead>");
					timesheetApprovalDiv.append("	        	<tbody>");
					for (ProjectMembers projectMember : projectMembers)
					{
						 ProjectMembers pm = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
						 timesheetApprovalDiv.append("<tr id='row").append(projectMember.getId()).append("'>");
						 timesheetApprovalDiv.append("  <td>");
						 timesheetApprovalDiv.append("<div class='time_appr_user'>");
						 timesheetApprovalDiv.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='").append(pm.getProjectMemberCandidteId().getConsultantUser().getFileUrl()).append("' alt='Profile Image' class='img-circle img-responsive'>");
						 timesheetApprovalDiv.append("    <span><b title='").append(pm.getProjectMemberCandidteId().getConsultantUser().getFullName()).append("'>").append(pm.getProjectMemberCandidteId().getConsultantUser().getFullName()).append("</b>")
						                                             .append("<b title='").append(pm.getProjectMemberCandidteId().getConsultantUser().getEmail()).append("'>").append(pm.getProjectMemberCandidteId().getConsultantUser().getEmail()).append("</b>")
						                                             .append("<b title='").append((pm.getProjectMemberCandidteId().getConsultantUser().getMobilePhone() != null) ?(pm.getProjectMemberCandidteId().getConsultantUser().getMobilePhone()) : "").append("'>").append((pm.getProjectMemberCandidteId().getConsultantUser().getMobilePhone() != null) ?(pm.getProjectMemberCandidteId().getConsultantUser().getMobilePhone()) : "").append("</b>")
						                                             .append("</span>");
						 timesheetApprovalDiv.append("   <div>");
						 timesheetApprovalDiv.append(" </td>");
						 
						 if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
					    	 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(projectMember.getEmployee().getOrgLocations().getId(), projectMember.getEmployee().getOrgRelationId().getId());
					    	 timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjectMemberCandidteId().getDesignationType()+","+orgRelation.getResponderLocationId().getOrgLocationName()).append("'>").append(projectMember.getProjectMemberCandidteId().getDesignationType()+"<br/>"+orgRelation.getResponderLocationId().getOrgLocationName()).append("</b></span></div></td>"); 
					     }else if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
					    	 timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjectMemberCandidteId().getDesignationType()).append("'>").append(projectMember.getProjectMemberCandidteId().getDesignationType()).append("</b></span></div></td>"); 
					     }else{
					    	 timesheetApprovalDiv.append("	<td><span><b title='").append("--").append("'>").append("--").append("</b></span></td>"); 
					     }
						 
					     timesheetApprovalDiv.append("	<td><div class='tbldata-dotdot-sm'><span><b title='").append(projectMember.getProjects().getProjectName()).append("'>").append(projectMember.getProjects().getProjectName()).append("</b></span></div></td>");
					     
					     if(null != projectMember.getProjectClientsId().getOrgRelationId()){
					    	 if(null != projectMember.getProjectClientsId().getOrgRelationId().getLocationId() && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
		     						if(projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() != null){
		     							timesheetApprovalDiv.append("<td><div>").append(projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getOrgLocationName()).append("</div></td>");
		     						}
		     					}
		     					if(null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() && null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId() && projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
		     						if(projectMember.getProjectClientsId().getOrgRelationId().getLocationId() != null){
		     							timesheetApprovalDiv.append("<td><div>").append(projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getOrgLocationName()).append("</div></td>");
		     						}
		     					}
					     }else{
					    	 timesheetApprovalDiv.append("<td><div>").append("--").append("</div></td>");
					     }
					     
					     Float totalHours = 0f;
					     Float estimatedTotalHours = 0f;
					     SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(user.getId(),projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId(),orgLocations.getId(),actualStartDate,actualEndDate);
					     if(displayWeeksList != null && !displayWeeksList.isEmpty()){
					    	    for (Map.Entry<Date,Date> entry : displayWeeksList.entrySet()) { 
					    	    	  long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
					    	    	  Float weekTotal = 0f;
					    	    	  if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
					    	    		  weekTotal = (float) ((diff)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	  else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
					    	    		  weekTotal = (float) ((diff-1)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	  else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
					    	    		  weekTotal = (float) ((diff+1)*8);
					    	    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
					    	    	  }
					    	    	 if(sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty() && sah.getWeekTotals().get(entry.getKey()) != null){
					    	    		 totalHours = CommonUtil.getCombinedHours(totalHours, sah.getWeekTotals().get(entry.getKey()));
					    	    		 timesheetApprovalDiv.append("<td><div class='sm'>").append(CommonUtil.getHoursDisplayFormat(sah.getWeekTotals().get(entry.getKey()))).append("/").append(weekTotal).append("</div></td>");
					    	    	 }
					    	    	 else{
					    	    		 timesheetApprovalDiv.append("<td><div class='sm'>0").append("/").append(weekTotal).append("</div></td>");
					    	    	 }
					    	    	
					    	     }
					     }
					     
					     
					     
					     timesheetApprovalDiv.append("<input type='hidden' name='totalHours'  id='totalHours' value='"+totalHours+"' >");
					    /*timesheetApprovalDiv.append("<td><div class='sm'><a href='javascript:void(0)'>").append(totalHours).append("/40</a></div></td>");*/
					    timesheetApprovalDiv.append("<td><div class='sm'><a href='#' onClick='timesheetApprovalPopUpByProjectMemberId(\""+startDate+"To"+endDate+"\" ,\""+projectMember.getId()+"\",\"approver\");'>").append(CommonUtil.getHoursDisplayFormat(totalHours)).append("/").append(estimatedTotalHours).append("</a></div></td>");
					    if(sah.getSubmissionApprovalStatus().getValue().contains("submitted for approval")){
					    	 timesheetApprovalDiv.append("<td><div class='sm' title ='"+Constants.submitForApproval+"' id='status").append(projectMember.getId()).append("'>").append(Constants.submitForApproval).append("</div></td>"); 
					     }else if(sah.getSubmissionApprovalStatus().getValue().contains("approved")){
					    	 timesheetApprovalDiv.append("<td><div class='sm' title ='"+Constants.TSAPPROVED+"' id='status").append(projectMember.getId()).append("'>").append(Constants.TSAPPROVED).append("</div></td>");
					     }else if(sah.getSubmissionApprovalStatus().getValue().contains("rejected")){
					    	 timesheetApprovalDiv.append("<td><div class='sm' title ='"+Constants.TSREJECTED+"' id='status").append(projectMember.getId()).append("'>").append(Constants.TSREJECTED).append("</div></td>");
					     }else if(sah.getSubmissionApprovalStatus().getValue().contains("new")){
					    	 timesheetApprovalDiv.append("<td><div class='sm' title ='"+Constants.TSNEW+"' id='status").append(projectMember.getId()).append("'>").append(Constants.TSNEW).append("</div></td>");
					     }else{
					    timesheetApprovalDiv.append("<td><div class='sm'title ='"+sah.getSubmissionApprovalStatus()+"' id='status").append(projectMember.getId()).append("'>").append(sah.getSubmissionApprovalStatus()).append("</div></td>");
					    }timesheetApprovalDiv.append("<td>");
					     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.NEW){
					    	 timesheetApprovalDiv.append("<div class='sm text-center'>--</div>");
					     }
					     if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
			            	 timesheetApprovalDiv.append("<div class='sm text-center'id='action").append(projectMember.getId()).append("'> <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveSubmissionByProjectMemberId(\"")
			            	                     .append(projectMember.getId()).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate)).append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate)).
			            	                     append("\",").append(tdSpanLength).append(",\"").append("approval").append("\");'></i>|<i class='fa fa-times' title='Reject' alt='Rejected' onclick='rejecteSubmissionByProjectMemberId(\"")
			            	                     .append(projectMember.getId()).append("\",\"")
			            	                      .append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate))
			            	                     .append("\",\"").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate))
			            	                     .append("\",").append(tdSpanLength).append(",\"").append("approval").append("\");'></i></div>");
			             }
					      if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
			            	 timesheetApprovalDiv.append(" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>");
			            	 timesheetApprovalDiv.append(" <i class='glyphicon glyphicon-comment' id='").append(projectMember.getId()).append("' onclick='rejectedMessage(\"rejectedMessage").append(projectMember.getId()).append("\");'>");
			            	 timesheetApprovalDiv.append(" <div class='app_error_mes' id='rejectedMessage").append(projectMember.getId()).append("'>").append(sah.getRejectedReason()).append("</div>");
			            	 timesheetApprovalDiv.append(" </i>");
			            	 timesheetApprovalDiv.append(" <b class='Rname' title='"+sah.getActionPerformedBy()+"'>By : ").append(sah.getActionPerformedBy());
			            	 timesheetApprovalDiv.append(" </b>");
			            	 timesheetApprovalDiv.append(" </div>");
			            }
					     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED){
			            	timesheetApprovalDiv.append(" <div class='sm text-center'> <h3>Approved</h3><b class='Rname' title='"+sah.getActionPerformedBy()+"'>By : ").append(sah.getActionPerformedBy()).append("</b></div>");
			            }  
			            timesheetApprovalDiv.append("    </td>");
				        timesheetApprovalDiv.append("</tr>");
				  }
				
				  timesheetApprovalDiv.append("	        </tbody>");
				  timesheetApprovalDiv.append("	     </table> ");
			      timesheetApprovalDiv.append("	</div>");
				}
		        else{
		        	timesheetApprovalDiv.append("   <div class='dataTables_empty'>"); 
		        	timesheetApprovalDiv.append("  <input type='hidden' id='hideExportApproval' value='EmptyList'>");
					/*timesheetApprovalDiv.append("	<div class='col-lg-12'>"); 
					timesheetApprovalDiv.append("		<div class='content-area clearfix'>"); 
					timesheetApprovalDiv.append("			<div class='blank-pages'>");
					timesheetApprovalDiv.append("			        <p><i class='fa fa-5x fa-bar-chart'></i></p>");
					timesheetApprovalDiv.append("			        <p>No Timesheets Found</p>");
					timesheetApprovalDiv.append("			 </div>");
					timesheetApprovalDiv.append("		</div>"); 
					timesheetApprovalDiv.append("	</div>"); */
					timesheetApprovalDiv.append("  </div> "); 
		        }
	    
	    return timesheetApprovalDiv;
	}
	
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	
@RequestMapping(value = "/getExportTimeSheetForApprovalMechanizm")  
	public void getExportTimeSheetForApprovalMechanizm(HttpServletRequest request,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="exportType", required=false) String exportType,
			@RequestParam(value="client", required=false) String client,
			@RequestParam(value="stage", required=false) String stage,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="frequencyMonth", required=false) String frequencyMonth,
			@RequestParam(value="weekFrequency", required=false) String weekFrequency,
			HttpServletResponse response) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		//DateFormat df = Constants.DMY;
		//DateFormat df = LoginSessionController.DATEFORMAT;
		Date stDate = null;
		Date edDate= null;
		if(null !=startDate && !("undefined").equalsIgnoreCase(startDate) && null !=endDate && !("undefined").equalsIgnoreCase(endDate)){
		 stDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate);
		 edDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
		}
		File file = null;
		
		  if(timesheetFrequency.equalsIgnoreCase("Monthly")){
			  Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthString(frequencyMonth.toString());
			  
			  stDate = dateList.get("STARTDATE");
			  edDate = dateList.get("ENDDATE");
		  }
		 if("pdf".equalsIgnoreCase(exportType)){
			file = exportTimesheetEntries.timeSheetApprovelExport(user, orgLocations, stDate, edDate, timesheetFrequency, client, stage,frequencyMonth,weekFrequency);
	    }else{
			file = exportTimesheetEntries.timeSheetApprovelExportExcel(user, orgLocations, stDate, edDate, timesheetFrequency, client, stage,frequencyMonth,weekFrequency);
		}
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
	}
	

}