package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AvailableAssociatesDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;

public interface ProjectsDao {

	public Projects saveProject(Projects projects) throws Exception;
	
	public Projects updateProject(Projects projects) throws Exception;
	
	public Projects getProjectById(Long id) throws Exception;
	
	List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	List<Projects> getAllProjectByOrgLocationsId(Long orgLocationId) throws Exception;
	
	List<Projects> getEndClientProjectByOrgLocationsIdAndClientOrgLocationId(Long orgLocationsId,Long clientOrgLocationId) throws Exception;
	
	List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception;
	
	List<Projects> getAllProjectsEndDateGraterthanCurrentDate() throws Exception;
	
	Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	Projects getProjectByOrgLocationsIdAndProjectName(Long orgLocationsId,String projectName) throws Exception;
	
	public Boolean deleteProjectById(Long id) throws Exception;
	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
	
	Boolean validateProjectShortCode(String shortCode, Long orgLocationsId) throws Exception;
	
	public Integer projectIdValidation(Long projectId) throws Exception;
	
	List<Projects> getAllProjects() throws Exception;
	
	List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception;
	
	List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception;

	public Integer updateStartDateOfProjectId(Date startDate, Long projectId);
	
	public Integer updateEndDateOfProjectId(Date endDate,Long projectId);

	List<Long> getApprovarAssignedProjects(Long userId,Long orgLocationId) throws Exception;
	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception;

	List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId,String clients,String status,Long orgLocationId) throws Exception;
	
	ResponseList<ProjectsDTO> getProjectsListByClientsAndStatus(ProjectsDTO projectsDTO,Long userId,String clients,String status,Long orgLocationId,String userRole,Long candidateId) throws Exception;
	
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId);
	
	public List<Projects> getProjectsByOrgLocationsIdAndApproverUserId(Long id, Long userId);
	
	public Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;

	public Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
	
	//public HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception;
	
	Integer getProjectCountByOrgLocationIdAndUserCode(Long orgLocationId,String userCode)throws Exception;

	
	public void saveAllProjects(List<Projects> projectsList) throws Exception;
	
	public List<SubmissionApproverHierarchy>  getApproveUserForTimeSheets(Long userId) throws Exception;
	
	//public HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;

	//public HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek();
	
	public Projects checkProjectIdNumberInDatebase(Long orgLocationId);
	
	Integer getProjectApproversCountByUserIdAndOrgLocationId(Long userId,Long orgLocationId)throws Exception;
	
	List<Long> getProjectIdsByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	Integer getProjectCountByOrgLocationId(Long orgLocationId)throws Exception;
	
	Boolean validateTasksName(String taskname, Long projectId) throws Exception;

	public List<Projects> getClientProjectsByOrgRelationId(Long orgRelationId, Long orgLocationId)throws Exception;

	public List<Projects> getAllProjectsByOrgLocationIdAndStatus(Long orgLocationId)throws Exception;
	
	Projects getProjectByProjectUUID(String projectUUID) throws Exception;
	
	List<Projects> getAllProjectsByOrgLocationIdAndStatusForTransfer(Long orgLocationId,String projectClientUUID) throws Exception ;

	Projects getProjectByProjectName(String projectName) throws Exception;

	public List<Projects> getAllProjectsBasedOnProjectType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto);
	
	List<Projects> getAllClientProjectsByOrgLocationId(Long locationId) throws Exception;
	
	public List<Projects> getProjectsListByUserIdAndOrgLocationIdAndStatus(Long userId,Long orgLocationsId,Long orgId,Long candidateId,String userRole,String globalAccess) throws Exception;
	
	List<ProjectsDTO> getProjectListForDaywiseReports(Long orgLocationsId,Long orgId,String isGlobalManager,Long userId,String type,String userRole) throws Exception;

	public List<Projects> getProjectReportList(ProjectReportDTO projectReportDTO, Long orgLocationId);

	List<Object[]> getAvailableAssociatesReport(AvailableAssociatesDTO availableAssociatesDTO, OrgLocations orgLocations);

	public List<Projects> getProjectsListByCanddateIdAndOrgId(String associates, Long orglocationId);

	public ProjectMembers isUserAssignedToProject(User userId, Long orgLocationId, Long projectId);
	
	List<ProjectsDTO> getProjectListForProjectTimeEntriesReports(Long orgLocationsId,Long orgId,String isGlobalManager,Long userId,String type,String userRole) throws Exception;
	
	List<ProjectsDTO> getProjectListForResourceTimeEntriesReports(Long orgLocationsId,Long orgId,String isGlobalManager,Long userId,String type,String userRole) throws Exception;


}