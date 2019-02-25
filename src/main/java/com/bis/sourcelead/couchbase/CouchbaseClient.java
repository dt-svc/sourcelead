package com.bis.sourcelead.couchbase;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;


@Component
public class CouchbaseClient {
	Bucket userProfileBucket;
	Bucket userLoginSessionStoreBucket;
	Bucket orgLocationsBucket;
    private Cluster cluster;
    
    
     ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
	 CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
		 

	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(536699)
			.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();

	public CouchbaseClient() {
		if(couchConfig.getIsCouchbaseEnabled().equalsIgnoreCase("true")) {
			cluster = CouchbaseCluster.create(ENV, couchConfig.getCouchbaseUrl());
			userProfileBucket = cluster.openBucket(couchConfig.getUserProfileBucket(), couchConfig.getPassword());
			userLoginSessionStoreBucket = cluster.openBucket(couchConfig.getUserLoginSessionStoreBucket(), couchConfig.getPassword());
			orgLocationsBucket = cluster.openBucket(couchConfig.getOrgLocationsBucket(), couchConfig.getPassword());
		}
	}

	public void shutdown() {
		cluster.disconnect();
	}
	
	
	public void deleteSessionDocumentsFromBucket(String key) {
			
		userLoginSessionStoreBucket.bucketManager().createN1qlPrimaryIndex(true, false);
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
			}
		}
	
	

	public void setUserProfile(String id, String key, JsonObject value) throws Exception {

		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the Document
		userProfileBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	public void setOrgLocations(String id, String key, JsonObject value) throws Exception {

		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the Document
		orgLocationsBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	
	public Object getUserProfile(String key) {

		if (userProfileBucket.get(key) != null) {
			return userProfileBucket.get(key).content().toString();
		} else {
			return null;
		}

	}
	
	
	public Object getOrgLocationsDetails(String key) {

		if (orgLocationsBucket.get(key) != null) {
			return orgLocationsBucket.get(key).content().toString();
		} else {
			return null;
		}

	}
	
	
	public void setUserLoginSession(String id, String key, JsonObject value) throws Exception {

		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the Document
		userLoginSessionStoreBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	public Object getUserLoginSession(String key) {

		if (userLoginSessionStoreBucket.get(key) != null) {
			return userLoginSessionStoreBucket.get(key).content().toString();
		} else {
			return null;
		}

	}
	

}
