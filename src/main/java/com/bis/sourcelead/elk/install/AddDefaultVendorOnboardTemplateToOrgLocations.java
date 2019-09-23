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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddDefaultVendorOnboardTemplateToOrgLocations {


	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is adding default projects for org locations
 * @throws IOException
 * @throws SQLException
 */
public void dumpDefaultVendorOnboardTemplate() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS where ID != '1' ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 
			 System.out.println("----Adding default vendor onboard template starts-----");
			 while(rs1.next()){
				 
				 Long  orgLocId = rs1.getLong("ID");
				 String  orgLocName = rs1.getString("ORG_NAME");
				 String templateName = orgLocName+" Default Vendor Onboard Template";
				 Date startDate = rs1.getDate("CREATED_DATE");
				 Long userId = rs1.getLong("PRIMARY_CONTACT_USER_ID");
				 if(userId == 0)
					 continue;
				 
				 
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE where ORG_LOCATION_ID = '"+orgLocId+"' and IS_DEFAULT_TEMPLATE = 'Y' ";
				 java.sql.PreparedStatement stmt = conn.prepareStatement(query); 
				 ResultSet rs = stmt.executeQuery();
				 
				 if(!rs.next()) {
					 
		 		   String query1 = " insert into "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE (TEMPLATE_NAME, TEMPLATE_UUID, TEMPLATE_STATUS, IS_DEFAULT_TEMPLATE, STATUS, ORG_LOCATION_ID, CREATED_DATE,  "
							+ "CREATED_BY,UPDATED_DATE,UPDATED_BY)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    		
		    		try {
		    			
		    			 PreparedStatement pstmt1 = conn.prepareStatement(query1);
			    		 pstmt1.setString(1, templateName);
			    		 pstmt1.setString(2,SourceLeadRandomCodeHelper.generateRandomUUID());
			    		 pstmt1.setString(3, "Finished");
			    		 pstmt1.setString(4, Constants.YES);
			    		 pstmt1.setInt(5, 1);
			    		 pstmt1.setLong(6, orgLocId);
			    		 pstmt1.setDate(7,(java.sql.Date) startDate);
			    		 pstmt1.setLong(8,userId);
			    		 pstmt1.setDate(9,(java.sql.Date) startDate);
			    		 pstmt1.setLong(10,userId);
			    		 pstmt1.executeUpdate();	
			    		 
		    		}catch (Exception e){   
						e.printStackTrace();
					}
		    		
		    		
		    		
		    		 String query2 = "SELECT * from "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE where ORG_LOCATION_ID = '"+orgLocId+"' and IS_DEFAULT_TEMPLATE = 'Y' ";
					 java.sql.PreparedStatement pstmt = conn.prepareStatement(query2); 
					 ResultSet rs2 = pstmt.executeQuery();
					 Long onboardTemplateId = null;
					 if (rs2.next()) {
						 onboardTemplateId = rs2.getLong("ID");
						 
						 
						 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS where VENDOR_ON_BOARDING_TEMPLATE_ID = '1' ";
						 java.sql.PreparedStatement pstmt1 = conn.prepareStatement(sql2); 
						 ResultSet rs3 = pstmt1.executeQuery();
						 
						 while (rs3.next()) {
							 
							 String documentName = rs3.getString("DOCUMENT_NAME");
							 String isRequired = rs3.getString("IS_REQUIRED");
							 
							 String query3 = " insert into "+dbconfig.getSchema()+".VENDOR_ON_BOARDING_TEMPLATE_DOCUMENTS (VENDOR_ON_BOARDING_TEMPLATE_ID, DOCUMENT_NAME, IS_REQUIRED, DOCUMENT_TYPE, DOCUMENT_UUID, STATUS, CREATED_DATE,  "
										+ "CREATED_BY,UPDATED_DATE,UPDATED_BY)"
										+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					    		
					    		try {
					    			 PreparedStatement pstmt2 = conn.prepareStatement(query3);
						    		 pstmt2.setLong(1, onboardTemplateId);
						    		 pstmt2.setString(2,documentName);
						    		 pstmt2.setString(3,isRequired);
						    		 pstmt2.setString(4,"Documents");
						    		 pstmt2.setString(5, SourceLeadRandomCodeHelper.generateRandomUUID());
						    		 pstmt2.setInt(6, 1);
						    		 pstmt2.setDate(7,(java.sql.Date) startDate);
						    		 pstmt2.setLong(8,userId);
						    		 pstmt2.setDate(9,(java.sql.Date) startDate);
						    		 pstmt2.setLong(10,userId);
						    		 pstmt2.executeUpdate();	
						    		 
					    		}catch (Exception e){   
									e.printStackTrace();
								}
						 }
						 
					 }
		}
	 }
			System.out.println("----Adding default vendor onboard template ends-----");
		
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
	AddDefaultVendorOnboardTemplateToOrgLocations dump = new AddDefaultVendorOnboardTemplateToOrgLocations();
	dump.dumpDefaultVendorOnboardTemplate();
}


}
