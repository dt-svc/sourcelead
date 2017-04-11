package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OnBoardingTemplate;

public interface OnBoardingTemplateService {

	List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception;
}
