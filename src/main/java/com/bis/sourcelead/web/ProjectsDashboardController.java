package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private ProjectsService projectsService;
	 
	 @Autowired
	 private ProjectMembersService projectMembersService;
	 
	 @Autowired
	 private ClientVendorExport clientVendorExport;
	 
	 @Autowired
	 private ProjectClientService projectClientService;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @Autowired
	 private SourceleadTagsService sourceleadTagsService;
	 /**
	 * This method for redirect projectDashboard
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(@RequestParam(value="searchResult", required=false) String searchResult,
			@RequestParam(value="searchValue", required=false) String searchValue,
			@RequestParam(value="projectId",required=false) Long projectId,
			@RequestParam(value="projectName",required=false) String projectName,
			@RequestParam(value="startDate",required=false) String startDate,
			@RequestParam(value="endDate",required=false) String endDate,
			@RequestParam(value="status",required=false) String status,
			@RequestParam(value="projectKeyword",required=false) String projectKeyword,
			HttpServletRequest request) throws Exception {
           logger.debug("projectDashboard");
           
		ModelAndView model = new ModelAndView();
		model.setViewName("project/projectDashboard");
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
	    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
        if(sessionData == null)
        {
            sessionData = new SessionMaintainanceData();
        }
        sessionData.setNav(Constants.Projects);
        request.getSession().setAttribute("sessionData",sessionData);
        
        List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(loginOrgLocations.getId()); 
        model.addObject("add_client", orgClientList);
        model.addObject("clientListSize", orgClientList.size());
        model.addObject(orgClientList);
        
        if(null != searchResult && null != searchValue){
        	model.addObject("searchValue", searchValue);
        	model.addObject("searchResult", searchResult);
        	model.addObject("searchType", "Projects");
        }
        
        if(null != projectKeyword && !"".equalsIgnoreCase(projectKeyword) &&  projectKeyword.equalsIgnoreCase("projectSearch")){
			model.addObject("projectKeyword",projectKeyword);
			model.addObject("projectId",projectId);
			model.addObject("projectName",projectName);
			model.addObject("startDate",startDate);
			model.addObject("endDate",endDate);
			model.addObject("status",status);
		}
        
        List<User> projectManagersList = userRoleService.getAllProjectManagersAssignedToProjects(loginOrgLocations.getId());
        model.addObject("projectManagersList", projectManagersList);
        model.addObject("projectManagersListSize", projectManagersList.size());
       
        List<String> prjectTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.PROJECTS.toString(), loginOrgLocations.getId());
        model.addObject("prjectTagsList", prjectTagsList);
        model.addObject("prjectTagsListSize", prjectTagsList.size());
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "projectCreateMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "projectCreateMsg");
		}
		
		model.addObject("sourceleadUrl", sourceleadUrl);

		return model;
		
    }
	 
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	/**	 
	 * checks if string is null or empty
	 * @param str
	 * @return boolean
	 */	
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	 /**
	  * @author Rajashekar
	  * 
	  * Writes Projects Assgined employee data to excel and pdf based on exportType
	  * @param projectId
	  * @return
	  * @throws Exception
	  */
	 @PreAuthorize("hasAnyRole('ROLE_EXPORT_EMPLOYEE')")
	 @RequestMapping(value = "/exportProjectEmployeeList")
	 public void  exportProjectEmployeeList(@RequestParam(value="projectId", required=false) Long projectId,
			 @RequestParam(value="status", required=false) String status,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		 ProjectClients projectClients = null;
		 
		 if(StringUtils.isNotBlank(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase(Constants.YES)) {
			 projectClients = projectClientService.getProjectClientsByProjectIdAndOrgId(projectId, org.getId());
		 }else {
			 projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectId, orgLocations.getId());
		 }
		
		 List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectClientIdAndStatus(projectClients.getId(),status);
		 Projects project = projectsService.getProjectById(projectId);
		 
		 File file = clientVendorExport.getexportProjectEmployeeFile(projectMembersList,exportType,project,projectClients); 
		 String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			if(file!=null){
				file.delete();
			}
		
	  }
}
