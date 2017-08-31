package com.bis.sourcelead.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.model.ProjectDocumentBO;

public interface ProjectsService {

	    Projects saveProject(Projects projects) throws Exception;
	   
	    void saveAllProjects(List<Projects> projectsList) throws Exception;
		
	    Projects updateProject(Projects projects) throws Exception;
		
	    Projects getProjectById(Long id) throws Exception;
		
		List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
		List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception;
		
		List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception;
		
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
		
		List<Projects> getProjectsByOrgLocationsIdAndApproverUserId(Long id, Long approverUserId);
		
	    List<Long> getProjectIdsByOrgLocationsId(Long orgLocationsId) throws Exception;   
	
		Projects getProjectDetailsUpdateByProjectId(Long projectId,User user,JSONObject jsonObj) throws Exception ;
		
		List<Projects> getProjectBasedClientsByOrgLocationsIdAndApproverUserId(Long orgLocationsId, Long userId) throws Exception;

		Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;
	
		Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
		
		//HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception;
		
		void  userEmailNotificationForTimeSheetSubmission(User user,Long locationsId, Map<String, String> timesheetFrequencyMap,Projects projects, String level) throws Exception;
		
		//HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;
		
		String getProjectDetailsByProjectIdAndLoginOrgId(Long projectId,User user,String loginOrgLocation) throws Exception ;
	
	
		String storeEmployeeInProjectDashboard(OrgLocations loginOrgLocation,User users,JSONObject jsonObj) throws Exception ;
		 
		ProjectMembers getProjectEmployeeListByProjectId(Long projectId) throws Exception ;
		
		String saveProjectByUserOrgLocationsJson(User user,OrgLocations loginOrgLocation,String loginOrgCode,JSONObject jsonObj) throws Exception ;
        
		String  storeProject(JSONObject jsonObj,List<ProjectDocumentBO> projectDocuments) throws Exception;

//		void userEmailNotificationForTimeSheetSubmissionToManagers(Map<UserRole, HashSet<TimesheetSubmissions>> timesheetSubmissionsMap, int level)throws Exception;
//
//		void userEmailNotificationForTimeSheetSubmissionAdmin(Map<UserRole, HashSet<TimesheetSubmissions>> timesheetSubmissionsMap, OrgLocations orgLocations)throws Exception;

		void userEmailToAdmin(StringBuilder tempHTML, OrgLocations orgLocations, User user)throws Exception;

		//HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() throws Exception;
		
		
		Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId,Long orgLocationId)throws Exception;
		
		void tereminateAsProjectApprover(JSONObject jsonObj,User user,Long userId,OrgLocations orgLocations) throws Exception;
		
		void saveOrUpdateProjectElkDocument(Projects projects) throws Exception;
		
		Integer getProjectCountByOrgLocationId(Long orgLocationId)throws Exception;

		List<Object> getAllUniversalSearchProjectsList(OrgLocations orgLocations, String searchValue) throws Exception;
		
		Boolean validateTasksName(String taskname, Long projectId) throws Exception;
		
		ProjectMembers getProjectEmployeeListByProjectIdAndStatus(Long projectId,String status) throws Exception ;

		List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId)throws Exception ;

		List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationId) throws Exception ;
		
		Projects getProjectByProjectUUID(String projectUUID) throws Exception;
		
//		public String getProjectId(OrgLocations orgLocations);
		
}
