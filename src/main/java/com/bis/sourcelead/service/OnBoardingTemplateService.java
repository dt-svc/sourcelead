package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface OnBoardingTemplateService {

	List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception;
	
	public OnBoardingTemplate saveOnBoardingTemplate(OnBoardingTemplate onboardingTemplate) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateById(Long templateId) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,Long orgLocationId) throws Exception;
	
	OnBoardingTemplate getOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception;
	
	List<OnBoardingTemplate> getOnBoardingFinishedTemplatesList(Long orgLocationId) throws Exception;
	
	OnBoardingTemplate getOnBoardTemplateDetails(OnBoardingTemplateDTO onBoardingTemplateDTO,User user,OrgLocations orgLocations) throws Exception;
}
