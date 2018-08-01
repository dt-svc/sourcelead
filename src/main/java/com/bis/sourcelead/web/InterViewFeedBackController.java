package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class InterViewFeedBackController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService;
	


	/**
	 * author Laxman
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')")
	@RequestMapping(value = "/interViewFeedBackTemplate")
	public ModelAndView interViewFeedBackTemplate(HttpServletRequest request,@RequestParam(value = "type", required = false) String type) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("type", type);
		model.setViewName("requirement/interViewFeedBackTemplate");
		return model;

	}
	
	/**
	 * author Laxman
	 * This method id using for redirect to public interviewFeedback template
	 * @param templateId
	 * @param requirementInterviewid
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/ws/publicInterviewFeedBackTemplate")
	public ModelAndView publicInterviewFeedBackTemplate(@RequestParam(value = "templateId", required = true) String templateId,
			@RequestParam(value = "requirementInterviewid", required = true) String requirementInterviewerId,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("templateId", templateId);
		model.addObject("requirementInterviewid", requirementInterviewerId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		RequirementInterviewer interviewer = requirementInterviewerService.getRequirementInterviewerByUUID(requirementInterviewerId);
		InterviewFeedbackTemplate ift = interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(interviewer.getId());
		if(ift !=null){
			String requirementId = ift.getRequirementInterviewerId().getUuid();
			model.addObject("feedbackStatus", ift.getStatus());
			model.addObject("afterRequirementInterviewid", requirementId);
		}
		model.setViewName("requirement/publicInterviewFeedBackTemplate");
		return model;

	}
	
	
	/**
	 * author David Raju Sammeta
	 * This method id using for redirect to public interviewFeedback template
	 * @param templateId
	 * @param requirementInterviewid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/dashboardInterviewFeedBackTemplate")
	public ModelAndView dashboardInterviewFeedBackTemplate(@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "requirementInterviewid", required = false) String requirementInterviewId,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("templateId", templateId);
		model.addObject("requirementInterviewid", requirementInterviewId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		
	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

	    model.addObject("orgLocations", loginOrgLocations.getId());
	    
	    RequirementInterviewer reqInterviewer = requirementInterviewerService.getRequirementInterviewerByUUID(requirementInterviewId);
	    if(null != reqInterviewer){
	    	if(null != reqInterviewer.getRequirementInterview()){
	    		if(null != reqInterviewer.getRequirementInterview().getSubmissionId()){
	    			if(null != reqInterviewer.getRequirementInterview().getSubmissionId().getRequirement()){
	    				model.addObject("requirementName", reqInterviewer.getRequirementInterview().getSubmissionId().getRequirement().getJobTitle());
	    			}
			    	StringBuilder applicantNameb = new StringBuilder();
			    	if(null != reqInterviewer.getRequirementInterview().getSubmissionId().getCandidate().getLastName()){
			    		model.addObject("applicantName", applicantNameb.append(reqInterviewer.getRequirementInterview().getSubmissionId().getCandidate().getFirstName()).append(" ").append(reqInterviewer.getRequirementInterview().getSubmissionId().getCandidate().getLastName()).toString());	
			    	}else{
			    		model.addObject("applicantName", applicantNameb.append(reqInterviewer.getRequirementInterview().getSubmissionId().getCandidate().getFirstName()).toString());
			    	}
	    		}
	    	}
	    }
	    
	    //req.setInterviewerName(req.getRequirementInterview().getSubmissionId().getCandidate().getFirstName());
	    RequirementInterviewer interviewer = requirementInterviewerService.getRequirementInterviewerByUUID(requirementInterviewId);
		InterviewFeedbackTemplate ift =interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(interviewer.getId());
		if(ift !=null){
			String requirementId = ift.getRequirementInterviewerId().getUuid();
			model.addObject("feedbackStatus", ift.getStatus());
			model.addObject("afterRequirementInterviewid", requirementId);
		}
		model.setViewName("requirement/dashboardInterviewFeedBackTemplate");
		return model;

	}
	

}
