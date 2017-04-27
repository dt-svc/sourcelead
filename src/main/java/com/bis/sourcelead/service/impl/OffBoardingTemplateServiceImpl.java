package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OffBoardingTemplateDao;
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.service.OffBoardingTemplateService;



@Service
public class OffBoardingTemplateServiceImpl implements OffBoardingTemplateService {
	
	@Autowired
	OffBoardingTemplateDao offBoardingTemplateDao;

	@Override
	public List<OffBoardingTemplate> getOffBoardingTemplatesList(Long orgLocationId) throws Exception {
		return offBoardingTemplateDao.getOffBoardingTemplatesList(orgLocationId);
	}

	@Override
	public OffBoardingTemplate saveOffBoardingTemplate(OffBoardingTemplate offboardingTemplate) throws Exception {
		return offBoardingTemplateDao.saveOffBoardingTemplate(offboardingTemplate);
	}

	@Override
	public OffBoardingTemplate getOffBoardingTemplateById(Long templateId) throws Exception {
		return offBoardingTemplateDao.getOffBoardingTemplateById(templateId);
	}

	@Override
	public OffBoardingTemplate getOffBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		return offBoardingTemplateDao.getOffBoardingTemplateByTemplateNameAndOrgLocationId(templateName, orgLocationId);
	}

	@Override
	public OffBoardingTemplate getOffBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {
		return offBoardingTemplateDao.getOffBoardingTemplateByTemplateUUID(templateUUID);
	}

	@Override
	public List<OffBoardingTemplate> getOffBoardingFinishedTemplatesList(Long orgLocationId) throws Exception {
		return offBoardingTemplateDao.getOffBoardingFinishedTemplatesList(orgLocationId);
	}

}
