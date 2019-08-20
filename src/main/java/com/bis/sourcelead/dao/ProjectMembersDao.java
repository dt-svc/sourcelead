
package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.enums.Primary;

public interface ProjectMembersDao {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
    List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate,Date endDate,Long locationsId, Long projectId)throws Exception;
    
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	void deleteProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
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
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndActiveStatusRecords(Long projectClientsId) throws Exception;
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

	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId, String fromType);

	public Integer getAllProjectMembersCountByProjectId(Long id);
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String status, Long orgLocationId);

	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId);
	
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId);
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId,Long orgId, Long candidateId);
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId, Long approverUserId);
	
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,Long projectId,String status,Long locationsId, Long approverUserId,String searchValue,String projectName, int start, int length,String timesheetFor,String isGlobalUser);

	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(String status,String startDate,String endDate,Long projectId);
	
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception;
	
	public Collection<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId,String status) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate,Date endDate,Long locationsId, Long approverUserId);

	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate)throws Exception;

	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId)throws Exception;

	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId);
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId,String status,String roleIds,String teamIds) throws Exception;
	
	public ProjectMembers getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(Long candidateId,Long projectId,Long orgLocationId) throws Exception;
	
	List<ProjectMembers> getAllProjectMembersByMasterCandidateId(Long masterCandidateId,Long projectId) throws Exception;
	
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(Long locationsId,Date startDate,Date endDate,int start,int length);
	
	Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception;
	
	ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId,Long projectId) throws Exception;
	
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
	
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(Long masterCandidateId,Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForExpences(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	public ProjectMembers getConsultantUserProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;

	ProjectMembers getProjectMembersByCandidateIdAndProjectClientId(Long candidateId,Long projectClientsId) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientId(Long projectClientsId) throws Exception;
	
	public ProjectMembers getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,Long orgId,String isGlobalAccess) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgIdAndMasterCandidateId(Long orgId, Long masterCandidateId);
	
	public List<ProjectMembers> getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception;

	public ProjectMembers getProjectMemberByUserIdAndLoginOrglocationId(Long userId, Long orgLocationId) throws Exception;
	
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId);
	
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(Long locationsId,Long orgId, Long masterCandidateId);
	
	public List<ProjectMembers> getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(Long locationsId,Long orgId, Long candidateId,String timesheetFrequency);
	
	List<ProjectMembers> getAllProjectMembersListByMasterCandidateId(Long masterCandidateId,Long projectId) throws Exception;
	
	public List<ProjectMembers> getAssignedProjectMembersGroupListByProjectClientId(List<Long> projectClientsId) throws Exception;
	
	boolean isProjectMembersExisted(Long projectId,Long masterCandidateId,Long projectClientId) throws Exception;
	
	public List<ProjectMembers> getAllActiveProjectsByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	List<ProjectMembers> getProjectMembersListByCandidateIdAndProjectClientId(Long candidateId,List<Long> projectClientsId) throws Exception;
	ResponseList<MarginSheetReportDTO> getProjectMemeberByClientProjectAndOrglocation(Long orglocationId,Date[] searchDates,MarginSheetReportDTO marginSheetReportDTO);
	
	
	public ProjectMembers getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,String isGlobalUser) throws Exception;
			
	ProjectMembers getProjectMembersByMasterCandiateIdAndProjectClientId(Long masterCandidateId,Long projectClientsId) throws Exception;

	public List<Object[]> getAllAvailableAssociates( Long orgLocationId);
	
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectClientId(Long masterCandidateId, Long projectClientsId) throws Exception;
	
	public List<ProjectMembers> getAllProjectsAssigendToCandidateIdAndStatus(Long candidateId,String status) throws Exception;
			
}