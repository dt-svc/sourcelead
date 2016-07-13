package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsService {
	
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetEntries)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllTimesheetSubmissions(Date startDate , Date endDate , List<Integer>  projectMemberIds)throws Exception; 
     
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId) throws Exception ;
    
}