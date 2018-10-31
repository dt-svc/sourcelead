package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class UsersListController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	 private SourceleadTagsService sourceleadTagsService;
	
	/**
	 * To forward users List jsp page
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	/* This method is used for landing page of user tab. So, who has user tab access they can see users list. No preAuthorize for this method */
	@RequestMapping(value = "/usersList")
	public ModelAndView usersList(@RequestParam(value="searchResult",required = false) String searchResult,@RequestParam(value="searchValue",required = false) String searchValue,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long loginUserRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRoleId");
		Collection<Roles> rolesList = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("users/usersList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(searchResult !=null && searchValue !=null){
			model.addObject("searchResult", searchResult);
			model.addObject("searchValue", searchValue);
			model.addObject("searchType", "Users");
		}
		
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Users);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		try {
			rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
	
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsg");
		}
		model.addObject("rolesList", rolesList);
		
		Integer orgLocationsCount = orgLocationService.getRegisteredOrgLoctionsCountByOrgId(org.getId());
		model.addObject("orgLocationsCount", orgLocationsCount);
		
		
		List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(orgLocations.getId()); 
        model.addObject("clientsList", orgClientList);
        model.addObject("clientListSize", orgClientList.size());
        
        
        List<UserDTO> reportingManagersList = userRoleService.getAllReportingManagersListAssignedToAssociates(orgLocations.getId());
        model.addObject("reportingManagersList", reportingManagersList);
        model.addObject("reportingManagersListSize", reportingManagersList.size());
        
        List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
        model.addObject("teamsList", teamsList);
        model.addObject("teamsListSize", teamsList.size());
        
        List<Department> departmentList = departmentService.getDepartmentByOrgLocationId(orgLocations.getId());
        model.addObject("departmentList", departmentList);
        model.addObject("departmentListSize", departmentList.size());
		
        List<String> associateTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.ASSOCIATES.toString(), orgLocations.getId());
        model.addObject("associateTagsList", associateTagsList);
        
		return model;

	}
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	
	
}
