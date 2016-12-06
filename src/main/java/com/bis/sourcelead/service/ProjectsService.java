package com.bis.sourcelead.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;

public interface ProjectsService {

	    Projects saveProject(Projects projects) throws Exception;
	   
	    void saveAllProjects(List<Projects> projectsList) throws Exception;
		
	    Projects updateProject(Projects projects) throws Exception;
		
	    Projects getProjectById(Long id) throws Exception;
		
		List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
		List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception;
		
	    Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
		
		Boolean deleteProjectById(Long id) throws Exception;
		
		Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
		
		Integer projectIdValidation(Long id) throws Exception;
		
		List<Projects> getAllProjects() throws Exception;
		
		List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception;
		
		List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId,Long locationId) throws Exception;
	
		Integer updateStartDateOfProjectId(Date startDate, Long projectId);
	
		Integer updateEndDateOfProjectId(Date endDate, Long projectId);
	
		List<Long> getApprovarAssignedProjects(Long userId,Long orgLocationId) throws Exception;
		
		Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception;
		
		List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId,String clients,String status,Long orgLocationId) throws Exception;
		
		List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId);
	
		Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;
	
		Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
		
		//HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception;
		
		void  userEmailNotificationForTimeSheetSubmission(User user,Long locationsId, HashSet<TimesheetSubmissions> timesheetSubmissions) throws Exception;
		
		//HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;
		
		String getProjectDetailsByProjectIdAndLoginOrgId(Long projectId,User user,String loginOrgLocation) throws Exception ;
	
		String getProjectDetailsUpdateByProjectId(Long projectId,User user,JSONObject jsonObj) throws Exception ;
	
		String storeEmployeeInProjectDashboard(OrgLocations loginOrgLocation,User users,JSONObject jsonObj) throws Exception ;
		 
		ProjectMembers getProjectEmployeeListByProjectId(Long projectId) throws Exception ;
		
		String saveProjectByUserOrgLocationsJson(User user,OrgLocations loginOrgLocation,String loginOrgCode,JSONObject jsonObj) throws Exception ;
        
		String  storeProject(JSONObject jsonObj,User user,OrgLocations loginOrgLocation,String loginOrgCode) throws Exception;
		
		//HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() throws Exception;
		
		
		
		
}
