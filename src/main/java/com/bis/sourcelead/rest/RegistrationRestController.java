package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SourceleadAuthenticationProvider;
import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CompanyRegistrationDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserRegistrationDTO;
import com.bis.sourcelead.dto.EmployerRegistrationDTO;
import com.bis.sourcelead.dto.LocationEnrollmentDTO;
import com.bis.sourcelead.dto.OrgDTO;
import com.bis.sourcelead.dto.OrgLicenseSubscriptionDTO;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.dto.OrgPrimaryUserDTO;
import com.bis.sourcelead.dto.RegistrationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TabsDTO;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionPrice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PasswordResets;
import com.bis.sourcelead.entity.RegistrationOptionalService;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.form.RestResponsetUtil;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionPriceService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.PasswordResetsSevice;
import com.bis.sourcelead.service.RegistrationOptionalServicesService;
import com.bis.sourcelead.service.RegistrationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UserLoginHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationRestController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);
	 
	@Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
	 @Autowired
	 RegistrationService registrationService;
	
	 @Autowired
	 SourceleadAuthenticationProvider surceleadAuthenticationProvider;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 PasswordResetsSevice passwordResetsSevice;
	 
	 @Autowired
	 OrgService orgService;
	 
	 @Autowired
	 UserLoginHelper userLoginHelper;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @Autowired
	 SourceleadActionsService sourceleadActionsService;
	 
	 @Autowired
	 RolesService rolesService;
	 
	 @Autowired
	 OrgLocationService orgLocationService;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
	 
	 @Autowired
	 private RegistrationOptionalServicesService registrationOptionalServicesService;
	 
	 
	 @Autowired
	 WhiteListIpAddressService whiteListIpAddressService;
	 
	 @Autowired
	 UserLoginLogoutSessionService userLoginLogoutSessionService;
	 
	 @Autowired
	 TabsService tabsService;
	 
	 @Autowired
	 OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	 
	 @Autowired
	 OrgLicenseSubscriptionPriceService orgLicenseSubscriptionPriceService;
	 
	 @Autowired
     ConversionUtil conversionUtil;
	 
	 @Autowired
	 UserStatsCacheHelper userStatsCacheHelper;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Value("${isCouchbaseEnabled}")
	 private String isCouchbaseEnabled;
	 
	 @Autowired
     LookUpDataService lookUpDataService;
	 
	 @RequestMapping(value = "/app/getOrgsList")
	 		public @ResponseBody String getUsersList(HttpServletRequest request,
				@RequestParam(value="searchByName", required=false) String searchByName,
				@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "userState", required = false) String userState) {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<Org> orgsList = new ArrayList<>();

		ArrayList<Org> regOrgsList = new ArrayList<Org>();

		try {
			orgsList = orgService.getEnrolledOrgs(org.getId(), userState, searchByName,offset);
			if(orgsList != null && orgsList.size() > 0) {
			for(Org regOrg : orgsList){
					OrgLocations orglocations = orgLocationService.getPrimaryOrgLocationByOrgId(regOrg.getId());
					if(null != regOrg.getUser()){
						UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(regOrg.getUser().getId(),orglocations.getId());
						regOrg.setEmail(regOrg.getUser().getEmail());
						regOrg.setIsMailSent(userRole.getIsMailSent());
						regOrg.setUserState(regOrg.getUser().getUserState());
						regOrg.setMobilePhone(regOrg.getUser().getMobilePhone());
						regOrg.setRegisteredUserId(regOrg.getUser().getId());
						if(null != userRole){
							regOrg.setUserState(userRole.getUserState());
						}
					}
					
					if(null != orglocations){
						regOrg.setLocation(orglocations.getCountry());
						regOrg.setPrimaryOrgLocationId(orglocations.getId());
					}
					regOrgsList.add(regOrg);
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		resultJson = commonUtil.toJSON(regOrgsList);
		return resultJson;

	}
	 
	 
	 @RequestMapping(value = "/app/getDynamicOrgsList" , method = RequestMethod.POST)
		public @ResponseBody ResponseList<OrgDTO> getDynamicOrgsList(OrgDTO orgDTO,HttpServletRequest request) throws Exception {
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 ResponseList<OrgDTO> responseList = new ResponseList<>();
		   ResponseList<Org> orgsList =orgService.getDynamicEnrolledOrgs(org.getId(), orgDTO);
		   List<OrgDTO> orgDTOList = new ArrayList<>();
		   if(orgsList != null) {
				for(Org regOrg : orgsList.getData()){
					OrgDTO orgDto = new OrgDTO();
						OrgLocations orglocations = orgLocationService.getPrimaryOrgLocationByOrgId(regOrg.getId());
						UserRole userRole = null;
						if(null != regOrg.getUser() && null != orglocations){
							 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(regOrg.getUser().getId(),orglocations.getId());
						}
						if(null != userRole){
							
							orgDto.setId(regOrg.getId());
							orgDto.setOrgCode(regOrg.getOrgCode());
							orgDto.setOrgName(regOrg.getOrgName());
							orgDto.setEmail(regOrg.getUser().getEmail());
							orgDto.setIsMailSent(userRole.getIsMailSent().toString());
							if(regOrg.getUser().getUserState() != null) {
								orgDto.setUserState(regOrg.getUser().getUserState().toString());
							}
							orgDto.setMobilePhone(regOrg.getUser().getMobilePhone());
							orgDto.setRegisteredUserId(regOrg.getUser().getId());
							if(null != userRole){
								orgDto.setUserState(userRole.getUserState().toString());
							}
							orgDto.setUserId(regOrg.getUser().getId());
							orgDto.setUser(regOrg.getUser());
						}
						
						if(null != orglocations){
							orgDto.setUser(regOrg.getUser());
							orgDto.setId(regOrg.getId());
							orgDto.setOrgCode(regOrg.getOrgCode());
							orgDto.setOrgName(regOrg.getOrgName());
							if(orglocations.getLocation() != null && !(orglocations.getLocation().isEmpty())) {
								orgDto.setLocation(orglocations.getLocation());
							}else {
								if(orglocations.getCountry() != null && !(orglocations.getCountry().isEmpty())) {
									orgDto.setLocation(orglocations.getCountry());
								}
							}
							orgDto.setPrimaryOrgLocationId(orglocations.getId());
							orgDto.setRegistrationStatus(orglocations.getRegistrationStatus());
							orgDto.setLocationCode(orglocations.getLocationCode());
						}
						orgDTOList.add(orgDto);
					}
				}
		   
		    responseList.setRecordsFiltered(orgsList.getRecordsFiltered());
			responseList.setRecordsTotal(orgsList.getRecordsTotal());
			responseList.setData(orgDTOList);
			return responseList;
    }
	 
	 
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/companyRegistration" , method = RequestMethod.POST)
	   public OrgLocationDTO addCompanyRegistration(
			   CompanyRegistrationDTO companyRegistrationDTO,CandidateDTO candidateDTO,
	   			HttpServletRequest request) {
		OrgLocationDTO orgLocationDTO = new OrgLocationDTO();
		if(Validator.validate(companyRegistrationDTO)){
		try {

				Registration registration = new Registration();
				ModelMapper mapper1 = new ModelMapper();
				registration = mapper1.map(companyRegistrationDTO, Registration.class);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			    orgLocationDTO = registrationService.addCompanyRegistration(registration,user,orgLocations.getId(),candidateDTO);
			    orgLocationDTO.setRegistrationStatusStr("orgList");
			   /* StringBuilder orgCreateMsg = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "orgCreateMsg", orgCreateMsg.append(registration.getCompanyName()).append(" Org has created sucessfully.").toString());*/
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
		}else{
			orgLocationDTO.setRegistrationStatusStr("DataMissing");
		}
		
		return orgLocationDTO;
	     
	   }
	
	
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/getOrgSubscribedList" , method = RequestMethod.POST)
	   public List<OrgLicenseSubscriptionDTO> getOrgSubscribedList(@RequestParam(value="orgId", required=true) Long orgId,
			   HttpServletRequest request) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<OrgLicenseSubscriptionDTO> subscriptionList =  new ArrayList<>();
		List<OrgLicenseSubscription> licenseSubscriptionList =  orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgId(orgId);
		if(licenseSubscriptionList != null && !licenseSubscriptionList.isEmpty()) {
			for(OrgLicenseSubscription subscription : licenseSubscriptionList) {
				OrgLicenseSubscriptionDTO subscriptionDTO = new OrgLicenseSubscriptionDTO();
				subscriptionDTO.setSubscriptionType(subscription.getSubscriptionType().getName());
				subscriptionDTO.setCountry(subscription.getCountry());
				subscriptionDTO.setStorageType(subscription.getStorageSubscriptionType().getName());
				if(subscription.getUserSubscriptionType() != null) {
					subscriptionDTO.setUserSubscription(subscription.getUserSubscriptionType().getName());
				}
				
				
				if(subscription.getFromDate() != null) {
					subscriptionDTO.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getFromDate()));
				}
				
				if(subscription.getToDate() != null) {
					subscriptionDTO.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getToDate()));
				}
				
				subscriptionDTO.setCurrency(subscription.getCurrency());
				subscriptionDTO.setInvoiceAmount(subscription.getInvoiceAmount());
				String[] subscribedTabsList = subscription.getSubscriptionTabs().split(",");
				List<Long> tabIdsList = new ArrayList<>();
			    for(int i=0; i<subscribedTabsList.length; i++) {
			    	tabIdsList.add(Long.parseLong(subscribedTabsList[i]));
			    }
				
				List<Tabs> tabsList = tabsService.getTabsListByIds(tabIdsList);
				List<String> registeredTabs = new ArrayList<>();
				if(tabsList != null && !tabsList.isEmpty()) {
					for(Tabs tabs : tabsList) {
						registeredTabs.add(tabs.getTabName());
					}
				}
				
				subscriptionDTO.setModulesList(registeredTabs);
				subscriptionDTO.setLicenseUUID(subscription.getUuid());
				subscriptionList.add(subscriptionDTO);
			}
		}
		return subscriptionList;
	   }
	
	
	

	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/getLocationSubscriptionDetails" , method = RequestMethod.POST)
	   public OrgLicenseSubscriptionDTO getLocationSubscriptionDetails(@RequestParam(value="licenseUUID", required=true) String licenseUUID,
			   HttpServletRequest request) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		OrgLicenseSubscription subscription =  orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(licenseUUID);

		OrgLicenseSubscriptionDTO subscriptionDTO = new OrgLicenseSubscriptionDTO();
		subscriptionDTO.setSubscriptionType(subscription.getSubscriptionType().getId().toString());
		subscriptionDTO.setCountry(subscription.getCountry());
		if(subscription.getStorageSubscriptionType() != null) {
			subscriptionDTO.setStorageType(subscription.getStorageSubscriptionType().getId().toString());
		}
		
		if(subscription.getUserSubscriptionType() != null) {
			subscriptionDTO.setUserSubscription(subscription.getUserSubscriptionType().getId().toString());
		}
		
		if(subscription.getTimezone() != null) {
			subscriptionDTO.setTimezone(subscription.getTimezone().getId().toString());
		}
	
		
		if(subscription.getFromDate() != null) {
			subscriptionDTO.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getFromDate()));
		}
		
		if(subscription.getToDate() != null) {
			subscriptionDTO.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getToDate()));
		}
		
		
	    if(!subscription.getStorageSubscriptionType().getName().equalsIgnoreCase("Free Trail")) {
	    	subscriptionDTO.setInvoiceAmount(subscription.getInvoiceAmount());
		/*	if(subscription.getCountry().equalsIgnoreCase("United States")) {
				subscriptionDTO.setInvoiceAmount(subscription.getInvoiceAmount());
			}else {
				if(subscription.getInvoiceAmount() != null) {
					Double invoiceAmount = conversionUtil.currencyConvert(subscription.getCurrency(), "USD" ,subscription.getInvoiceAmount());
					DecimalFormat df = new DecimalFormat(".##");
					subscriptionDTO.setInvoiceAmount(Double.valueOf(df.format(invoiceAmount)));
				}
				
			}*/
		}else {
			subscriptionDTO.setInvoiceAmount(0d);
		}
		
		
		String[] subscribedTabsList = subscription.getSubscriptionTabs().split(",");
		List<Long> tabIdsList = new ArrayList<>();
	    for(int i=0; i<subscribedTabsList.length; i++) {
	    	tabIdsList.add(Long.parseLong(subscribedTabsList[i]));
	    }
		
		subscriptionDTO.setModulesIdsList(tabIdsList);
		subscriptionDTO.setLicenseUUID(subscription.getUuid());
	
		return subscriptionDTO;
	   }
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/companyLocationRegistration" , method = RequestMethod.POST)
	   public Response<String> companyLocationRegistration(
			   LocationEnrollmentDTO locationEnrollmentDTO,CandidateDTO candidateDTO,
	   			HttpServletRequest request) {
		
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		
	    	try{
	    		if(user != null){
	    		if(Validator.validate(locationEnrollmentDTO)){
	    			Registration registration = new Registration();
					ModelMapper mapper1 = new ModelMapper();
					registration = mapper1.map(locationEnrollmentDTO, Registration.class);
				   
					registrationService.addRegistration(registration,user,orgLocationId,candidateDTO);
					StringBuilder orgCreateMsg = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "orgCreateMsg", orgCreateMsg.append(registration.getCompanyName()).append(" Org has created sucessfully.").toString());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
		    	}else{
		    		response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
		    	}
    		}else{
    	    	response.setResponse(Result.SESSION_TIMEDOUT.name());
    			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    	    }
    	  }catch (Exception e) {
	    		response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
	    	
	    
		return response;
	     
	   }
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/getRegistrationTabs" , method = RequestMethod.POST)
	   public List<TabsDTO> getRegistrationTabs(
	   			HttpServletRequest request) throws Exception {
		
		List<TabsDTO> tabsDTOList = new ArrayList<>();
		Collection<Tabs> tabsList = tabsService.getAllTabsList();
		List<Long> tabIs = new ArrayList<>();
		tabIs.add(14L);
		tabIs.add(11L);
		List<Tabs> otherTabsList = tabsService.getTabsListByIds(tabIs);
		tabsList.addAll(otherTabsList);
		if(tabsList != null) {
			for(Tabs tabs : tabsList) {
				TabsDTO tabsDTO = new TabsDTO();
				tabsDTO.setId(tabs.getId());
				if(tabs.getTabName().equalsIgnoreCase("Billing")) {
					tabsDTO.setTabName("Finance");
				}else {
					tabsDTO.setTabName(tabs.getTabName());
				}
				tabsDTO.setTabIcon(tabs.getTabIcon());
				tabsDTOList.add(tabsDTO);
			}
		}
		
		return tabsDTOList;
	     
	   }
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/getOrgSubscribeCountries" , method = RequestMethod.POST)
	   public List<String> getOrgSubscribeCountries( @RequestParam(value="orgId", required=true) Long orgId,
	   			HttpServletRequest request) throws Exception {
		
		List<OrgLicenseSubscription> subscriptionList =  orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgId(orgId);
	    List<String> orgSubscribedCountries = new ArrayList<>();
		if(subscriptionList != null) {
	    	for(OrgLicenseSubscription subscription : subscriptionList) {
	    		orgSubscribedCountries.add(subscription.getCountry());
	    	}
	    }
		
		List<String> orgRegistrationCountries = new ArrayList<>();
		 String[] registrationCountries = Constants.REGISTERED_COUNTRIES;
		 for(int i=0; i<registrationCountries.length;i++) {
			 orgRegistrationCountries.add(registrationCountries[i]);
		 }
		 
		 orgSubscribedCountries.retainAll(orgRegistrationCountries);
		 orgRegistrationCountries.removeAll(orgSubscribedCountries);
		
		return orgRegistrationCountries;
	     
	   }
	
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/updateOrgLicenseSubscription" , method = RequestMethod.POST)
	   public String updateOrgLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		orgLicenseSubscriptionService.registerOrgLicenseSubscription(orgLicenseSubscriptionDTO,loginUser);
		return "success";
     }
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/getInvoiceRateBySubscriptionRateAndUserSubscription" , method = RequestMethod.POST)
	   public Double getInvoiceRateBySubscriptionRateAndUserSubscription(@RequestParam(value="subscriptionType", required=true) Long subscriptionType,
			   @RequestParam(value="userSubscription", required=true) Long userSubscription,HttpServletRequest request) throws Exception {
		OrgLicenseSubscriptionPrice subscriptionPrice = orgLicenseSubscriptionPriceService.getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(subscriptionType, userSubscription);
		if(subscriptionPrice != null) {
			return subscriptionPrice.getSubscriptionRate();
		}else {
			return 0d;
		}
		
     }
	
	
	@RequestMapping(value = "/app/getUpgradeInvoiceRateBySubscriptionRateAndUserSubscription" , method = RequestMethod.POST)
	   public Double getUpgradeInvoiceRateBySubscriptionRateAndUserSubscription(@RequestParam(value="subscriptionType", required=true) Long subscriptionType,
			   @RequestParam(value="userSubscription", required=true) Long userSubscription,@RequestParam(value="subscriptionUUID", required=true) String subscriptionUUID,
			   HttpServletRequest request) throws Exception {
		Double invoiceAmount = 0d;
		OrgLicenseSubscriptionPrice subscriptionPrice = orgLicenseSubscriptionPriceService.getOrgLicenseSubscriptionPriceBySubscriptionTypeAndUserType(subscriptionType, userSubscription);
		OrgLicenseSubscription subscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(subscriptionUUID);
	
		if(subscriptionPrice != null) {
			if(subscription.getSubscriptionType().getId().equals(subscriptionType) && subscription.getUserSubscriptionType().getId().equals(userSubscription)) {
				invoiceAmount = subscriptionPrice.getSubscriptionRate();
			}else {
				invoiceAmount = subscription.getInvoiceAmount()+subscriptionPrice.getSubscriptionRate();
			}
		}
		
		return invoiceAmount;
		
  }
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/saveOrgLocationSubscription" , method = RequestMethod.POST)
	   public String saveOrgLocationSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,
			   @RequestParam(value="orgId", required=true) Long orgId,HttpServletRequest request) {
		
		if(Validator.validate(orgLicenseSubscriptionDTO)){
			try {
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				orgLicenseSubscriptionService.upgradeOrgLicenseSubscription(orgLicenseSubscriptionDTO, loginUser, orgId);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "subscribeList";
		}else {
			return "DataMissing";
		}
		
     }
	
	@RequestMapping(value = "/app/upgradeOrgLocationLicenseSubscription" , method = RequestMethod.POST)
	   public String upgradeOrgLocationLicenseSubscription(OrgLicenseSubscriptionDTO orgLicenseSubscriptionDTO,
			   @RequestParam(value="orgId", required=true) Long orgId,HttpServletRequest request) {
		
		if(Validator.validate(orgLicenseSubscriptionDTO)){
			try {
				User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				orgLicenseSubscriptionService.upgradeLicenseSubscription(orgLicenseSubscriptionDTO, loginUser, orgId, orgLicenseSubscriptionDTO.getLicenseUUID());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "subscribeList";
		}else {
			return "DataMissing";
		}
		
  }
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/getcompanyRegisteredDetails" , method = RequestMethod.POST)
	   public OrgLocationDTO getcompanyRegisteredDetails(
			   @RequestParam(value="orgLocationId", required=false) Long orgLocationId,
	   			HttpServletRequest request) {
		OrgLocationDTO orgLocationDTO = new OrgLocationDTO();
		try {
			orgLocationDTO = orgLocationService.getRegisteredOrgLocationDetails(orgLocationId);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return orgLocationDTO;
	   }
	
	
	
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/createUesrBySourceleadAdmin" , method = RequestMethod.POST)
	 public  String createUser(AddUserDTO addUserDTO, CandidateDTO candidateDTO,HttpServletRequest request) throws Exception {
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 registrationService.addUserBySourcelead(user, addUserDTO,candidateDTO);
	 	
		 return "success";
	   }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/changePrimaryAdmin" , method = RequestMethod.POST)
	 public  String changePrimaryAdmin(@RequestParam(value="userId", required=false) Long userId,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception {
		 	
		 UserRole userRole = null;
		 Long existingUserId = null;
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		 if(orgLocations.getUser() != null) {
			 existingUserId = orgLocations.getUser().getId();
		 }
		 
		 User user = new User();
		 user.setId(userId);
		 orgLocations.setUser(user);
		 orgLocationService.updateOrgLocations(orgLocations);
		 
		 if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(userId, orgLocationId);
			 userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			 
			 if(existingUserId != null) {
				 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(existingUserId, orgLocationId);
				 userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			 }
		 }
		
		 
		 return "success";
	   }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	 @RequestMapping(value = "/app/changeSuperAdmin" , method = RequestMethod.POST)
	 public  String changeSuperAdmin(@RequestParam(value="userId", required=false) Long userId,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId,HttpServletRequest request) throws Exception {
		 	
		 UserRole userRole = null;
		 Long existingUserId = null;
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		 if(orgLocations.getUser() != null) {
			 existingUserId = orgLocations.getUser().getId();
		 }
		 User user = new User();
		 user.setId(userId);
		 Org org = orgLocations.getOrg();
		 org.setUser(user);
		 orgService.addOrganization(org);
		 
		 if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(userId, orgLocationId);
			 userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			 
			 if(existingUserId != null) {
				 userRole = userRoleService.getUserDetailsByUserIdAndLocationId(existingUserId, orgLocationId);
				 userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
			 }
		 }
		 
		 return "success";
	   }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	 @RequestMapping(value = "/app/editOrgPrimaryUser" , method = RequestMethod.POST)
	 public  Response<String> editOrgPrimaryUser(OrgPrimaryUserDTO OrgPrimaryUserDTO, 
			 @RequestParam(value="userId", required=false) Long userId ,
			 @RequestParam(value="orgLocationId", required=false) Long orgLocationId ,HttpServletRequest request) throws Exception {
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
	 	 Response<String> response=new Response<>();
	 	 
	 	 try{
	 		if(user !=null){
	 			if(Validator.validate(OrgPrimaryUserDTO)){
	 			 	 User primaryUser = userService.getUserById(userId);
	 			 	 if(null != primaryUser){
	 			 		 if(!isNullOrEmpty(OrgPrimaryUserDTO.getPrimaryUserFirstName())){
	 			 			primaryUser.setFirstName(OrgPrimaryUserDTO.getPrimaryUserFirstName());
	 			 		 }
	 			 		 
	 			 		 if(!isNullOrEmpty(OrgPrimaryUserDTO.getPrimaryUserLastName())){
	 				 			primaryUser.setLastName(OrgPrimaryUserDTO.getPrimaryUserLastName());
	 			 		 }else{
	 			 			primaryUser.setLastName(null);
	 			 		 }
	 			 		 
	 			 		 if(!isNullOrEmpty(OrgPrimaryUserDTO.getPrimaryUserEmail())){
	 				 			primaryUser.setEmail(OrgPrimaryUserDTO.getPrimaryUserEmail());
	 				 			primaryUser.setUserName(OrgPrimaryUserDTO.getPrimaryUserEmail());
	 			 		 }
	 			 		 
	 			 		 if(!isNullOrEmpty(OrgPrimaryUserDTO.getMobilePhone())){
	 			 			primaryUser.setMobilePhone(OrgPrimaryUserDTO.getMobilePhone());
	 			 		 }else{
	 			 			primaryUser.setMobilePhone(null);
	 			 		 }
	 			 		 
	 			 		String password = SourceLeadRandomCodeHelper.generateRandomPassword();
	 			 		primaryUser.setPassword(sourceleadPasswordEncoder.encode(password));
	 			 		userService.addUser(primaryUser);
	 			 		
	 			 		userService.sendEmail(primaryUser, user, password,orgLocations.getId());
	 			 		response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
	 			 	 }
	 				
	 			}else{
	 				response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
	 			}
	 		} else{
	 			response.setResponse(Result.SESSION_TIMEDOUT.name());
    			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	 		}
	 	 }catch (Exception e) {
	 		response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	 
	 	
		 return response;
	   }
	
	
	 @RequestMapping(value = "/app/ws/registerAsProfessional" , method = RequestMethod.POST)
	 public  String registerAsProfessional(CrowdSourcingUserRegistrationDTO csuRegistrationDTOO,
			 @RequestParam(value="contactUUID", required=false) String contactUUID,
			 HttpServletRequest request) throws Exception {
		
		 String result = registrationService.saveProfessional(csuRegistrationDTOO,contactUUID);
		 Long profUserId = null;
		if (result.contains("success")) {
			String s2[] = result.split("success");
			profUserId = Long.parseLong(s2[s2.length - 1]);
			result = "success";
		}
		SourceLeadSessionManager.setSessionAttribute(request, "profUserId", profUserId);
		 SourceLeadSessionManager.setSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg",
					"Username sent to your mail.");
	 	
		 return result;
	   }
	 
	 @RequestMapping(value = "/app/ws/registerAsIndividualRecruiter" , method = RequestMethod.POST)
	 public  String registerAsIndividualRecruiter(CrowdSourcingUserRegistrationDTO csUserRegistrationDTO, HttpServletRequest request) throws Exception {
		
		 String result = registrationService.saveIndividualRecruiter(csUserRegistrationDTO);
		 Long recUserId = null;
			if (result.contains("success")) {
				String s2[] = result.split("success");
				recUserId = Long.parseLong(s2[s2.length - 1]);
				result = "success";
			}
			SourceLeadSessionManager.setSessionAttribute(request, "profUserId", recUserId);
		 SourceLeadSessionManager.setSessionAttribute(request, "crowdSourcingRegistrationSuccessMsg",
					"Username sent to your mail.");
	 	
		 return result;
	   }
	 
	 
	 
	 @RequestMapping(value = "/app/ws/registerAsIndividualEmployer" , method = RequestMethod.POST)
	 public  String registerAsIndividualEmployer(EmployerRegistrationDTO employerRegistrationDTO, HttpServletRequest request) throws Exception {
		  registrationService.saveIndividualEmployer(employerRegistrationDTO);
		 return "success";
	   }
	 
	 @RequestMapping(value = "/app/ws/demoRequest" , method = RequestMethod.POST)
	 public  String demoRequest(EmployerRegistrationDTO employerRegistrationDTO, HttpServletRequest request) throws Exception {
		  registrationService.saveIndividualEmployer(employerRegistrationDTO);
		 return "success";
	   }
	 @RequestMapping(value = "/app/getAdminDemoModulesList", method = RequestMethod.GET)
		public @ResponseBody List<LookUpData> getAdminDemoModulesList(HttpServletRequest request) throws Exception
		{
			List<LookUpData> requestOrgList = lookUpDataService.getLookUpDataByCategory(Constants.REQUEST_ORG);
			return requestOrgList;
		}
	 @RequestMapping(value = "/app/getDemoTimezonesList", method = RequestMethod.POST)
		public @ResponseBody String getDemoTimezonesList(HttpServletRequest request) throws Exception
		{
			String resultJson = null;
			List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
			resultJson = commonUtil.toJSON(timezoneList);
			return resultJson;
		}
	 
	 @RequestMapping(value = "/app/ws/validateOrgRequestCompanyNameByName" , method = RequestMethod.POST)
	 public  String validateOrgRequestCompanyNameByName(@RequestParam(value="companyNames", required=false) String companyNames) throws Exception {
		 
		 String result = "New Org";
		 OrgLocations orgLocations = orgLocationService.getOrgLocationsByOrgLocationName(companyNames);
		 if(orgLocations == null) {
			 Boolean flag =  registrationService.validateOrgRequestCompanyNameByName(companyNames); 
			 if(flag == true) {
				 result = "Requested Org"; 
			 }
		 }else {
			 result = "Existed Org";
		 }
		
		 return result;
	   }
	 
	 
	 @RequestMapping(value = "/app/ws/registerAsProfessionalFromSocialInviter" , method = RequestMethod.POST)
	 public  String registerAsProfessionalFromSocialInviter(@RequestParam(value="json", required=false) String json,
			 HttpServletRequest request) throws Exception {
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
		 registrationService.saveProfessionalFromSocialInviter(jsonObj);
	 	
		 return "success";
	   }
	 
	 
	 
	 
	 @RequestMapping(value = "/app/ws/registerAsIndividualRecruiterFromSocialInviter" , method = RequestMethod.POST)
	 public  String registerAsIndividualRecruiterFromSocialInviter(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
		 registrationService.saveIndividualRecruiterFromSocialInviter(jsonObj);
		 return "success";
	   }
	 
	 
	 
	 @RequestMapping(value = "/app/ws/resetCredentials" , method = RequestMethod.POST)
	 public  String resetCredentials(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 User user = userService.getUserByUserName(jsonObj.getString("userName"));
		 if(null != user){
			 user.setPassword(sourceleadPasswordEncoder.encode(jsonObj.getString("password")));
			 prepareRegisterOptionalServicesToAllForms(jsonObj,user);
			 userService.addUser(user);
		 }
		 return "success";
	   }
	 
	 
	 public void prepareRegisterOptionalServicesToAllForms(JSONObject jsonObj, User user) throws JSONException{
			if(jsonObj.has("Wouldliketo") && !isNullOrEmpty(jsonObj.getString("Wouldliketo"))){
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
	 
	 
	 @RequestMapping(value = "/app/ws/getOptionalValueList" , method = RequestMethod.POST)
	 public  String getOptionalValueList(HttpServletRequest request) throws Exception {
	 			String registerTypeTwo = "Would like to";
				List<RegistrationOptionalService> WouldLikeToList = registrationOptionalServicesService.getRegistraionOptionalServiceListByRegisterType(registerTypeTwo);
				return commonUtil.toJSON(WouldLikeToList);
	        }
	 
	 
	
	   @PreAuthorize("hasAnyRole('ROLE_ENROLL_ORG','ROLE_VIEW_EDIT_ORG_SETTINGS')")
	   @RequestMapping(value = "/app/updateOrgModules" , method = RequestMethod.POST)
	   public String updateOrgModules(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
				@RequestParam(value="modulesList", required=false) String modulesList ,
				@RequestParam(value="submodulesList", required=false) String submodulesList ,
			    @RequestParam(value="actionIds", required=false) String actionIds,
			    HttpServletRequest request) throws Exception {
		
		     OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			 String orgModules = "";
			 StringBuilder strbul = null; 
			 StringBuilder strbul2 = null; 
			 StringBuilder strbul3 = null; 
			 
			 if(!isNullOrEmpty(submodulesList)){
					String[] registeredSubModules =  submodulesList.split(",");
					for(int i=0; i<registeredSubModules.length; i++){
							orgModules = orgModules+registeredSubModules[i]+",";
							
							 Long moduleId = null;
							 Long tabId = null;
							 
							if(registeredSubModules[i].equals("3")){
								moduleId = (long) Constants.MODULE_VMS;
								tabId = (long) Constants.TAB_VENDOR;
							}
							else if(registeredSubModules[i].equals("2")){
								moduleId = (long) Constants.MODULE_ATS;
								tabId = (long) Constants.TAB_CLIENT;
							}
								List<Long> actionsList = sourceleadActionsService.getSourceLeadActionsByModuleId(moduleId);
								
								Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
								 List<Long> adminRoleActonList = new ArrayList<Long>();
								 for (String s : adminrole.getActionId().split(",")){
									 adminRoleActonList.add(Long.parseLong(s));
								 }
								 
								 adminRoleActonList.retainAll(actionsList);
								 actionsList.removeAll(adminRoleActonList);
								 
								 strbul  = new StringBuilder();
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
				}
				
				if(!isNullOrEmpty(modulesList)){
					List<Long> actionsList = null;
					String employeeIds = null;
					String[] registeredModules =  modulesList.split(",");
					for(int i=0; i<registeredModules.length; i++){
						if(Long.parseLong(registeredModules[i]) != 1L){
							orgModules = orgModules+registeredModules[i]+",";
						}
						
						
						if(registeredModules[i].equals("4")){
							
							Long moduleId = (long) Constants.MODULE_TIMESHEET;
							Long myTimeSheetTabId = (long) Constants.TAB_MY_TIMESHEET;
							Long timeSheetApprovalTabId = (long) Constants.TAB_TIMESHEET_APPROVAL;
							Long tabId = (long) Constants.TAB_CLIENT;
							Long projectsTabId = (long) Constants.TAB_PROJECTS;
							
							actionsList = sourceleadActionsService.getSourceLeadTimeSheetActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId, timeSheetApprovalTabId);
							
							 strbul  = new StringBuilder();
						     Iterator<Long> iter = actionsList.iterator();
						     while(iter.hasNext())
						     {
						         strbul.append(iter.next());
						        if(iter.hasNext()){
						         strbul.append(",");
						        }
						     }
						     
						     strbul.toString();
						     
						     strbul2 = new StringBuilder();
						     List<Long> clientActionsList = sourceleadActionsService.getSourceLeadActionsByTabId(tabId);
						     Iterator<Long> iter2 = clientActionsList.iterator();
						     while(iter2.hasNext())
						     {
						    	 strbul2.append(iter2.next());
						        if(iter2.hasNext()){
						        	strbul2.append(",");
						        }
						     }
						        strbul2.toString();
						        
						        strbul3 = new StringBuilder();
							     List<Long> projectsActionsList = sourceleadActionsService.getSourceLeadActionsByTabId(projectsTabId);
							     Iterator<Long> iter3 = projectsActionsList.iterator();
							     while(iter3.hasNext())
							     {
							    	 strbul3.append(iter3.next());
							        if(iter3.hasNext()){
							        	strbul3.append(",");
							        }
							     }
							        strbul3.toString();
						        
						        Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
						        String adminids = null;
						        List<Long> adminRoleActonList = new ArrayList<Long>();
								 for (String s : adminrole.getActionId().split(",")){
									 adminRoleActonList.add(Long.parseLong(s));
								 }
								 
								 adminRoleActonList.retainAll(clientActionsList);
								
								 if(adminRoleActonList.size() == 0){
									 adminids = adminrole.getActionId()+","+strbul+","+strbul3+","+strbul2;
								 }else{
									 adminids = adminrole.getActionId()+","+strbul+","+strbul3;
								 }
						        
								adminrole.setActionId(adminids);
								rolesService.storeRoles(adminrole);
								
								Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
								String managerids = null;
								
								List<Long> managerRoleActonList = new ArrayList<Long>();
								 for (String s : managerrole.getActionId().split(",")){
									 managerRoleActonList.add(Long.parseLong(s));
								 }
								 
								 managerRoleActonList.retainAll(clientActionsList);
								
								 if(managerRoleActonList.size() == 0){
									 managerids = managerrole.getActionId()+","+strbul+","+strbul3+","+strbul2;
								 }else{
									 managerids = managerrole.getActionId()+","+strbul+","+strbul3;
								 }
								
								managerrole.setActionId(managerids);
								rolesService.storeRoles(managerrole);
								
								
								actionsList = sourceleadActionsService.getSourceLeadActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId);
								
								 strbul  = new StringBuilder();
							     Iterator<Long> iter1 = actionsList.iterator();
							     while(iter1.hasNext())
							     {
							         strbul.append(iter1.next());
							        if(iter1.hasNext()){
							         strbul.append(",");
							        }
							     }
							     strbul.toString();
							     
						        Roles employeeRole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_USER, orgLocations.getId());
								if(!isNullOrEmpty(employeeRole.getActionId())){
									employeeIds = employeeRole.getActionId()+","+strbul;
								}else{
									employeeIds = strbul.toString();
								}
								employeeRole.setActionId(employeeIds);
								rolesService.storeRoles(employeeRole);
							
						}
					}
				}
				
				if("" != orgModules){
					orgModules = orgModules.substring(0, orgModules.length()-1);
					StringBuilder orgModulesstr = new StringBuilder();
					orgModules = orgModulesstr.append(orgLocations.getModulesId()).append(",").append(orgModules).toString();
					orgLocations.setModulesId(orgModules);
				}
			    orgLocationService.updateOrgLocations(orgLocations);
		   
		return "success";
	   }
	
	
	
	
	
	
	
	@RequestMapping(value = "/app/ws/resetPasswordWithUserName" , method = RequestMethod.POST)
	public RestResponsetUtil resetPasswordWithUserName(RegistrationDTO registrationDTO,HttpServletRequest request) throws Exception {
		 RestResponsetUtil resResponsetUtil = new RestResponsetUtil();
//		String json1 = IOUtils.toString(request.getInputStream());
		if(!registrationDTO.getEmail().isEmpty()){
//			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//			Registration registration = gson.fromJson(json1, Registration.class);
			User user = userService.findByUserName(registrationDTO.getEmail());
			if (null != user) {
				UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
				if (null != userRole) {
					try {
						PasswordResets passwordResets = passwordResetsSevice.getPasswordResetsByUserId(user.getId());
						if (null == passwordResets) {
							String verificationCode = registrationService.addPasswordResets(user);
							registrationService.sendResetPasswordEmail(user,userRole.getOrgLocations().getId(), verificationCode);
						} else {
							passwordResets.setVerificationCode(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
							passwordResets = passwordResetsSevice.savePasswordResets(passwordResets);
							// email to user
							registrationService.sendResetPasswordEmail(user,userRole.getOrgLocations().getId(), passwordResets.getVerificationCode());
						}
		
						SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordMsg",
								"Verification Code sent to your mail.");
					} catch (Exception e) {
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}else {
					resResponsetUtil.setStatus("Inactive");
					return resResponsetUtil;
				}
				resResponsetUtil.setStatus("valid");
				return resResponsetUtil;
			} else {
				resResponsetUtil.setStatus("Invalid");
				return resResponsetUtil;
			}
		}
		resResponsetUtil.setStatus("Invalid");
		return resResponsetUtil;
	}
	

	@RequestMapping(value = "/app/ws/verifyResetPassword" , method = RequestMethod.POST)
	   public RestResponsetUtil verifyResetPassword(HttpServletRequest request) throws Exception {
		RestResponsetUtil resResponsetUtil = new RestResponsetUtil();
		String json1 = IOUtils.toString(request.getInputStream());
		if(StringUtils.isNotEmpty(json1)){
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Registration registration =  gson.fromJson(json1,Registration.class);
			
			//FetchType for User in PasswordResets is declared with Eager. So, User also loaded along with PasswordResets
			PasswordResets passwordResets  = passwordResetsSevice.getPasswordResetsByVerificationCode(registration.getVerificationCode());
			
			if(null != passwordResets){
				try {
					registrationService.resetPassword(passwordResets, registration.getPassword());
					registrationService.deletePasswordResets(passwordResets);
					
					//email to user
					User user = userService.findByUserName(passwordResets.getEmail());
					if(null != user){
						UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
						if (null != userRole) {
							registrationService.sendUpdatePasswordEmail(user,userRole.getOrgLocations().getId());
						}
					}
					 SourceLeadSessionManager.setSessionAttribute(request, "resetPasswordSucessfulMsg", "Your password has reset sucessfully.");
					
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				resResponsetUtil.setResetResponse("login");
				return resResponsetUtil;
			}else{
				resResponsetUtil.setResetResponse("Invalid");
				return resResponsetUtil;
			}
		}
		resResponsetUtil.setResetResponse("Invalid");
		return resResponsetUtil;
	 }
	
	   @RequestMapping(value = "/app/validateOrgWebsite", method = RequestMethod.GET)
	   public @ResponseBody String validateOrgWebsite(
	   		@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites) {
			String status;
			if(!isNullOrEmpty(website)){
			    String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
				if ("Website Duplicated".equalsIgnoreCase(orgInfo)) {
					status = "Website Duplicated";
				}else if("Invalid Website".equalsIgnoreCase(orgInfo)){
					status = "Invalid Website";
				}
				else{
					status = "SUCCESS";
				}
			}else{
				status = "Invalid Website";
			}
			
	    return status;
	   }
	   
	   
	   @RequestMapping(value = "/app/validateCompanyName", method = RequestMethod.GET)
	   public @ResponseBody String validateCompanyName(
	   		@RequestParam(value="companyName", required=true) String companyName,@RequestParam(value="orgLocationId", required=false) String orgLocationId,HttpServletRequest request) {
			String status = null;
			boolean flag = false;
			OrgLocations regOrgLocation = null;
				try {
					if(StringUtils.isNoneBlank(orgLocationId)) {
						regOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(orgLocationId));
					}
					
					if(regOrgLocation != null && regOrgLocation.getOrgLocationName().equalsIgnoreCase(companyName)) {
						flag = false;
					}else {
						List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
						if(null != orgLocationsList){
							for(OrgLocations orgLocations : orgLocationsList){
								 if(orgLocations.getOrgLocationName().equalsIgnoreCase(companyName)){
									flag = true;
								}
							}
						}
					}
					
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		   
		   if(flag == true){
			   status = "Company Duplicated";
		   }else{
			   status = "success";
		   }
		   
	       return status;
	   }
	   
	   
	   
	   @RequestMapping(value = "/app/validateRegiseredCompanyName", method = RequestMethod.GET)
	   public @ResponseBody String validateRegiseredCompanyName(
	   		@RequestParam(value="companyName", required=false) String companyName,HttpServletRequest request) {
			String status = null;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			if(!loginOrgLocation.getOrgLocationName().equalsIgnoreCase(companyName)) {
				boolean flag = false;
				try {
					List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocations();
					if(null != orgLocationsList){
						for(OrgLocations orgLocations : orgLocationsList){
							if(orgLocations.getOrgLocationName().equalsIgnoreCase(companyName)){
								flag = true;
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		   
		   if(flag == true){
			   status = "Company Duplicated";
		   }else{
			   status = "success";
		   }
		}else {
			 status = "success";
		}
		   
       return status;
	   }
	   
	   
	   @RequestMapping(value = "/app/getUserDetails",  method = RequestMethod.POST)
	    public @ResponseBody String getUserDetails(@RequestParam(value="email", required=false) String email,
	    		@RequestParam(value="json", required=false) String json) {
			
		   ArrayList<String> userList = new ArrayList<String>();
			
		   User user = null;
		   
			try {
				user = userService.findByEmail(email);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(null != user)
			{
				userList.add(user.getFirstName());
				userList.add(user.getLastName());
				userList.add(user.getMobilePhone());
			}
			
			return commonUtil.toJSON(userList);
			
		}
	   
	   @PreAuthorize("hasRole('ROLE_ENROLL_ORG')")	   
	   @RequestMapping(value = "/app/orgSummary/{orgCode}" , method = RequestMethod.GET)
		 public @ResponseBody void orgSummary(@PathVariable String orgCode,HttpServletRequest request,HttpServletResponse response) throws Exception {
				StringBuilder redirectUrl = new StringBuilder();
				response.sendRedirect(redirectUrl.append("/app/enrolledOrgSummary?orgCode=").append(orgCode).toString());
				
		   }
	   
	   
	   
	   @RequestMapping(value = "/app/emailAndWebsiteRegistrationValidation",  method = RequestMethod.GET)
	   public @ResponseBody String emailAndWebsiteRegistrationValidation(@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites,
	   		@RequestParam(value="email", required=false) String email) throws Exception {
		   return userService.validateEmailandCandidate(website, email, sourceleadBLockedwebsites);
	   }
	   
	   
	    @RequestMapping(value = "/app/getRegisteredOrgsLocationsList")
		public @ResponseBody String getRegisteredOrgsLocationsList(HttpServletRequest request) {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgLocations> orgLocationsList = new ArrayList<>();

		ArrayList<OrgLocations> regOrgsLocationsList = new ArrayList<OrgLocations>();

		try {
			orgLocationsList = orgLocationService.getAllRegisteredOrgLocationsByOrgId(org.getId(),loginOrgLocation.getId());
			if(null != orgLocationsList && orgLocationsList.size() > 0){
			for(OrgLocations orgLocations : orgLocationsList){
				if(null != orgLocations.getUser()){
					
					StringBuilder userName = new StringBuilder();
					if(!isNullOrEmpty(orgLocations.getUser().getLastName())){
						userName = userName.append(orgLocations.getUser().getFirstName()).append(" ").append(orgLocations.getUser().getLastName());
					}else{
						userName = userName.append(orgLocations.getUser().getFirstName());
					}
					orgLocations.setOrglocationUserName(userName.toString());
					orgLocations.setOrglocationUserEmail(orgLocations.getUser().getEmail());
					orgLocations.setOrglocationUserPhone(orgLocations.getUser().getMobilePhone());
					orgLocations.setOrglocationUserId(orgLocations.getUser().getId());
					orgLocations.setOrgCode(org.getOrgCode());
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(),orgLocations.getId());
					if(null != userRole){
						orgLocations.setOrglocationUserStatus(userRole.getUserState().toString());
					}
				}
				
				regOrgsLocationsList.add(orgLocations);
			}
		  }
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		resultJson = commonUtil.toJSON(regOrgsLocationsList);
		return resultJson;

	}
	    
	    
	@RequestMapping(value = "/app/ws/activateEmail", method = RequestMethod.GET)
	public @ResponseBody ModelAndView activateEmail(@RequestParam(value="token", required=false) String token, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		String status = "";
		User user = userService.getUserByVerficationUUID(token);
		if(user != null){
			Date uuidSentTime = user.getUrlSentTime();
			Calendar cal = Calendar.getInstance(); // creates calendar
			Date current = cal.getTime();
		    cal.setTime(uuidSentTime); // sets calendar time/date
//		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
		    /**
		     * to check uuidSentTime and current time is within 24 hours, 
		     * below added one day to current time
		     */
		    cal.add(Calendar.DATE, 1);
		    Date addedDtime = cal.getTime();
			if((current.after(uuidSentTime) && current.before(addedDtime)) || current.equals(addedDtime)){
				String isVerified = user.getIsVerified();
				if(Constants.NO.equalsIgnoreCase(isVerified)){
					user.setIsVerified(Constants.YES);
					user.setUpdatedDate(new Date());
					userService.addUser(user);
					if(Constants.INDIVIDUAL_RECRUITER.equalsIgnoreCase(user.getUserType()))
						registrationService.sendEmailToIndividualRecruiter(user);
					else
						registrationService.sendEmailToProfessional(user);
					status = "success";
					// verified click to login
				}else{
					status = "already Verified";
				}
			}else{
				//url expiry
				status = "expired";
			}
			
			model.addObject("userId",user.getId());
			model.addObject("token",token);
			model.addObject("sourceleadUrl",sourceleadUrl);
		}else{
			//home page
			status = "home";
		}
		model.addObject("status", status);
		model.setViewName("verifyAccount");
		return model;
	}
	
	
	@RequestMapping(value = "/app/ws/resendVerificationEmail", method = RequestMethod.GET)
	public @ResponseBody String resendVerificationEmail(@RequestParam(value = "userId", required = false) Long userId,@RequestParam(value = "userName", required = false) String userName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = null;
		if(userId != null)
			user = userService.getUserById(userId);
		else
			user = userService.findByUserName(userName);
		if (Constants.NO.equalsIgnoreCase(user.getIsVerified())) {
			user.setVerificationUUID(RandomStringUtils.randomAlphanumeric(16));
			user.setUrlSentTime(new Date());
			user.setIsVerified(Constants.NO);
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			userService.addUser(user);
			registrationService.sendActivationEmail(user,userRole.getOrgLocations());
			return "success";
		}
		else {
			return "failure";
		}
	}
	   
	@RequestMapping(value = "/app/ws/verificationDone", method = RequestMethod.POST)
	public String verificationDone(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "userRoleId", required = false) long userRoleId, @RequestParam(value = "verfDone", required = false) String verfDone, HttpServletRequest request) throws Exception
	{
		UserRole userRole = null;
		User user = null;
		String actionUUID = null;
		if (userRoleId > 0)
			userRole = userRoleService.getUserRoleById(userRoleId);
		else {
			 user = userService.findByUserName(userName);
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		}
		
		
		StringBuilder generatedToken = new StringBuilder();
		generatedToken.append(SourceLeadRandomCodeHelper.generateRandomCustomToken());
		UserLoginLogoutSession userLoginSession = null;
	    if(userRole != null){
		    	userLoginSession = new UserLoginLogoutSession();
		    	String ipAddress = commonUtil.getClientIpAddr(request);
		    	userLoginSession.setIpaddress(ipAddress);
			    userLoginSession.setUserId(userRole.getUser());
				if (Constants.YES.equalsIgnoreCase(verfDone))
					userLoginSession.setAuthenticated(Constants.YES);
			    userLoginSession.setLoginTime(new Date());
			    actionUUID = RandomStringUtils.randomAlphanumeric(16);
			    userLoginSession.setActionUUID(actionUUID);
			    userLoginSession =  userLoginLogoutSessionService.storeUserLoginLogoutSession(userLoginSession);
			    request.getSession().setAttribute("userLoginSession", userLoginSession);
		    }
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(generatedToken.toString(),userRole,actionUUID,userLoginSession.getId(),request);
		}
		
		StringBuilder userNameString = new StringBuilder();
		userNameString.append(userName).append("##").append(userRole.getId()).append("##").append(generatedToken.toString());
		SecurityContextHolder.clearContext();
		SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, userNameString.toString(), password, verfDone));
		return "true";
	}
	
	@RequestMapping(value = "/app/ws/getUserDetailsByRoleId", method = RequestMethod.POST)
	public ModelAndView getUserDetailsByRoleId(@RequestParam(value = "id", required = false) Long roleId,@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "password", required = false) String password) throws Exception
	{
		ModelAndView model = new ModelAndView();
		UserRole userRole = null;
		if(roleId != null && roleId >0)
			userRole = userRoleService.getUserRoleById(roleId);
		else{
			User user = userService.findByUserName(userName);
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		}
		if(userRole.getUser() != null){
			model.addObject("userId",userRole.getUser().getId());
			model.addObject("userLoginSecurity",userRole.getUser().getLoginSecurity());
			model.addObject("userRoleId",userRole.getId());
			model.addObject("emailId",userRole.getUser().getEmail());
			if(StringUtils.isNotBlank(userRole.getUser().getMobilePhone()))
				model.addObject("mobilePhone",userRole.getUser().getMobilePhone());
		}
		if(userName != null)
			model.addObject("username",userName);
		if(password != null)
			model.addObject("password",password);
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("otpVerification");
		return model;
	}
	
	@RequestMapping(value = "/app/ws/sendOtpToUser", method = RequestMethod.GET)
	public ModelAndView sendOtpToUser(@RequestParam(value = "id", required = false) Long userId,@RequestParam(value = "type", required = false) String type,HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		User user = userService.getUserById(userId);
		String otp = registrationService.sendOtpToUser(user,type);
		if(user != null){
			model.addObject("emailId",user.getEmail());
			if(StringUtils.isNotBlank(user.getMobilePhone()))
				model.addObject("mobilePhone",user.getMobilePhone());
		}
		model.addObject("authToken",otp);
		model.addObject("status","success");
		model.addObject("sourceleadUrl",sourceleadUrl);
		model.setViewName("otpVerification");
		return model;
	}
	
	@RequestMapping(value = "/app/ws/validateWhitelistIp", method = RequestMethod.GET)
	public boolean validateWhitelistIp(@RequestParam(value = "userRoleId", required = false) long userRoleId,
			@RequestParam(value = "name", required = false) String userName,
			@RequestParam(value = "ipAddress", required = false) String ipAddress,
			@RequestParam(value = "whiteListId", required = false) Long whiteListId,
			@RequestParam(value = "accessingType", required = false) String accessingType,HttpServletRequest request) throws Exception
	{
		UserRole userRole = null;
		if (userRoleId > 0)
			userRole = userRoleService.getUserRoleById(userRoleId);
		else {
			User user = userService.findByUserName(userName);
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		}
		if ("".equalsIgnoreCase(ipAddress) || ipAddress == null) {
			ipAddress = commonUtil.getClientIpAddr(request);
		}
		
		return whiteListIpAddressService.validateIPAddress(ipAddress, userRole,whiteListId,accessingType);
	}
	
	@RequestMapping(value = "/app/ws/validateUserAlreadyAuthenticated", method = RequestMethod.GET)
	public boolean validateUserAlreadyAuthenticated(@RequestParam(value = "userRoleId", required = false) long userRoleId, @RequestParam(value = "name", required = false) String userName, HttpServletRequest request) throws Exception
	{
		UserRole userRole = null;
		if (userRoleId > 0)
			userRole = userRoleService.getUserRoleById(userRoleId);
		else {
			User user = userService.findByUserName(userName);
			userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		}
		String ipAddress = commonUtil.getClientIpAddr(request);
		boolean alreadyVerifiedBefore = userLoginLogoutSessionService.validatePreviouslyAuthenticatedByUserIdAndIpAddress(ipAddress, userRole.getUser().getId());
		return alreadyVerifiedBefore;
	}
	
	@RequestMapping(value = "/app/getTimezonesList")
	public @ResponseBody String getTimezonesList(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		resultJson = commonUtil.toJSON(timezoneList);
		return resultJson;
	}
	
	
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	}