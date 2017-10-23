package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.dto.OnBoardingTemplateDocumentsDTO;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.OnboardingCandidateDocsStep3DTO;
import com.bis.sourcelead.dto.OnboardingCandidateSetp2DTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.OnboardStatus;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.service.OnboardTasksService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;
import com.drew.lang.StringUtil;

import cucumber.deps.difflib.StringUtills;

@RestController
public class CandidateOnBoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OnBoardingTemplateService onBoardingTemplateService;
	
	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	OnboardTasksService onboardTasksService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/getOnboardingTemplatesList")
	 public String getOnboardingTemplatesList(HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<OnBoardingTemplate> templatesList = onBoardingTemplateService.getOnBoardingTemplatesList(loginOrgLocation.getId());
		List<OnBoardingTemplate> onboardingTemplatesList = new ArrayList<OnBoardingTemplate>();
		//SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		for(OnBoardingTemplate template : templatesList){
			template.setTemplateCreatedDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),template.getCreatedDate()));
			onboardingTemplatesList.add(template);
		}
		
		resultJson = commonUtil.toJSON(onboardingTemplatesList);
		return resultJson;
	 }
	
	
	
	@RequestMapping(value = "/createNewOnBoardingTemplate" , method = RequestMethod.POST)
	 public @ResponseBody  OnBoardingTemplate createNewOnBoardingTemplate(OnBoardingTemplateDTO onBoardingTemplateDTO,HttpServletRequest request) throws Exception {
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 OnBoardingTemplate onBoardingTemplate = onBoardingTemplateService.getOnBoardTemplateDetails(onBoardingTemplateDTO, user, orgLocations);
		 return onBoardingTemplate;
	  
	}
	
	
	
	@RequestMapping(value = "/getOnboardTemplateById" , method = RequestMethod.POST)
	 public @ResponseBody OnBoardingTemplate getOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		
 	    OnBoardingTemplate onBoardingTemplate = onBoardingTemplateService.getOnBoardingTemplateById(templateId);
		return onBoardingTemplate;
	   }
	
	
	@RequestMapping(value = "/validateOnboardingTemplateName", method = RequestMethod.POST)
	@ResponseBody String validateOnboardingTemplateName(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		OnBoardingTemplate onBoardingTemplate = null;
		String result = null;
		
		if(!isNullOrEmpty(templateId)){
			onBoardingTemplate  = onBoardingTemplateService.getOnBoardingTemplateById(Long.parseLong(templateId));
			if(onBoardingTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				onBoardingTemplate  = onBoardingTemplateService.getOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
				if(null != onBoardingTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			onBoardingTemplate  = onBoardingTemplateService.getOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
			if(null != onBoardingTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	
	
	@RequestMapping(value = "/getOnboardTemplateDocumentByTemplateId" , method = RequestMethod.POST)
	 public @ResponseBody List<OnBoardingTemplateDocuments> getOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		
		List<OnBoardingTemplateDocuments> documentsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
		return documentsList;
	   
	}
	
	@RequestMapping(value = "/saveOnboardingTemplateDocuments", method = RequestMethod.POST)
	public @ResponseBody String saveOnboardingTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(Validator.validate(onBoardingTemplateDTO)) {
			onBoardingTemplateDTO.setStaticPath(staticPath);
//			FileUploadUtil.fillUploadedFileInfo(onBoardingTemplateDTO, onBoardingTemplateDTO.getFileInfo());
			onBoardingTemplateDocumentsService.storeOnBoardTemplateDocuments(onBoardingTemplateDTO, loginUser);
		 }else
			 return "DataMissing";
		
		
		return "success";
			
	}
	
	@RequestMapping(value = "/downloadTemplateDocument", method = RequestMethod.GET)
    public void downloadTemplateDocument(@RequestParam(value="documentUUID", required=false) String documentUUID ,HttpServletResponse response) throws Exception {
		
		
			OnBoardingTemplateDocuments	templateDocuments =	onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);	
		    
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
		    InputStream inputStream = null;
	        
		    if(!isNullOrEmpty(templateDocuments.getUploadedDocFileName())){
		    	response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(templateDocuments.getUploadedDocFileName()).append("\"").toString());
		    }else{
		    	response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(templateDocuments.getFileName()).append("\"").toString());
		    }
		    
	        if(null != templateDocuments.getUploadedDocFile()){
	        	inputStream = new ByteArrayInputStream(templateDocuments.getUploadedDocFile());
	        }else{
	        	inputStream = new ByteArrayInputStream(templateDocuments.getDocFile());
	        }
	        
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
	
	
	@RequestMapping(value = "/deleteTemplateDocumentByDocumentUUID" , method = RequestMethod.POST)
	 public @ResponseBody  String deleteTemplateDocumentByDocumentUUID(@RequestParam(value="documentUUID", required=false) String documentUUID,
			 HttpServletRequest request) throws Exception {
		
		OnBoardingTemplateDocuments	templateDocuments =	onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	 	 if(null != templateDocuments){
	 		templateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	 		onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(templateDocuments);
	 	 }
		return "success";
	   
	}
	
	
	 @RequestMapping(value = "/getOnboardTasksAssignToUsersList", method = RequestMethod.POST)
	 @ResponseBody List<UserRoleDTO> getOnboardTasksAssignToUsersList(HttpServletRequest request) throws Exception{
		 
		 List<UserRoleDTO> userRoleListDTO=new ArrayList<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 List<UserRole> userRoleList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
			for(UserRole ur :userRoleList){
				
				UserRoleDTO userRoleDTO = new UserRoleDTO();
				StringBuilder userName = new StringBuilder();
				userRoleDTO.setUserId(ur.getUser().getId());
				
				if(null != ur.getUser().getLastName()){
					userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
				}else{
					userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).toString());
				}
				userRoleListDTO.add(userRoleDTO);
			}
			
		return userRoleListDTO;
		}
	 
	
	 
	 @RequestMapping(value = "/saveOnBoardTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveOnBoardTasks(OnboardTasksDTO onboardTasksDTO, HttpServletRequest request) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OnboardTasks onboardTasks = new OnboardTasks();
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskName())){
			 onboardTasks.setTaskName(onboardTasksDTO.getTaskName());
		 }
		 
		 OnBoardingTemplate template = new OnBoardingTemplate();
		 template.setId(Long.valueOf(onboardTasksDTO.getTemplateId()));
		 onboardTasks.setOnboardingTemplateId(template);
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getOnboardAssignedTo())){
			 onboardTasks.setAssignToUsers(onboardTasksDTO.getOnboardAssignedTo());
		 }else{
			 onboardTasks.setAssignToCandidate(Constants.YES);
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskPriority())){
			 onboardTasks.setPriority(onboardTasksDTO.getTaskPriority());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskRepeat())){
			 onboardTasks.setTaskRepeat(onboardTasksDTO.getTaskRepeat());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMonths())){
			 onboardTasks.setDueAfterMonths(Integer.parseInt(onboardTasksDTO.getTaskMonths()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskDays())){
			 onboardTasks.setDueAfterDays(Integer.parseInt(onboardTasksDTO.getTaskDays()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskHours())){
			 onboardTasks.setDueAfterHours(Integer.parseInt(onboardTasksDTO.getTaskHours()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMinutes())){
			 onboardTasks.setDueAfterMinutes(Integer.parseInt(onboardTasksDTO.getTaskMinutes()));
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskNotification())){
			 onboardTasks.setTaskNotification(onboardTasksDTO.getTaskNotification());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskEmail())){
			 onboardTasks.setTaskEmail(onboardTasksDTO.getTaskEmail());
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskdescription())){
			 onboardTasks.setDescription(onboardTasksDTO.getTaskdescription());
		 }
		 
		 onboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
		 onboardTasks.setCreatedDate(CommonUtil.currentDate());
		 onboardTasks.setCreatedBy(user.getUserCode());
		 onboardTasks.setUpdatedDate(CommonUtil.currentDate());
		 onboardTasks.setUpdatedBy(user.getUserCode());
		 
		 onboardTasksService.saveOnboardTasks(onboardTasks);
		  return "success";
	   }
	 
	 
	 @RequestMapping(value = "/getOnboardTasksListByTemplateId", method = RequestMethod.POST)
	 @ResponseBody String getOnboardTasksListByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		
		 List<OnboardTasks> tasksList = onboardTasksService.getOnboardTasksByTemplateId(templateId);
		 
		 List<OnboardTasks> onboardtasksList = new ArrayList<OnboardTasks>();
		 
		 for(OnboardTasks tasks : tasksList){
			 
			 List<String> usersList = new ArrayList<String>();
				 
				 if(!isNullOrEmpty(tasks.getAssignToUsers())){
					 String[] userIds = tasks.getAssignToUsers().split(",");
					 
					 for(int i = 0 ; i < userIds.length; i++ ){
						 StringBuilder userName = new StringBuilder();
						 User user = userService.getUserById(Long.valueOf(userIds[i]));
						 String assignedUser = null;
						 if(null != user.getLastName()){
							 assignedUser = userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
							}else{
								assignedUser = userName.append(user.getFirstName()).toString();
							}
						 usersList.add(assignedUser);
					 }
				 }
				 
				 StringBuilder dueAfter = new StringBuilder();
				 
				 if(null != tasks.getDueAfterMonths()){
					 dueAfter = dueAfter.append(tasks.getDueAfterMonths()).append(" Month(s) ");
				 }
				 
				 if(null != tasks.getDueAfterDays()){
					 dueAfter = dueAfter.append(tasks.getDueAfterDays()).append(" Day(s) ");
				 }
				 
				 if(null != tasks.getDueAfterHours()){
					 dueAfter = dueAfter.append(tasks.getDueAfterHours()).append(" Hours ");
				 }
				 
				 if(null != tasks.getDueAfterMinutes()){
					 dueAfter = dueAfter.append(tasks.getDueAfterMinutes()).append(" Minutes");
				 }
				 
				
				 tasks.setDueAfterString(dueAfter.toString());
				 tasks.setUsersList(usersList); 
				 onboardtasksList.add(tasks);
		 }
			
		return commonUtil.toJSON(onboardtasksList);
		}
	 
	 
	 
	 @RequestMapping(value = "/saveAndFinishOnboardTemplate", method = RequestMethod.POST)
	 @ResponseBody String saveAndFinishOnboardTemplate(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		
		OnBoardingTemplate template = onBoardingTemplateService.getOnBoardingTemplateById(templateId);
		if(null != template){
			template.setTemplateStatus("Finished");
			onBoardingTemplateService.saveOnBoardingTemplate(template);
			SourceLeadSessionManager.setSessionAttribute(request, "OnboardCreateMsg", "Success!! New OnBoard Template created");
		}
		return "success";
		
	 }
	 
	 
	 @RequestMapping(value = "/getOfferAcceptedCandidatesForOnBoard")
	 public String getOfferAcceptedCandidatesForOnBoard(HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<RequirementOffers> candidateOffersList = requirementOffersService.getOfferAcceptedCandidatesByOrgLocationId(loginOrgLocation.getId());
		List<RequirementOffers> offersList = new ArrayList<RequirementOffers>();
		
		
		if(null != candidateOffersList  && candidateOffersList.size() > 0){
			
			for(RequirementOffers offers : candidateOffersList){
				
				StringBuilder candidateName = new StringBuilder();
				
					if(null != offers.getSubmissionId().getCandidate().getLastName()){
						offers.setCandidateName(candidateName.append(offers.getSubmissionId().getCandidate().getFirstName()).append(" ").append(offers.getSubmissionId().getCandidate().getLastName()).toString());
					}else{
						offers.setCandidateName(candidateName.append(offers.getSubmissionId().getCandidate().getFirstName()).toString());
					}
					
					offers.setCandidateId(offers.getSubmissionId().getCandidate().getId());
					offers.setJobTitle(offers.getSubmissionId().getRequirement().getJobTitle());
					
					offersList.add(offers);
			}
			
			}
		
		
		return commonUtil.toJSON(offersList);
	 
	 }
	 
	 
	 
	 @RequestMapping(value = "/getOnBoardingCandidateDetails")
		public String getOnBoardingCandidateDetails(@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request) throws Exception {

			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			
			return commonUtil.toJSON(candidate);

		}
	 
	 
	 
	 @RequestMapping(value = "/getOnBoardingCandidateOfferDetails")
		public String getOnBoardingCandidateOfferDetails(@RequestParam(value = "offerId", required = false) Long offerId,
				@RequestParam(value = "locationCode") String locationCode) throws Exception {
		 
		 	OrgLocations locations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		 	//SimpleDateFormat dateFormat = locations.getLocFullFormDateFormatter();
			RequirementOffers offer = requirementOffersService.getRequirementOfferId(offerId);
			if(null != offer.getDateOfJoining()){
				offer.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),offer.getDateOfJoining()));
			}
			
			
			return commonUtil.toJSON(offer);

		}
	 
	 
	 
	 @RequestMapping(value = "/getRolesListForCandidateOnBoarding")
		public String getRolesListForCandidateOnBoarding(HttpServletRequest request) throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Roles> rolesList = rolesService.getRoles(loginOrgLocation.getId());
			
			return commonUtil.toJSON(rolesList);

		}
	 
	 
	 @RequestMapping(value = "/getTeamsListForCandidateOnBoarding")
		public String getTeamsListForCandidateOnBoarding(HttpServletRequest request) throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Team> teamList = teamService.getTeamListByOrgLocationID(loginOrgLocation.getId());
			
			return commonUtil.toJSON(teamList);

		}
	 
	 
	 @RequestMapping(value = "/getOnboardingTemplatesForCandidateOnBoarding")
		public String getOnboardingTemplatesForCandidateOnBoarding(HttpServletRequest request) throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<OnBoardingTemplate> teamplatesList = onBoardingTemplateService.getOnBoardingFinishedTemplatesList(loginOrgLocation.getId());
			
			return commonUtil.toJSON(teamplatesList);

		}
	 
	 
	 
	 @RequestMapping(value = "/saveOnboardingCandidateDetails" , method = RequestMethod.POST)
	 public @ResponseBody  CandidateOnBoarding saveOnboardingCandidateDetails(CandidateOnBoardingDTO candidateOnBoardingDTO , HttpServletRequest request) throws Exception {
		
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.saveOnBoardingCandidatesDetails(candidateOnBoardingDTO, loginOrgLocation, user);
		return candidateOnBoarding;
		 	
	 /*	resultJson = commonUtil.toJSON(Long.valueOf(candidateOnBoarding.getId()));
		return resultJson;*/
	   }
	 
	 
	 @RequestMapping(value = "/getOnboardCandidateList")
	 public String getOnboardCandidateList(@RequestParam("locationCode") String locationCode) throws Exception {

		String resultJson;
		
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		//SimpleDateFormat format = loginOrgLocation.getLocFullFormDateFormatter();
		List<CandidateOnBoarding> candidateOnBoardingList = candidateOnBoardingService.getCandidateOnBoardingListByOrgLocationId(loginOrgLocation.getId());
		List<CandidateOnBoarding> onBoardingList = new ArrayList<CandidateOnBoarding>();
		
		if(null != candidateOnBoardingList && candidateOnBoardingList.size() > 0){
			for(CandidateOnBoarding candidateOnBoarding : candidateOnBoardingList){
				if(null != candidateOnBoarding.getStartDate()){
					candidateOnBoarding.setJoiningDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),candidateOnBoarding.getStartDate()));
				}
				
				StringBuilder candidateName = new StringBuilder();
				
				if(null != candidateOnBoarding.getLastName()){
					candidateOnBoarding.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).append(" ").append(candidateOnBoarding.getLastName()).toString());
				}else{
					candidateOnBoarding.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).toString());
				}
				
				if(null != candidateOnBoarding.getOnboardingUser()){
					candidateOnBoarding.setUserEmail(candidateOnBoarding.getOnboardingUser().getEmail());
				}
				
				Integer documentsCount = onBoardingTemplateDocumentsService.getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(candidateOnBoarding.getId());
				candidateOnBoarding.setDocumentsCount(documentsCount);
				Integer acceptedDocumentsCount = onBoardingTemplateDocumentsService.getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(candidateOnBoarding.getId());
				candidateOnBoarding.setDocumentsAcceptedCount(acceptedDocumentsCount);
				
				onBoardingList.add(candidateOnBoarding);
				
			}
		}
		
		resultJson = commonUtil.toJSON(onBoardingList);
		
		return resultJson;
	 }
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value = {"/loadOnboardingCandidateDetails","/rest/loadOnboardingCandidateDetails"}, method = RequestMethod.POST)
	 public CandidateOnBoardingDTO loadOnboardingCandidateDetails(@RequestParam(value="onBoardingUUID", required=false) String onBoardingUUID,
			 @RequestParam(value="locationCode") String locationCode) throws Exception {

		 CandidateOnBoardingDTO candidateOnBoardingDTO = new CandidateOnBoardingDTO();
		StringBuilder candidateName = new StringBuilder();
		OrgLocations orglocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		//SimpleDateFormat formatter = orglocations.getLocFullFormDateFormatter();
		CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(onBoardingUUID);
		if(null != candidateOnBoarding){
			candidateOnBoardingDTO.setOnBoardCandidateId(candidateOnBoarding.getCandidateId().getId());
			candidateOnBoardingDTO.setOnBoardOfferId(candidateOnBoarding.getOfferId().getId());
			candidateOnBoardingDTO.setOnBoardRoleId((candidateOnBoarding.getRoleId().getId()).toString());
			candidateOnBoardingDTO.setJobTitle(candidateOnBoarding.getOfferId().getSubmissionId().getRequirement().getJobTitle());
			
			if(null != candidateOnBoarding.getManagerUserId()){
				StringBuilder mgName = new StringBuilder();
				if(candidateOnBoarding.getManagerUserId().getLastName() != null){
					candidateOnBoardingDTO.setManagerName(mgName.append(candidateOnBoarding.getManagerUserId().getFirstName()).append(" ").append(candidateOnBoarding.getManagerUserId().getLastName()).toString());	
				}else{
					candidateOnBoardingDTO.setManagerName(mgName.append(candidateOnBoarding.getManagerUserId().getFirstName()).toString());
				}
			}
			if(null != candidateOnBoarding.getId()){
				candidateOnBoardingDTO.setId((candidateOnBoarding.getId()).toString());	
			}
			if(null != candidateOnBoarding.getFirstName()){
				candidateOnBoardingDTO.setFirstName(candidateOnBoarding.getFirstName());	
			}
			if(null != candidateOnBoarding.getLastName()){
				candidateOnBoardingDTO.setLastName(candidateOnBoarding.getLastName());	
			}
			if(null != candidateOnBoarding.getAddress1()){
				candidateOnBoardingDTO.setAddress1((candidateOnBoarding.getAddress1()));	
			}
			if(null != candidateOnBoarding.getAddress2()){
				candidateOnBoardingDTO.setAddress2((candidateOnBoarding.getAddress2()));	
			}
			if(null != candidateOnBoarding.getMobileNumber()){
				candidateOnBoardingDTO.setMobileNumber((candidateOnBoarding.getMobileNumber()));	
			}
			if(null != candidateOnBoarding.getPersonalEmail()){
				candidateOnBoardingDTO.setPersonalEmail((candidateOnBoarding.getPersonalEmail()));	
			}
			
			if(null != candidateOnBoarding.getZipCode()){
				candidateOnBoardingDTO.setZipCode((candidateOnBoarding.getZipCode()));	
			}
			if(null != candidateOnBoarding.getCountry()){
				candidateOnBoardingDTO.setCountry((candidateOnBoarding.getCountry()));	
			}
			if(null != candidateOnBoarding.getState()){
				candidateOnBoardingDTO.setCountry((candidateOnBoarding.getCountry()));	
			}
			if(null != candidateOnBoarding.getCity()){
				candidateOnBoardingDTO.setCity((candidateOnBoarding.getCity()));	
			}
			if(null != candidateOnBoarding.getLocation()){
				candidateOnBoardingDTO.setLocation((candidateOnBoarding.getLocation()));	
			}
			if(null != candidateOnBoarding.getPaymentMode()){
				candidateOnBoardingDTO.setPaymentMode((candidateOnBoarding.getPaymentMode()));	
			}
			if(null != candidateOnBoarding.getPaymentType()){
				candidateOnBoardingDTO.setPaymentType((candidateOnBoarding.getPaymentType()));	
			}
			if(null != candidateOnBoarding.getSalaryRate()){
				candidateOnBoardingDTO.setSalaryRate((candidateOnBoarding.getSalaryRate()));	
			}
			if(null != candidateOnBoarding.getEmploymentOtherType()){
				candidateOnBoardingDTO.setEmploymentOtherType((candidateOnBoarding.getEmploymentOtherType()));	
			}
			if(null != candidateOnBoarding.getEmploymentType()){
				candidateOnBoardingDTO.setEmploymentType((candidateOnBoarding.getEmploymentType()));	
			}
			if(null != candidateOnBoarding.getProbationNotice()){
				candidateOnBoardingDTO.setProbationNotice((candidateOnBoarding.getProbationNotice()));	
			}
			if(null != candidateOnBoarding.getTerminationNotice()){
				candidateOnBoardingDTO.setTerminationNotice((candidateOnBoarding.getTerminationNotice()));	
			}
			if(null != candidateOnBoarding.getDueDateStr()){
				candidateOnBoardingDTO.setDueDateStr((candidateOnBoarding.getDueDateStr()));	
			}
			if(null != candidateOnBoarding.getSpecialConditions()){
				candidateOnBoardingDTO.setSpecialConditions((candidateOnBoarding.getSpecialConditions()));	
			}
			if(null != candidateOnBoarding.getOnboardTemplateId()){
				candidateOnBoardingDTO.setOnboardTemplateId((candidateOnBoarding.getOnboardTemplateId()));	
			}
			
			if(null != candidateOnBoarding.getCompanyEmail()){
				candidateOnBoardingDTO.setState((candidateOnBoarding.getState()));	
			}
			if(null != candidateOnBoarding.getEndDateStr()){
				candidateOnBoardingDTO.setEndDateStr((candidateOnBoarding.getEndDateStr()));	
			}
			if(null != candidateOnBoarding.getEndDate()){
				candidateOnBoardingDTO.setEndDate((candidateOnBoarding.getEndDate()).toString());	
			}
			if(null != candidateOnBoarding.getDueDate()){
				candidateOnBoardingDTO.setDueDate((candidateOnBoarding.getDueDate()).toString());	
			}
			
			if(null != candidateOnBoarding.getTeamId()){
				candidateOnBoardingDTO.setTeamName(candidateOnBoarding.getTeamId().getName());	
			}
			if(null !=candidateOnBoarding.getRoleId()){
				candidateOnBoardingDTO.setRoleName(candidateOnBoarding.getRoleId().getDisplayName());	
			}
			if(null != candidateOnBoarding.getManagerUserId()){
				candidateOnBoardingDTO.setOnBoardManagerId((candidateOnBoarding.getManagerUserId().getId()).toString());
			}
			
			if(null != candidateOnBoarding.getTeamId()){
				candidateOnBoardingDTO.setOnBoardTeamId((candidateOnBoarding.getTeamId().getId()).toString());
			}
			
			candidateOnBoardingDTO.setJoiningDateStr(commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),candidateOnBoarding.getStartDate()));
			
			if(null != candidateOnBoarding.getEndDate()){
				candidateOnBoardingDTO.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),candidateOnBoarding.getEndDate()));
			}
			
			if(null != candidateOnBoarding.getDueDate()){
				candidateOnBoardingDTO.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orglocations.getDateFormat(),candidateOnBoarding.getDueDate()));
			}
			
			
			if(null != candidateOnBoarding.getLastName()){
				candidateOnBoardingDTO.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).append(" ").append(candidateOnBoarding.getLastName()).toString());
			}else{
				candidateOnBoardingDTO.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).toString());
			}
			
		}
		
		return candidateOnBoardingDTO;
	 }
	 
	 
		@RequestMapping(value ={ "/getCandidateOnboardTemplateDocumentByOnboardId", "/rest/getCandidateOnboardTemplateDocumentByOnboardId"}, method = RequestMethod.POST)
		 public @ResponseBody  List<OnBoardingTemplateDocumentsDTO> getCandidateOnboardTemplateDocumentByOnboardId(@RequestParam(value="onboardId", required=false) Long onboardId,
				 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
			
			List<OnBoardingTemplateDocumentsDTO> listDocumentsDTO=new ArrayList<>();
			List<OnBoardingTemplateDocuments> documentsList = null;
			
			Integer documentsCount = onBoardingTemplateDocumentsService.getCandidateOnboardingDocumentsByCandidateOnboardingId(onboardId);
			
			if(documentsCount > 0){
				documentsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(onboardId,documentType);
				if(null != documentsList && documentsList.size() > 0){
					for(OnBoardingTemplateDocuments  documents : documentsList ){
						documents.setOnboardDocumentType("Candidate Documents");
						listDocumentsDTO.add(getOnboardDocuments(documents));
					}
				}
				
				
			}else{
				 
				CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingById(onboardId);
				 List<Long> templateIdsList = new ArrayList<Long>();
				 
				 if(!isNullOrEmpty(onboarding.getOnboardTemplateId())){
					 for (String s : onboarding.getOnboardTemplateId().split(",")){
						 templateIdsList.add(Long.parseLong(s));
					 }
					 
					documentsList = onBoardingTemplateDocumentsService.getCandidateOnBoardingTemplateDocumentsByTemplateId(templateIdsList,documentType);
					if(null != documentsList && documentsList.size() > 0){
						for(OnBoardingTemplateDocuments  documents : documentsList ){
							documents.setOnboardDocumentType("Template Documents");
							listDocumentsDTO.add(getOnboardDocuments(documents));
						}
					}
				 }
			}
			
			return listDocumentsDTO;
		   
		}
		public  OnBoardingTemplateDocumentsDTO getOnboardDocuments(OnBoardingTemplateDocuments onBoardTemp){
			OnBoardingTemplateDocumentsDTO onBoardingTemplDTO=new OnBoardingTemplateDocumentsDTO();
			onBoardingTemplDTO.setId(onBoardTemp.getId());
			onBoardingTemplDTO.setFileName(onBoardTemp.getFileName());
			onBoardingTemplDTO.setFileURL(onBoardTemp.getFileURL());
			onBoardingTemplDTO.setDocumentUUID(onBoardTemp.getDocumentUUID());
			onBoardingTemplDTO.setOnboardDocumentType(onBoardTemp.getDocumentType());
			return onBoardingTemplDTO;
		}
	 
	 
		@RequestMapping(value = "/saveCandidatedOnboardingTemplateDocuments", method = RequestMethod.POST)
		public @ResponseBody String saveCandidatedOnboardingTemplateDocuments(OnBoardingTemplateDocumentsDTO onBoardingTemplateDocumentsDTO,TimesheetEntriesForm timesheetEntriesForm,
				HttpServletRequest request) throws Exception {
			   onBoardingTemplateDocumentsDTO.setStaticPath(staticPath);
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			   CandidateOnBoarding onBoarding = candidateOnBoardingService.getCandidateOnBoardingById(Long.valueOf(onBoardingTemplateDocumentsDTO.getCandidateonboardingId()));
			   
			   //Contracts document saving...
			   if(onBoardingTemplateDocumentsDTO.getContractsFileInfo() != null &&  onBoardingTemplateDocumentsDTO.getContractsFileInfo().size() > 0) {
					for(FileInfo fileInfo : onBoardingTemplateDocumentsDTO.getContractsFileInfo()) {
						OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
						documents.setCandidateOnBoardingId(onBoarding);
						documents.setDocumentStatus(OnboardStatus.NEW);
						documents.setDocumentType("Contracts");
	                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                    documents.setCreatedDate(CommonUtil.currentDate());
	                    documents.setCreatedBy(loginUser.getUserCode());
	                    documents.setUpdatedDate(CommonUtil.currentDate());
	                    documents.setUpdatedBy(loginUser.getUserCode());
	                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
		                    FileUploadUtil.moveFile(onBoardingTemplateDocumentsDTO .getStaticPath(), fileInfo, "Candidate-Onboard-Contracts");
					}
				}
			
			   //Contracts document updating...
				if(StringUtils.isNotBlank(onBoardingTemplateDocumentsDTO.getSavedContractsAttcahments())){
					 String[] savedTemplateIds = onBoardingTemplateDocumentsDTO.getSavedContractsAttcahments().split(",");
					 for(int i=0; i < savedTemplateIds.length; i++){
						 OnBoardingTemplateDocuments templateDocument = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.valueOf(savedTemplateIds[i]));
						   templateDocument.setCandidateOnBoardingId(onBoarding);
						    templateDocument.setUpdatedDate(CommonUtil.currentDate());
						    templateDocument.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(templateDocument);
					 
					 }
					 
				}
				
			   
			   	// Forms documents saving....
				if(onBoardingTemplateDocumentsDTO.getFormsFileInfo() != null && onBoardingTemplateDocumentsDTO.getFormsFileInfo().size() > 0) {
					for(FileInfo fileInfo : onBoardingTemplateDocumentsDTO.getFormsFileInfo()) {
						OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
						    documents.setCandidateOnBoardingId(onBoarding);
						    documents.setDocumentType("Forms");
			       		    documents.setDocumentStatus(OnboardStatus.NEW);
		                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                    documents.setCreatedDate(CommonUtil.currentDate());
		                    documents.setCreatedBy(loginUser.getUserCode());
		                    documents.setUpdatedDate(CommonUtil.currentDate());
		                    documents.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
	       					FileUploadUtil.moveFile(onBoardingTemplateDocumentsDTO .getStaticPath(), fileInfo, "Candidate-Onboard-Forms");
					}
				}
				
				// Forms documents updating....
				if(StringUtils.isNotBlank(onBoardingTemplateDocumentsDTO.getSavedFormsAttcahments())){
					 String[] savedTemplateIds = onBoardingTemplateDocumentsDTO.getSavedFormsAttcahments().split(",");
					 for(int i=0; i < savedTemplateIds.length; i++){
						 OnBoardingTemplateDocuments templateDocument = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.valueOf(savedTemplateIds[i]));
						    templateDocument.setCandidateOnBoardingId(onBoarding);
		       		        templateDocument.setUpdatedDate(CommonUtil.currentDate());
		                    templateDocument.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(templateDocument);
					 
					 }
					 
				}
				
				//Polacies documents saving.....
				if(onBoardingTemplateDocumentsDTO.getPolaciesFileInfo() != null && onBoardingTemplateDocumentsDTO.getPolaciesFileInfo().size() > 0) {
					for(FileInfo fileInfo : onBoardingTemplateDocumentsDTO.getPolaciesFileInfo()) {
						OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
						    documents.setCandidateOnBoardingId(onBoarding);
		                    documents.setDocumentType("Policies");
		                    documents.setDocumentStatus(OnboardStatus.NEW);
		                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                    documents.setCreatedDate(CommonUtil.currentDate());
		                    documents.setCreatedBy(loginUser.getUserCode());
		                    documents.setUpdatedDate(CommonUtil.currentDate());
		                    documents.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
		   					FileUploadUtil.moveFile(onBoardingTemplateDocumentsDTO .getStaticPath(), fileInfo, "Candidate-Onboard-Policies");
					}
				}
				
				//Polacies documents updating.....
				if(StringUtils.isNotBlank(onBoardingTemplateDocumentsDTO.getSavedPoliciesAttcahments())){
					 String[] savedTemplateIds = onBoardingTemplateDocumentsDTO.getSavedPoliciesAttcahments().split(",");
					 for(int i=0; i < savedTemplateIds.length; i++){
						 OnBoardingTemplateDocuments templateDocument = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.valueOf(savedTemplateIds[i]));
						   
						 templateDocument.setCandidateOnBoardingId(onBoarding);
		       		      
		                    templateDocument.setUpdatedDate(CommonUtil.currentDate());
		                    templateDocument.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(templateDocument);
					 
					 }
				}
				
				//Supporting documents saving..
				if(onBoardingTemplateDocumentsDTO.getSupportingFileInfo() != null && onBoardingTemplateDocumentsDTO.getSupportingFileInfo().size() > 0) {
					for(FileInfo fileInfo : onBoardingTemplateDocumentsDTO.getSupportingFileInfo()) {
						OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						 if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
						        documents.setCandidateOnBoardingId(onBoarding);  
		                        documents.setDocumentType("Supported");
		                        documents.setDocumentStatus(OnboardStatus.NEW);
			                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
			                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			                    documents.setCreatedDate(CommonUtil.currentDate());
			                    documents.setCreatedBy(loginUser.getUserCode());
			                    documents.setUpdatedDate(CommonUtil.currentDate());
			                    documents.setUpdatedBy(loginUser.getUserCode());
			                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
			      			 FileUploadUtil.moveFile(onBoardingTemplateDocumentsDTO .getStaticPath(), fileInfo, "Candidate-Onboard-Supporting");
					}
				}
				
				//Supporting documents updating..
				if(StringUtils.isNotBlank(onBoardingTemplateDocumentsDTO.getSavedSupportedAttcahments())){
					 String[] savedTemplateIds = onBoardingTemplateDocumentsDTO.getSavedSupportedAttcahments().split(",");
					 for(int i=0; i < savedTemplateIds.length; i++){
						 OnBoardingTemplateDocuments templateDocument = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.valueOf(savedTemplateIds[i]));
						    
						 templateDocument.setCandidateOnBoardingId(onBoarding);
		   
		                    templateDocument.setUpdatedDate(CommonUtil.currentDate());
		                    templateDocument.setUpdatedBy(loginUser.getUserCode());
		                    onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(templateDocument);
					 
					 }
					 
				}
				
			return "success";
				
		}
		
		
		 @RequestMapping(value = "/storeOnboardingCandidateDocsStep3" , method = RequestMethod.POST)
		 public @ResponseBody  String storeOnboardingCandidateDocsStep3(OnboardingCandidateDocsStep3DTO onboardingCandidateDocsStep3DTO,HttpServletRequest request) throws Exception {
			 onboardingCandidateDocsStep3DTO.setStaticPath(staticPath);
			 //contracts documents saving.....
			  List<Long> dbContractDocIds = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(Long.valueOf(onboardingCandidateDocsStep3DTO.getCandidateOnboardId()), Constants.ONBOARDING_CONTRACTS);
			  List<Long> freshContractDocIds = new ArrayList<>();
			  if(onboardingCandidateDocsStep3DTO.getUploadContractsFile() != null && onboardingCandidateDocsStep3DTO.getUploadContractsFile().size()>=0){
				  for(FileInfo fileInfo : onboardingCandidateDocsStep3DTO.getUploadContractsFile()) {
					  if(StringUtils.isNotEmpty(fileInfo.getFileDbId())) {
						  OnBoardingTemplateDocuments contractsDocs = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.parseLong(fileInfo.getFileDbId()));
						  contractsDocs.setUploadFileURL(fileInfo.getFileURL());
 			       		  contractsDocs.setUploadedDocFileName(fileInfo.getFileName());
		                  contractsDocs.setUploadedFileContentType(fileInfo.getContentType());
	                      contractsDocs.setDocumentStatus(OnboardStatus.ACCEPTED);
	                      contractsDocs = onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(contractsDocs);
	                      FileUploadUtil.moveFile(onboardingCandidateDocsStep3DTO .getStaticPath(), fileInfo, "Welcome-Onboarding-Contract-Documents");
	                      freshContractDocIds.add(contractsDocs.getId());
					  }
					  
				  }
			  }
			  dbContractDocIds.removeAll(freshContractDocIds);
			  if(dbContractDocIds != null && !dbContractDocIds.isEmpty()) {
				  for(Long dbContractDocId :dbContractDocIds) {
					  OnBoardingTemplateDocuments contractsDocs = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(dbContractDocId);
					  if(contractsDocs.getDocumentStatus().equals(OnboardStatus.NEW)){
               		   contractsDocs.setDocumentStatus(OnboardStatus.PENDING);
	                       onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(contractsDocs); 
               	       }
				  }
			  }
			  
			  //Forms documents saving....
			  List<Long> dbFormsDocIds = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(Long.valueOf(onboardingCandidateDocsStep3DTO.getCandidateOnboardId()), Constants.ONBOARDING_FORMS);
			  List<Long> freshFormsDocIds = new ArrayList<>();
			  if(onboardingCandidateDocsStep3DTO.getUploadFormsFile() != null && onboardingCandidateDocsStep3DTO.getUploadFormsFile().size()>=0){
				  for(FileInfo fileInfo : onboardingCandidateDocsStep3DTO.getUploadFormsFile()) {
					  if(StringUtils.isNotEmpty(fileInfo.getFileDbId())) {
						  OnBoardingTemplateDocuments formDocs = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(Long.parseLong(fileInfo.getFileDbId()));
						  formDocs.setUploadFileURL(fileInfo.getFileURL());
 			       		  formDocs.setUploadedDocFileName(fileInfo.getFileName());
		                  formDocs.setUploadedFileContentType(fileInfo.getContentType());
	                      formDocs.setDocumentStatus(OnboardStatus.ACCEPTED);
	                      formDocs = onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(formDocs);
	                      FileUploadUtil.moveFile(onboardingCandidateDocsStep3DTO .getStaticPath(), fileInfo, "Welcome-Onboarding-Forms-Documents");
	                      freshFormsDocIds.add(formDocs.getId());
					  }
					  
				  }
			  }
			  dbFormsDocIds.removeAll(freshFormsDocIds);
			  if(dbFormsDocIds != null && !dbFormsDocIds.isEmpty()) {
				  for(Long dbFormsDocId :dbFormsDocIds) {
					  OnBoardingTemplateDocuments contractsDocs = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsById(dbFormsDocId);
					  if(contractsDocs.getDocumentStatus().equals(OnboardStatus.NEW)){
               		   contractsDocs.setDocumentStatus(OnboardStatus.PENDING);
	                       onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(contractsDocs); 
               	       }
				  }
			  }
			  
			/*	if(StringUtils.isNotBlank(onboardingCandidateDocsStep3DTO.getCandidateOnboardId())){
						List<OnBoardingTemplateDocuments> formsDocsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(Long.valueOf(onboardingCandidateDocsStep3DTO.getCandidateOnboardId()), Constants.ONBOARDING_FORMS);
						if(null != formsDocsList && formsDocsList.size() > 0){
							for(OnBoardingTemplateDocuments formsDocs : formsDocsList){
								
									final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
					       		    final Map files = multiRequest.getFileMap();
					       		     
					       		    MultipartFile  multiFile = (MultipartFile)files.get("docFile"+formsDocs.getId());
					       		    if(multiFile != null && !multiFile.isEmpty()){
					       		    	formsDocs.setUploadedDocFile(multiFile.getBytes());
					       		    	formsDocs.setUploadedDocFileName(multiFile.getOriginalFilename());
				                       
				                       if(null != multiFile.getContentType()){
				                    	   formsDocs.setUploadedFileContentType(multiFile.getContentType());
				                       }
				                       formsDocs.setDocumentStatus(OnboardStatus.ACCEPTED);
				                       onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(formsDocs);
				                   }else{
				                	   
				                	   if(formsDocs.getDocumentStatus().equals(OnboardStatus.NEW)){
				                		   formsDocs.setDocumentStatus(OnboardStatus.PENDING); 
				                		   onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(formsDocs);
				                	   }
				                       
				                   }
								}
							}
						
				}
			 */
			 
			return "success";
		   }
		
		
		
		 @RequestMapping(value = "/getCandidateOnboardTasksList", method = RequestMethod.POST)
		 @ResponseBody List<OnboardTasksDTO> getCandidateOnboardTasksList(@RequestParam(value="onboardId", required=false) Long onboardId,HttpServletRequest request) throws Exception{
			
			
			 List<OnboardTasks> templateTasksList = null;
			 
			 List<OnboardTasks> tasksList = new ArrayList<OnboardTasks>();;
			 
		     List<OnboardTasks> candidateTasksList = onboardTasksService.getOnBoardTasksListByCandidateOnBoardingId(onboardId);
		     tasksList.addAll(candidateTasksList);
		 
		     
		     if(candidateTasksList.size() == 0){
		    	 
			     CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingById(onboardId);
				 List<Long> templateIdsList = new ArrayList<Long>();
				 
				 if(!isNullOrEmpty(onboarding.getOnboardTemplateId())){
					 for (String s : onboarding.getOnboardTemplateId().split(",")){
						 templateIdsList.add(Long.parseLong(s));
					 }
					
					 templateTasksList = onboardTasksService.getOnboardTasksByTemplateIdList(templateIdsList);
					 if(null != templateTasksList){
						 tasksList.addAll(templateTasksList);
					 }
					 
				 }
		     }
	
			 List<OnboardTasks> onboardtasksList = new ArrayList<OnboardTasks>();
			 List<OnboardTasksDTO> onboardTaskDTOList=new ArrayList<>();
			 
			 for(OnboardTasks tasks : tasksList){
				 List<String> usersList = new ArrayList<String>();
					 
					 if(!isNullOrEmpty(tasks.getAssignToUsers())){
						 String[] userIds = tasks.getAssignToUsers().split(",");
						 
						 for(int i = 0 ; i < userIds.length; i++ ){
							 StringBuilder userName = new StringBuilder();
							 User user = userService.getUserById(Long.valueOf(userIds[i]));
							 String assignedUser = null;
							 if(null != user.getLastName()){
								 assignedUser = userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
								}else{
									assignedUser = userName.append(user.getFirstName()).toString();
								}
							 usersList.add(assignedUser);
						 }
					 }
					 
					 StringBuilder dueAfter = new StringBuilder();
					 
					 if(null != tasks.getDueAfterMonths()){
						 dueAfter = dueAfter.append(tasks.getDueAfterMonths()).append(" Month(s) ");
					 }
					 
					 if(null != tasks.getDueAfterDays()){
						 dueAfter = dueAfter.append(tasks.getDueAfterDays()).append(" Day(s) ");
					 }
					 
					 if(null != tasks.getDueAfterHours()){
						 dueAfter = dueAfter.append(tasks.getDueAfterHours()).append(" Hours ");
					 }
					 
					 if(null != tasks.getDueAfterMinutes()){
						 dueAfter = dueAfter.append(tasks.getDueAfterMinutes()).append(" Minutes");
					 }
					 
					
					 tasks.setDueAfterString(dueAfter.toString());
					 tasks.setUsersList(usersList); 
					 
					 if(null != tasks.getCandidateOnBoardingId()){
						 tasks.setOnboardTaskType("Candidate Task");
					 }else  if(null != tasks.getOnboardingTemplateId()){
						 tasks.setOnboardTaskType("Template Task");
					 }
					 
					 onboardTaskDTOList.add(getOnboardTasks(tasks));
			 }
				
			return onboardTaskDTOList;
			}
		 
		 public OnboardTasksDTO getOnboardTasks(OnboardTasks onboardTasks){
			 
			 OnboardTasksDTO onboardTasksDTO=new OnboardTasksDTO();
			 onboardTasksDTO.setId(onboardTasks.getId());
			 onboardTasksDTO.setTaskName(onboardTasks.getTaskName());
			 onboardTasksDTO.setPriority(onboardTasks.getPriority());
			 onboardTasksDTO.setTaskRepeat(onboardTasks.getTaskRepeat());
			 onboardTasksDTO.setDueAfterString(onboardTasks.getDueAfterString());
			 onboardTasksDTO.setUsersList(onboardTasks.getUsersList());
			 onboardTasksDTO.setOnboardTaskType(onboardTasks.getOnboardTaskType());
			 return onboardTasksDTO;
		 }
		 
		 
		 
		 @RequestMapping(value = "/viewCandidateOnboardTasks", method = RequestMethod.POST)
		 @ResponseBody String viewCandidateOnboardTasks(@RequestParam(value="onBoardingUUID", required=false) String onBoardingUUID,HttpServletRequest request) throws Exception{
			 List<OnboardTasks> templateTasksList = null;
			 List<OnboardTasks> tasksList = new ArrayList<OnboardTasks>();;
			 CandidateOnBoarding cnb = candidateOnBoardingService.getCandidateOnBoardingByUUID(onBoardingUUID);
		     List<OnboardTasks> candidateTasksList = onboardTasksService.getOnBoardTasksListByCandidateOnBoardingId(cnb.getId());
		     tasksList.addAll(candidateTasksList);
		 
			 CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingById(cnb.getId());
			 List<Long> templateIdsList = new ArrayList<Long>();
			 
			 if(!isNullOrEmpty(onboarding.getOnboardTemplateId())){
				 for (String s : onboarding.getOnboardTemplateId().split(",")){
					 templateIdsList.add(Long.parseLong(s));
				 }
				
				 templateTasksList = onboardTasksService.getOnboardTasksByTemplateIdList(templateIdsList);
				 if(null != templateTasksList){
					 tasksList.addAll(templateTasksList);
				 }
				 
			 }
			 List<OnboardTasks> onboardtasksList = new ArrayList<OnboardTasks>();
			 
			 for(OnboardTasks tasks : tasksList){
				 
				 List<String> usersList = new ArrayList<String>();
					 
					 if(!isNullOrEmpty(tasks.getAssignToUsers())){
						 String[] userIds = tasks.getAssignToUsers().split(",");
						 
						 for(int i = 0 ; i < userIds.length; i++ ){
							 StringBuilder userName = new StringBuilder();
							 User user = userService.getUserById(Long.valueOf(userIds[i]));
							 String assignedUser = null;
							 if(null != user.getLastName()){
								 assignedUser = userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
								}else{
									assignedUser = userName.append(user.getFirstName()).toString();
								}
							 usersList.add(assignedUser);
						 }
					 }
					 
					 StringBuilder dueAfter = new StringBuilder();
					 
					 if(null != tasks.getDueAfterMonths()){
						 dueAfter = dueAfter.append(tasks.getDueAfterMonths()).append(" Months ");
					 }
					 
					 if(null != tasks.getDueAfterDays()){
						 dueAfter = dueAfter.append(tasks.getDueAfterDays()).append(" Days ");
					 }
					 
					 if(null != tasks.getDueAfterHours()){
						 dueAfter = dueAfter.append(tasks.getDueAfterHours()).append(" Hours ");
					 }
					 
					 if(null != tasks.getDueAfterMinutes()){
						 dueAfter = dueAfter.append(tasks.getDueAfterMinutes()).append(" Minutes");
					 }
					
					 tasks.setDueAfterString(dueAfter.toString());
					 tasks.setUsersList(usersList); 
					 
					 if(null != tasks.getCandidateOnBoardingId()){
						 tasks.setOnboardTaskType("Candidate Task");
					 }else  if(null != tasks.getOnboardingTemplateId()){
						 tasks.setOnboardTaskType("Template Task");
					 }
					 
					 onboardtasksList.add(tasks);
			 }
				
			return commonUtil.toJSON(onboardtasksList);
			}
		 
		
		
		 
		 @RequestMapping(value = "/saveCandidateOnBoardTasks" , method = RequestMethod.POST)
		 public @ResponseBody  String saveCandidateOnBoardTasks(OnboardTasksDTO onboardTasksDTO,
				 @RequestParam(value="tasks", required=false) String tasks,HttpServletRequest request) throws Exception {
			 
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			 OnboardTasks onboardTasks = new OnboardTasks();
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskName())){
				 onboardTasks.setTaskName(onboardTasksDTO.getTaskName());
			 }
			 
			 CandidateOnBoarding onBoarding = new CandidateOnBoarding();
			 onBoarding.setId(Long.valueOf(onboardTasksDTO.getOnboardTaskId()));
			 onboardTasks.setCandidateOnBoardingId(onBoarding);
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getOnboardAssignedTo())){
				 onboardTasks.setAssignToUsers(onboardTasksDTO.getOnboardAssignedTo());
			 }else{
				 onboardTasks.setAssignToCandidate(Constants.YES);
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskPriority())){
				 onboardTasks.setPriority(onboardTasksDTO.getTaskPriority());
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskRepeat())){
				 onboardTasks.setTaskRepeat(onboardTasksDTO.getTaskRepeat());
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMonths())){
				 onboardTasks.setDueAfterMonths(Integer.parseInt(onboardTasksDTO.getTaskMonths()));
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskDays())){
				 onboardTasks.setDueAfterDays(Integer.parseInt(onboardTasksDTO.getTaskDays()));
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskHours())){
				 onboardTasks.setDueAfterHours(Integer.parseInt(onboardTasksDTO.getTaskHours()));
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskMinutes())){
				 onboardTasks.setDueAfterMinutes(Integer.parseInt(onboardTasksDTO.getTaskMinutes()));
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskNotification())){
				 onboardTasks.setTaskNotification(onboardTasksDTO.getTaskNotification());
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskEmail())){
				 onboardTasks.setTaskEmail(onboardTasksDTO.getTaskEmail());
			 }
			 
			 if(StringUtils.isNotBlank(onboardTasksDTO.getTaskdescription())){
				 onboardTasks.setDescription(onboardTasksDTO.getTaskdescription());
			 }
			 
			 onboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
			 onboardTasks.setCreatedDate(CommonUtil.currentDate());
			 onboardTasks.setCreatedBy(user.getUserCode());
			 onboardTasks.setUpdatedDate(CommonUtil.currentDate());
			 onboardTasks.setUpdatedBy(user.getUserCode());
			 
			 onboardTasksService.saveOnboardTasks(onboardTasks);
			 
			 
			 if(!isNullOrEmpty(tasks)){
				 String[] taksIds = tasks.split(",");
				 for(int i=0; i<taksIds.length; i++){
					 onboardTasks = onboardTasksService.getOnboardTasksByTaskId(Long.valueOf(taksIds[i]));
					 
					 OnboardTasks candidateTask = new OnboardTasks();
					 candidateTask.setTaskName(onboardTasks.getTaskName());
					 candidateTask.setCandidateOnBoardingId(onBoarding);
					 candidateTask.setAssignToUsers(onboardTasks.getAssignToUsers());
					 candidateTask.setAssignToCandidate(onboardTasks.getAssignToCandidate());
					 candidateTask.setPriority(onboardTasks.getPriority());
					 candidateTask.setTaskRepeat(onboardTasks.getTaskRepeat());
					 candidateTask.setDueAfterMonths(onboardTasks.getDueAfterMonths());
					 candidateTask.setDueAfterDays(onboardTasks.getDueAfterDays());
					 candidateTask.setDueAfterHours(onboardTasks.getDueAfterHours());
					 candidateTask.setDueAfterMinutes(onboardTasks.getDueAfterMinutes());
					 candidateTask.setTaskNotification(onboardTasks.getTaskNotification());
					 candidateTask.setTaskEmail(onboardTasks.getTaskEmail());
					 candidateTask.setDescription(onboardTasks.getDescription());
					 candidateTask.setStatus(Constants.CANDIDATE_ACTIVE);
					 candidateTask.setCreatedDate(CommonUtil.currentDate());
					 candidateTask.setCreatedBy(user.getUserCode());
					 candidateTask.setUpdatedDate(CommonUtil.currentDate());
					 candidateTask.setUpdatedBy(user.getUserCode());
					 onboardTasksService.saveOnboardTasks(candidateTask);
				 }
			 }
			
			  return "success";
		   }
		 
		 
		 @RequestMapping(value = "/deleteOnboardTaskByTaskId" , method = RequestMethod.POST)
		 public @ResponseBody  String deleteOnboardTaskByTaskId(@RequestParam(value="taskId", required=false) Long taskId,
				 HttpServletRequest request) throws Exception {
			
			OnboardTasks onboardTasks =	onboardTasksService.getOnboardTasksByTaskId(taskId);
		 	 if(null != onboardTasks){
		 		onboardTasks.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		 		onboardTasksService.saveOnboardTasks(onboardTasks);
		 	 }
			return "success";
		   
		}
		 
		 
		 @RequestMapping(value = "/saveAndFinishCandidateOnboard" , method = RequestMethod.POST)
		 public @ResponseBody  String saveAndFinishCandidateOnboard(@RequestParam(value="onboardId", required=false) Long onboardId,
				 @RequestParam(value="tasks", required=false) String tasks,
				 HttpServletRequest request) throws Exception {
			 
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 
			 if(!isNullOrEmpty(tasks)){
				 String[] taksIds = tasks.split(",");
				 for(int i=0; i<taksIds.length; i++){
					 OnboardTasks onboardTasks = onboardTasksService.getOnboardTasksByTaskId(Long.valueOf(taksIds[i]));
					 
					 OnboardTasks candidateTask = new OnboardTasks();
					 candidateTask.setTaskName(onboardTasks.getTaskName());
					 CandidateOnBoarding onboarding = new CandidateOnBoarding();
					 onboarding.setId(onboardId);
					 candidateTask.setCandidateOnBoardingId(onboarding);
					 candidateTask.setAssignToUsers(onboardTasks.getAssignToUsers());
					 candidateTask.setAssignToCandidate(onboardTasks.getAssignToCandidate());
					 candidateTask.setPriority(onboardTasks.getPriority());
					 candidateTask.setTaskRepeat(onboardTasks.getTaskRepeat());
					 candidateTask.setDueAfterMonths(onboardTasks.getDueAfterMonths());
					 candidateTask.setDueAfterDays(onboardTasks.getDueAfterDays());
					 candidateTask.setDueAfterHours(onboardTasks.getDueAfterHours());
					 candidateTask.setDueAfterMinutes(onboardTasks.getDueAfterMinutes());
					 candidateTask.setTaskNotification(onboardTasks.getTaskNotification());
					 candidateTask.setTaskEmail(onboardTasks.getTaskEmail());
					 candidateTask.setDescription(onboardTasks.getDescription());
					 candidateTask.setStatus(Constants.CANDIDATE_ACTIVE);
					 candidateTask.setCreatedDate(CommonUtil.currentDate());
					 candidateTask.setCreatedBy(user.getUserCode());
					 candidateTask.setUpdatedDate(CommonUtil.currentDate());
					 candidateTask.setUpdatedBy(user.getUserCode());
					 onboardTasksService.saveOnboardTasks(candidateTask);
				 }
			 }
			
			 CandidateOnBoarding onBoarding = candidateOnBoardingService.getCandidateOnBoardingById(onboardId);
			 
			 candidateOnBoardingService.createOnboardingUser(onBoarding,user,loginOrgLocation);
			
			return "success";
		   
		}
		 
		 
		 @RequestMapping(value = "/validateOnboardCandidate" , method = RequestMethod.POST)
		 public @ResponseBody  String validateOnboardCandidate(@RequestParam(value="email", required=false) String email,
				 HttpServletRequest request) throws Exception {
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = userService.findByEmailAndOrgLocationsId(email,loginOrgLocation.getId());
			if(null != user){
				return "Invalid";
			}else{
				return "success";
			}
		   
		}
		 
		 @RequestMapping(value = "/validateOnboardCandidateFromDashboard" , method = RequestMethod.POST)
		 public @ResponseBody  String validateOnboardCandidateFromDashboard(@RequestParam(value="candidateId", required=false) Long candidateId,
				 HttpServletRequest request) throws Exception {
			 
		    String result;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			CandidateOnBoarding onBoarding = candidateOnBoardingService.getCandidateOnBoardingByCandidateIdAndLocationId(candidateId, loginOrgLocation.getId());
			if(onBoarding != null){
				result = "Invalid";
			}else{
				result = "Success";
			}
		    return result;
		}
		 
		 
		 
		 @RequestMapping(value = "/validateOnboardCandidateEmail" , method = RequestMethod.POST)
		 public @ResponseBody  String validateOnboardCandidateEmail(@RequestParam(value="email", required=false) String email,
				 HttpServletRequest request) throws Exception {
			 	
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 	
			 String result;
			 	User user = userService.findByEmailAndOrgLocationsId(email,loginOrgLocation.getId());
			 	
			 	if (user != null) {
					result = "Invalid User Email";
				}else{
					
					CandidateOnBoarding onBoarding = candidateOnBoardingService.getCandidateOnBoardingByEmailAndLocationId(email, loginOrgLocation.getId());
					if(onBoarding != null){
						result = "Invalid OnboardUser Email";
					}else{
						result = "Success";
					}
				}
				return result;
		   }
		 
		
		 @RequestMapping(value = "/saveOnboardingCandidateStepDetails" , method = RequestMethod.POST)
		 public @ResponseBody  String saveOnboardingCandidateStepDetails(OnboardingCandidateSetp2DTO onboardingCandidateSetp2DTO, HttpServletRequest request) throws Exception {
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
			 
			 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getFirstName())){
				 user.setFirstName(onboardingCandidateSetp2DTO.getFirstName());
				 candidateOnBoarding.setFirstName(onboardingCandidateSetp2DTO.getFirstName());
			 }
			 
			 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getLastName())){
				 user.setLastName(onboardingCandidateSetp2DTO.getLastName());
				 candidateOnBoarding.setLastName(onboardingCandidateSetp2DTO.getLastName());
			 }else{
				 user.setLastName(null);
				 candidateOnBoarding.setLastName(null);
			 }
			 
			 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getCountry())){
				 user.setLocation(onboardingCandidateSetp2DTO.getCountry());
				 candidateOnBoarding.setCountry(onboardingCandidateSetp2DTO.getCountry());
			 }
			 
			 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getMobileNumber()) ){
				 user.setMobilePhone(onboardingCandidateSetp2DTO.getMobileNumber());
				 candidateOnBoarding.setMobileNumber(onboardingCandidateSetp2DTO.getMobileNumber());
			 }
			 
			 userService.addUser(user);
			 SourceLeadSessionManager.setSessionAttribute(request, "user", user);
			 
			 
			 Employees onboardingEmployee = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), loginOrgLocation.getId());
				if(null != onboardingEmployee){
					
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getCountry())){
						 onboardingEmployee.setCountry(onboardingCandidateSetp2DTO.getCountry());
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getState())){
						 onboardingEmployee.setState(onboardingCandidateSetp2DTO.getState());
					 }else{
						 onboardingEmployee.setState(null);
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getCity())){
						 onboardingEmployee.setCity(onboardingCandidateSetp2DTO.getCity());
					 }else{
						 onboardingEmployee.setCity(null);
					 }
					 
					 employeesService.addEmployee(onboardingEmployee);
				}
				
				
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getCountry())){
						 candidateOnBoarding.setCountry(onboardingCandidateSetp2DTO.getCountry());
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getLocation())){
						 candidateOnBoarding.setLocation(onboardingCandidateSetp2DTO.getLocation());
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getState())){
						 candidateOnBoarding.setState(onboardingCandidateSetp2DTO.getState());
					 }else{
						 candidateOnBoarding.setState(null);
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getCity())){
						 candidateOnBoarding.setCity(onboardingCandidateSetp2DTO.getCity());
					 }else{
						 candidateOnBoarding.setCity(null);
					 }
					 
					 if(StringUtils.isNotBlank(onboardingCandidateSetp2DTO.getZipCode())){
						 candidateOnBoarding.setZipCode(onboardingCandidateSetp2DTO.getZipCode());
					 }else{
						 candidateOnBoarding.setZipCode(null);
					 }
					 
					 candidateOnBoardingService.saveCandidateOnBoarding(candidateOnBoarding);
			 
			 
			  return "success";
		   }
		 
	 
		 @RequestMapping(value = "/getCandidateOnboardTemplateDocumentByonBoardingUUID" , method = RequestMethod.POST)
		 public @ResponseBody  String getCandidateOnboardTemplateDocumentByonBoardingUUID(@RequestParam(value="onBoardingUUID", required=false) String onBoardingUUID,
				 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
			
			String resultJson;
			List<OnBoardingTemplateDocuments> documentsList = null;
			List<OnBoardingTemplateDocuments> onboardingDocumentsList = new ArrayList<OnBoardingTemplateDocuments>();
			CandidateOnBoarding cnb = candidateOnBoardingService.getCandidateOnBoardingByUUID(onBoardingUUID);
			Integer documentsCount = onBoardingTemplateDocumentsService.getCandidateOnboardingDocumentsByCandidateOnboardingId(cnb.getId());
			
			if(documentsCount > 0){
				documentsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(cnb.getId(),documentType);
				if(null != documentsList && documentsList.size() > 0){
					for(OnBoardingTemplateDocuments  documents : documentsList ){
						documents.setOnboardDocumentType("Candidate Documents");
						onboardingDocumentsList.add(documents);
					}
				}
				
				
			}else{
				 
				CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingById(cnb.getId());
				 List<Long> templateIdsList = new ArrayList<Long>();
				 
				 if(!isNullOrEmpty(onboarding.getOnboardTemplateId())){
					 for (String s : onboarding.getOnboardTemplateId().split(",")){
						 templateIdsList.add(Long.parseLong(s));
					 }
					 
					documentsList = onBoardingTemplateDocumentsService.getCandidateOnBoardingTemplateDocumentsByTemplateId(templateIdsList,documentType);
					if(null != documentsList && documentsList.size() > 0){
						for(OnBoardingTemplateDocuments  documents : documentsList ){
							documents.setOnboardDocumentType("Template Documents");
							onboardingDocumentsList.add(documents);
						}
					}
				 }
			}
			
		 	resultJson = commonUtil.toJSON(onboardingDocumentsList);
			return resultJson;
		   
		}
		 
		 
		 
		 @RequestMapping(value = "/getOnboardingCandidatesList")
		 public String getOnboardingCandidatesList(HttpServletRequest request) throws Exception {

			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			List<RequirementSubmission> submissionList = requirementSubmissionService.getOfferAcceptedCandidatesListByOrgLocationId(loginOrgLocation.getId());
			List<RequirementSubmission> requirementSubmissionList = new ArrayList<RequirementSubmission>();
			//SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
			if(null != submissionList && submissionList.size() > 0){
				
				for(RequirementSubmission submission : submissionList){
					StringBuilder candidateName = new StringBuilder();
					if(!isNullOrEmpty(submission.getCandidate().getLastName())){
						submission.setCandidateName(candidateName.append(submission.getCandidate().getFirstName()).append(" ").append(submission.getCandidate().getLastName()).toString());
					}else{
						submission.setCandidateName(candidateName.append(submission.getCandidate().getFirstName()).toString());
					}
					
					submission.setCandidateEmail(submission.getCandidate().getEmail());
					submission.setRequirementName(submission.getRequirement().getJobTitle());
					submission.setCandidateUUID(submission.getCandidate().getCandidateUUID());
					submission.setRequirementUUID(submission.getRequirement().getRequirementUUID());
					
					RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(submission.getId());
					submission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementOffers.getDateOfJoining()));
					submission.setPositionType(requirementOffers.getPositionType());
					submission.setRate(requirementOffers.getAmount());
					submission.setRateType(requirementOffers.getPaymentMode());
					submission.setPaymentType(requirementOffers.getPaymentType());
					submission.setOfferUUID(requirementOffers.getOfferUUID());
					requirementSubmissionList.add(submission);
					
					CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByCandidateIdAndOfferId(submission.getCandidate().getId(), requirementOffers.getId());
					if(null != candidateOnBoarding){
						submission.setOnboardUUId(candidateOnBoarding.getOnboardCandidateUUID());
						submission.setOnboardStatus(candidateOnBoarding.getCandidateOnBoardStatus());
					}
					
				}
			}
			
			
			resultJson = commonUtil.toJSON(requirementSubmissionList);
			return resultJson;
		 }
		 
		 
		 @RequestMapping(value = "/changeTaskStatusForOnboardAndOffboardCandidate" , method = RequestMethod.POST)
		 public @ResponseBody  String changeTaskStatusForOnboardAndOffboardCandidate(@RequestParam(value="taskId", required=false) Long taskId,
				 HttpServletRequest request) throws Exception {
			 	
			 	Tasks tasks = taskService.getTasksById(taskId);
			 	
			 	if (null != tasks) {
					
			 		if(!isNullOrEmpty(tasks.getTaskStatus())){
			 			if(tasks.getTaskStatus().equalsIgnoreCase("Open")){
			 				tasks.setTaskStatus("Closed");
			 			}else{
			 				tasks.setTaskStatus("Open");
			 			}
			 			
			 			taskService.saveTasks(tasks);
			 		}
				}
			 	
				return "success";
		   }
		 
		 
		 
		 
		 
		 @RequestMapping(value = "/deleteOnboardCandidateUploadedDocument" , method = RequestMethod.POST)
		 public @ResponseBody  String deleteOnboardCandidateUploadedDocument(@RequestParam(value="documentUUID", required=false) String documentUUID,
				 HttpServletRequest request,HttpServletResponse response) throws Exception {
			 
			 
			 OnBoardingTemplateDocuments document = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
			 
			 if(null != document){
				 document.setUploadedDocFile(null);
				 document.setUploadedDocFileName(null);
				 document.setUploadedFileContentType(null);
				 document.setUploadFileURL(null);
				 document.setDocumentStatus(OnboardStatus.PENDING);
			 }
			
			 onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(document);
			 return "success";
			 
		   }
		 
		 
		 @RequestMapping(value = "/acceptCandidatePolicyDocument" , method = RequestMethod.POST)
		 public @ResponseBody  String acceptCandidatePolicyDocument(@RequestParam(value="documentUUID", required=false) String documentUUID,
				 HttpServletRequest request,HttpServletResponse response) throws Exception {
			 
			 OnBoardingTemplateDocuments document = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
			 if(document.getDocumentStatus().equals(OnboardStatus.NEW)){
				 document.setDocumentStatus(OnboardStatus.ACCEPTED);
			 }else{
				 document.setDocumentStatus(OnboardStatus.NEW);
			 }
			 
			 onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(document);
			 return "success";
			 
		   }
		 
		 
		 @RequestMapping(value = "/sendOnboardCandidateToDashboard" , method = RequestMethod.POST)
		 public @ResponseBody  String sendOnboardCandidateToDashboard(@RequestParam(value="taskId", required=false) Long taskId,
				 HttpServletRequest request,HttpServletResponse response) throws Exception {
			 
			 	
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
			 candidateOnBoarding.setOnboardSubmitted(Constants.YES);	
			 candidateOnBoarding.setCandidateOnBoardStatus("Submitted");
			 candidateOnBoardingService.saveCandidateOnBoarding(candidateOnBoarding);
			 
			 return "success";
			 
		   }
		 
		 
		 @RequestMapping(value = "/rejectOnboardedCandidate" , method = RequestMethod.POST)
		 public @ResponseBody  String rejectOnboardedCandidate(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
			 
			 
			 json = "{"+json+"}";
			 
			 JSONObject jsonObj = new JSONObject(json);
			 
			 if(jsonObj.has("onboardCandidateUUID") && !isNullOrEmpty(jsonObj.getString("onboardCandidateUUID"))){
				 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(jsonObj.getString("onboardCandidateUUID"));
				 candidateOnBoarding.setOnboardSubmitted(Constants.NO);	
				 candidateOnBoarding.setRejectedReason(jsonObj.getString("rejectedReason"));
				 candidateOnBoarding.setCandidateOnBoardStatus("Rejected");
				 candidateOnBoardingService.saveCandidateOnBoarding(candidateOnBoarding);
			 }
			 
			 return "success";
			 
		   }
		 
		 
		 @RequestMapping(value = "/acceptOnboardingCandiate" , method = RequestMethod.POST)
		 public @ResponseBody  String acceptOnboardingCandiate(@RequestParam(value="onboardCandidateUUID", required=false) String onboardCandidateUUID,HttpServletRequest request) throws Exception {
			 
				 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(onboardCandidateUUID);
				 candidateOnBoarding.setCandidateOnBoardStatus("Accepted");
				 candidateOnBoardingService.saveCandidateOnBoarding(candidateOnBoarding);
			 
			 return "success";
			 
		   }
		 
		 
		 @RequestMapping(value = "/viewCandidateOnboardTasksByOnboardingUUID" , method = RequestMethod.GET)
		 public @ResponseBody  String viewCandidateOnboardTasksByOnboardingUUID(@RequestParam(value="onBoardingUUID", required=false) String onBoardingUUID,@RequestParam(value="locationId", required=false) Long locationId,
				 HttpServletRequest request,HttpServletResponse response) throws Exception {
			 
			 
			 CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(onBoardingUUID);
			 OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
			 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
			 List<Tasks> tasksList = taskService.getTasksListOnboardingId(onboarding.getId());
				List<Tasks> taskList = new ArrayList<Tasks>();
				if(null != tasksList && tasksList.size() > 0){
					for(Tasks tasks : tasksList){
						tasks.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),tasks.getTaskDate()));
						
						StringBuilder userName = new StringBuilder();
						if(null != tasks.getAssignedUserId().getLastName()){
							tasks.setAssignedName(userName.append(tasks.getAssignedUserId().getFirstName()).append(" ").append(tasks.getAssignedUserId().getLastName()).toString());
						}else{
							tasks.setAssignedName(userName.append(tasks.getAssignedUserId().getFirstName()).toString());
						}
						taskList.add(tasks);
					}
					
				}
			 
				return commonUtil.toJSON(taskList);
			 
		   }
		 
		 
		 
}
