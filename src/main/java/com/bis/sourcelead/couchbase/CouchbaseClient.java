package com.bis.sourcelead.couchbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.bis.sourcelead.service.CouchbaseClusterService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;


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
	
	public Object getUserLoginSession(String key) {

		Bucket userLoginSessionStoreBucket = couchbaseClusterService.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
		if (userLoginSessionStoreBucket.get(key) != null) {
			Object userSessionObj =  userLoginSessionStoreBucket.get(key).content().toString();
			return userSessionObj;
		} else {
			return null;
		}

	}
	

}
