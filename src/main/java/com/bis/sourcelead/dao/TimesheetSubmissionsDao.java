package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissions;

public interface TimesheetSubmissionsDao {

	
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
	
    public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions)throws Exception;
    
    public TimesheetSubmissions getTimesheetSubmissions(String id)throws Exception;
    
    public Boolean deleteTimesheetSubmissions(String id)throws Exception;
    
    public List<TimesheetSubmissions> getAllTimesheetSubmissions()throws Exception; 
    
    
    
}