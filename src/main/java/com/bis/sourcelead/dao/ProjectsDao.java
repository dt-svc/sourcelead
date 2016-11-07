package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;

import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface ProjectsDao {

	public Projects saveProject(Projects projects) throws Exception;
	
	public Projects updateProject(Projects projects) throws Exception;
	
	public Projects getProjectById(Long id) throws Exception;
	
	List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception;
	
	Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	public Boolean deleteProjectById(Long id) throws Exception;
	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
	
	public Integer projectIdValidation(Long projectId) throws Exception;
	
	List<Projects> getAllProjects() throws Exception;
	
	List<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception;
	
	List<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception;

	public Integer updateStartDateOfProjectId(Date startDate, Long projectId);
	
	public Integer updateEndDateOfProjectId(Date endDate,Long projectId);

	List<Long> getApprovarAssignedProjects(Long userId,Long orgLocationId) throws Exception;
	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception;

	List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId,String clients,String status,Long orgLocationId) throws Exception;
	
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId);
	

	public Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;

	public Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
	
	public HashSet<User> getAllUserTimeSheetSubmissionStatus() throws Exception;
	
	public void saveAllProjects(List<Projects> projectsList) throws Exception;
	
	public List<SubmissionApproverHierarchy>  getApproveUserForTimeSheets(Long userId) throws Exception;
	
	public HashSet<User> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception;

	public HashSet<User> getAllUserTimeSheetSubmissionStatusCurrentWeek();

}
