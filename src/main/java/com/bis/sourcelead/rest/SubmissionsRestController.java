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
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementService;
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
public class SubmissionsRestController {
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	private UserService userService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
    
    @RequestMapping(value = "/getCandidateResumes")
    public String getCandidateResumes(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request) throws Exception {
    	
    	String resultJson = null;
    	List<CandidateResumes> resumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(candidateId, Constants.CANDIDATE_ACTIVE);
    	resultJson = toJSON(resumesList);
		return resultJson;
		
	}
    
    
    @RequestMapping(value = "/getCandidateDetails")
    public String getCandidateDetails(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request) throws Exception {
    	
    	String resultJson = null;
    	Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
    	resultJson = toJSON(candidate);
		return resultJson;
		
	}
    
    @PreAuthorize("hasAnyRole('ROLE_VIEW_SUBMISSION_TEMPLATE','ROLE_ADD_APPLICANT')")
    @RequestMapping(value = "/getRequirmentOrgSubmissionTemplate" , method = RequestMethod.GET)
	   public String getRequirmentOrgSubmissionTemplate(@RequestParam(value="requirementId", required=false) Long requirementId) throws Exception {
			
		    Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		    
			return requirement.getSubmissionTemplateJsonString();
	    
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADD_APPLICANT')")
    @RequestMapping(value = "/saveAddApplicant" , method = RequestMethod.POST)
	   public String saveAddApplicant(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="con", required=false) String con,MultipartHttpServletRequest request,
			   @RequestParam(value="locationMapFile", required=false) CommonsMultipartFile locationMapFileData) throws Exception {
			
    	    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			
			MultipartFile multiFile = null;
			Iterator<String> itrator = request.getFileNames();
			if(itrator.hasNext()){
		        multiFile = request.getFile(itrator.next());
			}
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			requirementSubmissionService.saveRequirementSubmission(jsonObj, user, orgLocations,con,multiFile);
    	    
    	    
			return "success";
	    
 }
    
    @RequestMapping(value = "/getOrgSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody String getOrgSubmissionTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationId(orgLocations.getId());
		
		return commonUtil.toJSON(submissiontTemplateList);
	}
    


    @RequestMapping(value = "/saveOrgSubmissionTemplate" , method = RequestMethod.POST)
	   public String saveClientSubmissionTemplate(@RequestParam(value="jsonData", required=false) String json,
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
			   return "home";
	    
	   }
    
    
    @PreAuthorize("hasAnyRole('ROLE_SHORTLIST','ROLE_SUBMIT_TO_CLIENT')")
    @RequestMapping(value = "/getSubmissionCandidates" , method = RequestMethod.POST)
	   public String getSubmissionCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="status", required=false) String status,HttpServletRequest request) throws Exception {
			
    	 String resultJson = null;
    	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	 List<RequirementSubmission> shortlistedList = requirementSubmissionService.getAllSubmissionListByRequirementIdAndSubmissionStage(orgLocations.getId(), requirementId,status);    
     	 ArrayList<RequirementSubmission> candidatesList = new ArrayList<RequirementSubmission>();
     	 
     	 for (RequirementSubmission requirementSubmission : shortlistedList) {
			CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			if(requirementSubmissionTemplateFields.getLastName() != null){
				requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName()+" "+requirementSubmissionTemplateFields.getLastName());
			}else{
				requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
			}
			
			requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
			requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
			requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
			requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
			if(requirementSubmission.getCreatedDate() != null){
				requirementSubmission.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
			}
			User userVal = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			requirementSubmission.setUserName(userVal.getFirstName()+" "+userVal.getLastName());
			requirementSubmission.setResumeName(candidateResumes.getFileName());
			candidatesList.add(requirementSubmission);
		  }
     	 resultJson = toJSON(candidatesList);
		 return resultJson;
	    
    }
    
    
    @RequestMapping(value = "/submitShortlistedToClient" , method = RequestMethod.POST)
	   public String submitShortlistedToClient(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="submissionsList", required=false) String submissionsList,HttpServletRequest request) throws Exception {
    	
    	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
    	
    	 requirementSubmissionService.storeClientSubmission(requirementId, user, orgLocations, submissionsList,org);
    	 
    	
    	return "success";
    }
    
    
    
    @RequestMapping(value = "/candidateShortList" , method = RequestMethod.POST)
	   public String candidateShortList(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="submissionsList", required=false) String submissionsList,HttpServletRequest request) throws Exception {
 	
 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 	 Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
 	 
 	 String[] submissionId = submissionsList.split(",");
 	 
 	 for(int i=0; i < submissionId.length; i++){
 		 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
 		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SHORTLIST);
 		 submission.setUpdatedBy(user.getUserCode());
 		 submission.setUpdatedDate(new Date());
 		 requirementSubmissionService.storeRequirementSubmission(submission);
 		
 		}
 	 int shotListCount = submissionId.length;
 	  Requirement requirement  = requirementService.getRequirementByRequirementId(requirementId);
 	  if(requirement.getManagerUserId().getEmail() != null){
 		 sendEmailToManages(requirement,orgLocations,user,shotListCount,loginOrg);
 	  }
 	return "success";
 }
    
    @PreAuthorize("hasRole('ROLE_VIEW_APPLICANT')")
	@RequestMapping(value = "/getSubmissionDetails" , method = RequestMethod.POST)
	   public String getSubmissionDetails(@RequestParam(value="submissionId", required=false) Long submissionId,HttpServletRequest request) throws Exception {
    	 
    	 String resultJson = null;
    	 
    	 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
    	 String skillsList = "";
			Set<CandidateSkills> candidateSkills = submission.getCandidate().getCandidateSkills();
			int i = 0;
			for(CandidateSkills skilss: candidateSkills){
				if(i <= 5){
					skillsList = skillsList+skilss.getName();
					if(i != candidateSkills.size()-1){
						skillsList = skillsList+",";
					}
				}
				i++;
			}
			
			submission.setSkillsList(skillsList);
    	   resultJson = toJSON(submission);
		 return resultJson;
    	
    }
    
    
    
    
    

	//vendors mail sending the private method

	public void sendEmailToManages(Requirement requirement,OrgLocations loginOrgLocations,User user,Integer shotListCount,Org loginOrg)	throws Exception {
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(requirement.getManagerUserId().getFirstName());
		 emailMessage.setLastName(requirement.getManagerUserId().getLastName());
		 emailMessage.setToEmail(requirement.getManagerUserId().getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromName(user.getFirstName()+" "+user.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("managerFirstName", emailMessage.getFirstName());
			 globalVarsMap.put("managerLastName",emailMessage.getLastName());
			 globalVarsMap.put("loginUser", emailMessage.getFromName());
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 
			 globalVarsMap.put("loginOrgName",loginOrg.getOrgName());
			 globalVarsMap.put("shortListApplicantCount", shotListCount.toString());
			 
			 if((requirement.getMaxExp() == null || requirement.getMaxExp() == "" || requirement.getMaxExp() == "undefiend") && (requirement.getMinExp() == null || requirement.getMinExp() == "" || requirement.getMinExp() == "undefiend" )){
				 globalVarsMap.put("requirementExperience"," 0 ");
			 }
			 else if((requirement.getMaxExp() != null && requirement.getMaxExp() != "" && requirement.getMaxExp() != "undefiend") && (requirement.getMinExp() != null && requirement.getMinExp() != "" && requirement.getMinExp() != "undefiend" )){
				 globalVarsMap.put("requirementExperience",requirement.getMinExp() +" - "+requirement.getMaxExp());
			 }
			 else if((requirement.getMaxExp() != null && requirement.getMaxExp() != "" && requirement.getMaxExp() != "undefiend") && (requirement.getMinExp() == null || requirement.getMinExp() == "" || requirement.getMinExp() == "undefiend" )){
				 globalVarsMap.put("requirementExperience",requirement.getMaxExp());
			 }
			 else if((requirement.getMaxExp() == null || requirement.getMaxExp() == "" || requirement.getMaxExp() == "undefiend") && (requirement.getMinExp() != null && requirement.getMinExp() != "" && requirement.getMinExp() != "undefiend" )){
				 globalVarsMap.put("requirementExperience",requirement.getMinExp() );
			 }
			 if(requirement.getRate() != null && requirement.getRate() != "" && requirement.getRate() != "undefiend"){
				 globalVarsMap.put("requirementRate",requirement.getRate() +" "+requirement.getRateType());
			 }
			 if(requirement.getSkills() != null && requirement.getSkills() != "" && requirement.getSkills() != "undefiend"){
				 globalVarsMap.put("requirementSkill",requirement.getSkills());
			 }
			 
			 globalVarsMap.put("LoginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.SHORTLIST_APPLICANTS,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocations.getId(),emailRecipientList,new ArrayList<EmailAttachment>());
		 
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

