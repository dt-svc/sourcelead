
package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.enums.Primary;

public interface ProjectMembersDao {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
    List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate,Date endDate,Long locationsId, Long projectId)throws Exception;
    
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	void deleteProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(Long projectId);
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByProjectIdAndOrgLocationId(Long projectId,Long orglocationId) throws Exception;
	
	List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception;
	
	public ProjectMembers getProjectMemberByUserIdAndProjectId(Long userId, Long projectId) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(Date startDate, Date endDate,Long locationsId, Long approverUserId);
	
	/**
	 * @author Prasad Salina
	 * @param empId
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public ProjectMembers getProjectMemberByEmpIdAndProjectId(Long empId,Long projectId) throws Exception;
	
	List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId,OrgLocations orgLocations) throws Exception;
	
    List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;
    
    public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheet() throws Exception;

	public List<ProjectMembers> getAllProjectsByUserId(Long userId);

	public Integer getAllProjectMembersCountByProjectId(Long id);
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status);

	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId);
	
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId);
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId);
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId, Long approverUserId);
	
	public List<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,String status,Long locationsId, Long approverUserId);

	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(String status,String startDate,String endDate,Long projectId);
	
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception;
	
	public Collection<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId,String status) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate,Date endDate,Long locationsId, Long approverUserId);

	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate)throws Exception;

	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId)throws Exception;

	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId);
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId,String status) throws Exception;
	
	public ProjectMembers getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(Long candidateId,Long projectId,Long orgLocationId) throws Exception;
	
	List<ProjectMembers> getAllProjectMembersByMasterCandidateId(Long masterCandidateId,Long projectId) throws Exception;
	
	Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception;
	
	ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId,Long projectId) throws Exception;
	
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
}