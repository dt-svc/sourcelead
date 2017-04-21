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
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.OffBoardingTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateOffBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateOffBoardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OffBoardingTemplateService offBoardingTemplateService;
	

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/candidateOffBoardCreation")
	public ModelAndView candidateOffBoardCreation(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("offboard/candidateOffBoardCreation");
		return model;
	}
	
	
	@RequestMapping(value = "/offboardingTemplatesList")
 	public ModelAndView offboardingTemplatesList(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("offboard/offboardingTemplatesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	
	@RequestMapping(value = "/addNewOffboardTemplate")
 	public ModelAndView addNewOffboardTemplate(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("offboard/addNewOffboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	@RequestMapping(value = "/viewOffboardTemplateByTemplateUUID")
 	public ModelAndView viewOffboardTemplateByTemplateUUID(@RequestParam(value="templateUUID", required=false) String templateUUID,
 			HttpServletRequest request) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			OffBoardingTemplate template = offBoardingTemplateService.getOffBoardingTemplateByTemplateUUID(templateUUID);
 			
 			model.setViewName("offboard/addNewOffboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("templateId", template.getId());
 			return model;
 	}
	
	

}
