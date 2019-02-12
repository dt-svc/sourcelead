package com.bis.sourcelead.couchbase.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("${propertyLocation:app.properties}")
public class CouchbaseConnectionConfig {

	@Value("${couchbaseUrl}")
	private String couchbaseUrl;
	
	@Value("${userProfileBucket}")
	private String userProfileBucket;
	
	@Value("${userLoginSessionStoreBucket}")
	private String userLoginSessionStoreBucket;


	@Value("${couchbase.password}")
	private String password;
	
	

	public String getCouchbaseUrl() {
		return couchbaseUrl;
	}

	public void setCouchbaseUrl(String couchbaseUrl) {
		this.couchbaseUrl = couchbaseUrl;
	}

	public String getUserProfileBucket() {
		return userProfileBucket;
	}

	public void setUserProfileBucket(String userProfileBucket) {
		this.userProfileBucket = userProfileBucket;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserLoginSessionStoreBucket() {
		return userLoginSessionStoreBucket;
	}

	public void setUserLoginSessionStoreBucket(String userLoginSessionStoreBucket) {
		this.userLoginSessionStoreBucket = userLoginSessionStoreBucket;
	}
}
