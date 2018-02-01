package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.elasticsearch.index.engine.DocumentMissingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKTC;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class AddCampaignELK {
	private static final Logger logger = LoggerFactory.getLogger(AddCampaignELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private SourceleadELKTC client;
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addCampaignELK() throws IOException, SQLException {
		ArrayList<CampaignIndex> elkCampaignList = getElkCampaigns();
		if(elkCampaignList != null && !elkCampaignList.isEmpty()){
			for (CampaignIndex campaignIndex :elkCampaignList) {
				saveOrUpdateElkDocument(campaignIndex, Constants.ELK_CAMPAIGN_INDEX, Constants.ELK_CAMPAIGN_TYPE,campaignIndex.getMailBoxId().toString());
			}
		}
		  logger.info("Campaigns Successfully Added To Elk");
	}
	
	private ArrayList<CampaignIndex> getElkCampaigns(){
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<CampaignIndex> campaignIndexsList = new ArrayList<CampaignIndex>();
		 try {
				String sql = "SELECT * FROM sourcelead.USER_MAIL_BOX mailbox";
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	CampaignIndex campaignIndex = new CampaignIndex();
			    	
			    	campaignIndex = new CampaignIndex();
					campaignIndex.setMailBoxId(rs.getLong("ID"));
					campaignIndex.setUserId(rs.getLong("USER_ID"));
					campaignIndex.setOrgLocationId(rs.getLong("ORG_LOCATION_ID"));
					campaignIndex.setToMail(rs.getString("TO_MAIL"));
					campaignIndex.setFromMail(rs.getString("FROM_MAIL"));
					campaignIndex.setMailStatus(rs.getString("MAIL_STATUS"));
					campaignIndex.setSubject(rs.getString("SUBJECT"));
					if(!isNullOrEmpty(rs.getString("CAMPAIGN_TABLE_DATA"))){
						campaignIndex.setCampaignTableData(rs.getString("CAMPAIGN_TABLE_DATA"));
					}
					
					if(!isNullOrEmpty(rs.getString("BODY"))){
						campaignIndex.setBody(rs.getString("BODY"));
					}
					
					if(!isNullOrEmpty(rs.getString("CANDIDATE_ID"))){
						campaignIndex.setCandidateId(rs.getString("CANDIDATE_ID"));
					}
					campaignIndex.setUuid(rs.getString("UUID"));
					campaignIndex.setIsFavourite(rs.getInt("IS_FAVOURITE"));
					
					if(!isNullOrEmpty(rs.getString("NAME_FIELDS"))){
						campaignIndex.setNameFields(rs.getString("NAME_FIELDS"));
					}
					
					if(!isNullOrEmpty(rs.getString("PHONE_NO"))){
						campaignIndex.setPhno(rs.getString("PHONE_NO"));
					}
					
					if(!isNullOrEmpty(rs.getString("MAIL_TYPE"))){
						campaignIndex.setMailType(rs.getString("MAIL_TYPE"));
					}
					
					if(!isNullOrEmpty(rs.getString("COMMENTS"))){
						campaignIndex.setComments(rs.getString("COMMENTS"));
					}
					
					campaignIndex.setStatus(rs.getInt("STATUS"));
					
					if(!isNullOrEmpty(rs.getString("SIGNATURE"))){
						campaignIndex.setSignature(rs.getString("SIGNATURE"));
					}
					
					if(!isNullOrEmpty(rs.getString("FILE_CONTENT_TYPE"))){
						campaignIndex.setFileContentType(rs.getString("FILE_CONTENT_TYPE"));
					}
					
					if(!isNullOrEmpty(rs.getString("FILE_NAME"))){
						campaignIndex.setFileName(rs.getString("FILE_NAME"));
					}
					
					campaignIndex.setIsMailRead(rs.getInt("IS_MAIL_READ"));
					
					if(!isNullOrEmpty(rs.getString("CREATED_BY"))){
						campaignIndex.setCreatedBy(rs.getString("CREATED_BY"));
					}
					
					if(!isNullOrEmpty(rs.getString("UPDATED_BY"))){
						campaignIndex.setUpdatedBy(rs.getString("UPDATED_BY"));
					}
					
					Timestamp createdDateTimestamp = rs.getTimestamp("CREATED_DATE");
				    if (createdDateTimestamp != null){
				    	campaignIndex.setCreatedDate(new java.util.Date(createdDateTimestamp.getTime()));
				    }
					Timestamp updatedDateTimestamp = rs.getTimestamp("UPDATED_DATE");
					if (updatedDateTimestamp != null){
						campaignIndex.setUpdatedDate(new java.util.Date(updatedDateTimestamp.getTime()));
				    }
			    	
					campaignIndexsList.add(campaignIndex);
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return campaignIndexsList;
	}
	
	public void saveOrUpdateElkDocument(CampaignIndex campaignIndex,String index,String type,String id) {
		String entityInJson = null;
		 Map<String, Object> d = new HashMap<>();
		try {
		    entityInJson = toJSON(campaignIndex);
		    JSONObject json = new JSONObject(entityInJson);
		   
		    Iterator<String> it = json.keys();
		    while(it.hasNext()) {
		    	String key = it.next();
		    		d.put(key, json.isNull(key) ? null : json.get(key));
		    }
		    client.getClient().prepareUpdate(index, type,id).setDoc(d).get();
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        		client.getClient().prepareIndex(index, type,id)
		    .setSource(d).execute().actionGet();
		    logger.info(type+" has been indexed.");
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
     }
	
	/*public void saveOrUpdateElkDocument(CampaignIndex campaignIndex,String index,String type,String id) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(campaignIndex);
		    client.getClient().prepareUpdate(index, type,id).setDoc(entityInJson).get();
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.getClient().prepareIndex(index, type,id)
		    .setSource(entityInJson).execute().actionGet();
		    logger.info(type+" has been indexed.");
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
     }*/
	
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	  }
	 
	 
	@Bean
	public DataSource comboPooledDataSource() {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);

		this.comboPooledDataSource = new ComboPooledDataSource();
		try {
			comboPooledDataSource.setDriverClass(dbconfig.getDriverClass());
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		comboPooledDataSource.setJdbcUrl(dbconfig.getJdbcUrl());
		comboPooledDataSource.setUser(dbconfig.getUser());
		comboPooledDataSource.setPassword(dbconfig.getPassword());
		
		return comboPooledDataSource;
	}
	public static void main(String[] args) throws IOException {
			
		try {
			   AddCampaignELK addCampaignELK = new AddCampaignELK();
			   addCampaignELK.client = new SourceleadELKTC();
			   addCampaignELK.addCampaignELK();
			   addCampaignELK.client.shutdown();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
