package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorIndexHelper;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;




public class AddClientVendorELK {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	OrgService orgService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AddClientVendorELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private TransportClient client;
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addCampaignELK() throws IOException, SQLException,Exception{
		ArrayList<ClientVendorIndex> elkclientVendorIndexList = getElkCampaigns();
		if(elkclientVendorIndexList != null && !elkclientVendorIndexList.isEmpty()){
			for (ClientVendorIndex clientVendorIndex :elkclientVendorIndexList) {
				
			       saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,clientVendorIndex.getId().toString());
			}
		}
		  logger.info("ORG_RELATIONS Successfully Added To Elk");
	}
	
	private ArrayList<ClientVendorIndex> getElkCampaigns() throws Exception{
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<ClientVendorIndex> clientVendorList = new ArrayList<ClientVendorIndex>();
		 try {
				
				String sql = "SELECT * FROM sourcelead.ORG_RELATIONS relation";
				
				Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	
			    	ClientVendorIndex campaignIndex = new ClientVendorIndex();
                       String createdBy =  rs.getString("CREATED_BY");
			    	   Long  responderLocationId = rs.getLong("RESPONDER_LOCATION_ID");
			    	   
			    	campaignIndex.setId(rs.getLong("ID"));
			    	campaignIndex.setInitiatorLevel(rs.getString("INITIATOR_LEVEL"));
			    	campaignIndex.setResponderLevel(rs.getString("RESPONDER_LEVEL"));
			    	campaignIndex.setResponderLocationId(rs.getLong("RESPONDER_LOCATION_ID"));
			    	campaignIndex.setResponderType(rs.getString("RESPONDER_TYPE"));
			    	campaignIndex.setInitiatorType(rs.getString("INITIATOR_TYPE"));
			    	campaignIndex.setOrgLocationId(rs.getLong("LOCATION_ID"));
			    	campaignIndex.setOrgRelationId(rs.getLong("ID"));
			    	campaignIndex.setStatus(rs.getString("STATUS"));
			    	
			    	String sql1 = "SELECT * FROM sourcelead.USER  user where  user.USER_CODE = '"+createdBy+"' ";
						Statement stmt1 = conn.createStatement();
						ResultSet rs1 = stmt1.executeQuery(sql1);
						if(rs1 != null){
							 while(rs1.next()){
								  campaignIndex.setCreatedBy(createdBy);
								   if(null != rs1.getString("FIRST_NAME") && null != rs1.getString("LAST_NAME")){
									   StringBuilder sb =  new StringBuilder();
									   campaignIndex.setCreatedBy(sb.append(rs1.getString("FIRST_NAME")).append("").append(rs1.getString("LAST_NAME")).toString());
								   }else if(null != rs1.getString("FIRST_NAME")){
									   StringBuilder sb =  new StringBuilder();
									   campaignIndex.setCreatedBy(sb.append(rs1.getString("FIRST_NAME")).toString());
								   }
						    }
	    	           }
						String sql2 = "SELECT * FROM sourcelead.ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+responderLocationId+"' ";
						Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(sql2);
						if(rs2 != null){
							 while(rs2.next()){
								 Long orgId = rs2.getLong("ORG_ID");
								   if(null != rs2.getString("COUNTRY")){
									   campaignIndex.setCountry(rs2.getString("COUNTRY"));
								   }
								   if(null != rs2.getString("STATE")){
									   campaignIndex.setState(rs2.getString("STATE"));
								   }
								   if(null != rs2.getString("CITY")){
									   campaignIndex.setCity(rs2.getString("CITY"));
								   }
								   
								   String sql3 = "SELECT * FROM sourcelead.ORG  org where  org.ID = '"+orgId+"' ";
									Statement stmt3 = conn.createStatement();
									ResultSet rs3 = stmt3.executeQuery(sql3);
									if(rs3 != null){
										 while(rs3.next()){
											   if(null != rs3.getString("ORG_NAME")){
												   campaignIndex.setOrgName(rs3.getString("ORG_NAME"));
											   }
											   if(null != rs3.getString("WEBSITE")){
												   campaignIndex.setWebsite(rs3.getString("WEBSITE"));
											   }
									    }
				    	           }
						    }
							
	    	           }
						clientVendorList.add(campaignIndex);
			    	/*
			    	OrgRelations relation = orgRelationsService.getOrRelationsById(rs.getLong("ID"));
			    	
			    	OrgLocations clientOrgLocations =  orgLocationService.getOrgLocationByOrgId(relation.getResponderLocationId().getId());
			    	Org org  = orgService.findOrgByID(clientOrgLocations.getOrg().getId());
			    	clientService.saveOrUpdateClientVendorElkDocument(org, clientOrgLocations, null,null, relation, null);
					campaignIndexsList.add(campaignIndex);*/
			    }
			   conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return clientVendorList;
	}
	
	
	public void saveOrUpdateElkDocument(ClientVendorIndex clientVendorIndex,String index,String type,String id) {
		String entityInJson = null;
		try {
		    entityInJson = toJSON(clientVendorIndex);
		    client.prepareUpdate(index, type,id).setDoc(entityInJson).get();
		    logger.info(type+" has been updated.");
		} 
        catch(DocumentMissingException e){
        	client.prepareIndex(index, type,id)
		    .setSource(entityInJson).execute().actionGet();
		    logger.info(type+" has been indexed.");
		}
		catch (Exception e) {
			logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
		}
		
     }
	
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
		
		try {
	    	  Settings settings = Settings.settingsBuilder()
	    			  .put("cluster.name", dbconfig.getCluster())
	                  .put("node.name", dbconfig.getNode())
	         		  .put("path.home", dbconfig.getPath())
	                  .build();
			client = TransportClient.builder().settings(settings).build().
			      addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(dbconfig.getHost()), 9300));
	     } catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		
		return comboPooledDataSource;
	}
	public static void main(String[] args) throws IOException {
			
		try {
			   AddClientVendorELK addCampaignELK = new AddClientVendorELK();
			   addCampaignELK.addCampaignELK();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
