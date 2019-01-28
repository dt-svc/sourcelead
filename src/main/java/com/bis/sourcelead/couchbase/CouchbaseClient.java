package com.bis.sourcelead.couchbase;

import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

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
