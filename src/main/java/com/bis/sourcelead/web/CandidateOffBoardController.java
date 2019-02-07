package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.entity.OffboardCheckList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.OffBoardingDocumentsService;
import com.bis.sourcelead.service.OffBoardingTemplateService;
import com.bis.sourcelead.service.OffboardCheckListService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class CandidateOffBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateOffBoardController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OffBoardingTemplateService offBoardingTemplateService;
	
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;
	
	@Autowired
	OffboardCheckListService offboardCheckListService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	OffBoardingDocumentsService offBoardingDocumentsService;
	

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')")
	@RequestMapping(value = "/offboardingTemplatesList")
 	public ModelAndView offboardingTemplatesList(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("offboard/offboardingTemplatesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "OffboardCreateMsg");
 			if(!isNullOrEmpty(msg)){
 				model.addObject("msg", msg);
 				SourceLeadSessionManager.removeSessionAttribute(request, "OffboardCreateMsg");
 			}
 			return model;
 	}
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')")
	@RequestMapping(value = "/addNewOffboardTemplate")
 	public ModelAndView addNewOffboardTemplate(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			
 			model.setViewName("offboard/addNewOffboardTemplate");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')")
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
