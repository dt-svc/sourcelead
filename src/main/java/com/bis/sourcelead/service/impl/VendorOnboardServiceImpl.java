package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.VendorDocumentsFileInfo;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.dto.VendorOnboardTaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgBankDetailsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	
	@Autowired
	OrgBankDetailsService orgBankDetailsService;
	
	@Autowired
	VendorOnboardTemplateDocumentsDao vendorOnboardTemplateDocumentsDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	NotificationsService notificationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	 
	 
	 
	
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
		
		
		if(StringUtils.isNotBlank(onboardTasksDTO.getIsMailSent())){
			setEmialAndNotificationToVendor(onboardingSpocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Invitation");
			Set<OrgSpoc> orgSpocList = vendorOnboarding.getOrgRelationId().getOrgSpocList();
			if(orgSpocList != null){
				for(OrgSpoc orgSpc : orgSpocList){
					orgSpc.setInvitationStatus("Sent");
					orgSpocService.addOrgSpoc(orgSpc);
				}
			}
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
		 tasks.setTaskOwnerId(loginUser);
		 tasks.setAssignedUserId(user);
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
		 tasks.setStatus(1);
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
	
	
	public void setEmialAndNotificationToVendor(User spocUser, User loginUser, OrgLocations orgLocations,VendorOnboarding onboarding,OrgLocations vendorOrgLocation,String invitationType) throws Exception{
		
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(spocUser.getFirstName());
		 emailMessage.setLastName(spocUser.getLastName());
		 emailMessage.setToEmail(spocUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(loginUser.getFullName());

		 globalVarsMap.put("firstName", spocUser.getFirstName());
		 if(StringUtils.isNotBlank(spocUser.getLastName())){
			 globalVarsMap.put("lastName",spocUser.getLastName());
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
					  userDao.addUser(spocUser);
					  
					  globalVarsMap.put("userName",spocUser.getUserName());
				      globalVarsMap.put("password",password);
				  }else{
					  globalVarsMap.put("userName",spocUser.getEmail());
					  globalVarsMap.put("password","Login with your existing sourcelead credentials");
					}
			   
		 }
		 
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
		 
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(onboarding.getSpocUserId().getId(), onboarding.getVendorLocationId().getId());
		 if(userRole != null) {
			 userRole.setIsMailSent(Status.Y);
			 userRoleService.addUserRole(userRole);
		 }
		 
		if(invitationType.equalsIgnoreCase("Vendor Invitation")) {
			Set<OrgSpoc> orgSpocList = onboarding.getOrgRelationId().getOrgSpocList();
			for(OrgSpoc orgSpc : orgSpocList){
				
				 String notificationUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
				 noificationEmailScenario.setBody(notificationbody);
				 noificationEmailScenario.setUUID(notificationUUID);
				 
			     StringBuilder actionLinkBuilder = new StringBuilder();
				 String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("acceptVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 StringBuilder rejectActionBuilder = new StringBuilder();
				 String  rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("rejectVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,loginUser, spocUser, vendorOrgLocation, noificationEmailScenario,orgSpc.getUUID());
			}
		}else{
			List<OrgSpoc> orgSpocList = orgSpocService.getSpocListByVendorUserIdAndOrgRelationId(onboarding.getOrgRelationId().getId(), spocUser.getId());
			for(OrgSpoc orgSpc : orgSpocList){
				 String notificationUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
				 noificationEmailScenario.setBody(notificationbody);
				 noificationEmailScenario.setUUID(notificationUUID);
				 
			     StringBuilder actionLinkBuilder = new StringBuilder();
				 String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("acceptVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 StringBuilder rejectActionBuilder = new StringBuilder();
				 String  rejectActionLink = rejectActionBuilder.append("").append(sourceleadUrl).append("/").append("rejectVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,loginUser, spocUser, vendorOrgLocation, noificationEmailScenario,orgSpc.getUUID());
			}
		}
		
		
	}
	
	
	
	

	@Override
	public VendorOnboarding getVendorOnboardingByUUID(String onboardUUID) throws Exception {
		return vendorOnboardDao.getVendorOnboardingByUUID(onboardUUID);
	}

	@Override
	public void saveVendorOnboardDetails(VendorOnboardDTO vendorOnboardDTO,Long vendorOnboardId,User user)
			throws Exception {
		
		OrgBankDetails bankDetails = null;
		
		VendorOnboarding onboarding = vendorOnboardDao.getVendorOnboardingById(vendorOnboardId);
		OrgRelations orgRelations = onboarding.getOrgRelationId();
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getTier())){
			orgRelations.setInitiatorLevel(vendorOnboardDTO.getTier());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getIndustryName())){
			orgRelations.setIndustryName(vendorOnboardDTO.getIndustryName());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getPaymentTerm())){
			orgRelations.setPaymentTerm(vendorOnboardDTO.getPaymentTerm());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getSicCode())){
			orgRelations.setSicCode(vendorOnboardDTO.getSicCode());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getTaxRegNumber())){
			orgRelations.setTaxRegistrationNumber(vendorOnboardDTO.getTaxRegNumber());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getDunsNumber())){
			orgRelations.setDunsNumber(vendorOnboardDTO.getDunsNumber());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getNaicsCode())){
			orgRelations.setNaicsCode(vendorOnboardDTO.getNaicsCode());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getEinCode())){
			orgRelations.setEinCode(vendorOnboardDTO.getEinCode());
		}
		
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getCurrency())){
			orgRelations.setCurrency(vendorOnboardDTO.getCurrency());
		}
		
		bankDetails = orgBankDetailsService.getOrgBankDetailsByOrgLocationId(onboarding.getVendorLocationId().getId());
		if(bankDetails == null){
			bankDetails = new OrgBankDetails();
			bankDetails.setUid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		bankDetails.setOrgLocationsId(onboarding.getVendorLocationId());
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getAccountName())){
			bankDetails.setAccountName(vendorOnboardDTO.getAccountName());
		}
	
		if(StringUtils.isNotBlank(vendorOnboardDTO.getAccountName())){
			bankDetails.setAccountName(vendorOnboardDTO.getAccountName());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getAccountNumber())){
			bankDetails.setAccountNumber(vendorOnboardDTO.getAccountNumber());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getAccountNumber())){
			bankDetails.setBankName(vendorOnboardDTO.getBankName());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getBranchNumber())){
			bankDetails.setBranchNumber(vendorOnboardDTO.getBranchNumber());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getBicNumber())){
			bankDetails.setBankIdentifierCode(vendorOnboardDTO.getBicNumber());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getBankAddress())){
			bankDetails.setBankAddress(vendorOnboardDTO.getBankAddress());
		}
		
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getPoEmail())){
			bankDetails.setPoEmail(vendorOnboardDTO.getPoEmail());
		}
		
		if(StringUtils.isNotBlank(vendorOnboardDTO.getPaymentRemittanceEmail())){
			bankDetails.setPaymentRemittanceEmail(vendorOnboardDTO.getPaymentRemittanceEmail());
		}
		
		bankDetails.setCreatedBy(user);
		bankDetails.setUpdatedBy(user);
		bankDetails.setCreatedDate(commonUtil.currentDate());
		bankDetails.setUpdatedDate(commonUtil.currentDate());
		orgBankDetailsService.saveOrgBankDetails(bankDetails);
		
		OrgLocations vendorOrgLocations = onboarding.getVendorLocationId();
		if(StringUtils.isNotBlank(vendorOnboardDTO.getVendorAddress())){
			vendorOrgLocations.setOrgLocationAddress(vendorOnboardDTO.getVendorAddress());
		}
		if(StringUtils.isNotBlank(vendorOnboardDTO.getVendorBillingAddress())){
			vendorOrgLocations.setBillingAddress(vendorOnboardDTO.getVendorBillingAddress());
		}
		orgLocationsDao.updateOrgLocations(vendorOrgLocations);
		
		orgRelations.setBankDetailsId(bankDetails);
		orgRelationsDao.addOrgRelations(orgRelations);
		
	}

	@Override
	public void saveVendorOnboardDocuments(VendorOnboardDTO vendorOnboardDTO) throws Exception {
		
		if(vendorOnboardDTO.getVendorDocumentsFileInfo() != null &&  vendorOnboardDTO.getVendorDocumentsFileInfo().size() > 0) {
			for(VendorDocumentsFileInfo fileInfo : vendorOnboardDTO.getVendorDocumentsFileInfo()) {
				if(fileInfo != null && fileInfo.getFileURL() != null) {
					if(StringUtils.isNotBlank(fileInfo.getDocId())){
						VendorOnboardingTemplateDocuments documents = vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsById(Long.valueOf(fileInfo.getDocId()));
						documents.setUploadedFileName(fileInfo.getFileName());
					    documents.setUploadedFileContentType(fileInfo.getContentType());
					    documents.setUploadedFileUrl(fileInfo.getFileURL());
					    documents.setDocumentStatus("Submitted");
					    vendorOnboardTemplateDocumentsDao.saveVendorOnBoardingTemplateDocuments(documents);
					}
					 FileUploadUtil.moveFile(vendorOnboardDTO .getStaticPath(), fileInfo, "Vendor-Onboard-Submitted-Documents");
				   }
			}
		}
		
	}

	@Override
	public void finishVendorOnboardProcess(Long vendorOnboardId, String notificationUUID,User user,String actionType) throws Exception {
		
		VendorOnboarding onboard = vendorOnboardDao.getVendorOnboardingById(vendorOnboardId);
		OrgRelations orgRelations = onboard.getOrgRelationId();
		OrgLocations vendorOrgLocations = onboard.getVendorLocationId();
		Org vendorOrg = vendorOrgLocations.getOrg();
		
		if(null != vendorOrg){
			Long moduleId = null;
			Long tabId = null;
			boolean flag;
			String modules = vendorOrgLocations.getModulesId();
			
			vendorOrg.setOrgType("Company");
			
			List<Integer> moduleIds = new ArrayList<Integer>();
			 for (String s : vendorOrgLocations.getModulesId().split(",")){
				 moduleIds.add(Integer.parseInt(s));
			 }
			
			 
			 flag = moduleIds.contains(Constants.MODULE_VMS);
	    		tabId = (long) Constants.TAB_VENDOR;
	    		moduleId = (long) Constants.MODULE_VMS;
	    		if(flag == false){
	    			modules = modules+","+Constants.MODULE_VMS;
	    			saveUserRoleActions(moduleId,tabId,vendorOrgLocations);
	    		}
	    		
	    		
	    		vendorOrg.setStatus(Status.ACTIVE);
				orgService.updateOrg(vendorOrg);
				vendorOrgLocations.setUser(user);
				orgLocationService.updateOrgLocations(vendorOrgLocations);
				
				 Notifications notifications = notificationsService.getNotificationsByUUID(notificationUUID);
				 if(null != notifications){
					 if(actionType.equalsIgnoreCase(Constants.MAIL_ACCEPTED)){
						 notifications.setUserNotificationStatus(Constants.MAIL_ACCEPTED);
						}else{
							notifications.setUserNotificationStatus(Constants.MAIL_REJECTED);
						}
					
					 notificationsService.saveNotification(notifications);
				 }
				 
				 OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdAndAssignedUserId(orgRelations.getId(), user.getId());
				 if(null != orgSpoc ){
					 if(actionType.equalsIgnoreCase(Constants.MAIL_ACCEPTED)){
						 orgSpoc.setInvitationStatus(Constants.MAIL_ACCEPTED);
					 }else{
						 orgSpoc.setInvitationStatus(Constants.MAIL_REJECTED);
						 orgSpoc.setSpocStatus("In-Active");
					 }
					
					 orgSpocService.addOrgSpoc(orgSpoc);
				 }
				 
				 if(actionType.equalsIgnoreCase(Constants.MAIL_ACCEPTED)){
					 clientService.sendAcceptEmailToClientOrVendor(orgSpoc,orgSpoc.getClientUser(),vendorOrgLocations);
					 onboard.setVendorOnboardStatus("Submitted");
					 onboard.setOnboardSubmitted(Constants.YES);
				 }else{
					 clientService.sendRejectEmailToClientOrVendor(orgSpoc,orgSpoc.getClientUser(),vendorOrgLocations);
					 onboard.setVendorOnboardStatus("Rejected");
				 }
				 
				 vendorOnboardDao.saveVendorOnboarding(onboard);
				 
		}
		
	}
	
	
	
	public void saveUserRoleActions(Long moduleId,Long tabId,OrgLocations orgLocations) throws Exception{
		 
		List<Long> actionsList = sourceleadActionsService.getSourceLeadActionsByModuleId(moduleId);
		
		Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
		 List<Long> adminRoleActonList = new ArrayList<Long>();
		 for (String s : adminrole.getActionId().split(",")){
			 adminRoleActonList.add(Long.parseLong(s));
		 }
		 
		 adminRoleActonList.retainAll(actionsList);
		 actionsList.removeAll(adminRoleActonList);
		
		
    	StringBuilder strbul  = new StringBuilder();
	     Iterator<Long> iter = actionsList.iterator();
	     while(iter.hasNext())
	     {
	         strbul.append(iter.next());
	        if(iter.hasNext()){
	         strbul.append(",");
	        }
	     }
	     strbul.toString();
		
		
        String adminids = adminrole.getActionId()+","+strbul;
		adminrole.setActionId(adminids);
		rolesService.storeRoles(adminrole);
		
		Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
		String managerids = managerrole.getActionId()+","+strbul;
	    managerrole.setActionId(managerids);
		rolesService.storeRoles(managerrole);
 }

	@Override
	public VendorOnboarding getVendorOnboardingByOrgLocationIdAndVendorLocationId(Long orgLocationId,
			Long vendorLocationId) throws Exception {
		
		return vendorOnboardDao.getVendorOnboardingByOrgLocationIdAndVendorLocationId(orgLocationId, vendorLocationId);
	}

	@Override
	public void sendMailAndNotificationToVendorContact(VendorOnboarding vendorOnboarding,
			OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user,User spocUser) throws Exception {
		
		setEmialAndNotificationToVendor(spocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Contact Invitation");
	}

	@Override
	public void saveVendorOnboardForVendorBulkImports(Long vendorOnboardingId,User user,User spocUser,OrgLocations orgLocations) throws Exception {
		
		VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingById(vendorOnboardingId);
		OrgRelations orgRelations = vendorOnboarding.getOrgRelationId();
		OrgLocations vendorOrgLocations = vendorOnboarding.getVendorLocationId();
		
		if(orgRelations != null && StringUtils.isNotBlank(orgRelations.getVendorOnboardTemplateId())){
			List<Long> templateIdsList = new ArrayList<>();
			String[] templateIds = orgRelations.getVendorOnboardTemplateId().split(",");
			for(String onboardTempalteId : templateIds){
				templateIdsList.add(Long.valueOf(onboardTempalteId));
			}
			
		    List<VendorOnboardingTemplateDocuments> documentsList = vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsByTemplateList(templateIdsList,"Documents");
		    if(documentsList != null && documentsList.size() > 0){
		    	
		    	for(VendorOnboardingTemplateDocuments documents : documentsList){
		    		
		    		VendorOnboardingTemplateDocuments templateDocuments = null;
		    		templateDocuments = new VendorOnboardingTemplateDocuments();
					templateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					templateDocuments.setVendorOnboardingId(vendorOnboarding);
					templateDocuments.setDocumentName(documents.getDocumentName());
					templateDocuments.setIsRequired(documents.getIsRequired());
					templateDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
					templateDocuments.setDocumentType("Vendor Documents");
					templateDocuments.setCreatedBy(user);
					templateDocuments.setUpdatedBy(user);
					templateDocuments.setCreatedDate(CommonUtil.currentDate());
					templateDocuments.setUpdatedDate(CommonUtil.currentDate());
					vendorOnboardTemplateDocumentsDao.saveVendorOnBoardingTemplateDocuments(templateDocuments);
		    	}
		    }
		    
		    List<VendorOnboardTasks> tasksList = vendorOnboardTasksDao.getVendorOnboardTasksByTemplateIdsList(templateIdsList);
	    	if(tasksList != null && tasksList.size() > 0){
	    	
	    		for(VendorOnboardTasks task : tasksList){
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
							 tasks = getTaskDetails(onboardTasks, spocUser, user, orgLocations);
							 tasks.setOwnerOrglocationId(vendorOrgLocations);
							 tasks.setVendorOnboardingId(vendorOnboarding);
							 taskDao.saveTasks(tasks);
						 }
					}
				}
				
				vendorOnboarding.setVendorOnboardStatus("Sent");
				vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
				
				orgRelations.setVendorRelationStatus("Sent");
				orgRelationsDao.addOrgRelations(orgRelations);
				
				SubmissionTemplate submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(
						vendorOrgLocations.getId(), orgLocations.getId());
				
				clientService.saveOrUpdateClientVendorElkDocument(orgLocations.getOrg(), vendorOrgLocations, "Vendor", null, orgRelations,
						submissionTemplate, user, orgLocations);
				
				setEmialAndNotificationToVendor(spocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Contact Invitation");
	    	}
		}
		
	}

}
