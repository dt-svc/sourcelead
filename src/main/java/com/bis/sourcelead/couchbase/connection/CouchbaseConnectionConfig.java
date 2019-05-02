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
	
	@Value("${orgLocationsBucket}")
	private String orgLocationsBucket;
	
	@Value("${bulkImportsBucket}")
	private String bulkImportsBucket;
	
	@Value("${customTokensBucket}")
	private String customTokensBucket;

	@Value("${couchbase.password}")
	private String password;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	

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

	public String getOrgLocationsBucket() {
		return orgLocationsBucket;
	}

	public void setOrgLocationsBucket(String orgLocationsBucket) {
		this.orgLocationsBucket = orgLocationsBucket;
	}

	public String getIsCouchbaseEnabled() {
		return isCouchbaseEnabled;
	}

	public void setIsCouchbaseEnabled(String isCouchbaseEnabled) {
		this.isCouchbaseEnabled = isCouchbaseEnabled;
	}

	public String getBulkImportsBucket() {
		return bulkImportsBucket;
	}

	public void setBulkImportsBucket(String bulkImportsBucket) {
		this.bulkImportsBucket = bulkImportsBucket;
	}

	public String getCustomTokensBucket() {
		return customTokensBucket;
	}

	public void setCustomTokensBucket(String customTokensBucket) {
		this.customTokensBucket = customTokensBucket;
	}

	
}
