package com.bis.sourcelead.couchbase.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;

/** 
 * Purpose : 
 */

@Component
public class UserStatsCacheHelper {

	static CouchbaseClient c = null;
	static {
		c = ConnectionManager.instance().getClient();
	}
	
	/**
	 * In what case is this method used for get orgLocations details from couch by passing key as orgLocationCode.
	 * 
	 * @param orgLocationCode
	 * @return Returns the orglocation details as JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getOrgLocationCacheDetails(String orgLocationCode) {
		JSONObject orgLocationJson = new JSONObject();
		Object sv = null;
		sv = c.getOrgLocationsDetails(orgLocationCode);
		if (sv != null) {

			JSONParser parser = new JSONParser();
			try {

				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						Object keyvalue = jsonObjVal.get(keyStr);
						orgLocationJson.put(keyStr, keyvalue);
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orgLocationJson;
	}
	

	/**
	 * 
	 * @param userLocKey username##role
	 * @return Map of stats for the user for the current location
	 */
	public JSONObject getLogInUserRoleStats(String userRoleAuthKey) {

		JSONObject userRoleJson = new JSONObject();
		Object sv = null;
		sv = c.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						Object keyvalue = jsonObjVal.get(keyStr);
						if (!keyStr.equalsIgnoreCase("menuList") && !keyStr.equalsIgnoreCase("rolesList")) {
							userRoleJson.put(keyStr, keyvalue);
						}
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userRoleJson;
	}
	
	
	/**
	 * 
	 * @param userLocKey username##role
	 * @return Map of stats for the user for the current location
	 */
	public JSONObject getLogInUserRoleMenusJson(String userRoleAuthKey) {

		JSONObject userRoleMenusListJson = new JSONObject();
		Object sv = null;
		sv = c.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("menuList")) {
							Object keyvalue = jsonObjVal.get(keyStr);
							userRoleMenusListJson.put(keyStr, keyvalue);
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userRoleMenusListJson;
	}
	
	
	
	
	/**
	 * 
	 * @param userLocKey username##role
	 * @return Map of stats for the user for the current location
	 */
	public List<String> getLogInUserRoleMenusList(String userRoleAuthKey) {

		Object sv = null;
		List<String> menusList = new ArrayList<>();
		sv = c.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("menuList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] menusStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<menusStringArray.length; i++) {
								menusList.add(menusStringArray[i].substring(1, menusStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return menusList;
	}

	
	
	/**
	 * 
	 * @param userLocKey username##role
	 * @return Map of stats for the user for the current location
	 */
	public List<String> getLogInUserRoleActionsList(String userRoleAuthKey) {

		Object sv = null;
		List<String> rolesList = new ArrayList<>();
		sv = c.getUserProfile(userRoleAuthKey);
		if (sv != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					JSONObject jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
					for (Object okey : jsonObjVal.keySet()) {
						String keyStr = (String) okey;
						if (keyStr.equalsIgnoreCase("rolesList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] rolesStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<rolesStringArray.length; i++) {
								rolesList.add(rolesStringArray[i].substring(1, rolesStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return rolesList;
	}
	
	
	
	public List<String> getLoginUserAccounts(String loginUserCode){
		List<String> userAccountsList = c.getLoginUserAccounts(loginUserCode);
		return userAccountsList;
	}
}
