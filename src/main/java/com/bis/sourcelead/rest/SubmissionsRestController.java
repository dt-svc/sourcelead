package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.dto.ApplicantRejectBackOutDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.RequirementSubmissionServiceImpl;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
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
	private RequirementOffersService requirementOffersService;

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
	
	@Autowired
	RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	@Autowired
	RecommendationsService recommendationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;
	

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Autowired
	RequirementSubmissionServiceImpl requirementSubmissionServiceImpl;
	
	@Autowired
	CandidateCommentsService candidateCommentsService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	private RequirementSubmissionHistoryDao requirementSubmissionHistoryDao;

	 @RequestMapping(value = { "/getCandidateResumes", "/rest/getCandidateResumes" }, method = RequestMethod.POST)
	@ResponseBody ResponseList<CandidateResumes> getCandidateResumes(@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception {
		ResponseList<CandidateResumes> response = new ResponseList<>();
		try {
			List<CandidateResumes> resumesList = candidateResumesService
					.getCandidateResumesListByCandidateIdAndStatus(candidateId, Constants.CANDIDATE_ACTIVE);
			response.setData(resumesList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;

	}

	@RequestMapping(value = "/getCandidateDetails")
	@ResponseBody Response<Candidate> getCandidateDetails(@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			HttpServletRequest request) throws Exception {
		Response<Candidate> response = new Response<>();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			List<String> educationList = candidateEducationService.getCandidateEducationNamesByCandidateId(candidateId);
			StringBuilder education = new StringBuilder();
			if (educationList != null && educationList.size() > 0) {
				for(String educationName : educationList){
					education.append(educationName).append(", ");
				}
				
				candidate.setCandidateEdu(education.substring(0,education.length()-2).toString());
			}
			if(candidate.getOrgRelationId() != null && candidate.getSpocId() != null){
				candidate.setRelationId(candidate.getOrgRelationId().getId());
				candidate.setVenSpocId(candidate.getSpocId().getId());
			}
			//this block of code is added to get customized score for candidate. -- pavan jampana
			if(requirementId != null){
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				RecommendedCustomizeScore recommendedCustomizeScore = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementId(requirement.getId());
				String score = recommendationsService.getCandidateScore(candidate,requirement,recommendedCustomizeScore);
				if(score != null && !"".equalsIgnoreCase(score)){
					candidate.setCandidateScore(score);
				}
				
				boolean isSumbitted = requirementSubmissionService.isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(candidateId, requirementId);
				if(isSumbitted == true){
					candidate.setIsCandidateSubmitted(Constants.YES);
				}else{
					candidate.setIsCandidateSubmitted(Constants.NO);
				}
			}
			int percentage = candidateService.getCandidatePercentage(candidate,orgLocation);
			candidate.setProfilePercentage(percentage);
			
			CandidateResumes candidateResume = candidateResumesService.getCandidateLatesResumes(candidateId, Constants.RECORD_ACTIVE);
			if(candidateResume != null){
				candidate.setResumeUUID(candidateResume.getResumeUUID());
				candidate.setCandidateResumeName(candidateResume.getFileName());
				candidate.setFileUrl(candidateResume.getFileURL());
				candidate.setFileContentType(candidateResume.getFileContentType());
			}
			
			CandidateEmployers currentEmployer = candidateEmployersService.getCandidateCurrentEmployer(candidateId, orgLocation.getId());
			if(null != currentEmployer){
				candidate.setEmployer(currentEmployer.getName());
			}else{
				CandidateEmployers lastCompanyEmployer = candidateEmployersService.getCandidateLastCompanyEmployer(candidateId, orgLocation.getId());
				if(null != lastCompanyEmployer){
					candidate.setEmployer(lastCompanyEmployer.getName());
				}
			}
			candidate.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), candidate.getDateOfBirth()));
			response.setObject(candidate);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;

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
	 
	 
	 
	 @RequestMapping(value = "/validateRequirementGuestCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateRequirementGuestCandidateEmail(@RequestParam(value="email", required=false) String email,@RequestParam(value="requirementId", required=false) Long requirementId,HttpServletRequest request) throws Exception {
		 	String result = null;
		 	Requirement req =requirementService.getRequirementByRequirementId(requirementId);
		 	RequirementSubmission requirementSubmission = requirementSubmissionService.validateRequirementCandidateEmail(email,requirementId,req.getOrgLocationId().getId());
		 	
		 	if (requirementSubmission != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }

	 
	 @RequestMapping(value = "/app/validateRequirementRecruiterCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateRequirementRecruiterCandidateEmail(@RequestParam(value="email", required=false) String email,@RequestParam(value="requirementId", required=false) Long requirementId,HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	RequirementSubmission requirementSubmission = requirementSubmissionService.validateRequirementByRecruiterCandidateEmail(email, requirementId, user.getId());
		 	
		 	if (requirementSubmission != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }

	@PreAuthorize("hasAnyRole('ROLE_VIEW_SUBMISSION_TEMPLATE','ROLE_ADD_APPLICANT','ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = {"/getRequirmentOrgSubmissionTemplate"}, method = RequestMethod.GET)
	public String getRequirmentOrgSubmissionTemplate(
			@RequestParam(value = "requirementId", required = false) Long requirementId) throws Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);

		return requirement.getSubmissionTemplateJsonString();

	}
	
	@RequestMapping(value = {"/rest/getRequirmentOrgSubmissionTemplate"}, method = RequestMethod.POST)
	@ResponseBody
	public org.json.simple.JSONArray getRequirmentOrgSubmissionTemplateForMobile(
			@RequestParam(value = "requirementId", required = false) Long requirementId) throws Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		JSONParser par = new JSONParser();
		org.json.simple.JSONArray template = (org.json.simple.JSONArray)par.parse(requirement.getSubmissionTemplateJsonString());
		return template;
	}
	
	@RequestMapping(value = "/getGuestUserRequirmentOrgSubmissionTemplate", method = RequestMethod.GET)
	public String getGuestUserRequirmentOrgSubmissionTemplate(@RequestParam(value = "requirementId", required = false) Long requirementId) throws Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);

		return requirement.getSubmissionTemplateJsonString();

	}


	
	@RequestMapping(value = "/getInterviewrsViewFeedbackList", method = RequestMethod.GET)
	public String getInterviewFeedbackTemplate(@RequestParam(value = "templateId", required = false) String templateId)
			throws Exception {
		SubmissionTemplate st = submissionTemplateService.getSubmissionTemplateByUUID(templateId);
		return st.getJsonString();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADD_APPLICANT','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveAddApplicant", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveAddApplicant(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request,
			@RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		
		Response<String> response = new Response<>();

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmission(jsonObj,user, orgLocations, con, loginUserRole);
		
				RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
				requirementSubmissionHistory.setFromStage("NEW");
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				Requirement requirement = requirementSubmission.getRequirement();
				
				if(null != requirement){
					Integer totalApplicatsCountForRequirement = 0;
				   //updating total number of applicants for requrement
					totalApplicatsCountForRequirement = requirementSubmissionService.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
					if(totalApplicatsCountForRequirement > 0){
						requirement.setTotalApplicants(totalApplicatsCountForRequirement); 
						requirementService.updateRequirement(requirement);
					}else{
						requirement.setTotalApplicants(0); 
					}
					
					// pass dto as parameter
					RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
					requirementElkUtil.saveElkRequirement(requirement,requirementDTO);	
				}
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				}catch(Exception e) {
					e.printStackTrace();
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	/**
	 * @author Pradeep
	 * @param json
	 * @param con
	 * @param request
	 * @param locationMapFileData
	 * @return success
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveGuestAddApplicant", method = RequestMethod.POST)
	public String saveGuestAddApplicant(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request,
			@RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
			
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmission(jsonObj,user, orgLocations, con, loginUserRole);

		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
		requirementSubmissionHistory.setFromStage("NEW");
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		Requirement requirement = requirementSubmission.getRequirement();
		
		if(null != requirement){
			Integer totalApplicatsCountForRequirement = 0;
		   //updating total number of applicants for requrement
			totalApplicatsCountForRequirement = requirementSubmissionService.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
			
			requirement.setRequirementLocations(new HashSet<RequirementLocations>(requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId())));
			
			if(totalApplicatsCountForRequirement > 0){
				requirement.setTotalApplicants(totalApplicatsCountForRequirement); 
				requirementService.updateRequirement(requirement);
			}else{
				requirement.setTotalApplicants(0); 
			}
			
			// pass dto as parameter
			RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
			requirementElkUtil.saveElkRequirement(requirement,requirementDTO);	
		}
		SourceLeadSessionManager.setSessionAttribute(request, "guestuserCreateMsg", "Successfully Applied");
		return "success";

	}
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADD_APPLICANT','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/editSubmisionDetails", method = RequestMethod.POST)
	public String editSubmisionDetails(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request)
			throws Exception {
		
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.editRequirementSubmission(jsonObj, user, orgLocations, con);
		return "success";
		
	}

	@RequestMapping(value = "/app/getOrgSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody
	String getOrgSubmissionTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList = submissionTemplateService
				.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocations.getId(), "Submission");

		return commonUtil.toJSON(submissiontTemplateList);
	}

	@RequestMapping(value = "/app/saveOrgSubmissionTemplate", method = RequestMethod.POST)
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
				submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
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
			SourceLeadSessionManager.setSessionAttribute(request, "SubmissionTemplateCreateMsg", "Success!! New Submission Template created");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";

	}

	@PreAuthorize("hasAnyRole('ROLE_SHORTLIST')")
	@RequestMapping(value = "/getSubmissionCandidates", method = RequestMethod.POST)
	public ResponseList<RequirementSubmission> getSubmissionCandidates(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request)
			throws Exception {
		ResponseList<RequirementSubmission> response = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		if(user != null) {
			try {
				String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				//java.text.SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				if (requirement != null && requirement.getPrimaryRecruiterUserId() != null) {
					user = requirement.getManagerUserId();
				}
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
						requirementSubmission.setLastRateIniLocId(pastRequirementRateSubmissionNegotiation.getInitiatorOrgLocationId().getId().toString());
						requirementSubmission.setLastRateIniType(pastRequirementRateSubmissionNegotiation.getRateInitiatorType().getValue());
						requirementSubmission.setLastRateIniUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
						requirementSubmission.setLastRateStage(pastRequirementRateSubmissionNegotiation.getRateStage().getValue());
						requirementSubmission.setExpectedCTC(pastRequirementRateSubmissionNegotiation.getExpectedRate());
					}else{
						requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
					}
					
					requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
					requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
					if(requirementSubmission.getCandidateResumes() != null) {
						requirementSubmission.setFileURL(requirementSubmission.getCandidateResumes().getFileURL());
						requirementSubmission.setFilename((requirementSubmission.getCandidateResumes().getFileName()));
						requirementSubmission.setFileContentType(requirementSubmission.getCandidateResumes().getFileContentType());
					}
					
					if(requirementSubmission.getVendorLocationId() != null){
						requirementSubmission.setOrgName(requirementSubmission.getVendorLocationId().getOrgLocationName());
					}
					
					if (requirementSubmission.getCreatedDate() != null) {
						requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmission.getCreatedDate()));
					}
					candidatesList.add(requirementSubmission);
				}
				response.setData(candidatesList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	@PreAuthorize("hasAnyRole('ROLE_SUBMIT_TO_CLIENT')")
	@RequestMapping(value = "/getShortListedCandidates", method = RequestMethod.POST)
	public ResponseList<RequirementSubmission> getShortListedCandidates(@RequestParam(value = "requirementId", required = false) Long requirementId,
			HttpServletRequest request) throws Exception {
		ResponseList<RequirementSubmission> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		if(user != null) {
			try {
				boolean submitToClient = true;
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				if(null != requirement){
					if(null != requirement.getParentRequirement() && requirement.getIsClientJob().equalsIgnoreCase("Y")){
						if(!requirement.getParentRequirement().getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.OPEN) && 
								!requirement.getParentRequirement().getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.NEW)){
							submitToClient = false;
						}
					}
				}
				
				if(submitToClient){
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				List<RequirementSubmission> shortlistedList = requirementSubmissionService
						.getAllShortLitstedCandidatesForClientSubmissionByRequirementId(orgLocations, requirement,user,loginUserRole);
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
					if(requirementSubmission.getCandidateResumes() != null) {
						requirementSubmission.setFileURL(requirementSubmission.getCandidateResumes().getFileURL());
					}
					
					if(StringUtils.isNotEmpty(requirementSubmission.getFinalizedRate())){
						requirementSubmission.setSubmitToClientRate(requirementSubmission.getFinalizedRate());
					}else{
						requirementSubmission.setSubmitToClientRate(requirementSubmissionTemplateFields.getExpectedCTC());
					}
					
					if(StringUtils.isNotEmpty(requirementSubmission.getFinalizedRateType())){
						requirementSubmission.setSubmitToClientRateType(requirementSubmission.getFinalizedRateType());
					}else{
						requirementSubmission.setSubmitToClientRateType(requirementSubmissionTemplateFields.getExpectedRatePer());
					}
					
					if(requirementSubmission.getVendorLocationId() != null){
						requirementSubmission.setOrgName(requirementSubmission.getVendorLocationId().getOrgLocationName());
					}
			
					if (requirementSubmission.getCreatedDate() != null) {
						requirementSubmission.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmission.getCreatedDate()));
					}
					candidatesList.add(requirementSubmission);
				}
				response.setData(candidatesList);
				}else{
					response.setError("failed");
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
	}else {
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	}
		return response;
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
	public Response<String> candidateShortList(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionsList", required = false) String submissionsList,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				int  count = 0;
				String[] submissionId = submissionsList.split(",");
		
				for (int i = 0; i < submissionId.length; i++) {
					RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
					RequirementSubmission submission = requirementSubmissionService .getRequirementSubmissionById(Long.parseLong(submissionId[i]));
					requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus());
					submission.setPreviousSubmissionStatus(submission.getSubmissionStatus());
					submission.setSubmissionStatus(ApplicantSubmissionStatus.SHORTLIST.toString());
					submission.setUpdatedBy(user.getUserCode());
					submission.setUpdatedUser(user.getFullName());
					if(null != submission.getSourceSubmission()){
						count = count + 1;
					}
					submission.setUpdatedDate(new Date());
					RequirementSubmission requirementSubmission = requirementSubmissionService.storeRequirementSubmission(submission);
					requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				}
				int shotListCount = submissionId.length;
				Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
				
				// for URL redirections from mail
				String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
				Long redirectOrgLocationId = orgLocations.getId();
				
				if (requirement.getManagerUserId().getEmail() != null && requirement.getManagerUserId().getId().longValue() != user.getId().longValue()) {
					sendEmailToManages(requirement, orgLocations, user, shotListCount, loginOrg,redirectURL,redirectOrgLocationId);
				}
				if(count>0){
					requirementSubmissionService.shortListedMailToVendorFromClient(requirement,submissionsList,orgLocations,user);
				}
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
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
		User  submittedSourceUser = userService.getUserByUserCode(submission.getCreatedBy());
		// This is to show the Requested User for Applicant AUthorization
		if(submittedSourceUser !=null){
		if(null != submittedSourceUser.getLastName() && !" ".equalsIgnoreCase(submittedSourceUser.getLastName())){
            StringBuilder sub = new  StringBuilder();
            submission.setSubmittedBy(sub.append(submittedSourceUser.getFirstName()).append(" ").append(submittedSourceUser.getLastName()).toString() );
		}else{
			submission.setSubmittedBy(submittedSourceUser.getFirstName());

		}
		}
		submission.setDesignationType(submission.getCandidate().getDesignationType());
		submission.setCandidateSummary(submission.getCandidate().getSummary());
		submission.setSkillsList(skillsList.toString());
		submission.setRequirementSubmissionTemplateFields(requirementSubmissionTemplateFields);
		submission.setFacebookUrl(submission.getCandidate().getFacebookUrl());
		submission.setLinkedInUrl(submission.getCandidate().getLinkedUrl());
		submission.setTwitterUrl(submission.getCandidate().getTwitterUrl());
		if(submission.getCandidateResumes() != null) {
			submission.setResumeId(submission.getCandidateResumes().getId());
		}
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		int percentage = candidateService.getCandidatePercentage(submission.getCandidate(),orgLocations);
		submission.setProfilePercentage(percentage);
		
		List<CandidateComments> candidateCommentsList = candidateCommentsService.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(submission.getCandidate().getId(), orgLocations);
		submission.setCandidateCommentsCount(String.valueOf(candidateCommentsList.size()));
		
		resultJson = toJSON(submission);
		return resultJson;

	}
	
	
	
	
	@RequestMapping(value = "/getSubmittedCandidateDetailsForRequriement", method = RequestMethod.GET)
	public RequirementSubmissionTemplateFields getSubmittedCandidateDetailsForRequriement(@RequestParam(value = "submissionId", required = true) Long submissionId) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		requirementSubmissionTemplateFields.setSubmissionJsonString(requirementSubmission.getSubmissionTemplateJsonString());
		requirementSubmissionTemplateFields.setSubmissionJsonStringValue(requirementSubmission.getSubmissionTemplateJsonStringValue());
		return requirementSubmissionTemplateFields;
	}
	
	

	/**
	 * 
	 * author : Pavan Jampana desc : This method is used to Reject Applicant
	 * from Requirement Pipeline List from any Stage.
	 */


	@RequestMapping(value = "/rejectApplicant")
	public @ResponseBody Response<String> rejectApplicant(ApplicantRejectBackOutDTO applicantRejectBackOutDTO, @RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request) throws Exception

	{
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
				// to update submission history
				RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
				requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.REJECTED.toString());
				// to update Submission status
				requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
				if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getReasonForReject())) {
					requirementSubmission.setRejectedReason(applicantRejectBackOutDTO.getReasonForReject());
				}
				if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getRejectedCategory())) {
					requirementSubmission.setRejectedCategoryType(applicantRejectBackOutDTO.getRejectedCategory());
				}
				if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getRejectedCategoryOther())) {
					requirementSubmission.setRejectedCategoryOther(applicantRejectBackOutDTO.getRejectedCategoryOther());
				}
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED.toString());
				requirementSubmission.setUpdatedBy(user.getUserCode());
				StringBuilder sb = new StringBuilder();
				requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				requirementSubmission.setUpdatedDate(new Date());
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				if(!orgLocations.getId().equals(requirementSubmission.getCandidate().getOrgLocationId().getId()) && requirementSubmission.getSourceSubmission() != null &&  !requirementSubmission.getCandidate().getCandidateType().equalsIgnoreCase("Professional")) {
					requirementSubmissionService.sendRejectionMailToVendorContact(user, requirementSubmission.getRejectedReason(), submissionId, orgLocations);
				}
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	// vendors mail sending the private method

	public void sendEmailToManages(Requirement requirement, OrgLocations loginOrgLocations, User user,
			Integer shotListCount, Org loginOrg,String redirectURL, Long redirectOrgLocationId) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(requirement.getManagerUserId().getFirstName());
		emailMessage.setLastName(requirement.getManagerUserId().getLastName());
		emailMessage.setToEmail(requirement.getManagerUserId().getEmail());
		emailMessage.setFromEmail("donotreply@sourcelead.com");
		emailMessage.setCreatedBy(user.getUserCode());
		StringBuilder userNameSB = new StringBuilder();
//		emailMessage.setFromName(userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("ManagerFirstName", emailMessage.getFirstName());
		globalVarsMap.put("ManagerLastName", emailMessage.getLastName());
		globalVarsMap.put("LoginUser", emailMessage.getFromName());
		//globalVarsMap.put("requirementName", requirement.getJobTitle());

		
		 if(requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder builder=new StringBuilder();
			 globalVarsMap.put("RequirementName", builder.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		 }else{
			 globalVarsMap.put("RequirementName", requirement.getJobTitle());
		 }
		 
		 
		 StringBuilder location = new StringBuilder();
		 if(requirement.getState() != null && !"".equalsIgnoreCase(requirement.getState())){
			 globalVarsMap.put("RequirementLocation", location.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		 }else if(requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())){
			 globalVarsMap.put("RequirementLocation",location.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		 }else{
			 globalVarsMap.put("RequirementLocation", requirement.getCountry());
		 }
		 if(Constants.JOB_OTHER.equalsIgnoreCase(requirement.getJobType())) {
			 globalVarsMap.put("EmploymentType", requirement.getOtherJobType());
		 }
		 else if(requirement.getJobType() != null && !"".equalsIgnoreCase(requirement.getJobType())){
			 globalVarsMap.put("EmploymentType", requirement.getJobType());
		 }else{
			 globalVarsMap.put("EmploymentType","--");
		 }
		globalVarsMap.put("loginOrgName", loginOrgLocations.getOrgLocationName());
		globalVarsMap.put("ShortListApplicantCount", shotListCount.toString());

		if ((requirement.getMaxExp() == null || requirement.getMaxExp() == "" || requirement.getMaxExp() == "undefiend")
				&& (requirement.getMinExp() == null || requirement.getMinExp() == ""
						|| requirement.getMinExp() == "undefiend")) {
			globalVarsMap.put("RequirementExperience", " 0 ");
		} else if ((requirement.getMaxExp() != null && requirement.getMaxExp() != ""
				&& requirement.getMaxExp() != "undefiend")
				&& (requirement.getMinExp() != null && requirement.getMinExp() != ""
						&& requirement.getMinExp() != "undefiend")) {
			StringBuilder expSB = new StringBuilder();
			globalVarsMap.put("RequirementExperience", expSB.append(requirement.getMinExp()).append(" - " ).append(requirement.getMaxExp()).toString());
		} else if ((requirement.getMaxExp() != null && requirement.getMaxExp() != ""
				&& requirement.getMaxExp() != "undefiend")
				&& (requirement.getMinExp() == null || requirement.getMinExp() == ""
						|| requirement.getMinExp() == "undefiend")) {
			globalVarsMap.put("RequirementExperience", requirement.getMaxExp());
		} else if ((requirement.getMaxExp() == null || requirement.getMaxExp() == ""
				|| requirement.getMaxExp() == "undefiend")
				&& (requirement.getMinExp() != null && requirement.getMinExp() != ""
						&& requirement.getMinExp() != "undefiend")) {
			globalVarsMap.put("RequirementExperience", requirement.getMinExp());
		}
		if (requirement.getRate() != null && requirement.getRate() != "" && requirement.getRate() != "undefiend") {
			StringBuilder rateSB = new StringBuilder();
			globalVarsMap.put("RequirementRate", rateSB.append(requirement.getRate()).append(" ").append(requirement.getRateType()).toString());
		}
		if (requirement.getSkills() != null && requirement.getSkills() != ""
				&& requirement.getSkills() != "undefiend") {
			globalVarsMap.put("RequirementSkill", requirement.getSkills());
		}

		StringBuilder loginURLBuilder = new StringBuilder();
		globalVarsMap.put("LoginURL", loginURLBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("'style='text-decoration:none;'><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Applicants</div></a>").toString());
		

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SHORTLIST_NOTIFICATION_MANAGER,
				Constants.ENTITY_TYPE_NOTIFICATION);


		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		

		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocations.getOrg(),loginOrgLocations,Constants.SHORTLIST_APPLICANTS,new ArrayList<EmailAttachment>());
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink =actionLinkBuilder.append("").append(atsUrl).append("/").append("requirementSummary?requirementUUID="+requirement.getRequirementUUID()).toString();	 
		emailScenerioUtil.sendNotification(actionLink, user,requirement.getManagerUserId(), loginOrgLocations, notificationScenario,new HashMap<String,String>());

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
	public Response<Requirement> addEmailtemplate(@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "selectedShortlistsCount", required = false) String submissionIds,
			HttpServletRequest request) throws Exception {
		Response<Requirement> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String resultJson = null;
				String[] submissions = submissionIds.split(",");
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
				if(requirement.getParentRequirement() != null && StringUtils.isNotEmpty(requirement.getParentRequirement().getReleasedBy())){
					User releasedUser = userService.getUserById(Long.parseLong(requirement.getParentRequirement().getReleasedBy()));
					requirement.setSubmitToClientToMailId(releasedUser.getEmail());
				}else{
					requirement.setSubmitToClientToMailId(requirement.getSpocId().getClientUser().getEmail());
				}
				StringBuilder subjectSB = new StringBuilder();
				if(null != requirement.getCity() && !"".equalsIgnoreCase(requirement.getCity())){
					requirement.setSubmitToClientMailSubject(subjectSB.append("Applicants received for the").append(" ").append(requirement.getJobTitle()).append("/").append(requirement.getCity()).append(",").append(requirement.getState()).append(",").append(requirement.getCountry()).toString());	
				}else if(null != requirement.getState() && !"".equalsIgnoreCase(requirement.getState())){
					requirement.setSubmitToClientMailSubject(subjectSB.append("Applicants received for the").append(" ").append(requirement.getJobTitle()).append("/").append(requirement.getState()).append(",").append(requirement.getCountry()).toString());	
				}else if(null != requirement.getCountry() && !"".equalsIgnoreCase(requirement.getCountry())){
					requirement.setSubmitToClientMailSubject(subjectSB.append("Applicants received for the").append(" ").append(requirement.getJobTitle()).append("/").append(requirement.getCountry()).toString());
				}else {
					requirement.setSubmitToClientMailSubject(subjectSB.append("Applicants received for the").append(" ").append(requirement.getJobTitle()).toString());
				}
		
				
				 List<String> requirementLocationsList = new ArrayList<>();
					List<RequirementLocations> locationsList;
					String location = "";
					StringBuilder requirementBuilder=new StringBuilder();
						locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
						if(locationsList != null && !locationsList.isEmpty()) {
							for(RequirementLocations locationsJob : locationsList) {
								requirementLocationsList.add(locationsJob.getLocation());
								if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
									location = requirementBuilder.append(locationsJob.getLocation()).append("</br>").toString();
								}else{
									location = "--";
								}
							}
						}
					
					
				if (requirement != null && requirement.getParentRequirement() != null) {
					if(requirement != null && requirement.getParentRequirement().getManagerUserId() != null){
						sb.append("<p>").append("Hi,").append(requirement.getParentRequirement().getManagerUserId().getFullName()).append("</p>")
						.append("<p>").append(fullName).append(" submitted the applicants for the").append(" ")
						.append(requirement.getJobTitle()).append("</p>")
						.append("<p>").append("Total no.of submitted applicants :").append(submissions.length).append("<p>");
						}
					     sb.append("<p>").append("Location :").append(location).append("<p>");
					   
						if(null !=requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp())){
						sb.append("<p>").append("Experience :").append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).append("Years").append("<p>");
						}else if(null !=requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp())){
						sb.append("<p>").append("Experience :").append(requirement.getMinExp()).append("Years").append("<p>");
						}else{
						sb.append("<p>").append("Experience :").append("--").append("<p>");	
						}
						if(null !=requirement.getSkills()){
						sb.append("<p>").append("Skills :").append(requirement.getSkills()).append("<p>");
						}else{
						sb.append("<p>").append("Skills :").append("--").append("<p>");	
						}
						sb.append("<p>").append("Best Regards,").append("</p>")
						.append("<p>").append(fullName).append("</p>");
						requirement.setMailBody(sb.toString());
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
								}sb.append("<p>").append(fullName).append(" submitted the applicants for the").append(" ").append(requirement.getJobTitle()).append("</p>")
								.append("<p>").append("Total no.of submitted applicants :").append(submissions.length).append("</p>");
								sb.append("<p>").append("Location :").append(location).append("<p>");
								if(null !=requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp())){
								sb.append("<p>").append("Experience :").append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).append("Years").append("<p>");
								}else if(null !=requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp())){
								sb.append("<p>").append("Experience :").append(requirement.getMinExp()).append("Years").append("<p>");
								}else{
								sb.append("<p>").append("Experience :").append("--").append("<p>");	
								}
								if(null !=requirement.getSkills()){
								sb.append("<p>").append("Skills :").append(requirement.getSkills()).append("<p>");
								}else{
								sb.append("<p>").append("Skills :").append("--").append("<p>");	
								}sb.append("<p>").append("Best Regards,").append("</p>")
								.append("<p>").append(fullName).append("</p>");
								requirement.setMailBody(sb.toString());
						}
					}
				}
				response.setObject(requirement);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	/**
	 * author : Pavan Jampana
	 * desc : This method is used to Reactivate the Rejected Applicant
	 * from Requirement Pipeline List from any Stage.
	 */

	@RequestMapping(value = "/reActivateRejectedApplicant")
	public @ResponseBody String reActivateApplicant(
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "type", required = false) String type,HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

		// to update submission history
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		if(ApplicantSubmissionStatus.REJECTED.getValue().equalsIgnoreCase(type)){
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REJECTED.getValue());
		}else if(ApplicantSubmissionStatus.BACKOUT.getValue().equalsIgnoreCase(type)){
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.BACKOUT.getValue());
		}
		
		if(requirementSubmission.getPreviousSubmissionStatus()!=null){
		requirementSubmissionHistory.setToStage(requirementSubmission.getPreviousSubmissionStatus().toString());
		}
		
		if ("Y".equalsIgnoreCase(requirementSubmission.getNeedApplicantPermit())
				&& requirementSubmission.getPreviousSubmissionStatus() != null
				&& ApplicantSubmissionStatus.REQUEST_SENT.getValue().equalsIgnoreCase(requirementSubmission.getPreviousSubmissionStatus())
				&& requirementSubmission.getSubmissionStatus() != null
				&& ApplicantSubmissionStatus.REJECTED.getValue().equalsIgnoreCase(requirementSubmission.getSubmissionStatus())) {
			requirementSubmissionServiceImpl.applicantReceiveMailAboutRequirement(requirementSubmission.getId(), requirementSubmission.getRequirement(), requirementSubmission.getCandidate(), user, orgLocations);
		}

		// to update Submission status
		requirementSubmission.setSubmissionStatus(requirementSubmission.getPreviousSubmissionStatus());
		requirementSubmission.setSubmissionRateStatus(null);
		requirementSubmission.setPreviousSubmissionStatus(null);
		requirementSubmission.setUpdatedBy(user.getUserCode());
		requirementSubmission.setUpdatedDate(new Date());
		requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		
		return "success";
	}
	
	/**
	 * 
	 * author : Pavan Jampana desc : This method is used to Backout the Applicant
	 * from Requirement Pipeline List from Offer Accept Stage.
	 */


	@RequestMapping(value = "/backoutApplicant")
	public @ResponseBody String backoutApplicant(ApplicantRejectBackOutDTO applicantRejectBackOutDTO,
			@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

		// to update submission history
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
		requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.BACKOUT.toString());

		// to update Submission status
		requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
		
		if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getReasonForbackOut())) {
			requirementSubmission.setBackOutReason(applicantRejectBackOutDTO.getReasonForbackOut());
		}
		if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getBackOutCategory())) {
			requirementSubmission.setBackOutCategoryType(applicantRejectBackOutDTO.getBackOutCategory());
		}
		if (StringUtils.isNotBlank(applicantRejectBackOutDTO.getBackOutCategoryOther())) {
			requirementSubmission.setBackOutCategoryOther(applicantRejectBackOutDTO.getBackOutCategoryOther());
		}
		
		requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.BACKOUT.toString());
		requirementSubmission.setUpdatedBy(user.getUserCode());
		StringBuilder sb = new StringBuilder();
		requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		requirementSubmission.setUpdatedDate(new Date());
		requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());

		return "success";
	}
	
	
	@RequestMapping(value = "/saveJoinedRequestForApplicant")
	public @ResponseBody Response<String> saveJoinedRequestForApplicant(ApplicantRejectBackOutDTO applicantRejectBackOutDTO,
			@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				response.setObject(commonUtil.saveJoinStatusInSubmission(applicantRejectBackOutDTO, submissionId, orgLocations, user));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value = "/saveSelectedOnInterviewStatusForApplicant")
	public @ResponseBody Response<String> saveSelectedOnInterviewStatusForApplicant(@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

				// to update submission history
				RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
				requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW.toString());

				// to update Submission status
				requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW.toString());
				requirementSubmission.setUpdatedBy(user.getUserCode());
				StringBuilder sb = new StringBuilder();
				requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				requirementSubmission.setUpdatedDate(new Date());
				
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value = "/saveShortlistStatusForApplicant")
	public @ResponseBody Response<String> saveShortlistStatusForApplicant(@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

				// to update submission history
				RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
				requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.SHORTLIST.toString());

				// to update Submission status
				requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SHORTLIST.toString());
				requirementSubmission.setUpdatedBy(user.getUserCode());
				StringBuilder sb = new StringBuilder();
				requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				requirementSubmission.setUpdatedDate(new Date());
				
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/editApplicantSourceName", method = RequestMethod.POST)
	public @ResponseBody Response<String> editApplicantSourceName(@RequestParam(value = "submissionId", required = false) Long submissionId, 
			@RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long locationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(locationId);
		Response<String> response =  new Response<>();
	 	if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
				User updatedUser = userService.getUserById(userId);
				requirementSubmission.setUpdatedDate(new Date());
				requirementSubmission.setUpdatedBy(user.getUserCode());
				requirementSubmission.setCreatedBy(updatedUser.getUserCode());
				requirementSubmission.setCreatedUser(updatedUser.getFullName());
				RequirementSubmission savedrRequirementSubmission = requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				List<RequirementSubmissionHistory> requirementSubmissionHistoryList= requirementSubmissionHistoryService.getRequirementHistoryByLocationIdAndSubId(orgLocations.getId(),savedrRequirementSubmission.getId());
				requirementSubmissionHistoryList.forEach(requirementSubmissionHistory->{
					try {
						requirementSubmissionHistory.setUpdatedBy(user.getUserCode());
						requirementSubmissionHistory.setUpdatedDate(new Date());
						requirementSubmissionHistory.setCreatedBy(requirementSubmission.getCreatedBy());
						requirementSubmissionHistory.setCreatedUser(updatedUser.getFullName());
						requirementSubmissionHistory.setMasterUserCode(requirementSubmission.getCreatedBy());
						requirementSubmissionHistoryDao.updateRequirementHistory(requirementSubmissionHistory);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				});
				// for URL redirections from mail
				String redirectURL = "/requirementSummary-params-requirementUUID="+savedrRequirementSubmission.getRequirement().getRequirementUUID();
				sendMailtoNewAssignedUser(user,updatedUser,savedrRequirementSubmission,orgLocations,redirectURL);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	public void sendMailtoNewAssignedUser(User loginUser,User toUser,RequirementSubmission requirementSubmission ,OrgLocations orgLocations,String redirectURL) throws Exception{
		
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		
			globalVarsMap.put("jobTitle", requirementSubmission.getRequirement().getJobTitle());
		
			globalVarsMap.put("applicantName", requirementSubmission.getCandidate().getFullName());
	
			globalVarsMap.put("assignedUser", toUser.getFullName());
			
			globalVarsMap.put("loginUser", loginUser.getFullName());
			
			globalVarsMap.put("loginOrg", orgLocations.getOrg().getOrgName());
		
		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("viewApplicant", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+orgLocations.getId()).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Applicant</div></a>")
				.toString());
		
	
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.APPLICANT_SOURCE_CHANGE_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		StringBuilder actionLinkStringBuilder = new StringBuilder();
		
		String actionLink = actionLinkStringBuilder.append(atsUrl).append("/requirementSummary?requirementUUID=")
				.append(requirementSubmission.getRequirement().getRequirementUUID()).toString();
	
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, orgLocations, notificationScenario,
				new HashMap<String, String>());
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.APPLICANT_SOURCE_CHANGE_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());
		
	}
	
	@RequestMapping(value = "/getRequirementOfferSubmissionById")
	public @ResponseBody Response<RequirementOffers> getRequirementOfferSubmissionById(@RequestParam(value = "submissionId", required = false) Long submissionId, HttpServletRequest request)
			throws Exception {
		Response<RequirementOffers> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(submissionId);
				if(requirementOffers != null) {
					requirementOffers.setUpdatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementOffers.getUpdatedDate()));
					response.setObject(requirementOffers);
				}else {
					response.setObject(null);
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	
	@RequestMapping(value = "/saveOnHoldStatusForApplicant")
	public @ResponseBody Response<String> saveOnHoldStatusForApplicant(@RequestParam(value = "submissionId", required = false) Long submissionId, @RequestParam(value = "status", required = false) String status,HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);

				// to update submission history
				RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
				requirementSubmissionHistory.setFromStage(requirementSubmission.getSubmissionStatus().toString());
		if(status.equalsIgnoreCase("Status")){
					
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.SUBMISSION.toString());

				}
		else if(status.equalsIgnoreCase("On Hold")){
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.ONHOLD.toString());
		}
		else if(status.equalsIgnoreCase("Interview")){
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.INTERVIEW.toString());
		}
		else if(status.equalsIgnoreCase("Selected on Interview")){
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW.toString());
		}
		else if(status.equalsIgnoreCase("Offer Made")){
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.OFFERMADE.toString());
		}
		else if(status.equalsIgnoreCase("NoResponse")){
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.NORESPONSE.toString());
		}

				// to update Submission status
				requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
				if(status.equalsIgnoreCase("Status")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION.toString());
				}
				else if(status.equalsIgnoreCase("On Hold")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.ONHOLD.toString());
				}
				else if(status.equalsIgnoreCase("Interview")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW.toString());
				}
				else if(status.equalsIgnoreCase("Selected on Interview")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW.toString());
				}
				else if(status.equalsIgnoreCase("Offer Made")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE.toString());
				}
				else if(status.equalsIgnoreCase("NoResponse")){
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.NORESPONSE.toString());
				}
				requirementSubmission.setUpdatedBy(user.getUserCode());
				StringBuilder sb = new StringBuilder();
				requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				requirementSubmission.setUpdatedDate(new Date());
				
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
				requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

}
