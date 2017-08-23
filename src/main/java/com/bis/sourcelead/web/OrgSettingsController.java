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
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TimeZoneService;
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
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	ModulesService modulesService;
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/orgSettings")
	public ModelAndView orgSettings(HttpServletRequest request) throws Exception
	{
		logger.debug("Org settings");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		List<Modules> modulesList = null;
		List<Modules> requirementSubModulesList = null;
		try {
			modulesList = modulesService.getSourceLeadModules();
			model.addObject("modulesList", modulesList);
			requirementSubModulesList = modulesService.getRequirementSubModules();
			model.addObject("requirementSubModulesList", requirementSubModulesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("timezoneList", timezoneList);
		model.addObject("orgLocations", orgLocations);
		model.setViewName("orgSettings");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')")
	@RequestMapping(value = "/orgSubmissionTemplate")
	public ModelAndView orgSubmissionTemplate(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("orgSubmissionTemplateList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/orgTemplates")
	public ModelAndView orgTemplates(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("orgTemplates");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	// @PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/orgChart")
	public ModelAndView orgChart(HttpServletRequest request) throws Exception
	{
//		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("orgChart");
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/orgRolesList")
	public ModelAndView orgRolesList(HttpServletRequest request)
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
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
	@RequestMapping(value = "/roleHierarchy")
	public ModelAndView roleHierarchy(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		List<RoleHierarchy> hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.getId());
		List<RoleHierarchy> rolesHierarchyList = new ArrayList<RoleHierarchy>();
		if (null != hierarchyList) {
			for (RoleHierarchy hierarchy : hierarchyList) {
				List<Roles> rolesList = rolesService.getUserRolesListByRoleHierarchyIdAndOrgLocationId(orgLocations.getId(), hierarchy.getId(), org.getOrgCode());
				hierarchy.setRolesList(rolesList);
				rolesHierarchyList.add(hierarchy);
			}
		}
		model.addObject("rolesHierarchyList", rolesHierarchyList);
		model.setViewName("roleHierarchy");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/addDepartment")
	public ModelAndView addDepartment(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		/*
		 * List<RoleHierarchy> hierarchyList =
		 * roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.
		 * getId()); List<RoleHierarchy> rolesHierarchyList = new
		 * ArrayList<RoleHierarchy>();
		 * 
		 * if(null != hierarchyList){ for(RoleHierarchy hierarchy :
		 * hierarchyList){ List<Roles> rolesList =
		 * rolesService.getUserRolesListByRoleHierarchyIdAndOrgLocationId(
		 * orgLocations.getId(), hierarchy.getId(),org.getOrgCode());
		 * hierarchy.setRolesList(rolesList); rolesHierarchyList.add(hierarchy);
		 * } }
		 * 
		 * model.addObject("rolesHierarchyList", rolesHierarchyList);
		 */
		model.setViewName("department");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/enrollRoleActions")
	public ModelAndView enrollRoleActions(HttpServletRequest request, @RequestParam(value = "roleId", required = false) Long roleId) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Roles roles = rolesService.getRolesByIdAndOrgLocationId(roleId, orgLocations.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		if (null != roles) {
			List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActionsByOrgLocationId(orgLocations);
			if (null != roles.getActionId()) {
				ArrayList<SourceleadActions> sourceleadActionsList = new ArrayList<SourceleadActions>();
				List<Long> roleActonList = new ArrayList<Long>();
				for (String s : roles.getActionId().split(",")) {
					roleActonList.add(Long.parseLong(s));
				}
				for (SourceleadActions sleadActions : actionsList) {
					if (roleActonList.contains(sleadActions.getId())) {
						sleadActions.setIsActionConfigured(Constants.YES);
					}
					else {
						sleadActions.setIsActionConfigured(Constants.NO);
					}
					sourceleadActionsList.add(sleadActions);
				}
				model.addObject("actionsList", sourceleadActionsList);
			}
			else {
				model.addObject("actionsList", actionsList);
			}
			model.addObject("roleId", roleId);
			model.addObject("roleDisplayName", roles.getDisplayName());
			model.addObject("roleName", roles.getRoleName());
			model.setViewName("enrollRoleActions");
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionRolesMsg");
			if (!isNullOrEmpty(msg)) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "actionRolesMsg");
			}
		}
		else {
			model.setViewName("registration/persmissionDenied");
		}
		return model;
	}
	
	protected boolean isNullOrEmpty(String str)
	{
		return str == null || "".equals(str.trim());
	}
}
