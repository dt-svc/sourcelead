package com.bis.sourcelead.util;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.couchbase.client.java.document.json.JsonObject;

public class SourceLeadSessionManager {
	
	
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
}
