package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.BoComericaTrans;

public interface BoComericaTransService {
	
	public List<BoComericaTrans> getBoComericaTransByFilters(JSONObject jsonObj) throws Exception;

	public List<BoComericaTrans> getBoComericaTrans() throws Exception;
	
	public BoComericaTrans saveBoComericaTrans(BoComericaTrans boComericaTrans) throws Exception;
}
