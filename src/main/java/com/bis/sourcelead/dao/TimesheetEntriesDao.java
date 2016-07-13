package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetEntries;

public interface TimesheetEntriesDao {

	
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries)throws Exception;
	
    public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries)throws Exception;
    
    public TimesheetEntries getTimesheetEntries(String id)throws Exception;
    
    public Boolean deleteTimesheetEntries(String id)throws Exception;
    
    public List<TimesheetEntries> getAllTimesheetEntries()throws Exception; 
    
    public Collection<TimesheetEntries>  getAllTimesheetEntriesBySubmissionId(Long submissionId)throws Exception;
        
}