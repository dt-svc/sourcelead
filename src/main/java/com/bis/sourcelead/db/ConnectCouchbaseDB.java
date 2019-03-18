package com.bis.sourcelead.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bis.sourcelead.couchbase.UserRoleSessionProfileDTO;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.datastructures.collections.CouchbaseQueue;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class ConnectCouchbaseDB {
	
/**
 * @author
 * this method is using for connect couch db externally
 * @throws IOException
 * @throws SQLException
 */
public void ConnectCouchbase() throws IOException, SQLException {
	
	try {
		
	 System.out.println("----Connecting to userProfile bkt statrs-----");
		
	 CouchbaseEnvironment environment = DefaultCouchbaseEnvironment.builder().socketConnectTimeout(536699)
				.connectTimeout(36678).keepAliveInterval(3600 * 30000).build();
	 
	 Cluster cluster = CouchbaseCluster.create(environment, "127.0.0.1");
			 Bucket bucket = cluster.openBucket("test","root123",56678L, TimeUnit.MILLISECONDS);
			 
			 try {
				tryQueue(bucket);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			 
			 
			 
			 ObjectMapper sessionObj = new ObjectMapper();
	         try {
	        	 UserRoleSessionProfileDTO userRoleDTO = new UserRoleSessionProfileDTO();
	        	 userRoleDTO.setDocumentType("Hi...Welcome to test Bkt");
				 String sessionJsonStr = sessionObj.writeValueAsString(userRoleDTO); 
				 JsonObject jsonObject = JsonObject.fromJson(sessionJsonStr);
				 JsonObject arthur = JsonObject.create().put("textMsg", jsonObject);
				 bucket.upsert(JsonDocument.create("12345", arthur));
				 
				 Bucket bkt = cluster.openBucket("test", "root123");
				 Object userSessionObj =  bkt.get("12345").content().toString();
					if(userSessionObj != null) {
						System.out.println("document retervied from couchbase");
					}else {
						System.out.println("document not retervied from couchbase");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 System.out.println("----Connecting to userProfile bkt ends-----");
		
		
/*		String jsonString = "[{\"documentId\":\"jquGZds1XmhFOs0\",\"errorMsg\":{\"firstName\":\"First Name field is missing\",\"mobilePhone\":\"Invalid Mobile Phone\",\"email\":\"Invalid Email\"}},{\"documentId\":\"E00j1wVZkiXw7r9\",\"errorMsg\":{\"mobilePhone\":\"Invalid Mobile Phone\",\"email\":\"Invalid Email\"}},{\"documentId\":\"KTRDDuHb6WktKgZ\",\"errorMsg\":{\"email\":\"Candidate already existed\"}}]";
		JSONArray jsonArray = new JSONArray(jsonString);
		 for (int i = 0; i < jsonArray.length(); i++) {
			 JSONParser parser = new JSONParser();
			 JSONObject jsonObjVal = (JSONObject) parser.parse(jsonArray.get(i).toString());
			 for (Object okey : jsonObjVal.keySet()) {
				 String keyStr = (String) okey;
				 if(!keyStr.equalsIgnoreCase("errorMsg")) {
					 String keyvalue = jsonObjVal.get(keyStr).toString();
					 System.out.println(keyvalue);
				 }
				
				 if(keyStr.equalsIgnoreCase("errorMsg")) {
					 String keyvalue = jsonObjVal.get(keyStr).toString();
					 JSONObject jsonObjErrorVal = (JSONObject) parser.parse(keyvalue);
					 for (Object errorkey : jsonObjErrorVal.keySet()) {
						 String errkeyStr = (String) errorkey;
						 String errkeyvalue = jsonObjErrorVal.get(errkeyStr).toString();
						 System.out.println(errkeyvalue);
					 }
				 }
				
			 }
         }
		*/
		
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



 
private void tryQueue(Bucket bucket) {
	Queue<String> shoppingList = new CouchbaseQueue<String>("queueDocId", bucket);
	shoppingList.add("loaf of bread");
	shoppingList.add("VENKATA");
	shoppingList.add("SUBBAYYA");
	shoppingList.add("GARU");
	shoppingList.add("container of milk");
	shoppingList.add("stick of butter");
	shoppingList.add("SUPERSTAR");
	shoppingList.add("SUPERMAN");

	// What does the JSON document look like?
	System.out.println(bucket.get(JsonArrayDocument.create("queueDocId")).content());
	        //=> ["stick of butter","container of milk","loaf of bread"]

	/*String item; int count = 0;
	while ((item = shoppingList.poll()) != null && count <= 3) {
		count++;
	    System.out.println(item);
	    // => loaf of bread
	    // => container of milk
	    // => stick of butter
	}*/

	// What does the JSON document look like after draining the queue?
	System.out.println(bucket.get(JsonArrayDocument.create("queueDocId")).content());
	       	
}




public static void main(String[] args) throws IOException, SQLException {
	ConnectCouchbaseDB dump = new ConnectCouchbaseDB();
	dump.ConnectCouchbase();
}


}
