package com.bis.sourcelead.couchbase;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.couchbase.connection.CouchbaseConnectionConfig;
import com.bis.sourcelead.couchbase.connection.CouchbasePropertyPathConfiguration;

@Component
public class ConnectionManager {
	private static CouchbaseClient client;
	private static ConnectionManager ref;
	private String couchUrl;
	List<String> urls;
	private String userProfileCouchBucket;
	private String userLoginSessionStoreBucket;
	private String couchPass;
	
	
	 ApplicationContext ctx = new AnnotationConfigApplicationContext(CouchbasePropertyPathConfiguration.class);
	 CouchbaseConnectionConfig couchConfig = ctx.getBean(CouchbaseConnectionConfig.class);
	
	public String getCouchUrl() {
		return couchUrl;
	}

	public void setCouchUrl(String couchUrl) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase URL Not Specified");
		this.couchUrl = couchUrl;
		if(couchUrl.indexOf(',')!=-1)
			urls = Arrays.asList(couchUrl.split("\\s*,\\s*"));
		else
			urls = Arrays.asList(couchUrl);
	}

		
	public String getUserProfileCouchBucket() {
		return userProfileCouchBucket;
	}

	public void setUserProfileCouchBucket(String userProfileCouchBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.userProfileCouchBucket = userProfileCouchBucket;
	}

	

	public String getUserLoginSessionStoreBucket() {
		return userLoginSessionStoreBucket;
	}

	public void setUserLoginSessionStoreBucket(String userLoginSessionStoreBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.userLoginSessionStoreBucket = userLoginSessionStoreBucket;
	}

	public String getCouchPassword() {
		return couchPass;
	}

	public void setCouchPassword(String couchPass) {
		if(couchPass == null)
			throw new RuntimeException("Couchbase PASSWORD Not Specified");
		this.couchPass = couchPass;
	}

	private ConnectionManager() {
		loadProperties();
		if (client == null) {		
			List<URI> hosts;
			try {
				if (getCouchUrl() != null && !getCouchUrl().isEmpty() && getUserProfileCouchBucket() != null && !getUserProfileCouchBucket().isEmpty()) {
					hosts = Arrays.asList(new URI(getCouchUrl()));
					List<String> couchBuckets = new ArrayList<>();
					couchBuckets.add(getUserProfileCouchBucket());
					couchBuckets.add(getUserLoginSessionStoreBucket());
					client = new CouchbaseClient(urls, couchBuckets,getCouchPassword());
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		 * if (client == null) { String bucket = "default"; String password =
		 * ""; try { List hosts = Arrays.asList(new URI[] { new
		 * URI("http://127.0.0.1:8091/pools") }); client = new
		 * CouchbaseClient(hosts, bucket, password); } catch (URISyntaxException
		 * e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */
	}

	public static synchronized ConnectionManager instance() {
		if (ref == null) {
			ref = new ConnectionManager();
		}
		return ref;
	}

	public CouchbaseClient getClient() {
		return client;
	}

	public void loadProperties() {
		try {
			setCouchUrl(couchConfig.getCouchbaseUrl().trim());
			setUserProfileCouchBucket(couchConfig.getUserProfileBucket().trim());
			setUserLoginSessionStoreBucket(couchConfig.getUserLoginSessionStoreBucket().trim());
			setCouchPassword(couchConfig.getPassword().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	  public static void reset(){
		    try{
		    	client.shutdown();
		    	client = null;
	    		 System.out.println("REMAKING conn in 20 secs" );
	    		Thread.sleep(20000);
	    		ConnectionManager.instance();
	    	}catch(Exception exs){
	    		exs.printStackTrace();
	    	}  
	  }
}
