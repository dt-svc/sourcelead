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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SetCoolingOffPeriodForOldOrgs {
	private ComboPooledDataSource comboPooledDataSource;
	
	public void addCoolingOffPeriodForOldOrgs() throws IOException, SQLException
	{
		Connection conn = null;
		Statement stmt1 = null;
		java.sql.PreparedStatement pstmt = null;
		try {
			DataSource dataSource = comboPooledDataSource();
			conn = dataSource.getConnection();
			String orgLocations = "SELECT * FROM sourcelead.ORG_LOCATIONS orgLocations where orgLocations.COOLINGOFF_PERIOD IS NULL";
			stmt1 = conn.createStatement();
			ResultSet rs2 = stmt1.executeQuery(orgLocations);
			while (rs2.next()) {
				Long orgLocationId = rs2.getLong("ID");
				String query = "update sourcelead.ORG_LOCATIONS set COOLINGOFF_PERIOD = ? where ID = ? ";
				pstmt = conn.prepareStatement(query); // create a statement
				pstmt.setString(1, Constants.DEFAULT_COOLINGOFF_PERIOD);
				pstmt.setLong(2, orgLocationId); // set input parameter 2
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				stmt1.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private DataSource comboPooledDataSource()
	{
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
		SetCoolingOffPeriodForOldOrgs dump = new SetCoolingOffPeriodForOldOrgs();
		dump.addCoolingOffPeriodForOldOrgs();
	}
}
