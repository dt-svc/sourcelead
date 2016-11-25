package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoJmpcTrans;

public interface BoJmpcTransService {
	
	BoJmpcTrans getBoJmpcTransById(Long Id) throws Exception;
	
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception;
	
	public BoJmpcTrans saveBoJmpcTrans(BoJmpcTrans boJmpcTrans) throws Exception;
}
