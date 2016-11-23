package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoComericaTrans;

public interface BoComericaTransDao {
	
	public List<BoComericaTrans> getBoComericaTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoComericaTrans> getBoComericaTrans() throws Exception;
}
