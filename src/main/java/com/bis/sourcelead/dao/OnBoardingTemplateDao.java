package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OnBoardingTemplate;

public interface OnBoardingTemplateDao {
	
	List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception;

}
