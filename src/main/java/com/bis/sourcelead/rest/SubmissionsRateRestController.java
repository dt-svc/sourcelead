package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
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
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/storeReject", method = RequestMethod.POST)
	public @ResponseBody String storeReject(@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "rejectreason", required = false) String rejectreason) throws Exception {
		 
		//this logic is for update status 
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
		requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
		
		//this logic is for mail 
		Requirement requirement = requirementSubmission.getRequirement();
		User initiatorUser = requirementSubmission.getOrgLocations().getUser();
		Candidate candidate = requirementSubmission.getCandidate();
		OrgLocations orgLocations = requirementSubmission.getOrgLocations();
		requirementSubmissionService.applicantRejectMailAboutRequirement(requirement,initiatorUser,candidate,orgLocations,rejectreason);
		return "success";
	}
	
	@RequestMapping(value = "/storeNegotiation", method = RequestMethod.POST)
	public @ResponseBody String storeNegotiation(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "submissionId", required = false) Long submissionId) throws Exception {
	 
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
		
		//RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		
		requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
		if(jsonObj.has("rateProposal")){
			requirementRateSubmissionNegotiation.setExpectedRate(jsonObj.getString("rateProposal"));
		}
		if(jsonObj.has("rateType")){
			requirementRateSubmissionNegotiation.setExpectedRateType(jsonObj.getString("rateType"));
		}
		if(pastRequirementRateSubmissionNegotiation != null){
			requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
			requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
		}
		requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
		requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
		requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
		requirementRateSubmissionNegotiation.setRateReason("rate is changed due to more expectations");
		requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
		requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
		requirementRateSubmissionNegotiation.setCreatedBy("");
		requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
		requirementRateSubmissionNegotiation.setUpdatedBy("");
		requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
		
		
		
		/*requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
		if(jsonObj.has("rateProposal")){
			requirementRateSubmissionNegotiation.setCurrentRate(jsonObj.getString("rateProposal"));
		}
		if(jsonObj.has("rateType")){
			requirementRateSubmissionNegotiation.setCurrentRateType(jsonObj.getString("rateType"));
		}
		requirementRateSubmissionNegotiation.setCreatedDate(new Date());
		requirementRateSubmissionNegotiation.setCreatedBy("");
		requirementRateSubmissionNegotiation.setUpdatedDate(new Date());
		requirementRateSubmissionNegotiation.setUpdatedBy("");
		
		requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);*/
	
		 Requirement requirement = requirementSubmission.getRequirement();
		 User initiatorUser = requirementSubmission.getOrgLocations().getUser();
		 Candidate candidate = requirementSubmission.getCandidate();
		 OrgLocations orgLocations = requirementSubmission.getOrgLocations();
		 RequirementRateSubmissionNegotiation neogtation = requirementSubmissionService.getRequirementRateBySubmissionById(submissionId);
		requirementSubmissionService.applicantNegotiateEmail(requirement,initiatorUser,candidate,orgLocations,neogtation);
		
		return "success";
	}
	
	
	@RequestMapping(value = "/getRateHistoryBySubmissionId", method = RequestMethod.POST)
	public @ResponseBody String getRateHistoryBySubmissionId(@RequestParam(value = "submissionId", required = false) Long submissionId,
															 @RequestParam(value = "candidateId", required = false) Long candidateId) throws Exception {
		 
		//to get rate history of a requirement
		List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,candidateId);
		for(RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation : allSubmissionRateHistory){
		
			DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
			String commonDateFormat = standardFormat.format(requirementRateSubmissionNegotiation.getCreatedDate());
			requirementRateSubmissionNegotiation.setCreatedDateStr(commonDateFormat.toString());
			
			if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CANDIDATE){
				requirementRateSubmissionNegotiation.setProposeByName(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getFirstName());
			}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CLIENT || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG){
				User proposedUser = userService.getUserDetailsByUserCode(requirementRateSubmissionNegotiation.getCreatedBy());
				requirementRateSubmissionNegotiation.setProposeByName(proposedUser.getFirstName());
			}
			
			requirementRateSubmissionNegotiation.setRateAndType(requirementRateSubmissionNegotiation.getExpectedRate()+" "+requirementRateSubmissionNegotiation.getExpectedRateType());
		}
		
		String resultJson = commonUtil.toJSON(allSubmissionRateHistory);
		 return resultJson;
	}
	
	
	/**
	  * Propose New Rate in Submission
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/proposeRate",  method = RequestMethod.POST)
		public @ResponseBody  String proposeRate(@RequestParam(value="json", required=false) String json,
				@RequestParam(value = "submissionId", required = false) Long submissionId,
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
		   String  json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	
		 	
		 	RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
			
			RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
			
			requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
			if(jsonObj.has("rateProposal")){
				requirementRateSubmissionNegotiation.setExpectedRate(jsonObj.getString("rateProposal"));
			}
			if(jsonObj.has("rateType")){
				requirementRateSubmissionNegotiation.setExpectedRateType(jsonObj.getString("rateType"));
			}
			if(jsonObj.has("rateComment")){
				requirementRateSubmissionNegotiation.setRateReason(jsonObj.getString("rateComment"));
			}
			if(pastRequirementRateSubmissionNegotiation != null){
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
			}
			
			if(requirementSubmission.getVendorLocationId() != null && requirementSubmission.getVendorLocationId() == loginOrgLocation){
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
			}else if(requirementSubmission.getVendorLocationId() != null &&  requirementSubmission.getVendorLocationId() != loginOrgLocation){
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CLIENT);
			}else{
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
			}
			
			requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
			requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
			requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
			requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
			requirementRateSubmissionNegotiation.setCreatedBy(users.getUserCode());
			requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
			requirementRateSubmissionNegotiation.setUpdatedBy(users.getUserCode());
			requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
			
			return "SUCCESS";
		}
	
	
	 /**
	  * accept Reject Submission Rate
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/acceptRejectRate",  method = RequestMethod.POST)
		public @ResponseBody  String acceptRejectRate(@RequestParam(value="submissionId", required=false) Long submissionId,
				@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	
		 	
		 	RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
			
			RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
			
			requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
			
			if(pastRequirementRateSubmissionNegotiation != null){
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setExpectedRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
			}
			
			if(requirementSubmission.getVendorLocationId() != null && requirementSubmission.getVendorLocationId() == loginOrgLocation){
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.VENDOR);
			}else if(requirementSubmission.getVendorLocationId() != null &&  requirementSubmission.getVendorLocationId() != loginOrgLocation){
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CLIENT);
			}else{
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
			}
			
			requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(loginOrgLocation);
			requirementRateSubmissionNegotiation.setRateReason("");
			if(status.equalsIgnoreCase("Accepted")){
				requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.ACCEPTED);
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
			
			requirementSubmission.setFinalizedRate(requirementRateSubmissionNegotiation.getExpectedRate());
			requirementSubmission.setFinalizedRateType(requirementRateSubmissionNegotiation.getExpectedRateType());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			return "SUCCESS";
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

