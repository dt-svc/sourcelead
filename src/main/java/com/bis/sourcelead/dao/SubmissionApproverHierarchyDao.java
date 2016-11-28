package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.SubmissionApproverHierarchy;

public interface SubmissionApproverHierarchyDao {
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
	
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)throws Exception;
   
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
   
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id)throws Exception;
   
	public Boolean deleteSubmissionApproverHierarchy(String id)throws Exception;
    
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies()throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId);
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyBySubmissionId(Long submissionId);
	
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId);
		
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,Long approverUserId,Long submissionId);
     
	public void updateSubmissionStatus(Long submissionId,String status);
	
	public void updateApprovalStatusForApprover(Long empId,Long approverUserId,Date startDate,String status,String rejectedReason);
	
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus);
	
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 
	
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String submissionStatus); 
	
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndProjectIdForEmployee(Long userId,Long projectId, String submissionStatus); 
    
	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus); 

	public List<SubmissionApproverHierarchy>  getTotalApprovedOrRejectedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String approvalStatus);

	public  List<SubmissionApproverHierarchy>  getTotalDraftAndSubmittedListByuserIdAndProjectIdForApprover(Long userId,Long projectId, String submissionStatus); 

	public  List<SubmissionApproverHierarchy>  getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus); 
	
	
}