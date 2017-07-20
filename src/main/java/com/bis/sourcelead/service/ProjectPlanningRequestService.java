package com.bis.sourcelead.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningRequestService {

	public void saveProjectPlanningRequest(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,String projectUUID,String saveType) throws JSONException, Exception;
}
