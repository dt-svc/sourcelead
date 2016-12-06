package com.bis.sourcelead.util;

import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Component
public  class SourceleadELKBaseIndexer<T> {
	private static final Logger logger = LoggerFactory.getLogger(SourceleadELKBaseIndexer.class);
	
	@Autowired
	SourceleadESTransportClient sourceleadESTransportClient;
	
	public void saveElkDocument(T entityClass,String index,String type) {
		String candidateJson = null;
		try {
			candidateJson = toJSON(entityClass);
		    sourceleadESTransportClient.getClient().prepareIndex(index, type)
			    .setSource(candidateJson).execute().actionGet();
		    logger.info(type+" has been indexed.");
		} catch (Exception e) {
			logger.error(type+" could not be serialized. ");
		}
		sourceleadESTransportClient.shutdown();
    }
	
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
	
    boolean createIndex(){
    	return false;
    }

     boolean upgradeIndex(){
    	return false;
    }

     boolean indexDocument(T document){
    	return false;
    }

     boolean deleteDocument(T document){
    	return false;
    }


}
