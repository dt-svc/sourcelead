package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;

public interface ProjectMembersService {

    public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception;
 	
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception;
	
	void deleteProjectMember(ProjectMembers projectMembers) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(Long projectId);
	
	public ProjectMembers getProjectMemberById(Long id) throws Exception;
	
	public Boolean deleteProjectMemberById(Long id) throws Exception;
	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long id,Long locationsId) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long employeeId) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(Date startDate, Date endDate,Long locationsId, Long approverUserId);
	
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId,OrgLocations orgLocations) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheet() throws Exception;

	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId);
	
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception;
	
	public ProjectMembers getProjectMemberByUserIdAndProjectId(Long userId,Long projectId) throws Exception;
	
	public ProjectMembers getProjectMemberByEmpIdAndProjectId(Long empId,Long projectId) throws Exception;
	
	public Integer  getAllProjectMembersCountByProjectId(Long id) throws Exception;
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String mEMBER_ACTIVE,Long orgLocationId) throws Exception;

	public Integer updateEndDateOfProjectMemberId(Date endDate1, Long projectMemberId);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
	
	public String storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(Long projectId,Long employeeId,String startDate,String endDate,String mailtoMembers,String loginOrgCode,User user,OrgLocations loginOrgLocation) throws Exception ;

	/*by sarath*/
    public Set<ProjectMembers> prepareProjectMembersListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception;
    
    public List<ProjectMembers> getAllProjectsAssigendToUserByIdAndStatus(Long userId, String status) throws Exception;
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId);
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId);
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId, Long approverUserId);
	
	public List<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,String status,Long locationsId, Long approverUserId);
    
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate,Date endDate,Long locationsId, Long approverUserId);
	
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(String status,String startDate,String endDate,Long projectId);
    
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds);
	
	public List<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId,String status,OrgLocations loginOrgLocation) throws Exception;
	
	List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate,Date endDate,Long locationsId, Long projectId) throws Exception;

	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate) throws Exception;

	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId)throws Exception;

	public List<ProjectMembers> getAllAssignedProjectsForUser(Long userId,Long orgLocationId)throws Exception;
	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId);

	public List<ProjectMembers> getProjectsAssignedMembersForExpenses(Long projectId)throws Exception;

	public List<ProjectMembers> getVendorAssignedMembersForProjects(Long projectId, Long orgLocationId, Long orgRelationId)throws Exception;
	
	public void saveAssignedProjectMembers(ProjectAssignedMembersDTO projectAssignedMembersDTO,String projectUUID,User user,OrgLocations orgLocations) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId,String status) throws Exception;

	public String transferMembers(OrgLocations orgLocations,Org org,Projects projects,String projectMembers,User user,Long projectOrgLocationId) throws Exception;
	
	Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception;
	
	ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId,Long projectId) throws Exception;
	
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
	
	public ProjectMembers getConsultantUserProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
}