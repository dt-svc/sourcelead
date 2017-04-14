package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.service.OnboardTasksService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CandidateOnBoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
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
	
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/getOnboardingTemplatesList")
	 public String getOnboardingTemplatesList(HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<OnBoardingTemplate> templatesList = onBoardingTemplateService.getOnBoardingTemplatesList(loginOrgLocation.getId());
		List<OnBoardingTemplate> onboardintTemplatesList = new ArrayList<OnBoardingTemplate>();
		
		for(OnBoardingTemplate template : templatesList){
			template.setTemplateCreatedDate(Constants.DMY.format(template.getCreatedDate()));
			onboardintTemplatesList.add(template);
		}
		
		resultJson = commonUtil.toJSON(onboardintTemplatesList);
		return resultJson;
	 }
	
	
	
	@RequestMapping(value = "/createNewOnBoardingTemplate" , method = RequestMethod.POST)
	 public @ResponseBody  String createNewOnBoardingTemplate(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
		 String resultJson;
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 
		 OnBoardingTemplate onBoardingTemplate = null;
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 
	 	 if(jsonObj.has("templateId") && !isNullOrEmpty(jsonObj.getString("templateId"))){
	 		onBoardingTemplate = onBoardingTemplateService.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
	 		onBoardingTemplate.setTemplateName(jsonObj.getString("templateName"));
		 	onBoardingTemplate.setDescription(jsonObj.getString("description"));
		 	onBoardingTemplate.setUpdatedBy(user.getUserCode());
		 	onBoardingTemplate.setUpdatedDate(new Date());
	 	 
	 	 }else{
	 		 onBoardingTemplate = new OnBoardingTemplate();
		 	 onBoardingTemplate.setTemplateName(jsonObj.getString("templateName"));
		 	 onBoardingTemplate.setDescription(jsonObj.getString("description"));
		 	 onBoardingTemplate.setOrgLocationId(orgLocations);
		 	
		 	 StringBuilder userName = new StringBuilder();
			if(!isNullOrEmpty(user.getLastName())){
				onBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}else{
				onBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).toString());
			}
			onBoardingTemplate.setTemplateStatus("Draft");
			onBoardingTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			onBoardingTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		 	onBoardingTemplate.setCreatedBy(user.getUserCode());
		 	onBoardingTemplate.setCreatedDate(new Date());
		 	onBoardingTemplate.setUpdatedBy(user.getUserCode());
		 	onBoardingTemplate.setUpdatedDate(new Date());
	 	 }
	 	 
	 	
	 	onBoardingTemplate = onBoardingTemplateService.saveOnBoardingTemplate(onBoardingTemplate);
		
	 	resultJson = commonUtil.toJSON(Long.valueOf(onBoardingTemplate.getId()));
		return resultJson;
	   }
	
	
	
	@RequestMapping(value = "/getOnboardTemplateById" , method = RequestMethod.POST)
	 public @ResponseBody  String getOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		
		String resultJson;
	 	 
 	    OnBoardingTemplate onBoardingTemplate = onBoardingTemplateService.getOnBoardingTemplateById(templateId);
		
	 	resultJson = commonUtil.toJSON(onBoardingTemplate);
		return resultJson;
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
	 public @ResponseBody  String getOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		
		String resultJson;
		
		List<OnBoardingTemplateDocuments> documentsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
	 	 
	 	resultJson = commonUtil.toJSON(documentsList);
		return resultJson;
	   
	}
	
	
	
	
	
	
	@RequestMapping(value = "/saveOnboardingTemplateDocuments", method = RequestMethod.POST)
	public @ResponseBody String saveOnboardingTemplateDocuments(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
			MultipartHttpServletRequest request) throws Exception {
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		
		//save  mutliple attachment for policies Documents.
		if ((jsonObj.has("policiesAttcahmentMaxRowNumber"))){
	    	String policiesAttcahmentMaxRowNumber = jsonObj.getString("policiesAttcahmentMaxRowNumber");
			int policiesAttcahmentMaxRowNumberCount = Integer.parseInt(policiesAttcahmentMaxRowNumber);
			
			OnBoardingTemplate template = onBoardingTemplateService.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
			
	    	for (int a = 0; a < policiesAttcahmentMaxRowNumberCount; a++) {
				   
				   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
				   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
       		       final Map files = multiRequest.getFileMap();
       		       
       		       documents.setOnboardingTemplateId(template);
                   MultipartFile  multiFile = (MultipartFile)files.get(a+"docFile");
                   if(multiFile != null && !multiFile.isEmpty()){
                	   documents.setDocFile(multiFile.getBytes());
                	   documents.setFileName(multiFile.getOriginalFilename());
                       
                       if(multiFile.getContentType()!=null){
                    	   documents.setFileContentType(multiFile.getContentType());
                       }
                       documents.setDocumentType("Policies");
                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                       documents.setCreatedDate(CommonUtil.currentDate());
                       documents.setCreatedBy(loginUser.getUserCode());
                       documents.setUpdatedDate(CommonUtil.currentDate());
                       documents.setUpdatedBy(loginUser.getUserCode());
                       onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
					   
                   }
                   
                 
	    	}
		}
		
		
		
		
		
		
		//save  mutliple attachment for supported Documents.
				if ((jsonObj.has("supportingAttcahmentMaxRowNumber"))){
			    	String supportingAttcahmentMaxRowNumber = jsonObj.getString("supportingAttcahmentMaxRowNumber");
					int supportedAttcahmentMaxRowNumberCount = Integer.parseInt(supportingAttcahmentMaxRowNumber);
					
					OnBoardingTemplate template = onBoardingTemplateService.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
					
			    	for (int a = 0; a < supportedAttcahmentMaxRowNumberCount; a++) {
						   
						   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		       		       final Map files = multiRequest.getFileMap();
		       		       
		       		       documents.setOnboardingTemplateId(template);
		                   MultipartFile  multiFile = (MultipartFile)files.get(a+"supportingdocFile");
		                   if(multiFile != null && !multiFile.isEmpty()){
		                	   documents.setDocFile(multiFile.getBytes());
		                	   documents.setFileName(multiFile.getOriginalFilename());
		                       
		                       if(multiFile.getContentType()!=null){
		                    	   documents.setFileContentType(multiFile.getContentType());
		                       }
		                       documents.setDocumentType("Supported");
		                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                       documents.setCreatedDate(CommonUtil.currentDate());
		                       documents.setCreatedBy(loginUser.getUserCode());
		                       documents.setUpdatedDate(CommonUtil.currentDate());
		                       documents.setUpdatedBy(loginUser.getUserCode());
		                       onBoardingTemplateDocumentsService.saveOnBoardingTemplateDocuments(documents);
							   
		                   }
		                   
		                 
			    	}
				}
		
		return "success";
			
	}
	
	
	
	@RequestMapping(value = "/downloadTemplateDocument", method = RequestMethod.GET)
    public void downloadTemplateDocument(@RequestParam(value="documentUUID", required=false) String documentUUID ,HttpServletResponse response) throws Exception {
		
		
			OnBoardingTemplateDocuments	templateDocuments =	onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);	
		    
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(templateDocuments.getFileName()).append("\"").toString());
			InputStream inputStream = new ByteArrayInputStream(templateDocuments.getDocFile());
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
	 @ResponseBody String getOnboardTasksAssignToUsersList(HttpServletRequest request) throws Exception{
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 List<UserRole> userRoleList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
			for(UserRole ur :userRoleList){
				StringBuilder userName = new StringBuilder();
				ur.setUserId(ur.getUser().getId());
				if(null != ur.getUser()){
					if(null != ur.getUser().getLastName()){
						ur.setUserName(userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
					}else{
						ur.setUserName(userName.append(ur.getUser().getFirstName()).toString());
					}
				}
			}
			
		return commonUtil.toJSON(userRoleList);
		}
	 
	 
	 
	 @RequestMapping(value = "/saveOnBoardTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveOnBoardTasks(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		 
		 OnboardTasks onboardTasks = new OnboardTasks();
		 
		 
		 if(jsonObj.has("taskName") && !isNullOrEmpty(jsonObj.getString("taskName"))){
			 onboardTasks.setTaskName(jsonObj.getString("taskName"));
		 }
		 
		 OnBoardingTemplate template = new OnBoardingTemplate();
		 template.setId(Long.valueOf(jsonObj.getString("templateId")));
		 onboardTasks.setOnboardingTemplateId(template);
		 
		 if(jsonObj.has("onboardAssignedTo") && !isNullOrEmpty(jsonObj.getString("onboardAssignedTo"))){
			
			 String userIds = jsonObj.getString("onboardAssignedTo");
			 
			 userIds = userIds.replaceAll("\\[", "").replaceAll("\\]","");
			 userIds = userIds.replaceAll("\"", "");
			 onboardTasks.setAssignToUsers(userIds);
		 }else{
			 onboardTasks.setAssignToCandidate(Constants.YES);
		 }
		 
		 if(jsonObj.has("taskPriority") && !isNullOrEmpty(jsonObj.getString("taskPriority"))){
			 onboardTasks.setPriority(jsonObj.getString("taskPriority"));
		 }
		 
		 if(jsonObj.has("taskRepeat") && !isNullOrEmpty(jsonObj.getString("taskRepeat"))){
			 onboardTasks.setTaskRepeat(jsonObj.getString("taskRepeat"));
		 }
		 
		 if(jsonObj.has("taskMonths") && !isNullOrEmpty(jsonObj.getString("taskMonths"))){
			 onboardTasks.setDueAfterMonths(Integer.parseInt(jsonObj.getString("taskMonths")));
		 }
		 
		 if(jsonObj.has("taskDays") && !isNullOrEmpty(jsonObj.getString("taskDays"))){
			 onboardTasks.setDueAfterDays(Integer.parseInt(jsonObj.getString("taskDays")));
		 }
		 
		 if(jsonObj.has("taskHours") && !isNullOrEmpty(jsonObj.getString("taskHours"))){
			 onboardTasks.setDueAfterHours(Integer.parseInt(jsonObj.getString("taskHours")));
		 }
		 
		 if(jsonObj.has("taskMinutes") && !isNullOrEmpty(jsonObj.getString("taskMinutes"))){
			 onboardTasks.setDueAfterMinutes(Integer.parseInt(jsonObj.getString("taskMinutes")));
		 }
		 
		 if(jsonObj.has("taskNotification") && !isNullOrEmpty(jsonObj.getString("taskNotification"))){
			 onboardTasks.setTaskNotification(jsonObj.getString("taskNotification"));
		 }
		 
		 if(jsonObj.has("taskEmail") && !isNullOrEmpty(jsonObj.getString("taskEmail"))){
			 onboardTasks.setTaskEmail(jsonObj.getString("taskEmail"));
		 }
		 
		 if(jsonObj.has("taskdescription") && !isNullOrEmpty(jsonObj.getString("taskdescription"))){
			 onboardTasks.setDescription(jsonObj.getString("taskdescription"));
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
				HttpServletRequest request) throws Exception {

			RequirementOffers offer = requirementOffersService.getRequirementOfferId(offerId);
			if(null != offer.getDateOfJoining()){
				offer.setDateOfJoiningStr(Constants.DMY.format(offer.getDateOfJoining()));
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
	 public @ResponseBody  String saveOnboardingCandidateDetails(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 
		 String resultJson;
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 
		 CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.saveOnBoardingCandidatesDetails(jsonObj, loginOrgLocation, user);
		 	
	 	resultJson = commonUtil.toJSON(Long.valueOf(candidateOnBoarding.getId()));
		return resultJson;
	   }
	 
	 
	 @RequestMapping(value = "/getOnboardCandidateList")
	 public String getOnboardCandidateList(HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<CandidateOnBoarding> candidateOnBoardingList = candidateOnBoardingService.getCandidateOnBoardingListByOrgLocationId(loginOrgLocation.getId());
		List<CandidateOnBoarding> onBoardingList = new ArrayList<CandidateOnBoarding>();
		
		if(null != candidateOnBoardingList && candidateOnBoardingList.size() > 0){
			for(CandidateOnBoarding candidateOnBoarding : candidateOnBoardingList){
				if(null != candidateOnBoarding.getStartDate()){
					candidateOnBoarding.setJoiningDateStr(Constants.DMY.format(candidateOnBoarding.getStartDate()));
				}
				
				StringBuilder candidateName = new StringBuilder();
				
				if(null != candidateOnBoarding.getLastName()){
					candidateOnBoarding.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).append(" ").append(candidateOnBoarding.getLastName()).toString());
				}else{
					candidateOnBoarding.setCandidateName(candidateName.append(candidateOnBoarding.getFirstName()).toString());
				}
				
				onBoardingList.add(candidateOnBoarding);
				
			}
		}
		
		resultJson = commonUtil.toJSON(onBoardingList);
		
		return resultJson;
	 }
	 
	 
	 
	 
	 
	 
	 @RequestMapping(value = "/loadOnboardingCandidateDetails")
	 public String loadOnboardingCandidateDetails(@RequestParam(value="onBoardingUUID", required=false) String onBoardingUUID,HttpServletRequest request) throws Exception {

		String resultJson;
		
		CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUUID(onBoardingUUID);
		if(null != candidateOnBoarding){
			candidateOnBoarding.setOnBoardCandidateId(candidateOnBoarding.getCandidateId().getId());
			candidateOnBoarding.setOnBoardOfferId(candidateOnBoarding.getOfferId().getId());
			candidateOnBoarding.setOnBoardRoleId(candidateOnBoarding.getRoleId().getId());
			
			if(null != candidateOnBoarding.getManagerUserId()){
				candidateOnBoarding.setOnBoardManagerId(candidateOnBoarding.getManagerUserId().getId());
			}
			
			if(null != candidateOnBoarding.getTeamId()){
				candidateOnBoarding.setOnBoardTeamId(candidateOnBoarding.getTeamId().getId());
			}
			
			
			candidateOnBoarding.setJoiningDateStr(Constants.DMY.format(candidateOnBoarding.getStartDate()));
			
			if(null != candidateOnBoarding.getEndDate()){
				candidateOnBoarding.setEndDateStr(Constants.DMY.format(candidateOnBoarding.getEndDate()));
			}
			
			if(null != candidateOnBoarding.getDueDate()){
				candidateOnBoarding.setDueDateStr(Constants.DMY.format(candidateOnBoarding.getDueDate()));
			}
			
		}
		
		resultJson = commonUtil.toJSON(candidateOnBoarding);
		
		return resultJson;
	 }
	 
	 
	 
	 
	 
	
	 
}
