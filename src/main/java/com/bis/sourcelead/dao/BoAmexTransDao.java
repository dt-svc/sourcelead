package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoAmexTrans;

public interface BoAmexTransDao {
	
	public List<BoAmexTrans> getBoAmexTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoAmexTrans> getBoAmexTrans() throws Exception;
}
