package com.bis.sourcelead.elk.setup;

import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;

/**
 * @purpose:Elastic Search Transport Client Configuration
 * @author: Naresh Bolisetty
 * @date: 06-December-2016
 */
@Component
public class SourceleadELKTransportClient {
    private static final Logger logger = LoggerFactory.getLogger(SourceleadELKTransportClient.class);
   
    private RestHighLevelClient client;
    
     ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	 DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
    
	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
    public SourceleadELKTransportClient() {
    	try {
			client = new RestHighLevelClient(
						RestClient.builder(
								new HttpHost(dbconfig.getHost(), 9200, "http"),
								new HttpHost(dbconfig.getHost(), 9201, "http")
						)
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public RestHighLevelClient getClient() {
    	return client;
    }

    public void shutdown() {
		try {
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
