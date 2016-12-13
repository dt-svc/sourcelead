package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class InterviewFeedbackRestController {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService; 
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
  
	@RequestMapping(value = "/getPublicInterviewFeedbackTemplate" , method = RequestMethod.GET)
	   public String getPublicInterviewFeedbackTemplate(@RequestParam(value="templateId", required=false) Long templateId,
			   @RequestParam(value="requirementInterviewid", required=false) Long requirementInterviewid) throws Exception {
		    String resultJson = null;
		    InterviewFeedbackTemplate interviewFeedbackTemplate = interviewFeedbackTemplateService.getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(templateId,requirementInterviewid);
		    resultJson = toJSON(interviewFeedbackTemplate);
			 return resultJson;
	   }
	
	@RequestMapping(value = "/savePublicInterviewFeddbackTemlateReason" , method = RequestMethod.GET)
	   public String savePublicInterviewFeddbackTemlateReason(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="requirementInterviewid", required=false) String requirementInterviewid,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
		       String json1 = "{" + json + "}";
		       
			   JSONObject jsonObj = new JSONObject(json1);
	           SubmissionTemplate sub =  submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
		       RequirementInterview rq = requirementInterviewService.getRequirementInterviewById(requirementInterviewid);
		       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		       
			   InterviewFeedbackTemplate ift = new InterviewFeedbackTemplate();
			   ift.setRequirementInterviewId(rq);
			   ift.setSubmissionTemplateId(sub);
			   ift.setStatus("N");
			   ift.setComment(jsonObj.getString("interviewComment"));
			   ift.setInterviewStatus(jsonObj.getString("feedback"));
			   ift.setCreatedBy(user.getUserCode());
			   ift.setCreatedDate(new Date());
			   interviewFeedbackTemplateService.addInterviewFeedbackTemplate(ift);
			   return "publicInterviewFeedBackTemplate";
	    
	   }
	
	
	 /**
     * author Laxman
     * Storing for interviewFeedbackTemplate
     * @param json
     * @param templateId
     * @param jsonStringData
     * @param request
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value = "/saveInterviewFeedBackTemplate" , method = RequestMethod.POST)
	   public String saveInterviewFeedBackTemplate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
    	       OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    
			    String json1 = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json1);
			
			   try {
				   SubmissionTemplate submissionTemplate = null;
				   if(!isNullOrEmpty(templateId)){
					   submissionTemplate = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
					}else{
						submissionTemplate = new SubmissionTemplate();
					}
				   submissionTemplate.setTemplateType("Feedback");
				   submissionTemplate.setTemplateName(jsonObj.getString("templateName"));
				   submissionTemplate.setDescription(jsonObj.getString("description"));
				   submissionTemplate.setOrgLocationId(orgLocations);
				   submissionTemplate.setJsonString(jsonStringData);
				   submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
				   submissionTemplate.setCreatedBy(user.getUserCode());
				   submissionTemplate.setCreatedDate(new Date());
				   submissionTemplateService.addSubmissionTemplate(submissionTemplate);
					
				} catch (Exception e) {
					e.printStackTrace(); 
				}
				   return "interViewFeedBackTemplate";
	    
	   }
    
    
    
    
    @RequestMapping(value = "/savePublicInterviewFeddbackTemlate" , method = RequestMethod.GET)
	   public String savePublicInterviewFeddbackTemlate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="requirementInterviewid", required=false) String requirementInterviewid,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
		       String json1 = "{" + json + "}";
		       
			   JSONObject jsonObj = new JSONObject(json1);
	           SubmissionTemplate sub =  submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
		       RequirementInterview rq = requirementInterviewService.getRequirementInterviewById(requirementInterviewid);
		       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		       
			   InterviewFeedbackTemplate ift = new InterviewFeedbackTemplate();
			   ift.setRequirementInterviewId(rq);
			   ift.setSubmissionTemplateId(sub);
			   ift.setStatus("Y");
			   ift.setComment(jsonObj.getString("interviewComment"));
			   ift.setInterviewStatus(jsonObj.getString("feedback"));
			   ift.setInterviewTemplateJsonString(jsonStringData);
			   ift.setCreatedBy(user.getUserCode());
			   ift.setCreatedDate(new Date());
			   interviewFeedbackTemplateService.addInterviewFeedbackTemplate(ift);
			   return "publicInterviewFeedBackTemplate";
	    
	   }
    
    @RequestMapping(value = "/getInterviewFeedbackTemplatesList", method = RequestMethod.POST)
 	@ResponseBody String getInterviewFeedbackTemplatesList(HttpServletRequest request) throws Exception {
 		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
 		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getInterviewFeedbackTemplatesByOrgLocationId(orgLocations.getId());
 		
 		return commonUtil.toJSON(submissiontTemplateList);
 	}
    
    private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }

	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
}

