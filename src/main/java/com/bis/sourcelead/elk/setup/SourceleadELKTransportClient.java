package com.bis.sourcelead.elk.setup;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @purpose:Elastic Search Transport Client Configuration
 * @author: Naresh Bolisetty
 * @date: 06-December-2016
 */
@Component
public class SourceleadELKTransportClient {
    private static final Logger logger = LoggerFactory.getLogger(SourceleadELKTransportClient.class);
   
    private TransportClient client;
    
    //ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	//DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
    
	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
    public SourceleadELKTransportClient() {
	     try {
	    	  Settings settings = Settings.settingsBuilder()
	    			  .put("cluster.name", "elasticsearch")
	                  .put("node.name", "Tundra")
	         		  .put("path.home", "/usr/share/elasticsearch")
	                  .build();
			client = TransportClient.builder().settings(settings).build().
					addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.125"), 9300));
	     } catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
    }

	/** {@inheritDoc} */
    public Client getClient() {
	return client;
    }

    /** {@inheritDoc} */
    public void shutdown() {
	client.close();
	client = null;
    }
}
