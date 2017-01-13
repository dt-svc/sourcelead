package com.bis.sourcelead.rest;

import java.util.List;

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
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TaskService;
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
	 CommonUtil commonUtil;
	 
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
	  * @author Laxman
	  * this method is getAssignedTasksList
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getAssignedTasksList", method = RequestMethod.POST)
		public @ResponseBody String getAssignedTasksList(HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<Tasks> taklist = taskService.assignedTasksListByIdANDOrgLocationId(orgLocations.getId(),user.getId());
			for(Tasks tk :taklist){
				tk.setSubject(tk.getSubject());
				tk.setTaskDateStr(Constants.DMY.format(tk.getTaskDate()));
				tk.setTaskTime(tk.getTaskTime());
				tk.setDescription(tk.getDescription());
				tk.setAlertType(tk.getAlertType());
				tk.setTaskStatus(tk.getTaskStatus());
				
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
	 
	 
	    @RequestMapping(value = "/getMyTasksList", method = RequestMethod.POST)
		public @ResponseBody String getMyTasksList(HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<Tasks> taklist = taskService.myTasksListByIdAndOrgLocationIdAssignedUserId(orgLocations.getId(),user.getId());
			for(Tasks tk :taklist){
				tk.setSubject(tk.getSubject());
				tk.setTaskDateStr(Constants.DMY.format(tk.getTaskDate()));
				tk.setTaskTime(tk.getTaskTime());
				tk.setDescription(tk.getDescription());
				tk.setAlertType(tk.getAlertType());
				tk.setTaskStatus(tk.getTaskStatus());
				
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
	 
	    
	    @RequestMapping(value = "/getTasksList", method = RequestMethod.POST)
		public @ResponseBody String getTasksList(@RequestParam(value = "status", required = false) String status,HttpServletRequest request) throws Exception {
			
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<Tasks> taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(orgLocations.getId(),user.getId(),status);
			 
			for(Tasks tk :taklist){
				tk.setSubject(tk.getSubject());
				if(null != tk.getTaskDate()){
					tk.setTaskDateStr(Constants.DMY.format(tk.getTaskDate()));	
				}
				tk.setTaskTime(tk.getTaskTime());
				tk.setDescription(tk.getDescription());
				tk.setAlertType(tk.getAlertType());
				tk.setTaskStatus(tk.getTaskStatus());
				
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
	  
	 
	   protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
