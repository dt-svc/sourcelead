package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoJmpcTransDao;
import com.bis.sourcelead.entity.BoJmpcTrans;
import com.bis.sourcelead.service.BoJmpcTransService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class BoJmpcTransServiceImpl implements BoJmpcTransService{

	@Autowired
	BoJmpcTransDao boJmpcTransDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public BoJmpcTrans getBoJmpcTransById(Long Id) throws Exception {
		return boJmpcTransDao.getBoJmpcTransById(Id);
	}
	
	@Override
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception {
		return boJmpcTransDao.getBoJmpcTransByFilters(jsonObj);
	}
	
	@Override
	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception {
		return boJmpcTransDao.getBoJmpcTrans();
	}

	@Override
	public BoJmpcTrans saveBoJmpcTrans(BoJmpcTrans boJmpcTrans) throws Exception {
		boJmpcTrans.setCreatedDate(commonUtil.currentDate());
		boJmpcTrans.setUpdatedDate(commonUtil.currentDate());
		return boJmpcTransDao.saveBoJmpcTrans(boJmpcTrans);
	}

}
