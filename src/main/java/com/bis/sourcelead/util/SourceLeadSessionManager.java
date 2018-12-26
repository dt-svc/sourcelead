package com.bis.sourcelead.util;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bis.sourcelead.BeanUtil;
import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.couchbase.client.java.document.json.JsonObject;

public class SourceLeadSessionManager {
	
	
    public static boolean USE_SESSION_STORE = true;
	
	private CommonUtil commonUtil = BeanUtil.getBean(CommonUtil.class);
	static CouchbaseClient c = null;
	static{
		c = ConnectionManager.instance().getClient();
	}

	public static void setSessionAttribute(HttpServletRequest request, String attribute, Object obj) {
		request.getSession().setAttribute("SourceLeadWeb_"+attribute, obj);
	}
	
	public static Object getSessionAttribute(HttpServletRequest request, String attribute) {
		return request.getSession().getAttribute("SourceLeadWeb_"+attribute);
	}

	public static void removeSessionAttribute(HttpServletRequest request, String attribute) {
		request.getSession().removeAttribute("SourceLeadWeb_"+attribute);
	}
	
	
	public static void setUserProfileJsonString(String userName, String attribute, JsonObject jsonString) {
		String jsonKey = userName ;
		try {
			if (userName != null && attribute != null) {
					if (jsonString != null) {
						c.setUserProfile(userName, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Email is NuLL : " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void setUserDashboardStatsJsonString(String userName, String attribute, JsonObject jsonString) {
		String jsonKey = userName ;
		try {
			if (userName != null && attribute != null) {
					if (jsonString != null) {
						c.setUserDashboardStats(userName, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Email is NuLL : " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
public static JSONObject getDashboardJobsCountJsonObject(HttpServletRequest request, String userName,String locationId) {
		
		JSONObject jsonObjVal = null;
		String id = request.getSession().getId();
		Object sv = null;
		sv = c.getDashboardStats(userName);
		if(sv != null) {
		  
		  JSONParser parser = new JSONParser(); 
		  try {
			  JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
			  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				    String key = (String) iterator.next();
				     Object jsonValue = jsonObject.get(key);
				     jsonObjVal = (JSONObject) parser.parse(jsonValue.toString());
				     
				    /* JSONObject attributeJsonObject = (JSONObject) parser.parse(jsonObjVal.toString());
				     for(Iterator itr = attributeJsonObject.keySet().iterator(); itr.hasNext();) {
				    	    String entitykey = (String) itr.next();
				    	    if(entitykey.equalsIgnoreCase(attribute)) {
				    	    	 Object attributeValue = attributeJsonObject.get(entitykey);
							     jsonObjVal = (JSONObject) parser.parse(attributeValue.toString());
							     break;
				    	    }
				     }*/
				}
			  
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  }
		return jsonObjVal;
	}
}
