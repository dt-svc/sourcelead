package com.bis.sourcelead.web;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementService;
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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
 
	/**
	 * this method is used to Accept or reject or to negotiate rate from Candidate
	 *
	 */
	@RequestMapping(value = "/acceptOrRejectOrRateNegotiateApplicantRates")
	public ModelAndView acceptOrRejectOrRateNegotiateApplicantRates(@RequestParam(value="rateStatus", required=false) String rateStatus,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			 HttpServletRequest request) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		
		ModelAndView model = new ModelAndView();
		model.addObject("rateStatus",rateStatus);
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		if("Accepted".equalsIgnoreCase(rateStatus)){
			//write accept logic ....like send mail and put status as submission
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			
			Requirement requirement = requirementSubmission.getRequirement();
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
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
		
		ModelAndView model = new ModelAndView();
		model.addObject("rateStatus",rateStatus);
		model.addObject("type","user");
		model.addObject("submissionId",submissionId);
		model.addObject("candidateId",requirementSubmission.getCandidate().getId());
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.addObject("requirementSubmission",requirementSubmission);
		model.setViewName("requirement/submissionRate");
		
		/*if("Accepted".equalsIgnoreCase(rateStatus)){
			//write accept logic ....like send mail and put status as submission
			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.SUBMISSION);
			requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
			
			
			Requirement requirement = requirementSubmission.getRequirement();
			User user = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), requirementSubmission.getOrgLocations().getId());
			Candidate candidate = requirementSubmission.getCandidate();
			OrgLocations orgLocations = requirementSubmission.getOrgLocations();
			requirementSubmissionService.applicantAcceptMailAboutRequirement(requirement,userRole.getUser(),candidate,orgLocations);
			
		}*/
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

