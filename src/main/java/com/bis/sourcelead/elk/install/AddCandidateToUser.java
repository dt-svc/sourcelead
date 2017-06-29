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
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class AddCandidateToUser {
	private ComboPooledDataSource comboPooledDataSource;
	
/**
 * @author Venkat I
 * this method is using for adding candidate to user	
 * @throws IOException
 * @throws SQLException
 */
public void addCandidteToUser() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		System.out.println("Adding Candidate To User Starts");
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql1 = "SELECT * FROM sourcelead.USER_ROLE userRole";
			 stmt1 = conn.createStatement();
			 ResultSet rs1 = stmt1.executeQuery(sql1);
			 
			while(rs1.next()){
				 Long  userId = rs1.getLong("USER_ID");
				 Long orgLocationId = rs1.getLong("ORG_LOCATIONS_ID");
				 Date createdDate = rs1.getDate("CREATED_DATE");
		    	 String createdBy = rs1.getString("CREATED_BY");
				
				 String query = "SELECT * FROM sourcelead.USER where ID = ? ";
			     PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
			     pstmt.setLong(1, userId); // set input parameter 1
			     ResultSet rs2 = pstmt.executeQuery(); 
			     
			     
			     while(rs2.next()){
			    	 String email = rs2.getString("EMAIL");
			    	 String firstName = rs2.getString("FIRST_NAME");
			    	 String lastName = rs2.getString("LAST_NAME");
			    	 String mobilePhone = rs2.getString("MOBILE_PHONE");
			    	 if(null == mobilePhone || "".equals(mobilePhone.trim())){
			    		 mobilePhone = null;
			    	 }
			    	 
				     
				     String query2 = "SELECT * FROM sourcelead.CANDIDATE where ORG_LOCATION_ID = ? and EMAIL = ? ";
				     PreparedStatement pstmt2 = conn.prepareStatement(query2); // create a statement
				     pstmt2.setLong(1, orgLocationId);
				     pstmt2.setString(2, email);// set input parameter 2
				     ResultSet rs3 = pstmt2.executeQuery();
				     
				     if (rs3.next()) {
				    	    do {
				    	    	Long candidateId = rs3.getLong("ID");
				    	    	String query3 = "UPDATE sourcelead.CANDIDATE set CONSULTANT_USER_ID = ?  where ID = ? ";
							    PreparedStatement pstmt3 = conn.prepareStatement(query3); // create a statement
							    pstmt3.setLong(1, userId);
							    pstmt3.setLong(2, candidateId);
							    pstmt3.executeUpdate();
					    		
				    	    } while(rs3.next());
				    	} else {
				    	    
				    		String query4 = " insert into sourcelead.CANDIDATE (ORG_LOCATION_ID, CONSULTANT_USER_ID, CANDIDATE_TYPE, FIRST_NAME, LAST_NAME, EMAIL, IS_FAVOURITE, "
									+ "IS_ASSOCIATE, CANDIDATE_UUID, CREATED_DATE, CREATED_BY, MOBILE_PHONE, STATUS)"
									+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				    		
				    		try {
				    			
				    			 PreparedStatement pstmt4 = conn.prepareStatement(query4);
					    		 pstmt4.setLong(1, orgLocationId);
					    		 pstmt4.setLong(2, userId);
					    		 pstmt4.setString(3, "Candidate");
					    		 pstmt4.setString(4, firstName);
					    		 pstmt4.setString(5, lastName);
					    		 pstmt4.setString(6, email);
					    		 pstmt4.setInt(7, 0);
					    		 pstmt4.setString(8, "N");
					    		 pstmt4.setString(9, SourceLeadRandomCodeHelper.generateRandomUUID());
					    		 pstmt4.setDate(10,(java.sql.Date) createdDate);
					    		 pstmt4.setString(11,createdBy);
					    		 pstmt4.setString(12, mobilePhone);
					    		 pstmt4.setInt(13, 1);
					    		 pstmt4.executeUpdate();	
					    		 
				    		}catch (Exception e){   
								e.printStackTrace();
							}
				    		 
				    	}
				     
				     
			     }
			}
			
		 System.out.println("Adding Candidate To User Ends");
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
	AddCandidateToUser actu = new AddCandidateToUser();
	actu.addCandidteToUser();
}

}
