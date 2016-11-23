package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoJmpcTransDao;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.service.BoJmpcTransService;

@Service
public class BoJmpcTransServiceImpl implements BoJmpcTransService{

	@Autowired
	BoJmpcTransDao boJmpcTransDao;
	
	@Override
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception {
		return boJmpcTransDao.getBoJmpcTransByFilters(jsonObj);
	}
	
	@Override
	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception {
		return boJmpcTransDao.getBoJmpcTrans();
	}

}
