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

public class AddProjectIDAndMasterCandidateIDToTimesheetSubmissions {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Naresh Bolisetty
 * this method is using for update candidate id and master candidate id to project members
 * @throws IOException
 * @throws SQLException
 */
public void dumpMasterCandidateIdToTimesheetSubmission() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".TIMESHEET_SUBMISSIONS";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			
			 System.out.println("----Adding ProjectId and MasterCandidateId  to Timesheet Submissions starts-----");
			 while(rs1.next()){
				 Long  submissionId = rs1.getLong("ID");
				 Long  projectMemberId = rs1.getLong("PROJECT_MEMBER_ID");
				 
				 String query = "SELECT * from "+dbconfig.getSchema()+".PROJECT_MEMBERS where ID = '"+projectMemberId+"' ";
				 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); 
				 ResultSet rs2 = pstmt.executeQuery();
			 	if (rs2.next()) {
			 		Long  masterCandidateId = rs2.getLong("MASTER_CANDIDATE_ID");
			 		Long  projectId = rs2.getLong("PROJECT_ID");
			    		if(projectId == 0 || masterCandidateId ==0)
			    			continue;
			    		String query2 = " update "+dbconfig.getSchema()+".TIMESHEET_SUBMISSIONS set MASTER_CANDIDATE_ID = ?, PROJECT_ID = ? where ID = ? ";
			    		java.sql.PreparedStatement pstmt3 = conn.prepareStatement(query2); 
			    		pstmt3.setLong(1, masterCandidateId);
			    		pstmt3.setLong(2, projectId);
			    		pstmt3.setLong(3, submissionId);
			    		pstmt3.executeUpdate(); 
		    	} 
				
			}
			System.out.println("----Adding ProjectId and MasterCandidateId  to Timesheet Submissions end-----");
		
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
	AddProjectIDAndMasterCandidateIDToTimesheetSubmissions dump = new AddProjectIDAndMasterCandidateIDToTimesheetSubmissions();
	dump.dumpMasterCandidateIdToTimesheetSubmission();
}

}
