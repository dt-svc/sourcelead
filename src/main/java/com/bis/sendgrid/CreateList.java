package com.bis.sendgrid;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class CreateList {
	private static final Logger logger = LoggerFactory.getLogger(CreateList.class);
	
	
public static String getEmailCategoryStats() throws IOException {
		
		try {
		      SendGrid sg = new SendGrid("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");
		      Request request = new Request();
		      request.setMethod(Method.GET);
		      request.setEndpoint("categories/stats");
		      request.addQueryParam("end_date", "2017-12-04");
//		      request.addQueryParam("aggregated_by", "day");
//		      request.addQueryParam("limit", "1");
//		      request.addQueryParam("offset", "1");
		      request.addQueryParam("start_date", "2017-12-04");
		      request.addQueryParam("categories", "hotlist");
		      Response response = sg.api(request);
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
//		      System.out.println(response.getHeaders());
		      return response.getBody();
		    } catch (IOException ex) {
		      throw ex;
		    }
		 
	}
	public static void main(String[] args) throws IOException, JSONException {
		
		String response = getEmailCategoryStats();
		  JSONArray jsonArr = new JSONArray(response);
//		  System.out.println("=Total Array=========="+jsonArr.length());
		  for (int i = 0; i < jsonArr.length(); i++)
	        {
	            JSONObject jsonObj = jsonArr.getJSONObject(i);

	            System.out.println(jsonObj.getString("date"));
	            JSONArray statJsonArr = jsonObj.getJSONArray("stats");
	            System.out.println("=Stats Array=========="+statJsonArr.length());
	            for (int j = 0; j < statJsonArr.length(); j++)
		        {
	            	 JSONObject jsonObj2 = statJsonArr.getJSONObject(j);
	            	 System.out.println(jsonObj2.getString("type"));
	            	 System.out.println(jsonObj2.getString("name"));
	            	 JSONObject jsonObj3 = jsonObj2.getJSONObject("metrics");
	            	 System.out.println("1.blocks = "+jsonObj3.getString("blocks"));
	            	 System.out.println("2.delivered = "+jsonObj3.getString("delivered"));
	            	 System.out.println("3.bounces = "+jsonObj3.getString("bounces"));
		        }
	        }

		 
		
//	    try {
//	      SendGrid sg = new SendGrid("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");
//	      Request request = new Request();
//	      request.setMethod(Method.POST);
//	      request.setEndpoint("contactdb/lists");
//	      request.setBody("{\"name\":\"rajashekar_test_team\"}");
//	      Response response = sg.api(request);
//	      logger.info(""+response.getStatusCode());
//	      logger.info(""+response.getBody());
//	      logger.info(""+response.getHeaders());
//	    } catch (IOException ex) {
//	      throw ex;
//	    }
	  }
}
