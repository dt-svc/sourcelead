package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsDao {

	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllEmployeeTimesheetSubmissions(Date startDate,Date endDate,Long userId)throws Exception; 
    
    public HashMap<String,Date> getMinAndMaxTimesheetSubmissionDatesForEmployee(Long  userId) throws Exception;
    
    public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId); 
    
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId,Date startDate) throws Exception ;

     
    public Integer getTimeSheetSubmissionsCountBasedOnStatus(Long userId ,String approvalStatus)throws Exception;

	public Integer getTotalApprovedOrRejectedHoursByuserId(Long userId, String approvalStatus);
	
	public Integer getTotalDraftAndSubmittedHoursByuserId(Long userId, String submissionStatus); 
}