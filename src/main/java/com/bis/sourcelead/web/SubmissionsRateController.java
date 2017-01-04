package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
 
	/**
	 * this method is used to Accept or reject or to negotiate rate from Candidate
	 *
	 */
	@RequestMapping(value = "/acceptOrRejectOrRateNegotiateApplicantRates")
	public ModelAndView acceptOrRejectOrRateNegotiateApplicantRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		
		String type = "Candidate";
		ModelAndView model = new ModelAndView();
		String permission = "YES";
		if(pastRequirementRateSubmissionNegotiation != null && (pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.ACCEPTED || pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.REJECTED) && (pastRequirementRateSubmissionNegotiation.getCreatedBy() == "" || pastRequirementRateSubmissionNegotiation.getCreatedBy() == null)){
			permission = "NO";
		}else if((requirementSubmission != null && requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.REJECTED)) || (rateStatus.equalsIgnoreCase("Accepted") && requirementSubmission != null && requirementSubmission.getSubmissionStatus().equals(ApplicantSubmissionStatus.SUBMISSION))){
			permission = "NO";
		}
		model.addObject("permission",permission);
		model.addObject("rateStatus",rateStatus);
		model.addObject("type",type);
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus)){
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
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);

			requirementSubmission.setUpdatedDate(commonUtil.currentDate());
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			//requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
			requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.REQUEST_SENT.toString());
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION_ACCEPT);
			requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
			
			Requirement requirement = requirementSubmission.getRequirement();
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantAcceptMailAboutRequirement(requirement,userRole.getUser(),candidate,orgLocations);
			
		}
		return model;

	}
	
	/**
	 * This method is used to Accept or Reject or Negotiate Rate from User.
	 */
	@RequestMapping(value = "/acceptOrRejectOrRateNegotiateUserRates")
	public ModelAndView acceptOrRejectOrRateNegotiateUserRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		RequirementRateSubmissionNegotiation  pastRequirementRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
		String type = "User";
		ModelAndView model = new ModelAndView();
		String permission = "YES";
		if(pastRequirementRateSubmissionNegotiation != null && (pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.ACCEPTED || pastRequirementRateSubmissionNegotiation.getRateStage() != SubmissionRateStatus.REJECTED && (pastRequirementRateSubmissionNegotiation.getCreatedBy() != "" || pastRequirementRateSubmissionNegotiation.getCreatedBy() != null))){
			permission = "NO";
		}
		model.addObject("permission",permission);
		model.addObject("rateStatus",rateStatus);
		model.addObject("type",type);
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus)){
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
			requirementSubmissionService.userAcceptRateNegotiation(requirement,userRole.getUser(),candidate,orgLocations,requirementRateSubmissionNegotiation);
			
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

