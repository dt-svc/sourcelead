package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public interface LookUpDataService {
	
	public List<LookUpData> getLookUpDataByEducation(String category) throws Exception;

}
