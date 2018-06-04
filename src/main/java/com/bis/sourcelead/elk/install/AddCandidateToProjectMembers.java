package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddCandidateToProjectMembers {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
/**
 * @author Venkat I
 * this method is using for update candidate id and master candidate id to project members
 * @throws IOException
 * @throws SQLException
 */
public void dumpCandidateIdToProjectMembers() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".PROJECT_MEMBERS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding candidate to Project Members starts-----");
			 while(rs1.next()){
				 
				 Long  employeeId = rs1.getLong("EMPLOYEE_ID");
				 Long projectMemberId = rs1.getLong("ID");
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".EMPLOYEE where ID = '"+employeeId+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
				
				 
			 	if (rs2.next()) {
			 		Long  userId = rs2.getLong("USER_ID");
			 		Long  orgLocationId = rs2.getLong("ORG_LOCATIONS_ID");
			 		String query1 = "SELECT * from "+dbconfig.getSchema()+".CANDIDATE where CONSULTANT_USER_ID = ? and ORG_LOCATION_ID = ? ";
			    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
			    	pstmt2.setLong(1, userId);
			    	pstmt2.setLong(2, orgLocationId);
			    	ResultSet rs3 = pstmt2.executeQuery();
			    	
			    	if (rs3.next()) {
			    		Long  candidateId = rs3.getLong("ID");
			    		Long  masterCandidateId = rs3.getLong("MASTER_CANDIDATE_ID");
			    		if(candidateId == 0 || masterCandidateId ==0)
			    			continue;
			    		String query2 = " update "+dbconfig.getSchema()+".PROJECT_MEMBERS set MASTER_CANDIDATE_ID = ?, CANDIDATE_ID = ? where ID = ? ";
			    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
			    		pstmt3.setLong(1, masterCandidateId);
			    		pstmt3.setLong(2, candidateId);
			    		pstmt3.setLong(3, projectMemberId);
			    		pstmt3.executeUpdate(); 
			    	}
		    	} 
				
			}
			System.out.println("----Adding candidate to Project Members ends-----");
		
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
	AddCandidateToProjectMembers dump = new AddCandidateToProjectMembers();
	dump.dumpCandidateIdToProjectMembers();
}

}
