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
	
}
