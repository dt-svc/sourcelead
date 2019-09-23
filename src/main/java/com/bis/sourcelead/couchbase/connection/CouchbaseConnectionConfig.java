package com.bis.sourcelead.couchbase.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("${propertyLocation:app.properties}")
public class CouchbaseConnectionConfig {

	@Value("${couchbaseUrl}")
	private String couchbaseUrl;
	
	@Value("${bulkImportsBucket}")
	private String bulkImportsBucket;
	
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

	public String getBulkImportsBucket() {
		return bulkImportsBucket;
	}

	public void setBulkImportsBucket(String bulkImportsBucket) {
		this.bulkImportsBucket = bulkImportsBucket;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsCouchbaseEnabled() {
		return isCouchbaseEnabled;
	}

	public void setIsCouchbaseEnabled(String isCouchbaseEnabled) {
		this.isCouchbaseEnabled = isCouchbaseEnabled;
	}

	
}
