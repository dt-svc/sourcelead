package com.bis.sourcelead.timesheet.rest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetApprovalDTO;
import com.bis.sourcelead.dto.TimesheetApprovalSubmissionDetailsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.TimesheetEntriesDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.timesheet.service.impl.TimeSheetExcelView;
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
	public ProjectsDao projectsDao;
	
	@Autowired
	public ProjectClientService projectClientService;
	
	@Autowired
	public ProjectTasksService tasksService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
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
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
		 
		@RequestMapping(value = "/rest/timesheetApprovalDetails", method = RequestMethod.POST ) 
		public TimesheetApprovalDTO timesheetApprovalDetailsMobile(@RequestBody TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
			return timesheetApprovalDetails(timesheetApprovalDTO, request);
		}
		
		@RequestMapping(value = "/timesheetApprovalDetails", method = RequestMethod.POST ) 
		public ModelAndView timesheetApprovalDetailsWeb(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			try {
				timesheetApprovalDTO = timesheetApprovalDetails(timesheetApprovalDTO,request);
				model.addObject("timesheetApproval", timesheetApprovalDTO);
				model.setViewName("timesheet/timesheetApprovalDetils");
			}catch (Exception e){
				e.printStackTrace();
				model.addObject("AWKWARD", true);
				model.setViewName("timesheet/timesheetApprovalDetils");
			}
			return model;
		
		}
		private TimesheetApprovalDTO timesheetApprovalDetails(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 if(orgLocations == null && StringUtils.isNotEmpty(timesheetApprovalDTO.getLocationCode())){
				orgLocations = orgLocationService.getOrgLocationsByLocationCode(timesheetApprovalDTO.getLocationCode());
			 }
			LinkedHashSet<String> datesList = timesheetApprovalFrequencyGeneratorUtil.getMonthsListFromCurrentDate(4, 2);
			timesheetApprovalDTO.setFrequencyMonthList(datesList);
			if(StringUtils.isEmpty(timesheetApprovalDTO.getFrequencyMonth())) {
				for (String dateStr : datesList) {
				    String currentDate = CalendarUtil.getMonthAndYearPatternDateByMonthPreference(CommonUtil.currentDate(),0);
				    if(StringUtils.isEmpty(timesheetApprovalDTO.getFrequencyMonth()) && currentDate.equalsIgnoreCase(dateStr)){
				    	timesheetApprovalDTO.setFrequencyMonth(dateStr);
				    }
				    else if(StringUtils.isNotEmpty(timesheetApprovalDTO.getFrequencyMonth()) && timesheetApprovalDTO.getFrequencyMonth().equalsIgnoreCase(dateStr)){
				    	timesheetApprovalDTO.setFrequencyMonth(timesheetApprovalDTO.getFrequencyMonth());
				    }
			     }
			}
			LinkedHashSet<String> timesheetFrequencyList = new LinkedHashSet<>();
			timesheetFrequencyList.add("Weekly");
			timesheetFrequencyList.add("Bi-Weekly");
			timesheetFrequencyList.add("Semi-Monthly");
			timesheetFrequencyList.add("Monthly");
			timesheetApprovalDTO.setTimesheetFrequencyList(timesheetFrequencyList);
			if(StringUtils.isEmpty(timesheetApprovalDTO.getTimesheetFrequency())){
				timesheetApprovalDTO.setTimesheetFrequency("Weekly");
            }
			
			timesheetApprovalDTO =  getWeekFrequncyDropDown(timesheetApprovalDTO,request);
			timesheetApprovalDTO = getClientList(request,timesheetApprovalDTO);
			timesheetApprovalDTO = getStagesList(request,timesheetApprovalDTO);
			timesheetApprovalDTO = dataTableWeekDateList(request,timesheetApprovalDTO,orgLocations);
			timesheetApprovalDTO = timesheetEmployeeSubmissionDetails(request,timesheetApprovalDTO);
			return timesheetApprovalDTO;
		}
		
		
		@RequestMapping(value="/getTimesheetFrequencyForFilter",method= RequestMethod.POST)
		public ModelAndView  getTimesheetFrequencyForFilter(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception{
			ModelAndView model = new ModelAndView();
			timesheetApprovalDTO = getWeekFrequncyDropDown(timesheetApprovalDTO, request);
			model.addObject("timesheetApproval",timesheetApprovalDTO);
			model.setViewName("timesheet/timesheetApprovalWeekFrequencyFilter");
			return model;
			
		}
		
		private TimesheetApprovalDTO  getWeekFrequncyDropDown(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception{
		     User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(loginUser == null && timesheetApprovalDTO.getUserId() != null){
				loginUser = userService.getUserById(timesheetApprovalDTO.getUserId());
			 }
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 if(orgLocations == null && StringUtils.isNotEmpty(timesheetApprovalDTO.getLocationCode())){
				orgLocations = orgLocationService.getOrgLocationsByLocationCode(timesheetApprovalDTO.getLocationCode());
			 }
			 Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthString(timesheetApprovalDTO.getFrequencyMonth());
			 String currentTimesheetFrequency = null;
			 if(!timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Monthly")){
	           	 String weekStartDate = null;
	           	 String weekEndDate =  null;
	           	 if(StringUtils.isNotEmpty(timesheetApprovalDTO.getWeekFrequency())){
	           		 String[] frequency = timesheetApprovalDTO.getWeekFrequency().split("To");
	           		 weekStartDate = frequency[0].trim();
	       		  	 weekEndDate = frequency[1].trim();
	           	 }
           	 
	           	 
	           	 Date startDate = dateList.get("STARTDATE");
	    	     Date endDate = dateList.get("ENDDATE");
	    	     Date sDate = null;
	    	     Date eDate = null;
	             if(StringUtils.isNotEmpty(weekStartDate) && StringUtils.isNotEmpty(weekEndDate))
	    		 {
	    			 sDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDate.replaceAll("/", "-"));
	    			 eDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekEndDate.replaceAll("/", "-"));
	    		 }
	    		 
	    	     Map<String,String> frequencyDropDown = new LinkedHashMap<>();
	    	     if(timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Semi-monthly"))      
	            {
	    	    	 HashMap<String,Date> tab1to15Frequency =  CalendarUtil.getMonthStartDateAnd15thDateByGivenDateAndMothPreference(startDate,0);
	    	    	 frequencyDropDown.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab1to15Frequency.get("STARTDATE")), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab1to15Frequency.get("ENDDATE")));
	    			 
	    	    	 HashMap<String,Date> tab16toEndFrequency = CalendarUtil.getMonth16thDateAndEndDateByGivenDateAndMothPreference(startDate,0);
	    	    	 frequencyDropDown.put(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab16toEndFrequency.get("STARTDATE")), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tab16toEndFrequency.get("ENDDATE")));
	            }
	    	     else if(timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Weekly") || timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Bi-Weekly")){
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
	    			 if(timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Weekly")){
	    				 endDay = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "SUN",orgLocations.getDateFormat());
	    			 }
	    			 else {
	    				 endDay = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON",orgLocations.getDateFormat());
	    			 }
	    			 if(timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Weekly")){
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
	    	        
	 			    if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
	 			    	int  count = 1;
	 			    	LinkedHashMap<String,String> dateFrequencyList = new LinkedHashMap<>();
	 			    	for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
	 			    		 String  k = entry.getKey();
	 			    		 String  v = entry.getValue();
	 			    		 Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
	 			    		 Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
	 			    		try {
	 			    			if(sDate == null && eDate == null&& count == 1
	 			    				&& !CalendarUtil.isCurrentDateBetweenDateRange(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),startDate), commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate),orgLocations.getDateFormat())){
	 			    				 dateList.clear();
	                           	     dateList.put("STARTDATE", frequencyStartDate);
	 							     dateList.put("ENDDATE",frequencyEndDate);
	 							     currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
	 			    			}
	 			    			else if(sDate == null && eDate == null
	 			    					 && CalendarUtil.isCurrentDateBetweenDateRange(k, v,orgLocations.getDateFormat())){
	                            	     dateList.clear();
	                            	     dateList.put("STARTDATE",frequencyStartDate);
	 							         dateList.put("ENDDATE", frequencyEndDate);
	 							         currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
	                             }
	 			    			else if(sDate != null && eDate != null 
	 			    					&& sDate.equals(frequencyStartDate) && eDate.equals(frequencyEndDate))
	 			    			{
	 			    				 dateList.clear();
	                           	     dateList.put("STARTDATE",frequencyStartDate);
	 							     dateList.put("ENDDATE", frequencyEndDate);
	 							     currentTimesheetFrequency = k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-");
	 			    			}
	 					      } catch (Exception e) {}
	 			    	     count++;
	 			    	    if(k.equalsIgnoreCase(v)){
	 			    	    	dateFrequencyList.put(k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-"), k.replaceAll("/", "-"));
				    	     }else{
				    	    	 dateFrequencyList.put(k.replaceAll("/", "-")+"To"+v.replaceAll("/", "-"),  k.replaceAll("/", "-")+" To "+v.replaceAll("/", "-"));
				    	     }
	 			    	    timesheetApprovalDTO.setWeekFrequencyList(dateFrequencyList);
	 			    	}
	 			    } 
			 }	
			 if(!timesheetApprovalDTO.getTimesheetFrequency().equalsIgnoreCase("Monthly")) {
				 timesheetApprovalDTO.setWeekFrequency(currentTimesheetFrequency);
			 }
			 else {
				 timesheetApprovalDTO.setWeekFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("STARTDATE"))+"To"+commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("ENDDATE")));
			 }
			 timesheetApprovalDTO.setFrequencyStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("STARTDATE")));
			 timesheetApprovalDTO.setFrequencyEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),dateList.get("ENDDATE")));
			 return timesheetApprovalDTO;
		}
		
		private TimesheetApprovalDTO getClientList(HttpServletRequest request, TimesheetApprovalDTO timesheetApprovalDTO) throws Exception
		{
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
			 List<ProjectClients> projectClientList = projectClientService.getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(orgLocations.getId(), user.getId(),isGlobalUser);
			 Map<Long,String> clientMap = new HashMap<>();
	                if(projectClientList != null && !projectClientList.isEmpty()){
	                	    projectClientList.forEach(projectClient ->{
	               	        	if(projectClient.getOrgRelationId() != null && projectClient.getOrgRelationId().getLocationId() != null && projectClient.getOrgRelationId().getLocationId().getId() != null && projectClient.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
	        						if(projectClient.getOrgRelationId().getResponderLocationId() != null){
	        							clientMap.put(projectClient.getOrgRelationId().getId(),projectClient.getOrgRelationId().getResponderLocationId().getOrgLocationName());
	        						}
	        					}
	        					if( projectClient.getOrgRelationId() != null && null != projectClient.getOrgRelationId().getResponderLocationId() && null != projectClient.getOrgRelationId().getResponderLocationId().getId() && projectClient.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
	        						if(projectClient.getOrgRelationId().getLocationId() != null){
	        							clientMap.put(projectClient.getOrgRelationId().getId(),projectClient.getOrgRelationId().getLocationId().getOrgLocationName());
	        						}
	        					}
	               	        });
	                	    
	                	    if(clientMap != null && !clientMap.isEmpty()) {
	                	    	 clientMap.put(null, "All");
	                	    }
	                	    timesheetApprovalDTO.setClientList(clientMap);
	                	    
								
	               }
	               /* Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
					String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
					 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");*/
					 List<Projects> projectsList = new ArrayList<>();
					 if(projectClientList != null && !projectClientList.isEmpty()) {
						 projectsList =  projectClientList.stream().map(client->client.getProjectId()).collect(Collectors.toList());
					 }
	                LinkedHashMap<Long,String> projectMap = new LinkedHashMap<>();
	                if(projectsList != null && !projectsList.isEmpty()){
	                	projectsList.forEach(project ->{
	                		projectMap.put(project.getId(), project.getProjectName());
	                	});
	                	timesheetApprovalDTO.setProjectList(projectMap);
	                }
	               return timesheetApprovalDTO;
		}
		
		
		private TimesheetApprovalDTO getStagesList(HttpServletRequest request, TimesheetApprovalDTO timesheetApprovalDTO) throws Exception{
			LinkedHashSet<String> timesheetStageList = new LinkedHashSet<>();
			timesheetStageList.add("All");
			timesheetStageList.add("NEW");
			timesheetStageList.add("SUBMITTED FOR APPROVAL");
			timesheetStageList.add("APPROVED");
			timesheetStageList.add("REJECTED");
			timesheetApprovalDTO.setStagesList(timesheetStageList);
			if(StringUtils.isEmpty(timesheetApprovalDTO.getStage())){
				timesheetApprovalDTO.setStage("SUBMITTED FOR APPROVAL");
			 }else {
				 timesheetApprovalDTO.setStage(timesheetApprovalDTO.getStage()); 
			 }
			return timesheetApprovalDTO;
			
		}
		
		
		private TimesheetApprovalDTO dataTableWeekDateList(HttpServletRequest request, TimesheetApprovalDTO timesheetApprovalDTO,OrgLocations orgLocations) throws Exception {
			LinkedHashMap<Date, Date> displayWeeksList = new LinkedHashMap<>();
			Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(timesheetApprovalDTO.getFrequencyStartDate(), timesheetApprovalDTO.getFrequencyEndDate(),orgLocations.getDateFormat());
			if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
				int tdSpanLength = 6;
				DateFormat shortormat = Constants.SHORT_DF_STD_DM;
				List<String> dateTableWeek = new ArrayList<>();
				for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
		    		 String  k = entry.getKey();
		    		 String  v = entry.getValue();
		    		 tdSpanLength = tdSpanLength+1;
				        Date weekStartDate;
				        Date weekEndDate;
					try {
						 weekStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),k.replaceAll("/", "-"));
						 weekEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),v.replaceAll("/", "-"));
						 displayWeeksList.put(weekStartDate, weekEndDate);
						 if(weekStartDate.equals(weekEndDate)){
							 dateTableWeek.add(shortormat.format(weekStartDate));
						 }else{
							 StringBuilder tableDateList = new StringBuilder();
							 dateTableWeek.add(tableDateList.append(shortormat.format(weekStartDate)).append("-").append(shortormat.format(weekEndDate)).toString());
						 }
	   					
					} catch (Exception e) {}
				}
				timesheetApprovalDTO.setDataTableWeekDateList(dateTableWeek);
				timesheetApprovalDTO.setDisplayWeekList(displayWeeksList);
			}
			return timesheetApprovalDTO;
		}
		
		@RequestMapping(value = "/timesheetEmployeeSubmissionDetails", method = RequestMethod.POST ) 
		public ModelAndView timesheetEmployeeSubmissionDetailsWeb(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			try {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 if(orgLocations == null && StringUtils.isNotEmpty(timesheetApprovalDTO.getLocationCode())){
					orgLocations = orgLocationService.getOrgLocationsByLocationCode(timesheetApprovalDTO.getLocationCode());
				 }
				 
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 String isGlobalUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
				 List<ProjectClients> projectClientList = projectClientService.getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(orgLocations.getId(), user.getId(),isGlobalUser);
				 List<Projects> projectsList = new ArrayList<>();
				 if(projectClientList != null && !projectClientList.isEmpty()) {
					 projectsList =  projectClientList.stream().map(client->client.getProjectId()).collect(Collectors.toList());
				 }
                LinkedHashMap<Long,String> projectMap = new LinkedHashMap<>();
   	 		    projectMap.put(null, "All");
                if(projectsList != null && !projectsList.isEmpty()){
                	projectsList.forEach(project ->{
                		projectMap.put(project.getId(), project.getProjectName());
                	});
                	timesheetApprovalDTO.setProjectList(projectMap);
                }
				timesheetApprovalDTO = dataTableWeekDateList(request, timesheetApprovalDTO,orgLocations);
				timesheetApprovalDTO = timesheetEmployeeSubmissionDetails(request,timesheetApprovalDTO);
				model.addObject("timesheetApproval", timesheetApprovalDTO);
				model.setViewName("timesheet/timesheetApprovalTableData");
			}catch (Exception e){
				e.printStackTrace();
				model.addObject("AWKWARD", true);
				model.setViewName("timesheet/timesheetApprovalTableData");
			}	
			return model;
			
		}
		private TimesheetApprovalDTO timesheetEmployeeSubmissionDetails(HttpServletRequest request, TimesheetApprovalDTO timesheetApprovalDTO) throws Exception {
			int start = timesheetApprovalDTO.getStart();
			int length = 0;
			if(timesheetApprovalDTO.getPageNo()<1) {
				timesheetApprovalDTO.setPageNo(1);
			}
			if(timesheetApprovalDTO.getPageSize()<1) {
				length = 5;
				timesheetApprovalDTO.setPageSize(5);
			}else {
				length = timesheetApprovalDTO.getPageSize();
			}
			
			OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(timesheetApprovalDTO.getLocationCode());
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
			
			Date actualStartDate=null;
		    Date actualEndDate=null;
		    try {
		    	 actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetApprovalDTO.getFrequencyStartDate().replaceAll("/", "-"));
		    	 actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetApprovalDTO.getFrequencyEndDate().replaceAll("/", "-"));
		    }catch (Exception e) {}
	        Long clientId = null;
	        Long projectId = null;
	        if(StringUtils.isNotEmpty(timesheetApprovalDTO.getClient()) && !timesheetApprovalDTO.getClient().equalsIgnoreCase("undefined") && !timesheetApprovalDTO.getClient().equalsIgnoreCase("All")){
	        	clientId =Long.parseLong(timesheetApprovalDTO.getClient());
	        }
	        if(StringUtils.isNotEmpty(timesheetApprovalDTO.getProject()) && !timesheetApprovalDTO.getProject().equalsIgnoreCase("undefined")  && !timesheetApprovalDTO.getProject().equalsIgnoreCase("All")){
	        	projectId =Long.parseLong(timesheetApprovalDTO.getProject());
	        }
	        ResponseList<ProjectMembers> projectMembers = projectMembersService.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetApprovalDTO.getTimesheetFrequency(), actualStartDate, actualEndDate, clientId,projectId, timesheetApprovalDTO.getStage(), orgLocations.getId(), user.getId(),timesheetApprovalDTO.getSearchValue(),timesheetApprovalDTO.getProjectName(),start,length,"",isGlobalAccess);
	        
	        timesheetApprovalDTO.setTotalRows(projectMembers.getRecordsTotal());
			
			int totalRows = projectMembers.getRecordsTotal();
			if(totalRows > 0) {
				 int remainder = totalRows % length;
				 int pageCount = totalRows / length;
				 if(remainder > 0) {
					 timesheetApprovalDTO.setPageCount(pageCount+1);
				 }
				 else {
					 timesheetApprovalDTO.setPageCount(pageCount);
				 }
			}
	        if(projectMembers.getData() != null && !projectMembers.getData().isEmpty()){
	        	int tdSpanLength = 6;
	        	List<TimesheetApprovalSubmissionDetailsDTO> subDetails = new ArrayList<>();
	         for (ProjectMembers projectMember : projectMembers.getData()){
	        	TimesheetApprovalSubmissionDetailsDTO ts = 	getTimesheetApprovalRowDetails(timesheetApprovalDTO, projectMember, orgLocations, actualStartDate, actualEndDate, user, tdSpanLength,isGlobalAccess);
	        	 ts.setTableFor("approver");
	        	subDetails.add(ts);
	         }
	         timesheetApprovalDTO.setTimesheetSubmission(subDetails);
	       }
	        return timesheetApprovalDTO;
	   }
		
	@RequestMapping(value = "/timesheetApprovalRowDetails", method = RequestMethod.POST ) 
	public ModelAndView timesheetApprovalRowDetailsWeb(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		try {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
	    if(orgLocations == null && StringUtils.isNotEmpty(timesheetApprovalDTO.getLocationCode())){
		  orgLocations = orgLocationService.getOrgLocationsByLocationCode(timesheetApprovalDTO.getLocationCode());
	    }
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 if(loginUser == null && timesheetApprovalDTO.getUserId() != null){
			loginUser = userService.getUserById(timesheetApprovalDTO.getUserId());
		 }
		timesheetApprovalDTO = dataTableWeekDateList(request, timesheetApprovalDTO,orgLocations);
		Date actualStartDate=null;
	    Date actualEndDate=null;
	    try {
	    	 actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetApprovalDTO.getFrequencyStartDate().replaceAll("/", "-"));
	    	 actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetApprovalDTO.getFrequencyEndDate().replaceAll("/", "-"));
	    }catch (Exception e) {}
	    timesheetApprovalDTO.setWeekFrequency(timesheetApprovalDTO.getFrequencyStartDate().trim()+"To"+timesheetApprovalDTO.getFrequencyEndDate().trim());
	    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(timesheetApprovalDTO.getProjectMembersId());
	    TimesheetApprovalSubmissionDetailsDTO submissionDTO = getTimesheetApprovalRowDetails(timesheetApprovalDTO,projectMembers,orgLocations,actualStartDate,actualEndDate,loginUser,6,isGlobalAccess);
	    if(StringUtils.isNotEmpty(timesheetApprovalDTO.getTableRowFor()) && (timesheetApprovalDTO.getTableRowFor().equalsIgnoreCase("approver") || timesheetApprovalDTO.getTableRowFor().equalsIgnoreCase("approverClose"))){
	    	submissionDTO.setTableFor("approver");
	    }
	    else{
	    	submissionDTO.setDashboardInvoiceFrequency(projectMembers.getProjects().getInvoiceFrequency());
	    	submissionDTO.setCurrentTimeFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate)+"To"+commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate));
	    	submissionDTO.setDashboardDateList(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate)+" To "+commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate));
	    	submissionDTO.setTableFor("dashboard");
	    }
	    
	    model.addObject("timesheetSubmission", submissionDTO);
		model.addObject("timesheetApprovalDTO", timesheetApprovalDTO);
		model.setViewName("timesheet/timesheetApprovalTableRowData");
		}catch (Exception e) {
			model.addObject("AWKWARD", true);
			model.setViewName("timesheet/timesheetApprovalTableRowData");
		}
		return model;
		
	}
	 private TimesheetApprovalSubmissionDetailsDTO getTimesheetApprovalRowDetails(TimesheetApprovalDTO timesheetApprovalDTO,ProjectMembers projectMember,OrgLocations orgLocations,Date actualStartDate,Date actualEndDate,User loginUser,int tdSpanLength,String isglobalUser) throws Exception {
			 TimesheetApprovalSubmissionDetailsDTO ts = new TimesheetApprovalSubmissionDetailsDTO();
			 ProjectMembers pm = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
			 String fileUrl = "";
			 if(pm != null && pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getConsultantUser() != null)
				 fileUrl = pm.getProjectMemberCandidteId().getConsultantUser().getFileUrl();
			
			 ts.setProfileImageFileUrl(fileUrl);
			 ts.setProjectMemberId(projectMember.getId());
			 ts.setEmployeeName(projectMember.getProjectMemberCandidteId().getFullName());
			 if(projectMember.getProjectMemberCandidteId().getConsultantUser() != null) {
				 ts.setEmployeeEmail(projectMember.getProjectMemberCandidteId().getConsultantUser().getEmail());
			 }else {
				ts.setEmployeeEmail(projectMember.getProjectMemberCandidteId().getEmail()); 
			 }
			 ts.setEmployeePhoneNo(projectMember.getProjectMemberCandidteId().getMobilePhone());
			 timesheetApprovalDTO.setWeekFrequency(timesheetApprovalDTO.getFrequencyStartDate().trim()+"To"+timesheetApprovalDTO.getFrequencyEndDate().trim());
			 if(null != projectMember.getProjectMemberCandidteId() && null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType()) && (projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C-1099")||projectMember.getProjectMemberCandidteId().getDesignationType().equalsIgnoreCase("C2C"))){
		   	 OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
		   	 if(orgRelation != null){
		   		 ts.setDesignation(projectMember.getProjectMemberCandidteId().getDesignationType());
		   		 ts.setVendorOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
		   	 }
		   	 else{
		   		 ts.setDesignation(projectMember.getProjectMemberCandidteId().getDesignationType()); 
		   	 }
		   	
		    }else if(null != projectMember.getProjectMemberCandidteId() && null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
		   	 ts.setDesignation(projectMember.getProjectMemberCandidteId().getDesignationType());  
		    }else{
		   	 ts.setDesignation("--"); 
		    }
			 ts.setMasterCandidateId(projectMember.getProjectMemberCandidteId().getMasterCandidateId().getId());
			 ts.setProjectId(projectMember.getProjects().getId());
			 ts.setProject(projectMember.getProjects().getProjectName());
		    
		    if(null != projectMember.getProjectClientsId().getOrgRelationId()){
		    	 OrgRelations orgRelations = orgRelationsService.getOrgRelationById(projectMember.getProjectClientsId().getOrgRelationId().getId());
		   	 if(null != projectMember.getProjectClientsId().getOrgRelationId().getLocationId() && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId() != null && projectMember.getProjectClientsId().getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(orgRelations.getResponderLocationId() != null){
							ts.setClientName(orgRelations.getResponderLocationId().getOrgLocationName());
						}
					}
					if(null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId() && null != projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId() && projectMember.getProjectClientsId().getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
						if(orgRelations.getLocationId() != null){
							ts.setClientName(orgRelations.getLocationId().getOrgLocationName());
						}
					}
					ts.setOrgRelationUUID(orgRelations.getUUID());
		    }else{
		   	 ts.setClientName("--");
			}
		    
		    User consultantUser = null;
		    ProjectMembers conPM =  projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
			if(conPM != null) {
				 consultantUser = conPM.getProjectMemberCandidteId().getConsultantUser();
			}
			
			Long workLocationId = 0l;
			if(conPM.getProjectMemberCandidteId() != null && conPM.getProjectMemberCandidteId().getWorkLocation() != null && conPM.getProjectMemberCandidteId().getWorkLocation().getId() != null) {
				workLocationId = conPM.getProjectMemberCandidteId().getWorkLocation().getId();
			}
			Float totalHours = 0f;
			Float estimatedTotalHours = 0f;
			SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(loginUser.getId(),projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId(),orgLocations.getId(),actualStartDate,actualEndDate,isglobalUser);
			if(timesheetApprovalDTO.getDisplayWeekList() != null && !timesheetApprovalDTO.getDisplayWeekList().isEmpty()){
				 List<String> weekTotalList = new ArrayList<String>();
				 List<String> submissionTotalList = new ArrayList<String>();   
				 Integer avgWorkingHoursPerDay = orgLocations.getAverageWorkingHours();
                 if(avgWorkingHoursPerDay == null || avgWorkingHoursPerDay == 0) {
					 avgWorkingHoursPerDay = 8; 
				  }
				    for (Map.Entry<Date,Date> entry : timesheetApprovalDTO.getDisplayWeekList().entrySet()) { 
				    		    	
				    	 long diff = CalendarUtil.getNumberOfDays(entry.getKey(), entry.getValue());
				    	 Float weekTotal = 0f;
				    	 if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 7){
				    		  weekTotal = (float) ((diff)*avgWorkingHoursPerDay);
				    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
				    	 }
				    	 else if(diff>=2 && CalendarUtil.getDayOfTheDate(entry.getValue()) == 1){
				    		  weekTotal = (float) ((diff-1)*avgWorkingHoursPerDay);
				    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
				    	 }
				    	 else if(CalendarUtil.getDayOfTheDate(entry.getKey()) == 2){
				    		  weekTotal = (float) ((diff+1)*avgWorkingHoursPerDay);
				    		  estimatedTotalHours = estimatedTotalHours+weekTotal;
				    	 }
				    	 float leaveTotal = 0f;
				    	 if(consultantUser != null) {
				    		 Map<String,Float> leaveTotalMap = appliedLeaveDateService.getAppliedLeaveDateAndHolidayHoursByUserId(consultantUser.getId(), entry.getKey(), entry.getValue(),workLocationId);
				    	     if(leaveTotalMap != null && !leaveTotalMap.isEmpty()) {
				    	    	 leaveTotal = leaveTotalMap.get("TOTAL");
				    	     }
				    	 
				    	 }
				    	 
				    	 if(sah.getWeekTotals() != null && !sah.getWeekTotals().isEmpty() && sah.getWeekTotals().get(entry.getKey()) != null){
				    		  totalHours = CommonUtil.getCombinedHours(totalHours, sah.getWeekTotals().get(entry.getKey()));
				    		  totalHours = CommonUtil.getCombinedHours(totalHours, leaveTotal);
				    		  weekTotalList.add(CommonUtil.getHoursDisplayFormat(weekTotal,null));
				    		  Float submissionTotal = CommonUtil.getCombinedHours(leaveTotal, sah.getWeekTotals().get(entry.getKey()));
				    		  submissionTotalList.add(CommonUtil.getHoursDisplayFormat(submissionTotal,null));
				    	 }else{
				    		 submissionTotalList.add(CommonUtil.getHoursDisplayFormat(leaveTotal,null));
				    		 weekTotalList.add(CommonUtil.getHoursDisplayFormat(weekTotal,null));
				    	 }
				    	 
				     }
				     ts.setWeeekTotal(weekTotalList);
				     ts.setSubmissionTotal(submissionTotalList);
				    ts.setTotalHours(CommonUtil.getHoursDisplayFormat(totalHours,null));
				    ts.setEstimatedHours(CommonUtil.getHoursDisplayFormat(estimatedTotalHours,null));
				    ts.setSubmissionStatus(sah.getSubmissionApprovalStatus().getValue().toUpperCase());
			  	  }
				  if(sah.getSubmissionApprovalStatus() !=null && sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL){
			      	 ts.setTdSpanLength(tdSpanLength);
			      	 ts.setActualStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualStartDate));
			      	 ts.setActualEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),actualEndDate));
			     }
			     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.REJECTED){
			          	 ts.setRejectedReason(sah.getRejectedReason());
			          	 ts.setActionPerformedBy(sah.getActionPerformedBy());
			     }
			     if(sah.getSubmissionApprovalStatus() == Submission_ApprovalStatus.APPROVED){
			    	 ts.setActionPerformedBy(sah.getActionPerformedBy());
			     }  
		     return ts;
       }
		
		@RequestMapping(value = "/getTimesheetListByStatus", method = RequestMethod.POST ) 
		public ModelAndView getTimesheetListByStatusWeb(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();	
			try {
				timesheetApprovalDTO = getTimesheetListByStatus(timesheetApprovalDTO,request);
				model.addObject("timesheetApproval", timesheetApprovalDTO);
				model.setViewName("timesheet/timesheetDashboardTableData");
			}catch(Exception e) {
				e.printStackTrace();
				model.addObject("AWKWARD", true);
				model.setViewName("timesheet/timesheetDashboardTableData");
			}
			return model;
			
		}
	
		
		public TimesheetApprovalDTO getTimesheetListByStatus(TimesheetApprovalDTO timesheetApprovalDTO,HttpServletRequest request) throws Exception{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
       	    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
       	    int start = timesheetApprovalDTO.getStart();
			int length = 0;
			if(timesheetApprovalDTO.getPageNo()<1) {
				timesheetApprovalDTO.setPageNo(1);
			}
			if(timesheetApprovalDTO.getPageSize()<1) {
				length = 10;
				timesheetApprovalDTO.setPageSize(10);
			}else {
				length = timesheetApprovalDTO.getPageSize();
			}
		    ResponseList<SubmissionApproverHierarchy> list  =	submissionApproverHierarchyService.getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(user.getId(), loginOrgLocations.getId(), timesheetApprovalDTO.getDashboardTimeStatus(), userRole,timesheetApprovalDTO.getSearchValue(),start,length,isGlobalAccess);
		    int tdSpanLength = 8;
		    if(list != null && !list.getData().isEmpty())
		    {
		    	timesheetApprovalDTO.setTotalRows(list.getRecordsTotal());
		    	List<TimesheetApprovalSubmissionDetailsDTO> subDetails = new ArrayList<>();
		    	for(SubmissionApproverHierarchy submissionApproverHierarchy:list.getData()) {
		    	 Date actualStartDate =submissionApproverHierarchy.getFrequencyStartDate();
		    	 Date actualEndDate =submissionApproverHierarchy.getFrequencyEndDate();
		    	 if(actualStartDate != null && actualEndDate != null){
			    	 timesheetApprovalDTO.setFrequencyStartDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate));
					 timesheetApprovalDTO.setFrequencyEndDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate));
			    	 timesheetApprovalDTO = dataTableWeekDateList(request, timesheetApprovalDTO,loginOrgLocations);
					 ProjectMembers projectMember = projectMembersService.getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getMasterCandidateId().getId(),submissionApproverHierarchy.getSubmissionId().getProjectMembers().getProjects().getId(),loginOrgLocations.getId(),loginOrgLocations.getOrg().getId(),isGlobalAccess);
					 TimesheetApprovalSubmissionDetailsDTO timeSub = getTimesheetApprovalRowDetails(timesheetApprovalDTO, projectMember, loginOrgLocations, actualStartDate, actualEndDate, user, tdSpanLength,isGlobalAccess);
					  timeSub.setDashboardInvoiceFrequency(projectMember.getProjects().getInvoiceFrequency());
					  timeSub.setCurrentTimeFrequency(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)+"To"+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate));
					  timeSub.setDashboardDateList(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualStartDate)+" To "+commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),actualEndDate));
					  timeSub.setTableFor("dashboard");
			          subDetails.add(timeSub);
		 		    }
		    	 	timesheetApprovalDTO.setTimesheetSubmission(subDetails);
		 		   }
		 		     	
		 		 }
			     return timesheetApprovalDTO;
	}
}
