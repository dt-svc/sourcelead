package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {

	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
    ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    EmployeesDao  employeesDao;
	
	@Override
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setCreatedDate(commonUtil.currentDate());
		submissionApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return submissionApproverHierarchyDao.addSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setCreatedDate(commonUtil.currentDate());
		submissionApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		return submissionApproverHierarchyDao.updateSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchy(id);
	}

	@Override
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.deleteSubmissionApproverHierarchy(id);
	}

	@Override
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception {
		return submissionApproverHierarchyDao.getAllSubmissionApproverHierarchies();
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployee(submissionId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionLowLevelApproverHierarchyForEmployee(submissionId);
	}
	
	
	@Override
	public List<SubmissionApproverHierarchy>getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyBySubmissionId(submissionId);
	}
	
	

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,
			Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForApprover(submissionId, approverUserId);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId,Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, approverUserId, submissionId);
	}

	@Override
	public void updateSubmissionStatus(Long submissionId, String status) {
		submissionApproverHierarchyDao.updateSubmissionStatus(submissionId, status);
		
	}

	@Override
	public void updateApprovalStatusForApprover(Long empId, Long approverUserId, Date startDate, String status,
			String rejectedReason) {
		submissionApproverHierarchyDao.updateApprovalStatusForApprover(empId, approverUserId, startDate, status, rejectedReason);
	}

	@Override
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForTheSubmission(submissionId,approvalStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectId, submissionStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(Long userId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectId, submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(
			Long userId, Long orgLocationId, String status,String userRole) throws Exception {
	
		
		
		List<SubmissionApproverHierarchy> list = new ArrayList<SubmissionApproverHierarchy>();
		
         if(status != null){
 			//if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_APPROVER".equalsIgnoreCase(userRole)   ||  "ROLE_TEAM_LEAD".equalsIgnoreCase(userRole)){
 				/*
 				 *  New  Timesheet List  as  per new User Story 
 				 */
 				 if("APPROVED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  status);
  				}
                 if("REJECTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
                 if("SUBMITTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
                 if("DRAFT".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, status);
  				}
 				
                 /*}else if("ROLE_USER".equalsIgnoreCase(userRole)  ){
 				
 				
 				if("APPROVED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,  status);
 				}
                if("REJECTED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}
                if("SUBMITTED".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}
                if("DRAFT".equalsIgnoreCase(status)){
    				list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, status);
 				}

 				
 			}*/

 		}
     	
         
         return list;
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByApproverUserId(approverUserId);
	}

	@Override
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)
			throws Exception {
		submissionApproverHierarchyDao.saveAllSubmissionApproverHierarchy(submissionApproverHierarchyList);
		
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin (Long approverUserId,
			Long projectMemberId, Date startDate, Date endDate) throws Exception{
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(approverUserId,projectMemberId, startDate, endDate);
	}

	@Override
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId, Long approverUserId,
			Date startDate, Date endDate, String status, String rejectedReason) throws Exception {
		 submissionApproverHierarchyDao.updateApprovalStatusByApproverForProjectMemberId(projectMemberId, approverUserId, startDate, endDate, status, rejectedReason);
	}

	
	
	

}
