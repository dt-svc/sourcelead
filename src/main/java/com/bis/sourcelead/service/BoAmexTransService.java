package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoAmexTrans;

public interface BoAmexTransService {
	
	public List<BoAmexTrans> getBoAmexTransByFilters(JSONObject jsonObj) throws Exception;
	
	public List<BoAmexTrans> getBoAmexTrans() throws Exception;

}
