package com.bis.sourcelead.util;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SourceleadDataBaseInstallation {
	
	    private ComboPooledDataSource comboPooledDataSource;

		public static void main(String args[]) throws Exception {
			SourceleadDataBaseInstallation installer = new SourceleadDataBaseInstallation();
			installer.configureDatabase();
		}
		private void configureDatabase() throws Exception {
			Flyway flyway = new Flyway();
	
			flyway.setDataSource(comboPooledDataSource());
			flyway.setTable("schema_history");
			flyway.setSqlMigrationSuffix(".sql");
			flyway.setLocations("filesystem:src\\main\\resources\\migrations");
			try{
				flyway.migrate();	
			}
			catch(FlywayException e){
				    e.printStackTrace();
	                flyway.repair();
			}
			
		  }
		
		@Bean
		public DataSource comboPooledDataSource() {
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
	}
