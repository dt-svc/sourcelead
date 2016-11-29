package com.bis.sourcelead.service.impl;

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
	public String getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(
			Long userId, Long orgLocationId, String status,String userRole) throws Exception {
	
		
		
		List<SubmissionApproverHierarchy> list = new ArrayList<SubmissionApproverHierarchy>();
		
         if(status != null){
 			if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_APPROVER".equalsIgnoreCase(userRole)   ||  "ROLE_TEAM_LEAD".equalsIgnoreCase(userRole)){
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
 				
 			}else if("ROLE_USER".equalsIgnoreCase(userRole)  ){
 				
 				
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

 				
 			}

 		}
     	StringBuilder sb = new StringBuilder();	
     	sb.append(" <table id='example' class='display' cellspacing='0' width='100%' >");
		sb.append(" <thead>");
		sb.append("     <tr>");
		sb.append("         <th>Employee</th>");
		sb.append("         <th>Frequency</th>");
		sb.append("         <th>Total hours</th>");
		sb.append("         <th>Stage</th>");
		sb.append("         <th>Action</th>");
		sb.append("     </tr>");
		sb.append(" </thead>");
		sb.append(" <tbody>");	
		
         if(list!=null && !list.isEmpty()){
				for(SubmissionApproverHierarchy submissionApproverHierarchy:list){
					sb.append("<tr id='row"+submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getId()+"'>");
					TimesheetSubmissions  ts = timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(submissionApproverHierarchy.getSubmissionId().getStartDate(), 
                            submissionApproverHierarchy.getSubmissionId().getEndDate(), 
                            userId, 
                            submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getUser().getId(),
                            submissionApproverHierarchy.getSubmissionId().getProjectMembers().getEmployee().getId());
					
					  ts.setUserId(ts.getProjectMembers().getEmployee().getUser().getId().toString());
				       ts.setEmployeeId(ts.getProjectMembers().getEmployee().getId().toString());
					   if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
						   ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
					   }
					   ts.setStartDateStr(Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate()));
					   ts.setEndDateStr(Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate()));
					 sb.append("  <td><div class='time_appr_user tbldata-dotdot-me'>");
			         sb.append("   <img  id='userImage' onerror='predefineProfileImage(this);' src='#' alt='Profile Image' class='img-circle img-responsive'>");
			         sb.append("    <span><b>"+ts.getFullName()+"</b></span>");
			         sb.append(" <div></td>");
				         
			         sb.append("<td><div id='datefrequency"+ts.getUserId()+"'>");
			         sb.append("  <span class='res_date'><i class='fa fa-calendar'></i>"+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate())+" To "+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate())+"</span>");
			         sb.append("    </div>");
			     	 sb.append("    </td>");
			     	
			     	sb.append("    <td><div> " );
			         sb.append(" <a onclick='myTimesheet(\""+ts.getEmployeeId()+"\",\""+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getStartDate())+"\",\""+Constants.DF_DMY.format(submissionApproverHierarchy.getSubmissionId().getEndDate())+"\");'  id='timesheetPopup"+ts.getId()+"'> "+ts.getCombinedTotalHours()+"</a>");
			         sb.append("    </div>");
			         sb.append("    </td>");
			         
			         sb.append("  <td><div id='status"+ts.getUserId()+"'>"+ts.getCombinedStatus()+"</div></td>");
			         
			         sb.append("    <td><div id='action"+ts.getUserId()+"'> " );
			             if( (ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("NEW")) || ( ts.getCombinedStatus() != null && ts.getCombinedStatus().equalsIgnoreCase("SUBMITTED FOR APPROVAL")) ){
			                 sb.append("<div><i class='fa fa-check' alt='Approve'  onclick='approveSubmission(\""+ts.getUserId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i>|<i class='fa fa-times' alt='Rejected' onclick='rejecteSubmission(\""+ts.getId()+"\",\""+ts.getEmployeeId()+"\",\""+ts.getUserId()+"\",\""+ts.getStartDateStr()+"\",\""+ts.getEndDateStr()+"\");'></i></div>");
			                 sb.append("<div id='rejectedPopup'></div>");
			             }
			             if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("REJECTED")){
			               sb.append(" <div><h3 class='reje_color'>Rejected</h3>");
			               sb.append(" <i class='glyphicon glyphicon-comment' id='"+ts.getId()+"' onclick='rejectedMessage(\"rejectedMessage"+ts.getId()+"\");'>");
			               sb.append(" <div class='app_error_mes' id='rejectedMessage"+ts.getId()+"'>"+ts.getRejectedReason()+"</div>");
			               sb.append(" </i>");
			               sb.append(" </div>");
			            }
			            if(ts.getCombinedStatus() !=null && ts.getCombinedStatus().equalsIgnoreCase("APPROVED")){
			          	  sb.append(" <div><h3>Approved</h3></div>");
			            }  
			            sb.append(" </div>");
			            sb.append("    </td>");
			            sb.append("<div class='clearfix'></div>");
			            sb.append("</tr>");
				}
			}
           sb.append("	<div>");
         
         return sb.toString();
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
