package com.bis.sourcelead.rest;

import java.io.Writer;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
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
		requirementRateSubmissionNegotiation.setRequirementsubmissionId(requirementSubmission);
		if(jsonObj.has("applicantRate")){
			requirementRateSubmissionNegotiation.setCurrentRate(jsonObj.getString("applicantRate"));
		}
		if(jsonObj.has("rateType")){
			requirementRateSubmissionNegotiation.setCurrentRateType(jsonObj.getString("rateType"));
		}
		requirementRateSubmissionNegotiationService.saveRateNegotiation(requirementRateSubmissionNegotiation);
		
		return "success";
		
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

