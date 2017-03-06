package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.helper.RequirementHelper;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
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
public class SubmissionsRestController {
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	CandidateResumesService candidateResumesService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	CandidateEducationService candidateEducationService;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	@Autowired
	private SubmissionTemplateService submissionTemplateService;

	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;

	@Autowired
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService; 
	
	@Autowired
	private UserService userService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Autowired
	EmailScenarioService emailScenarioService;

	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;

	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService;

	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementElkUtil requirementElkUtil;
	
	 @Autowired
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	

	@RequestMapping(value = "/getCandidateResumes")
	public String getCandidateResumes(@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception {

		String resultJson = null;
		List<CandidateResumes> resumesList = candidateResumesService
				.getCandidateResumesListByCandidateIdAndStatus(candidateId, Constants.CANDIDATE_ACTIVE);
		resultJson = toJSON(resumesList);
		return resultJson;

	}

	@RequestMapping(value = "/getCandidateDetails")
	public String getCandidateDetails(@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception {

		String resultJson = null;
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		List<String> educationList = candidateEducationService.getCandidateEducationNamesByCandidateId(candidateId);
		StringBuilder education = new StringBuilder();
		if (educationList != null && educationList.size() > 0) {
			for(String educationName : educationList){
				education.append(educationName).append(", ");
			}
			
			candidate.setCandidateEdu(education.substring(0,education.length()-2).toString());
		}
		resultJson = toJSON(candidate);
		return resultJson;

	}
	
	/**@author Prasad Salina
	  * To  validate candidate email
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateRequirementCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateRequirementCandidateEmail(@RequestParam(value="email", required=false) String email,@RequestParam(value="requirementId", required=false) Long requirementId,HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	RequirementSubmission requirementSubmission = requirementSubmissionService.validateRequirementCandidateEmail(email,requirementId,orgLocation.getId());
		 	
		 	if (requirementSubmission != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }

	@PreAuthorize("hasAnyRole('ROLE_VIEW_SUBMISSION_TEMPLATE','ROLE_ADD_APPLICANT','ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/getRequirmentOrgSubmissionTemplate", method = RequestMethod.GET)
	public String getRequirmentOrgSubmissionTemplate(
			@RequestParam(value = "requirementId", required = false) Long requirementId) throws Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);

		return requirement.getSubmissionTemplateJsonString();

	}

	@RequestMapping(value = "/getInterviewrsViewFeedbackList", method = RequestMethod.GET)
	public String getInterviewFeedbackTemplate(@RequestParam(value = "templateId", required = false) Long templateId)
			throws Exception {
		SubmissionTemplate st = submissionTemplateService.getSubmissionTemplateById(templateId);
		return st.getJsonString();
	}

	@PreAuthorize("hasRole('ROLE_ADD_APPLICANT')")
	@RequestMapping(value = "/saveAddApplicant", method = RequestMethod.POST)
	public String saveAddApplicant(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request,
			@RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		MultipartFile multiFile = null;
		//to get parse resume
		if(locationMapFileData != null && locationMapFileData.getSize() > 0 ){
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multiFile = request.getFile(itrator.next());
			}
		}
		//to get the manually uploaded resume
		else{
			final Map files = request.getFileMap();
		    multiFile = (MultipartFile)files.get("newUploadBtn");
		}
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String  userRoleName = (String)SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmission(jsonObj,user, orgLocations, con, multiFile);
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().getValue());
		requirementSubmissionHistory.setFromStage("NEW");
//		requirementSubmission.get
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory);
		Requirement requirement = requirementSubmission.getRequirement();
		
		if(null != requirement){
			Integer totalApplicatsCountForRequirement = 0;
		   //updating total number of applicants for requrement
			totalApplicatsCountForRequirement = requirementSubmissionService.getNumberOfApplicantsForRequirement(orgLocations.getId(), requirement.getId());
//			totalApplicatsCountForRequirement = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(orgLocations,requirement.getId(),user,userRoleName);
			if(totalApplicatsCountForRequirement > 0){
				requirement.setTotalApplicants(totalApplicatsCountForRequirement); 
				requirementService.updateRequirement(requirement);
			}else{
				requirement.setTotalApplicants(0); 
			}
			requirementElkUtil.saveElkRequirement(requirement,new JSONObject());	
		}
		return "success";

	}

	@RequestMapping(value = "/getOrgSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody
	String getOrgSubmissionTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList = submissionTemplateService
				.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocations.getId(), "Submission");

		return commonUtil.toJSON(submissiontTemplateList);
	}

	@RequestMapping(value = "/saveOrgSubmissionTemplate", method = RequestMethod.POST)
	public String saveClientSubmissionTemplate(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request)
			throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		try {
			SubmissionTemplate submissionTemplate = null;
			if (!isNullOrEmpty(templateId)) {
				submissionTemplate = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
			} else {
				submissionTemplate = new SubmissionTemplate();
			}

			submissionTemplate.setTemplateName(jsonObj.getString("templateName").trim());
			submissionTemplate.setDescription(jsonObj.getString("description"));
			submissionTemplate.setOrgLocationId(orgLocations);
			submissionTemplate.setJsonString(jsonStringData);
			submissionTemplate.setTemplateType("Submission");
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";

	}

	@PreAuthorize("hasAnyRole('ROLE_SHORTLIST')")
	@RequestMapping(value = "/getSubmissionCandidates", method = RequestMethod.POST)
	public String getSubmissionCandidates(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request)
			throws Exception {

		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		List<RequirementSubmission> shortlistedList = requirementSubmissionService.getAllSubmissionListByRequirementIdAndSubmissionStage(orgLocations, requirementId, status,user,loginUserRole);
		
		ArrayList<RequirementSubmission> candidatesList = new ArrayList<RequirementSubmission>();

		for (RequirementSubmission requirementSubmission : shortlistedList) {
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService
					.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			if (null != requirementSubmissionTemplateFields.getLastName()) {
				StringBuilder canNameSB = new StringBuilder();
				requirementSubmission.setCandidateName(canNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			} else {
				requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
			}

			requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
			RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(requirementSubmission.getId());
			if(pastRequirementRateSubmissionNegotiation != null){
				requirementSubmission.setExpectedCTC(pastRequirementRateSubmissionNegotiation.getExpectedRate());
			}else{
				requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
			}
			
			requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
			requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
			
			if(requirementSubmission.getVendorLocationId() != null){
				requirementSubmission.setOrgName(requirementSubmission.getVendorLocationId().getOrg().getOrgName());
			}
			
			if (requirementSubmission.getCreatedDate() != null) {
				requirementSubmission
						.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
			}
			candidatesList.add(requirementSubmission);
		}
		resultJson = toJSON(candidatesList);
		return resultJson;

	}

	@PreAuthorize("hasAnyRole('ROLE_SUBMIT_TO_CLIENT')")
	@RequestMapping(value = "/getShortListedCandidates", method = RequestMethod.POST)
	public String getShortListedCandidates(@RequestParam(value = "requirementId", required = false) Long requirementId,
			HttpServletRequest request) throws Exception {

		String resultJson = null;
		boolean submitToClient = true;
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		
		if(null != requirement){
			if(null != requirement.getParentRequirement() && requirement.getIsClientJob().equalsIgnoreCase("Y")){
				if(!requirement.getParentRequirement().getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.OPEN)){
					submitToClient = false;
				}
			}
		}
		
		if(submitToClient){
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<RequirementSubmission> shortlistedList = requirementSubmissionService
				.getAllShortLitstedCandidatesForClientSubmissionByRequirementId(orgLocations.getId(), requirementId);
		ArrayList<RequirementSubmission> candidatesList = new ArrayList<RequirementSubmission>();

		for (RequirementSubmission requirementSubmission : shortlistedList) {
			
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService
					.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			if (requirementSubmissionTemplateFields.getLastName() != null) {
				StringBuilder canNameSB = new StringBuilder();
				requirementSubmission.setCandidateName(canNameSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			} else {
				requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
			}

			requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
			requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
			requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
			requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
			
			if(StringUtils.isNotEmpty(requirementSubmission.getFinalizedRate())){
				requirementSubmission.setSubmitToClientRate(requirementSubmission.getFinalizedRate());
			}else{
				requirementSubmission.setSubmitToClientRate(requirementSubmissionTemplateFields.getExpectedCTC());
			}
			
			if(requirementSubmission.getVendorLocationId() != null){
				requirementSubmission.setOrgName(requirementSubmission.getVendorLocationId().getOrg().getOrgName());
			}
	
			if (requirementSubmission.getCreatedDate() != null) {
				requirementSubmission
						.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
			}
			candidatesList.add(requirementSubmission);
		}
		resultJson = toJSON(candidatesList);
		return resultJson;
		}else{
			return "failed";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_SUBMIT_TO_CLIENT')")
	@RequestMapping(value = "/submitShortlistedToClient", method = RequestMethod.POST)
	public String submitShortlistedToClient(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionsList", required = false) String submissionsList,
			@RequestParam(value = "selectedShortlistsRate", required = false) String selectedShortlistsRate,
			HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

		requirementSubmissionService.storeClientSubmission(requirementId, user, orgLocations, submissionsList,
				selectedShortlistsRate, org);
		return "success";
	}

	@RequestMapping(value = "/candidateShortList", method = RequestMethod.POST)
	public String candidateShortList(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionsList", required = false) String submissionsList,
			HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

		String[] submissionId = submissionsList.split(",");

		for (int i = 0; i < submissionId.length; i++) {
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			RequirementSubmission submission = requirementSubmissionService
					.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
			requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus().getValue());
			submission.setSubmissionStatus(ApplicantSubmissionStatus.SHORTLIST);
			submission.setUpdatedBy(user.getUserCode());
			submission.setUpdatedDate(new Date());
			RequirementSubmission requirementSubmission = requirementSubmissionService
					.storeRequirementSubmission(submission);
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,
					requirementSubmissionHistory);

		}
		int shotListCount = submissionId.length;
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		if (requirement.getManagerUserId().getEmail() != null) {
			sendEmailToManages(requirement, orgLocations, user, shotListCount, loginOrg);
		}
		return "success";
	}

	@PreAuthorize("hasRole('ROLE_VIEW_APPLICANT')")
	@RequestMapping(value = "/getSubmissionDetails", method = RequestMethod.POST)
	public String getSubmissionDetails(@RequestParam(value = "submissionId", required = false) Long submissionId,
			HttpServletRequest request) throws Exception {

		String resultJson = null;

		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		StringBuilder skillsList = new StringBuilder();
		if(null != submission.getCandidate().getSkillsName()){
			String[] candidateSkills = submission.getCandidate().getSkillsName().split(",");
			int i = 0;
			for (String skill : candidateSkills) {
				if (i <= 5) {
					skillsList = skillsList.append(skill);
					if (i != candidateSkills.length - 1) {
						skillsList = skillsList.append(",");
					}
				}
				i++;
			}
		}
		
		submission.setSkillsList(skillsList.toString());
		submission.setRequirementSubmissionTemplateFields(requirementSubmissionTemplateFields);
		resultJson = toJSON(submission);
		return resultJson;

	}

	/**
	 * 
	 * author : Pavan Jampana desc : This method is used to Reject Applicant
	 * from Requirement Pipeline List from any Stage.
	 */

	@RequestMapping(value = "/rejectApplicant")
	public @ResponseBody String rejectApplicant(
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		RequirementSubmission requirementSubmission = requirementSubmissionService
				.getRequirementSubmissionById(submissionId);

		// to update submission history
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().getValue());
		requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.REJECTED.getValue());

		// to update Submission status
		requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
		requirementSubmission.setRejectedReason(reason);
		requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
		requirementSubmission.setUpdatedBy(user.getUserCode());
		StringBuilder sb = new StringBuilder();
		requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		requirementSubmission.setUpdatedDate(new Date());
		requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		requirementSubmissionService.sendRejectionMailToVendorContact(user,reason,submissionId,orgLocations);
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,
				requirementSubmissionHistory);

		return "success";
	}

	// vendors mail sending the private method

	public void sendEmailToManages(Requirement requirement, OrgLocations loginOrgLocations, User user,
			Integer shotListCount, Org loginOrg) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(requirement.getManagerUserId().getFirstName());
		emailMessage.setLastName(requirement.getManagerUserId().getLastName());
		emailMessage.setToEmail(requirement.getManagerUserId().getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setCreatedBy(user.getUserCode());
		StringBuilder userNameSB = new StringBuilder();
		emailMessage.setFromName(userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("managerFirstName", emailMessage.getFirstName());
		globalVarsMap.put("managerLastName", emailMessage.getLastName());
		globalVarsMap.put("loginUser", emailMessage.getFromName());
		globalVarsMap.put("requirementName", requirement.getJobTitle());

		globalVarsMap.put("loginOrgName", loginOrg.getOrgName());
		globalVarsMap.put("shortListApplicantCount", shotListCount.toString());

		if ((requirement.getMaxExp() == null || requirement.getMaxExp() == "" || requirement.getMaxExp() == "undefiend")
				&& (requirement.getMinExp() == null || requirement.getMinExp() == ""
						|| requirement.getMinExp() == "undefiend")) {
			globalVarsMap.put("requirementExperience", " 0 ");
		} else if ((requirement.getMaxExp() != null && requirement.getMaxExp() != ""
				&& requirement.getMaxExp() != "undefiend")
				&& (requirement.getMinExp() != null && requirement.getMinExp() != ""
						&& requirement.getMinExp() != "undefiend")) {
			StringBuilder expSB = new StringBuilder();
			globalVarsMap.put("requirementExperience", expSB.append(requirement.getMinExp()).append(" - " ).append(requirement.getMaxExp()).toString());
		} else if ((requirement.getMaxExp() != null && requirement.getMaxExp() != ""
				&& requirement.getMaxExp() != "undefiend")
				&& (requirement.getMinExp() == null || requirement.getMinExp() == ""
						|| requirement.getMinExp() == "undefiend")) {
			globalVarsMap.put("requirementExperience", requirement.getMaxExp());
		} else if ((requirement.getMaxExp() == null || requirement.getMaxExp() == ""
				|| requirement.getMaxExp() == "undefiend")
				&& (requirement.getMinExp() != null && requirement.getMinExp() != ""
						&& requirement.getMinExp() != "undefiend")) {
			globalVarsMap.put("requirementExperience", requirement.getMinExp());
		}
		if (requirement.getRate() != null && requirement.getRate() != "" && requirement.getRate() != "undefiend") {
			StringBuilder rateSB = new StringBuilder();
			globalVarsMap.put("requirementRate", rateSB.append(requirement.getRate()).append(" ").append(requirement.getRateType()).toString());
		}
		if (requirement.getSkills() != null && requirement.getSkills() != ""
				&& requirement.getSkills() != "undefiend") {
			globalVarsMap.put("requirementSkill", requirement.getSkills());
		}

		StringBuilder loginURLBuilder = new StringBuilder();
		globalVarsMap.put("LoginURL", loginURLBuilder.append("<a href='").append(sourceleadUrl).append("/").append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = null;
		emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SHORTLIST_APPLICANTS,
				Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = "";

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, new Org());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, loginOrgLocations.getId(), iAdressArray,
				new ArrayList<EmailAttachment>());

	}

	/**
	 * @author Shiva Yogi candidate submit to the client with email template
	 *         preview
	 * @param jsonData
	 * @param submissionsList
	 * @param requirementId
	 * @param multPartrequest
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_SUBMIT_TO_CLIENT')")
	@RequestMapping(value = "/submitToClient", method = RequestMethod.POST)
	public @ResponseBody String submitToClient(@RequestParam(value = "jsonData", required = false) String jsonData,
			@RequestParam(value = "selectedIds", required = false) String submissionsList,
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "selectedShortlistsRate", required = false) String selectedShortlistsRate,
			@RequestParam(value = "selectedShortlistsRatePer", required = false) String selectedShortlistsRatePer,
			MultipartHttpServletRequest multPartrequest) throws Exception {
		String jsonc = "{" + jsonData + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(multPartrequest,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(multPartrequest, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(multPartrequest, "org");
		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) multPartrequest;
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		requirementSubmissionService.storeClientSubmissionWithEmailPreview(requirementId, user, orgLocations,
				submissionsList, org, jsonObj, files,selectedShortlistsRate,selectedShortlistsRatePer);
		return "success";
	}

	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	/**
	 * check given string empty or null
	 * 
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	// /addEmailtemplate

	@PreAuthorize("hasAnyRole('ROLE_SUBMIT_TO_CLIENT')")
	@RequestMapping(value = "/addEmailtemplate", method = RequestMethod.GET)
	@ResponseBody
	public String addEmailtemplate(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "selectedShortlistsCount", required = false) String submissionIds,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson = null;
		String[] submissions = submissionIds.split(",");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String fullName = null;
		if (!"".equalsIgnoreCase(user.getLastName()) && user.getLastName() != null) {
			StringBuilder nameSB = new StringBuilder();
			fullName = nameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		} else {
			fullName = user.getFirstName();
		}
		StringBuilder sb = new StringBuilder();
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		requirement.setSubmitToClientFromMailId(user.getEmail());
		requirement.setSubmitToClientToMailId(requirement.getSpocId().getClientUser().getEmail());
		/*if(requirement.getSpocId().getInitiatorLocationId().getId().longValue() == orgLocations.getId().longValue() ){
				requirement.setSubmitToClientToMailId(requirement.getSpocId().getClientUser().getEmail());
		 }
		if(requirement.getSpocId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
			requirement.setSubmitToClientToMailId(requirement.getSpocId().getOwnerUser().getEmail());
		}*/
		StringBuilder subjectSB = new StringBuilder();
		requirement.setSubmitToClientMailSubject(subjectSB.append("New applicants received for the").append(" ").append(requirement.getJobTitle()).toString());

		if (requirement != null && requirement.getParentRequirement() != null) {
			if(requirement != null && requirement.getParentRequirement().getManagerUserId() != null){
				sb.append("<p>").append("Hi,").append(requirement.getParentRequirement().getManagerUserId().getFirstName()).append(" ")
				.append(requirement.getParentRequirement().getManagerUserId().getLastName()).append("</p>")
				.append("<p>").append(fullName).append(" submitted the applicants for the").append(" ")
				.append(requirement.getJobTitle()).append("</p>")
				.append("<p>").append("Total no.of submitted applicants :").append(submissions.length).append("<p>")
				.append("<p>").append("Best Regards,").append("</p>")
				.append("<p>").append(fullName).append("</p>");
				requirement.setMailBody(sb.toString());
			}
		}else{
			if(null != requirement.getSpocId()){
				if(null != requirement.getSpocId().getClientUser()){
					sb.append("<p>").append("Hi,");
					if(user.getId().longValue() == requirement.getSpocId().getClientUser().getId().longValue()){
						sb.append(" ").append(requirement.getSpocId().getOwnerUser().getFirstName()).append(" ")
						.append(requirement.getSpocId().getOwnerUser().getLastName()).append("</p>").append("\n");
					}else{
						sb.append(" ").append(requirement.getSpocId().getClientUser().getFirstName()).append(" ")
						.append(requirement.getSpocId().getClientUser().getLastName()).append("</p>").append("\n");
					}
					sb.append("<p>").append(fullName).append(" submitted the applicants for the").append(" ").append(requirement.getJobTitle()).append("</p>")
					.append("<p>").append("Total no.of submitted applicants :").append(submissions.length).append("</p>")
					.append("<p>").append("Best Regards,").append("</p>")
					.append("<p>").append(fullName).append("</p>");
					requirement.setMailBody(sb.toString());
				}
			}
		}
		resultJson = toJSON(requirement);
		return resultJson;
	}
	
	/**
	 * author : Pavan Jampana
	 * desc : This method is used to Reactivate the Rejected Applicant
	 * from Requirement Pipeline List from any Stage.
	 */

	@RequestMapping(value = "/reActivateRejectedApplicant")
	public @ResponseBody String reActivateApplicant(
			@RequestParam(value = "submissionId", required = false) Long submissionId,HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

		// to update submission history
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REJECTED.getValue());
		if(requirementSubmission.getPreviousSubmissionStatus()!=null){
		requirementSubmissionHistory.setToStage(requirementSubmission.getPreviousSubmissionStatus().getValue());
		}

		// to update Submission status
		requirementSubmission.setSubmissionStatus(requirementSubmission.getPreviousSubmissionStatus());
		requirementSubmission.setSubmissionRateStatus(null);
		requirementSubmission.setPreviousSubmissionStatus(null);
		requirementSubmission.setUpdatedBy(user.getUserCode());
		requirementSubmission.setUpdatedDate(new Date());
		requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,
				requirementSubmissionHistory);

		return "success";
	}

}
