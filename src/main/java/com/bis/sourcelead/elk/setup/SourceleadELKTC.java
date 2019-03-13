package com.bis.sourcelead.elk.setup;

import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.UserIndex;
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
   
    private TransportClient client;
    
     ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	 DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
    
	/**
     * Default constructor, initializing the transport client.
     * @throws UnknownHostException 
     */
    public SourceleadELKTC() {
	     try {
	    	 try {
		    	  Settings settings = Settings.builder()
		    			  .put("cluster.name", dbconfig.getCluster())
		                  .put("node.name", dbconfig.getNode())
		         		  .put("path.home", dbconfig.getPath())
		                  .build();
				client = new PreBuiltTransportClient(settings)
						.addTransportAddress(new TransportAddress(InetAddress.getByName(dbconfig.getHost()), new Integer(dbconfig.getTcport())));
		     } catch (Exception exception) {
				logger.error(ExceptionUtils.getFullStackTrace(exception));
			}
			
	     } catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
    }
    
    public void saveOrUpdateElkDocument(T userIndex, String index, String type, String id) {
		String value = "";
		try {
			value = toJSON(userIndex);
		    client.prepareUpdate(index, type,id).setDoc(value, XContentType.JSON).get();
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.prepareIndex(index, type,id)
		    .setSource(value, XContentType.JSON).execute().actionGet();
		    logger.info(type+" has been indexed. " + id);
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
     }

	public String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
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
