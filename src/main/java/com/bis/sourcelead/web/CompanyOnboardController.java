package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.dto.PaymentAccountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocationInformationDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationIndiaBankingDetailsService;
import com.bis.sourcelead.service.OrgLocationInformationDetailsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;
import com.bis.sourcelead.service.OrglocationOnboardDocumentsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class CompanyOnboardController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyOnboardController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ModulesService modulesService;
	
	@Autowired
	private StatesService statesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	OrglocationBankingDetailsService orglocationBankingDetailsService;
	
	@Autowired
	OrglocationOnboardDocumentsService orglocationOnboardDocumentsService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Autowired
	OrgLocationInformationDetailsService orgLocationInformationDetailsService;
	
	@Autowired
	OrgLocationIndiaBankingDetailsService orglocationIndiaBankingDetailsService;
	
	@Autowired
	private OrgService orgService;
	
	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;

	
		@RequestMapping(value = "/companyOnboard")
		public ModelAndView companyOnboard(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView(); 
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
				List<LookUpData> bussinessTypesList = lookUpDataService.getLookUpDataByCategory(Constants.COMPANY_ONBOARD_BUSINESS_TYPE);
				model.addObject("bussinessTypesList", bussinessTypesList);
				List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
				model.addObject("timezoneList", timezoneList);
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("onboard/companyOnboard");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
	
	
		@RequestMapping(value = "/companyOnboardLocations")
		public ModelAndView companyOnboardLocations(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
			List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
			
			
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
			 if(subscribedCountries != null && !subscribedCountries.isEmpty())
				 subscribedCountries = subscribedCountries.substring(0,subscribedCountries.length()-1);
			 
			model.addObject("orgSubscribedCountriesList", orgSubscribedCountriesList);
			model.addObject("subscribedCountries", subscribedCountries);
			
			model.addObject("timezoneList", timezoneList);
			model.addObject("orgLocations", orgLocations);
			model.setViewName("orgSettings");
			model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("onboard/companyOnboardLocations");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			return model;
		}
		
		@RequestMapping(value = "/companyOnboardFederalAndStateTaxes")
		public ModelAndView companyOnboardFederalAndStateTaxes(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
				List<States> states = statesService.getAllStatesByCountry(orgLocations.getCountry());
				model.addObject("statesList", states);
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("onboard/companyOnboardFederalAndStateTaxes");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
		
		
		@RequestMapping(value = "/companyOnboardInformationDetails")
		public ModelAndView companyOnboardInformationDetails(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
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
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("onboard/companyOnboardInformationDetails");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
		
		
		
		@RequestMapping(value = "/companyOnboardBankingDetails")
		public ModelAndView companyOnboardBankingDetails(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			 model.addObject("sourceleadUrl", sourceleadUrl);
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
				model.addObject("orgLocationId", orgLocations.getId());
				if(StringUtils.isNotBlank(orgLocations.getCountry()) && orgLocations.getCountry().equalsIgnoreCase("India")) {
					 List<PaymentAccountDTO> paymentList  = orgService.getPaymentAccountList(org.getId(), orgLocations.getId(), false);
					 model.addObject("paymentList", paymentList);
					 model.addObject("paymentListSize", paymentList.size());
					model.setViewName("onboard/companyOnboardIndiaBankingDetails");
				}else {
					 List<PaymentAccountDTO> paymentList  = orgService.getPaymentAccountList(org.getId(), orgLocations.getId(), false);
					 model.addObject("paymentList", paymentList);
					 model.addObject("paymentListSize", paymentList.size());
					model.setViewName("onboard/companyOnboardBankingDetails");
				}
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
		
		
		@RequestMapping(value = "/companyOnboardDocuments")
		public ModelAndView companyOnboardDocuments(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
				OrglocationOnboardDocuments businessLicense = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.ONBOARD_DOCUMENT_BUSNIESS_LICENSE, orgLocations.getId());
				if(businessLicense != null) {
					model.addObject("businessLicenseDoc", businessLicense.getFileUrl());
					model.addObject("businessLicenseDocName", businessLicense.getFileName());
					model.addObject("businessLicenseDocfileContentType", businessLicense.getFileContentType());
					model.addObject("businessLicenseDocfileUUID", businessLicense.getUuid());
				}
				
				OrglocationOnboardDocuments bankCheque = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.BANK_CHEQUE, orgLocations.getId());
				if(bankCheque != null) {
					model.addObject("bankChequeDoc", bankCheque.getFileUrl());
					model.addObject("bankChequeDocName", bankCheque.getFileName());
					model.addObject("bankChequefileContentType", bankCheque.getFileContentType());
					model.addObject("bankChequefileUUID", bankCheque.getUuid());
				}
				
				OrglocationOnboardDocuments registrationDoc  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.REGISTRATION_OR_INCORPORATION_OF_RECORDING, orgLocations.getId());
				if(registrationDoc != null) {
					model.addObject("registrationDoc", registrationDoc.getFileUrl());
					model.addObject("registrationDocName", registrationDoc.getFileName());
					model.addObject("registrationDocfileContentType", registrationDoc.getFileContentType());
					model.addObject("registrationDocUUID", registrationDoc.getUuid());
				}
				
				OrglocationOnboardDocuments annulReportBusiness  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.ANNUAL_REPORT_BUSINESS_LICENSE, orgLocations.getId());
				if(annulReportBusiness != null) {
					model.addObject("annulReportBusinessDoc", annulReportBusiness.getFileUrl());
					model.addObject("annulReportBusinessDocName", annulReportBusiness.getFileName());
					model.addObject("annulReportBusinessDocfileContentType", annulReportBusiness.getFileContentType());
					model.addObject("annulReportBusinessDocUUID", annulReportBusiness.getUuid());
				}
				
				OrglocationOnboardDocuments w9Doc  = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentByDocumentName(Constants.W9_W8_BEN_E, orgLocations.getId());
				if(w9Doc != null) {
					model.addObject("w9Doc", w9Doc.getFileUrl());
					model.addObject("w9DocName", w9Doc.getFileName());
					model.addObject("w9DocfileContentType", w9Doc.getFileContentType());
					model.addObject("w9DocUUID", w9Doc.getUuid());
				}
				
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("onboard/companyOnboardDocuments");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
		
		@RequestMapping(value = "/companyOnboardSignatory")
		public ModelAndView companyOnboardSignatory(HttpServletRequest request) throws Exception  {
			
			ModelAndView model = new ModelAndView();  
			Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			
			if(StringUtils.isNotBlank(orgLocations.getIsCompanyOnboarded()) && orgLocations.getIsCompanyOnboarded().equalsIgnoreCase("N")) {
				List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
			    model.addObject("rolesList", rolesList);
				
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("onboard/companyOnboardSignatory");
			}else {
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;
		}
		
		
		
		
		
}
