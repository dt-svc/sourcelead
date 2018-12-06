package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ProjectApproverHierarchyDao {

	public ProjectApproverHierarchy saveProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getByProjectApproverHierarchyId(Long projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getLevelOneProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByProjectIdAndLevel(Long projectId,Integer level) throws Exception;
	
	public ProjectApproverHierarchy updateProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchy) throws Exception;
	
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId,Long orgId,String isGlobalAccess) throws Exception;
	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;

	public List<ProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;
	
	public List<ProjectApproverHierarchy> getAllApproverUsersByProjectIdList(List<Long> projectIds) throws Exception;
	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectId(long Id) throws Exception;
	
	public List<ProjectApproverHierarchy> getApproverUsersByProjectClientId(Long projectClientsId) throws Exception;
	
	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectId(long Id,long userId) throws Exception;
	
	public List<ProjectApproverHierarchy> getProjectHierarchyListByLoginUserId(Long userId);
	
	public List<Long> getProjectIdsByLoginUserId(Long userId);
	
	public void deleteProjectApproverHierarchy(ProjectApproverHierarchy projectApproverHierarchyS) throws Exception;

	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdForManager(Long projectId,
			Long userId, int managerLevel)throws Exception;

	public ProjectApproverHierarchy getApproverUsersByProjectIdLevel1(Long projectId, Long userId);

	public List<ProjectApproverHierarchy> getAllProjectApproverHierarchyByProjectIdlevel2Manager(Long projectId,Long userId)throws Exception;
	
	List<ProjectApproverHierarchy> getProjectApproversListByUserIdAndOrgLocationId(Long userId,Long orgLocationId)throws Exception;
	
	List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,Long orgLocationId)throws Exception;
	
	/**
	 * @author Prasad salina
	 * @param orgLocationId
	 * @param invoiceFrequency
	 * @param managerLevel
	 * @return
	 * @throws Exception
	 */
	public List<ProjectApproverHierarchy> getApproverUserIdsByOrgLocationIdAndLevelAndFrequency(Long orgLocationId, String invoiceFrequency, int managerLevel)throws Exception;
	
	public ProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(Long approverUserId, Long projectClientId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByProjectId(Long ProjectId) throws Exception;
	
	public ProjectApproverHierarchy getProjectApproverByUserIdAndProjectIdOrgLocationId(Long userId,Long projectId,Long orgLocationId) throws Exception;
	
	List<ProjectApproverHierarchy> getProjectsHierarchyListByProjectClientId(Long projectClientId) throws Exception;
	
	String getApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception;

	
}
