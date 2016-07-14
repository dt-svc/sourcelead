package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsDao {

	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllTimesheetSubmissions(Date startDate , Date endDate , List<Long>  projectMemberIds)throws Exception; 
    
    public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception;

    public Date getNextMondayByProjectMemberId(Long pojectMemberId); 
    
    public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception ;
    
    public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId,Long userId) throws Exception ;
   
}