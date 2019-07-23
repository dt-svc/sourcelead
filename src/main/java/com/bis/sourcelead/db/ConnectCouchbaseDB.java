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
		
	 /*System.out.println("----Connecting to userProfile bkt statrs-----");
		
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
			 
			 System.out.println("----Connecting to userProfile bkt ends-----");*/
		
	//	String jsonString = "[{\"documentId\":\"jquGZds1XmhFOs0\",\"errorMsg\":{\"firstName\":\"First Name field is missing\",\"mobilePhone\":\"Invalid Mobile Phone\",\"email\":\"Invalid Email\"}},{\"documentId\":\"E00j1wVZkiXw7r9\",\"errorMsg\":{\"mobilePhone\":\"Invalid Mobile Phone\",\"email\":\"Invalid Email\"}},{\"documentId\":\"KTRDDuHb6WktKgZ\",\"errorMsg\":{\"email\":\"Candidate already existed\"}}]";
		
	String jsonString = "[{" + 
			"\"someName\":[{" + 
			"\"requirementId\": \"8309\"," + 
			"\"candidateId\": \"140245\"," + 
			"\"candidateResume\": \"119728\"," + 
			"\"FirstName\": \"Santhosh\"," + 
			"\"LastName\": \"Rathod\"," + 
			"\"PhoneNumber\": \"1234567896\"," + 
			"\"AlternateNumber\": \"\"," + 
			"\"EmailId\": \"santhu9640@gmail.com\"," + 
			"\"location\": \"Hyderabad, Telangana, India\"," + 
			"\"country\": \"India\"," + 
			"\"state\": \"Telangana\"," + 
			"\"city\": \"Hyderabad\"," + 
			"\"zipcode\": \"\"," + 
			"\"DOB\": \"\"," + 
			"\"Current/LastCompany\": \"\"," + 
			"\"Designation\": \"Business Development Executive\"," + 
			"\"Education\": \"\"," + 
			"\"WorkExperience\": \"0\"," + 
			"\"monthExp\": \"0\"," + 
			"\"currentCTCRateType\": \"INR\"," + 
			"\"CurrentCTC\": \"\"," + 
			"\"currentCTCRatePer\": \"\"," + 
			"\"expectedCTCRateType\": \"INR\"," + 
			"\"ExpectedCTC\": \"\"," + 
			"\"expectedCTCRatePer\": \"\"," + 
			"\"NoticePeriod\": \"\"," + 
			"\"primarySkill\": \"\"" + 
			"}]," + 
			"\"otherName\":[" + 
			"{" + 
			"\"label\": \"First Name \"," + 
			"\"id\": \"text-98017\"," + 
			"\"name\": \"FirstName\"," + 
			"\"value\": \"Prasanna \"" + 
			"}, {" + 
			"\"label\": \"Last Name\"," + 
			"\"id\": \"text-53902\"," + 
			"\"name\": \"LastName\"," + 
			"\"value\": \"Koparde\"" + 
			"}, {" + 
			"\"label\": \"Phone Number \"," + 
			"\"id\": \"text-34282\"," + 
			"\"name\": \"PhoneNumber\"," + 
			"\"value\": \"9986013844\"" + 
			"}, {" + 
			"\"label\": \"Alternate Number\"," + 
			"\"id\": \"text-943456\"," + 
			"\"name\": \"AlternateNumber\"," + 
			"\"value\": \"\"" + 
			"}, {" + 
			"\"label\": \"Email Id \"," + 
			"\"id\": \"text-420938\"," + 
			"\"name\": \"EmailId\"," + 
			"\"value\": \"prasanna_koparde@yahoo.com\"" + 
			"}, {" + 
			"\"label\": \"Country\"," + 
			"\"id\": \"country\"," + 
			"\"name\": \"country\"," + 
			"\"value\": \"India\"" + 
			"}, {" + 
			"\"label\": \"State\"," + 
			"\"id\": \"state\"," + 
			"\"name\": \"state\"," + 
			"\"value\": \"Karnataka\"" + 
			"}, {" + 
			"\"label\": \"City\"," + 
			"\"id\": \"city\"," + 
			"\"name\": \"city\"," + 
			"\"value\": \"Bangalore\"" + 
			"}, {" + 
			"\"label\": \"DOB\"," + 
			"\"id\": \"DOB\"," + 
			"\"name\": \"DOB\"," + 
			"\"value\": \"1982-09-20\"" + 
			"}, {" + 
			"\"label\": \"Current / Last Company\"," + 
			"\"id\": \"text-975884\"," + 
			"\"name\": \"Current/LastCompany\"," + 
			"\"value\": \"Amiti Software Technologies\"" + 
			"}, {" + 
			"\"label\": \"Designation\"," + 
			"\"id\": \"text-287677\"," + 
			"\"name\": \"Designation\"," + 
			"\"value\": \"Technical Leader\"" + 
			"}, {" + 
			"\"label\": \"Education\"," + 
			"\"id\": \"text-190548\"," + 
			"\"name\": \"Education\"," + 
			"\"value\": \"MCA\"" + 
			"}, {" + 
			"\"label\": \"Work Experience\"," + 
			"\"id\": \"select-382969\"," + 
			"\"name\": \"WorkExperience\"," + 
			"\"value\": \"9\"" + 
			"}, {" + 
			"\"label\": \"Current CTC\"," + 
			"\"id\": \"text-109893\"," + 
			"\"name\": \"CurrentCTC\"," + 
			"\"value\": \"12\"" + 
			"}, {" + 
			"\"label\": \"Expected CTC\"," + 
			"\"id\": \"text-664905\"," + 
			"\"name\": \"ExpectedCTC\"," + 
			"\"value\": \"16\"" + 
			"}, {" + 
			"\"label\": \"Notice Period\"," + 
			"\"id\": \"select-555332\"," + 
			"\"name\": \"NoticePeriod\"," + 
			"\"value\": \"1 Month\"" + 
			"}]" + 
			"}]";
	
	
		JSONArray jsonArray = new JSONArray(jsonString);
		 for (int i = 0; i < jsonArray.length(); i++) {
			 JSONParser parser = new JSONParser();
			 JSONObject jsonObjVal = (JSONObject) parser.parse(jsonArray.get(i).toString());
			 for (Object okey : jsonObjVal.keySet()) {
				 String keyStr = (String) okey;
				 if(keyStr.equalsIgnoreCase("someName")) {
					 String keyvalue = jsonObjVal.get(keyStr).toString();
					 String jsonStringValues = keyvalue.substring(1, keyvalue.length()-1);
					 org.json.JSONObject jsonObj = new org.json.JSONObject(jsonStringValues);
					 System.out.println(jsonObj);
					 
				 }
				 
				 if(keyStr.equalsIgnoreCase("otherName")) {
					 String keyvalue = jsonObjVal.get(keyStr).toString();
					 System.out.println(keyvalue);
				 }
				
				/* if(keyStr.equalsIgnoreCase("otherName")) {
					 String keyvalue = jsonObjVal.get(keyStr).toString();
					 JSONObject jsonObjErrorVal = (JSONObject) parser.parse(keyvalue);
					 for (Object errorkey : jsonObjErrorVal.keySet()) {
						 String errkeyStr = (String) errorkey;
						 String errkeyvalue = jsonObjErrorVal.get(errkeyStr).toString();
						 System.out.println(errkeyvalue);
					 }
				 }*/
				
			 }
         }
		
		
		
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
