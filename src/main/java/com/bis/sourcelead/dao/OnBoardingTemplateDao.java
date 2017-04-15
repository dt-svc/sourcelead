package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OnBoardingTemplate;

public interface OnBoardingTemplateDao {
	
	List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception;
	
	public OnBoardingTemplate saveOnBoardingTemplate(OnBoardingTemplate onboardingTemplate) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateById(Long templateId) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,Long orgLocationId) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception;
	
	List<OnBoardingTemplate> getOnBoardingFinishedTemplatesList(Long orgLocationId) throws Exception;

}
