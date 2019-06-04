package com.bis.sourcelead.timesheet.dao;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetApprovalSubmissionDetailsDTO;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface SubmissionApproverHierarchyDao {
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
	
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)throws Exception;
   
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByProjectApproverHierarchyId(Long projectApproverHierarchyId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(Long projectApproverHierarchyId,Long submissionId);
   
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id)throws Exception;
   
	public Boolean deleteSubmissionApproverHierarchy(String id)throws Exception;

	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies()throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId);
	
	void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType,String status, Invoice invoice) throws Exception;
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeBySubmissionId(Long submissionId,Long masterCandidateId);
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId);
		
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,Long approverUserId,Long submissionId);
     
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId,Date startDate,Date endDate,String status);
	
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId,Long masterCandidateId,Date startDate,Date endDate,String status);
	
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId,
			Date startDate, Date endDate, String reason, User loginUser, String type, String isSuperUser) throws Exception;
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus);
	
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 
	
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId,Long projectId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String submissionStatus); 
    
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus); 

	public Integer getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLoginCount(Long masterCandidateId, String submissionStatus, Long orgLocationId);

	
	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus); 
	
	public Integer getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLoginCount(Long masterCandidateId, String submissionStatus, Long orgLocationId,Long orgId);

	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public  List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 

	public  List<SubmissionApproverHierarchy>  getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus); 
	
	ResponseList<SubmissionApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(Long userId, Long orgLocationId, String submissionStatus,String searchValue,int start,int length,String isGlobalUser);
	
	public Integer getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginCount(Long approverUserId,Boolean isAdmin,Long locationsId,Long orgId,String submissionStatus,String isGlobalUser);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);
	
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchiesByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId,Long locationId) throws Exception;
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(Date frequencyStartDate, Date frequencyEndDate, Long projectId,Long masterCandidateId);
			
	public Boolean getPendingForApprovalTimesheetReportByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);

 
	//public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate,String isGlobalUser) throws Exception;
	
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId,Long approverUserId, Date startDate, Date endDate, String status,String rejectedReason, OrgLocations orglocations,String isGlobalUser) throws Exception;

	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long masterCandidateId,Long projectId);

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long locationsId,Long projectMemberId, Date startDate, Date endDate,String isGlobalUser) throws Exception;
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long locationsId,Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId,Long locationsId, Date startDate, Date endDate,String isGlobalUser) throws Exception;
	
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId,Long orgLocationsId,Date frequencyStartDate, Date frequencyEndDate, String invoiceType) throws Exception;

	public Boolean isReopenButtonVisible(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId,Long locationId,Long orgId,String isGlobalAccess);
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByProjectIdAndMasterCandidateId(Long projectId, Long masterCandidateId);

	public Map<String,SubmissionApproverHierarchy> getTotalHoursByProjectIdAndLocationIdApprovalStatusStartDateEndDate(Long loginOrgLocationId,Long projectId,Date startDate,Date endDate)  throws Exception;

	public Boolean isMissingTimesheetPerProjectByMasterCandidateIdAndProjectId(Date frequencyStartDate, Date frequencyEndDate,Long masterCandidateId, Long projectId);
	
	public ResponseList<TimesheetApprovalSubmissionDetailsDTO> getSubmissionApproverHierarchyByTimesheetFrequencyOrgLocationsIdAndApproverUserId(Set<Long> projectIds,Boolean isAdmin,String timesheetFrequency,Date startDate,Date endDate,Long clientId,Long projectId,String status,Long locationsId,Long orgId,Long approverUserId,String searchValue,String projectName,int start, int length,int totalRows,String isGlobalUser,String tableFor,String dateFormat) throws Exception;

	public LinkedHashMap<Date, Float> getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long projectId, Long pmMasterCandidateId, Date startDate, Date endDate,DateFormat format,String dateFormat) throws Exception;

	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForPendinApproveTimesheetsByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId);
}