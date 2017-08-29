package com.bis.sourcelead.orgShortCodeIdForOldOrgs;

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

public class GenerateOrgShortCodeForOldOrgs {
	private ComboPooledDataSource comboPooledDataSource;
	public void OrgAnuUserInactiveForUnwantedOrg() throws IOException, SQLException {
		Connection conn = null ;
		Statement stmt1 = null;
	try {
		DataSource dataSource = comboPooledDataSource();
		conn = dataSource.getConnection();
		 String orgLocations = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.ORG_ID";
		 stmt1 = conn.createStatement();
		 ResultSet rs2 = stmt1.executeQuery(orgLocations);
		 while(rs2.next()){
			 
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
	
	public static void main(String[] args) {
		

	}

}
