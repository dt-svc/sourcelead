package com.bis.sourcelead.util;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.BeanUtil;
import com.bis.sourcelead.couchbase.ConnectionManager;
import com.bis.sourcelead.couchbase.CouchbaseClient;

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
	
	
	public static void setUserProfileJsonString(String userName, String attribute, String jsonString) {
		String jsonKey = "" + userName + "_SourceLeadWeb_" + attribute;
		try {
			if (userName != null && attribute != null) {
					if (jsonString != null) {
						c.setUserProfile(userName + "_" + attribute, jsonKey, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("Email is NuLL : " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
