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

public class UpdateTimesheetSubmissionApproverHierarchyFields{
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Naresh Bolisetty
 * this method is used for update Submission Approver Hierarchy Fields
 * @throws IOException
 * @throws SQLException
 */
public void updateSahRecords() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmt4 = null;
	Statement stmt5 = null;
	Statement stmt6 = null;
	Statement stmt7 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		     System.out.println("----Updating Submission Approver Hierarchy Fields Starts-----");
			 String sql1 = "SELECT ID,HIERARCHY_ID,SUBMISSION_ID FROM "+dbconfig.getSchema()+".SUBMISSION_APPROVER_HIERARCHY WHERE  STATUS !='0'";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  sahId = rs1.getLong("ID");
				 Long  pahId = rs1.getLong("HIERARCHY_ID");
				 Long  submissionId = rs1.getLong("SUBMISSION_ID");
				 Long  projectId = 0l;
				 Long  masterCanidateId = 0l;
				 Long projectClientId = 0l;
				 Long orgLocationId = 0l;
				 Long orgId = 0l;
				 Long projectMemberId = 0l;
				 Long candidateId =0l;
				 Long consultantUserId = 0l;
				 
				  //Getting projectId,masterCandidateId from timesheetSubmssions
				  sql1 = "SELECT PROJECT_ID,MASTER_CANDIDATE_ID FROM "+dbconfig.getSchema()+".TIMESHEET_SUBMISSIONS  where  ID ="+submissionId+"";
				  stmt2 = conn.createStatement();
				  ResultSet rs2 = stmt2.executeQuery(sql1);
				  while(rs2.next()){
					    projectId = rs2.getLong("PROJECT_ID");
					    masterCanidateId = rs2.getLong("MASTER_CANDIDATE_ID");
				  }
				  
				  //Getting ProjectClientId from projectApproverHierarchy
				  sql1 = "SELECT PROJECT_CLIENTS_ID FROM "+dbconfig.getSchema()+".PROJECT_APPROVER_HIERARCHY  where  ID ="+pahId+"";
				  stmt3 = conn.createStatement();
				  ResultSet rs3 = stmt3.executeQuery(sql1);
				  if(rs3.next()) {
					  projectClientId = rs3.getLong("PROJECT_CLIENTS_ID");
					  
					  //Getting orgLocationId,orgId from ProjectClients
					  sql1 = "SELECT ORG_LOCATIONS_ID,ORG_ID FROM "+dbconfig.getSchema()+".PROJECT_CLIENTS  where  ID ="+projectClientId+"";
					  stmt4 = conn.createStatement();
					  ResultSet rs4 = stmt4.executeQuery(sql1);
					  while(rs4.next()){
						    orgLocationId = rs4.getLong("ORG_LOCATIONS_ID");
						    orgId = rs4.getLong("ORG_ID");
					  }
					  
					  //Getting projectMemberId,candidateId from Projectmembers by using projectClientId and masterCandidateId
					  sql1 = "SELECT ID,CANDIDATE_ID FROM "+dbconfig.getSchema()+".PROJECT_MEMBERS  where  MASTER_CANDIDATE_ID ="+masterCanidateId+" AND PROJECT_CLIENTS_ID = "+projectClientId+"" ;
					  stmt5 = conn.createStatement();
					  ResultSet rs5 = stmt5.executeQuery(sql1);
					  while(rs5.next()){
						    projectMemberId = rs5.getLong("ID");
						    candidateId = rs5.getLong("CANDIDATE_ID");
					  }
					  
					  //Getting consultant userId from Candidate
					  sql1 = "SELECT CONSULTANT_USER_ID FROM "+dbconfig.getSchema()+".CANDIDATE  where  ID ="+candidateId+"" ;
					  stmt6 = conn.createStatement();
					  ResultSet rs6 = stmt6.executeQuery(sql1);
					  if(rs6.next()  && rs6.getLong("CONSULTANT_USER_ID") != 0) {
						    consultantUserId = rs6.getLong("CONSULTANT_USER_ID");
					  }else {
						  sql1 = "SELECT CONSULTANT_USER_ID FROM "+dbconfig.getSchema()+".CANDIDATE  where  MASTER_CANDIDATE_ID ="+masterCanidateId+" AND CONSULTANT_USER_ID IS NOT NULL AND CONSULTANT_USER_ID !=0 " ;
						  stmt7 = conn.createStatement();
						  ResultSet rs7 = stmt7.executeQuery(sql1);
						  while(rs7.next()){
							  consultantUserId = rs7.getLong("CONSULTANT_USER_ID");
						  }
					  }
					  if(consultantUserId != 0) {
						      String query1 = " update "+dbconfig.getSchema()+".SUBMISSION_APPROVER_HIERARCHY set PROJECT_ID = ?, PROJECT_MEMBER_ID = ?, MASTER_CANDIDATE_ID = ? , CANDIDATE_ID = ? , CONSULTANT_USER_ID = ? , ORG_LOCATION_ID = ? , ORG_ID = ? , PROJECT_CLIENT_ID = ? where ID = '"+sahId+"' ";
					    	  java.sql.PreparedStatement pstmt = conn.prepareStatement(query1); 
							  pstmt.setLong(1, projectId); 
							  pstmt.setLong(2, projectMemberId);
							  pstmt.setLong(3, masterCanidateId);
							  pstmt.setLong(4, candidateId);
							  pstmt.setLong(5, consultantUserId);
							  pstmt.setLong(6, orgLocationId);
							  pstmt.setLong(7, orgId);
							  pstmt.setLong(8, projectClientId);
						      pstmt.executeUpdate(); 
					  }else {
						  String query1 = " update "+dbconfig.getSchema()+".SUBMISSION_APPROVER_HIERARCHY set PROJECT_ID = ?, PROJECT_MEMBER_ID = ?, MASTER_CANDIDATE_ID = ? , CANDIDATE_ID = ? , ORG_LOCATION_ID = ? , ORG_ID = ? , PROJECT_CLIENT_ID = ? where ID = '"+sahId+"' ";
				    	  java.sql.PreparedStatement pstmt = conn.prepareStatement(query1); 
						  pstmt.setLong(1, projectId); 
						  pstmt.setLong(2, projectMemberId);
						  pstmt.setLong(3, masterCanidateId);
						  pstmt.setLong(4, candidateId);
						  pstmt.setLong(5, orgLocationId);
						  pstmt.setLong(6, orgId);
						  pstmt.setLong(7, projectClientId);
					      pstmt.executeUpdate(); 
					  }
				   
				      
					System.out.println("Submission Approver Hierarchy Table with record id '"+sahId+"'  Updated Successfully....");
				  }
			}
			 System.out.println("----Updating Submission Approver Hierarchy Fields ends-----");
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
			stmt2.close();
			stmt3.close();
			stmt4.close();
			stmt5.close();
			stmt6.close();
			if(stmt7 != null)
			stmt7.close();
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
	UpdateTimesheetSubmissionApproverHierarchyFields dump = new UpdateTimesheetSubmissionApproverHierarchyFields();
	dump.updateSahRecords();
}

}
