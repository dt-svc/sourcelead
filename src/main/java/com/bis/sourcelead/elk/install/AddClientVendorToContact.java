package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddClientVendorToContact {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * 
 * this method is using for update email for contacts
 * @throws IOException
 * @throws SQLException
 */
@SuppressWarnings("unused")
public void updateClientVendorToContact() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmt4 = null;
	Statement stmt5 = null;
	Statement stmt6 = null;
	Statement stmt7 = null;
	Statement stmt8 = null;
	Statement stmt9 = null;
	try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		
		String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG_SPOC";
		 stmt1 = conn.createStatement();
		 ResultSet rs = stmt1.executeQuery(sql);
		 
		 while(rs.next()){
			
			 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_RELATIONS where ID='"+rs.getLong("ORG_RELATION_ID")+"' ";
			 stmt2 = conn.createStatement();
			 ResultSet rs2= stmt2.executeQuery(sql2);
			 if(rs2.next()){
				 
				 String status=rs.getString("INVITATION_STATUS");
				 
				 if(rs2.getString("INITIATOR_TYPE").equalsIgnoreCase("client")){
					 Long refferalId=rs.getLong("CLIENT_USER_ID");
					 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".USER where ID='"+rs.getLong("VENDOR_USER_ID")+"' ";
					 stmt3 = conn.createStatement();
					 ResultSet rs3= stmt3.executeQuery(sql3);
					 
					 String sql11 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ID='"+rs2.getLong("LOCATION_ID")+"' ";
					 stmt8 = conn.createStatement();
					 ResultSet rs11= stmt8.executeQuery(sql11);
					 String companyName=null;
					 if(rs11.next()){
						 companyName=rs11.getString("ORG_NAME");
					 }
					 
					 if(rs3.next()){
						 
						 String sqla = "SELECT * FROM "+dbconfig.getSchema()+".REFER_CONTACTS where REFER_FROM_USER_ID='"+refferalId+"' and CONTACT_USER_EMAIL='"+rs3.getString("EMAIL")+"' ";
						 stmt6 = conn.createStatement();
						 ResultSet rs6= stmt6.executeQuery(sqla);
						 
						 if(!rs6.next()){
							 String  firstName = rs3.getString("FIRST_NAME");
							 String  lastName = rs3.getString("LAST_NAME");
							 String  email = rs3.getString("EMAIL");
							 String  mobilePhone = rs3.getString("MOBILE_PHONE");
							 String  createdBy = rs3.getString("CREATED_BY");
							 Date createdtDate = rs3.getDate("CREATED_DATE");
							 Long contactID=rs3.getLong("ID");
							 
							 String query2 = " insert into "+dbconfig.getSchema()+".REFER_CONTACTS (REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_PHONE,  "
										+ "CREATED_DATE, CREATED_BY,CONTACT_USER_ID,VENDOR_TYPE,COMPANY_NAME)"
										+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					    		
					    		try {
					    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
						    		 pstmt2.setLong(1, refferalId);
						    		 pstmt2.setString(2,firstName);
						    		 pstmt2.setString(3, lastName);
						    		 pstmt2.setString(4, email);
						    		 pstmt2.setString(5,mobilePhone);
						    		 pstmt2.setDate(6,(java.sql.Date) createdtDate);
						    		 pstmt2.setString(7,createdBy);
						    		 pstmt2.setLong(8, contactID);
						    		 pstmt2.setInt(9, 1);
						    		 pstmt2.setString(10,companyName);
						    		 pstmt2.execute();
						    		 
						    		
						    		 
					    		}catch (Exception e){   
									e.printStackTrace();
								}
						 
						 }
						 if(status.equalsIgnoreCase("accepted")){
			    			 
			    			 Long refferalId1=rs.getLong("VENDOR_USER_ID");
							 String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".USER where ID='"+rs.getLong("CLIENT_USER_ID")+"' ";
							 stmt4 = conn.createStatement();
							 ResultSet rs4= stmt4.executeQuery(sql4);
							 
							 String sql12 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ID='"+rs2.getLong("RESPONDER_LOCATION_ID")+"' "; 
							 stmt9 = conn.createStatement();
							 ResultSet rs12= stmt8.executeQuery(sql12);
							 String companyName1=null;
							 if(rs12.next()){
								 companyName1=rs12.getString("ORG_NAME");
							 }
							 
								 if(rs4.next()){
									 
									 String sqlb = "SELECT * FROM "+dbconfig.getSchema()+".REFER_CONTACTS where REFER_FROM_USER_ID='"+refferalId1+"' and CONTACT_USER_EMAIL='"+rs4.getString("EMAIL")+"' ";
									 stmt6 = conn.createStatement();
									 ResultSet rs7= stmt6.executeQuery(sqlb);
									 
									 if(!rs7.next()){
										 
										 String  firstName1 = rs4.getString("FIRST_NAME");
										 String  lastName1 = rs4.getString("LAST_NAME");
										 String  email1 = rs4.getString("EMAIL");
										 String  mobilePhone1 = rs4.getString("MOBILE_PHONE");
										 String  createdBy1 = rs4.getString("CREATED_BY");
										 Date createdtDate1 = rs4.getDate("CREATED_DATE");
										 Long contactID1=rs4.getLong("ID");
										 
										 String query4 = " insert into "+dbconfig.getSchema()+".REFER_CONTACTS (REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_PHONE,  "
													+ "CREATED_DATE, CREATED_BY,CONTACT_USER_ID,CLIENT_TYPE,COMPANY_NAME)"
													+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
								    		
										 
										 try{
											 PreparedStatement pstmt4 = conn.prepareStatement(query4);
							    			 pstmt4.setLong(1, refferalId1);
							    			 pstmt4.setString(2,firstName1);
							    			 pstmt4.setString(3, lastName1);
							    			 pstmt4.setString(4, email1);
							    			 pstmt4.setString(5,mobilePhone1);
							    			 pstmt4.setDate(6,(java.sql.Date) createdtDate1);
								    		 pstmt4.setString(7,createdBy1);
								    		 
								    		 pstmt4.setLong(8, contactID1);
								    		 pstmt4.setInt(9,1);
								    		 pstmt4.setString(10,companyName1);
								    		 pstmt4.execute();
										 }catch (Exception e){   
												e.printStackTrace();
											}
									
									 	}
									 
									 }
							 
							 
							
			    		 }
						 
						}
					 
					
				 }else{
					 

					 Long refferalId=rs.getLong("VENDOR_USER_ID");
					 String sql3 = "SELECT * FROM "+dbconfig.getSchema()+".USER where ID='"+rs.getLong("CLIENT_USER_ID")+"' ";
					 stmt1 = conn.createStatement();
					 ResultSet rs3= stmt1.executeQuery(sql3);
					 
					 if(rs3.next()){
						 
						 String sqla = "SELECT * FROM "+dbconfig.getSchema()+".REFER_CONTACTS where REFER_FROM_USER_ID='"+refferalId+"' and CONTACT_USER_EMAIL='"+rs3.getString("EMAIL")+"' ";
						 stmt6 = conn.createStatement();
						 ResultSet rs6= stmt6.executeQuery(sqla);
						 
						 String sql11 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ID='"+rs2.getLong("RESPONDER_LOCATION_ID")+"' ";
						 stmt8 = conn.createStatement();
						 ResultSet rs11= stmt8.executeQuery(sql11);
						 String companyName=null;
						 if(rs11.next()){
							 companyName=rs11.getString("ORG_NAME");
						 }
						 
						 if(!rs6.next()){
							 String  firstName = rs3.getString("FIRST_NAME");
							 String  lastName = rs3.getString("LAST_NAME");
							 String  email = rs3.getString("EMAIL");
							 String  mobilePhone = rs3.getString("MOBILE_PHONE");
							 String  createdBy = rs3.getString("CREATED_BY");
							 Date createdtDate = rs3.getDate("CREATED_DATE");
							 Long contactID=rs3.getLong("ID");
							 
							 String query2 = " insert into "+dbconfig.getSchema()+".REFER_CONTACTS (REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_PHONE,  "
										+ "CREATED_DATE, CREATED_BY,CONTACT_USER_ID,CLIENT_TYPE,COMPANY_NAME)"
										+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
					    		
					    		try {
					    			 PreparedStatement pstmt2 = conn.prepareStatement(query2);
						    		 pstmt2.setLong(1, refferalId);
						    		 pstmt2.setString(2,firstName);
						    		 pstmt2.setString(3, lastName);
						    		 pstmt2.setString(4, email);
						    		 pstmt2.setString(5,mobilePhone);
						    		 pstmt2.setDate(6,(java.sql.Date) createdtDate);
						    		 pstmt2.setString(7,createdBy);
						    		 pstmt2.setLong(8, contactID);
						    		 pstmt2.setInt(9,1);
						    		 pstmt2.setString(10,companyName);
						    		 pstmt2.execute();
						    		 
					    		}catch (Exception e){   
									e.printStackTrace();
								}
						 
						 	}
						 
						 }
					 if(status.equalsIgnoreCase("accepted")){
		    			 
		    			 Long refferalId1=rs.getLong("CLIENT_USER_ID");
						 String sql4 = "SELECT * FROM "+dbconfig.getSchema()+".USER where ID='"+rs.getLong("VENDOR_USER_ID")+"' ";
						 stmt2 = conn.createStatement();
						 ResultSet rs4= stmt2.executeQuery(sql4);
		    			 
						 if(rs4.next()){
							 
							 String sqlb = "SELECT * FROM "+dbconfig.getSchema()+".REFER_CONTACTS where REFER_FROM_USER_ID='"+refferalId1+"' and CONTACT_USER_EMAIL='"+rs4.getString("EMAIL")+"' ";
							 stmt6 = conn.createStatement();
							 ResultSet rs7= stmt6.executeQuery(sqlb);
							 
							 String sql11 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ID='"+rs2.getLong("LOCATION_ID")+"' ";
							 stmt8 = conn.createStatement();
							 ResultSet rs11= stmt8.executeQuery(sql11);
							 String companyName11=null;
							 if(rs11.next()){
								 companyName11=rs11.getString("ORG_NAME");
							 }
							 
							 if(!rs7.next()){
								 String  firstName1 = rs4.getString("FIRST_NAME");
								 String  lastName1 = rs4.getString("LAST_NAME");
								 String  email1 = rs4.getString("EMAIL");
								 String  mobilePhone1 = rs4.getString("MOBILE_PHONE");
								 String  createdBy1 = rs4.getString("CREATED_BY");
								 Date createdtDate1 = rs4.getDate("CREATED_DATE");
								 Long contactID1=rs4.getLong("ID");
								 
								 String query4 = " insert into "+dbconfig.getSchema()+".REFER_CONTACTS (REFER_FROM_USER_ID, CONTACT_USER_FIRSTNAME, CONTACT_USER_LASTNAME, CONTACT_USER_EMAIL, CONTACT_USER_PHONE,  "
											+ "CREATED_DATE, CREATED_BY,CONTACT_USER_ID,VENDOR_TYPE,COMPANY_NAME)"
											+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						    		
								 
								 try{
									 PreparedStatement pstmt4 = conn.prepareStatement(query4);
					    			 pstmt4.setLong(1, refferalId1);
					    			 pstmt4.setString(2,firstName1);
					    			 pstmt4.setString(3, lastName1);
					    			 pstmt4.setString(4, email1);
					    			 pstmt4.setString(5,mobilePhone1);
					    			 pstmt4.setDate(6,(java.sql.Date) createdtDate1);
						    		 pstmt4.setString(7,createdBy1);
						    		 pstmt4.setLong(8, contactID1);
						    		 pstmt4.setInt(9,1);
						    		 pstmt4.setString(10,companyName11);
						    		 pstmt4.execute();
								 }catch (Exception e){   
										e.printStackTrace();
									}
							 
							 	}
							 
							}
		    		 }
					 
				 
					 
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
}


/**
 * getting connection
 * @return DataSource
 */ 
private DataSource comboPooledDataSource() {
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
 
public static void main(String[] args) throws IOException, SQLException {
	AddClientVendorToContact dump = new AddClientVendorToContact();
	dump.updateClientVendorToContact();
}

}
