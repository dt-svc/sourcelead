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

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExportTimesheetEntries;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TimesheetEntriesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.TimeSheetExcelView;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.TimeSheetPdfUtil;
 

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
	
	@Autowired
	TimeSheetPdfUtil timeSheetPdfUtil;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private EmployeesService employeesService; 
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	/**
	 * @author naresh bolisetty and sarath
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
	 * @author naresh bolisetty and sarath
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
		Employees employees = employeesService.getEmployee(employeeId.toString());
		OrgLocations orgLocations = orgLocationService.getOrgLocations(employees.getOrgLocations().getId());
		String result = timesheetEntriesService.dynamicTimesheetpopUp(user, employeeId, startDate, endDate,orgLocations);
		return result;
	}
	/**
	 * @author naresh bolisetty and sarath 
	 * @param timesheetEntriesForm
	 * @param json
	 * @param request
	 * @param status
	 * @return
	 * @throws Exception
	 */
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.POST)
	public @ResponseBody String saveTimesheet(TimesheetEntriesForm timesheetEntriesForm,@RequestParam(value="json", required=false) String json,
				MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status,String savingType) throws Exception {
			
			String result = timesheetEntriesService.saveTimesheet(timesheetEntriesForm, json, request, status,savingType);
			return result;
			
	}
	
	/**
	 * @author naresh bolisetty and sarath
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS')")
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
	 * @author naresh bolisetty and sarath
	 * 
	 * usage : unused method : this is default method
	 * 
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
	 * @author naresh bolisetty and sarath
	 * 
	 *  usage : unused method : this is default method
	 *  
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
	 * @author naresh bolisetty and sarath
	 * @param id
	 * @return
	 * 
	 * usage : unused method : this is default method
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	/**
	 * @author naresh bolisetty and sarath 
	 * 
	 * usage : unused method : this is default method
	 *  
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
	
	/**
	 * @author naresh bolisetty and sarath 
	 * 
	 *  usage : unused method : this is default method
	 *  
	 * @param id
	 * @return json
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	/**
	 * @author naresh bolisetty and sarath 
	 * 
	 *  usage : unused method : this is default method
	 *  
	 * @param id
	 * @return json
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	//naresh bolisetty and sarath : this method is for null check
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
	
	@RequestMapping(value = "/exportTimeSheet")  
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS')")
	public void ExportTimeShhet(HttpServletRequest request,@RequestParam(value="projectMemberId", required=false) Long projectMemberId,
			@RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,
			@RequestParam(value="exportType", required=false) String exportType, 
			HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ProjectMembers projectMember =  null;
		if(null != projectMemberId){
			projectMember = projectMembersService.getProjectMemberById(projectMemberId);
		}
		
		File file = null;
		if(!"pdf".equalsIgnoreCase(exportType)){
			file = exportTimesheetEntries.exportTimesheets(projectMember,startDate,endDate,orgLocations);
		}else{
			file = timeSheetPdfUtil.exportTimesheetPdf(projectMember,startDate,endDate,orgLocations);
		}
		
		if(file != null){
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
		}
	}
}