package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.VendorOnboardTaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import bsh.StringUtil;

@Service
public class VendorOnboardServiceImpl implements VendorOnboardService {
	
	@Autowired
	VendorOnboardDao vendorOnboardDao;
	
	@Autowired
	VendorOnboardTasksDao vendorOnboardTasksDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TaskDao taskDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	OrgRelationsDao orgRelationsDao;

	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	ClientService clientService;
	
	@Override
	public VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user)
			throws Exception {
		
		VendorOnboarding vendorOnboarding = new VendorOnboarding();
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(vendorOrgLocations.getVendorOrgRelationId());
		vendorOnboarding.setOrgRelationId(orgRelations);
		vendorOnboarding.setVendorLocationId(vendorOrgLocations);
		User spocUser = new User();
		spocUser.setId(vendorOrgLocations.getVendorSpocId());
		vendorOnboarding.setSpocUserId(spocUser);
		vendorOnboarding.setVendorOnboardStatus("Draft");
		vendorOnboarding.setOnboardSubmitted(Constants.NO);
		vendorOnboarding.setOnboardUUID(SourceLeadRandomCodeHelper.generateUUID());
		vendorOnboarding.setOrgLocationsId(orgLocations);
		vendorOnboarding.setStatus(1);
		vendorOnboarding.setCreatedBy(user);
		vendorOnboarding.setUpdatedBy(user);
		vendorOnboarding.setCreatedDate(new Date());
		vendorOnboarding.setUpdatedDate(new Date());
		vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
		
		return vendorOnboarding;
		
	}

	@Override
	public VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception {
		return vendorOnboardDao.getVendorOnboardingByOrgRelationId(orgRelationId);
	}

	@Override
	public VendorOnboarding getVendorOnboardingById(Long id) throws Exception {
		return vendorOnboardDao.getVendorOnboardingById(id);
	}

	@Override
	public void finishVendorOnboarding(OnboardTasksDTO onboardTasksDTO, User user, OrgLocations orgLocations) throws Exception {

		VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingById(Long.valueOf(onboardTasksDTO.getVendorOnboardId()));
		User onboardingSpocUser = vendorOnboarding.getSpocUserId();
		OrgLocations vendorOrgLocations = vendorOnboarding.getVendorLocationId();
		
		if(onboardTasksDTO.getVendorOnboardTasksInfo() != null && onboardTasksDTO.getVendorOnboardTasksInfo().size() > 0){
			for(VendorOnboardTaskDTO taskDTO : onboardTasksDTO.getVendorOnboardTasksInfo()) {
				if(StringUtils.isNotBlank(taskDTO.getVendorOnboardTaskId())){
					VendorOnboardTasks task = vendorOnboardTasksDao.getVendorOnboardTasksByTaskId(Long.valueOf(taskDTO.getVendorOnboardTaskId()));
					if(task != null){
						VendorOnboardTasks vendorTask = new VendorOnboardTasks();
						vendorTask.setTaskName(task.getTaskName());
						vendorTask.setVendorOnboardingId(vendorOnboarding);
						vendorTask.setAssignToUsers(task.getAssignToUsers());
						vendorTask.setAssignToVendor(task.getAssignToVendor());
						vendorTask.setPriority(task.getPriority());
						vendorTask.setDueAfterMonths(task.getDueAfterMonths());
						vendorTask.setDueAfterHours(task.getDueAfterHours());
						vendorTask.setDueAfterMinutes(task.getDueAfterMinutes());
						vendorTask.setDueAfterDays(task.getDueAfterDays());
						vendorTask.setTaskRepeat(task.getTaskRepeat());
						vendorTask.setTaskEmail(task.getTaskEmail());
						vendorTask.setTaskNotification(task.getTaskNotification());
						vendorTask.setDescription(task.getDescription());
						vendorTask.setTaskType("Vendor Task");
						vendorTask.setStatus(Constants.CANDIDATE_ACTIVE);
						vendorTask.setCreatedBy(user);
						vendorTask.setUpdatedBy(user);
						vendorTask.setCreatedDate(commonUtil.currentDate());
						vendorTask.setUpdatedDate(commonUtil.currentDate());
						vendorOnboardTasksDao.saveVendorOnboardTasks(vendorTask);
					}
				}
			}
			
			
			List<VendorOnboardTasks> vendorOnboardTasksList = vendorOnboardTasksDao.getVendorOnboardTasksByVendorOnboardId(vendorOnboarding.getId());
			if(vendorOnboardTasksList != null && vendorOnboardTasksList.size() > 0){
				for(VendorOnboardTasks onboardTasks : vendorOnboardTasksList){
					if(StringUtils.isNotBlank(onboardTasks.getAssignToUsers())){
						 String[] userIds = onboardTasks.getAssignToUsers().split(",");
						 for(int i=0; i<userIds.length; i++){
							 Tasks tasks = null;
							 User taskUser = userDao.getUserById(Long.valueOf(userIds[i]));
							 tasks = getTaskDetails(onboardTasks, taskUser, user, orgLocations);
							 tasks.setOwnerOrglocationId(orgLocations);
							 tasks.setVendorOnboardingId(vendorOnboarding);
							 taskDao.saveTasks(tasks);
						 }
					}else{
						 Tasks tasks = null;
						 tasks = getTaskDetails(onboardTasks, onboardingSpocUser, user, orgLocations);
						 tasks.setOwnerOrglocationId(vendorOrgLocations);
						 tasks.setVendorOnboardingId(vendorOnboarding);
						 taskDao.saveTasks(tasks);
					 }
				}
			}
		}
		
		if(StringUtils.isNotBlank(onboardTasksDTO.getIsMailSent())){
			setEmialAndNotificationToVendort(onboardingSpocUser, user, orgLocations, vendorOnboarding.getOnboardUUID(), vendorOrgLocations);
		}
		
		vendorOnboarding.setVendorOnboardStatus("Sent");
		vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
		
		OrgRelations orgRelations = vendorOnboarding.getOrgRelationId();
		orgRelations.setVendorRelationStatus("Sent");
		orgRelationsDao.addOrgRelations(orgRelations);
		
		SubmissionTemplate submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(
				vendorOrgLocations.getId(), orgLocations.getId());
		
		clientService.saveOrUpdateClientVendorElkDocument(orgLocations.getOrg(), vendorOrgLocations, "Vendor", null, orgRelations,
				submissionTemplate, user, orgLocations);
		
	}
	

	public Tasks getTaskDetails(VendorOnboardTasks onboardTasks,User user,User loginUser,OrgLocations orgLocations){
		 
		 Tasks tasks = new Tasks();
		 tasks.setAssignedOrglocationId(orgLocations);
		 tasks.setTaskOwnerId(user);
		 tasks.setAssignedUserId(loginUser);
		 if(StringUtils.isNotBlank(onboardTasks.getPriority())){
			 tasks.setPriority(onboardTasks.getPriority());
		 }
		 tasks.setSubject(onboardTasks.getTaskName());
		 
		 if(StringUtils.isNotBlank(onboardTasks.getTaskRepeat())){
			 tasks.setTaskRepeat(onboardTasks.getTaskRepeat());
		 }
		 
		 if(null != onboardTasks.getDueAfterMonths() && null == onboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, onboardTasks.getDueAfterMonths());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != onboardTasks.getDueAfterDays() && null == onboardTasks.getDueAfterMonths()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.DATE, onboardTasks.getDueAfterDays());
			 tasks.setTaskDate(cal.getTime());
		 }
		 
		 if(null != onboardTasks.getDueAfterMonths() && null != onboardTasks.getDueAfterDays()){
			 Calendar cal = Calendar.getInstance(); 
			 cal.add(Calendar.MONTH, onboardTasks.getDueAfterMonths());
			 Date date = cal.getTime();
			 Date addedDate = addDays(date, onboardTasks.getDueAfterDays());
			 tasks.setTaskDate(addedDate);
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasks.getTaskEmail()) && !StringUtils.isNotBlank(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email");
		 }
		 
		 if(!StringUtils.isNotBlank(onboardTasks.getTaskEmail()) && StringUtils.isNotBlank(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Notification");
		 }
		 
		 if(StringUtils.isNotBlank(onboardTasks.getTaskEmail()) && StringUtils.isNotBlank(onboardTasks.getTaskNotification())){
			 tasks.setAlertType("Email,Notification");
		 }
		 
		 tasks.setTaskTime("23:59:00");
		 tasks.setDescription(onboardTasks.getDescription());
		 tasks.setTaskStatus(Constants.TASK_STATUS_OPEN);
		 tasks.setCreatedBy(loginUser.getUserCode());
		 tasks.setCreatedDate(commonUtil.currentDate());
		 tasks.setUpdatedBy(loginUser.getUserCode());
		 tasks.setUpdatedDate(commonUtil.currentDate());
		 return tasks;
		
		 
	}
	
	public static Date addDays(Date d, int days)
    {
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }
	
	
	public void setEmialAndNotificationToVendort(User spocUser, User loginUser, OrgLocations orgLocations,String onboardUUID,OrgLocations vendorOrgLocation) throws Exception{
		
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(spocUser.getFirstName());
		 emailMessage.setLastName(spocUser.getLastName());
		 emailMessage.setToEmail(spocUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFullName());

		 globalVarsMap.put("FirstName", spocUser.getFirstName());
		 if(StringUtils.isNotBlank(spocUser.getLastName())){
			 globalVarsMap.put("LastName",spocUser.getLastName());
		 }
		 
		 globalVarsMap.put("loginUsername",loginUser.getFullName());
		 globalVarsMap.put("clientOrgName",orgLocations.getOrgLocationName());
		
		 StringBuilder actionUrlBuilder = new StringBuilder();
		 globalVarsMap.put("acceptURL",actionUrlBuilder.append("<a href='").append(sourceleadUrl).append("/").append("login").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 StringBuilder vendorLinkBuilder = new StringBuilder();
		 globalVarsMap.put("supportLink",vendorLinkBuilder.append("<a href='http://support.sourcelead.com/faq' target='_blank' style='outline:none; border:none;'>http://support.sourcelead.com/faq</a>").toString());
		 
		 StringBuilder fbLinkBuilder = new StringBuilder();
		 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder linkedInLinkBuilder = new StringBuilder();
		 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder twitterLinkBuilder = new StringBuilder();
		 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder googleLinkBuilder = new StringBuilder();
		 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google Plus' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
		 
		 boolean isUserActive = false;
		 String password = null;
		 if(spocUser != null){
				  isUserActive = userRoleService.isUserActive(spocUser.getId());
				  if(isUserActive == false){
					  password = SourceLeadRandomCodeHelper.generateRandomPassword();
					  spocUser.setPassword(sourceleadPasswordEncoder.encode(password));
					  spocUser.setIsMailSent(Status.Y);
					  userDao.addUser(spocUser);
					  
					  globalVarsMap.put("userName",spocUser.getUserName());
				      globalVarsMap.put("password",password);
				  }else{
					  globalVarsMap.put("userName",spocUser.getEmail());
					  globalVarsMap.put("password","Login with your existing sourcelead credentials");
					}
			   
		 }
		 
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("acceptVendorOnboardInvitation?onboardUUID=").append(onboardUUID).append("").toString();
		 
		StringBuilder rejectActionBuilder = new StringBuilder();
		String  rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("rejectVendorOnboardInvitation?onboardUUID=").append(onboardUUID).append("").toString();
	 
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 EmailScenario noificationEmailScenario = null;
		 
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
		 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 
		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),vendorOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(vendorOrgLocation.getId());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailScenerioUtil.sendEmail(emailMessage,vendorOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 noificationEmailScenario.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		 
		 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,loginUser, spocUser, vendorOrgLocation, noificationEmailScenario,null);
	}
	
	

}
