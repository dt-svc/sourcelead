package com.bis.sourcelead.elk.setup;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Component
public class SourceleadELKBaseIndexer<T> {
	private static final Logger logger = LoggerFactory.getLogger(SourceleadELKBaseIndexer.class);

	@Autowired
	SourceleadELKTransportClient sourceleadELKTransportClient;

	@Bean
	public SourceleadELKBaseIndexer<T> getSourceleadELKBaseIndexer() {
		return new SourceleadELKBaseIndexer<T>();
	}

	/**
	 * 
	 * @param entityClass
	 * @param index
	 * @param type
	 * @param id
	 * @purpose : For To Save/Update Elk Document Based On id
	 */
	public void saveOrUpdateElkDocument(T entityClass, String index, String type, String id) {
		String value = "";
		try {
			value = toJSON(entityClass); //new ObjectMapper().writeValueAsString(entityClass);
			sourceleadELKTransportClient.getClient().prepareUpdate(index, type, id).setDoc(value, XContentType.JSON).get();
			logger.info(type + " has been updated.");
		} catch (DocumentMissingException e) {
			sourceleadELKTransportClient.getClient().prepareIndex(index, type, id).setSource(value, XContentType.JSON).execute()
					.actionGet();
			logger.info(type + " has been indexed.");
		} catch (Exception e) {
			logger.error(type + " could not be serialized. " + e.getLocalizedMessage());
		}

	}
	
	/**
	 * @param entityClass
	 * @param index
	 * @param type
	 * @param id
	 * @purpose : For To Delete Elk Document Based on db(ElkDocument) id
	 */
	public void deleteElkDocument(T entityClass, String index, String type, String id) {
		try {
			sourceleadELKTransportClient.getClient().prepareDelete(index, type, id).execute().actionGet();
			// sourceleadELKTransportClient.shutdown();
			logger.info(index + " Document With id :" + id + " has been deleted.");
		} catch (Exception e) {
			logger.error(index + " Document With id :" + id + " deletion fails " + ExceptionUtils.getFullStackTrace(e));
		}

	}

	public SearchHits searchDocumentWithMaxResults(String index, String type, int offset, int maxResults,
			BoolQueryBuilder query) {
		return searchDocumentWithMaxResults(index, type, offset, maxResults, "updatedDate", SortOrder.DESC, query).getHits();
	}

	public SearchResponse searchDocumentWithMaxResults(String index, String type, int offset, int maxResults,
			String sortColoumn, SortOrder sortOrder, BoolQueryBuilder query) {
		SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(index).setTypes(type)
				.setQuery(query).addSort(sortColoumn, sortOrder).setFrom(offset).setSize(maxResults).execute()
				.actionGet();

		return response;
	}
	
	public SearchResponse searchDocumentsResults(String index, String type, int offset, int maxResults,
			  BoolQueryBuilder query) {
		SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(index).setTypes(type)
				.setQuery(query).setFrom(offset).setSize(maxResults).execute()
				.actionGet();

		return response;
	}
	
	public SearchResponse searchDocumentWithGlobalSearchResult(String index, String type, BoolQueryBuilder query) {
		SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(index).setTypes(type)
				.setQuery(query).execute()
				.actionGet();

		return response;
	}
	
	

	public SearchHits searchDocumentWithMaxResultsForCampaigns(String index, String type, int offset, int maxResults,
			BoolQueryBuilder query) {
		SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(index)

				.setTypes(type).setQuery(query).addSort("mailBoxId", SortOrder.DESC).setFrom(offset).setSize(maxResults)
				.execute().actionGet();

		return response.getHits();

	}

	public SearchHits searchDocument(String index, String type, BoolQueryBuilder query) {
		SearchResponse response = sourceleadELKTransportClient.getClient().prepareSearch(index).setTypes(type)
				.setQuery(query).execute().actionGet();

		/*
		 * System.out.println("Current results: " + results.length); for (SearchHit hit
		 * : results) { System.out.println("------------------------------");
		 * Map<String,Object> result = hit.getSource(); System.out.println(result); }
		 */

		return response.getHits();

	}

	public String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

}
