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
	
	

}
