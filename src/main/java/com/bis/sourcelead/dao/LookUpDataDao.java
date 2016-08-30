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

	public List<LookUpData> getLookUpDataByEducation(String category) throws Exception;
	
}
