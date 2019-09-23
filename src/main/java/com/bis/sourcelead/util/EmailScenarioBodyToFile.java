package com.bis.sourcelead.util;

import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class EmailScenarioBodyToFile {
	
	private ComboPooledDataSource comboPooledDataSource;
	@SuppressWarnings("resource")
	ApplicationContext ctx = new AnnotationConfigApplicationContext(PropertyPathConfiguration.class);
	DatabaseConnectionConfig dbconfig = ctx.getBean(DatabaseConnectionConfig.class);
	private void convertBlobToFile(String table, String idColumn, String bodyColumn, String urlColumn, String bucketName, String environment)
	{
		DataSource dataSource = comboPooledDataSource();
		try (Connection con = dataSource.getConnection()) {
				Statement stmt = con.createStatement();
				Statement stmt1 = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT emailSce.* FROM "+table+" emailSce");
				int i = 0;
				ResultSetMetaData rsmd = rs.getMetaData();
				boolean flag = false;
				for (int j = 0; j < rsmd.getColumnCount(); j++) {
					if (rsmd.getColumnLabel(j+1).equalsIgnoreCase(urlColumn)) {
						flag = true;
						break;
					}
				}
				while (rs.next()){
					if(rs.getString(bodyColumn) != null) {
						String body=rs.getString(bodyColumn);
						String fileName=rs.getString("UNIQUE_SCENARIO_NAME");
						String entityType=rs.getString("ENTITY_TYPE");
						
						File folder = new File(dbconfig.getStaticPath()+"/emailDocuments/");
						if(!folder.exists())
							folder.mkdirs();
						File createFile = new File(folder,fileName+entityType+".txt");
						BufferedWriter bw=new BufferedWriter(new FileWriter(createFile));
						bw.write(body);
						bw.close();
						String link = "/slead-static/"+"emailDocuments/"+createFile.getName();
						stmt1.addBatch("update " + table + " set " + urlColumn + " = '" + link.replace("'", "''") + "' where "
								+ idColumn + "='" + rs.getString(idColumn) + "'");
						i++;
						if (i % 25 == 0) {
							stmt1.executeBatch();
						}
						System.out.println(link);
						}
						
					}
				
		}catch (Exception ex) {
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
		new EmailScenarioBodyToFile().convertBlobToFile("EMAIL_SCENARIO", "ID", "BODY", "BODY_URL", "sourcelead", "dev");
		long end = new Date().getTime();
		System.out.println(end-st);
	}
}
