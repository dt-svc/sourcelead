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
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OffBoardingDocumentsService;
import com.bis.sourcelead.service.OffBoardingTemplateService;
import com.bis.sourcelead.service.OffboardTasksService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CandidateOffBoardRestController {
	
private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
	CommonUtil commonUtil;
    
    @Autowired
    UserService userService;
    
    @Autowired
    EmployeesService employeesService;
    
    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    OffBoardingTemplateService offBoardingTemplateService;
    
    @Autowired
    OffBoardingDocumentsService offBoardingDocumentsService;
    
    @Autowired
    OffboardTasksService offboardTasksService;
     
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    
    
    
	 @RequestMapping(value = "/getUsersForOffboard")
	 public String getUsersForOffboard(HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		List<User> usersList = userService.getUsersByOrgLocationIdExceptLoggedInUser(loginOrgLocation.getId(),loginUser.getId());
		List<User> orgUsersList = new ArrayList<User>();
		
		if(null != usersList && usersList.size() > 0){
			for(User user : usersList){

				    StringBuilder userName = new StringBuilder();
					if(null != user.getLastName()){
						user.setUserName(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					}else{
						user.setUserName(userName.append(user.getFirstName()).toString());
					}
				
				orgUsersList.add(user);
			}
		}
		
		return commonUtil.toJSON(usersList);
	 
	 }
	 
	 
	 
	 
	 @RequestMapping(value = "/getOffboardUserDetailsById")
	 public String getOffboardUserDetailsById(@RequestParam(value="offboardUserId", required=false) Long offboardUserId,
			 HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = userService.getUserById(offboardUserId);
		Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), loginOrgLocation.getId());
		if(null != employees && null != employees.getHireDate()){
			user.setDateOfJoin(Constants.DMY.format(employees.getHireDate()));
		}
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
		user.setRole(userRole.getRoles().getDisplayName());
		
		return commonUtil.toJSON(user);
	 }
	 
	 
	 @RequestMapping(value = "/getOffboardingTemplatesList")
	 public String getOffboardingTemplatesList(HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<OffBoardingTemplate> templatesList = offBoardingTemplateService.getOffBoardingTemplatesList(loginOrgLocation.getId());
		List<OffBoardingTemplate> offboardingTemplatesList = new ArrayList<OffBoardingTemplate>();
		
		for(OffBoardingTemplate template : templatesList){
			template.setTemplateCreatedDate(Constants.DMY.format(template.getCreatedDate()));
			offboardingTemplatesList.add(template);
		}
		
		resultJson = commonUtil.toJSON(offboardingTemplatesList);
		return resultJson;
	 }
	 
	 
	 @RequestMapping(value = "/createNewOffBoardingTemplate" , method = RequestMethod.POST)
	 public @ResponseBody  String createNewOffBoardingTemplate(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
		 String resultJson;
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 
		 OffBoardingTemplate offBoardingTemplate = null;
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 
	 	 if(jsonObj.has("templateId") && !isNullOrEmpty(jsonObj.getString("templateId"))){
	 		offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
	 		offBoardingTemplate.setTemplateName(jsonObj.getString("templateName"));
	 		offBoardingTemplate.setDescription(jsonObj.getString("description"));
	 		offBoardingTemplate.setUpdatedBy(user.getUserCode());
	 		offBoardingTemplate.setUpdatedDate(new Date());
	 	 
	 	 }else{
	 		offBoardingTemplate = new OffBoardingTemplate();
	 		offBoardingTemplate.setTemplateName(jsonObj.getString("templateName"));
	 		offBoardingTemplate.setDescription(jsonObj.getString("description"));
	 		offBoardingTemplate.setOrgLocationId(orgLocations);
		 	
		 	 StringBuilder userName = new StringBuilder();
			if(!isNullOrEmpty(user.getLastName())){
				offBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}else{
				offBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).toString());
			}
			offBoardingTemplate.setTemplateStatus("Draft");
			offBoardingTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			offBoardingTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			offBoardingTemplate.setCreatedBy(user.getUserCode());
			offBoardingTemplate.setCreatedDate(new Date());
			offBoardingTemplate.setUpdatedBy(user.getUserCode());
			offBoardingTemplate.setUpdatedDate(new Date());
	 	 }
	 	 
	 	
	 	offBoardingTemplate = offBoardingTemplateService.saveOffBoardingTemplate(offBoardingTemplate);
		
	 	resultJson = commonUtil.toJSON(Long.valueOf(offBoardingTemplate.getId()));
		return resultJson;
	   }
	 
	 
		@RequestMapping(value = "/getOffboardTemplateById" , method = RequestMethod.POST)
		 public @ResponseBody  String getOffboardTemplateById(@RequestParam(value="templateId", required=false) Long templateId, HttpServletRequest request) throws Exception {
			
			String resultJson;
		 	 
	 	    OffBoardingTemplate offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateById(templateId);
			
		 	resultJson = commonUtil.toJSON(offBoardingTemplate);
			return resultJson;
		   }
		
		
		
		
		@RequestMapping(value = "/saveOffboardingTemplateDocuments", method = RequestMethod.POST)
		public @ResponseBody String saveOffboardingTemplateDocuments(@RequestParam(value="json", required=false) String json,
				MultipartHttpServletRequest request) throws Exception {
			
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			json = "{"+json+"}";
			JSONObject jsonObj = new JSONObject(json);
			
			
			//save  mutliple attachment for Resignation / Termination Documents.
			if ((jsonObj.has("resignationAttcahmentMaxRowNumber"))){
		    	String resignationAttcahmentMaxRowNumber = jsonObj.getString("resignationAttcahmentMaxRowNumber");
				int resignationAttcahmentMaxRowNumberCount = Integer.parseInt(resignationAttcahmentMaxRowNumber);
				
				OffBoardingTemplate template = offBoardingTemplateService.getOffBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
				
		    	for (int a = 0; a < resignationAttcahmentMaxRowNumberCount; a++) {
					   
					   OffBoardingDocuments documents = new OffBoardingDocuments();
					   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
	       		       final Map files = multiRequest.getFileMap();
	       		       
	       		       documents.setOffboardingTemplateId(template);
	                   MultipartFile  multiFile = (MultipartFile)files.get(a+"resignationdocFile");
	                   if(multiFile != null && !multiFile.isEmpty()){
	                	   documents.setDocFile(multiFile.getBytes());
	                	   documents.setFileName(multiFile.getOriginalFilename());
	                       
	                       if(multiFile.getContentType()!=null){
	                    	   documents.setFileContentType(multiFile.getContentType());
	                       }
	                       documents.setDocumentType("Resignation");
	                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                       documents.setCreatedDate(CommonUtil.currentDate());
	                       documents.setCreatedBy(loginUser.getUserCode());
	                       documents.setUpdatedDate(CommonUtil.currentDate());
	                       documents.setUpdatedBy(loginUser.getUserCode());
	                       offBoardingDocumentsService.saveOffBoardingDocuments(documents);
						   
	                   }
	                 
		    	}
			}
			
			
			
			//save  mutliple attachment for offboard Documents.
					if ((jsonObj.has("offboardPackAttcahmentMaxRowNumber"))){
				    	String offboardPackAttcahmentMaxRowNumber = jsonObj.getString("offboardPackAttcahmentMaxRowNumber");
						int offboardPackAttcahmentMaxRowNumberCount = Integer.parseInt(offboardPackAttcahmentMaxRowNumber);
						
						OffBoardingTemplate template = offBoardingTemplateService.getOffBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
						
				    	for (int a = 0; a < offboardPackAttcahmentMaxRowNumberCount; a++) {
							   
							   OffBoardingDocuments documents = new OffBoardingDocuments();
							   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
			       		       final Map files = multiRequest.getFileMap();
			       		       
			       		       documents.setOffboardingTemplateId(template);
			                   MultipartFile  multiFile = (MultipartFile)files.get(a+"offboardPackdocFile");
			                   if(multiFile != null && !multiFile.isEmpty()){
			                	   documents.setDocFile(multiFile.getBytes());
			                	   documents.setFileName(multiFile.getOriginalFilename());
			                       
			                       if(multiFile.getContentType()!=null){
			                    	   documents.setFileContentType(multiFile.getContentType());
			                       }
			                       documents.setDocumentType("Offboard Pack");
			                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
			                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			                       documents.setCreatedDate(CommonUtil.currentDate());
			                       documents.setCreatedBy(loginUser.getUserCode());
			                       documents.setUpdatedDate(CommonUtil.currentDate());
			                       documents.setUpdatedBy(loginUser.getUserCode());
			                       offBoardingDocumentsService.saveOffBoardingDocuments(documents);
								   
			                   }
			                 
				    	}
					}
					
					return "success";
			
		}
		
		
		
		@RequestMapping(value = "/getOffboardTemplateDocumentByTemplateId" , method = RequestMethod.POST)
		 public @ResponseBody  String getOffboardTemplateDocumentByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,
				 @RequestParam(value="documentType", required=false) String documentType, HttpServletRequest request) throws Exception {
			
			String resultJson;
			
			List<OffBoardingDocuments> documentsList = offBoardingDocumentsService.getOffBoardingDocumentsByTemplateId(templateId,documentType);
		 	 
		 	resultJson = commonUtil.toJSON(documentsList);
			return resultJson;
		   
		}
		
		
		@RequestMapping(value = "/validateOffboardingTemplateName", method = RequestMethod.POST)
		@ResponseBody String validateOffboardingTemplateName(@RequestParam(value = "templateName", required = false) String templateName,
				@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			OffBoardingTemplate offBoardingTemplate = null;
			String result = null;
			
			if(!isNullOrEmpty(templateId)){
				offBoardingTemplate  = offBoardingTemplateService.getOffBoardingTemplateById(Long.parseLong(templateId));
				if(offBoardingTemplate.getTemplateName().equalsIgnoreCase(templateName)){
					result = "valid";
				}else{
					offBoardingTemplate  = offBoardingTemplateService.getOffBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
					if(null != offBoardingTemplate){
						result = "invalid";
					}else{
						result = "valid";
					}
				}
			}
			
			else{
				offBoardingTemplate  = offBoardingTemplateService.getOffBoardingTemplateByTemplateNameAndOrgLocationId(templateName,orgLocations.getId());
				if(null != offBoardingTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
			
			return result;
			
		}
		
		
		 @RequestMapping(value = "/getOffboardTasksListByTemplateId", method = RequestMethod.POST)
		 @ResponseBody String getOffboardTasksListByTemplateId(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
			
			 List<OffboardTasks> tasksList = offboardTasksService.getOffboardTasksByTemplateId(templateId);
			 
			 List<OffboardTasks> offboardtasksList = new ArrayList<OffboardTasks>();
			 
			 for(OffboardTasks tasks : tasksList){
				 
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
					 offboardtasksList.add(tasks);
			 }
				
			return commonUtil.toJSON(offboardtasksList);
			}
		 
		 
		
		 @RequestMapping(value = "/saveAndFinishOffboardTemplate", method = RequestMethod.POST)
		 @ResponseBody String saveAndFinishOffboardTemplate(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception{
			
			OffBoardingTemplate template = offBoardingTemplateService.getOffBoardingTemplateById(templateId);
			if(null != template){
				template.setTemplateStatus("Finished");
				offBoardingTemplateService.saveOffBoardingTemplate(template);
			}
			return "success";
			
		 }
		 
		 
		 
		 @RequestMapping(value = "/saveOffBoardTasks" , method = RequestMethod.POST)
		 public @ResponseBody  String saveOffBoardTasks(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
			
			 
			 json = "{"+json+"}";
			 
			 JSONObject jsonObj = new JSONObject(json);
			 
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			 
			 OffboardTasks offboardTasks = new OffboardTasks();
			 
			 
			 if(jsonObj.has("taskName") && !isNullOrEmpty(jsonObj.getString("taskName"))){
				 offboardTasks.setTaskName(jsonObj.getString("taskName"));
			 }
			 
			 OffBoardingTemplate template = new OffBoardingTemplate();
			 template.setId(Long.valueOf(jsonObj.getString("templateId")));
			 offboardTasks.setOffboardingTemplateId(template);
			 
			 if(jsonObj.has("onboardAssignedTo") && !isNullOrEmpty(jsonObj.getString("onboardAssignedTo"))){
				
				 String userIds = jsonObj.getString("onboardAssignedTo");
				 
				 userIds = userIds.replaceAll("\\[", "").replaceAll("\\]","");
				 userIds = userIds.replaceAll("\"", "");
				 offboardTasks.setAssignToUsers(userIds);
			 }else{
				 offboardTasks.setAssignToCandidate(Constants.YES);
			 }
			 
			 if(jsonObj.has("taskPriority") && !isNullOrEmpty(jsonObj.getString("taskPriority"))){
				 offboardTasks.setPriority(jsonObj.getString("taskPriority"));
			 }
			 
			 if(jsonObj.has("taskRepeat") && !isNullOrEmpty(jsonObj.getString("taskRepeat"))){
				 offboardTasks.setTaskRepeat(jsonObj.getString("taskRepeat"));
			 }
			 
			 if(jsonObj.has("taskMonths") && !isNullOrEmpty(jsonObj.getString("taskMonths"))){
				 offboardTasks.setDueAfterMonths(Integer.parseInt(jsonObj.getString("taskMonths")));
			 }
			 
			 if(jsonObj.has("taskDays") && !isNullOrEmpty(jsonObj.getString("taskDays"))){
				 offboardTasks.setDueAfterDays(Integer.parseInt(jsonObj.getString("taskDays")));
			 }
			 
			 if(jsonObj.has("taskHours") && !isNullOrEmpty(jsonObj.getString("taskHours"))){
				 offboardTasks.setDueAfterHours(Integer.parseInt(jsonObj.getString("taskHours")));
			 }
			 
			 if(jsonObj.has("taskMinutes") && !isNullOrEmpty(jsonObj.getString("taskMinutes"))){
				 offboardTasks.setDueAfterMinutes(Integer.parseInt(jsonObj.getString("taskMinutes")));
			 }
			 
			 if(jsonObj.has("taskNotification") && !isNullOrEmpty(jsonObj.getString("taskNotification"))){
				 offboardTasks.setTaskNotification(jsonObj.getString("taskNotification"));
			 }
			 
			 if(jsonObj.has("taskEmail") && !isNullOrEmpty(jsonObj.getString("taskEmail"))){
				 offboardTasks.setTaskEmail(jsonObj.getString("taskEmail"));
			 }
			 
			 if(jsonObj.has("taskdescription") && !isNullOrEmpty(jsonObj.getString("taskdescription"))){
				 offboardTasks.setDescription(jsonObj.getString("taskdescription"));
			 }
			 
			 offboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
			 offboardTasks.setCreatedDate(CommonUtil.currentDate());
			 offboardTasks.setCreatedBy(user.getUserCode());
			 offboardTasks.setUpdatedDate(CommonUtil.currentDate());
			 offboardTasks.setUpdatedBy(user.getUserCode());
			 
			 
			 offboardTasksService.saveOffboardTasks(offboardTasks);
			
			  return "success";
		   }
		 
		 
		 
		 
		 
		 
		 
		 @RequestMapping(value = "/downloadOffboardDocument", method = RequestMethod.GET)
		    public void downloadOffboardDocument(@RequestParam(value="documentUUID", required=false) String documentUUID ,HttpServletResponse response) throws Exception {
				
				
					OffBoardingDocuments templateDocuments = offBoardingDocumentsService.getOffBoardingDocumentsBydocumentUUID(documentUUID);	
				    
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
			
			
			@RequestMapping(value = "/deleteOffBoardingDocumentByDocumentUUID" , method = RequestMethod.POST)
			 public @ResponseBody  String deleteOffBoardingDocumentByDocumentUUID(@RequestParam(value="documentUUID", required=false) String documentUUID,
					 HttpServletRequest request) throws Exception {
				
				OffBoardingDocuments templateDocuments = offBoardingDocumentsService.getOffBoardingDocumentsBydocumentUUID(documentUUID);
			 	 if(null != templateDocuments){
			 		templateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			 		offBoardingDocumentsService.saveOffBoardingDocuments(templateDocuments);
			 	 }
				return "success";
			   
			}
		

}
