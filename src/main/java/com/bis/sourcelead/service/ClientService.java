package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ClientService {
	
	public void saveClient(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception;

}
