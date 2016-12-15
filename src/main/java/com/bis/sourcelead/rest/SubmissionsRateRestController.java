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
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@RequestMapping(value = "/storeReject", method = RequestMethod.POST)
	public @ResponseBody String storeReject(@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "rejectreason", required = false) String rejectreason) throws Exception {
		 
		//this logic is for update status 
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
		requirementSubmission.setRejectedReason(rejectreason);
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
	public @ResponseBody String storeNegotiation(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "submissionId", required = false) Long submissionId,@RequestParam(value = "type", required = false) String type) throws Exception {
	 
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
		
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
		
		requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
		requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.NEWRATE);
		requirementRateSubmissionNegotiation.setRateReason("rate is changed due to more expectations");
		requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
		requirementRateSubmissionNegotiation.setCreatedDate(commonUtil.currentDate());
		if("user".equalsIgnoreCase(type)){
			requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
			requirementRateSubmissionNegotiation.setCreatedBy(user.getUserCode());
			requirementRateSubmissionNegotiation.setUpdatedBy(user.getUserCode());
		}else{
			requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
			requirementRateSubmissionNegotiation.setCreatedBy("");
			requirementRateSubmissionNegotiation.setUpdatedBy("");
		}
		requirementRateSubmissionNegotiation.setUpdatedDate(commonUtil.currentDate());
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
		 
		 Candidate candidate = requirementSubmission.getCandidate();
		 OrgLocations orgLocations = requirementSubmission.getOrgLocations();
		 RequirementRateSubmissionNegotiation neogtation = requirementSubmissionService.getRequirementRateBySubmissionById(submissionId);
		 
		 List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
		 StringBuilder tempHTML = new StringBuilder();
		 if(negotiationList != null){
	         tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
	         tempHTML.append("<tr>");
	         tempHTML.append("<th> Requirement Title </th>");
	         tempHTML.append("<th> Requirement Rate </th>");
	         tempHTML.append("<th> Rate </th>");
	         tempHTML.append("<th> By </th>");
	         //tempHTML.append("<th> Action </th>");
	         tempHTML.append("</tr>");
	        	 for (RequirementRateSubmissionNegotiation negotiations : negotiationList) {
			         tempHTML.append("<tr>");
			         tempHTML.append("<td>"+requirement.getJobTitle()+"</td>");
			         tempHTML.append("<td>"+requirement.getRate()+"</td>");
			         tempHTML.append("<td>"+negotiations.getExpectedRate()+"</td>");
			         if(negotiations.getCreatedBy() != null && "" != negotiations.getCreatedBy()){
			        	 User proposedUser = userService.getUserByUserCode(negotiations.getRequirementsubmissionId().getCreatedBy());
			        	 tempHTML.append("<td>"+proposedUser.getFirstName()+" "+proposedUser.getLastName()+"</td>");
			         }else{
			        	 tempHTML.append("<td>"+candidate.getFirstName()+" "+candidate.getLastName()+"</td>");
			         }
			         tempHTML.append("</tr>");
	            }
		        tempHTML.append("</table> ");
		        
		        if("user".equalsIgnoreCase(type)){
		        	//these links are provided in user to applicant Negotiate email
		        	tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Accepted&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
			        tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Rejected&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");
			        tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>");
		        }else{
		        	//these links are provided in applicant to user Negotiate email
		        	tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateUserRates?rateStatus=Accepted&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#61D339 ; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Accept</div></a>");
			        tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateUserRates?rateStatus=Rejected&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reject</div></a>");
			        tempHTML.append("<a href='"+sourceleadUrl+"/"+"acceptOrRejectOrRateNegotiateUserRates?rateStatus=Negotiate&submissionId="+submissionId+"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#D33B39; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Negotiate Rate</div></a>");
		        }
		        
		 	}
		 if("user".equalsIgnoreCase(type)){
			 requirementSubmissionService.userNegotiateEmail(requirement,userRole.getUser(),candidate,orgLocations,neogtation,tempHTML);
		 }else{
			 requirementSubmissionService.applicantNegotiateEmail(requirement,userRole.getUser(),candidate,orgLocations,neogtation,tempHTML);
		 }
		
		
		return "success";
	}
	
	
	@RequestMapping(value = "/getRateHistoryBySubmissionId", method = RequestMethod.POST)
	public @ResponseBody String getRateHistoryBySubmissionId(@RequestParam(value = "submissionId", required = false) Long submissionId,
															 @RequestParam(value = "candidateId", required = false) Long candidateId
															) throws Exception {
		 
		//to get rate history of a requirement
		List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,candidateId);
		for(RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation : allSubmissionRateHistory){
			
			RequirementRateSubmissionNegotiation rate = allSubmissionRateHistory.get(allSubmissionRateHistory.size()-1);
			if(rate != null){
				if(rate.getCreatedBy() != null && "" != rate.getCreatedBy()){
					requirementRateSubmissionNegotiation.setPreviouslyProposedBy("User");
				}else{
					requirementRateSubmissionNegotiation.setPreviouslyProposedBy("Candidate");
				}
			}
		
			requirementRateSubmissionNegotiation.setRequirementName(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement().getJobTitle());
			requirementRateSubmissionNegotiation.setRequirementRate(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getRequirement().getRate());
			
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
			DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
			String commonDateFormat = standardFormat.format(requirementRateSubmissionNegotiation.getCreatedDate());
			requirementRateSubmissionNegotiation.setCreatedDateStr(commonDateFormat.toString());
			
			if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CANDIDATE){
				requirementRateSubmissionNegotiation.setProposeByName(requirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getFirstName());
			}else if(requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CLIENT || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.VENDOR || requirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG){
				User proposedUser = userService.getUserDetailsByUserCode(requirementRateSubmissionNegotiation.getCreatedBy());
				requirementRateSubmissionNegotiation.setProposeByName(proposedUser.getFirstName());
			}
			
			requirementRateSubmissionNegotiation.setInitiatorOrgLocationIdStr(requirementRateSubmissionNegotiation.getInitiatorOrgLocationId().getId().toString());
			
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

