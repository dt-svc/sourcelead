package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@Service
public class TimesheetSubmissionsServiceImpl implements TimesheetSubmissionsService {

	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	
	@Override
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		return timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
	}
	
	@Override
	public HashMap<String, Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getMinAndMaxTimesheetSubmissionDatesForEmployee(userId);
	}

	@Override
	public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.updateTimesheetSubmissions(timesheetSubmissions);
	}

	@Override
	public TimesheetSubmissions getTimesheetSubmissions(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getTimesheetSubmissions(id);
	}

	@Override
	public Boolean deleteTimesheetSubmissions(String id) throws Exception {
		Boolean status = timesheetSubmissionsDao.deleteTimesheetSubmissions(id);
		return status;
		// TODO Auto-generated method stub
	}

	@Override
	public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
		List<TimesheetSubmissions> list = timesheetSubmissionsDao.getAllEmployeeTimesheetSubmissions(startDate,endDate,userId);
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate ,Date endDate,Long userId) throws Exception {
		TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissions(startDate,endDate,userId);
		// TODO Auto-generated method stub
		return list;
	}
	
	
	@Override
	public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate ,Date endDate,Long userId,Long memberId,Long employeeId) throws Exception {
		TimesheetSubmissions list = timesheetSubmissionsDao.getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(startDate,endDate,userId,memberId,employeeId);
		// TODO Auto-generated method stub
		return list;
	}
	
	
	@Override
	public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
		 timesheetSubmissionsDao.saveAllTimesheetSubmissions(timesheetSubmissionsList);
	}

	@Override
	public Date getNextMondayByProjectMemberId(Long pojectMemberId) {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getNextMondayByProjectMemberId(pojectMemberId);
	}

	@Override
	public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getApprovalTimesheetSubmissions(userId);
		return list;
	}

	@Override
	public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId,Date startDate)
			throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimesheetSubmissionsByAproverId(projectMemberId,userId,startDate);
		return list;
	}
	
	@Override
	public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus) throws Exception {
		
		Integer approvalStatusCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus);
		return approvalStatusCount;
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus) {
		return timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId,approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus) {
		return timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId,submissionStatus);
	}
	
	@Override
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId, String approvalStatus)
			throws Exception {
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimeSheetSubmissionsListByStatus(userId,approvalStatus);
		return list;
	}

	
	
	// Sammeta David: this  controller method is for getting TimesheetSubmission Counts based on Status.
		@Override
		public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserId(Long userId) throws Exception {
			
			
			
				

				 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
				 
				            Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, new Long(3), "DRAFT");
					        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "DRAFT");
					        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
					        timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(draftTimesheetsSubmissionsCount);

					        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "SUBMITTED");
					        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "SUBMITTED");
					        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
					        timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(submittedTimesheetsSubmissionsCount);
							
						    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "APPROVED");
						    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "APPROVED");
					        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
					        timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(approvalTimesheetsSubmissionsCount);
							
							Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId,new Long(3), "REJECTED");
						    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId,new Long(3), "REJECTED");
						    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
						    timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(rejectedTimesheetsSubmissionsCount);
				
				        return timeSheetSubmissionCount;
			}

			// Sammeta David: this  controller method is for getting TimesheetSubmission Hours Counts based on login User Id.
			@Override
			public TimeSheetSubmissionCount getTimeSheetSubmissionsHoursCountByUserId(Long userId) {
				// TODO Auto-generated method stub
				
				    TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
				    
				    Float draftTimesheetsSubmissionsHoursCount = null;
				    Float submittedTimesheetsSubmissionsHoursCount = null;
				    Float approvalTimesheetsSubmissionsHoursCount = null;
				    Float rejectedTimesheetsSubmissionsHoursCount = null;
					
						       //draft
						         draftTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "NEW");
					             timeSheetSubmissionCount.setDraftTimesheetsSubmissionsHoursCount(draftTimesheetsSubmissionsHoursCount.toString());
					           //Submitted
					             submittedTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "SUBMITTEDFORAPPROVAL");
					             timeSheetSubmissionCount.setSubmittedTimesheetsSubmissionsHoursCount(submittedTimesheetsSubmissionsHoursCount.toString());
					           //Approved
					             approvalTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId, "APPROVED");
					  	         timeSheetSubmissionCount.setApprovalTimesheetsSubmissionsHoursCount(approvalTimesheetsSubmissionsHoursCount.toString());
					  	       //Rejected  
					  	        rejectedTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalApprovedOrRejectedHoursByuserId(userId, "REJECTED");
						  	    timeSheetSubmissionCount.setRejectedTimesheetsSubmissionsHoursCount(rejectedTimesheetsSubmissionsHoursCount.toString());
						  	  
				  return timeSheetSubmissionCount;
			}
			
			

	@Override
		public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId,
				String clientId, String values) throws Exception {
			Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getProjectsBasedOnUserIdAndStatusAndClientForFilters(userId,clientId,values);
			return list;
		}
	
	@Override
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId, String approvalStatus,Long orgLocationId) throws Exception {
		Integer approvalStatusCount = timesheetSubmissionsDao.getUsersTimeSheetSubmissionsCountBasedOnStatus(userId,approvalStatus,orgLocationId);
		return approvalStatusCount;
	}

	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		
		 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		 
		Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "APPROVED");
		timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
		 
		Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "REJECTED");
		timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
		
		Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"SUBMITTEDFORAPPROVAL");
		timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
		
		Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"NEW");
		
		Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"DRAFT");
		Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() +draftindividualEmployeeList.size();
		
		timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
		
		return timeSheetSubmissionCount;
		 
	}

	@Override
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,
			Long orgLocationId) throws Exception {
		
		 TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		
		 
		    Collection<SubmissionApproverHierarchy> approvedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  "APPROVED");
			timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(new Float(approvedindividualEmployeeList.size()));
			 
			Collection<SubmissionApproverHierarchy> rejectedindividualEmployeeList = submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, "REJECTED");
			timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(new Float(rejectedindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> submittedForApprovalindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"SUBMITTEDFORAPPROVAL");
			timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(new Float(submittedForApprovalindividualEmployeeList.size()));
			
			Collection<SubmissionApproverHierarchy> newindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"NEW");
			
			Collection<SubmissionApproverHierarchy> draftindividualEmployeeList   = submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"DRAFT");
			Integer savedTimesheetSubmissionCount = newindividualEmployeeList.size() +draftindividualEmployeeList.size();
			
			timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(new Float(savedTimesheetSubmissionCount));
			
		return timeSheetSubmissionCount;
	}
	
	  @Override
		public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(Long userId,
				String employeeId) throws Exception {
			Collection<TimesheetSubmissions> thslist = timesheetSubmissionsDao.getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(userId,employeeId);
			return thslist;
		}
		
		
		@Override
		public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId)
				throws Exception {
			// TODO Auto-generated method stubgetSubmissionApproverHierarchyForEmployeeList
			List<TimesheetSubmissions> list = timesheetSubmissionsDao.getSubmissionApproverHierarchyForEmployeeList(approverUserId);
			return list;
		}

		@Override
		public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId, Date startDate)
				throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		

}
