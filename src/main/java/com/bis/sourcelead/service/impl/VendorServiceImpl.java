package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.NotificationsDao;
import com.bis.sourcelead.dao.OrgLicenseSubscriptionDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgBankDetailsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class VendorServiceImpl implements VendorService{
	
	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;

	@Autowired
	UserService userService;
	
	@Autowired
	OrgSpocDao orgSpocDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardDao vendorOnboardDao;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	OrgBankDetailsService orgBankDetailsService;
	
	@Autowired
	NotificationsDao notificationsDao;
	
	@Autowired
	OrgLicenseSubscriptionDao orgLicenseSubscriptionDao;
	
	@Override
	public OrgLocations updateVendor(ClientDTO clientDTO, AddUserDTO addUserDTO, CandidateDTO candidateDTO, User user,
			OrgLocations orgLocations, Long orgRelationId, Org org) throws Exception {
		
		OrgRelations orgRelations = orgRelationsDao.getOrgRelationById(orgRelationId);
		User spocUser = null;
		List<Long> updatedSpocs = new ArrayList<Long>();
		List<Long> savedSpocs = new ArrayList<Long>();
		String country = null;
		
		orgRelations.setResponderLevel(clientDTO.getLevel());
		 if(clientDTO.getTemplateId() != null && clientDTO.getTemplateId().size() > 0){
		    	StringBuilder templateIds = new StringBuilder();
		    	for(String templateId : clientDTO.getTemplateId()){
		    		templateIds.append(templateId.trim()).append(",");
		    	}
		    	String templateIdsStr = templateIds.toString();
		    	
		    	orgRelations.setVendorOnboardTemplateId(templateIdsStr.substring(0, templateIdsStr.length()-1)); 
		    }else{
		    	orgRelations.setVendorOnboardTemplateId(null);
		    }
		 
		 if(StringUtils.isNotBlank(clientDTO.getPaymentTerms())){
			 orgRelations.setPaymentTerm(clientDTO.getPaymentTerms());
		 }
		 
		 if (StringUtils.isNotBlank(clientDTO.getIsLawFirm()) && clientDTO.getIsLawFirm().equalsIgnoreCase("Y")) {
					orgRelations.setIsLawFirm(Constants.YES);
			} else {
				orgRelations.setIsLawFirm(Constants.NO);
			}
			if (StringUtils.isNotBlank(clientDTO.getIsAccountingFirm()) && clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")) {
					orgRelations.setIsAccountingFirm(Constants.YES);
			} else {
				orgRelations.setIsAccountingFirm(Constants.NO);
			}
		 
		 orgRelationsDao.addOrgRelations(orgRelations);
		 
		 OrgLocations vendorOrgLocations = null;
		 if(orgRelations.getLocationId().getId().equals(orgLocations.getId())){
			 vendorOrgLocations = orgRelations.getResponderLocationId();
			 country = vendorOrgLocations.getCountry();
		 }else{
			 vendorOrgLocations = orgRelations.getLocationId();
			 country = vendorOrgLocations.getCountry();
		 }
		 
		 vendorOrgLocations.setLocation(clientDTO.getLocation());
		 vendorOrgLocations.setCountry(clientDTO.getCountry());
		 vendorOrgLocations.setState(clientDTO.getState());
		 vendorOrgLocations.setCity(clientDTO.getCity());
		 
		 if((StringUtils.isNotBlank(clientDTO.getIsLawFirm())) && (StringUtils.isNotBlank(clientDTO.getIsAccountingFirm()))){
		 if(clientDTO.getIsLawFirm().equalsIgnoreCase("Y") && clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")){
			vendorOrgLocations.setIsLawFirm(Constants.YES);
			vendorOrgLocations.setIsAccountingFirm(Constants.YES);
		}
		}else{
			vendorOrgLocations.setIsLawFirm(Constants.NO);
			vendorOrgLocations.setIsAccountingFirm(Constants.NO);	
		}
		
		 if(StringUtils.isNotBlank(clientDTO.getWebSite())){
			 if(StringUtils.isNotBlank(clientDTO.getWebSite())){
				 vendorOrgLocations.setWebsite(convertWebsite(clientDTO.getWebSite()));
				}
		 }
		
		 if(StringUtils.isNotBlank(clientDTO.getAddress())){
			 vendorOrgLocations.setOrgLocationAddress(clientDTO.getAddress());
		 }
		 
		 if(StringUtils.isNotBlank(clientDTO.getBillingAddress())){
			 vendorOrgLocations.setBillingAddress(clientDTO.getBillingAddress());
		 }
		 
		 orgLocationsDao.updateOrgLocations(vendorOrgLocations);
		 OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionDao.getOrgLicenseSubscriptionByOrgIdAndCountry(vendorOrgLocations.getOrg().getId(),country);
		 orgLicenseSubscription.setCountry(vendorOrgLocations.getCountry());
		 orgLicenseSubscriptionDao.saveOrgLicenseSubscription(orgLicenseSubscription);
		 
		 if (StringUtils.isNotBlank(clientDTO.getEmail())) {
				spocUser = userService.storeUser(addUserDTO,candidateDTO, user, vendorOrgLocations, "CREATEUSER", "Vendor", org);

			}
		 
		 
			if(clientDTO.getOwner() != null && !clientDTO.getOwner().isEmpty()){
				for(String owners :clientDTO.getOwner()){
					OrgSpoc orgSpoc = orgSpocDao.getClientUserByOrgRelationIdAndOwnerUserId(orgRelations.getId(), Long.parseLong(owners));
					if(orgSpoc != null){
						orgSpoc.setVendorUser(spocUser);
						User clientUser = new User();
						clientUser.setId(Long.valueOf(owners));
						orgSpoc.setOwnerUser(clientUser);
						orgSpoc.setClientUser(clientUser);
						orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
						orgSpocDao.addOrgSpoc(orgSpoc);
						updatedSpocs.add(orgSpoc.getId());
					}else{
						orgSpoc = new OrgSpoc();
						orgSpoc.setClientLocationId(orgLocations);
						orgSpoc.setVendorLocationId(vendorOrgLocations);
						User clientUser = new User();
						clientUser.setId(Long.valueOf(owners));
						orgSpoc.setClientUser(clientUser);
						orgSpoc.setVendorUser(spocUser);
						orgSpoc.setOwnerUser(clientUser);
						orgSpoc.setOrgRelations(orgRelations);
						
						if (StringUtils.isNotBlank(clientDTO.getSpocStatus())) {
							orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
						} else {
							orgSpoc.setSpocStatus(Constants.ACTIVE);
						}
						if (StringUtils.isNotBlank(clientDTO.getIsMailSent())) {
							orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
						} else {
							orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
						}
						String UUID = SourceLeadRandomCodeHelper.generateUUID();
						orgSpoc.setUUID(UUID);
						orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
						orgSpoc.setCreatedDate(commonUtil.currentDate());
						orgSpoc.setCreatedBy(user.getUserCode());
						orgSpoc.setUpdatedDate(commonUtil.currentDate());
						orgSpoc.setUpdatedBy(user.getUserCode());
						orgSpocDao.addOrgSpoc(orgSpoc);
						updatedSpocs.add(orgSpoc.getId());
					}
				}
			}
			
			
			Set<OrgSpoc> spocsList = orgRelations.getOrgSpocList();
			for(OrgSpoc spoc : spocsList){
				if(spoc.getStatus().equalsIgnoreCase(Constants.MEMBER_ACTIVE)){
					savedSpocs.add(spoc.getId());
				}
			}
			
			updatedSpocs.retainAll(savedSpocs);
			savedSpocs.removeAll(updatedSpocs);
			
			if(savedSpocs != null && savedSpocs.size() > 0){
				for(Long spocId : savedSpocs){
					OrgSpoc spoc = orgSpocDao.getOrgSpocById(spocId);
					spoc.setStatus(Constants.MEMBER_IN_ACTIVE);
					orgSpocDao.addOrgSpoc(spoc);
				}
			}
			
			VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingByOrgRelationId(orgRelations.getId());
			if(vendorOnboarding != null){
				vendorOnboarding.setSpocUserId(spocUser);
				vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
			}
	
			SubmissionTemplate submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(
					vendorOrgLocations.getId(), orgLocations.getId());
			
			clientService.saveOrUpdateClientVendorElkDocument(org, vendorOrgLocations, "Vendor", null, orgRelations,
					submissionTemplate, user, orgLocations);
		 
		return vendorOrgLocations;
	}
	
	
	private String convertWebsite(String website) {
		if (!website.startsWith("http") && !website.startsWith("https")) {
			website = "http://" + website;
		}
		URL netUrl = null;
		try {
			netUrl = new URL(website);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String host = netUrl.getHost();
		if (host.startsWith("www")) {
			host = host.substring("www".length() + 1);
		}
		return host;
	}


	@Override
	public VendorOnboardDTO getVendorOnboardDetails(Long vendorOnboardId,String notificationUUID) throws Exception {
		
		VendorOnboardDTO onboardDTO = new  VendorOnboardDTO();
		VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingById(vendorOnboardId);
		OrgLocations clientOrgLocations = vendorOnboarding.getOrgLocationsId();
		onboardDTO.setClientName(clientOrgLocations.getOrgLocationName());
		onboardDTO.setClientWebsite(clientOrgLocations.getWebsite());
		onboardDTO.setClientLocation(clientOrgLocations.getLocation());
		onboardDTO.setClientAddress(clientOrgLocations.getOrgLocationAddress());
		
		OrgLocations vendorOrgLocations = vendorOnboarding.getVendorLocationId();
		onboardDTO.setVendorName(vendorOrgLocations.getOrgLocationName());
		onboardDTO.setVendorWebsite(vendorOrgLocations.getWebsite());
		onboardDTO.setVendorLocation(vendorOrgLocations.getLocation());
		onboardDTO.setVendorAddress(vendorOrgLocations.getOrgLocationAddress());
		onboardDTO.setVendorBillingAddress(vendorOrgLocations.getBillingAddress());
		
		
		Notifications notifications = notificationsDao.getNotificationDetailsByUUID(notificationUUID);
		OrgSpoc orgSpoc = orgSpocDao.getSpocBySpocUUID(notifications.getSpocUUID());
		User vendorContact = orgSpoc.getOwnerUser();
		onboardDTO.setClientContactName(vendorContact.getFullName());
		onboardDTO.setClientContactEmail(vendorContact.getEmail());
		onboardDTO.setClientContactPhone(vendorContact.getMobilePhone());
		
		OrgRelations orgRelations = vendorOnboarding.getOrgRelationId();
		onboardDTO.setPaymentTerm(orgRelations.getPaymentTerm());
		onboardDTO.setTier(orgRelations.getInitiatorLevel());
		onboardDTO.setSicCode(orgRelations.getSicCode());
		onboardDTO.setNaicsCode(orgRelations.getNaicsCode());
		onboardDTO.setEinCode(orgRelations.getEinCode());
		onboardDTO.setIndustryName(orgRelations.getIndustryName());
		onboardDTO.setCurrency(orgRelations.getCurrency());
		onboardDTO.setTaxRegNumber(orgRelations.getTaxRegistrationNumber());
		onboardDTO.setDunsNumber(orgRelations.getDunsNumber());
		
		if(orgRelations.getBankDetailsId() != null){
			OrgBankDetails bankDetails = orgBankDetailsService.getOrgBankDetailsById(orgRelations.getBankDetailsId().getId());
			onboardDTO.setAccountName(bankDetails.getAccountName());
			onboardDTO.setAccountNumber(bankDetails.getAccountNumber());
			onboardDTO.setBankName(bankDetails.getBankName());
			onboardDTO.setBranchNumber(bankDetails.getBranchNumber());
			onboardDTO.setBicNumber(bankDetails.getBankIdentifierCode());
			onboardDTO.setBankAddress(bankDetails.getBankAddress());
			onboardDTO.setPoEmail(bankDetails.getPoEmail());
			onboardDTO.setPaymentRemittanceEmail(bankDetails.getPaymentRemittanceEmail());
		}
		
		return onboardDTO;
	}

}
