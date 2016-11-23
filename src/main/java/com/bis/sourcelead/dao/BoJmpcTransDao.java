package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoJmpcTrans;

public interface BoJmpcTransDao {
	
	public List<BoJmpcTrans> getBoJmpcTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoJmpcTrans> getBoJmpcTrans() throws Exception;
}
