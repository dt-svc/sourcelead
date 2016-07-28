package com.bis.sourcelead.web;
import java.io.Writer;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
 

@RestController 
public class TimesheetEntriesController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public ProjectTasksService tasksService;
	
	@Autowired
	public TimeSheetExcelView timeSheetExcelView;
	
	
	// sarath kumar punnana : this controller  method is for get new timwsheet screen at page load.
	@RequestMapping(value = "/newTimesheet", method = RequestMethod.POST )    
	public @ResponseBody String newTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate) throws Exception {
		String result = timesheetEntriesService.newTimesheet(request, navigation, startDate);
		return result;
	}
	
	// sarath kumar punnana : this controller  method is for get timwsheet popup at approver scrren when click on count.
	@RequestMapping(value = "/dynamicTimesheetpopUp", method = RequestMethod.POST )
	public @ResponseBody String dynamicTimesheetpopUp(HttpServletRequest request,@RequestParam(value="employeeId", required=false) Long employeeId,
																				 @RequestParam(value="startDate", required=false) String startDate,
																				 @RequestParam(value="endDate", required=false) String endDate) throws Exception {
		
		String result = timesheetEntriesService.dynamicTimesheetpopUp(request, employeeId, startDate, endDate);
		return result;
	}

	// sarath kumar punnana : this  controller method is for store  timwsheet entries.
	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
				MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status) throws Exception {
			
			String result = timesheetEntriesService.saveTimesheet(timesheetEntriesForm, json, request, status);
			return result;
			
	}
	
	// sarath kumar punnana : this  controller method is for withdraw  timwsheet entries.
	@RequestMapping(value = "/withDrawTimesheet", method = RequestMethod.POST)
	public @ResponseBody String withDrawTimesheet(@RequestParam(value="json", required=false) String json) throws Exception {
				
			
			String result ="";
			return result;
			
	}
	
	// sarath kumar punnana : this  controller method is for timesheet screen redirection.
	@RequestMapping(value = "/timesheet")
	public ModelAndView registration(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.TimeSheet);
			
			request.getSession().setAttribute("sessionData",sessionData);
		 
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("timesheet/timesheet");
			return model;

	}
	
	// sarath kumar punnana : This method is for get tasks list by projectId
	@RequestMapping(value = "/projectTasksById" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	
	//Pavan Jampana : This method is for TimeSheet Pdf Download
	@RequestMapping(value = "/downloadTimesheetPdf", method = RequestMethod.GET)
	public ModelAndView generateAllTimesheetPdf(HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("redirect:/timesheetPdf/downloadPdf");
		return view;
	}
		
	// shivayogi s kadagad controller to download Exceltimesheet
	@RequestMapping(value = "/Excel", method = RequestMethod.GET)
	public ModelAndView getExcel() throws Exception {
		
		Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService .getAllTimesheetEntries(); 		
		return new ModelAndView(timeSheetExcelView,"timesheetEntriesList",timesheetEntriesList);
	}
		
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		
		ProjectTasks task = new ProjectTasks();
		/*Tasks task = new Tasks();
		task.setId(1l);
		timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("X");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad k");
		timesheetEntries.setUpdatedBy("naidu l");
		timesheetEntries.setUpdatedDate(new Date());*/
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries updateTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntries.setId(1l);
		
		ProjectTasks task = new ProjectTasks();
		task.setId(1l);
		
		/*timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("A");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad");
		timesheetEntries.setUpdatedBy("naidu");
		timesheetEntries.setUpdatedDate(new Date());*/
		
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = timesheetEntriesService.deleteTimesheetEntries(id);
		return status; 
	}
	
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	//sarath kumar punnana : unused method 
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	//sarath kumar punnana : convert java object to json data 
	private String toJSON(Object obj){
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer writer) {
	            return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
	    return xstream.toXML(obj);
	}
	
	//sarath kumar punnana : this method is for null check
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
 
}