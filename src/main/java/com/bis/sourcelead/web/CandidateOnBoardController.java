package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateOnBoardController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateOnBoardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OnBoardingTemplateService onBoardingTemplateService;
	
	
	@RequestMapping(value = "/candidateOnBoardingDetails")
	public ModelAndView candidateOnBoardingDetails(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/candidateOnBoardingDetails");
		return model;
	}
	
	
	@RequestMapping(value = "/onboardingTemplatesList")
 	public ModelAndView onboardingTemplatesList(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("onboard/onboardingTemplatesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	@RequestMapping(value = "/addNewOnboardTemplate")
 	public ModelAndView addNewOnboardTemplate(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("onboard/addNewOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@RequestMapping(value = "/viewOnboardTemplateByTemplateUUID")
 	public ModelAndView viewOnboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			OnBoardingTemplate template = onBoardingTemplateService.getOnBoardingTemplateByTemplateUUID(templateUUID);
 			
 			model.setViewName("onboard/addNewOnboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("templateId", template.getId());
 			return model;
 	}
	
	
	@RequestMapping(value = "/candidateOnBoardingList")
	public ModelAndView candidateOnBoardingList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/candidateOnBoardingList");
		return model;
	}
	
	
	@RequestMapping(value = "/candidateOnBoardCreation")
	public ModelAndView candidateOnBoardCreation(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("onboard/candidateOnBoardCreation");
		return model;
	}
	
}
