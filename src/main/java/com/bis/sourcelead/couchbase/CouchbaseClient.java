package com.bis.sourcelead.couchbase;

import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.datastructures.collections.CouchbaseQueue;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.google.gson.Gson;


@Component
public class CouchbaseClient {
	
	@Autowired
	CouchbaseClusterService couchbaseClusterService;
	
     ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
     CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
   
	public void deleteSessionDocumentsFromBucket(String key) {
			
	/*	userLoginSessionStoreBucket.bucketManager().createN1qlPrimaryIndex(true, false);
			N1qlQueryResult result = userLoginSessionStoreBucket.query(N1qlQuery.simple("SELECT meta().id FROM `userLoginSessionStore`"));
			if(result != null) {
				for (N1qlQueryRow row : result) {
					  try {
						    JSONParser parser = new JSONParser(); 
						    JSONObject jsonObject = (JSONObject) parser.parse(row.value().toString());
							  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
								     String jsonKey = (String) iterator.next();
								     Object jsonValue = jsonObject.get(jsonKey);
							    	 String documentKey = jsonValue.toString();
							    	 if(StringUtils.isNoneBlank(documentKey) && documentKey.equalsIgnoreCase(key)) {
							    		 userLoginSessionStoreBucket.remove(jsonValue.toString());
							    	 }
								}
							  
					    	 }
					 catch (ParseException e) {
						e.printStackTrace();
					}
		        }
			}*/
		}
	
	

	public void setUserProfile(String id, String key, JsonObject value) throws Exception {

		Bucket userProfileBucket = couchbaseClusterService.openBucket(couchConfig.getUserProfileBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		userProfileBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	public void setOrgLocations(String id, String key, JsonObject value) throws Exception {
		
		Bucket orgLocationsBucket = couchbaseClusterService.openBucket(couchConfig.getOrgLocationsBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		orgLocationsBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	
	public Object getUserProfile(String key) {

		Bucket userProfileBucket = couchbaseClusterService.openBucket(couchConfig.getUserProfileBucket(), couchConfig.getPassword());
		if (userProfileBucket.get(key) != null) {
			Object profileObj =  userProfileBucket.get(key).content().toString();
			return profileObj;
			
		} else {
			return null;
		}

	}
	
	
	public Object getOrgLocationsDetails(String key) {

		Bucket orgLocationsBucket = couchbaseClusterService.openBucket(couchConfig.getOrgLocationsBucket(), couchConfig.getPassword());
		if (orgLocationsBucket.get(key) != null) {
			Object orgLocObj =  orgLocationsBucket.get(key).content().toString();
			return orgLocObj;
		} else {
			return null;
		}

	}
	
	
	public void setUserLoginSession(String id, String key, JsonObject value) throws Exception {

		Bucket userLoginSessionStoreBucket = couchbaseClusterService.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		userLoginSessionStoreBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	
	public void setBulkImportDocuments(String key, JsonObject value) throws Exception {
		
		Bucket bulkImportsBucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		bulkImportsBucket.upsert(JsonDocument.create(key, arthur));
	}
	
    public void setCustomTokenDocuments(String token, JsonObject value) throws Exception {
		Bucket customTokenBucket = couchbaseClusterService.openBucket(couchConfig.getCustomTokensBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(token, value);
		customTokenBucket.upsert(JsonDocument.create(token, arthur));
	}
	
	
	public void setCoucuchbaseQueueDocument(String key, JsonObject value) throws Exception {
		
		Bucket bulkImportsBucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		bulkImportsBucket.upsert(JsonDocument.create(key, arthur));
	}

	
	public void setCouchbaseQueue(String documentQueueId,String documentId) throws Exception {

		 Bucket bulkImportsBucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
		 Queue<String> candidatesQueue = new CouchbaseQueue<String>(documentQueueId, bulkImportsBucket);
		 candidatesQueue.add(documentId);
	}
	
	
	public Object getCouchbaseQueueDocumentsList(String key) {

		 Bucket bulkImportsBucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
		if (bulkImportsBucket.get(key) != null) {
			Object docObj =  bulkImportsBucket.get(key).content().toString();
			return docObj;
			
		} else {
			return null;
		}

	}
	
	public Object getUserLoginSession(String key) {

		Bucket userLoginSessionStoreBucket = couchbaseClusterService.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
		if (userLoginSessionStoreBucket.get(key) != null) {
			Object userSessionObj =  userLoginSessionStoreBucket.get(key).content().toString();
			return userSessionObj;
		} else {
			return null;
		}

	}
	
	
	public Object getUserDetails(String key) {
		Bucket customTokenBucket = couchbaseClusterService.openBucket(couchConfig.getCustomTokensBucket(), couchConfig.getPassword());
		if (customTokenBucket.get(key) != null) {
			Object userDetailsObj =  customTokenBucket.get(key).content().toString();
			return userDetailsObj;
		} else {
			return null;
		}

	}
	

}
