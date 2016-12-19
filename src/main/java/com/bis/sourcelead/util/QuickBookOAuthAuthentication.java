package com.bis.sourcelead.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
 
@Component
public class QuickBookOAuthAuthentication {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickBookOAuthAuthentication.class);
	
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
	
	@Value("${sandBoxUrl}")
	private String sandBoxUrl;
	
	
	public String oAuthSignIn(String uri) throws ClientProtocolException, IOException{
		
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerkey,consumerSecret);
	    consumer.setTokenWithSecret(accessToken, accessSecret);
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    HttpGet httpget = new HttpGet(uri);
	    httpget.setHeader("content-type", "application/json"); 
	    httpget.setHeader("Accept","application/json"); 
	    try {
	        consumer.sign(httpget);
	    } catch (OAuthMessageSignerException ex) {
	        System.out.println("jjj");
	    } catch (OAuthExpectationFailedException ex) {
	    	System.out.println("ff");
	    } catch (OAuthCommunicationException ex) {
	        System.out.println("ee");
	    }
	    HttpResponse response = httpclient.execute(httpget);
	    
	    System.out.println(response.getStatusLine().toString());
	    InputStream inputStraem = response.getEntity().getContent();

	    StringWriter writer = new StringWriter();
	    IOUtils.copy(inputStraem, writer, "UTF-8");
	    String output = writer.toString();
	    return output;
	    
	}
 
}
