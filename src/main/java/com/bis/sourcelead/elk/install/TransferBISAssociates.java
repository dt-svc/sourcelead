package com.bis.sourcelead.elk.install;

/*import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;*/

public class TransferBISAssociates {
	/*private static final Logger logger = LoggerFactory.getLogger(AddClientVendorELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	private TransportClient client;
	
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	
*//**
 * @author Venkat I
 * this method is for to add US location BIS Associates to IND Location
 * @throws IOException
 * @throws SQLException
 *//*
public void transferAssociates() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		
		 SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
 	     String currentDate = formatter1.format(date);
		
		 Long INDOrgLocation = 5L;
		 Long USOrgLocation = 3L;
		 String shortCode = null;
		
	     System.out.println("----Adding BIS US locatino To IND Locations starts----");
	     
		 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS orgRelations where (orgRelations.LOCATION_ID = '"+INDOrgLocation+"' and  orgRelations.RESPONDER_LOCATION_ID = '"+USOrgLocation+"'  and orgRelations.INITIATOR_TYPE = 'Vendor') "
		 		+ " OR (orgRelations.RESPONDER_LOCATION_ID = '"+INDOrgLocation+"' and orgRelations.LOCATION_ID = '"+USOrgLocation+"'  and orgRelations.RESPONDER_TYPE = 'Client') ";
		 stmt1 = conn.createStatement();
		 ResultSet rs1 = stmt1.executeQuery(sql1);
		 Long orgRelationId = null; 
		 Long spocId = null;
		 String userCode = null;
		 Long userId = null;
		 
		if(rs1.next()) {
			 orgRelationId = rs1.getLong("ID");
			 String spocSql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
    		 Statement spocStmt = conn.createStatement();
			 ResultSet spocRs = spocStmt.executeQuery(spocSql);
			 if(spocRs.next()) {
				 spocId = spocRs.getLong("ID");
			 }
			 
			 String sql14 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgLocations where orgLocations.ID = '"+INDOrgLocation+"' ";
			 Statement stmt14 = conn.createStatement();
			 ResultSet rs14 = stmt14.executeQuery(sql14);
			
			 if(rs14.next()) {
				 shortCode = rs14.getString("GENERATED_USER_ID");
				 userId = rs14.getLong("PRIMARY_CONTACT_USER_ID");
				 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where  user.ID = '"+userId+"' ";
				 Statement stmt16 = conn.createStatement();
				 ResultSet rs15 = stmt16.executeQuery(sql3);
				 if(rs15.next()) {
					 userCode = rs15.getString("USER_CODE");
				 }
				 
			 }
				 
		}else {
			
			 userId = null;
			 Long clientUserId = 289L;
			 
			 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgLocations where orgLocations.ID = '"+INDOrgLocation+"' ";
			 Statement stmt2 = conn.createStatement();
			 ResultSet rs2 = stmt2.executeQuery(sql2);
			
			 
			 if(rs2.next()) {
				 shortCode = rs2.getString("GENERATED_USER_ID");
				 userId = rs2.getLong("PRIMARY_CONTACT_USER_ID");
				 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where  user.ID = '"+userId+"' ";
				 Statement stmt3 = conn.createStatement();
				 ResultSet rs3 = stmt3.executeQuery(sql3);
				 if(rs3.next()) {
					 userCode = rs3.getString("USER_CODE");
				 }
				 
			 }
			 
			 
			 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS orgLocations where orgLocations.ID = '"+USOrgLocation+"' ";
			 Statement stmt4 = conn.createStatement();
			 ResultSet rs3 = stmt4.executeQuery(sql3);
			 if(rs3.next()) {
				 clientUserId = rs3.getLong("PRIMARY_CONTACT_USER_ID");
			 }
			 
			
			String query2 = " insert into "+dbconfig.getSchema()+".ORG_RELATIONS (RESPONDER_LOCATION_ID, LOCATION_ID, INITIATOR_TYPE, RESPONDER_TYPE, INITIATOR_LEVEL,  "
					+ "RESPONDER_LEVEL, UUID, STATUS,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    		
    		try {
    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
	    		 pstmt2.setLong(1, USOrgLocation);
	    		 pstmt2.setLong(2,INDOrgLocation);
	    		 pstmt2.setString(3, "Vendor");
	    		 pstmt2.setString(4, "Client");
	    		 pstmt2.setString(5,"Tier-5");
	    		 pstmt2.setString(6,"Tier-5");
	    		 pstmt2.setString(7,SourceLeadRandomCodeHelper.generateRandomUUID());
	    		 pstmt2.setString(8,"1");
	    		 pstmt2.setString(9,currentDate);
	    		 pstmt2.setString(10,currentDate);
	    		 pstmt2.setString(11,userCode);
	    		 pstmt2.setString(12,userCode);
	    		 pstmt2.execute();	
	    		 
    		}catch (Exception e){   
				e.printStackTrace();
			}
    		
    		ResultSet rs4 = stmt1.executeQuery(sql1);
    		if(rs4.next()) {
    			 orgRelationId = rs4.getLong("ID");
    			
    			String query3 = " insert into "+dbconfig.getSchema()+".ORG_SPOC (ORG_RELATION_ID, CLIENT_USER_ID, VENDOR_USER_ID, INVITATION_STATUS, SPOC_STATUS,  "
    					+ "STATUS, VENDOR_LOCATION_ID, CLIENT_LOCATION_ID,OWNER_USER_ID,UUID,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY)"
    					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		
        		try {
        			 PreparedStatement pstmt3 = conn.prepareStatement(query3);
        			 pstmt3.setLong(1, orgRelationId);
        			 pstmt3.setLong(2,clientUserId);
        			 pstmt3.setLong(3, userId);
        			 pstmt3.setString(4, "Accepted");
        			 pstmt3.setString(5,"Active");
        			 pstmt3.setString(6,"1");
        			 pstmt3.setLong(7,INDOrgLocation);
        			 pstmt3.setLong(8,USOrgLocation);
        			 pstmt3.setLong(9,clientUserId);
        			 pstmt3.setString(10,SourceLeadRandomCodeHelper.generateRandomUUID());
        			 pstmt3.setString(11,currentDate);
        			 pstmt3.setString(12,currentDate);
        			 pstmt3.setString(13,userCode);
        			 pstmt3.setString(14,userCode);
        			 pstmt3.execute();	
    	    		 
        		}catch (Exception e){   
    				e.printStackTrace();
    			}
    		}
    		
    		
    		
    		 String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS orgRelations where orgRelations.ID = '"+orgRelationId+"' ";
    		 Statement stmt5 = conn.createStatement();
			 ResultSet rs5 = stmt5.executeQuery(sql4);
			 
			 if(rs5.next()) {
				 try {
				    	
				    	   ClientVendorIndex clientVendorIndex = new ClientVendorIndex();
	                       String createdBy =  rs5.getString("CREATED_BY");
				    	   Long  responderLocationId = rs5.getLong("RESPONDER_LOCATION_ID");
				    	   Long  initiatorLocationId = rs5.getLong("LOCATION_ID");
				    	   
				    	clientVendorIndex.setId(rs5.getLong("ID"));
				    	clientVendorIndex.setInitiatorLevel(rs5.getString("INITIATOR_LEVEL"));
				    	clientVendorIndex.setResponderLevel(rs5.getString("RESPONDER_LEVEL"));
				    	clientVendorIndex.setResponderLocationId(rs5.getLong("RESPONDER_LOCATION_ID"));
				    	clientVendorIndex.setResponderType(rs5.getString("RESPONDER_TYPE"));
				    	clientVendorIndex.setInitiatorType(rs5.getString("INITIATOR_TYPE"));
				    	clientVendorIndex.setOrgLocationId(rs5.getLong("LOCATION_ID"));
				    	clientVendorIndex.setOrgRelationId(rs5.getLong("ID"));
				    	clientVendorIndex.setStatus(rs5.getString("STATUS"));
				    	clientVendorIndex.setOrgRelationUUID(rs5.getString("UUID"));
				    	
				    	if(null != rs5.getString("VENDOR_ONBOARD_TEMPLATE_ID")){
				    		clientVendorIndex.setVendorOnboardTemplateId(rs5.getString("VENDOR_ONBOARD_TEMPLATE_ID"));
				    	}
				    	if(null != rs5.getString("VENDOR_RELATION_STATUS")){
				    		clientVendorIndex.setVendorRelationStatus(rs5.getString("VENDOR_RELATION_STATUS"));
				    	}
				    	
				    	clientVendorIndex.setNoContacts(getNumberOfContactsPerClient(rs5.getLong("ID"), conn));
				    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				    	
				    	clientVendorIndex.setCreatedDate(dateFormat.format(rs5.getDate("CREATED_DATE")));
				    	if(null != rs5.getDate("UPDATED_DATE")){
				    		clientVendorIndex.setUpdatedDate(dateFormat.format(rs5.getDate("UPDATED_DATE")));
				    	}else{
				    		clientVendorIndex.setUpdatedDate(dateFormat.format(rs5.getDate("CREATED_DATE")));
				    	}
				    	
				    	
				    	String sql5 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where  user.USER_CODE = '"+createdBy+"' ";
				    	 Statement stmtt = conn.createStatement();
							ResultSet rs6 = stmtt.executeQuery(sql5);
							if(rs6 != null){
								 if(rs6.next()){
									  clientVendorIndex.setCreatedBy(createdBy);
									   if(null != rs6.getString("FIRST_NAME") && null != rs6.getString("LAST_NAME")){
										   StringBuilder sb =  new StringBuilder();
										   clientVendorIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).append(" ").append(rs6.getString("LAST_NAME")).toString());
									   }else if(null != rs6.getString("FIRST_NAME")){
										   StringBuilder sb =  new StringBuilder();
										   clientVendorIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).toString());
									   }
							    }
		    	           }
							
							String sql6 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+responderLocationId+"' ";
							Statement stmt6 = conn.createStatement();
							ResultSet rs7 = stmt6.executeQuery(sql6);
							if(rs7 != null){
								 if(rs7.next()){
									   if(null != rs7.getString("COUNTRY")){
										   clientVendorIndex.setResponderCountry(rs7.getString("COUNTRY"));
									   }
									   if(null != rs7.getString("STATE")){
										   clientVendorIndex.setResponderState(rs7.getString("STATE"));
									   }
									   if(null != rs7.getString("CITY")){
										   clientVendorIndex.setResponderCity(rs7.getString("CITY"));
									   }
									   if(null != rs7.getString("ORG_NAME")){
										   clientVendorIndex.setResponderOrgName(rs7.getString("ORG_NAME"));
									   } 
									   if(null != rs7.getString("WEB_SITE")){
										   clientVendorIndex.setResponderWebSite(rs7.getString("WEB_SITE"));
									   }
									   
									   
								
							    }
								
		    	           }
							String sql7 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+initiatorLocationId+"' ";
							Statement stmt7 = conn.createStatement();
							ResultSet rs8 = stmt7.executeQuery(sql7);
							if(rs8 != null){
								 if(rs8.next()){
									   if(null != rs8.getString("COUNTRY")){
										   clientVendorIndex.setInitiatorCountry(rs8.getString("COUNTRY"));
									   }
									   if(null != rs8.getString("STATE")){
										   clientVendorIndex.setInitiatorState(rs8.getString("STATE"));
									   }
									   if(null != rs8.getString("CITY")){
										   clientVendorIndex.setInitiatorCity(rs8.getString("CITY"));
									   }
									   if(null != rs8.getString("ORG_NAME")){
										   clientVendorIndex.setInitiatorOrgName(rs8.getString("ORG_NAME"));
									   } 
									   if(null != rs8.getString("WEB_SITE")){
										   clientVendorIndex.setInitiatorWebSite(rs8.getString("WEB_SITE"));
									   }
							    }
								
		    	           }
							saveOrUpdateElkclientVendorIndexDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,clientVendorIndex.getId().toString());
				    	
							

				    		 String sql8 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
				    		 Statement stmt8 = conn.createStatement();
							 ResultSet rs9 = stmt8.executeQuery(sql8);
							 if(rs9.next()) {
								 
							    	
							    	   ClientVendorContactsIndex clientVendorContactsIndex = new ClientVendorContactsIndex();
							    	   Long  spocresponderLocationId = rs9.getLong("CLIENT_LOCATION_ID");
							    	   Long  spocinitiatorLocationId = rs9.getLong("VENDOR_LOCATION_ID");
							    	   
							    	   Long  spocorgRelationId = rs9.getLong("ORG_RELATION_ID");
							    	   Long  ownerUserId = rs9.getLong("VENDOR_USER_ID");
							    	   Long  spockUserId = rs9.getLong("CLIENT_USER_ID");
							    	   
							    	   clientVendorContactsIndex.setId(rs9.getLong("ID"));

										clientVendorContactsIndex.setVendorLocationId(rs9.getLong("VENDOR_LOCATION_ID"));
										clientVendorContactsIndex.setOrgRelationId(rs9.getLong("ORG_RELATION_ID"));
										clientVendorContactsIndex.setClientLocationId(rs9.getLong("CLIENT_LOCATION_ID"));
										clientVendorContactsIndex.setInvitationStatus(rs9.getString("INVITATION_STATUS"));
										clientVendorContactsIndex.setSpocStatus(rs9.getString("SPOC_STATUS"));
										clientVendorContactsIndex.setSpocUserId(rs9.getLong("CLIENT_USER_ID"));
										clientVendorContactsIndex.setStatus(rs9.getString("STATUS"));
										clientVendorContactsIndex.setOwnerUserId(rs9.getLong("OWNER_USER_ID"));
										
										DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
										clientVendorContactsIndex.setCreatedDate(dateFormat2.format(rs9.getDate("CREATED_DATE")));
										clientVendorContactsIndex.setUpdatedDate(dateFormat2.format(rs9.getDate("UPDATED_DATE")));
								    	
										
										// saving responderLocationId and  Org Name
										String sql9 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+spocresponderLocationId+"' ";
										
										Statement stmt9 = conn.createStatement();
										ResultSet rs10 = stmt9.executeQuery(sql9);
										if(rs10 != null){
											 while(rs10.next()){
												 clientVendorContactsIndex.setClientOrgName(rs10.getString("ORG_NAME"));
												 clientVendorContactsIndex.setOrgName(rs10.getString("ORG_NAME"));
										    }
					    	           }
										
										// saving  the Initiator Location id and Org Name
										String sql10 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+spocinitiatorLocationId+"' ";
										Statement stmt10 = conn.createStatement();
										ResultSet rs11 = stmt10.executeQuery(sql10);
										if(rs11 != null){
											 while(rs11.next()){
												   Long initiatororgId = rs11.getLong("ORG_ID");
												   String sql11 = "SELECT * FROM "+dbconfig.getSchema()+".ORG  org where  org.ID = '"+initiatororgId+"' ";
													Statement stmt11 = conn.createStatement();
													ResultSet rs12 = stmt11.executeQuery(sql11);
													if(rs12 != null){
														 while(rs12.next()){
															   if(null != rs12.getString("ORG_NAME")){
																   clientVendorContactsIndex.setVendorOrgName(rs12.getString("ORG_NAME"));
															   }
													    }
								    	           }
										    }
											
							           }
										
									//  Storing the value of OrgRelations InitiatorType and ResponderType
										String sql11= "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS relation where relation.ID = '"+spocorgRelationId+"' ";
										Statement stmt11 = conn.createStatement();
										ResultSet rs12 = stmt11.executeQuery(sql11);
										if(rs12 != null){
											 while(rs12.next()){
												     clientVendorContactsIndex.setInitiatorType(rs12.getString("INITIATOR_TYPE"));
													 clientVendorContactsIndex.setResponderType(rs12.getString("RESPONDER_TYPE"));
										    }
											
							           }
										

										
										
										
										// Saving  the OwnerUserId in ELk Search
				  						String sql12 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where user.ID = '"+ownerUserId+"' ";
				  						Statement stmt12 = conn.createStatement();
										ResultSet rs13 = stmt12.executeQuery(sql12);
										if(rs13 != null){
											 while(rs13.next()){
												 
													clientVendorContactsIndex.setVendorUserFirstName(rs13.getString("FIRST_NAME"));
													if(null != rs13.getString("LAST_NAME")){
														clientVendorContactsIndex.setVendorUserLastName(rs13.getString("LAST_NAME"));
													}
													if(null != rs13.getString("MOBILE_PHONE")){
														clientVendorContactsIndex.setVendorUserPhNo(rs13.getString("MOBILE_PHONE"));
													}
													
													if(!isNullOrEmpty(rs13.getString("LAST_NAME"))){
														clientVendorContactsIndex.setVendorUserName(rs13.getString("FIRST_NAME")+" "+rs13.getString("LAST_NAME"));
													}else {
														clientVendorContactsIndex.setVendorUserName(rs13.getString("FIRST_NAME"));
													}
													
													clientVendorContactsIndex.setVendorUserId(rs13.getLong("ID"));
													clientVendorContactsIndex.setVendorUserEmail(rs13.getString("EMAIL"));
											        }
										     }
										
										
										// Saving  the spockUserId in ELk Search
				  						String sql13 = "SELECT * FROM "+dbconfig.getSchema()+".USER  user where user.ID = '"+spockUserId+"' ";
				  						Statement stmt13 = conn.createStatement();
										ResultSet rs14 = stmt13.executeQuery(sql13);
											 while(rs14.next()){
													clientVendorContactsIndex.setClientUserFirstName(rs14.getString("FIRST_NAME"));
													if(null != rs14.getString("LAST_NAME")){
														clientVendorContactsIndex.setClientUserLastName(rs14.getString("LAST_NAME"));
													}
													
													if(!isNullOrEmpty(rs14.getString("LAST_NAME"))){
														clientVendorContactsIndex.setClientUserName(rs14.getString("FIRST_NAME")+" "+rs14.getString("LAST_NAME"));
													}else {
														clientVendorContactsIndex.setClientUserName(rs14.getString("FIRST_NAME"));
													}
													
													if(null != rs14.getString("MOBILE_PHONE")){
														clientVendorContactsIndex.setClientUserPhNo(rs14.getString("MOBILE_PHONE"));
													}
													clientVendorContactsIndex.setClientUserId(rs14.getLong("ID"));
													clientVendorContactsIndex.setClientUserEmail(rs14.getString("EMAIL"));
											        }
											 
											 
											 saveOrUpdateElkclientVendorContactsIndexDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,clientVendorContactsIndex.getId().toString());
										     
							 }
							 
				    
					 
				 }catch (Exception e){   
	    				e.printStackTrace();
	    			}
				 
			 }
    		
			
		}
		
		
		String[] emailsList = BisAssociatesEmails.ASSOCIATE_EMAILS;
		if(spocId == null) {
			 String spocSql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
			 Statement spocStmt = conn.createStatement();
			 ResultSet spocRs = spocStmt.executeQuery(spocSql);
			 if(spocRs.next()) {
				 spocId = spocRs.getLong("ID");
			 }
		}
	
		for (String associateEmail : emailsList) {
			
			
			 String existedcandidateSql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+INDOrgLocation+"' ";
			 Statement existedcandidateStmt = conn.createStatement();
			 ResultSet existedcandidateRs = existedcandidateStmt.executeQuery(existedcandidateSql);
			 
			 
			if(!existedcandidateRs.next()) {
				
			 String countcandidateSql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where  candidate.ORG_LOCATION_ID = '"+INDOrgLocation+"' ";
			 Statement countCandidateStmt = conn.createStatement();
			 ResultSet countCandidateRs = countCandidateStmt.executeQuery(countcandidateSql);
			 Integer canndidateCount = 0;
			 
			 while(countCandidateRs.next()) {
				 canndidateCount = canndidateCount+1;
			 }
				
			 String candidateSql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+USOrgLocation+"' ";
			 Statement candidateStmt = conn.createStatement();
			 ResultSet candidateRs = candidateStmt.executeQuery(candidateSql);
			 Long candidateId = null;
			 Long vendorCandidateId = null;
			 
			 if(candidateRs.next()) {
				 
				 Long masterCandidateId = candidateRs.getLong("MASTER_CANDIDATE_ID");
				 Long candidateUserId = candidateRs.getLong("CONSULTANT_USER_ID");
				 String firstName = candidateRs.getString("FIRST_NAME");
				 String lastName = candidateRs.getString("LAST_NAME");
				 String country = candidateRs.getString("COUNTRY");
				 
				 candidateId = candidateRs.getLong("ID");
				 
				 
				 String query3 = " insert into "+dbconfig.getSchema()+".CANDIDATE (MASTER_CANDIDATE_ID, CONSULTANT_USER_ID, FIRST_NAME, LAST_NAME, EMAIL,  "
    					+ "COUNTRY,STATUS,CANDIDATE_UUID,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY,ORG_LOCATION_ID,CANDIDATE_TYPE,DESIGNATION_TYPE,IS_ASSOCIATE,GENERATED_ID)"
    					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		
        		 try {
        			 PreparedStatement candpstmt = conn.prepareStatement(query3);
        			 candpstmt.setLong(1, masterCandidateId);
        			 candpstmt.setLong(2,candidateUserId);
        			 candpstmt.setString(3, firstName);
        			 candpstmt.setString(4, lastName);
        			 candpstmt.setString(5,associateEmail);
        			 candpstmt.setString(6,country);
        			 candpstmt.setString(7,"1");
        			 candpstmt.setString(8,SourceLeadRandomCodeHelper.generateRandomUUID());
        			 candpstmt.setString(9,currentDate);
        			 candpstmt.setString(10,currentDate);
        			 candpstmt.setString(11,userCode);
        			 candpstmt.setString(12,userCode);
        			 candpstmt.setLong(13, INDOrgLocation);
        			 candpstmt.setString(14, "Candidate");
        			 candpstmt.setString(15, "Full Time");
        			 candpstmt.setString(16, "Y");
        			 candpstmt.setString(17, shortCode+canndidateCount);
        			 candpstmt.execute();	
    	    		 
        		}catch (Exception e){   
    				e.printStackTrace();
    			}
        		 
        		 
        		 
        		 
				 String query4 = " insert into "+dbconfig.getSchema()+".USER_ROLE (USER_ID, REPORTING_MANAGER_ID, ROLE_ID, ORG_LOCATIONS_ID, IS_PRIMARY,  "
    					+ "USER_STATE,USER_TYPE,IS_MAIL_SENT,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY)"
    					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		
        		 try {
        			 PreparedStatement rolepstmt = conn.prepareStatement(query4);
        			 rolepstmt.setLong(1, candidateUserId);
        			 rolepstmt.setLong(2,userId);
        			 rolepstmt.setLong(3, 11);
        			 rolepstmt.setLong(4, INDOrgLocation);
        			 rolepstmt.setString(5,"0");
        			 rolepstmt.setString(6,"NOT_LOGGEDIN");
        			 rolepstmt.setString(7,"Employee");
        			 rolepstmt.setString(8,"Y");
        			 rolepstmt.setString(9,currentDate);
        			 rolepstmt.setString(10,currentDate);
        			 rolepstmt.setString(11,userCode);
        			 rolepstmt.setString(12,userCode);
        			 rolepstmt.execute();	
    	    		 
        		}catch (Exception e){   
    				e.printStackTrace();
    			}
				 
        		 
    			 String query5 = " insert into "+dbconfig.getSchema()+".EMPLOYEE (USER_ID, ORG_LOCATIONS_ID, "
     					+ "CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY)"
     					+ " values (?, ?, ?, ?, ?, ?)";
         		
         		 try {
         			 PreparedStatement emppstmt = conn.prepareStatement(query5);
	         			emppstmt.setLong(1, userId);
	         			emppstmt.setLong(2,INDOrgLocation);
	         			emppstmt.setString(3,currentDate);
	         			emppstmt.setString(4,currentDate);
	         			emppstmt.setString(5,userCode);
	         			emppstmt.setString(6,userCode);
	         			emppstmt.execute();	
         		}catch (Exception e){   
     				e.printStackTrace();
     			}
				 
			  }
			 
			 
			 try {
				 String vendorcandidateSql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+INDOrgLocation+"' ";
				 Statement vendorCandidateStmt = conn.createStatement();
				 ResultSet vendorCandidateRs = vendorCandidateStmt.executeQuery(vendorcandidateSql);
				 
				 if(vendorCandidateRs.next()) {
					 
				    	CandidateIndex candidateIndex = new CandidateIndex();
				    	candidateIndex.setCandidateId(vendorCandidateRs.getLong("ID"));
				    	candidateIndex.setOrgLocationId(vendorCandidateRs.getLong("ORG_LOCATION_ID"));
						candidateIndex.setFirstName(vendorCandidateRs.getString("FIRST_NAME"));
						candidateIndex.setLastName(vendorCandidateRs.getString("LAST_NAME"));
						
						if(!isNullOrEmpty(vendorCandidateRs.getString("LAST_NAME"))){
							candidateIndex.setFullName(vendorCandidateRs.getString("FIRST_NAME")+" "+vendorCandidateRs.getString("LAST_NAME"));
						}else {
							candidateIndex.setFullName(vendorCandidateRs.getString("FIRST_NAME"));
						}
						
						candidateIndex.setEmail(vendorCandidateRs.getString("EMAIL"));
						candidateIndex.setAlternateEmail(vendorCandidateRs.getString("ALTERNATE_EMAIL"));
						candidateIndex.setMobilePhone(vendorCandidateRs.getString("MOBILE_PHONE"));
						candidateIndex.setAlternateMobilePhone(vendorCandidateRs.getString("ALTERNATE_MOBILE_PHONE"));
						candidateIndex.setCountry(vendorCandidateRs.getString("COUNTRY"));
						candidateIndex.setState(vendorCandidateRs.getString("STATE"));
						candidateIndex.setCity(vendorCandidateRs.getString("CITY"));
						candidateIndex.setAddress(vendorCandidateRs.getString("ADDRESS"));
						candidateIndex.setSummary(vendorCandidateRs.getString("SUMMARY"));
						candidateIndex.setCandidateType(vendorCandidateRs.getString("CANDIDATE_TYPE"));
						candidateIndex.setCandidateUUID(vendorCandidateRs.getString("CANDIDATE_UUID"));
						candidateIndex.setLocation(vendorCandidateRs.getString("LOCATION"));
						candidateIndex.setZipCode(vendorCandidateRs.getString("ZIPCODE"));
						candidateIndex.setLatitude(vendorCandidateRs.getString("LATITUDE"));
						candidateIndex.setLongitude(vendorCandidateRs.getString("LONGITUDE"));
						
						candidateIndex.setGeneratedId(vendorCandidateRs.getString("GENERATED_ID"));
						candidateIndex.setMasterCandidateId(vendorCandidateRs.getLong("MASTER_CANDIDATE_ID"));
						candidateIndex.setUserId(vendorCandidateRs.getLong("CONSULTANT_USER_ID"));
						candidateIndex.setRecruiterUserId(0L);
						candidateIndex.setTeamId(0L);
						candidateIndex.setIsFavourite(0);
						candidateIndex.setIsFavouriteAssociate(0);
						candidateIndex.setIsAssociate(Constants.YES);
						
						 candidateIndex.setOrgRelationId(vendorCandidateRs.getLong("ORG_RELATION_ID"));
						 candidateIndex.setSpocId(vendorCandidateRs.getLong("SPOC_ID"));
						
						if(!isNullOrEmpty(vendorCandidateRs.getString("DESIGNATION"))) {
							candidateIndex.setDesignation(vendorCandidateRs.getString("DESIGNATION"));
						}
						
						if(!isNullOrEmpty(vendorCandidateRs.getString("DESIGNATION_TYPE"))) {
							candidateIndex.setDesignationType(vendorCandidateRs.getString("DESIGNATION_TYPE"));
						}
						
						candidateIndex.setStatus(vendorCandidateRs.getInt("STATUS"));
						 	
						Timestamp createdDateTimestamp = vendorCandidateRs.getTimestamp("CREATED_DATE");
					    if (createdDateTimestamp != null){
					    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							candidateIndex.setCreatedDate(df.format(new java.util.Date(createdDateTimestamp.getTime())));
					    }
					    candidateIndex.setCreatedBy(vendorCandidateRs.getString("CREATED_BY"));   
						Timestamp updatedDateTimestamp = vendorCandidateRs.getTimestamp("UPDATED_DATE");
						if (updatedDateTimestamp != null){
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							candidateIndex.setUpdatedDate(df.format(new java.util.Date(updatedDateTimestamp.getTime())));
					    }
						
						if(StringUtils.isNotEmpty(vendorCandidateRs.getString("IS_PROFILE_PUBLIC"))){
							candidateIndex.setIsProfilePublic(vendorCandidateRs.getString("IS_PROFILE_PUBLIC"));
						}
						
						
						candidateIndex.setUpdatedBy(vendorCandidateRs.getString("UPDATED_BY"));
						
						saveOrUpdateCandidateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateIndex.getCandidateId().toString());
					   
					    vendorCandidateId = vendorCandidateRs.getLong("ID");
					
				 }
				 
				 
				 try {
					 String designationType = "Offshore C2C";
					 String clientCandidateQry = " update "+dbconfig.getSchema()+".CANDIDATE candidate set candidate.PARENT_CANDIDATE_ID = '"+vendorCandidateId+"', candidate.DESIGNATION_TYPE = '"+designationType+"', candidate.ORG_RELATION_ID = '"+orgRelationId+"', candidate.SPOC_ID = '"+spocId+"' where candidate.ID = '"+candidateId+"' ";
			    	 java.sql.PreparedStatement clientCandidatePstmt = conn.prepareStatement(clientCandidateQry); 
			    	 clientCandidatePstmt.executeUpdate(); 
			    	 
			    	 String clientcandidateSql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+USOrgLocation+"' ";
					 Statement clientCandidateStmt = conn.createStatement();
					 ResultSet clientCandidateRs = clientCandidateStmt.executeQuery(clientcandidateSql);
					 
					 if(clientCandidateRs.next()) {

					    	String associateUserId = clientCandidateRs.getString("CONSULTANT_USER_ID"); 
						 
						    CandidateIndex candidateIndex = new CandidateIndex();
					    	
					    	candidateIndex.setCandidateId(clientCandidateRs.getLong("ID"));
					    	candidateIndex.setOrgLocationId(clientCandidateRs.getLong("ORG_LOCATION_ID"));
							candidateIndex.setFirstName(clientCandidateRs.getString("FIRST_NAME"));
							candidateIndex.setLastName(clientCandidateRs.getString("LAST_NAME"));
							
							if(!isNullOrEmpty(clientCandidateRs.getString("LAST_NAME"))){
								candidateIndex.setFullName(clientCandidateRs.getString("FIRST_NAME")+" "+clientCandidateRs.getString("LAST_NAME"));
							}else {
								candidateIndex.setFullName(clientCandidateRs.getString("FIRST_NAME"));
							}
							
							candidateIndex.setEmail(clientCandidateRs.getString("EMAIL"));
							candidateIndex.setAlternateEmail(clientCandidateRs.getString("ALTERNATE_EMAIL"));
							candidateIndex.setMobilePhone(clientCandidateRs.getString("MOBILE_PHONE"));
							candidateIndex.setAlternateMobilePhone(clientCandidateRs.getString("ALTERNATE_MOBILE_PHONE"));
							candidateIndex.setCountry(clientCandidateRs.getString("COUNTRY"));
							candidateIndex.setState(clientCandidateRs.getString("STATE"));
							candidateIndex.setCity(clientCandidateRs.getString("CITY"));
							candidateIndex.setAddress(clientCandidateRs.getString("ADDRESS"));
							candidateIndex.setSummary(clientCandidateRs.getString("SUMMARY"));
							candidateIndex.setCandidateType(clientCandidateRs.getString("CANDIDATE_TYPE"));
							candidateIndex.setCandidateUUID(clientCandidateRs.getString("CANDIDATE_UUID"));
							candidateIndex.setLocation(clientCandidateRs.getString("LOCATION"));
							candidateIndex.setZipCode(clientCandidateRs.getString("ZIPCODE"));
							candidateIndex.setLatitude(clientCandidateRs.getString("LATITUDE"));
							candidateIndex.setLongitude(clientCandidateRs.getString("LONGITUDE"));
							
							candidateIndex.setGeneratedId(clientCandidateRs.getString("GENERATED_ID"));
							
							if(!isNullOrEmpty(clientCandidateRs.getLong("MASTER_CANDIDATE_ID"))){
								candidateIndex.setMasterCandidateId(clientCandidateRs.getLong("MASTER_CANDIDATE_ID"));
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getLong("CONSULTANT_USER_ID"))){
								candidateIndex.setUserId(clientCandidateRs.getLong("CONSULTANT_USER_ID"));
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getLong("RECRUITER_USER_ID"))){
								candidateIndex.setRecruiterUserId(clientCandidateRs.getLong("RECRUITER_USER_ID"));
							}else{
								candidateIndex.setRecruiterUserId(0L);
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getString("TEAM_ID"))){
								candidateIndex.setTeamId(clientCandidateRs.getLong("TEAM_ID"));
							}else{
								candidateIndex.setTeamId(0L);
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getString("IS_FAVOURITE"))){
							candidateIndex.setIsFavourite(clientCandidateRs.getInt("IS_FAVOURITE"));
							}else{
								candidateIndex.setIsFavourite(0);
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getString("IS_FAVOURITE_ASSOCIATE"))){
							    candidateIndex.setIsFavouriteAssociate(clientCandidateRs.getInt("IS_FAVOURITE_ASSOCIATE"));
							}else{
								candidateIndex.setIsFavouriteAssociate(0);
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getString("IS_ASSOCIATE"))){
							    candidateIndex.setIsAssociate(clientCandidateRs.getString("IS_ASSOCIATE"));
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getLong("ORG_RELATION_ID"))){
							    candidateIndex.setOrgRelationId(clientCandidateRs.getLong("ORG_RELATION_ID"));
							}
							
							if(!isNullOrEmpty(clientCandidateRs.getLong("SPOC_ID"))){
							    candidateIndex.setSpocId(clientCandidateRs.getLong("SPOC_ID"));
							}
							
							
							if(StringUtils.isNotEmpty(clientCandidateRs.getString("EXPERIENCE_YEARS"))){
								if(clientCandidateRs.getString("EXPERIENCE_YEARS").equalsIgnoreCase("25+")){
									candidateIndex.setExperienceYears(Constants.EXPERIENCE_26YEARS);
								}
								else{
									candidateIndex.setExperienceYears(Integer.parseInt(clientCandidateRs.getString("EXPERIENCE_YEARS")));
								}
							}
							
							if(StringUtils.isNotEmpty(clientCandidateRs.getString("EXPERIENCE_MONTHS"))){
								candidateIndex.setExperienceMonths(Integer.parseInt(clientCandidateRs.getString("EXPERIENCE_MONTHS")));
							}
							candidateIndex.setDesignation(clientCandidateRs.getString("DESIGNATION"));
							candidateIndex.setDesignationType(clientCandidateRs.getString("DESIGNATION_TYPE"));
							candidateIndex.setNoticePriod(clientCandidateRs.getString("NOTICE_PERIOD"));
							candidateIndex.setCurrentRateType(clientCandidateRs.getString("CURRENT_RATE_TYPE"));
							candidateIndex.setCurrentRateCurrency(clientCandidateRs.getString("CURRENT_RATE_CURRENCY"));
							
							if(StringUtils.isNotEmpty(clientCandidateRs.getString("CURRENT_RATE"))){
								candidateIndex.setCurrentRate(clientCandidateRs.getString("CURRENT_RATE"));
							}
							
							if(NumberUtils.isParsable(clientCandidateRs.getString("EXPECTED_RATE"))){
								candidateIndex.setExpectedRate(Float.parseFloat(clientCandidateRs.getString("EXPECTED_RATE")));
							}
							candidateIndex.setRateType(clientCandidateRs.getString("RATE_TYPE"));
							candidateIndex.setExpectedRateCurrencey(clientCandidateRs.getString("EXPECTED_RATE_CURRENCY"));
							candidateIndex.setInvitationstatus(clientCandidateRs.getString("INVITATION_STATUS"));
							candidateIndex.setStatus(clientCandidateRs.getInt("STATUS"));
							candidateIndex.setTerminateReason(clientCandidateRs.getString("TERMINATE_REASON"));
							 	
							candidateIndex.setSkillsName(clientCandidateRs.getString("SKILLS_NAME"));
							if(clientCandidateRs.getString("VISA_TYPE") !=null){
								candidateIndex.setVisaType(clientCandidateRs.getString("VISA_TYPE").split(","));
							}

							Timestamp createdDateTimestamp = clientCandidateRs.getTimestamp("CREATED_DATE");
						    if (createdDateTimestamp != null){
						    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								candidateIndex.setCreatedDate(df.format(new java.util.Date(createdDateTimestamp.getTime())));
						    }
						    candidateIndex.setCreatedBy(clientCandidateRs.getString("CREATED_BY"));   
							Timestamp updatedDateTimestamp = clientCandidateRs.getTimestamp("UPDATED_DATE");
							if (updatedDateTimestamp != null){
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								candidateIndex.setUpdatedDate(df.format(new java.util.Date(updatedDateTimestamp.getTime())));
						    }
							
							if(StringUtils.isNotEmpty(clientCandidateRs.getString("IS_PROFILE_PUBLIC"))){
								candidateIndex.setIsProfilePublic(clientCandidateRs.getString("IS_PROFILE_PUBLIC"));
							}
							
							
							candidateIndex.setUpdatedBy(clientCandidateRs.getString("UPDATED_BY"));
						    
						    Long clientcandidateId = clientCandidateRs.getLong("ID");
				    	    if(clientcandidateId != null && clientcandidateId != 0){
									
								    String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE_EMPLOYERS candidateEmployers where candidateEmployers.CANDIDATE_ID = '"+ clientcandidateId+"' ";
									Statement stmt2 = conn.createStatement();
									ResultSet rs2 = stmt2.executeQuery(sql2);
									if(rs2 != null){
										ArrayList<String> employerList = new ArrayList<>();
										 while(rs2.next()){
											 employerList.add(rs2.getString("NAME"));
									    }
									    if(null != employerList && !employerList.isEmpty()){
											candidateIndex.setEmployer(employerList);
										}
				    	           }
									
								   String sql3= "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE_EDUCATION candidateEducation where candidateEducation.CANDIDATE_ID = '"+ clientcandidateId+"' ";
								   Statement stmt3 = conn.createStatement();
									ResultSet rs3 = stmt3.executeQuery(sql3);
									if(rs3 != null){
										ArrayList<String> educationList = new ArrayList<>();
										 while(rs3.next()){
											 educationList.add(rs3.getString("NAME"));
									    }
									    if(null != educationList && !educationList.isEmpty()){
											candidateIndex.setEducation(educationList);
										}
				    	           }
									
								   String sql5= "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE_CERTIFICATIONS candidateCertifications where candidateCertifications.CANDIDATE_ID = '"+ clientcandidateId+"' ";
								   Statement stmt5 = conn.createStatement();
									ResultSet rs5 = stmt5.executeQuery(sql5);
									if(rs5 != null){
										ArrayList<String> certList = new ArrayList<>();
										 while(rs5.next()){
											 certList.add(rs5.getString("NAME"));
									    }
									    if(null != certList && !certList.isEmpty()){
											candidateIndex.setCertifications(certList);
										}
				    	           }
										
								   String sql4= "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE_RESUMES candidateResumes where candidateResumes.CANDIDATE_ID = '"+ clientcandidateId+"'  and candidateResumes.CREATED_DATE IN (SELECT max(canresume.CREATED_DATE) FROM "+dbconfig.getSchema()+".CANDIDATE_RESUMES canresume where canresume.CANDIDATE_ID = '"+ candidateId+"' and canresume.status = '1') and candidateResumes.status = '1' ";
								   Statement stmt4 = conn.createStatement();
									ResultSet rs4 = stmt4.executeQuery(sql4);
									if(rs4 != null){
										 while(rs4.next()){
											 candidateIndex.setCurrentResumeId(rs4.getLong("ID"));
											 candidateIndex.setCurrentResumeName(rs4.getString("FILE_NAME"));
											 candidateIndex.setResumeContent("");
											 candidateIndex.setFileUrl(rs4.getString("FILE_URL"));
											 candidateIndex.setFileContentType(rs4.getString("FILE_CONTENT_TYPE"));
											 candidateIndex.setResumeName(rs4.getString("FILE_NAME"));
									    }
				    	           }
				    	    }
				    	    
				    	    deleteElkDocument(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE, candidateIndex.getCandidateId().toString());
				    	    saveOrUpdateCandidateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateIndex.getCandidateId().toString());
				    	    
				    	    
				    	     String employeeSql = "SELECT * FROM "+dbconfig.getSchema()+".EMPLOYEE employee  where employee.USER_ID = '"+associateUserId+"' and  employee.ORG_LOCATIONS_ID = '"+USOrgLocation+"' ";
							 Statement employeeStmt = conn.createStatement();
							 ResultSet employeeRs = employeeStmt.executeQuery(employeeSql);
							 
							 
				    	    if(employeeRs.next()) {
				    	    	
				    	    	 String empId = employeeRs.getString("ID");
				    	    	
				    	    	 String clientemplQry = " update "+dbconfig.getSchema()+".EMPLOYEE employee set employee.EMPLOYMENT_TYPE = '"+designationType+"', employee.ORG_RELATION_ID = '"+orgRelationId+"', employee.SPOC_ID = '"+spocId+"' where employee.ID = '"+empId+"' ";
						    	 java.sql.PreparedStatement clientemplPstmt = conn.prepareStatement(clientemplQry); 
						    	 clientemplPstmt.executeUpdate();
				    	    }
				    	     
					    	 
						 
					 }
			    	 
			    	 
				 }catch (Exception e) {
					 e.printStackTrace();
				}
				 
			 }catch (Exception e) {
				 e.printStackTrace();
			  }
			 
		}
			 
		}
			
		
			
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	System.out.println("----Adding BIS US locatino To IND Locations ends----");
}


public void deleteElkDocument(String index, String type, String id) {
	try {
		client.prepareDelete(index, type, id).execute().actionGet();
		// sourceleadELKTransportClient.shutdown();
		logger.info(index + " Document With id :" + id + " has been deleted.");
	} catch (Exception e) {
		logger.error(index + " Document With id :" + id + " deletion fails " + ExceptionUtils.getFullStackTrace(e));
	}

}


public void saveOrUpdateCandidateElkDocument(CandidateIndex candidateIndex,String index,String type,String id) {
	String value = "";
	try {
		value = toJSON(candidateIndex);
	    client.prepareUpdate(index, type,id).setDoc(value, XContentType.JSON).get();
	    logger.info(type+" has been updated.");
	} 
    catch(DocumentMissingException e){
    	client.prepareIndex(index, type,id)
	    .setSource(value, XContentType.JSON).execute().actionGet();
	    logger.info(type+" has been indexed.");
	}
	catch (Exception e) {
		logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
	}
	
 }


private boolean isNullOrEmpty(long long1) {
	// TODO Auto-generated method stub
	return false;
}

public void saveOrUpdateElkclientVendorIndexDocument(ClientVendorIndex clientVendorIndex,String index,String type,String id) {
	String value = "";
	try {
		value = toJSON(clientVendorIndex);
	    client.prepareUpdate(index, type,id).setDoc(value, XContentType.JSON).get();
	    logger.info(type+" has been updated.");
	} 
    catch(DocumentMissingException e){
    	client.prepareIndex(index, type,id)
	    .setSource(value, XContentType.JSON).execute().actionGet();
	    logger.info(type+" has been indexed.");
	}
	catch (Exception e) {
		logger.error(type+" could not be serialized. "+e.getLocalizedMessage());
	}
	
 }

public void saveOrUpdateElkclientVendorContactsIndexDocument(ClientVendorContactsIndex contactsIndex,String index,String type,String id) {
	String value = "";
	try {
		value = toJSON(contactsIndex);
	    client.prepareUpdate(index, type,id).setDoc(value, XContentType.JSON).get();
	    logger.info(type+" has been updated.");
	} 
    catch(DocumentMissingException e){
    	client.prepareIndex(index, type,id)
	    .setSource(value, XContentType.JSON).execute().actionGet();
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


private long getNumberOfContactsPerClient(long orgRelationId, Connection conn11) {
	String numberOfContacts = "0";
	 DataSource dataSource1 = comboPooledDataSource();
	 Connection conn11 = null;
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

*//**
 * getting connection
 * @return DataSource
 *//* 
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
	
    try {
   	 	try {
	    	  Settings settings = Settings.builder()
	    			  .put("cluster.name", dbconfig.getCluster())
	                  .put("node.name", dbconfig.getNode())
	         		  .put("path.home", dbconfig.getPath())
	                  .build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(dbconfig.getHost()), 9300));
	     } catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		
    } catch (Exception exception) {
		logger.error(ExceptionUtils.getFullStackTrace(exception));
	}
	
	return comboPooledDataSource;
}
 
public static void main(String[] args) throws IOException, SQLException {
	TransferBISAssociates dump = new TransferBISAssociates();
	dump.transferAssociates();
}*/

}
