package com.bis.sourcelead.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @purpose:Elastic Search Transport Client Configuration
 * @author: Naresh Bolisetty
 * @date: 06-December-2016
 */
@Configuration
@PropertySource("${propertyLocation:app.properties}")
public class SourceleadESTransportClient {

    private TransportClient client;
	@Autowired
	private Environment env;
    
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
    public SourceleadESTransportClient() {
	  final  Settings settings = Settings.settingsBuilder()
              .put("cluster.name", "sourcelead")
              .put("node.name", "sourcelead_node")
      		  .put("path.home", "/home/sourcelead_es")
		    /* .put("cluster.name", cluster)
              .put("node.name", node)
      		  .put("path.home", path)*/
              .build();
	       try {
			client = TransportClient.builder().settings(settings).build().
			      addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		} catch (UnknownHostException e) {
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

	public void setEnv(Environment env) {
		this.env = env;
	}
}
