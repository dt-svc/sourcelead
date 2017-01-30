package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
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
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Override
	public Tasks saveTasks(Tasks tasks) throws Exception {
		return taskDao.saveTasks(tasks);
	}

	@Override
	public Tasks getTasksById(Long id) {
		return taskDao.getTasksById(id);
	}
	
	@Override
	public List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId,String Status,String date) throws Exception {
		return taskDao.tasksListByIdAndOrgLocationIdAssignedUserId(OrgLocationId,userId,Status,date);
	}

	public String saveTasks(JSONObject jsonObj) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

	 	String pattern = "dd-mm-yy";
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
				 //mail logic for users
     	        /*if(null != task.getAlertType() && !"".equalsIgnoreCase(task.getAlertType())){
     	        	mailToUserTask(user,ownerUser,task,loginOrgLocation);
     	        }*/
			}
        }
        
		return "success";
	}

	

 /**
	 * @author shivayogi
	 * @param loginUser
	 * @param assignedUser
	 * @param tasks
	 * @param orgLocations
	 * @throws Exception
	 */
	@Override
	public void mailToUserTask(Tasks tasks) throws Exception{
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(tasks.getTaskOwnerId().getFirstName());
			 emailMessage.setLastName(tasks.getTaskOwnerId().getLastName());
			 emailMessage.setToEmail(tasks.getAssignedUserId().getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setCreatedBy(tasks.getTaskOwnerId().getUserCode());
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(tasks.getTaskOwnerId().getFirstName()).append(" ").append(tasks.getTaskOwnerId().getLastName()).toString());
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 if(tasks.getSubject()!= null  && !"".equalsIgnoreCase(tasks.getSubject())){
			 globalVarsMap.put("subject", tasks.getSubject()); 
			 }else{
			 globalVarsMap.put("subject", "--");	 
			 }
			 if(tasks.getPriority()!= null  && !"".equalsIgnoreCase(tasks.getPriority())){
			 globalVarsMap.put("priority", tasks.getPriority()); 
			 }else{
			 globalVarsMap.put("subject", "--");	 
			 }
			 if(null != tasks.getTaskDate() && !"".equalsIgnoreCase(tasks.getTaskDate().toString())){
			 tasks.setTaskDateStr(Constants.DF_DMY.format(tasks.getTaskDate()));
			 if( tasks.getTaskDateStr()!= null  && !"".equalsIgnoreCase( tasks.getTaskDateStr().toString())){
			 globalVarsMap.put("date", tasks.getTaskDateStr());	 
			 	}
			 }else{
		     globalVarsMap.put("date","--"); 
			 }
			 if(tasks.getTaskTime()!= null  && !"".equalsIgnoreCase(tasks.getTaskTime())){
			 globalVarsMap.put("time", tasks.getTaskTime());	 
			 }else{
			 globalVarsMap.put("time","--"); 
			 }
			 if(tasks.getDescription()!= null  && !"".equalsIgnoreCase(tasks.getDescription())){
			 globalVarsMap.put("description", tasks.getDescription()); 
			 }else{
			 globalVarsMap.put("description","--"); 
			 }
			 if(tasks.getTaskOwnerId().getFirstName() != null && tasks.getTaskOwnerId().getLastName()!= null  && !"".equalsIgnoreCase( tasks.getTaskOwnerId().getFirstName()) && !"".equalsIgnoreCase( tasks.getTaskOwnerId().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("LoginUser",userBuild.append(tasks.getTaskOwnerId().getFirstName()).append(" ").append(tasks.getTaskOwnerId().getLastName()).toString());
			 }else{
			 globalVarsMap.put("LoginUser",tasks.getTaskOwnerId().getFirstName()); 
			 }
			 if (tasks.getAssignedUserId().getFirstName() != null && tasks.getAssignedUserId().getLastName()!= null  && !"".equalsIgnoreCase( tasks.getAssignedUserId().getFirstName()) && !"".equalsIgnoreCase( tasks.getAssignedUserId().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("assignedUser",userBuild.append(tasks.getAssignedUserId().getFirstName()).append(" ").append(tasks.getAssignedUserId().getLastName()).toString());
			 }else{
			 globalVarsMap.put("assignedUser",tasks.getAssignedUserId().getFirstName()); 
			 }
			 
			 
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_EMAIL,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = "";
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the Email 
			 if(tasks.getAlertType().equalsIgnoreCase("Email") && null !=tasks.getAlertType()){
				 emailScenerioUtil.sendEmail(emailMessage,tasks.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(tasks.getAlertType().equalsIgnoreCase("Notification") && null !=tasks.getAlertType()){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, tasks.getTaskOwnerId(),tasks.getAssignedUserId(), tasks.getOwnerOrglocationId(), notificationScenario);
			 }
			 //calling the Email  and notification 
			 if(tasks.getAlertType().equalsIgnoreCase("Email,Notification") && null !=tasks.getAlertType()){
				 emailScenerioUtil.sendEmail(emailMessage,tasks.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, tasks.getTaskOwnerId(),tasks.getAssignedUserId(),tasks.getOwnerOrglocationId(), notificationScenario);
			 }

	}

@Override
public List<Tasks> getTaskByScheduledTime(String currentTime) throws Exception {
	return taskDao.getTaskByScheduledTime(currentTime);
}
}
	



