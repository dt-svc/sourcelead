package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.dto.UserManualsDTO;
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
import com.bis.sourcelead.enums.Result;
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
@RequestMapping("/app")
public class VendorOnboardRestController {
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(VendorOnboardRestController.class);

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
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/getVendorOnboardingTemplatesList", "/rest/getVendorOnboardingTemplatesList" }, method = RequestMethod.POST)
	public ResponseList<OnBoardingTemplateDTO> getVendorOnboardingTemplatesList(HttpServletRequest request) throws Exception {
		ResponseList<OnBoardingTemplateDTO> reponse = new ResponseList<>(); 
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
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
				if(templateDTOList != null){
					reponse.setData(templateDTOList);
 					reponse.setResponse(Result.SUCCESS.name());
 					reponse.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponse.setResponse(Result.FAILED.name());
					reponse.setResponseMessage(Result.FAILED.getValue());
				}	
				
			}else if (user == null) {
				reponse.setResponse(Result.SESSION_TIMEDOUT.name());
				reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				reponse.setResponse(Result.INVALID_DATA.name());
				reponse.setResponseMessage(Result.INVALID_DATA.getValue());
			}
			
		}catch (Exception e) {
			reponse.setResponse(Result.AWKWARD.name());
			reponse.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return reponse;
	 }
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/createNewVendorOnBoardingTemplate", "/rest/createNewVendorOnBoardingTemplate" }, method = RequestMethod.POST)
	 public @ResponseBody  Response<OnBoardingTemplateDTO> createNewVendorOnBoardingTemplate(OnBoardingTemplateDTO onBoardingTemplateDTO,HttpServletRequest request) throws Exception {
		Response<OnBoardingTemplateDTO> reponse = new Response<>();  
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null && Validator.validate(onBoardingTemplateDTO)){
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	 VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardTemplateDetails(onBoardingTemplateDTO, user, orgLocations);
			 	 OnBoardingTemplateDTO templateDTO = new OnBoardingTemplateDTO();
			 	 templateDTO.setId(onBoardingTemplate.getId());
			 	 if(templateDTO != null){
			 		reponse.setObject(templateDTO);
	 					reponse.setResponse(Result.SUCCESS.name());
	 					reponse.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						reponse.setResponse(Result.FAILED.name());
						reponse.setResponseMessage(Result.FAILED.getValue());
					} 
			 	 
			} else if (user == null) {
				reponse.setResponse(Result.SESSION_TIMEDOUT.name());
				reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				reponse.setResponse(Result.INVALID_DATA.name());
				reponse.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		 	 
		}catch (Exception e) {
				reponse.setResponse(Result.AWKWARD.name());
				reponse.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		
	 	 return reponse;
	  
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/getVendorOnboardTemplateById", "/rest/getVendorOnboardTemplateById" }, method = RequestMethod.POST)
	 public @ResponseBody Response<OnBoardingTemplateDTO> getVendorOnboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
		Response<OnBoardingTemplateDTO> reponse = new Response<>();  
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				VendorOnboardingTemplate onBoardingTemplate = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
				 OnBoardingTemplateDTO templateDTO = new OnBoardingTemplateDTO();
			 	 templateDTO.setTemplateName(onBoardingTemplate.getTemplateName());
			 	 templateDTO.setDescription(onBoardingTemplate.getDescription());
			 	 if(templateDTO != null){
			 		reponse.setObject(templateDTO);
 					reponse.setResponse(Result.SUCCESS.name());
 					reponse.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponse.setResponse(Result.FAILED.name());
					reponse.setResponseMessage(Result.FAILED.getValue());
				}  
			}else if (user == null) {
				reponse.setResponse(Result.SESSION_TIMEDOUT.name());
				reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				reponse.setResponse(Result.INVALID_DATA.name());
				reponse.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}catch (Exception e) {
			reponse.setResponse(Result.AWKWARD.name());
			reponse.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	 	 return reponse;
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
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/getVendorOnboardTemplateDocumentByTemplateId", "/rest/getVendorOnboardTemplateDocumentByTemplateId" }, method = RequestMethod.POST)
	 public @ResponseBody ResponseList<VendorOnboardingTemplateDocumentDTO> getVendorOnboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
			 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
		ResponseList<VendorOnboardingTemplateDocumentDTO> reponse = new ResponseList<>();  
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
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
				if(documentsDTOList != null){
				reponse.setData(documentsDTOList);
					reponse.setResponse(Result.SUCCESS.name());
					reponse.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				reponse.setResponse(Result.FAILED.name());
				reponse.setResponseMessage(Result.FAILED.getValue());
			} 
				}else if (user == null) {
					reponse.setResponse(Result.SESSION_TIMEDOUT.name());
					reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					reponse.setResponse(Result.INVALID_DATA.name());
					reponse.setResponseMessage(Result.INVALID_DATA.getValue());
				}
		}catch (Exception e) {
			reponse.setResponse(Result.AWKWARD.name());
			reponse.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
			
		
		
		return reponse;
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
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveVendorOnboardingTemplateDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveVendorOnboardingTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, HttpServletRequest request) throws Exception {
		Response<String> reponse = new Response<>(); 
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
					onBoardingTemplateDTO.setStaticPath(staticPath);
					 VendorOnboardingTemplateDocuments docs=vendorOnboardTemplateDocumentsService.storeVendorOnBoardTemplateDocuments(onBoardingTemplateDTO, loginUser);
						 reponse.setResponse(Result.SUCCESS.name());
						 reponse.setResponseMessage(Result.SUCCESS.getValue());
						
			} else if (loginUser == null) {
				reponse.setResponse(Result.SESSION_TIMEDOUT.name());
				reponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				reponse.setResponse(Result.INVALID_DATA.name());
				reponse.setResponseMessage(Result.INVALID_DATA.getValue());
			}
			
		}catch (Exception e) {
				reponse.setResponse(Result.AWKWARD.name());
				reponse.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		
		return reponse;
			
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
	 public @ResponseBody  Response<String> deleteVendorOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
			 HttpServletRequest request) throws Exception {
		Response<String> response=new Response<String>();
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null){
				 VendorOnboardingTemplate template =	vendorOnboardTemplateService.getVendorOnBoardingTemplateByTemplateUUID(templateUUID);
				 VendorOnboardingTemplate temp=null;
			 	 if(template != null){
			 		template.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			 		temp=vendorOnboardTemplateService.saveVendorOnBoardingTemplate(template);
			 	 }
			 	 if(temp != null){
			 		response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			 }
			
		}catch (Exception e) {
			 response.setResponse(Result.AWKWARD.name());
			 response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		return response;
	   
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
	
	
	 @SuppressWarnings("unused")
	@RequestMapping(value = { "/getVendorOnboardTemplateTasksListByTemplateId", "/rest/getVendorOnboardTemplateTasksListByTemplateId" }, method = RequestMethod.GET)
	 @ResponseBody ResponseList<VendorOnboardTaskDTO> getVendorOnboardTemplateTasksListByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		 ResponseList<VendorOnboardTaskDTO> response = new ResponseList<>(); 
			try{
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(loginUser != null){
					
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
							 if(tasksDTOList != null){
								 response.setData(tasksDTOList);
								 response.setResponse(Result.SUCCESS.name());
									response.setResponseMessage(Result.SUCCESS.getValue());
								} else {
									response.setResponse(Result.FAILED.name());
									response.setResponseMessage(Result.FAILED.getValue());
								}
					 }
					}else if (loginUser == null) {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					} else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
			}catch (Exception e) {
				 response.setResponse(Result.AWKWARD.name());
				 response.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
					e.printStackTrace();
				}
				
		 return response;
			
		}
	 
	 
	 
	 @RequestMapping(value = "/saveVendorOnBoardTemplateTasks" , method = RequestMethod.POST)
	 public @ResponseBody   Response<String> saveVendorOnBoardTemplateTasks(OnboardTasksDTO onboardTasksDTO, HttpServletRequest request) throws Exception {
		 Response<String> response=new Response<>();
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null && Validator.validate(onboardTasksDTO)){
				 VendorOnboardTasks tasks= vendorOnboardTasksService.saveVendorOnboardTemplateTask(onboardTasksDTO, user);
				 if(tasks != null){
					 response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
	    			
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
				
		 }catch (Exception e) {
			 response.setResponse(Result.AWKWARD.name());
			 response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		 
		 return response;
	   }
	 
	 
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/deleteVendorOnboardTaskByTaskId" , method = RequestMethod.POST)
	 public @ResponseBody  Response<String> deleteVendorOnboardTaskByTaskId(@RequestParam(value="taskId", required=false) Long taskId,
			 HttpServletRequest request) throws Exception {
		Response<String> response=new Response<>();
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null){
				 VendorOnboardTasks onboardTasks =	vendorOnboardTasksService.getVendorOnboardTasksByTaskId(taskId);
				 VendorOnboardTasks tasks=null;
			 	 if(null != onboardTasks){
			 		onboardTasks.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			 		tasks=vendorOnboardTasksService.saveVendorOnboardTasks(onboardTasks);
			 	 }
			 	 if(tasks != null){
			 		 response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
		}catch (Exception e) {
			 response.setResponse(Result.AWKWARD.name());
			 response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		return response;
	   
	}
	 
	 @SuppressWarnings("unused")
	@RequestMapping(value = "/saveAndFinishVendorOnboardTemplate", method = RequestMethod.POST)
	 @ResponseBody Response<String> saveAndFinishVendorOnboardTemplate(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
		Response<String> response=new Response<String>();
		try{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null){
				 VendorOnboardingTemplate template = vendorOnboardTemplateService.getVendorOnBoardingTemplateById(templateId);
				 VendorOnboardingTemplate temp=null;
					if(null != template){
						template.setTemplateStatus("Finished");
						temp=vendorOnboardTemplateService.saveVendorOnBoardingTemplate(template);
					}
					if(temp != null){
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
		}catch (Exception e) {
			 response.setResponse(Result.AWKWARD.name());
			 response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		return response;
		
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
			if(vendorOnboarding != null) {
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
		
		

		@RequestMapping(value = { "/validateVendorOnboardRequiredAttachedDocuments", "/rest/validateVendorOnboardRequiredAttachedDocuments" }, method = RequestMethod.POST)
		public @ResponseBody String validateVendorOnboardRequiredAttachedDocuments(@RequestParam(value="vendorOnboardId", required=false) Long vendorOnboardId) throws Exception {
			
			String result = "Success";
			
			List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsService.getVendorOnboaringRequiredDocumentsByVendorOnboardId(vendorOnboardId);
			if(documentsList != null && documentsList.size() > 0) {
				for(VendorOnboardingTemplateDocuments documents : documentsList) {
					if(StringUtils.isBlank(documents.getUploadedFileUrl())) {
						result = "Invalid";
						break;
					}
				}
			}
			
			return result;
		}	
		
		
		@RequestMapping(value = { "/deleteVendorUploadedOnboardDocument", "/rest/deleteVendorUploadedOnboardDocument" }, method = RequestMethod.POST)
		public @ResponseBody String deleteVendorUploadedOnboardDocument(@RequestParam(value="documentId", required=false) Long documentId) throws Exception {
			
			VendorOnboardingTemplateDocuments documents = vendorOnboardTemplateDocumentsService.getVendorOnBoardingTemplateDocumentsById(documentId);
			documents.setUploadedFileContentType(null);
			documents.setUploadedFileName(null);
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
			if(vendorOnboarding != null) {
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
