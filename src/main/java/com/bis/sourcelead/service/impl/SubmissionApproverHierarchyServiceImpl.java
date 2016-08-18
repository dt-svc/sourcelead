package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {

	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	

	@Override
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.addSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.updateSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchy(id);
	}

	@Override
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.deleteSubmissionApproverHierarchy(id);
	}

	@Override
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getAllSubmissionApproverHierarchies();
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployee(submissionId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyBySubmissionId(submissionId);
	}
	
	

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,
			Long approverUserId) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForApprover(submissionId, approverUserId);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId, Date startDate) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, approverUserId, startDate);
	}

	@Override
	public void updateSubmissionStatus(Long submissionId, String status) {
		// TODO Auto-generated method stub
		submissionApproverHierarchyDao.updateSubmissionStatus(submissionId, status);
		
	}

	@Override
	public void updateApprovalStatusForApprover(Long empId, Long approverUserId, Date startDate, String status,
			String rejectedReason) {
		// TODO Auto-generated method stub
		submissionApproverHierarchyDao.updateApprovalStatusForApprover(empId, approverUserId, startDate, status, rejectedReason);
	}

	@Override
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForTheSubmission(submissionId,approvalStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String approvalStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String submissionStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectId, submissionStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String approvalStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String submissionStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectId, submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		// TODO Auto-generated method stub
		
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(
			Long userId, Long orgLocationId, String status,String userRole) {
	
		
		List<SubmissionApproverHierarchy> list = null  ;
		
         if(status != null){
 			if(userRole.equalsIgnoreCase("ROLE_ADMIN")){
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					 list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId, status);
 				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED")  ||  status.equalsIgnoreCase("NEW") ){
 					 list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  status);
 				}
 				
 			}else if( userRole.equalsIgnoreCase("ROLE_USER") ){
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId, status);
 				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED") ||  status.equalsIgnoreCase("NEW")){
 					list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,  status);
 				}
 				
 			}else if( userRole.equalsIgnoreCase("ROLE_APPROVER") ){
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId, status);
 				}else if( status.equalsIgnoreCase("DRAFT") || status.equalsIgnoreCase("SUBMITTED") ||  status.equalsIgnoreCase("NEW")){
 					list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  status);
 				}

 			}
 			
 		}
         
         return list;
	}
	
	
	

}
