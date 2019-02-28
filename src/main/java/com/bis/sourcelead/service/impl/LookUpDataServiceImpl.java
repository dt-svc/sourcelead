package com.bis.sourcelead.service.impl;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.service.LookUpDataService;

/**
 * 
 * @author Prasad K
 * @date 30th Aug 2016
 *
 */

@Service
public class LookUpDataServiceImpl implements LookUpDataService {

	 @Autowired
	 LookUpDataDao lookUpDataDao;
	
	/**
	 * This method for to get all Educations(MCA,Btech,BSC,...etc) From DB
	 */
	
	@Override
	public List<LookUpData> getLookUpDataByCategory(String category) throws Exception {
		return lookUpDataDao.getLookUpDataByCategory(category);
	}
	
	@Override
	public LinkedHashSet<String> getLookUpDataByCategoryWithSortedOrder(String category) throws Exception {
		return lookUpDataDao.getLookUpDataByCategoryWithSortedOrder(category);
	}

	@Override
	public List<String> getAllSkillsBySkillCategory() throws Exception {
		return lookUpDataDao.getAllSkillsBySkillCategory();
	}

	@Override
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception {
		return lookUpDataDao.getSkillsBySkillsName(skillNames);
	}

	@Override
	public List<String> getLookUpDataByCategoryEducation(){
		return lookUpDataDao.getAllEducationsByEducationCategory();
	}
	
	@Override
	public List<String> getAllSkillsByIndustryCategory() throws Exception{
		return lookUpDataDao.getAllSkillsByIndustryCategory();
	}

	@Override
	public List<String> getAllVisaByCategory() throws Exception {
		return lookUpDataDao.getAllVisaByCategory();
	}

	@Override
	public LookUpData getTasksById(Long id) throws Exception {
		return lookUpDataDao.getTasksById(id);
	}
	
	@Override
	public LookUpData getLookUpdataByCategoryAndName(String category,String Name) throws Exception {
		return lookUpDataDao.getLookUpdataByCategoryAndName(category,Name);
	}

	@Override
	public LookUpData getTasksTypeByUniqueKey(String uniqueKey) throws Exception {
		return lookUpDataDao.getTasksTypeByUniqueKey(uniqueKey);
	}

	@Override
	public LookUpData getRequirementExperienceLevelyUniqueKey(String uniqueKey) throws Exception
	{
		return lookUpDataDao.getRequirementExperienceLevelyUniqueKey(uniqueKey);
	}

	@Override
	public LookUpData getLookUpDataByIdAndCategory(String visaType, long id) {

		return lookUpDataDao.getLookUpDataByIdAndCategory(visaType, id);
	}

	@Override
	public LookUpData saveLookupData(LookUpData lookUpData) throws Exception {
		return lookUpDataDao.saveLookupData(lookUpData);
	}

	@Override
	public List<LookUpData> getLookUpdataByCategoryAndOrgLocationId(String category, Long orgLocation) throws Exception {
		return lookUpDataDao.getLookUpdataByCategoryAndOrgLocationId(category, orgLocation);
	}

	@Override
	public void deleteLookupDateByLookupId(LookUpData lookUpData)throws Exception {
		lookUpDataDao.deleteLookupDateByLookupId(lookUpData);
	}

	@Override
	public List<LookUpData> getReferLookUpDataByCategory(String category) {
		return lookUpDataDao.getReferLookUpDataByCategory(category);
	}

	@Override
	public Boolean validateTaskTypeName(String taskTypeName,String category,Long orgLocationId) {
		return lookUpDataDao.validateTaskTypeName(taskTypeName,category,orgLocationId);
	}

	@Override
	public LookUpData validateTaskTypeNameByTaskNameAndLookupId(String taskTypeName, Long lookupId,String category,Long orgLocationId) {
		return lookUpDataDao.validateTaskTypeNameByTaskNameAndLookupId(taskTypeName, lookupId,category,orgLocationId);
	}

	@Override
	public List<String> getListOfStringLookUpdataByCategoryAndOrgLocationId(String category, Long orgLocation) throws Exception {
		return lookUpDataDao.getListOfStringLookUpdataByCategoryAndOrgLocationId(category, orgLocation);
	}

	@Override
	public List<String> getCategoriesListByCategoryType(String category) throws Exception {
		return lookUpDataDao.getCategoriesListByCategoryType(category);
	}
	
}
