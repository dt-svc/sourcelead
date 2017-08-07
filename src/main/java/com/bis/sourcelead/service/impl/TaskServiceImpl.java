package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Override
	public Tasks saveTasks(Tasks tasks) throws Exception {
		return taskDao.saveTasks(tasks);
	}

	@Override
	public Tasks getTasksById(Long id) {
		return taskDao.getTasksById(id);
	}
	
	@Override
	public List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId,String Status,String date,Date converteDate, Timezone timezone) throws Exception {
		Date endDateUTC =null ;
		Date startDateUTC = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(OrgLocationId);
		SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		if(null != date && "" != date){
		DateFormat  sdf = simpleDateFormat;		
		Date startTime =  sdf.parse(date);
		Date endTime =  sdf.parse(date);
		String customStartTimeStr = TZ_DF_CSD.format(startTime);  
		String customEndTimeStr = TZ_DF_CED.format(endTime);  
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
		String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
		startDateUTC = TZ_DF_TS.parse(startDateString);
		endDateUTC = TZ_DF_TS.parse(endDateString);
		
		}
		
		List<Tasks> taklist =  taskDao.tasksListByIdAndOrgLocationIdAssignedUserId(OrgLocationId,userId,Status,date,startDateUTC,endDateUTC);
		
		Set<Tasks> taskList = new HashSet<Tasks>();
		for(Tasks tasks : taklist) {
			taskList.add(tasks);
		}
		
		for(Tasks tk :taklist){
			tk.setSubject(tk.getSubject());
			
			if(tk.getTaskDate() != null){

				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
				 TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = simpleDateFormat.format(converteDate);
				tk.setTaskDateStr(currentDateInTimezone);
		    }
			if(tk.getTaskTime() != null){
				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
				 TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 converteDate = TZ_DF_TS.parse(currentDateInTimezone);
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
		return taklist;
		
	}

	public String saveTasks(JSONObject jsonObj) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Timezone timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
		SimpleDateFormat simpleDateFormat = loginOrgLocation.getLocFullFormDateFormatter();
	 	//String pattern = "dd-MM-yy";
        //SimpleDateFormat format = new SimpleDateFormat(pattern);
        
       
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
				 UserRole assignedUser =  userRoleService.getUserByUserId(ownerUser.getId());
				 if(assignedUser != null && assignedUser.getOrgLocations() != null){
					 task.setAssignedOrglocationId(assignedUser.getOrgLocations());	 
				 }
				 
				 if(jsonObj.has("priority") && !jsonObj.getString("priority").isEmpty()){
		        	 task.setPriority(jsonObj.getString("priority"));
		          }
			 
				 if(jsonObj.has("description") && !jsonObj.getString("description").isEmpty()){
		        	 task.setDescription(jsonObj.getString("description"));
		        }

		        /*if(jsonObj.has("taskDatpicker") && !jsonObj.getString("taskDatpicker").isEmpty()){
		    		Date taskDate = format.parse(jsonObj.getString("taskDatpicker"));
		    		task.setTaskDate(taskDate);
		    	}

		        if(jsonObj.has("timepicker") && !jsonObj.getString("timepicker").isEmpty()){
		    		task.setTaskTime(jsonObj.getString("timepicker"));
		    	}*/
				
				if(jsonObj.has("taskDatpicker") && !jsonObj.getString("taskDatpicker").isEmpty() && jsonObj.has("timepicker") && !jsonObj.getString("timepicker").isEmpty()){
				Date startTime =  CalendarUtil.getDateFromDateAndTime(simpleDateFormat.parse(jsonObj.getString("taskDatpicker")),jsonObj.getString("timepicker"));
				String currentTimeStr = Constants.TZ_DF.format(startTime);  
				String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE , currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
				task.setTaskDate(convertedDate);
						if(jsonObj.has("timepicker") && !jsonObj.getString("timepicker").isEmpty()){
								String splitDate = convertedDate.toString();
								String[] timeFormat = splitDate.split(" ");
								task.setTaskTime(timeFormat[3]);
						}
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
		        task.setUpdatedDate(commonUtil.currentDate());
     	        taskDao.saveTasks(task);
				 //mail logic for users
     	        if(null != task.getAlertType() && !"".equalsIgnoreCase(task.getAlertType())){
     	        	mailToUserTask(task);
     	        }
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
	public void mailToUserTask(Tasks task) throws Exception{
		Timezone timezone = timeZoneService.getTimezone(task.getOwnerOrglocationId().getTimezone().getId());
		
		OrgLocations orglo = orgLocationService.getOrgLocations(task.getOwnerOrglocationId().getId());
		SimpleDateFormat simpleDateFormat = orglo.getLocFullFormDateFormatter();
		
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(task.getTaskOwnerId().getFirstName());
			 emailMessage.setLastName(task.getTaskOwnerId().getLastName());
			 emailMessage.setToEmail(task.getAssignedUserId().getEmail());
			 emailMessage.setFromEmail(task.getTaskOwnerId().getEmail());
			 emailMessage.setCreatedBy(task.getTaskOwnerId().getUserCode());
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(task.getTaskOwnerId().getFirstName()).append(" ").append(task.getTaskOwnerId().getLastName()).toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 if(task.getSubject()!= null  && !"".equalsIgnoreCase(task.getSubject())){
			 globalVarsMap.put("subject", task.getSubject()); 
			 }else{
			 globalVarsMap.put("subject", "--");	 
			 }
			 if(task.getPriority()!= null  && !"".equalsIgnoreCase(task.getPriority())){
			 globalVarsMap.put("priority", task.getPriority()); 
			 }else{
			 globalVarsMap.put("priority", "--");	 
			 }
			 if(null != task.getTaskDate() && !"".equalsIgnoreCase(task.getTaskDate().toString())){
			 task.setTaskDateStr(simpleDateFormat.format(task.getTaskDate()));
			 if( task.getTaskDateStr()!= null  && !"".equalsIgnoreCase( task.getTaskDateStr().toString())){
			 globalVarsMap.put("date", task.getTaskDateStr());	 
			 	}
			 }else{
		     globalVarsMap.put("date","--"); 
			 }
			 if(task.getTaskTime() != null){
			    	DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
					String currentTimeStr = Constants.TZ_DF.format(task.getTaskDate());  
					String startTimeUTC= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					String convertedDateStr = DF_DMY.format(convertedDate);
					globalVarsMap.put("time", convertedDateStr);	 
				 
			    }
			 
			 
			 if(task.getDescription()!= null  && !"".equalsIgnoreCase(task.getDescription())){
			 globalVarsMap.put("description", task.getDescription()); 
			 }else{
			 globalVarsMap.put("description","--"); 
			 }
			 if(task.getTaskOwnerId().getFirstName() != null && task.getTaskOwnerId().getLastName()!= null  && !"".equalsIgnoreCase( task.getTaskOwnerId().getFirstName()) && !"".equalsIgnoreCase( task.getTaskOwnerId().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("LoginUser",userBuild.append(task.getTaskOwnerId().getFirstName()).append(" ").append(task.getTaskOwnerId().getLastName()).toString());
			 }else{
			 globalVarsMap.put("LoginUser",task.getTaskOwnerId().getFirstName()); 
			 }
			 if (task.getAssignedUserId().getFirstName() != null && task.getAssignedUserId().getLastName()!= null  && !"".equalsIgnoreCase( task.getAssignedUserId().getFirstName()) && !"".equalsIgnoreCase( task.getAssignedUserId().getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("assignedUser",userBuild.append(task.getAssignedUserId().getFirstName()).append(" ").append(task.getAssignedUserId().getLastName()).toString());
			 }else{
			 globalVarsMap.put("assignedUser",task.getAssignedUserId().getFirstName()); 
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
			 String url = sourceleadUrl;
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
			 notificationScenario.setBody(notificationbody);
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, task.getOwnerOrglocationId().getOrg(),task.getOwnerOrglocationId());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the Email 
			 if(task.getAlertType() != null && task.getAlertType().equalsIgnoreCase("Email")){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(task.getAlertType().equalsIgnoreCase("Notification") && null !=task.getAlertType()){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(), task.getOwnerOrglocationId(), notificationScenario);
			 }
			 //calling the Email  and notification 
			 if(task.getAlertType().equalsIgnoreCase("Email,Notification") && null !=task.getAlertType()){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(),task.getOwnerOrglocationId(), notificationScenario);
			 }

	}

@Override
public List<Tasks> getTaskByScheduledTime(String currentTime) throws Exception {
	return taskDao.getTaskByScheduledTime(currentTime);
}

@Override
public Long getTasksCompletedTodayByLocationCodeandUserId(OrgLocations orglocationCode, User user) throws Exception{
	
	return taskDao.getTasksCompletedTodayByLocationCodeandUserId(orglocationCode,user);
}

@Override
public List<Tasks> getTasskByAssignedUserIdAndOrgLocationId(Long orgLocationId, Long userId) throws Exception {
	return taskDao.getTasskByAssignedUserIdAndOrgLocationId(orgLocationId, userId);
}

@Override
public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(Long orgLocationId, Long userId,
		Long onboardingId) throws Exception {
	return taskDao.getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(orgLocationId, userId, onboardingId);
}

@Override
public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(Long orgLocationId, Long userId,
		Long offboardingId) throws Exception {
	return taskDao.getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(orgLocationId, userId, offboardingId);
}

@Override
public List<Tasks> getTasksListOffboardingId(Long offboardingId) throws Exception {
	return taskDao.getTasksListOffboardingId(offboardingId);
}

@Override
public List<Tasks> getTasksListOnboardingId(Long onboardingId) throws Exception {
	return taskDao.getTasksListOnboardingId(onboardingId);
}

@Override
public List<Tasks> getTaskListByRepeatStatus(String taskRepeatStatus) throws Exception {
	return taskDao.getTaskListByRepeatStatus(taskRepeatStatus);
}
}
	



