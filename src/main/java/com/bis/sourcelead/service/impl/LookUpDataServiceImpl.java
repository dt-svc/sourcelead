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
	public List<LookUpData> getLookUpDataByEducation(String category) throws Exception {
		return lookUpDataDao.getLookUpDataByEducation(category);
	}

	
	
	
}
