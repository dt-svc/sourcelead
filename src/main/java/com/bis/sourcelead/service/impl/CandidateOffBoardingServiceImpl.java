package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateOffBoardingDao;
import com.bis.sourcelead.dao.OffboardTasksDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CandidateOffBoardingServiceImpl implements CandidateOffBoardingService{
	
	@Autowired
	CandidateOffBoardingDao candidateOffBoardingDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OffboardTasksDao offboardTasksDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception {
		return candidateOffBoardingDao.saveCandidateOffBoarding(candidateOffBoarding);
	}

	@Override
	public CandidateOffBoarding saveOffBoardingCandidatesDetails(JSONObject jsonObj, OrgLocations orgLocations, User user)
			throws Exception {
		
		CandidateOffBoarding candidateOffBoarding = null;
		
		if(jsonObj.has("savedoffboardId") && !isNullOrEmpty(jsonObj.getString("savedoffboardId"))){
			candidateOffBoarding = candidateOffBoardingDao.getCandidateOffBoardingById(Long.valueOf(jsonObj.getString("savedoffboardId")));
		}else{
			candidateOffBoarding = new CandidateOffBoarding();
			candidateOffBoarding.setOffboardUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		User offboardUser = new User();
		offboardUser.setId(Long.valueOf(jsonObj.getString("offboardUser")));
		candidateOffBoarding.setUserId(offboardUser);
		
		candidateOffBoarding.setFirstName(jsonObj.getString("firstName"));
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidateOffBoarding.setLastName(jsonObj.getString("lastName"));
		}else{
			candidateOffBoarding.setLastName(null);
		}
		
		if(jsonObj.has("mobileNumber") && !isNullOrEmpty(jsonObj.getString("mobileNumber"))){
			candidateOffBoarding.setMobileNumber(jsonObj.getString("mobileNumber"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			candidateOffBoarding.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("dateOfJoin") && !isNullOrEmpty(jsonObj.getString("dateOfJoin"))){
			candidateOffBoarding.setDateOfJoin(commonUtil.getDateFormat(jsonObj.getString("dateOfJoin")));
		}
		
		if(jsonObj.has("role") && !isNullOrEmpty(jsonObj.getString("role"))){
			candidateOffBoarding.setRole(jsonObj.getString("role"));
		}
		
		
		if(jsonObj.has("endDate") && !isNullOrEmpty(jsonObj.getString("endDate"))){
			candidateOffBoarding.setEndDate(commonUtil.getDateFormat(jsonObj.getString("endDate")));
		}
		
		if(jsonObj.has("reason") && !isNullOrEmpty(jsonObj.getString("reason"))){
			candidateOffBoarding.setReason(jsonObj.getString("reason"));
		}
		
		if(jsonObj.has("explanation") && !isNullOrEmpty(jsonObj.getString("explanation"))){
			candidateOffBoarding.setExplanation(jsonObj.getString("explanation"));
		}
		
		if(jsonObj.has("noticePeriod") && !isNullOrEmpty(jsonObj.getString("noticePeriod"))){
			candidateOffBoarding.setNoticePeriod(jsonObj.getString("noticePeriod"));
			candidateOffBoarding.setHaveToWorkInNoticePeriod(jsonObj.getString("workInNoticePeriod"));
		}else{
			candidateOffBoarding.setHaveToWorkInNoticePeriod(null);
		}
		
		if(jsonObj.has("effectiveDate") && !isNullOrEmpty(jsonObj.getString("effectiveDate"))){
			candidateOffBoarding.setEffectiveDate(commonUtil.getDateFormat(jsonObj.getString("effectiveDate")));
		}
		
		if(jsonObj.has("offboardingTemplate") && !isNullOrEmpty(jsonObj.getString("offboardingTemplate"))){
			
			String templateIds = jsonObj.getString("offboardingTemplate");
			templateIds = templateIds.replaceAll("\\[", "").replaceAll("\\]","");
			templateIds = templateIds.replaceAll("\"", "");
			candidateOffBoarding.setOffboardTemplateId(templateIds);
		}else{
			candidateOffBoarding.setOffboardTemplateId(null);
		}
		
		candidateOffBoarding.setOffboardSubmitted(Constants.NO);
		candidateOffBoarding.setOrgLocationId(orgLocations);
		candidateOffBoarding.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateOffBoarding.setOffBoardStatus("Draft");
		candidateOffBoarding.setCreatedDate(CommonUtil.currentDate());
		candidateOffBoarding.setCreatedBy(user.getUserCode());
		candidateOffBoarding.setUpdatedDate(CommonUtil.currentDate());
		candidateOffBoarding.setUpdatedBy(user.getUserCode());
		
		candidateOffBoardingDao.saveCandidateOffBoarding(candidateOffBoarding);
		
		return candidateOffBoarding;
	}


	@Override
	public CandidateOffBoarding getCandidateOffBoardingById(Long Id) throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingById(Id);
	}


	@Override
	public List<CandidateOffBoarding> getCandidateOffBoardingListByOrgLocationId(Long orgLocationId) throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingListByOrgLocationId(orgLocationId);
	}


	@Override
	public Integer getCandidateOffBoardingListCountByOrgLocationId(Long orgLocationId) throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingListCountByOrgLocationId(orgLocationId);
	}


	@Override
	public CandidateOffBoarding getCandidateOffBoardingByOffboardUUID(String offboardUUID) throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingByOffboardUUID(offboardUUID);
	}


	@Override
	public void terminateOffboardingUser(CandidateOffBoarding candidateOffBoarding, User loginUser,
			OrgLocations orgLocations) throws Exception {
		
		
		List<OffboardTasks> offboardTasksList = offboardTasksDao.getOffBoardTasksListByCandidateOffBoardingId(candidateOffBoarding.getId());
		 if(null != offboardTasksList && offboardTasksList.size() > 0){
			 
			 for(OffboardTasks offboardTasks : offboardTasksList){
				 if(!isNullOrEmpty(offboardTasks.getAssignToUsers())){
					 String[] userIds = offboardTasks.getAssignToUsers().split(",");
					 for(int i=0; i<userIds.length; i++){
						 
						 Tasks tasks = null;
						 User taskUser = userDao.getUserById(Long.valueOf(userIds[i]));
						 tasks = getTaskDetails(offboardTasks, taskUser, loginUser, orgLocations);
						 taskDao.saveTasks(tasks);
						 mailToUserTask(tasks);
					 }
				 }else{
					 Tasks tasks = null;
					 tasks = getTaskDetails(offboardTasks, candidateOffBoarding.getUserId(), loginUser, orgLocations);
					 taskDao.saveTasks(tasks);
					 mailToUserTask(tasks);
				 }
			 }
			 
		 }
		 
		 candidateOffBoarding.setOffBoardStatus("Finished");
		 candidateOffBoarding.setOffboardSubmitted(Constants.NO);
		 
		 candidateOffBoardingDao.saveCandidateOffBoarding(candidateOffBoarding);
		
	}
	
	
	public Tasks getTaskDetails(OffboardTasks offboardTasks,User user,User loginUser,OrgLocations orgLocations){
		 
		 Tasks tasks = new Tasks();
		 tasks.setOwnerOrglocationId(orgLocations);
		 tasks.setAssignedOrglocationId(orgLocations);
		 tasks.setTaskOwnerId(loginUser);
		 tasks.setAssignedUserId(user);
		 if(!isNullOrEmpty(offboardTasks.getPriority())){
			 tasks.setPriority(offboardTasks.getPriority());
		 }
		 tasks.setSubject(offboardTasks.getTaskName());
		 
		 if(!isNullOrEmpty(offboardTasks.getTaskRepeat())){
			 tasks.setTaskRepeat(offboardTasks.getTaskRepeat());
		 }
		 
		 if(null != offboardTasks.getDueAfterMonths() && null == offboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, offboardTasks.getDueAfterMonths());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != offboardTasks.getDueAfterDays() && null == offboardTasks.getDueAfterMonths()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.DATE, -offboardTasks.getDueAfterDays());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != offboardTasks.getDueAfterMonths() && null != offboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, -offboardTasks.getDueAfterMonths());
			 Date date = cal.getTime();
			 Date addedDate = substractDays(date, offboardTasks.getDueAfterDays());
			 tasks.setTaskDate(addedDate);
		 }
		 
		 if(!isNullOrEmpty(offboardTasks.getTaskEmail()) && isNullOrEmpty(offboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email");
		 }
		 
		 if(isNullOrEmpty(offboardTasks.getTaskEmail()) && !isNullOrEmpty(offboardTasks.getTaskNotification())){
			 tasks.setAlertType("Notification");
		 }
		 
		 if(!isNullOrEmpty(offboardTasks.getTaskEmail()) && !isNullOrEmpty(offboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email,Notification");
		 }
		 
		 tasks.setTaskTime("23:59:00");
		 tasks.setDescription(offboardTasks.getDescription());
		 tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
		 tasks.setCreatedBy(loginUser.getUserCode());
		 tasks.setCreatedDate(commonUtil.currentDate());
		 tasks.setUpdatedBy(loginUser.getUserCode());
		 tasks.setUpdatedDate(commonUtil.currentDate());
		 return tasks;
		
		 
	}
	
	public static Date substractDays(Date d, int days)
    {
        d.setTime(d.getTime() - days * 24 * 3600 * 1000 );
        return d;
    }
	
	public void mailToUserTask(Tasks task) throws Exception{
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
		 task.setTaskDateStr(Constants.DF_DMY.format(task.getTaskDate()));
		 if( task.getTaskDateStr()!= null  && !"".equalsIgnoreCase( task.getTaskDateStr().toString())){
		 globalVarsMap.put("date", task.getTaskDateStr());	 
		 	}
		 }else{
	     globalVarsMap.put("date","--"); 
		 }
		 if(task.getTaskTime()!= null  && !"".equalsIgnoreCase(task.getTaskTime())){
		 globalVarsMap.put("time", task.getTaskTime());	 
		 }else{
		 globalVarsMap.put("time","--"); 
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
		 String url = "";
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the Email 
		 
		 if(!isNullOrEmpty(task.getAlertType())){
			 if(task.getAlertType().equalsIgnoreCase("Email")){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(task.getAlertType().equalsIgnoreCase("Notification")){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(), task.getOwnerOrglocationId(), notificationScenario);
			 }
			 //calling the Email  and notification 
			 if(task.getAlertType().equalsIgnoreCase("Email,Notification")){
				 emailScenerioUtil.sendEmail(emailMessage,task.getOwnerOrglocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(),task.getOwnerOrglocationId(), notificationScenario);
			 }
		 }

}

}
