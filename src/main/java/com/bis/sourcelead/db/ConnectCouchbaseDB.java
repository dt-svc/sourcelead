package com.bis.sourcelead.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConnectCouchbaseDB {

/**
 * @author
 * this method is using for connect couch db externally
 * @throws IOException
 * @throws SQLException
 */
public void ConnectCouchbase() throws IOException, SQLException {
	
	try {
 
	 System.out.println("----Connecting to test bkt statrs-----");
		
	 CouchbaseEnvironment environment = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(536699)
				.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();
	 
	 Cluster cluster = CouchbaseCluster.create(environment, "50.21.179.125");
	 System.out.println("----Couchbase Cluster created-----");
			 Bucket bucket = cluster.openBucket("test","root123",56678L, TimeUnit.MILLISECONDS);
			 System.out.println("----Bucket test opened-----");
			 
			 ObjectMapper sessionObj = new ObjectMapper();
	         try {
	        	 UserRoleSessionProfileDTO userRoleDTO = new UserRoleSessionProfileDTO();
	        	 userRoleDTO.setDocumentType("Hi...Welcome to test Bkt");
				 String sessionJsonStr = sessionObj.writeValueAsString(userRoleDTO); 
				 JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
				 JsonObject arthur = JsonObject.create().put("textMsg", jsonObject);
				 bucket.upsert(JsonDocument.create("12345", arthur));
				 System.out.println("----Record inserted into test bucket-----");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			 
			 System.out.println("----Connecting to test bkt ends-----");
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



 
public static void main(String[] args) throws IOException, SQLException {
	ConnectCouchbaseDB dump = new ConnectCouchbaseDB();
	dump.ConnectCouchbase();
}


}
