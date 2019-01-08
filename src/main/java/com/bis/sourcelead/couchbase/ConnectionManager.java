package com.bis.sourcelead.couchbase;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.couchbase.client.java.Bucket;

///import org.apache.log4j.Logger;
///import com.couchbase.client.CouchbaseClient;

public class ConnectionManager {
	private static CouchbaseClient client;
	private static ConnectionManager ref;
	private String couchUrl;
	List<String> urls;
	private String couchBucket;
	private String userProfileCouchBucket;
	private String userDashboardStatsBucket;
	private String orgLocationsBucket;
	private String couchPass;
	
///	private Logger logger = Logger.getLogger(ConnectionManager.class);

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

	public String getCouchBucket() {
		return couchBucket;
	}

	public void setCouchBucket(String couchBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.couchBucket = couchBucket;
	}
	
	public String getUserProfileCouchBucket() {
		return userProfileCouchBucket;
	}

	public void setUserProfileCouchBucket(String userProfileCouchBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.userProfileCouchBucket = userProfileCouchBucket;
	}

	public String getUserDashboardStatsBucket() {
		return userDashboardStatsBucket;
	}

	public void setUserDashboardStatsBucket(String userDashboardStatsBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.userDashboardStatsBucket = userDashboardStatsBucket;
	}
	
	
	public String getOrgLocationsBucket() {
		return orgLocationsBucket;
	}

	public void setOrgLocationsBucket(String orgLocationsBucket) {
		if(couchUrl == null)
			throw new RuntimeException("Couchbase BUCKET Not Specified");
		this.orgLocationsBucket = orgLocationsBucket;
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
				if (getCouchUrl() != null && !getCouchUrl().isEmpty() && getCouchBucket() != null && !getCouchBucket().isEmpty()) {
					hosts = Arrays.asList(new URI(getCouchUrl()));
					List<String> couchBuckets = new ArrayList<>();
					couchBuckets.add(getCouchBucket());
					couchBuckets.add(getUserProfileCouchBucket());
					couchBuckets.add(getUserDashboardStatsBucket());
					couchBuckets.add(getOrgLocationsBucket());
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
			ResourceBundle rb = ResourceBundle.getBundle("application");
			if (rb != null) {
				setCouchUrl(rb.getString("couchbaseUrl"));
				setCouchBucket(rb.getString("bucket"));
				setUserProfileCouchBucket(rb.getString("userProfileBucket"));
				setUserDashboardStatsBucket(rb.getString("userDashboardStatsBucket"));
				setOrgLocationsBucket(rb.getString("orgLocationsBucket"));
				setCouchPassword(rb.getString("password"));
			}

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
