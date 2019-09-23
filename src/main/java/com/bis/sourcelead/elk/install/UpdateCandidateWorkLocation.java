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

public class UpdateCandidateWorkLocation {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Naresh Bolisetty
 * this method is used for update worklocations of candidates
 * @throws IOException
 * @throws SQLException
 */
public void updateContactEmail() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			
		     System.out.println("----Updating Offshore_C2C Candidate Worklocations Starts-----");
			 String sql1 = "SELECT PARENT_CANDIDATE_ID,ID FROM "+dbconfig.getSchema()+".CANDIDATE  where DESIGNATION_TYPE IS NOT NULL AND DESIGNATION_TYPE = 'Offshore C2C' ";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 while(rs1.next()){
				 Long  parentCandidateId = rs1.getLong("PARENT_CANDIDATE_ID");
				 Long  candidateId = rs1.getLong("ID");
				  sql1 = "SELECT WORK_LOCATION FROM "+dbconfig.getSchema()+".CANDIDATE  where  ID ="+parentCandidateId+"";
				  stmt3 = conn.createStatement();
				  ResultSet rs2 = stmt3.executeQuery(sql1);
				  while(rs2.next()){
					  Long  workLocationId = rs2.getLong("WORK_LOCATION");
						 
						 if(parentCandidateId != null && workLocationId != null && workLocationId != 0) {
								    String query1 = " update "+dbconfig.getSchema()+".CANDIDATE set WORK_LOCATION = '"+workLocationId+"' where ID = '"+candidateId+"' ";
							    	java.sql.PreparedStatement pstmt2 = conn.prepareStatement(query1); 
									pstmt2.executeUpdate(); 
						 }
				  }
				 
				 
				
			}
			System.out.println("----Updating Offshore_C2C Candidate Worklocations ends-----");
			
			System.out.println("----Updating  Vendor Candidate Worklocations Using C2C Candidates WorkLocation Starts-----");
			
			 String sql2= "SELECT MASTER_CANDIDATE_ID,WORK_LOCATION FROM "+dbconfig.getSchema()+".CANDIDATE can where DESIGNATION_TYPE IS NOT NULL AND DESIGNATION_TYPE = 'C2C' ";
			 stmt2 = conn.createStatement();
			 ResultSet rs2 = stmt2.executeQuery(sql2);
			 while(rs2.next()){
				 Long  masterCandidateId = rs2.getLong("MASTER_CANDIDATE_ID");
				 Long  workLocationId = rs2.getLong("WORK_LOCATION");
				 if(masterCandidateId != null && workLocationId != null && workLocationId != 0) {
						    String q1 = " update "+dbconfig.getSchema()+".CANDIDATE set WORK_LOCATION = '"+workLocationId+"' where MASTER_CANDIDATE_ID = '"+masterCandidateId+"' and DESIGNATION_TYPE IS NOT NULL AND (DESIGNATION_TYPE <> 'C2C' || DESIGNATION_TYPE <> 'Offshore C2C') ";
					    	java.sql.PreparedStatement pstmt3 = conn.prepareStatement(q1); 
					    	pstmt3.executeUpdate(); 
				 }
			}
			System.out.println("----Updating Offshore_C2C Candidate Worklocations ends-----");
			  
		
	}catch(Exception e){
		e.printStackTrace();
	}finally {
		try {
			stmt1.close();
			stmt2.close();
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
	UpdateCandidateWorkLocation dump = new UpdateCandidateWorkLocation();
	dump.updateContactEmail();
}

}
