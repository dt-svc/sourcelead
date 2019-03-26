package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddMasterCandidateToCandidate {
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Venkat I
 * this method is using for adding mastercandidate to candidate	
 * @throws IOException
 * @throws SQLException
 */
public void addMasterCandidate() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		System.out.println("Adding Master Candidate To Candidate Starts");
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql1 = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			while(rs1.next()){
				Long masterCand = rs1.getLong("MASTER_CANDIDATE_ID");
				
				if(masterCand == 0){
				 String  email = rs1.getString("EMAIL");
				 String mobilePhone = rs1.getString("MOBILE_PHONE");
				 Long candidateId = rs1.getLong("ID");
				 String alternatePhone = rs1.getString("ALTERNATE_MOBILE_PHONE");
				 String  alternateEmail = rs1.getString("ALTERNATE_EMAIL");
				 
				 Date createdDate = rs1.getDate("CREATED_DATE");
		    	 String createdBy = rs1.getString("CREATED_BY");
				 
				 String firstName = rs1.getString("FIRST_NAME");
				 
				 String lastName = rs1.getString("LAST_NAME");
				 if(null == lastName || "".equals(lastName.trim())){
					 lastName = null;
				 }
				 
				 if(null == mobilePhone || "".equals(mobilePhone.trim())){
					 mobilePhone = null;
				 }
				 
				 if(null == alternatePhone || "".equals(alternatePhone.trim())){
					 alternatePhone = null;
				 }
				 
				 if(null == alternateEmail || "".equals(alternateEmail.trim())){
					 alternateEmail = null;
				 }
				 
				 String query = null;
				 PreparedStatement pstmt = null;
				 ResultSet rs2 = null;
				 if(null == mobilePhone || "".equals(mobilePhone.trim())){
					 query = "SELECT * FROM "+dbconfig.getSchema()+".MASTER_CANDIDATE where EMAIL = ? ";
					 pstmt = conn.prepareStatement(query); // create a statement
				     pstmt.setString(1, email); // set input parameter 1
				     rs2 = pstmt.executeQuery(); 
		    	 }else{
		    		 query = "SELECT * FROM "+dbconfig.getSchema()+".MASTER_CANDIDATE where (EMAIL = ?  OR  PHNO = ?)";
		    		 pstmt = conn.prepareStatement(query); // create a statement
				     pstmt.setString(1, email); // set input parameter 1
				     pstmt.setString(2, mobilePhone);
				     rs2 = pstmt.executeQuery(); 
		    	 }
				 
				 if (rs2.next()) {
					    do {
			    	    	Long masterCandidateId = rs2.getLong("ID");
			    	        String query3 = "UPDATE "+dbconfig.getSchema()+".CANDIDATE set MASTER_CANDIDATE_ID = ?  where ID = ? ";
						    PreparedStatement pstmt3 = conn.prepareStatement(query3); // create a statement
						    pstmt3.setLong(1, masterCandidateId);
						    pstmt3.setLong(2, candidateId);
						    pstmt3.executeUpdate();
				    		
			    	    } while(rs2.next());
			    	} else {
			    		
			    		String query4 = " insert into "+dbconfig.getSchema()+".MASTER_CANDIDATE (FIRST_NAME, LAST_NAME, EMAIL, ALTERNATE_EMAIL, PHNO, ALTERNATE_PHNO, STATUS, "
								+ "CREATED_DATE, CREATED_BY)"
								+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			    		
			    		try {
			    			
			    			 PreparedStatement pstmt4 = conn.prepareStatement(query4);
				    		 pstmt4.setString(1, firstName);
				    		 pstmt4.setString(2, lastName);
				    		 pstmt4.setString(3, email);
				    		 pstmt4.setString(4, alternateEmail);
				    		 pstmt4.setString(5, mobilePhone);
				    		 pstmt4.setString(6, alternatePhone);
				    		 pstmt4.setInt(7, 1);
				    		 pstmt4.setDate(8,(java.sql.Date) createdDate);
				    		 pstmt4.setString(9,createdBy);
				    		 pstmt4.executeUpdate();	
				    		 
			    		}catch (Exception e){   
							e.printStackTrace();
						}
			    	}
			 }
			}
			System.out.println("Adding Master Candidate To Candidate Ends");
		
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
	AddMasterCandidateToCandidate dump = new AddMasterCandidateToCandidate();
	dump.addMasterCandidate();
}

}