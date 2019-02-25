package com.bis.sourcelead.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.couchbase.client.java.document.json.JsonObject;

@Component
public class SourceLeadSessionManager {
	
	@Autowired
	CouchbaseClient couchbaseClient;

	public static void setSessionAttribute(HttpServletRequest request, String attribute, Object obj) {
		request.getSession().setAttribute("SourceLeadWeb_"+attribute, obj);
	}
	
	public static Object getSessionAttribute(HttpServletRequest request, String attribute) {
		return request.getSession().getAttribute("SourceLeadWeb_"+attribute);
	}

	public static void removeSessionAttribute(HttpServletRequest request, String attribute) {
		request.getSession().removeAttribute("SourceLeadWeb_"+attribute);
	}
	
	public  void deleteUserLoginSessionFromBucket(String sessionId) {
		couchbaseClient.deleteSessionDocumentsFromBucket(sessionId);
	}
	
	
	public  void setUserProfileJsonString(String userName, String attribute, JsonObject jsonString) {
		String jsonKey = userName ;
		try {
			if (userName != null && attribute != null) {
					if (jsonString != null) {
						couchbaseClient.setUserProfile(userName, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Email is NuLL : " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public  void setOrgLocationsJsonString(String userName, String attribute, JsonObject jsonString) {
		String jsonKey = userName ;
		try {
			if (userName != null && attribute != null) {
					if (jsonString != null) {
						couchbaseClient.setOrgLocations(userName, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Email is NuLL : " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public  void setUserLoginSessionJsonString(String sessionId, String attribute, JsonObject jsonString) {
		String jsonKey = sessionId ;
		try {
			if (sessionId != null && attribute != null) {
					if (jsonString != null) {
						couchbaseClient.setUserLoginSession(sessionId, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Session Id is NuLL : " + sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
