package com.bis.sourcelead.couchbase.connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(
basePackages = {"com.bis.sourcelead.couchbase.connection"},
useDefaultFilters = false,
includeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CouchbaseConnectionConfig.class)
})
public class CouchbasePropertyPathConfiguration {

	@Bean
	  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	    return new PropertySourcesPlaceholderConfigurer();
	  }
}
