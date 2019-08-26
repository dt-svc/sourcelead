package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
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
	
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(Long locationsId,Long orgId, Long masterCandidateId);
	
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long id,Long locationsId,String isGlobalUser) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByemployeeId(Long employeeId) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(Date startDate, Date endDate,Long locationsId, Long approverUserId);
	
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId,OrgLocations orgLocations) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception;
	
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheet() throws Exception;

//	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId);
	
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception;
	
	public ProjectMembers getProjectMemberByUserIdAndProjectId(Long userId,Long projectId) throws Exception;
	
	public ProjectMembers getProjectMemberByEmpIdAndProjectId(Long empId,Long projectId) throws Exception;
	
	public Integer  getAllProjectMembersCountByProjectId(Long id) throws Exception;
	
	public Integer updateProjectMemberDeletedReason(Long projectMemberId, String reason);

	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String mEMBER_ACTIVE,Long orgLocationId) throws Exception;

	public Integer updateEndDateOfProjectMemberId(Date endDate1, Long projectMemberId);
	
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId ,Long projectId) ;
	
	public String storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(Long projectId,Long employeeId,String startDate,String endDate,String mailtoMembers,String loginOrgCode,User user,OrgLocations loginOrgLocation) throws Exception ;

	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndActiveStatusRecords(Long projectClientsId) throws Exception;
	
	/*by sarath*/
    public Set<ProjectMembers> prepareProjectMembersListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception;
    
    public List<ProjectMembers> getAllProjectsAssigendToUserByIdAndStatus(Long userId, String status, Long orgLocationId) throws Exception;
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId);
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId, Long orgId, Long candidateId);
    
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId, Long approverUserId);
	
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(String timesheetFrequency,Date startDate,Date endDate,Long clientId,Long projectId,String status,Long locationsId, Long approverUserId,String searchValue,String projectName, int start, int length,String timesheetFor,String isGlobalUser);
    
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate,Date endDate,Long locationsId, Long approverUserId);
	
    public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(String status,String startDate,String endDate,Long projectId);
    
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds);
	
	public List<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId,String status,OrgLocations loginOrgLocation,String roleIds,String teamIds,String isGlobalAccess) throws Exception;
	
	List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate,Date endDate,Long locationsId, Long projectId) throws Exception;

	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate) throws Exception;

	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId)throws Exception;

	public List<ProjectMembers> getAllAssignedProjectsForUser(Long userId,Long orgLocationId, String fromType)throws Exception;
	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId);

	public List<ProjectMembers> getProjectsAssignedMembersForExpenses(Long projectId)throws Exception;

	public List<ProjectMembers> getVendorAssignedMembersForProjects(Long projectId, Long orgLocationId, Long orgRelationId,String isGlobalUser)throws Exception;
	
	public void saveAssignedProjectMembers(ProjectAssignedMembersDTO projectAssignedMembersDTO,String projectUUID,User user,OrgLocations orgLocations,String isGlobalUser) throws Exception;
	
	public void saveAssignedSharedProjectMembers(ProjectAssignedMembersDTO projectAssignedMembersDTO,String projectUUID,User user,OrgLocations orgLocations,String isGlobalUser) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId,String status) throws Exception;

	public String transferMembers(OrgLocations orgLocations,Org org,Projects projects,String projectMembers,User user,Long projectOrgLocationId) throws Exception;
	
	Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception;
	
	ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId,Long projectId) throws Exception;
	
	ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId,Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(Long masterCandidateId,Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
	
	public ProjectMembers getConsultantUserProjectMemberByProjectMemberId(Long projectMemberId) throws Exception;
	
	ProjectMembers getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId,Long projectId,Long locationId,String isGlobalUser) throws Exception;
	
	ProjectMembers getProjectMembersByCandidateIdAndProjectClientId(Long candidateId,Long projectClientsId) throws Exception;
	
	public List<ProjectMembers> getAllProjectMembersByProjectClientId(Long projectClientsId) throws Exception;
	
	List<ProjectMembers> getAllProjectMembersByMasterCandidateId(Long masterCandidateId,Long projectId) throws Exception;
	
	public ProjectMembers getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,Long locationId,Long orgId,String isGlobalAccess) throws Exception;
	
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(Long locationsId,Date startDate,Date endDate,int start,int length);
	
	public Boolean getPayRateOrBillRateChanges(ProjectMembers projectMembers,ProjectMembersDTO projectMembersDTO) throws Exception;
	
	public List<ProjectMembers> getProjectMembersListByOrgIdAndMasterCandidateId(Long orgId, Long masterCandidateId);

	public List<ProjectMembers> getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception;
	
	public ProjectMembers getProjectMemberByUserIdAndLoginOrglocationId(Long userId, Long orgLocationId) throws Exception;
	
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId);
	
	public List<ProjectMembers> getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(Long locationsId,Long orgId, Long candidateId,String timesheetFrequency);
	
	List<ProjectMembers> getAllProjectMembersListByMasterCandidateId(Long masterCandidateId,Long projectId) throws Exception;
	
	public List<ProjectMembers> getAllActiveProjectsByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public ProjectMembers getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(Long candidateId,Long projectId,Long orgLocationId) throws Exception;
	
	List<ProjectMembers> getProjectMembersListByCandidateIdAndProjectClientId(Long candidateId,List<Long> projectClientsId) throws Exception;
	
	public List<Long> getAllProjectMembersIdsByProjectIdAndOrgLocationId(Long projectId,Long orglocationId) throws Exception;
	
	ResponseList<MarginSheetReportDTO> getProjectMemeberByClientProjectAndOrglocation(Long orglocationId,Date[] searchDates,MarginSheetReportDTO marginSheetReportDTO);
	
	ProjectMembers getProjectMembersByMasterCandiateIdAndProjectClientId(Long masterCandidateId,Long projectClientsId) throws Exception;

	public List<Candidate> getAllAvailableAssociates( Long orgLocation);
	
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectClientId(Long masterCandidateId, Long projectClientsId) throws Exception;
	
	public List<ProjectMembers> getAllProjectsAssigendToCandidateIdAndStatus(Long candidateId,String status) throws Exception;
	
	public List<ProjectMembers> getAllResourceTimeEntryProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception;
			 
	
}