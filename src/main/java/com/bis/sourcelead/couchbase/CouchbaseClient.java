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

	
	public void setBulkImportDocuments(String key, JsonObject value) throws Exception {
		
		Bucket bulkImportsBucket = couchbaseClusterService.openBucket(couchConfig.getBulkImportsBucket(), couchConfig.getPassword());
		JsonObject arthur = JsonObject.create().put(key, value);
		bulkImportsBucket.upsert(JsonDocument.create(key, arthur));
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
	

}
