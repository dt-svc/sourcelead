package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface SubmissionApproverHierarchyService {
	
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
	
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)throws Exception;
	   
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
   
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id)throws Exception;
   
	public Boolean deleteSubmissionApproverHierarchy(String id)throws Exception;
    
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies()throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId);
	
	public void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType,String status) throws Exception;
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId);
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId);

	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,Long approverUserId,Long submissionId);
    
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId,Date startDate,Date endDate,String status);
	
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId,Long masterCandidateId,Date startDate,Date endDate,String status);
	
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId,
			Date startDate, Date endDate, String reason, User loginUser, String type, String isSuperUser) throws Exception;
	
	public void sendReopenTimeSheetMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLoc, String timesheetFrequency, Float totalHours) throws Exception;
	
	public void sendReopenTimeSheetAcceptOrRejectMail(Long projectMemberId,String reason, User loginUser, OrgLocations orgLoc, String timesheetFrequency, String type, User reopenedUser) throws Exception;
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus);
	
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 
	
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandiateId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId,Long projectId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId,Date frequencyStartDate,Date frequencyEndDate, String contractType);
	
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(Long userId,Long orgLocationId, String submissionStatus,String userRole) throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId);
	
	//public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate) throws Exception;
	
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId,Long approverUserId, Date startDate, Date endDate, String status,String rejectedReason) throws Exception;

	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);

	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId, Date startDate, Date endDate) throws Exception;

	
}