package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class TeamController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	
	@PreAuthorize("hasRole('ROLE_ADD_TEAM')")
	@RequestMapping(value = "/team")
	public ModelAndView newRequirement(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/team");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> allusersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		model.addObject("usersList", allusersList); 
		 
		return model;

	}

	/**
	 * author Laxman
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/interViewFeedBackTemplate")
	public ModelAndView interViewFeedBackTemplate(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/interViewFeedBackTemplate");
		return model;

	}
	
	
	@RequestMapping(value = "/publicInterviewFeedBackTemplate")
	public ModelAndView publicInterviewFeedBackTemplate(@RequestParam(value = "templateId", required = false) Long templateId,
			@RequestParam(value = "requirementInterviewid", required = false) Long requirementInterviewid,HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("templateId", templateId);
		model.addObject("requirementInterviewid", requirementInterviewid);
		model.addObject("sourceleadUrl", sourceleadUrl);
		InterviewFeedbackTemplate ift =interviewFeedbackTemplateService.getInterviewFeedbackTemplateById(requirementInterviewid);
		if(ift !=null){
			String requirementId = ift.getRequirementInterviewId().getId().toString();
			model.addObject("feedbackStatus", ift.getStatus());
			model.addObject("afterRequirementInterviewid", requirementId);
		}
		model.setViewName("requirement/publicInterviewFeedBackTemplate");
		return model;

	}
	
}
