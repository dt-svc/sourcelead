package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class LoginSessionController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginSessionController.class);
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TabsService  tabsService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired 
	private NotificationsService notificationsService;
	
	@Autowired 
	private ProjectsService projectsService;
	
	@Autowired 
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired 
	private ModulesService modulesService;
	
	@Autowired 
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired 
	private UserMailBoxService userMailBoxService;
	
	@Autowired 
	private RequirementService requirementService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired 
	private CountriesService countriesService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	TeamMembersService teamMembersService;
	
	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;
	
	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	OrgService orgService;

	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@RequestMapping(value = "/dashboard" , method = RequestMethod.GET)
	   public ModelAndView loginSession(HttpServletRequest request,Authentication authentication,
			   @RequestParam(value = "requirementUUID", required = false) String requirementUUID,
			   @RequestParam(value = "referUUID", required = false) String referUUID,HttpServletResponse response) throws Exception {
		
		User user = userService.findByUserName(authentication.getName());
		UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		ModelAndView model = new ModelAndView();
		Collection<Tabs> tabsList = null;
		Integer associatesTabsCount = 0;
		Integer billingTabsCount = 0;
		Integer expensesTabCount = 0;
		
		if(!userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL) && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)){
			OrgLocations orgLocations = userRole.getOrgLocations();
			Org org = orgLocations.getOrg();
			List<Long> modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
			if(org.getOrgType().equalsIgnoreCase("Company")){
				tabsList = tabsService.getTabsListByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
				for(Tabs tabs : tabsList){
					
					if(tabs.getTabName().equalsIgnoreCase("Associates List") || tabs.getTabName().equalsIgnoreCase("On & Off Boarding")){
						associatesTabsCount = associatesTabsCount+1;
					}
					
					if(tabs.getTabName().equalsIgnoreCase("Receivable") || tabs.getTabName().equalsIgnoreCase("Payables")){
						billingTabsCount = billingTabsCount+1;
					}
					
					if(tabs.getTabName().equalsIgnoreCase("My Expenses") || tabs.getTabName().equalsIgnoreCase("Expenses Approval")){
						expensesTabCount = expensesTabCount+1;
					}
				}
				
			}

			SourceLeadSessionManager.setSessionAttribute(request, "sourceleadUrl", sourceleadUrl);
			
			if(isNullOrEmpty(orgLocations.getDateFormat()) || isNullOrEmpty(orgLocations.getGeneratedUserId())){
				SourceLeadSessionManager.setSessionAttribute(request, "settingsConfigured", "N");
			}else{
				SourceLeadSessionManager.setSessionAttribute(request, "settingsConfigured", "Y");
			}

			SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
            SourceLeadSessionManager.setSessionAttribute(request, "averageWorkingHours", orgLocations.getAverageWorkingHours());
            SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
            SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());

			SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(request, "associatesTabsCount", associatesTabsCount);
			SourceLeadSessionManager.setSessionAttribute(request, "billingTabsCount", billingTabsCount);
			SourceLeadSessionManager.setSessionAttribute(request, "expensesTabCount", expensesTabCount);
			
			Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
			SourceLeadSessionManager.setSessionAttribute(request, "candidateId", candidate.getId());
			
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserFirstName", user.getFirstName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserLastName", user.getLastName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserEmail", user.getEmail());
			
			
			boolean isSuperUser = orgLocationService.isLoginUserIsSuperUser(user.getId(),orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "isSuperUser", isSuperUser);
			
			boolean isSuperAdmin = orgService.isLoginUserIsSuperAdmin(user.getId(),org.getId());
			if(isSuperAdmin == true){
				SourceLeadSessionManager.setSessionAttribute(request, "isSuperAdmin", Constants.YES);
			}else{
				SourceLeadSessionManager.setSessionAttribute(request, "isSuperAdmin", Constants.NO);
			}
			
			
			String[] modules = orgLocations.getModulesId().split(",");
			for(int i = 0 ; i< modules.length ; i++){
				if(modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "ats", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "vms", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_TIMESHEET_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "timesheet", "Y");
				}
			}
			
			userRole.setNewlyLoginUser(Constants.NO);
			userRoleService.addUserRole(userRole);
			
			setLoginSession(request, authentication,user,userRole);
			
			if("ROLE_PRODUCTION_SUPPORT".equalsIgnoreCase(userRole.getRoles().getRoleName())){
				List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredOrgLocations();
				if(orgLocationsList.size() > 1){
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", orgLocationsList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", orgLocationsList.size());
					SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", userRole.getRoles().getRoleName());
					SourceLeadSessionManager.setSessionAttribute(request, "prodSupportOrgLocationId", userRole.getRoles().getOrgLocations().getId());
				}
			}else{
				List<UserRole> userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());
				if(userSpocRolesList.size() > 1){
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
					SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", null);
				}
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "displayName", userRole.getRoles().getDisplayName());
			
			
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPwdMsg");
			if(null != msg && !msg.isEmpty()){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "resetPwdMsg");
			}
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = sdf.format(new Date());
			Date todayDate = sdf.parse(currentDate);
			
			CandidateOffBoarding candidateOffBoarding =  candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
			if(null != candidateOffBoarding){
				
				String endDate = sdf.format(candidateOffBoarding.getEndDate());
				Date onboardEndDte = sdf.parse(endDate);
				if(onboardEndDte.equals(todayDate) || todayDate.after(onboardEndDte)){
					SourceLeadSessionManager.setSessionAttribute(request, "candidateOffBoarding", candidateOffBoarding);
					if(candidateOffBoarding.getOffboardSubmitted().equalsIgnoreCase("N")){
						model.setViewName("offboard/offboardStep1");
					}else{
						model.setViewName("offboard/offboardUser");
					}
				}else{
					model.setViewName("dashboard");
				}
			}else{
				
				CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(null != candidateOnBoarding){
					SourceLeadSessionManager.setSessionAttribute(request, "candidateOnBoarding", candidateOnBoarding);
					if(candidateOnBoarding.getManagerUserId()!=null){
						SourceLeadSessionManager.setSessionAttribute(request, "OnboardingManagerUserId", candidateOnBoarding.getManagerUserId().getId());
					}
					if(candidateOnBoarding.getOnBoardTeamId()!=null){
						SourceLeadSessionManager.setSessionAttribute(request, "OnboardingTeamOwnerUserId", candidateOnBoarding.getOnBoardTeamId());
					}
					if(candidateOnBoarding.getOnboardSubmitted().equalsIgnoreCase("N")){
					   getOnboardingDetails(candidateOnBoarding,user,orgLocations,request);
					   model.setViewName("onboard/welcomeStep1");
					}else{
						model.setViewName("dashboard");
					}
				}else{
					model.setViewName("dashboard");
				}
			}
			
			List<LookUpData> skillList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillList", skillList);
			
			Boolean isManagerWithSubmissionRateStatusAsRequestOrAccept = requirementService.checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(user.getId(), orgLocations.getId());
			model.addObject("isManagerWithSubmissionRateStatusAsRequestOrAccept", isManagerWithSubmissionRateStatusAsRequestOrAccept);
			
			Boolean isAdmin = userRoleService.isUserAdminByOrgLocationId(user.getId(),orgLocations.getId());
			model.addObject("isAdmin",isAdmin);
			SourceLeadSessionManager.setSessionAttribute(request,"isAdmin",isAdmin);
			
			setTimeZone(request,orgLocations, model);
			
		}
		else{
			commonUtil.highLightMenu(Constants.Dashboard, request);
			setSessionUserAttributes(user,request,sourceleadUrl);
			setSessionOrgAttributes(userRole.getOrgId(),userRole.getOrgLocations(),request);
			tabsList = tabsService.getCrowdSourcingTabsListByRoleId(userRole.getRoles().getId());
			
			SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
			
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRole", userRole.getRoles().getRoleName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRoleId", userRole.getRoles().getId());
			SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", null);

			SourceLeadSessionManager.setSessionAttribute(request, "userType", userRole.getUserType());
			SourceLeadSessionManager.setSessionAttribute(request, "displayName", userRole.getUserType());
			SourceLeadSessionManager.setSessionAttribute(request, "designation", userRole.getDesignation());
			SourceLeadSessionManager.setSessionAttribute(request, "country", user.getCountry());
			
			OrgLocations orgLocations = userRole.getOrgLocations();
			SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
			SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
			
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserFirstName", user.getFirstName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserLastName", user.getLastName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserEmail", user.getEmail());
			List<UserRole> userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());
			if(userSpocRolesList.size() > 1){
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
			}
			
			try {
				Countries countries = countriesService.getCountriesByCountryName(user.getCountry());
				if(null != countries){
					SourceLeadSessionManager.setSessionAttribute(request, "currencyCode", countries.getCurrency());
					SourceLeadSessionManager.setSessionAttribute(request, "loginCountryName", countries.getName());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
				userRole.setUserState(Status.ACTIVE);
				userRoleService.addUserRole(userRole);
			}
			
						
			Candidate candidate = null;
			if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")){
				candidate = candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(user.getId(), Constants.PROFESSIONAL);
				StringBuilder redirectUrl = new StringBuilder();
				if(!isNullOrEmpty(requirementUUID) && isNullOrEmpty(referUUID)){
					response.sendRedirect(redirectUrl.append("/sourcelead/professionalRequirements?requirementUUID=").append(requirementUUID).toString());
				}
				else if(!isNullOrEmpty(requirementUUID) && !isNullOrEmpty(referUUID)){
					response.sendRedirect(redirectUrl.append("/sourcelead/professionalRequirements?requirementUUID=").append(requirementUUID).append("&referUUID=").append(referUUID).toString());
				}
				else{
					if(!isNullOrEmpty(userRole.getNewlyLoginUser()) && userRole.getNewlyLoginUser().equalsIgnoreCase("Y")){
						response.sendRedirect(redirectUrl.append("/sourcelead/professionalPraseCandidate").toString());
					}else{
						model.setViewName("professional/professionalDashboard");
					}
				}
				
				
				if(isNullOrEmpty(user.getProfessionalRegisterCountry())){
					SourceLeadSessionManager.setSessionAttribute(request, "country", user.getLocation());
				}else{
					SourceLeadSessionManager.setSessionAttribute(request, "country", user.getProfessionalRegisterCountry());
				}
				
			}else if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
				candidate = candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(user.getId(), Constants.INDIVIDUAL_RECRUITER);
				if(!isNullOrEmpty(requirementUUID)){
					StringBuilder redirectUrl = new StringBuilder();
					response.sendRedirect(redirectUrl.append("/sourcelead/professionalRequirements?requirementUUID=").append(requirementUUID).toString());
				}else{
					model.setViewName("recruiter/recruiterDashboard");
				}
				
				if(isNullOrEmpty(user.getRecruiterRegisterCountry())){
					SourceLeadSessionManager.setSessionAttribute(request, "country", user.getLocation());
				}else{
					SourceLeadSessionManager.setSessionAttribute(request, "country", user.getRecruiterRegisterCountry());
				}
			}
			
			userRole.setNewlyLoginUser(Constants.NO);
			userRoleService.addUserRole(userRole);
			
			SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
			SourceLeadSessionManager.setSessionAttribute(request, "candidateId", candidate.getId());
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			setTimeZone(request,orgLocations, model);
			
			
		}
	   
		return model;
		
	}
	
	
	
	@RequestMapping(value = "/dashboardLinks" , method = RequestMethod.GET)
	   public ModelAndView loginSessionLinks(HttpServletRequest request,HttpServletResponse response,Authentication authentication,
			   @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			   @RequestParam(value = "switchUserType", required = false) String switchUserType,
			   @RequestParam(value = "loginOrgLocatonId", required = false) Long loginOrgLocatonId) throws Exception {
		
		ModelAndView model = new ModelAndView();
		User user = userService.findByUserName(authentication.getName());
		UserRole userRole = null;
		UserRole productionUserRole = null;
		Candidate candidate = null;
		if(isNullOrEmpty(switchUserType)){
			if(null != loginOrgLocatonId){
				productionUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), loginOrgLocatonId);
				if(null != productionUserRole && "ROLE_PRODUCTION_SUPPORT".equalsIgnoreCase(productionUserRole.getRoles().getRoleName())){
					OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
					userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(), orgLocationId);
				}
			}else{
				userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocationId);
			}
			
		}else{
			userRole = userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(), orgLocationId, switchUserType);
		}
		
		SourceLeadSessionManager.setSessionAttribute(request, "switchUserType", switchUserType);
		
		Collection<Tabs> tabsList = null;
		List<SourceleadActions> actionsList = null;
		if(null != userRole){
			
		  if(!userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL) && !userRole.getRoles().getRoleName().equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)){
			
			OrgLocations orgLocations = userRole.getOrgLocations();
			Org org = orgLocations.getOrg();
			
			List<Long> modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
			actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
			
			Integer associatesTabsCount = 0;
			Integer billingTabsCount = 0;
			Integer expensesTabCount = 0;
			
			if(null == productionUserRole){
				userRole.setNewlyLoginUser(Constants.NO);
				userRoleService.addUserRole(userRole);
			}
			
			
			if(org.getOrgType().equalsIgnoreCase("Company")){
				tabsList = tabsService.getTabsListByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
				for(Tabs tabs : tabsList){
					
					if(tabs.getTabName().equalsIgnoreCase("Associates List") || tabs.getTabName().equalsIgnoreCase("On & Off Boarding")){
						associatesTabsCount = associatesTabsCount+1;
					}
					
					if(tabs.getTabName().equalsIgnoreCase("Receivable") || tabs.getTabName().equalsIgnoreCase("Payables")){
						billingTabsCount = billingTabsCount+1;
					}
					
					if(tabs.getTabName().equalsIgnoreCase("My Expenses") || tabs.getTabName().equalsIgnoreCase("Expenses Approval")){
						expensesTabCount = expensesTabCount+1;
					}
				}
				
			}
			
			if(isNullOrEmpty(orgLocations.getDateFormat()) || isNullOrEmpty(orgLocations.getGeneratedUserId())){
				SourceLeadSessionManager.setSessionAttribute(request, "settingsConfigured", "N");
			}else{
				SourceLeadSessionManager.setSessionAttribute(request, "settingsConfigured", "Y");
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
			SourceLeadSessionManager.setSessionAttribute(request, "associatesTabsCount", associatesTabsCount);
			SourceLeadSessionManager.setSessionAttribute(request, "billingTabsCount", billingTabsCount);
			SourceLeadSessionManager.setSessionAttribute(request, "expensesTabCount", expensesTabCount);

			if(null != productionUserRole && "ROLE_PRODUCTION_SUPPORT".equalsIgnoreCase(productionUserRole.getRoles().getRoleName())){
				candidate = candidateService.getCandidateByConsultantUserId(orgLocations.getUser().getId(), orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserFirstName", orgLocations.getUser().getFirstName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserLastName", orgLocations.getUser().getLastName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserEmail", orgLocations.getUser().getEmail());
			}else{
				candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserFirstName", user.getFirstName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserLastName", user.getLastName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserEmail", user.getEmail());
			}
			
			
			SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
			SourceLeadSessionManager.setSessionAttribute(request, "candidateId", candidate.getId());
			
			boolean isSuperUser = orgLocationService.isLoginUserIsSuperUser(user.getId(),orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "isSuperUser", isSuperUser);
			
			boolean isSuperAdmin = orgService.isLoginUserIsSuperAdmin(user.getId(),org.getId());
			if(isSuperAdmin == true){
				SourceLeadSessionManager.setSessionAttribute(request, "isSuperAdmin", Constants.YES);
			}else{
				SourceLeadSessionManager.setSessionAttribute(request, "isSuperAdmin", Constants.NO);
			}
			
			String[] modules = orgLocations.getModulesId().split(",");
			for(int i = 0 ; i< modules.length ; i++){
				if(modules[i].equalsIgnoreCase(Constants.MODULE_ATS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "ats", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_VMS_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "vms", "Y");
				}
				
				if(modules[i].equalsIgnoreCase(Constants.MODULE_TIMESHEET_STR)){
					SourceLeadSessionManager.setSessionAttribute(request, "timesheet", "Y");
				}
			}
			
			String userName = (String) authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName,null, buildUserAuthority(actionsList)));
			setLoginSession(request, authentication,user,userRole);
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			
			if(null == productionUserRole){
				if("ROLE_PRODUCTION_SUPPORT".equalsIgnoreCase(userRole.getRoles().getRoleName())){
					List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredOrgLocations();
					if(orgLocationsList.size() > 1){
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", orgLocationsList);
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", orgLocationsList.size());
						SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", userRole.getRoles().getRoleName());
						SourceLeadSessionManager.setSessionAttribute(request, "prodSupportOrgLocationId", userRole.getRoles().getOrgLocations().getId());
						
					}
				}else{
					List<UserRole> userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());
					if(userSpocRolesList.size() > 1){
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
						SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", null);
						
					}
				}
				
				SourceLeadSessionManager.setSessionAttribute(request, "displayName", userRole.getRoles().getDisplayName());
			}else{
				if("ROLE_PRODUCTION_SUPPORT".equalsIgnoreCase(productionUserRole.getRoles().getRoleName())){
					List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredOrgLocations();
					if(orgLocationsList.size() > 1){
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", orgLocationsList);
						SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", orgLocationsList.size());
						SourceLeadSessionManager.setSessionAttribute(request, "displayName", productionUserRole.getRoles().getDisplayName());
					}
				}
				
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = sdf.format(new Date());
			Date todayDate = sdf.parse(currentDate);
			
			CandidateOffBoarding candidateOffBoarding =  candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
			if(null != candidateOffBoarding){
				
				String endDate = sdf.format(candidateOffBoarding.getEndDate());
				Date onboardEndDte = sdf.parse(endDate);
				if(onboardEndDte.equals(todayDate) || todayDate.after(onboardEndDte)){
					SourceLeadSessionManager.setSessionAttribute(request, "candidateOffBoarding", candidateOffBoarding);
					if(candidateOffBoarding.getOffboardSubmitted().equalsIgnoreCase("N")){
						model.setViewName("offboard/offboardStep1");
					}else{
						model.setViewName("offboard/offboardUser");
					}
				}else{
					model.setViewName("dashboard");
				}
			}else{
				CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(null != candidateOnBoarding){
					SourceLeadSessionManager.setSessionAttribute(request, "candidateOnBoarding", candidateOnBoarding);
					if(candidateOnBoarding.getManagerUserId() != null){
						SourceLeadSessionManager.setSessionAttribute(request, "OnboardingManagerUserId", candidateOnBoarding.getManagerUserId().getId());
					}
					if(candidateOnBoarding.getOnBoardTeamId() != null){
						SourceLeadSessionManager.setSessionAttribute(request, "OnboardingTeamOwnerUserId", candidateOnBoarding.getOnBoardTeamId());
					}
					if(candidateOnBoarding.getOnboardSubmitted().equalsIgnoreCase("N")){
					   getOnboardingDetails(candidateOnBoarding,user,orgLocations,request);
					   model.setViewName("onboard/welcomeStep1");
					}else{
						model.setViewName("dashboard");
					}
				}else{
					model.setViewName("dashboard");
				}
			}
			
			List<LookUpData> skillList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillList", skillList);
			
			Boolean isManagerWithSubmissionRateStatusAsRequestOrAccept = requirementService.checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(user.getId(), orgLocations.getId());
			model.addObject("isManagerWithSubmissionRateStatusAsRequestOrAccept", isManagerWithSubmissionRateStatusAsRequestOrAccept);
			
			setTimeZone(request,orgLocations, model);
			
			}
			else{
				commonUtil.highLightMenu(Constants.Dashboard, request);
				
				actionsList = sourceleadActionsService.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(userRole.getRoles().getId());
				String userName = (String) authentication.getPrincipal();
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName,null, buildUserAuthority(actionsList)));
				
				setSessionUserAttributes(user,request,sourceleadUrl);
				setSessionOrgAttributes(userRole.getOrgId(),userRole.getOrgLocations(), request);
				tabsList = tabsService.getCrowdSourcingTabsListByRoleId(userRole.getRoles().getId());
				
				SourceLeadSessionManager.setSessionAttribute(request, "tabsList", tabsList);
				
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserRole", userRole.getRoles().getRoleName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserRoleId", userRole.getRoles().getId());
				SourceLeadSessionManager.setSessionAttribute(request, "prodSupportLoginUserRole", null);

				SourceLeadSessionManager.setSessionAttribute(request, "userType", userRole.getUserType());
				SourceLeadSessionManager.setSessionAttribute(request, "displayName", userRole.getUserType());
				SourceLeadSessionManager.setSessionAttribute(request, "designation", userRole.getDesignation());
				
				OrgLocations orgLocations = userRole.getOrgLocations();
				SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
				SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
				SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
				
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserFirstName", user.getFirstName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserLastName", user.getLastName());
				SourceLeadSessionManager.setSessionAttribute(request, "loginUserEmail", user.getEmail());
				
				List<UserRole> userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());
				if(userSpocRolesList.size() > 1){
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
					SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", userSpocRolesList.size());
				}
				
				try {
					Countries countries = countriesService.getCountriesByCountryName(user.getCountry());
					if(null != countries){
						SourceLeadSessionManager.setSessionAttribute(request, "currencyCode", countries.getCurrency());
						SourceLeadSessionManager.setSessionAttribute(request, "loginCountryName", countries.getName());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
							
				if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")){
					candidate = candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(user.getId(), Constants.PROFESSIONAL);
					StringBuilder redirectUrl = new StringBuilder();
					
					model.setViewName("professional/professionalDashboard");
					if(isNullOrEmpty(user.getProfessionalRegisterCountry())){
						SourceLeadSessionManager.setSessionAttribute(request, "country", user.getLocation());
					}else{
						SourceLeadSessionManager.setSessionAttribute(request, "country", user.getProfessionalRegisterCountry());
					}
					
					
				}else if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
					candidate = candidateService.getProfessionalInfoByLoginUserIdAndCandidateType(user.getId(), Constants.INDIVIDUAL_RECRUITER);
					
					model.setViewName("recruiter/recruiterDashboard");
					
					if(isNullOrEmpty(user.getRecruiterRegisterCountry())){
						SourceLeadSessionManager.setSessionAttribute(request, "country", user.getLocation());
					}else{
						SourceLeadSessionManager.setSessionAttribute(request, "country", user.getRecruiterRegisterCountry());
					}
				}
				
				userRole.setNewlyLoginUser(Constants.NO);
				userRoleService.addUserRole(userRole);
				
				SourceLeadSessionManager.setSessionAttribute(request, "candidate", candidate);
				SourceLeadSessionManager.setSessionAttribute(request, "candidateId", candidate.getId());
				
				model.addObject("sourceleadUrl", sourceleadUrl);
				setTimeZone(request,orgLocations, model);
				
			}
		   
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
	    return model;
		
	}
	
	
	private void setTimeZone(HttpServletRequest request,OrgLocations orgLocations,ModelAndView model) throws Exception{
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		SourceLeadSessionManager.setSessionAttribute(request, "timezone", timezone);
		model.addObject("timezone",timezone.getTimeZoneId());
	}
	
	
	
	private void setLoginSession(HttpServletRequest request,Authentication authentication,User user,UserRole userRole) throws Exception{
		
      commonUtil.highLightMenu(Constants.Dashboard, request);
		
		
		setSessionUserAttributes(user,request,sourceleadUrl);
		OrgLocations orgLocations = null;
		try {
			orgLocations = userRole.getOrgLocations();
			setSessionOrgLocationAttributes(orgLocations,request);
			setSessionOrgLocationAttributesDateFormat(orgLocations);
			Org org = orgLocations.getOrg();
			setSessionOrgAttributes(org,orgLocations,request);
			
			User orgPrimaryUser = org.getUser();
			User orgLocationPrimaryUser = orgLocations.getUser();
			
			if(orgLocationPrimaryUser != null &&  orgPrimaryUser != null && (user.getId().equals(orgPrimaryUser.getId()) || user.getId().equals(orgLocationPrimaryUser.getId()))){
				SourceLeadSessionManager.setSessionAttribute(request, "orgSettingsVisible", "Y");
			}
			
			if("ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole.getRoles().getRoleName())){
				SourceLeadSessionManager.setSessionAttribute(request, "enrollOrgVisible", "Y");
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRole", userRole.getRoles().getRoleName());
			SourceLeadSessionManager.setSessionAttribute(request, "loginUserRoleId", userRole.getRoles().getId());

			SourceLeadSessionManager.setSessionAttribute(request, "roleHierarchyId", userRole.getRoles().getRoleHierarchy().getId());

			SourceLeadSessionManager.setSessionAttribute(request, "userType", userRole.getUserType());
			SourceLeadSessionManager.setSessionAttribute(request, "designation", userRole.getDesignation());
			SourceLeadSessionManager.setSessionAttribute(request, "loginOrg", orgLocations.getOrgLocationName());
			
			List<Long> tabIds = tabsService.getTabsIdsListByUserRoleIdAndOrgLocationId(userRole.getRoles().getId(), orgLocations.getId());
			if(null != tabIds){
				for(Long tabId : tabIds){
					if(tabId.equals(Long.valueOf(Constants.TAB_REQUIREMENT))){
						SourceLeadSessionManager.setSessionAttribute(request, "REQUIREMENT_TAB", "Y");
					}
					
					else if(tabId.equals(Long.valueOf(Constants.TAB_MY_TIMESHEET))){
						SourceLeadSessionManager.setSessionAttribute(request, "MY_TIMESHEET_TAB", "Y");
					}
					
					else if(tabId.equals(Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL))){
						SourceLeadSessionManager.setSessionAttribute(request, "IMESHEET_APPROVAL_TAB", "Y");
					}
				}
			}
		
			
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}
		
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		
		//get notification count
		String orgCode = loginOrgCode;
		String locationCode = loginOrgLocation;
		Integer notificationCount = notificationsService.getNotificationsCountByUserId(loginUser.getId(),orgCode,locationCode);
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationCount",notificationCount);
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		
		// get notification list by user id
		Long notificationTo = loginUser.getId();
		List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(notificationTo,orgCode,locationCode);
		calculateDays(notificationList, timezone);
		
		/**
		 * commented by Prasad salina
		 */
		//commonUtil.storeTimesheetSubmissionRecords(user);
		/*commonUtil.terminateAllProjectMembersByUser(user);
		
		try {
			List<Projects> projectsList = projectsService.getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(orgLocations.getId(), loginUser.getId());
			for(Projects project : projectsList){
				commonUtil.terminateProject(project);
			}
			projectsService.saveAllProjects(projectsList);
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
		}*/
		
		
		SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationList",notificationList);
		
		Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Inbox");
		Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Requested");
		Integer totalUnReadMailsCount = inboxUnreadMailsCount+requestedUnreadMailsCount;
		SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", totalUnReadMailsCount);
		
	}
	
	private void calculateDays(List<Notifications> notificationList, Timezone timezone) throws ParseException {
		for (Notifications notifications : notificationList) {
					/*//differentiating date and time
						Date date = notifications.getCreatedDate();
					    String[] parts = date.toString().split(" ");
					    notifications.setDateValue(parts[0]);
					    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
					    final Date dateObj = sdf.parse(parts[1]);
					    notifications.setTime(new SimpleDateFormat("K:mm a").format(dateObj));
					    
					    // calculating days b/w dates 
					    Date currentDate = new Date();
						Date d1 = notifications.getCreatedDate();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						String strDate = dateFormat.format(currentDate);
						Date d2 = dateFormat.parse(strDate);
						long diff = d2.getTime() - d1.getTime();
						long diffDays = diff / (24 * 60 * 60 * 1000);
						notifications.setCalDays(String.valueOf(diffDays));*/
			long diff = commonUtil.currentDate().getTime() - notifications.getCreatedDate().getTime();
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				notifications.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0 && diffHours == 0){
				notifications.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0){
				notifications.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			}else if(diffDays < 7){
				notifications.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String currentTimeStr = Constants.TZ_DF.format(notifications.getCreatedDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String commonDateFormat = standardFormat.format(converteDate);
				notifications.setCalDays(commonDateFormat.toString());
					
				}
					
				}
		
	}


	private void setSessionUserAttributes(User user, HttpServletRequest request,String sourceleadUrl) throws Exception
	{
		
		SourceLeadSessionManager.setSessionAttribute(request, "sourceleadUrl", sourceleadUrl);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "userType", user.getUserType());
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());
		
		StringBuilder userNameSB = new StringBuilder();
		if(null != user.getLastName()){
			SourceLeadSessionManager.setSessionAttribute(request, "userName", userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		}else{
			SourceLeadSessionManager.setSessionAttribute(request, "userName", userNameSB.append(user.getFirstName()).toString());
		}

		
	}
	
	private void setSessionOrgLocationAttributes(OrgLocations orgLocations, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
		SourceLeadSessionManager.setSessionAttribute(request, "orgBasedDateFormat", orgLocations.getDateFormat());
		if(null != orgLocations.getUser() && null != orgLocations.getUser().getId()){
			SourceLeadSessionManager.setSessionAttribute(request, "superUserId", orgLocations.getUser().getId());
		}
		
		SourceLeadSessionManager.setSessionAttribute(request, "country", orgLocations.getCountry());
		
		try {
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
				SourceLeadSessionManager.setSessionAttribute(request, "currencyCode", countries.getCurrency());
				SourceLeadSessionManager.setSessionAttribute(request, "loginCountryName", countries.getName());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DateFormat DATEFORMAT;
	private void setSessionOrgLocationAttributesDateFormat(OrgLocations orgLocations){
	
		DATEFORMAT = new SimpleDateFormat(orgLocations.getDateFormat());	
	}
	
	private void setSessionOrgAttributes(Org org,OrgLocations orgLocations, HttpServletRequest request)
	{
		SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		SourceLeadSessionManager.setSessionAttribute(request, "orgCode", org.getOrgCode());
		SourceLeadSessionManager.setSessionAttribute(request, "website", org.getWebSite());
		SourceLeadSessionManager.setSessionAttribute(request, "orgName", orgLocations.getOrgLocationName());
		SourceLeadSessionManager.setSessionAttribute(request, "address", orgLocations.getOrgLocationAddress());
	}
	
	
	private void getOnboardingDetails(CandidateOnBoarding candidateOnBoarding,User user,OrgLocations orgLocations,HttpServletRequest request) throws Exception {

		
		candidateOnBoarding.setJoiningDateStr(LoginSessionController.DATEFORMAT.format(candidateOnBoarding.getStartDate()));
		
		if(null != candidateOnBoarding.getEndDate()){
			candidateOnBoarding.setEndDateStr(LoginSessionController.DATEFORMAT.format(candidateOnBoarding.getEndDate()));
		}
		
		if(null != candidateOnBoarding.getDueDate()){
			candidateOnBoarding.setDueDateStr(LoginSessionController.DATEFORMAT.format(candidateOnBoarding.getDueDate()));
		}
		
		
		if(null != candidateOnBoarding.getManagerUserId()){
			StringBuilder managerName = new StringBuilder();
			String manager  = null;
			if(!isNullOrEmpty(candidateOnBoarding.getManagerUserId().getLastName())){
				manager = managerName.append(candidateOnBoarding.getManagerUserId().getFirstName()).append(" ").append(candidateOnBoarding.getManagerUserId().getLastName()).toString();
			}else{
				manager = managerName.append(candidateOnBoarding.getManagerUserId().getFirstName()).toString();
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "managerName", manager);
		}
		
		if(null != candidateOnBoarding.getTeamId()){
			Team team = teamService.getTeamByTeamId(candidateOnBoarding.getTeamId().getId());
			SourceLeadSessionManager.setSessionAttribute(request, "teamName", team.getName());
			
			StringBuilder teamOwnerName = new StringBuilder();
			String teamOwnerUserName  = null;
			if(!isNullOrEmpty(team.getOwnerUserId().getLastName())){
				teamOwnerUserName = teamOwnerName.append(team.getOwnerUserId().getFirstName()).append(" ").append(team.getOwnerUserId().getLastName()).toString();
			}else{
				teamOwnerUserName = teamOwnerName.append(team.getOwnerUserId().getFirstName()).toString();
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerName", teamOwnerUserName);
			
			
			UserRole teamOwner = userRoleService.getUserDetailsByUserIdAndLocationId(team.getOwnerUserId().getId(), orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerRole", teamOwner.getRoles().getDisplayName());
			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerUserId", team.getOwnerUserId().getId());
			
			
			List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByTeamID(team.getId(), orgLocations.getId());
			List<TeamMembers> membersList = new ArrayList<TeamMembers>();
			if(null != teamMembersList){
				for(TeamMembers members : teamMembersList){
					
					StringBuilder teamMemberName  = new StringBuilder();
					if(!isNullOrEmpty(members.getMemberUserId().getLastName())){
						members.setMemberName(teamMemberName.append(members.getMemberUserId().getFirstName()).append(" ").append(members.getMemberUserId().getLastName()).toString());
					}else{
						members.setMemberName(teamMemberName.append(members.getMemberUserId().getFirstName()).toString());
					}
					
					UserRole memberUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(members.getMemberUserId().getId(), orgLocations.getId());
					members.setRoleName(memberUserRole.getRoles().getDisplayName());
					
					membersList.add(members);
				}
			}
			SourceLeadSessionManager.setSessionAttribute(request, "teamMembersList", membersList);
		}
		
		
		List<OnBoardingTemplateDocuments> supportedDocumentsList = onBoardingTemplateDocumentsService.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoarding.getId(), "Supported");
		SourceLeadSessionManager.setSessionAttribute(request, "supportedDocumentsList", supportedDocumentsList);
		SourceLeadSessionManager.setSessionAttribute(request, "supportedDocumentsListSize", supportedDocumentsList.size());
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