package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OnBoardingTemplateDao;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.service.OnBoardingTemplateService;

@Service
public class OnBoardingTemplateServiceImpl implements OnBoardingTemplateService{

	@Autowired
	OnBoardingTemplateDao onBoardingTemplateDao;

	@Override
	public List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplatesList(orgLocationId);
	}

	@Override
	public OnBoardingTemplate saveOnBoardingTemplate(OnBoardingTemplate onboardingTemplate) throws Exception {
		return onBoardingTemplateDao.saveOnBoardingTemplate(onboardingTemplate);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateById(Long templateId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateById(templateId);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName, orgLocationId);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateByTemplateUUID(templateUUID);
	}
	
	

}
