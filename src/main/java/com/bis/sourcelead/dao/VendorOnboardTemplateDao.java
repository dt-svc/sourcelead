package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.VendorOnboardingTemplate;

public interface VendorOnboardTemplateDao {

	List<VendorOnboardingTemplate> getVendorOnBoardingTemplatesList(Long orgLocationId) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateById(Long templateId) throws Exception;
	
	public VendorOnboardingTemplate saveVendorOnBoardingTemplate(VendorOnboardingTemplate onboardingTemplate) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,Long orgLocationId) throws Exception;
	
	VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception;
	
	List<VendorOnboardingTemplate> getVendorOnBoardingFinishedTemplatesList(Long orgLocationId) throws Exception;
}
