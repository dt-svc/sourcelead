package com.bis.sourcelead.rest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
 

@RestController 
public class TimesheetEntriesRestController {
	
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
	
	@Autowired
	private ExportTimesheetEntries exportTimesheetEntries;
	
	@Autowired
	CommonUtil commonUtil;
	
	/**
	 * @author sarath kumar punnana
	 * @param request
	 * @param navigation
	 * @param startDate
	 * @return JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/newTimesheet", method = RequestMethod.POST )   
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS')")
	public @ResponseBody String newTimesheet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String result = timesheetEntriesService.newTimesheet(user, navigation, startDate,orgLocations.getId());
		return result;
	}
	/**
	 * @author sarath kumar punnana
	 * @param request
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @return JSON
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/dynamicTimesheetpopUp", method = RequestMethod.POST )
	public @ResponseBody String dynamicTimesheetpopUp(HttpServletRequest request,@RequestParam(value="employeeId", required=false) Long employeeId,
																				 @RequestParam(value="startDate", required=false) String startDate,
																				 @RequestParam(value="endDate", required=false) String endDate) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String result = timesheetEntriesService.dynamicTimesheetpopUp(user, employeeId, startDate, endDate);
		return result;
	}
/**
 * @author sarath kumar punnana 
 * @param timesheetEntriesForm
 * @param json
 * @param request
 * @param status
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
				MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status,String savingType) throws Exception {
			
			String result = timesheetEntriesService.saveTimesheet(timesheetEntriesForm, json, request, status,savingType);
			return result;
			
	}
	
	/**
	 * @author sarath kumar punnana
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectTasksById" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="avilableProjectTaskIds", required=false) String  avilableProjectTaskIds) throws Exception {
		 	
			 
			List<Long> ids = new ArrayList<Long>();
			String[] avilableProjectTaskId = avilableProjectTaskIds.split(",");
			for(String id  : avilableProjectTaskId){
				if(id != null && !id.isEmpty()){
					ids.add(Long.parseLong(id));
				}
			}
			
			 String resultJson;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectIdAndExistedProjectTaskIds(projectId, ids);
			 resultJson = commonUtil.toJSON(taskList);
			 return resultJson;
		 	
	 }
/**
 * @author sarath kumar punnana
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	/**
	 * @author sarath kumar punnana
	 */
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
		return  timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
	}
	/**
	 * @author sarath kumar punnana
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	/**
	 * @author sarath kumar punnana 
	 * @param id
	 * @return json
	 * @throws Exception
	 */
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
	
	//sarath kumar punnana : this method is for null check
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * @author Rajashekar
	 * @param request
	 * @param navigation
	 * @param startDate
	 * @return JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportTimeShhet")    
	public void ExportTimeShhet(HttpServletRequest request,@RequestParam(value="navigation", required=false) String navigation,@RequestParam(value="startDate", required=false) String startDate,HttpServletResponse response) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		File file = exportTimesheetEntries.exportTimesheets(user, navigation, startDate,orgLocations);
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
	}
 
}