package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.TimesheetEntriesForm;

public interface TimesheetEntriesService {
	
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries)throws Exception;
	
    public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries)throws Exception;
    
    public TimesheetEntries getTimesheetEntries(String id)throws Exception;
    
    public Boolean deleteTimesheetEntries(String id)throws Exception;
    
    public List<TimesheetEntries> getAllTimesheetEntries()throws Exception; 
    
    public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId)throws Exception; 
    
    public String newTimesheet(User user,String navigation, String startDate) throws Exception;
    
    public String dynamicTimesheetpopUp(User user, Long employeeId,String startDate,String endDate) throws Exception ;
    
    public  String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm ,String json,MultipartHttpServletRequest request, String status,String savingType) throws Exception ;

}