package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.VendorDocumentsFileInfo;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.dto.VendorOnboardTaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EntityType;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ClassFieldService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OrgBankDetailsService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TabsService;
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
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
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
	
	@Value("${vmsUrl}")
    private String vmsUrl;
	
	@Value("${sourceleadLoginUrl}")
    private String sourceleadLoginUrl;
	
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
	
	@Autowired
	SourceleadRolesService sourceleadRolesService;
	 
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	@Autowired
	TabsService tabService;
	
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	ClassFieldService classFieldService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	

	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
 
	
	@Override
	public VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user)
			throws Exception {
		
		VendorOnboarding vendorOnboarding = new VendorOnboarding();
		OrgRelations orgRelations = orgRelationsService.getOrgRelationById(vendorOrgLocations.getVendorOrgRelationId());
		vendorOnboarding.setOrgRelationId(orgRelations);
		vendorOnboarding.setVendorLocationId(vendorOrgLocations);
		User spocUser = userService.getUserById(vendorOrgLocations.getVendorSpocId());
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
		
		
		if(StringUtils.isNotBlank(onboardTasksDTO.getIsMailSent()) && onboardTasksDTO.getIsMailSent().equalsIgnoreCase(Constants.YES)){
			sendActivationEmail(onboardingSpocUser);
			OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdAndAssignedUserId(vendorOnboarding.getOrgRelationId().getId(), onboardingSpocUser.getId());
			setEmialAndNotificationToVendor(onboardingSpocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Invitation",orgSpoc);
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
		
		if(StringUtils.isNotBlank(orgRelations.getIsLawFirm()) && orgRelations.getIsLawFirm().equalsIgnoreCase(Constants.YES)){
			OrgLicenseSubscription subscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(vendorOrgLocations.getOrg().getId(), vendorOrgLocations.getCountry());
			String[] subscriptionTabs = subscription.getSubscriptionTabs().split(",");
			List<Long> subscriptionTabIdsList = new ArrayList<>();
			for(int i=0; i<subscriptionTabs.length;i ++){
				subscriptionTabIdsList.add(Long.valueOf(subscriptionTabs[i]));
			}
			
			if(!subscriptionTabIdsList.contains(Long.valueOf(Constants.TAB_IMMIGRATION))){
				List<Long> tabsIds = new ArrayList<>();
				List<Tabs> childTabsList = tabService.getClildTabsListByTabId(Long.valueOf(Constants.TAB_IMMIGRATION));
				if(childTabsList.size() > 0) {
					for(Tabs childTabs : childTabsList) {
						tabsIds.add(childTabs.getId());
					}
				}
				
				List<Long> sourceLeadActionsIdsList =  sourceleadActionsService.getAllSourceleadActionsIdsByTabsList(tabsIds);
				StringBuilder immigrationTabIds  = new StringBuilder();
				for(Long tabId : sourceLeadActionsIdsList){
					immigrationTabIds.append(tabId).append(",");
				}
				
				String tabIds = immigrationTabIds.toString();
				tabIds = tabIds.substring(0, tabIds.length()-1);
				
				Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, vendorOrgLocations.getId());
				String adminIds = adminrole.getActionId()+","+tabIds;
				adminrole.setActionId(adminIds);
				rolesService.storeRoles(adminrole);
				
				Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, vendorOrgLocations.getId());
				String managerIds = managerrole.getActionId()+","+tabIds;
				managerrole.setActionId(managerIds);
				rolesService.storeRoles(managerrole);
				
				String subscriptionRegisteredTabs = subscription.getSubscriptionTabs();
				subscriptionRegisteredTabs = subscriptionRegisteredTabs+","+Constants.TAB_IMMIGRATION;
				subscription.setSubscriptionTabs(subscriptionRegisteredTabs);
				orgLicenseSubscriptionService.saveOrgLicenseSubscription(subscription);
			}
		}
		
		SubmissionTemplate submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(
				vendorOrgLocations.getId(), orgLocations.getId());
		
		clientService.saveOrUpdateClientVendorElkDocument(orgLocations.getOrg(), vendorOrgLocations, "Vendor", null, orgRelations,
				submissionTemplate, user, orgLocations);
		
	}
	
	
	
	
	public void sendActivationEmail(User user) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 if(StringUtils.isNotBlank(user.getLastName())){
			 professionalName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 professionalName = sb.append(user.getFirstName()).toString();
		 }
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("sladmin");
		 
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("registerName", professionalName);
		 StringBuilder verifylink = new StringBuilder();
		 
		 globalVarsMap.put("verifyLink",verifylink.append("<a href='").append(sourceleadUrl).append("/ws/activateEmail?&token=").append(user.getVerificationUUID()).append("'>").append(sourceleadUrl).append("/activateEmail?&token=").append(user.getVerificationUUID()).append("</a>").toString()); 

		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACTIVATE_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
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
	
	
	public void setEmialAndNotificationToVendor(User spocUser, User loginUser, OrgLocations orgLocations,VendorOnboarding onboarding,OrgLocations vendorOrgLocation,String invitationType, OrgSpoc orgSpoc) throws Exception{
		
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
		 globalVarsMap.put("acceptURL",actionUrlBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 StringBuilder vendorLinkBuilder = new StringBuilder();
		 globalVarsMap.put("supportLink",vendorLinkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
		 
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
				      globalVarsMap.put("Password",password);
				  }else{
					  globalVarsMap.put("userName",spocUser.getEmail());
					  globalVarsMap.put("Password","Login with your existing sourcelead credentials");
					}
			   
		 }
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		 if(orgLocations != null)
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
		  if(emailScenario == null){
				emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
		   }
		 EmailScenario noificationEmailScenario = null;
		 
//		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_EMAIL);
		 noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR_INVITATION,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 EntityType entityType=classFieldService.getEntityTypeById(Constants.ACCEPT_VENDOR_INVITATION, Constants.ENTITY_TYPE_EMAIL);
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 //String body = emailScenerioUtil.buildTempBody(emailBody,globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),vendorOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(vendorOrgLocation.getId());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(),globalVarsMap,noificationEmailScenario);
		 
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
				 String actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("acceptVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 StringBuilder rejectActionBuilder = new StringBuilder();
				 String  rejectActionLink = rejectActionBuilder.append("").append(vmsUrl).append("/").append("rejectVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("&orgSpocUUID=").append(orgSpc.getUUID()).append("").toString();
				 
				 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,loginUser, spocUser, vendorOrgLocation, noificationEmailScenario,orgSpc.getUUID());
			}
		}else{
			List<OrgSpoc> orgSpocList = orgSpocService.getSpocListByVendorUserIdAndOrgRelationId(onboarding.getOrgRelationId().getId(), spocUser.getId());
//			for(OrgSpoc orgSpc : orgSpocList){
				 String notificationUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
				 noificationEmailScenario.setBody(notificationbody);
				 noificationEmailScenario.setUUID(notificationUUID);
				 
			     StringBuilder actionLinkBuilder = new StringBuilder();
				 String actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("acceptVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("").toString();
				 
				 StringBuilder rejectActionBuilder = new StringBuilder();
				 String  rejectActionLink = rejectActionBuilder.append("").append(vmsUrl).append("/").append("rejectVendorOnboardInvitation?onboardUUID=").append(onboarding.getOnboardUUID()).append("&notificationUUID=").append(notificationUUID).append("&orgSpocUUID=").append(orgSpoc.getUUID()).append("").toString();
				 
				 if(spocUser.getId().longValue() == orgSpoc.getClientUser().getId().longValue()) {
					 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,orgSpoc.getVendorUser(), spocUser, vendorOrgLocation, noificationEmailScenario,orgSpoc.getUUID());
				 }else {
					 emailScenerioUtil.sendNotificationToClientOrVendor(actionLink,rejectActionLink, loginUser,orgSpoc.getClientUser(), spocUser, vendorOrgLocation, noificationEmailScenario,orgSpoc.getUUID());
				 }
				 
//			}
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
					    FileUploadUtil.moveFile(vendorOnboardDTO.getStaticPath(), fileInfo, "Vendor-Onboard-Submitted-Documents");
					}
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
			Long tsModuleId = (long) Constants.MODULE_TIMESHEET;
			String currentOrgType = vendorOrg.getOrgType();
			vendorOrg.setOrgType("Company");
			
			List<Integer> moduleIds = new ArrayList<Integer>();
			 for (String s : vendorOrgLocations.getModulesId().split(",")){
				 moduleIds.add(Integer.parseInt(s));
			 }
			
			 
			 flag = moduleIds.contains(Constants.MODULE_ATS);
	    		tabId = (long) Constants.TAB_CLIENT;
	    		moduleId = (long) Constants.MODULE_ATS;
	    		if(flag == false){
	    			modules = modules+","+Constants.MODULE_ATS;
	    			
	    		}
	    		
	    		boolean tsflag = moduleIds.contains(Constants.MODULE_TIMESHEET);
	    		if(tsflag == false) {
    				modules = modules+","+Constants.MODULE_TIMESHEET;
    			}
	    		
	    		saveUserRoleActions(vendorOrgLocations);
	    		
	    		vendorOrgLocations.setModulesId(modules);
			
	    		vendorOrg.setStatus(Status.ACTIVE);
	    		vendorOrg.setUser(user);
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
					 
					 if(actionType.equalsIgnoreCase(Constants.MAIL_ACCEPTED)){
						 Long LogInid=user.getId();
							 if(LogInid != orgSpoc.getClientUser().getId()){
								 
								 ReferContacts referContact=referContactsDao.getReferContactsByEmails(LogInid, orgSpoc.getClientUser().getEmail());
								 
								 if(referContact == null){
									 ReferContacts referContacts=new ReferContacts();
									 referContacts.setReferFromUser(orgSpoc.getVendorUser());
									 referContacts.setFirstName(orgSpoc.getClientUser().getFirstName());
									 referContacts.setLastName(orgSpoc.getClientUser().getLastName());
									 referContacts.setContactUserEmail(orgSpoc.getClientUser().getEmail());
									 referContacts.setContactUserPhone(orgSpoc.getClientUser().getMobilePhone());
									 referContacts.setContactUser(orgSpoc.getClientUser());
									 referContacts.setContactUserUUID(SourceLeadRandomCodeHelper.generateUUID());

									 

									 if(orgSpoc.getOrgRelations().getInitiatorType().equalsIgnoreCase("Client")){
										String compName=orgSpoc.getOrgRelations().getLocationId().getOrgLocationName(); 
										//OrgLocations OrgLocations =orgLocationsDao.getOrgLocations(id);
										referContacts.setCompanyName(compName);
									 }
									 if(orgSpoc.getOrgRelations().getResponderType().equalsIgnoreCase("Client")){
											//Long id=Long.valueOf(orgSpoc.getOrgRelations().getResponderId()).longValue(); 
											//OrgLocations OrgLocations =orgLocationsDao.getOrgLocations(id);
										 String compName=orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName(); 
											referContacts.setCompanyName(compName);
										 }
									 
									 referContacts.setClientType(1);
									 referContacts.setCreatedDate(new Date());
									 referContacts.setCreatedBy(user.getUserCode());
									 referContactsDao.addReferContacts(referContacts);
								 }
								 
							 }
						 }
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
					if(StringUtils.isNotBlank(currentOrgType) && currentOrgType.equalsIgnoreCase("Guest")) {
						List<UserRole> userRolesList = userRoleService.getAllUserRolesByOrgLocationId(vendorOrgLocations.getId());
						if(userRolesList != null && !userRolesList.isEmpty()) {
							
							for(UserRole userRole : userRolesList) {
								userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
							}
						}
					}
		}
		
	}
	
	
	
	public void saveUserRoleActions(OrgLocations orgLocations) throws Exception{
		 
		SourceleadRoles soruceLeadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_VENDOR);
		List<Long> soureleadActionIdList = new ArrayList<>();
		String[] actionsIds = null;
		actionsIds = soruceLeadRoles.getActionId().split(",");
		 for(int i=0 ; i < actionsIds.length; i++) {
			 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
		 }
		
		Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
		 List<Long> adminRoleActonList = new ArrayList<Long>();
		 for (String s : adminrole.getActionId().split(",")){
			 adminRoleActonList.add(Long.parseLong(s));
		 }
		 
		 adminRoleActonList.retainAll(soureleadActionIdList);
		 soureleadActionIdList.removeAll(adminRoleActonList);
		
		
    	 StringBuilder strbul  = new StringBuilder();
	     Iterator<Long> iter = soureleadActionIdList.iterator();
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
		
		updateOrgSubscriptionRegsiteredTabs(orgLocations, adminids);
		
 }
	
 public void updateOrgSubscriptionRegsiteredTabs(OrgLocations orglocations,String actionIds) throws Exception {
		 
		 String[] sourceleadActionIds = actionIds.split(",");
		 List<Long> actionIdsList = new ArrayList<>();
		 for(int i=0; i<sourceleadActionIds.length;i++) {
			 actionIdsList.add(Long.parseLong(sourceleadActionIds[i]));
		 }
		 
		 List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActionsByIdsList(actionIdsList);
		 Set<Long> tabIds = new HashSet<>();
		 if(actionsList != null && !actionsList.isEmpty()) {
			 for(SourceleadActions action : actionsList) {
				 Tabs tabs = tabService.getTabListByTabId(action.getTabs().getId());
				 if(tabs.getTabs() != null){
					 if(tabs.getId() == 14L) {
						 tabIds.add(tabs.getId());
					 }else {
						 tabIds.add(tabs.getTabs().getId());
					 }
				 }else {
					 tabIds.add(tabs.getId());
				 }
			 }
		 }
		 
		 StringBuilder tabsBuilder = new StringBuilder();
		 for (Long tabId : tabIds) {
			 tabsBuilder.append(tabId).append(",");
		 }
		 String tabsBuilderString = tabsBuilder.substring(0, tabsBuilder.length()-1);
		 
		 OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orglocations.getOrg().getId(), orglocations.getCountry());
		 orgLicenseSubscription.setSubscriptionTabs(tabsBuilderString);
		 orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
	 }

	@Override
	public VendorOnboarding getVendorOnboardingByOrgLocationIdAndVendorLocationId(Long orgLocationId,
			Long vendorLocationId) throws Exception {
		
		return vendorOnboardDao.getVendorOnboardingByOrgLocationIdAndVendorLocationId(orgLocationId, vendorLocationId);
	}

	@Override
	public void sendMailAndNotificationToVendorContact(VendorOnboarding vendorOnboarding,
			OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user,User spocUser,OrgSpoc orgSpoc) throws Exception {
		
		setEmialAndNotificationToVendor(spocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Contact Invitation",orgSpoc);
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
				sendActivationEmail(spocUser);
				OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdAndAssignedUserId(orgRelations.getId(), spocUser.getId());
				setEmialAndNotificationToVendor(spocUser, user, orgLocations, vendorOnboarding, vendorOrgLocations,"Vendor Contact Invitation",orgSpoc);
	    	
		}
		
	}


	@Override
	public String getVendorOnboardingUUIDByOrgRelationId(Long orgRelationId) {
		return vendorOnboardDao.getVendorOnboardingUUIDByOrgRelationId(orgRelationId);
	}

}
