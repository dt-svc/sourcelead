package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;
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
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	 private OrgLocationService orgLocationService;
  
	
	/**
	 * author Laxman
	 * this method is used for the getting public page interviewFeedback template list  
	 * @param templateId
	 * @param requirementInterviewid
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getPublicInterviewFeedbackTemplate" , method = RequestMethod.GET)
	   public String getPublicInterviewFeedbackTemplate(@RequestParam(value="templateId", required=false) Long templateId,
			   @RequestParam(value="requirementInterviewid", required=false) Long requirementInterviewid) throws Exception {
		    String resultJson = null;
		    InterviewFeedbackTemplate interviewFeedbackTemplate = interviewFeedbackTemplateService.getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(templateId,requirementInterviewid);
		    resultJson = toJSON(interviewFeedbackTemplate);
			 return resultJson;
	   }
	
	/**
	 * Store the InterviewFeedBack Template Reason 
	 * @param json
	 * @param templateId
	 * @param requirementInterviewid
	 * @param jsonStringData
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/savePublicInterviewFeddbackTemlateReason" , method = RequestMethod.GET)
	   public String savePublicInterviewFeddbackTemlateReason(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="requirementInterviewid", required=false) Long requirementInterviewerId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
		       String json1 = "{" + json + "}";
		       
			   JSONObject jsonObj = new JSONObject(json1);
	           SubmissionTemplate sub =  submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
		       RequirementInterviewer rqr = requirementInterviewerService.getInterviwerstByInterviewId(requirementInterviewerId);
		       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		       
			   InterviewFeedbackTemplate ift = new InterviewFeedbackTemplate();
			   ift.setRequirementInterviewerId(rqr);
			   ift.setSubmissionTemplateId(sub);
			   ift.setStatus("N");
			   ift.setComment(jsonObj.getString("interviewComment"));
			   if(jsonObj.has("feedback") && !"".equalsIgnoreCase(jsonObj.getString("feedback"))  && null != jsonObj.getString("feedback")){
				   ift.setInterviewStatus(jsonObj.getString("feedback"));
			   }
			   if(null != user){
				   ift.setCreatedBy(user.getUserCode());   
			   }
			   ift.setCreatedDate(new Date());
			   interviewFeedbackTemplateService.addInterviewFeedbackTemplate(ift);
			   
			   RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(rqr.getRequirementInterview().getSubmissionId().getId());
		       RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		       requirementSubmissionHistory.setInterviewerId(rqr);
		       requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
			   requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.FEEDBACK);
			   requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
			   
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
    
	@PreAuthorize("hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')")
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
				   submissionTemplate.setTemplateName(jsonObj.getString("templateName").trim());
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
    
    
    /**
     * author Laxman
     * Store the publicInterviewFeedback Template 
     * @param json
     * @param templateId
     * @param requirementInterviewid
     * @param jsonStringData
     * @param request
     * @return publicInterviewFeedBackTemplate page
     * @throws Exception
     */
	
    
       @RequestMapping(value = "/savePublicInterviewFeddbackTemlate" , method = RequestMethod.GET)
	   public String savePublicInterviewFeddbackTemlate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="requirementInterviewid", required=false) Long requirementInterviewerId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
		       String json1 = "{" + json + "}";
		       
			   JSONObject jsonObj = new JSONObject(json1);
	           SubmissionTemplate sub =  submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
	           RequirementInterviewer rqr = requirementInterviewerService.getInterviwerstByInterviewId(requirementInterviewerId);
		       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		       
			   InterviewFeedbackTemplate ift = new InterviewFeedbackTemplate();
			   ift.setRequirementInterviewerId(rqr);
			   ift.setSubmissionTemplateId(sub);
			   ift.setStatus("Y");
			   ift.setInterviewTemplateJsonString(jsonStringData);
			   if(null != user){
				   ift.setCreatedBy(user.getUserCode());   
			   }
			   ift.setCreatedDate(new Date());
			   interviewFeedbackTemplateService.addInterviewFeedbackTemplate(ift);
			   
		      RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(rqr.getRequirementInterview().getSubmissionId().getId());
		      RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		      requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
			  requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.FEEDBACK);
			  requirementSubmissionHistory.setInterviewerId(rqr);

			  requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
			  requirementInterviewerService.updatedFeedbackMailFromInterviewerToRequirementOwner(rqr,requirementSubmission,jsonObj,templateId);
			   return "publicInterviewFeedBackTemplate";
	    
	   }
       
       /**
        * author Laxman
        * this methos is used for interviewFeedback Template list
        * @param request
        * @return json submissiontTemplateList
        * @throws Exception
        */
    
    @RequestMapping(value = "/getInterviewFeedbackTemplatesList", method = RequestMethod.POST)
 	@ResponseBody String getInterviewFeedbackTemplatesList(HttpServletRequest request) throws Exception {
 		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
 		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getInterviewFeedbackTemplatesByOrgLocationId(orgLocations.getId());
 		
 		return commonUtil.toJSON(submissiontTemplateList);
 	}
    
    
    
    @RequestMapping(value = "/getInterviewrsFeedbackList" , method = RequestMethod.GET)
	   public String getInterviewrsFeedbackList(@RequestParam(value="interviewId", required=false) Long interviewId,@RequestParam(value="locationId", required=false) Long locationId) throws Exception {
		    String resultJson = null;
						List<RequirementInterviewer> interviewers = requirementInterviewerService.getInterviwersListByInterviewId(interviewId);
						OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
						//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
						for(RequirementInterviewer interviewer: interviewers){
							StringBuilder name = new StringBuilder();
							if(null != interviewer.getInterviewerUser().getLastName() && !"".equalsIgnoreCase(interviewer.getInterviewerUser().getLastName())){
								interviewer.setInterviewerName(name.append(interviewer.getInterviewerUser().getFirstName()).append(" ").append(interviewer.getInterviewerUser().getLastName()).toString());	
							}else{
								interviewer.setInterviewerName(name.append(interviewer.getInterviewerUser().getFirstName()).toString());
							}
							
							InterviewFeedbackTemplate feedbackTemplate = interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(interviewer.getId()) ;
							if(feedbackTemplate != null){
								interviewer.setFeedbackDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),feedbackTemplate.getCreatedDate()));
								interviewer.setFeedbackStatus(feedbackTemplate.getStatus());
							}
						}
		      resultJson = toJSON(interviewers);
			 return resultJson;
	   }
    
    @RequestMapping(value = "/loadInterviewFeedbackTemplate" , method = RequestMethod.GET)
	   public String loadInterviewFeedbackTemplate() throws Exception {
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
			return submissionTemplate.getJsonString();
	    
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

