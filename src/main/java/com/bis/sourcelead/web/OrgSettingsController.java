package com.bis.sourcelead.web;

import java.util.ArrayList;
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
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.util.CommonUtil;
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
	private RolesService rolesService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RoleHierarchyService roleHierarchyService;
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_ORG_SETTINGS','ROLE_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/orgSettings")
 	public ModelAndView orgSettings(HttpServletRequest request) {
		logger.debug("Org settings");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("orgSettings");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	 @PreAuthorize("hasRole('ROLE_ORG_SUBMISSION_TEMPLATE')")
	@RequestMapping(value = "/orgSubmissionTemplate")
 	public ModelAndView orgSubmissionTemplate(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			model.setViewName("orgSubmissionTemplateList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/orgRolesList")
 	public ModelAndView orgRolesList(HttpServletRequest request) {
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    ModelAndView model = new ModelAndView();
 			commonUtil.highLightMenu(Constants.OrgSettings, request);
 			List<RoleHierarchy> hierarchyList = null;
 			try {
				 hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
 			model.addObject("hierarchyList", hierarchyList);
 			model.setViewName("orgRolesList");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/enrollRoleActions")
	public ModelAndView enrollRoleActions(HttpServletRequest request,
			@RequestParam(value = "roleId", required = false) Long roleId) throws Exception {
		   
		    ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			
			Roles roles = rolesService.getRolesByIdAndOrgLocationId(roleId,orgLocations.getId());
			model.addObject("sourceleadUrl", sourceleadUrl);
			if(null != roles){
			
			List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActions(org);
			
			if(null != roles.getActionId()){
				ArrayList<SourceleadActions> sourceleadActionsList = new ArrayList<SourceleadActions>();
				List<Long> roleActonList = new ArrayList<Long>();
				 for (String s : roles.getActionId().split(",")){
					 roleActonList.add(Long.parseLong(s));
				 }
				 
				 for(SourceleadActions sleadActions : actionsList){
					 if(roleActonList.contains(sleadActions.getId())){
						 sleadActions.setIsActionConfigured(Constants.YES);
					 }else{
						 sleadActions.setIsActionConfigured(Constants.NO);
					 }
					 sourceleadActionsList.add(sleadActions);
				 }
				 
				 model.addObject("actionsList", sourceleadActionsList);
			}else{
				 model.addObject("actionsList", actionsList);
			}
			
			 model.addObject("roleId",roleId);
			 model.addObject("roleDisplayName",roles.getDisplayName());
			 model.addObject("roleName",roles.getRoleName());
			 model.setViewName("enrollRoleActions");
			 
			 String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionRolesMsg");
				if(!isNullOrEmpty(msg)){
					model.addObject("msg", msg);
					SourceLeadSessionManager.removeSessionAttribute(request, "actionRolesMsg");
				}
				
			}
			else{
				model.setViewName("registration/persmissionDenied");
			}
		
		return model;

	}
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
}
