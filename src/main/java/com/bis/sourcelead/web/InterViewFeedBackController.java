package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.service.UserService;

@Controller
public class InterViewFeedBackController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
	InterviewFeedbackTemplateService interviewFeedbackTemplateService;
	


	/**
	 * author Laxman
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')")
	@RequestMapping(value = "/interViewFeedBackTemplate")
	public ModelAndView interViewFeedBackTemplate(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
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
