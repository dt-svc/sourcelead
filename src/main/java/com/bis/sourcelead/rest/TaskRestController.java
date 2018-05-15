package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddTaskDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class TaskRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskRestController.class);
	
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
	 public @ResponseBody  Response<String> storeTasks(AddTaskDTO addTaskDTO,HttpServletRequest request) throws Exception {
		 Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 try{
			if(user != null){
				if(Validator.validate(addTaskDTO)){
					 String tasks =  taskService.saveTasksDTO(addTaskDTO);
					 response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					
				}else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		 }catch (Exception e) {
			 response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		    return response;		
			 
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
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
/*		String taskCurrentTimeStr = "";
		String taskCurrentDateInTimezone ="";
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
*/		Date converteDate=null;
		/*if(!"".equalsIgnoreCase(date)){
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			taskCurrentTimeStr = Constants.TZ_DF.format(date1);
			taskCurrentDateInTimezone= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", taskCurrentTimeStr);
			converteDate = TZ_DF_TS.parse(taskCurrentDateInTimezone);
			taskCurrentDateInTimezone = Constants.DMY.format(converteDate);
		}*/
		
		List<Tasks> taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(orgLocations.getId(),user.getId(),status,date,converteDate,timezone);
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
						ur.setUserName(userName.append(ur.getUser().getFirstName()).append(" ").append(ur.getUser().getLastName()).toString());
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

