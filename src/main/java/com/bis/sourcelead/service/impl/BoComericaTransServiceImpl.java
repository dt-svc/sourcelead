package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.BoComericaTransDao;
import com.bis.sourcelead.entity.BoComericaTrans;
import com.bis.sourcelead.service.BoComericaTransService;

@Service
public class BoComericaTransServiceImpl implements BoComericaTransService{

	@Autowired
	BoComericaTransDao boComericaTransDao;
	
	@Override
	public List<BoComericaTrans> getBoComericaTransByFilters(JSONObject jsonObj) throws Exception {
		return boComericaTransDao.getBoComericaTransByFilters(jsonObj);
	}
	
	@Override
	public List<BoComericaTrans> getBoComericaTrans() throws Exception {
		return boComericaTransDao.getBoComericaTrans();
	}

	@Override
	public BoComericaTrans saveBoComericaTrans(BoComericaTrans boComericaTrans) throws Exception {
		return boComericaTransDao.saveBoComericaTrans(boComericaTrans);
	}

	
}
