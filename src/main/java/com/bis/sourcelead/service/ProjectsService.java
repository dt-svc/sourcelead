package com.bis.sourcelead.service;

import java.util.Collection;

import java.util.Date;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface ProjectsService {

    public Projects saveProject(Projects projects) throws Exception;
	
	public Projects updateProject(Projects projects) throws Exception;
	
	public Projects getProjectById(Long id) throws Exception;
	
	List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
    Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	public Boolean deleteProjectById(Long id) throws Exception;
	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
	
	public Integer projectIdValidation(Long id) throws Exception;
	
	Collection<Projects> getAllProjects() throws Exception;
	
	Collection<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception;
	
	Collection<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId,Long locationId) throws Exception;

	public Integer updateStartDateOfProjectId(Date startDate, Long projectId);

	public Integer updateEndDateOfProjectId(Date endDate, Long projectId);

	List<Long> getApprovarAssignedProjects(Long userId,Long orgLocationId) throws Exception;
	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception;
	
	List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId,String clients,String status,Long orgLocationId) throws Exception;
	
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId);
	

	public Projects getProjectByIdAndLoginOrgId(Long projectId,Long orgLocationId) throws Exception;

	public Integer getUserAssignedProjectsCount(Long userId,Long orgLocationId);
	
	public List<User> getAllUesrTimeSheetSubmissionStatus() throws Exception;
	
	public void  userEmailNotificationForTimeShhetSubmission(User user) throws Exception;
	
	public List<User> getAllUesrPrevoiusWeekTimeSheetSubmissionStatus() throws Exception;


}
