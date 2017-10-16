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

public class AddDefaultProjectsToOrgLocations {
	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author Venkat I
 * this method is adding default projects for org locations
 * @throws IOException
 * @throws SQLException
 */
public void dumpDefaultProjects() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM sourcelead.ORG_LOCATIONS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding default projects starts-----");
			 while(rs1.next()){
				 
				 Long  orgLocId = rs1.getLong("ID");
				 String  orgLocName = rs1.getString("ORG_NAME");
				 String projectName = orgLocName+" Default Project";
				 Date startDate = rs1.getDate("CREATED_DATE");
				 String createdBy = rs1.getString("CREATED_BY");
				 Long userId = rs1.getLong("PRIMARY_CONTACT_USER_ID");
				 if(userId == 0)
					 continue;
				 
					 
		 		String query1 = " insert into sourcelead.PROJECTS (PROJECT_NAME, START_DATE, INVOICE_FREQUENCY, TIMESHEET_FREQUENCY, PROJECT_STATUS, IS_PRIMARY,  "
							+ "IS_DEFAULT_PROJECT,ORG_LOCATIONS_ID,PROJECT_UUID,PROJECT_MODE,CREATED_DATE, CREATED_BY)"
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    		
		    		try {
		    			
		    			 PreparedStatement pstmt1 = conn.prepareStatement(query1);
			    		 pstmt1.setString(1, projectName);
			    		 pstmt1.setDate(2,(java.sql.Date) startDate);
			    		 pstmt1.setString(3, "Weekly");
			    		 pstmt1.setString(4, "Weekly");
			    		 pstmt1.setString(5, "INPROGRESS");
			    		 pstmt1.setInt(6, 1);
			    		 pstmt1.setString(7,"Y");
			    		 pstmt1.setLong(8,orgLocId);
			    		 pstmt1.setString(9,SourceLeadRandomCodeHelper.generateRandomUUID());
			    		 pstmt1.setString(10,"Finished");
			    		 pstmt1.setDate(11,(java.sql.Date) startDate);
			    		 pstmt1.setString(12,createdBy);
			    		 pstmt1.executeUpdate();	
			    		 
		    		}catch (Exception e){   
						e.printStackTrace();
					}
		    		
		    		
		    		 String query2 = "SELECT * from sourcelead.PROJECTS where ORG_LOCATIONS_ID = '"+orgLocId+"' and IS_DEFAULT_PROJECT = 'Y' ";
					 java.sql.PreparedStatement pstmt = conn.prepareStatement(query2); 
					 ResultSet rs2 = pstmt.executeQuery();
					 Long projectId = null;
					 if (rs2.next()) {
						  projectId = rs2.getLong("ID");
						 
					 		String query3 = " insert into sourcelead.PROJECT_CLIENTS (PROJECT_ID, ORG_LOCATIONS_ID, UUID, STATUS,LEVEL,  "
									+ "CREATED_DATE, CREATED_BY)"
									+ " values (?, ?, ?, ?, ?, ?, ?)";
				    		
				    		try {
				    			 PreparedStatement pstmt2 = conn.prepareStatement(query3);
					    		 pstmt2.setLong(1, projectId);
					    		 pstmt2.setLong(2,orgLocId);
					    		 pstmt2.setString(3, SourceLeadRandomCodeHelper.generateRandomUUID());
					    		 pstmt2.setInt(4, 1);
					    		 pstmt2.setLong(5,1);
					    		 pstmt2.setDate(6,(java.sql.Date) startDate);
					    		 pstmt2.setLong(7,userId);
					    		
					    		 pstmt2.execute();	
					    		 
				    		}catch (Exception e){   
								e.printStackTrace();
							}
						 
					 }
					 
					 
					 
					 String query3 = "SELECT * from sourcelead.PROJECT_CLIENTS where ORG_LOCATIONS_ID = '"+orgLocId+"' and PROJECT_ID = '"+projectId+"' ";
					 java.sql.PreparedStatement pstmt1 = conn.prepareStatement(query3); 
					 ResultSet rs3 = pstmt1.executeQuery();
					 if (rs3.next()) {
						 Long  projectClientId = rs3.getLong("ID");
						 
					 		String query4 = " insert into sourcelead.PROJECT_APPROVER_HIERARCHY (PROJECT_ID, PROJECT_CLIENTS_ID, APPROVER_USER_ID, LEVEL, STATUS, "
									+ "CREATED_DATE, CREATED_BY)"
									+ " values (?, ?, ?, ?, ?, ?, ?)";
				    		
				    		try {
				    			
				    			 PreparedStatement pstmt3 = conn.prepareStatement(query4);
				    			 pstmt3.setLong(1, projectId);
				    			 pstmt3.setLong(2,projectClientId);
				    			 pstmt3.setLong(3, userId);
				    			 pstmt3.setInt(4, 1);
				    			 pstmt3.setString(5, "1");
				    			 pstmt3.setDate(6,(java.sql.Date) startDate);
				    			 pstmt3.setString(7,createdBy);
				    			 pstmt3.execute();
					    		 
				    		}catch (Exception e){   
								e.printStackTrace();
							}
						 
					 }
					 
				    	
		}
			System.out.println("----Adding default projects ends-----");
		
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
 
public static void main(String[] args) throws IOException, SQLException {
	AddDefaultProjectsToOrgLocations dump = new AddDefaultProjectsToOrgLocations();
	dump.dumpDefaultProjects();
}

}
