package com.bis.sourcelead.elk.setup;

import java.io.Writer;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Configuration
public  class SourceleadELKBaseIndexer<T> {
	private static final Logger logger = LoggerFactory.getLogger(SourceleadELKBaseIndexer.class);
	
	SourceleadELKTransportClient sourceleadELKTransportClient = new SourceleadELKTransportClient();
	
	/**
	 * 
	 * @param entityClass
	 * @param index
	 * @param type
	 * @param id
	 * @purpose : For To Save Fresh Elk Document
	 */
	public void saveElkDocument(T entityClass,String index,String type,String id) {
		try {
			String entityInJson = toJSON(entityClass);
			sourceleadELKTransportClient.getClient().prepareIndex(index, type,id)
			    .setSource(entityInJson).execute().actionGet();
			
			sourceleadELKTransportClient.shutdown();
		    logger.info(type+" has been indexed.");
		} catch (Exception e) {
			logger.error(type+" could not be serialized. "+ExceptionUtils.getFullStackTrace(e));
		}
		
    }
	
	
	/**
	 * 
	 * @param entityClass
	 * @param index
	 * @param type
	 * @param id
	 *  @purpose : For To Update  Elk Document Based on db(ElkDocument) id
	 */
	public void updateElkDocument(T entityClass,String index,String type,String id) {
		try {
			String entityInJson = toJSON(entityClass);
			sourceleadELKTransportClient.getClient().prepareUpdate(index, type,id).setDoc(entityInJson).get();
			sourceleadELKTransportClient.shutdown();
		    logger.info(type+" has been indexed.");
		} catch (Exception e) {
			logger.error(type+" could not be serialized. "+ExceptionUtils.getFullStackTrace(e));
		}
		
    }
	
	
	/**
	 * @param entityClass
	 * @param index
	 * @param type
	 * @param id
	 *  @purpose : For To Delete  Elk Document Based on db(ElkDocument) id
	 */
	public void deleteElkDocument(T entityClass,String index,String type,String id) {
		try {
			 sourceleadELKTransportClient.getClient().prepareDelete(index, type,id).execute().actionGet();
			 sourceleadELKTransportClient.shutdown();
			 logger.info(index+" Document With id :"+ id +" has been deleted.");
		}
		catch (Exception e) {
			logger.error(index+" Document With id :"+ id +" deletion fails "+ExceptionUtils.getFullStackTrace(e));
		}
		
		
	}
	public  void searchDocument(String index,String type, String field, String value){
           
		
		//  BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
               

			//SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(paramArrayOfString).
					
					
				/*	get.prepareSearch(index)
			              .setTypes(type)
			              .setSearchType(SearchType.QUERY_AND_FETCH)
			              .setQuery(fieldQuery(field, value))
			              .setFrom(0).setSize(60).setExplain(true)
			              .execute()
			              .actionGet();*/
			
			/*SearchHit[] results = response.getHits().getHits();
			
			System.out.println("Current results: " + results.length);
			for (SearchHit hit : results) {
			System.out.println("------------------------------");
			Map<String,Object> result = hit.getSource();
			System.out.println(result);
			}*/

   }
	
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	  }
	
   


}
