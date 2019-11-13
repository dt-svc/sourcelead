package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocationInformationDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OfferLetterTemplateService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationIndiaBankingDetailsService;
import com.bis.sourcelead.service.OrgLocationInformationDetailsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;
import com.bis.sourcelead.service.OrglocationOnboardDocumentsService;
import com.bis.sourcelead.service.OrglocationSignatoryService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class OrgSettingsController {
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	RoleHierarchyService roleHierarchyService;
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	ModulesService modulesService;
	
	 @Autowired
	 private OrgLocationService orgLocationService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	OrglocationBankingDetailsService orglocationBankingDetailsService;
	
	@Autowired
	OrglocationOnboardDocumentsService orglocationOnboardDocumentsService;
	
	@Autowired
	OrglocationSignatoryService orglocationSignatoryService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Autowired
	SourceleadRolesService sourceleadRolesService;
	
	@Autowired
	EmailScenarioConfigurationService emailconService;
	
	
	@Autowired
	UserRoleService userRoleService;

	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	OrgLocationInformationDetailsService orgLocationInformationDetailsService;
	
	@Autowired
	OrgLocationIndiaBankingDetailsService orglocationIndiaBankingDetailsService;
	
	@Autowired
	EmailScenarioConfigurationService emailScenarioconService;
	
	@Autowired
	EmailReminderService emailReminderService;
	
	@Autowired
	private LetterHeadService letterHeadService;
	
	@Autowired
	private OfferLetterTemplateService offerLetterTemplateService;

	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/orgSettings")
	public ModelAndView orgSettings(HttpServletRequest request,@RequestParam(value = "tabType", required = false) String tabType) throws Exception
	{
		logger.debug("Org settings");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		
		List<States> states = statesService.getAllStatesByCountry(orgLocations.getCountry());
		model.addObject("statesList", states);
		
		List<LookUpData> bussinessTypesList = lookUpDataService.getLookUpDataByCategory(Constants.COMPANY_ONBOARD_BUSINESS_TYPE);
		model.addObject("bussinessTypesList", bussinessTypesList);
		
		
		if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
			List<OrgLocationIndiaBankingDetailsDTO> bankingDetailsDTOList =  orglocationIndiaBankingDetailsService.getOrglocationIndiaBankingDetailsList(orgLocations.getId());
			model.addObject("bankingDetailsDTOList", bankingDetailsDTOList);
			model.addObject("bankingDetailsDTOListSize", bankingDetailsDTOList.size());
		}else {
			List<OrgLocationBankingDetailsDTO> bankingDetailsDTOList =  orglocationBankingDetailsService.getOrglocationBankingDetailsListByOrgLocationId(orgLocations.getId());
			model.addObject("bankingDetailsDTOList", bankingDetailsDTOList);
			model.addObject("bankingDetailsDTOListSize", bankingDetailsDTOList.size());
		}
		
		
		OrglocationOnboardDocuments businessLicense = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.ONBOARD_DOCUMENT_BUSNIESS_LICENSE, orgLocations.getId());
		if(businessLicense != null) {
			model.addObject("businessLicenseDoc", businessLicense);
		}
		
		OrglocationOnboardDocuments bankCheque = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.BANK_CHEQUE, orgLocations.getId());
		if(bankCheque != null) {
			model.addObject("bankChequeDoc", bankCheque);
		}
		
		OrglocationOnboardDocuments registrationDoc  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.REGISTRATION_OR_INCORPORATION_OF_RECORDING, orgLocations.getId());
		if(registrationDoc != null) {
			model.addObject("registrationDoc", registrationDoc);
		}
		
		OrglocationOnboardDocuments annulReportBusiness  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.ANNUAL_REPORT_BUSINESS_LICENSE, orgLocations.getId());
		if(annulReportBusiness != null) {
			model.addObject("annulReportBusinessDoc", annulReportBusiness);
		}
		
		OrglocationOnboardDocuments w9Doc  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.W9_W8_BEN_E, orgLocations.getId());
		if(w9Doc != null) {
			model.addObject("w9Doc", w9Doc);
		}
		
		List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
	    model.addObject("rolesList", rolesList);
		
		/*OrglocationSignatory signatory = orglocationSignatoryService.getOrglocationSignatoryByOrglocationId(orgLocations.getId());
		if(signatory != null) {
			model.addObject("signatoryUser", signatory.getUserId().getId());
			if(!signatory.getUserId().getId().equals(user.getId())) {
				model.addObject("signatory", signatory);
				model.addObject("role",signatory.getUserRoleId().getRoles().getDisplayName());
			}
		}*/
	    
	    
	    
	    if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
	    	OrgLocationInformationDetails informationDetails = orgLocationInformationDetailsService.getOrgLocationInformationDetailsByOrgLocationId(orgLocations.getId());
			if(informationDetails != null) {
				
				if(informationDetails.getIncorporationDate() != null) {
					informationDetails.setIncorporationDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),informationDetails.getIncorporationDate()));
				}
				
				if(informationDetails.getPfRegistrationDate() != null) {
					informationDetails.setPfRegistrationDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),informationDetails.getPfRegistrationDate()));
				}
				
				if(informationDetails.getEsiRegistrationDate() != null) {
					informationDetails.setEsiRegistrationDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),informationDetails.getEsiRegistrationDate()));
				}
				
				if(informationDetails.getPtRegistrationDate() != null) {
					informationDetails.setPtRegistrationDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),informationDetails.getPtRegistrationDate()));
				}
				model.addObject("informationDetails", informationDetails);
			}
	    }
	
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(countries != null){
			model.addObject("loginCountryName", countries.getName());
		}
		
		List<OrgLicenseSubscription> subscriptionList = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgId(org.getId());
		List<String> orgSubscribedCountriesList = new ArrayList<>();
		StringBuilder subscriptionCountries = new StringBuilder(); 
		if(subscriptionList != null && !subscriptionList.isEmpty()) {
			for(OrgLicenseSubscription subscription : subscriptionList) {
				Countries subscriptionCountry = countriesService.getCountriesByCountryName(subscription.getCountry());
				subscriptionCountries.append(subscriptionCountry.getCode2()).append(",");
				orgSubscribedCountriesList.add(subscription.getCountry());
			}
		}
		
		 String subscribedCountries = subscriptionCountries.toString();
		 if(subscribedCountries.length() >0){
		 subscribedCountries = subscribedCountries.substring(0,subscribedCountries.length()-1);
		 }
		 EmailScenarioConfiguration emailSceconf= emailconService.getEmailConfigOrgLocId(orgLocations.getId());
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 Collection<Tabs> tabsList = new ArrayList<>();
		 Collection<Tabs> modulesList = new ArrayList<>();
		 Collection<String> modulesNamesList = new ArrayList<>();
		 Tabs dashobardTab = tabsService.getTabListByTabId(1L);
		 if(!userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			 tabsList = (Collection<Tabs>) SourceLeadSessionManager.getSessionAttribute(request, "tabsList");
		 }else{
			 if(orgLocations.getId() != null && user.getId() != null) {
				 UserRole orgUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocations.getId());
				 tabsList = tabsService.getTabsListByOrgLocationId(orgUserRole.getRoles().getId(), orgLocations.getId());
				
			 }
		 }
		 
		 modulesList.add(dashobardTab);
		 if(tabsList != null){
			 for(Tabs tabs : tabsList) {
				 modulesList.add(tabs);
				 modulesNamesList.add(tabs.getTabName());
			 }
		 }
		 
		 model.addObject("modulesList", modulesList);
		 model.addObject("modulesNamesList", modulesNamesList);
		model.addObject("emailSceconf", emailSceconf);
		model.addObject("orgSubscribedCountriesList", orgSubscribedCountriesList);
		model.addObject("subscribedCountries", subscribedCountries);
		model.addObject("timezoneList", timezoneList);
		model.addObject("orgLocations", orgLocations);
		if(StringUtils.isNotBlank(tabType)) {
			model.addObject("tabType",tabType);
		}
		model.setViewName("orgSettings");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')")
	@RequestMapping(value = "/orgSubmissionTemplate")
	public ModelAndView orgSubmissionTemplate(HttpServletRequest request,@RequestParam(value = "type", required = false) String type)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("orgSubmissionTemplateList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("type", type);
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "SubmissionTemplateCreateMsg");
		  if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "SubmissionTemplateCreateMsg");
		}
		return model;
	}
	
	@RequestMapping(value = "/orgTemplates")
	public ModelAndView orgTemplates(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		model.setViewName("orgTemplates");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	// @PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	 @RequestMapping(value = "/orgChart")
	    public ModelAndView orgChart(HttpServletRequest request) throws Exception
	    {
	        Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	        ModelAndView model = new ModelAndView();
	        model.addObject("sourceleadUrl", sourceleadUrl);
	        model.addObject("locations", orgLocationService.getAllOrgLocationsByOrgId(org.getId()));
	        model.setViewName("orgChart");
	        return model;
	    }
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/orgRolesList")
	public ModelAndView orgRolesList(HttpServletRequest request)
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		List<RoleHierarchy> hierarchyList = null;
		try {
			hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("hierarchyList", hierarchyList);
		model.setViewName("orgRolesList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/guestOrgActionRoles")
	public ModelAndView guestOrgActionRoles(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("guestOrgActionRoles");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/roleHierarchy")
	public ModelAndView roleHierarchy(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		List<RoleHierarchy> hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.getId());
		List<RoleHierarchy> rolesHierarchyList = new ArrayList<RoleHierarchy>();
		if (null != hierarchyList) {
			for (RoleHierarchy hierarchy : hierarchyList) {
				List<Roles> rolesList = rolesService.getUserRolesListByRoleHierarchyIdAndOrgLocationId(orgLocations.getId(), hierarchy.getId(), org.getOrgCode());
				hierarchy.setRolesList(rolesList);
				rolesHierarchyList.add(hierarchy);
			}
		}
		model.addObject("rolesHierarchyList", rolesHierarchyList);
		model.setViewName("roleHierarchy");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/addDepartment")
	public ModelAndView addDepartment(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		/*
		 * List<RoleHierarchy> hierarchyList =
		 * roleHierarchyService.getRoleHierarchyByOrgLocationId(orgLocations.
		 * getId()); List<RoleHierarchy> rolesHierarchyList = new
		 * ArrayList<RoleHierarchy>();
		 * 
		 * if(null != hierarchyList){ for(RoleHierarchy hierarchy :
		 * hierarchyList){ List<Roles> rolesList =
		 * rolesService.getUserRolesListByRoleHierarchyIdAndOrgLocationId(
		 * orgLocations.getId(), hierarchy.getId(),org.getOrgCode());
		 * hierarchy.setRolesList(rolesList); rolesHierarchyList.add(hierarchy);
		 * } }
		 * 
		 * model.addObject("rolesHierarchyList", rolesHierarchyList);
		 */
		model.setViewName("department");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/enrollRoleActions")
	public ModelAndView enrollRoleActions(HttpServletRequest request, @RequestParam(value = "uuid", required = false) String uuid) throws Exception
	{
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Roles roles = rolesService.getRolesByUUIDAndOrgLocationId(uuid, orgLocations.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		if (null != roles) {
			List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActionsForEnrollByOrgLocationIdAndOrgId(orgLocations,org);
			if (null != roles.getActionId()) {
				List<SourceleadActions> sourceleadActionsList = new ArrayList<SourceleadActions>();
				List<Long> roleActonList = new ArrayList<Long>();
				for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActonList.add(Long.parseLong(s));
	        	 }
				for (SourceleadActions sleadActions : actionsList) {
					if (roleActonList.contains(sleadActions.getId())) {
						sleadActions.setIsActionConfigured(Constants.YES);
					}
					else {
						sleadActions.setIsActionConfigured(Constants.NO);
					}
					sourceleadActionsList.add(sleadActions);
				}
				
				model.addObject("tabsMap", getActionTabs(sourceleadActionsList));
				model.addObject("actionsList", sourceleadActionsList);
			}
			else {
				model.addObject("tabsMap", getActionTabs(actionsList));
				model.addObject("actionsList", actionsList);
			}
			model.addObject("uuid", uuid);
			model.addObject("roleDisplayName", roles.getDisplayName());
			model.addObject("roleName", roles.getRoleName());
			model.setViewName("enrollRoleActions");
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionRolesMsg");
			if (!isNullOrEmpty(msg)) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "actionRolesMsg");
			}
		}
		else {
			model.setViewName("registration/persmissionDenied");
		}
		return model;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/guestEnrollRoleActions")
	public ModelAndView guestEnrollRoleActions(HttpServletRequest request, @RequestParam(value = "uuid", required = true) String uuid) throws Exception
	{
		ModelAndView model = new ModelAndView();
		SourceleadRoles roles = sourceleadRolesService.getSourceleadRolesByUUID(uuid);
		model.addObject("sourceleadUrl", sourceleadUrl);
		if (null != roles) {
			List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadGuestOrgActionsForEnroll();
			if (null != roles.getActionId()) {
				List<SourceleadActions> sourceleadActionsList = new ArrayList<SourceleadActions>();
				List<Long> roleActonList = new ArrayList<Long>();
				/*for (String s : roles.getActionId().split(",")) {
					roleActonList.add(Long.parseLong(s));
				}*/
				for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActonList.add(Long.parseLong(s));
	        	 }
				for (SourceleadActions sleadActions : actionsList) {
					if (roleActonList.contains(sleadActions.getId())) {
						sleadActions.setIsActionConfigured(Constants.YES);
					}
					else {
						sleadActions.setIsActionConfigured(Constants.NO);
					}
					sourceleadActionsList.add(sleadActions);
				}
				model.addObject("tabsMap", getActionTabs(sourceleadActionsList));
				model.addObject("actionsList", sourceleadActionsList);
			}
			else {
				model.addObject("tabsMap", getActionTabs(actionsList));
				model.addObject("actionsList", actionsList);
			}
			model.addObject("roleDisplayName", roles.getDisplayName());
			model.addObject("roleName", roles.getRoleName());
			model.addObject("uuid", uuid);
			model.setViewName("enrollGuestRoleActions");
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionRolesMsg");
			if (!isNullOrEmpty(msg)) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "actionRolesMsg");
			}
		}
		else {
			model.setViewName("registration/persmissionDenied");
		}
		return model;
	}
	
	private Map<String, Set<String>> getActionTabs(List<SourceleadActions> sourceleadActionsList) {
		Map<String, Set<String>> actionTabs = new LinkedHashMap<>();
		try {
			for(SourceleadActions sourceleadActions : sourceleadActionsList) {
				String tabName = sourceleadActions.getTabs().getTabName();
				if (tabName.equalsIgnoreCase("My Timesheets") || tabName.equalsIgnoreCase("Timesheet Approval")) {
					tabName = "Timesheets";
				} else if (tabName.equalsIgnoreCase("Associates List")) {
					tabName = "Associates";
				} else if (tabName.equalsIgnoreCase("Receivable") || tabName.equalsIgnoreCase("Payables")) {
					tabName = "Billing";
				} else if (tabName.equalsIgnoreCase("My Expenses") || tabName.equalsIgnoreCase("Expenses Approval")) {
					tabName = "Expenses";
				} else if (tabName.equalsIgnoreCase("Blogs") || tabName.equalsIgnoreCase("User Manuals")) {
					tabName = "Resources";
				} else if (tabName.equalsIgnoreCase("Immigrations") || tabName.equalsIgnoreCase("My Immigration")) {
					tabName = "Immigration";
				} else if (tabName.equalsIgnoreCase("Run Payroll") || tabName.equalsIgnoreCase("My Payroll")) {
					tabName = "Pay Roll";
				}
				
				if(actionTabs.get(tabName) != null && !actionTabs.get(tabName).isEmpty()) {
					actionTabs.get(tabName).add(sourceleadActions.getTabs().getTabName());
				} else {
					Set<String> tabsList = new LinkedHashSet<>();
					tabsList.add(sourceleadActions.getTabs().getTabName());
					actionTabs.put(tabName, tabsList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionTabs;
	}
	
	@RequestMapping(value = "/addGrade")
	public ModelAndView addGrade(HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		
		model.setViewName("addGrade");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	protected boolean isNullOrEmpty(String str)
	{
		return str == null || "".equals(str.trim());
	}
	

	@RequestMapping(value = "/addMailPopUp", method = RequestMethod.POST)
	public ModelAndView addMailPopUp(HttpServletRequest request, @RequestParam(value = "emailUUID", required = false) String emailUUID) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 Collection<Tabs> tabsList = new ArrayList<>();
		 Collection<Tabs> modulesList = new CopyOnWriteArrayList<>();
		 Collection<String> modulesNamesList = new ArrayList<>();
		 
		 List<EmailConfigurationFromMail> fromMailList= emailScenarioconService.getConfFromMailList(orgLocations.getId());
		 Set<Tabs> moduleList=new HashSet<Tabs>();
		 for(EmailConfigurationFromMail emailconf:fromMailList){
				if( emailconf.getEmailModules() != null){
					String[] jobIds = emailconf.getEmailModules().split(",");
					for(int i=0; i<jobIds.length; i++) {
					Tabs tab=tabsService.getTabListByTabId(Long.valueOf(jobIds[i]));
					moduleList.add(tab);
					}
 				
				}
		 }
		 
		 Tabs dashobardTab = tabsService.getTabListByTabId(1L);
		 if(!userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			 tabsList = (Collection<Tabs>) SourceLeadSessionManager.getSessionAttribute(request, "tabsList");
		 }else{
			 if(orgLocations.getId() != null && user.getId() != null) {
				 UserRole orgUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocations.getId());
				 tabsList = tabsService.getTabsListByOrgLocationId(orgUserRole.getRoles().getId(), orgLocations.getId());
				
			 }
		 }
		 
		 modulesList.add(dashobardTab);
		 for(Tabs tabs : tabsList) {
			 modulesList.add(tabs);
			 modulesNamesList.add(tabs.getTabName());
		 }
		 if(moduleList.size()>0){
			 for(Tabs tabold:modulesList){
				 for(Tabs tab:moduleList){
					 if(tabold.getTabName().equalsIgnoreCase(tab.getTabName())){
						 modulesList.remove(tabold);
					 }
				 }
			 }
			
		 }
		 EmailConfigurationFromMail emailFromMail=emailScenarioconService.getFromMailByUUID(emailUUID);
		 String moduleNames="";
			if( emailFromMail!=null && emailFromMail.getEmailModules() != null){
				String[] jobIds = emailFromMail.getEmailModules().split(",");
				for(int i=0; i<jobIds.length; i++) {
				Tabs tab=tabsService.getTabListByTabId(Long.valueOf(jobIds[i]));
				modulesList.add(tab);
				moduleNames=moduleNames+tab.getTabName()+",";
				}
				String modules=emailFromMail.getEmailModules();
				emailFromMail.setEmailModules(moduleNames.substring(0, moduleNames.length()-1));
			}
		 
		 model.addObject("modulesList", modulesList);
		 model.addObject("emailFromMail", emailFromMail);
		 model.addObject("modulesNamesList", modulesNamesList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("addMailPopUp");
		return model;
	}
	
	 @RequestMapping(value = "/emailreminderConfigurationDashboard")
	   	public ModelAndView emailreminderConfigurationDashboard(HttpServletRequest request) throws Exception {
	   	    ModelAndView model = new ModelAndView();
	   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKSEVERITY);
			model.addObject("referTypeList", referTypeList);
	   		model.setViewName("emailreminderConfigurationDashboard");
	   		model.addObject("sourceleadUrl", sourceleadUrl);
	   		return model;
	       }
	    
	    
	    @RequestMapping(value = "/jobsConfigureEmails")
	   	public ModelAndView reminderJobsConfigureEmails(HttpServletRequest request) throws Exception {
	   	    ModelAndView model = new ModelAndView();
	   	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			//get job related reminders
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByTabsIdAndOrgLocationId(8l , orgLocations.getId());
			//model.addObject("emailReminderList", emailReminderList);
			
			for(EmailReminder emailReminder : emailReminderList) {
				emailReminder.setEmailReminderHierarchyList(emailReminder.getEmailReminderHierarchyList().stream().filter(h->h.getStatus() != 0).collect(Collectors.toList()));
				if(emailReminder.getScenarionName().equalsIgnoreCase("ApplicantJoining")) {
					model.addObject("applicantJoining", emailReminder);
				}
				
				
			}
			
	   		model.setViewName("requirement/jobsConfigureEmails");
	   		model.addObject("sourceleadUrl", sourceleadUrl);
	   		return model;
	       }
	    
	    
	    @RequestMapping(value = "/financeConfigureEmails")
	   	public ModelAndView reminderFinanceConfigureEmails(HttpServletRequest request) throws Exception {
	   	    ModelAndView model = new ModelAndView();
	   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKSEVERITY);
	   	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   	    model.addObject("orgLocationId", orgLocations.getId());
	   		model.addObject("referTypeList", referTypeList);
	   		model.setViewName("billing/financeConfigureEmails");
	   		model.addObject("sourceleadUrl", sourceleadUrl);
	   		return model;
	       }
	    
	    //for setting page redirection
	    @RequestMapping(value = "/settings")
		 public ModelAndView goToSettings(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();		
			model.setViewName("settings");
			model.addObject("sourceleadUrl", sourceleadUrl);			
			return model;
		}
	    
	    
	    @RequestMapping(value = "/emailReminderLeaveConfiguration")
	   	public ModelAndView reminderLeaveConfigureEmails(HttpServletRequest request) throws Exception {
	   	    ModelAndView model = new ModelAndView();
	   	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			//get job related reminders
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByTabsIdAndOrgLocationId(31l , orgLocations.getId());
			//model.addObject("emailReminderList", emailReminderList);
			
			for(EmailReminder emailReminder : emailReminderList) {
				emailReminder.setEmailReminderHierarchyList(emailReminder.getEmailReminderHierarchyList().stream().filter(h->h.getStatus() != 0).collect(Collectors.toList()));
				if(emailReminder.getScenarionName().equalsIgnoreCase("HolidayRemainder")) {
					model.addObject("Holiday", emailReminder);
				}
				else if(emailReminder.getScenarionName().equalsIgnoreCase("approvePendingLeaves")) {
					model.addObject("approvePendingLeaves", emailReminder);
				}
			
			  else {
			  model.addObject("employeeonLeave", emailReminder); 
			  }
			}
	   		model.setViewName("leavemanagement/emailReminderLeaveConfiguration");
	   		model.addObject("sourceleadUrl", sourceleadUrl);
	   		return model;
	       }
	    
	    @RequestMapping(value = "/contractConfigureEmails")
	   	public ModelAndView reminderContractsConfigureEmails(HttpServletRequest request) throws Exception {
	   	    ModelAndView model = new ModelAndView();
	   	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			//get contracts related reminders
			List<EmailReminder> emailReminderList = emailReminderService.getEmailReminderListByTabsIdAndOrgLocationId(16l , orgLocations.getId());
			//model.addObject("emailReminderList", emailReminderList);
			
			for(EmailReminder emailReminder : emailReminderList) {
				emailReminder.setEmailReminderHierarchyList(emailReminder.getEmailReminderHierarchyList().stream().filter(h->h.getStatus() != 0).collect(Collectors.toList()));
				if(emailReminder.getScenarionName().equalsIgnoreCase("contractExpiryRemainder")) {
					model.addObject("contractExpire", emailReminder);
				}
			}
	   		model.setViewName("contracts/contractConfigureEmails");
	   		model.addObject("sourceleadUrl", sourceleadUrl);
	   		return model;
	       }
	    

	    @RequestMapping(value = "/addOfferLetterTemplate")
	   		public ModelAndView addOfferLetterTemplate(@RequestParam(value="templateUUID", required=false) String templateUUID ,HttpServletRequest request,HttpServletResponse response) throws Exception {
	   			ModelAndView model = new ModelAndView();
	   			
	   			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   			if(templateUUID != null) {
	   				OfferLetterTemplate offerLetterTemplate = offerLetterTemplateService.getOfferLetterTemplateDetailsByTemplateUUID(templateUUID);
	   				model.addObject("offerLetterTemplate", offerLetterTemplate);
	   			}
	   			List<LetterHead> letterHeadList = letterHeadService.getLetterheadBylocationId(orgLocation.getId());
	   			model.addObject("letterHeadList", letterHeadList);
	   			model.addObject("sourceleadUrl", sourceleadUrl);
	   			model.setViewName("offerLetter/addOfferLetterTemplate");
	   			return model;
	   		}
	   	    
	   	    @RequestMapping(value = "/viewOfferLetterTemplate")
	   		public ModelAndView orgContractTemplate() {
	   			ModelAndView model = new ModelAndView();
	   			model.addObject("sourceleadUrl", sourceleadUrl);
	   			model.setViewName("offerLetter/viewOfferLetterTemplate");
	   			return model;
	   		}
}
