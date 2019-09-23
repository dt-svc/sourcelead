package com.bis.sourcelead.elk.setup;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
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
   
    private TransportClient client;
    
     ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	 DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
    
	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
    public SourceleadELKTransportClient() {
	     try {
	    	 Settings settings = Settings.builder()
	    			  .put("cluster.name", dbconfig.getCluster())
	                  .put("node.name", dbconfig.getNode())
	         		  .put("path.home", dbconfig.getPath())
	                  .build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(dbconfig.getHost()),  new Integer(dbconfig.getTcport())));
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
	//client = null;
    }
}
