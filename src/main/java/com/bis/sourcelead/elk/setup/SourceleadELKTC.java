package com.bis.sourcelead.elk.setup;

import java.io.Writer;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * @purpose:Elastic Search Transport Client Configuration
 * @author: Naresh Bolisetty
 * @date: 06-December-2016
 */
public class SourceleadELKTC<T> {
    private static final Logger logger = LoggerFactory.getLogger(SourceleadELKTC.class);
   
    private RestHighLevelClient client;
    
     ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	 DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
    
	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
	 public SourceleadELKTC() {
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
    
    public void saveOrUpdateElkDocument(T userIndex, String index, String type, String id) {
		String value = "";
		try {
			value = toJSON(userIndex);
			
			IndexRequest indexRequest = new IndexRequest(index).type(type).id(id).source(value, XContentType.JSON);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			
		    System.out.println("ID : "+ id + " ---- " + type+" has been updated.");
		} catch (Exception e) {
			System.out.println("In Exception ---------");
			e.printStackTrace();
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
     }

	public String toJSON(Object obj) {
		System.out.println("inside SourceleadELKTC --> toJSON()");
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}


	public RestHighLevelClient getClient() {
		return client;
	}

	public void shutdown() {
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
