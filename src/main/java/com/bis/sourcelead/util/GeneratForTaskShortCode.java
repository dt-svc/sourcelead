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

public class GeneratForTaskShortCode {
	private ComboPooledDataSource comboPooledDataSource;
	private void convertBlobToFile(String table, String idColumn, String blobColumn, String fileNameColumn, String urlColumn, String bucketName, String environment)
	{
		 DataSource dataSource = comboPooledDataSource();
		try (Connection con = dataSource.getConnection()) {
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT onb.*, o.ID as ORG_ID,coId.ORG_LOCATION_ID FROM "+table+" onb, ORG o, ORG_LOCATIONS ol,candidate_on_boarding coId where o.ID = ol.ORG_ID and onb.CANDIDATE_ON_BOARDING_ID = coId.ID and coId.ORG_LOCATION_ID = ol.ID");
			int i = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			boolean flag = false;
			for (int j = 0; j < rsmd.getColumnCount(); j++) {
				if (rsmd.getColumnLabel(j+1).equalsIgnoreCase(urlColumn)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				con.createStatement().execute("alter table " + table + " add column(" + urlColumn + " varchar(4000))");
			}
			while (rs.next()) {
				String fileName = rs.getString(fileNameColumn);
				if(rs.getBlob(blobColumn) != null) {
				InputStream in = rs.getBlob(blobColumn).getBinaryStream();
				byte[] b = new byte[in.available()];
				in.read(b);
				File folder = null;
				String link = "";
				if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Contracts")) {
					folder = new File("C:/blob/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Contracts-Documents/");
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Forms")) {
					folder = new File("C:/blob/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Forms-Documents/");
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Policies")) {
					folder = new File("C:/blob/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Policies-Documetns/");
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Supported")) {
					folder = new File("C:/blob/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Supporting-Documents/");
				}
				 
				if(!folder.exists())
					folder.mkdirs();
				File file = new File(folder,fileName.substring(0, fileName.lastIndexOf("."))+"-"+(new Date().getTime())+(fileName.substring(fileName.lastIndexOf("."))));
				Files.write(file.toPath(), b);
				
				if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Contracts")) {
					link = "/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Contracts-Documents/"+file.getName();
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Forms")) {
					link = "/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Forms-Documents/"+file.getName();
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Policies")) {
					link = "/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Policies-Documetns/"+file.getName();
				}else if(rs.getString("DOCUMENT_TYPE").equalsIgnoreCase("Supported")) {
					link = "/slead-static/"+rs.getString("ORG_ID")+"/"+rs.getString("ORG_LOCATION_ID")+"/Onboard-Template-Supporting-Documents/"+file.getName();
				}
				 
				stmt1.addBatch("update " + table + " set " + urlColumn + " = '" + link.replace("'", "''") + "' where "
						+ idColumn + "='" + rs.getString(idColumn) + "'");
				i++;
				if (i % 25 == 0) {
					stmt1.executeBatch();
				}
				System.out.println(link);
				}
			}
			stmt1.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
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
	 
	public static void main(String... args)
	{
		long st = new Date().getTime();
		new GeneratForTaskShortCode().convertBlobToFile("ON_BOARDING_TEMPLATE_DOCUMENTS", "ID", "DOC_FILE", "FILE_NAME", "FILE_URL", "sourcelead", "dev");
		long end = new Date().getTime();
		System.out.println(end-st);
	}
}