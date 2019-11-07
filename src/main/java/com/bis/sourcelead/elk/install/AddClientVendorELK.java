package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;




public class AddClientVendorELK {
	
	private static final Logger logger = LoggerFactory.getLogger(AddClientVendorELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private SourceleadELKBaseIndexer<ClientVendorIndex> client;
	 ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addClientVendorELK() throws IOException, SQLException,Exception{
		ArrayList<ClientVendorIndex> elkclientVendorIndexList = getElkClientVendors();
		if(elkclientVendorIndexList != null && !elkclientVendorIndexList.isEmpty()){
			for (ClientVendorIndex clientVendorIndex :elkclientVendorIndexList) {
				
			      client.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,clientVendorIndex.getId().toString());
			}
		}
		  logger.info("ORG_RELATIONS Successfully Added To Elk");
	}
	
	private ArrayList<ClientVendorIndex> getElkClientVendors() throws Exception{
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<ClientVendorIndex> clientVendorList = new ArrayList<ClientVendorIndex>();
		Connection conn = dataSource.getConnection();
		 try {
				
				String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS relation";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	
			    	ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
                       String createdBy =  rs.getString("CREATED_BY");
			    	   Long  responderLocationId = rs.getLong("RESPONDER_LOCATION_ID");
			    	   Long  initiatorLocationId = rs.getLong("LOCATION_ID");
			    	   
			    	clientVendorIndex.setId(rs.getLong("ID"));
			    	clientVendorIndex.setInitiatorLevel(rs.getString("INITIATOR_LEVEL"));
			    	clientVendorIndex.setResponderLevel(rs.getString("RESPONDER_LEVEL"));
			    	clientVendorIndex.setResponderLocationId(rs.getLong("RESPONDER_LOCATION_ID"));
			    	clientVendorIndex.setResponderType(rs.getString("RESPONDER_TYPE"));
			    	clientVendorIndex.setInitiatorType(rs.getString("INITIATOR_TYPE"));
			    	clientVendorIndex.setOrgLocationId(rs.getLong("LOCATION_ID"));
			    	clientVendorIndex.setOrgRelationId(rs.getLong("ID"));
			    	clientVendorIndex.setStatus(rs.getString("STATUS"));
			    	clientVendorIndex.setOrgRelationUUID(rs.getString("UUID"));
			    	
			    	if(null != rs.getString("VENDOR_ONBOARD_TEMPLATE_ID")){
			    		clientVendorIndex.setVendorOnboardTemplateId(rs.getString("VENDOR_ONBOARD_TEMPLATE_ID"));
			    	}
			    	if(null != rs.getString("VENDOR_RELATION_STATUS")){
			    		clientVendorIndex.setVendorRelationStatus(rs.getString("VENDOR_RELATION_STATUS"));
			    	}
			    	
			    	clientVendorIndex.setNoContacts(getNumberOfContactsPerClient(rs.getLong("ID"), conn));
			    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    	clientVendorIndex.setCreatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
			    	if(null != rs.getDate("UPDATED_DATE")){
			    		clientVendorIndex.setUpdatedDate(dateFormat.format(rs.getDate("UPDATED_DATE")));
			    	}else{
			    		clientVendorIndex.setUpdatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
			    	}
			    	
			    	
			    	String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where  user.USER_CODE = '"+createdBy+"' ";
						Statement stmt1 = conn.createStatement();
						ResultSet rs1 = stmt1.executeQuery(sql1);
						if(rs1 != null){
							 while(rs1.next()){
								  clientVendorIndex.setCreatedBy(createdBy);
								   if(null != rs1.getString("FIRST_NAME") && null != rs1.getString("LAST_NAME")){
									   StringBuilder sb =  new StringBuilder();
									   clientVendorIndex.setCreatedBy(sb.append(rs1.getString("FIRST_NAME")).append(" ").append(rs1.getString("LAST_NAME")).toString());
								   }else if(null != rs1.getString("FIRST_NAME")){
									   StringBuilder sb =  new StringBuilder();
									   clientVendorIndex.setCreatedBy(sb.append(rs1.getString("FIRST_NAME")).toString());
								   }
						    }
	    	           }
						String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+responderLocationId+"' ";
						Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(sql2);
						if(rs2 != null){
							 while(rs2.next()){
								 Long orgId = rs2.getLong("ORG_ID");
								   if(null != rs2.getString("COUNTRY")){
									   clientVendorIndex.setResponderCountry(rs2.getString("COUNTRY"));
								   }
								   if(null != rs2.getString("STATE")){
									   clientVendorIndex.setResponderState(rs2.getString("STATE"));
								   }
								   if(null != rs2.getString("CITY")){
									   clientVendorIndex.setResponderCity(rs2.getString("CITY"));
								   }
								   if(null != rs2.getString("ORG_NAME")){
									   clientVendorIndex.setResponderOrgName(rs2.getString("ORG_NAME"));
								   } 
								   if(null != rs2.getString("WEB_SITE")){
									   clientVendorIndex.setResponderWebSite(rs2.getString("WEB_SITE"));
								   }
								   
								   
								   
							/*	   String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".ORG  org where  org.ID = '"+orgId+"' ";
									Statement stmt3 = conn.createStatement();
									ResultSet rs3 = stmt3.executeQuery(sql3);
									if(rs3 != null){
										 while(rs3.next()){
											   if(null != rs3.getString("ORG_NAME")){
												   clientVendorIndex.setResponderOrgName(rs3.getString("ORG_NAME"));
											   }
											   if(null != rs3.getString("WEBSITE")){
												   clientVendorIndex.setResponderWebSite(rs3.getString("WEBSITE"));
											   }
									    }
				    	           }*/
						    }
							
	    	           }
						String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+initiatorLocationId+"' ";
						Statement stmt4 = conn.createStatement();
						ResultSet rs4 = stmt4.executeQuery(sql4);
						if(rs4 != null){
							 while(rs4.next()){
								 Long orgId = rs4.getLong("ORG_ID");
								   if(null != rs4.getString("COUNTRY")){
									   clientVendorIndex.setInitiatorCountry(rs4.getString("COUNTRY"));
								   }
								   if(null != rs4.getString("STATE")){
									   clientVendorIndex.setInitiatorState(rs4.getString("STATE"));
								   }
								   if(null != rs4.getString("CITY")){
									   clientVendorIndex.setInitiatorCity(rs4.getString("CITY"));
								   }
								   if(null != rs4.getString("ORG_NAME")){
									   clientVendorIndex.setInitiatorOrgName(rs4.getString("ORG_NAME"));
								   } 
								   if(null != rs4.getString("WEB_SITE")){
									   clientVendorIndex.setInitiatorWebSite(rs4.getString("WEB_SITE"));
								   }
								   
							/*	   String sql5 = "SELECT * FROM "+dbconfig.getSchema()+".ORG  org where  org.ID = '"+orgId+"' ";
									Statement stmt5 = conn.createStatement();
									ResultSet rs5 = stmt5.executeQuery(sql5);
									if(rs5 != null){
										 while(rs5.next()){
											   if(null != rs5.getString("ORG_NAME")){
												   clientVendorIndex.setInitiatorOrgName(rs5.getString("ORG_NAME"));
											   }
											   if(null != rs5.getString("WEBSITE")){
												   clientVendorIndex.setInitiatorWebSite(rs5.getString("WEBSITE"));
											   }
									    }
				    	           }*/
						    }
							
	    	           }
						clientVendorList.add(clientVendorIndex);
			    	
			    }
			 //  conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return clientVendorList;
	}
	
	
	private long getNumberOfContactsPerClient(long orgRelationId, Connection conn11) {
		String numberOfContacts = "0";
		 /*DataSource dataSource1 = comboPooledDataSource();
		 Connection conn11 = null;*/
		 Statement stmt11 = null;
		 try {
			  //conn11 = dataSource1.getConnection();
				String sql = "SELECT  count(*) FROM "+dbconfig.getSchema()+".ORG_SPOC where ORG_RELATION_ID = "+orgRelationId;
				stmt11 = conn11.createStatement();
				ResultSet rs = stmt11.executeQuery(sql);
				while(rs.next()) {
					numberOfContacts = rs.getString(1);	
				}
		 }catch (Exception e) {
			 e.printStackTrace();
		}finally {
			try {
				stmt11.close();
				//conn11.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return Long.parseLong(numberOfContacts);
	}

	 
	@Bean
	public DataSource comboPooledDataSource() {
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
			   AddClientVendorELK addClientVendorELK = new AddClientVendorELK();
			   addClientVendorELK.client = new SourceleadELKBaseIndexer<>();
			   addClientVendorELK.addClientVendorELK();
//			   addClientVendorELK.client.shutdown();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
