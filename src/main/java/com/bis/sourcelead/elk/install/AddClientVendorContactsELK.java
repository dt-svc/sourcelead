package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
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
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;




public class AddClientVendorContactsELK {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AddClientVendorContactsELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	 private SourceleadELKBaseIndexer<ClientVendorContactsIndex> client;
	 ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
		DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	 
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	public void addCampaignELK() throws IOException, SQLException,Exception{
		ArrayList<ClientVendorContactsIndex> elkclientVendorIndexList = getElkCampaigns();
		if(elkclientVendorIndexList != null && !elkclientVendorIndexList.isEmpty()){
			for (ClientVendorContactsIndex clientVendorContactsIndex :elkclientVendorIndexList) {
				
				client.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,clientVendorContactsIndex.getId().toString());
					

			}
		}
		  logger.info("ORG_SPOC Successfully Added To Elk");
	}
	
	private ArrayList<ClientVendorContactsIndex> getElkCampaigns() throws Exception{
		 DataSource dataSource = comboPooledDataSource();
		ArrayList<ClientVendorContactsIndex> contactsList = new ArrayList<ClientVendorContactsIndex>();
		Connection conn = dataSource.getConnection(); 
		try {
				
				String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC orgSpock";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	
			    	ClientVendorContactsIndex clientVendorContactsIndex = new ClientVendorContactsIndex();
                       String createdBy =  rs.getString("CREATED_BY");
			    	   Long  responderLocationId = rs.getLong("CLIENT_LOCATION_ID");
			    	   Long  initiatorLocationId = rs.getLong("VENDOR_LOCATION_ID");
			    	   
			    	   Long  orgRelationId = rs.getLong("ORG_RELATION_ID");
			    	   Long  ownerUserId = rs.getLong("VENDOR_USER_ID");
			    	   Long  spockUserId = rs.getLong("CLIENT_USER_ID");
			    	   
			    	   clientVendorContactsIndex.setId(rs.getLong("ID"));

						clientVendorContactsIndex.setVendorLocationId(rs.getLong("VENDOR_LOCATION_ID"));
						clientVendorContactsIndex.setOrgRelationId(rs.getLong("ORG_RELATION_ID"));
						clientVendorContactsIndex.setClientLocationId(rs.getLong("CLIENT_LOCATION_ID"));
						clientVendorContactsIndex.setInvitationStatus(rs.getString("INVITATION_STATUS"));
						clientVendorContactsIndex.setSpocStatus(rs.getString("SPOC_STATUS"));
						clientVendorContactsIndex.setSpocUserId(rs.getLong("CLIENT_USER_ID"));
						clientVendorContactsIndex.setStatus(rs.getString("STATUS"));
						clientVendorContactsIndex.setOwnerUserId(rs.getLong("OWNER_USER_ID"));
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						clientVendorContactsIndex.setCreatedDate(dateFormat.format(rs.getDate("CREATED_DATE")));
						clientVendorContactsIndex.setUpdatedDate(dateFormat.format(rs.getDate("UPDATED_DATE")));
				    	
						
						// saving responderLocationId and  Org Name
						String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+responderLocationId+"' ";
						
						Statement stmt1 = conn.createStatement();
						ResultSet rs1 = stmt1.executeQuery(sql1);
						if(rs1 != null){
							 while(rs1.next()){
								   Long responderorgId = rs1.getLong("ORG_ID");
								   String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG  org where  org.ID = '"+responderorgId+"' ";
									Statement stmt2 = conn.createStatement();
									ResultSet rs2 = stmt2.executeQuery(sql2);
									if(rs2 != null){
										 while(rs2.next()){
											   if(null != rs2.getString("ORG_NAME")){
												   clientVendorContactsIndex.setClientOrgName(rs2.getString("ORG_NAME"));
												   clientVendorContactsIndex.setOrgName(rs2.getString("ORG_NAME"));

											   }
									    }
				    	           }
						    }
	    	           }
						
						// saving  the Initiator Location id and Org Name
						String sql5 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+initiatorLocationId+"' ";
						Statement stmt5 = conn.createStatement();
						ResultSet rs5 = stmt5.executeQuery(sql5);
						if(rs5 != null){
							 while(rs5.next()){
								   Long initiatororgId = rs5.getLong("ORG_ID");
								   String sql6 = "SELECT * FROM "+dbconfig.getSchema()+".ORG  org where  org.ID = '"+initiatororgId+"' ";
									Statement stmt6 = conn.createStatement();
									ResultSet rs6 = stmt6.executeQuery(sql6);
									if(rs6 != null){
										 while(rs6.next()){
											   if(null != rs6.getString("ORG_NAME")){
												   clientVendorContactsIndex.setVendorOrgName(rs6.getString("ORG_NAME"));
											   }
									    }
				    	           }
						    }
							
			           }
						
					//  Storing the value of OrgRelations InitiatorType and ResponderType
						String sql7= "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS relation where relation.ID = '"+orgRelationId+"' ";
						Statement stmt7 = conn.createStatement();
						ResultSet rs7 = stmt7.executeQuery(sql7);
						if(rs7 != null){
							 while(rs7.next()){
								     clientVendorContactsIndex.setInitiatorType(rs7.getString("INITIATOR_TYPE"));
									 clientVendorContactsIndex.setResponderType(rs7.getString("RESPONDER_TYPE"));
						    }
							
			           }
						

						
						
						
						// Saving  the OwnerUserId in ELk Search
  						String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where user.ID = '"+ownerUserId+"' ";
  						Statement stmt3 = conn.createStatement();
						ResultSet rs3 = stmt3.executeQuery(sql3);
						if(rs3 != null){
							 while(rs3.next()){
								 
									clientVendorContactsIndex.setVendorUserFirstName(rs3.getString("FIRST_NAME"));
									if(null != rs3.getString("LAST_NAME")){
										clientVendorContactsIndex.setVendorUserLastName(rs3.getString("LAST_NAME"));
									}
									if(null != rs3.getString("MOBILE_PHONE")){
										clientVendorContactsIndex.setVendorUserPhNo(rs3.getString("MOBILE_PHONE"));
									}
									
									if(!isNullOrEmpty(rs3.getString("LAST_NAME"))){
										clientVendorContactsIndex.setVendorUserName(rs3.getString("FIRST_NAME")+" "+rs3.getString("LAST_NAME"));
									}else {
										clientVendorContactsIndex.setVendorUserName(rs3.getString("FIRST_NAME"));
									}
									
									clientVendorContactsIndex.setVendorUserId(rs3.getLong("ID"));
									clientVendorContactsIndex.setVendorUserEmail(rs3.getString("EMAIL"));
							        }
						     }
						
						
						// Saving  the spockUserId in ELk Search
  						String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where user.ID = '"+spockUserId+"' ";
  						Statement stmt4 = conn.createStatement();
						ResultSet rs4 = stmt4.executeQuery(sql4);
							 while(rs4.next()){
									clientVendorContactsIndex.setClientUserFirstName(rs4.getString("FIRST_NAME"));
									if(null != rs4.getString("LAST_NAME")){
										clientVendorContactsIndex.setClientUserLastName(rs4.getString("LAST_NAME"));
									}
									
									if(!isNullOrEmpty(rs4.getString("LAST_NAME"))){
										clientVendorContactsIndex.setClientUserName(rs4.getString("FIRST_NAME")+" "+rs4.getString("LAST_NAME"));
									}else {
										clientVendorContactsIndex.setClientUserName(rs4.getString("FIRST_NAME"));
									}
									
									if(null != rs4.getString("MOBILE_PHONE")){
										clientVendorContactsIndex.setClientUserPhNo(rs4.getString("MOBILE_PHONE"));
									}
									clientVendorContactsIndex.setClientUserId(rs4.getLong("ID"));
									clientVendorContactsIndex.setClientUserEmail(rs4.getString("EMAIL"));
							        }
							 
							 
							 contactsList.add(clientVendorContactsIndex);
						     }

			   
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return contactsList;
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
			   AddClientVendorContactsELK addCampaignELK = new AddClientVendorContactsELK();
			   addCampaignELK.client = new SourceleadELKBaseIndexer<>();
			   addCampaignELK.addCampaignELK();
//			   addCampaignELK.client.shutdown();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
