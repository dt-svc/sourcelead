package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@Service
public class TimesheetSubmissionsServiceImpl implements TimesheetSubmissionsService {

	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	
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
			     Integer draftTimesheetsSubmissionsCount = null;
			     Integer newTimesheetsSubmissionsCount   = null;
				 Integer savedTimesheetsSubmissionsCount = null;
				 Integer waitingForApprovalTimesheetSubmissionCount = null;
				 Integer approvedTimesheetSubmissionCount = null;
				 Integer rejectedTimesheetSubmissionCount =null;
				 
				      //draft
				         draftTimesheetsSubmissionsCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId, "DRAFT");
				      //new
				         newTimesheetsSubmissionsCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId, "NEW");
				         savedTimesheetsSubmissionsCount =  newTimesheetsSubmissionsCount +draftTimesheetsSubmissionsCount;
				         timeSheetSubmissionCount.setSavedTimesheetSubmissionCount(savedTimesheetsSubmissionsCount);
			  	       //submitted
				         waitingForApprovalTimesheetSubmissionCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId, "SUBMITTED");
				         timeSheetSubmissionCount.setWaitingForApprovalTimesheetSubmissionCount(waitingForApprovalTimesheetSubmissionCount);
			  	       //approved
				         approvedTimesheetSubmissionCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId, "APPROVED");
				         timeSheetSubmissionCount.setApprovedTimesheetSubmissionCount(approvedTimesheetSubmissionCount);
			 	       //rejected
				         rejectedTimesheetSubmissionCount = timesheetSubmissionsDao.getTimeSheetSubmissionsCountBasedOnStatus(userId, "REJECTED");
				         timeSheetSubmissionCount.setRejectedTimesheetSubmissionCount(rejectedTimesheetSubmissionCount);
				
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
						         draftTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "DRAFT");
					             timeSheetSubmissionCount.setDraftTimesheetsSubmissionsHoursCount(draftTimesheetsSubmissionsHoursCount.toString());
					           //Submitted
					             submittedTimesheetsSubmissionsHoursCount = timesheetSubmissionsDao.getTotalDraftAndSubmittedHoursByuserId(userId, "SUBMITTED");
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

}
