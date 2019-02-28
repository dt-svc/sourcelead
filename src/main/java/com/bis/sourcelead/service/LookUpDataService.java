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
	
	LookUpData saveLookupData(LookUpData lookUpData)throws Exception;
	
	LookUpData getLookUpdataByCategoryAndName(String category,String Name) throws Exception;
	
	List<LookUpData> getLookUpdataByCategoryAndOrgLocationId(String category,Long orgLocation) throws Exception;
	
	List<String> getListOfStringLookUpdataByCategoryAndOrgLocationId(String category,Long orgLocation) throws Exception;
	
	LookUpData getTasksTypeByUniqueKey(String uniqueKey) throws Exception;
	
	LookUpData getRequirementExperienceLevelyUniqueKey(String uniqueKey) throws Exception;

	public LookUpData getLookUpDataByIdAndCategory(String visaType, long id);
  
	void deleteLookupDateByLookupId(LookUpData lookUpData)throws Exception;

	public List<LookUpData> getReferLookUpDataByCategory(String category);

	public Boolean validateTaskTypeName(String taskTypeName, String category,Long orgLocationId);

	public LookUpData validateTaskTypeNameByTaskNameAndLookupId(String taskTypeName, Long lookupId, String category, Long orgLocationId);
	
	List<String> getCategoriesListByCategoryType(String category) throws Exception;
}
