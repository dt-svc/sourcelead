package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface SubmissionApproverHierarchyDao {
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
	
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)throws Exception;
   
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
   
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id)throws Exception;
   
	public Boolean deleteSubmissionApproverHierarchy(String id)throws Exception;

	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies()throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId);
	
	void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType,String status) throws Exception;
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId);
	
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

	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public  List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 

	public  List<SubmissionApproverHierarchy>  getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus); 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(Date frequencyStartDate, Date frequencyEndDate, Long projectId,Long masterCandidateId);
			
 
	//public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate) throws Exception;
	
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId,Long approverUserId, Date startDate, Date endDate, String status,String rejectedReason) throws Exception;

	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate,Date frequencyEndDate,Long projectMemberId);

	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus);
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(Long approverUserId,Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public Map<String,Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId, Date startDate, Date endDate) throws Exception;
	
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId, Date frequencyStartDate, Date frequencyEndDate, String invoiceType);

	public Boolean isReopenButtonVisible(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId,Long locationId);
}