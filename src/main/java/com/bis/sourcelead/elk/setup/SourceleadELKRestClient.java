package com.bis.sourcelead.elk.setup;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;

/**
 * 
 * @author Mahaveer Manoj
 * @purpose: Rest ELK Client configuration 
 * @since 15th August 2019
 */

@Component
public class SourceleadELKRestClient {
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadELKRestClient.class);
	   
    private RestHighLevelClient client;
    
    ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

    public RestHighLevelClient getClient() {
    	client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(dbconfig.getHost(), 9200, "http"))
				.setMaxRetryTimeoutMillis(100000)
				.setRequestConfigCallback(requestConfigBuilder -> 
                requestConfigBuilder
                .setConnectTimeout(5000)
                .setSocketTimeout((int)10000))
			);
//    	logger.info("RestClient created");
    	return client;
    }

    public void shutdown() {
		try {
			client.close();
//			System.gc();
//			logger.info("RestClient closed");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception occured at SourceleadELKRestClient -- shutdown()");
			logger.error("Exception occured at SourceleadELKRestClient -- shutdown()");
		}
	}
}
