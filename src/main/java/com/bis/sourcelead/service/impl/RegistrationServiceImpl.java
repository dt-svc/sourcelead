package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserRegistrationDTO;
import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionPriceService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRequestService;
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
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.util.Calendar;

@Service
public class RegistrationServiceImpl implements RegistrationService{

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
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
	 
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
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
	
	@Autowired
	MasterCandidateService masterCandidateService;
	
	@Autowired
	private ExpensesCategoryService expensesCategoryService;
	
	@Autowired
	private ExpensesCategoryDao expensesCategoryDao;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	OrgRequestService orgRequestService;
	
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Autowired
	LookUpDataService lookupDataService;
	
	@Autowired
	OrgLicenseSubscriptionPriceService orgLicenseSubscriptionPriceService;
	
	@Autowired
	TabsDao tabsDao;
	 
	@Autowired
	OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioConfigurationService;
	
	protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
	}
	
	
	@Override
	public OrgLocationDTO addCompanyRegistration(Registration registration, User loginUser, Long orgLocationsId,
			CandidateDTO candidateDTO) throws Exception {
		
		OrgLocationDTO orgLocationDTO = new OrgLocationDTO();
		
		Org org = getOrgDetails(registration);
		try {
			orgService.addOrganization(org);
			orgLocationDTO.setOrgId(org.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String password = "";
		User user = getUserDetails(registration,password);
		userService.addUser(user);
		OrgLocations orgLocations = getOrgLocationsDetails(registration, org, user);
		orgLocations.setRegistrationStatus("Draft");
		Timezone timeZone = timeZoneService.getTimezone(Long.parseLong(registration.getTimeZone()));
		orgLocations.setTimezone(timeZone);
		try {
			orgLocationService.updateOrgLocations(orgLocations);
			orgLocationDTO.setId(orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		OrgLicenseSubscription orgLicenseSubscription =  getOrgLicenseSubscriptionDetails(registration, org, user,orgLocations,loginUser);
		try {
			orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
			orgLocationDTO.setOrgLinceseSubscriptionId(orgLicenseSubscription.getId());
			OrgLicenseSubscriptionPrice orgLicenseSubscriptionPrice = orgLicenseSubscriptionPriceService.getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(Long.parseLong(registration.getOrgEnrollment()), Long.parseLong(registration.getUserSubscription()));
			   if(orgLicenseSubscriptionPrice != null) {
				   orgLocationDTO.setSubscriptionRate(orgLicenseSubscriptionPrice.getSubscriptionRate());
			   }else {
				   orgLocationDTO.setSubscriptionRate(0d);
			   }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orgLocationDTO;
	}
	
	
	@Override
	public void addRegistration(Registration registration,User loginUser,Long orgLocationsId,CandidateDTO candidateDTO) throws Exception {
		
		Org org = null;
		//For Saving of Org Details
		
		if(isNullOrEmpty(registration.getRegisteredOrgId())){
			org = getOrgDetails(registration);
			try {
				orgService.addOrganization(org);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			org = orgService.findOrgByID(Long.valueOf(registration.getRegisteredOrgId()));
		}
		
		
		//For Saving of User Details
		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
		User user = null;
		UserRole uRole = null;
		user = userService.findByUserName(registration.getEmail());
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
			if("Newly Registered".equalsIgnoreCase(user.getRegistrationType()))
				sendActivationEmail(user,null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(isNullOrEmpty(registration.getRegisteredOrgId())){
			org.setUser(user);
			try {
				orgService.updateOrg(org);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//For saving of orgLocation Details which location has choose in the registration
		OrgLocations orgLocations = getOrgLocationsDetails(registration, org, user);
		orgLocations.setRegistrationStatus("Registered");
		Timezone timeZone = timeZoneService.getTimezone(Long.parseLong(registration.getTimeZone()));
		orgLocations.setTimezone(timeZone);
		try {
			orgLocationService.addOrgLocations(orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<LookUpData> lookUpExpCatList = lookUpDataService.getLookUpDataByCategory(Constants.EXPENSESCATEGORY);
	    for(LookUpData lookUpData:lookUpExpCatList){
		    ExpensesCategory expensesCategory = new ExpensesCategory();
		    expensesCategory.setExpensesName(lookUpData.getName());
		    expensesCategory.setAmountType("Flat");
		    expensesCategory.setOrgLocationId(orgLocations);
		    expensesCategory.setStatus(Constants.MEMBER_ACTIVE);
			expensesCategory.setCreatedBy(user.getUserCode());
			expensesCategory.setCreatedDate(commonUtil.currentDate());
			expensesCategory.setUpdatedBy(user.getUserCode());
			expensesCategory.setUpdatedDate(commonUtil.currentDate());
			expensesCategoryDao.storeExpenseCategory(expensesCategory); 
	    }
		
		saveOrgRoleHierarchy(orgLocations,user);
		saveOrgBasedRoles(org,orgLocations,user);
		Shift shift = commonUtil.saveDefaultGenaeralShift(orgLocations,user,Constants.GENERAL_SHIFT);
		//For saving the User Role (At registration user will have Admin Role)
		UserRole userRole = getUserRoleDetails(user,orgLocations);
		try {
			userRole.setShift(shift);
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
		
		 MasterCandidate masterCandidate = null; 
		 masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
		 if(null == masterCandidate){
			 masterCandidate = getMasterCandidateDetails(registration,user);
		 }
		
		try {
			masterCandidateService.saveMasterCandidate(masterCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		Candidate candidate = getCandidateDetails(user,orgLocations,masterCandidate);
		try {
			Candidate savedCandidate = candidateService.addCandidate(candidate);
			candidateService.getCandidatePercentage(savedCandidate, orgLocations);
			candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		saveDefaultSubmissionTemplate(org,orgLocations,user);
		saveDefaultInterviewFeedbackTemplate(org,orgLocations,user);
		
		commonUtil.saveDefaultContractTemplate(org,orgLocations,user," Default Client MSA Template",Constants.CLIENT_MSA);
		commonUtil.saveDefaultContractTemplate(org,orgLocations,user," Default Vendor MSA Template",Constants.VENDOR_MSA);
		commonUtil.saveDefaultContractTemplate(org,orgLocations,user," Default Client SOW Template",Constants.Client_SOW);
		commonUtil.saveDefaultContractTemplate(org,orgLocations,user," Default Vendor SOW Template",Constants.Vendor_SOW);
		commonUtil.saveDefaultContractTemplate(org,orgLocations,user," Default NDA Template",Constants.NDA);
		commonUtil.saveDefaultPayslipTemplate(orgLocations,user);

		commonUtil.saveDefaultEmployeeVerificationTemplate(org,orgLocations,user,"Default Employee Verification Template");
//		saveDefaultContractTemplate(org,orgLocations,user," Default NCA Template","NSA");
		
		

		
		saveOrgLocationDefaultProject(registration, orgLocations, user);
		commonUtil.storeVendorOnboardDefaultTemplate(user, orgLocations);
		
		userService.saveOrUpdateUserElkDocument(userRole,employee,"sladmin");
		userService.sendEmail(user, loginUser, password,orgLocations.getId());
	
	}
	
	private Candidate getCandidateDetails(User user, OrgLocations orgLocations,MasterCandidate masterCandidate){
		
		Candidate candidate = null;
		try {
			candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(candidate == null){
			candidate = new Candidate();
			candidate.setConsultantUser(user);
			candidate.setMasterCandidateId(masterCandidate);
			candidate.setOrgLocationId(orgLocations);
			candidate.setCandidateType(Constants.CANDIDATE);
			candidate.setFirstName(user.getFirstName());
			candidate.setLastName(user.getLastName());
			candidate.setCountry(orgLocations.getCountry());
			candidate.setEmail(user.getEmail());
			candidate.setIsAssociate(Constants.YES);
			candidate.setStatus(Constants.RECORD_ACTIVE);
			candidate.setCreatedBy(user.getUserCode());
			candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidate.setCreatedDate(commonUtil.currentDate());
		}else{
			candidate.setConsultantUser(user);
			candidate.setMasterCandidateId(masterCandidate);
		}
		
		return candidate;
	}
	
	private MasterCandidate getMasterCandidateDetails(Registration registration,User user) {
		MasterCandidate masterCandidate = new MasterCandidate();
		masterCandidate.setFirstName(registration.getFirstName());
		masterCandidate.setLastName(registration.getLastName());
		masterCandidate.setEmail(registration.getEmail());
		masterCandidate.setPhoneNumber(registration.getMobilePhone());
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		return masterCandidate;
	}

	
	
	private Org getOrgDetails(Registration registration){
		
		Org org = null;
		if(StringUtils.isNotBlank(registration.getRegisteredOrgId())) {
			try {
				org = orgService.findOrgByID(Long.parseLong(registration.getRegisteredOrgId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			 org = new Org();
		}
		
		org.setOrgName(registration.getCompanyName());
		org.setWebSite(convertWebsite(registration.getWebsite()));
		org.setOrgType("Company");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		org.setCreatedBy("sladmin");
		org.setCreatedDate(new Date());
		return org;
	}
	
	private User getUserDetails(Registration registration,String password){
		User user = null;
		user = userService.findByUserName(registration.getEmail());
		if(user == null) {
			user = new User();
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			user.setIsVerified(Constants.NO);
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
			user.setUrlSentTime(new Date());
			user.setLoginSecurity(Constants.NO);
			user.setRegistrationType("Newly Registered");
		}
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setUserName(registration.getEmail());
		if(StringUtils.isNotBlank(password)) {
			user.setPassword(sourceleadPasswordEncoder.encode(password));
		}
		
		user.setMobilePhone(registration.getMobilePhone());
		user.setCreatedBy(user.getUserCode());
		user.setCreatedDate(new Date());
		
		
		return user;
	}
	
	
	 public Projects  saveOrgLocationDefaultProject(Registration registration,OrgLocations orgLocations,User approverUser) throws Exception {
		 			 	
	    	Projects projects = new Projects();
	    	StringBuilder defaultProjectName = new  StringBuilder();
	    	projects.setProjectName(defaultProjectName.append(registration.getCompanyName().trim()).append(" ").append("Default Project").toString());
	    	StringBuilder defaultProjectNameDesc = new  StringBuilder();

	    	projects.setDescription(defaultProjectNameDesc.append(registration.getCompanyName().trim()).append(" ").append("Default Project").toString());
	    	projects.setStartDate(new Date());
	    	
	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -2);
	    	Date startDate = cal.getTime();
	    	Date projStartDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(),startDate);
	    	
	    	projects.setStartDate(projStartDate);

	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    	projects.setTimesheetFrequency(Constants.MONTHLY);
	    	projects.setProjectStatus(Project_Status.INPROGRESS);
	    	projects.setIsPrimary(1);
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	projects.setIsDefaultProject(Constants.YES);
	    	projects.setOrgLocationsId(orgLocations);
	    	Set<ProjectClients> projectClientsList = getProjectClientsForProject(projects,orgLocations,approverUser);
	    	projects.setProjectClients(projectClientsList);
			projects.setProjectUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			projects.setProjectShortCode("DFP");
			projects.setProjectMode("Finished");
			projects.setCreatedDate(new Date());
			projects.setUpdatedDate(new Date());
			projects.setIsProjectForTimeSheets(Constants.YES);
			projects.setIsProjectForExpenses(Constants.YES);
			projects.setProjectApprovalType(Constants.PARALLEL);
			projects.setExpenseApprovalType(Constants.PARALLEL);
			
			Countries countires = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(countires != null) {
				projects.setCurrency(countires.getCurrency());
			}
			
			Projects updatedProjects = projectsService.saveProject(projects);
			projectsService.saveOrUpdateProjectElkDocument(updatedProjects);
			commonUtil.storeSourceleadTasks(approverUser, updatedProjects);
			return updatedProjects;
	}
	 
	 
		private Set<ProjectClients> getProjectClientsForProject(Projects projects,OrgLocations orglocations,User approverUser) throws Exception {
			Set<ProjectClients> projectClientsSet = new HashSet<>();
			
			ProjectClients projectClients = new ProjectClients();
			projectClients.setProjectId(projects);
			projectClients.setOrgLocationsId(orglocations);
			projectClients.setOrgId(orglocations.getOrg());
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setStatus(1);
			projectClients.setLevel(1);
			projectClients.setIsEndClient(Constants.YES);
			projectClients.setCreatedDate(new Date());
			projectClients.setUpdatedDate(new Date());
			projectClients.setCreatedBy(approverUser);
			projectClients.setUpdatedBy(approverUser);
			
			//prepare ProjectApproverHierarchy
			Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
			ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
			pah.setProjectId(projects);
			pah.setProjectClientsId(projectClients);
			pah.setApproverUserId(approverUser);
			pah.setCreatedDate(new Date());
			pah.setCreatedBy(approverUser.getUserCode());
			pah.setLevel(1);
			pah.setStatus("1");
			projectApproverHierarchyList.add(pah);
			projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
			
			
			//prepare Project Expense Approver Hierarchy
			Set<ExpenseProjectApproverHierarchy> projectExpenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
			ExpenseProjectApproverHierarchy eah = new ExpenseProjectApproverHierarchy();
			eah.setProjectId(projects);
			eah.setProjectClientsId(projectClients);
			eah.setApproverUserId(approverUser);
			eah.setLevel(1);
			eah.setStatus("1");
			eah.setCreatedDate(new Date());
			eah.setCreatedBy(approverUser.getUserCode());
			projectExpenseApproverHierarchyList.add(eah);
			projectClients.setExpenseProjectApproverHierarchyList(projectExpenseApproverHierarchyList);
			
	    	projectClientsSet.add(projectClients);
			return projectClientsSet;
		}
	 
	 
	private OrgLocations getOrgLocationsDetails(Registration registration,Org org,User user) throws Exception{
		
		OrgLocations orgLocations = null;
		
		if(StringUtils.isNotBlank(registration.getOrgLocationId())) {
			orgLocations = orgLocationService.getOrgLocations(Long.parseLong(registration.getOrgLocationId()));
		}else {
			orgLocations = new OrgLocations();
			orgLocations.setLocationCode(SourceLeadRandomCodeHelper.generateRandomOrgLocationCode());
		}
		orgLocations.setCountry(registration.getCountry());
		orgLocations.setState(registration.getState());
		orgLocations.setCity(registration.getCity());
		orgLocations.setOrgLocationAddress(registration.getAddress());
		orgLocations.setLocation(registration.getLocation());
		orgLocations.setZipCode(registration.getZipCode());
		orgLocations.setOrg(org);
		orgLocations.setUser(user);
		orgLocations.setDateFormat(registration.getDateFormat());
		orgLocations.setStatus(Status.ACTIVE);
		orgLocations.setProfilePercentage(Constants.PROFILE_PERCENTAGE);

		orgLocations.setLoginSecurity(Constants.NO);
		Countries countries = countriesService.getCountriesByCountryName(registration.getCountry());
		orgLocations.setCreatedBy(user.getUserCode());
		orgLocations.setCreatedDate(new Date());
		orgLocations.setOrgLocationName(registration.getCompanyName().trim());
		orgLocations.setWebsite(convertWebsite(registration.getWebsite()));
		orgLocations.setIsCompanyOnboarded(Constants.NO);
		
	    if(StringUtils.isNotBlank(registration.getDba())){
        	orgLocations.setDba(registration.getDba());
        }
        
        if(registration.getGeneratedUserId() != null &&  !registration.getGeneratedUserId().isEmpty()){
        	orgLocations.setGeneratedUserId(registration.getGeneratedUserId());
        }
        
        if(registration.getAverageWorkingHours() != null) {
        	orgLocations.setAverageWorkingHours(registration.getAverageWorkingHours());
        }
		
		if(isNullOrEmpty(registration.getRegisteredOrgId())){
			orgLocations.setIsPrimary(Constants.YES);
		}
		
		orgLocations.setModulesId("2,3,4");
		
		return orgLocations;
	}
	
	
	private OrgLicenseSubscription getOrgLicenseSubscriptionDetails(Registration registration,Org org,User user,OrgLocations orgLocations,User loginUser) throws Exception{
		OrgLicenseSubscription orgLicenseSubscription = null;
		if(StringUtils.isNotBlank(registration.getOrgLinceseSubscriptionId())) {
			orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionById(Long.parseLong(registration.getOrgLinceseSubscriptionId()));
		}else {
			orgLicenseSubscription = new OrgLicenseSubscription();
			orgLicenseSubscription.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		orgLicenseSubscription.setOrg(org);
		orgLicenseSubscription.setCountry(registration.getCountry());
		
		OrgLicenseSubscriptionTypes subscriptionType = new OrgLicenseSubscriptionTypes();
		subscriptionType.setId(Long.parseLong(registration.getOrgEnrollment()));
		orgLicenseSubscription.setSubscriptionType(subscriptionType);
		
		OrgLicenseSubscriptionTypes userSubscriptionType = new OrgLicenseSubscriptionTypes();
		userSubscriptionType.setId(Long.parseLong(registration.getUserSubscription()));
		orgLicenseSubscription.setUserSubscriptionType(userSubscriptionType);
		
		OrgLicenseSubscriptionTypes storageSubscriptionType = new OrgLicenseSubscriptionTypes();
		storageSubscriptionType.setId(Long.parseLong(registration.getDataStorage()));
		orgLicenseSubscription.setStorageSubscriptionType(storageSubscriptionType);
		
		orgLicenseSubscription.setCreatedBy(loginUser);
		orgLicenseSubscription.setCreatedDate(new Date());
		orgLicenseSubscription.setUpdatedBy(loginUser);
		orgLicenseSubscription.setUpdatedDate(new Date());
		orgLicenseSubscription.setStatus(1);
		
		Date subscriptionDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),registration.getSubscriptionDate());
		subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLocations.getTimezone().getTimeZoneId(), subscriptionDate, orgLocations.getDateFormat());
		orgLicenseSubscription.setFromDate(subscriptionDate);
		
		orgLicenseSubscription.setTimezone(orgLocations.getTimezone());
		
		Calendar toDate = Calendar.getInstance();
		toDate.setTime(subscriptionDate);    
	    
		OrgLicenseSubscriptionTypes subscriptionTypes = orgLicenseSubscriptionTypesService.getOrgLicenseSubscriptionTypesById(Long.parseLong(registration.getOrgEnrollment()));
		
	    if(subscriptionTypes.getName().equalsIgnoreCase("Free Trail") || subscriptionTypes.getName().equalsIgnoreCase("1 Month")) {
	    	toDate.add(Calendar.MONTH, +1); 
	    }else if(subscriptionTypes.getName().equalsIgnoreCase("6 Months")) {
	    	toDate.add(Calendar.MONTH, +6); 
	    }else if(subscriptionTypes.getName().equalsIgnoreCase("1 Year")) {
	    	toDate.add(Calendar.YEAR, +1); 
	    }else if(subscriptionTypes.getName().equalsIgnoreCase("2 Years")) {
	    	toDate.add(Calendar.YEAR, +2); 
	    }
	    
	    Date subscriptionToDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(),toDate.getTime());
	    subscriptionToDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLocations.getTimezone().getTimeZoneId(), subscriptionToDate, orgLocations.getDateFormat());
		orgLicenseSubscription.setToDate(subscriptionToDate);
	    
		return orgLicenseSubscription;
	}
	
	private UserRole getUserRoleDetails(User user,OrgLocations orgLocations ){
		
		UserRole primaryUserRole = null;
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setIsMailSent(Status.Y);
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
		
		OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(org.getId(), orgLocations.getCountry());
				
				List<Long> tabsIds = new ArrayList<>();
				String[] registeredTabs = orgLicenseSubscription.getSubscriptionTabs().split(",");
				
				for(int i=0; i<registeredTabs.length; i++) {
					List<Tabs> childTabsList = tabsDao.getClildTabsListByTabId(Long.valueOf(registeredTabs[i]));
					if(childTabsList.size() > 0) {
						for(Tabs childTabs : childTabsList) {
							tabsIds.add(childTabs.getId());
						}
					}else {
						tabsIds.add(Long.valueOf(registeredTabs[i]));
					}
				}
				
				List<SourceleadRoles> rolesList = sourceleadRolesService.getSourceleadRoles();
				List<SourceleadActions> sourceLeadActionsList =  sourceleadActionsService.getAllSourceleadActionsByTabsList(tabsIds);
				Countries countires = countriesService.getCountriesByCountryName(orgLocations.getCountry()); 
				
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
							for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
				    			 roleActonsList.add(Long.parseLong(s));
				        	 }
						}
						
						for(SourceleadActions actions : sourceLeadActionsList){
							if(roleActonsList.contains(actions.getId())){
								if(actions.getLocationCode() == null) {
									strbul.append(actions.getId()).append(",");
								}else if(actions.getLocationCode() != null) {
									if(actions.getLocationCode().equalsIgnoreCase(countires.getCode2())) {
										strbul.append(actions.getId()).append(",");
									}
								}
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
		 submissionTemplate.setTemplateName(templateStringBuilder.append(orgLocations.getOrgLocationName()).append(" Default Template").toString());
		 submissionTemplate.setDescription(templateStringBuilder.append(orgLocations.getOrgLocationName()).append(" Default Template").toString());
		 submissionTemplate.setOrgLocationId(orgLocations);
		 submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
		 submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
		 submissionTemplate.setIsDefault(Constants.YES);
		 submissionTemplate.setUpdatedBy(user.getUserCode());
		 submissionTemplate.setTemplateType("Submission");
		 submissionTemplate.setUpdatedDate(new Date());
		 submissionTemplate.setCreatedBy(user.getUserCode());
		 submissionTemplate.setCreatedDate(new Date());
		 submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		 submissionTemplateService.addSubmissionTemplate(submissionTemplate);
		 
	 }
	 
	 
	 
	 public void  saveDefaultInterviewFeedbackTemplate(Org org,OrgLocations orgLocations,User user) throws Exception {
		 SubmissionTemplate sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
		 
		 StringBuilder templateStringBuilder=new StringBuilder();
		 SubmissionTemplate submissionTemplate = new SubmissionTemplate();
		 submissionTemplate.setTemplateName(templateStringBuilder.append(orgLocations.getOrgLocationName()).append(" ").append("Interview Feedback Template").toString());
		 submissionTemplate.setDescription(templateStringBuilder.append(orgLocations.getOrgLocationName()).append(" ").append("Interview Feedback Template").toString());
		 submissionTemplate.setOrgLocationId(orgLocations);
		 submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
		 submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
		 submissionTemplate.setUpdatedBy(user.getUserCode());
		 submissionTemplate.setTemplateType("Feedback");
		 submissionTemplate.setUpdatedDate(new Date());
		 submissionTemplate.setIsDefault(Constants.YES);
		 submissionTemplate.setCreatedBy(user.getUserCode());
		 submissionTemplate.setCreatedDate(new Date());
		 submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
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
		 emailMessage.setFromEmail("info@sourcelead.com");
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 StringBuilder loginURLStringBuilder=new StringBuilder();
		 globalVarsMap.put("loginURL",loginURLStringBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
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
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,locationsId,iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	@Override
	public void sendResetPasswordEmail(User user,Long locationsId, String verificationCode) throws Exception {
		
		try {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationsId);
			 if(userRole == null){
			    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			 }
			OrgLocations locations= orgLocationService.getOrgLocations(locationsId);
			
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail(user.getEmail());
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 emailMessage.setFromName("SourceLead");
			 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("FirstName", emailMessage.getFirstName());
			 globalVarsMap.put("LastName",emailMessage.getLastName());
			 globalVarsMap.put("verificationCode",verificationCode);
			 StringBuilder resetPasswordURLStringBuilder=new StringBuilder();
			 String mailUrl = sourceleadUrl.replace("/app", "");
			 globalVarsMap.put("ResetPasswordURL",resetPasswordURLStringBuilder.append("<a href=").append(mailUrl).append("/resetPassword").append(" style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Reset Password</div></a>").toString());
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.FORGOT_PASSWORD,new ArrayList<EmailAttachment>());
		}catch(Exception e) {
			
		}
		
		

		
	}
	
	
	@Override
	public void sendUpdatePasswordEmail(User user,Long locationsId) throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationsId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		OrgLocations locations= orgLocationService.getOrgLocations(locationsId);
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setFromName("SourceLead");
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("FirstName", emailMessage.getFirstName());
		 globalVarsMap.put("LastName",emailMessage.getLastName());
		 globalVarsMap.put("userName",user.getEmail());
		 globalVarsMap.put("updatedDate",user.getUpdatedDate().toString());
		 StringBuilder sbLink = new StringBuilder();
		 globalVarsMap.put("forgotPasswordLink",sbLink.append("<a href=").append(sourceleadUrl).append("/forgotPassword").append(" target='_blank' style='outline:none; border:none;'>Forgot Password</a>").toString());
		 StringBuilder linkBuilder = new StringBuilder();
		 globalVarsMap.put("supportLink",linkBuilder.append("<a href='support@sourcelead.com' target='_blank' style='outline:none; border:none;'>support@sourcelead.com</a>").toString());
		 StringBuilder sourceLink = new StringBuilder();
		 globalVarsMap.put("sourceleadLink",sourceLink.append("<a href='http://www.sourcelead.com/' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/</a>").toString());
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.UPDATE_PASSWORD,new ArrayList<EmailAttachment>());

		
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
	public void addUserBySourcelead(User user, AddUserDTO addUserDTO,CandidateDTO candidateDTO) throws Exception {
		
		User orgUser = userService.findByUserName(addUserDTO.getEmail());
		UserRole userRole = new UserRole();
		String password = null;
		if(null == orgUser){
			orgUser = new User();
			orgUser.setFirstName(addUserDTO.getFirstName());
			if(StringUtils.isNotBlank(addUserDTO.getLastName())){
				orgUser.setLastName(addUserDTO.getLastName());
			}else{
				orgUser.setLastName("");
			}
			orgUser.setEmail(addUserDTO.getEmail());
			orgUser.setCreatedBy(user.getUserCode());
			orgUser.setCreatedDate(new Date());
			orgUser.setUpdatedBy(user.getUserCode());
			orgUser.setUserName(addUserDTO.getEmail());
			password = SourceLeadRandomCodeHelper.generateRandomPassword();
			orgUser.setPassword(sourceleadPasswordEncoder.encode(password));
			
			orgUser.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			orgUser.setUpdatedDate(new Date());
			orgUser.setVerificationUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			orgUser.setIsVerified(Constants.NO);
			orgUser.setUrlSentTime(new Date());
			orgUser.setLoginSecurity(Constants.NO);
			orgUser.setRegistrationType("Newly Registered");
			userService.addUser(orgUser);
			userRole.setIsPrimary(1);
			
		}else{
			userRole.setIsPrimary(0);
		}
		
		userRole.setIsMailSent(Status.Y);
		userRole.setUser(orgUser);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(addUserDTO.getRegisteredOrgLocationId()));
		userRole.setOrgLocations(orgLocations);
		Roles roles = rolesService.getRolesById(Long.valueOf(addUserDTO.getRoleTypeId()));
		userRole.setRoles(roles);
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(roles.getDisplayName());
		if(orgLocations.getUser() != null) {
			userRole.setResourceManager(orgLocations.getUser());
			userRole.setReportingManager(orgLocations.getUser());
		}
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
		
		 MasterCandidate masterCandidate = null; 
		 masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
		 if(null == masterCandidate){
		    masterCandidate = new MasterCandidate();
			masterCandidate.setFirstName(addUserDTO.getFirstName());
			if(StringUtils.isNotBlank(addUserDTO.getLastName())){
				masterCandidate.setLastName(addUserDTO.getLastName());
			}else{
				masterCandidate.setLastName("");
			}
			masterCandidate.setEmail(addUserDTO.getEmail());
			masterCandidate.setStatus(Constants.RECORD_ACTIVE);
			masterCandidate.setCreatedBy(user.getUserCode());
			masterCandidate.setCreatedDate(commonUtil.currentDate());
			masterCandidate.setUpdatedBy(user.getUserCode());
			masterCandidate.setUpdatedDate(commonUtil.currentDate());
		 }
		
		try {
			masterCandidateService.saveMasterCandidate(masterCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Candidate candidate = getCandidateDetails(orgUser,orgLocations,masterCandidate);
		try {
			Candidate savedCandidate = candidateService.addCandidate(candidate);
			candidateService.getCandidatePercentage(savedCandidate, orgLocations);
			candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		if("Newly Registered".equalsIgnoreCase(orgUser.getRegistrationType()))
	    	sendActivationEmail(orgUser,orgLocations);
		sendEmail(orgUser,user,password,orgLocations.getId(),addUserDTO.getRegistredOrgId());
	}
	
	
	public void sendEmail(User user, User loginUser, String password, Long locationId,String orgId) throws Exception {
		
		 UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), locationId);
		 if(userRole == null){
		    userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 }
		 OrgLocations locations= orgLocationService.getOrgLocations(locationId);
		 
		 boolean isUserActive = false;
		 isUserActive = userRoleService.isUserActive(user.getId());
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 StringBuilder builder=new StringBuilder();
		 emailMessage.setFirstName(builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());

		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setOrgLocationId(locationId);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("FirstName", emailMessage.getFirstName());
		// globalVarsMap.put("lastName",emailMessage.getLastName());
		/* globalVarsMap.put("administratorFirstName", loginUser.getFirstName());
		 globalVarsMap.put("administratorLastName",loginUser.getLastName());*/
		 StringBuilder stringbuilder=new StringBuilder();
		 if(loginUser.getFirstName() != null && loginUser.getLastName() !=""){
			 globalVarsMap.put("loginUser",stringbuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());	 
		 }else{
			 globalVarsMap.put("loginUser", user.getFirstName());	 
		 }
		 globalVarsMap.put("Role",userRole.getUserType());
		 globalVarsMap.put("orgName", locations.getOrgLocationName());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("LoginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("UserEmailId",user.getEmail());
		 if(isUserActive == false){
			 password = SourceLeadRandomCodeHelper.generateRandomPassword();
			 user.setPassword(sourceleadPasswordEncoder.encode(password));
			 userService.addUser(user);
			 globalVarsMap.put("Password",password);
		 }else{
			 globalVarsMap.put("Password","<font color='green'><b>Login with your existing Sourcelead password.</b></font>");
		 }
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.INVITE_USER,new ArrayList<EmailAttachment>());

		 
		
	}

	@Override
	public String saveProfessional(CrowdSourcingUserRegistrationDTO csuRegistrationDTO,String contactUUID) throws Exception {
		
		
		User user = null;
		user = userService.findByEmail(csuRegistrationDTO.getEmail());
		String result = "";
		
		if(null == user){
			user = new User();
			if(StringUtils.isNotBlank(csuRegistrationDTO.getFirstName())){
				user.setFirstName(csuRegistrationDTO.getFirstName());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getLastName())){
				user.setLastName(csuRegistrationDTO.getLastName());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getEmail())){
				user.setEmail(csuRegistrationDTO.getEmail());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getLocation())){
				user.setCountry(csuRegistrationDTO.getLocation());
				user.setProfessionalRegisterCountry(csuRegistrationDTO.getLocation());
			}
			
			user.setUserName(csuRegistrationDTO.getEmail());
			user.setPassword(sourceleadPasswordEncoder.encode(csuRegistrationDTO.getConfirmPassword()));
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			
			user.setUserType(Constants.PROFESSIONAL);
			user.setCreatedBy("sladmin");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
			user.setUrlSentTime(new Date());
			user.setIsVerified(Constants.NO);
			
			user.setRegistrationType("Newly Registered");
			userService.addUser(user);
			result = "success"+user.getId();
			
		}else{
			user.setRegistrationType("Existed User");
			user.setProfessionalRegisterCountry(csuRegistrationDTO.getLocation());
			userService.addUser(user);
			result = "login";
		}
		
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
		
		UserRole primaryUserRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		if(null == primaryUserRole){
			userRole.setIsPrimary(1);
		}else{
			userRole.setIsPrimary(0);
		}
		
//		userRole.setVerificationUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
//		userRole.setUrlSentTime(new Date());
//		userRole.setIsVerified(Constants.NO);
		
		userRole.setCreatedBy("sladmin");
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		userRole.setUserState(Status.NOT_LOGGEDIN);
		userRole.setUserType(Constants.PROFESSIONAL);
		userRole.setIsMailSent(Status.Y);
		userRoleService.addUserRole(userRole);
		
		Candidate candidate = new Candidate();
		candidate.setConsultantUser(user);
		candidate.setOrgId(org);
		candidate.setOrgLocationId(orgLocations);
		candidate.setCandidateType(Constants.PROFESSIONAL);
		candidate.setCountry(csuRegistrationDTO.getLocation());
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getFirstName())){
			candidate.setFirstName(csuRegistrationDTO.getFirstName());
		}
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getLastName())){
			candidate.setLastName(csuRegistrationDTO.getLastName());
		}
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getEmail())){
			candidate.setEmail(csuRegistrationDTO.getEmail());
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		MasterCandidate masterCandidate = null; 
		 CandidateDTO candidateDTO = new CandidateDTO();
		 candidateDTO.setEmail(csuRegistrationDTO.getEmail());
		 masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
		 if(null == masterCandidate){
			 masterCandidate = getMasterCandidateDetailsForProfessionalAndRecruiterLogins(candidate,user);
		 }
		
		try {
			masterCandidateService.saveMasterCandidate(masterCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		candidate.setMasterCandidateId(masterCandidate);
		
		
		candidateService.addCandidate(candidate);
		candidateService.getCandidatePercentage(candidate, orgLocations);
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
		
		if("Newly Registered".equalsIgnoreCase(user.getRegistrationType()))
			sendActivationEmail(user,orgLocations);
		else
			sendEmailToProfessional(user);
		
		return result;
	}
	
	
	public void sendActivationEmail(User user,OrgLocations orgLocations) throws Exception
	{
		StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 professionalName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 professionalName = sb.append(user.getFirstName()).toString();
		 }
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setToEmail(user.getEmail());
		 if(orgLocations != null) {
			 emailMessage.setFromName(orgLocations.getOrgName());
			 EmailConfigurationFromMail confFromMail = emailScenarioConfigurationService.getConfFromMailByStatus(orgLocations.getId());
			 if(confFromMail != null) {
				 emailMessage.setFromEmail(confFromMail.getEmail());
			 }else {
				 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
			 }
		 }else {
			 emailMessage.setFromName("Sourcelead Admin");
			 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 }
	
		 
		 emailMessage.setCreatedBy("sladmin");
		 
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("registerName", professionalName);
		 StringBuilder verifylink = new StringBuilder();
		 
		 globalVarsMap.put("verifyLink",verifylink.append("<a href='").append(sourceleadUrl).append("/ws/activateEmail?&token=").append(user.getVerificationUUID()).append("'>").append(sourceleadUrl).append("/activateEmail?&token=").append(user.getVerificationUUID()).append("</a>").toString()); 

		 //setting the Recipient information
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.ACTIVATE_EMAIL, null);
		
		
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
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("sladmin");
		 emailMessage.setFirstName(professionalName);
		 //emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("loginUserName", user.getUserName());
		 StringBuilder loginBuilder = new StringBuilder();
		 

		

			 globalVarsMap.put("resetLink",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		 StringBuilder linkBuilder = new StringBuilder();
				 globalVarsMap.put("supportLink",linkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
				
				 StringBuilder fbLinkBuilder = new StringBuilder();
				 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
				
				 StringBuilder linkedInLinkBuilder = new StringBuilder();
				 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
				
				 StringBuilder twitterLinkBuilder = new StringBuilder();
				 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
				
				 StringBuilder googleLinkBuilder = new StringBuilder();
				 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_PROFESSIONAL,Constants.ENTITY_TYPE_EMAIL);
		
		
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

	@Override
	public String saveIndividualRecruiter(CrowdSourcingUserRegistrationDTO csuRegistrationDTO) throws Exception {
		
		User user = null;
		user = userService.findByEmail(csuRegistrationDTO.getEmail());
		String result = "";
		
		if(null == user){
			user = new User();
			if(StringUtils.isNotBlank(csuRegistrationDTO.getFirstName())){
				user.setFirstName(csuRegistrationDTO.getFirstName());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getLastName())){
				user.setLastName(csuRegistrationDTO.getLastName());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getEmail())){
				user.setEmail(csuRegistrationDTO.getEmail());
			}
			
			if(StringUtils.isNotBlank(csuRegistrationDTO.getLocation())){
				user.setCountry(csuRegistrationDTO.getLocation());
				user.setRecruiterRegisterCountry(csuRegistrationDTO.getLocation());
			}
			
			//prepareRegisterOptionalServicesToAllForms(jsonObj,user);
					
			user.setUserName(csuRegistrationDTO.getEmail());
			user.setPassword(sourceleadPasswordEncoder.encode(csuRegistrationDTO.getConfirmPassword()));
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			user.setUserType(Constants.INDIVIDUAL_RECRUITER);
			user.setCreatedBy("sladmin");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
			user.setUrlSentTime(new Date());
			user.setIsVerified(Constants.NO);
			
			user.setRegistrationType("Newly Registered");
			userService.addUser(user);
			result = "success"+user.getId();
		}else{
			user.setRegistrationType("Existed User");
			user.setRecruiterRegisterCountry(csuRegistrationDTO.getLocation());
			userService.addUser(user);
			result = "login";
		}
		
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
		userRole.setIsMailSent(Status.Y);
		
		UserRole primaryUserRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		if(null == primaryUserRole){
			userRole.setIsPrimary(1);
		}else{
			userRole.setIsPrimary(0);
		}
		
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
		candidate.setCountry(csuRegistrationDTO.getLocation());
		candidate.setCandidateType(Constants.INDIVIDUAL_RECRUITER);
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getFirstName())){
			candidate.setFirstName(csuRegistrationDTO.getFirstName());
		}
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getLastName())){
			candidate.setLastName(csuRegistrationDTO.getLastName());
		}
		
		if(StringUtils.isNotBlank(csuRegistrationDTO.getEmail())){
			candidate.setEmail(csuRegistrationDTO.getEmail());
		}
		
		candidate.setStatus(Constants.CANDIDATE_ACTIVE);
		candidate.setCreatedBy("sladmin");
		candidate.setCreatedDate(new Date());
		candidate.setUpdatedDate(new Date());
		candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		MasterCandidate masterCandidate = null; 
		 CandidateDTO candidateDTO = new CandidateDTO();
		 candidateDTO.setEmail(csuRegistrationDTO.getEmail());
		 masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
		 if(null == masterCandidate){
			 masterCandidate = getMasterCandidateDetailsForProfessionalAndRecruiterLogins(candidate,user);
		 }
		
		try {
			masterCandidateService.saveMasterCandidate(masterCandidate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		candidate.setMasterCandidateId(masterCandidate);
		
		
		candidateService.addCandidate(candidate);
		candidateService.getCandidatePercentage(candidate, orgLocations);
		candidateService.saveOrUpdateCandidateElkDocument(candidate);
		
		if("Newly Registered".equalsIgnoreCase(user.getRegistrationType()))
			sendActivationEmail(user,orgLocations);
		else
			sendEmailToIndividualRecruiter(user);
		
		return result;
		
	}
	
	public void prepareRegisterOptionalServicesToAllForms(JSONObject jsonObj, User user) throws JSONException{
		if(jsonObj.has("wantedtobeList") && !isNullOrEmpty(jsonObj.getString("wantedtobeList")) && jsonObj.has("Wouldliketo") && !isNullOrEmpty(jsonObj.getString("Wouldliketo"))){
			String wantedtobeListids = jsonObj.getString("wantedtobeList");
			String wanted = replacejsonToString(wantedtobeListids);
		    String WouldliketoString  = jsonObj.getString("Wouldliketo");
		    String Wouldlike = replacejsonToString(WouldliketoString);
		    user.setRegisterOptionalServiceId(wanted+","+Wouldlike);
			}else if(jsonObj.has("wantedtobeList") && !isNullOrEmpty(jsonObj.getString("wantedtobeList").toString())){
				String wantedtobeListids = jsonObj.getString("wantedtobeList");
				String wanted = replacejsonToString(wantedtobeListids);
			user.setRegisterOptionalServiceId(wanted);
			}else if(jsonObj.has("Wouldliketo") && !isNullOrEmpty(jsonObj.getString("Wouldliketo"))){
				String WouldliketoString  = jsonObj.getString("Wouldliketo");
			    String Wouldlike = replacejsonToString(WouldliketoString);
				user.setRegisterOptionalServiceId(Wouldlike);
		   }
	}
	
	
	public String replacejsonToString(String replaceString){
		String newReplacedString = null;
		newReplacedString = replaceString.replaceAll("\\[", "").replaceAll("\\]","");
		newReplacedString = newReplacedString.replaceAll("\"", "");
		return newReplacedString;
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
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("sladmin");
		 emailMessage.setFirstName(professionalName);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("professionalName", professionalName);
		 globalVarsMap.put("loginUserName", user.getUserName());
		 StringBuilder loginBuilder = new StringBuilder();
		 
		 

		 //if(user.getRegistrationType().equalsIgnoreCase("Newly Registered")){
			// globalVarsMap.put("resetLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("resetloginCredentials").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString()); 
		 //}else{

		 globalVarsMap.put("resetLink",loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
//		 }
		 
		  StringBuilder linkBuilder = new StringBuilder();
			 globalVarsMap.put("supportLink",linkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
			
			 StringBuilder fbLinkBuilder = new StringBuilder();
			 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder linkedInLinkBuilder = new StringBuilder();
			 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder twitterLinkBuilder = new StringBuilder();
			 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
			
			 StringBuilder googleLinkBuilder = new StringBuilder();
			 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_INDIVIDUAL_RECRUITER,Constants.ENTITY_TYPE_EMAIL);
		
		
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

	@SuppressWarnings("null")
	@Override
	public void saveProfessionalFromSocialInviter(JSONObject jsonObj) throws Exception {
		 String userName = null;
		 User user = null;
		 Candidate can = null;
		 UserRole userRole = null;
		 OrgLocations orgLocations = null;
		if(jsonObj.has("userName") && !isNullOrEmpty(jsonObj.getString("userName"))){
			userName = jsonObj.getString("userName");
			user = userService.getUserByEmailAndUserType(userName,Constants.PROFESSIONAL);
			
		}
		if(user != null ) {
			     if(jsonObj.has("service") && !isNullOrEmpty(jsonObj.getString("service"))
					  && jsonObj.has("profileUrl") && !isNullOrEmpty(jsonObj.getString("profileUrl"))){
						String service = jsonObj.getString("service");
						String profileUrl = jsonObj.getString("profileUrl");
						if(!isNullOrEmpty(service)  && (service.equalsIgnoreCase("googleplus") || service.equalsIgnoreCase("gmail") || service.equalsIgnoreCase("google"))){
							user.setGooglePlus(profileUrl);
							//can.setGooglePlus(profileUrl);
						}
						else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("facebook")){
							user.setFacebook(profileUrl);
							//can.setFacebookUrl(profileUrl);
						}
						else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("twitter")){
							user.setTwitter(profileUrl);
							//can.setTwitterUrl(profileUrl);
						}
						else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("linkedin")){
							user.setLinkedIn(profileUrl);
							//can.setLinkedUrl(profileUrl);
						}
						
					}
					userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
					orgLocations = userRole.getOrgLocations();
					
		}
		else {
			 user = new User();
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
				
				if(jsonObj.has("confirmPassword") && !isNullOrEmpty(jsonObj.getString("confirmPassword"))){
					user.setPassword(sourceleadPasswordEncoder.encode(jsonObj.getString("confirmPassword")));
				}
				
				user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
				if(jsonObj.has("userName") && !isNullOrEmpty(jsonObj.getString("userName"))){
					user.setUserName(jsonObj.getString("userName"));
				}
				/*if(jsonObj.has("imageUrl") && !isNullOrEmpty(jsonObj.getString("imageUrl"))){
					user.setDocFile(fetchRemoteFile(jsonObj.getString("imageUrl")));
					user.setFileContentType("image/png");
				}*/
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
				
				prepareRegisterOptionalServicesToAllForms(jsonObj,user);
				user.setUserType(Constants.PROFESSIONAL);
				user.setCreatedBy("sladmin");
				user.setCreatedDate(new Date());
				user.setUpdatedDate(new Date());
				
				user = userService.addUser(user);
				
				Org org = new Org();
				org.setId(1L);
				
			    orgLocations = new OrgLocations();
				orgLocations.setId(1L);
						
				
			    userRole = new UserRole();
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
				userRole.setIsMailSent(Status.Y);
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

				if(jsonObj.has("service") && !isNullOrEmpty(jsonObj.getString("service"))
						  && jsonObj.has("profileUrl") && !isNullOrEmpty(jsonObj.getString("profileUrl"))){
							String service = jsonObj.getString("service");
							String profileUrl = jsonObj.getString("profileUrl");
							if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("facebook")){
								candidate.setFacebookUrl(profileUrl);
							}
							else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("twitter")){
								candidate.setTwitterUrl(profileUrl);			
							}
							else if(!isNullOrEmpty(service)  && service.equalsIgnoreCase("linkedin")){
								candidate.setLinkedUrl(profileUrl);
							}
							
						}
				
				
				candidate.setStatus(Constants.CANDIDATE_ACTIVE);
				candidate.setCreatedBy("sladmin");
				candidate.setCreatedDate(new Date());
				candidate.setUpdatedDate(new Date());
				candidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				
				MasterCandidate masterCandidate = null; 
				 masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
				 if(null == masterCandidate){
					 masterCandidate = getMasterCandidateDetailsForProfessionalAndRecruiterLogins(candidate,user);
				 }
				
				try {
					masterCandidateService.saveMasterCandidate(masterCandidate);
				} catch (Exception e) {
					e.printStackTrace();
				}
				candidate.setMasterCandidateId(masterCandidate);
				
				
				candidateService.addCandidate(candidate);
				candidateService.getCandidatePercentage(candidate, orgLocations);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
		 }
		loginWithSocialInviter(user, userRole, orgLocations);
	}

	@Override
	public void saveIndividualRecruiterFromSocialInviter(JSONObject jsonObj) throws Exception {
		 String userName = null;
		 User user = null;
		 UserRole userRole = null;
		 OrgLocations orgLocations = null;
		if(jsonObj.has("userName") && !isNullOrEmpty(jsonObj.getString("userName"))){
			userName = jsonObj.getString("userName");
			user = userService.getUserByEmailAndUserType(userName,Constants.RECRUITER);
		}
		if(user != null){
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
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			orgLocations = userRole.getOrgLocations();
		}
	   else{
	 		user = new User();
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
			/*if(jsonObj.has("imageUrl") && !isNullOrEmpty(jsonObj.getString("imageUrl"))){
				user.setDocFile(fetchRemoteFile(jsonObj.getString("imageUrl")));
				user.setFileContentType("image/png");
			}*/
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
			
			prepareRegisterOptionalServicesToAllForms(jsonObj,user);
			user.setUserCode(SourceLeadRandomCodeHelper.generateRandomUserCode());
			user.setUserType(Constants.INDIVIDUAL_RECRUITER);
			user.setCreatedBy("sladmin");
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			
			userService.addUser(user);
			
			Org org = new Org();
			org.setId(1L);
			
		    orgLocations = new OrgLocations();
			orgLocations.setId(1L);
			
		    userRole = new UserRole();
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
			userRole.setIsMailSent(Status.Y);
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
			
			MasterCandidate masterCandidate = null; 
			 masterCandidate = masterCandidateService.getMasterCandidateByInteligence(jsonObj);
			 if(null == masterCandidate){
				 masterCandidate = getMasterCandidateDetailsForProfessionalAndRecruiterLogins(candidate,user);
			 }
			
			try {
				masterCandidateService.saveMasterCandidate(masterCandidate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			candidate.setMasterCandidateId(masterCandidate);
			
			
			candidateService.addCandidate(candidate);
			candidateService.getCandidatePercentage(candidate, orgLocations);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
		}
		 
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
	 
	 
	 
	private MasterCandidate getMasterCandidateDetailsForProfessionalAndRecruiterLogins(Candidate candidate,User user) {
		MasterCandidate masterCandidate = new MasterCandidate();
		masterCandidate.setFirstName(candidate.getFirstName());
		masterCandidate.setLastName(candidate.getLastName());
		masterCandidate.setEmail(candidate.getEmail());
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		return masterCandidate;
	}

	@Override
	public String sendOtpToUser(User user, String type) throws Exception
	{
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String userName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 userName = sb.append(user.getFirstName()).toString();
		 }
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromName("Sourcelead Admin");
		 emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		 emailMessage.setCreatedBy("sladmin");
		 
		 String token = RandomStringUtils.randomAlphanumeric(6);
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("userName", userName);
		 globalVarsMap.put("token", token);
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 OrgLocations orgloc = orgLocationService.getOrgLocations(1l);
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, new Org(), orgloc, Constants.LOGIN_OTP_EMAIL,new ArrayList<EmailAttachment>());
		 
		 return token;
				
	}

	@Override
	public String saveIndividualEmployer(EmployerRegistrationDTO employerRegistrationDTO) throws Exception {
		OrgRequest orgRequest = new OrgRequest();
		orgRequest.setFirstName(employerRegistrationDTO.getFirstName());
		if(!employerRegistrationDTO.getLastName().isEmpty()) {
			orgRequest.setLastName(employerRegistrationDTO.getLastName());
		}
		orgRequest.setPhoneNumber(employerRegistrationDTO.getPhoneNumber());
		orgRequest.setEmail(employerRegistrationDTO.getEmail());
		orgRequest.setCompanyName(employerRegistrationDTO.getCompanyName());
		orgRequest.setCompanywebsite(employerRegistrationDTO.getCompanyWebsite());
		orgRequest.setCountry(employerRegistrationDTO.getCountry());
		if(!employerRegistrationDTO.getCompanyAddress().isEmpty()) {
			orgRequest.setCompanyAddress(employerRegistrationDTO.getCompanyAddress());
		}
		
		
		orgRequest.setOrgType(employerRegistrationDTO.getOrgType());
		if(employerRegistrationDTO.getOrgType().equalsIgnoreCase("Demo")) {
			orgRequest.setRequestStatus("Requested");
			Timezone timezone=timeZoneService.getTimezone(Long.valueOf(employerRegistrationDTO.getTimeZone()));
			 
			 if(employerRegistrationDTO.getDemoDate() != null && !employerRegistrationDTO.getDemoDate().isEmpty()) {
				 String demoDateString=employerRegistrationDTO.getDemoDate();  
				 DateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
				 Date demoDate = dt.parse(demoDateString); 
				 orgRequest.setDemoDate(demoDate);
			 }
			
			if(employerRegistrationDTO.getDemoTime() != null && !employerRegistrationDTO.getDemoTime().isEmpty()) {
				orgRequest.setDemoTime(employerRegistrationDTO.getDemoTime());
			}
			orgRequest.setTimezone(timezone);
		}
		orgRequest.setModules(employerRegistrationDTO.getModulesList().toString());
		orgRequest.setStatus(Constants.RECORD_ACTIVE);
		orgRequest.setCreatedDate(new Date());
		orgRequestService.saveOrgRequest(orgRequest);
	    orgRequestService.senEmailOrgRequestToSourcelead(orgRequest);
		
		return "success";
	}

	@Override
	public Boolean validateOrgRequestCompanyNameByName(String companyName) throws Exception {
		Boolean flag = orgRequestService.validateOrgRequestCompanyNameByName(companyName);
		return flag;
	}



}
