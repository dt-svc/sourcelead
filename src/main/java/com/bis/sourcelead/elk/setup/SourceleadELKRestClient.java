package com.bis.sourcelead.elk.setup;

import java.io.Writer;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class SourceleadELKRestClient<T> {

	RestHighLevelClient client;
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

	public SourceleadELKRestClient() {
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

	public String toJSON(Object obj) {
		System.out.println("inside SourceleadELKRestClient --> toJSON()");
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	@SuppressWarnings("unchecked")
	public void saveOrUpdateElkDocument(T userIndex, String index, String type, String id) {
		System.out.println("enterd saveOrUpdateElkDocument() in SourceleadELKRestClient.java");
		String value = "";
		try {
			value = toJSON(userIndex);
			IndexRequest indexRequest = new IndexRequest(index).type(type).id(id).source(value, XContentType.JSON);
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			System.out.println(type + " has been " + indexResponse.getResult().toString().toLowerCase());
		} catch (ElasticsearchException e) {
			System.out.println(e.status().toString());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("In Exception ---------");
			e.printStackTrace();
		}
		System.out.println("exited from saveOrUpdateElkDocument() in SourceleadELKRestClient.java");
	}

	public RestHighLevelClient getClient() {
		return client;
	}

	public void shutdown() {
		try {
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
