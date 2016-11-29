package com.bis.sourcelead.service.impl;

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
	public List<String> getAllSkillsBySkillCategory() throws Exception {
		return lookUpDataDao.getAllSkillsBySkillCategory();
	}

	@Override
	public List<LookUpData> getSkillsBySkillsName(List<String> skillNames) throws Exception {
		return lookUpDataDao.getSkillsBySkillsName(skillNames);
	}

	
	
	
}
