package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequirementWorkflowController {

	@RequestMapping(value = "/workflowDashboard")
	public ModelAndView workflowDashboard(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowDashboard");
		return modelandview;
	}
	
	@RequestMapping(value = "/defaultWorkflow")
	public ModelAndView defaultWorkflow(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/defaultWorkflow");
		return modelandview;
	}
	
	@RequestMapping(value = "/workflowList")
	public ModelAndView workflowList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/workflowList");
		return modelandview;
	}
	
	@RequestMapping(value = "/addStage")
	public ModelAndView addStage(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/addStage");
		return modelandview;
	}
	
	@RequestMapping(value = "/questionnaireList")
	public ModelAndView questionnaireList(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/questionnaireList");
		return modelandview;
	}
	
	@RequestMapping(value = "/addQuestionnaire")
	public ModelAndView addQuestionnaire(HttpServletRequest request) throws Exception{
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("workflow/addQuestionnaire");
		return modelandview;
	}
	
	
	
}
