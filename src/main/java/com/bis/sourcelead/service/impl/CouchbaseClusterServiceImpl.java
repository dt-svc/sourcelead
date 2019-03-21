package com.bis.sourcelead.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.CouchbaseClusterService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

@Service
public class CouchbaseClusterServiceImpl implements CouchbaseClusterService{

    private Cluster cluster;
	 
    private Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    @Value("${couchbaseUrl}")
    private String couchbaseUrl;
		 

	private static final CouchbaseEnvironment ENV = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(536699)
			.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();
    
    @PostConstruct
    private void init() {
        cluster =  CouchbaseCluster.create(ENV, couchbaseUrl);
    }
    
	@Override
	synchronized public Bucket openBucket(String bucketName, String bucketPassword) {
		
		 if(!buckets.containsKey(bucketName)) {
		        Bucket bucket = cluster.openBucket(bucketName, bucketPassword);
		        buckets.put(bucketName, bucket);
		    }else {
	    }
		    return buckets.get(bucketName);
	}
	
	/*@PreDestroy
	protected void clusterDisconnect() throws Throwable {
		 System.out.println(">>>>>>>>>>Entered into clusterDisconnect() method>>>>>>>>");
		 cluster.disconnect();
	}*/

	
}
