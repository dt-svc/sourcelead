package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;

public interface VendorOnboardTemplateService {

	List<VendorOnboardingTemplate> getVendorOnBoardingTemplatesList(Long orgLocationId) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardTemplateDetails(OnBoardingTemplateDTO onBoardingTemplateDTO,User user,OrgLocations orgLocations) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateById(Long templateId) throws Exception;
	
	public VendorOnboardingTemplate saveVendorOnBoardingTemplate(VendorOnboardingTemplate onboardingTemplate) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,Long orgLocationId) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception;
	
	List<VendorOnboardingTemplate> getVendorOnBoardingFinishedTemplatesList(Long orgLocationId) throws Exception;
}
