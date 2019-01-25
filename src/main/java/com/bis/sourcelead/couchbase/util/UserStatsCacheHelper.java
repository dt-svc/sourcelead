package com.bis.sourcelead.couchbase.util;

import java.util.Iterator;

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
					orgLocationJson.put(key, jsonValue);
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orgLocationJson;
	}
	


}
