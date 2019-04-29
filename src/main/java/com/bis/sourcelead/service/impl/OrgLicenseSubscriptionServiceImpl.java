package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionDao;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionHistoryDao;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionPriceDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.RoleHierarchyDao;
import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.dao.SourceleadRolesDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.OrgLicenseSubscriptionDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionHistory;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
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
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.ticketManagement.dao.TicketManagementDao;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.util.Calendar;

@Service
public class OrgLicenseSubscriptionServiceImpl implements OrgLicenseSubscriptionService{

	@Autowired
	OrgLicenseSubscriptionDao orgLicenseSubscriptionDao;
	
	@Autowired
	CountriesDao countriesDao;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	LookUpDataDao lookUpDataDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ExpensesCategoryDao expensesCategoryDao;
	
	@Autowired
	RoleHierarchyDao roleHierarchyDao;
	
	@Autowired
	SourceleadRolesDao sourceleadRolesDao;
	
	@Autowired
	SourceleadActionsDao sourceleadActionsDao;
	
	@Autowired
	RolesDao rolesDao;
	
	@Autowired
	TabsDao tabsDao;
	
	@Autowired
	EmployeesDao employeesDao;
	
	@Autowired
	MasterCandidateService masterCandidateService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	OrgDao orgDao;
	
	@Autowired
	LookUpDataDao lookupDataDao;
	
	@Autowired
	OrgLicenseSubscriptionPriceDao orgLicenseSubscriptionPriceDao;
	
	@Autowired
	OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;
	
	@Autowired
	OrgLicenseSubscriptionHistoryDao orgLicenseSubscriptionHistoryDao;
	
	@Autowired
	private TicketManagementDao ticketManagementDao;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
}
	
	@Override
	public OrgLicenseSubscription saveOrgLicenseSubscription(OrgLicenseSubscription orgLicenseSubscription)
			throws Exception {
		return orgLicenseSubscriptionDao.saveOrgLicenseSubscription(orgLicenseSubscription);
	}

	@Override
	public OrgLicenseSubscription getOrgLicenseSubscriptionById(Long id) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionById(id);
	}

	@Override
	public OrgLicenseSubscription getOrgLicenseSubscriptionByOrgIdAndCountry(Long orgId, String country)
			throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByOrgIdAndCountry(orgId, country);
	}

	@Override
	public void registerOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,User loginUser) throws Exception {
		
		OrgLicenseSubscription orgLicenseSubscription = null;
		try {
			orgLicenseSubscription = getOrgLicenseSubscriptionById(orgLicenseSubscriptionDTO.getOrgLinceseSubscriptionId());
			orgLicenseSubscription.setSubscriptionTabs(orgLicenseSubscriptionDTO.getRegisteredModules());
			
				orgLicenseSubscription.setCurrency("USD");
			
		    if(!orgLicenseSubscription.getStorageSubscriptionType().getName().equalsIgnoreCase("Free Trail")) {
		    	orgLicenseSubscription.setInvoiceAmount(orgLicenseSubscriptionDTO.getInvoiceAmount());
			
			}else {
				orgLicenseSubscription.setInvoiceAmount(0d);
			}
		    
		    orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.NO);
			
			saveOrgLicenseSubscription(orgLicenseSubscription);
			
			OrgLocations orgLocations = orgLocationsDao.getOrgLocations(orgLicenseSubscriptionDTO.getOrgLocationId());
			User user = null;
			String password = null;
			if(orgLocations.getUser() != null) {
				 user =  orgLocations.getUser();
				UserRole userRole = userRoleDao.getLoginUserPrimaryRole(user.getId());
				 if(userRole == null || (null != userRole && userRole.getUserState().equals(Status.NOT_LOGGEDIN))) {
					 password = SourceLeadRandomCodeHelper.generateRandomPassword();
					 user.setPassword(sourceleadPasswordEncoder.encode(password));
				 }
				 
				try {
					userService.addUser(user);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			orgLocations.setRegistrationStatus("Registered");
			orgLocations.setUser(user);
			orgLocationsDao.updateOrgLocations(orgLocations);
			ticketManagementDao.dumpTicketManagementPreDefinedValuesToAllOrgLocations(orgLocations.getId());
			
			Org org = orgLicenseSubscription.getOrg();
			org.setUser(user);
			orgDao.addOrganization(org);
			
			
			List<LookUpData> lookUpExpCatList = lookUpDataDao.getLookUpDataByCategory(Constants.EXPENSESCATEGORY);
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
			saveOrgBasedRoles(orgLicenseSubscription.getOrg(),orgLocations,user,orgLicenseSubscriptionDTO.getRegisteredModules());
			
			Shift shift = commonUtil.saveDefaultGenaeralShift(orgLocations, user, Constants.GENERAL_SHIFT);
			//For saving the User Role (At registration user will have Admin Role)
			UserRole userRole = getUserRoleDetails(user,orgLocations);
			try {
				userRole.setShift(shift);
				userRoleDao.addUserRole(userRole);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//For saving Employee details (Every User is an Employee)
			Employees employee = getEmployeeDetails(user,orgLocations);
			try {
				employeesDao.addEmployee(employee);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 MasterCandidate masterCandidate = null;
			 CandidateDTO candidateDTO = new CandidateDTO();
			 candidateDTO.setEmail(user.getEmail());
			 candidateDTO.setPhoneNumber(user.getMobilePhone());
			 masterCandidate = masterCandidateService.getMasterCandidateByInteligenceCandidateDTO(candidateDTO);
			 if(null == masterCandidate){
				 masterCandidate = getMasterCandidateDetails(user);
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
			 
			saveDefaultSubmissionTemplate(orgLicenseSubscription.getOrg(),orgLocations,user);
			saveDefaultInterviewFeedbackTemplate(orgLicenseSubscription.getOrg(),orgLocations,user);
			
			commonUtil.saveDefaultContractTemplate(orgLicenseSubscription.getOrg(),orgLocations,user," Default Client MSA Template",Constants.CLIENT_MSA);
			commonUtil.saveDefaultContractTemplate(orgLicenseSubscription.getOrg(),orgLocations,user," Default Vendor MSA Template",Constants.VENDOR_MSA);
			commonUtil.saveDefaultContractTemplate(orgLicenseSubscription.getOrg(),orgLocations,user," Default Client SOW Template",Constants.Client_SOW);
			commonUtil.saveDefaultContractTemplate(orgLicenseSubscription.getOrg(),orgLocations,user," Default Vendor SOW Template",Constants.Vendor_SOW);
			commonUtil.saveDefaultContractTemplate(orgLicenseSubscription.getOrg(),orgLocations,user," Default NDA Template",Constants.NDA);
			commonUtil.saveDefaultPayslipTemplate(orgLocations,user);
			
			
			saveOrgLocationDefaultProject(orgLocations, user);
			commonUtil.storeVendorOnboardDefaultTemplate(user, orgLocations);
			
			userService.saveOrUpdateUserElkDocument(userRole,employee,"sladmin");
			sendActivationEmail(user);
			userService.sendEmail(user, loginUser, password,orgLocations.getId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 public Projects  saveOrgLocationDefaultProject(OrgLocations orgLocations,User approverUser) throws Exception {
		 	
	    	Projects projects = new Projects();
	    	StringBuilder defaultProjectName = new  StringBuilder();
	    	projects.setProjectName(defaultProjectName.append(orgLocations.getOrgLocationName().trim()).append(" ").append("Default Project").toString());
	    	StringBuilder defaultProjectNameDesc = new  StringBuilder();

	    	projects.setDescription(defaultProjectNameDesc.append(orgLocations.getOrgLocationName().trim()).append(" ").append("Default Project").toString());
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
			Countries countires = countriesDao.getCountriesByCountryName(orgLocations.getCountry());
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
			pah.setLevel(1);
			pah.setStatus("1");
			pah.setCreatedDate(new Date());
			pah.setCreatedBy(approverUser.getUserCode());
			projectApproverHierarchyList.add(pah);
			
			
			//prepare ProjectApproverHierarchy
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
			
			projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
	    	projectClientsSet.add(projectClients);
			return projectClientsSet;
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
	
	
	private MasterCandidate getMasterCandidateDetails(User user) {
		MasterCandidate masterCandidate = new MasterCandidate();
		masterCandidate.setFirstName(user.getFirstName());
		masterCandidate.setLastName(user.getLastName());
		masterCandidate.setEmail(user.getEmail());
		masterCandidate.setPhoneNumber(user.getMobilePhone());
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		return masterCandidate;
	}
	
	public void saveOrgRoleHierarchy(OrgLocations orgLocations,User user) throws Exception{
		List<RoleHierarchy> hierarchyList = roleHierarchyDao.getRoleHierarchyByOrgLocationId(1L);
		if(null != hierarchyList){
			for(RoleHierarchy roleHierarchy : hierarchyList){
				RoleHierarchy orgRoleHierarchy = new RoleHierarchy();
				orgRoleHierarchy.setHierarchyName(roleHierarchy.getHierarchyName());
				orgRoleHierarchy.setHierarchyLevel(roleHierarchy.getHierarchyLevel());
				orgRoleHierarchy.setOrgLocations(orgLocations);
				orgRoleHierarchy.setStatus(roleHierarchy.getStatus());
				orgRoleHierarchy.setCreatedBy(user.getUserCode());
				orgRoleHierarchy.setCreatedDate(new Date());
				roleHierarchyDao.saveRoleHierarchy(orgRoleHierarchy);
			}
		}
		
	}
	
	
	public void saveOrgBasedRoles(Org org,OrgLocations orgLocations,User user,String regsiteredModules) throws Exception{
		
		List<Long> tabsIds = new ArrayList<>();
		String[] registeredTabs = regsiteredModules.split(",");
		
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
		
		List<SourceleadRoles> rolesList = sourceleadRolesDao.getSourceleadRoles();
		List<SourceleadActions> sourceLeadActionsList =  sourceleadActionsDao.getAllSourceleadActionsByTabsList(tabsIds);
		Countries countires = countriesDao.getCountriesByCountryName(orgLocations.getCountry());
		
		if(null != rolesList){
			for(SourceleadRoles roles : rolesList){
				Roles orgRoles = new Roles();
				orgRoles.setRoleName(roles.getRoleName());
				orgRoles.setDisplayName(roles.getDisplayName());
				orgRoles.setOrgLocations(orgLocations);
				RoleHierarchy roleHierarchy = roleHierarchyDao.getRoleHierarchyByHierarchyLevelAndOrgLocationId(roles.getRoleHierarchy().getHierarchyLevel(), orgLocations.getId());
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
						}
						else if(actions.getLocationCode() != null) {
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
				rolesDao.storeRoles(orgRoles);
			}
		}
		
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
			roles = rolesDao.getRolesByRoleNameAndOrgLocationId("ROLE_ADMIN",orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		userRole.setRoles(roles);
		try {
			primaryUserRole = userRoleDao.getLoginUserPrimaryRole(user.getId());
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
	
	
	public void sendActivationEmail(User user) throws Exception
	{
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

	@Override
	public List<OrgLicenseSubscription> getOrgLicenseSubscriptionByOrgId(Long orgId) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByOrgId(orgId);
	}

	@Override
	public void upgradeOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO, User loginUser,Long orgId)
			throws Exception {
		
		try {
			
			OrgLicenseSubscription orgLicenseSubscription = null;
			OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory = null;
			if(orgLicenseSubscriptionDTO.getLicenseUUID() != null && !orgLicenseSubscriptionDTO.getLicenseUUID().isEmpty()) {
				orgLicenseSubscription = orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByUUID(orgLicenseSubscriptionDTO.getLicenseUUID());
				orgLicenseSubscriptionHistory =  commonUtil.getOrgLicenseSubscriptionHistory(orgLicenseSubscription,loginUser);
			}else {
				orgLicenseSubscription = new OrgLicenseSubscription();
				orgLicenseSubscription.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			}
			Org org = orgDao.findOrgByID(orgId);
			orgLicenseSubscription.setOrg(org);
			orgLicenseSubscription.setCountry(orgLicenseSubscriptionDTO.getCountry());
			
			OrgLicenseSubscriptionTypes subscriptionType = new OrgLicenseSubscriptionTypes();
			subscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getSubscriptionType()));
			orgLicenseSubscription.setSubscriptionType(subscriptionType);
			
			OrgLicenseSubscriptionTypes userSubscriptionType = new OrgLicenseSubscriptionTypes();
			userSubscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getUserSubscription()));
			orgLicenseSubscription.setUserSubscriptionType(userSubscriptionType);
			
			OrgLicenseSubscriptionTypes storageSubscriptionType = new OrgLicenseSubscriptionTypes();
			storageSubscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getStorageType()));
			orgLicenseSubscription.setStorageSubscriptionType(storageSubscriptionType);
			
			
			Timezone timezone = new Timezone();
			timezone.setId(Long.parseLong(orgLicenseSubscriptionDTO.getTimezone()));
			orgLicenseSubscription.setTimezone(timezone);
			
			orgLicenseSubscription.setCreatedBy(loginUser);
			orgLicenseSubscription.setCreatedDate(new Date());
			orgLicenseSubscription.setUpdatedBy(loginUser);
			orgLicenseSubscription.setUpdatedDate(new Date());
			orgLicenseSubscription.setStatus(1);
			
			Date subscriptionDate = commonUtil.orgLocationDateFormat("dd-MM-yyyy",orgLicenseSubscriptionDTO.getSubscriptionDate());
			subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscriptionDTO.getTimezone(), subscriptionDate, "dd-MM-yyyy");
			orgLicenseSubscription.setFromDate(subscriptionDate);
			
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(subscriptionDate);    
		    
		    OrgLicenseSubscriptionTypes orgLicenseSubscriptionTypes = orgLicenseSubscriptionTypesService.getOrgLicenseSubscriptionTypesById(Long.parseLong(orgLicenseSubscriptionDTO.getSubscriptionType()));
			
		    if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Free Trail") || orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Month")) {
		    	toDate.add(Calendar.MONTH, +1); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("6 Months")) {
		    	toDate.add(Calendar.MONTH, +6); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Year")) {
		    	toDate.add(Calendar.YEAR, +1); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("2 Years")) {
		    	toDate.add(Calendar.YEAR, +2); 
		    }
		    
		    Date subscriptionToDate = commonUtil.getOrgLocationCurrentDate("dd-MM-yyyy",toDate.getTime());
		    subscriptionToDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscriptionDTO.getTimezone(), subscriptionToDate, "dd-MM-yyyy");
			orgLicenseSubscription.setToDate(subscriptionToDate);
			orgLicenseSubscription.setCurrency("USD");
			
			
		    
			if(!orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Free Trail") && !orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Guest") && !orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Unlimited")) {
				OrgLicenseSubscriptionPrice subscriptionPrice = orgLicenseSubscriptionPriceDao.getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(Long.parseLong(orgLicenseSubscriptionDTO.getSubscriptionType()), Long.parseLong(orgLicenseSubscriptionDTO.getUserSubscription()));		
				orgLicenseSubscription.setInvoiceAmount(subscriptionPrice.getSubscriptionRate());
			}else {
				orgLicenseSubscription.setInvoiceAmount(0d);
				}
			orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.NO);
			orgLicenseSubscription.setSubscriptionTabs(orgLicenseSubscriptionDTO.getRegisteredModules());
			
			if(orgLicenseSubscriptionDTO.getLicenseUUID() != null && !orgLicenseSubscriptionDTO.getLicenseUUID().isEmpty()) {
				updateOrgBasedRoles(orgLicenseSubscriptionDTO,orgId);
				orgLicenseSubscriptionHistoryDao.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
			}
			
			saveOrgLicenseSubscription(orgLicenseSubscription);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
public void updateOrgBasedRoles(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,Long orgId) throws Exception{
		
		List<Long> tabsIds = new ArrayList<>();
		String[] registeredTabs = orgLicenseSubscriptionDTO.getRegisteredModules().split(",");
		
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
		
		List<Long> sourceLeadActionsIdsList =  new ArrayList<>();;
		List<SourceleadActions> sourceLeadActionsList =  sourceleadActionsDao.getAllSourceleadActionsByTabsList(tabsIds);
		Countries countires = countriesDao.getCountriesByCountryName(orgLicenseSubscriptionDTO.getCountry());
		for(SourceleadActions actions : sourceLeadActionsList) {
			if(actions.getLocationCode() == null || (actions.getLocationCode() != null && actions.getLocationCode().equalsIgnoreCase(countires.getCode2()))) {
				sourceLeadActionsIdsList.add(actions.getId());
			}
		}
		
		
		List<Long> orgLocationsIdsList = orgLocationsDao.getOrgLocationsIdsListByOrgIdAndCountry(orgId, orgLicenseSubscriptionDTO.getCountry());
		if(orgLocationsIdsList != null && !orgLocationsIdsList.isEmpty()) {
			List<Roles> rolesList = rolesDao.getAllRolesListByOrgLocationIdList(orgLocationsIdsList);
			if(rolesList != null && !rolesList.isEmpty()) {
				for(Roles roles : rolesList) {
					
					List<Long> roleActonsList = new ArrayList<Long>();
					if(null != roles.getActionId()){
						for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
			    			 roleActonsList.add(Long.parseLong(s));
			        	 }
					}
					
					roleActonsList.retainAll(sourceLeadActionsIdsList);
					
					StringBuilder strbul  = new StringBuilder();
					
					if(roleActonsList != null && !roleActonsList.isEmpty()) {
						for(Long actionId : roleActonsList) {
							strbul.append(Long.valueOf(actionId)).append(",");
						}
					
						String actionId = strbul.toString();
						
						if(!isNullOrEmpty(actionId)){
							actionId = actionId.substring(0, actionId.length()-1);
							roles.setActionId(actionId);
						}else{
							roles.setActionId(null);
						}
					}
					
					rolesDao.storeRoles(roles);
				}
			}
		}
		
	}

	@Override
	public OrgLicenseSubscription getOrgLicenseSubscriptionByUUID(String uuid) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByUUID(uuid);
	}

	@Override
	public List<String> getOrgLicenseSubscribedCountriesByOrgId(Long orgId) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscribedCountriesByOrgId(orgId);
	}

	@Override
	public void upgradeLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO, User loginUser,
			Long orgId,String subscriptionUUID) throws Exception {
		
		try {
			
			OrgLicenseSubscription orgLicenseSubscription = null;
			OrgLicenseSubscriptionHistory orgLicenseSubscriptionHistory = null;
		
			orgLicenseSubscription = orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
			orgLicenseSubscriptionHistory =  commonUtil.getOrgLicenseSubscriptionHistory(orgLicenseSubscription,loginUser);
			
			
			OrgLicenseSubscriptionTypes subscriptionType = new OrgLicenseSubscriptionTypes();
			subscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getSubscriptionType()));
			orgLicenseSubscription.setSubscriptionType(subscriptionType);
			
			OrgLicenseSubscriptionTypes userSubscriptionType = new OrgLicenseSubscriptionTypes();
			userSubscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getUserSubscription()));
			orgLicenseSubscription.setUserSubscriptionType(userSubscriptionType);
			
			OrgLicenseSubscriptionTypes storageSubscriptionType = new OrgLicenseSubscriptionTypes();
			storageSubscriptionType.setId(Long.parseLong(orgLicenseSubscriptionDTO.getStorageType()));
			orgLicenseSubscription.setStorageSubscriptionType(storageSubscriptionType);
			
			Timezone timezone = new Timezone();
			timezone.setId(Long.parseLong(orgLicenseSubscriptionDTO.getTimezone()));
			orgLicenseSubscription.setTimezone(timezone);
			
			orgLicenseSubscription.setCreatedBy(loginUser);
			orgLicenseSubscription.setCreatedDate(new Date());
			orgLicenseSubscription.setUpdatedBy(loginUser);
			orgLicenseSubscription.setUpdatedDate(new Date());
			orgLicenseSubscription.setStatus(1);
			
			Date subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscriptionDTO.getTimezone(), new Date(), "dd-MM-yyyy");
			orgLicenseSubscription.setFromDate(subscriptionDate);
			
			Calendar toDate = Calendar.getInstance();
			toDate.setTime(subscriptionDate);    
		    
		    OrgLicenseSubscriptionTypes orgLicenseSubscriptionTypes = orgLicenseSubscriptionTypesService.getOrgLicenseSubscriptionTypesById(Long.parseLong(orgLicenseSubscriptionDTO.getSubscriptionType()));
			
		    if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("Free Trail") || orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Month")) {
		    	toDate.add(Calendar.MONTH, +1); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("6 Months")) {
		    	toDate.add(Calendar.MONTH, +6); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("1 Year")) {
		    	toDate.add(Calendar.YEAR, +1); 
		    }else if(orgLicenseSubscriptionTypes.getName().equalsIgnoreCase("2 Years")) {
		    	toDate.add(Calendar.YEAR, +2); 
		    }
		    
		    Date subscriptionToDate = commonUtil.getOrgLocationCurrentDate("dd-MM-yyyy",toDate.getTime());
		    subscriptionToDate = CalendarUtil.getDateTimeForGivenTimeZone(orgLicenseSubscriptionDTO.getTimezone(), subscriptionToDate, "dd-MM-yyyy");
			orgLicenseSubscription.setToDate(subscriptionToDate);
			orgLicenseSubscription.setCurrency("USD");
			orgLicenseSubscription.setInvoiceAmount(0d);
			orgLicenseSubscription.setIsInvoiceAmountPaid(Constants.YES);
			orgLicenseSubscription.setSubscriptionTabs(orgLicenseSubscriptionDTO.getRegisteredModules());
			
			if(orgLicenseSubscriptionDTO.getLicenseUUID() != null && !orgLicenseSubscriptionDTO.getLicenseUUID().isEmpty()) {
				updateOrgBasedRoles(orgLicenseSubscriptionDTO,orgId);
				orgLicenseSubscriptionHistoryDao.saveOrgLicenseSubscriptionHistory(orgLicenseSubscriptionHistory);
			}
			
			saveOrgLicenseSubscription(orgLicenseSubscription);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	public List<String> getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(Long orgId) throws Exception {
		return orgLicenseSubscriptionDao.getOrgLicenseSubscribedAndRegisteredCountriesByOrgId(orgId);
	}

}
