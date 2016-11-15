package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class OrgSettingsController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	@RequestMapping(value = "/orgSettings")
 	public ModelAndView orgSettings(HttpServletRequest request) {
		logger.debug("Org settings");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("orgSettings");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@RequestMapping(value = "/orgSubmissionTemplate")
 	public ModelAndView orgSubmissionTemplate(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("orgSubmissionTemplateList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	@RequestMapping(value = "/orgRolesList")
 	public ModelAndView orgRolesList(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("orgRolesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	
	@RequestMapping(value = "/enrollRoleActions")
	public ModelAndView enrollRoleActions(HttpServletRequest request,
			@RequestParam(value = "roleId", required = false) Long roleId) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			model.setViewName("enrollRoleActions");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActions();
			model.addObject("actionsList", actionsList);
		
		return model;

	}
	
	
}
