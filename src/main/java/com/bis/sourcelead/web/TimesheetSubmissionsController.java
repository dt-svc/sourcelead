package com.bis.sourcelead.web;


import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController 
public class TimesheetSubmissionsController {
	
	 
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;

	
	@RequestMapping(value = "/getArrovalSubmissions",  method = RequestMethod.POST)
	public @ResponseBody  String getArrovalSubmissions(HttpServletRequest request) throws Exception {
		
		String resultJson = null;
        User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
        Collection<TimesheetSubmissions> timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissions(user.getId());
        
        ArrayList<TimesheetSubmissions> timesheetSubmissionsArrList = new ArrayList<TimesheetSubmissions>();
        for(TimesheetSubmissions ts : timesheetSubmissionsList){
        	if(ts.getProjectMembers() != null && ts.getProjectMembers().getEmployee() != null && ts.getProjectMembers().getEmployee().getUser() != null){
        		ts.setFullName(ts.getProjectMembers().getEmployee().getUser().getFirstName()+" "+ts.getProjectMembers().getEmployee().getUser().getLastName());
        	}
            if(ts.getStartDate()!=null){
            	ts.setStartDateStr(Constants.DF_DMY.format(ts.getStartDate()));
            }
        	if(ts.getEndDate()!=null){
        		ts.setEndDateStr(Constants.DF_DMY.format(ts.getEndDate()));
        	}
            timesheetSubmissionsArrList.add(ts);
         }
         resultJson = toJSON(timesheetSubmissionsArrList);
         return resultJson;
         
	}
	
	@RequestMapping(value = "/saveSubmissionApprovalStatus", method = RequestMethod.POST)
    public  String saveSubmissionApprovalStatus(@RequestParam(value="projectMemberId", required=false) String projectMemberId,@RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
             
        
        TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(projectMemberId);
        if(timesheetSubmissions!=null){
             if(approvalStatus.equalsIgnoreCase("REJECTED") && approvalStatus!=null){
                   timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.REJECTED);
                   timesheetSubmissions.setRejectedReason(rejectedReason);
             }else if(approvalStatus.equalsIgnoreCase("APPROVED") && approvalStatus!=null){
                 
                   timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
             }
             
             timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
        }
        return null;
    }

	
	@RequestMapping(value = "/timesheetApproval")
	public ModelAndView timesheetApproval(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Collection<TimesheetSubmissions> timesheetSubmissionsList =  timesheetSubmissionsService.getApprovalTimesheetSubmissions(user.getId());
			model.addObject("sourcelead_timesheetSubmissionsList",timesheetSubmissionsList);
			
			model.setViewName("timesheet/timesheetApproval");
	        return model;
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("ok");*/
		timesheetSubmissions.setStatus("Active");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("prasad");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("venket");
		
		TimesheetSubmissions savedTimesheetSubmissionss = timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		return savedTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "timesheetSubmissions/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		/*timesheetSubmissions.setSubmissionStatus("False");*/
		timesheetSubmissions.setStatus("Reject");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("Sri");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("Sai");
		
		TimesheetSubmissions updtaeTimesheetSubmissionss = timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		return updtaeTimesheetSubmissionss; 
	}
	
	
/*	@RequestMapping(value = "/getTimesheetEntries", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@RequestParam String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}*/
	
	@RequestMapping(value = "timesheetSubmissions/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		Boolean status = timesheetSubmissionsService.deleteTimesheetSubmissions(id);
		return status; 
	}
	
	
	/*@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetSubmissions> getAllTimesheetSubmissions() throws Exception
			 
	{
		  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions();
		  return timesheetSubmissionsList;
	}*/
     
	 private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	 }
	
 
}
