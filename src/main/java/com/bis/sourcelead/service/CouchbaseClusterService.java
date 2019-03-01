package com.bis.sourcelead.service;

import com.couchbase.client.java.Bucket;

public interface CouchbaseClusterService {

	Bucket openBucket(String bucketName,String bucketPassword);
}
