package com.bis.sourcelead.rest;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.TimeSheetSubmissionDTO;
import com.bis.sourcelead.dto.TimesheetEntriesDTO;
import com.bis.sourcelead.dto.TimesheetEntryDetailsDTO;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.TimesheetProjectMemberDTO;
import com.bis.sourcelead.dto.TimesheetProjectTaskDTO;
import com.bis.sourcelead.dto.TimesheetTaskDTO;
import com.bis.sourcelead.dto.WeekDaysDTO;
import com.bis.sourcelead.dto.helper.ProjectMemberDTOConverter;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
 

@RestController 
@RequestMapping("/app")
public class TimesheetRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	/*@Autowired
	public TimesheetEntriesService timesheetEntriesService;*/
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	@Autowired
	private ExportTimesheetEntries exportTimesheetEntries;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	TimeSheetPdfUtil timeSheetPdfUtil;
	
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	/*@Autowired
	TimesheetEntriesDao timesheetEntriesDao;*/
	
	@Autowired
	private TimeSheetDayEntriesDao timesheetDayEntriesDao;
	
	@Autowired
	TimesheetSubmissionsDocumentsService TimesheetSubmissionsDocumentsService;
	
	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	TimesheetSubmissionsDocumentsService timesheetSubmissionsDocumentsService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CandidateService candidateService;

	@Value("${static.path}")
	private String staticPath;
	
	/***
	 * @purpose : Getting ProjectList For Login User (Step - 1)
	 * @param eedDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getTimesheetEntryDetails")
	public TimesheetEntryDetailsDTO getTimesheetEntryDetailsMobile(@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
	{
		return getTimesheetEntryDetails(eedDTO, request);
		
	}
	
	@RequestMapping(value = "/getTimesheetEntryDetails")
	public ModelAndView getTimesheetEntryDetailsWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
	{
		 ModelAndView model = new ModelAndView();
		 TimesheetEntryDetailsDTO tedDTO = getTimesheetEntryDetails(eedDTO, request); 
		 Long projectMemberId = eedDTO.getProjectMembersId();
		 if(projectMemberId != null) {
			 model.addObject("timesheetEntryDetailsDTO",tedDTO);
			 model.addObject("timesheetSubmissionDTO",tedDTO.getTimesheetSubmission());
			 LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(tedDTO.getTimesheetSubmission());
			 model.addObject("entriesMap",entriesMap);
		 }
		
		 model.setViewName("timesheet/timesheetProjects");
		 return model;
		
	}
	
	
	private  LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> getEntriesMapForDisplayWeb(TimeSheetSubmissionDTO tsDTO){
		LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> entriesMap = new LinkedHashMap<>();
		if(tsDTO!= null && tsDTO.getTimesheetEntries() != null && !tsDTO.getTimesheetEntries().isEmpty()){
			for(TimesheetEntriesDTO teDTO : tsDTO.getTimesheetEntries()){
				Long taskId = 0l;
				if(teDTO.getTimesheetTaskDTO() != null && teDTO.getTimesheetTaskDTO().getId() != null){
					taskId = teDTO.getTimesheetTaskDTO().getId();
				}
				LinkedList<TimesheetEntriesDTO> tsDTOList = entriesMap.get(taskId);
				if(tsDTOList == null){
					tsDTOList = new LinkedList<>();
					tsDTOList.add(teDTO);
				}else{
					tsDTOList.add(teDTO);
				}
				entriesMap.put(taskId, tsDTOList);
			}
		}
		return entriesMap;
	}
	private TimesheetEntryDetailsDTO getTimesheetEntryDetails(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
	{
		TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser == null && eedDTO.getUserId() != null){
			loginUser = userService.getUserById(eedDTO.getUserId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())){
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}
		
		Candidate candidate =(Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if(candidate == null && loginUser != null &&  orgLocations != null) {
			 candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}
		
		/**
		 * Getting List Of Projects For LoginUser
		 */
		timesheetEntryDetailsDTO = getProjectListForLoginUser(eedDTO, timesheetEntryDetailsDTO, orgLocations, candidate, request);
		return timesheetEntryDetailsDTO;
		
 }


 private TimesheetEntryDetailsDTO getProjectListForLoginUser(TimesheetExpenseEntryDetailsUtilDTO eedDTO,TimesheetEntryDetailsDTO timesheetEntryDetailsDTO,OrgLocations orgLocations,Candidate candidate,HttpServletRequest request) throws Exception {
		Map<String,String>  timesheetFrequencyMap  = null;
		Long projectMemberId = eedDTO.getProjectMembersId();
		Long projectId = eedDTO.getProjectsId();
		Long masterCandidateId = eedDTO.getMasterCandidatesId();
		List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocations.getId(),candidate.getId());
		ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
			if (projectMembersList != null && !projectMembersList.isEmpty()) {
				long count = 1;
				for (ProjectMembers pm : projectMembersList) {
	                if(count == 1 && projectMemberId == null){
	                    timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm,orgLocations,"view");
	                    if(timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
	                        continue;
						
	                    projectMemberId = pm.getId();
	                    projectId = pm.getProjects().getId();
						masterCandidateId = pm.getMasterCandidateId().getId();
						count++;
					}
	                else if(pm.getId().equals(projectMemberId)){
	                	 timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm,orgLocations,"view");
		                    if(timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
		                        continue;
		                    if(projectId == null || masterCandidateId == null){
		       				 projectId =         pm.getProjects().getId();
		       				 masterCandidateId = pm.getMasterCandidateId().getId();
		       			    }
							count++;
	                	
	                }
					pmDTOList.add(ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm));
				}
			}
		timesheetEntryDetailsDTO.setTimeFrequencies(timesheetFrequencyMap);
		timesheetEntryDetailsDTO.setProjectMembersList(pmDTOList);
		timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
		timesheetEntryDetailsDTO.setProjectId(projectId);
		timesheetEntryDetailsDTO.setMasterCandidateId(masterCandidateId);
		
		eedDTO.setProjectMembersId(projectMemberId);
		eedDTO.setProjectsId(projectId);
		eedDTO.setMasterCandidatesId(masterCandidateId);
		/**
		 * Getting TimesheetFrequencyList For Selected Project
		 */
		timesheetEntryDetailsDTO = getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO,request);
		
		return timesheetEntryDetailsDTO;
   }
 
 /**
  * @purpose : Getting TimesheetFrequencyList For Selected Project(Step - 2)
  * @param eedDTO
  * @param request
  * @return
  * @throws Exception
  */
 @RequestMapping(value = "/rest/getTimesheetfrequencyList")
 public TimesheetEntryDetailsDTO getTimesheetfrequencyListMobile(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
 {
	 TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
	 return getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO, request);
 }
 @RequestMapping(value = "/getTimesheetfrequencyList")
 public ModelAndView getTimesheetfrequencyListWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
 {
	 ModelAndView model = new ModelAndView();
	 TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
	 timesheetEntryDetailsDTO =  getTimesheetFrequencyList(eedDTO, timesheetEntryDetailsDTO,request);
	 model.addObject("timesheetSubmissionDTO",timesheetEntryDetailsDTO.getTimesheetSubmission());
	 model.addObject("timesheetEntryDetailsDTO",timesheetEntryDetailsDTO);
	 LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(timesheetEntryDetailsDTO.getTimesheetSubmission());
	 model.addObject("entriesMap",entriesMap);
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.setViewName("timesheet/timesheetfrequencyList");
	 return model;
 }
  private TimesheetEntryDetailsDTO getTimesheetFrequencyList(TimesheetExpenseEntryDetailsUtilDTO eedDTO,TimesheetEntryDetailsDTO timesheetEntryDetailsDTO,HttpServletRequest request) throws Exception {
	     User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 if(loginUser == null && eedDTO.getUserId() != null){
			loginUser = userService.getUserById(eedDTO.getUserId());
		 }
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 if(orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())){
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		 }
		
		 Candidate candidate =(Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		 if(candidate == null && loginUser != null &&  orgLocations != null) {
			 candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		 }
	  
	    Long projectMemberId = eedDTO.getProjectMembersId();
	    if(projectMemberId != null) {
	    	ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
			Map<String,String>  timesheetFrequencyMap = timesheetEntryDetailsDTO.getTimesheetFrequencyMap();
			    if(timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty()){
			    	timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,orgLocations,"view");
		    }
		    LinkedHashMap<String,String> timeFrequencies = new LinkedHashMap<>();
			String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
			//For List Of Frequencies 
			if(StringUtils.isEmpty(currentTimeFrequency)){
				StringBuilder selectedStartDate = new StringBuilder("");
				StringBuilder selectedEndDate =  new StringBuilder("");
				if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
					long flag = 1;
					for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
					    String key = entry.getKey();
					    String value =entry.getValue();
					    Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
					    Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
					    HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId, currentStartDate, currentEndDate);
					    if(pmhDateList.isEmpty() || pmhDateList == null)
					    continue;
					    if(flag == 1){
					    	selectedStartDate.append(key.replaceAll("/", "-"));
					    	selectedEndDate.append(value.replaceAll("/", "-"));
					    }
					    if(CalendarUtil.isCurrentDateBetweenDateRange(key, value,orgLocations.getDateFormat())){
					    	selectedStartDate.setLength(0);
					    	selectedEndDate.setLength(0);
					    	selectedStartDate.append(key.replaceAll("/", "-"));
					    	selectedEndDate.append(value.replaceAll("/", "-"));
					    }
					    timeFrequencies.put(key.replaceAll("/", "-")+"To"+value.replaceAll("/", "-"), key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-"));
					    flag++;
				   }
					currentTimeFrequency = selectedStartDate.toString().replaceAll("/", "-")+"To"+selectedEndDate.toString().replaceAll("/", "-");
				}
			}
			else{
				if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
					for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
					    String key = entry.getKey();
					    String value =entry.getValue();
					    Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
					    Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
					    HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId, currentStartDate, currentEndDate);
					    if(pmhDateList.isEmpty() || pmhDateList == null)
					    continue;
					    timeFrequencies.put(key.replaceAll("/", "-")+"To"+value.replaceAll("/", "-"), key.replaceAll("/", "-")+" To "+value.replaceAll("/", "-"));
					}
				}
			}
			timesheetEntryDetailsDTO.setTimeFrequencies(timeFrequencies);
			 timesheetEntryDetailsDTO.setCurrentTimeFrequency(currentTimeFrequency);
			 timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
			 timesheetEntryDetailsDTO.setProjectId(projectMember.getProjects().getId());
			 timesheetEntryDetailsDTO.setMasterCandidateId(projectMember.getMasterCandidateId().getId());
			 eedDTO.setProjectsId(projectMember.getProjects().getId());
			 eedDTO.setMasterCandidatesId(projectMember.getMasterCandidateId().getId());
			 eedDTO.setCurrentTimeFrequency(currentTimeFrequency);
			 eedDTO.setUserId(loginUser.getId());
			 eedDTO.setLocationCode(orgLocations.getLocationCode());
			
			/**
			 * Getting Timehsheet Sub-FrequencyList For Selected Timesheet Frequency
			 */
			timesheetEntryDetailsDTO = getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, timesheetEntryDetailsDTO, orgLocations,request);
			
		   TimeSheetSubmissionDTO ts = getTimesheetSubmissionDetails(eedDTO, request);
		   timesheetEntryDetailsDTO.setTimesheetSubmission(ts);
	    }
		return timesheetEntryDetailsDTO;
  }
	
  private TimesheetEntryDetailsDTO getTimesheetSubFrequencyListByCurrentFrequency(TimesheetExpenseEntryDetailsUtilDTO eedDTO,TimesheetEntryDetailsDTO timesheetEntryDetailsDTO,OrgLocations orgLocations,HttpServletRequest request) throws Exception{
	  LinkedHashMap<String,String> timeSubFrequencies = new LinkedHashMap<>();
	   
	  String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
      String[] frequency = currentTimeFrequency.split("To");
 	  String currentFrequencyStartDate = frequency[0].trim();
  	  String currentFrequencyEndDate = frequency[1].trim();
  	  
  	  String timesheetFor = "user";
  	  if(StringUtils.isNotEmpty(eedDTO.getTimeSheetsFor())){
  		timesheetFor = eedDTO.getTimeSheetsFor();
  	  }
  	 
	  if(!isNullOrEmpty(currentFrequencyStartDate) && !isNullOrEmpty(currentFrequencyEndDate)){
	    Map<String,String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(currentFrequencyStartDate, currentFrequencyEndDate,orgLocations.getDateFormat());
	    String currentTimeSubFrequency = eedDTO.getCurrentTimeSubFrequency();
	   
		StringBuilder currentStartDate = new StringBuilder("");
		StringBuilder currentEndDate = new StringBuilder("");
		int count = 1;
		LinkedList<String> filledTimeSubFrequencies = new LinkedList<>();
		if(timeSubFrequencyMap != null && !timeSubFrequencyMap.isEmpty()){
			for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
			     String key = entry.getKey();
			     String value = entry.getValue();
				 Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
				 Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
			     DateFormat shortormat = Constants.SHORT_DF_STD_DM;
			     TimesheetSubmissions ts =timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate, eedDTO.getProjectsId(), eedDTO.getMasterCandidatesId());
			    
			     if(ts != null &&  eedDTO != null && eedDTO.getUserId() != null){
			    	 SubmissionApproverHierarchy sah  = null;
			    	 if(timesheetFor.equalsIgnoreCase("user")){
			    		 sah  = submissionApproverHierarchyService.getSubmissionLowLevelApproverHierarchyForEmployee(ts.getId());
			    	 }else{
			    		 sah  = submissionApproverHierarchyService.getSubmissionApproverHierarchyForApprover(ts.getId(), eedDTO.getUserId());
			    	 }
			    	 if(sah != null){
			    		 Submission_Status submissionStatus = sah.getSubmissionStatus();
			    		 String submissionStatusStr = submissionStatus.getValue();
			    		 if(!isNullOrEmpty(submissionStatusStr) && !submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())){
			    			 filledTimeSubFrequencies.add(key+"To"+value);
			    		 }
			    	 }
                	
                 }
			     
			    if(CalendarUtil.isCurrentDateBetweenDateRange(currentFrequencyStartDate, currentFrequencyEndDate,orgLocations.getDateFormat())){  
			    	if(CalendarUtil.isCurrentDateBetweenDateRange(key, value,orgLocations.getDateFormat())){
			    		   currentStartDate.append(key);
			    		   currentEndDate.append(value);
			    	}
			    	if(startDate.equals(endDate)){
			    		timeSubFrequencies.put(key+"To"+value, shortormat.format(startDate));
			    	}
			    	else{
			    		timeSubFrequencies.put(key+"To"+value, shortormat.format(startDate)+" - "+shortormat.format(endDate));
			    	}
			    }
			    else{
				    	if(count == 1){
			            	   currentStartDate.append(key);
				    		   currentEndDate.append(value);
			            }
				    	if(startDate.equals(endDate)){
				    		timeSubFrequencies.put(key+"To"+value, shortormat.format(startDate));
				    	}else { 
				    		 timeSubFrequencies.put(key+"To"+value, shortormat.format(startDate)+" - "+shortormat.format(endDate));
				    	}
			    }
			    count++;
			}
			
			 if(StringUtils.isEmpty(eedDTO.getCurrentTimeSubFrequency())){
				 currentTimeSubFrequency=currentStartDate.toString()+"To"+currentEndDate.toString();
			 }
			 timesheetEntryDetailsDTO.setFilledTimeSubFrequencies(filledTimeSubFrequencies);
			 timesheetEntryDetailsDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
			 timesheetEntryDetailsDTO.setTimeSubFrequencies(timeSubFrequencies);
			 timesheetEntryDetailsDTO.setTimesheetFor(timesheetFor);
			 timesheetEntryDetailsDTO.setLocDateFomat(orgLocations.getDateFormat());
			 eedDTO.setCurrentTimeSubFrequency(currentTimeSubFrequency);
        }
	  }	
	  return timesheetEntryDetailsDTO;
  }
  
  
  private HashMap<String,String> getTimesheetStatusByTimeSubFrequency(ProjectMembers pm,String timesheetFor,String currentTimeFrequency,OrgLocations orgLocations,User loginUser,Boolean isModifyMyTimeSheet,Boolean isFillMyTimeSheet) throws Exception{
	  String[] frequency = currentTimeFrequency.split("To");
 	  String currentFrequencyStartDate = frequency[0].trim();
  	  String currentFrequencyEndDate = frequency[1].trim();
	  
	  
	  if(StringUtils.isEmpty(timesheetFor)){
			timesheetFor = "user";
	  }
	HashMap<String,String> statusMap = new HashMap<>();
   if(pm != null){
    Long projectId = pm.getProjects().getId();
    Long masterCandidateId = pm.getMasterCandidateId().getId();
	Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentFrequencyStartDate.replaceAll("/", "-"));
	Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentFrequencyEndDate.replaceAll("/", "-"));
	 
	SubmissionApproverHierarchy sah = null;
	if(StringUtils.isNotEmpty(timesheetFor) && timesheetFor.equalsIgnoreCase("user")){
		sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);  
	}
	else{
		sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(loginUser.getId(),projectId,masterCandidateId,orgLocations.getId(),frequencyStartDate, frequencyEndDate);
	}
	Submission_Status submissionStatus = sah.getSubmissionStatus();
	Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(pm.getId(), loginUser);
	String submissionStatusStr = submissionStatus.getValue();
    SubmissionApproverHierarchy isSah = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId,masterCandidateId, frequencyStartDate, frequencyEndDate, "APPROVED");
    boolean isSahApproved = false;
    if((isSah == null &&timesheetFor.equalsIgnoreCase("user") && isModifyMyTimeSheet && !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())))
       || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()) && timesheetFor.equalsIgnoreCase("approver") && StringUtils.isEmpty( sah.getReopenReason())) ){
        if(isSah == null)
    	isSahApproved = true;
     }
    
    boolean isSahReopened = false;
    if(null != isSah && timesheetFor.equalsIgnoreCase("approver") && null != isSah.getReopenReason() && !"".equalsIgnoreCase(isSah.getReopenReason())){
	   isSahReopened = true;
    }
  
    String acSubmissionStatusStr = null;
    if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
	   SubmissionApproverHierarchy acsah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);
	   Submission_Status acSubmissionStatus = acsah.getSubmissionStatus();
	   acSubmissionStatusStr = acSubmissionStatus.getValue();
    }
    statusMap.put("STATUS", submissionStatusStr.toUpperCase());
    statusMap.put("REOPEN_REASON",sah.getReopenReason());
    if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
    	if(isSahApproved && isModifyMyTimeSheet && timesheetFor.equalsIgnoreCase("user") && isModifyMyTimeSheet && isFillMyTimeSheet){
    		 statusMap.put("IS_MODIFY_BUTTON_VISIBLE","true");
    	}
    }
    
    if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
        	if(timesheetFor.equalsIgnoreCase("approver") && StringUtils.isEmpty(sah.getReopenReason())){
        		//to show re-open rejected reason
        		if(StringUtils.isNotEmpty(sah.getReopenRejectedReason())){
        			 statusMap.put("REOPEN_REJECTED_REASON",sah.getReopenRejectedReason());
        		}
        		if(!isNullOrEmpty(acSubmissionStatusStr) && acSubmissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
        			statusMap.put("IS_REOPEN_BUTTON_VISIBLE","true");
        		}
        	}
        	else if(timesheetFor.equalsIgnoreCase("approver") && !isSuperUser && StringUtils.isNotEmpty(sah.getReopenReason()) && isSahReopened){
        		//to show re-open reason
        		if(StringUtils.isNotEmpty(sah.getReopenReason())){
        			 statusMap.put("REOPEN_REASON",sah.getReopenReason());
        		}
        		statusMap.put("REOPEN_REQUEST_SENT","true");
	        }
        	else if(timesheetFor.equalsIgnoreCase("approver") && isSahReopened && isSuperUser && null != sah.getReopenedUser()){
        		//to show re-open reason
        		if(StringUtils.isNotEmpty(sah.getReopenReason())){
        			 statusMap.put("REOPEN_REASON",sah.getReopenReason());
        		}
        		statusMap.put("REOPEN_USER_ID",""+sah.getReopenedUser().getId());
        		statusMap.put("REOPEN_REQUEST_SENT","true");
        	    statusMap.put("IS_REOPEN_ACCEPT_REJECT_BUTTON_VISIBLE","true");
        	}
        }
        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
        	statusMap.put("REJECTED_REASON",sah.getRejectedReason());
        }
       }
       return statusMap;
    }
 
  
  /**
   * @purpose : Getting TimesheetSubmission Details For Current-TimesheetFrequency and Current-TimesheetSubFrequency(Step - 3)
   * @param eedDTO
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/rest/getTimesheetSubmissionDetails")
  public TimeSheetSubmissionDTO  getTimesheetSubmissionDetailsMobile(@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
  {
	  return getTimesheetSubmissionDetails(eedDTO, request);
  }
  @RequestMapping(value = "/getTimesheetSubmissionDetails")
  public ModelAndView getTimesheetSubmissionDetailsWeb(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception
  {
	  
	   User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	  if(loginUser == null && eedDTO.getUserId() != null){
		loginUser = userService.getUserById(eedDTO.getUserId());
	  }
	  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	  if(orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())){
		orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
	  }
	  Long projectMemberId = eedDTO.getProjectMembersId();
	  ModelAndView model = new ModelAndView();
	  if(projectMemberId != null) {
		     ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			 eedDTO.setUserId(loginUser.getId());
			 eedDTO.setLocationCode(orgLocations.getLocationCode());
			 eedDTO.setProjectsId(pm.getProjects().getId());
			 eedDTO.setMasterCandidatesId(pm.getMasterCandidateId().getId());
			 TimesheetEntryDetailsDTO  timesheetEntryDetailsDTO = getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, new TimesheetEntryDetailsDTO(), orgLocations,request);
			 timesheetEntryDetailsDTO.setProjectMemberId(projectMemberId);
			 timesheetEntryDetailsDTO.setProjectId(pm.getProjects().getId());
			 timesheetEntryDetailsDTO.setMasterCandidateId(pm.getMasterCandidateId().getId());
			 
			 String fileUrl = "";
			 if(pm != null && pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getConsultantUser() != null)
				 fileUrl = pm.getProjectMemberCandidteId().getConsultantUser().getFileUrl();
			 
			 timesheetEntryDetailsDTO.setProfileImageFileUrl(fileUrl);
			 timesheetEntryDetailsDTO.setEmployeeName(pm.getProjectMemberCandidteId().getFullName());
			 timesheetEntryDetailsDTO.setEmployeeEmail(pm.getProjectMemberCandidteId().getEmail());
			 timesheetEntryDetailsDTO.setEmployeePhoneNo(pm.getProjectMemberCandidteId().getMobilePhone());
			 
			 TimeSheetSubmissionDTO tsDTO =  getTimesheetSubmissionDetails(eedDTO, request);
			 model.addObject("timesheetSubmissionDTO",tsDTO);
		     LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(tsDTO);
			 model.addObject("entriesMap",entriesMap);
		     model.addObject("timesheetEntryDetailsDTO",timesheetEntryDetailsDTO);
	  }
	 model.setViewName("timesheet/timeSheetSubmission");
	 return model;
  }
   private TimeSheetSubmissionDTO getTimesheetSubmissionDetails(TimesheetExpenseEntryDetailsUtilDTO eedDTO,HttpServletRequest request) throws Exception {
	    Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
	    Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");	
		if(isFillMyTimeSheet || isFillMemberTimesheet){
			if(!isFillMyTimeSheet && isFillMemberTimesheet){
				isFillMyTimeSheet = isFillMemberTimesheet;
			}
		}
		/**
		 * Getting TimesheetStatus
		 */
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser == null && eedDTO.getUserId() != null){
			loginUser = userService.getUserById(eedDTO.getUserId());
		}
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if(orgLocations == null && StringUtils.isNotEmpty(eedDTO.getLocationCode())){
			orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		}
		
		Candidate candidate =(Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if(candidate == null && loginUser != null &&  orgLocations != null) {
			 candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLocations.getId());
		}
	   
	   
	   Long projectMemberId = eedDTO.getProjectMembersId();
	   String currentTimeFrequency = eedDTO.getCurrentTimeFrequency();
	   String currentTimeSubFrequency = eedDTO.getCurrentTimeSubFrequency();
	   String timesheetFor = eedDTO.getTimeSheetsFor();
	   if(StringUtils.isEmpty(timesheetFor)){
		   timesheetFor = "user";
	   }
	   TimeSheetSubmissionDTO tsDTO = new TimeSheetSubmissionDTO();
	   if(projectMemberId != null && StringUtils.isNotEmpty(currentTimeFrequency) && StringUtils.isNotEmpty(currentTimeSubFrequency)){
		    ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		    HashMap<String,String> statusMap = getTimesheetStatusByTimeSubFrequency(projectMember, timesheetFor, currentTimeFrequency, orgLocations, loginUser, isModifyMyTimeSheet, isFillMyTimeSheet);
		    tsDTO.setTimeSubFrequency(currentTimeSubFrequency);
		    tsDTO.setTimesheetStatus(statusMap.get("STATUS"));
		    tsDTO.setReopenReason(statusMap.get("REOPEN_REASON"));
		    tsDTO.setModifyButtonVisible(new Boolean(statusMap.get("IS_MODIFY_BUTTON_VISIBLE")));
		    tsDTO.setReopenRejectedReason(statusMap.get("REOPEN_REJECTED_REASON"));
		    tsDTO.setReopenButtonVisible(new Boolean(statusMap.get("IS_REOPEN_BUTTON_VISIBLE")));
		    tsDTO.setReopenRequestSent(new Boolean(statusMap.get("REOPEN_REQUEST_SENT")));
		    tsDTO.setReopenAcceptRejectButtonVisible(new Boolean(statusMap.get("IS_REOPEN_ACCEPT_REJECT_BUTTON_VISIBLE")));
		    tsDTO.setReopenUserId(statusMap.get("REOPEN_USER_ID"));
		    tsDTO.setRejectedReason(statusMap.get("REJECTED_REASON"));
		    
		    String approvalStatus = tsDTO.getTimesheetStatus();
		    
		    LinkedList<TimesheetTaskDTO> taskDTOList = new LinkedList<>();
		    Collection<ProjectTasks> projectTasksList = projectTasksService.getAllTasksByProjectId(projectMember.getProjects().getId());
		    if(projectTasksList != null && !projectTasksList.isEmpty()){
		    	for(ProjectTasks pt :projectTasksList){
		    		TimesheetTaskDTO taskDTO = new TimesheetTaskDTO();
		    		taskDTO.setId(pt.getId());
		    		taskDTO.setTaskName(pt.getTaskName());
		    		taskDTO.setTaskType(pt.getBillableType());
		    		taskDTOList.add(taskDTO);
		    	}
		    	
		    }
		    StringBuilder disbleCondition1 = new StringBuilder("");
			StringBuilder disbleCondition2 = new StringBuilder("");
			StringBuilder disbleCondition3 = new StringBuilder("");
			StringBuilder disbleCondition4 = new StringBuilder("");
			StringBuilder disbleCondition5 = new StringBuilder("");
			StringBuilder disbleCondition6 = new StringBuilder("");
			StringBuilder disbleCondition7 = new StringBuilder("");
			
			String[] currentFrequency = currentTimeFrequency.split("To");
		    String currentFrequencyEndDate = currentFrequency[1].trim();
		    
		    String[] currentSubFrequency = currentTimeSubFrequency.split("To");
		 	String currentSubFrequencyStartDate = currentSubFrequency[0].trim();
		    String currentSubFrequencyEndDate = currentSubFrequency[1].trim();
		  	  
			Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyStartDate.replaceAll("/", "-"));
		    Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyEndDate.replaceAll("/", "-"));
			int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyStartDate));
			String weekStartDate = currentSubFrequencyStartDate;
			switch (dayOfTheDateWithStartDate) {
		        case 1:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,6,orgLocations.getDateFormat());
		                 disbleCondition1.setLength(0);
		                 disbleCondition2.setLength(0);
		                 disbleCondition3.setLength(0);
		                 disbleCondition4.setLength(0);
		                 disbleCondition5.setLength(0);
		                 disbleCondition6.setLength(0);
		                 disbleCondition1.append("disabled");
		                 disbleCondition2.append("disabled");
		                 disbleCondition3.append("disabled");
		                 disbleCondition4.append("disabled");
		                 disbleCondition5.append("disabled");
		                 disbleCondition6.append("disabled");
		                 break;
		        case 2:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,0,orgLocations.getDateFormat());
		                 break;
		        case 3:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,1,orgLocations.getDateFormat());
		                  disbleCondition1.setLength(0);
		                 disbleCondition1.append("disabled");
		             break;
		        case 4:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,2,orgLocations.getDateFormat());
		                 disbleCondition1.setLength(0);
	                    disbleCondition2.setLength(0);
				         disbleCondition1.append("disabled");
			             disbleCondition2.append("disabled");
		            break;
		        case 5: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,3,orgLocations.getDateFormat());
				        disbleCondition1.setLength(0);
			            disbleCondition2.setLength(0);
			            disbleCondition3.setLength(0);
				        disbleCondition1.append("disabled");
			            disbleCondition2.append("disabled");
			            disbleCondition3.append("disabled");
		            break;
		        case 6:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,4,orgLocations.getDateFormat());
				         disbleCondition1.setLength(0);
			             disbleCondition2.setLength(0);
			             disbleCondition3.setLength(0);
			             disbleCondition4.setLength(0);
				         disbleCondition1.append("disabled");
			             disbleCondition2.append("disabled");
			             disbleCondition3.append("disabled");
			             disbleCondition4.append("disabled");
		              break;
		        case 7: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,5,orgLocations.getDateFormat());
				        disbleCondition1.setLength(0);
			            disbleCondition2.setLength(0);
			            disbleCondition3.setLength(0);
			            disbleCondition4.setLength(0);
			            disbleCondition5.setLength(0);
				        disbleCondition1.append("disabled");
			            disbleCondition2.append("disabled");
			            disbleCondition3.append("disabled");
			            disbleCondition4.append("disabled");
			            disbleCondition5.append("disabled");
		             break;
		        default:   weekStartDate = CalendarUtil.getPreviousDateByDecrementor(currentSubFrequencyStartDate,0,orgLocations.getDateFormat());
		                 break;
	      }
			int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentSubFrequencyEndDate));
			switch (dayOfTheDateWithEndDate) {
				case 2:   
				           disbleCondition2.setLength(0);
				           disbleCondition3.setLength(0);
				           disbleCondition4.setLength(0);
				           disbleCondition5.setLength(0);
				           disbleCondition6.setLength(0);
				           disbleCondition7.setLength(0);
					       disbleCondition2.append("disabled");
		                   disbleCondition3.append("disabled");
		                   disbleCondition4.append("disabled");
		                   disbleCondition5.append("disabled");
		                   disbleCondition6.append("disabled");
		                   disbleCondition7.append("disabled");
		                   break;
		                   
		        case 3:  
			             disbleCondition3.setLength(0);
			             disbleCondition4.setLength(0);
			             disbleCondition5.setLength(0);
			             disbleCondition6.setLength(0);
			             disbleCondition7.setLength(0);
		        	     disbleCondition3.append("disabled");
				         disbleCondition4.append("disabled");
				         disbleCondition5.append("disabled");
				         disbleCondition6.append("disabled");
				         disbleCondition7.append("disabled");
				          break;
		        case 4: 
			        	 disbleCondition4.setLength(0);
			        	 disbleCondition5.setLength(0);
			        	 disbleCondition6.setLength(0);
			        	 disbleCondition7.setLength(0);
		        	     disbleCondition4.append("disabled");
				         disbleCondition5.append("disabled");
				         disbleCondition6.append("disabled");
				         disbleCondition7.append("disabled");
				         break;
		        case 5: 
				       	 disbleCondition5.setLength(0);
				       	 disbleCondition6.setLength(0);
				       	 disbleCondition7.setLength(0);
		        	     disbleCondition5.append("disabled");
			             disbleCondition6.append("disabled");
			             disbleCondition7.append("disabled");
		                 break;
		        case 6:  
		       	         disbleCondition6.setLength(0);
		       	         disbleCondition7.setLength(0);
		        	     disbleCondition6.append("disabled");
			             disbleCondition7.append("disabled");
		                 break;
		        case 7:  disbleCondition7.setLength(0);
		        	     disbleCondition7.append("disabled");
		                 break;
	       }
			
			Date actualWeekStartDate = 	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDate.replaceAll("/", "-"));
			String tuesDayDate =  CalendarUtil.getNextDate(weekStartDate,orgLocations.getDateFormat());
			String WednesDayDate =  CalendarUtil.getNextDate(tuesDayDate,orgLocations.getDateFormat());
			String ThursDayDate =  CalendarUtil.getNextDate(WednesDayDate,orgLocations.getDateFormat());
			String FriDayDate =  CalendarUtil.getNextDate(ThursDayDate,orgLocations.getDateFormat());
			String SaturdayDate =  CalendarUtil.getNextDate(FriDayDate,orgLocations.getDateFormat());
			String SundayDate =  CalendarUtil.getNextDate(SaturdayDate,orgLocations.getDateFormat());
			
			
			
			/**
			 * Here getting  dates which are enabled in the current submission
			 * (Hint:In particular project,the member may assigned in the mid of the week) 
		    */
			HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId,actualWeekStartDate, actualEndDate);
			
	 	    Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		    currentCal.setLenient(false);
		    currentCal.setTime(actualWeekStartDate);
			 
			
			if( (!isFillMyTimeSheet) || (!pmhDateList.contains(actualWeekStartDate)) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
				disbleCondition1.setLength(0);
				disbleCondition1.append("disabled");
			}
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition2.setLength(0);
	       	disbleCondition2.append("disabled");
			}
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition3.setLength(0);
	       	disbleCondition3.append("disabled");
			}
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition4.setLength(0);
	       	disbleCondition4.append("disabled");
			} 
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition5.setLength(0);
	       	disbleCondition5.append("disabled");
			}
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition6.setLength(0);
	       	disbleCondition6.append("disabled");
			}
	       if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SUN",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
	       	disbleCondition7.setLength(0);
	       	disbleCondition7.append("disabled");
			} 
	       
          TimesheetSubmissions ts = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate, projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
          Collection<TimeSheetDayEntries>  timesheetEntriesList = new ArrayList<>();
		  if(ts != null){
			  tsDTO.setId(ts.getId());
			 timesheetEntriesList =   timesheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(ts.getId());
		  }	
		 
		 
		 /**
		  * Current Week Days 
		  */
		
		 LinkedHashMap<String,WeekDaysDTO> weekDaysMap= new LinkedHashMap<>();
		 WeekDaysDTO monDayDTO	= new WeekDaysDTO();
		 monDayDTO.setDate(weekStartDate);
		 monDayDTO.setDayOfTheDate("Mon");
		 if(disbleCondition1.toString().equalsIgnoreCase("disabled")){
			 monDayDTO.setEditable(false);
		 }else{
			 monDayDTO.setEditable(true);
		 }
		 weekDaysMap.put("Mon", monDayDTO);
		 
		 
		 WeekDaysDTO tuesDayDTO	= new WeekDaysDTO();
		 tuesDayDTO.setDate(tuesDayDate);
		 tuesDayDTO.setDayOfTheDate("Tue");
		 if(disbleCondition2.toString().equalsIgnoreCase("disabled")){
			 tuesDayDTO.setEditable(false);
		 }else{
			 tuesDayDTO.setEditable(true);
		 }
		 weekDaysMap.put("Tue", tuesDayDTO);
		 
		 
		 WeekDaysDTO wedDayDTO	= new WeekDaysDTO();
		 wedDayDTO.setDate(WednesDayDate);
		 wedDayDTO.setDayOfTheDate("Wed");
		 if(disbleCondition3.toString().equalsIgnoreCase("disabled")){
			 wedDayDTO.setEditable(false);
		 }else{
			 wedDayDTO.setEditable(true);
		 }
		 weekDaysMap.put("Wed", wedDayDTO);
		 
		 
		 WeekDaysDTO thursDayDTO	= new WeekDaysDTO();
		 thursDayDTO.setDate(ThursDayDate);
		 thursDayDTO.setDayOfTheDate("Thu");
		 if(disbleCondition4.toString().equalsIgnoreCase("disabled")){
			 thursDayDTO.setEditable(false);
		 }else{
			 thursDayDTO.setEditable(true);
		 }
		 weekDaysMap.put("Thu", thursDayDTO);
		 
		 
		 WeekDaysDTO friDayDTO	= new WeekDaysDTO();
		 friDayDTO.setDate(FriDayDate);
		 friDayDTO.setDayOfTheDate("Fri");
		 if(disbleCondition5.toString().equalsIgnoreCase("disabled")){
			 friDayDTO.setEditable(false);
		 }else{
			 friDayDTO.setEditable(true);
		 }
		 weekDaysMap.put("Fri", friDayDTO);
		 
		 
		 WeekDaysDTO satDayDTO	= new WeekDaysDTO();
		 satDayDTO.setDate(SaturdayDate);
		 satDayDTO.setDayOfTheDate("Sat");
		 if(disbleCondition6.toString().equalsIgnoreCase("disabled")){
			 satDayDTO.setEditable(false);
		 }else{
			 satDayDTO.setEditable(true);
		 }
		 
		 weekDaysMap.put("Sat", satDayDTO);
		 
		 WeekDaysDTO sunDayDTO	= new WeekDaysDTO();
		 sunDayDTO.setDate(SundayDate);
		 sunDayDTO.setDayOfTheDate("Sun");
		 if(disbleCondition7.toString().equalsIgnoreCase("disabled")){
			 sunDayDTO.setEditable(false); 
		 }else{
			 sunDayDTO.setEditable(true);
		 }
		 
		 weekDaysMap.put("Sun", sunDayDTO);
		 
		 /**
		  * TimesheetEntries List
		  */
		 tsDTO = getTimesheetSubmissionDTOWithEntriesList(ts, tsDTO, weekDaysMap, timesheetEntriesList,orgLocations.getDateFormat());

		 /**
		  * Project Task List
		  */
		 tsDTO.setTimesheetTaskList(taskDTOList);
		 
		 /**
		  * Status_map
		  */
		 
		 if(statusMap != null && !statusMap.isEmpty()){
			 
		 }

		if(StringUtils.isNotEmpty(currentFrequencyEndDate)){
			List<FileInfo> fileInfoList = new ArrayList<>();
			Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentFrequencyEndDate.replaceAll("/", "-"));
			if(frequencyEndDate.equals(actualEndDate)){
					List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = null;
					if(ts != null){
						timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(ts.getId());
						
						if(timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()){
								for(TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList){
									FileInfo fileInfo = new FileInfo();
									fileInfo.setFileDbId(tsd.getId().toString());
									fileInfo.setFileName(tsd.getFileName());
									fileInfo.setFileURL(tsd.getFileUrl());
									fileInfo.setStatus(tsd.getStatus());
									fileInfo.setContentType(tsd.getFileContentType());
									fileInfoList.add(fileInfo);
								}
							} 
					}
					
					 if(projectMember.getProjects().getIsTimesheetAttachmentRequired() != null && projectMember.getProjects().getIsTimesheetAttachmentRequired() != 0){
						 tsDTO.setAttachmentRequired(true);
					 }
					 else{
						 tsDTO.setAttachmentRequired(false); 
					 }
				  
				  if(frequencyEndDate.equals(actualEndDate)){
					  tsDTO.setWeekCommentVisible(true);
				  }
				  
					  
			    if(ts != null && ts.getWeekcomments() != null && !ts.getWeekcomments().isEmpty()){
			    	tsDTO.setWeekComment(ts.getWeekcomments());
				}
			}
			tsDTO.setFileInfo(fileInfoList);
		}
		 if( isFillMyTimeSheet) {
		    if(!isNullOrEmpty(approvalStatus)  && !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user")) && !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()) ){
		    	if(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()) || approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()) || !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && timesheetFor.equalsIgnoreCase("user")))
		    	  tsDTO.setSaveButtonVisible(true);
				
				if(StringUtils.isNotEmpty(currentFrequencyEndDate)){
					Date frequencyEndDate = 	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),currentFrequencyEndDate.replaceAll("/", "-"));
					if(frequencyEndDate.equals(actualEndDate))
						tsDTO.setSubmitButtonVisible(true);
				}
				
				if(!isNullOrEmpty(approvalStatus)  && approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("New").getValue()))
					tsDTO.setCancelButtonVisible(true);	
		     }else{
		    	 tsDTO.setSaveButtonVisible(false);
		    	 tsDTO.setSubmitButtonVisible(false);
		    	 tsDTO.setCancelButtonVisible(false);	
		    }
		}
		 tsDTO.setResponseStatus("success");
	   }
	   else{
		   tsDTO.setResponseStatus("failure");
	   }
	   return tsDTO;
   }
	
   
   private TimeSheetSubmissionDTO getTimesheetSubmissionDTOWithEntriesList(TimesheetSubmissions ts,TimeSheetSubmissionDTO tsDTO,LinkedHashMap<String,WeekDaysDTO> weekDaysMap,Collection<TimeSheetDayEntries>  timesheetEntriesList,String dateFormat){
	   
	      LinkedList<TimesheetEntriesDTO> entriesDTOList = new LinkedList<>();
		  Float monDayTotalHours = 0f;
		  Float monDayBillableHours = 0f;
		  Float monDayNonBillableHours = 0f;
		  
		  Float tuesDayTotalHours = 0f;
		  Float tuesDayBillableHours = 0f;
		  Float tuesDayNonBillableHours = 0f;
		  
		  Float wedDayTotalHours = 0f;
		  Float wedDayBillableHours = 0f;
		  Float wedDayNonBillableHours = 0f;
		  
		  Float thursDayTotalHours = 0f;
		  Float thursDayBillableHours = 0f;
		  Float thursDayNonBillableHours = 0f;
		  
		  Float friDayTotalHours = 0f;
		  Float friDayBillableHours = 0f;
		  Float friDayNonBillableHours = 0f;
		  
		  Float satDayTotalHours = 0f;
		  Float satDayBillableHours = 0f;
		  Float satDayNonBillableHours = 0f;
		  
		  Float sunDayTotalHours = 0f;
		  Float sunDayBillableHours = 0f;
		  Float sunDayNonBillableHours = 0f;
		  
		  Float totalHours = 0f;
		  Float billableHours = 0f;
		  Float nonBillableHours = 0f;
		  ArrayList<WeekDaysDTO> weekDays = new ArrayList<>();
		  WeekDaysDTO monDayDTO	= weekDaysMap.get("Mon");
		  WeekDaysDTO tuesDayDTO	= weekDaysMap.get("Tue");
		  WeekDaysDTO wedDayDTO	= weekDaysMap.get("Wed");
		  WeekDaysDTO thursDayDTO	= weekDaysMap.get("Thu");
		  WeekDaysDTO friDayDTO	= weekDaysMap.get("Fri");
		  WeekDaysDTO satDayDTO	= weekDaysMap.get("Sat");
		  WeekDaysDTO sunDayDTO	= weekDaysMap.get("Sun");
		  if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
			  HashSet<Long> taskSize = new HashSet<>();
				  for(TimeSheetDayEntries te   : timesheetEntriesList){
					     String billableType = "Billable";
					     TimesheetTaskDTO timesheetTaskDTO	= new TimesheetTaskDTO();
					     if(te.getProjectTask() != null){
					    	 taskSize.add(te.getProjectTask().getId());
					    	 billableType = te.getTaskType();
					    	 timesheetTaskDTO.setId(te.getProjectTask().getId());
					    	 timesheetTaskDTO.setTaskName(te.getProjectTask().getTaskName());
					     }
						
						 timesheetTaskDTO.setTaskType(billableType);

						 if(monDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String mondayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(mondayHours)){
								 mondayHours = "00:00";
							 }
							 TimesheetEntriesDTO teMonDTO = new TimesheetEntriesDTO();
							 teMonDTO.setId(te.getId());
							 teMonDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teMonDTO.setDayOfTheDate("Mon");
							 teMonDTO.setDate(monDayDTO.getDate());
							 teMonDTO.setEditable(monDayDTO.isEditable());
							 teMonDTO.setHours(mondayHours);
							 teMonDTO.setComments(te.getComments());
							 entriesDTOList.add(teMonDTO);
							 monDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")), monDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								monDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")), monDayNonBillableHours);
							 }
							 else{
								monDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(mondayHours.replace(":", ".")), monDayBillableHours);
							 }
							 
						 }else if(tuesDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String tuesdayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(tuesdayHours)){
								 tuesdayHours = "00:00";
							 }
							 TimesheetEntriesDTO teTueDTO = new TimesheetEntriesDTO();
							 teTueDTO.setId(te.getId());
							 teTueDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teTueDTO.setDayOfTheDate("Tue");
							 teTueDTO.setDate(tuesDayDTO.getDate());
							 teTueDTO.setEditable(tuesDayDTO.isEditable());
							 teTueDTO.setHours(tuesdayHours);
							 teTueDTO.setComments(te.getComments());
							 entriesDTOList.add(teTueDTO);
							 tuesDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")), tuesDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								tuesDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")), tuesDayNonBillableHours);
							 }
							 else{
								tuesDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(tuesdayHours.replace(":", ".")), tuesDayBillableHours);
							 }
							  
						 }else if(wedDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String weddayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(weddayHours)){
								 weddayHours = "00:00";
							 }
							 TimesheetEntriesDTO teWedDTO = new TimesheetEntriesDTO();
							 teWedDTO.setId(te.getId());
							 teWedDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teWedDTO.setDayOfTheDate("Wed");
							 teWedDTO.setDate(wedDayDTO.getDate());
							 teWedDTO.setEditable(wedDayDTO.isEditable());
							 teWedDTO.setHours(weddayHours);
							 teWedDTO.setComments(te.getComments());
							 entriesDTOList.add(teWedDTO);
							 wedDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")), wedDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								wedDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")), wedDayNonBillableHours);
							 }
							 else{
								wedDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(weddayHours.replace(":", ".")), wedDayBillableHours);
							 }
							 
						 }else if(thursDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String thursdayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(thursdayHours)){
								 thursdayHours = "00:00";
							 }
							 TimesheetEntriesDTO teThuDTO = new TimesheetEntriesDTO();
							 teThuDTO.setId(te.getId());
							 teThuDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teThuDTO.setDayOfTheDate("Thu");
							 teThuDTO.setDate(thursDayDTO.getDate());
							 teThuDTO.setEditable(thursDayDTO.isEditable());
							 teThuDTO.setHours(thursdayHours);
							 teThuDTO.setComments(te.getComments());	   
							 entriesDTOList.add(teThuDTO);
							 thursDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")), thursDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								thursDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")), thursDayNonBillableHours);
							 }
							 else{
								 thursDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(thursdayHours.replace(":", ".")), thursDayBillableHours);
							 }
							 
							 
						 }else if(friDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String fridayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(fridayHours)){
								 fridayHours = "00:00";
							 }
							 TimesheetEntriesDTO teFriDTO = new TimesheetEntriesDTO();
							 teFriDTO.setId(te.getId());
							 teFriDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teFriDTO.setDayOfTheDate("Fri");
							 teFriDTO.setDate(friDayDTO.getDate());
							 teFriDTO.setEditable(friDayDTO.isEditable());
							 teFriDTO.setHours(fridayHours);
							 teFriDTO.setComments(te.getComments());	   
							 entriesDTOList.add(teFriDTO);
							 friDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")), friDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								friDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")), friDayNonBillableHours);
							 }
							 else{
								friDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(fridayHours.replace(":", ".")), friDayBillableHours);
							 }
							 
						 }else if(satDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String satdayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(satdayHours)){
								 satdayHours = "00:00";
							 }
							 TimesheetEntriesDTO teSatDTO = new TimesheetEntriesDTO();
							 teSatDTO.setId(te.getId());
							 teSatDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teSatDTO.setDayOfTheDate("Sat");
							 teSatDTO.setDate(satDayDTO.getDate());
							 teSatDTO.setEditable(satDayDTO.isEditable());
							 teSatDTO.setHours(satdayHours);
							 teSatDTO.setComments(te.getComments());	   		              
							 entriesDTOList.add(teSatDTO);
							 satDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")), satDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								 satDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")), satDayNonBillableHours);
							 }
							 else{
								 satDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(satdayHours.replace(":", ".")), satDayBillableHours);
							 }
							 
						 }else if(sunDayDTO.getDate().equalsIgnoreCase(commonUtil.dateStrFromOrgDateFormatter(dateFormat,te.getTimesheetDate()))){
							 String sundayHours = te.getTotalHours();
							 if(StringUtils.isEmpty(sundayHours)){
								 sundayHours = "00:00";
							 }
							 TimesheetEntriesDTO teSunDTO = new TimesheetEntriesDTO();
							 teSunDTO.setId(te.getId());
							 teSunDTO.setTimesheetTaskDTO(timesheetTaskDTO);
							 teSunDTO.setDayOfTheDate("Sun");
							 teSunDTO.setDate(sunDayDTO.getDate());
							 teSunDTO.setEditable(sunDayDTO.isEditable());
							 teSunDTO.setHours(sundayHours);
							 teSunDTO.setComments(te.getComments());	 
							 entriesDTOList.add(teSunDTO);	
							 sunDayTotalHours = CommonUtil.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")), sunDayTotalHours);
							 if(StringUtils.isNotBlank(billableType) && billableType.equalsIgnoreCase("Non-Billable")){
								sunDayNonBillableHours  = CommonUtil.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")), sunDayNonBillableHours);
							 }
							 else{
								sunDayBillableHours = CommonUtil.getCombinedHours(Float.parseFloat(sundayHours.replace(":", ".")), sunDayBillableHours);
							 }
						 }
					}
				  tsDTO.setMaxRowCount(taskSize.size());
		  }
		  else{
			    //TimesheetEntry For Mon
				 TimesheetEntriesDTO teMonDTO = new TimesheetEntriesDTO();
				 teMonDTO.setDayOfTheDate("Mon");
				 teMonDTO.setDate(monDayDTO.getDate());
				 teMonDTO.setEditable(monDayDTO.isEditable());
				 teMonDTO.setHours("HH:MM");
				 entriesDTOList.add(teMonDTO);
				 
				//TimesheetEntry For Tue
				 TimesheetEntriesDTO teTueDTO = new TimesheetEntriesDTO();
				 teTueDTO.setDayOfTheDate("Tue");
				 teTueDTO.setDate(tuesDayDTO.getDate());
				 teTueDTO.setEditable(tuesDayDTO.isEditable());
				 teTueDTO.setHours("HH:MM");
				 entriesDTOList.add(teTueDTO);
				 
				 
				//TimesheetEntry For Wed
				 TimesheetEntriesDTO teWedDTO = new TimesheetEntriesDTO();
				 teWedDTO.setDayOfTheDate("Wed");
				 teWedDTO.setDate(wedDayDTO.getDate());
				 teWedDTO.setEditable(wedDayDTO.isEditable());
				 teWedDTO.setHours("HH:MM");
				 entriesDTOList.add(teWedDTO);
				 
				//TimesheetEntry For Wed
				 TimesheetEntriesDTO teThuDTO = new TimesheetEntriesDTO();
				 teThuDTO.setDayOfTheDate("Thu");
				 teThuDTO.setDate(thursDayDTO.getDate());
				 teThuDTO.setEditable(thursDayDTO.isEditable());
				 teThuDTO.setHours("HH:MM");
				 entriesDTOList.add(teThuDTO);

				 
				//TimesheetEntry For Fri
				 TimesheetEntriesDTO teFriDTO = new TimesheetEntriesDTO();
				 teFriDTO.setDayOfTheDate("Fri");
				 teFriDTO.setDate(friDayDTO.getDate());
				 teFriDTO.setEditable(friDayDTO.isEditable());
				 teFriDTO.setHours("HH:MM");
				 entriesDTOList.add(teFriDTO);
				
				 
				//TimesheetEntry For SAT
				 TimesheetEntriesDTO teSatDTO = new TimesheetEntriesDTO();
				 teSatDTO.setDayOfTheDate("Sat");
				 teSatDTO.setDate(satDayDTO.getDate());
				 teSatDTO.setEditable(satDayDTO.isEditable());
				 teSatDTO.setHours("HH:MM");
				 entriesDTOList.add(teSatDTO);
				
				 
				//TimesheetEntry For SUN
				 TimesheetEntriesDTO teSunDTO = new TimesheetEntriesDTO();
				 teSunDTO.setDayOfTheDate("Sun");
				 teSunDTO.setDate(sunDayDTO.getDate());
				 teSunDTO.setEditable(sunDayDTO.isEditable());
				 teSunDTO.setHours("HH:MM");
				 entriesDTOList.add(teSunDTO);	
				 
		  }
		  tsDTO.setTimesheetEntries(entriesDTOList);

		 if(ts != null && StringUtils.isNotBlank(ts.getTotalHours()))
			 totalHours = Float.parseFloat(ts.getTotalHours());
		 tsDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(totalHours));
		 
		 if(ts != null &&StringUtils.isNotBlank(ts.getBillableHours()))
			 billableHours = Float.parseFloat(ts.getBillableHours());
		 tsDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(billableHours));
		 
		 if(ts != null && StringUtils.isNotBlank(ts.getNonBillableHours()))
			 nonBillableHours = Float.parseFloat(ts.getNonBillableHours());
		 tsDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(nonBillableHours));
			 
		 monDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(monDayTotalHours));
		 monDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(monDayBillableHours));
		 monDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(monDayNonBillableHours));
		 weekDays.add(monDayDTO);
		 
	     tuesDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(tuesDayTotalHours));
		 tuesDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(tuesDayBillableHours));
		 tuesDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(tuesDayNonBillableHours));
		 weekDays.add(tuesDayDTO);
		 
		 wedDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(wedDayTotalHours));
		 wedDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(wedDayBillableHours));
		 wedDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(wedDayNonBillableHours));
		 weekDays.add(wedDayDTO);
		 
		 thursDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(thursDayTotalHours));
		 thursDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(thursDayBillableHours));
		 thursDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(thursDayNonBillableHours));
		 weekDays.add(thursDayDTO);
		 
		 friDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(friDayTotalHours));
		 friDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(friDayBillableHours));
		 friDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(friDayNonBillableHours));
		 weekDays.add(friDayDTO);
		 
		 satDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(satDayTotalHours));
		 satDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(satDayBillableHours));
		 satDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(satDayNonBillableHours));
		 weekDays.add(satDayDTO);
		 
		 
		 sunDayDTO.setTotalHours(CommonUtil.getHoursDisplayFormat(sunDayTotalHours));
		 sunDayDTO.setBillableHours(CommonUtil.getHoursDisplayFormat(sunDayBillableHours));
		 sunDayDTO.setNonBillableHours(CommonUtil.getHoursDisplayFormat(sunDayNonBillableHours));
		 weekDays.add(sunDayDTO);
		 
		 tsDTO.setWeekDays(weekDays);
			 
	     return tsDTO;
   }
   
    @PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
  	@RequestMapping(value = "/saveTimesheetEntries", method = RequestMethod.POST)
  	public ModelAndView saveTimesheetEntriesWeb(TimesheetEntryDetailsDTO tedDTO,HttpServletRequest request) throws Exception {
    	 tedDTO =saveTimesheetEntries(tedDTO, request);
    	 ModelAndView model = new ModelAndView();
         model.addObject("timesheetSubmissionDTO",tedDTO.getTimesheetSubmission());
         LinkedHashMap<Long, LinkedList<TimesheetEntriesDTO>> entriesMap = getEntriesMapForDisplayWeb(tedDTO.getTimesheetSubmission());
    	 model.addObject("entriesMap",entriesMap);
         model.addObject("timesheetEntryDetailsDTO",tedDTO);
    	 model.setViewName("timesheet/timeSheetSubmission");
    	 return model;
    }
    
    @PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
  	@RequestMapping(value = "/rest/saveTimesheetEntries", method = RequestMethod.POST)
  	public @ResponseBody TimesheetEntryDetailsDTO saveTimesheetEntriesMobile(@RequestBody TimesheetEntryDetailsDTO tedDTO,HttpServletRequest request) throws Exception {
    	return saveTimesheetEntries(tedDTO, request);
    }
    
	private TimesheetEntryDetailsDTO saveTimesheetEntries(TimesheetEntryDetailsDTO tedDTO,HttpServletRequest request) throws Exception {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		 	
		 	 TimesheetExpenseEntryDetailsUtilDTO eedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
		 	 eedDTO.setUserId(loginUser.getId());
		 	 eedDTO.setLocationCode(loginOrgLocation.getLocationCode());
			 eedDTO.setProjectMembersId(tedDTO.getProjectMemberId());
			 eedDTO.setTimeSheetsFor(tedDTO.getTimesheetFor());
			 eedDTO.setProjectsId(tedDTO.getProjectId());
			 eedDTO.setMasterCandidatesId(tedDTO.getMasterCandidateId());
			 eedDTO.setCurrentTimeFrequency(tedDTO.getCurrentTimeFrequency());
			 eedDTO.setCurrentTimeSubFrequency(tedDTO.getCurrentTimeSubFrequency());
			 tedDTO =getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, tedDTO, loginOrgLocation, request);
			String status = "";
			String savingType = "";
			TimeSheetSubmissionDTO tsDTO = null;
			if(tedDTO.getTimesheetSubmission() != null){
				tsDTO = tedDTO.getTimesheetSubmission();
			}
			if(tsDTO != null && StringUtils.isNotEmpty(tsDTO.getTimesheetStatus())){
				 status = tsDTO.getTimesheetStatus();
			}else{
				 status = "Draft";
			}
			
			if(StringUtils.isEmpty(tedDTO.getTimesheetFor())){
				savingType = "user";
			}
			else{
				savingType = tedDTO.getTimesheetFor();
			}
			
	    	ProjectMembers projectMember = null;
			if(tedDTO != null && tedDTO.getProjectMemberId() != null){
				  projectMember = projectMembersService.getProjectMemberById(tedDTO.getProjectMemberId());
			}
			Date frequencyStartDate = null;
			Date frequencyEndDate = null;
	    	if(status != null && (status.equalsIgnoreCase("Draft") || status.equalsIgnoreCase("Submitted"))){
					Long submissionId = null;
					if (tsDTO != null && tsDTO.getId() != null ){
						 submissionId = tsDTO.getId();
					}
					
					Date startDate = null;
					Date endDate = null;
					if(StringUtils.isNotEmpty(tedDTO.getCurrentTimeSubFrequency())){
						String[] subfrequency = tedDTO.getCurrentTimeSubFrequency().split("To");
			       		 String subfrstartDate = subfrequency[0].trim();
			       		 String subfrEndDate = subfrequency[1].trim();
			       		 startDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),subfrstartDate);
			       		 endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),subfrEndDate);
			    	}
					
					if(StringUtils.isNotEmpty(tedDTO.getCurrentTimeFrequency())){
						 String[] frequency = tedDTO.getCurrentTimeFrequency().split("To");
			       		 String frstartDate = frequency[0].trim();
			       		 String frEndDate = frequency[1].trim();
			       		 frequencyStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),frstartDate);
			       		 frequencyEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),frEndDate);
					}
		            Long total = 0l;
		            Long totalBillableHours = 0l;
		            Long totalNonBillableHours = 0l;
		            TimesheetSubmissions timesheetSubmissions ;
		            HashSet<SubmissionApproverHierarchy> freshSaHierarchyList = new HashSet<SubmissionApproverHierarchy>();
		            if(submissionId != null){
		            	 timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
		            	 if(timesheetSubmissions != null){
		            		 List<SubmissionApproverHierarchy> saHierarchyList =  submissionApproverHierarchyService.getSubmissionApproverHierarchyBySubmissionId(timesheetSubmissions.getId());
		            		 for(SubmissionApproverHierarchy saHierarchy : saHierarchyList){
		            			   Submission_Status currentSahStatus = saHierarchy.getSubmissionStatus();
		            			   if(currentSahStatus != null && currentSahStatus.equals(Submission_Status.SUBMITTED)  && StringUtils.isNotEmpty(savingType) && !savingType.equalsIgnoreCase("user") && StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft")){
		            				   status = "submitted";
		            			   }
		            			   
		            			   if(StringUtils.isNotEmpty(saHierarchy.getSubmissionStatus().getValue()) && saHierarchy.getSubmissionStatus().getValue().equalsIgnoreCase("approved")){
		            				   status = "approved";
		            				   saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("approved"));
		            			   }
		            			   else{
		            				   saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
		            			   }
		            			  
		            			  if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Draft"))
		    					  saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
		            			  
		            			  if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("Submitted"))
		            			   saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("submitted for approval"));
		            			  
		    					   saHierarchy.setUpdatedDate(CommonUtil.currentDate());
		    					   saHierarchy.setUpdatedBy(loginUser.getUserCode());
		    					   
		    					   if(frequencyStartDate != null)
		    					   saHierarchy.setFrequencyStartDate(frequencyStartDate);
								   if(frequencyEndDate != null)
								   saHierarchy.setFrequencyEndDate(frequencyEndDate);
		            			   freshSaHierarchyList.add(saHierarchy);
		            		 }
		            		 submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(new ArrayList<>(freshSaHierarchyList));
		            		 if(tsDTO != null && StringUtils.isNotEmpty(tsDTO.getWeekComment())){
		            			 timesheetSubmissions.setWeekcomments(tsDTO.getWeekComment());
		            		 }
		            		 timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
	 		     	    	 timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
	 		     	    	 timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		            	 }
		            	 
		            }else{
		            	timesheetSubmissions = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate,endDate,projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
		            	if(timesheetSubmissions == null){
		            		timesheetSubmissions = new TimesheetSubmissions();	
		            		timesheetSubmissions.setStartDate(startDate);
			     	    	timesheetSubmissions.setEndDate(endDate);
			     	    	timesheetSubmissions.setProjectMembers(projectMember);
			     	    	timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE+"");
			     	    	timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
			     	    	timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
			     	    	timesheetSubmissions.setProjects(projectMember.getProjects());
			     	    	timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
		            	}
		            	
		     	    	timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
		     	    	timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
		     	    	 if(tsDTO != null && StringUtils.isNotEmpty(tsDTO.getWeekComment())){
	            			 timesheetSubmissions.setWeekcomments(tsDTO.getWeekComment());
	            		 }
		     	    	
		     	    	ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
		    	    	List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
		    	        if(hierarchyList != null && !hierarchyList.isEmpty()){
		    					for(ProjectApproverHierarchy hierarchy : hierarchyList){
		    						SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
		    						saHierarchy.setHeirarchyId(hierarchy);
		    						saHierarchy.setSubmissionId(timesheetSubmissions);
		    						saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue(status));
		    						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
		    						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
		    						if(frequencyStartDate != null)
			    			        saHierarchy.setFrequencyStartDate(frequencyStartDate);
									if(frequencyEndDate != null)
								    saHierarchy.setFrequencyEndDate(frequencyEndDate);
		    						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
		    						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
		    						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
		    						saHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
		    						freshSaHierarchyList.add(saHierarchy);
		    					}
		    					timesheetSubmissions.setApproverHierarchyList(freshSaHierarchyList);
		    			}
		    			timesheetSubmissionsList.add(timesheetSubmissions);
		    			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		            }
			    	//save  mutliple attachment in timesheet submissison attachment.
		            LinkedList<FileInfo> fileInfoList = new LinkedList<>();
		            if(tsDTO.getFileInfo() != null){
		            	 for(FileInfo file:tsDTO.getFileInfo()) {
		            		 FileInfo freshFile = file;
		            		 if(StringUtils.isEmpty(file.getFileDbId())){
		            			 FileUploadUtil.moveFile(staticPath, file, "timesheetDocuments");
									TimesheetSubmissionsDocuments tsd = new TimesheetSubmissionsDocuments();
									tsd.setTimesheetSubmissions(timesheetSubmissions);
									tsd.setFileUrl(file.getFileURL());
									tsd.setFileName(file.getFileName());
									tsd.setFileContentType(file.getContentType());
									tsd.setStatus(Constants.CANDIDATE_ACTIVE);
									tsd.setCreatedDate(CommonUtil.currentDate());
					                tsd.setCreatedBy(loginUser.getUserCode());
					                tsd.setUpdatedDate(CommonUtil.currentDate());
					                tsd.setUpdatedBy(loginUser.getUserCode());
					                tsd = timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
					                freshFile.setFileDbId(tsd.getId().toString());
		            		 }
			                 fileInfoList.add(freshFile);
			            }
		            }
		            tsDTO.setFileInfo(fileInfoList);
		            
		            if(tsDTO.getRemovedDocumentIds() != null && !tsDTO.getRemovedDocumentIds().isEmpty()){
		            	for(Long removedId :tsDTO.getRemovedDocumentIds()){
		            		deleteSubmissionDocument(removedId);
		            	}
		            }
		            List<Long> oldDbIdList = timesheetDayEntriesDao.getAllTimeSheetDayEntryIdsBySubmissionId(timesheetSubmissions.getId());
		            List<Long> freshIdList = new ArrayList<>();
		            LinkedList<TimeSheetDayEntries> entriesDayList = new LinkedList<>();
		            if(tsDTO.getTimesheetEntries() !=null && !tsDTO.getTimesheetEntries().isEmpty()){
		            	for(TimesheetEntriesDTO teDTO : tsDTO.getTimesheetEntries()){
		            		
		            		if((teDTO.getTimesheetTaskDTO() == null) || (teDTO.getTimesheetTaskDTO() == null && teDTO.getTimesheetTaskDTO().getId() == null))
		            			continue;
		            		ProjectTasks projectTasks = projectTasksService.getTasksId(teDTO.getTimesheetTaskDTO().getId().toString());
		            		String taskType = teDTO.getTimesheetTaskDTO().getTaskType();
		            		
		            		String billableHours="00:00";
		            		String nonBillableHours="00:00";
		        		    TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
		        		    if(teDTO.getId() != null){
		        		    	timesheetDayEntries.setId(teDTO.getId());
		        		    }
							timesheetDayEntries.setProjectTask(projectTasks);
							timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
							total = getTotalHoursFormDayHours(teDTO.getHours(), total);
							if(taskType.equalsIgnoreCase("Billable")) {
								if(StringUtils.isNotEmpty(teDTO.getHours())){
									billableHours = teDTO.getHours();
								}
								totalBillableHours = getTotalHoursFormDayHours(billableHours,totalBillableHours);
								timesheetDayEntries.setBillableHours(billableHours);
								timesheetDayEntries.setTotalHours(billableHours);
								timesheetDayEntries.setTaskType("Billable");
							}else if(taskType.equalsIgnoreCase("Non-Billable")) {
								if(StringUtils.isNotEmpty(teDTO.getHours())){
									nonBillableHours = teDTO.getHours();
								}
								totalNonBillableHours = getTotalHoursFormDayHours(nonBillableHours,totalNonBillableHours);
								timesheetDayEntries.setNonBillableHours(nonBillableHours);
								timesheetDayEntries.setTotalHours(nonBillableHours);
								timesheetDayEntries.setTaskType("Non-Billable");
							}
							
							timesheetDayEntries.setComments(teDTO.getComments());
							timesheetDayEntries.setTimesheetDate(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),teDTO.getDate()));
							timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
							timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
							timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
							timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
		        		    entriesDayList.add(timesheetDayEntries);
		            	}
	            		
	            		if(entriesDayList != null && !entriesDayList.isEmpty()){
		            		for(TimeSheetDayEntries timesheetdayEntries :entriesDayList){
		            			timesheetdayEntries = timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetdayEntries);
		            			freshIdList.add(timesheetdayEntries.getId());
		            		}
		            	}
		            	
		            }
		            oldDbIdList.removeAll(freshIdList);
		            
		            if(oldDbIdList != null && !oldDbIdList.isEmpty()){
		            	for(Long removedEntryId :oldDbIdList){
		            		timesheetDayEntriesDao.deleteTimeSheetDayEntries(removedEntryId);
		            	}
		            }
					if(total != null){
						/**
						 * Total Hours Calculation
						 */
			            int actualHours = (int) (total / 60);
			            int actualMinutes =   (int)(total % 60);
			            String actualMinutesStr = ""+actualMinutes;
			            String startWithZero = "";
			            if(StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length()==1)
			            	startWithZero ="0";	
			            String actualTimeString =  actualHours+"."+startWithZero+actualMinutes;
						timesheetSubmissions.setTotalHours(actualTimeString);
						
						/**
						 * Billable Hours Calculation
						 */
						int actualBillableHours = (int) (totalBillableHours / 60);
			            int actualBillableMinutes =   (int)(totalBillableHours % 60);
			            String actualBillableMinutesStr = ""+actualBillableMinutes;
			            String billableStartWithZero = "";
			            if(StringUtils.isNotEmpty(actualBillableMinutesStr) && actualBillableMinutesStr.length()==1)
			            	billableStartWithZero ="0";	
			            String actualBillableTimeString =  actualBillableHours+"."+billableStartWithZero+actualBillableMinutes;
						timesheetSubmissions.setBillableHours(actualBillableTimeString);
						
						/**
						 * Non-Billable Hours Calculation
						 */
						int actualNonBillableHours = (int) (totalNonBillableHours / 60);
			            int actualNonBillableMinutes =   (int)(totalNonBillableHours % 60);
			            String actualNonBillableMinutesStr = ""+actualNonBillableMinutes;
			            String nonBillableStartWithZero = "";
			            if(StringUtils.isNotEmpty(actualNonBillableMinutesStr) && actualNonBillableMinutesStr.length()==1)
			            	nonBillableStartWithZero ="0";	
			            String actualNonBillableTimeString =  actualNonBillableHours+"."+nonBillableStartWithZero+actualNonBillableMinutes;
						timesheetSubmissions.setNonBillableHours(actualNonBillableTimeString);
						timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
						
						//send mail to assigned manager at time sheet submission
						if(!actualTimeString.equalsIgnoreCase("0.0")){
							if(!"Draft".equalsIgnoreCase(status) 
									&& projectMember != null 
									&&  projectMember.getProjects() != null
									&& frequencyStartDate != null
									&& frequencyEndDate != null ){
								sendEmailToManagerAtTimesheetSubmissions(projectMember.getMasterCandidateId().getId(),projectMember.getProjects().getId(),frequencyStartDate,frequencyEndDate,loginUser,loginOrgLocation,loginOrgLocation.getOrgCode());
							}
						}
					}
					if(status != null &&  status.equalsIgnoreCase("Submitted")){
					if (tedDTO.getTimeSubFrequencies() != null && !tedDTO.getTimeSubFrequencies().isEmpty()){
						   	LinkedList<String> tabDates = new LinkedList(tedDTO.getTimeSubFrequencies().keySet());
						   	if(tabDates != null && !tabDates.isEmpty()){ 
								for(String tabDate : tabDates){
									tabDate = tabDate.replace("\"", "");
									String[] words = tabDate.split("To");	
									Date actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),words[0].replaceAll("/", "-"));
									Date actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),words[1].replaceAll("/", "-"));
									List<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(actualStartDate, actualEndDate, projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
						            if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
						            	for(TimesheetSubmissions ts : timesheetSubmissionsList){
						            		List<SubmissionApproverHierarchy>  sahList =submissionApproverHierarchyService.getSubmissionApproverHierarchyBySubmissionId(ts.getId());
											if(sahList != null){
												for(SubmissionApproverHierarchy sah :  sahList){
													if(sah != null && sah.getId() != null){
														if(frequencyStartDate != null)
														sah.setFrequencyStartDate(frequencyStartDate);
														if(frequencyEndDate != null)
														sah.setFrequencyEndDate(frequencyEndDate);
														sah.setSubmissionStatus(Submission_Status.SUBMITTED);
														sah.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
														submissionApproverHierarchyService.addSubmissionApproverHierarchy(sah);
										            }
												}
											}
						            	}
						            	
						            }
									else{
							            	insertNewTimesheetRecordByProjectMemberAndDates(projectMember,actualStartDate, actualEndDate,frequencyStartDate,frequencyEndDate,loginUser,candidate);
									}
									
								}
						   	}	
									  				
						}
					   }
					 tedDTO =  getTimesheetSubFrequencyListByCurrentFrequency(eedDTO, tedDTO, loginOrgLocation, request);
				     tsDTO = getTimesheetSubmissionDetails(eedDTO, request);
				     tedDTO.setTimesheetSubmission(tsDTO);
	    	}
			return tedDTO;
	}
	
	@PreAuthorize("hasRole('ROLE_MODIFY_MY_TIMESHEETS')")
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody void withDrawTimesheet(@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="dateFormat", required=false) String dateFormat,HttpServletRequest request) throws Exception {
			//DateFormat format = Constants.DF_DMY;
		//DateFormat  format = LoginSessionController.DATEFORMAT;
			if(StringUtils.isNotEmpty(timesheetFrequency)){
			 	 String[] frequency = timesheetFrequency.split("To");
			 	 String weekStartDate = frequency[0].trim();
			  	 String weekEndDate = frequency[1].trim();
			  	 Date startDate = commonUtil.orgLocationDateFormat(dateFormat,weekStartDate.replaceAll("/", "-"));
				 Date endDate = commonUtil.orgLocationDateFormat(dateFormat,weekEndDate.replaceAll("/", "-"));
				 ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			  	 submissionApproverHierarchyService.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(pm.getProjects().getId(),pm.getMasterCandidateId().getId(), startDate, endDate, Submission_Status.DRAFT.getValue());;
			}
		
	}
	
		
	
	/**
	 * 
	 * @param timesheetSubmissions
	 * @param loginUser
	 * @param loginOrgLocation
	 * @param loginOrgCode
	 * @throws Exception
	 */
	private void sendEmailToManagerAtTimesheetSubmissions(Long masterCanidateId,Long projectId,Date frequencyStartDate,Date frequencyEndDate, User loginUser, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
		List<ProjectApproverHierarchy> approverHierarchyList = projectApproverHierarchyService.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(masterCanidateId,projectId);
		for(ProjectApproverHierarchy approver : approverHierarchyList){
				
			if(!loginUser.getUserCode().equalsIgnoreCase(approver.getApproverUserId().getUserCode())){
				
			 User projectManager = userService.getUserById(approver.getApproverUserId().getId());
			 //Employees emp = employeesService.getEmployeeByUserId(loginUser.getId());
				
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(projectManager.getFirstName());
			 emailMessage.setLastName(projectManager.getLastName());
			 emailMessage.setToEmail(projectManager.getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
//			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
			 
			 /*String pattern = "dd-MM-yyyy";
	         SimpleDateFormat format = new SimpleDateFormat(pattern);*/
	         String startDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyStartDate);
	         String endDateStr = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),frequencyEndDate);

			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
//			 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
//			 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
			 StringBuilder loginName = new StringBuilder();
			 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
				 globalVarsMap.put("loginUserFullName",loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUserFullName",loginUser.getFirstName());
			 }
			 StringBuilder managerName = new StringBuilder();
			 if(null != projectManager.getLastName() && !"".equalsIgnoreCase(projectManager.getLastName())){
				 globalVarsMap.put("managerFullName",managerName.append(projectManager.getFirstName()).append(" ").append(projectManager.getLastName()).toString());
			 }else{
				 globalVarsMap.put("managerFullName",projectManager.getFirstName());
			 }
//			 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
//			 globalVarsMap.put("managerLastName",projectManager.getLastName());
			 //globalVarsMap.put("projectName", project.getProjectName());
			 globalVarsMap.put("orgName",loginOrgLocation.getOrgLocationName());
			 globalVarsMap.put("timeFrequency",startDateStr+" to "+endDateStr);
			 globalVarsMap.put("loginURL","<a href='"+sourceleadLoginUrl+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER_TIMESHEET,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER_TIMESHEET,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario);
			
		}
	}
	}
	
    @Async
    public void insertNewTimesheetRecordByProjectMemberAndDates(ProjectMembers projectMember,Date actualStartDate,Date actualEndDate,Date frequencyStartDate,Date frequencyEndDate,User loginUser,Candidate cnadidate) throws Exception{
				
	    	TimesheetSubmissions timesheetSubmissions  = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate,actualEndDate,projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
	    	if(timesheetSubmissions == null){
	    		timesheetSubmissions =  new TimesheetSubmissions();
		    	timesheetSubmissions.setStartDate(actualStartDate);
		    	timesheetSubmissions.setEndDate(actualEndDate);
		    	timesheetSubmissions.setProjectMembers(projectMember);
		    	timesheetSubmissions.setProjects(projectMember.getProjects());
		    	timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
		    	timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE+"");
		    	timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
		    	timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
	    	}
	    	timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
	    	timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
	    	timesheetSubmissions.setWeekcomments("");
	    	ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
	    	List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
	    	HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
	        if(hierarchyList != null && !hierarchyList.isEmpty()){
					for(ProjectApproverHierarchy hierarchy : hierarchyList){
						SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
						saHierarchy.setHeirarchyId(hierarchy);
						saHierarchy.setSubmissionId(timesheetSubmissions);
						if(frequencyStartDate != null)
    			        saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if(frequencyEndDate != null)
					    saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setSubmissionStatus(Submission_Status.SUBMITTED);
						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
						saHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
						saHierarchyList.add(saHierarchy);
					}
					timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
			}
			timesheetSubmissionsList.add(timesheetSubmissions);
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			
			while(actualStartDate.before(actualEndDate) || actualStartDate.equals(actualEndDate)){
				TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
				timesheetDayEntries.setBillableHours("00:00");
				timesheetDayEntries.setNonBillableHours("00:00");
				timesheetDayEntries.setTotalHours("00:00");
				timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
				timesheetDayEntries.setTimesheetDate(actualStartDate);
				timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
				timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
				timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
				timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
				timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetDayEntries);
				actualStartDate = CalendarUtil.getNextDate(actualStartDate);
			}
		}
    
       @RequestMapping(value = "/addTimeSheetDayEntries")
       public List<TimeSheetDayEntries> addTimeSheetDayEntries(TimesheetProjectTaskDTO timesheetProjectTaskDTO,HttpServletRequest request) throws Exception {
    	 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 List<TimesheetProjectMemberDTO> assignedMember = timesheetProjectTaskDTO.getAssignedMember();
	 	 LinkedList<TimeSheetDayEntries> timeSheetDayEntriesList = new LinkedList<>();
	 	 if(assignedMember != null && !assignedMember.isEmpty()){
	 		 for(TimesheetProjectMemberDTO memberDTO : assignedMember){
	 			TimeSheetDayEntries tde = addTimesheetDayEntriesByProjectIdAndProjectMemberId(timesheetProjectTaskDTO.getProjectId(), memberDTO.getProjectMemberId(), timesheetProjectTaskDTO.getProjectTaskId(), memberDTO.getTimesheetDate(), memberDTO.getHours(),loginUser, loginOrgLocation);
	 			timeSheetDayEntriesList.add(tde);
	 		 }
	 	 }
	 	 return null;
       }
       
       private TimeSheetDayEntries addTimesheetDayEntriesByProjectIdAndProjectMemberId(Long projectId,Long projectMemberId,Long projectTaskId,Date timesheetDate,String hours,User loginUser,OrgLocations loginOrgLocation) throws Exception{
    	   Projects projects = projectsService.getProjectById(projectId);
    	   ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
    	   Date frequencyStartDate = null;
    	   Date frequencyEndDate =  null;
    	   Date actualStartDate = null;
    	   Date actualEndDate = null;
    	   if(projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Monthly")){
    		   frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate,Constants.CURRENT_MONTH).get("STARTDATE");
    		   frequencyEndDate = CalendarUtil.getMonthEndDateByGivenDate(timesheetDate,Constants.CURRENT_MONTH).get("ENDDATE");
    	   }else if(projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Semi-Monthly")){
    		      Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(timesheetDate,Constants.CURRENT_MONTH).get("STARTDATE");
				  if(timesheetDate.after(empMonth15thDate)){
					  frequencyStartDate = CalendarUtil.getMonth16thDateByGivenDate(timesheetDate,Constants.CURRENT_MONTH).get("STARTDATE");
					  frequencyEndDate   =  CalendarUtil.getMonthEndDateByGivenDate(frequencyStartDate, 0).get("ENDDATE");
				  }
				  else{
					  frequencyStartDate = CalendarUtil.getMonthStartDateByGivenDate(timesheetDate,Constants.CURRENT_MONTH).get("STARTDATE");
					  frequencyEndDate = CalendarUtil.getMonth15thDateByGivenDate(frequencyStartDate, 0).get("STARTDATE");
				  }
    	   }else if(projects != null && projects.getInvoiceFrequency().equalsIgnoreCase("Bi-Weekly")){
    		    Date biWeeklyStartMondayDate = timesheetFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDate(projects,timesheetDate);
    		    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				cal.setLenient(false);
				cal.setTime(biWeeklyStartMondayDate);
				Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",loginOrgLocation.getDateFormat());
				frequencyStartDate = startMonday.getTime();
				Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
				frequencyEndDate = endSunday.getTime();
    	   }else{
    		    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
				cal.setLenient(false);
				cal.setTime(timesheetDate);
				Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",loginOrgLocation.getDateFormat());
				frequencyStartDate = startMonday.getTime();
				Calendar endSunday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SUN",loginOrgLocation.getDateFormat());
				frequencyEndDate = endSunday.getTime();
				
           }
    	   Map<String,String> timeSubFrequencyMap = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), frequencyStartDate), commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), frequencyEndDate),loginOrgLocation.getDateFormat());
    	  
    	   for (Map.Entry<String, String> entry : timeSubFrequencyMap.entrySet()) {
			     String key = entry.getKey();
			     String value = entry.getValue();
				
			     String date = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), timesheetDate);
				 if(CalendarUtil.isGivenDateBetweenDateRange(key, value,date,loginOrgLocation.getDateFormat())){
					actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),key.replaceAll("/", "-"));
					actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),value.replaceAll("/", "-"));
				 }
			     
			}
    	   return addTimesheetDayEntry(projectMember, actualStartDate, actualEndDate, frequencyStartDate, frequencyEndDate, loginUser,timesheetDate,hours,projectTaskId);
       }
       
       private TimeSheetDayEntries addTimesheetDayEntry(ProjectMembers projectMember,Date actualStartDate,Date actualEndDate,Date frequencyStartDate,Date frequencyEndDate,User loginUser,Date timesheetDate,String hours,Long projectTaskId) throws Exception{
    	    TimesheetSubmissions timesheetSubmissions  = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate,actualEndDate,projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
	    	if(timesheetSubmissions == null){
	    		timesheetSubmissions =  new TimesheetSubmissions();
		    	timesheetSubmissions.setStartDate(actualStartDate);
		    	timesheetSubmissions.setEndDate(actualEndDate);
		    	timesheetSubmissions.setProjectMembers(projectMember);
		    	timesheetSubmissions.setProjects(projectMember.getProjects());
		    	timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
		    	timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE+"");
		    	timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
		    	timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
	    	}
	    	timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
	    	timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
	    	timesheetSubmissions.setWeekcomments("");
	    	ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
	    	List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
	    	HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
	        if(hierarchyList != null && !hierarchyList.isEmpty()){
					for(ProjectApproverHierarchy hierarchy : hierarchyList){
						SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
						saHierarchy.setHeirarchyId(hierarchy);
						saHierarchy.setSubmissionId(timesheetSubmissions);
						if(frequencyStartDate != null)
    			        saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if(frequencyEndDate != null)
					    saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setSubmissionStatus(Submission_Status.DRAFT);
						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
						saHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
						saHierarchyList.add(saHierarchy);
					}
					timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
			}
			timesheetSubmissionsList.add(timesheetSubmissions);
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			TimeSheetDayEntries timesheetDayEntry = null;
			while(actualStartDate.before(actualEndDate) || actualStartDate.equals(actualEndDate)){
				if(actualStartDate.equals(timesheetDate)){
					TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
					ProjectTasks projectTasks = projectTasksService.getTasksId(projectTaskId.toString());
            		String taskType =projectTasks.getBillableType();
            		timesheetDayEntries.setProjectTask(projectTasks);
            		if(taskType.equalsIgnoreCase("Billable")) {
            			timesheetDayEntries.setBillableHours(hours);
    					timesheetDayEntries.setNonBillableHours("00:00");
    					timesheetDayEntries.setTotalHours(hours);
            		}else{
            			timesheetDayEntries.setBillableHours("00:00");
    					timesheetDayEntries.setNonBillableHours(hours);
    					timesheetDayEntries.setTotalHours(hours);
            		}
					
					timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
					timesheetDayEntries.setTimesheetDate(actualStartDate);
					timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
					timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
					timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetDayEntries);
					timesheetDayEntry = timesheetDayEntries;
				}else{
					TimeSheetDayEntries timesheetDayEntries = new TimeSheetDayEntries();
					timesheetDayEntries.setBillableHours("00:00");
					timesheetDayEntries.setNonBillableHours("00:00");
					timesheetDayEntries.setTotalHours("00:00");
					timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
					timesheetDayEntries.setTimesheetDate(actualStartDate);
					timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
					timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
					timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetDayEntries);
				}
				
				actualStartDate = CalendarUtil.getNextDate(actualStartDate);
			}
			
			return timesheetDayEntry;
       }
        
		public static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
		
		private Long getTotalHoursFormDayHours(String dayHours,Long total){
			String hours = "00";
			String min = "00";
			if(StringUtils.isNotEmpty(dayHours)){
			    String[] parts = dayHours.split(":");
				hours = parts[0]; 
				min = parts[1]; 
			}
			Long  localHours = Long.parseLong(hours) * 60;
			Long  localHoursMinute = Long.parseLong(min);
			Long  finalmin = localHours + localHoursMinute;
			total = total + finalmin;
			return total;
		}

		@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
		@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
	    public void downloadDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
			
			
				TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);	
			    //TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());
			    
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(timesheetSubmissionsDocuments.getFileName()).append("\"").toString());
				InputStream inputStream = new ByteArrayInputStream(timesheetSubmissionsDocuments.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 

		}  
		
		@RequestMapping(value = "/notifyReopenTimeSheet", method = RequestMethod.POST)
		public @ResponseBody void notifyReopenTimeSheet(@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
				@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
				@RequestParam(value="reason", required=false) String reason,
				@RequestParam(value="totalHours", required=false) Float totalHours, HttpServletRequest request) throws Exception {
				
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    String isSuperUser = Constants.NO;
			    if(commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(projectMemberId, loginUser)){
			    	isSuperUser = Constants.YES;
			    }
			    
				//DateFormat format = Constants.DF_DMY;
			    //DateFormat  format = LoginSessionController.DATEFORMAT;
				if(StringUtils.isNotEmpty(timesheetFrequency)){
				 	 String[] frequency = timesheetFrequency.split("To");
				 	 String weekStartDate = frequency[0].trim();
				  	 String weekEndDate = frequency[1].trim();
				  	 Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekStartDate.replaceAll("/", "-"));
					 Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekEndDate.replaceAll("/", "-"));
				  	submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,loginUser,"notify",isSuperUser);
				
				    //send mail & notification logic
				  	if(isSuperUser.equalsIgnoreCase(Constants.NO)){
				  		submissionApproverHierarchyService.sendReopenTimeSheetMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,totalHours);
				  	}
				  	
				}
			
		}
		
		
		@RequestMapping(value = "/ws/acceptOrRejectReopenTimeSheetBySuperUser", method = RequestMethod.POST)
		public @ResponseBody void acceptOrRejectReopenTimeSheetBySuperUser(@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
				@RequestParam(value="reOpenedUserId", required=false) Long reopenedUserId,
				@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
				@RequestParam(value="type", required=false) String type,
				@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) throws Exception {
				
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    User reopenedUser = userService.getUserById(reopenedUserId);
				//DateFormat format = Constants.DF_DMY;
			    //DateFormat  format = LoginSessionController.DATEFORMAT;
				if(StringUtils.isNotEmpty(timesheetFrequency) && null != type && "accept".equalsIgnoreCase(type)){
				 	 String[] frequency = timesheetFrequency.split("To");
				 	 String weekStartDate = frequency[0].trim();
				  	 String weekEndDate = frequency[1].trim();
				  	 Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekStartDate.replaceAll("/", "-"));
					 Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekEndDate.replaceAll("/", "-"));
				  	 submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,loginUser,type,Constants.YES);
				
				    //send mail & notification logic
				  	submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				  	
				}else if(StringUtils.isNotEmpty(timesheetFrequency) && null != type && "reject".equalsIgnoreCase(type) && null != reason && !"".equalsIgnoreCase(reason)){
					 String[] frequency = timesheetFrequency.split("To");
				 	 String weekStartDate = frequency[0].trim();
				  	 String weekEndDate = frequency[1].trim();
				  	 Date startDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekStartDate.replaceAll("/", "-"));
				  	 Date endDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),weekEndDate.replaceAll("/", "-"));
				  	 submissionApproverHierarchyService.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,loginUser,type,Constants.YES);
				
				    //send mail & notification logic
				  	submissionApproverHierarchyService.sendReopenTimeSheetAcceptOrRejectMail(projectMemberId,reason,loginUser,orgLoc,timesheetFrequency,type,reopenedUser);
				}
			
		}
		
		
		@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS')")
		@RequestMapping(value = "/deleteSubmissionDocument", method = RequestMethod.POST)
	    public String deleteSubmissionDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId) throws Exception {
	        
	            /*TimesheetSubmissions timesheetSubmissions =    timesheetSubmissionsService.getTimesheetSubmissions(submissionId.toString());*/
				TimesheetSubmissionsDocuments	timesheetSubmissionsDocuments =	timesheetSubmissionsDocumentsService.getTimesheetSubmissionsDocuments(submissionDocumentId);
	            timesheetSubmissionsDocuments.setDocFile(null);
	            timesheetSubmissionsDocuments.setFileContentType(null);
	            timesheetSubmissionsDocuments.setFileName(null);
	            timesheetSubmissionsDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	            timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);
	            return "success";
	    }
		
}