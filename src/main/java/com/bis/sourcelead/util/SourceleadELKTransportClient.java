package com.bis.sourcelead.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
   
    
    @Value("${elk.cluster}")
	private String cluster;

	@Value("${elk.node}")
	private String node;

	@Value("${elk.path}")
	private String path;

	@Value("${elk.host}")
	private String host;

	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
    public SourceleadELKTransportClient() {
	     try {
	    	 final  Settings settings = Settings.settingsBuilder()
	    			  .put("cluster.name", "sourcelead")
	                  .put("node.name", "sourcelead_node")
	         		  .put("path.home", "/home/sourcelead_es")
	                  .build();
			client = TransportClient.builder().settings(settings).build().
			      addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
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
