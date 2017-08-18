package com.bis.sourcelead.service;

import java.util.LinkedHashSet;
import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public interface LookUpDataService {
	
	public List<LookUpData> getLookUpDataByCategory(String category) throws Exception;
	
	public LinkedHashSet<String> getLookUpDataByCategoryWithSortedOrder(String category) throws Exception;
	
	public List<String> getAllSkillsBySkillCategory()throws Exception;
	
	public List<String> getAllSkillsByIndustryCategory()throws Exception;
	
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception;

	public List<String> getLookUpDataByCategoryEducation();

	public List<String> getAllVisaByCategory()throws Exception;
	
	LookUpData getTasksById(Long id) throws Exception;
}
