package com.bis.sourcelead.couchbase.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.CouchbaseClient;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/** 
 * Purpose : 
 */

@Component
public class UserStatsCacheHelper {
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	TabsService tabsService;
	
	@Autowired
	CandidateService candiateService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CouchbaseClient couchbaseClient;
	
	@Autowired
	SourceLeadSessionManager sourceLeadSessionManager;
	
	@Autowired
	UserService userService;
	
	
	
	public List<String> getCouchbaseQueueDocumentsList(String queueKey) {

		Object sv = null;
		List<String> docsList = new ArrayList<>();
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(queueKey);
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
						if (keyStr.equalsIgnoreCase("queueDocumentsList")) {
							String keyvalue = jsonObjVal.get(keyStr).toString();
							String[] docsStringArray = keyvalue.toString().substring(1, keyvalue.toString().length()-1).split(",");
							for(int i=0; i<docsStringArray.length; i++) {
								docsList.add(docsStringArray[i].substring(1, docsStringArray[i].length()-1));
							}
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return docsList;
	}
	
	
	
	public JSONObject getUploadedQueueUserDetails(String queueKey) {

		JSONObject queueDetailsListJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(queueKey);
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
						if (!keyStr.equalsIgnoreCase("queueDocumentsList")) {
							Object keyvalue = jsonObjVal.get(keyStr);
							queueDetailsListJson.put(keyStr, keyvalue);
						}

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return queueDetailsListJson;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getCandidateDocument(String dockey) {
		JSONObject orgLocationJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(dockey);
		if (sv != null) {

			JSONParser parser = new JSONParser();
			try {

				JSONObject jsonObject = (JSONObject) parser.parse(sv.toString());
				for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object jsonValue = jsonObject.get(key);
					orgLocationJson = (JSONObject) parser.parse(jsonValue.toString());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return orgLocationJson;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getBulkImportsFailureList(String dockey) {

		JSONObject failureUploadJson = new JSONObject();
		Object sv = null;
		sv = couchbaseClient.getCouchbaseQueueDocumentsList(dockey);
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
						failureUploadJson.put(keyStr, keyvalue);

					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return failureUploadJson;
	
	}
	
	
	
	
	
}
