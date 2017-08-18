package com.bis.sourcelead.dao;

import java.util.LinkedHashSet;
import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

/**
 * 
 * @author Prasad K
 * @date 30th Aug 2016
 *
 */
public interface LookUpDataDao {

	public List<LookUpData> getLookUpDataByCategory(String category) throws Exception;
	
	public LinkedHashSet<String> getLookUpDataByCategoryWithSortedOrder(String category) throws Exception;
	
	public List<String> getAllSkillsBySkillCategory() throws Exception;
	
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception;

	public List<String> getAllEducationsByEducationCategory();
	
	public List<String> getAllSkillsByIndustryCategory()throws Exception;

	public List<String> getAllVisaByCategory()throws Exception;
	
	LookUpData getTasksById(Long id) throws Exception;
}
