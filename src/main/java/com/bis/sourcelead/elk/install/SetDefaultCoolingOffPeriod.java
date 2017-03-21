package com.bis.sourcelead.elk.install;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SetDefaultCoolingOffPeriod {
		private ComboPooledDataSource comboPooledDataSource;
		
		
	private  void setDefaultCoolinOffPeriod() throws IOException, SQLException {
		 
		try {
				 DataSource dataSource = comboPooledDataSource();
				 Connection	 conn = dataSource.getConnection();
				 Statement stmtOrgLoc = conn.createStatement();
				 Statement stmtReq = conn.createStatement();
					
				 String queryOrgLoc = "update sourcelead.ORG_LOCATIONS orgLocations set orgLocations.COOLINGOFF_PERIOD = ? ";
			     java.sql.PreparedStatement pstmtOrgLoc = conn.prepareStatement(queryOrgLoc); // create a statement
			     
			     pstmtOrgLoc.setString(1, "3 Months"); // set input parameter 1
			     pstmtOrgLoc.executeUpdate(); 
			     stmtOrgLoc.close();
			     
			     String queryReq = "update sourcelead.REQUIREMENT requirement set requirement.REQ_COOLINGOFF_PERIOD = ? ";
			     java.sql.PreparedStatement pstmtReq = conn.prepareStatement(queryReq); // create a statement
			     
			     pstmtReq.setString(1, "3 Months"); // set input parameter 1
			     pstmtReq.executeUpdate(); 
			     stmtReq.close();
			     
			     conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
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
		SetDefaultCoolinOffPeriod dump = new SetDefaultCoolinOffPeriod();
		dump.setDefaultCoolinOffPeriod();
	}


}