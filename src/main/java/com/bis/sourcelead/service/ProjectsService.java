package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.ClientProjectDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.MissingTimesheetRepotDTO;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;

public interface ProjectsService {

	    Projects saveProject(Projects projects) throws Exception;
	   
	    void saveAllProjects(List<Projects> projectsList) throws Exception;
		
	    Projects updateProject(Projects projects) throws Exception;
		
	    Projects getProjectById(Long id) throws Exception;
		
		List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
		
		List<Projects> getAllProjectByOrgLocationsId(Long orgLocationId) throws Exception;
		
		List<Projects> getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(Long orgLocationsId,Long clientOrgLocationId) throws Exception;
	
		List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception;
		
		List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception;
		
	    Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	    
	    Projects getProjectByOrgLocationsIdAndProjectName(Long orgLocationsId,String projectName) throws Exception;
		
		Boolean deleteProjectById(Long id) throws Exception;
		
		Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
		
		Boolean validateProjectShortCode(String shortCode, Long orgLocationsId) throws Exception;
		
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
	
		Projects getProjectDetailsUpdateByProjectId(Long projectId,User user,ProjectsDTO projectsDTO) throws Exception ;

		Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;
	
		Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
		
		//HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception;
		
		void  userEmailNotificationForTimeSheetSubmission(User user,Long locationsId, Map<String, String> timesheetFrequencyMap,Projects projects, String level) throws Exception;
		
		//HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;
		
		ProjectsDTO getProjectDetailsByProjectIdAndLoginOrgId(Long projectId,User user,String loginOrgLocation,String isGlobalAccess) throws Exception ;
	
	
		String storeEmployeeInProjectDashboard(OrgLocations loginOrgLocation,User users,JSONObject jsonObj) throws Exception ;
		 
		ProjectMembers getProjectEmployeeListByProjectId(Long projectId) throws Exception ;
		
		Projects  storeProject(ProjectsDTO projectsDTO) throws Exception;
		
		Projects  storeClientProject(ClientProjectDTO clientProjectDTO) throws Exception;

//		void userEmailNotificationForTimeSheetSubmissionToManagers(Map<UserRole, HashSet<TimesheetSubmissions>> timesheetSubmissionsMap, int level)throws Exception;
//
//		void userEmailNotificationForTimeSheetSubmissionAdmin(Map<UserRole, HashSet<TimesheetSubmissions>> timesheetSubmissionsMap, OrgLocations orgLocations)throws Exception;

		void userEmailToAdmin(StringBuilder tempHTML, OrgLocations orgLocations, User user)throws Exception;

		//HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek() throws Exception;
		
		
		Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId,Long orgLocationId)throws Exception;
		
		void tereminateAsProjectApprover(Long approverUser,User user,Long userId,OrgLocations orgLocations) throws Exception;
		
		void saveOrUpdateProjectElkDocument(Projects projects) throws Exception;
		
		Integer getProjectCountByOrgLocationId(Long orgLocationId)throws Exception;
		
		Integer getProjectCountByOrgLocationIdAndUserCode(Long orgLocationId,String userCode)throws Exception;

		List<Object> getAllUniversalSearchProjectsList(OrgLocations orgLocations, String searchValue) throws Exception;
		
		Boolean validateTasksName(String taskname, Long projectId) throws Exception;
		
		List<ProjectMembers> getProjectEmployeeListByProjectIdAndStatus(Long projectId,String status,OrgLocations loginOrgLocation,String roleIds,String teamIds,String isGlobalAccess) throws Exception ;

		List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId)throws Exception ;

		List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationId) throws Exception ;
		
		Projects getProjectByProjectUUID(String projectUUID) throws Exception;
		
		ResponseList<ProjectsDTO> getProjectsListByClientsAndStatus(ProjectsDTO projectsDTO,Long userId,String clients,String status,Long orgLocationId,String userRole,Long candidateId) throws Exception;
		
		List<Projects> getAllProjectsByOrgLocationIdAndStatusForTransfer(Long orgLocationId,String projectClientUUID) throws Exception ;
		
		Projects makeInternalProjectAsClientProject(Projects projects,Long orgRelationId,Long clientOrgLocationId,OrgLocations loginOrgLocation) throws Exception;
		
		Projects getClientProjectApproversUpdate(String projectClientUUID,User user,ProjectsDTO projectsDTO) throws Exception ;

		Projects getProjectByProjectName(String projectName) throws Exception;

		Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations)throws Exception;

		File getPdfFile(ResponseList<ProjectsDTO> projectsList, OrgLocations orgLocations)throws Exception;

		List<Projects> getAllProjectsBasedOnStartDateEndDateAndProjectType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto);
		
		List<Projects> getAllClientProjectsByOrgLocationId(Long locationId) throws Exception;
		
		List<ProjectsDTO> getProjectListForDaywiseReports(Long orgLocationsId,Long orgId,String isGlobalManager,Long userId,String type,String userRole) throws Exception;

		public ResponseList<ProjectReportDTO> getProjectReportList(ProjectReportDTO projectReportDTO,Long orgLocationId)throws Exception;
		
		Map<String, List<AvailableAssociatesDTO>> getAvailableAssociatesReport(AvailableAssociatesDTO availableAssociatesDTO,OrgLocations orgLocations);

		//public File getProjectReportListOfExcel(ProjectReportDTO projectReportDTO, User user, OrgLocations orgLocation,String isGlobalAccess);

		File getProjectReportListOfExcel(List<ProjectReportDTO> projectReportDTO, User user, OrgLocations orgLocation,String isGlobalAccess) throws Exception;

		List<Projects> getProjectsListByCanddateIdAndOrgId(String associates, Long orgLocatonId);


		ProjectMembers isUserAssignedToProject(User userId, Long orgLocationId, Long projectId);

		File availableAssociatereportReportListOfExcel(Map<String, List<AvailableAssociatesDTO>> object, User user,OrgLocations orgLocations);
		
//		public String getProjectId(OrgLocations orgLocations);
		
}