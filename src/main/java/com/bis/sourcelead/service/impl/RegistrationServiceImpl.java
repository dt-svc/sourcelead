package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Constants;
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
	private ProjectTasksService projectTasksService;
	@Override
	public void addRegistration(Registration registration) throws Exception {
		
		//For Saving of Org Details
		Org org = getOrgDetails(registration);
		try {
			orgService.addOrganization(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For Saving of User Details
		User user = getUserDetails(registration);
		try {
			userService.addUser(user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		org.setUser(user);
		org.setCreatedBy(user.getUserCode());
		
		try {
			orgService.updateOrg(org);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For saving of orgLocation Details which location has choose in the registration
		OrgLocations orgLocations = getOrgLocationsDetails(registration, org, user);
		try {
			orgLocationService.addOrgLocations(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For saving the User Role (At registration user will have Admin Role)
		UserRole userRole = getUserRoleDetails(user,orgLocations);
		try {
			userRoleService.addUserRole(userRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For saving Employee details (Every User is an Employee)
		Employees employee = getEmployeeDetails(user);
		try {
			employeesService.addEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		saveProject(registration, orgLocations, user, employee);
		
	}
	
	private Org getOrgDetails(Registration registration){
		Org org = new Org();
		org.setOrgName(registration.getCompanyName());
		org.setWebSite(convertWebsite(registration.getWebsite()));
		org.setOrgType("Company");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		org.setCreatedDate(new Date());
		return org;
	}
	
	private User getUserDetails(Registration registration){
		User user = new User();
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setUserName(registration.getEmail());
		user.setUserState(Status.ACTIVE);
		user.setUserType("Admin");
		user.setPassword(sourceleadPasswordEncoder.encode(registration.getPassword()));
		user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
		user.setCreatedBy(user.getUserCode());
		user.setCreatedDate(new Date());
		return user;
	}
	
	
	 public Projects  saveProject(Registration registration,OrgLocations orgLocations,User approverUser,Employees employee) throws Exception {
		 			 	
	    	Projects projects = new Projects();
	    	projects.setProjectSequeneNumber(registration.getCompanyName()+"_Project01");
	    	projects.setProjectName(registration.getCompanyName()+"_Project");
	    	projects.setDescription(registration.getCompanyName()+"_Project");
	    	projects.setStartDate(new Date());
	    	
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
		    	pah1.setLevel(1);
		    	try {
					 projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	ProjectMembers pm = new ProjectMembers();
				pm.setEmployee(employee);
				pm.setProjects(projects);
				pm.setProjectAssignedDate(projects.getStartDate());
				pm.setCreatedDate(new Date());
				pm.setUpdatedDate(new Date());
				projectMembersService.saveProjectMember(pm);
			}
			commonUtil.storeSourceleadTasks(approverUser, updatedProjects);
			return updatedProjects;
	}
	 
	 
	private OrgLocations getOrgLocationsDetails(Registration registration,Org org,User user ){
		
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
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setOrgLocations(orgLocations);
		Roles roles = null;
		try {
			roles = rolesService.getRolesByRoleName("ROLE_ADMIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		userRole.setIsPrimary(1);
		userRole.setCreatedDate(new Date());
		userRole.setCreatedBy(user.getUserCode());
		return userRole;
		
	}
	
	private Employees getEmployeeDetails(User user){
		
		Employees employees = new Employees();
		employees.setUser(user);
		employees.setCreatedBy(user.getUserCode());
		employees.setCreatedDate(new Date());
		return employees;
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
	public void sendEmail(Registration registration) throws Exception {
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
		 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"'>Login</a>");
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ORG_REGISTRATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = null;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		
	}
	
	@Override
	public void sendResetPasswordEmail(User user, String verificationCode) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("verificationCode",verificationCode);
		 globalVarsMap.put("resetPasswordURL","<a href='"+sourceleadUrl+"/resetPassword"+"'>Reset Password</a>");
			
		//setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.FORGOT_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
		
		//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = null;
		
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		
	}
	
	
	@Override
	public void sendUpdatePasswordEmail(User user) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
			
		//setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATE_PASSWORD,Constants.ENTITY_TYPE_EMAIL);
	
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = null;
		 
		//construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		
		
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

}
