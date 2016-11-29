package com.bis.sourcelead.dao;

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
	
	public List<String> getAllSkillsBySkillCategory() throws Exception;
	
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception;



			
	
}
