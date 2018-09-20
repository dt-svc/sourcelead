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

public class UpdateDateFormats {
	

	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author Ajith
 * this method is using for update the RequirmentUUID	
 * @throws IOException
 * @throws SQLException
 */
public void dates() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".ORG_LOCATIONS group by COUNTRY";
			 stmt1 = conn.createStatement();
			 ResultSet rs = stmt1.executeQuery(sql2);
			 System.out.println("Updating Dateformat Starts.....");
			 while(rs.next()){
					 String country = rs.getString("COUNTRY");
					 if(country != null && !country.isEmpty()){
						 String query = "update "+dbconfig.getSchema()+".ORG_LOCATIONS orgLoc set DATE_FORMAT = ? where  orgLoc.COUNTRY = '"+country+"' ";
					  	 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
					  	 if(country.equalsIgnoreCase("India")){
					  		pstmt.setString(1, "dd-MM-yyyy");
					  	 }else{
					  		pstmt.setString(1, "MM-dd-yyyy"); 
					  	 } 
					      pstmt.executeUpdate(); 
					 }
    	           }
			 System.out.println("Updating Dateformat Ends.....");
		
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
	UpdateDateFormats dump = new UpdateDateFormats();
	dump.dates();
}
}
