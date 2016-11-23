package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoAmexTransDao;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.service.BoAmexTransService;

@Service
public class BoAmexTransServiceImpl implements BoAmexTransService {
	
	@Autowired
	BoAmexTransDao boAmexTransDao;
	
	@Override
	public List<BoAmexTrans> getBoAmexTransByFilters(JSONObject jsonObj) throws Exception {
		return boAmexTransDao.getBoAmexTransByFilters(jsonObj);
	}

	@Override
	public List<BoAmexTrans> getBoAmexTrans() throws Exception{
		return boAmexTransDao.getBoAmexTrans();
	}

}
