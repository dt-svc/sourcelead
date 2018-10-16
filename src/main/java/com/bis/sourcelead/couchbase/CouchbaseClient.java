package com.bis.sourcelead.couchbase;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	Bucket bucket;
	
	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().connectTimeout(8000).keepAliveInterval(3600 * 1000).build();
	public CouchbaseClient(List<String> urls, String couchBucket, String password) {
		
		cluster = CouchbaseCluster.create(urls);
		bucket = cluster.openBucket(couchBucket, password);
		
	}
	
	
	public void deleteSessionDocumentsFromBucket(String key) {
		
		bucket.bucketManager().createN1qlPrimaryIndex(true, false);
		N1qlQueryResult result = bucket.query(N1qlQuery.simple("SELECT meta().id FROM `test`"));
		 for (N1qlQueryRow row : result) {
	            
			  try {
				    JSONParser parser = new JSONParser(); 
				    JSONObject jsonObject = (JSONObject) parser.parse(row.value().toString());
					  for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
						     String jsonKey = (String) iterator.next();
						     Object jsonValue = jsonObject.get(jsonKey);
					    	 String[] sessionId = jsonValue.toString().split("_");
					    	 if(sessionId[0].equalsIgnoreCase(key)) {
					    		 bucket.remove(jsonValue.toString());
					    	 }
						}
					  
			    	 }
			 catch (ParseException e) {
				e.printStackTrace();
			}
        }
	}

	public void shutdown() {
		cluster.disconnect();
	}

	public Object get(String key){
		
		if(bucket.get(key) != null) {
			return bucket.get(key).content().toString();
		}else {
			return null;
		}
		
	}
	
	public void set(String id, String key, String value) throws Exception{
		
			
			JsonObject arthur = JsonObject.create()
		            .put(key, value);

	        // Store the Document
	        bucket.upsert(JsonDocument.create(id, arthur));			
			
			
		
	}
	
	public static org.json.simple.JSONObject toSimple(JsonObject jo){
		Map h = jo.toMap();
		org.json.simple.JSONObject simpleJson = new org.json.simple.JSONObject();
		simpleJson.putAll(h);
		return simpleJson;
	}
	
	public static JsonObject fromSimple(org.json.simple.JSONObject simpleJson){
		JsonObject content = JsonObject.empty();
		for(Iterator iterator = simpleJson.keySet().iterator(); iterator.hasNext();) {
		    String key = (String) iterator.next();
		    content.put(key, simpleJson.get(key));
		    System.out.println(simpleJson.get(key));
		}
		return null;
	}
}
