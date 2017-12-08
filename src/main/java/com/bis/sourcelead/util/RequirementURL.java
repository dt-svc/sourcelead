package com.bis.sourcelead.util;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bis.sourcelead.db.DatabaseConnectionConfig;
import com.bis.sourcelead.db.PropertyPathConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class RequirementURL {
	
	private ComboPooledDataSource comboPooledDataSource;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	
	private void addURLToAllRequirements(String table, String idColumn, String jobTitle, String url, String bucketName, String environment)
	{
		DataSource dataSource = comboPooledDataSource();
		try (Connection con = dataSource.getConnection()) {
			
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT req.*FROM " + table + " req");
			while (rs.next()) {
				String jobUrl = "";
				jobUrl = rs.getString(jobTitle).replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase();
				if (jobUrl.startsWith("-")){
					jobUrl = jobUrl.substring(1);
			    }
				if (jobUrl.endsWith("-")){
					jobUrl = jobUrl.substring(0, jobUrl.length() - 1);
			    }
				stmt1.addBatch("update " + table + " set " + url + " = '" + jobUrl+ "' where " + idColumn + "='" + rs.getString(idColumn) + "'");
				stmt1.executeBatch();
			}
			stmt1.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
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
	
	public static void main(String... args)
	{
		long st = new Date().getTime();
		new RequirementURL().addURLToAllRequirements("REQUIREMENT", "ID", "JOB_TITLE", "URL", "sourcelead", "dev");
		long end = new Date().getTime();
		System.out.println(end-st);
	}
}
