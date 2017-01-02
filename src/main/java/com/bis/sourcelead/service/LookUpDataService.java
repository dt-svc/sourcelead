package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public interface LookUpDataService {
	
	public List<LookUpData> getLookUpDataByCategory(String category) throws Exception;
	
	public List<String> getAllSkillsBySkillCategory()throws Exception;
	
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception;


	

}
