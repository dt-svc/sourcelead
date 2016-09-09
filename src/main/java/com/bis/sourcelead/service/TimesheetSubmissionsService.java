package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.bind.annotation.RequestParam;

import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;

public interface TimesheetSubmissionsService {
	
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId,Date startDate)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate, Long  userId)throws Exception; 
    
    public TimesheetSubmissions  getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate, Long  userId)throws Exception;
    
    public TimesheetSubmissions  getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(Date startDate,Date endDate, Long  userId,Long  empUserId,Long employeeId)throws Exception;
    
    public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId) throws Exception;
    
    Date getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId,Date currentStartDate);
    
    Date getRightNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId,Date currentStartDate);
     
    Date getLeftNavStartDateForApproverBasedOnCurrentStartDate(Long  approverUserId,Long empUserId,Date currentStartDate);
    
    Date getRightNavStartDateForApproverBasedOnCurrentStartDate(Long  approverUserId,Long empUserId,Date currentStartDate);
    
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId,Date startDate) throws Exception ;
    
    public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId);
    
    public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus)throws Exception;

	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus); 
	
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId,String approvalStatus)throws Exception;
	
    public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserId(Long userId) throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsHoursCountByUserId(Long userId);
	
	public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId ,String clientId,String values)throws Exception;
	
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus,Long orgLocationId)throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,Long orgLocationId) throws Exception;
	
	public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId)throws Exception ;
	
	public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(Long memberId, Date terminatedDate) throws Exception;
	
	public String getArrovalSubmissions(String employeeId,User user)throws Exception;
	
	public String dynamicTimesheet(User user,String navigation,String startDate,Long memberId,Long projectMemberUserId, Long employeeId) throws Exception ;
	
}