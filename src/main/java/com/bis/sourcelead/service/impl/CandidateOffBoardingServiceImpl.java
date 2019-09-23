package com.bis.sourcelead.service.impl;

import java.nio.file.StandardWatchEventKinds;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateOffBoardingDao;
import com.bis.sourcelead.dao.OffboardTasksDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.CandidateOffBoardingDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.web.LoginSessionController;

import cucumber.deps.difflib.StringUtills;

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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public CandidateOffBoarding saveCandidateOffBoarding(CandidateOffBoarding candidateOffBoarding) throws Exception {
		return candidateOffBoardingDao.saveCandidateOffBoarding(candidateOffBoarding);
	}

	@Override
	public CandidateOffBoarding saveOffBoardingCandidatesDetails(CandidateOffBoardingDTO candidateOffBoardingDTO, OrgLocations orgLocations, User user)
			throws Exception {
		
		CandidateOffBoarding candidateOffBoarding = null;
		
		// date format
		//SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getSavedoffboardId())){
			candidateOffBoarding = candidateOffBoardingDao.getCandidateOffBoardingById(Long.valueOf(candidateOffBoardingDTO.getSavedoffboardId()));
		}else{
			candidateOffBoarding = new CandidateOffBoarding();
			candidateOffBoarding.setOffboardUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidateOffBoarding.setOffBoardStatus("Draft");
		}
		
		User offboardUser = new User();
		offboardUser.setId(Long.valueOf(candidateOffBoardingDTO.getOffboardUser()));
		candidateOffBoarding.setUserId(offboardUser);
		
		candidateOffBoarding.setFirstName(candidateOffBoardingDTO.getFirstName());
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getLastName())){
			candidateOffBoarding.setLastName(candidateOffBoardingDTO.getLastName());
		}else{
			candidateOffBoarding.setLastName(null);
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getMobileNumber())){
			candidateOffBoarding.setMobileNumber(candidateOffBoardingDTO.getMobileNumber());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getEmail())){
			candidateOffBoarding.setEmail(candidateOffBoardingDTO.getEmail());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getDateOfJoin())){
			candidateOffBoarding.setDateOfJoin(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOffBoardingDTO.getDateOfJoin()));
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getRole())){
			candidateOffBoarding.setRole(candidateOffBoardingDTO.getRole());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getEmploymentType())){
			candidateOffBoarding.setEmploymentType(candidateOffBoardingDTO.getEmploymentType());
		}
		
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getEndDate())){
			candidateOffBoarding.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOffBoardingDTO.getEndDate()));
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getReason())){
			candidateOffBoarding.setReason(candidateOffBoardingDTO.getReason());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getExplanation())){
			candidateOffBoarding.setExplanation(candidateOffBoardingDTO.getExplanation());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getNoticePeriod())){
			candidateOffBoarding.setNoticePeriod(candidateOffBoardingDTO.getNoticePeriod());
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getNoticePeriod()) && !candidateOffBoardingDTO.getNoticePeriod().equalsIgnoreCase("Relieve Immediately")){
			candidateOffBoarding.setHaveToWorkInNoticePeriod(candidateOffBoardingDTO.getWorkInNoticePeriod());
		}else{
			candidateOffBoarding.setHaveToWorkInNoticePeriod(null);
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getEffectiveDate())){
			candidateOffBoarding.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),candidateOffBoardingDTO.getEffectiveDate()));
		}
		
		if(StringUtils.isNotBlank(candidateOffBoardingDTO.getOffboardingTemplate())){
			candidateOffBoarding.setOffboardTemplateId(candidateOffBoardingDTO.getOffboardingTemplate());
		}else{
			candidateOffBoarding.setOffboardTemplateId(null);
		}
		
		candidateOffBoarding.setOffboardSubmitted(Constants.NO);
		candidateOffBoarding.setOrgLocationId(orgLocations);
		candidateOffBoarding.setStatus(Constants.CANDIDATE_ACTIVE);
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
						 tasks.setOffboardingId(candidateOffBoarding);
						 taskDao.saveTasks(tasks);
						 mailToUserTask(tasks,orgLocations);
					 }
					 
					 offboardTasks.setIsTaskSumbitted(Constants.YES);
					 offboardTasksDao.saveOffboardTasks(offboardTasks);
				 }else{
					 Tasks tasks = null;
					 tasks = getTaskDetails(offboardTasks, candidateOffBoarding.getUserId(), loginUser, orgLocations);
					 tasks.setOffboardingId(candidateOffBoarding);
					 taskDao.saveTasks(tasks);
					 mailToUserTask(tasks,orgLocations);
					 offboardTasks.setIsTaskSumbitted(Constants.YES);
					 offboardTasksDao.saveOffboardTasks(offboardTasks);
				 }
			 }
			 
		 }
		 
		 candidateOffBoarding.setOffBoardStatus("Finished");
		 candidateOffBoarding.setOffboardSubmitted(Constants.NO);
		 //mail for offboard candidates
         mailToOffboardCandidate(candidateOffBoarding,loginUser,orgLocations);
		 candidateOffBoardingDao.saveCandidateOffBoarding(candidateOffBoarding);
		 
		 
		
	}
public void mailToOffboardCandidate(CandidateOffBoarding candidateOffBoarding, User loginUser,OrgLocations orgLocations) throws Exception{
       // SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
        EmailMessage emailMessage = new EmailMessage();
         emailMessage.setFirstName(loginUser.getFirstName());
         emailMessage.setLastName(loginUser.getLastName());
         emailMessage.setToEmail(candidateOffBoarding.getEmail());
         emailMessage.setFromEmail(loginUser.getEmail());
         emailMessage.setCreatedBy(loginUser.getUserCode());
         StringBuilder msgBuild = new StringBuilder();
         emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
         emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
        //setting the class filed values
         Map<String, String> globalVarsMap = new HashMap<String, String>();
        
        String effectiveDate=commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOffBoarding.getEffectiveDate());
         globalVarsMap.put("effectiveDate", effectiveDate);
         String endDate=commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateOffBoarding.getEndDate());
         globalVarsMap.put("endDate", endDate);
         globalVarsMap.put("offboardReason", candidateOffBoarding.getReason());
         globalVarsMap.put("loginUser", emailMessage.getFromName());

         StringBuilder name = new StringBuilder();
         if(candidateOffBoarding.getLastName() != null && candidateOffBoarding.getLastName() != "" && candidateOffBoarding.getLastName() != "undefined"){
        	  globalVarsMap.put("applicantName", name.append(candidateOffBoarding.getFirstName()).append(" ").append(candidateOffBoarding.getLastName()).toString());
        
         }
         else{
        	 globalVarsMap.put("applicantName", candidateOffBoarding.getFirstName());
         }
         globalVarsMap.put("companyName", orgLocations.getOrgLocationName());
        
         StringBuilder linkBuild = new StringBuilder();
         globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
         //getting the current EmailScenario by EmailScenario unique name and entity type
         EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.OFFBOARDINGPROCESSAPPLICANTTERMINATION,Constants.ENTITY_TYPE_NOTIFICATION);

        
         String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
         notificationScenario.setBody(notificationbody);
        
        // OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();

         //calling the sendEmail method of emailScenerioUtil
         commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.OFFBOARDINGPROCESSAPPLICANTTERMINATION,new ArrayList<EmailAttachment>());
         String actionLink="";
       emailScenerioUtil.sendNotification(actionLink, loginUser, candidateOffBoarding.getUserId(), orgLocations, notificationScenario,new HashMap<String,String>());


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
	
	public void mailToUserTask(Tasks task,OrgLocations orgLocations ) throws Exception{
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(task.getTaskOwnerId().getFirstName());
		 emailMessage.setLastName(task.getTaskOwnerId().getLastName());
		 emailMessage.setToEmail(task.getAssignedUserId().getEmail());
		 emailMessage.setFromEmail(task.getTaskOwnerId().getEmail());
		 emailMessage.setCreatedBy(task.getTaskOwnerId().getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
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
		 task.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),task.getTaskDate()));
		 if( task.getTaskDateStr()!= null  && !"".equalsIgnoreCase( task.getTaskDateStr().toString())){
		 globalVarsMap.put("date", task.getTaskDateStr());	 
		 	}
		 }else{
	     globalVarsMap.put("date","--"); 
		 }
		
		 
		 Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());

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
		 
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 
		 //calling the Email 
		 
		 if(!isNullOrEmpty(task.getAlertType())){
			 if(task.getAlertType().equalsIgnoreCase("Email")){
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,task.getOwnerOrglocationId().getOrg(),task.getOwnerOrglocationId(),Constants.TASK_MECHANISM_EMAIL,new ArrayList<EmailAttachment>());
			 }
			 //calling the Notification 
			 if(task.getAlertType().equalsIgnoreCase("Notification")){
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(), task.getOwnerOrglocationId(), notificationScenario,new HashMap<String,String>());
			 }
			 //calling the Email  and notification 
			 if(task.getAlertType().equalsIgnoreCase("Email,Notification")){
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,task.getOwnerOrglocationId().getOrg(),task.getOwnerOrglocationId(),Constants.TASK_MECHANISM_EMAIL,new ArrayList<EmailAttachment>());
				 String actionLink="";
				 emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(),task.getAssignedUserId(),task.getOwnerOrglocationId(), notificationScenario,new HashMap<String,String>());
			 }
		 }

}

	@Override
	public List<CandidateOffBoarding> candidateOffboringDelayMail(){
		return candidateOffBoardingDao.candidateOffboringDelayMail();
	}

	@Override
	public CandidateOffBoarding getCandidateOffBoardingByUserIdAndLocationId(Long offboardingUser, Long orgLocationId)
			throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingByUserIdAndLocationId(offboardingUser, orgLocationId);
	}


	@Override
	public ResponseList<CandidateOffBoardingDTO> getCandidateOffBoardingList(
			CandidateOffBoardingDTO candidateOffBoardingDTO, OrgLocations orgLocations) throws Exception {
		return candidateOffBoardingDao.getCandidateOffBoardingList(candidateOffBoardingDTO, orgLocations);
	}

}
