package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.aop.ThrowsAdvice;

import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsService {
	
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate, Long  userId)throws Exception; 
    
    public TimesheetSubmissions  getAllApprovalEmployeeTimesheetSubmissions(Date startDate,Date endDate, Long  userId)throws Exception;
    
    public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId) throws Exception;
     
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId,Date startDate) throws Exception ;
    
    public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId);
    
    public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId,String approvalStatus)throws Exception;

	public Integer getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus);

	public Integer getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus); 
}