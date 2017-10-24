package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.dto.VendorOnboardTaskDTO;
import com.bis.sourcelead.dto.VendorOnboardingTemplateDocumentDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.service.VendorOnboardTasksService;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;
import com.bis.sourcelead.service.VendorOnboardTemplateService;
import com.bis.sourcelead.service.VendorService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class VendorOnboardRestController {
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	VendorOnboardTemplateService vendorOnboardTemplateService;
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardTemplateDocumentsService vendorOnboardTemplateDocumentsService;
	
	@Autowired
	VendorOnboardTasksService vendorOnboardTasksService;
	
	@Autowired
	UserService userService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value = { "/getVendorOnboardingTemplatesList", "/rest/getVendorOnboardingTemplatesList" }, method = RequestMethod.POST)
	public List<OnBoardingTemplateDTO> getVendorOnboardingTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<OnBoardingTemplateDTO> templateDTOList = new ArrayList<>();
		List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingTemplatesList(loginOrgLocation.getId());
		if(templatesList != null && templatesList.size() > 0) {
			for(VendorOnboardingTemplate template : templatesList) {
				OnBoardingTemplateDTO templateDTO = new OnBoardingTemplateDTO();
				templateDTO.setId(template.getId());
				templateDTO.setTemplateName(template.getTemplateName());
				templateDTO.setDescription(template.getDescription());
				templateDTO.setTemplateStatus(template.getTemplateStatus());
				templateDTO.setTemplateUUID(template.getTemplateUUID());
				templateDTO.setTemplateCreatedDate(template.getTemplateCreatedDate());
				templateDTO.setCreatedUser(template.getCreatedUser());
				templateDTO.setIsDefaultTemplate(template.getIsDefaultTemplate());
				
				Integer documentsCount = vendorOnboardTemplateDocumentsService.getTemplateDocumentsCountByTemplateId(template.getId());
				templateDTO.setDocumentsCount(documentsCount);
				
				Integer tasksCount = vendorOnboardTasksService.getTemplateTasksCountByTemplateId(template.getId());
				templateDTO.setTasksCount(tasksCount);
				
				templateDTOList.add(templateDTO);
			}
		}
		
		return templateDTOList;
	 }
	
	
	@RequestMapping(value = { "/createNewVendorOnBoardingTemplate", "/rest/createNewVendorOnBoardingTemplate" }, method = RequestMethod.POST)
	 public @ResponseBody  OnBoardingTemplateDTO createNewVendorOnBoardingTemplate(OnBoardingTemplateDTO onBoardingTemplateDTO,HttpServletRequest request) throws Exception {
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardTemplateDetails(onBoardingTemplateDTO, user, orgLocations);
	 	 OnBoardingTemplateDTO templateDTO = new OnBoardingTemplateDTO();
	 	 templateDTO.setId(onBoardingTemplate.getId());
	 	 return templateDTO;
	  
	}
	
	@RequestMapping(value = { "/getVendorOnboardTemplateById", "/rest/getVendorOnboardTemplateById" }, method = RequestMethod.POST)
	 public @ResponseBody OnBoardingTemplateDTO getVendorOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
		 OnBoardingTemplateDTO templateDTO = new OnBoardingTemplateDTO();
	 	 templateDTO.setTemplateName(onBoardingTemplate.getTemplateName());
	 	 templateDTO.setDescription(onBoardingTemplate.getDescription());
	 	 return templateDTO;
	   }
	
	
	
	@RequestMapping(value = { "/validateVendorOnboardingTemplateName", "/rest/validateVendorOnboardingTemplateName" }, method = RequestMethod.POST)
	@ResponseBody String validateVendorOnboardingTemplateName(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		VendorOnboardingTemplate onBoardingTemplate = null;
		String result = null;
		
		if(!isNullOrEmpty(templateId)){
			onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(Long.parseLong(templateId));
			if(onBoardingTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
				if(null != onBoardingTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			onBoardingTemplate  = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
			if(null != onBoardingTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value = { "/getVendorOnboardTemplateDocumentByTemplateId", "/rest/getVendorOnboardTemplateDocumentByTemplateId" }, method = RequestMethod.POST)
	 public @ResponseBody List<VendorOnboardingTemplateDocumentDTO> getVendorOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		
		List<VendorOnboardingTemplateDocumentDTO> documentsDTOList = new ArrayList<>();
		List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
		
		if(documentsList != null && documentsList.size() > 0) {
			for(VendorOnboardingTemplateDocuments documents : documentsList) {
				VendorOnboardingTemplateDocumentDTO docsDTO = new VendorOnboardingTemplateDocumentDTO();
				docsDTO.setId(documents.getId());
				docsDTO.setDocumentName(documents.getDocumentName());
				docsDTO.setIsRequired(documents.getIsRequired());
				docsDTO.setDocumentUUID(documents.getDocumentUUID());
				documentsDTOList.add(docsDTO);
			}
		}
		
		return documentsDTOList;
	}
	
	@RequestMapping(value = "/viewVendorOnboardTemplateByTemplateUUID")
 	public ModelAndView viewVendorOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			VendorOnboardingTemplate template = vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateUUID(templateUUID);
 			
 			model.setViewName("vendors/addNewVendorOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("templateId", template.getId());
 			return model;
 	}
	
	@RequestMapping(value = "/saveVendorOnboardingTemplateDocuments", method = RequestMethod.POST)
	public @ResponseBody String saveVendorOnboardingTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(Validator.validate(onBoardingTemplateDTO)) {
			onBoardingTemplateDTO.setStaticPath(staticPath);
			vendorOnboardTemplateDocumentsService.storeVendorOnBoardTemplateDocuments(onBoardingTemplateDTO, loginUser);
		 }else
			 return "DataMissing";
		
		
		return "success";
			
	}
	
	@RequestMapping(value = "/deleteVendorTemplateDocumentByDocumentUUID" , method = RequestMethod.POST)
	 public @ResponseBody  String deleteVendorTemplateDocumentByDocumentUUID(@RequestParam(value="documentUUID", required=false) String documentUUID,
			 HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplateDocuments	templateDocuments =	vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	 	 if(templateDocuments != null){
	 		templateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	 		vendorOnboardTemplateDocumentsService.saveVendorOnBoardingTemplateDocuments(templateDocuments);
	 	 }
		return "success";
	   
	}
	
	
	
	@RequestMapping(value = "/deleteVendorOnboardTemplateByTemplateUUID" , method = RequestMethod.POST)
	 public @ResponseBody  String deleteVendorOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
			 HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplate template =	vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateUUID(templateUUID);
	 	 if(template != null){
	 		template.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	 		vendorOnboardTemplateService.saveVendorOnBoardingTemplate(template);
	 	 }
		return "success";
	   
	}
	
	
	
	@RequestMapping(value = "/requiredOrNotTemplateDocumentByDocumentUUID" , method = RequestMethod.POST)
	 public @ResponseBody  String requiredOrNotTemplateDocumentByDocumentUUID(@RequestParam(value="documentUUID", required=false) String documentUUID,
			 HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplateDocuments templateDocuments = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	 	 if(templateDocuments != null){
	 		if(templateDocuments.getIsRequired().equalsIgnoreCase(Constants.YES)){
	 			templateDocuments.setIsRequired(Constants.NO);
	 		}else{
	 			templateDocuments.setIsRequired(Constants.YES);
	 		}
	 		vendorOnboardTemplateDocumentsService.saveVendorOnBoardingTemplateDocuments(templateDocuments);
	 	 }
		return "success";
	   
	}
	
	
	 @RequestMapping(value = { "/getVendorOnboardTemplateTasksListByTemplateId", "/rest/getVendorOnboardTemplateTasksListByTemplateId" }, method = RequestMethod.GET)
	 @ResponseBody List<VendorOnboardTaskDTO> getVendorOnboardTemplateTasksListByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		
		 List<VendorOnboardTasks> tasksList = vendorOnboardTasksService.getVendorOnboardTasksByTemplateId(templateId);
		 List<VendorOnboardTaskDTO> tasksDTOList = new ArrayList<>();
		 
		 for(VendorOnboardTasks tasks : tasksList){
			 
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
				 
				 VendorOnboardTaskDTO taskDTO = new  VendorOnboardTaskDTO();
				 taskDTO.setId(tasks.getId());
				 taskDTO.setPriority(tasks.getPriority());
				 taskDTO.setTaskRepeat(tasks.getTaskRepeat());
				 taskDTO.setTaskName(tasks.getTaskName());
				 taskDTO.setDueAfterString(tasks.getDueAfterString());
				 taskDTO.setUsersList(usersList); 
				 tasksDTOList.add(taskDTO);
		 }
		 
		 return tasksDTOList;
			
		}
	 
	 
	 
	 @RequestMapping(value = "/saveVendorOnBoardTemplateTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveVendorOnBoardTemplateTasks(OnboardTasksDTO onboardTasksDTO, HttpServletRequest request) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		 vendorOnboardTasksService.saveVendorOnboardTemplateTask(onboardTasksDTO, user);
		 return "success";
	   }
	 
	 
	 @RequestMapping(value = "/deleteVendorOnboardTaskByTaskId" , method = RequestMethod.POST)
	 public @ResponseBody  String deleteVendorOnboardTaskByTaskId(@RequestParam(value="taskId", required=false) Long taskId,
			 HttpServletRequest request) throws Exception {
		
		 VendorOnboardTasks onboardTasks =	vendorOnboardTasksService.getVendorOnboardTasksByTaskId(taskId);
	 	 if(null != onboardTasks){
	 		onboardTasks.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	 		vendorOnboardTasksService.saveVendorOnboardTasks(onboardTasks);
	 	 }
		return "success";
	   
	}
	 
	 @RequestMapping(value = "/saveAndFinishVendorOnboardTemplate", method = RequestMethod.POST)
	 @ResponseBody String saveAndFinishVendorOnboardTemplate(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		
		VendorOnboardingTemplate template = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
		if(null != template){
			template.setTemplateStatus("Finished");
			vendorOnboardTemplateService.saveVendorOnBoardingTemplate(template);
		}
		return "success";
		
	 }
	 
	 
	 
	 @RequestMapping(value = { "/getVendorOnboardDetailsByVendorOnboardId", "/rest/getVendorOnboardDetailsByVendorOnboardId" }, method = RequestMethod.POST)
	 @ResponseBody VendorOnboardDTO getVendorOnboardDetailsByVendorOnboardId(@RequestParam(value="vendorOnboardId", required=true) Long vendorOnboardId,
			 @RequestParam(value="notificationUUID", required=false) String notificationUUID,HttpServletRequest request) throws Exception{
		
	     VendorOnboardDTO vendorOnboardDTO = vendorService.getVendorOnboardDetails(vendorOnboardId,notificationUUID);
		 return vendorOnboardDTO;
		
	 }
	 
	 @RequestMapping(value = "/acceptAndSaveVendorOnboard", method = RequestMethod.POST)
	 @ResponseBody String acceptAndSaveVendorOnboard(VendorOnboardDTO vendorOnboardDTO,@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId,HttpServletRequest request) throws Exception{
		 
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 vendorOnboardService.saveVendorOnboardDetails(vendorOnboardDTO, vendorOnboardId,user);
		
		 return "success";
		
	 }
	 
	 
	 @RequestMapping(value = { "/getVendorOnboardDocumentByVendorOnboardId", "/rest/getVendorOnboardDocumentByVendorOnboardId" }, method = RequestMethod.GET)
	 @ResponseBody List<VendorOnboardingTemplateDocumentDTO> getVendorOnboardDocumentByVendorOnboardId(@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId,HttpServletRequest request) throws Exception{
		 
		    List<VendorOnboardingTemplateDocumentDTO> documentsDTOList = new ArrayList<>();
			List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnboaringTemplateDocumentsByVendorOnboardId(vendorOnboardId);
		   
			if(documentsList != null && documentsList.size() > 0) {
				for(VendorOnboardingTemplateDocuments documents : documentsList) {
					VendorOnboardingTemplateDocumentDTO docsDTO = new VendorOnboardingTemplateDocumentDTO();
					docsDTO.setId(documents.getId());
					docsDTO.setDocumentName(documents.getDocumentName());
					docsDTO.setIsRequired(documents.getIsRequired());
					docsDTO.setUploadedFileUrl(documents.getUploadedFileUrl());
					docsDTO.setUploadedFileName(documents.getUploadedFileName());
					documentsDTOList.add(docsDTO);
				}
			}
			
			return documentsDTOList;
		
	 }
	 
	 
	 
	 
	 @RequestMapping(value = { "/getVendorOnboardDocumentByOrgRelationId", "/rest/getVendorOnboardDocumentByOrgRelationId" }, method = RequestMethod.GET)
	 @ResponseBody List<VendorOnboardingTemplateDocumentDTO> getVendorOnboardDocumentByOrgRelationId(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,HttpServletRequest request) throws Exception{
		 
		    List<VendorOnboardingTemplateDocumentDTO> documentsDTOList = new ArrayList<>();
		    VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByOrgRelationId(orgRelationId);
			List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnboaringTemplateDocumentsByVendorOnboardId(vendorOnboarding.getId());
		    if(documentsList != null && documentsList.size() > 0) {
		    	for(VendorOnboardingTemplateDocuments documents : documentsList) {
		    		VendorOnboardingTemplateDocumentDTO docsDTO = new VendorOnboardingTemplateDocumentDTO();
					docsDTO.setDocumentName(documents.getDocumentName());
					docsDTO.setUploadedFileUrl(documents.getUploadedFileUrl());
					docsDTO.setUploadedFileName(documents.getUploadedFileName());
					documentsDTOList.add(docsDTO);
		    	}
		    }
			
			return documentsDTOList;
		
	 }
	 
	 
		@RequestMapping(value = "/saveVendorOnboardSubmittedDocuments", method = RequestMethod.POST)
		public @ResponseBody String saveVendorOnboardSubmittedDocuments(VendorOnboardDTO vendorOnboardDTO, HttpServletRequest request) throws Exception {
			if(Validator.validate(vendorOnboardDTO)) {
				vendorOnboardDTO.setStaticPath(staticPath);
				vendorOnboardService.saveVendorOnboardDocuments(vendorOnboardDTO);
			 }else
				 return "dataMissing";
			
			return "success";
				
		}
		
		
		@RequestMapping(value = { "/deleteVendorUploadedOnboardDocument", "/rest/deleteVendorUploadedOnboardDocument" }, method = RequestMethod.POST)
		public @ResponseBody String deleteVendorUploadedOnboardDocument(@RequestParam(value="documentId", required=false) Long documentId) throws Exception {
			
			VendorOnboardingTemplateDocuments documents = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsById(documentId);
			documents.setUploadedFileContentType(null);
			documents.setUploadedFileContentType(null);
			documents.setUploadedFileUrl(null);
			documents.setDocumentStatus(null);
			vendorOnboardTemplateDocumentsService.saveVendorOnBoardingTemplateDocuments(documents);
			return "success";
				
		}
		
		@RequestMapping(value = { "/getVendorOnboardTasksByVendorOnboardId", "/rest/getVendorOnboardTasksByVendorOnboardId" }, method = RequestMethod.GET)
		public @ResponseBody List<TaskDTO> getVendorOnboardTasksByVendorOnboardId(@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId,HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<TaskDTO> tasksListDTO = new ArrayList<>();
			
			List<Tasks> tasksList = taskService.getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(orgLocations.getId(), user.getId(), vendorOnboardId);
			if(tasksList != null && tasksList.size() > 0) {
				for(Tasks tasks : tasksList) {
					TaskDTO taskDTO = new TaskDTO();
					taskDTO.setId(tasks.getId());
					taskDTO.setSubject(tasks.getSubject());
					taskDTO.setPriority(tasks.getPriority());
					taskDTO.setTaskRepeat(tasks.getTaskRepeat());
					taskDTO.setVendorTaskDateStr(tasks.getVendorTaskDateStr());
					taskDTO.setTaskStatus(tasks.getTaskStatus());
					tasksListDTO.add(taskDTO);
				}
				
			}
			
			
			return tasksListDTO;
		}
		
		
		@RequestMapping(value = { "/getVendorSubmittedOnboardTasksByOrgRelationId", "/rest/getVendorSubmittedOnboardTasksByOrgRelationId" }, method = RequestMethod.GET)
		public @ResponseBody List<TaskDTO> getVendorSubmittedOnboardTasksByOrgRelationId(@RequestParam(value="orgRelationId", required=true) Long orgRelationId,HttpServletRequest request) throws Exception {
			
			List<TaskDTO> tasksListDTO = new ArrayList<>();
			VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByOrgRelationId(orgRelationId);
			OrgLocations vendorOrgLocations = vendorOnboarding.getVendorLocationId();
			User vendorUser = vendorOnboarding.getSpocUserId();
			List<Tasks> tasksList = taskService.getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(vendorOrgLocations.getId(), vendorUser.getId(), vendorOnboarding.getId());
			if(tasksList != null && tasksList.size() > 0) {
				for(Tasks tasks : tasksList) {
					TaskDTO taskDTO = new TaskDTO();
					taskDTO.setId(tasks.getId());
					taskDTO.setSubject(tasks.getSubject());
					taskDTO.setPriority(tasks.getPriority());
					taskDTO.setTaskRepeat(tasks.getTaskRepeat());
					taskDTO.setVendorTaskDateStr(tasks.getVendorTaskDateStr());
					taskDTO.setAssignedName(tasks.getAssignedName());
					taskDTO.setTaskStatus(tasks.getTaskStatus());
					tasksListDTO.add(taskDTO);
				}
				
			}
			return tasksListDTO;
		}
		
		
		
		@RequestMapping(value = "/changeVendorOnboardTaskStatus", method = RequestMethod.POST)
		public @ResponseBody String changeVendorOnboardTaskStatus(@RequestParam(value="taskId", required=false) Long taskId) throws Exception {
			
			Tasks tasks = taskService.getTasksById(taskId);
			if(tasks.getTaskStatus().equalsIgnoreCase(Constants.TASK_STATUS_OPEN)){
				tasks.setTaskStatus(Constants.TASK_STATUS_CLOSE);
			}else{
				tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
			}
			
			taskService.saveTasks(tasks);
			
			return "success";
				
		}
		
		
		
		@RequestMapping(value = "/finishVendorOnboardProcess", method = RequestMethod.POST)
		public @ResponseBody String finishVendorOnboardProcess(@RequestParam(value="vendorOnboardId", required=true) Long vendorOnboardId,
				@RequestParam(value="notificationUUID", required=true) String notificationUUID,HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			vendorOnboardService.finishVendorOnboardProcess(vendorOnboardId, notificationUUID, user,Constants.MAIL_ACCEPTED);
			
			return "success";
				
		}
		
		
	
}
