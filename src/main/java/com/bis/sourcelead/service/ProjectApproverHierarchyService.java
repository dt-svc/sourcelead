package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;

public interface ProjectApproverHierarchyService {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel(Long projectId,Integer level) throws Exception;
	
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId,Long orgId,String isGlobalAccess) throws Exception;
			
	public List<ProjectApproverHierarchy> getAllApproverUsersByProjectIdList(List<Long> projectIds) throws Exception;

	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;
	
	public List<ProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;
	
	public void deleteProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long projectId,long userId) throws Exception;
	
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);
	
	public List<Long> getProjectIdsByLoginUserId(Long userId);
	
	Set<ProjectApproverHierarchy> prepareProjectApproverHierarchyListByJson(ProjectsDTO projectsDTO,HttpServletRequest request,Projects projects,ProjectClients projectClients) throws Exception;

	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdForManager(Long projectId, Long userId, int managerLevel) throws Exception;
	
	Set<ExpenseProjectApproverHierarchy> prepareExpenseProjectApproverHierarchyList(ProjectsDTO projectsDTO,HttpServletRequest request,Projects projects,ProjectClients projectClients) throws Exception;

	/**
	 * @author Prasad salina
	 * @param orgLocationId
	 * @param managerLevel
	 * @return
	 * @throws Exception
	 */
	public List<ProjectApproverHierarchy> getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(Long orgLocationId, String invoiceFrequency,int managerLevel)throws Exception;
	
	public ProjectApproverHierarchy getApproverUsersByProjectIdLevel1(Long projectId, Long userId);
	
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(Long approverUserId, Long projectClientId) throws Exception;

	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdlevel2Manager(Long projectId, Long userId)throws Exception;
	
	List<ProjectApproverHierarchy> getProjectApproversListByUserIdAndOrgLocationId(Long userId,Long orgLocationId)throws Exception;

	List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,Long orgLocationId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByUserIdAndProjectIdOrgLocationId(Long userId,Long projectId,Long orgLocationId) throws Exception;
	
	List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectClientId(Long projectClientId) throws Exception;
	
	String getApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception;

}
