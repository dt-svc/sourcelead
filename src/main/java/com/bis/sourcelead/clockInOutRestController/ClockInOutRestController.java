package com.bis.sourcelead.clockInOutRestController;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutUserTimeEntryDTO;
import com.bis.sourcelead.clockInOutEntity.Breaks;
import com.bis.sourcelead.clockInOutEntity.ClockInOut;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.clockInOutService.ClockInOutConfigService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ClockInOutRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ClockInOutConfigService clockInOutConfigService;
	
	@RequestMapping(value = "/clockInOutConfig")
	public ModelAndView redirect(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("clockInOut/clockInOutConfig");
		return model;
		
	}
	
	
	@RequestMapping(value = {"/saveClockInOutConfigure","/rest/saveClockInOutConfigure"},method = RequestMethod.POST)
	public ClockInOutConfig saveClockInOutConfigure(ClockInOutConfigDTO clockInOutConfigDTO,HttpServletRequest request) {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		return clockInOutConfigService.saveClockInOutConfiguration(clockInOutConfigDTO,loginOrgLocation,user.getId());
		
	}
	
	@RequestMapping(value = {"/getClockInOutConfigData","/rest/getClockInOutConfigData"})
	public ClockInOutConfig getClockInOutConfigData(HttpServletRequest request) {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return clockInOutConfigService.getClockInOutConfigBasedOnOrgLocationId(loginOrgLocation.getId());
		
	}
	

	@RequestMapping(value = {"/saveClockInOutTime","/rest/saveClockInOutTime"},method = RequestMethod.POST)
	public ClockInOut saveClockInOutTime(ClockInOutDTO clockInOutDTO,HttpServletRequest request) {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return clockInOutConfigService.saveClockInOutTime(clockInOutDTO,loginOrgLocation,user);
		
	}
	

	/**
	 * Get Job Assigned to login user 
	 * @param status
	 * @param clients
	 * @param isWalkIn
	 * @param walkInDay
	 * @param request
	 * @return ResponseList<Requirement>
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getAssignedJobs","/rest/getAssignedJobs"},method = RequestMethod.GET)
	public ResponseList<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "clients", required = false) String clients, @RequestParam(defaultValue = "false") boolean isWalkIn, @RequestParam(defaultValue = "-1") int walkInDay, HttpServletRequest request) throws Exception
	{
		ResponseList<Requirement> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			try {
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				String userType = null;
				List<Requirement> requirementList = null;
				if (Constants.ROLE_ADMIN.equalsIgnoreCase(userRole)
						|| Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)) {
					userType = Constants.ADMIN;
					requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId, userType, user.getId());
				}
				else {
					requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId, userType, user.getId());
				}
				response.setData(requirementList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}
		else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	
	@RequestMapping(value = {"/getProjectsByStatusAndClient","/rest/getProjectsByStatusAndClient"})
	public ResponseList<Projects> getProjectsByStatusAndClient(ProjectsDTO projectsDTO,@RequestParam(value="values", required=false) String values,HttpServletRequest request,@RequestParam(value="clients", required=false) String clients) throws Exception {

	    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
	    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		projectsDTO.setIsGlobalAccess(isGlobalAccess);
		projectsDTO.setOrgId(org.getId());
		ResponseList<Projects> projectsList = clockInOutConfigService.getProjectsListByUserId(projectsDTO,user.getId(), clients, values,orgLocationId,userRole,candidateId);
	   
		return projectsList;
	}
	
	@RequestMapping(value = {"/getProjectsTasksByProjectId","/rest/getProjectsTasksByProjectId"})
	public List<ProjectTasks> getProjectsTasksByProjectId(@RequestParam(value="projectId", required=false) Long projectId,HttpServletRequest request) throws Exception {

		return clockInOutConfigService.getProjectsTasksByProjectId(projectId);
	   
	}
	
	@RequestMapping(value = {"/getGeneralTasks","/rest/getGeneralTasks"})
	public List<Tasks> getGeneralTasks(HttpServletRequest request) throws Exception {

		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		return clockInOutConfigService.getGeneralTasks(orgLocationId,userId);
	   
	}
	
	@RequestMapping(value = {"/getExistedClockTime","/rest/getExistedClockTime"})
	public ClockInOut getExistedClockTime(HttpServletRequest request) throws Exception {

		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		return clockInOutConfigService.getExistedClockTime(orgLocationId,userId);
	   
	}
	
	@RequestMapping(value = {"/updateClockInOutTime","/rest/updateClockInOutTime"},method = RequestMethod.POST)
	public void updateClockInOutTime(ClockInOutDTO clockInOutDTO,HttpServletRequest request) {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		clockInOutConfigService.updateClockInOutTime(clockInOutDTO,loginOrgLocation,user);
		
	}
	
	@RequestMapping(value = {"/saveBreakTime","/rest/saveBreakTime"},method = RequestMethod.POST)
	public Breaks saveBreakTime(ClockInOutDTO clockInOutDTO,HttpServletRequest request) {
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		return clockInOutConfigService.saveBreakTime(clockInOutDTO,userId);
		
	}
	
	@RequestMapping(value = {"/getExistedBreakTime","/rest/getExistedBreakTime"})
	public Breaks getExistedBreakTime(@RequestParam(value="clockInOutId", required=false) Long clockInOutId) {
		return clockInOutConfigService.getExistedBreakTime(clockInOutId);
		
	}
	
	@RequestMapping(value = "/employeeInOutTime")
	public ModelAndView timeEntry() {
		ModelAndView model = new ModelAndView();
		model.setViewName("clockInOut/employeeInOutTime");
		return model;
	}
	
	@RequestMapping(value = "/associateClockInSummaryReport")
	public ModelAndView associateClockInSummaryReport() {
		ModelAndView model = new ModelAndView();
		model.setViewName("clockInOut/associateClockInSummaryReport");
		return model;
	}
	
	@RequestMapping(value = {"/getUserTimeEntries","/rest/getUserTimeEntries"})
	public ResponseList<ClockInOutUserTimeEntryDTO> getUserTimeEntries(HttpServletRequest request) {
		
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
		return clockInOutConfigService.getUserTimeEntries(orgLocationId,userId,dateFormat);
		
	}

}
