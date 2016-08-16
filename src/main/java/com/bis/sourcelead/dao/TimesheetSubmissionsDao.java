package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsDao {

	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long userId)throws Exception;
    
    public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long userId)throws Exception;
    
    public TimesheetSubmissions getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate,Date endDate,Long userId,Long memberId,Long employeeId)throws Exception;
    
    public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId) throws Exception;
    
    public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId); 
    
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId,Date startDate) throws Exception ;

    public TimesheetSubmissions getTimesheetSubmissionsByMemberIdAndStartDate(Long memberId,Date startDate)throws Exception;
     
    public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId ,String approvalStatus)throws Exception;

	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus);
	
	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus); 
	
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId ,String approvalStatus)throws Exception;
	
	public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId ,String clientId,String values)throws Exception;
	
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus,Long orgLocationId)throws Exception;
	
	public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId);
	
	public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(Long memberId, Date terminatedDate) throws Exception;
	
	public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissionsByUserIdAndEmplyeeId(Long userId,String employeeId) throws Exception ;

	public List<TimesheetSubmissions>  getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,String status,Long orgLocationId) throws Exception ;
 
	

}