package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementRateSubmissionNegotiationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.UserRoleService;
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
@RequestMapping("/app")
public class SubmissionsRateRestController {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private UserService userService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	private OrgLocationService orgLocationService; 
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/ws/storeReject", method = RequestMethod.POST)
	public @ResponseBody String storeReject(@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "rejectreason", required = false) String rejectreason,@RequestParam(value = "type", required = false) String type) throws Exception {
		 
		//this logic is for update status 
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
		
		List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,requirementSubmission.getCandidate().getId());
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
		
		String permission = "YES";
		if(null != pastRequirementRateSubmissionNegotiation && (pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.ACCEPTED || pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.REJECTED) && (pastRequirementRateSubmissionNegotiation.getCreatedBy() == "" || null == pastRequirementRateSubmissionNegotiation.getCreatedBy())){
			permission = "NO";
		}else if(null != requirementSubmission && (requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.REJECTED) || requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.SUBMISSION))){
			permission = "NO";
		}else if(pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null && pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode().equals(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
			permission = "YES";
		}
//		if(permission.equalsIgnoreCase("YES")){
			if(null != allSubmissionRateHistory && !allSubmissionRateHistory.isEmpty()){
				requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
				requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setExpectedRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.REJECTED);
				requirementRateSubmissionNegotiation.setRateReason(rejectreason);
				if("user".equalsIgnoreCase(type)){
					requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
					requirementRateSubmissionNegotiation.setCreatedBy(user.getUserCode());
					requirementRateSubmissionNegotiation.setUpdatedBy(user.getUserCode());
				}else{
					requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
					if(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null){
						requirementRateSubmissionNegotiation.setCreatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode());
						requirementRateSubmissionNegotiation.setUpdatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode());
					}else{
						requirementRateSubmissionNegotiation.setCreatedBy("");
						requirementRateSubmissionNegotiation.setUpdatedBy("");
					}
				}
				requirementRateSubmissionNegotiation.setCreatedDate(new Date());
				requirementRateSubmissionNegotiation.setUpdatedDate(new Date());
				requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
				requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
				//to update requirement submission
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
				requirementSubmission.setSubmissionRateComment(rejectreason);
			}else{
				if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
					requirementSubmission.setPreviousSubmissionStatus(requirementSubmission.getSubmissionStatus());
					requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
					requirementSubmission.setRejectedReason(rejectreason);
				}
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
				requirementSubmission.setSubmissionRateComment(rejectreason);
			}
			StringBuilder sb = new StringBuilder();
			
			// if candidate reject the requirement updated by name is candidate
			Candidate candidate = requirementSubmission.getCandidate();
			if("Candidate".equalsIgnoreCase(type)){
			requirementSubmission.setUpdatedUser(sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
			}else{
			requirementSubmission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			}
			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
			requirementSubmissionHistory.setFromStage("NEW");
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			
			//this logic is for mail 
			Requirement requirement = requirementSubmission.getRequirement();
			
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
			StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirement,candidate,submissionId,type);
			if("user".equalsIgnoreCase(type)){
				requirementSubmissionService.userRejectRateNegotiation(requirement,userRole.getUser(),candidate,orgLocations,requirementRateSubmissionNegotiation,rejectreason,tempHTML);
			}else{
				requirementSubmissionService.applicantRejectMailAboutRequirement(requirement,userRole.getUser(),candidate,orgLocations,rejectreason,tempHTML,requirementRateSubmissionNegotiation,submissionId);
			}
			
			return "success";
		}

//	}
	
	@RequestMapping(value = "/ws/storeNegotiation", method = RequestMethod.POST)
	public @ResponseBody Response<String> storeNegotiation(RequirementRateSubmissionNegotiationDTO reqRateSubNegotiationDTO,@RequestParam(value = "submissionId", required = false) String submissionId,@RequestParam(value = "type", required = false) String type) throws Exception {
	 
		Response<String> response = new Response<>();
		try {
			RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
			
			RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			
			//RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long.parseLong(submissionId));
			
			requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
			if(StringUtils.isNoneBlank(reqRateSubNegotiationDTO.getRateProposal())){
				requirementRateSubmissionNegotiation.setExpectedRate(reqRateSubNegotiationDTO.getRateProposal());
			}
			if(StringUtils.isNoneBlank(reqRateSubNegotiationDTO.getRateType())){
				requirementRateSubmissionNegotiation.setExpectedRateType(reqRateSubNegotiationDTO.getRateType());
			}
			if(null != pastRequirementRateSubmissionNegotiation){
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
			}
			
			requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
			requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
			if(StringUtils.isNoneBlank(reqRateSubNegotiationDTO.getComment())){
				requirementRateSubmissionNegotiation.setRateReason(reqRateSubNegotiationDTO.getComment().trim());
			}
			
			requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
			requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
			if("user".equalsIgnoreCase(type)){
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
				if(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null && requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode().equalsIgnoreCase(user.getUserCode())){
					requirementRateSubmissionNegotiation.setCreatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement().getManagerUserId().getUserCode());
					requirementRateSubmissionNegotiation.setUpdatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement().getManagerUserId().getUserCode());
				}else{
					requirementRateSubmissionNegotiation.setCreatedBy(user.getUserCode());
					requirementRateSubmissionNegotiation.setUpdatedBy(user.getUserCode());
				}
			}else{
				if(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null){
					requirementRateSubmissionNegotiation.setCreatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode());
					requirementRateSubmissionNegotiation.setUpdatedBy(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode());
				}else{
					requirementRateSubmissionNegotiation.setCreatedBy("");
					requirementRateSubmissionNegotiation.setUpdatedBy("");
				}
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
			}
			requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
			requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
			
			/*if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
				requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			}*/
			
			 Requirement requirement = requirementSubmission.getRequirement();
			 
			 Candidate candidate = requirementSubmission.getCandidate();
			 OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			 RequirementRateSubmissionNegotiation neogtation = requirementSubmissionService.getRequirementRateBySubmissionById(Long.parseLong(submissionId));
			 
			 List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(Long.parseLong(submissionId), candidate.getId());
			 StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirement,candidate,Long.parseLong(submissionId),type);
			 if("user".equalsIgnoreCase(type)){
				 if(userRole != null && userRole.getUser() != null){
					 requirementSubmissionService.userNegotiateEmail(requirement,userRole.getUser(),candidate,orgLocations,neogtation,tempHTML); 
				 }else{
					 requirementSubmissionService.userNegotiateEmail(requirement,requirement.getManagerUserId(),candidate,orgLocations,neogtation,tempHTML);
				 }
			 }else{
				 if(userRole != null && userRole.getUser() != null){
					 requirementSubmissionService.applicantNegotiateEmail(requirement,userRole.getUser(),candidate,orgLocations,neogtation,tempHTML); 
				 }else{
					 requirementSubmissionService.applicantNegotiateEmail(requirement,requirement.getManagerUserId(),candidate,orgLocations,neogtation,tempHTML);
				 }
				 
			 }
			 response.setResponse(Result.SUCCESS.name());
			 response.setResponseMessage(Result.SUCCESS.getValue());
		}catch(Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		 return response;
	}
	
	
	@RequestMapping(value = "/ws/getRateHistoryBySubmissionIdPublic", method = RequestMethod.POST)
	public @ResponseBody ResponseList<RequirementRateSubmissionNegotiation> getRateHistoryBySubmissionIdPublic(@RequestParam(value = "submissionId", required = false) Long submissionId,
															 @RequestParam(value = "candidateId", required = false) Long candidateId,
															 @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
															 HttpServletRequest request) throws Exception {
		ResponseList<RequirementRateSubmissionNegotiation> response = new ResponseList<>();
		try {
			List<RequirementRateSubmissionNegotiation> requirementRateSubmissionNegotiationList = getNegotiationListHistoryCommon(submissionId, candidateId, orgLocationId, request );
			response.setData(requirementRateSubmissionNegotiationList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}catch(Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	

	
	@RequestMapping(value = "/getRateHistoryBySubmissionId", method = RequestMethod.POST)
	public @ResponseBody ResponseList<RequirementRateSubmissionNegotiation> getRateHistoryBySubmissionId(@RequestParam(value = "submissionId", required = false) Long submissionId,
															 @RequestParam(value = "candidateId", required = false) Long candidateId,
															 @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
															 HttpServletRequest request) throws Exception {
		ResponseList<RequirementRateSubmissionNegotiation> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				List<RequirementRateSubmissionNegotiation> requirementRateSubmissionNegotiationList = getNegotiationListHistoryCommon(submissionId, candidateId, orgLocationId, request );
				response.setData(requirementRateSubmissionNegotiationList);
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
	  * Propose New Rate in Submission
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/proposeRate",  method = RequestMethod.POST)
		public @ResponseBody  Response<String> proposeRate(RequirementRateSubmissionNegotiationDTO requirementRateSubmissionNegotiationDTO,
				@RequestParam(value = "submissionId", required = false) Long submissionId,
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
			 	Response<String> response = new Response<>();
			 	Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	if(users != null) {
			 		try {
					 	Candidate candidate = new Candidate();
					 	User pastUser = new User();
					 	RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
						
						RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
						RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
						
						requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
						if(StringUtils.isNotEmpty(requirementRateSubmissionNegotiationDTO.getRateProposal())){
							requirementRateSubmissionNegotiation.setExpectedRate(requirementRateSubmissionNegotiationDTO.getRateProposal());
						}
						if(StringUtils.isNotEmpty(requirementRateSubmissionNegotiationDTO.getRateType())){
							requirementRateSubmissionNegotiation.setExpectedRateType(requirementRateSubmissionNegotiationDTO.getRateType());
						}
						if(StringUtils.isNotEmpty(requirementRateSubmissionNegotiationDTO.getRateComment())){
							requirementRateSubmissionNegotiation.setRateReason(requirementRateSubmissionNegotiationDTO.getRateComment());
						}
						if(null != pastRequirementRateSubmissionNegotiation){
							requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
							requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
						}
						
						if(null != requirementSubmission.getVendorLocationId() && requirementSubmission.getVendorLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
							requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
							candidate = requirementSubmission.getCandidate();
							if(pastRequirementRateSubmissionNegotiation != null && !isNullOrEmpty(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
								pastUser = userService.getUserByUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
							}
						}else if(null != requirementSubmission.getVendorLocationId() &&  requirementSubmission.getVendorLocationId().getId().longValue() != loginOrgLocation.getId().longValue()){
							requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CLIENT);
							candidate = requirementSubmission.getCandidate();
							if(pastRequirementRateSubmissionNegotiation != null && !isNullOrEmpty(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
								pastUser = userService.getUserByUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
							}
						}else if(requirementSubmission.getCandidate() != null && requirementSubmission.getCandidate().getConsultantUser() != null && requirementSubmission.getCandidate().getConsultantUser().getId().equals(users.getId())){
							requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
							candidate = requirementSubmission.getCandidate();
							if(pastRequirementRateSubmissionNegotiation != null && !isNullOrEmpty(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
								pastUser = userService.getUserByUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
							}
						}else{
							requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
							candidate = requirementSubmission.getCandidate();
						}
						
						requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
						requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
						requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
						requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
						requirementRateSubmissionNegotiation.setCreatedBy(users.getUserCode());
						requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
						requirementRateSubmissionNegotiation.setUpdatedBy(users.getUserCode());
						requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
						
						
						
						//UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(pastUser.getId(), requirementSubmission.getOrgLocations().getId());
						//Requirement requirement = requirementSubmission.getRequirement();
						 
						 if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR){
							 List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,new Long(candidateId));
							 StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),candidate,submissionId,"Vendor");
							 requirementSubmissionService.sendEmailtoClient(loginOrg,users,pastUser,requirementSubmission,candidate,tempHTML,loginOrgLocation);
						 }else{
							 RequirementRateSubmissionNegotiation neogtation = requirementSubmissionService.getRequirementRateBySubmissionById(submissionId);
							 List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
							 if(null != requirementSubmission.getVendorLocationId()){
								 StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),candidate,submissionId,"Client");
								 requirementSubmissionService.sendEmailtoVendor(loginOrg,users,pastUser,requirementSubmission,candidate,tempHTML,loginOrgLocation);
							 }else{
								 StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),candidate,submissionId,"user");
								 requirementSubmissionService.userNegotiateEmail(requirementSubmission.getRequirement(),users,candidate,loginOrgLocation,neogtation,tempHTML);
							 }
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
	
	
	 /**
	  * accept Reject Submission Rate
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/acceptRejectRate",  method = RequestMethod.POST)
		public @ResponseBody  Response<String> acceptRejectRate(@RequestParam(value="submissionId", required=false) Long submissionId,
				@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {
		 	Response<String> response = new Response<>();
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	if(users != null) {
		 		try {
			 		User pastUser = new User();
				 	String rejectreason = "";
				 	RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
					
					RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
					RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
					
					requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
					
					if(null != pastRequirementRateSubmissionNegotiation){
						requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
						requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
						requirementRateSubmissionNegotiation.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
						requirementRateSubmissionNegotiation.setExpectedRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
					}
					
					if(null != requirementSubmission.getVendorLocationId() && requirementSubmission.getVendorLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
						requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
						if(!isNullOrEmpty(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
							pastUser = userService.getUserByUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
						}
					}else if(null != requirementSubmission.getVendorLocationId() &&  requirementSubmission.getVendorLocationId().getId().longValue() != loginOrgLocation.getId().longValue()){
						requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CLIENT);
						if(!isNullOrEmpty(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
							pastUser = userService.getUserByUserCode(pastRequirementRateSubmissionNegotiation.getCreatedBy());
						}
					}else{
						requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
					}
					
					requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
					requirementRateSubmissionNegotiation.setRateReason("");
					if(status.equalsIgnoreCase("Accepted")){
						requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.ACCEPTED);
						requirementSubmission.setFinalizedRate(requirementRateSubmissionNegotiation.getExpectedRate());
						requirementSubmission.setFinalizedRateType(requirementRateSubmissionNegotiation.getExpectedRateType());
					}
					if(status.equalsIgnoreCase("Rejected")){
						requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.REJECTED);
					}
					
					requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
					requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
					requirementRateSubmissionNegotiation.setCreatedBy(users.getUserCode());
					requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
					requirementRateSubmissionNegotiation.setUpdatedBy(users.getUserCode());
					requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
					
					requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					
					if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG && "Accepted".equalsIgnoreCase(status)){
						Requirement requirement = requirementSubmission.getRequirement();
						Candidate candidate = requirementSubmission.getCandidate();
						OrgLocations orgLocations = requirementSubmission.getOrgLocations();
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirement,candidate,submissionId,"user");
						requirementSubmissionService.userAcceptRateNegotiation(requirement,users,candidate,orgLocations,requirementRateSubmissionNegotiation,tempHTML);
					}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG){
						Requirement requirement = requirementSubmission.getRequirement();
						Candidate candidate = requirementSubmission.getCandidate();
						OrgLocations orgLocations = requirementSubmission.getOrgLocations();
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirement,candidate,submissionId,"user");
						requirementSubmissionService.userRejectRateNegotiation(requirement,users,candidate,orgLocations,requirementRateSubmissionNegotiation,rejectreason,tempHTML);
					}
					
					if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CLIENT && "Accepted".equalsIgnoreCase(status)){
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, requirementSubmission.getCandidate().getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),requirementSubmission.getCandidate(),submissionId,"Client");
						String type = Constants.CLIENT;
						requirementSubmissionService.clientOrVendorAcceptOrRejectRateNegotiation(loginOrg,users,pastUser,requirementSubmission,requirementSubmission.getCandidate(),tempHTML,loginOrgLocation,type,status);
					}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CLIENT && "Rejected".equalsIgnoreCase(status)){
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, requirementSubmission.getCandidate().getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),requirementSubmission.getCandidate(),submissionId,"Client");
						String type = Constants.CLIENT;
						requirementSubmissionService.clientOrVendorAcceptOrRejectRateNegotiation(loginOrg,users,pastUser,requirementSubmission,requirementSubmission.getCandidate(),tempHTML,loginOrgLocation,type,status);
					}
					
					if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR && "Accepted".equalsIgnoreCase(status)){
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, requirementSubmission.getCandidate().getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),requirementSubmission.getCandidate(),submissionId,"Vendor");
						String type = Constants.VENDOR;
						requirementSubmissionService.clientOrVendorAcceptOrRejectRateNegotiation(loginOrg,users,pastUser,requirementSubmission,requirementSubmission.getCandidate(),tempHTML,loginOrgLocation,type,status);
					}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR && "Rejected".equalsIgnoreCase(status)){
						List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, requirementSubmission.getCandidate().getId());
						StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirementSubmission.getRequirement(),requirementSubmission.getCandidate(),submissionId,"Vendor");
						String type = Constants.VENDOR;
						requirementSubmissionService.clientOrVendorAcceptOrRejectRateNegotiation(loginOrg,users,pastUser,requirementSubmission,requirementSubmission.getCandidate(),tempHTML,loginOrgLocation,type,status);
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
	 
	 
	 
	 @RequestMapping(value = "/ws/storeApplicantRejectReason", method = RequestMethod.POST)
		public @ResponseBody String storeApplicantRejectReason(@RequestParam(value = "submissionId", required = false) Long submissionId,
				@RequestParam(value = "rejectreason", required = false) String rejectreason) throws Exception {
			 
			RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			
			if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.REJECTED);
			}
			
			if(null != requirementSubmissionTemplateFields.getFirstName() && null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getFirstName()) && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			 StringBuilder candBuild = new StringBuilder();
			 requirementSubmission.setUpdatedUser(candBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 requirementSubmission.setSubmissionRequestUser(candBuild.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
			 }else{
				 requirementSubmission.setUpdatedUser(requirementSubmissionTemplateFields.getFirstName());
				 requirementSubmission.setSubmissionRequestUser(requirementSubmissionTemplateFields.getFirstName());
			 }
			
			requirementSubmission.setRejectedReason(rejectreason);
			
			requirementSubmission.setRejectedCategoryType(Constants.APPLICANT_REJECTED);

			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			requirementSubmission.setPreviousSubmissionStatus(ApplicantSubmissionStatus.REQUEST_SENT);
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REQUEST_SENT.toString());
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			
			Requirement requirement = requirementSubmission.getRequirement();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantRejectMailAboutRequirement(requirement, user, requirementSubmission.getCandidate(), orgLocations, rejectreason, null, null, submissionId);
			return "success";
		}
	 
	 private List<RequirementRateSubmissionNegotiation> getNegotiationListHistoryCommon(Long submissionId, Long candidateId, Long orgLocationId, HttpServletRequest request ) throws Exception {
			User logInUsers = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
			RequirementRateSubmissionNegotiation requirementRateSubmission = new RequirementRateSubmissionNegotiation();
			String expectedRateRN = "";
			String expectedRateTypeRN = "";
			String expectedRatePerRN = "";
			OrgLocations orgLocations = null;
			if(requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement() !=null){
			 orgLocations = requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement().getOrgLocationId();	
			}else{
				orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			}
			
			
			//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
			if(null != requirementSubmissionTemplateFields){
					if(null  != requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement()){
						requirementRateSubmission.setRequirementName(requirementSubmissionTemplateFields.getRequirementSubmission().getRequirement().getJobTitle());
					}
					
					requirementRateSubmission.setExpectedRate(requirementSubmissionTemplateFields.getExpectedCTC());
					requirementRateSubmission.setExpectedRateType(requirementSubmissionTemplateFields.getExpectedRateType());
					requirementRateSubmission.setExpectedRatePer(requirementSubmissionTemplateFields.getExpectedRatePer());
					
					expectedRateRN = requirementSubmissionTemplateFields.getExpectedCTC();
					expectedRateTypeRN = requirementSubmissionTemplateFields.getExpectedRateType();
					expectedRatePerRN = requirementSubmissionTemplateFields.getExpectedRatePer();
					
					requirementRateSubmission.setExpectedRateRN(expectedRateRN);
					requirementRateSubmission.setExpectedRateTypeRN(expectedRateTypeRN);
					requirementRateSubmission.setExpectedRatePerRN(expectedRatePerRN);
					
					if(null != requirementSubmissionTemplateFields.getExpectedCTC() &&  null != requirementSubmissionTemplateFields.getExpectedRatePer()){
						StringBuilder rateAndType = new StringBuilder();
						requirementRateSubmission.setRateAndType(rateAndType.append(requirementSubmissionTemplateFields.getExpectedCTC()).append(" ").append(requirementSubmissionTemplateFields.getExpectedRatePer()).toString());	
					}
					if(requirementSubmissionTemplateFields.getCreatedBy() != null){
					User proposedByUser = userService.getUserDetailsByUserCode(requirementSubmissionTemplateFields.getCreatedBy());
					if(null != proposedByUser.getFirstName() && null != proposedByUser.getLastName() && !"".equalsIgnoreCase(proposedByUser.getFirstName()) && !"".equalsIgnoreCase(proposedByUser.getLastName())){
						 StringBuilder sb = new StringBuilder();
						 requirementRateSubmission.setProposeByName(sb.append(proposedByUser.getFirstName()).append(" ").append(proposedByUser.getLastName()).toString());
					}else{
						requirementRateSubmission.setProposeByName(proposedByUser.getFirstName());
					}
					}
					//DateFormat date = LoginSessionController.DATEFORMAT;
					String commonDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementSubmissionTemplateFields.getCreatedDate());
					requirementRateSubmission.setCreatedDateStr(commonDate.toString());
					requirementRateSubmission.setIsNeedApplicantPermit(requirementSubmissionTemplateFields.getRequirementSubmission().getNeedApplicantPermit());
			}
			
			//to get rate history of a requirement
			List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,candidateId);
			for(RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation : allSubmissionRateHistory){
				
				RequirementRateSubmissionNegotiation rate = allSubmissionRateHistory.get(allSubmissionRateHistory.size()-1);
				if(null != rate){
					if(null != rate.getCreatedBy() && "" != rate.getCreatedBy()){
						requirementRateSubmissionNegotiation.setPreviouslyProposedBy("User");
						if(rate.getRequirementsubmissionId().getCandidate() != null && rate.getRequirementsubmissionId().getCandidate().getConsultantUser() != null && rate.getCreatedBy().equalsIgnoreCase(rate.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode())){
							requirementRateSubmissionNegotiation.setPreviouslyProposedBy("Candidate");
						}
					}else{
						requirementRateSubmissionNegotiation.setPreviouslyProposedBy("Candidate");
					}
				}
			
				requirementRateSubmissionNegotiation.setRateStage(requirementRateSubmissionNegotiation.getRateStage());
				if(null != requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement()){
					requirementRateSubmissionNegotiation.setRequirementName(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement().getJobTitle());	
				}
				requirementRateSubmissionNegotiation.setRequirementRate(requirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setExpectedRateType(requirementRateSubmissionNegotiation.getExpectedRateType());
				
				/*if(requirementRateSubmissionNegotiation.getCreatedBy() != null && "" != requirementRateSubmissionNegotiation.getCreatedBy()){
		        	 User proposedUser = userService.getUserByUserCode(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCreatedBy());
		        	 requirementRateSubmissionNegotiation.setProposeByName(proposedUser.getFirstName()+" "+proposedUser.getLastName());
				}else{
					Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
					if(candidate.getLastName() != null){
						requirementRateSubmissionNegotiation.setProposeByName(candidate.getFirstName()+" "+candidate.getLastName());
					}else{
						requirementRateSubmissionNegotiation.setProposeByName(candidate.getFirstName());
					}
				}*/
				//DateFormat standardFormat = new SimpleDateFormat( "dd-MM-yyyy");
				String commonDateFormat = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementRateSubmissionNegotiation.getCreatedDate());
				requirementRateSubmissionNegotiation.setCreatedDateStr(commonDateFormat.toString());
				
				if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CANDIDATE){
					if(null != requirementSubmissionTemplateFields.getLastName() && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
						StringBuilder candiSB = new StringBuilder();
						requirementRateSubmissionNegotiation.setProposeByName(candiSB.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
					}else{
						requirementRateSubmissionNegotiation.setProposeByName(requirementSubmissionTemplateFields.getFirstName());
					}
					
				}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CLIENT || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG){
					User proposedUser = userService.getUserDetailsByUserCode(requirementRateSubmissionNegotiation.getCreatedBy());
					if(null != proposedUser){
						if(null != proposedUser.getLastName() && !"".equalsIgnoreCase(proposedUser.getLastName())){
							StringBuilder proposedSB = new StringBuilder();
							requirementRateSubmissionNegotiation.setProposeByName(proposedSB.append(proposedUser.getFirstName()).append(" ").append(proposedUser.getLastName()).toString());
						}else{
							requirementRateSubmissionNegotiation.setProposeByName(proposedUser.getFirstName());
						}
					}
				}
				
				requirementRateSubmissionNegotiation.setInitiatorOrgLocationIdStr(requirementRateSubmissionNegotiation.getInitiatorOrgLocationId().getId().toString());
				if( requirementRateSubmissionNegotiation.getExpectedRate() != null && requirementRateSubmissionNegotiation.getExpectedRateType() != null){
					StringBuilder rateTypeSB = new StringBuilder();
					requirementRateSubmissionNegotiation.setRateAndType(rateTypeSB.append(requirementRateSubmissionNegotiation.getExpectedRate()).append(" ").append(requirementRateSubmissionNegotiation.getExpectedRateType()).toString());	
				}
				
				if(requirementRateSubmissionNegotiation.getCreatedBy() != null && logInUsers != null && requirementRateSubmissionNegotiation.getCreatedBy().equalsIgnoreCase(logInUsers.getUserCode())){
					requirementRateSubmissionNegotiation.setIsLastProposedUser("Y");
				}else{
					requirementRateSubmissionNegotiation.setIsLastProposedUser("N");
				}
				
				
				if(null != requirementRateSubmissionNegotiation.getRequirementsubmissionId() && null != requirementRateSubmissionNegotiation.getRequirementsubmissionId().getSubmissionStatus()){
					requirementRateSubmissionNegotiation.setSubmissionRateStatus(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getSubmissionStatus().toString());
				}
				
				requirementRateSubmissionNegotiation.setIsNeedApplicantPermit(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getNeedApplicantPermit());
				requirementRateSubmissionNegotiation.setSubmissionStatus(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getSubmissionStatus().toString());
				
				requirementRateSubmissionNegotiation.setExpectedRateRN(expectedRateRN);
				requirementRateSubmissionNegotiation.setExpectedRateTypeRN(expectedRateTypeRN);
				requirementRateSubmissionNegotiation.setExpectedRatePerRN(expectedRatePerRN);
			}
			
			if(allSubmissionRateHistory.isEmpty()){
				allSubmissionRateHistory.add(requirementRateSubmission);
			}
			return allSubmissionRateHistory;
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

