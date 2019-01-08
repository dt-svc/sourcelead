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
	Bucket userProfileBucket;
	Bucket userDashboardStatsBucket;
	Bucket orgLocationsBucket;

	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(336699)
			.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();

	public CouchbaseClient(List<String> urls, List<String> couchBucketList, String password) {

		cluster = CouchbaseCluster.create(ENV, urls);
		bucket = cluster.openBucket(couchBucketList.get(0), password);
		userProfileBucket = cluster.openBucket(couchBucketList.get(1), password);
		userDashboardStatsBucket = cluster.openBucket(couchBucketList.get(2), password);
		orgLocationsBucket = cluster.openBucket(couchBucketList.get(3), password);

	}

	public void deleteSessionDocumentsFromBucket(String key) {

		bucket.bucketManager().createN1qlPrimaryIndex(true, false);
		N1qlQueryResult result = bucket.query(N1qlQuery.simple("SELECT meta().id FROM `test`"));
		if (result != null) {
			for (N1qlQueryRow row : result) {
				try {
					JSONParser parser = new JSONParser();
					JSONObject jsonObject = (JSONObject) parser.parse(row.value().toString());
					for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
						String jsonKey = (String) iterator.next();
						Object jsonValue = jsonObject.get(jsonKey);
						String[] sessionId = jsonValue.toString().split("_");
						if (sessionId[0].equalsIgnoreCase(key)) {
							bucket.remove(jsonValue.toString());
						}
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void shutdown() {
		cluster.disconnect();
	}

	public Object get(String key) {

		if (bucket.get(key) != null) {
			return bucket.get(key).content().toString();
		} else {
			return null;
		}

	}

	public Object getDashboardStats(String key) {

		if (userDashboardStatsBucket.get(key) != null) {
			return userDashboardStatsBucket.get(key).content().toString();
		} else {
			return null;
		}

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

		if (orgLocationsBucket.get(key) != null) {
			return orgLocationsBucket.get(key).content().toString();
		} else {
			return null;
		}

	}

	public void setUserDashboardStats(String id, String key, JsonObject value) throws Exception {
		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the Document
		userDashboardStatsBucket.upsert(JsonDocument.create(id, arthur));
	}
	
	
	public void setOrgLocationsStats(String id, String key, JsonObject value) throws Exception {
		JsonObject arthur = JsonObject.create().put(key, value);
		// Store the OrgLocations Document
		orgLocationsBucket.upsert(JsonDocument.create(id, arthur));
	}

	public static org.json.simple.JSONObject toSimple(JsonObject jo) {
		Map h = jo.toMap();
		org.json.simple.JSONObject simpleJson = new org.json.simple.JSONObject();
		simpleJson.putAll(h);
		return simpleJson;
	}

	public static JsonObject fromSimple(org.json.simple.JSONObject simpleJson) {
		JsonObject content = JsonObject.empty();
		for (Iterator iterator = simpleJson.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			content.put(key, simpleJson.get(key));
			System.out.println(simpleJson.get(key));
		}
		return null;
	}
}
