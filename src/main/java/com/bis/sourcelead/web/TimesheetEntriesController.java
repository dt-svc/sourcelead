package com.bis.sourcelead.web;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TasksService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
 

@RestController 
public class TimesheetEntriesController {
	
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	@Autowired
	public TimesheetEntriesDao timesheetEntriesDao;
	
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public TasksService tasksService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;


	@RequestMapping(value = "/timesheet")
	public ModelAndView registration() throws Exception {
			ModelAndView model = new ModelAndView();
			Collection<Projects> projectsList =   projectsService.getAllProjects();
			model.addObject("sourcelead_projectsList", projectsList);
			
			Collection<Tasks> tasksList = tasksService.getAllTasks();
			model.addObject("sourcelead_tasksList", tasksList);
			
			model.setViewName("timesheet/timesheet");
			return model;

	}
	
	
	@RequestMapping(value = "/saveTimesheet",  method = RequestMethod.GET)
	public @ResponseBody String saveTimesheet(@RequestParam(value="json", required=false) String json) throws Exception {
		String result = "";
		String resultJson = null;
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	/*String[] projectIds =   jsonObj.getNames("projectId");*/
    	
    	//Collection<TimesheetEntries> timesheetEntriesList = timesheetEntries(jsonObj,projectId);
    	String[] projectIds = new String[2];
    	projectIds[0] = "1";
    	projectIds[1] = "2";

    	for(String projectId : projectIds){
    		
		    
	    	TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
	    	timesheetSubmissions.setStatus("agreed");
	    	timesheetSubmissions.setSubmissionStatus("not agreed");
	    	ProjectMembers pm = new ProjectMembers();
	    	pm.setId((long) 1);
	    	timesheetSubmissions.setProjectMembers(pm);
	    	timesheetSubmissions.setStartDate(new Date());
	    	timesheetSubmissions.setEndDate(new Date());
	    	timesheetSubmissions.setUpdatedDate(new Date());
	    	timesheetSubmissions.setCreatedDate(new Date());
	    	timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
			    
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber"+projectId);
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			int rowNum = 0;
			
			for (int c = 1; c <= maxCount; c++) {
				if (isNullOrEmpty(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id")))
					continue;
				TimesheetEntries timesheetEntries = new TimesheetEntries();
				timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
				
			    timesheetEntries.setMonHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id"));
				timesheetEntries.setMoncomments(jsonObj.getString(c+"project"+projectId+"comment1Id"));
				Tasks monTask = new Tasks();
				monTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setMonTask(monTask);
				
				/*timesheetEntries.setFricomments(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id"));*/
				rowNum++;
				
				timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
				
			}
    	}
    	
		result =  "success";
		resultJson = toJSON(result);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		
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
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries updateTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntries.setId(1l);
		
		Tasks task = new Tasks();
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
	
	
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = timesheetEntriesService.deleteTimesheetEntries(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
     
	
	 
	@RequestMapping(value = "/getSum", method = RequestMethod.GET)
	public @ResponseBody String getDetails(@RequestParam String valueOne)
			 
	{
		 System.out.println("hlo1");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	
	@RequestMapping(value = "/getSum", method = RequestMethod.POST)
	public @ResponseBody String getDetailss(@RequestBody String valueOne)
			 
	{
		 System.out.println("hlo2");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	private String toJSON(Object obj){
	    XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer writer) {
	            return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
	    return xstream.toXML(obj);
	}
	
	
	private List<TimesheetEntries> timesheetEntries(JSONObject json , String[] projectIds) throws JSONException
	{
		
		
		
		List<TimesheetEntries> timesheetEntriesList = new ArrayList<TimesheetEntries>();
		String maxAdminTimeTypeRowNum = json.getString("ss");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		int rowNum = 0;
		
		for (int c = 1; c <= maxCount; c++) {
			if (isNullOrEmpty(json.getString("name" + c)))
				continue;
			TimesheetEntries timesheetEntries = new TimesheetEntries();
			
			 
			timesheetEntries.setFricomments(json.getString("description" + c));
			rowNum++;
			
			timesheetEntriesList.add(timesheetEntries);
		}
		return timesheetEntriesList;
	}
	 
	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
 
}
