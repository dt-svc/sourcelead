package com.bis.sourcelead.dashboard.util;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DashBoardUtil {
	
	
	/**
	 * converting list of map to json String
	 * @param map
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getMapListToJsonString(List<Map<String, String>> list) {
		String convertedString ="";
		try {
			
			JSONArray jsonArray = new JSONArray();
			for(Map<String, String> map : list) {
				JSONObject jsonObj = new JSONObject();
				map.forEach((key, value) -> {
					jsonObj.put(key, value);
				});
				jsonArray.add(jsonObj);	
			}
	   convertedString = jsonArray.toString().replace("\"", "\\\"");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return convertedString;
		
	}

}
