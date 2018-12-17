package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddLocationsToRequirement {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for adding locations to requirement
 * @throws IOException
 * @throws SQLException
 */
public void addLocations() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding locations to requirement starts-----");
			 while(rs1.next()){
				 Long  requirementId = rs1.getLong("ID");
				 String location = null;
				 StringBuilder jobLocation = new StringBuilder();
				 if(rs1.getString("LOCATION") == null || rs1.getString("LOCATION").isEmpty()) {
					 
					 if(rs1.getString("CITY") != null && !rs1.getString("CITY").isEmpty()) {
						 jobLocation = jobLocation.append(rs1.getString("CITY")).append(",");
					 }
					 
					 if(rs1.getString("STATE") != null && !rs1.getString("STATE").isEmpty()) {
						 jobLocation = jobLocation.append(rs1.getString("STATE")).append(",");
					 }
					 
					 if(rs1.getString("COUNTRY") != null && !rs1.getString("COUNTRY").isEmpty()) {
						 jobLocation = jobLocation.append(rs1.getString("COUNTRY")).append(",");
					 }
					 
					 if(jobLocation.toString() != null && !jobLocation.toString().isEmpty()) {
						 location =  jobLocation.toString();
					 }
					 
					 if(location != null && !location.isEmpty()) {
						 location = location.substring(0, location.length() - 1);
					 }
					
				 }else{
					 location =  rs1.getString("LOCATION");
				 }
				 
				
				 String country =  rs1.getString("COUNTRY");
				 String state =  rs1.getString("STATE");
				 String city =  rs1.getString("CITY");
				 String zipcode =  rs1.getString("ZIPCODE");
				 
				String query = " insert into "+dbconfig.getSchema()+".REQUIREMENT_LOCATIONS (REQUIREMENT_ID, LOCATION, COUNTRY, STATE, CITY, ZIPCODE, "
							+ "LOCATION_UID, STATUS)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
		    		
		    		try {
		    			
		    			 PreparedStatement pstmt = conn.prepareStatement(query);
		    			 pstmt.setLong(1, requirementId);
		    			 pstmt.setString(2, location);
		    			 pstmt.setString(3, country);
		    			 pstmt.setString(4, state);
		    			 pstmt.setString(5, city);
		    			 pstmt.setString(6, zipcode);
		    			 pstmt.setString(7, SourceLeadRandomCodeHelper.generateRandomUUID());
		    			 pstmt.setInt(8, 1);
		    			 pstmt.executeUpdate();	
			    		 
		    		}catch (Exception e){   
						e.printStackTrace();
					}
				
			}
			System.out.println("----Adding locations to requirement ends-----");
		
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
	AddLocationsToRequirement dump = new AddLocationsToRequirement();
	dump.addLocations();
}

}
