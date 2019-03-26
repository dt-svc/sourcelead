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

public class UpdateModules {
	

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
			 String sql = "SELECT * FROM "+dbconfig.getSchema()+".ORG org";
			 stmt1 = conn.createStatement();
			 ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()){
				 Long  orgId = rs.getLong("ID");
				 String orgName = rs.getString("ORG_NAME");
				 String moduleIds = rs.getString("MODULES_ID");
				
				  	String query = "update "+dbconfig.getSchema()+".ORG_LOCATIONS orgLoc set ORG_NAME = ?, MODULES_ID = ?, IS_PRIMARY = ?  where  orgLoc.ORG_ID = '"+orgId+"' ";
				  	 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
				  	  pstmt.setString(1, orgName); 
				  	  pstmt.setString(2, moduleIds); // set input parameter 1
				      pstmt.setString(3, "Y"); // set input parameter 2
				      pstmt.executeUpdate(); 
    	           }
		
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
	UpdateModules dump = new UpdateModules();
	dump.addModules();
}
}
