package com.bis.sourcelead.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RegistrationServiceImpl implements RegistrationService{

	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	 
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	 
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private PasswordResetsSevice passwordResetsSevice;
	
	@Autowired
	private SubmissionTemplateService  submissionTemplateService;
	
	@Autowired
	private RoleHierarchyService roleHierarchyService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private SourceleadRolesService sourceleadRolesService;
	
	@Autowired
    private SourceleadELKBaseIndexer<UserIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	ReferContactsService referContactsService;
	
	@Autowired
	RequirementReferService requirementReferService;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	OrgDao orgDao;
	
	
	protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public void addRegistration(Registration registration,User loginUser,String modules,String subModules, Long orgLocationsId) throws Exception {
		
		String[] registredModules =  modules.split(",");
		
		//For Saving of Org Details
		Org org = getOrgDetails(registration,subModules,modules);
		try {
			orgService.addOrganization(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For Saving of User Details
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		User user = null;
		UserRole uRole = null;
		user = userService.findByEmail(registration.getEmail());
		if(null != user){
			   uRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		   }
		if(user == null){
			 user = getUserDetails(registration,password);
		}else if(null != uRole && uRole.getUserState().equals(Status.NOT_LOGGEDIN)){
			user.setPassword(sourceleadPasswordEncoder.encode(password));
		}
		try {
			userService.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		org.setUser(user);
		
		try {
			orgService.updateOrg(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For saving of orgLocation Details which location has choose in the registration
		OrgLocations orgLocations = getOrgLocationsDetails(registration, org, user);
		Timezone timeZone = timeZoneService.getTimezone(Long.parseLong(registration.getTimeZone()));
		orgLocations.setTimezone(timeZone);
		try {
			orgLocationService.addOrgLocations(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		saveOrgRoleHierarchy(orgLocations,user);
		
		saveOrgBasedRoles(org,orgLocations,user);
		
		//For saving the User Role (At registration user will have Admin Role)
		UserRole userRole = getUserRoleDetails(user,orgLocations);
		try {
			userRoleService.addUserRole(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For saving Employee details (Every User is an Employee)
		Employees employee = getEmployeeDetails(user,orgLocations);
		try {
			employeesService.addEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<Integer> moduleIds = new ArrayList<Integer>();
		 for (String s : org.getModulesId().split(",")){
			 moduleIds.add(Integer.parseInt(s));
		 }
		 
		 
		/* 
		  //commented by Prasad salina
		  boolean flag = moduleIds.contains(Constants.MODULE_TIMESHEET);
		  if(flag == true){
			  saveProject(registration, orgLocations, user, employee);
 		  }*/
		
		saveDefaultSubmissionTemplate(org,orgLocations,user);
		saveDefaultInterviewFeedbackTemplate(org,orgLocations,user);
		
		/*
		 * Saving USER DB data as ElasticSearch Documents
		 */
		userService.saveOrUpdateUserElkDocument(userRole,employee,"sladmin");
		
		userService.sendEmail(user, loginUser, password,orgLocationsId);
	
	}
	
	private Org getOrgDetails(Registration registration,String subModules,String modules){
		
		Org org = new Org();
		org.setOrgName(registration.getCompanyName());
		org.setWebSite(convertWebsite(registration.getWebsite()));
		org.setOrgType("Company");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		String orgModules = "";
		
		
		if(!isNullOrEmpty(subModules)){
			String[] registeredSubModules =  subModules.split(",");
			for(int i=0; i<registeredSubModules.length; i++){
					orgModules = orgModules+registeredSubModules[i]+",";
			}
		}
		
		if(!isNullOrEmpty(modules)){
			String[] registeredModules =  modules.split(",");
			for(int i=0; i<registeredModules.length; i++){
				if(Long.parseLong(registeredModules[i]) != 1L){
					orgModules = orgModules+registeredModules[i]+",";
				}
			}
		}
		
		
		orgModules = orgModules.substring(0, orgModules.length()-1);
		org.setModulesId(orgModules);
		org.setCreatedBy("sladmin");
		
		org.setCreatedDate(new Date());
		return org;
	}
	
	private User getUserDetails(Registration registration,String password){
		User user = new User();
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setUserName(registration.getEmail());
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(user.getUserCode());
		user.setCreatedDate(new Date());
		user.setIsMailSent(Status.Y);
		return user;
	}
	
	
	 public Projects  saveProject(Registration registration,OrgLocations orgLocations,User approverUser,Employees employee) throws Exception {
		 			 	
	    	Projects projects = new Projects();
	    	projects.setProjectSequeneNumber("Project001");
	    	projects.setProjectName(registration.getCompanyName());
	    	projects.setDescription(registration.getCompanyName());
	    	projects.setStartDate(new Date());
	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    	projects.setProjectStatus(Project_Status.STARTED);
	    	projects.setIsPrimary(1);
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	projects.setOrgLocationsId(orgLocations);
	    	Projects updatedProjects = null;
	    	
	    	updatedProjects = projectsService.saveProject(projects);
	    	
	    	
			if(updatedProjects != null){
				ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
		    	pah1.setProjectId(updatedProjects);
		    	pah1.setApproverUserId(approverUser);
		    	pah1.setLevel(2);
		    	try {
					 projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    /*	ProjectMembers pm = new ProjectMembers();
				pm.setEmployee(employee);
				pm.setProjects(projects);
				pm.setProjectAssignedDate(projects.getStartDate());
				pm.setCreatedDate(new Date());
				pm.setUpdatedDate(new Date());
				projectMembersService.saveProjectMember(pm);*/
			}
			commonUtil.storeSourceleadTasks(approverUser, updatedProjects);
			return updatedProjects;
	}
	 
	 
	private OrgLocations getOrgLocationsDetails(Registration registration,Org org,User user ) throws Exception{
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setCountry(registration.getCountry());
		orgLocations.setOrg(org);
		orgLocations.setUser(user);
		orgLocations.setStatus(Status.ACTIVE);
		Countries countries = countriesService.getCountriesByCountryName(registration.getCountry());
		orgLocations.setLocationCode(org.getOrgCode()+countries.getCode2());
		orgLocations.setCreatedBy(user.getUserCode());
		orgLocations.setCreatedDate(new Date());
		return orgLocations;
	}
	
	private UserRole getUserRoleDetails(User user,OrgLocations orgLocations ){
		
		UserRole primaryUserRole = null;
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setOrgLocations(orgLocations);
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType("Admin");
		Roles roles = null;
		try {
			roles = rolesService.getRolesByRoleNameAndOrgLocationId("ROLE_ADMIN",orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		try {
			primaryUserRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null == primaryUserRole){
			userRole.setIsPrimary(1);
		}else{
			userRole.setIsPrimary(0);
		}
		
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(user.getUserCode());
		return userRole;
		
	}
	
	private Employees getEmployeeDetails(User user,OrgLocations orgLocations){
		
		Employees employees = new Employees();
		employees.setUser(user);
		employees.setOrgLocations(orgLocations);
		employees.setCreatedBy(user.getUserCode());
		employees.setCreatedDate(new Date());
		return employees;
	}
	
	
	public void saveOrgRoleHierarchy(OrgLocations orgLocations,User user) throws Exception{
		List<RoleHierarchy> hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(1L);
		if(null != hierarchyList){
			for(RoleHierarchy roleHierarchy : hierarchyList){
				RoleHierarchy orgRoleHierarchy = new RoleHierarchy();
				orgRoleHierarchy.setHierarchyName(roleHierarchy.getHierarchyName());
				orgRoleHierarchy.setHierarchyLevel(roleHierarchy.getHierarchyLevel());
				orgRoleHierarchy.setOrgLocations(orgLocations);
				orgRoleHierarchy.setStatus(roleHierarchy.getStatus());
				orgRoleHierarchy.setCreatedBy(user.getUserCode());
				orgRoleHierarchy.setCreatedDate(new Date());
				roleHierarchyService.saveRoleHierarchy(orgRoleHierarchy);
			}
		}
		
	}
	
	public void saveOrgBasedRoles(Org org,OrgLocations orgLocations,User user) throws Exception{
		List<SourceleadRoles> rolesList = sourceleadRolesService.getSourceleadRoles();
		if(null != rolesList){
			for(SourceleadRoles roles : rolesList){
				Roles orgRoles = new Roles();
				orgRoles.setRoleName(roles.getRoleName());
				orgRoles.setDisplayName(roles.getDisplayName());
				orgRoles.setOrgLocations(orgLocations);
				RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyByHierarchyLevelAndOrgLocationId(roles.getRoleHierarchy().getHierarchyLevel(), orgLocations.getId());
				orgRoles.setRoleHierarchy(roleHierarchy);
				orgRoles.setOrg(orgLocations.getOrg());
				orgRoles.setDefaultRole(1);
				StringBuilder strbul  = new StringBuilder();
				
				List<Long> roleActonsList = new ArrayList<Long>();
				if(null != roles.getActionId()){
					for (String s : roles.getActionId().split(",")){
						 roleActonsList.add(Long.parseLong(s));
					 }
				}
				
				List<SourceleadActions> sourceLeadActionsList =  sourceleadActionsService.getAllSourceleadActions(org);
				for(SourceleadActions actions : sourceLeadActionsList){
					if(roleActonsList.contains(actions.getId())){
						 strbul.append(actions.getId()).append(",");
					}
				}
				
				String actionId = strbul.toString();
				
				if(!isNullOrEmpty(actionId)){
					actionId = actionId.substring(0, actionId.length()-1);
					orgRoles.setActionId(actionId);
				}else{
					orgRoles.setActionId(null);
				}
				
				orgRoles.setStatus(roles.getStatus().toString());
				orgRoles.setCreatedBy(user.getUserCode());
				orgRoles.setCreatedDate(new Date());
				rolesService.storeRoles(orgRoles);
			}
		}
		
	}
	
	 
	 public void  saveDefaultSubmissionTemplate(Org org,OrgLocations orgLocations,User user) throws Exception {
		 SubmissionTemplate sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
		 
		 StringBuilder templateStringBuilder=new StringBuilder();
		 SubmissionTemplate submissionTemplate = new SubmissionTemplate();
		 submissionTemplate.setTemplateName(templateStringBuilder.append(org.getOrgName()).append(" Default Template").toString());
		 submissionTemplate.setDescription(templateStringBuilder.append(org.getOrgName()).append(" Default Template").toString());
		 submissionTemplate.setOrgLocationId(orgLocations);
		 submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
		 submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
		 submissionTemplate.setUpdatedBy(user.getUserCode());
		 submissionTemplate.setTemplateType("Submission");
		 submissionTemplate.setUpdatedDate(new Date());
		 submissionTemplate.setCreatedBy(user.getUserCode());
		 submissionTemplate.setCreatedDate(new Date());
		 submissionTemplateService.addSubmissionTemplate(submissionTemplate);
		 
	 }
	 
	 
	 
	 public void  saveDefaultInterviewFeedbackTemplate(Org org,OrgLocations orgLocations,User user) throws Exception {
		 SubmissionTemplate sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
		 
		 StringBuilder templateStringBuilder=new StringBuilder();
		 SubmissionTemplate submissionTemplate = new SubmissionTemplate();
		 submissionTemplate.setTemplateName(templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
		 submissionTemplate.setDescription(templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
		 submissionTemplate.setOrgLocationId(orgLocations);
		 submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
		 submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
		 submissionTemplate.setUpdatedBy(user.getUserCode());
		 submissionTemplate.setTemplateType("Feedback");
		 submissionTemplate.setUpdatedDate(new Date());
		 submissionTemplate.setCreatedBy(user.getUserCode());
		 submissionTemplate.setCreatedDate(new Date());
		 submissionTemplateService.addSubmissionTemplate(submissionTemplate);
		 
	 }
	
	
	private String convertWebsite(String website){
		if(!website.startsWith("http") && !website.startsWith("https")){
			 website = "http://" + website;
	    }        
	    URL netUrl = null;
		try {
			netUrl = new URL(website);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	    String host = netUrl.getHost();
	    if(host.startsWith("www")){
	        host = host.substring("www".length()+1);
	    }
	    return host;
	}

	@Override
	public void sendEmail(Registration registration,Long locationsId) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(registration.getFirstName());
		 emailMessage.setLastName(registration.getLastName());
		 emailMessage.setToEmail(registration.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 StringBuilder loginURLStringBuilder=new StringBuilder();
		 globalVarsMap.put("loginURL",loginURLStringBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ORG_REGISTRATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	@Override
	public void sendResetPasswordEmail(User user,Long locationsId, String verificationCode) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("sourceleadadmin@sourcelead.com");
		 emailMessage.setFromName("SourceLead");
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("verificationCode",verificationCode);
		 StringBuilder resetPasswordURLStringBuilder=new StringBuilder();
		 globalVarsMap.put("resetPasswordURL",resetPasswordURLStringBuilder.append("<a href='").append(sourceleadUrl).append("/resetPassword").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reset Password</div></a>").toString());
			
		//setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.FORGOT_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
		
		//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	
	@Override
	public void sendUpdatePasswordEmail(User user,Long locationsId) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("sourceleadadmin@sourcelead.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
			
		//setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATE_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
	
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		//construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		
		
	}

	@Override
	public String addPasswordResets(User user) throws Exception {
		
		PasswordResets passwordResets = getPasswordResetsDetails(user);
		passwordResetsSevice.savePasswordResets(passwordResets);
		return passwordResets.getVerificationCode(); 
		
		
	}
	
	private PasswordResets getPasswordResetsDetails(User user){
		
		PasswordResets passwordResets = new PasswordResets();
		passwordResets.setUser(user);
		passwordResets.setEmail(user.getEmail());
		passwordResets.setStatus("Open");
		passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
		passwordResets.setCreatedDate(new Date());
		passwordResets.setCreatedBy(user.getUserCode());
		return passwordResets;
		
	}


	@Override
	public void resetPassword(PasswordResets passwordResets,String password) throws Exception {
		
		//Here user will loaded automatically without any DB hit. beacuse user fetchtype is declared as Eager in PasswordResets.
		User user = passwordResets.getUser();
		user.setPassword(sourceleadPasswordEncoder.encode(password));
		userService.addUser(user);
		
	}


	@Override
	public void deletePasswordResets(PasswordResets passwordResets) throws Exception {
		passwordResetsSevice.deletePasswordResets(passwordResets);
	}

	@Override
	public void addUserBySourcelead(User user, JSONObject jsonObj) throws Exception {
		
		User orgUser = userService.findByEmail(jsonObj.getString("userEmail"));
		UserRole userRole = new UserRole();
		String password = null;
		if(null == orgUser){
			orgUser = new User();
			orgUser.setFirstName(jsonObj.getString("firstName"));
			
			if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
				orgUser.setLastName(jsonObj.getString("lastName"));
			}
			
			orgUser.setEmail(jsonObj.getString("userEmail"));
			orgUser.setCreatedBy(user.getUserCode());
			orgUser.setCreatedDate(new Date());
			orgUser.setUpdatedBy(user.getUserCode());
			orgUser.setUserName(jsonObj.getString("userEmail"));
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			orgUser.setPassword(sourceleadPasswordEncoder.encode(password));
			orgUser.setIsMailSent(Status.Y);
			orgUser.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			orgUser.setUpdatedDate(new Date());
			userService.addUser(orgUser);
			userRole.setIsPrimary(1);
		}else{
			userRole.setIsPrimary(0);
		}
		
		
		
		userRole.setUser(orgUser);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(jsonObj.getString("registeredOrgLocationId")));
		userRole.setOrgLocations(orgLocations);
		Roles roles = rolesService.getRolesById(Long.valueOf(jsonObj.getString("userRole")));
		userRole.setRoles(roles);
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(roles.getDisplayName());
		
		userRole.setCreatedBy(user.getUserCode());
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedBy(user.getUserCode());
		userRole.setUpdatedDate(new Date());
		userRoleService.addUserRole(userRole);
		
		
		Employees employees = new Employees();
		employees.setUser(orgUser);
		employees.setOrgLocations(orgLocations);
		employees.setCreatedBy(user.getUserCode());
		employees.setCreatedDate(new Date());
		employees.setUpdatedBy(user.getUserCode());
		employees.setUpdatedDate(new Date());
		employeesService.addEmployee(employees);
		
		if(null == orgLocations.getUser()){
			Org org = orgLocations.getOrg();
			if(roles.getRoleName().equalsIgnoreCase("ROLE_ADMIN")){
				orgLocations.setUser(orgUser);
				orgLocationService.updateOrgLocations(orgLocations);
				org.setUser(orgUser);
			}	
			
			if(org.getOrgType().equalsIgnoreCase("Guest")){
				org.setOrgType("Company");
				org.setStatus(Status.ACTIVE);
			}
			orgService.addOrganization(org);
		}
		/*
		 * Saving USER DB data as ElasticSearch Documents
		 */
		userService.saveOrUpdateUserElkDocument(userRole,employees,orgUser.getUserCode());
		sendEmail(orgUser,user,password,orgLocations.getId(),jsonObj.getString("registredOrgId"));
		
		
		
	}
	
	
	public void sendEmail(User user, User loginUser, String password, Long locationId,String orgId) throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		 
		 boolean isUserActive = false;
		 isUserActive = userRoleService.isUserActive(user.getId());
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());

		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
		 globalVarsMap.put("administratorLastName",loginUser.getLastName());
		 globalVarsMap.put("role",userRole.getUserType());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("userEmailId",user.getEmail());
		 if(isUserActive == false){
			 password = SourceLeadRandomCodeHelper.generateRandomPassword();
			 user.setPassword(sourceleadPasswordEncoder.encode(password));
			 userService.addUser(user);
			 globalVarsMap.put("password",password);
		 }else{
			 globalVarsMap.put("password","<font color='green'>Note : <b>Login with your existing Sourcelead password.</b></font>");
		 }
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_USER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 Org registredOrg = orgDao.findOrgByID(Long.parseLong(orgId));
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, registredOrg);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationId,iAdressArray,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public void saveProfessional(JSONObject jsonObj,String contactUUID) throws Exception {
		
		User user = new User();
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			user.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			user.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			user.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			user.setCountry(jsonObj.getString("location"));
		}
		
		user.setUserName(SourceLeadRandomCodeHelper.generateRandomUniquUserName());
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setUserType(Constants.PROFESSIONAL);
		user.setCreatedBy("sladmin");
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		
		userService.addUser(user);
		
		Org org = new Org();
		org.setId(1L);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
				
		
		UserRole userRole = new UserRole();
		Roles roles = rolesService.getRolesByRoleNameAndOrgId(Constants.ROLE_NAME_PROFESSIONAL, 1L);
		userRole.setUser(user);
		userRole.setRoles(roles);
		userRole.setOrgId(org);
		userRole.setOrgLocations(orgLocations);
		userRole.setIsPrimary(1);
		userRole.setCreatedBy("sladmin");
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(Constants.PROFESSIONAL);
		userRoleService.addUserRole(userRole);
		
		Candidate candidate = new Candidate();
		candidate.setConsultantUser(user);
		candidate.setOrgId(org);
		candidate.setOrgLocationId(orgLocations);
		candidate.setCandidateType(Constants.PROFESSIONAL);
		candidate.setCountry(jsonObj.getString("location"));
		
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			candidate.setEmail(jsonObj.getString("email"));
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		candidateService.addCandidate(candidate);
		candidateService.saveOrUpdateCandidateElkDocument(candidate);
		
		
		if(!isNullOrEmpty(contactUUID)){
			
			ReferContacts referContacts = referContactsService.getReferContactsByUUID(contactUUID); 
			String referToEmail = referContacts.getContactUserEmail();
			referContacts.setFirstName(user.getFirstName());
			referContacts.setLastName(user.getLastName());
			referContacts.setContactUserEmail(user.getEmail());
			referContacts.setContactUser(user);
			referContactsService.addReferContacts(referContacts);
			
			List<RequirementRefer>  requirementReferList = requirementReferService.getRequirementReferListByEmail(referToEmail);
			if(null != requirementReferList && requirementReferList.size() > 0){
				for(RequirementRefer requirementRefer : requirementReferList){
					requirementRefer.setReferToUser(user);
					requirementRefer.setReferToEmail(referContacts.getContactUserEmail());
					requirementReferService.addRequirementRefer(requirementRefer);
					
				}
			}
			
			
		}
		
		
		sendEmailToProfessional(user);
		
	}
	
	
	public void sendEmailToProfessional(User user) throws Exception {
		
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 professionalName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 professionalName = sb.append(user.getFirstName()).toString();
		 }
		 
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setFromEmail("sourceleadadmin@sourcelead.com");
		 emailMessage.setCreatedBy("sladmin");
		 
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("loginUserName", user.getUserName());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("resetLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("resetloginCredentials").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_PROFESSIONAL,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public void saveIndividualRecruiter(JSONObject jsonObj) throws Exception {
		
		User user = new User();
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			user.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			user.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			user.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			user.setCountry(jsonObj.getString("location"));
		}
		
		user.setUserName(SourceLeadRandomCodeHelper.generateRandomUniquUserName());
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setUserType(Constants.INDIVIDUAL_RECRUITER);
		user.setCreatedBy("sladmin");
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		userService.addUser(user);
		
		Org org = new Org();
		org.setId(1L);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		
		UserRole userRole = new UserRole();
		Roles roles = rolesService.getRolesByRoleNameAndOrgId(Constants.ROLE_NAME_INDEPENDENT_RECRUITER, 1L);
		userRole.setUser(user);
		userRole.setRoles(roles);
		userRole.setOrgId(org);
		userRole.setOrgLocations(orgLocations);
		userRole.setIsPrimary(1);
		userRole.setCreatedBy("sladmin");
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(Constants.INDIVIDUAL_RECRUITER);
		userRoleService.addUserRole(userRole);
		
		Candidate candidate = new Candidate();
		candidate.setConsultantUser(user);
		candidate.setOrgId(org);
		candidate.setOrgLocationId(orgLocations);
		candidate.setCountry(jsonObj.getString("location"));
		candidate.setCandidateType(Constants.INDIVIDUAL_RECRUITER);
		
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			candidate.setEmail(jsonObj.getString("email"));
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		candidateService.addCandidate(candidate);
		candidateService.saveOrUpdateCandidateElkDocument(candidate);
		
		sendEmailToIndividualRecruiter(user);
		
	}
	
	
	
	public void sendEmailToIndividualRecruiter(User user) throws Exception {
		
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 professionalName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 professionalName = sb.append(user.getFirstName()).toString();
		 }
		 
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setCreatedBy("sladmin");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("loginUserName", user.getUserName());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("resetLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("resetloginCredentials").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_INDIVIDUAL_RECRUITER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public void saveProfessionalFromSocialInviter(JSONObject jsonObj) throws Exception {
		User user = new User();
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			user.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			user.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			user.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			user.setCountry(jsonObj.getString("location"));
		}
		
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		if(jsonObj.has("userName") && !isNullOrEmpty(jsonObj.getString("userName"))){
			user.setUserName(jsonObj.getString("userName"));
		}
		if(jsonObj.has("imageUrl") && !isNullOrEmpty(jsonObj.getString("imageUrl"))){
			user.setDocFile(fetchRemoteFile(jsonObj.getString("imageUrl")));
			user.setFileContentType("image/png");
		}
		if(jsonObj.has("service") && !isNullOrEmpty(jsonObj.getString("service"))
		  && jsonObj.has("profileUrl") && !isNullOrEmpty(jsonObj.getString("profileUrl"))){
			String service = jsonObj.getString("service");
			String profileUrl = jsonObj.getString("profileUrl");
			if(!isNullOrEmpty(service)  && (service.equalsIgnoreCase("googleplus") || service.equalsIgnoreCase("gmail") || service.equalsIgnoreCase("google"))){
				user.setGooglePlus(profileUrl);
			}
			else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("facebook")){
				user.setFacebook(profileUrl);
			}
			else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("twitter")){
				user.setTwitter(profileUrl);			
			}
			else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("linkedin")){
				user.setLinkedIn(profileUrl);
			}
			
		}
		user.setUserType(Constants.PROFESSIONAL);
		user.setCreatedBy("sladmin");
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		
		user = userService.addUser(user);
		
		Org org = new Org();
		org.setId(1L);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
				
		
		UserRole userRole = new UserRole();
		Roles roles = rolesService.getRolesByRoleNameAndOrgId(Constants.ROLE_NAME_PROFESSIONAL, 1L);
		userRole.setUser(user);
		userRole.setRoles(roles);
		userRole.setOrgId(org);
		userRole.setOrgLocations(orgLocations);
		userRole.setIsPrimary(1);
		userRole.setCreatedBy("sladmin");
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		userRole.setUserState(Status.ACTIVE);
		userRole.setUserType(Constants.PROFESSIONAL);
		userRole = userRoleService.addUserRole(userRole);
		
		Candidate candidate = new Candidate();
		candidate.setConsultantUser(user);
		candidate.setOrgId(org);
		candidate.setOrgLocationId(orgLocations);
		candidate.setCandidateType(Constants.PROFESSIONAL);
		candidate.setCountry(jsonObj.getString("location"));
		
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			candidate.setEmail(jsonObj.getString("email"));
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		candidateService.addCandidate(candidate);
		candidateService.saveOrUpdateCandidateElkDocument(candidate);
		loginWithSocialInviter(user, userRole, orgLocations);
	}

	@Override
	public void saveIndividualRecruiterFromSocialInviter(JSONObject jsonObj) throws Exception {
		User user = new User();
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			user.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			user.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			user.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("location") && !isNullOrEmpty(jsonObj.getString("location"))){
			user.setCountry(jsonObj.getString("location"));
		}
		
		if(jsonObj.has("userName") && !isNullOrEmpty(jsonObj.getString("userName"))){
			user.setUserName(jsonObj.getString("userName"));
		}
		if(jsonObj.has("imageUrl") && !isNullOrEmpty(jsonObj.getString("imageUrl"))){
			user.setDocFile(fetchRemoteFile(jsonObj.getString("imageUrl")));
			user.setFileContentType("image/png");
		}
		if(jsonObj.has("service") && !isNullOrEmpty(jsonObj.getString("service"))
		  && jsonObj.has("profileurl") && !isNullOrEmpty(jsonObj.getString("profileurl"))){
			String service = jsonObj.getString("service");
			String profileUrl = jsonObj.getString("profileurl");
			if(isNullOrEmpty(service)  && (service.equalsIgnoreCase("googleplus") || service.equalsIgnoreCase("gmail") || service.equalsIgnoreCase("google"))){
				user.setGooglePlus(profileUrl);
			}
			else if(isNullOrEmpty(service)  && service.equalsIgnoreCase("facebook")){
				user.setFacebook(profileUrl);
			}
			else if(isNullOrEmpty(service)  && service.equalsIgnoreCase("twitter")){
				user.setTwitter(profileUrl);			
			}
			else if(isNullOrEmpty(service)  && service.equalsIgnoreCase("linkedin")){
				user.setLinkedIn(profileUrl);
			}
			
		}
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setUserType(Constants.INDIVIDUAL_RECRUITER);
		user.setCreatedBy("sladmin");
		user.setCreatedDate(new Date());
		user.setUpdatedDate(new Date());
		
		userService.addUser(user);
		
		Org org = new Org();
		org.setId(1L);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		
		UserRole userRole = new UserRole();
		Roles roles = rolesService.getRolesByRoleNameAndOrgId(Constants.ROLE_NAME_INDEPENDENT_RECRUITER, 1L);
		userRole.setUser(user);
		userRole.setRoles(roles);
		userRole.setOrgId(org);
		userRole.setOrgLocations(orgLocations);
		userRole.setIsPrimary(1);
		userRole.setCreatedBy("sladmin");
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		userRole.setUserState(Status.ACTIVE);
		userRole.setUserType(Constants.INDIVIDUAL_RECRUITER);
		userRoleService.addUserRole(userRole);
		
		Candidate candidate = new Candidate();
		candidate.setConsultantUser(user);
		candidate.setOrgId(org);
		candidate.setOrgLocationId(orgLocations);
		candidate.setCountry(jsonObj.getString("location"));
		candidate.setCandidateType(Constants.INDIVIDUAL_RECRUITER);
		
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			candidate.setEmail(jsonObj.getString("email"));
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		candidateService.addCandidate(candidate);
		candidateService.saveOrUpdateCandidateElkDocument(candidate);
		loginWithSocialInviter(user, userRole, orgLocations);
		
	}
	private byte[] fetchRemoteFile(String location) throws Exception {
		  URL url = new URL(location);
		  InputStream is = null;
		  byte[] bytes = null;
		  try {
		    is = url.openStream ();
		    bytes = IOUtils.toByteArray(is);
		  } catch (IOException e) {
		    //handle errors
		  }
		  finally {
		    if (is != null) is.close();
		  }
		  return bytes;
		}
	
	 private void loginWithSocialInviter(User user,UserRole userRole,OrgLocations orgLocations) throws Exception{
		 List<SourceleadActions> actionsList = sourceleadActionsService.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(userRole.getRoles().getId());
		  List<GrantedAuthority> authorities = buildUserAuthority(actionsList); 
		  try {
			  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
   			  userRole.setUserState(Status.ACTIVE);
   			  userRoleService.addUserRole(userRole);

   			  /*
                  * Update USER DB data as ElasticSearch Documents
                  */
                 Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
                 if(null != employee){
                     userService.saveOrUpdateUserElkDocument(userRole,employee,userRole.getCreatedBy());
                 }
   		  }
		   } catch (Exception e) {
			  e.printStackTrace();
		   }
		  SecurityContextHolder.clearContext();
		  SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), authorities));
	 }
	 private List<GrantedAuthority> buildUserAuthority(List<SourceleadActions> actionsList) {

    	 Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
       
    	if(actionsList != null && !actionsList.isEmpty()){
    		// Build user's authorities
    		for (SourceleadActions sourceleadActions : actionsList) {
    			setAuths.add(new SimpleGrantedAuthority(sourceleadActions.getActionRole()));
    		}
    	}
    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
    	return Result;
    }

}
