package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoAmexTransDao;
import com.bis.sourcelead.entity.BoAmexTrans;
import com.bis.sourcelead.service.BoAmexTransService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class BoAmexTransServiceImpl implements BoAmexTransService {
	
	@Autowired
	BoAmexTransDao boAmexTransDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public BoAmexTrans getBoAmexTransById(Long Id) throws Exception {
		return boAmexTransDao.getBoAmexTransById(Id);
	}
	
	@Override
	public List<BoAmexTrans> getBoAmexTransByFilters(JSONObject jsonObj) throws Exception {
		return boAmexTransDao.getBoAmexTransByFilters(jsonObj);
	}

	@Override
	public List<BoAmexTrans> getBoAmexTrans() throws Exception{
		return boAmexTransDao.getBoAmexTrans();
	}

	@Override
	public BoAmexTrans saveBoAmexTrans(BoAmexTrans boAmexTrans) throws Exception {
		boAmexTrans.setCreatedDate(commonUtil.currentDate());
		boAmexTrans.setUpdatedDate(commonUtil.currentDate());
		return boAmexTransDao.saveBoAmexTrans(boAmexTrans);
	}

}
