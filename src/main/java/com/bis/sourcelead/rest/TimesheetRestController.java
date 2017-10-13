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
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.helper.ProjectMemberDTOConverter;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
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
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
import com.bis.sourcelead.util.TimesheetApprovalFrequencyGeneratorUtil;
import com.bis.sourcelead.util.TimesheetFrequencyGeneratorUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 

@RestController 
public class TimesheetRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
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
	
	/*@RequestMapping(value = "/rest/getTimesheetEntryDetails")
	public TimesheetEntryDetailsDTO getTimesheetEntryDetails(@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO) throws Exception
	{
		TimesheetEntryDetailsDTO timesheetEntryDetailsDTO = new TimesheetEntryDetailsDTO();
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		Candidate candidate = candidateService.getCandidateByConsultantUserId(eedDTO.getUserId(), orgLocations.getId());
		List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocations.getId(),candidate.getId());
		if (projectMembersList != null && !projectMembersList.isEmpty()) {
			ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
			long count = 1;
			Long initialProjectMemberId = null;
			Long initialProjectId = null;
			Long masterCandidateId = null;
			Map<String,String>  timesheetFrequencyMap  = null;
			for (ProjectMembers pm : projectMembersList) {
                if(count == 1){
                    timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm,"view");
                    if(timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
                        continue;
					
                    initialProjectMemberId = pm.getId();
					initialProjectId = pm.getProjects().getId();
					masterCandidateId = pm.getMasterCandidateId().getId();
					count++;
				}
				pmDTOList.add(ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm));
			}
			timesheetEntryDetailsDTO.setProjectMembersList(pmDTOList);
			timesheetEntryDetailsDTO.setProjectMemberId(initialProjectMemberId);
			StringBuilder selectedStartDate = new StringBuilder("");
			StringBuilder selectedEndDate =  new StringBuilder("");
			TimesheetEntriesDTO entriesDTO = new TimesheetEntriesDTO();
			if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
				long flag = 1;
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
				    String key = entry.getKey();
				    String value =entry.getValue();
				    Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
				    Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
				    HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(initialProjectMemberId, currentStartDate, currentEndDate);
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
				    timesheetEntriesDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+key.replaceAll("/", "-")+"To"+value.replaceAll("/", "-")+"'>");
				    timesheetEntriesDiv.append(key).append(" To ").append(value);
				    timesheetEntriesDiv.append("		                  </option>");
				    flag++;
			   }
			}
			
		}
		return null;
		
	}
	*/
	
	@RequestMapping(value = "/timesheetEntriesDivByProjectMemberId", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetEntriesDivByProjectMemberId(HttpServletRequest request,
			@RequestParam(value="projectMemberId", required=false) String projectMemberId,
			@RequestParam(value="from", required=false) String from,
			@RequestParam(value="locationCode") String locationCode) throws Exception {
		
		    Long superUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "superUserId");
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
	    	StringBuilder projectDiv = new StringBuilder("");
		    if(StringUtils.isNotEmpty(projectMemberId)){
		    	 ProjectMembers pm = projectMembersService.getProjectMemberById(Long.parseLong(projectMemberId));
				 Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(Long.parseLong(projectMemberId), loginUser);
		    	 projectDiv = timesheetEntriesDivByProjectMemberId(loginUser,projectMemberId,from,superUserId,isSuperUser,orgLocations,pm.getId(),pm.getMasterCandidateId().getId());
		    }
		    return projectDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetDivByFrequency", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetDivByFrequency(HttpServletRequest request,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="from", required=false) String from,
			@RequestParam(value="locationCode") String locationCode) throws Exception {
		
		Long superUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "superUserId");
		
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		StringBuilder frequencyBasedDiv = new StringBuilder();
		if(StringUtils.isNotEmpty(timesheetFrequency)){
		 	 String[] frequency = timesheetFrequency.split("To");
		 	 String weekStartDate = frequency[0].trim();
		  	 String weekEndDate = frequency[1].trim();
		     ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
		     Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(projectMemberId, loginUser);
			 frequencyBasedDiv = timesheetEntriesDivByFrequnecy(loginUser,weekStartDate,weekEndDate,projectMemberId,from,superUserId,isSuperUser,orgLocations,pm.getProjects().getId(),pm.getMasterCandidateId().getId());
		}
		
		return frequencyBasedDiv.toString();
	}
	
	@RequestMapping(value = "/timesheetDivByTab", method = RequestMethod.POST ) 
	public @ResponseBody String timesheetDivByTab(HttpServletRequest request,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="approvalStatus", required=false) String approvalStatus,
			@RequestParam(value="from", required=false) String from,
			@RequestParam(value="locationCode") String locationCode) throws Exception {
		    StringBuilder tabDiv = new StringBuilder("");
		    OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		     if(StringUtils.isNotEmpty(timesheetFrequency)){
		    	 ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			 	 String[] frequency = timesheetFrequency.split("To");
			  	 String weekEndDate = frequency[1].trim();
				  tabDiv =  projectTabDataByDates(startDate, endDate,projectMemberId,weekEndDate,approvalStatus,from,orgLocations,pm.getMasterCandidateId().getId());
		    }
		    return tabDiv.toString();
	}
	
	
	@RequestMapping(value = "/timesheetDivAfterSaving", method = RequestMethod.POST ) 
	public @ResponseBody  HashMap<String,String> timesheetDivAfterSaving(HttpServletRequest request,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="timesheetFrequency", required=false) String timesheetFrequency,
			@RequestParam(value="approvalStatus", required=false) String approvalStatus,
			@RequestParam(value="from", required=false) String from,
			@RequestParam(value="locationCode") String locationCode) throws Exception {
		
		    Long superUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "superUserId");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			  
		    Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(projectMemberId, loginUser);
		    StringBuilder tabDiv = new StringBuilder("");
		    StringBuilder statusDiv = new StringBuilder("");
		    Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		  
		    HashMap<String,String> result = new HashMap<>();
		     if(StringUtils.isNotEmpty(timesheetFrequency)){
			 	 String[] frequency = timesheetFrequency.split("To");
			 	 String actualStartDate = frequency[0].trim();
			  	 String actualEndDate = frequency[1].trim();
			  	 ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			  	  
				 tabDiv =  projectTabDataByDates(startDate, endDate,projectMemberId,actualEndDate,approvalStatus,from,orgLocations,pm.getMasterCandidateId().getId());
				 result.put("TabDiv",tabDiv.toString());
				 
				 //DateFormat format = Constants.DF_DMY;
				 //DateFormat  format = LoginSessionController.DATEFORMAT;
				 Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),actualStartDate.replaceAll("/", "-"));
				 Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),actualEndDate.replaceAll("/", "-"));
				  
				 SubmissionApproverHierarchy sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, pm.getProjects().getId(),pm.getMasterCandidateId().getId());
				 Submission_Status submissionStatus = sah.getSubmissionStatus();
			 	 String submissionStatusStr = submissionStatus.getValue();
			 	 
			     String pullRightClass = "pull-right";
			     String styleWidth = "";
			     String innerStyleWidth = "";
			     String statusDivWidth = "";
			     if(from != null && !from.isEmpty() && from.equalsIgnoreCase("user")){
				   pullRightClass = "" ;
			     }
			    SubmissionApproverHierarchy isSah = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(pm.getProjects().getId(),pm.getMasterCandidateId().getId(), frequencyStartDate, frequencyEndDate, "APPROVED");
			    boolean isSahApproved = false;
			    if((isSah == null &&from.equalsIgnoreCase("user") && isModifyMyTimeSheet && !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())))
			       || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()) && from.equalsIgnoreCase("approver") && StringUtils.isEmpty( sah.getReopenReason())) ){
			        if(isSah == null)
			    	isSahApproved = true;
			    	styleWidth = "style='width:244px;'";
				   innerStyleWidth = "style='width:40%;'";
				   statusDivWidth = "244";
			     }
			    
			    if(!isNullOrEmpty(submissionStatusStr)  && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()) && from.equalsIgnoreCase("approver") && StringUtils.isNotEmpty(sah.getReopenReason())){
					   styleWidth = "style='width:300px;'";
					   innerStyleWidth = "style='width:40%;'";
				   }
			    
			    boolean isSahReopened = false;
			    if(null != isSah && from.equalsIgnoreCase("approver") && null != isSah.getReopenReason() && !"".equalsIgnoreCase(isSah.getReopenReason()) /*&& null != isSah.getReopenRejectedReason() && !"".equalsIgnoreCase(isSah.getReopenRejectedReason())*/){
				   isSahReopened = true;
			    }
			    
				 StringBuilder submissionStatusDiv = getSubmissionStatusDiv(sah ,projectMemberId,frequencyStartDate,frequencyEndDate,startDate,endDate,from,isSahApproved,pullRightClass,styleWidth,innerStyleWidth,superUserId,isSahReopened,isSuperUser,pm.getProjects().getId(),pm.getMasterCandidateId().getId());
				 statusDiv.append("<div>");
				 statusDiv.append(submissionStatusDiv);
			     result.put("StatusDiv",statusDiv.toString());
			     
			     result.put("statusDivWidth",statusDivWidth);
		    }
		    return result;
	}
	
	 
	@RequestMapping(value = "/timesheetEntries", method = RequestMethod.POST )   
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	public @ResponseBody String timesheetEntries(HttpServletRequest request,
			@RequestParam(value="weekStartDate", required=false) String weekStartDate,
			@RequestParam(value="weekEndDate", required=false) String weekEndDate,
			@RequestParam(value="from", required=false) String from) throws Exception {
			 
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Long superUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "superUserId");
		List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLoc.getId(),candidate.getId());
		
		if(projectMembersList != null && !projectMembersList.isEmpty()){
		
			StringBuilder timesheetEntriesDiv = new StringBuilder();
			timesheetEntriesDiv.append("");
			
			timesheetEntriesDiv.append("		<div class='timesheet_out'> ");
			timesheetEntriesDiv.append("		<div class='timesheet_wrap' id='timesheet_wrap'>");
			timesheetEntriesDiv.append("		<div class='data_loading'></div>");
			
			timesheetEntriesDiv.append("				<div class='timesheetNewHead'>");
			
			timesheetEntriesDiv.append("					<div class='timeNewSe'>");
			
			timesheetEntriesDiv.append("		                <select id='projectMemberId' name='projectMemberId' class='form-control chosen-select' onChange='timesheetEntriesDivByProjectMemberId()'>");
			Long initialProjectMemberId = null;
			Long initialProjectId = null;
			Long masterCandidateId = null;
			StringBuilder isSelected = new StringBuilder("");
			if(projectMembersList != null && !projectMembersList.isEmpty()){
				long count = 1;
				for(ProjectMembers pm :projectMembersList){
					Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(pm,"view");
                    if(timesheetFrequencyMap == null || timesheetFrequencyMap.isEmpty())
                        continue;
					if(count == 1){
						isSelected.setLength(0);
						isSelected.append("selected='selected'");
						initialProjectMemberId = pm.getId();
						initialProjectId = pm.getProjects().getId();
						masterCandidateId = pm.getMasterCandidateId().getId();
						
					}
					else{
						isSelected.setLength(0);
					}
					 count++;
					timesheetEntriesDiv.append("		                  <option  "+isSelected.toString()+" value='"+pm.getId()+"'>"+pm.getProjects().getProjectName()+"</option>");
				   
				}
				
			}
			timesheetEntriesDiv.append("		                </select>");
			timesheetEntriesDiv.append("		            </div>");
			
				timesheetEntriesDiv.append("					<div id='timesheetEntriesDivByProjectMemberId'>");
				if(initialProjectMemberId != null){
					 Boolean isSuperUser = commonUtil.isSuperAdminForProjectMemberToReopenTimeOrExpenseses(initialProjectMemberId, loginUser);
					 StringBuilder projectDiv = timesheetEntriesDivByProjectMemberId(loginUser,initialProjectMemberId.toString(),from,superUserId,isSuperUser,orgLoc,initialProjectId,masterCandidateId);
					 timesheetEntriesDiv.append(projectDiv);		 
				}
				timesheetEntriesDiv.append("		            </div>");	
			
			timesheetEntriesDiv.append("		    </div>");	
			timesheetEntriesDiv.append("		</div>  ");	
			
			return timesheetEntriesDiv.toString();
		}else{
			return "";
		}
	}
	
	public StringBuilder timesheetEntriesDivByProjectMemberId(User loginUser,String projectMemberId,String from,Long superUserId,boolean isSuperUser,OrgLocations orgLocations,Long projectId,Long masterCandidateId) throws Exception{
		StringBuilder timesheetEntriesDiv = new StringBuilder();
		if(StringUtils.isNotEmpty(projectMemberId)){
			ProjectMembers projectMember = projectMembersService.getProjectMemberById(Long.parseLong(projectMemberId));
			if(projectMember != null){
				Map<String,String>  timesheetFrequencyMap = timesheetFrequencyGeneratorUtil.getTimesheetFrequencyListFromProjectInvoiceFrequency(projectMember,"view");
				if(timesheetFrequencyMap != null && !timesheetFrequencyMap.isEmpty()){
					
				/*timesheetEntriesDiv.append("					<div id='timesheetEntriesDivByProjectMemberId'>");*/
				timesheetEntriesDiv.append("					<div class='timeNewSe timeNewSe_a addicon'>");
				timesheetEntriesDiv.append("		                <select id='timesheetFrequency' name='timesheetFrequency' class='form-control chosen-select' onChange='timesheetDivByFrequency()' >");
				StringBuilder selectedStartDate = new StringBuilder("");
				StringBuilder selectedEndDate =  new StringBuilder("");
				int count = 1;
				 //DateFormat format = Constants.DF_DMY;
				//DateFormat  format = LoginSessionController.DATEFORMAT;
				for (Map.Entry<String, String> entry : timesheetFrequencyMap.entrySet()) {
					    String key = entry.getKey();
					    String value =entry.getValue();
					    Date currentStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
					    Date currentEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
					    HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMember.getId(), currentStartDate, currentEndDate);
					    if(pmhDateList.isEmpty() || pmhDateList == null)
					    continue;
					    StringBuilder isSelected = new StringBuilder("");
					    if(count == 1){
					    	selectedStartDate.append(key.replaceAll("/", "-"));
					    	selectedEndDate.append(value.replaceAll("/", "-"));
					    }
					    if(CalendarUtil.isCurrentDateBetweenDateRange(key, value,orgLocations.getDateFormat())){
					    	selectedStartDate.setLength(0);
					    	selectedEndDate.setLength(0);
					    	isSelected.append("selected='selected'");
					    	selectedStartDate.append(key.replaceAll("/", "-"));
					    	selectedEndDate.append(value.replaceAll("/", "-"));
					    }
					    timesheetEntriesDiv.append("		                  <option data-icon='fa fa-calendar' "+isSelected.toString()+" value='"+key.replaceAll("/", "-")+"To"+value.replaceAll("/", "-")+"'>");
					    timesheetEntriesDiv.append(key).append(" To ").append(value);
					    timesheetEntriesDiv.append("		                  </option>");
				count++;
				}
				timesheetEntriesDiv.append("		                </select>");
				timesheetEntriesDiv.append("		            </div> ");
				
				timesheetEntriesDiv.append("			<div id='timesheetDivByFrequency'> ");
					StringBuilder frequencyBasedDiv = timesheetEntriesDivByFrequnecy(loginUser,selectedStartDate.toString(),selectedEndDate.toString(),projectMember.getId(),from,superUserId,isSuperUser,orgLocations,projectId,masterCandidateId);
					timesheetEntriesDiv.append(frequencyBasedDiv);
				timesheetEntriesDiv.append("		            </div> ");
				
				/*timesheetEntriesDiv.append("</div>  ");*/
			 }
		   }
		}
		
		return timesheetEntriesDiv;
		
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
				 Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
			  	 submissionApproverHierarchyService.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(pm.getProjects().getId(),pm.getMasterCandidateId().getId(), startDate, endDate, Submission_Status.DRAFT.getValue());;
			}
		
	}
	
	public StringBuilder timesheetEntriesDivByFrequnecy(User loginUser,String weekStartDate,String weekEndDate,Long projectMemberId,String from,Long superUserId,boolean isSuperUser,OrgLocations orgLocations,Long projectId,Long masterCandidateId) throws Exception{
		
		Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		
		StringBuilder timesheetEntriesDiv = new StringBuilder();
		//DateFormat format = Constants.DF_DMY;
		//DateFormat  format = LoginSessionController.DATEFORMAT;
		if(!isNullOrEmpty(weekStartDate) && !isNullOrEmpty(weekEndDate)){
			
		
		Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekStartDate.replaceAll("/", "-"));
		Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),weekEndDate.replaceAll("/", "-"));
		 
		SubmissionApproverHierarchy sah = null;
		if(from != null && !from.isEmpty() && from.equalsIgnoreCase("user")){
			sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);  
		}
		else{
			sah =submissionApproverHierarchyService.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(loginUser.getId(),projectId,masterCandidateId,orgLocations.getId(),frequencyStartDate, frequencyEndDate);
		}
		Submission_Status submissionStatus = sah.getSubmissionStatus();
		String submissionStatusStr = submissionStatus.getValue();
		
		timesheetEntriesDiv.append("<form id='timesheet_form' name='timesheet_form' enctype='multipart/form-data' >");
		
		
		timesheetEntriesDiv.append("<input type='hidden' name='projectMemberId'  id='projectMemberId' value='"+projectMemberId+"' >");
		timesheetEntriesDiv.append("<input type='hidden' name='timesheetFrequency'  id='timesheetFrequency' value='"+weekStartDate+"To"+weekEndDate+"' >");
		timesheetEntriesDiv.append("<input type='hidden' name='frequencyDates'  id='frequencyDates' value='"+weekStartDate+"To"+weekEndDate+"' >");
		   String pullRightClass = "pull-right";
		   String styleWidth = "";
		   String innerStyleWidth = "";
		   if(from != null && !from.isEmpty() && from.equalsIgnoreCase("user")){
			   pullRightClass = "" ;
		   }
		   SubmissionApproverHierarchy isSah = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId,masterCandidateId, frequencyStartDate, frequencyEndDate, "APPROVED");
		   boolean isSahApproved = false;
		   if((isSah == null &&from.equalsIgnoreCase("user") && isModifyMyTimeSheet && !isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())))
			  || (!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()) && from.equalsIgnoreCase("approver") && StringUtils.isEmpty(sah.getReopenReason())) ){
			  if(isSah == null)
			  isSahApproved = true;
			 styleWidth = "style='width:244px;'";
			 innerStyleWidth = "style='width:40%;'";
		   }
		   
		   if(!isNullOrEmpty(submissionStatusStr)   && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()) && from.equalsIgnoreCase("approver") && StringUtils.isNotEmpty(sah.getReopenReason())){
			   styleWidth = "style='width:300px;'";
			   innerStyleWidth = "style='width:40%;'";
		   }
		   
		   boolean isSahReopened = false;
		   if(null != isSah && from.equalsIgnoreCase("approver") && null != isSah.getReopenReason() && !"".equalsIgnoreCase(isSah.getReopenReason()) /*&& null != isSah.getReopenRejectedReason() && !"".equalsIgnoreCase(isSah.getReopenRejectedReason())*/){
			   isSahReopened = true;
		   }
		
	 	  timesheetEntriesDiv.append("					<div id='timesheetSubmissionStatusDiv' "+styleWidth+" class='timeNewSe "+pullRightClass+"'>");
	 	 
		   StringBuilder submissionStatusDiv = getSubmissionStatusDiv(sah ,projectMemberId,frequencyStartDate,frequencyEndDate,weekStartDate,weekEndDate,from,isSahApproved,pullRightClass,styleWidth,innerStyleWidth,superUserId,isSahReopened,isSuperUser,projectId,masterCandidateId);
	      timesheetEntriesDiv.append(submissionStatusDiv); 
	      timesheetEntriesDiv.append("					</div>   ");
		  timesheetEntriesDiv.append("					<div class='clearfix'></div>   ");
		StringBuilder tabData = new StringBuilder();
		Map<String,String> tabFrequency = timesheetFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(weekStartDate, weekEndDate,orgLocations.getDateFormat());
		timesheetEntriesDiv.append("			<div class='timesheetNewTabs'> ");
		timesheetEntriesDiv.append("			<div class='project_side_wrap_inn_tabs'> ");
		timesheetEntriesDiv.append("			    <ul class='project-tabs-menu'> ");
		StringBuilder currentStartDate = new StringBuilder("");
		StringBuilder currentEndDate = new StringBuilder("");
		int count = 1;
		String timesheetFrequencyEndDate = "";
		if(tabFrequency != null && !tabFrequency.isEmpty()){
			for (Map.Entry<String, String> entry : tabFrequency.entrySet()) {
			     String key = entry.getKey();
			     String value = entry.getValue();
			     
				 Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),key.replaceAll("/", "-"));
				 Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),value.replaceAll("/", "-"));
			    DateFormat shortormat = Constants.SHORT_DF_STD_DM;
			    StringBuilder isActive = new StringBuilder("");
			    
			    TimesheetSubmissions ts =timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(startDate, endDate, projectId, masterCandidateId);
			    
			    if(CalendarUtil.isCurrentDateBetweenDateRange(weekStartDate, weekEndDate,orgLocations.getDateFormat())){  
			    	if(CalendarUtil.isCurrentDateBetweenDateRange(key, value,orgLocations.getDateFormat())){
			    		   isActive.append("class='current'");
			    		   if(ts != null && !isNullOrEmpty(submissionStatusStr) && !submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())){
			    			    isActive.delete(0, isActive.length());
					    		isActive.append("");
				    			isActive.append("class='current subColor'");
						    }
			    		   currentStartDate.append(key);
			    		   currentEndDate.append(value);
			    	}
			    	else{
			    		isActive.delete(0, isActive.length());
			    		isActive.append("");
			    		if(ts != null && !isNullOrEmpty(submissionStatusStr) && !submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())){
			    			isActive.append("class='subColor'");
					    }
			    	}
			    	if(startDate.equals(endDate)){
			    	   timesheetEntriesDiv.append("<input type='hidden' name='tabDates'  id='tabDates' value='"+key+"To"+value+"' >");
			    	   timesheetEntriesDiv.append("			        <li "+isActive.toString()+"><a href='#'  onClick='timesheetDivByTab(\""+key+"\",\""+value+"\",\""+submissionStatusStr+"\");'>"+shortormat.format(startDate)+"</a></li> ");
			    	}
			    	else{
			    	  timesheetEntriesDiv.append("<input type='hidden' name='tabDates'  id='tabDates' value='"+key+"To"+value+"' >");	
			    	  timesheetEntriesDiv.append("			        <li "+isActive.toString()+"><a href='#' onClick='timesheetDivByTab(\""+key+"\",\""+value+"\",\""+submissionStatusStr+"\")'>"+shortormat.format(startDate)+" - "+shortormat.format(endDate)+"</a></li> ");	
			    	}
			    }
			    else{
				    	if(count == 1){
				    		   isActive.append("class='current'");
				    		   if(ts != null && !isNullOrEmpty(submissionStatusStr) && !submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())){
				    			    isActive.delete(0, isActive.length());
						    		isActive.append("");
					    			isActive.append("class='current subColor'");
							    }
			            	   currentStartDate.append(key);
				    		   currentEndDate.append(value);
			            }
				    	else{
				    		isActive.delete(0, isActive.length());
				    		isActive.append("");
				    		if(ts != null && !isNullOrEmpty(submissionStatusStr) && !submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue())){
				    			isActive.append("class='subColor'");
						    }
				    	}
				    	if(startDate.equals(endDate)){
				    		timesheetEntriesDiv.append("<input type='hidden' name='tabDates'  id='tabDates' value='"+key+"To"+value+"' >");
				    		timesheetEntriesDiv.append("			        <li "+isActive.toString()+"><a href='#'  onClick='timesheetDivByTab(\""+key+"\",\""+value+"\",\""+submissionStatusStr+"\")'>"+shortormat.format(startDate)+"</a></li> ");
				    	}else { 
				    		 timesheetEntriesDiv.append("<input type='hidden' name='tabDates'  id='tabDates' value='"+key+"To"+value+"' >");
				    		 timesheetEntriesDiv.append("			        <li "+isActive.toString()+"><a href='#'  onClick='timesheetDivByTab(\""+key+"\",\""+value+"\",\""+submissionStatusStr+"\")'>"+shortormat.format(startDate)+" To "+shortormat.format(endDate)+"</a></li> ");
				    	}
			    }
			    count++;
			    timesheetFrequencyEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endDate);
			}
			tabData =	projectTabDataByDates(currentStartDate.toString(), currentEndDate.toString(),projectMemberId,timesheetFrequencyEndDate,submissionStatus.getValue(),from,orgLocations,masterCandidateId);
		}
		
		String exportPdf = "pdf";
		String exportExcel = "excel";
		 Boolean isExportMyTimeSheet= commonUtil.hasRole("ROLE_EXPORT_MY_TIMESHEETS");
		if(isExportMyTimeSheet){
		timesheetEntriesDiv.append("					<div class='export dropdown'> ");
		timesheetEntriesDiv.append("						<p class='dropdown-toggle' data-toggle='dropdown'><i class='glyphicon glyphicon-export'></i>Export<i class='fa fa-caret-down'></i></p> ");
		timesheetEntriesDiv.append("							<ul id='exporrtEntries' class='dropdown-menu dropdown-menu-right'> ");
		timesheetEntriesDiv.append("			                	<li onClick='timesheetEntriesExport(\""+weekStartDate+"\",\""+weekEndDate+"\","+projectMemberId+",\""+exportPdf+"\",\""+submissionStatus+"\");'><i class='fa fa-file-pdf-o text-danger'></i>&nbsp;PDF</li> ");
		timesheetEntriesDiv.append("			                    <li onClick='timesheetEntriesExport(\""+weekStartDate+"\",\""+weekEndDate+"\","+projectMemberId+",\""+exportExcel+"\",\""+submissionStatus+"\");'><i class='fa fa-file-excel-o text-primary'></i>&nbsp;Excel</li> ");
		timesheetEntriesDiv.append("			                    <li class='hidden'>Word<i class='fa fa-file-word-o'></i></li> ");
		timesheetEntriesDiv.append("							</ul>			 ");
		timesheetEntriesDiv.append("					</div> ");
		timesheetEntriesDiv.append("			        <div class='clearfix'></div> ");
		timesheetEntriesDiv.append("			    </ul> "); 
		}
		
	    timesheetEntriesDiv.append(tabData);
		
							    
		timesheetEntriesDiv.append("					</div>	");
		timesheetEntriesDiv.append("					</div>");   
		
		/*timesheetEntriesDiv.append("</div>");  */ 
		/* Fix for Defect 3311 IE 11 issue*/
		timesheetEntriesDiv.append("<input type='hidden' name='_ignore'/>");
		/* End Of Fix for Defect 3311 IE 11 issue*/
		
		timesheetEntriesDiv.append("		 </form> 	");	
		}
		return timesheetEntriesDiv;
		
		
		
	}
	
	
	public StringBuilder getSubmissionStatusDiv(SubmissionApproverHierarchy sah ,Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String weekStartDate,String weekEndDate,String from,boolean isSahApproved,String pullRightClass,String styleWidth,String innerStyleWidth,Long superUserId,boolean isSahReopened,boolean isSuperUser,Long projectId,Long masterCandidateId){
		
		   Submission_Status submissionStatus = sah.getSubmissionStatus();
		   String submissionStatusStr = submissionStatus.getValue();
		   StringBuilder timesheetEntriesDiv = new StringBuilder();
		  // DateFormat format = Constants.DF_DMY;
		   //DateFormat  format = LoginSessionController.DATEFORMAT;
		   Boolean isModifyMyTimeSheet = commonUtil.hasRole("ROLE_MODIFY_MY_TIMESHEETS");
		   String acSubmissionStatusStr = null;
		   if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
			   SubmissionApproverHierarchy acsah =submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);
			   Submission_Status acSubmissionStatus = acsah.getSubmissionStatus();
			   acSubmissionStatusStr = acSubmissionStatus.getValue();
		   }
		  
		   
		    timesheetEntriesDiv.append("<input type='hidden' name='timesheetSubmissionStatus'  id='timesheetSubmissionStatus' value='"+submissionStatusStr+"' >");
		    timesheetEntriesDiv.append("<input type='hidden' name='isSuperUser'  id='isSuperUser' value='"+isSuperUser+"' >");
		    
			if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()))){
		    	timesheetEntriesDiv.append("<b class='proj_new "+pullRightClass+"'>").append(submissionStatus).append("</b>");
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()))){
	        	//to show re-open reason
        		if(from.equalsIgnoreCase("approver") && null != sah.getReopenReason()){
        			timesheetEntriesDiv.append(" <i class='glyphicon glyphicon-comment rejectedHover'>");
        			timesheetEntriesDiv.append(" <div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>").append(sah.getReopenReason()).append("</div>");
	        		timesheetEntriesDiv.append(" </i>");
        		}
	        	timesheetEntriesDiv.append("<b class='proj_draft "+pullRightClass+"'>").append(submissionStatus).append("</b>");
	        }
	        
	        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()))){
	        	timesheetEntriesDiv.append("<b "+innerStyleWidth+"  class='proj_process "+pullRightClass+" '>").append(submissionStatus).append("</b>");
	        	if(isSahApproved && isModifyMyTimeSheet && from.equalsIgnoreCase("user") && isModifyMyTimeSheet){
	                timesheetEntriesDiv.append("&nbsp;&nbsp;<b "+innerStyleWidth+" class='proj_withdraw'  onClick='withdrawMyTimesheet(\""+projectMemberId+"\",\""+weekStartDate+"To"+weekEndDate+"\");' >Modify</b>");
	        	}
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue())){
	        	timesheetEntriesDiv.append("<div id='reopenTimeSheetId'>");
	        	if(from.equalsIgnoreCase("approver") && StringUtils.isEmpty(sah.getReopenReason())){
	        		//to show re-open rejected reason
	        		if(StringUtils.isNotEmpty(sah.getReopenRejectedReason())){
	        			timesheetEntriesDiv.append(" <i class='glyphicon glyphicon-comment rejectedHover'>");
	        			timesheetEntriesDiv.append(" <div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>").append(sah.getReopenRejectedReason()).append("</div>");
		        		timesheetEntriesDiv.append(" </i>");
	        		}
	        		if(!isNullOrEmpty(acSubmissionStatusStr) && acSubmissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))
	        		timesheetEntriesDiv.append("<b  "+innerStyleWidth+" class='proj_withdraw "+pullRightClass+" ml'  onClick='reOpenMyTimesheet(\""+projectMemberId+"\",\""+weekStartDate+"To"+weekEndDate+"\");' >Re-Open</b>");
	        	}
	        	else if(from.equalsIgnoreCase("approver") && !isSuperUser && StringUtils.isNotEmpty(sah.getReopenReason()) && isSahReopened){
	        		//to show re-open reason
	        		if(StringUtils.isNotEmpty(sah.getReopenReason())){
	        			timesheetEntriesDiv.append(" <i class='pull-right glyphicon glyphicon-comment rejectedHover'>");
	        			timesheetEntriesDiv.append(" <div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>").append(sah.getReopenReason()).append("</div>");
		        		timesheetEntriesDiv.append(" </i>");
	        		}
	        		timesheetEntriesDiv.append("<p class='pull-right ml' style='width: 87px;'>Re-Open Request sent</p>");
		        }
	        	else if(from.equalsIgnoreCase("approver") && isSahReopened && isSuperUser && null != sah.getReopenedUser()){
	        		//to show re-open reason
	        		if(StringUtils.isNotEmpty(sah.getReopenReason())){
	        			timesheetEntriesDiv.append(" <i class='pull-right  glyphicon glyphicon-comment rejectedHover'>");
	        			timesheetEntriesDiv.append(" <div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>").append(sah.getReopenReason()).append("</div>");
		        		timesheetEntriesDiv.append(" </i>");
	        		}
	        		 timesheetEntriesDiv.append("<div class='pull-right  sm text-center reopen'>").append(" <label class='clearfix reopen-label'>RE-OPEN</label><i class='reopen-text-success fa fa-check' title='Accept' alt='Accept'  onclick='acceptReopenTimeSheetBySuperUser(\"")
	                .append(projectMemberId).append("\",\"").append(sah.getReopenedUser().getId()).append("\",\"").append(weekStartDate+"To"+weekEndDate)
	                .append("\");'></i>|<i class='reopen-text-danger fa fa-times' title='Reject' alt='Reject' onclick='rejectReopenTimeSheetBySuperUser(\"")
	                .append(projectMemberId).append("\",\"").append(sah.getReopenedUser().getId()).append("\",\"").append(weekStartDate+"To"+weekEndDate)
	                .append("\");'></i></div>");
	        	}
	        	timesheetEntriesDiv.append("&nbsp;&nbsp;<b  "+innerStyleWidth+" class='proj_active "+pullRightClass+"'>").append(submissionStatus).append("</b>");
	        	timesheetEntriesDiv.append("</div>");
	        }
	        if(!isNullOrEmpty(submissionStatusStr) && (submissionStatusStr.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue()))){
	        	timesheetEntriesDiv.append("<b class='proj_closed "+pullRightClass+"'>").append(submissionStatus).append("</b>");
	        	//timesheetEntriesDiv.append(" <i class='glyphicon glyphicon-comment rejectedHover' id='"+projectMemberId+"' onclick='rejectedMessage(\"rejectedMessage"+projectMemberId+"\");'>");
	        	timesheetEntriesDiv.append(" <i class='glyphicon glyphicon-comment rejectedHover'>");
	        	timesheetEntriesDiv.append(" <div class='app_error_mes' id='rejectedMessage1"+projectMemberId+"'>").append(sah.getRejectedReason()).append("</div>");
	        	timesheetEntriesDiv.append(" </i>");
	        }
	        return timesheetEntriesDiv;
	}
	
	public StringBuilder projectTabDataByDates(String startDate ,String endDate,Long projectMemberId,String timesheetFrequencyEndDate,String approvalStatus,String from,OrgLocations orgLocations,Long masterCandidateId) throws Exception{
		
		ProjectMembers projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");	
		if(isFillMyTimeSheet || isFillMemberTimesheet){
			if(!isFillMyTimeSheet && isFillMemberTimesheet){
				isFillMyTimeSheet = isFillMemberTimesheet;
			}
		}
		StringBuilder tabDiv = new StringBuilder();
		
		
		tabDiv.append("<div id='timesheetDivByTab'>");
		
		tabDiv.append("<div class='project_tabs'>");
		tabDiv.append("<div id='tab-1' class='project_tabs_inn'>");
		tabDiv.append("<div class='timesheet_tableNew'>");
		
		
		tabDiv.append("	<input type='hidden' name='tabStartDate' id='tabStartDate' value=\""+startDate+"\" /><input type='hidden' name='tabEndDate' id='tabEndDate' value=\""+endDate+"\" />");
		tabDiv.append("			        	<table id='timesheet' cellpadding='0' cellspacing='0'>");
		tabDiv.append("			            	<tbody>");
		tabDiv.append("			                	<tr>");
		tabDiv.append("			                        <th>Tasks </th>");
		tabDiv.append("			                        <th>Type </th>");
		
		StringBuilder disbleCondition1 = new StringBuilder("");
		StringBuilder disbleCondition2 = new StringBuilder("");
		StringBuilder disbleCondition3 = new StringBuilder("");
		StringBuilder disbleCondition4 = new StringBuilder("");
		StringBuilder disbleCondition5 = new StringBuilder("");
		StringBuilder disbleCondition6 = new StringBuilder("");
		StringBuilder disbleCondition7 = new StringBuilder("");
		
		
		//DateFormat format = Constants.DF_DMY;
		//DateFormat  format = LoginSessionController.DATEFORMAT;
		Date actualStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate.replaceAll("/", "-"));
	    Date actualEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate.replaceAll("/", "-"));
		int dayOfTheDateWithStartDate = CalendarUtil.getDayOfTheDate(	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate));
		String weekStartDate = startDate;
		switch (dayOfTheDateWithStartDate) {
	        case 1:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,6,orgLocations.getDateFormat());
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
	        case 2:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,0,orgLocations.getDateFormat());
	                 break;
	        case 3:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,1,orgLocations.getDateFormat());
	                  disbleCondition1.setLength(0);
	                 disbleCondition1.append("disabled");
	             break;
	        case 4:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,2,orgLocations.getDateFormat());
	                 disbleCondition1.setLength(0);
                     disbleCondition2.setLength(0);
			         disbleCondition1.append("disabled");
		             disbleCondition2.append("disabled");
	            break;
	        case 5: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,3,orgLocations.getDateFormat());
			        disbleCondition1.setLength(0);
		            disbleCondition2.setLength(0);
		            disbleCondition3.setLength(0);
			        disbleCondition1.append("disabled");
		            disbleCondition2.append("disabled");
		            disbleCondition3.append("disabled");
	            break;
	        case 6:  weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,4,orgLocations.getDateFormat());
			         disbleCondition1.setLength(0);
		             disbleCondition2.setLength(0);
		             disbleCondition3.setLength(0);
		             disbleCondition4.setLength(0);
			         disbleCondition1.append("disabled");
		             disbleCondition2.append("disabled");
		             disbleCondition3.append("disabled");
		             disbleCondition4.append("disabled");
	              break;
	        case 7: weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,5,orgLocations.getDateFormat());
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
	        default:   weekStartDate = CalendarUtil.getPreviousDateByDecrementor(startDate,0,orgLocations.getDateFormat());
	                 break;
       }
		int dayOfTheDateWithEndDate = CalendarUtil.getDayOfTheDate(	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate));
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
		tabDiv.append("			                        <th>Mon<i>").append(weekStartDate).append("</i></th>");
		String tuesDayDate =  CalendarUtil.getNextDate(weekStartDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Tue<i>").append(tuesDayDate).append("</i></th>");
		
		String WednesDayDate =  CalendarUtil.getNextDate(tuesDayDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Wed<i>").append(WednesDayDate).append("</i></th>");
		
		String ThursDayDate =  CalendarUtil.getNextDate(WednesDayDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Thu<i>").append(ThursDayDate).append("</i></th>");
		
		String FriDayDate =  CalendarUtil.getNextDate(ThursDayDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Fri<i>").append(FriDayDate).append("</i></th>");
		
		String SaturdayDate =  CalendarUtil.getNextDate(FriDayDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Sat<i>").append(SaturdayDate).append("</i></th>");
		
		String SundayDate =  CalendarUtil.getNextDate(SaturdayDate,orgLocations.getDateFormat());
		tabDiv.append("			                        <th>Sun<i>").append(SundayDate).append("</i></th>");
		
		tabDiv.append("			                    </tr>");
		
		
		/**
		 * Here getting  dates which are enabled in the current submission
		 * (Hint:In particular project,the member may assigned in the mid of the week) 
	    */
		HashSet<Date> pmhDateList =  projectMembersHistoryService.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId,actualWeekStartDate, actualEndDate);
		
  	    Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
	    currentCal.setLenient(false);
	    currentCal.setTime(actualWeekStartDate);
		 
		
		if( (!isFillMyTimeSheet) || (!pmhDateList.contains(actualWeekStartDate)) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
			disbleCondition1.setLength(0);
			disbleCondition1.append("disabled");
		}
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"TUE",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition2.setLength(0);
        	disbleCondition2.append("disabled");
		}
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"WED",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition3.setLength(0);
        	disbleCondition3.append("disabled");
		}
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"THU",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition4.setLength(0);
        	disbleCondition4.append("disabled");
		} 
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"FRI",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition5.setLength(0);
        	disbleCondition5.append("disabled");
		}
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SAT",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition6.setLength(0);
        	disbleCondition6.append("disabled");
		}
        if((!isFillMyTimeSheet) || (!pmhDateList.contains(CalendarUtil.getCurrentWeekDayByCalendar(currentCal,"SUN",orgLocations.getDateFormat()).getTime())) || !isNullOrEmpty(approvalStatus) && ((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))|| approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("approved").getValue()))){
        	disbleCondition7.setLength(0);
        	disbleCondition7.append("disabled");
		} 
		
		
        //String approvalStatus = "NEW";
		TimesheetSubmissions ts = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate, actualEndDate, projectMember.getProjects().getId(),masterCandidateId);
		Collection<TimesheetEntries>  timesheetEntriesList = null;
		if(ts != null){
			timesheetEntriesList =   timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(ts.getId());
			tabDiv.append("<input type='hidden' name='submissionId'  class='initiateTimesheetMax' id='submissionId'  value='"+ts.getId()+"' />");
		}	
			if(timesheetEntriesList != null && !timesheetEntriesList.isEmpty()){
						Integer maxCount = timesheetEntriesList.size();
						Integer rowCount = 1;
						tabDiv.append("<input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='"+maxCount+"' />");
						for(TimesheetEntries timesheetEntries   : timesheetEntriesList){
							           
									  tabDiv.append("<input type='hidden' name='"+rowCount+"timesheetEntriesId'   id='"+rowCount+"timesheetEntriesId'   value='"+timesheetEntries.getId()+"' />");
									  tabDiv.append("       <tr id='timesheetRow"+rowCount+"'>");
									  tabDiv.append("                 <td>");
									  tabDiv.append("                      <div> ");
									  
									  Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(ts.getProjectMembers().getProjects().getId());
									  if(isFillMyTimeSheet 
										    		&&!isNullOrEmpty(approvalStatus)
										    		&&!(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())&& from.equalsIgnoreCase("user")) 
										    		&& !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())){
										 
												 //Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasks();
												  tabDiv.append("                       <select id='"+rowCount+"task1Id' name='"+rowCount+"task1Id' data-validation='required' field-name='Task' class='existedProjectTaskId"+ts.getProjectMembers().getProjects().getId()+"' onChange='getProjectTasks(\""+rowCount+"task1Id\",\""+ts.getProjectMembers().getProjects().getId()+"\")' >");
												  tabDiv.append("<option value=''>--select task--</option>");
									              for(ProjectTasks pt   : ProjectTasksList){
											    	       if(timesheetEntries.getProjectTask() != null && pt.getId().equals(timesheetEntries.getProjectTask().getId())){
											    	    	   tabDiv.append("<option value='"+pt.getId()+"' selected>").append(pt.getTaskName()).append("</option>");
											    	       }
											    	       else{
											    	    		   tabDiv.append("<option value='"+pt.getId()+"'>").append(pt.getTaskName()).append("</option>");
											    	       }
													                
												  }
									              tabDiv.append(" </select>"); 
										 
										 }
										 else{
											 if(timesheetEntries.getProjectTask() != null){
												 for(ProjectTasks pt   : ProjectTasksList){
										    	       if(pt.getId().equals(timesheetEntries.getProjectTask().getId())){
										    	    	   tabDiv.append("<p>").append(pt.getTaskName()).append("</p>");
										    	       }
												 }
											 }
											 else {
												 tabDiv.append("<p>").append("--").append("</p>");
											 }
											
										 }
									 
						              
						 			     if(isFillMyTimeSheet 
									    		&&!isNullOrEmpty(approvalStatus)
									    		&&!(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())&& from.equalsIgnoreCase("user")) 
									    		&& !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())){
									              if(rowCount == 1){
									            	  tabDiv.append("<input type='button' value='' onClick='addTimeSheetRow("+ts.getProjectMembers().getProjects().getId()+")' class='addtaskfield'/>");
									              }else{
									            	  tabDiv.append("<input type='button' value=''  onClick='removeTimeSheetRow(\""+rowCount+"\",\""+ts.getId()+"\");'  class='addtaskfield removetaskfield'/>");
									              }
								              }   
									  tabDiv.append("                      </div>");
						              tabDiv.append("                 </td> ");
						              
						              //for task billable type
						              if(timesheetEntries.getProjectTask() != null){
						            	  ProjectTasks tasks = projectTasksService.getTasksId(timesheetEntries.getProjectTask().getId().toString());
						            	  tabDiv.append("				<td> <div> ");   
							              tabDiv.append("<span id='"+rowCount+"task1IdBillableTypeId'>"+tasks.getBillableType()+"</span>");
							              tabDiv.append("				</td> </div> "); 
						              }
						              else{
						            	  tabDiv.append("				<td> <div> ");   
							              tabDiv.append("<span id='"+rowCount+"task1IdBillableTypeId'>Billable</span>");
							              tabDiv.append("				</td> </div> "); 
						              }
						              
						              
						              
									  tabDiv.append("				<td> <div> ");   
						              tabDiv.append(" <input type='text' "+disbleCondition1+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay1Id' name='"+rowCount+"timeSheetDay1Id' class='inline_field' value='"+timesheetEntries.getMonHours()+"'  placeholder='HH:MM' />	"); 
						              tabDiv.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getMoncomments())){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment1Id\");'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"comment1Id' >").append(timesheetEntries.getMoncomments()).append("</textarea></span></div>");
					            	  }else if(StringUtils.isEmpty(disbleCondition1.toString()) || disbleCondition1.toString().equalsIgnoreCase("disabled")){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment1Id\");'></i>");
					            		//tabDiv.append(" <i class='blankDiv'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea "+disbleCondition1+" name='"+rowCount+"comment1Id'  id='"+rowCount+"textArea1Id' ></textarea></span></div>");
					            	  }
					            	  tabDiv.append(" </div> </td>"); 
						              
					            	  tabDiv.append(" <td> <div>"); 
					            	  tabDiv.append(" <input type='text' "+disbleCondition2+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay2Id' name='"+rowCount+"timeSheetDay2Id' class='inline_field' value='"+timesheetEntries.getTueHours()+"' placeholder='HH:MM' />	"); 
					            	  tabDiv.append(" <div class='timesheet_comm'>");
						              if(!isNullOrEmpty(timesheetEntries.getTuecomments())){
						            	  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment2Id\");'></i>");
						            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"comment2Id'>").append(timesheetEntries.getTuecomments()).append("</textarea></span></div>");
						              }else if(StringUtils.isEmpty(disbleCondition2.toString()) || disbleCondition2.toString().equalsIgnoreCase("disabled")){
						            	  tabDiv.append(" <i class='glyphicon glyphicon-comment opactiy_0' onclick='timesheetComment(\""+rowCount+"comment2Id\");'></i>");
						            	//tabDiv.append(" <i class='blankDiv'></i>");
						            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea "+disbleCondition2+" name='"+rowCount+"comment2Id' id='"+rowCount+"textArea2Id'></textarea></span></div>");
						              }
						              tabDiv.append(" </div> </td>"); 
						              
						              tabDiv.append(" <td> <div>"); 
						              tabDiv.append(" <input type='text' "+disbleCondition3+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay3Id' name='"+rowCount+"timeSheetDay3Id' class='inline_field' value='"+timesheetEntries.getWedHours()+"' placeholder='HH:MM' />	"); 
						              tabDiv.append(" <div class='timesheet_comm'>"); 
						              if(!isNullOrEmpty(timesheetEntries.getWedcomments())){
						            	  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment3Id\");'></i>");  
						            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"comment3Id'>").append(timesheetEntries.getWedcomments()).append("</textarea></span></div>");
						              }else if(StringUtils.isEmpty(disbleCondition3.toString()) || disbleCondition3.toString().equalsIgnoreCase("disabled")){
						            	  tabDiv.append(" <i class='glyphicon glyphicon-comment opactiy_0' onclick='timesheetComment(\""+rowCount+"comment3Id\");'></i>");
						            	  // tabDiv.append(" <i class='blankDiv'></i>");
						            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea "+disbleCondition3+" name='"+rowCount+"comment3Id' id='"+rowCount+"textArea3Id'></textarea></span></div>");
						              }
						              tabDiv.append(" </div> "); 
						              tabDiv.append(" </td>"); 
					
						              tabDiv.append(" <td> <div>"); 
						    		  tabDiv.append(" <input type='text' "+disbleCondition4+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay4Id' name='"+rowCount+"timeSheetDay4Id' class='inline_field' value='"+timesheetEntries.getThuHours()+"' placeholder='HH:MM' />"); 	
						    		  tabDiv.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getThucomments())){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment4Id\");'></i>"); 
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"comment4Id'>").append(timesheetEntries.getThucomments()).append("</textarea></span></div>");
					            	  }
						              else if(StringUtils.isEmpty(disbleCondition4.toString())|| disbleCondition4.toString().equalsIgnoreCase("disabled")){
						            	  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment4Id\");'></i>");
						            	  //tabDiv.append(" <i class='blankDiv'></i>");
						            	  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id' ><textarea "+disbleCondition4+" name='"+rowCount+"comment4Id' id='"+rowCount+"textArea4Id'></textarea></span></div>");
						              }
					            	  tabDiv.append(" </div> </td>"); 
						              
					            	  tabDiv.append(" <td> <div>"); 
					            	  tabDiv.append(" <input type='text'  "+disbleCondition5+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay5Id' name='"+rowCount+"timeSheetDay5Id' class='inline_field' value='"+timesheetEntries.getFriHours()+"' placeholder='HH:MM' />	"); 
					            	  tabDiv.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getFricomments())){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"comment5Id'>").append(timesheetEntries.getFricomments()).append("</textarea></span></div>");
					            	  }else if(StringUtils.isEmpty(disbleCondition5.toString())|| disbleCondition5.toString().equalsIgnoreCase("disabled")){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>");
					            		  //tabDiv.append(" <i class='blankDiv'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea "+disbleCondition5+" name='"+rowCount+"comment5Id' id='"+rowCount+"textArea5Id'></textarea></span></div>");
					            	  }
					            	  tabDiv.append(" </div> </td>"); 
						              
					            	  tabDiv.append("<td> <div>"); 
					            	  tabDiv.append(" <input type='text' "+disbleCondition6+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay6Id' name='"+rowCount+"timeSheetDay6Id' class='inline_field' value='"+timesheetEntries.getSatHours()+"'  placeholder='HH:MM' />	"); 
					            	  tabDiv.append(" <div class='timesheet_comm'>"); 
					            	  if(!isNullOrEmpty(timesheetEntries.getSatcomments())){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment6Id\");'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"comment6Id'>").append(timesheetEntries.getSatcomments()).append("</textarea></span></div>");
					            	  }else if(StringUtils.isEmpty(disbleCondition6.toString())|| disbleCondition6.toString().equalsIgnoreCase("disabled")){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment6Id\");'></i>");
					            		  //tabDiv.append(" <i class='blankDiv'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id' ><textarea "+disbleCondition6+" name='"+rowCount+"comment6Id' id='"+rowCount+"textArea6Id'></textarea></span></div>");
					            	  }
					            	  tabDiv.append(" </div> </td>"); 
						              
					            	  tabDiv.append("						<td> <div>"); 
					            	  tabDiv.append(" <input type='text' "+disbleCondition7+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay7Id' name='"+rowCount+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='"+timesheetEntries.getSunHours()+"' />	"); 
					            	  tabDiv.append(" <div class='timesheet_comm'>");
					            	  if(!isNullOrEmpty(timesheetEntries.getSuncomments())){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment com_active' onclick='timesheetComment(\""+rowCount+"comment7Id\");'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"comment7Id' >").append(timesheetEntries.getSuncomments()).append("</textarea></span></div>");
					            	  }else if(StringUtils.isEmpty(disbleCondition7.toString())|| disbleCondition7.toString().equalsIgnoreCase("disabled")){
					            		  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment7Id\");'></i>");
					            		  //tabDiv.append(" <i class='blankDiv'></i>");
					            		  tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment7Id' ><textarea "+disbleCondition7+" name='"+rowCount+"comment7Id' id='"+rowCount+"textArea7Id'></textarea></span></div>");
					            	  }
					            	  tabDiv.append(" </div> </td>"); 
									                                         
					            	  tabDiv.append(" </tr> ");      
						              rowCount++;      
				}
			}			
		else{
			Integer maxCount = 0;
			Integer rowCount = 1;
			maxCount = maxCount+1;
			tabDiv.append("<input type='hidden' name='maxRowNumber'  class='initiateTimesheetMax' id='maxRowNumber'  value='"+maxCount+"' />");
			
			tabDiv.append("			                    <tr id='timesheetRow0'>");
			tabDiv.append("			                                         <td>");
			tabDiv.append("			                                              <div> ");
			
            if(!isNullOrEmpty(approvalStatus) && (approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("submitted for approval").getValue())|| approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()))){
        	   tabDiv.append("<p>NO DATA</p>");
            }
			 else{
            	  if((isFillMyTimeSheet )){
            		  tabDiv.append("     <select  id='"+rowCount+"task1Id' name='"+rowCount+"task1Id' data-validation='required' field-name='Task' class='existedProjectTaskId"+projectMember.getProjects().getId()+"' onChange='getProjectTasks(\""+rowCount+"task1Id\",\""+projectMember.getProjects().getId()+"\")' >");
	                      tabDiv.append("<option value=''>--select task--</option>");
	                      Collection<ProjectTasks>  ProjectTasksList = projectTasksService.getAllTasksByProjectId(projectMember.getProjects().getId());
	                      for(ProjectTasks pt   : ProjectTasksList){
	                    	  tabDiv.append("<option value='"+pt.getId()+","+pt.getBillableType()+"'>").append(pt.getTaskName()).append("</option>");   
					      }
	                      tabDiv.append(" </select>"); 
			              tabDiv.append("<input type='button' value=''  onClick='addTimeSheetRow(\""+projectMember.getProjects().getId()+"\");'  class='addtaskfield'/>");
	            	  }else{
	            		  tabDiv.append("<p>NO DATA</p>");
	            	  }
				   }
	             tabDiv.append("                      </div>");
	             tabDiv.append("                 </td> ");
	             
	             tabDiv.append("				<td> <div> ");   
	             tabDiv.append("<span id='"+rowCount+"task1IdBillableTypeId'></span>");
	             tabDiv.append("				</td> </div> ");   
	             
	             tabDiv.append("				<td> <div> ");   
	             tabDiv.append(" <input type='text'  "+disbleCondition1+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay1Id' name='"+rowCount+"timeSheetDay1Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
		            tabDiv.append(" <div class='timesheet_comm'>");
		          if(isNullOrEmpty(disbleCondition1.toString()))
		        	  tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment1Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment1Id'><textarea name='"+rowCount+"comment1Id' id='"+rowCount+"textArea1Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
		          
		          tabDiv.append(" <td> <div>"); 
		          tabDiv.append(" <input type='text' "+disbleCondition2+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay2Id' name='"+rowCount+"timeSheetDay2Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition2.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment2Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment2Id'><textarea name='"+rowCount+"comment2Id' id='"+rowCount+"textArea2Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
		          
				  tabDiv.append(" <td> <div>");  
		          tabDiv.append(" <input type='text' "+disbleCondition3+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay3Id' name='"+rowCount+"timeSheetDay3Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition3.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment3Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment3Id'><textarea name='"+rowCount+"comment3Id' id='"+rowCount+"textArea3Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> "); 
		          tabDiv.append(" </td>"); 
		
				  tabDiv.append(" <td> <div>"); 
		          tabDiv.append(" <input type='text' "+disbleCondition4+" onblur='editMe(this);'   id='"+rowCount+"timeSheetDay4Id' name='"+rowCount+"timeSheetDay4Id' class='inline_field' value='' placeholder='HH:MM' />"); 	
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition4.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment4Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment4Id' ><textarea name='"+rowCount+"comment4Id' id='"+rowCount+"textArea4Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
		          
		          tabDiv.append(" <td> <div>"); 
		          tabDiv.append(" <input type='text' "+disbleCondition5+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay5Id' name='"+rowCount+"timeSheetDay5Id' class='inline_field' value='' placeholder='HH:MM' />	"); 
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition5.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment5Id\");' ></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment5Id'><textarea name='"+rowCount+"comment5Id' id='"+rowCount+"textArea5Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
		          
				  tabDiv.append("<td> <div>"); 
		          tabDiv.append(" <input type='text' "+disbleCondition6+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay6Id' name='"+rowCount+"timeSheetDay6Id' class='inline_field' value=''  placeholder='HH:MM' />	"); 
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition6.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment6Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment6Id' ><textarea name='"+rowCount+"comment6Id' id='"+rowCount+"textArea6Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
		          
				  tabDiv.append("						<td> <div>"); 
		          tabDiv.append(" <input type='text' "+disbleCondition7+"  onblur='editMe(this);'  id='"+rowCount+"timeSheetDay7Id' name='"+rowCount+"timeSheetDay7Id' class='inline_field' placeholder='HH:MM' value='' />	"); 
		          tabDiv.append(" <div class='timesheet_comm'>"); 
		          if(isNullOrEmpty(disbleCondition7.toString()))
		          tabDiv.append(" <i class='glyphicon glyphicon-comment' onclick='timesheetComment(\""+rowCount+"comment7Id\");'></i>"); 
		          tabDiv.append(" <span class='teskcomment' id='"+rowCount+"comment7Id' ><textarea name='"+rowCount+"comment7Id' id='"+rowCount+"textArea7Id'></textarea></span></div>"); 
		          tabDiv.append(" </div> </td>"); 
			 	  tabDiv.append("			                  </tr>");  
					
			    }
			
				tabDiv.append("	  <tr class='last_child'>"); 
		        tabDiv.append("    <td class='total_field'>Total</td>"); 
		        tabDiv.append("    <td></td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal1Id' name='rowTotal1Id' class='inline_field' value='HH:MM' placeholder='00:00' />"); 	
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal2Id' name='rowTotal2Id' class='inline_field' value='HH:MM' placeholder='00:00' />	"); 
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal3Id' name='rowTotal3Id' class='inline_field' value='HH:MM' placeholder='00:00' />	"); 
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text'disabled  id='rowTotal4Id' name='rowTotal4Id' class='inline_field' value='HH:MM' placeholder='00:00' />	"); 
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal5Id' name='rowTotal5Id' class='inline_field' value='HH:MM' placeholder='00:00' />"); 	
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal6Id' name='rowTotal6Id' class='inline_field' value='HH:MM' placeholder='00:00' />"); 	
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("    <td> <div class='alignDiv'>"); 
		        tabDiv.append("    <input type='text' disabled id='rowTotal7Id' name='rowTotal7Id' class='inline_field' value='HH:MM' placeholder='00:00' />	"); 
		        tabDiv.append("    </div> </td>"); 
		        tabDiv.append("      </tr> ");  
		        
				tabDiv.append("			                </tbody>");
				tabDiv.append("			            </table>");
				
				
		
			//attachemtns and comments.

				tabDiv.append("		    <div class='timesheet_bot'>");	
				tabDiv.append("		    	<div class='timesheet_week_comm'>");
				/*if(projectMember.getProjects().getIsTimesheetAttachmentRequired() != null && projectMember.getProjects().getIsTimesheetAttachmentRequired() != 0){*/
				if(StringUtils.isNotEmpty(timesheetFrequencyEndDate)){
					Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetFrequencyEndDate.replaceAll("/", "-"));
					if(frequencyEndDate.equals(actualEndDate)){
						StringBuilder attachmentDiv = attachmentsAndComments(approvalStatus,startDate, endDate,ts,projectMember.getProjects().getIsTimesheetAttachmentRequired(),from);
						  tabDiv.append(attachmentDiv);
						  
						  String readonly = "readonly='readonly'";
						  if(!isNullOrEmpty(approvalStatus) && (!((approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user"))) ||approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()) || approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()) || approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("rejected").getValue())) ){
							  readonly = "";
						  }
							  
					    if(ts != null && ts.getWeekcomments() != null && !ts.getWeekcomments().isEmpty()){
					    	tabDiv.append("<div class='commentTime pull-right'>");
					    	tabDiv.append("<textarea  name='weekComment'  id='weekComment' "+readonly+" placeholder='comment here'>").append(ts.getWeekcomments()).append("</textarea>");
					    	tabDiv.append("</div>");
						}else{
							tabDiv.append("<div class='commentTime pull-right'>");
							tabDiv.append("<textarea   name='weekComment' id='weekComment'   placeholder='comment here'></textarea>");
							tabDiv.append("</div>");
						}
					}
				}
				      
				/*}*/
			    /*tabDiv.append("		            <div class='commentTime'><textarea placeholder='comment here'></textarea></div>");*/	
			    tabDiv.append("</div>");
			tabDiv.append("		    </div>  ");	
			tabDiv.append("</div>");      
			
			
			
			
			tabDiv.append("		    <div class='timesheet_bot'>");	
	    tabDiv.append("		    	<div class='timesheet_button'>");	
	    
	    
	    tabDiv.append("<div class='totalWrap pull-left'>");
	    tabDiv.append("<ul>");
		tabDiv.append("<li>");
		tabDiv.append("<span>Billable Hours</span>");
		tabDiv.append("<input type='text' name='grandBillableTotal' id='grandBillableTotal' value='00:00' placeholder='HH/HH' readonly>");
		tabDiv.append("</li>");
		tabDiv.append("<li>");
		tabDiv.append("<span>Non-Billable Hours</span>");
		tabDiv.append("<input type='text' name='grandNonBillableTotal' id='grandNonBillableTotal' value='00:00' placeholder='HH/HH' readonly>");
		tabDiv.append("</li>");
		tabDiv.append(" <li>");
		tabDiv.append("<span>Total Hours</span>");
		tabDiv.append("<input type='text' id='grandTotal' value='00:00' placeholder='HH/HH' readonly>");
		tabDiv.append("</li>");
		tabDiv.append("<div class='clearfix'></div> ");                                      
		tabDiv.append(" </ul>");
		tabDiv.append("</div>");

	      
	    if(!isNullOrEmpty(approvalStatus)  && !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user")) && !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue()) ){
			
	    	if(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("new").getValue()) || approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("draft").getValue()) || !(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user")))
	    	tabDiv.append("		        	<input type='button' onClick='newSaveTimesheet(\"Draft\",\""+from+"\");'  value='Save' class='time_btn time_dra' />");	
			
			if(StringUtils.isNotEmpty(timesheetFrequencyEndDate)){
				Date frequencyEndDate = 	commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),timesheetFrequencyEndDate.replaceAll("/", "-"));
				if(frequencyEndDate.equals(actualEndDate))
				tabDiv.append("		            <input type='button' onClick='newSaveTimesheet(\"Submitted\",\""+from+"\");'  value='Submit' class='time_btn' />");	
			}
			
			if(!isNullOrEmpty(approvalStatus)  && approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("New").getValue()))
			tabDiv.append("		            <input type='button' value='Clear' onClick='clearTimesheet();' class='time_btn time_cancel' />");	
	    }
	    else{
	    	
	    	tabDiv.append("		       <div class='clearfix'></div>   ");
	    }
		
		tabDiv.append("		        </div>   ");	
		tabDiv.append("<div class='clearfix'></div> ");     
		tabDiv.append("		    </div>  ");	
			
			
			
			
			tabDiv.append("</div>");
			tabDiv.append("</div>");
		
		    
		    return tabDiv;
	}
	
	public StringBuilder attachmentsAndComments(String approvalStatus,String startDate ,String endDate,TimesheetSubmissions ts, Integer isTimesheetAttachmentRequired,String from) throws Exception{
			
		    Boolean isFillMyTimeSheet = commonUtil.hasRole("ROLE_FILL_MY_TIMESHEETS");
		    Boolean isFillMemberTimesheet = commonUtil.hasRole("ROLE_FILL_MEMBER_TIMESHEETS");	
			if(isFillMyTimeSheet || isFillMemberTimesheet){
				if(!isFillMyTimeSheet && isFillMemberTimesheet){
					isFillMyTimeSheet = isFillMemberTimesheet;
				}
			}
			List<TimesheetSubmissionsDocuments> timesheetSubmissionsDocumentsList = null;
			StringBuilder documentBuffer = new StringBuilder();	
			documentBuffer.append("<input type='hidden' name='attcahmentMaxRowNumber'  id='attcahmentMaxRowNumber' value='1' />");
			documentBuffer.append("<div id='timesheetDocumentsId' class='timesheetDocs'>"); 
			if(ts != null){
				timesheetSubmissionsDocumentsList = timesheetSubmissionsDocumentsService.getAllTimesheetSubmissionsDocumentsBySubmissionId(ts.getId());
				if(timesheetSubmissionsDocumentsList != null && !timesheetSubmissionsDocumentsList.isEmpty()){
						for(TimesheetSubmissionsDocuments tsd : timesheetSubmissionsDocumentsList){
							if(!isNullOrEmpty(tsd.getFileName())){
									documentBuffer.append("<strong id='"+tsd.getId()+"document'>");
									documentBuffer.append(" <a class='downicon' href=\'").append(sourceleadUrl).append("/downloadDocument?submissionDocumentId=").append(tsd.getId()).append("\'>");
									documentBuffer.append("<i class='glyphicon glyphicon-save'></i>");
									documentBuffer.append(" <input type='button' value='"+tsd.getFileName()+"'>");
									documentBuffer.append("</a>");
									
								    if(isFillMyTimeSheet 
								    		&&!isNullOrEmpty(approvalStatus)
								    		&&!(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue())&& from.equalsIgnoreCase("user"))
								    		&& !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())){
										documentBuffer.append("<input class='dele' type='button' onClick='deleteSubmissionDocument(\""+tsd.getId()+"\");' >");
									}
									documentBuffer.append("<input class='deleApp' type='button' onClick='viewSubmissionDocument(\""+tsd.getId()+"\",\""+tsd.getFileName()+"\");' >");
									documentBuffer.append("</strong>");
							} 
						}
					} 
			}
			 if(isFillMyTimeSheet 
			    		&&!isNullOrEmpty(approvalStatus)
			    		&&!(approvalStatus.equalsIgnoreCase(Submission_Status.getNameByValue("submitted").getValue()) && from.equalsIgnoreCase("user")) 
			    		&& !approvalStatus.equalsIgnoreCase(Submission_ApprovalStatus.getNameByValue("approved").getValue())){
            	documentBuffer.append("<div class='commentFile'>"); 
				documentBuffer.append("<span>"); 
				documentBuffer.append("<table class='timesheetAttachment' id='attachmentTable' cellpadding='0' cellspacing='0'>");
						documentBuffer.append("<tr id='divAttachmentTsRow0'>");
						if(isTimesheetAttachmentRequired != null && isTimesheetAttachmentRequired != 0 && (timesheetSubmissionsDocumentsList == null || timesheetSubmissionsDocumentsList.isEmpty())){
							documentBuffer.append("<input type='hidden' name='isTimesheetAttachmentRequired'  id='isTimesheetAttachmentRequired' value='Y' />");
						}else{
							documentBuffer.append("<input type='hidden' name='isTimesheetAttachmentRequired'  id='isTimesheetAttachmentRequired' value='N' />");
						}
						documentBuffer.append("<td><div id='0docFileuploadFileGroup'><input type='file' name='0docFile' id='0docFile' field-name='Attachment' onchange='validateUploadFile(0)' data-validation-optional='true' class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addAttachment()'></i></div></td> ");
						documentBuffer.append("<input type='hidden' name='dbIsTimesheetAttachmentRequired'  id='dbIsTimesheetAttachmentRequired' value='"+isTimesheetAttachmentRequired+"' />");         
						documentBuffer.append("</tr>");
				documentBuffer.append("</table>");
				documentBuffer.append("</span>");
				documentBuffer.append("<i class='fa fa-info-circle' title='Allowed File Formats: ppt,pptx,pps,ppsx,doc,docx,pdf,rtf,odt,xls,xlsx,csv,  zip,txt,jpg,jpeg,bmp,png,gif,eps,tiff,TGA,psd,svg,msg,wpd,wps      Allowed Size : 2MB '></i>"); 
				documentBuffer.append("</div>");
			}
		documentBuffer.append("</div>"); 
		return documentBuffer;
	}
	
		
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/newSaveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
			MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status,@RequestParam(value="savingType", required=false)  String savingType) throws Exception {
			
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		 	
		 	
			
			json = "{"+json+"}";
			JSONObject jsonObj = new JSONObject(json);
	    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    	//DateFormat format = Constants.DF_DMY;
	    	//DateFormat  format = LoginSessionController.DATEFORMAT;	
	    	ProjectMembers projectMember = null;
			if(jsonObj.getString("projectMemberId") != null && jsonObj.getString("projectMemberId") != "" && jsonObj.getString("projectMemberId") != "undefined"){
				  projectMember = projectMembersService.getProjectMemberById(Long.parseLong(jsonObj.getString("projectMemberId")));
			}
			Date frequencyStartDate = null;
			Date frequencyEndDate = null;
	    	if(status != null && (status.equalsIgnoreCase("Draft") || status.equalsIgnoreCase("Submitted"))){
				
					String result = "";
					String resultJson = null;
					
					String	 submissionId = null;
					if (jsonObj.has("submissionId")){
						 submissionId = jsonObj.getString("submissionId");
					}
					
					Date startDate = null;
					Date endDate = null;
					
					
					if(jsonObj.getString("tabStartDate") != null && jsonObj.getString("tabStartDate") != "" && jsonObj.getString("tabStartDate") != "undefined"){
			    	    startDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),jsonObj.getString("tabStartDate"));
			    	}
					if(jsonObj.getString("tabEndDate") != null && jsonObj.getString("tabEndDate") != "" && jsonObj.getString("tabEndDate") != "undefined"){
			    		 endDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),jsonObj.getString("tabEndDate"));
			    	}
					
					if(jsonObj.getString("frequencyDates") != null && jsonObj.getString("frequencyDates") != "" && jsonObj.getString("frequencyDates") != "undefined"){
						 String[] frequency = jsonObj.getString("frequencyDates").split("To");
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
		            if(!isNullOrEmpty(submissionId)){
		            	 timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(submissionId);
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
		            		 if(jsonObj.has("weekComment")){
		            			 timesheetSubmissions.setWeekcomments(jsonObj.getString("weekComment"));
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
		     	    	if(jsonObj.has("weekComment")){
		     	    		timesheetSubmissions.setWeekcomments(jsonObj.getString("weekComment"));
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
					if ((jsonObj.has("attcahmentMaxRowNumber"))){
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
			                       tsd.setStatus(Constants.CANDIDATE_ACTIVE);
				                   tsd.setCreatedDate(CommonUtil.currentDate());
				                   tsd.setCreatedBy(loginUser.getUserCode());
				                   tsd.setUpdatedDate(CommonUtil.currentDate());
				                   tsd.setUpdatedBy(loginUser.getUserCode());
								   timesheetSubmissionsDocumentsService.addTimesheetSubmissionsDocuments(tsd);
								   
			                   }
			                   
			                 
				    	}
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
								else{
									timesheetEntries.setCreatedBy(loginUser.getCreatedBy());
								}
						    } 
							timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
							timesheetEntries.setStartDate(startDate);
							timesheetEntries.setEndDate(startDate);
							timesheetEntries.setCreatedDate(CommonUtil.currentDate());
							timesheetEntries.setUpdatedBy(loginUser.getCreatedBy());
							timesheetEntries.setUpdatedDate(CommonUtil.currentDate());
							ProjectTasks projectTasks = new ProjectTasks();
							Boolean isBillable = false;
						   if (jsonObj.has(c+"task1Id")){
							   if(!isNullOrEmpty(jsonObj.getString(c+"task1Id"))){
								      String taskIdDetails[] = jsonObj.getString(c+"task1Id").split(",");
									 /* projectTasks.setId(Long.parseLong(taskIdDetails[0]));*/
									  projectTasks = projectTasksService.getTasksId(taskIdDetails[0]);
									  if(projectTasks != null){
										  timesheetEntries.setProjectTask(projectTasks);
										  if(projectTasks.getBillableType().equalsIgnoreCase("Billable")){
											  isBillable = true;
										  }
									  }
									  
							   }
							 }
							//For Monday Hours
							if (jsonObj.has(c+"timeSheetDay1Id")){
								String monHours = jsonObj.getString(c+"timeSheetDay1Id");
								if(!isNullOrEmpty(monHours)){
									timesheetEntries.setMonHours(monHours);
									total = getTotalHoursFormDayHours(monHours, total);
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(monHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(monHours,totalNonBillableHours);
									}
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(tueHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(tueHours,totalNonBillableHours);
									}
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(wedHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(wedHours,totalNonBillableHours);
									}
									
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(thuHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(thuHours,totalNonBillableHours);
									}
									
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(friHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(friHours,totalNonBillableHours);
									}
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(satHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(satHours,totalNonBillableHours);
									}
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
									if(isBillable){
										totalBillableHours = getTotalHoursFormDayHours(sunHours,totalBillableHours);
									}
									else{
										totalNonBillableHours = getTotalHoursFormDayHours(sunHours,totalNonBillableHours);
									}
									
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
						
						/*if(jsonObj.getString("grandBillableTotal") != null && jsonObj.getString("grandBillableTotal") != "" && jsonObj.getString("grandBillableTotal") != "undefined"){
							String grandBillableTotal = jsonObj.getString("grandBillableTotal").replaceAll(":", ".");
							timesheetSubmissions.setBillableHours(grandBillableTotal);
						}*/
						
						/*if(jsonObj.has("grandNonBillableTotal") && jsonObj.getString("grandNonBillableTotal") != null && jsonObj.getString("grandNonBillableTotal") != "" && jsonObj.getString("grandNonBillableTotal") != "undefined"){
							String grandNonBillableTotal = jsonObj.getString("grandNonBillableTotal").replaceAll(":", ".");
							timesheetSubmissions.setNonBillableHours(grandNonBillableTotal);
						}*/
						
						timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
						
						//send mail to assigned manager at time sheet submission
						if(!actualTimeString.equalsIgnoreCase("0.0")){
							if(!"Draft".equalsIgnoreCase(status) 
									&& projectMember != null 
									&&  projectMember.getProjects() != null
									&& frequencyStartDate != null
									&& frequencyEndDate != null ){
								sendEmailToManagerAtTimesheetSubmissions(projectMember.getMasterCandidateId().getId(),projectMember.getProjects().getId(),frequencyStartDate,frequencyEndDate,loginUser,loginOrgLocation,loginOrgCode);
							}
						}
					}
					/*String pattern = "dd-MM-yyyy";
			        format = new SimpleDateFormat(pattern);
			        String startDateStr = format.format(startDate);
					return startDateStr;*/
	    	}
			if(status != null &&  status.equalsIgnoreCase("Submitted")){
				if (jsonObj.has("tabDates")){
					  String tabDates = null;
				   	  try{
				   		   JSONArray jsonArray = jsonObj.getJSONArray("tabDates");
				   		   tabDates = jsonArray.toString();
				   	  }
				   	  catch(JSONException e){
				   		  tabDates= jsonObj.getString("tabDates");
				   	  }
				   	  tabDates = tabDates.replaceAll("\\[", "").replaceAll("\\]","");
				   	  String[] tabDatesArray = tabDates.split(",");
				   	  
				   	if(tabDatesArray != null && tabDatesArray.length>0){ 
						for(String tabDate : tabDatesArray){
							tabDate = tabDate.replace("\"", "");
							String[] words = tabDate.split("To");	
							Date actualStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),words[0].replaceAll("/", "-"));
							Date actualEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),words[1].replaceAll("/", "-"));
							List<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(actualStartDate, actualEndDate, projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
				            if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				            	for(TimesheetSubmissions timesheetSubmissions : timesheetSubmissionsList){
				            		List<SubmissionApproverHierarchy>  sahList =submissionApproverHierarchyService.getSubmissionApproverHierarchyBySubmissionId(timesheetSubmissions.getId());
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
							  				
				   	  return "";
				}
			}
			return "";
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
			 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
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
			
			TimesheetEntries timesheetEntries = new TimesheetEntries();
			timesheetEntries.setMonHours("00:00");
			timesheetEntries.setTueHours("00:00");
			timesheetEntries.setWedHours("00:00");
			timesheetEntries.setThuHours("00:00");
			timesheetEntries.setFriHours("00:00");
			timesheetEntries.setSatHours("00:00");
			timesheetEntries.setSunHours("00:00");
			
			timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
			timesheetEntries.setStartDate(actualStartDate);
			timesheetEntries.setEndDate(actualEndDate);     
			timesheetEntries.setCreatedDate(CommonUtil.currentDate());
			timesheetEntries.setUpdatedDate(CommonUtil.currentDate());
			
			timesheetEntriesService.addTimesheetEntries(timesheetEntries);
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
		
		
		@RequestMapping(value = "/acceptOrRejectReopenTimeSheetBySuperUser", method = RequestMethod.POST)
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
	    public String deleteSubmissionDocument(@RequestParam(value="submissionDocumentId", required=false) Long submissionDocumentId ,HttpServletResponse response) throws Exception {
	        
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