package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.SubmissionApproverHierarchy;

public interface SubmissionApproverHierarchyDao {
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
   
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(SubmissionApproverHierarchy submissionApproverHierarchy)throws Exception;
   
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id)throws Exception;
   
	public Boolean deleteSubmissionApproverHierarchy(String id)throws Exception;
    
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies()throws Exception; 
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId);
	
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,Long approverUserId);
		
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,Long approverUserId,Date startDate);
     
	public void updateSubmissionStatus(Long submissionId,String status);
	
	public void updateApprovalStatusForApprover(Long empId,Long approverUserId,Date startDate,String status,String rejectedReason);
	
}