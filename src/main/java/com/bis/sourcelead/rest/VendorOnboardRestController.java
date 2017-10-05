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
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.entity.LookUpData;
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
	
	@RequestMapping(value = "/getVendorOnboardingTemplatesList")
	public List<VendorOnboardingTemplate> getVendorOnboardingTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingTemplatesList(loginOrgLocation.getId());
		return templatesList;
	 }
	
	
	@RequestMapping(value = "/createNewVendorOnBoardingTemplate" , method = RequestMethod.POST)
	 public @ResponseBody  VendorOnboardingTemplate createNewVendorOnBoardingTemplate(OnBoardingTemplateDTO onBoardingTemplateDTO,HttpServletRequest request) throws Exception {
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardTemplateDetails(onBoardingTemplateDTO, user, orgLocations);
		 return onBoardingTemplate;
	  
	}
	
	@RequestMapping(value = "/getVendorOnboardTemplateById" , method = RequestMethod.POST)
	 public @ResponseBody VendorOnboardingTemplate getVendorOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		
		VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
		return onBoardingTemplate;
	   }
	
	
	
	@RequestMapping(value = "/validateVendorOnboardingTemplateName", method = RequestMethod.POST)
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
	
	
	@RequestMapping(value = "/getVendorOnboardTemplateDocumentByTemplateId" , method = RequestMethod.POST)
	 public @ResponseBody List<VendorOnboardingTemplateDocuments> getVendorOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		
		List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
		return documentsList;
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
	
	
	 @RequestMapping(value = "/getVendorOnboardTemplateTasksListByTemplateId", method = RequestMethod.GET)
	 @ResponseBody List<VendorOnboardTasks> getVendorOnboardTemplateTasksListByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		
		 List<VendorOnboardTasks> tasksList = vendorOnboardTasksService.getVendorOnboardTasksByTemplateId(templateId);
		 
		 List<VendorOnboardTasks> vendorOnboardtasksList = new ArrayList<VendorOnboardTasks>();
		 
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
				 
				 tasks.setUsersList(usersList); 
				 vendorOnboardtasksList.add(tasks);
		 }
		 
		 return vendorOnboardtasksList;
			
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
	 
	 
	 
	 @RequestMapping(value = "/getVendorOnboardDetailsByVendorOnboardId", method = RequestMethod.POST)
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
	 
	 
	 @RequestMapping(value = "/getVendorOnboardDocumentByVendorOnboardId", method = RequestMethod.GET)
	 @ResponseBody List<VendorOnboardingTemplateDocuments> getVendorOnboardDocumentByVendorOnboardId(@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId,HttpServletRequest request) throws Exception{
		 
			List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnboaringTemplateDocumentsByVendorOnboardId(vendorOnboardId);
		    return documentsList;
		
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
		
		
		@RequestMapping(value = "/deleteVendorUploadedOnboardDocument", method = RequestMethod.POST)
		public @ResponseBody String deleteVendorUploadedOnboardDocument(@RequestParam(value="documentId", required=false) Long documentId) throws Exception {
			
			VendorOnboardingTemplateDocuments documents = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsById(documentId);
			documents.setUploadedFileContentType(null);
			documents.setUploadedFileContentType(null);
			documents.setUploadedFileUrl(null);
			documents.setDocumentStatus(null);
			vendorOnboardTemplateDocumentsService.saveVendorOnBoardingTemplateDocuments(documents);
			return "success";
				
		}
		
		@RequestMapping(value = "/getVendorOnboardTasksByVendorOnboardId", method = RequestMethod.GET)
		public @ResponseBody List<Tasks> getVendorOnboardTasksByVendorOnboardId(@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId,HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Tasks> tasksList = taskService.getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(orgLocations.getId(), user.getId(), vendorOnboardId);
			return tasksList;
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
