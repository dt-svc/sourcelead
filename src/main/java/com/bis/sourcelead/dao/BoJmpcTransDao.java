package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoJmpcTrans;

public interface BoJmpcTransDao {
	
	BoJmpcTrans getBoJmpcTransById(Long Id) throws Exception;
	
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception;
	
	public BoJmpcTrans saveBoJmpcTrans(BoJmpcTrans boJmpcTrans) throws Exception;
}
