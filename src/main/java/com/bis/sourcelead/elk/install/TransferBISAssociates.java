package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bis.sourcelead.beans.BisAssociatesEmails;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class TransferBISAssociates {
	private static final Logger logger = LoggerFactory.getLogger(AddClientVendorELK.class);
	private ComboPooledDataSource comboPooledDataSource;
	private TransportClient client;
	
	 protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	
/**
 * @author Venkat I
 * this method is for to add US location BIS Associates to IND Location
 * @throws IOException
 * @throws SQLException
 */
public void transferAssociates() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		
		 SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
 	     String currentDate = formatter1.format(date);
		
		Long INDOrgLocation = 2286L;
		Long USOrgLocation = 2301L;
		
	      
		 String sql1 = "SELECT * FROM sourcelead.ORG_RELATIONS orgRelations where (orgRelations.LOCATION_ID = '"+INDOrgLocation+"' and  orgRelations.RESPONDER_LOCATION_ID = '"+USOrgLocation+"'  and orgRelations.INITIATOR_TYPE = 'Vendor') "
		 		+ " OR (orgRelations.RESPONDER_LOCATION_ID = '"+INDOrgLocation+"' and orgRelations.LOCATION_ID = '"+USOrgLocation+"'  and orgRelations.RESPONDER_TYPE = 'Client') ";
		 stmt1 = conn.createStatement();
		 ResultSet rs1 = stmt1.executeQuery(sql1);
		 Long orgRelationId = null; 
		 Long spocId = null;
		 String userCode = null;
		 
		if(rs1.next()) {
			 orgRelationId = rs1.getLong("ID");
			 String spocSql = "SELECT * FROM sourcelead.ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
    		 Statement spocStmt = conn.createStatement();
			 ResultSet spocRs = spocStmt.executeQuery(spocSql);
			 if(spocRs.next()) {
				 spocId = spocRs.getLong("ID");
			 }
			 
			 String sql14 = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.ID = '"+INDOrgLocation+"' ";
			 Statement stmt14 = conn.createStatement();
			 ResultSet rs14 = stmt14.executeQuery(sql14);
			
			 if(rs14.next()) {
				Long userId = rs14.getLong("PRIMARY_CONTACT_USER_ID");
				 String sql3 = "SELECT * FROM sourcelead.USER  user where  user.ID = '"+userId+"' ";
				 Statement stmt16 = conn.createStatement();
				 ResultSet rs15 = stmt16.executeQuery(sql3);
				 if(rs15.next()) {
					 userCode = rs15.getString("USER_CODE");
				 }
				 
			 }
				 
		}else {
			
			 Long userId = null;
			 Long clientUserId = null;
			 
			 String sql2 = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.ID = '"+INDOrgLocation+"' ";
			 Statement stmt2 = conn.createStatement();
			 ResultSet rs2 = stmt2.executeQuery(sql2);
			
			 
			 if(rs2.next()) {
				 userId = rs2.getLong("PRIMARY_CONTACT_USER_ID");
				 String sql3 = "SELECT * FROM sourcelead.USER  user where  user.ID = '"+userId+"' ";
				 Statement stmt3 = conn.createStatement();
				 ResultSet rs3 = stmt3.executeQuery(sql3);
				 if(rs3.next()) {
					 userCode = rs3.getString("USER_CODE");
				 }
				 
			 }
			 
			 
			 String sql3 = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.ID = '"+USOrgLocation+"' ";
			 Statement stmt4 = conn.createStatement();
			 ResultSet rs3 = stmt4.executeQuery(sql3);
			 if(rs3.next()) {
				 clientUserId = rs3.getLong("PRIMARY_CONTACT_USER_ID");
			 }
			 
			
			String query2 = " insert into sourcelead.ORG_RELATIONS (RESPONDER_LOCATION_ID, LOCATION_ID, INITIATOR_TYPE, RESPONDER_TYPE, INITIATOR_LEVEL,  "
					+ "RESPONDER_LEVEL, UUID, STATUS,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    		
    		try {
    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
	    		 pstmt2.setLong(1, USOrgLocation);
	    		 pstmt2.setLong(2,2286L);
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
    			
    			String query3 = " insert into sourcelead.ORG_SPOC (ORG_RELATION_ID, CLIENT_USER_ID, VENDOR_USER_ID, INVITATION_STATUS, SPOC_STATUS,  "
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
        			 pstmt3.setLong(7,2286);
        			 pstmt3.setLong(8,2301);
        			 pstmt3.setLong(9,3839);
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
    		
    		
    		
    		 String sql4 = "SELECT * FROM sourcelead.ORG_RELATIONS orgRelations where orgRelations.ID = '"+orgRelationId+"' ";
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
				    	
				    	
				    	String sql5 = "SELECT * FROM sourcelead.USER  user where  user.USER_CODE = '"+createdBy+"' ";
				    	 Statement stmtt = conn.createStatement();
							ResultSet rs6 = stmtt.executeQuery(sql5);
							if(rs6 != null){
								 if(rs6.next()){
									  clientVendorIndex.setCreatedBy(createdBy);
									   if(null != rs6.getString("FIRST_NAME") && null != rs6.getString("LAST_NAME")){
										   StringBuilder sb =  new StringBuilder();
										   clientVendorIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).append("").append(rs6.getString("LAST_NAME")).toString());
									   }else if(null != rs6.getString("FIRST_NAME")){
										   StringBuilder sb =  new StringBuilder();
										   clientVendorIndex.setCreatedBy(sb.append(rs6.getString("FIRST_NAME")).toString());
									   }
							    }
		    	           }
							
							String sql6 = "SELECT * FROM sourcelead.ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+responderLocationId+"' ";
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
							String sql7 = "SELECT * FROM sourcelead.ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+initiatorLocationId+"' ";
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
				    	
							

				    		 String sql8 = "SELECT * FROM sourcelead.ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
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
										String sql9 = "SELECT * FROM sourcelead.ORG_LOCATIONS  responderLocationId where responderLocationId.ID = '"+spocresponderLocationId+"' ";
										
										Statement stmt9 = conn.createStatement();
										ResultSet rs10 = stmt9.executeQuery(sql9);
										if(rs10 != null){
											 while(rs10.next()){
												   Long responderorgId = rs10.getLong("ORG_ID");
												   String sql10 = "SELECT * FROM sourcelead.ORG  org where  org.ID = '"+responderorgId+"' ";
													Statement stmt10 = conn.createStatement();
													ResultSet rs11 = stmt10.executeQuery(sql10);
													if(rs11 != null){
														 while(rs11.next()){
															   if(null != rs11.getString("ORG_NAME")){
																   clientVendorContactsIndex.setClientOrgName(rs11.getString("ORG_NAME"));
																   clientVendorContactsIndex.setOrgName(rs11.getString("ORG_NAME"));

															   }
													    }
								    	           }
										    }
					    	           }
										
										// saving  the Initiator Location id and Org Name
										String sql10 = "SELECT * FROM sourcelead.ORG_LOCATIONS  initiatorLocationId where initiatorLocationId.ID = '"+spocinitiatorLocationId+"' ";
										Statement stmt10 = conn.createStatement();
										ResultSet rs11 = stmt10.executeQuery(sql10);
										if(rs11 != null){
											 while(rs11.next()){
												   Long initiatororgId = rs11.getLong("ORG_ID");
												   String sql11 = "SELECT * FROM sourcelead.ORG  org where  org.ID = '"+initiatororgId+"' ";
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
										String sql11= "SELECT * FROM sourcelead.ORG_RELATIONS relation where relation.ID = '"+spocorgRelationId+"' ";
										Statement stmt11 = conn.createStatement();
										ResultSet rs12 = stmt11.executeQuery(sql11);
										if(rs12 != null){
											 while(rs12.next()){
												     clientVendorContactsIndex.setInitiatorType(rs12.getString("INITIATOR_TYPE"));
													 clientVendorContactsIndex.setResponderType(rs12.getString("RESPONDER_TYPE"));
										    }
											
							           }
										

										
										
										
										// Saving  the OwnerUserId in ELk Search
				  						String sql12 = "SELECT * FROM sourcelead.USER  user where user.ID = '"+ownerUserId+"' ";
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
				  						String sql13 = "SELECT * FROM sourcelead.USER  user where user.ID = '"+spockUserId+"' ";
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
			 String spocSql = "SELECT * FROM sourcelead.ORG_SPOC orgSpoc where orgSpoc.ORG_RELATION_ID = '"+orgRelationId+"' ";
			 Statement spocStmt = conn.createStatement();
			 ResultSet spocRs = spocStmt.executeQuery(spocSql);
			 if(spocRs.next()) {
				 spocId = spocRs.getLong("ID");
			 }
		}
	
		for (String associateEmail : emailsList) {
			
			
			 String countcandidateSql = "SELECT count(*) FROM sourcelead.CANDIDATE candidate where  candidate.ORG_LOCATION_ID = '"+INDOrgLocation+"' ";
			 Statement countCandidateStmt = conn.createStatement();
			 ResultSet countCandidateRs = countCandidateStmt.executeQuery(countcandidateSql);
			if(countCandidateRs.next()) {
				System.out.println(countCandidateRs.getRow());
			}
			 
			
			 String candidateSql = "SELECT * FROM sourcelead.CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+USOrgLocation+"' ";
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
				 
				 
				 String query3 = " insert into sourcelead.CANDIDATE (MASTER_CANDIDATE_ID, CONSULTANT_USER_ID, FIRST_NAME, LAST_NAME, EMAIL,  "
    					+ "COUNTRY,STATUS,CANDIDATE_UUID,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY,ORG_LOCATION_ID,CANDIDATE_TYPE,DESIGNATION_TYPE)"
    					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        		
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
        			candpstmt.execute();	
    	    		 
        		}catch (Exception e){   
    				e.printStackTrace();
    			}
				 
				 
			 }
			 
			 

			 String vendorcandidateSql = "SELECT * FROM sourcelead.CANDIDATE candidate where candidate.EMAIL = '"+associateEmail+"' and  candidate.ORG_LOCATION_ID = '"+INDOrgLocation+"' ";
			 Statement vendorCandidateStmt = conn.createStatement();
			 ResultSet vendorCandidateRs = vendorCandidateStmt.executeQuery(vendorcandidateSql);
			 
			 if(vendorCandidateRs.next()) {
				 vendorCandidateId = vendorCandidateRs.getLong("ID");
			 }
			 
			 String clientCandidateQry = " update sourcelead.CANDIDATE set PARENT_CANDIDATE_ID = '"+vendorCandidateId+"' and ORG_RELATION_ID = '"+orgRelationId+"' and SPOC_ID = '"+spocId+"' where ID = '"+candidateId+"' ";
	    	 java.sql.PreparedStatement clientCandidatePstmt = conn.prepareStatement(clientCandidateQry); 
	    	 clientCandidatePstmt.executeUpdate(); 
			 
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
}


public void saveOrUpdateElkclientVendorIndexDocument(ClientVendorIndex clientVendorIndex,String index,String type,String id) {
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

public void saveOrUpdateElkclientVendorContactsIndexDocument(ClientVendorContactsIndex contactsIndex,String index,String type,String id) {
	String entityInJson = null;
	try {
	    entityInJson = toJSON(contactsIndex);
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


private long getNumberOfContactsPerClient(long orgRelationId, Connection conn11) {
	String numberOfContacts = "0";
	 /*DataSource dataSource1 = comboPooledDataSource();
	 Connection conn11 = null;*/
	 Statement stmt11 = null;
	 try {
		  //conn11 = dataSource1.getConnection();
			String sql = "SELECT  count(*) FROM sourcelead.ORG_SPOC where ORG_RELATION_ID = "+orgRelationId;
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

/**
 * getting connection
 * @return DataSource
 */ 
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
 
public static void main(String[] args) throws IOException, SQLException {
	TransferBISAssociates dump = new TransferBISAssociates();
	dump.transferAssociates();
}

}
