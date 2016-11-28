package com.bis.sourcelead.web;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
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
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
 
	@RequestMapping(value = "/acceptOrRejectOrRateNegotiateApplicantRates")
	public ModelAndView acceptOrRejectOrRateNegotiateApplicantRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		ModelAndView model = new ModelAndView();
		model.addObject("rateStatus",rateStatus);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus)){
			//write accept logic ....like send mail and put status as submission
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			Requirement requirement = requirementSubmission.getRequirement();
			User initiatorUser = requirementSubmission.getOrgLocations().getUser();
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantAcceptMailAboutRequirement(requirement,initiatorUser,candidate,orgLocations);
		}
		if("Rejected".equalsIgnoreCase(rateStatus)){
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
			Requirement requirement = requirementSubmission.getRequirement();
			User initiatorUser = requirementSubmission.getOrgLocations().getUser();
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantRejectMailAboutRequirement(requirement,initiatorUser,candidate,orgLocations);
			
		}
		if("negotiate".equalsIgnoreCase(rateStatus)){
			//write accept logic ....like send mail and put status as submission
			
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

