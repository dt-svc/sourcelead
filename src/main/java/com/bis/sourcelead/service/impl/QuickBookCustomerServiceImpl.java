package com.bis.sourcelead.service.impl;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.QuickBookCustomerService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.QuickBookOAuthAuthentication;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
public class QuickBookCustomerServiceImpl implements QuickBookCustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickBookCustomerServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private QuickBookOAuthAuthentication quickBookOAuthAuthentication;
	
	@Value("${quickBoxUrl}")
	private String quickBoxUrl;
	
	

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${consumerkey}")
	private String consumerkey;
	
	@Value("${consumerSecret}")
	private String consumerSecret;
	
	@Value("${accessToken}")
	private String accessToken;
	
	@Value("${accessSecret}")
	private String accessSecret;
	
	
	 /**
		 * @author Ajith Matta and sarath kumar punnana 
		 * @return all customers.
		 * @throws Exception
		 * This method is used to get the all customers.
		 */
	
	@Override
	public String getAllCustomers() throws Exception {
		  
		  String uri = quickBoxUrl+"/query?query=SELECT%20%2A%20FROM%20Customer%20STARTPOSITION%201%20MAXRESULTS%201000&minorversion=4";
		  
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		   
	}

	 /**
		 * @author  Ajith Matta and sarath kumar punnana  
		 * @param customerId
		 * @return customer by id.
		 * @throws Exception
		 * This method is used to get the customer by id.
		 */
	@Override
	public String getCustomerByCustomerId(Long customerId) throws Exception {

		  String uri = quickBoxUrl+"/customer/"+customerId+"?minorversion=4";
		  String outPut = quickBookOAuthAuthentication.oAuthSignIn(uri);
		  return outPut;  
		  
	}
	
	public void saveCustomer() throws Exception {

		
		HttpClient httpclient = new DefaultHttpClient();
		 
		JSONObject outerObject = new JSONObject();

		
		JSONObject innerObject = new JSONObject();
		innerObject.put("Line1","123 Main Street");
		innerObject.put("City","Mountain");
		innerObject.put("Country","Usa");
		innerObject.put("CountrySubDivisionCode","123 Main Street");
		outerObject.put("BillAddr", innerObject);
		
		outerObject.put( "Notes","Here are other details.");
	    outerObject.put("Title", "Mr");
	    outerObject.put("GivenName" , "westen");
	    outerObject.put("MiddleName", "B");
	    outerObject.put("FamilyName","King");
	    outerObject.put("Suffix", "Jr");
	    outerObject.put("FullyQualifiedName", "King Groceries");
	    outerObject.put("CompanyName", "King Groceries");
	    outerObject.put("DisplayName", "karpuram one");
	    
	    JSONObject innerObject1 = new JSONObject();
	    innerObject1.put("FreeFormNumber","(555) 555-5555");
	    outerObject.put("PrimaryPhone",innerObject1);
	    
	    JSONObject innerObject2 = new JSONObject();
	    innerObject2.put("Address","jdrew@myemail.com");
	    outerObject.put("PrimaryEmailAddr",innerObject2);
	    
		
	  
		 
	    HttpPost httpPost = new HttpPost("https://sandbox-quickbooks.api.intuit.com/v3/company/123145730629109/customer?minorversion=4");
	    StringEntity params = new StringEntity(outerObject.toString());
	    httpPost.addHeader("content-type", "application/json");
	    httpPost.setHeader("Accept","application/json"); 
	    httpPost.setEntity(params);
	   
	 
		 
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerkey,consumerSecret);
	    consumer.setTokenWithSecret(accessToken, accessSecret);
	    
	    try {
	        consumer.sign(httpPost);
	    } catch (OAuthMessageSignerException ex) {
	        logger.debug("hello");
	    } catch (OAuthExpectationFailedException ex) {
	        logger.debug("hello");
	    } catch (OAuthCommunicationException ex) {
	        logger.debug("hello");
	    }
	     
	    HttpResponse response = httpclient.execute(httpPost);
	    
	    logger.debug(response.getStatusLine().toString());
	    InputStream inputStraem = response.getEntity().getContent();

	    StringWriter writer = new StringWriter();
	    IOUtils.copy(inputStraem, writer, "UTF-8");
	    String output = writer.toString();
	    logger.debug(output);
	    
	   
	    
		
		
	}

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
