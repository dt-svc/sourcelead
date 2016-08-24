package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {

	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
    ProjectApproverHierarchyDao projectApproverHierarchyDao;

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
	
		
		
		List<SubmissionApproverHierarchy> list = new ArrayList<SubmissionApproverHierarchy>();
		
         if(status != null){
 			if(userRole.equalsIgnoreCase("ROLE_ADMIN")){
 				
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					
 					  list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId, status);
 				}else if(status.equalsIgnoreCase("SUBMITTED")){
 					
 					  list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  status);
 				}else if(status.equalsIgnoreCase("NEW")){
 					
 					   List<SubmissionApproverHierarchy> newlist = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  "NEW");
					   List<SubmissionApproverHierarchy> draft = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  "DRAFT");
					
					   list.addAll(newlist);
					   list.addAll(draft);
 				}
 				
 			}else if( userRole.equalsIgnoreCase("ROLE_USER") ){
 				
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					
 					list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId, status);
 				}else if(status.equalsIgnoreCase("SUBMITTED")){
 					
 					list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,  status);
 				}
 			   else if(status.equalsIgnoreCase("NEW")){
 				        List<SubmissionApproverHierarchy> newlist   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,  "NEW");
					    List<SubmissionApproverHierarchy> draft  = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId,orgLocationId,   "DRAFT");
					   
					    list.addAll(newlist);
					    list.addAll(draft);
 			  }
 				
 			}else if( userRole.equalsIgnoreCase("ROLE_APPROVER") ){
 				
 				if( status.equalsIgnoreCase("APPROVED") || status.equalsIgnoreCase("REJECTED") ){
 					
 					list = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId, status);
 				}else if(status.equalsIgnoreCase("SUBMITTED")){
 					
 					list = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  status);
 				}else if(status.equalsIgnoreCase("NEW")){
 					
 				    List<SubmissionApproverHierarchy> newlist = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  "NEW");
 				    List<SubmissionApproverHierarchy> draft  =  submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,  "DRAFT");
 				   
 				    list.addAll(newlist);
				    list.addAll(draft);
 				}
 			}
 			
 		}
        
         if(list!=null && !list.isEmpty()){
				for(SubmissionApproverHierarchy submissionApproverHierarchy:list){
			       
					submissionApproverHierarchy.setProjectName(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getProjects().getProjectName());
					
					 String pattern = "yyyy-MM-dd";
		    	     SimpleDateFormat format = new SimpleDateFormat(pattern);
					 submissionApproverHierarchy.setStartDate( Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate()));
					 submissionApproverHierarchy.setEndDate(Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate()));
					 submissionApproverHierarchy.setEmployeeName(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getFirstName()+""+submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getLastName());
					if(submissionApproverHierarchy.getSubmissionId().getTotalHours() != null){
						 submissionApproverHierarchy.setTotalHours(Float.valueOf(submissionApproverHierarchy.getSubmissionId().getTotalHours()));
					}
					 
					Collection<ProjectApproverHierarchy> projectApproverHierarchies = null;
					try {
						projectApproverHierarchies = projectApproverHierarchyDao.getApproverUsersByProjectId(submissionApproverHierarchy.getSubmissionId().getProjectMembers().getProjects().getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					for(ProjectApproverHierarchy pah : projectApproverHierarchies){
						if(pah.getLevel() == 1){
							submissionApproverHierarchy.setManagerLevel1(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
						}else if(pah.getLevel() == 2){
							submissionApproverHierarchy.setManagerLevel2(pah.getApproverUserId().getFirstName()+""+pah.getApproverUserId().getLastName());
						}
					}
				}
			}
         
         
         return list;
	}
	
	
	

}
