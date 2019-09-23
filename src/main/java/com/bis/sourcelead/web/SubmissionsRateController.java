package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller  
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class SubmissionsRateController {
	
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
	UserRoleService userRoleService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
 
	/**
	 * this method is used to Accept or reject or to negotiate rate from Candidate
	 *
	 */
	@RequestMapping(value = "/ws/acceptOrRejectOrRateNegotiateApplicantRates")
	public ModelAndView acceptOrRejectOrRateNegotiateApplicantRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		
		String type = "Candidate";
		ModelAndView model = new ModelAndView();
		String permission = "YES";
		if((requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.SUBMISSION || requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REJECTED) && (pastRequirementRateSubmissionNegotiation != null && pastRequirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.CANDIDATE)){
			permission = "NO";
			/*if(pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null && pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode().equals(pastRequirementRateSubmissionNegotiation.getCreatedBy())){
				permission = "YES";
			}*/
		}
		model.addObject("permission",permission);
		model.addObject("rateStatus",rateStatus);
		model.addObject("type",type);
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus) && "YES".equalsIgnoreCase(permission)){
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
//			UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
			
			List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,requirementSubmission.getCandidate().getId());
			//RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
			RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			
			if(allSubmissionRateHistory != null && !allSubmissionRateHistory.isEmpty()){
				requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
				requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setExpectedRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.ACCEPTED);
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.CANDIDATE);
				requirementRateSubmissionNegotiation.setCreatedBy("");
				requirementRateSubmissionNegotiation.setUpdatedBy("");
				requirementRateSubmissionNegotiation.setCreatedDate(new Date());
				requirementRateSubmissionNegotiation.setUpdatedDate(new Date());
				requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
				requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
			}
			if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			}
			requirementSubmission.setFinalizedRate(requirementRateSubmissionNegotiation.getCurrentRate());
			requirementSubmission.setFinalizedRateType(requirementRateSubmissionNegotiation.getCurrentRateType());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);

			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			
			
			requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
			
			
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			//requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REQUEST_SENT.toString());
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			
			Requirement requirement = requirementSubmission.getRequirement();
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidate.getId());
			StringBuilder tempHTML = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList,requirement,candidate,submissionId,type);
			requirementSubmissionService.applicantAcceptMailAboutRateNegotiation(requirement,user,requirementSubmission,orgLocations,tempHTML);
			
		}
		return model;

	}
	
	/**
	 * This method is used to Accept or Reject or Negotiate Rate from User.
	 */
	@RequestMapping(value = "/ws/acceptOrRejectOrRateNegotiateUserRates")
	public ModelAndView acceptOrRejectOrRateNegotiateUserRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		String type = "User";
		ModelAndView model = new ModelAndView();
		String permission = "YES";
		if(pastRequirementRateSubmissionNegotiation != null && pastRequirementRateSubmissionNegotiation.getRateInitiatorType() == SubmissionRateStatus.ORG){
			permission = "NO";
			if(pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate() != null && pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser() != null && !pastRequirementRateSubmissionNegotiation.getRequirementsubmissionId().getCandidate().getConsultantUser().getUserCode().equals(pastRequirementRateSubmissionNegotiation.getCreatedBy()) && !SubmissionRateStatus.ORG.equals(pastRequirementRateSubmissionNegotiation.getRateInitiatorType())){
				permission = "YES";
			}
		}
		model.addObject("permission",permission);
		model.addObject("rateStatus",rateStatus);
		model.addObject("type",type);
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus) && "YES".equalsIgnoreCase(permission)){
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			UserRole userRole =userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
			
			List<RequirementRateSubmissionNegotiation> allSubmissionRateHistory = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId,requirementSubmission.getCandidate().getId());
			//RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
			RequirementRateSubmissionNegotiation  requirementRateSubmissionNegotiation = new RequirementRateSubmissionNegotiation();
			
			if(allSubmissionRateHistory != null && !allSubmissionRateHistory.isEmpty()){
				requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
				requirementRateSubmissionNegotiation.setInitiatorOrgLocationId(requirementSubmission.getCandidate().getOrgLocationId());
				requirementRateSubmissionNegotiation.setCurrentRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setCurrentRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setExpectedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
				requirementRateSubmissionNegotiation.setExpectedRateType(pastRequirementRateSubmissionNegotiation.getExpectedRateType());
				requirementRateSubmissionNegotiation.setRateStage(SubmissionRateStatus.ACCEPTED);
				requirementRateSubmissionNegotiation.setRateInitiatorType(SubmissionRateStatus.ORG);
				requirementRateSubmissionNegotiation.setCreatedBy(user.getUserCode());
				requirementRateSubmissionNegotiation.setUpdatedBy(user.getUserCode());
				requirementRateSubmissionNegotiation.setCreatedDate(new Date());
				requirementRateSubmissionNegotiation.setUpdatedDate(new Date());
				requirementRateSubmissionNegotiation.setStatus(Constants.RECORD_ACTIVE);
				requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
			}
			if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			}
			requirementSubmission.setFinalizedRate(pastRequirementRateSubmissionNegotiation.getExpectedRate());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);

			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			Requirement requirement = requirementSubmission.getRequirement();
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			
			List<RequirementRateSubmissionNegotiation>  negotiationList = requirementRateSubmissionNegotiationService.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, requirementSubmission.getCandidate().getId());
			StringBuilder tempHtml = requirementRateSubmissionNegotiationService.buildNegotiationHistory(negotiationList, requirement, candidate, submissionId, type);
			requirementSubmissionService.userAcceptRateNegotiation(requirement,userRole.getUser(),candidate,orgLocations,requirementRateSubmissionNegotiation,tempHtml);
			
		}
		return model;

	}
	
	
	/**
	 * This method is used to Accept or Reject Rate from Candidate.
	 */
	@RequestMapping(value = "/ws/acceptOrRejectRequirement")
	public ModelAndView acceptOrRejectRequirement(@RequestParam(value="rateStatus", required=false) String rateStatus,@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		
		ModelAndView model = new ModelAndView();
		String permission = "YES";
		if(requirementSubmission.getSubmissionStatus() != ApplicantSubmissionStatus.REQUEST_SENT){
			permission = "NO";
		}
		
		model.addObject("permission",permission);
		model.addObject("rateStatus",rateStatus);
		model.addObject("submissionId",submissionId);
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
		
		if("Accepted".equalsIgnoreCase(rateStatus) && "YES".equalsIgnoreCase(permission)){
			
			if(requirementSubmission.getSubmissionStatus() == ApplicantSubmissionStatus.REQUEST_SENT){
				requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
				requirementSubmission.setSubmissionRateStatus(SubmissionRateStatus.ACCEPTED);
			}
			
			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			if(null != requirementSubmissionTemplateFields.getLastName()){
			requirementSubmission.setUpdatedUser(requirementSubmissionTemplateFields.getFirstName() +" "+requirementSubmissionTemplateFields.getLastName());
			requirementSubmission.setSubmissionRequestUser(requirementSubmissionTemplateFields.getFirstName() +" "+requirementSubmissionTemplateFields.getLastName());
			}else{
				requirementSubmission.setUpdatedUser(requirementSubmissionTemplateFields.getFirstName() );
				requirementSubmission.setSubmissionRequestUser(requirementSubmissionTemplateFields.getFirstName());
				
			}
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REQUEST_SENT.toString());
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			
			Requirement requirement = requirementSubmission.getRequirement();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantAcceptMailAboutRequirement(requirement, user, requirementSubmission, orgLocations);
		}
		return model;

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

