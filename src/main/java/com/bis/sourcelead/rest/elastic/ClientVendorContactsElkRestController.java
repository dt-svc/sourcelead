package com.bis.sourcelead.rest.elastic;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKRestClient;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@RestController
@RequestMapping("/app")
public class ClientVendorContactsElkRestController {
    private static final Logger logger = LoggerFactory.getLogger(ClientVendorContactsElkRestController.class);
	private ComboPooledDataSource comboPooledDataSource;
	private SourceleadELKRestClient<ClientVendorContactsIndex> client;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	 @RequestMapping(value = "/ws/storeClientAndVendorContactsInElastic/{minLimitSize}/{maxLimitSize}", method = RequestMethod.GET)
	 public void storeCandidatesInELK(@PathVariable Long minLimitSize,@PathVariable Long maxLimitSize) throws Exception {
		 
		    DataSource dataSource = comboPooledDataSource();
			ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
			DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
			client = new SourceleadELKRestClient<>();
			int count = 0;
		 try {
				System.out.println("------------ storeClientAndVendorContactsInElastic starts ----------");
				String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC orgSpock where (orgSpock.ID >= '"+ minLimitSize+"' and orgSpock.ID <= '"+ maxLimitSize+"') ";
				Connection conn = dataSource.getConnection();
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
							 
							 
							 client.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,clientVendorContactsIndex.getId().toString());
							 System.out.println("Saved Count : " + (++count));
			    }
			    conn.close();
			    System.out.println("------------ storeClientAndVendorContactsInElastic ends ----------");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 
	 
	 
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
	 
   public String firstLetterCapitalizedString(String name){
		 String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}
   
   protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
   
   private boolean isNullOrEmpty(long long1) {
		// TODO Auto-generated method stub
		return false;
	}
   
   

}
