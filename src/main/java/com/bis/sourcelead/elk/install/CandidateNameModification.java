package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CandidateNameModification {
	

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author LAxman
 * this method is using for update the RequirmentUUID	
 * @throws IOException
 * @throws SQLException
 */
public void addModules() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
	try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".CANDIDATE candidate";
			 stmt1 = conn.createStatement();
			 ResultSet rs = stmt1.executeQuery(sql);
			 System.out.println("====Started=====");
			while(rs.next()){
				 Long  canId = rs.getLong("ID");
				 String firstName = rs.getString("FIRST_NAME");
				 String lastName = rs.getString("LAST_NAME");
				 String email = rs.getString("EMAIL");
				
				  	String query = "update "+dbconfig.getSchema()+".CANDIDATE can set FIRST_NAME = ?, LAST_NAME = ?  where  can.ID = '"+canId+"' ";
				  	 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
				  	 if(StringUtils.isNotBlank(firstName)){
				  	 String fName=	WordUtils.capitalizeFully(firstName);
				  	 pstmt.setString(1, fName); 
				  	 }else {
				  		if(StringUtils.isNotBlank(email)) {
				  			String str = email;
					        String [] arrOfStr = str.split("@");
					  		String fNameNull=	WordUtils.capitalizeFully(arrOfStr[0]);
						  	 pstmt.setString(1, fNameNull);  
				  		}else {
				  			 pstmt.setString(1, firstName); 
				  		}
				  		
				  	 }
				  	 String lName=	WordUtils.capitalizeFully(lastName);
				  	  pstmt.setString(2, lName); // set input parameter 1
				  	
				      pstmt.executeUpdate(); 
				     
    	           }
			 System.out.println("===Ending====");
		
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
	CandidateNameModification dump = new CandidateNameModification();
	dump.addModules();
}
}
