package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OffBoardingTemplate;

public interface OffBoardingTemplateService {
	
	List<OffBoardingTemplate> getOffBoardingTemplatesList(Long orgLocationId) throws Exception;
	
    public OffBoardingTemplate saveOffBoardingTemplate(OffBoardingTemplate offboardingTemplate) throws Exception;
	
    OffBoardingTemplate getOffBoardingTemplateById(Long templateId) throws Exception;
	
    OffBoardingTemplate getOffBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,Long orgLocationId) throws Exception;
	
    OffBoardingTemplate getOffBoardingTemplateByTemplateUUID(String templateUUID) throws Exception;
	
	List<OffBoardingTemplate> getOffBoardingFinishedTemplatesList(Long orgLocationId) throws Exception;

}
