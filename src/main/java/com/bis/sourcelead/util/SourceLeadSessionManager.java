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
	
	
	public  void setCouchbaseBulkImportJsonString(String key, String attribute, JsonObject jsonString) {
		try {
			if (key != null && attribute != null) {
					if (jsonString != null) {
						couchbaseClient.setBulkImportDocuments(key, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("key is NuLL : " +  key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void setCouchbaseQueue(String documentQueueId, String documentId) {
		try {
			if (documentQueueId != null && documentId != null) {
				couchbaseClient.setCouchbaseQueue(documentQueueId, documentId);
			} else
				System.out.println("Queue is Null");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void setCouchbaseQueueJsonString(String key, String attribute, JsonObject jsonString) {
		try {
			if (key != null && attribute != null) {
					if (jsonString != null) {
						couchbaseClient.setCoucuchbaseQueueDocument(key, jsonString);
					} else
						System.out.println("Json String is NuLL : " + attribute);
			} else
				System.out.println("key is NuLL : " +  key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
