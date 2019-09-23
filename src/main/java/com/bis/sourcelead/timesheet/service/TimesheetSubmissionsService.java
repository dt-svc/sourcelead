package com.bis.sourcelead.timesheet.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
    
    //public TimesheetSubmissions getAllEmployeeTimesheetSubmissionsByProjectMemberId(Date startDate,Date endDate, Long  projectMemberId)throws Exception;
    
    public TimesheetSubmissions getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(Date startDate,Date endDate, Long  projectId,Long masterCandidateId)throws Exception;
    
    public List<TimesheetSubmissions> getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateIdForUpdation(Date startDate,Date endDate,  Long  projectId,Long masterCandidateId)throws Exception;
    
    public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissionsByProjectMemberIdForUpdation(Date startDate,Date endDate, Long  projectMemberId)throws Exception;
    
    public TimesheetSubmissions  getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate, Long  userId)throws Exception;
    
    public TimesheetSubmissions  getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(Date startDate,Date endDate, Long  userId,Long  empUserId,Long employeeId)throws Exception;
    
    public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId,Long locationsId) throws Exception;
    
    Date getLeftNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId,Date currentStartDate);
    
    Date getRightNavStartDateForEmployeeBasedOnCurrentStartDate(Long userId,Date currentStartDate);
     
    Date getLeftNavStartDateForApproverBasedOnCurrentStartDate(Long  approverUserId,Long empUserId,Date currentStartDate);
    
    Date getRightNavStartDateForApproverBasedOnCurrentStartDate(Long  approverUserId,Long empUserId,Date currentStartDate);
    
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId,Long locationsId) throws Exception ;
    
    public List<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId,Date startDate) throws Exception ;
    
    public List<TimesheetSubmissions> saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId) throws Exception;
    
    public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus)throws Exception;

	public Float getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus);

	public Float getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus); 
	
	public Collection<TimesheetSubmissions> getTimeSheetSubmissionsListByStatus(Long userId,String approvalStatus)throws Exception;
	
//    public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserId(Long userId) throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsHoursCountByUserId(Long userId);
	
	public Collection<TimesheetSubmissions> getProjectsBasedOnUserIdAndStatusAndClientForFilters(Long userId ,String clientId,String values)throws Exception;
	
	public Integer getUsersTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus,Long orgLocationId)throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(Long userId,Long orgLocationId,Long orgId, String timesheetCountType) throws Exception;
	
	public TimeSheetSubmissionCount getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(Long userId,Long orgLocationId,Long orgId,String countType,String isGlobalUser,Boolean isAdmin) throws Exception;
	
	public List<TimesheetSubmissions> getSubmissionApproverHierarchyForEmployeeList(Long approverUserId)throws Exception ;
	
	public List<TimesheetSubmissions> getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(Long memberId, Date terminatedDate) throws Exception;
	
	public String getArrovalSubmissions(String employeeId,User user,Long locationsId)throws Exception;
	
	public String dynamicTimesheet(User user,String navigation,String startDate,Long memberId,Long projectMemberUserId, Long employeeId,Long locationsId) throws Exception ;
	
	public HashSet<TimesheetSubmissions> getWeekFrequency(Long project,Long LocationsId, Long userId) throws Exception;

	public HashSet<TimesheetSubmissions> getWeekFrequencyByProjMemId(Long projectId) throws Exception;
	
	public List<TimesheetSubmissions> getTimeSheetSubmissionByProjectMemberId(Long pojectMemberId) throws Exception;
	
	public List<TimesheetSubmissions> getTimesheetSubmissionsByProjectIdAndMasterCandidateId(Long  projectId,Long masterCandidateId) throws Exception;

	public TimesheetSubmissions getSubmissionDetailsByProjectIdMasterCandiDateIdStartDateandEndDate(Long projectId,Long masterCanidateId, Date frequencyStartDate, Date frequencyEndDate);

	public String getWeekCommentByCurrentFrequency(Date startDate,Date endDate,Long projectId,Long  masterCandidateId) throws Exception;
	
	public void updateWeekCommentForAllSubmissions(String weekComment,Date startDate,Date endDate,Long projectId,Long  masterCandidateId) throws Exception;
}