package com.bis.sourcelead.couchbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

public class CouchbaseClient {
	Cluster cluster;
	Bucket userProfileBucket;
	Bucket userLoginSessionStoreBucket;
	Bucket orgLocationsBucket;

	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(336699)
			.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();

	public CouchbaseClient(List<String> urls, List<String> couchBucketList, String password) {

		cluster = CouchbaseCluster.create(ENV, urls);
		userProfileBucket = cluster.openBucket(couchBucketList.get(0), password);
		userLoginSessionStoreBucket = cluster.openBucket(couchBucketList.get(1), password);
		orgLocationsBucket = cluster.openBucket(couchBucketList.get(2), password);
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
	
	
	public List<String> getLoginUserAccounts(String key) {
        
		List<String> userAccountsList = new ArrayList<>();
		userProfileBucket.bucketManager().createN1qlPrimaryIndex(true, false);
		JsonObject pVal = JsonObject.create().put("documentType", "User Account");
		N1qlQueryResult result = userProfileBucket.query(N1qlQuery.parameterized("SELECT meta().id FROM `userProfile`", pVal));
		if (result != null) {
			for (N1qlQueryRow row : result) {
				try {
					JSONParser parser = new JSONParser();
					JSONObject jsonObject = (JSONObject) parser.parse(row.value().toString());
					for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
						String jsonKey = (String) iterator.next();
						Object jsonValue = jsonObject.get(jsonKey);
						userAccountsList.add(jsonKey);
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return userAccountsList;
	}


	public void setUserProfile(String id, String key, JsonObject value) throws Exception {

		// Store the user profile Document
		userProfileBucket.upsert(JsonDocument.create(id, value));
	}
	
	public void setOrgLocations(String id, String key, JsonObject value) throws Exception {

		// Store the orglocations document
		orgLocationsBucket.upsert(JsonDocument.create(id, value));
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

		// Store the session Document
		userLoginSessionStoreBucket.upsert(JsonDocument.create(id, value));
	}
	
	public Object getUserLoginSession(String key) {

		if (userLoginSessionStoreBucket.get(key) != null) {
			return userLoginSessionStoreBucket.get(key).content().toString();
		} else {
			return null;
		}

	}
	
	
	public JSONObject getUserProfileDetails(String key) {
		
		JSONObject userRoleJson = new JSONObject();
		userProfileBucket.bucketManager().createN1qlPrimaryIndex(true, false);
			N1qlQueryResult result = userProfileBucket.query(N1qlQuery.simple("SELECT * FROM `userProfile` where meta().id = '"+key+"'"));
			if(result != null) {
				for (N1qlQueryRow row : result) {
					  try {
						    JSONParser parser = new JSONParser(); 
						    JSONObject sessionJson = (JSONObject) parser.parse(row.value().toString());
						    for (Iterator iterator = sessionJson.keySet().iterator(); iterator.hasNext();) {
								String jsonKey = (String) iterator.next();
								Object jsonValue = sessionJson.get(jsonKey);
								userRoleJson = (JSONObject) parser.parse(jsonValue.toString());
					    	 }
					  }
					 catch (ParseException e) {
						e.printStackTrace();
					}
		        }
			}
			return userRoleJson;	
	   }
	
	
	public JSONObject getOrgLocationDetails(String key) {
		
		JSONObject orglocationJson = new JSONObject();
		orgLocationsBucket.bucketManager().createN1qlPrimaryIndex(true, false);
			N1qlQueryResult result = orgLocationsBucket.query(N1qlQuery.simple("SELECT * FROM `orgLocations` where meta().id = '"+key+"'"));
			if(result != null) {
				for (N1qlQueryRow row : result) {
					  try {
						    JSONParser parser = new JSONParser(); 
						    JSONObject sessionJson = (JSONObject) parser.parse(row.value().toString());
						    for (Iterator iterator = sessionJson.keySet().iterator(); iterator.hasNext();) {
								String jsonKey = (String) iterator.next();
								Object jsonValue = sessionJson.get(jsonKey);
								orglocationJson = (JSONObject) parser.parse(jsonValue.toString());
					    	 }
					  }
					 catch (ParseException e) {
						e.printStackTrace();
					}
		        }
				
			}
			return orglocationJson;	
	   }
	
	
	public JSONObject getUserLoginSessionStoreDetails(String key) {
		
		JSONObject sessionStoreJson = new JSONObject();
		userLoginSessionStoreBucket.bucketManager().createN1qlPrimaryIndex(true, false);
			N1qlQueryResult result = userLoginSessionStoreBucket.query(N1qlQuery.simple("SELECT * FROM `userLoginSessionStore` where meta().id = '"+key+"'"));
			if(result != null) {
				for (N1qlQueryRow row : result) {
					  try {
						    JSONParser parser = new JSONParser(); 
						    JSONObject sessionJson = (JSONObject) parser.parse(row.value().toString());
						    for (Iterator iterator = sessionJson.keySet().iterator(); iterator.hasNext();) {
								String jsonKey = (String) iterator.next();
								Object jsonValue = sessionJson.get(jsonKey);
								sessionStoreJson = (JSONObject) parser.parse(jsonValue.toString());
					    	 }
					  }
					 catch (ParseException e) {
						e.printStackTrace();
					}
		        }
			}
			return sessionStoreJson;	
			
	   }

}
