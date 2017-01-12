package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class TaskServiceImpl implements TaskService{
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	TaskDao taskDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	UserService userService;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Override
	public Tasks saveTasks(Tasks tasks) throws Exception {
		return taskDao.saveTasks(tasks);
	}

	@Override
	public Tasks getTasksById(Long id) {
		return taskDao.getTasksById(id);
	}
	@Override
	public List<Tasks> assignedTasksListByIdANDOrgLocationId(Long OrgLocationId,Long userId) throws Exception {
		
		return taskDao.assignedTasksListByIdANDOrgLocationId(OrgLocationId,userId);
	}
	
	public String saveTasks(JSONObject jsonObj) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

	 	String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
       
        if(jsonObj.has("assignTo") && !jsonObj.getString("assignTo").isEmpty()){
        	String assignOwnersId =null;
			try{
				JSONArray jsonArray = jsonObj.getJSONArray("assignTo");
				assignOwnersId = jsonArray.toString();
			}
			catch(JSONException e){
				assignOwnersId =jsonObj.getString("assignTo");
			}
			assignOwnersId = assignOwnersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = assignOwnersId.split(",");
			
			for(int i=0 ;i<owners.length;i++ ){
				 Tasks task = new Tasks();
				 User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				 task.setAssignedUserId(ownerUser);
				 task.setOwnerOrglocationId(loginOrgLocation);
				 task.setTaskOwnerId(user);
				 
				 if(jsonObj.has("priority") && !jsonObj.getString("priority").isEmpty()){
		        	 task.setPriority(jsonObj.getString("priority"));
		          }
			 
				 if(jsonObj.has("description") && !jsonObj.getString("description").isEmpty()){
		        	 task.setDescription(jsonObj.getString("description"));
		        }

		        if(jsonObj.has("taskDatpicker") && !jsonObj.getString("taskDatpicker").isEmpty()){
		    		Date taskDate = format.parse(jsonObj.getString("taskDatpicker"));
		    		task.setTaskDate(taskDate);
		    	}

		        if(jsonObj.has("timepicker") && !jsonObj.getString("timepicker").isEmpty()){
		    		task.setTaskTime(jsonObj.getString("timepicker"));
		    	}
		        
		        if(jsonObj.has("repeat") && !jsonObj.getString("repeat").isEmpty()){
		    		task.setTaskRepeat(jsonObj.getString("repeat"));
		    	}
		        
		        if(jsonObj.has("subject") && !jsonObj.getString("subject").isEmpty()){
		    		task.setSubject(jsonObj.getString("subject"));
		    	}
		        
		        //Email and Notification conditions
		        if(jsonObj.has("email") && !jsonObj.getString("email").isEmpty() && jsonObj.has("notification") && !jsonObj.getString("notification").isEmpty()){
		        	 task.setAlertType(jsonObj.getString("email")+","+jsonObj.getString("notification"));
		        }else if(jsonObj.has("email") && !jsonObj.getString("email").isEmpty()){
		        	task.setAlertType(jsonObj.getString("email"));
		        }else if(jsonObj.has("notification") && !jsonObj.getString("notification").isEmpty()){
		        	task.setAlertType(jsonObj.getString("notification"));
		        }
		         if(user.getUserCode() != null){
		        	task.setCreatedBy(user.getUserCode());	
		    	}
		        task.setTaskStatus(Constants.TASK_STATUS_OPEN);
		        task.setCreatedDate(commonUtil.currentDate());
     	        taskDao.saveTasks(task);
			}
        }
        
		return "sucess";
	}
 
}
