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

public class UpdateJobUrl {
	

private ComboPooledDataSource comboPooledDataSource;
ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
/**
 * @author sathish
 * this method is using for update the jobUrl	
 * @throws IOException
 * @throws SQLException
 */
public void jobUrl() throws IOException, SQLException {
	Connection conn = null ;
	Statement stmt1 = null;
try {
		
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
			 String sql2 = "SELECT * FROM "+dbconfig.getSchema()+".REQUIREMENT";
			 stmt1 = conn.createStatement();
			 ResultSet rs = stmt1.executeQuery(sql2);
			 System.out.println("Updating JobURL Starts.....");
			 while(rs.next()){
					 String title = rs.getString("JOB_TITLE");
					 if(title != null && !title.isEmpty()){
						 String query = "update "+dbconfig.getSchema()+".REQUIREMENT req set URL = ? where  req.JOB_TITLE = '"+title+"' ";
					  	 java.sql.PreparedStatement pstmt = conn.prepareStatement(query); // create a statement
					  	title = rs.getString("JOB_TITLE");
					  	title = title.replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase();
					  	 if (title.startsWith("-")){
					  		title = title.substring(1);
					    }
						if (title.endsWith("-")){
							title = title.substring(0, title.length() - 1);
					    }
					  		pstmt.setString(1, title);
					  	
					      pstmt.executeUpdate(); 
					 }
    	           }
			 System.out.println("Updating JobURL Ends.....");
		
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
	UpdateJobUrl dump = new UpdateJobUrl();
	dump.jobUrl();
}
}
