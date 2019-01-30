package com.bis.sourcelead.couchbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(336699)
			.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();

	public CouchbaseClient(List<String> urls, List<String> couchBucketList, String password) {

		cluster = CouchbaseCluster.create(ENV, urls);
		userProfileBucket = cluster.openBucket(couchBucketList.get(0), password);

	}

	public void shutdown() {
		cluster.disconnect();
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

		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the Document
		userProfileBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	
	public Object getUserProfile(String key) {

		if (userProfileBucket.get(key) != null) {
			return userProfileBucket.get(key).content().toString();
		} else {
			return null;
		}

	}
	
	
	public Object getOrgLocationsDetails(String key) {

		if (userProfileBucket.get(key) != null) {
			return userProfileBucket.get(key).content().toString();
		} else {
			return null;
		}

	}

}
