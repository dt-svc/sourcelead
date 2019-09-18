package com.bis.sourcelead.elk.setup;

import java.io.Writer;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Component
public class SourceleadELKBaseIndexer<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadELKBaseIndexer.class);

	@Autowired
	SourceleadELKRestClient sourceleadELKRestClient;

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
			value = toJSON(entityClass);
			
			IndexRequest indexRequest = new IndexRequest(index, type, id).source(value, XContentType.JSON);
			
			if(sourceleadELKRestClient == null)
				sourceleadELKRestClient = new SourceleadELKRestClient();
			
			IndexResponse indexResponse = sourceleadELKRestClient.getClient().index(indexRequest, RequestOptions.DEFAULT);
			if(indexResponse.getResult() == Result.CREATED || indexResponse.getResult() == Result.UPDATED) {
				logger.info("ID : "+ id + " ---- " + type + " has been " + indexResponse.getResult().toString().toLowerCase());
			}
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
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
			DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
			DeleteResponse deleteResponse = sourceleadELKRestClient.getClient().delete(deleteRequest, RequestOptions.DEFAULT);
			if(deleteResponse.getResult() == Result.DELETED) {
				logger.info(index + " Document With id :" + id + " has been deleted.");
			}
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(index + " Document With id :" + id + " deletion fails " + ExceptionUtils.getFullStackTrace(e));
		}
	}

	public SearchHits searchDocumentWithMaxResults(String index, String type, int offset, int maxResults,
			BoolQueryBuilder query) {
		return searchDocumentWithMaxResults(index, type, offset, maxResults, "updatedDate", SortOrder.DESC, query).getHits();
	}

	public SearchResponse searchDocumentWithMaxResults(String index, String type, int offset, int maxResults,
			String sortColoumn, SortOrder sortOrder, BoolQueryBuilder query) {
		SearchResponse response = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(query);
			searchSourceBuilder.from(offset);
			searchSourceBuilder.size(maxResults);
			searchSourceBuilder.sort(sortColoumn, sortOrder);

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(searchSourceBuilder);
			
			RestHighLevelClient client = sourceleadELKRestClient.getClient();
			if(client != null) {
				response = client.search(searchRequest, RequestOptions.DEFAULT);
			} else {
				logger.info("Client is null");
			}
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured at searchDocumentWithMaxResults()");
		}
		return response;
	}
		
	public SearchResponse searchDocumentWithGlobalSearchResult(String index, String type, BoolQueryBuilder query) {
		SearchResponse response = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.query(query);

			response = sourceleadELKRestClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured at searchDocumentWithGlobalSearchResult()");
		}
		return response;
	}
	
	public SearchResponse searchDocumentsResults(String index, String type, int offset, int maxResults,
			  BoolQueryBuilder query) {
		SearchResponse response = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.from(offset);
			searchSourceBuilder.size(maxResults);

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.query(query);

			response = sourceleadELKRestClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured at searchDocumentsResults()");
		}
		return response;
	}
	
	public SearchHits searchDocumentWithMaxResultsForCampaigns(String index, String type, int offset, int maxResults,
			BoolQueryBuilder query) {
		SearchHits searchHits = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.from(offset);
			searchSourceBuilder.size(maxResults);
			searchSourceBuilder.sort("mailBoxId", SortOrder.DESC);

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.indices(index);
			searchRequest.types(type);
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.query(query);

			SearchResponse response = sourceleadELKRestClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured at searchDocumentWithMaxResultsForCampaigns()");
		}
		return searchHits;
	}

	public SearchHits searchDocument(String index, String type, BoolQueryBuilder query) {
		SearchHits searchHits = null;
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

			SearchRequest searchRequest = new SearchRequest();
			searchRequest.types(type);
			searchRequest.indices(index);
			searchRequest.source(searchSourceBuilder);
			searchSourceBuilder.query(query);

			SearchResponse response = sourceleadELKRestClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			sourceleadELKRestClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured at searchDocument()");
		}
		return searchHits;
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