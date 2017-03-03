package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@PropertySource("${propertyLocation:app.properties}")
public class TaskRestController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	UserRoleService userRoleService;
	
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 /**
	  * @author Laxman
	  * this method is store tasks
	  * @param json
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/storeTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String storeTasks(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		    String json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
		    String tasks =  taskService.saveTasks(jsonObj);
		    return tasks;		
			 
	   }
	 
	 
    /**
     * this method is used for all tasks list
     * @param status
     * @param date
     * @param request
     * @return json
     * @throws Exception
     */
    @RequestMapping(value = "/getTasksList", method = RequestMethod.POST)
	public @ResponseBody String getTasksList(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "date", required = false) String date,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<Tasks> taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(orgLocations.getId(),user.getId(),status,date);
		
		Set<Tasks> taskList = new HashSet<Tasks>();
		for(Tasks tasks : taklist) {
			taskList.add(tasks);
		}
		
		for(Tasks tk :taklist){
			tk.setSubject(tk.getSubject());
			
			Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
			
			
			if(tk.getTaskDate() != null){

				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = Constants.DMY.format(converteDate);
				tk.setTaskDateStr(currentDateInTimezone);
		    }
			if(tk.getTaskTime() != null){
				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = Constants.TMS.format(converteDate);
				tk.setTaskTime(currentDateInTimezone);
			}
			tk.setDescription(tk.getDescription());
			tk.setAlertType(tk.getAlertType());
			tk.setTaskStatus(tk.getTaskStatus());
			tk.setPriority(tk.getPriority());
			
			if( null != tk.getAssignedUserId()){
				StringBuilder assignedName= new StringBuilder();
				if(null != tk.getAssignedUserId().getLastName()){
					tk.setAssignedName(assignedName.append(tk.getAssignedUserId().getFirstName()+" "+tk.getAssignedUserId().getLastName()).toString());
				}else{
					tk.setAssignedName(assignedName.append(tk.getAssignedUserId().getFirstName()).toString());
				}
			}
			
			if( null != tk.getTaskOwnerId()){
				StringBuilder taskOwnerName= new StringBuilder();
				if(null != tk.getAssignedUserId().getLastName()){
					tk.setTaskOwnerName(taskOwnerName.append(tk.getTaskOwnerId().getFirstName()+" "+tk.getTaskOwnerId().getLastName()).toString());
				}else{
					tk.setTaskOwnerName(taskOwnerName.append(tk.getTaskOwnerId().getFirstName()).toString());
				}
			}
			
			
				
		}
		return commonUtil.toJSON(taklist);
	} 
 
	 
	  /**
	   * @author Laxman
	   * This method is update for taskStatus
	   * @param taskId
	   * @param taskStatus
	   * @return
	   * @throws Exception
	   */
		 @RequestMapping(value = "/changeTaskStatus", method = RequestMethod.POST)
		 @ResponseBody String changeTaskStatus(@RequestParam(value = "taskId", required = false) Long taskId,@RequestParam(value="taskStatus", required=false) String taskStatus) throws Exception{
			 Tasks   task = taskService.getTasksById(taskId);
			 if(null != task){
				 task.setTaskStatus(taskStatus);
				 taskService.saveTasks(task);
			 }
					return "success";
			}
		 
		 
   /**
   * @author Laxman
   * This method is get the users list
   * @return json
   * @throws Exception
   */
	 @RequestMapping(value = "/assignedUserList", method = RequestMethod.POST)
	 @ResponseBody String assignedUserList(HttpServletRequest request) throws Exception{
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 List<UserRole> userRoleList = userRoleService.getAllUsersListByOrgLocationId(orgLocations.getId(),user.getId());
			for(UserRole ur :userRoleList){
				StringBuilder userName = new StringBuilder();
				ur.setUserId(ur.getUser().getId());
				if(null != ur.getUser()){
					if(null != ur.getUser().getLastName()){
						ur.setUserName(userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
					}else{
						ur.setUserName(userName.append(ur.getUser().getFirstName()).toString());
					}
				}
			}
			
		return commonUtil.toJSON(userRoleList);
		}
			 
			 
	  
	 
	   protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}

