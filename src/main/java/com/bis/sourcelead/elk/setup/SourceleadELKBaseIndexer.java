package com.bis.sourcelead.elk.setup;

import java.io.Writer;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
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
			IndexRequest indexRequest = new IndexRequest(index).type(type).id(id).source(value, XContentType.JSON);
			IndexResponse indexResponse = sourceleadELKTransportClient.getClient().index(indexRequest, RequestOptions.DEFAULT);
			System.out.println(type + " has been " + indexResponse.getResult().toString().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(type + " could not be serialized. " + e.getLocalizedMessage());
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
			sourceleadELKTransportClient.getClient().delete(deleteRequest, RequestOptions.DEFAULT);
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

			response = sourceleadELKTransportClient.getClient().search(searchRequest, RequestOptions.DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
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

			response = sourceleadELKTransportClient.getClient().search(searchRequest, RequestOptions.DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
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

			response = sourceleadELKTransportClient.getClient().search(searchRequest, RequestOptions.DEFAULT);

		} catch (Exception e) {
			e.printStackTrace();
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

			SearchResponse response = sourceleadELKTransportClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
		} catch (Exception e) {
			e.printStackTrace();
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

			SearchResponse response = sourceleadELKTransportClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
		} catch (Exception e) {
			e.printStackTrace();
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
