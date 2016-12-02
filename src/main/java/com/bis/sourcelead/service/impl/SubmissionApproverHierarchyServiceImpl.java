package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {

	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
    ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
    EmployeesDao  employeesDao;
	
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
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionLowLevelApproverHierarchyForEmployee(submissionId);
	}
	
	
	@Override
	public List<SubmissionApproverHierarchy>getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
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
			Long approverUserId,Long submissionId) {
		// TODO Auto-generated method stub
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, approverUserId, submissionId);
	}

	@Override
	public void updateSubmissionStatus(Long submissionId, String status) {
		// TODO Auto-generated method stub
		submissionApproverHierarchyDao.updateSubmissionStatus(submissionId, status);
		
	}

	@Override
	public void updateApprovalStatusForApprover(Long empId, Long approverUserId, Date startDate, String status,
			String rejectedReason) {
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

	
	
	

}
