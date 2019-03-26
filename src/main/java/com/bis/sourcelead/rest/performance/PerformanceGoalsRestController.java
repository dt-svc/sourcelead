package com.bis.sourcelead.rest.performance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class PerformanceGoalsRestController {
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	UserRoleService userRoleService;
	
	  @RequestMapping(value = "/getResourceManagersUsersForGoalsAssignment" , method = RequestMethod.GET)
	   public List<UserRole> getResourceManagersUsersForGoalsAssignment(HttpServletRequest request) throws Exception {
		   Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		   Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		   Long userRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userRoleId");
		   List<UserRole> userRolesList = null;
		   try {
			  userRolesList = userRoleService.getAllAssignedUsersByReportingManagerId(userId,orgLocationId);
			  UserRole userRole = userRoleService.getUserRoleById(userRoleId);
			  userRolesList.add(userRole);
			
		   }catch (Exception e) {
			     e.printStackTrace();
			}
		   return userRolesList;
	   }
	  
	  
	  @RequestMapping(value = { "/savePerformanceGoals", "/rest/savePerformanceGoals" }, method = RequestMethod.POST)
		public @ResponseBody Response<PerformanceGoalsDTO> savePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO) throws Exception {
			Response<PerformanceGoalsDTO> response = new  Response<>();
			try {
			 if(Validator.validate(performanceGoalsDTO)) {
				 performanceGoalsDTO.setStaticPath(staticPath);
				 /*projects = projectsService.storeProject(projectsDTO);
				 
			    ProjectsDTO stroredProjectsDTO = new ProjectsDTO();
				stroredProjectsDTO.setId(projects.getId());
				stroredProjectsDTO.setProjectUUID(projects.getProjectUUID());
				stroredProjectsDTO.setStartDateStr(projects.getStartDateStr());
				stroredProjectsDTO.setEndDateStr(projects.getEndDateStr());
				if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
					stroredProjectsDTO.setSavedSharedClientOrgLocId(projects.getSharedLocationId().getId().toString());
				}
				
				response.setObject(stroredProjectsDTO);*/
				
				 response.setResponse(Result.SUCCESS.name());
				 response.setResponseMessage(Result.SUCCESS.getValue());
			 }else {
	    		 response.setResponse(Result.INVALID_DATA.name());
				 response.setResponseMessage(Result.INVALID_DATA.getValue());
	    	 }
			}catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}

}
