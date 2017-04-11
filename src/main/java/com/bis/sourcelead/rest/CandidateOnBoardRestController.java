package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
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
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class CandidateOnBoardRestController {


	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OnBoardingTemplateService onBoardingTemplateService;
	
	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;
	
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
	
	
}
